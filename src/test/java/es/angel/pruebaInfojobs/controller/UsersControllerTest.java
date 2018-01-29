package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.exception.BadRequestException;
import es.angel.pruebaInfojobs.exception.NotFoundException;
import es.angel.pruebaInfojobs.infrastructure.InMemoryDatabase;
import es.angel.pruebaInfojobs.model.Response;
import es.angel.pruebaInfojobs.model.Users;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class UsersControllerTest {


    private UsersController usersController = new UsersController();

    @Test(expected = NotFoundException.class)
    public void when_execute_a_post_request_with_invalid_path_should_return_not_found() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "dasdas");
        usersController.doPost(params);
    }

    @Test(expected = BadRequestException.class)
    public void when_execute_a_post_request_and_user_data_is_invalid_should_return_bad_request() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "");
        usersController.doPost(params);
    }

    @Test
    public void when_execute_a_post_request_with_valid_data_should_return_ok_response() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "");
        params.put("userName", "test");
        params.put("password", "pwd1");
        params.put("roles", "admin");
        final Response response = usersController.doPost(params);
        assertThat(response.getStatusCode(), is(201));
        InMemoryDatabase.getInstance().erase("users", "test1");
    }

    @Test
    public void when_execute_a_get_request_for_all_users_should_return_ok_response() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "");
        final Response response = usersController.doGet(params);
        assertThat(response.getStatusCode(), is(200));
    }

    @Test(expected = NotFoundException.class)
    public void when_execute_a_get_request_for_a_user_that_not_exists_should_return_not_found() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "usertes");
        usersController.doGet(params);
    }

    @Test
    public void when_execute_a_get_request_for_ones_user_should_return_ok_response() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "admin");
        final Response response = usersController.doGet(params);
        assertThat(response.getStatusCode(), is(200));
    }

    @Test(expected = BadRequestException.class)
    public void when_execute_a_put_request_without_path_query_should_return_bad_request() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "");
        usersController.doPut(params);
    }

    @Test(expected = BadRequestException.class)
    public void when_execute_a_put_request_with_path_query_and_bad_parametes_should_return_bad_request() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "user1");
        usersController.doPut(params);

    }

    @Test
    public void when_execute_a_put_request_with_path_query_and_good_parametes_should_return_ok() {
        InMemoryDatabase.getInstance().write("users",new Users.Builder().withUserName("test2").withPassword("pwd2").build());
        final Map<String, String> params = new HashMap<>();
        params.put("userName", "test2");
        params.put("password", "pwd2");
        params.put("roles", "page2");
        params.put("PATH_QUERY", "test2");
        final Response response = usersController.doPut(params);
        assertThat(response.getStatusCode(), is(200));
        assertThat(Arrays.asList("page2"),
                is(((Users) InMemoryDatabase.getInstance().readOne("users", "test2").get()).getRoles()));
        InMemoryDatabase.getInstance().erase("users","test2");
    }

    @Test(expected = BadRequestException.class)
    public void when_execute_a_delete_request_without_path_query_should_return_bad_request() {
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "");
        usersController.doDelete(params);
    }

    @Test
    public void when_execute_a_delete_with_path_query_should_retunt_not_content_and_not_exists_user() {
        InMemoryDatabase.getInstance().write("users",new Users.Builder().withUserName("test2").withPassword("pwd2").build());
        final Map<String, String> params = new HashMap<>();
        params.put("PATH_QUERY", "test2");
        final Response response = usersController.doDelete(params);
        assertThat(response.getStatusCode(), is(204));
        assertFalse(InMemoryDatabase.getInstance().readOne("users", "test2").isPresent());
    }

}
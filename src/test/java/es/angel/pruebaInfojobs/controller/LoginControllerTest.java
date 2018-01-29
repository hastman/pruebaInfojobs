package es.angel.pruebaInfojobs.controller;

import es.angel.pruebaInfojobs.exception.UnAuthorizedException;
import es.angel.pruebaInfojobs.model.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LoginControllerTest {

    private LoginController loginController = new LoginController();

    @Test(expected = UnAuthorizedException.class)
    public void when_try_to_login_without_correctness_credentials_should_throw_unauthorized(){
        final Map<String, String> params = new HashMap<>();
        params.put("userName","adsas");
        params.put("password","123");
        loginController.doPost(params);
    }

    @Test
    public void when_try_to_login_with_correctness_credentials_should_return_ok_respones(){
        final Map<String, String> params = new HashMap<>();
        params.put("userName","admin");
        params.put("password","admin");
        Response response = loginController.doPost(params);
        assertThat(response.getStatusCode(),is(200));
    }

}
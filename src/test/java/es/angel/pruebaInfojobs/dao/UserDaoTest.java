package es.angel.pruebaInfojobs.dao;

import es.angel.pruebaInfojobs.infrastructure.InMemoryDatabase;
import es.angel.pruebaInfojobs.model.Users;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class UserDaoTest {


    private UserDao userDao = new UserDao();
    InMemoryDatabase databaseChecker = InMemoryDatabase.getInstance();

    @Test
    public void when_store_a_new_user_should_be_exists_in_database(){
        final Users newUser = new Users.Builder().withPassword("password").withRoles(Arrays.asList("roles")).withUserName("userfortest").build();
        userDao.save(newUser);
        assertTrue(databaseChecker.readOne("users","userfortest").isPresent());
    }

    @Test
    public void when_read_all_users_should_return_all_users(){
        final List<Users> users = userDao.readAll();
        assertThat(users,is(databaseChecker.read("users")));
    }

    @Test
    public void when_read_one_users_should_be_returned(){
        final Users readOne = userDao.findOne("user1").get();
        assertThat(readOne, notNullValue());
    }

    @Test
    public void when_read_one_and_not_exists_should_be_not_present(){
        assertFalse(userDao.findOne("usernotregisterd").isPresent());
    }

    @Test
    public void when_delete_one_users_should_not_exists(){
        final Users userForDelete = new Users.Builder().withPassword("password").withRoles(Arrays.asList("roles")).withUserName("userdelete").build();
        databaseChecker.write("users",userForDelete);
        userDao.delete("userdelete");
        assertFalse(databaseChecker.readOne("users","userdelete").isPresent());
    }





}
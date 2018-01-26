package es.angel.pruebaInfojobs.infrastructure;

import es.angel.pruebaInfojobs.model.KeyModel;
import es.angel.pruebaInfojobs.model.Users;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class InMemoryDatabaseTest {


    @Test
    public void when_call_several_times_database_without_changes_should_be_the_same_object() {
        final InMemoryDatabase inMemoryDatabase = InMemoryDatabase.getInstance();
        final InMemoryDatabase inMemoryDatabase1 = InMemoryDatabase.getInstance();
        assertEquals(inMemoryDatabase, inMemoryDatabase1);
    }

    @Test
    public void when_call_several_times_database_with_changes_should_be_the_same_object() {
        final InMemoryDatabase inMemoryDatabase = InMemoryDatabase.getInstance();
        final Users newUser = new Users.Builder().withPassword("pass2").withRoles(Arrays.asList("role1", "role2")).withUserName("username").build();
        inMemoryDatabase.write("users", newUser);
        final InMemoryDatabase inMemoryDatabase1 = InMemoryDatabase.getInstance();
        assertEquals(inMemoryDatabase, inMemoryDatabase1);
    }

    @Test
    public void when_write_new_data_in_the_database_should_be_present_in_read() {
        final InMemoryDatabase inMemoryDatabase = InMemoryDatabase.getInstance();
        final List<Users> expected = InMemoryDatabase.USER_INITIAL_DATA.stream().map(Users.class::cast).collect(toList());
        final Users newUser = new Users.Builder().withPassword("pass2").withRoles(Arrays.asList("role1", "role2")).withUserName("username").build();
        inMemoryDatabase.write("users", newUser);
        expected.add(newUser);
        assertThat(inMemoryDatabase.read("users"), is(expected));
    }

    @Test
    public void when_read_first_time_the_database_instance_should_contains_only_the_inital_data() {
        final InMemoryDatabase inMemoryDatabase = InMemoryDatabase.getInstance();
        assertThat(inMemoryDatabase.read("users"), is(InMemoryDatabase.USER_INITIAL_DATA));
    }

    @Test
    public void when_read_one_data_and_exists_optional_should_be_present() {
        final InMemoryDatabase inMemoryDatabase = InMemoryDatabase.getInstance();
        Optional<KeyModel> userReaded = inMemoryDatabase.readOne("users", "user1");
        assertThat(userReaded.isPresent(), is(true));
    }

    @Test
    public void when_read_one_data_not_and_exists_optional_should_not_be_present() {
        final InMemoryDatabase inMemoryDatabase = InMemoryDatabase.getInstance();
        Optional<KeyModel> userReaded = inMemoryDatabase.readOne("users", "user145454");
        assertThat(userReaded.isPresent(), is(false));
    }

    @Test
    public void when_erase_data_should_not_exists(){
        final InMemoryDatabase inMemoryDatabase = InMemoryDatabase.getInstance();
        inMemoryDatabase.erase("users","user2");
        Optional<KeyModel> userDeleted = inMemoryDatabase.readOne("users", "user2");
        assertThat(userDeleted.isPresent(), is(false));
    }
}
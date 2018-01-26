package es.angel.pruebaInfojobs.dao;

import es.angel.pruebaInfojobs.infrastructure.InMemoryDatabase;
import es.angel.pruebaInfojobs.model.Access;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class AccessDaoTest {

    private AccessDao accessDao = new AccessDao();
    InMemoryDatabase databaseChecker = InMemoryDatabase.getInstance();

    @Test(expected = UnsupportedOperationException.class)
    public void when_store_a_new_access_should_throw_unsupportedoperation() {
        accessDao.save(new Access());
    }

    @Test
    public void when_read_all_access_should_return_all_access() {
        final List<Access> accesses = accessDao.readAll();
        assertThat(accesses, is(databaseChecker.read("access")));
    }

    @Test
    public void when_read_one_access_should_be_returned() {
        final Access readOne = accessDao.findOne("ADMIN").get();
        assertThat(readOne, notNullValue());
    }

    @Test
    public void when_read_one_and_not_exists_should_be_not_present() {
        assertFalse(accessDao.findOne("ROLE12").isPresent());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void when_delete_one_access_should_throw_unsupported_operation() {
        accessDao.delete("ROLE");
    }
}
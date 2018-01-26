package es.angel.pruebaInfojobs.dao;

import es.angel.pruebaInfojobs.infrastructure.InMemoryDatabase;
import es.angel.pruebaInfojobs.model.Access;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class AccessDao implements BaseDao<Access, String> {

    private static final String ACCESS_TABLE = "access";
    private InMemoryDatabase inMemoryDatabase;

    public AccessDao() {
        inMemoryDatabase = InMemoryDatabase.getInstance();
    }

    @Override
    public void save(Access model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Access> readAll() {
        return inMemoryDatabase.read(ACCESS_TABLE)
                .stream()
                .map(Access.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Access> findOne(String key) {
        return inMemoryDatabase.readOne(ACCESS_TABLE, key)
                .map(Access.class::cast);
    }

    @Override
    public void delete(String key) {
        throw new UnsupportedOperationException();
    }
}

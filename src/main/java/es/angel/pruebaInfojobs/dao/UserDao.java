package es.angel.pruebaInfojobs.dao;

import es.angel.pruebaInfojobs.infrastructure.InMemoryDatabase;
import es.angel.pruebaInfojobs.model.Users;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserDao implements BaseDao<Users, String> {

    private static final String USERS_TABLE = "users";
    private final InMemoryDatabase inMemoryDatabase;

    public UserDao() {
        inMemoryDatabase = InMemoryDatabase.getInstance();
    }

    @Override
    public void save(Users model) {
        inMemoryDatabase.write(USERS_TABLE, model);
    }

    @Override
    public List<Users> readAll() {
        return inMemoryDatabase.read(USERS_TABLE)
                .stream()
                .map(Users.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Users> findOne(String key) {
        return inMemoryDatabase.readOne(USERS_TABLE, key)
                .map(Users.class::cast);
    }

    @Override
    public void delete(String key) {
        inMemoryDatabase.erase(USERS_TABLE, key);

    }
}

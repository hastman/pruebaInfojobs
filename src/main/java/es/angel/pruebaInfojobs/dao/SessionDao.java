package es.angel.pruebaInfojobs.dao;

import es.angel.pruebaInfojobs.infrastructure.InMemoryDatabase;
import es.angel.pruebaInfojobs.model.Session;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SessionDao implements BaseDao<Session, String> {

    public static final String SESSIONS_TABLE = "sessions";
    private InMemoryDatabase inMemoryDatabase;

    public SessionDao() {
        inMemoryDatabase = InMemoryDatabase.getInstance();
    }

    @Override
    public void save(Session model) {
        inMemoryDatabase.write(SESSIONS_TABLE, model);
    }

    @Override
    public List<Session> readAll() {
        return inMemoryDatabase.read(SESSIONS_TABLE).stream()
                .map(Session.class::cast)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Session> findOne(String key) {
        return inMemoryDatabase.readOne(SESSIONS_TABLE, key)
                .map(Session.class::cast);
    }

    @Override
    public void delete(String key) {
        inMemoryDatabase.erase(SESSIONS_TABLE, key);
    }
}

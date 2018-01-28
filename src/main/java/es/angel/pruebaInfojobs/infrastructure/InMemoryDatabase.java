package es.angel.pruebaInfojobs.infrastructure;

import es.angel.pruebaInfojobs.model.Access;
import es.angel.pruebaInfojobs.model.Authorization;
import es.angel.pruebaInfojobs.model.KeyModel;
import es.angel.pruebaInfojobs.model.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * In memory storage with initial users
 * Singleton pattern, with this solution is possible share the database across the entire application
 */
public class InMemoryDatabase {


    private final Map<String, List<KeyModel>> storage = new ConcurrentHashMap<>();

    static final List<KeyModel> USER_INITIAL_DATA = new ArrayList<>(asList(
            new Users(singletonList("ADMIN"), "admin", "admin"),
            new Users(singletonList("PAGE_1"), "user1", "pwd1"),
            new Users(singletonList("PAGE_2"), "user2", "pwd1"),
            new Users(singletonList("PAGE_3"), "user3", "pwd1")));

    private static final List<KeyModel> ACCESS_INITIAL_DATA = new ArrayList<>(asList(
            new Access(singletonList(new Authorization("*", singletonList("*"))), "ADMIN"),
            new Access(asList(new Authorization("main", singletonList("get")), new Authorization("page1", singletonList("get")), new Authorization("users", singletonList("get"))), "PAGE_1"),
            new Access(asList(new Authorization("main", singletonList("get")), new Authorization("page2", singletonList("get")), new Authorization("users", singletonList("get"))), "PAGE_2"),
            new Access(asList(new Authorization("main", singletonList("get")), new Authorization("page3", singletonList("get")), new Authorization("users", singletonList("get"))), "PAGE_3")));

    private static final InMemoryDatabase INSTANCE = new InMemoryDatabase();

    public static InMemoryDatabase getInstance() {
        return INSTANCE;
    }

    private InMemoryDatabase() {
        this.storage.put("users", USER_INITIAL_DATA);
        this.storage.put("access", ACCESS_INITIAL_DATA);
    }


    public void write(String table, KeyModel objectToWrite) {
        if (!this.storage.containsKey(table)) {
            this.storage.put(table, new ArrayList<>());
        }
        this.storage.getOrDefault(table, new ArrayList<>()).add(objectToWrite);
    }


    public List<KeyModel> read(String table) {
        return this.storage.getOrDefault(table, new ArrayList<>());
    }

    public Optional<KeyModel> readOne(final String table, final String searchIndex) {
        return this.storage.getOrDefault(table, new ArrayList<>()).stream()
                .filter(contetn -> contetn.getKey().equals(searchIndex))
                .findFirst();
    }

    public void erase(final String table, String searchIndex) {
        this.storage.getOrDefault(table, new ArrayList<>())
                .removeIf(keyModel -> keyModel.getKey().equals(searchIndex));
    }
}
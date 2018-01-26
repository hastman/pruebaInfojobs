package es.angel.pruebaInfojobs.dao;

import java.util.List;
import java.util.Optional;

/**
 * Base interface for dao pattern
 * T -> Model , K -> Type of Key
 */
public interface BaseDao<T, K> {

    void save(T model);

    List<T> readAll();

    Optional<T> findOne(K key);

    void delete(K key);


}

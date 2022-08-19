package ru.vitstark.library.dao;

import java.util.Optional;

public interface CRUD<ID,T> {
    void save(T entity);
    Optional<T> findByID(ID id);
    void update(T entity);
    void delete(ID id);
}

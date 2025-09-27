package com.pedroluizforlan.pontodoc.service;

import java.util.List;

public interface Crud<ID, T>  {
    List<T> findAll();

    T findById(ID id);

    T create(T entity);

    T update(ID id, T entity);

    T delete(ID id);
}

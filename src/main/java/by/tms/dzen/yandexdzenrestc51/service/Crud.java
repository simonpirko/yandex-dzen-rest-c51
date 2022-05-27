package by.tms.dzen.yandexdzenrestc51.service;

import java.util.List;

public interface Crud<T> {
    T save(T t);

    void delete(T t);

    void delete(Long id);

    T update(T t);

    T findById(Long id);
}

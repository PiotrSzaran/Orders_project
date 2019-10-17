package pl.szaran.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    Optional<T> saveOrUpdate(T t);

    T delete(Long id);

    void deleteAll();

    Optional<T> findById(Long id);

    //List<T> findAll(T t);
    List<T> findAll();
}

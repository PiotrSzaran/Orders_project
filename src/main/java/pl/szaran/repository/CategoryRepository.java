package pl.szaran.repository;

import pl.szaran.model.Category;

import java.util.Optional;

public interface CategoryRepository extends GenericRepository<Category>{
    Optional<Category> findByName (String name);
}
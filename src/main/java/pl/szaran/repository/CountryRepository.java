package pl.szaran.repository;

import pl.szaran.model.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends GenericRepository<Country> {
    Optional<Country> findByName(String countryName);
    List<Country> findAll();
}

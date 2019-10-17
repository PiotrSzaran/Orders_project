package pl.szaran.repository;

import java.util.Optional;

//przemyśleć czy warto robić repo dla klasy Error
public interface ErrorRepository extends GenericRepository<Error> {
    Optional<Error> findByName(String name);
    //void insertErrorIntoTable(String message);
}
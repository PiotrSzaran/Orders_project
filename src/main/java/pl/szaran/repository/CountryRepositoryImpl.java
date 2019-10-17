package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Country;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryRepositoryImpl extends AbstractGenericRepository<Country> implements CountryRepository {
    @Override
    public Optional<Country> findByName(String name) {

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Country> item = Optional.empty();

        try {
            if (name == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "COUNTRY FIND BY NAME - NAME IS NULL");
            }

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Country> elements = entityManager
                    .createQuery("select c from Country c where c.name = :name", Country.class)
                    .setParameter("name", name)
                    .getResultList();
            item = elements.size() == 0 ? Optional.empty() : Optional.of(elements.get(0));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();;
            }
            e.printStackTrace();
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return item;
    }

    @Override
    public List<Country> findAll() {
        List<Country> countriesFromDB = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            countriesFromDB = entityManager.createQuery("select c from Country c", Country.class).getResultList();
            //countriesFromDB = entityManager.createQuery("select c, c.shops, c.customers, c.producers from Country c join Shop shop join Customer customer join Producer producer", Country.class).getResultList();
            tx.commit();
        } catch (Exception e){
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return countriesFromDB;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Country c").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}

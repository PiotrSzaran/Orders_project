package pl.szaran.repository;

import pl.szaran.model.Error;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ErrorRepositoryImpl extends AbstractGenericRepository<Error> implements ErrorRepository {
    @Override
    public Optional<Error> findByName(String name) {

        return Optional.empty();
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Error e").executeUpdate();
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

    @Override
    public List<Error> findAll() {
        List<Error> errorsFromDB = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx= entityManager.getTransaction();
            tx.begin();

            errorsFromDB = entityManager.createQuery("select e from Error e ORDER BY e.date", Error.class).getResultList();
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
        return errorsFromDB;
    }
}
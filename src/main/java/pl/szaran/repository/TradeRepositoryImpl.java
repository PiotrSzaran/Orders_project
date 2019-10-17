package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Trade;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TradeRepositoryImpl extends AbstractGenericRepository<Trade> implements TradeRepository {
    @Override
    public Optional<Trade> findByName(String name) {

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Trade> item = Optional.empty();

        try {
            if (name == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "TRADE FIND BY NAME - NAME IS NULL");
            }
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            ;
            List<Trade> elements = entityManager
                    .createQuery("select c from Trade c where c.name = :name", Trade.class)
                    .setParameter("name", name)
                    .getResultList();
            item = elements.size() == 0 ? Optional.empty() : Optional.of(elements.get(0));
            tx.commit();

        } catch (Exception e) {
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

        return item;
    }

    @Override
    public List<Trade> findAll() {
        List<Trade> tradesFromDB = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            tradesFromDB = entityManager.createQuery("select t from Trade t", Trade.class).getResultList();
            tx.commit();
        } catch (Exception e) {
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
        return tradesFromDB;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Trade t").executeUpdate();
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

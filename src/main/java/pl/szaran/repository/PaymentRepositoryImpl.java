package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Payment;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentRepositoryImpl extends AbstractGenericRepository<Payment> implements PaymentRepository {
    @Override
    public Optional<Payment> findByName(String name) {

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Payment> item = Optional.empty();

        try {
            if (name == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "PAYMENT FIND BY NAME - NAME IS NULL");
            }
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            List<Payment> elements = entityManager
                    .createQuery("select p from Payment p where p.payment = :name", Payment.class)
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
    public List<Payment> findAll() {
        List<Payment> paymentsFromDB = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            paymentsFromDB = entityManager.createQuery("select p from Payment p", Payment.class).getResultList();
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

        return paymentsFromDB;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Payment p").executeUpdate();
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
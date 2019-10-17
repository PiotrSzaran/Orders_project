package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public class AbstractGenericRepository<T> implements GenericRepository<T> {

    EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getEntityManagerFactory();

    @SuppressWarnings("unchecked")
    private Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public Optional<T> saveOrUpdate(T t) {

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<T> item = Optional.empty();
        try {
            if (t == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "SAVE OR UPDATE - OBJECT IS NULL");
            }

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            //noinspection unchecked
            item = Optional.of((T) entityManager.merge(t));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace(); // tylko na potrzeby testowania kodu
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }

    @Override
    public T delete(Long id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<T> findById(Long id) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<T> item = Optional.empty();
        try {
            if (id == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "FIND BY ID - ID IS NULL");
            }

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = Optional.ofNullable(entityManager.find(type, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace(); // tylko na potrzeby testowania kodu
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

}
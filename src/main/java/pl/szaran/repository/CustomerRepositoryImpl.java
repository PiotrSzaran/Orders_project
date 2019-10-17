package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl extends AbstractGenericRepository<Customer> implements CustomerRepository {
    private EntityManager entityManager = null;
    private EntityTransaction tx = null;

    @Override
    public Optional<Customer> findByName(String name) {

        Optional<Customer> item = Optional.empty();

        try {
            if (name == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "CUSTOMER FIND BY NAME - NAME IS NULL");
            }

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = Optional.ofNullable(entityManager
                    .createQuery("select c from Customer c where c.name = :name", Customer.class)
                    .setParameter("name", name)
                    .getSingleResult());

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
    public List<Customer> findAll() {

        List<Customer> customersFromDB = new ArrayList<>();

        try {

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            customersFromDB = entityManager.createQuery("select c from Customer c ORDER BY c.surname", Customer.class).getResultList();


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

        return customersFromDB;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Customer c").executeUpdate();
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

    public boolean isCustomerWithNameSurnameCountry(String name, String surname, int age, String countryName) {
        int size = 0;

        try {

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            size = entityManager.createQuery("select c from Customer c join c.country ctr where ctr.name=:countryName and c.name=:name and c.surname=:surname and c.age=:age", Customer.class)
                    .setParameter("countryName", countryName)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
                    .setParameter("age", age)
                    .getResultList().size();

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
        return size > 0;
    }
}
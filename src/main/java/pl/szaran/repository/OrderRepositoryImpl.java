package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Order;
import pl.szaran.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl extends AbstractGenericRepository<Order> implements OrderRepository {
    //private final ErrorRepository errorRepository = new ErrorRepositoryImpl();
    private EntityManager entityManager = null;
    private EntityTransaction tx = null;

    @Override
    public List<Order> findAll() {
        List<Order> ordersFromDB = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            ordersFromDB = entityManager.createQuery("select  o from Order o", Order.class)
                    .getResultList();

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
        return ordersFromDB;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Order o").executeUpdate();
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
    public boolean hasElementWithDiscount(Double orderDiscount) {
        return false;
    }

    @Override
    public List<Product> findProductsByCustomerCountryAndAge(String countryName, int youngest, int oldest) {
        if (countryName == null || countryName.isEmpty() || youngest > oldest) {
            //errorRepository.insertErrorIntoTable("ORDERS;" + "ORDER, method findProductsByCustomerCountryAndAge has incorrect arguments");
            throw new MyException(ExceptionCode.REPOSITORY, "ORDER, method findProductsByCustomerCountryAndAge has incorrect arguments");
        }
        List<Product> products = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            products = entityManager.createQuery("select o.product from Order o where o.customer.country.name = :countryName and o.customer.age >= :youngest and o.customer.age <= :oldest ORDER BY o.product.price desc", Product.class)
                    .setParameter("countryName", countryName)
                    .setParameter("youngest", youngest)
                    .setParameter("oldest", oldest)
                    .getResultList();

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
        return products;
    }
}

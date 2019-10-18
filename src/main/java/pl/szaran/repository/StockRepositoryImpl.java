package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Stock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

public class StockRepositoryImpl extends AbstractGenericRepository<Stock> implements StockRepository {

    @Override
    public List<Stock> findAll() {
        List<Stock> stockFromDB = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            stockFromDB = entityManager.createQuery("select s from Stock s", Stock.class).getResultList();
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
        return stockFromDB;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Stock s").executeUpdate();
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
    public boolean hasElementWithQuantity(Integer quantityValue) {
        return false;
    }

    @Override
    public int findByProductAndShop(String productName, String shopName, String countryName) {

        List<Stock> stockFromDB = new ArrayList<>();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            stockFromDB = entityManager.createQuery("select p.name, s.name from Stock stock join stock.product p join stock.shop s where p.name=:productName and s.name=:shopName and s.country.name=:countryName")
                    .setParameter("productName", productName)
                    .setParameter("shopName", shopName)
                    .setParameter("countryName", countryName)
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
        return stockFromDB.size();

    }

    @Override
    public Long getIDByProductAndShop(String productName, String shopName, String countryName) {

        List<Long> stockFromDB = new ArrayList<>();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            stockFromDB = entityManager.createQuery("select stock.id from Stock stock join stock.product p join stock.shop s where p.name=:productName and s.name=:shopName and s.country.name=:countryName")
                    .setParameter("productName", productName)
                    .setParameter("shopName", shopName)
                    .setParameter("countryName", countryName)
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
        return stockFromDB.get(0);

    }

    @Override
    public Integer getStockQuantity(String productName, String shopName) {
        List<Integer> list = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            list = entityManager.createQuery("select stock.quantity from Stock stock join stock.product p join stock.shop s where p.name=:productName and s.name=:shopName")
                    .setParameter("productName", productName)
                    .setParameter("shopName", shopName)
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

        return list.get(0);
    }

    @Override
    public Integer getStockQuantity(Long id) {
        List<Integer> list = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            list = entityManager.createQuery("select stock.quantity from Stock stock join stock.product p where p.id=:id")
                    .setParameter("id", id)
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

        return list.get(0);
    }

    @Override
    public void updateQuantity(Long id, Integer quantity) {
        if (quantity == null) {
            throw new MyException(ExceptionCode.SERVICE, "METHOD updateQuantity: ARGUMENT OF METHOD IS NULL");
        }
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            Stock stock =
                    (Stock)entityManager.find(Stock.class, id);
            stock.setQuantity(quantity);
            entityManager.persist(stock);
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
    }

    @Override
    public List <Stock> getStockByProduct(String productName) {
        List<Stock> list = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            list = entityManager.createQuery("select s from Stock s where s.product.name=:productName", Stock.class)
                    .setParameter("productName", productName)

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

        return list;
    }
}

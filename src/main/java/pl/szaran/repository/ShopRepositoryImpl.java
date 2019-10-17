package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Shop;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopRepositoryImpl extends AbstractGenericRepository<Shop> implements ShopRepository {
    @Override
    public Optional<Shop> findByName(String name) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Shop> item = Optional.empty();
        try {
            if (name == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "SHOP FIND BY NAME - NAME IS NULL");
            }
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Shop> elements = entityManager
                    .createQuery("select c from Shop c where c.name = :name", Shop.class)
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
    public List<Shop> findAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        List<Shop> shopsFromDB = new ArrayList<>();
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            shopsFromDB = entityManager.createQuery("select s from Shop s", Shop.class).getResultList();
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

        return shopsFromDB;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Shop s").executeUpdate();
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
    public boolean isShopWithNameCountry(String name, String countryName) {

        int size = 0;

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            size = entityManager.createQuery("select c.name, ctr.name from Shop c join c.country ctr where ctr.name=:countryName and c.name=:name")
                    .setParameter("countryName", countryName)
                    .setParameter("name", name)
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

    @Override
    public Optional<Shop> findByNameCountry(String name, String countryName) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Shop> item = Optional.empty();
        try {
            if (name == null || countryName == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "SHOP FIND BY NAME OR COUNTRY NAME  - NAME  OR COUNTRY NAME IS NULL");
            }
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Shop> elements = entityManager
                    .createQuery("select c from Shop c join c.country ctr where ctr.name=:countryName and c.name=:name", Shop.class)
                    .setParameter("name", name)
                    .setParameter("countryName", countryName)
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
}

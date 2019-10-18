package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Producer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerRepositoryImpl extends AbstractGenericRepository<Producer> implements ProducerRepository {
    @Override
    public Optional<Producer> findByName(String name) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Producer> item = Optional.empty();

        try {
            if (name == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "PRODUCER FIND BY NAME - NAME IS NULL");
            }

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Producer> elements = entityManager
                    .createQuery("select c from Producer c where c.name = :name", Producer.class)
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
    public List<Producer> findAll() {

        List<Producer> producersFromDB = new ArrayList<>();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {


            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            producersFromDB = entityManager.createQuery("select p from Producer p", Producer.class).getResultList();

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

        return producersFromDB;
    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Producer p").executeUpdate();
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
    public boolean isProducerWithNameCountryTrade(String name, String countryName, String tradeName) {
        int size = 0;

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            size = entityManager.createQuery("select p.name, ctr.name, tr.name from Producer p join p.country ctr join p.trade tr where ctr.name=:countryName and p.name=:name and tr.name=:tradeName")
                    .setParameter("countryName", countryName)
                    .setParameter("name", name)
                    .setParameter("tradeName", tradeName)
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
    public Optional<Producer> findByNameCountryTrade(String name, String countryName, String tradeName) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Producer> item = Optional.empty();

        try {
            if (name == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "PRODUCER FIND BY NAME - NAME IS NULL");
            }

            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Producer> elements = entityManager
                    .createQuery("select p from Producer p join p.country ctr join p.trade tr where ctr.name=:countryName and p.name=:name and tr.name=:tradeName")
                    .setParameter("countryName", countryName)
                    .setParameter("name", name)
                    .setParameter("tradeName", tradeName)
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

    public List<Producer> getProducerFromStock() {
        List<Producer> list = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            list = entityManager.createQuery("select s.product.producer from Stock s", Producer.class).getResultList();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return list;
    }
}

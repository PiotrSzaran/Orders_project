package pl.szaran.repository;

import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Category;
import pl.szaran.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl extends AbstractGenericRepository<Product> implements ProductRepository {
    @Override
    public Optional<Product> findByName(String name) {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Product> item = Optional.empty();

        try {
            if (name == null) {
                throw new MyException(ExceptionCode.REPOSITORY, "PRODUCT FIND BY NAME - NAME IS NULL");
            }
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            List<Product> elements = entityManager
                    .createQuery("select c from Product c join fetch c.guaranteeComponents where c.name = :name", Product.class)
                    .setParameter("name", name)
                    .getResultList();
            item = elements.size() == 0 ? Optional.empty() : Optional.of(elements.get(0));
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
                ;
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
    public List<Product> findAll() {
        List<Product> productsFromDB = new ArrayList<>();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            productsFromDB = entityManager.createQuery("select distinct p from Product p inner join fetch p.guaranteeComponents", Product.class).getResultList();

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
        return productsFromDB;

    }

    @Override
    public void deleteAll() {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.createQuery("delete from Product p").executeUpdate();
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

    public boolean isProductWithProducerCategory(String name, String producerName, String categoryName) {
        int size = 0;

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            size = entityManager.createQuery("select a.name, p.name, cat.name from Product a join a.producer p join a.category cat where p.name=:producerName and a.name=:name and cat.name=:categoryName")
                    .setParameter("categoryName", categoryName)
                    .setParameter("producerName", producerName)
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

    public List<Product> findByCategory(String name) {
        List<Product> productsFromDB = new ArrayList<>();

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            productsFromDB = entityManager.createQuery("select distinct c from Product c inner join fetch c.guaranteeComponents where c.category.name = :name", Product.class)
                    .setParameter("name", name)
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
        return productsFromDB;
    }

    @Override
    public List<Product> findBiggestPriceByCategories() {
        final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
        List<Category> list = new ArrayList<>(categoryRepository.findAll()); // pobieram listę kategorii
        List<Product> productsFromDB = new ArrayList<>();
        String name = new String();
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        for (Category c : list
        ) {
            name = c.getName();
            try {
                Product product = new Product();
                List<Product> productList = new ArrayList<>();

                entityManager = entityManagerFactory.createEntityManager();
                tx = entityManager.getTransaction();
                tx.begin();
                productList = (entityManager.createQuery("select c from Product c where c.category.name = :name order by price desc", Product.class)
                        .setParameter("name", name))
                        .getResultList();
                if (productList.isEmpty()) {
                    product = null;
                } else {

                    product = (entityManager.createQuery("select c from Product c where c.category.name = :name order by price desc", Product.class)
                            .setParameter("name", name)
                            .setMaxResults(1)
                            .getSingleResult());
                }
                tx.commit();
                productsFromDB.add(product);
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
        return productsFromDB;
    }

    public List<Product> getProductsByClientData(String name, String surname, String countryName) {
        List<Product> productList = new ArrayList<>();
        EntityManager entityManager = null;
        EntityTransaction tx = null;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            productList = entityManager.createQuery("select o.product from Order o where o.customer.name =:name and o.customer.surname =:surname and o.product.producer.country.name =:countryName order by o.product.producer.name asc ", Product.class)
                    .setParameter("name", name)
                    .setParameter("surname", surname)
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
        return productList;
    }
}


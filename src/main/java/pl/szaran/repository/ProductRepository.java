package pl.szaran.repository;

import pl.szaran.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends GenericRepository<Product> {
    Optional<Product> findByName(String productName);
    boolean isProductWithProducerCategory(String name, String producerName, String categoryName);
    List<Product> findByCategory(String name);
    List<Product> findBiggestPriceByCategories();
    List<Product> getProductsByClientData(String name, String surname, String countryName);
}


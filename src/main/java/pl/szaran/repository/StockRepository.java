package pl.szaran.repository;

import pl.szaran.model.Stock;

import java.util.List;

public interface StockRepository extends GenericRepository<Stock> {
    boolean hasElementWithQuantity (Integer quantityValue);
    int findByProductAndShop(String productName, String shopName, String countryName);
    Long getIDByProductAndShop(String productName, String shopName, String countryName);
    Integer getStockQuantity(String productName, String shopName);
    Integer getStockQuantity(Long id);
    void updateQuantity(Long id, Integer quantity);
    List<Stock> getStockByProduct(String productName);
}
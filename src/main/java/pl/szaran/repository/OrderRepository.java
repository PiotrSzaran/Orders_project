package pl.szaran.repository;

import pl.szaran.model.Order;
import pl.szaran.model.Product;

import java.util.List;

public interface OrderRepository extends GenericRepository<Order> {
    boolean hasElementWithDiscount (Double orderDiscount);
    List<Product> findProductsByCustomerCountryAndAge(String countryName, int youngest, int oldest);
}

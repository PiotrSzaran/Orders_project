package pl.szaran.repository;

import pl.szaran.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer> {
    Optional<Customer> findByName(String customerName);

    boolean isCustomerWithNameSurnameCountry(String name, String surname, int age, String countryName);
}

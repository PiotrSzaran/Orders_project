package pl.szaran.validators;

import pl.szaran.dto.CustomerDTO;

import java.util.HashMap;
import java.util.Map;

public class CustomerValidator {

    public Map<String, String> validateCustomerData(CustomerDTO customerDTO) {

        Map<String, String> errors = new HashMap<>();

        if (customerDTO == null) {
            errors.put("customer", "customer object is null");
            return errors;
        }

        if (customerDTO.getName() == null) {
            errors.put("customer", "customer name is null");
            return errors;
        }

        if (customerDTO.getSurname() == null) {
            errors.put("customer", "customer surname is null");
            return errors;
        }

        if (!customerDTO.getName().matches("[A-Z\\s]+")) {
            errors.put("customer", "name is incorrect: " + customerDTO.getName());
            return errors;
        }

        if (!customerDTO.getSurname().matches("[A-Z\\s]+")) {
            errors.put("customer", "surname is incorrect: " + customerDTO.getSurname());
        }

        if (customerDTO.getAge() == null) {
            errors.put("customer", "customer age is null");
            return errors;
        }

        if (customerDTO.getAge() < 18) {
            errors.put("customer", "age is incorrect: " + customerDTO.getAge());
            return errors;
        }

        //WALIDACJA COUNTRY
        errors = new CountryValidator().validateCountryData(customerDTO.getCountryDTO());
        if (!errors.isEmpty()) {
            return errors;
        }

        return errors;
    }


}
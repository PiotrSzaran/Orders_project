package pl.szaran.service;

import pl.szaran.dto.CountryDTO;
import pl.szaran.dto.CustomerDTO;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Country;
import pl.szaran.model.Customer;
import pl.szaran.repository.*;
import pl.szaran.validators.CustomerValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerService {
    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private final CountryRepository countryRepository = new CountryRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private String TABLE = "CUSTOMER;";

    public void addCustomer(CustomerDTO customerDTO) {
        CustomerValidator customerValidator = new CustomerValidator();
        //------WALIDACJA------
        var validate = customerValidator.validateCustomerData(customerDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Customer customer = null;

            if (customerDTO.getId() != null) {
                customer = customerRepository.findById(customerDTO.getId()).orElse(null);
            }

            if (customer == null) {
                Country country = null;

                if (customerDTO.getCountryDTO().getId() != null) {
                    country = countryRepository.findById(customerDTO.getCountryDTO().getId()).orElse(null);
                }

                if (country == null && customerDTO.getCountryDTO().getName() != null) {
                    country = countryRepository.findByName(customerDTO.getCountryDTO().getName()).orElse(null);
                }

                if (country == null) {
                    country = countryRepository
                            .saveOrUpdate(Country.builder().name(customerDTO.getCountryDTO().getName()).build())
                            .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW COUNTRY"));
                }


                if (customerRepository.isCustomerWithNameSurnameCountry(customerDTO.getName(), customerDTO.getSurname(), customerDTO.getAge(), customerDTO.getCountryDTO().getName())) {
                    String errorMessage = "CUSTOMER " + customerDTO.getName()
                            + " " + customerDTO.getSurname()
                            + ", " + customerDTO.getAge() + " YO "
                            + "FROM " + customerDTO.getCountryDTO().getName()
                            + " ALREADY ADDED";
                    System.out.println(errorMessage);
                    errorService.addError(TABLE+errorMessage);
                } else {

                    customer = ModelMapper.fromCustomerDTOToCustomer(customerDTO);
                    customer.setCountry(country);
                    customerRepository.saveOrUpdate(customer);
                }
            }
        }
    }

    public List<CustomerDTO> getCustomers(){
        return customerRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCustomerToCustomerDTO)
                .map(customerDTO -> CustomerDTO.builder()
                        .name(customerDTO.getName())
                        .surname(customerDTO.getSurname())
                        .countryDTO(CountryDTO.builder()
                                .name(customerDTO.getCountryDTO().getName())
                                .build())
                        .age(customerDTO.getAge()).build())
                .collect(Collectors.toList());
    }

    public Map<Integer, Customer> getMapOfCustomers() {
        List<Customer> list = new ArrayList<>(customerRepository.findAll());
        int i = 1;
        Map<Integer, Customer> customersMap = new HashMap<>();
        for (Customer c : list) {
            customersMap.put(i, c);
            i++;
        }
        return customersMap;
    }

    public Map<Integer, Customer> showCustomers() {
        Map<Integer, Customer> map = getMapOfCustomers();
        for (Map.Entry<Integer, Customer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName() + " "
                    + entry.getValue().getSurname() + ", lat "
                    + entry.getValue().getAge() + ", " + entry.getValue().getCountry().getName());
        }
        return map;
    }

    public void deleteCustomer() {
        customerRepository.deleteAll();
    }

    //todo dorobić metodę
    public void showCustomersWithCommonCountryProducts() {}


}
package pl.szaran.validators;

import pl.szaran.dto.OrderDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class OrderValidator {

    public Map<String, String> validateOrderData(OrderDTO orderDTO) {

        Map<String, String> errors = new HashMap<>();
        if (orderDTO == null) {
            errors.put("order", "order object is null");
            return errors;
        }
        if (orderDTO.getQuantity() == null) {
            errors.put("order", "quantity value is null");
            return errors;
        }
        if (orderDTO.getQuantity().compareTo(0) <= 0) {
            errors.put("order", "quantity is incorrect: " + orderDTO.getQuantity());
            return errors;
        }

        if (orderDTO.getDate() == null) {
            errors.put("order", "date is null");
            return errors;
        }
        if (orderDTO.getDate().isBefore(LocalDate.now())) {
            errors.put("order", "date is from the past: " + orderDTO.getDate());
            return errors;
        }
        if (orderDTO.getDiscount() == null) {
            errors.put("order", "discount is null");
            return errors;
        }
        if (orderDTO.getDiscount().compareTo(new BigDecimal(0)) < 0 && orderDTO.getDiscount().compareTo(new BigDecimal(1)) > 0) {
            errors.put("order", "discount value is incorrect: " + orderDTO.getDiscount());
            return errors;
        }

        //WALIDACJA PRODUCT
        errors = new ProductValidator().validateProductData(orderDTO.getProductDTO());
        if (!errors.isEmpty()) {
            return errors;
        }
        //WALIDACJA PAYMENT
        errors = new PaymentValidator().validatePaymentData(orderDTO.getPaymentDTO());
        if (!errors.isEmpty()) {
            return errors;
        }
        //WALIDACJA CUSTOMER
        errors = new CustomerValidator().validateCustomerData(orderDTO.getCustomerDTO());
        if (!errors.isEmpty()) {
            return errors;
        }

        return errors;
    }
}
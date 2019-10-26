package pl.szaran.validators;

import pl.szaran.dto.ProductDTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProductValidator {

    public Map<String, String> validateProductData(ProductDTO productDTO) {

        Map<String, String> errors = new HashMap<>();

        if (productDTO == null) {
            errors.put("product", "product object is null");
            return errors;

        }
        if (productDTO.getName() == null) {
            errors.put("product", "product name is null");
            return errors;

        }
        if (!productDTO.getName().matches("[A-Z\\s]+")) {
            errors.put("product", "name is incorrect: " + productDTO.getName());
            return errors;

        }
        if (productDTO.getPrice() == null) {
            errors.put("product", "price is null");
            return errors;
        }
        if ((productDTO.getPrice().compareTo(BigDecimal.ZERO)) < 0) {
            errors.put("product", "price is lower than 0: " + productDTO.getPrice());
            return errors;
        }

        //WALIDACJA PRODUCER
        errors = new ProducerValidator().validateProducerData(productDTO.getProducerDTO());
        if (!errors.isEmpty()) {
            return errors;
        }

        //WALIDACJA CATEGORY
        errors = new CategoryValidator().validateCategoryData(productDTO.getCategoryDTO());
        if (!errors.isEmpty()) {
            return errors;
        }

        return errors;
    }
}

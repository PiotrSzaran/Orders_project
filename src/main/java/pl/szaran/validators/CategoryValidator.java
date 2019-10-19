package pl.szaran.validators;

import pl.szaran.dto.CategoryDTO;

import java.util.HashMap;
import java.util.Map;

public class CategoryValidator {

    public Map<String, String> validateCategoryData(CategoryDTO categoryDTO) {

        Map<String, String> errors = new HashMap<>();

        if (categoryDTO == null) {
            errors.put("category", "category object is null");
            return errors;
        }

        if (categoryDTO.getName() == null) {
            errors.put("category", "category name is null");
            return errors;
        }

        if (!categoryDTO.getName().matches("[A-Z\\s]+")) {
            errors.put("category", "name is incorrect: " + categoryDTO.getName());
            return errors;
        }

        return errors;
    }
}

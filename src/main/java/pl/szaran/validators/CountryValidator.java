package pl.szaran.validators;

import pl.szaran.dto.CountryDTO;

import java.util.HashMap;
import java.util.Map;

public class CountryValidator{

    public Map<String, String> validateCountryData(CountryDTO countryDTO) {

        Map<String, String> errors = new HashMap<>();

        if (countryDTO == null) {
            errors.put("country", "country object is null");
            return errors;
        }

        if (countryDTO.getName() == null) {
            errors.put("country", "country name is null");
            return errors;
        }

        if (!countryDTO.getName().matches("[A-Z\\s]+")) {
            errors.put("country", "name is incorrect: " + countryDTO.getName());
            return errors;
        }

        return errors;
    }
}

package pl.szaran.validators;

import pl.szaran.dto.CountryDTO;
import pl.szaran.service.ErrorService;
import pl.szaran.service.ModelMapper;

import java.util.HashMap;
import java.util.Map;

public class CountryValidator implements ModelMapper {
    private final ErrorService errorService = new ErrorService();
    private final String TABLE = "COUNTRY;";

    public Map<String, String> validateCountryData(CountryDTO countryDTO) {

        Map<String, String> errors = new HashMap<>();

        if (countryDTO == null) {
            errors.put("country", "country object is null");
            errorService.addError(TABLE + errors.get("country"));
            return errors;
        }

        if (countryDTO.getName() == null) {
            errors.put("country", "country name is null");
            errorService.addError(TABLE + errors.get("country"));
            return errors;
        }

        if (!countryDTO.getName().matches("[A-Z\\s]+")) {
            errors.put("country", "name is incorrect: " + countryDTO.getName());
            errorService.addError(TABLE + errors.get("country"));
            return errors;
        }

        return errors;
    }
}

package pl.szaran.validators;

import pl.szaran.dto.ShopDTO;

import java.util.HashMap;
import java.util.Map;

public class ShopValidator {

    public Map<String, String> validateShopData(ShopDTO shopDTO) {

        Map<String, String> errors = new HashMap<>();

        if (shopDTO == null) {
            errors.put("shop", "shop object is null");
            return errors;
        }

        if (shopDTO.getName() == null) {
            errors.put("shop", "shop name is null");
            return errors;
        }

        if (!shopDTO.getName().matches("[A-Z\\s]+")){
            errors.put("shop", "name is incorrect: " + shopDTO.getName());
            return errors;
        }

        //WALIDACJA COUNTRY
        errors = new CountryValidator().validateCountryData(shopDTO.getCountryDTO());
        if (!errors.isEmpty()) {
            return errors;
        }
        return errors;
    }
}
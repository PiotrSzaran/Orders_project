package pl.szaran.validators;

import pl.szaran.dto.ProducerDTO;

import java.util.HashMap;
import java.util.Map;

public class ProducerValidator {

    public Map<String, String> validateProducerData(ProducerDTO producerDTO) {
        Map<String, String> errors = new HashMap<>();

        if (producerDTO == null) {
            errors.put("producer", "producer object is null");
            return errors;
        }

        if (producerDTO.getName() == null) {
            errors.put("producer", "name is null");
            return errors;
        }

        if (!producerDTO.getName().matches("[A-Z\\s]+")) {
            errors.put("producer", "name is incorrect: " + producerDTO.getName());
            return errors;
        }

        //WALIDACJA TRADE
        errors = new TradeValidator().validateTradeData(producerDTO.getTradeDTO());
        if (!errors.isEmpty()){
            return errors;
        }

        //WALIDACJA COUNTRY
        errors = new CountryValidator().validateCountryData(producerDTO.getCountryDTO());
        if (!errors.isEmpty()) {
            return errors;
        }

        return errors;
    }
}

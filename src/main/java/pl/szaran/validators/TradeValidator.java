package pl.szaran.validators;

import pl.szaran.dto.TradeDTO;

import java.util.HashMap;
import java.util.Map;

public class TradeValidator {

    public Map<String, String> validateTradeData(TradeDTO tradeDTO) {

        Map<String, String> errors = new HashMap<>();

        if (tradeDTO == null) {
            errors.put("trade", "trade object is null");
            return errors;
        }

        if (tradeDTO.getName() == null) {
            errors.put("trade", " trade name is null");
            return errors;
        }

        if (!tradeDTO.getName().matches("[A-Z\\s]+")) {
            errors.put("trade", "name is incorrect: " + tradeDTO.getName());
            return errors;
        }

        return errors;
    }
}

package pl.szaran.validators;

import pl.szaran.dto.StockDTO;

import java.util.HashMap;
import java.util.Map;

public class StockValidator {

    public Map<String, String> validateStockData(StockDTO stockDTO) {
        Map<String, String> errors = new HashMap<>();

        if (stockDTO == null) {
            errors.put("stock", "stock object is null");
            return errors;
        }

        if (stockDTO.getQuantity() == null) {
            errors.put("stock", "quantity is null");
            return errors;
        }

        if (stockDTO.getQuantity() < Integer.valueOf(0)) {
            errors.put("stock", "quantity is incorrect: " + stockDTO.getQuantity());
            return errors;
        }
        //WALIDACJA SHOP
        errors = new ShopValidator().validateShopData(stockDTO.getShopDTO());
        if (!errors.isEmpty()) {
            return errors;
        }

        //WALIDACJA PRODUCT
        errors = new ProductValidator().validateProductData(stockDTO.getProductDTO());
        if (!errors.isEmpty()) {
            return errors;
        }
        return errors;
    }
}
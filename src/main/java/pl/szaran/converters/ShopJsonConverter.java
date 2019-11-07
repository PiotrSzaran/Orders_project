package pl.szaran.converters;

import pl.szaran.dto.ShopDTO;

import java.util.List;

public class ShopJsonConverter extends JsonConverter<List<ShopDTO>> {
    public ShopJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

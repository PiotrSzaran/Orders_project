package pl.szaran.converters;

import pl.szaran.dto.ProductDTO;

import java.util.List;

public class ProductJsonConverter extends JsonConverter<List<ProductDTO>> {
    public ProductJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

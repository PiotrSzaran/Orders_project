package pl.szaran.converters;

import pl.szaran.dto.StockDTO;

import java.util.List;

public class StockJsonConverter extends JsonConverter<List<StockDTO>> {
    public StockJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

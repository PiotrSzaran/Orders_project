package pl.szaran.converters;

import pl.szaran.dto.TradeDTO;

import java.util.List;

public class TradeJsonConverter extends JsonConverter<List<TradeDTO>> {
    public TradeJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

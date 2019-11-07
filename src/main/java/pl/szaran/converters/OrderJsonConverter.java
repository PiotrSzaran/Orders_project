package pl.szaran.converters;

import pl.szaran.dto.OrderDTO;

import java.util.List;

public class OrderJsonConverter extends JsonConverter<List<OrderDTO>> {
    public OrderJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

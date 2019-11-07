package pl.szaran.converters;

import pl.szaran.dto.CustomerDTO;

import java.util.List;

public class CustomerJsonConverter extends JsonConverter<List<CustomerDTO>> {
    public CustomerJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

package pl.szaran.converters;

import pl.szaran.dto.ProducerDTO;

import java.util.List;

public class ProducerJsonConverter extends JsonConverter<List<ProducerDTO>> {
    public ProducerJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

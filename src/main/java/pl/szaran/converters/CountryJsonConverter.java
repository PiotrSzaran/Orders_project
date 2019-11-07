package pl.szaran.converters;

import pl.szaran.dto.CountryDTO;

import java.util.List;

public class CountryJsonConverter extends JsonConverter<List<CountryDTO>> {
    public CountryJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

package pl.szaran.converters;

import pl.szaran.dto.ErrorDTO;

import java.util.List;

public class ErrorJsonConverter extends JsonConverter<List<ErrorDTO>> {
    public ErrorJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

package pl.szaran.converters;

import pl.szaran.dto.CategoryDTO;

import java.util.List;

public class CategoryJsonConverter extends JsonConverter<List<CategoryDTO>> {
    public CategoryJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}

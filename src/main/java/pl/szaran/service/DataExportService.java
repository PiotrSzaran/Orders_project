package pl.szaran.service;

import pl.szaran.converters.CategoryJsonConverter;

public class DataExportService {
    private final CategoryService categoryService;

    public DataExportService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private void exportCategoriesToJson(final String jsonFilename) {
        CategoryJsonConverter categoryJsonConverter = new CategoryJsonConverter(jsonFilename);
        categoryJsonConverter
                .toJson(categoryService.getCategories());
    }

    public void exportData() {
        final String categoriesJsonFilename = "categories.json";
        exportCategoriesToJson(categoriesJsonFilename);
    }
}

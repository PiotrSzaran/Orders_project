package pl.szaran.service;

import pl.szaran.dto.CategoryDTO;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Category;
import pl.szaran.repository.CategoryRepository;
import pl.szaran.repository.CategoryRepositoryImpl;
import pl.szaran.validators.CategoryValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryService implements ModelMapper {
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private final String TABLE = "CATEGORY;";

    public void addCategory(CategoryDTO categoryDTO) {
        CategoryValidator categoryValidator = new CategoryValidator();
        //------WALIDACJA------
        var validate = categoryValidator.validateCategoryData(categoryDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Category category = null;
            if (categoryDTO.getId() != null) {
                category = categoryRepository.findById(categoryDTO.getId()).orElse(null);
            }
            if (category == null && categoryDTO.getName() != null) {
                category = categoryRepository.findByName(categoryDTO.getName()).orElse(null);
            }

            if (category == null) {
                category = ModelMapper.fromCategoryDTOToCategory(categoryDTO);
                categoryRepository.saveOrUpdate(category);
            } else {
                String message = "CATEGORY " + categoryDTO.getName() + " ALREADY ADDED";
                errorService.addError(TABLE + message);
                System.out.println(message);
            }

            if (category == null) {
                errorService.addError(TABLE + "PROBLEMS WITH CATEGORY");
                throw new MyException(ExceptionCode.SERVICE, "PROBLEMS WITH CATEGORY");
            }
        }
    }

    public List<CategoryDTO> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCategoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    public Map<Integer, Category> getMapOfCategories() {
        List<Category> list = new ArrayList<>(categoryRepository.findAll());
        int i = 1;
        Map<Integer, Category> categoriesMap = new HashMap<>();
        for (Category c : list) {
            categoriesMap.put(i, c);
            i++;
        }
        return categoriesMap;
    }

    public void showCategories() {

        Map<Integer, Category> map = getMapOfCategories();
        for (Map.Entry<Integer, Category> entry : map.entrySet()) {

            System.out.println(entry.getKey() + ". " + entry.getValue().getName());
        }
    }

    public void deleteCategories() {
        categoryRepository.deleteAll();
    }
}

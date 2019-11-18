package pl.szaran.service;

import pl.szaran.dto.*;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.*;
import pl.szaran.repository.*;
import pl.szaran.validators.ProductValidator;

import java.util.*;
import java.util.stream.Collectors;

public class ProductService implements ModelMapper {
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private final CountryRepository countryRepository = new CountryRepositoryImpl();
    private final TradeRepository tradeRepository = new TradeRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private String TABLE = "PRODUCT;";

    public void addProduct(ProductDTO productDTO) {
        ProductValidator productValidator = new ProductValidator();
        //------WALIDACJA------
        var validate = productValidator.validateProductData(productDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Product product = null;
            if (productDTO.getId() != null) {
                product = productRepository.findById(productDTO.getId()).orElse(null);
            }
            if (product == null) {

                Category category = null;

                if (productDTO.getCategoryDTO().getId() != null) {
                    category = categoryRepository.findById(productDTO.getCategoryDTO().getId()).orElse(null);
                }
                if (category == null && productDTO.getCategoryDTO().getName() != null) {
                    category = categoryRepository.findByName(productDTO.getCategoryDTO().getName()).orElse(null);
                }
                if (category == null) {
                    category = categoryRepository
                            .saveOrUpdate(Category.builder().name(productDTO.getCategoryDTO().getName()).build())
                            .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW CATEGORY"));
                }

                Producer producer = null;

                if ((productDTO.getProducerDTO().getId() != null)) {
                    producer = producerRepository.findById(productDTO.getProducerDTO().getId()).orElse(null);
                }
                if (producer == null && productDTO.getProducerDTO().getName() != null) {
                    producer = producerRepository.findByName(productDTO.getProducerDTO().getName()).orElse(null);
                }

                if (producer == null) {

                    Country country = null;
                    if (productDTO.getProducerDTO().getCountryDTO().getId() != null) {
                        country = countryRepository.findById(productDTO.getProducerDTO().getCountryDTO().getId()).orElse(null);
                    }
                    if (country == null && productDTO.getProducerDTO().getCountryDTO().getName() != null) {
                        country = countryRepository.findByName(productDTO.getProducerDTO().getCountryDTO().getName()).orElse(null);
                    }
                    if (country == null) {
                        country = countryRepository
                                .saveOrUpdate(Country.builder().name(productDTO.getProducerDTO().getCountryDTO().getName()).build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW COUNTRY"));
                    }

                    Trade trade = null;
                    if (productDTO.getProducerDTO().getTradeDTO().getId() != null) {
                        trade = tradeRepository.findById(productDTO.getProducerDTO().getTradeDTO().getId()).orElse(null);
                    }
                    if (trade == null && productDTO.getProducerDTO().getTradeDTO().getName() != null) {
                        trade = tradeRepository.findByName(productDTO.getProducerDTO().getTradeDTO().getName()).orElse(null);
                    }

                    if (trade == null) {
                        trade = tradeRepository
                                .saveOrUpdate(Trade.builder().name(productDTO.getProducerDTO().getTradeDTO().getName()).build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW TRADE"));
                    }

                    if (producerRepository.isProducerWithNameCountryTrade(productDTO.getProducerDTO().getName(), productDTO.getProducerDTO().getCountryDTO().getName(), productDTO.getProducerDTO().getTradeDTO().getName())) {
                        String errorMessage = "PRODUCER " + productDTO.getProducerDTO().getName()
                                + " FROM " + productDTO.getProducerDTO().getCountryDTO().getName()
                                + " AND TRADE " + productDTO.getProducerDTO().getTradeDTO().getName()
                                + " ALREADY ADDED";
                        System.out.println(errorMessage);
                        errorService.addError(TABLE + errorMessage);
                    } else {
                        producer = ModelMapper.fromProducerDTOToProducer(productDTO.getProducerDTO());
                        producer.setCountry(country);
                        producer.setTrade(trade);
                        producer = producerRepository.saveOrUpdate(Producer.builder()
                                .name(productDTO.getProducerDTO().getName())
                                .country(country)
                                .trade(trade)
                                .build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD PRODUCER"));
                    }
                }

                if (productRepository.isProductWithProducerCategory(productDTO.getName(), productDTO.getProducerDTO().getName(), productDTO.getCategoryDTO().getName())) {
                    String errorMessage = "PRODUCT " + productDTO.getName()
                            + " FROM PRODUCER " + productDTO.getProducerDTO().getName()
                            + " AND CATEGORY " + productDTO.getCategoryDTO().getName() + " ALREADY ADDED";
                    System.out.println(errorMessage);
                    errorService.addError(TABLE + errorMessage);
                } else {
                    product = ModelMapper.fromProductDTOToProduct(productDTO);
                    product.setCategory(category);
                    product.setProducer(producer);
                    productRepository.saveOrUpdate(product);
                }
            }
        }
    }

    public List<ProductDTO> getProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromProductToProductDTO)
                .collect(Collectors.toList());
    }

    public Map<Integer, Product> getMapOfProducts() {
        List<Product> list = new ArrayList<>(productRepository.findAll());
        int i = 1;
        Map<Integer, Product> productsMap = new HashMap<>();
        for (Product p : list) {
            productsMap.put(i, p);
            i++;
        }
        return productsMap;
    }

    public void showProducts() {
        Map<Integer, Product> map = getMapOfProducts();
        for (Map.Entry<Integer, Product> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName() + ", cena: "
                    + entry.getValue().getPrice() + ", wyprodukowany przez: " + entry.getValue().getProducer().getName() + " "
                    + entry.getValue().getProducer().getCountry().getName() + ", kategoria: " + entry.getValue().getCategory().getName()
                    + ", pakiet gwarancyjny: " + entry.getValue().getGuaranteeComponents()
            );
        }
    }

    public void deleteProducts() {
        productRepository.deleteAll();
    }

    public Map<Integer, Product> showProductsByCategory() {
        final CategoryService categoryService = new CategoryService();
        categoryService.showCategories();
        int i = UserDataService.getInt("Wybierz kategorię produktu");
        Map<Integer, Category> mapOfCategories = categoryService.getMapOfCategories();
        Map<Integer, Product> map = getMapOfProductsWithCategory(mapOfCategories.get(i).getName());

        for (Map.Entry<Integer, Product> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName() + ", cena: "
                    + entry.getValue().getPrice() + ", wyprodukowany przez: " + entry.getValue().getProducer().getName() + " "
                    + entry.getValue().getProducer().getCountry().getName()
                    + ", pakiet gwarancyjny: " + entry.getValue().getGuaranteeComponents()
            );
        }

        return map;
    }

    public Map<Integer, Product> getMapOfProductsWithCategory(String s) {
        if (s == null) {
            errorService.addError(TABLE + "METHOD getMapOfProductsWithCategory: ARGUMENT OF METHOD IS NULL");
            throw new MyException(ExceptionCode.SERVICE, "METHOD getMapOfProductsWithCategory: ARGUMENT OF METHOD IS NULL");
        }
        List<Product> list = new ArrayList<>(productRepository.findByCategory(s));
        int i = 1;
        Map<Integer, Product> productsMap = new HashMap<>();
        for (Product p : list) {
            productsMap.put(i, p);
            i++;
        }
        return productsMap;
    }

    public Map<Integer, Product> getProductsWithBiggestPriceFromCategory() {
        List<Product> list = productRepository.findBiggestPriceByCategories();
        int i = 1;
        Map<Integer, Product> productsMap = new HashMap<>();
        for (Product p : list) {
            productsMap.put(i, p);
            i++;
        }
        return productsMap;
    }

    public List<Product> getProductListByClientData(String name, String surname, String countryName) {
        if (name.equals(null) || name.isEmpty() || surname.isEmpty() || surname.equals(null) || countryName.isEmpty() || countryName.equals(null)) {
            //return null;
            throw new MyException(ExceptionCode.SERVICE, "METHOD getProductListByClientData: ARGUMENT OF METHOD IS INCORRECT");
        }
        return productRepository.getProductsByClientData(name, surname, countryName);
    }

    public void showProductsByClientData() {
        String name = UserDataService.getString("Podaj imię klienta:", "[A-Z]+");
        String surname = UserDataService.getString("Podaj nazwisko klienta:", "[A-Z]+");
        String countryName = UserDataService.getString("Podaj kraj pochodzenia produktu", "[A-Z]+");

        if (name.equals(null) || name.isEmpty() || surname.isEmpty() || surname.equals(null) || countryName.isEmpty() || countryName.equals(null)) {
            throw new MyException(ExceptionCode.SERVICE, "METHOD showProductsNyClientData: NAME: " + name + ", SURNAME: "
                    + surname + " OR COUNTRY: " + countryName + " ARE INCORRECT");

        } else {

            List<Product> list = getProductListByClientData(name, surname, countryName);

            for (Product p : list) {
                System.out.println(p.getName() + " " + p.getPrice() + " " + p.getProducer().getName());
            }
        }
    }

    //todo dorobić metodę
    public void showProductsWithBiggestPriceCategory() {
    }

    //todo dorobić metodę
    public void showProductsByCustomersOriginAndAge() {
    }

}

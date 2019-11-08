package pl.szaran.service;

import pl.szaran.converters.*;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;

public class DataGeneratorService {

    private final CategoryService categoryService;
    private final CountryService countryService;
    private final CustomerService customerService;
    private final ErrorService errorService;
    private final OrderService orderService;
    private final ProducerService producerService;
    private final ProductService productService;
    private final ShopService shopService;
    private final StockService stockService;
    private final TradeService tradeService;

    public DataGeneratorService(CategoryService categoryService, CountryService countryService, CustomerService customerService,
                                ErrorService errorService, OrderService orderService, ProducerService producerService,
                                ProductService productService, ShopService shopService, StockService stockService,
                                TradeService tradeService) {
        this.categoryService = categoryService;
        this.countryService = countryService;
        this.customerService = customerService;
        this.errorService = errorService;
        this.orderService = orderService;
        this.producerService = producerService;
        this.productService = productService;
        this.shopService = shopService;
        this.stockService = stockService;
        this.tradeService = tradeService;
    }

    private void saveCategoriesToDB(final String jsonFilename) {
        CategoryJsonConverter categoryJsonConverter = new CategoryJsonConverter(jsonFilename);
        categoryJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CATEGORY JSON CONVERTER - DATA GENERATOR"))
                .forEach(categoryService::addCategory);
    }

    private void saveCountriesToDB(final String jsonFilename) {
        CountryJsonConverter countryJsonConverter = new CountryJsonConverter(jsonFilename);
        countryJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "COUNTRY JSON CONVERTER - DATA GENERATOR"))
                .forEach(countryService::addCountry);
    }

    private void saveCustomersToDB(final String jsonFilename) {
        CustomerJsonConverter customerJsonConverter = new CustomerJsonConverter(jsonFilename);
        customerJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CUSTOMER JSON CONVERTER - DATA GENERATOR"))
                .forEach(customerService::addCustomer);
    }

    private void  saveErrorsToDB(final String jsonFilename) {
        ErrorJsonConverter errorJsonConverter = new ErrorJsonConverter(jsonFilename);
        errorJsonConverter
                .fromJson()
                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "ERROR JSON CONVERTER - DATA GENERATOR"))
                .forEach(errorService::addError);
    }

    private void saveOrdersToDB(final String jsonFilename) {
        OrderJsonConverter orderJsonConverter = new OrderJsonConverter(jsonFilename);
        orderJsonConverter
                .fromJson()
                .orElseThrow(()-> new MyException(ExceptionCode.SERVICE, "ORDER JSON CONVERTER - DATA GENERATOR"))
                //.forEach(System.out::println);
                .forEach(orderService::addOrder);
    }

    private void saveProducersToDB(final String jsonFilename) {
        ProducerJsonConverter producerJsonConverter = new ProducerJsonConverter(jsonFilename);
        producerJsonConverter
                .fromJson()
                .orElseThrow(()-> new MyException(ExceptionCode.SERVICE, "PRODUCER JSON CONVERTER - DATA GENERATOR"))
                .forEach(producerService::addProducer);
    }

    private void saveProductsToDB(final String jsonFilename) {
        ProductJsonConverter productJsonConverter = new ProductJsonConverter(jsonFilename);
        productJsonConverter
                .fromJson()
                .orElseThrow(()-> new MyException(ExceptionCode.SERVICE, "PRODUCT JSON CONVERTER - DATA GENERATOR"))
                .forEach(productService::addProduct);
    }

    private void saveShopsToDB(final String jsonFilename) {
        ShopJsonConverter shopJsonConverter = new ShopJsonConverter(jsonFilename);
        shopJsonConverter
                .fromJson()
                .orElseThrow(()-> new MyException(ExceptionCode.SERVICE, "SHOP JSON CONVERTER - DATA GENERATOR"))
                .forEach(shopService::addShop);
    }

    private void saveStockToDB(final String jsonFilename) {
        StockJsonConverter stockJsonConverter = new StockJsonConverter(jsonFilename);
        stockJsonConverter
                .fromJson()
                .orElseThrow(()-> new MyException(ExceptionCode.SERVICE, "STOCK JSON CONVERTER - DATA GENERATOR"))
                .forEach(stockService::addStock);
    }

    private void saveTradesToDB(final String jsonFilename) {
        TradeJsonConverter tradeJsonConverter = new TradeJsonConverter(jsonFilename);
        tradeJsonConverter
                .fromJson()
                .orElseThrow(()-> new MyException(ExceptionCode.SERVICE, "TRADE JSON CONVERTER - DATA GENERATOR"))
                .forEach(tradeService::addTrade);
    }



}

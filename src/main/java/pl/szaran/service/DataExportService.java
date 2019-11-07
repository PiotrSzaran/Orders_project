package pl.szaran.service;

import pl.szaran.converters.*;

public class DataExportService {
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

    public DataExportService(CategoryService categoryService, CountryService countryService,CustomerService customerService,
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

    private void exportCategoriesToJson(final String jsonFilename) {
        CategoryJsonConverter categoryJsonConverter = new CategoryJsonConverter(jsonFilename);
        categoryJsonConverter
                .toJson(categoryService.getCategories());
    }

    private void exportCountriesToJson(final String jsonFilename) {
        CountryJsonConverter countryJsonConverter = new CountryJsonConverter(jsonFilename);
        countryJsonConverter
                .toJson(countryService.getCountries());
    }

    private void exportCustomersToJson(final String jsonFilename) {
        CustomerJsonConverter customerJsonConverter = new CustomerJsonConverter(jsonFilename);
        customerJsonConverter
                .toJson(customerService.getCustomers());
    }

    private void exportErrorsToJson(final String jsonFilename) {
        ErrorJsonConverter errorJsonConverter = new ErrorJsonConverter(jsonFilename);
        errorJsonConverter
                .toJson(errorService.getErrors());
    }

    private void exportOrdersToJson(final String jsonFilename) {
        OrderJsonConverter orderJsonConverter = new OrderJsonConverter(jsonFilename);
        orderJsonConverter
                .toJson(orderService.getOrders());
    }

    private void exportProducersToJson(final String jsonFilename) {
        ProducerJsonConverter producerJsonConverter = new ProducerJsonConverter(jsonFilename);
        producerJsonConverter
                .toJson(producerService.getProducers());
    }

    private void exportProductsToJson(final String jsonFilename) {
        ProductJsonConverter productJsonConverter = new ProductJsonConverter(jsonFilename);
        productJsonConverter
                .toJson(productService.getProducts());
    }

    private void exportShopsToJson(final String jsonFilename) {
        ShopJsonConverter shopJsonConverter = new ShopJsonConverter(jsonFilename);
        shopJsonConverter
                .toJson(shopService.getShops());
    }

    private void exportStockToJson(final String jsonFilename) {
        StockJsonConverter stockJsonConverter = new StockJsonConverter(jsonFilename);
        stockJsonConverter
                .toJson(stockService.getStock());
    }

    private void exportTradeToJson(final String jsonFilename) {
        TradeJsonConverter tradeJsonConverter = new TradeJsonConverter(jsonFilename);
        tradeJsonConverter
                .toJson(tradeService.getTrades());
    }

    public void exportData() {
        final String categoriesJsonFilename = "categories.json";
        exportCategoriesToJson(categoriesJsonFilename);

        final String countriesJsonFilename = "countries.json";
        exportCountriesToJson(countriesJsonFilename);

        final String customersJsonFilename = "customers.json";
        exportCustomersToJson(customersJsonFilename);

        final String errorsToJsonFilename = "errors.json";
        exportErrorsToJson(errorsToJsonFilename);

        final String ordersToJsonFilename = "orders.json";
        exportOrdersToJson(ordersToJsonFilename);

        final String producersToJsonFilename = "producers.json";
        exportProducersToJson(producersToJsonFilename);

        final String productsToJsonFilename = "products.json";
        exportProductsToJson(productsToJsonFilename);

        final String shopsToJsonFilename = "shops.json";
        exportShopsToJson(shopsToJsonFilename);

        final String stockToJsonFilename = "stock.json";
        exportStockToJson(stockToJsonFilename);

        final String tradeToJsonFilename = "trades.json";
        exportTradeToJson(tradeToJsonFilename);
    }
}

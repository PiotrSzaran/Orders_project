package pl.szaran.service;

import pl.szaran.dto.*;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.*;
import pl.szaran.repository.*;
import pl.szaran.validators.StockValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StockService implements ModelMapper {
    private final StockRepository stockRepository = new StockRepositoryImpl();
    private final ShopRepository shopRepository = new ShopRepositoryImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final CountryRepository countryRepository = new CountryRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private final TradeRepository tradeRepository = new TradeRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private final String TABLE = "STOCK;";

    public void addStock(StockDTO stockDTO) {
        StockValidator stockValidator = new StockValidator();
        //--------WALIDACJA---------
        var validate = stockValidator.validateStockData(stockDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Stock stock = null;
            if (stockDTO.getId() != null) {
                stock = stockRepository.findById(stockDTO.getId()).orElse(null);
            }
            if (stock == null) {

                Shop shop = null;
                if (stockDTO.getShopDTO().getId() != null) {
                    shop = shopRepository.findById(stockDTO.getShopDTO().getId()).orElse(null);
                }
                if (shop == null && stockDTO.getShopDTO().getName() != null) {
                    shop = shopRepository.findByName(stockDTO.getShopDTO().getName()).orElse(null);
                }
                if (shop == null) {
                    Country country = null;
                    if (stockDTO.getShopDTO().getCountryDTO().getId() != null) {
                        country = countryRepository.findById(stockDTO.getShopDTO().getCountryDTO().getId()).orElse(null);
                    }
                    if (country == null && stockDTO.getShopDTO().getCountryDTO().getName() != null) {
                        country = countryRepository.findByName(stockDTO.getShopDTO().getCountryDTO().getName()).orElse(null);
                    }
                    if (country == null) {
                        country = countryRepository.saveOrUpdate(Country.builder().name(stockDTO.getShopDTO().getCountryDTO().getName()).build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW COUNTRY FOR SHOP"));
                    }

                    if (shopRepository.isShopWithNameCountry(stockDTO.getShopDTO().getName(), stockDTO.getShopDTO().getCountryDTO().getName())) {
                        String errorMessage = "SHOP " + stockDTO.getShopDTO().getName()
                                + " FROM " + stockDTO.getShopDTO().getCountryDTO().getName() + " ALREADY ADDED";
                        System.out.println(errorMessage);
                        errorService.addError(TABLE + errorMessage);
                        shop = shopRepository.findByNameCountry(stockDTO.getShopDTO().getName(), stockDTO.getShopDTO().getCountryDTO().getName()).orElse(null);

                    } else {

                        shop = shopRepository.saveOrUpdate(Shop.builder()
                                .name(stockDTO.getShopDTO().getName())
                                .country(country)
                                .build()).orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW SHOP"));
                    }
                }

                Product product = null;
                if (stockDTO.getProductDTO().getId() != null) {
                    product = productRepository.findById(stockDTO.getProductDTO().getId()).orElse(null);
                }
                if (product == null && stockDTO.getProductDTO().getName() != null) {
                    product = productRepository.findByName(stockDTO.getProductDTO().getName()).orElse(null);
                }
                if (product == null) {
                    Category category = null;

                    if (stockDTO.getProductDTO().getCategoryDTO().getId() != null) {
                        category = categoryRepository.findById(stockDTO.getProductDTO().getCategoryDTO().getId()).orElse(null);
                    }
                    if (category == null && stockDTO.getProductDTO().getCategoryDTO().getName() != null) {
                        category = categoryRepository.findByName(stockDTO.getProductDTO().getCategoryDTO().getName()).orElse(null);
                    }
                    if (category == null) {
                        category = categoryRepository.saveOrUpdate(Category.builder().name(stockDTO.getProductDTO().getCategoryDTO().getName()).build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW CATEGORY"));
                    }

                    Producer producer = null;

                    if ((stockDTO.getProductDTO().getProducerDTO().getId() != null)) {
                        producer = producerRepository.findById(stockDTO.getProductDTO().getProducerDTO().getId()).orElse(null);
                    }
                    if (producer == null && stockDTO.getProductDTO().getProducerDTO().getName() != null) {
                        producer = producerRepository.findByName(stockDTO.getProductDTO().getProducerDTO().getName()).orElse(null);
                    }

                    if (producer == null) {

                        Country country = null;
                        if (stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getId() != null) {
                            country = countryRepository.findById(stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getId()).orElse(null);
                        }
                        if (country == null && stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getName() != null) {
                            country = countryRepository.findByName(stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getName()).orElse(null);
                        }
                        if (country == null) {
                            country = countryRepository.saveOrUpdate(Country.builder().name(stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getName()).build())
                                    .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW COUNTRY FOR PRODUCER"));
                        }

                        Trade trade = null;
                        if (stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getId() != null) {
                            trade = tradeRepository.findById(stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getId()).orElse(null);
                        }
                        if (trade == null && stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getName() != null) {
                            trade = tradeRepository.findByName(stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getName()).orElse(null);
                        }

                        if (trade == null) {
                            trade = tradeRepository.saveOrUpdate(Trade.builder().name(stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getName()).build())
                                    .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW TRADE"));
                        }

                        if (producerRepository.isProducerWithNameCountryTrade(stockDTO.getProductDTO().getProducerDTO().getName(),
                                stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getName(),
                                stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getName())) {
                            String errorMessage = "PRODUCER " + stockDTO.getProductDTO().getProducerDTO().getName()
                                    + " FROM " + stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getName()
                                    + " AND TRADE " + stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getName()
                                    + " ALREADY ADDED";
                            System.out.println(errorMessage);
                            errorService.addError(TABLE + errorMessage);
                            producer = producerRepository.findByNameCountryTrade(stockDTO.getProductDTO().getProducerDTO().getName(),
                                    stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getName(),
                                    stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getName()).orElse(null);
                        } else {

                            producer = producerRepository.saveOrUpdate(Producer.builder()
                                    .name(stockDTO.getProductDTO().getProducerDTO().getName())
                                    .country(country)
                                    .trade(trade)
                                    .build())
                                    .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW PRODUCER"));
                        }
                    }

                    if (productRepository.isProductWithProducerCategory(stockDTO.getProductDTO().getName(),
                            stockDTO.getProductDTO().getProducerDTO().getName(),
                            stockDTO.getProductDTO().getCategoryDTO().getName())) {
                        String errorMessage = "PRODUCT " + stockDTO.getProductDTO().getName()
                                + " FROM PRODUCER " + stockDTO.getProductDTO().getProducerDTO().getName()
                                + " AND CATEGORY " + stockDTO.getProductDTO().getCategoryDTO().getName() + " ALREADY ADDED";
                        System.out.println(errorMessage);
                        errorService.addError(TABLE + errorMessage);
                    } else {

                        product = productRepository.saveOrUpdate(Product.builder()
                                .name(stockDTO.getProductDTO().getName())
                                .price(stockDTO.getProductDTO().getPrice())
                                .guaranteeComponents(stockDTO.getProductDTO().getGuarantees())
                                .producer(producer)
                                .category(category)
                                .build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW PRODUCT"));
                    }
                }

                if (stockRepository.findByProductAndShop(stockDTO.getProductDTO().getName(), stockDTO.getShopDTO().getName(), stockDTO.getShopDTO().getCountryDTO().getName()) != 0) {
                    Integer quantity = stockRepository.getStockQuantity(stockDTO.getProductDTO().getName(), stockDTO.getShopDTO().getName());

                    if (!quantity.equals(stockDTO.getQuantity())) {
                        Long id = stockRepository.getIDByProductAndShop(stockDTO.getProductDTO().getName(), stockDTO.getShopDTO().getName(), stockDTO.getShopDTO().getCountryDTO().getName());
                        stockRepository.updateQuantity(id, Integer.sum(quantity, stockDTO.getQuantity()));

                    } else {
                        System.out.println("STOCK WITH THE SAME QUANITY");
                        errorService.addError(TABLE + "STOCK WITH THE SAME QUANITY");
                    }

                } else {
                    stock = ModelMapper.fromStockDTOToStock(stockDTO);
                    stock.setShop(shop);
                    stock.setProduct(product);
                    stockRepository.saveOrUpdate(stock)
                            .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW STOCK"));
                }
            }
        }
    }

    public List<StockDTO> getStock() {
        return stockRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromStockToStockDTO)
                .map(stockDTO -> StockDTO.builder()
                        .quantity(stockDTO.getQuantity())
                        .shopDTO(ShopDTO.builder()
                                .name(stockDTO.getShopDTO().getName())
                                .countryDTO(CountryDTO.builder()
                                        .name(stockDTO.getShopDTO().getCountryDTO().getName())
                                        .build())
                                .build())
                        .productDTO(ProductDTO.builder()
                                .name(stockDTO.getProductDTO().getName())
                                .price(stockDTO.getProductDTO().getPrice())
                                .categoryDTO(CategoryDTO.builder()
                                        .name(stockDTO.getProductDTO().getCategoryDTO().getName())
                                        .build())
                                .producerDTO(ProducerDTO.builder()
                                        .name(stockDTO.getProductDTO().getProducerDTO().getName())
                                        .countryDTO(CountryDTO.builder()
                                                .name(stockDTO.getProductDTO().getProducerDTO().getCountryDTO().getName())
                                                .build())
                                        .tradeDTO(TradeDTO.builder()
                                                .name(stockDTO.getProductDTO().getProducerDTO().getTradeDTO().getName())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    public Map<Integer, Stock> getMapOfStock() {
        List<Stock> list = stockRepository.findAll();
        int i = 1;
        Map<Integer, Stock> stockMap = new HashMap<>();
        for (Stock c : list) {
            stockMap.put(i, c);
            i++;
        }
        return stockMap;
    }

    public void showStock() {
        Map<Integer, Stock> map = getMapOfStock();
        for (Map.Entry<Integer, Stock> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getProduct().getName()
                    + ", sklep: " + entry.getValue().getShop().getName()
                    + ", ilość sztuk " + entry.getValue().getQuantity()
            );
        }
    }

    public void deleteStock() {
        stockRepository.deleteAll();
    }

    public Map<Integer, Stock> getMapOfStockProducts(String productName) {
        if (productName == null || productName.isEmpty()) {
            errorService.addError(TABLE + "METHOD getMapOfStockProducts: ARGUMENT OF METHOD IS INCORRECT");
            throw new MyException(ExceptionCode.SERVICE, "METHOD getMapOfStockProducts: ARGUMENT OF METHOD IS INCORRECT");
        }
        List<Stock> list = stockRepository.getStockByProduct(productName);
        int i = 1;
        Map<Integer, Stock> stockMap = new HashMap<>();
        for (Stock s : list) {
            stockMap.put(i, s);
            i++;
        }
        return stockMap;
    }

    public void updateStockQuantity(Stock stock, int quantity) {
        if (quantity <= 0 || stock == null) {
            throw new MyException(ExceptionCode.SERVICE, "UPDATE STOCK QUANTITY ERROR");

        } else {
            stockRepository.updateQuantity(stock.getId(), (stock.getQuantity() - quantity));
        }
    }
}
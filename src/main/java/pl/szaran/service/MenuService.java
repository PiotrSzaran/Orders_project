package pl.szaran.service;

import pl.szaran.dto.*;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.Customer;
import pl.szaran.model.Payment;
import pl.szaran.model.Product;
import pl.szaran.model.Stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public final class MenuService {

    private final CategoryService categoryService;
    private final CountryService countryService;
    private final CustomerService customerService;
    private final ErrorService errorService;
    private final GuaranteeService guaranteeService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final ProducerService producerService;
    private final ProductService productService;
    private final ShopService shopService;
    private final StockService stockService;
    private final TradeService tradeService;
    private final DataExportService dataExportService;
    private final DataGeneratorService dataGeneratorService;
    private final HtmlExportService htmlExportService;

    public MenuService(CategoryService categoryService, CountryService countryService, CustomerService customerService,
                       ErrorService errorService, GuaranteeService guaranteeService, OrderService orderService,
                       PaymentService paymentService, ProducerService producerService, ProductService productService,
                       ShopService shopService, StockService stockService, TradeService tradeService) {
        this.categoryService = categoryService;
        this.countryService = countryService;
        this.customerService = customerService;
        this.errorService = errorService;
        this.guaranteeService = guaranteeService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.producerService = producerService;
        this.productService = productService;
        this.shopService = shopService;
        this.stockService = stockService;
        this.tradeService = tradeService;

        dataExportService = new DataExportService(categoryService, countryService, customerService, errorService,
                orderService, producerService, productService, shopService, stockService, tradeService);

        dataGeneratorService = new DataGeneratorService(categoryService, countryService, customerService, errorService,
                orderService, producerService, productService, shopService, stockService, tradeService);

        htmlExportService = new HtmlExportService(categoryService, countryService, customerService, orderService,
                paymentService, producerService, productService, shopService, stockService, tradeService);
    }

    private void showMenu() {
        System.out.println("1. Wprowadź dane");
        System.out.println("2. Wyświetl dane");
        System.out.println("3. Import z plików JSON do DB");
        System.out.println("4. Eksport z DB do plików JSON");
        System.out.println("5. Usuwanie danych z DB");
        System.out.println("6. STATYSTYKA");
        System.out.println("7. Eksport do HTML");
        System.out.println("99. WYJSCIE");
    }

    private void showAddSubMenu() {
        System.out.println("1. Wprowadź kategorię");
        System.out.println("2. Wprowadź kraj");
        System.out.println("3. Wprowadź klienta");
        System.out.println("4. Wprowadź zamówienie");
        System.out.println("5. Wprowadź producenta");
        System.out.println("6. Wprowadź produkt");
        System.out.println("7. Wprowadź sklep");
        System.out.println("8. Wprowadź produkt do magazynu");
        System.out.println("9. Wprowadź branżę");
        System.out.println("99. POWRÓT DO GŁÓWNEGO MENU");
    }

    private void showPrintSubMenu() {
        System.out.println("1. Wyświetl kategorie produktów");
        System.out.println("2. Wyświetl kraje");
        System.out.println("3. Wyświetl klientów");
        System.out.println("4. Wyświetl zamówienia");
        System.out.println("5. Wyświetl producentów");
        System.out.println("6. Wyświetl produkty");
        System.out.println("7. Wyświetl produkty według wybranej kategorii");
        System.out.println("8. Wyświetl sklepy");
        System.out.println("9. Wyświetl stan magazynu");
        System.out.println("10. Wyświetl płatności");
        System.out.println("11. Wyświetl branże");
        System.out.println("12. Wyświetl usługi gwarancyjne");
        System.out.println("13. Wyświetl dziennik błędów");
        System.out.println("99. POWRÓT DO GŁÓWNEGO MENU");
    }

    private void showStatisticsSubMenu() {
        System.out.println("1. Wyświetl produkty o największej cenie w każdej kategorii");
        System.out.println("2. Wyświetl produkty według kraju i wieku klientów");
        System.out.println("3. Wyświetl sklepy, według produktów, których kraj jest inny niż kraje, w których występują oddziały sklepu");
        System.out.println("4. Wyświetl procentów według podanej branży, których całkowita liczba produktów > niż podana liczba");
        System.out.println("5. Wyświetl zamówienia, według daty o kwocie zamówienia większej niż wartość podana przez użytkownika.");
        System.out.println("6. Wyświetl produkty, według podanych danych. Produkty należy pogrupować ze względu na producenta");
        System.out.println("7. Wyświetl klientów, którzy zamówili przynajmniej jeden produkt pochodzący z tego samego kraju co klient");
        System.out.println("99. POWRÓT DO GŁÓWNEGO MENU");
    }

    public void mainMenu() {

        //DODAWANIE PŁATNOSCI DO BAZY - info w PaymentService
        paymentService.addPaymentsToDB();

        boolean quit = false;
        int option;
        try {
            do {
                showMenu();
                option = UserDataService.getInt("Wybierz opcję:");

                switch (option) {
                    case 1:
                        addSubMenu();
                        break;
                    case 2:
                        printSubMenu();
                        break;
                    case 3:
                        dataGeneratorService.generateData();
                        break;
                    case 4:
                        dataExportService.exportData();
                        break;
                    case 5:
                        dataGeneratorService.deleteData();
                        break;
                    case 6:
                        printStatisticsSubmenu();
                        break;
                    case 7:
                        htmlExportService.exportData();
                        break;
                    case 99:
                        quit = true;
                        break;
                    default:
                        System.out.println("\n NIEPOPRAWNY WYBÓR \n");
                }
            }
            while (!quit);
            System.out.println("DO ZOBACZENIA!");
        } catch (MyException e) {
            e.printStackTrace();
            System.err.println(e.getExceptionInfo());
        }
    }

    public void printSubMenu() {

        boolean quit = false;
        int option;
        try {
            do {
                showPrintSubMenu();

                option = UserDataService.getInt("Wybierz opcję:");

                switch (option) {
                    case 1:
                        categoryService.showCategories();
                        break;
                    case 2:
                        countryService.showCountries();
                        break;
                    case 3:
                        customerService.showCustomers();
                        break;
                    case 4:
                        orderService.showOrders();
                        break;
                    case 5:
                        producerService.showProducers();
                        break;
                    case 6:
                        productService.showProducts();
                        break;
                    case 7:
                        productService.showProductsByCategory();
                        break;
                    case 8:
                        shopService.showShops();
                        break;
                    case 9:
                        stockService.showStock();
                        break;
                    case 10:
                        paymentService.showPayments();
                        break;
                    case 11:
                        tradeService.showTrades();
                        break;
                    case 12:
                        guaranteeService.showGuarantees();
                        break;
                    case 13:
                        errorService.showErrors();
                        break;
                    case 99:
                        quit = true;
                        break;
                    default:
                        System.out.println("NIEPOPRAWNY WYBÓR");
                }
            } while (!quit);
        } catch (MyException e) {
            e.printStackTrace();
            System.err.println(e.getExceptionInfo());
        }
    }

    public void addSubMenu() {
        boolean quit = false;
        int option;
        try {
            do {
                showAddSubMenu();

                option = UserDataService.getInt("Wybierz opcję:");

                switch (option) {
                    case 1:
                        insertCategory();
                        break;
                    case 2:
                        insertCountry();
                        break;
                    case 3:
                        insertCustomer();
                        break;
                    case 4:
                        insertOrder();
                        break;
                    case 5:
                        insertProducer();
                        break;
                    case 6:
                        insertProduct();
                        break;
                    case 7:
                        insertShop();
                        break;
                    case 8:
                        insertProductToStock();
                        break;
                    case 9:
                        insertTrade();
                        break;
                    case 99:
                        quit = true;
                        break;
                    default:
                        System.out.println("NIEPOPRAWNY WYBÓR");
                }
            } while (!quit);
        } catch (MyException e) {
            e.printStackTrace();
            System.err.println(e.getExceptionInfo());
        }
    }

    void printStatisticsSubmenu() {
        boolean quit = false;
        int option;
        try {
            do {
                showStatisticsSubMenu();

                option = UserDataService.getInt("Wybierz opcję:");

                switch (option) {
                    case 1:
                        productService.showProductsWithBiggestPriceCategory();
                        break;
                    case 2:
                        productService.showProductsByCustomersOriginAndAge();
                        break;
                    case 3:
                        shopService.showShopsProductsWithDifferentCountries();
                        break;
                    case 4:
                        producerService.showProducersByTradeAndQuantity();
                        break;
                    case 5:
                        orderService.showOrdersByTimeTotalPrice();
                        break;
                    case 6:
                        productService.showProductsByClientData();
                        break;
                    case 7:
                        customerService.showCustomersWithCommonCountryProducts();
                        break;
                    case 99:
                        quit = true;
                        break;
                    default:
                        System.out.println("NIEPOPRAWNY WYBÓR");
                }
            } while (!quit);
        } catch (MyException e) {
            e.printStackTrace();
            System.err.println(e.getExceptionInfo());
        }
    }

    /**
     * Poniżej umieszczam metody dodające poszczególne obiekty (kategorie, kraje,  klientów, produkty, producentów, itd.) do DB
     */

    private void insertCategory() {
        String categoryName = UserDataService.getString("Podaj nazwę kategorii", "[A-Z]+");

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name(categoryName)
                .build();

        categoryService.addCategory(categoryDTO);
    }

    private void insertCountry() {
        String countryName = UserDataService.getString("Podaj nazwę kraju", "[A-Z]+");

        CountryDTO countryDTO = CountryDTO.builder()
                .name(countryName)
                .build();

        countryService.addCountry(countryDTO);
    }

    private void insertCustomer() {
        String customerName = UserDataService.getString("Podaj imię klienta", "[A-Z]+");
        String customerSurname = UserDataService.getString("Podaj nazwisko klienta", "[A-Z]+");
        String customerCountryName = UserDataService.getString("Podaj kraj klienta", "[A-Z]+");
        int customerAge = UserDataService.getInt("Podaj wiek klienta");

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name(customerName)
                .surname(customerSurname)
                .age(customerAge)
                .countryDTO(CountryDTO.builder()
                        .name(customerCountryName)
                        .build())
                .build();

        customerService.addCustomer(customerDTO);
    }

    private void insertOrder() {
        int choice;
        BigDecimal discount;

        Product product = null;
        Customer customer = null;
        Payment payment = null;
        Stock stock = null;
        int quantity;

        /**
         * WYBÓR PRODUKTU
         * generuję mapę produktów według wybranej kategorii metodą showProductsByCategory()
         * (którą wykorzystuję też w menu do podglądu produktów)
         * użytkownik wybiera produkt z mapy wyświetlonej na ekranie w formie listy
         */

        Map<Integer, Product> productMap = productService.showProductsByCategory();
        choice = UserDataService.getInt("Wybierz produkt:");
        if (choice < 1 && choice > productMap.size()) {
            throw new MyException(ExceptionCode.SERVICE, "PRODUCT - INVALID CHOICE");
        } else {
            product = productMap.get(choice);
            System.out.println("Wybrano " + product.getName() + ", którego cena wynosi " + product.getPrice());
        }

        /**
         * WYBÓR KLIENTA
         * generuję mapę klientów metodą showCustomers(), którą wykorzystuję też w menu do podglądu produktów.
         * użytkownik wybiera klienta z mapy wyświetlonej na ekranie w formie listy
         */

        Map<Integer, Customer> customerMap = customerService.showMapOfCustomers();
        choice = UserDataService.getInt("Wybierz klienta:");
        if (choice < 1 && choice > customerMap.size()) {
            throw new MyException(ExceptionCode.SERVICE, "CUSTOMER - INVALID CHOICE");
        } else {
            customer = customerMap.get(choice);
            System.out.println("Wybrany klient: " + customer.getName() + " " + customer.getSurname() + ", lat " + customer.getAge() + " " + customer.getCountry().getName());
        }

        /**
         * WYBÓR PŁATNOSCI
         * generuję mapę płatności metodą showPayments(), którą wykorzystję też w menu do podglądu płatności.
         * użytkownik wybiera płatność z mapy, wyświetlonej na ekranie w formie listy
         */

        Map<Integer, Payment> paymentMap = paymentService.showPayments();
        choice = UserDataService.getInt("Wynierz płatność:");
        if (choice < 1 && choice > paymentMap.size()) {
            throw new MyException(ExceptionCode.SERVICE, "PAYMENT - INVALID CHOICE");
        } else {
            payment = paymentMap.get(choice);
            System.out.println("Wybrano płatoność: " + payment.getPayment());
        }

        /**
         * WYBÓR SKLEPU Z MAGAZYNU w różnych sklepach może pojawić się ten sam towar w różenj ilości
         */

        Map<Integer, Stock> stockMap = stockService.getMapOfStockProducts(product.getName());
        System.out.println("Wybrany produkt: " + product.getName());
        for (Map.Entry<Integer, Stock> entry : stockMap.entrySet()) {
            System.out.println(entry.getKey() + ". " + " Sklep: " + entry.getValue().getShop().getName()
                    + " ilość w magazynie: " + entry.getValue().getQuantity());
        }

        System.out.println();
        choice = UserDataService.getInt("Wybierz sklep:");
        if (choice < 1 && choice > stockMap.size()) {
            throw new MyException(ExceptionCode.SERVICE, "STOCK - INVALID CHOICE");
        } else {
            stock = stockMap.get(choice);
        }
        System.out.println("Wybrano sklep: " + stock.getShop().getName());
        System.out.println("Dostępna ilość sztuk towaru: " + stock.getQuantity());

        choice = UserDataService.getInt("Podaj ilość zamawianego towaru");
        if (choice < 1 && choice > stock.getQuantity()) {
            errorService.addError("ORDER;" + "SELECTED QUANTITY IS INCORRECT");
            throw new MyException(ExceptionCode.SERVICE, "STOCK - INVALID QUANTITY");
        } else {
            quantity = choice;
        }

        System.out.println();
        discount = UserDataService.getBigDecimal("Wprowadź zniżkę (0-1):");
        //walidacja jest w OrderValidator

        OrderDTO orderDTO = OrderDTO.builder()
                .customerDTO(ModelMapper.fromCustomerToCustomerDTO(customer))
                .productDTO(ModelMapper.fromProductToProductDTO(product))
                .paymentDTO(ModelMapper.fromPaymentToPaymentDTO(payment))
                .date(LocalDate.now())
                .quantity(quantity)
                .discount(discount)
                .build();

        orderService.addOrder(orderDTO);
        stockService.updateStockQuantity(stock, quantity); //pomniejszenie stanu magazynu o zamawianą ilość towaru
    }

    private void insertProducer() {
        String producerName = UserDataService.getString("Podaj nazwę producenta", "[A-Z]+");
        String producerCountryName = UserDataService.getString("Podaj kraj producenta", "[A-Z]+");
        String producerTradeName = UserDataService.getString("Podaj branżę producenta", "[A-Z]+");

        ProducerDTO producerDTO = ProducerDTO.builder()
                .name(producerName)
                .countryDTO(CountryDTO.builder()
                        .name(producerCountryName)
                        .build())
                .tradeDTO(TradeDTO.builder()
                        .name(producerTradeName)
                        .build())
                .build();

        producerService.addProducer(producerDTO);
    }

    private void insertProduct() {
        String productName = UserDataService.getString("Podaj nazwę produktu", "[A-Z ]+");
        String productCategoryName = UserDataService.getString("Podaj nazwę kategorii produktu", "[A-Z ]+");
        String productProducerName = UserDataService.getString("Podaj nazwę producenta", "[A-Z ]+");
        String productProducerCountryName = UserDataService.getString("Podaj nazwę kraju producenta", "[A-Z ]+");
        String productProducerTradeName = UserDataService.getString("Podaj nazwę branży producenta", "[A-Z ]+");
        BigDecimal productPrice = UserDataService.getBigDecimal("Podaj cenę produktu");

        var guaranteeSet = guaranteeService.setProductGuarantees(); //wybór usług gwarancyjnych

        ProductDTO productDTO = ProductDTO.builder()
                .name(productName)
                .categoryDTO(CategoryDTO.builder()
                        .name(productCategoryName)
                        .build())
                .producerDTO(ProducerDTO.builder()
                        .name(productProducerName)
                        .countryDTO(CountryDTO.builder()
                                .name(productProducerCountryName)
                                .build())
                        .tradeDTO(TradeDTO.builder()
                                .name(productProducerTradeName)
                                .build())
                        .build())
                .price(productPrice)
                .guarantees(guaranteeSet)
                .build();

        productService.addProduct(productDTO);
    }

    private void insertShop() {
        String shopName = UserDataService.getString("Podaj nazwę sklepu", "[A-Z]+");
        String shopCountryName = UserDataService.getString("Podaj kraj sklepu", "[A-Z]+");

        ShopDTO shopDTO = ShopDTO.builder()
                .name(shopName)
                .countryDTO(CountryDTO.builder()
                        .name(shopCountryName)
                        .build())
                .build();

        shopService.addShop(shopDTO);
    }

    private void insertTrade() {
        String tradeName = UserDataService.getString("Podaj nazwę branży", "[A-Z]+");

        TradeDTO tradeDTO = TradeDTO.builder()
                .name(tradeName)
                .build();

        tradeService.addTrade(tradeDTO);
    }

    private void insertProductToStock() {
        String productName = UserDataService.getString("Podaj nazwę produktu", "[A-Z ]+");
        BigDecimal productPrice = UserDataService.getBigDecimal("Podaj cenę produktu");
        String categoryName = UserDataService.getString("Podaj nazwę kategorii produktu", "[A-Z ]+");
        String producerName = UserDataService.getString("Podaj nazwę producenta", "[A-Z ]+");
        String producerCountryName = UserDataService.getString("Podaj nazwę kraju producenta", "[A-Z ]+");
        String producerTradeName = UserDataService.getString("Podaj nazwę branży", "[A-Z ]+");
        String shopName = UserDataService.getString("Podaj nazwę sklepu", "[A-Z ]+");
        String shopCountryName = UserDataService.getString("Podaj nazwę kraju sklepu", "[A-Z ]+");
        int quantity = UserDataService.getInt("Podaj ilość produktu w magazynie");

        var guaranteeSet = guaranteeService.setProductGuarantees(); //wybór usług gwarancyjnych

        StockDTO stockDTO = StockDTO.builder()
                .quantity(quantity)
                .productDTO(ProductDTO.builder()
                        .name(productName)
                        .price(productPrice)
                        .guarantees(guaranteeSet)
                        .categoryDTO(CategoryDTO.builder()
                                .name(categoryName)
                                .build())
                        .producerDTO(ProducerDTO.builder()
                                .name(producerName)
                                .countryDTO(CountryDTO.builder()
                                        .name(producerCountryName)
                                        .build())
                                .tradeDTO(TradeDTO.builder()
                                        .name(producerTradeName)
                                        .build())
                                .build())
                        .build())
                .shopDTO(ShopDTO.builder()
                        .name(shopName)
                        .countryDTO(CountryDTO.builder()
                                .name(shopCountryName)
                                .build())
                        .build())
                .build();


        stockService.addStock(stockDTO);
    }

}
package pl.szaran.service;

import pl.szaran.exceptions.MyException;

import java.util.Scanner;

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

    public void mainMenu() {

        //DODAWANIE PŁATNOSCI DO BAZY - info w PaymentService
        paymentService.addPaymentsToDB();

        Scanner sc = new Scanner(System.in);
        boolean quit = false;
        int option;
        try {
            do {
                showMenu();
                System.out.println("Wybierz opcję:");

                option = sc.nextInt();

                switch (option) {
                    case 1:
                        showAddSubMenu();
                        break;
                    case 2:
                        printSubMenu();
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

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
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    public void printSubMenu() {

        Scanner sc = new Scanner(System.in);
        boolean quit = false;
        int option;
        try {
            do {
                showPrintSubMenu();
                System.out.println("Wybierz opcję:");
                option = sc.nextInt();

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
        }/* To zamykało zasób scannera przy powrocie do głównego menu, wywalając java.util.nosuchelementexception w metodzie mainMenu():
        finally {
            if (sc != null) {
                sc.close();
            }
        }*/
    }
}
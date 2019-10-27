package pl.szaran;

import pl.szaran.service.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        CategoryService categoryService = new CategoryService();
        CountryService countryService = new CountryService();
        CustomerService customerService = new CustomerService();
        ErrorService errorService = new ErrorService();
        GuaranteeService guaranteeService = new GuaranteeService();
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        ProducerService producerService = new ProducerService();
        ProductService productService = new ProductService();
        ShopService shopService = new ShopService();
        StockService stockService = new StockService();
        TradeService tradeService = new TradeService();

        MenuService menuService = new MenuService(categoryService, countryService, customerService, errorService,
                guaranteeService, orderService, paymentService, producerService, productService, shopService,
                stockService, tradeService);

        menuService.mainMenu();
    }
}

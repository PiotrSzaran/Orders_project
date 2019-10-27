package pl.szaran;

import pl.szaran.service.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        PaymentService paymentService = new PaymentService();

        MenuService menuService = new MenuService(paymentService);

        menuService.mainMenu();

    }
}

package pl.szaran.service;

import j2html.TagCreator;

import java.io.FileWriter;
import java.io.IOException;

import static j2html.TagCreator.*;

public class HtmlExportService {


    private final CategoryService categoryService;
    private final CountryService countryService;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final ProducerService producerService;
    private final ProductService productService;
    private final ShopService shopService;
    private final StockService stockService;
    private final TradeService tradeService;

    public HtmlExportService(CategoryService categoryService, CountryService countryService,
                             CustomerService customerService, OrderService orderService, PaymentService paymentService,
                             ProducerService producerService, ProductService productService,
                             ShopService shopService, StockService stockService, TradeService tradeService) {

        this.categoryService = categoryService;
        this.countryService = countryService;
        this.customerService = customerService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.producerService = producerService;
        this.productService = productService;
        this.shopService = shopService;
        this.stockService = stockService;
        this.tradeService = tradeService;
    }

    public void exportData() {

        final String categoriesHtmlFilename = "categories.html";
        final String countryHtmlFilename = "countries.html";
        final String customersHtmlFilename = "customers.html";
        final String ordersHtmlFilename = "orders.html";
        final String paymentsHtmlFilename = "payments.html";
        final String producersHtmlFilename = "producers.html";
        final String productsHtmlFilename = "products.html";
        final String shopsHtmlFilename = "shops.html";
        final String stockHtmlFilename = "stock.html";
        final String tradesHtmlFilename = "trades.html";

        exportCategoriesToHtml(categoriesHtmlFilename);
        exportCountriesToHtml(countryHtmlFilename);
        exportCustomersToHtml(customersHtmlFilename);
        exportOrdersToHtml(ordersHtmlFilename);
        exportPaymentsToHtml(paymentsHtmlFilename);
        exportProducersToHtml(producersHtmlFilename);
        exportProductsToHtml(productsHtmlFilename);
        exportShopsToHtml(shopsHtmlFilename);
        exportStockToHtml(stockHtmlFilename);
        exportTradesToHtml(tradesHtmlFilename);


    }

    private void exportCategoriesToHtml(final String htmlFilename) {

        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Kategorie")
                    ),
                    body(
                            each(categoryService.getCategories(), cat ->

                                    p(cat.getName())
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportCountriesToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Kraje")
                    ),
                    body(
                            each(countryService.getCountries(), country ->

                                    p(country.getName())
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportCustomersToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Klienci")
                    ),
                    body(
                            each(customerService.getCustomers(), c ->
                                    div(
                                            h3(c.getName() + " " + c.getSurname()),
                                            p("kraj pochodzenia: " + c.getCountryDTO().getName()),
                                            p("lat: " + c.getAge())
                                    )
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportOrdersToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Zamowienia")
                    ),
                    body(
                            each(orderService.getOrders(), order ->
                                    div(
                                            h4("Data zamowienia: " + order.getDate()),
                                            div(
                                                    h2("Klient: " + order.getCustomerDTO().getName() + " " + order.getCustomerDTO().getSurname()),
                                                    p(i("Wiek: " + order.getCustomerDTO().getAge())),
                                                    p(u("kraj pochodzenia: " + order.getCustomerDTO().getCountryDTO().getName()))
                                            ),
                                            div(
                                                    h3("Produkt: " + order.getProductDTO().getName()),
                                                    p("producent: " + order.getProductDTO().getProducerDTO().getName()),
                                                    p("kraj producenta: " + order.getProductDTO().getProducerDTO().getCountryDTO().getName()),
                                                    p("branza producenta: " + order.getProductDTO().getProducerDTO().getTradeDTO().getName())

                                            ),
                                            p("Platnosc: " + order.getPaymentDTO().getPayment()),
                                            p(b("cena: " + order.getProductDTO().getPrice()),
                                                    p("Znizka: " + order.getDiscount()),
                                                    p("Ilosc zamawianych produktow: " + order.getQuantity())
                                            )
                                    )
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportPaymentsToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Platnosci")
                    ),
                    body(
                            each(paymentService.getPayments(), payment ->

                                    p(payment.getPayment().toString())
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportProducersToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Producenci")
                    ),
                    body(
                            each(producerService.getProducers(), producer ->
                                    div(
                                            h3(producer.getName()),
                                            p("kraj pochodzenia: " + producer.getCountryDTO().getName()),
                                            p("branza: " + producer.getTradeDTO().getName())
                                    )
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportProductsToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Produkty")
                    ),
                    body(
                            each(productService.getProducts(), product ->
                                    div(
                                            h3(product.getName()),
                                            div(
                                                    p("producent: " + product.getProducerDTO().getName()),
                                                    p("kraj producenta: " + product.getProducerDTO().getCountryDTO().getName()),
                                                    p("branza producenta: " + product.getProducerDTO().getTradeDTO().getName())
                                            ),
                                            p("cena: " + product.getPrice())
                                    )
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportShopsToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            html(
                    head(
                            title("Sklepy")
                    ),
                    body(
                            each(shopService.getShops(), shop ->
                                    div(
                                            h3(shop.getName()),
                                            p("kraj pochodzenia: " + shop.getCountryDTO().getName())
                                    )
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exportStockToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Magazyn")
                    ),
                    body(
                            each(stockService.getStock(), stock ->
                                    div(
                                            p(b(stock.getProductDTO().getName())),
                                            p("sklep: " + stock.getShopDTO().getName() + " kraj sklepu: " + stock.getShopDTO().getCountryDTO().getName()),
                                            p("ilosc sztuk: " + stock.getQuantity())
                                    )
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void exportTradesToHtml(final String htmlFilename) {
        try (FileWriter fw = new FileWriter(htmlFilename)) {
            TagCreator.html(
                    head(
                            title("Branze")
                    ),
                    body(
                            each(tradeService.getTrades(), trade ->

                                    p(trade.getName())
                            )
                    )

            ).render(fw);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

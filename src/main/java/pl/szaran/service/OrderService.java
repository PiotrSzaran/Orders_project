package pl.szaran.service;

import pl.szaran.dto.*;
import pl.szaran.exceptions.ExceptionCode;
import pl.szaran.exceptions.MyException;
import pl.szaran.model.*;
import pl.szaran.repository.*;
import pl.szaran.validators.OrderValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService implements ModelMapper {
    private final OrderRepository orderRepository = new OrderRepositoryImpl();
    private final CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private final CountryRepository countryRepository = new CountryRepositoryImpl();
    private final TradeRepository tradeRepository = new TradeRepositoryImpl();
    private final PaymentRepository paymentRepository = new PaymentRepositoryImpl();
    private final ErrorService errorService = new ErrorService();
    private final String TABLE = "ORDER;";


    public void addOrder(OrderDTO orderDTO) {
        OrderValidator orderValidator = new OrderValidator();
        //------WALIDACJA------
        var validate = orderValidator.validateOrderData(orderDTO);
        if (!validate.isEmpty()) {
            validate.forEach((k, v) -> System.out.println(k + " " + v));
            validate.forEach((k, v) -> errorService.addError(TABLE + v));
        } else {

            Order order = null;
            if (orderDTO.getId() != null) {
                order = orderRepository.findById(orderDTO.getId()).orElse(null);
            }
            if (order == null) {

                Product product = null;
                if (orderDTO.getProductDTO().getId() != null) {
                    product = productRepository.findById(orderDTO.getProductDTO().getId()).orElse(null);
                }
                if (product == null) {
                    Category category = null;

                    if (orderDTO.getProductDTO().getCategoryDTO().getId() != null) {
                        category = categoryRepository.findById(orderDTO.getProductDTO().getCategoryDTO().getId()).orElse(null);
                    }
                    if (category == null && orderDTO.getProductDTO().getCategoryDTO().getName() != null) {
                        category = categoryRepository.findByName(orderDTO.getProductDTO().getCategoryDTO().getName()).orElse(null);
                    }
                    if (category == null) {
                        category = categoryRepository
                                .saveOrUpdate(Category.builder().name(orderDTO.getProductDTO().getCategoryDTO().getName()).build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW CATEGORY"));
                    }

                    Producer producer = null;
                    if (orderDTO.getProductDTO().getProducerDTO().getId() != null) {
                        producer = producerRepository.findById(orderDTO.getProductDTO().getProducerDTO().getId()).orElse(null);
                    }
                    if (producer == null) {
                        Country country = null;
                        if (orderDTO.getProductDTO().getProducerDTO().getCountryDTO().getId() != null) {
                            country = countryRepository.findById(orderDTO.getProductDTO().getProducerDTO().getCountryDTO().getId()).orElse(null);
                        }
                        if (country == null && orderDTO.getProductDTO().getProducerDTO().getCountryDTO().getName() != null) {
                            country = countryRepository.findByName(orderDTO.getProductDTO().getProducerDTO().getCountryDTO().getName()).orElse(null);
                        }
                        if (country == null) {
                            country = countryRepository
                                    .saveOrUpdate(Country.builder().name(orderDTO.getProductDTO().getProducerDTO().getCountryDTO().getName()).build())
                                    .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW COUNTRY"));
                        }


                        Trade trade = null;
                        if (orderDTO.getProductDTO().getProducerDTO().getTradeDTO().getId() != null) {
                            trade = tradeRepository.findById(orderDTO.getProductDTO().getProducerDTO().getTradeDTO().getId()).orElse(null);
                        }
                        if (trade == null && orderDTO.getProductDTO().getProducerDTO().getTradeDTO().getName() != null) {
                            trade = tradeRepository.findByName(orderDTO.getProductDTO().getProducerDTO().getTradeDTO().getName()).orElse(null);
                        }
                        if (trade == null) {
                            trade = tradeRepository
                                    .saveOrUpdate(Trade.builder().name(orderDTO.getProductDTO().getProducerDTO().getTradeDTO().getName()).build())
                                    .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW TRADE"));
                        }

                        if (producerRepository.isProducerWithNameCountryTrade(orderDTO.getProductDTO().getProducerDTO().getName(),
                                orderDTO.getProductDTO().getProducerDTO().getCountryDTO().getName(),
                                orderDTO.getProductDTO().getProducerDTO().getTradeDTO().getName())) {
                            String errorMessage = "PRODUCER " + orderDTO.getProductDTO().getProducerDTO().getName()
                                    + " FROM " + orderDTO.getProductDTO().getProducerDTO().getCountryDTO().getName()
                                    + " AND TRADE " + orderDTO.getProductDTO().getProducerDTO().getTradeDTO().getName()
                                    + " ALREADY ADDED";
                            System.out.println(errorMessage);
                            errorService.addError(TABLE + errorMessage);

                        } else {
                            producer = producerRepository.saveOrUpdate(Producer.builder()
                                    .name(orderDTO.getProductDTO().getProducerDTO().getName())
                                    .country(country)
                                    .trade(trade)
                                    .build())
                                    .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW PRODUCER"));

                        }
                    }
                    if (productRepository.isProductWithProducerCategory(orderDTO.getProductDTO().getName(),
                            orderDTO.getProductDTO().getProducerDTO().getName(),
                            orderDTO.getProductDTO().getCategoryDTO().getName())) {
                        String errorMessage = "PRODUCT " + orderDTO.getProductDTO().getName()
                                + " FROM PRODUCER " + orderDTO.getProductDTO().getProducerDTO().getName()
                                + " AND CATEGORY " + orderDTO.getProductDTO().getCategoryDTO().getName() + " ALREADY ADDED";
                        System.out.println(errorMessage);
                        errorService.addError(TABLE + errorMessage);

                    } else {
                        product = productRepository.saveOrUpdate(Product.builder()
                                .name(orderDTO.getProductDTO().getName())
                                .price(orderDTO.getProductDTO().getPrice())
                                .producer(producer)
                                .category(category)
                                .build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW PRODUCT"));
                    }
                }

                Customer customer = null;
                if (orderDTO.getCustomerDTO().getId() != null) {
                    customer = customerRepository.findById(orderDTO.getCustomerDTO().getId()).orElse(null);
                }
                if (customer == null) {
                    Country country = null;
                    if (orderDTO.getCustomerDTO().getCountryDTO().getId() != null) {
                        country = countryRepository.findById(orderDTO.getCustomerDTO().getCountryDTO().getId()).orElse(null);
                    }
                    if (country == null && orderDTO.getCustomerDTO().getCountryDTO().getName() != null) {
                        country = countryRepository.findByName(orderDTO.getCustomerDTO().getCountryDTO().getName()).orElse(null);
                    }
                    if (country == null) {
                        country = countryRepository
                                .saveOrUpdate(Country.builder().name(orderDTO.getCustomerDTO().getCountryDTO().getName()).build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW COUNTRY"));
                    }

                    if (customerRepository.isCustomerWithNameSurnameCountry(
                            orderDTO.getCustomerDTO().getName(),
                            orderDTO.getCustomerDTO().getSurname(),
                            orderDTO.getCustomerDTO().getAge(),
                            orderDTO.getCustomerDTO().getCountryDTO().getName()
                    )) {
                        String errorMessage = "CUSTOMER " + orderDTO.getCustomerDTO().getName() + "" + orderDTO.getCustomerDTO().getSurname()
                                + ", " + orderDTO.getCustomerDTO().getAge() + " YO "
                                + "FROM " + orderDTO.getCustomerDTO().getCountryDTO().getName()
                                + " ALREADY ADDED";
                        System.out.println(errorMessage);
                        errorService.addError(TABLE + errorMessage);
                    } else {
                        customer = customerRepository.saveOrUpdate(Customer.builder()
                                .name(orderDTO.getCustomerDTO().getName())
                                .surname(orderDTO.getCustomerDTO().getSurname())
                                .age(orderDTO.getCustomerDTO().getAge())
                                .country(country)
                                .build())
                                .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW CUSTOMER"));
                    }
                }
                Payment payment = null;
                if (orderDTO.getPaymentDTO().getId() != null) {
                    payment = paymentRepository.findById(orderDTO.getPaymentDTO().getId()).orElse(null);
                }
                if (payment == null && orderDTO.getPaymentDTO().getPayment() != null) {
                    payment = paymentRepository.findByName(orderDTO.getPaymentDTO().getPayment().name()).orElse(null);
                }
                if (payment == null) {
                    payment = paymentRepository
                            .saveOrUpdate(Payment.builder().payment(orderDTO.getPaymentDTO().getPayment()).build())
                            .orElseThrow(() -> new MyException(ExceptionCode.SERVICE, "CANNOT ADD NEW PAYMENT"));
                }

                order = ModelMapper.fromOrderDTOToOrder(orderDTO);
                order.setCustomer(customer);
                order.setDate(LocalDate.now());
                order.setProduct(product);
                order.setPayment(payment);
                orderRepository.saveOrUpdate(order);
            }
        }
    }

    public List<OrderDTO> getOrders() {
        return orderRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromOrderToOrderDTO)
                .map(orderDTO -> OrderDTO.builder()
                        .date(orderDTO.getDate())
                        .discount(orderDTO.getDiscount())
                        .quantity(orderDTO.getQuantity())
                        .customerDTO(CustomerDTO.builder()
                                .name(orderDTO.getCustomerDTO().getName())
                                .surname(orderDTO.getCustomerDTO().getSurname())
                                .age(orderDTO.getCustomerDTO().getAge())
                                .countryDTO(CountryDTO.builder()
                                        .name(orderDTO.getCustomerDTO().getCountryDTO().getName())
                                        .build())
                                .build())
                        .paymentDTO(PaymentDTO.builder()
                                .payment(orderDTO.getPaymentDTO().getPayment())
                                .build())
                        .productDTO(ProductDTO.builder()
                                .name(orderDTO.getProductDTO().getName())
                                .price(orderDTO.getProductDTO().getPrice())
                                .categoryDTO(CategoryDTO.builder()
                                        .name(orderDTO.getProductDTO().getCategoryDTO().getName())
                                        .build())
                                .producerDTO(ProducerDTO.builder()
                                        .name(orderDTO.getProductDTO().getProducerDTO().getName())
                                        .tradeDTO(TradeDTO.builder()
                                                .name(orderDTO.getProductDTO().getProducerDTO().getTradeDTO().getName())
                                                .build())
                                        .countryDTO(CountryDTO.builder()
                                                .name(orderDTO.getProductDTO().getProducerDTO().getCountryDTO().getName())
                                                .build())
                                        .build())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    public Map<Integer, Order> getMapOfOrders() {
        List<Order> list = new ArrayList<>(orderRepository.findAll());
        int i = 1;
        Map<Integer, Order> ordersMap = new HashMap<>();
        for (Order o : list) {
            ordersMap.put(i, o);
            i++;
        }
        return ordersMap;
    }

    public Map<Integer, Product> getMapOfProductsByCustomerCountryAndAge(String countryName, int youngest, int oldest) {
        List<Product> list = new ArrayList<>(orderRepository.findProductsByCustomerCountryAndAge(countryName, youngest, oldest));

        int i = 1;
        Map<Integer, Product> productsMap = new HashMap<>();
        for (Product p : list) {
            productsMap.put(i, p);
            i++;
        }
        return productsMap;
    }

    public void showOrders() {
        Map<Integer, Order> map = getMapOfOrders();
        for (Map.Entry<Integer, Order> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getProduct().getName()
                    + " Zamawiający: " + entry.getValue().getCustomer().getName() + " " + entry.getValue().getCustomer().getSurname()
                    + ", ilość sztuk: " + entry.getValue().getQuantity()
                    + ", znizka: " + entry.getValue().getDiscount()
                    + ", data zamówienia: " + entry.getValue().getDate()
                    + ", sposób płatności: " + entry.getValue().getPayment().getPayment());
        }
    }

    public void deleteOrders() {
        orderRepository.deleteAll();
    }

    public void showOrdersByTimeTotalPrice() {
        List<Order> orderList = orderRepository.findAll();
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj początkową datę zakresu");
        System.out.println("Podaj rok:");
        int startYear = sc.nextInt();
        System.out.println("Podaj miesiąc:");
        int startMonth = sc.nextInt();
        System.out.println("Podaj dzień:");
        int startDay = sc.nextInt();
        System.out.println("Podaj końcową datę zakresu");
        System.out.println("Podaj rok:");
        int endYear = sc.nextInt();
        System.out.println("Podaj miesiąc:");
        int endMonth = sc.nextInt();
        System.out.println("Podaj dzień:");
        int endDay = sc.nextInt();
        System.out.println("Kwotę zamówienia:");
        BigDecimal price = sc.nextBigDecimal();

        orderList
                .stream()
                .filter(order -> (order.getDate().isAfter(LocalDate.of(startYear, startMonth, startDay))))
                .filter(order -> order.getDate().isBefore(LocalDate.of(endYear, endMonth, endDay)))
                .filter(order -> ((order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
                        .subtract(order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())).multiply(order.getDiscount())).compareTo(price) >= 0))
                .forEach(order -> System.out.println(order.getProduct().getName() + " sprzedano " + order.getQuantity() + " sztuk, dnia "
                        + order.getDate() + " ze znizką " + order.getDiscount() + ". Cena za sztukę: " + order.getProduct().getPrice()
                        + " cena na zamowieniu: " + order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity()))
                        + " Razem ze znizką: " + (order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())))
                        .subtract(order.getProduct().getPrice().multiply(BigDecimal.valueOf(order.getQuantity())).multiply(order.getDiscount()))));
    }
}
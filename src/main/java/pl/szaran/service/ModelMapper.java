package pl.szaran.service;

import pl.szaran.dto.*;
import pl.szaran.model.*;
import pl.szaran.model.Error;

import java.util.HashSet;

public interface ModelMapper {

    //------CATEGORY------
    static CategoryDTO fromCategoryToCategoryDTO(Category category) {
        return category == null ? null : CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    static Category fromCategoryDTOToCategory(CategoryDTO categoryDTO) {
        return categoryDTO == null ? null : Category.builder()
                .id(categoryDTO.getId())
                .name(categoryDTO.getName())
                .products(new HashSet<>())
                .build();
    }

    //------COUNTRY------
    static CountryDTO fromCountryToCountryDTO(Country country) {
        return country == null ? null : CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    static Country fromCountryDTOToCountry(CountryDTO countryDTO) {
        return countryDTO == null ? null : Country.builder()
                .id(countryDTO.getId())
                .name(countryDTO.getName())
                .shops(new HashSet<>())
                .customers(new HashSet<>())
                .producers(new HashSet<>())
                .build();
    }

    //------CUSTOMER------
    static CustomerDTO fromCustomerToCustomerDTO(Customer customer) {
        return customer == null ? null : CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .age(customer.getAge())
                .countryDTO(customer.getCountry() == null ? null : fromCountryToCountryDTO(customer.getCountry()))
                .build();

    }

    static Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO) {
        return customerDTO == null ? null : Customer.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName().toUpperCase())
                .surname(customerDTO.getSurname().toUpperCase())
                .age(customerDTO.getAge())
                .country(customerDTO.getCountryDTO() == null ? null : fromCountryDTOToCountry(customerDTO.getCountryDTO()))
                .orders(new HashSet<>())
                .build();
    }

    //------ERROR------
    static ErrorDTO fromErrorToErrorDTO(Error error) {
        return error == null ? null : ErrorDTO.builder()
                .id(error.getId())
                .date(error.getDate())
                .message(error.getMessage())
                .build();
    }

    static Error fromErrorDTOToError(ErrorDTO errorDTO) {
        return errorDTO == null ? null : Error.builder()
                .id(errorDTO.getId())
                .date(errorDTO.getDate())
                .message(errorDTO.getMessage())
                .build();
    }

    //------ORDER------
    static OrderDTO fromOrderToOrderDTO(Order order) {
        return order == null ? null : OrderDTO.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .date(order.getDate())
                .discount(order.getDiscount())
                .customerDTO(order.getCustomer() == null ? null : fromCustomerToCustomerDTO(order.getCustomer()))
                .paymentDTO(order.getPayment() == null ? null : fromPaymentToPaymentDTO(order.getPayment()))
                .productDTO(order.getProduct() == null ? null : fromProductToProductDTO(order.getProduct()))
                .build();
    }

    static Order fromOrderDTOToOrder(OrderDTO orderDTO) {
        return orderDTO == null ? null : Order.builder()
                .id(orderDTO.getId())
                .quantity(orderDTO.getQuantity())
                .date(orderDTO.getDate())
                .discount(orderDTO.getDiscount())
                .customer(orderDTO.getCustomerDTO() == null ? null : fromCustomerDTOToCustomer(orderDTO.getCustomerDTO()))
                .payment(orderDTO.getPaymentDTO() == null ? null : fromPaymentDTOToPayment(orderDTO.getPaymentDTO()))
                .product(orderDTO.getProductDTO() == null ? null : fromProductDTOToProduct(orderDTO.getProductDTO()))
                .build();
    }

    //------PAYMENT------
    static PaymentDTO fromPaymentToPaymentDTO(Payment payment) {
        return payment == null ? null : PaymentDTO.builder()
                .id(payment.getId())
                .payment(payment.getPayment())
                .build();
    }

    static Payment fromPaymentDTOToPayment(PaymentDTO paymentDTO) {
        return paymentDTO == null ? null : Payment.builder()
                .id(paymentDTO.getId())
                .payment(paymentDTO.getPayment())
                .build();
    }

    //------GUARANTEE------
    /*public GuaranteeDTO fromGuaranteeToGuaranteeDTO(Guarantee guarantee) {
        return guarantee == null ? null : GuaranteeDTO.builder()
                .id(guarantee.getId())
                .guaranteeComponent(guarantee.getGuaranteeComponent())
                .build();
    }

    public Guarantee fromGuaranteeDTOToGuarantee(GuaranteeDTO guaranteeDTO) {
        return guaranteeDTO == null ? null : Guarantee.builder()
                .id(guaranteeDTO.getId())
                .guaranteeComponent(guaranteeDTO.getGuaranteeComponent())
                .build();

    }

     */

    //------PRODUCER------
    static ProducerDTO fromProducerToProducerDTO(Producer producer) {
        return producer == null ? null : ProducerDTO.builder()
                .id(producer.getId())
                .name(producer.getName())
                .countryDTO(producer.getCountry() == null ? null : fromCountryToCountryDTO(producer.getCountry()))
                .tradeDTO(producer.getTrade() == null ? null : fromTradeToTradeDTO(producer.getTrade()))
                .build();
    }

    static Producer fromProducerDTOToProducer(ProducerDTO producerDTO) {
        return producerDTO == null ? null : Producer.builder()
                .id(producerDTO.getId())
                .name(producerDTO.getName().toUpperCase())
                .country(producerDTO.getCountryDTO() == null ? null : fromCountryDTOToCountry(producerDTO.getCountryDTO()))
                .trade(producerDTO.getTradeDTO() == null ? null : fromTradeDTOToTrade(producerDTO.getTradeDTO()))
                .products(new HashSet<>())
                .build();
    }

    //------PRODUCT------
    static ProductDTO fromProductToProductDTO(Product product) {
        return product == null ? null : ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .guarantees(product.getGuaranteeComponents())
                .categoryDTO(product.getCategory() == null ? null : fromCategoryToCategoryDTO(product.getCategory()))
                .producerDTO(product.getProducer() == null ? null : fromProducerToProducerDTO(product.getProducer()))

                .build();
    }

    static Product fromProductDTOToProduct(ProductDTO productDTO) {
        System.out.println(productDTO);
        return productDTO == null ? null : Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .category(productDTO.getCategoryDTO() == null ? null : fromCategoryDTOToCategory(productDTO.getCategoryDTO()))
                .producer(productDTO.getProducerDTO() == null ? null : fromProducerDTOToProducer(productDTO.getProducerDTO()))
                .stocks(new HashSet<>())
                .orders(new HashSet<>())
                .guaranteeComponents(productDTO.getGuarantees())
                .build();
    }

    //------SHOP------
    static ShopDTO fromShopToShopDTO(Shop shop) {
        return shop == null ? null : ShopDTO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .countryDTO(shop.getCountry() == null ? null : fromCountryToCountryDTO(shop.getCountry()))
                .build();
    }

    static Shop fromShopDTOToShop(ShopDTO shopDTO) {
        return shopDTO == null ? null : Shop.builder()
                .id(shopDTO.getId())
                .name(shopDTO.getName().toUpperCase())
                .country(shopDTO.getCountryDTO() == null ? null : fromCountryDTOToCountry(shopDTO.getCountryDTO()))
                .stocks(new HashSet<>())
                .build();
    }

    //------STOCK------
    static StockDTO fromStockToStockDTO(Stock stock) {
        return stock == null ? null : StockDTO.builder()
                .id(stock.getId())
                .quantity(stock.getQuantity())
                .productDTO(stock.getProduct() == null ? null : fromProductToProductDTO(stock.getProduct()))
                .shopDTO(stock.getShop() == null ? null : fromShopToShopDTO(stock.getShop()))
                .build();
    }

    static Stock fromStockDTOToStock(StockDTO stockDTO) {
        return stockDTO == null ? null : Stock.builder()
                .id(stockDTO.getId())
                .quantity(stockDTO.getQuantity())
                .product(stockDTO.getProductDTO() == null ? null : fromProductDTOToProduct(stockDTO.getProductDTO()))
                .shop(stockDTO.getShopDTO() == null ? null : fromShopDTOToShop(stockDTO.getShopDTO()))
                .build();
    }

    //------TRADE------
    static TradeDTO fromTradeToTradeDTO(Trade trade) {
        return trade == null ? null : TradeDTO.builder()
                .id(trade.getId())
                .name(trade.getName())
                .build();
    }

    static Trade fromTradeDTOToTrade(TradeDTO tradeDTO) {
        return tradeDTO == null ? null : Trade.builder()
                .id(tradeDTO.getId())
                .name(tradeDTO.getName())
                .producers(new HashSet<>())
                .build();
    }
}
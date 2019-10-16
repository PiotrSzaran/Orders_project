package pl.szaran.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;
    private LocalDate date;
    private BigDecimal discount;


    //customer_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Customer customer;

    //payment_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "payment_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Payment payment;

    //product_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;
}

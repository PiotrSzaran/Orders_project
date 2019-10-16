package pl.szaran.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue
    private Long id;
    private Integer quantity;

    //product_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;

    //shop_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shop_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Shop shop;
}
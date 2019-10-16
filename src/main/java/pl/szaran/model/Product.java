package pl.szaran.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private BigDecimal price;

    //category_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    //producer_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Producer producer;


    @OneToMany(mappedBy = "product")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Stock> stocks;

    @OneToMany(mappedBy = "product")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Order> orders;

    /*@OneToMany(mappedBy = "product")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Guarantee> guaranteeComponents;

     */

    /*@ElementCollection
    @CollectionTable(
            name = "guarantee_components",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "guarantee_component")
    @Enumerated(EnumType.STRING)
    private Set<EGuarantee> guarantees ;
*/
    @ElementCollection
    @CollectionTable(
            name = "guarantee_components",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "guarantee_component")
    @Enumerated(EnumType.STRING)
    private Set<EGuarantee> guaranteeComponents;

}

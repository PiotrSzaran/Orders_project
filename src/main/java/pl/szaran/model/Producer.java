package pl.szaran.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "producers")
public class Producer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    //country_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "country_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Country country;


    //trade_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "trade_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Trade trade;


    @OneToMany(mappedBy = "producer")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Product> products;

}

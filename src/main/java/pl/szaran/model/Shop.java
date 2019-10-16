package pl.szaran.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "shops")
public class Shop {
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

    @OneToMany(mappedBy = "shop")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Stock> stocks;
}

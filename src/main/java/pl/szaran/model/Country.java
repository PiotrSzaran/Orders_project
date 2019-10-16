package pl.szaran.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Shop> shops;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Customer> customers;

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Producer> producers;

}

package pl.szaran.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private Integer age;


    //country_id
    @ManyToOne(cascade = CascadeType.PERSIST)
    //@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Country country;

    @OneToMany(mappedBy = "customer")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Order> orders;


}

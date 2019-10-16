package pl.szaran.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "trades")
public class Trade {
    @Id
    @GeneratedValue
    private Long id;
    private String name;


    @OneToMany(mappedBy = "trade")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Producer> producers;
}

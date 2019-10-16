package pl.szaran.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Product> products;
}

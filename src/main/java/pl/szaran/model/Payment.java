package pl.szaran.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING) //(EnumType.STRING) - dla wartości tekstowej, Ordinal lub puste dla indeksu
    private EPayment payment;
}

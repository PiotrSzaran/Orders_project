package pl.szaran.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Integer quantity;
    private LocalDate date;
    private BigDecimal discount;
    private CustomerDTO customerDTO;
    private PaymentDTO paymentDTO;
    private ProductDTO productDTO;
}

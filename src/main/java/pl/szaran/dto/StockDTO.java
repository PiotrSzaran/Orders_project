package pl.szaran.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDTO {
    private Long id;
    private Integer quantity;
    private ProductDTO productDTO;
    private ShopDTO shopDTO;
}
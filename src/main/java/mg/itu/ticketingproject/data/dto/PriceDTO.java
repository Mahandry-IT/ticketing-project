package mg.itu.ticketingproject.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PriceDTO {
    private BigDecimal promotionPrice;
    private BigDecimal price;

    public PriceDTO(BigDecimal promotionPrice, BigDecimal price) {
        this.promotionPrice = promotionPrice;
        this.price = price;
    }

}

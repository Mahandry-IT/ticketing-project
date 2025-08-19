package mg.itu.ticketingproject.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeatAvailabilityDTO {
    private String seatType;
    private BigDecimal price;
    private int totalSeats;
    private Long availableSeats;

    public SeatAvailabilityDTO(String seatType, BigDecimal price, Integer totalSeats, Long availableSeats) {
        this.seatType = seatType;
        this.price = price;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

}

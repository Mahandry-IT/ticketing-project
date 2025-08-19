package mg.itu.ticketingproject.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import mg.itu.annotation.FormParametre;

@Data
public class PriceRequest {
    private Integer idFlight;
    private Integer idSeatType;
    private int age;
}

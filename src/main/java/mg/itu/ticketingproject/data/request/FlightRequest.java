package mg.itu.ticketingproject.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import mg.itu.annotation.FormParametre;

@Data
public class FlightRequest {
    @NotEmpty
    @FormParametre("departure_city")
    private String departureCity;
    @NotEmpty
    @FormParametre("arrival_city")
    private String arrivalCity;
    @NotEmpty
    @FormParametre("flight_id")
    private String flightId;
    @NotEmpty
    @FormParametre("departure_date")
    private String departureTime;
    @NotEmpty
    @FormParametre("arrival_date")
    private  String arrivalTime;
    @NotEmpty
    @FormParametre("plane")
    private String plane;
    @NotEmpty
    @FormParametre("seat_count")
    private String[] seatCount;
    @NotEmpty
    @FormParametre("seat_price")
    private String[] seatPrice;
    @NotEmpty
    @FormParametre("seat_id")
    private String[] seatId;
    @NotEmpty
    @FormParametre("plane_seat_id")
    private String[] planeSeatId;
}

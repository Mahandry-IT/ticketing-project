package mg.itu.ticketingproject.data.request;

import lombok.Data;
import mg.itu.annotation.FormParametre;

import java.time.LocalDateTime;

@Data
public class FlightMultiSearchRequest {
    @FormParametre("departure_city")
    private String departureCity;
    @FormParametre("arrival_city")
    private String arrivalCity;
    @FormParametre("departure_date")
    private LocalDateTime departureDate;
    @FormParametre("arrival_date")
    private LocalDateTime arrivalDate;
    @FormParametre("id_plane")
    private String idPlane;

}

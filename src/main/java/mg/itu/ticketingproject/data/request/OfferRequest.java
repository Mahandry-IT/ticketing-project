package mg.itu.ticketingproject.data.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mg.itu.annotation.FormParametre;

import java.sql.Date;

@Data
public class OfferRequest {
    @NotNull
    @FormParametre("id_offer")
    private int id_offer;
    @NotNull
    @FormParametre("flight")
    private int flight;
    @NotNull
    @FormParametre("seat_type")
    private int seat_type;
    @NotNull
    @FormParametre("number_seats")
    private int number_seats;
    @NotNull
    @FormParametre("discount")
    private double discount;
}

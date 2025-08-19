package mg.itu.ticketingproject.data.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mg.itu.annotation.FormParametre;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationRequest {
    @NotNull
    @FormParametre("flight_id")
    private Integer flight_id;
    @FormParametre("reservation_id")
    private Integer reservation_id;
    @NotNull
    @FormParametre("reservation_time")
    private LocalDateTime reservation_time;
    @NotEmpty
    @FormParametre("name")
    private List<String> name;
    @NotNull
    @FormParametre("seat_type")
    private List<Integer> seat_type;
    @NotNull
    @FormParametre("age")
    private List<Integer> age;
    @FormParametre("detail_id")
    private List<Integer> detail_id;
    @NotNull
    @FormParametre("promotion")
    private List<Double> price;
}

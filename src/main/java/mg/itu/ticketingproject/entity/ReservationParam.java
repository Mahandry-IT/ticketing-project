package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "reservation_param")
@Data
public class ReservationParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parameter", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "cancel_time", nullable = false)
    private Integer cancelTime;

    @NotNull
    @Column(name = "reservation_time", nullable = false)
    private Integer reservationTime;
}
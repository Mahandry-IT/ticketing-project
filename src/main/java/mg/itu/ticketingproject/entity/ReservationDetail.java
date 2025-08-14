package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "reservation_details")
@Data
public class ReservationDetail {
    @Id
    @Column(name = "id_details", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "passenger_name")
    private String passengerName;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @Size(max = 255)
    @NotNull
    @Column(name = "passport", nullable = false)
    private String passport;

    @NotNull
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type", nullable = false)
    private SeatType seatType;
}
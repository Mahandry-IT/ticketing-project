package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "reservation")
@Data
public class Reservation {
    @Id
    @Column(name = "id_reservation", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "reservation_time", nullable = false)
    private LocalDateTime reservationTime;

    @NotNull
    @Column(name = "total_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;

    @NotNull
    @Column(name = "passenger_count", nullable = false)
    private Integer passengerCount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private Appuser user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_flight", nullable = false)
    private Flight flight;

    @OneToMany(mappedBy = "reservation")
    private List<ReservationDetail> reservationDetails = new ArrayList<>();
}
package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @Column(name = "id_flight", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_departure_city", nullable = false)
    private City departureCity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_arrival_city", nullable = false)
    private City arrivalCity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_plane", nullable = false)
    private Plane plane;

}
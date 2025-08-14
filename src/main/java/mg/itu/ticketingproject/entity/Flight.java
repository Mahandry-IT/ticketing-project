package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @Column(name = "id_flight", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private Instant departureTime;

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    private Instant arrivalTime;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_departure_city", nullable = false)
    private City idDepartureCity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_arrival_city", nullable = false)
    private City idArrivalCity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_plane", nullable = false)
    private Plane idPlane;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Instant departureTime) {
        this.departureTime = departureTime;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public City getIdDepartureCity() {
        return idDepartureCity;
    }

    public void setIdDepartureCity(City idDepartureCity) {
        this.idDepartureCity = idDepartureCity;
    }

    public City getIdArrivalCity() {
        return idArrivalCity;
    }

    public void setIdArrivalCity(City idArrivalCity) {
        this.idArrivalCity = idArrivalCity;
    }

    public Plane getIdPlane() {
        return idPlane;
    }

    public void setIdPlane(Plane idPlane) {
        this.idPlane = idPlane;
    }

}
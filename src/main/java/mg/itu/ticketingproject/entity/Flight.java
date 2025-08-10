package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flight")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_flight")
    private Long idFlight;

    @Column(name = "flight_number", nullable = false, length = 20)
    private String flightNumber;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "status", length = 50)
    private String status = "SCHEDULED";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_departure_city", nullable = false)
    private City departureCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_arrival_city", nullable = false)
    private City arrivalCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_plane", nullable = false)
    private Plane plane;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeatPrice> seatPrices;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Offer> offers;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Flight() {}

    public Flight(String flightNumber, LocalDateTime departureTime, LocalDateTime arrivalTime,
                  City departureCity, City arrivalCity, Plane plane) {
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.plane = plane;
    }

    // Getters and Setters
    public Long getIdFlight() { return idFlight; }
    public void setIdFlight(Long idFlight) { this.idFlight = idFlight; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public City getDepartureCity() { return departureCity; }
    public void setDepartureCity(City departureCity) { this.departureCity = departureCity; }

    public City getArrivalCity() { return arrivalCity; }
    public void setArrivalCity(City arrivalCity) { this.arrivalCity = arrivalCity; }

    public Plane getPlane() { return plane; }
    public void setPlane(Plane plane) { this.plane = plane; }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    public List<SeatPrice> getSeatPrices() { return seatPrices; }
    public void setSeatPrices(List<SeatPrice> seatPrices) { this.seatPrices = seatPrices; }

    public List<Offer> getOffers() { return offers; }
    public void setOffers(List<Offer> offers) { this.offers = offers; }
}

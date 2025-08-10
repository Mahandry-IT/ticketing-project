package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_details")
public class ReservationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_details")
    private Long idDetails;

    @NotBlank
    @Column(name = "passenger_name", nullable = false)
    private String passengerName;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @NotBlank
    @Column(name = "passport", nullable = false)
    private String passport;

    @NotBlank
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @NotNull
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type", nullable = false)
    private SeatType seatType;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public ReservationDetails() {}

    public ReservationDetails(String passengerName, Integer age, String passport, 
                            String seatNumber, BigDecimal price, Reservation reservation, SeatType seatType) {
        this.passengerName = passengerName;
        this.age = age;
        this.passport = passport;
        this.seatNumber = seatNumber;
        this.price = price;
        this.reservation = reservation;
        this.seatType = seatType;
    }

    // Getters and Setters
    public Long getIdDetails() { return idDetails; }
    public void setIdDetails(Long idDetails) { this.idDetails = idDetails; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getPassport() { return passport; }
    public void setPassport(String passport) { this.passport = passport; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }

    public SeatType getSeatType() { return seatType; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }
}

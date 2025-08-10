package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_offer")
    private Long idOffer;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Column(name = "offer", nullable = false, precision = 5, scale = 2)
    private BigDecimal offer;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type", nullable = false)
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_flight", nullable = false)
    private Flight flight;

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
    public Offer() {}

    public Offer(Integer number, BigDecimal offer, SeatType seatType, Flight flight) {
        this.number = number;
        this.offer = offer;
        this.seatType = seatType;
        this.flight = flight;
    }

    // Getters and Setters
    public Long getIdOffer() { return idOffer; }
    public void setIdOffer(Long idOffer) { this.idOffer = idOffer; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public BigDecimal getOffer() { return offer; }
    public void setOffer(BigDecimal offer) { this.offer = offer; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public SeatType getSeatType() { return seatType; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }
}

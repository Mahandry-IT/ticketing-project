package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "seat_type")
public class SeatType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type")
    private Long idType;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "seatType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaneSeat> planeSeats;

    @OneToMany(mappedBy = "seatType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeatPrice> seatPrices;

    @OneToMany(mappedBy = "seatType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Offer> offers;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public SeatType() {}

    public SeatType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Long getIdType() { return idType; }
    public void setIdType(Long idType) { this.idType = idType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<PlaneSeat> getPlaneSeats() { return planeSeats; }
    public void setPlaneSeats(List<PlaneSeat> planeSeats) { this.planeSeats = planeSeats; }

    public List<SeatPrice> getSeatPrices() { return seatPrices; }
    public void setSeatPrices(List<SeatPrice> seatPrices) { this.seatPrices = seatPrices; }

    public List<Offer> getOffers() { return offers; }
    public void setOffers(List<Offer> offers) { this.offers = offers; }
}

package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "age_offer")
public class AgeOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_age")
    private Long idAge;

    @NotNull
    @Column(name = "offer", nullable = false, precision = 5, scale = 2)
    private BigDecimal offer;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category", nullable = false)
    private AgeCategory ageCategory;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public AgeOffer() {}

    public AgeOffer(BigDecimal offer, AgeCategory ageCategory) {
        this.offer = offer;
        this.ageCategory = ageCategory;
    }

    // Getters and Setters
    public Long getIdAge() { return idAge; }
    public void setIdAge(Long idAge) { this.idAge = idAge; }

    public BigDecimal getOffer() { return offer; }
    public void setOffer(BigDecimal offer) { this.offer = offer; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public AgeCategory getAgeCategory() { return ageCategory; }
    public void setAgeCategory(AgeCategory ageCategory) { this.ageCategory = ageCategory; }
}

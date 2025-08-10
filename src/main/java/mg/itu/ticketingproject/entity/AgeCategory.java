package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "age_category")
public class AgeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category")
    private Long idCategory;

    @NotNull
    @Column(name = "minimal", nullable = false)
    private Integer minimal;

    @NotNull
    @Column(name = "maximal", nullable = false)
    private Integer maximal;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "ageCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AgeOffer> ageOffers;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public AgeCategory() {}

    public AgeCategory(Integer minimal, Integer maximal, String name) {
        this.minimal = minimal;
        this.maximal = maximal;
        this.name = name;
    }

    // Getters and Setters
    public Long getIdCategory() { return idCategory; }
    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public Integer getMinimal() { return minimal; }
    public void setMinimal(Integer minimal) { this.minimal = minimal; }

    public Integer getMaximal() { return maximal; }
    public void setMaximal(Integer maximal) { this.maximal = maximal; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<AgeOffer> getAgeOffers() { return ageOffers; }
    public void setAgeOffers(List<AgeOffer> ageOffers) { this.ageOffers = ageOffers; }
}

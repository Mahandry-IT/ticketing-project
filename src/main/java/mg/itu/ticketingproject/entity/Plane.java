package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "plane")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plane")
    private Long idPlane;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "manufacturing_date", nullable = false)
    private LocalDate manufacturingDate;

    @Column(name = "registration_number", length = 50, unique = true)
    private String registrationNumber;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_model", nullable = false)
    private Model model;

    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> flights;

    @OneToMany(mappedBy = "plane", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlaneSeat> planeSeats;

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
    public Plane() {}

    public Plane(String name, LocalDate manufacturingDate, String registrationNumber, Model model) {
        this.name = name;
        this.manufacturingDate = manufacturingDate;
        this.registrationNumber = registrationNumber;
        this.model = model;
    }

    // Getters and Setters
    public Long getIdPlane() { return idPlane; }
    public void setIdPlane(Long idPlane) { this.idPlane = idPlane; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getManufacturingDate() { return manufacturingDate; }
    public void setManufacturingDate(LocalDate manufacturingDate) { this.manufacturingDate = manufacturingDate; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Model getModel() { return model; }
    public void setModel(Model model) { this.model = model; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }

    public List<PlaneSeat> getPlaneSeats() { return planeSeats; }
    public void setPlaneSeats(List<PlaneSeat> planeSeats) { this.planeSeats = planeSeats; }
}

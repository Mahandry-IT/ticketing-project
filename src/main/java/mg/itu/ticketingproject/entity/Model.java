package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_model")
    private Long idModel;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Plane> planes;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public Model() {}

    public Model(String name, String manufacturer, Integer capacity) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.capacity = capacity;
    }

    // Getters and Setters
    public Long getIdModel() { return idModel; }
    public void setIdModel(Long idModel) { this.idModel = idModel; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Plane> getPlanes() { return planes; }
    public void setPlanes(List<Plane> planes) { this.planes = planes; }
}

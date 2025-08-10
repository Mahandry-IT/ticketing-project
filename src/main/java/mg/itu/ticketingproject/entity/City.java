package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_city")
    private Long idCity;

    @NotBlank
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "departureCity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> departureFlights;

    @OneToMany(mappedBy = "arrivalCity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flight> arrivalFlights;

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
    public City() {}

    public City(String name, String country, String code) {
        this.name = name;
        this.country = country;
        this.code = code;
    }

    // Getters and Setters
    public Long getIdCity() { return idCity; }
    public void setIdCity(Long idCity) { this.idCity = idCity; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<Flight> getDepartureFlights() { return departureFlights; }
    public void setDepartureFlights(List<Flight> departureFlights) { this.departureFlights = departureFlights; }

    public List<Flight> getArrivalFlights() { return arrivalFlights; }
    public void setArrivalFlights(List<Flight> arrivalFlights) { this.arrivalFlights = arrivalFlights; }

    @Override
    public String toString() {
        return name + (country != null ? " (" + country + ")" : "");
    }
}

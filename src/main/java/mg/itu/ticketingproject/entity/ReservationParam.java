package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_param")
public class ReservationParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_parameter")
    private Long idParameter;

    @NotNull
    @Column(name = "cancel_time", nullable = false)
    private Integer cancelTime;

    @NotNull
    @Column(name = "reservation_time", nullable = false)
    private Integer reservationTime;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

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
    public ReservationParam() {}

    public ReservationParam(Integer cancelTime, Integer reservationTime) {
        this.cancelTime = cancelTime;
        this.reservationTime = reservationTime;
    }

    // Getters and Setters
    public Long getIdParameter() { return idParameter; }
    public void setIdParameter(Long idParameter) { this.idParameter = idParameter; }

    public Integer getCancelTime() { return cancelTime; }
    public void setCancelTime(Integer cancelTime) { this.cancelTime = cancelTime; }

    public Integer getReservationTime() { return reservationTime; }
    public void setReservationTime(Integer reservationTime) { this.reservationTime = reservationTime; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

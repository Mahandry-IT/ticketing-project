package mg.itu.ticketingproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "reservation_param")
public class ReservationParam {
    @Id
    @Column(name = "id_parameter", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "cancel_time", nullable = false)
    private Integer cancelTime;

    @NotNull
    @Column(name = "reservation_time", nullable = false)
    private Integer reservationTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Integer cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Integer getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Integer reservationTime) {
        this.reservationTime = reservationTime;
    }

}
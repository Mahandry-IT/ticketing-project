package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "reservation_details")
public class ReservationDetail {
    @Id
    @Column(name = "id_details", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "passenger_name")
    private String passengerName;

    @NotNull
    @Column(name = "age", nullable = false)
    private Integer age;

    @Size(max = 255)
    @NotNull
    @Column(name = "passport", nullable = false)
    private String passport;

    @NotNull
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_reservation", nullable = false)
    private Reservation idReservation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type", nullable = false)
    private SeatType idType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Reservation getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Reservation idReservation) {
        this.idReservation = idReservation;
    }

    public SeatType getIdType() {
        return idType;
    }

    public void setIdType(SeatType idType) {
        this.idType = idType;
    }

}
package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "plane_seat")
public class PlaneSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_number")
    private Long idNumber;

    @NotBlank
    @Column(name = "seat_number", nullable = false, length = 10)
    private String seatNumber;

    @NotNull
    @Column(name = "row_number", nullable = false)
    private Integer rowNumber;

    @NotBlank
    @Column(name = "seat_letter", nullable = false, length = 2)
    private String seatLetter;

    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_plane", nullable = false)
    private Plane plane;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type", nullable = false)
    private SeatType seatType;

    // Constructors
    public PlaneSeat() {}

    public PlaneSeat(String seatNumber, Integer rowNumber, String seatLetter, Plane plane, SeatType seatType) {
        this.seatNumber = seatNumber;
        this.rowNumber = rowNumber;
        this.seatLetter = seatLetter;
        this.plane = plane;
        this.seatType = seatType;
    }

    // Getters and Setters
    public Long getIdNumber() { return idNumber; }
    public void setIdNumber(Long idNumber) { this.idNumber = idNumber; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Integer getRowNumber() { return rowNumber; }
    public void setRowNumber(Integer rowNumber) { this.rowNumber = rowNumber; }

    public String getSeatLetter() { return seatLetter; }
    public void setSeatLetter(String seatLetter) { this.seatLetter = seatLetter; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public Plane getPlane() { return plane; }
    public void setPlane(Plane plane) { this.plane = plane; }

    public SeatType getSeatType() { return seatType; }
    public void setSeatType(SeatType seatType) { this.seatType = seatType; }
}

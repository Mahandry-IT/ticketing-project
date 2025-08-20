package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "promotion_alea")
public class PromotionAlea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_promotion", nullable = false)
    private Integer id;

    @Column(name = "number")
    private Integer number;

    @NotNull
    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @NotNull
    @Column(name = "final_date", nullable = false)
    private LocalDate finalDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_flight", nullable = false)
    private Flight idFlight;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_type", nullable = false)
    private SeatType idType;

}
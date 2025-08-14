package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "age_offer")
public class AgeOffer {
    @Id
    @Column(name = "id_age", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "offer", nullable = false, precision = 5, scale = 2)
    private BigDecimal offer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    private AgeCategory category;

}
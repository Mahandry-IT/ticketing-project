package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
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
    private AgeCategory idCategory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getOffer() {
        return offer;
    }

    public void setOffer(BigDecimal offer) {
        this.offer = offer;
    }

    public AgeCategory getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(AgeCategory idCategory) {
        this.idCategory = idCategory;
    }

}
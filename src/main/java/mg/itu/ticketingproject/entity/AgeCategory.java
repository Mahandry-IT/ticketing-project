package mg.itu.ticketingproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "age_category")
@Data
public class AgeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "minimal", nullable = false)
    private Integer minimal;

    @NotNull
    @Column(name = "maximal", nullable = false)
    private Integer maximal;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
}
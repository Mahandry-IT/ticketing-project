package mg.itu.ticketingproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "age_category")
public class AgeCategory {
    @Id
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMinimal() {
        return minimal;
    }

    public void setMinimal(Integer minimal) {
        this.minimal = minimal;
    }

    public Integer getMaximal() {
        return maximal;
    }

    public void setMaximal(Integer maximal) {
        this.maximal = maximal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
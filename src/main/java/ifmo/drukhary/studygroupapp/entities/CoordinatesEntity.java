package ifmo.drukhary.studygroupapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "coordinates")
public class CoordinatesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic
    @Column(name = "x", nullable = true)
    private Float x;
    @Basic
    @Column(name = "y", nullable = false)
    private Float y;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinatesEntity that = (CoordinatesEntity) o;
        return Double.compare(y, that.y) == 0 && Objects.equals(x, that.x) && (long) id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }
}

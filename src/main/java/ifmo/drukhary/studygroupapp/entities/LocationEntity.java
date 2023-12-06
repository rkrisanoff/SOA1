package ifmo.drukhary.studygroupapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
public class LocationEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "x", nullable = true, precision = 0)
    private Double x;

    @Basic
    @Column(name = "y", nullable = false)

    private Long y;

    @Basic
    @Column(name = "z", nullable = false, precision = 0)

    private Double z;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationEntity that = (LocationEntity) o;
        return Objects.equals(y, that.y) && Double.compare(z, that.z) == 0 && Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y, z);
    }
}

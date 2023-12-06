package ifmo.drukhary.studygroupapp.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class Coordinates {
    @NotNull(message = "x must be not null")
    private BigDecimal x;
    @NotNull(message="y must be not null")
    private BigDecimal y; //Поле не может быть null
}

package ifmo.drukhary.studygroupapp.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class Location {
    @NotNull(message = "x must be not null")
    @Min(value = 0, message = "y must be greater than 0")
    @Max(value = 9223372036854775807L, message = "y must be less than 9223372036854775807")
    private BigDecimal x;
    @NotNull(message = "y must be not null")
    @Min(value = 0, message = "y must be greater than 0")
    @Max(value = 9223372036854775807L, message = "y must be less than 9223372036854775807")
    private BigInteger y; //Поле не может быть null
    @NotNull(message = "z must be not null")
    private BigDecimal z; //Поле не может быть null
}

package ifmo.drukhary.studygroupapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WrongFilterException extends Exception{
    final String value;
}

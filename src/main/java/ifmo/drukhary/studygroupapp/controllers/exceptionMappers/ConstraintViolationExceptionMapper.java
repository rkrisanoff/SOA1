package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice

public class ConstraintViolationExceptionMapper {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorData> constraintViolationExceptionHandler(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(new ErrorData(errors.stream().reduce("Errors: ", (acc, error) -> acc + " - " + error + "\n")));
    }
}
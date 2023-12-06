package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class ConstraintViolationExceptionMapper {

    @ExceptionHandler({ConstraintViolationException.class})
    public Response constraintViolationExceptionHandler(ConstraintViolationException e) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorData(errors.stream().reduce("Errors: ", (acc, error) -> acc + " - " + error + "\n")))
                .build();
    }
}
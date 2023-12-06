package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class NotFoundExceptionMapper {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorData> notFoundExceptionHandler(NotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorData("Not Found"));
    }
}

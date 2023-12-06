package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class InternalServerErrorExceptionMapper {
    @ExceptionHandler({InternalServerErrorException.class})
    public ResponseEntity<ErrorData> internalServerErrorExceptionHandler(InternalServerErrorException arg) {
        return ResponseEntity.status(BAD_REQUEST).body(
                new ErrorData("Сервер недоступен"));
    }
}

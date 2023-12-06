package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class UnexpectedExceptionMapper {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorData> toResponse(Exception exception) {
        System.err.println(exception.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(
                new ErrorData("Сервер недоступен."));
    }
}

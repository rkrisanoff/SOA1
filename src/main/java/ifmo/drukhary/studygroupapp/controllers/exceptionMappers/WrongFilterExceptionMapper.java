package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import ifmo.drukhary.studygroupapp.exceptions.WrongFilterException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ifmo.drukhary.studygroupapp.utils.Constants.STUDY_GROUP_VALIDATION_EXPRESSION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class WrongFilterExceptionMapper {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ErrorData> NotFoundExceptionHandler(WrongFilterException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorData("`" + e.getValue() + "`" + " must be match pattern " + "/" + STUDY_GROUP_VALIDATION_EXPRESSION + "/"));
    }
}

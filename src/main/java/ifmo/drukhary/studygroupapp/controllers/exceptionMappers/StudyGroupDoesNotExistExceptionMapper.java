package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import ifmo.drukhary.studygroupapp.exceptions.StudyGroupDoesNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

@ControllerAdvice
public class StudyGroupDoesNotExistExceptionMapper {
    @ExceptionHandler({StudyGroupDoesNotExistException.class})
    public ResponseEntity<ErrorData> toResponse(StudyGroupDoesNotExistException ex) {
        return ResponseEntity.status(HTTP_NOT_FOUND).body(new ErrorData("Not Found"));
    }
}
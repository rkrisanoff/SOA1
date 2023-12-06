package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import ifmo.drukhary.studygroupapp.exceptions.StudyGroupDoesNotExistException;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class StudyGroupDoesNotExistExceptionMapper {
    @ExceptionHandler({StudyGroupDoesNotExistException.class})
    public Response toResponse(StudyGroupDoesNotExistException ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorData("Not Found")).build();
    }
}
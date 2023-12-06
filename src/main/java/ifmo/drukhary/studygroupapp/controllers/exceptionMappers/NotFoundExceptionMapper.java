package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

public class NotFoundExceptionMapper {
    @ExceptionHandler({NotFoundException.class})
    public Response notFoundExceptionHandler(NotFoundException ex) {
        return Response.status(NOT_FOUND).entity(new ErrorData("Not Found")).build();
    }
}

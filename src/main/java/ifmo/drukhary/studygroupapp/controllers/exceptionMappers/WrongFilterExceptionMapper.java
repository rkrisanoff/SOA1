package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;

import ifmo.drukhary.studygroupapp.DTO.ErrorData;
import ifmo.drukhary.studygroupapp.exceptions.WrongFilterException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ifmo.drukhary.studygroupapp.utils.Constants.STUDY_GROUP_VALIDATION_EXPRESSION;

public class WrongFilterExceptionMapper {
    @ExceptionHandler({NotFoundException.class})
    public Response NotFoundExceptionHandler(WrongFilterException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorData("`" + e.getValue() + "`" + " must be match pattern " + "/" + STUDY_GROUP_VALIDATION_EXPRESSION + "/")).build();
    }
}

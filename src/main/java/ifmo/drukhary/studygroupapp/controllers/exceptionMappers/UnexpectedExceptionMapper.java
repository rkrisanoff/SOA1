//package ifmo.drukhary.studygroupapp.controllers.exceptionMappers;
//
//import ifmo.drukhary.studygroupapp.DTO.ErrorData;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.ExceptionMapper;
//import jakarta.ws.rs.ext.Provider;
//
//@Provider
//public class UnexpectedExceptionMapper implements ExceptionMapper<Exception> {
//
//    @Override
//    public Response toResponse(Exception exception) {
//        System.err.println(exception.getMessage());
//        return Response.status(Response.Status.BAD_REQUEST).entity(
//                        new ErrorData("Сервер недоступен."))
//                .build();
//    }
//}

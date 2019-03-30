package fr.vinze.textanalysis.ws.exception;

import javax.ws.rs.core.Response;

public class ExceptionToResponse {


    public static Response exceptionToResponse(WsException exception) {
        // TODO
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}

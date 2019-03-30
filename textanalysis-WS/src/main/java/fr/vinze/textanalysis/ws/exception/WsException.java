package fr.vinze.textanalysis.ws.exception;

import javax.ws.rs.core.Response;

public class WsException extends Exception {

    private static final long serialVersionUID = 3749978008440521692L;
    private final Response.Status status;

    public WsException(Response.Status status) {
        this.status = status;
    }

    public WsException(Response.Status status, String message) {
        super(message);
        this.status = status;
    }

    public WsException(Response.Status status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public WsException(Response.Status status, Throwable cause) {
        super(cause);
        this.status = status;
    }

}

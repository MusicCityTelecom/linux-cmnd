/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Response;

public class InternalServerErrorException
extends ServerErrorException {
    private static final long serialVersionUID = -6515710697540553309L;

    public InternalServerErrorException() {
        super(Response.Status.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message) {
        super(message, Response.Status.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(Response response) {
        super(InternalServerErrorException.validate(response, Response.Status.INTERNAL_SERVER_ERROR));
    }

    public InternalServerErrorException(String message, Response response) {
        super(message, InternalServerErrorException.validate(response, Response.Status.INTERNAL_SERVER_ERROR));
    }

    public InternalServerErrorException(Throwable cause) {
        super(Response.Status.INTERNAL_SERVER_ERROR, cause);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, Response.Status.INTERNAL_SERVER_ERROR, cause);
    }

    public InternalServerErrorException(Response response, Throwable cause) {
        super(InternalServerErrorException.validate(response, Response.Status.INTERNAL_SERVER_ERROR), cause);
    }

    public InternalServerErrorException(String message, Response response, Throwable cause) {
        super(message, InternalServerErrorException.validate(response, Response.Status.INTERNAL_SERVER_ERROR), cause);
    }
}


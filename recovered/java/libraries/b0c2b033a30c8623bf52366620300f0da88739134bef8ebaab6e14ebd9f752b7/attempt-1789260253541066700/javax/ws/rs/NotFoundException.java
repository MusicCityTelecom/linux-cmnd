/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class NotFoundException
extends ClientErrorException {
    private static final long serialVersionUID = -6820866117511628388L;

    public NotFoundException() {
        super(Response.Status.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }

    public NotFoundException(Response response) {
        super(NotFoundException.validate(response, Response.Status.NOT_FOUND));
    }

    public NotFoundException(String message, Response response) {
        super(message, NotFoundException.validate(response, Response.Status.NOT_FOUND));
    }

    public NotFoundException(Throwable cause) {
        super(Response.Status.NOT_FOUND, cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, Response.Status.NOT_FOUND, cause);
    }

    public NotFoundException(Response response, Throwable cause) {
        super(NotFoundException.validate(response, Response.Status.NOT_FOUND), cause);
    }

    public NotFoundException(String message, Response response, Throwable cause) {
        super(message, NotFoundException.validate(response, Response.Status.NOT_FOUND), cause);
    }
}


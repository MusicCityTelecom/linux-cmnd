/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class NotAcceptableException
extends ClientErrorException {
    private static final long serialVersionUID = -1476163816796529078L;

    public NotAcceptableException() {
        super(Response.Status.NOT_ACCEPTABLE);
    }

    public NotAcceptableException(String message) {
        super(message, Response.Status.NOT_ACCEPTABLE);
    }

    public NotAcceptableException(Response response) {
        super(NotAcceptableException.validate(response, Response.Status.NOT_ACCEPTABLE));
    }

    public NotAcceptableException(String message, Response response) {
        super(message, NotAcceptableException.validate(response, Response.Status.NOT_ACCEPTABLE));
    }

    public NotAcceptableException(Throwable cause) {
        super(Response.Status.NOT_ACCEPTABLE, cause);
    }

    public NotAcceptableException(String message, Throwable cause) {
        super(message, Response.Status.NOT_ACCEPTABLE, cause);
    }

    public NotAcceptableException(Response response, Throwable cause) {
        super(NotAcceptableException.validate(response, Response.Status.NOT_ACCEPTABLE), cause);
    }

    public NotAcceptableException(String message, Response response, Throwable cause) {
        super(message, NotAcceptableException.validate(response, Response.Status.NOT_ACCEPTABLE), cause);
    }
}


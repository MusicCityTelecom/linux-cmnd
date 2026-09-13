/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class NotAllowedException
extends ClientErrorException {
    private static final long serialVersionUID = -586776054369626119L;

    public NotAllowedException(String allowed, String ... moreAllowed) {
        super(NotAllowedException.validateAllow(NotAllowedException.createNotAllowedResponse(allowed, moreAllowed)));
    }

    public NotAllowedException(String message, String allowed, String ... moreAllowed) {
        super(message, NotAllowedException.validateAllow(NotAllowedException.createNotAllowedResponse(allowed, moreAllowed)));
    }

    private static Response createNotAllowedResponse(String allowed, String ... moreAllowed) {
        Set<String> methods;
        if (allowed == null) {
            throw new NullPointerException("No allowed method specified.");
        }
        if (moreAllowed != null && moreAllowed.length > 0) {
            methods = new HashSet<String>(moreAllowed.length + 1);
            methods.add(allowed);
            Collections.addAll(methods, moreAllowed);
        } else {
            methods = Collections.singleton(allowed);
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).allow(methods).build();
    }

    public NotAllowedException(Response response) {
        super(NotAllowedException.validate(response, Response.Status.METHOD_NOT_ALLOWED));
    }

    public NotAllowedException(String message, Response response) {
        super(message, NotAllowedException.validate(response, Response.Status.METHOD_NOT_ALLOWED));
    }

    public NotAllowedException(Throwable cause, String ... allowedMethods) {
        super(NotAllowedException.validateAllow(Response.status(Response.Status.METHOD_NOT_ALLOWED).allow(allowedMethods).build()), cause);
    }

    public NotAllowedException(String message, Throwable cause, String ... allowedMethods) {
        super(message, NotAllowedException.validateAllow(Response.status(Response.Status.METHOD_NOT_ALLOWED).allow(allowedMethods).build()), cause);
    }

    public NotAllowedException(Response response, Throwable cause) {
        super(NotAllowedException.validateAllow(NotAllowedException.validate(response, Response.Status.METHOD_NOT_ALLOWED)), cause);
    }

    public NotAllowedException(String message, Response response, Throwable cause) {
        super(message, NotAllowedException.validateAllow(NotAllowedException.validate(response, Response.Status.METHOD_NOT_ALLOWED)), cause);
    }

    private static Response validateAllow(Response response) {
        if (!response.getHeaders().containsKey("Allow")) {
            throw new IllegalArgumentException("Response does not contain required 'Allow' HTTP header.");
        }
        return response;
    }
}


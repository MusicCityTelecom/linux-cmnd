/*
 * Decompiled with CFR 0.152.
 */
package javax.ws.rs;

import javax.ws.rs.core.Response;

public class WebApplicationException
extends RuntimeException {
    private static final long serialVersionUID = 8273970399584007146L;
    private final Response response;

    public WebApplicationException() {
        this((Throwable)null, Response.Status.INTERNAL_SERVER_ERROR);
    }

    public WebApplicationException(String message) {
        this(message, null, Response.Status.INTERNAL_SERVER_ERROR);
    }

    public WebApplicationException(Response response) {
        this((Throwable)null, response);
    }

    public WebApplicationException(String message, Response response) {
        this(message, null, response);
    }

    public WebApplicationException(int status) {
        this((Throwable)null, status);
    }

    public WebApplicationException(String message, int status) {
        this(message, null, status);
    }

    public WebApplicationException(Response.Status status) {
        this((Throwable)null, status);
    }

    public WebApplicationException(String message, Response.Status status) {
        this(message, null, status);
    }

    public WebApplicationException(Throwable cause) {
        this(cause, Response.Status.INTERNAL_SERVER_ERROR);
    }

    public WebApplicationException(String message, Throwable cause) {
        this(message, cause, Response.Status.INTERNAL_SERVER_ERROR);
    }

    public WebApplicationException(Throwable cause, Response response) {
        this(WebApplicationException.computeExceptionMessage(response), cause, response);
    }

    public WebApplicationException(String message, Throwable cause, Response response) {
        super(message, cause);
        this.response = response == null ? Response.serverError().build() : response;
    }

    private static String computeExceptionMessage(Response response) {
        Response.StatusType statusInfo = response != null ? response.getStatusInfo() : Response.Status.INTERNAL_SERVER_ERROR;
        return "HTTP " + statusInfo.getStatusCode() + ' ' + statusInfo.getReasonPhrase();
    }

    public WebApplicationException(Throwable cause, int status) {
        this(cause, Response.status(status).build());
    }

    public WebApplicationException(String message, Throwable cause, int status) {
        this(message, cause, Response.status(status).build());
    }

    public WebApplicationException(Throwable cause, Response.Status status) throws IllegalArgumentException {
        this(cause, Response.status(status).build());
    }

    public WebApplicationException(String message, Throwable cause, Response.Status status) throws IllegalArgumentException {
        this(message, cause, Response.status(status).build());
    }

    public Response getResponse() {
        return this.response;
    }

    static Response validate(Response response, Response.Status expectedStatus) {
        if (expectedStatus.getStatusCode() != response.getStatus()) {
            throw new IllegalArgumentException(String.format("Invalid response status code. Expected [%d], was [%d].", expectedStatus.getStatusCode(), response.getStatus()));
        }
        return response;
    }

    static Response validate(Response response, Response.Status.Family expectedStatusFamily) {
        if (response.getStatusInfo().getFamily() != expectedStatusFamily) {
            throw new IllegalArgumentException(String.format("Status code of the supplied response [%d] is not from the required status code family \"%s\".", new Object[]{response.getStatus(), expectedStatusFamily}));
        }
        return response;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.MimeType
 */
package org.springframework.boot.actuate.endpoint.web;

import org.springframework.boot.actuate.endpoint.Producible;
import org.springframework.util.MimeType;

public final class WebEndpointResponse<T> {
    public static final int STATUS_OK = 200;
    public static final int STATUS_NO_CONTENT = 204;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_TOO_MANY_REQUESTS = 429;
    public static final int STATUS_INTERNAL_SERVER_ERROR = 500;
    public static final int STATUS_SERVICE_UNAVAILABLE = 503;
    private final T body;
    private final int status;
    private final MimeType contentType;

    public WebEndpointResponse() {
        this(null);
    }

    public WebEndpointResponse(int status) {
        this(null, status);
    }

    public WebEndpointResponse(T body) {
        this(body, 200);
    }

    public WebEndpointResponse(T body, Producible<?> producible) {
        this(body, 200, producible.getProducedMimeType());
    }

    public WebEndpointResponse(T body, MimeType contentType) {
        this(body, 200, contentType);
    }

    public WebEndpointResponse(T body, int status) {
        this(body, status, null);
    }

    public WebEndpointResponse(T body, int status, MimeType contentType) {
        this.body = body;
        this.status = status;
        this.contentType = contentType;
    }

    public MimeType getContentType() {
        return this.contentType;
    }

    public T getBody() {
        return this.body;
    }

    public int getStatus() {
        return this.status;
    }
}


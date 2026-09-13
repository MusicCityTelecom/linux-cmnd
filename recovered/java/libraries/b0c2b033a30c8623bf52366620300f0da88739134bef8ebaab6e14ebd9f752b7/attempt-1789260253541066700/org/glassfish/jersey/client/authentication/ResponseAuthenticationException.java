/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.authentication;

import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;

public class ResponseAuthenticationException
extends ResponseProcessingException {
    public ResponseAuthenticationException(Response response, Throwable cause) {
        super(response, cause);
    }

    public ResponseAuthenticationException(Response response, String message) {
        super(response, message);
    }

    public ResponseAuthenticationException(Response response, String message, Throwable cause) {
        super(response, message, cause);
    }
}


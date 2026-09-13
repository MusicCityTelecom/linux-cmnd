/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.authentication;

import javax.ws.rs.ProcessingException;

public class RequestAuthenticationException
extends ProcessingException {
    public RequestAuthenticationException(Throwable cause) {
        super(cause);
    }

    public RequestAuthenticationException(String message) {
        super(message);
    }

    public RequestAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}


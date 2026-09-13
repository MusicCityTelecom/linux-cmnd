/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import javax.ws.rs.ProcessingException;

public class MessageBodyProviderNotFoundException
extends ProcessingException {
    private static final long serialVersionUID = 2093175681702118380L;

    public MessageBodyProviderNotFoundException(Throwable cause) {
        super(cause);
    }

    public MessageBodyProviderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageBodyProviderNotFoundException(String message) {
        super(message);
    }
}


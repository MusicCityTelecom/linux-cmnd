/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import javax.ws.rs.ProcessingException;

public class MessageBodyProcessingException
extends ProcessingException {
    private static final long serialVersionUID = 2093175681702118380L;

    public MessageBodyProcessingException(Throwable cause) {
        super(cause);
    }

    public MessageBodyProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageBodyProcessingException(String message) {
        super(message);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import javax.ws.rs.ProcessingException;

public class HeaderValueException
extends ProcessingException {
    private static final long serialVersionUID = 981810773601231157L;
    private final Context context;

    public HeaderValueException(String message, Throwable cause, Context context) {
        super(message, cause);
        this.context = context;
    }

    public HeaderValueException(String message, Context context) {
        super(message);
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public static enum Context {
        INBOUND,
        OUTBOUND;

    }
}


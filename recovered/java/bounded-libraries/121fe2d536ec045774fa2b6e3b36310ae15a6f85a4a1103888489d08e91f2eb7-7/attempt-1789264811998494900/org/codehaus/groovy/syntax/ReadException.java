/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.syntax;

import java.io.IOException;
import org.codehaus.groovy.GroovyException;

public class ReadException
extends GroovyException {
    private static final long serialVersionUID = 848585058428047961L;
    private final IOException cause;

    public ReadException(IOException cause) {
        this.cause = cause;
    }

    public ReadException(String message, IOException cause) {
        super(message);
        this.cause = cause;
    }

    public IOException getIOCause() {
        return this.cause;
    }

    @Override
    public String toString() {
        String message = super.getMessage();
        if (message == null || message.trim().isEmpty()) {
            message = this.cause.getMessage();
        }
        return message;
    }

    @Override
    public String getMessage() {
        return this.toString();
    }
}


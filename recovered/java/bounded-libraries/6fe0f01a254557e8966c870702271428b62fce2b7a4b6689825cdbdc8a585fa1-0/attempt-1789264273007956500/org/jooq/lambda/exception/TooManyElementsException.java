/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda.exception;

public class TooManyElementsException
extends RuntimeException {
    private static final long serialVersionUID = 491834858363345767L;

    public TooManyElementsException() {
    }

    public TooManyElementsException(String message) {
        super(message);
    }

    public TooManyElementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooManyElementsException(Throwable cause) {
        super(cause);
    }
}


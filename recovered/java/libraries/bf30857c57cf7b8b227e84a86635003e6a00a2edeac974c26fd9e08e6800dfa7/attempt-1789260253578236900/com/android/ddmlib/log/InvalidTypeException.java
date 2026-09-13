/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.log;

public final class InvalidTypeException
extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidTypeException() {
        super("Invalid Type");
    }

    public InvalidTypeException(String message) {
        super(message);
    }

    public InvalidTypeException(Throwable cause) {
        super(cause);
    }

    public InvalidTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}


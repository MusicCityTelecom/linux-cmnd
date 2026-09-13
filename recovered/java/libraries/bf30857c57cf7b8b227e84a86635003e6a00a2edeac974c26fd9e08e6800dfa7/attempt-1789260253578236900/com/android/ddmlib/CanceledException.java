/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

public abstract class CanceledException
extends Exception {
    private static final long serialVersionUID = 1L;

    CanceledException(String message) {
        super(message);
    }

    CanceledException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract boolean wasCanceled();
}


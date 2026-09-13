/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

public class TimeoutException
extends Exception {
    private static final long serialVersionUID = 1L;

    public TimeoutException() {
    }

    public TimeoutException(String s3) {
        super(s3);
    }

    public TimeoutException(String s3, Throwable throwable) {
        super(s3, throwable);
    }

    public TimeoutException(Throwable throwable) {
        super(throwable);
    }
}


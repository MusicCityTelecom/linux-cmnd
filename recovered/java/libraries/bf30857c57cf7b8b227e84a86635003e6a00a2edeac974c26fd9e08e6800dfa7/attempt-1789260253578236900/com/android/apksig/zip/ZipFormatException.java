/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.zip;

public class ZipFormatException
extends Exception {
    private static final long serialVersionUID = 1L;

    public ZipFormatException(String message) {
        super(message);
    }

    public ZipFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}


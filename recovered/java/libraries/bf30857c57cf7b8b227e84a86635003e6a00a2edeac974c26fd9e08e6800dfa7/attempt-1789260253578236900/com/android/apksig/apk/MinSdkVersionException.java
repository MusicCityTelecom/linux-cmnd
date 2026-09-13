/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.apk;

import com.android.apksig.apk.ApkFormatException;

public class MinSdkVersionException
extends ApkFormatException {
    private static final long serialVersionUID = 1L;

    public MinSdkVersionException(String message) {
        super(message);
    }

    public MinSdkVersionException(String message, Throwable cause) {
        super(message, cause);
    }
}


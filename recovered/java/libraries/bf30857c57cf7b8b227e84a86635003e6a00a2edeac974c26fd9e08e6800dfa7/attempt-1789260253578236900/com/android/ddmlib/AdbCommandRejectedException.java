/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

public class AdbCommandRejectedException
extends Exception {
    private static final long serialVersionUID = 1L;
    private final boolean mIsDeviceOffline;
    private final boolean mErrorDuringDeviceSelection;

    AdbCommandRejectedException(String message) {
        super(message);
        this.mIsDeviceOffline = "device offline".equals(message);
        this.mErrorDuringDeviceSelection = false;
    }

    AdbCommandRejectedException(String message, boolean errorDuringDeviceSelection) {
        super(message);
        this.mErrorDuringDeviceSelection = errorDuringDeviceSelection;
        this.mIsDeviceOffline = "device offline".equals(message);
    }

    public boolean isDeviceOffline() {
        return this.mIsDeviceOffline;
    }

    public boolean wasErrorDuringDeviceSelection() {
        return this.mErrorDuringDeviceSelection;
    }
}


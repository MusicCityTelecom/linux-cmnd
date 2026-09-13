/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

public interface SocketClientCallback {
    public void onError(Throwable var1);

    public void onConnected();

    public void onDisconnected();

    public void onReceived(byte[] var1);

    public void onSent(byte[] var1);
}


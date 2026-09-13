/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

public interface IShellOutputReceiver {
    public void addOutput(byte[] var1, int var2, int var3);

    public void flush();

    public boolean isCancelled();
}


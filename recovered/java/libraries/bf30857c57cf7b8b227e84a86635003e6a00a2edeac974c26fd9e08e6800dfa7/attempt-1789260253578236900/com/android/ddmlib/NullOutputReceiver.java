/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IShellOutputReceiver;

public final class NullOutputReceiver
implements IShellOutputReceiver {
    private static NullOutputReceiver sReceiver = new NullOutputReceiver();

    public static IShellOutputReceiver getReceiver() {
        return sReceiver;
    }

    @Override
    public void addOutput(byte[] data, int offset, int length) {
    }

    @Override
    public void flush() {
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}


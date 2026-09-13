/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import java.io.IOException;
import java.io.InputStream;

class LimitedInputStream
extends InputStream {
    private final InputStream input;
    private long remaining;
    private boolean eofDetected;

    LimitedInputStream(InputStream input, long maximum) {
        this.input = input;
        this.remaining = maximum;
        this.eofDetected = false;
    }

    @Override
    public int read() throws IOException {
        if (this.remaining == 0L) {
            return -1;
        }
        int r3 = this.input.read();
        if (r3 >= 0) {
            --this.remaining;
        } else {
            this.eofDetected = true;
        }
        return r3;
    }

    @Override
    public int read(byte[] whereTo, int offset, int length) throws IOException {
        if (this.remaining == 0L) {
            return -1;
        }
        int toRead = (int)Math.min(this.remaining, (long)length);
        int r3 = this.input.read(whereTo, offset, toRead);
        if (r3 >= 0) {
            this.remaining -= (long)r3;
        } else {
            this.eofDetected = true;
        }
        return r3;
    }

    boolean isInputFinished() {
        return this.eofDetected;
    }
}


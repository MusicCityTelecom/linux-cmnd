/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.google.common.annotations.VisibleForTesting;
import java.io.IOException;
import java.io.InputStream;

class SwitchableDelegateInputStream
extends InputStream {
    private InputStream delegate;
    private long currentOffset;
    @VisibleForTesting
    boolean endOfStreamReached;
    private long needsSkipping;

    SwitchableDelegateInputStream(InputStream delegate) {
        this.delegate = delegate;
        this.currentOffset = 0L;
        this.endOfStreamReached = false;
        this.needsSkipping = 0L;
    }

    private void skipDataIfNeeded() throws IOException {
        while (this.needsSkipping > 0L) {
            long skipped = this.delegate.skip(this.needsSkipping);
            if (skipped == 0L) {
                throw new IOException("Skipping InputStream after switching failed");
            }
            this.needsSkipping -= skipped;
        }
    }

    private int increaseOffset(int amount) {
        return (int)this.increaseOffset((long)amount);
    }

    private long increaseOffset(long amount) {
        if (amount > 0L) {
            this.currentOffset += amount;
        }
        if (amount == -1L) {
            this.endOfStreamReached = true;
        }
        return amount;
    }

    @Override
    public synchronized int read(byte[] b2) throws IOException {
        if (this.endOfStreamReached) {
            return -1;
        }
        this.skipDataIfNeeded();
        return this.increaseOffset(this.delegate.read(b2));
    }

    @Override
    public synchronized int read(byte[] b2, int off, int len) throws IOException {
        if (this.endOfStreamReached) {
            return -1;
        }
        this.skipDataIfNeeded();
        return this.increaseOffset(this.delegate.read(b2, off, len));
    }

    @Override
    public synchronized int read() throws IOException {
        if (this.endOfStreamReached) {
            return -1;
        }
        this.skipDataIfNeeded();
        int r3 = this.delegate.read();
        if (r3 == -1) {
            this.endOfStreamReached = true;
        } else {
            this.increaseOffset(1);
        }
        return r3;
    }

    @Override
    public synchronized long skip(long n2) throws IOException {
        if (this.endOfStreamReached) {
            return 0L;
        }
        this.skipDataIfNeeded();
        return this.increaseOffset(this.delegate.skip(n2));
    }

    @Override
    public synchronized int available() throws IOException {
        if (this.endOfStreamReached) {
            return 0;
        }
        this.skipDataIfNeeded();
        return this.delegate.available();
    }

    @Override
    public synchronized void close() throws IOException {
        this.endOfStreamReached = true;
        this.delegate.close();
    }

    @Override
    public void mark(int readlimit) {
    }

    @Override
    public void reset() throws IOException {
        throw new IOException("Mark not supported");
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    synchronized void switchStream(InputStream newStream) throws IOException {
        if (newStream == this.delegate) {
            return;
        }
        try (InputStream oldDelegate = this.delegate;){
            this.delegate = newStream;
            this.needsSkipping = this.currentOffset;
        }
    }
}


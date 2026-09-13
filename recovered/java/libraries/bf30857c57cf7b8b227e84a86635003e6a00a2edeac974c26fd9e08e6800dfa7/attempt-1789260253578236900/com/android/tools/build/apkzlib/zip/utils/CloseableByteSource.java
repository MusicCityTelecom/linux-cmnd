/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.utils;

import com.google.common.io.ByteSource;
import java.io.Closeable;
import java.io.IOException;

public abstract class CloseableByteSource
extends ByteSource
implements Closeable {
    private boolean closed = false;

    @Override
    public final synchronized void close() throws IOException {
        if (this.closed) {
            return;
        }
        try {
            this.innerClose();
        }
        finally {
            this.closed = true;
        }
    }

    protected abstract void innerClose() throws IOException;
}


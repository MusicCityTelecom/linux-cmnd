/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.CloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.base.Preconditions;
import java.io.IOException;

abstract class AbstractCloseableByteSourceFromOutputStreamBuilder
extends CloseableByteSourceFromOutputStreamBuilder {
    private final byte[] tempByte = new byte[1];
    private boolean closed = false;
    private boolean built = false;

    AbstractCloseableByteSourceFromOutputStreamBuilder() {
    }

    @Override
    public void write(byte[] b2, int off, int len) throws IOException {
        Preconditions.checkState(!this.closed);
        this.doWrite(b2, off, len);
    }

    @Override
    public void write(int b2) throws IOException {
        this.tempByte[0] = (byte)b2;
        this.write(this.tempByte, 0, 1);
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }

    @Override
    public CloseableByteSource build() throws IOException {
        Preconditions.checkState(!this.built);
        this.closed = true;
        this.built = true;
        return this.doBuild();
    }

    protected abstract void doWrite(byte[] var1, int var2, int var3) throws IOException;

    protected abstract CloseableByteSource doBuild() throws IOException;
}


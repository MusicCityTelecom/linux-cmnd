/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.AbstractCloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.CloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.android.tools.build.apkzlib.zip.utils.CloseableDelegateByteSource;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InMemoryByteStorage
implements ByteStorage {
    private long bytesUsed;
    private long maxBytesUsed;

    @Override
    public CloseableByteSource fromStream(InputStream stream) throws IOException {
        byte[] data = ByteStreams.toByteArray(stream);
        this.updateUsage(data.length);
        return new CloseableDelegateByteSource(ByteSource.wrap(data), data.length){

            @Override
            public synchronized void innerClose() throws IOException {
                super.innerClose();
                InMemoryByteStorage.this.updateUsage(-this.sizeNoException());
            }
        };
    }

    @Override
    public CloseableByteSourceFromOutputStreamBuilder makeBuilder() throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        return new AbstractCloseableByteSourceFromOutputStreamBuilder(){

            @Override
            protected void doWrite(byte[] b2, int off, int len) throws IOException {
                output.write(b2, off, len);
                InMemoryByteStorage.this.updateUsage(len);
            }

            @Override
            protected CloseableByteSource doBuild() throws IOException {
                final byte[] data = output.toByteArray();
                return new CloseableDelegateByteSource(ByteSource.wrap(data), data.length){

                    @Override
                    protected synchronized void innerClose() throws IOException {
                        super.innerClose();
                        InMemoryByteStorage.this.updateUsage(-data.length);
                    }
                };
            }
        };
    }

    @Override
    public CloseableByteSource fromSource(ByteSource source) throws IOException {
        return this.fromStream(source.openStream());
    }

    private synchronized void updateUsage(long delta) {
        this.bytesUsed += delta;
        if (this.maxBytesUsed < this.bytesUsed) {
            this.maxBytesUsed = this.bytesUsed;
        }
    }

    @Override
    public synchronized long getBytesUsed() {
        return this.bytesUsed;
    }

    @Override
    public synchronized long getMaxBytesUsed() {
        return this.maxBytesUsed;
    }

    @Override
    public void close() throws IOException {
    }
}


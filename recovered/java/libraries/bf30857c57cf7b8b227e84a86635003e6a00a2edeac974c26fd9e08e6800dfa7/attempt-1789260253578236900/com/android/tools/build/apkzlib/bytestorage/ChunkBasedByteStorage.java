/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.AbstractCloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.ChunkBasedCloseableByteSource;
import com.android.tools.build.apkzlib.bytestorage.CloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.bytestorage.LimitedInputStream;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.ByteSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class ChunkBasedByteStorage
implements ByteStorage {
    private static final long DEFAULT_CHUNK_SIZE_BYTES = 0xA00000L;
    private final long maxChunkSize;
    private final ByteStorage delegate;

    ChunkBasedByteStorage(ByteStorage delegate) {
        this(0xA00000L, delegate);
    }

    ChunkBasedByteStorage(long maxChunkSize, ByteStorage delegate) {
        this.maxChunkSize = maxChunkSize;
        this.delegate = delegate;
    }

    @VisibleForTesting
    public ByteStorage getDelegate() {
        return this.delegate;
    }

    @Override
    public CloseableByteSource fromStream(InputStream stream) throws IOException {
        LimitedInputStream limitedInput;
        ArrayList<CloseableByteSource> sources = new ArrayList<CloseableByteSource>();
        do {
            limitedInput = new LimitedInputStream(stream, this.maxChunkSize);
            sources.add(this.delegate.fromStream(limitedInput));
        } while (!limitedInput.isInputFinished());
        return new ChunkBasedCloseableByteSource(sources);
    }

    @Override
    public CloseableByteSourceFromOutputStreamBuilder makeBuilder() throws IOException {
        return new AbstractCloseableByteSourceFromOutputStreamBuilder(){
            private final List<CloseableByteSource> sources = new ArrayList<CloseableByteSource>();
            @Nullable
            private CloseableByteSourceFromOutputStreamBuilder currentBuilder = null;
            private long written = 0L;

            @Override
            protected void doWrite(byte[] b2, int off, int len) throws IOException {
                int actualOffset = off;
                int remaining = len;
                while (remaining > 0) {
                    if (this.currentBuilder == null) {
                        this.currentBuilder = ChunkBasedByteStorage.this.delegate.makeBuilder();
                        this.written = 0L;
                    }
                    int maxWrite = (int)Math.min(ChunkBasedByteStorage.this.maxChunkSize - this.written, (long)remaining);
                    this.currentBuilder.write(b2, actualOffset, maxWrite);
                    this.written += (long)maxWrite;
                    remaining -= maxWrite;
                    actualOffset += maxWrite;
                    if (this.written != ChunkBasedByteStorage.this.maxChunkSize) continue;
                    this.sources.add(this.currentBuilder.build());
                    this.currentBuilder = null;
                }
            }

            @Override
            protected CloseableByteSource doBuild() throws IOException {
                if (this.currentBuilder != null) {
                    this.sources.add(this.currentBuilder.build());
                    this.currentBuilder = null;
                }
                return new ChunkBasedCloseableByteSource(this.sources);
            }
        };
    }

    @Override
    public CloseableByteSource fromSource(ByteSource source) throws IOException {
        long chunkSize;
        ArrayList<CloseableByteSource> sources = new ArrayList<CloseableByteSource>();
        long end = source.size();
        for (long start = 0L; start < end; start += chunkSize) {
            chunkSize = Math.min(end - start, this.maxChunkSize);
            sources.add(this.delegate.fromSource(source.slice(start, chunkSize)));
        }
        return new ChunkBasedCloseableByteSource(sources);
    }

    @Override
    public long getBytesUsed() {
        return this.delegate.getBytesUsed();
    }

    @Override
    public long getMaxBytesUsed() {
        return this.delegate.getMaxBytesUsed();
    }

    @Override
    public void close() throws IOException {
        this.delegate.close();
    }
}


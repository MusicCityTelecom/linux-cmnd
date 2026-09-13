/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferDataSource
implements DataSource {
    private final ByteBuffer mBuffer;
    private final int mSize;

    public ByteBufferDataSource(ByteBuffer buffer) {
        this(buffer, true);
    }

    private ByteBufferDataSource(ByteBuffer buffer, boolean sliceRequired) {
        this.mBuffer = sliceRequired ? buffer.slice() : buffer;
        this.mSize = buffer.remaining();
    }

    @Override
    public long size() {
        return this.mSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ByteBuffer getByteBuffer(long offset, int size) {
        this.checkChunkValid(offset, size);
        int chunkPosition = (int)offset;
        int chunkLimit = chunkPosition + size;
        ByteBuffer byteBuffer = this.mBuffer;
        synchronized (byteBuffer) {
            this.mBuffer.position(0);
            this.mBuffer.limit(chunkLimit);
            this.mBuffer.position(chunkPosition);
            return this.mBuffer.slice();
        }
    }

    @Override
    public void copyTo(long offset, int size, ByteBuffer dest) {
        dest.put(this.getByteBuffer(offset, size));
    }

    @Override
    public void feed(long offset, long size, DataSink sink) throws IOException {
        if (size < 0L || size > (long)this.mSize) {
            throw new IndexOutOfBoundsException("size: " + size + ", source size: " + this.mSize);
        }
        sink.consume(this.getByteBuffer(offset, (int)size));
    }

    @Override
    public ByteBufferDataSource slice(long offset, long size) {
        if (offset == 0L && size == (long)this.mSize) {
            return this;
        }
        if (size < 0L || size > (long)this.mSize) {
            throw new IndexOutOfBoundsException("size: " + size + ", source size: " + this.mSize);
        }
        return new ByteBufferDataSource(this.getByteBuffer(offset, (int)size), false);
    }

    private void checkChunkValid(long offset, long size) {
        if (offset < 0L) {
            throw new IndexOutOfBoundsException("offset: " + offset);
        }
        if (size < 0L) {
            throw new IndexOutOfBoundsException("size: " + size);
        }
        if (offset > (long)this.mSize) {
            throw new IndexOutOfBoundsException("offset (" + offset + ") > source size (" + this.mSize + ")");
        }
        long endOffset = offset + size;
        if (endOffset < offset) {
            throw new IndexOutOfBoundsException("offset (" + offset + ") + size (" + size + ") overflow");
        }
        if (endOffset > (long)this.mSize) {
            throw new IndexOutOfBoundsException("offset (" + offset + ") + size (" + size + ") > source size (" + this.mSize + ")");
        }
    }
}


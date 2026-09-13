/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.ReadableDataSink;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ByteArrayDataSink
implements ReadableDataSink {
    private static final int MAX_READ_CHUNK_SIZE = 65536;
    private byte[] mArray;
    private int mSize;

    public ByteArrayDataSink() {
        this(65536);
    }

    public ByteArrayDataSink(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("initial capacity: " + initialCapacity);
        }
        this.mArray = new byte[initialCapacity];
    }

    @Override
    public void consume(byte[] buf, int offset, int length) throws IOException {
        if (offset < 0) {
            throw new IndexOutOfBoundsException("offset: " + offset);
        }
        if (offset > buf.length) {
            throw new IndexOutOfBoundsException("offset: " + offset + ", buf.length: " + buf.length);
        }
        if (length == 0) {
            return;
        }
        this.ensureAvailable(length);
        System.arraycopy(buf, offset, this.mArray, this.mSize, length);
        this.mSize += length;
    }

    @Override
    public void consume(ByteBuffer buf) throws IOException {
        if (!buf.hasRemaining()) {
            return;
        }
        if (buf.hasArray()) {
            this.consume(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
            buf.position(buf.limit());
            return;
        }
        this.ensureAvailable(buf.remaining());
        byte[] tmp = new byte[Math.min(buf.remaining(), 65536)];
        while (buf.hasRemaining()) {
            int chunkSize = Math.min(buf.remaining(), tmp.length);
            buf.get(tmp, 0, chunkSize);
            System.arraycopy(tmp, 0, this.mArray, this.mSize, chunkSize);
            this.mSize += chunkSize;
        }
    }

    private void ensureAvailable(int minAvailable) throws IOException {
        if (minAvailable <= 0) {
            return;
        }
        long minCapacity = (long)this.mSize + (long)minAvailable;
        if (minCapacity <= (long)this.mArray.length) {
            return;
        }
        if (minCapacity > Integer.MAX_VALUE) {
            throw new IOException("Required capacity too large: " + minCapacity + ", max: " + Integer.MAX_VALUE);
        }
        int doubleCurrentSize = (int)Math.min((long)this.mArray.length * 2L, Integer.MAX_VALUE);
        int newSize = (int)Math.max(minCapacity, (long)doubleCurrentSize);
        this.mArray = Arrays.copyOf(this.mArray, newSize);
    }

    @Override
    public long size() {
        return this.mSize;
    }

    @Override
    public ByteBuffer getByteBuffer(long offset, int size) {
        this.checkChunkValid(offset, size);
        return ByteBuffer.wrap(this.mArray, (int)offset, size).slice();
    }

    @Override
    public void feed(long offset, long size, DataSink sink) throws IOException {
        this.checkChunkValid(offset, size);
        sink.consume(this.mArray, (int)offset, (int)size);
    }

    @Override
    public void copyTo(long offset, int size, ByteBuffer dest) throws IOException {
        this.checkChunkValid(offset, size);
        dest.put(this.mArray, (int)offset, size);
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

    @Override
    public DataSource slice(long offset, long size) {
        this.checkChunkValid(offset, size);
        return new SliceDataSource((int)offset, (int)size);
    }

    private class SliceDataSource
    implements DataSource {
        private final int mSliceOffset;
        private final int mSliceSize;

        private SliceDataSource(int offset, int size) {
            this.mSliceOffset = offset;
            this.mSliceSize = size;
        }

        @Override
        public long size() {
            return this.mSliceSize;
        }

        @Override
        public void feed(long offset, long size, DataSink sink) throws IOException {
            this.checkChunkValid(offset, size);
            sink.consume(ByteArrayDataSink.this.mArray, (int)((long)this.mSliceOffset + offset), (int)size);
        }

        @Override
        public ByteBuffer getByteBuffer(long offset, int size) throws IOException {
            this.checkChunkValid(offset, size);
            return ByteBuffer.wrap(ByteArrayDataSink.this.mArray, (int)((long)this.mSliceOffset + offset), size).slice();
        }

        @Override
        public void copyTo(long offset, int size, ByteBuffer dest) throws IOException {
            this.checkChunkValid(offset, size);
            dest.put(ByteArrayDataSink.this.mArray, (int)((long)this.mSliceOffset + offset), size);
        }

        @Override
        public DataSource slice(long offset, long size) {
            this.checkChunkValid(offset, size);
            return new SliceDataSource((int)((long)this.mSliceOffset + offset), (int)size);
        }

        private void checkChunkValid(long offset, long size) {
            if (offset < 0L) {
                throw new IndexOutOfBoundsException("offset: " + offset);
            }
            if (size < 0L) {
                throw new IndexOutOfBoundsException("size: " + size);
            }
            if (offset > (long)this.mSliceSize) {
                throw new IndexOutOfBoundsException("offset (" + offset + ") > source size (" + this.mSliceSize + ")");
            }
            long endOffset = offset + size;
            if (endOffset < offset) {
                throw new IndexOutOfBoundsException("offset (" + offset + ") + size (" + size + ") overflow");
            }
            if (endOffset > (long)this.mSliceSize) {
                throw new IndexOutOfBoundsException("offset (" + offset + ") + size (" + size + ") > source size (" + this.mSliceSize + ")");
            }
        }
    }
}


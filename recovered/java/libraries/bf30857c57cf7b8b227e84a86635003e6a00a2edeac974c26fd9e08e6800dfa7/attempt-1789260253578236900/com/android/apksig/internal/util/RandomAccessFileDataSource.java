/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class RandomAccessFileDataSource
implements DataSource {
    private static final int MAX_READ_CHUNK_SIZE = 65536;
    private final RandomAccessFile mFile;
    private final long mOffset;
    private final long mSize;

    public RandomAccessFileDataSource(RandomAccessFile file) {
        this.mFile = file;
        this.mOffset = 0L;
        this.mSize = -1L;
    }

    public RandomAccessFileDataSource(RandomAccessFile file, long offset, long size) {
        if (offset < 0L) {
            throw new IndexOutOfBoundsException("offset: " + size);
        }
        if (size < 0L) {
            throw new IndexOutOfBoundsException("size: " + size);
        }
        this.mFile = file;
        this.mOffset = offset;
        this.mSize = size;
    }

    @Override
    public long size() {
        if (this.mSize == -1L) {
            try {
                return this.mFile.length();
            }
            catch (IOException e2) {
                return 0L;
            }
        }
        return this.mSize;
    }

    @Override
    public RandomAccessFileDataSource slice(long offset, long size) {
        long sourceSize = this.size();
        RandomAccessFileDataSource.checkChunkValid(offset, size, sourceSize);
        if (offset == 0L && size == sourceSize) {
            return this;
        }
        return new RandomAccessFileDataSource(this.mFile, this.mOffset + offset, size);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void feed(long offset, long size, DataSink sink) throws IOException {
        int chunkSize;
        long remaining;
        long sourceSize = this.size();
        RandomAccessFileDataSource.checkChunkValid(offset, size, sourceSize);
        if (size == 0L) {
            return;
        }
        long chunkOffsetInFile = this.mOffset + offset;
        byte[] buf = new byte[(int)Math.min(remaining, 65536L)];
        for (remaining = size; remaining > 0L; remaining -= (long)chunkSize) {
            chunkSize = (int)Math.min(remaining, (long)buf.length);
            RandomAccessFile randomAccessFile = this.mFile;
            synchronized (randomAccessFile) {
                this.mFile.seek(chunkOffsetInFile);
                this.mFile.readFully(buf, 0, chunkSize);
            }
            sink.consume(buf, 0, chunkSize);
            chunkOffsetInFile += (long)chunkSize;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void copyTo(long offset, int size, ByteBuffer dest) throws IOException {
        long sourceSize = this.size();
        RandomAccessFileDataSource.checkChunkValid(offset, size, sourceSize);
        if (size == 0) {
            return;
        }
        if (size > dest.remaining()) {
            throw new BufferOverflowException();
        }
        long offsetInFile = this.mOffset + offset;
        int prevLimit = dest.limit();
        try {
            int chunkSize;
            dest.limit(dest.position() + size);
            FileChannel fileChannel = this.mFile.getChannel();
            for (int remaining = size; remaining > 0; remaining -= chunkSize) {
                RandomAccessFile randomAccessFile = this.mFile;
                synchronized (randomAccessFile) {
                    fileChannel.position(offsetInFile);
                    chunkSize = fileChannel.read(dest);
                }
                offsetInFile += (long)chunkSize;
            }
        }
        finally {
            dest.limit(prevLimit);
        }
    }

    @Override
    public ByteBuffer getByteBuffer(long offset, int size) throws IOException {
        if (size < 0) {
            throw new IndexOutOfBoundsException("size: " + size);
        }
        ByteBuffer result = ByteBuffer.allocate(size);
        this.copyTo(offset, size, result);
        result.flip();
        return result;
    }

    private static void checkChunkValid(long offset, long size, long sourceSize) {
        if (offset < 0L) {
            throw new IndexOutOfBoundsException("offset: " + offset);
        }
        if (size < 0L) {
            throw new IndexOutOfBoundsException("size: " + size);
        }
        if (offset > sourceSize) {
            throw new IndexOutOfBoundsException("offset (" + offset + ") > source size (" + sourceSize + ")");
        }
        long endOffset = offset + size;
        if (endOffset < offset) {
            throw new IndexOutOfBoundsException("offset (" + offset + ") + size (" + size + ") overflow");
        }
        if (endOffset > sourceSize) {
            throw new IndexOutOfBoundsException("offset (" + offset + ") + size (" + size + ") > source size (" + sourceSize + ")");
        }
    }
}


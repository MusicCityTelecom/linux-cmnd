/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.sign;

import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import com.android.tools.build.apkzlib.zip.ZFile;
import com.google.common.base.Preconditions;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;

class ZFileDataSource
implements DataSource {
    private static final int MAX_READ_CHUNK_SIZE = 65536;
    private final ZFile file;
    private final long offset;
    private final long size;

    public ZFileDataSource(ZFile file) {
        this.file = file;
        this.offset = 0L;
        this.size = -1L;
    }

    public ZFileDataSource(ZFile file, long offset, long size) {
        Preconditions.checkArgument(offset >= 0L, "offset < 0");
        Preconditions.checkArgument(size >= 0L, "size < 0");
        this.file = file;
        this.offset = offset;
        this.size = size;
    }

    @Override
    public long size() {
        if (this.size == -1L) {
            try {
                return this.file.directSize();
            }
            catch (IOException e2) {
                return 0L;
            }
        }
        return this.size;
    }

    @Override
    public DataSource slice(long offset, long size) {
        long sourceSize = this.size();
        ZFileDataSource.checkChunkValid(offset, size, sourceSize);
        if (offset == 0L && size == sourceSize) {
            return this;
        }
        return new ZFileDataSource(this.file, this.offset + offset, size);
    }

    @Override
    public void feed(long offset, long size, DataSink sink) throws IOException {
        long sourceSize = this.size();
        ZFileDataSource.checkChunkValid(offset, size, sourceSize);
        if (size == 0L) {
            return;
        }
        long chunkOffsetInFile = this.offset + offset;
        long remaining = size;
        byte[] buf = new byte[(int)Math.min(remaining, 65536L)];
        while (remaining > 0L) {
            int chunkSize = (int)Math.min(remaining, (long)buf.length);
            int readSize = this.file.directRead(chunkOffsetInFile, buf, 0, chunkSize);
            if (readSize == -1) {
                throw new EOFException("Premature EOF");
            }
            if (readSize <= 0) continue;
            sink.consume(buf, 0, readSize);
            chunkOffsetInFile += (long)readSize;
            remaining -= (long)readSize;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void copyTo(long offset, int size, ByteBuffer dest) throws IOException {
        long sourceSize = this.size();
        ZFileDataSource.checkChunkValid(offset, size, sourceSize);
        if (size == 0) {
            return;
        }
        int prevLimit = dest.limit();
        try {
            this.file.directFullyRead(this.offset + offset, dest);
        }
        finally {
            dest.limit(prevLimit);
        }
    }

    @Override
    public ByteBuffer getByteBuffer(long offset, int size) throws IOException {
        ByteBuffer result = ByteBuffer.allocate(size);
        this.copyTo(offset, size, result);
        result.flip();
        return result;
    }

    private static void checkChunkValid(long offset, long size, long sourceSize) {
        Preconditions.checkArgument(offset >= 0L, "offset < 0");
        Preconditions.checkArgument(size >= 0L, "size < 0");
        Preconditions.checkArgument(offset <= sourceSize, "offset > sourceSize");
        long endOffset = offset + size;
        Preconditions.checkArgument(offset <= endOffset, "offset > endOffset");
        Preconditions.checkArgument(endOffset <= sourceSize, "endOffset > sourceSize");
    }
}


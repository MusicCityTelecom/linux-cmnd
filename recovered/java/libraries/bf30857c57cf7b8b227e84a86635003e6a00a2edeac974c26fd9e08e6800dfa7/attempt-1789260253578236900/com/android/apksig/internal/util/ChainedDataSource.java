/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.internal.util.ByteBufferSink;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class ChainedDataSource
implements DataSource {
    private final DataSource[] mSources;
    private final long mTotalSize;

    public ChainedDataSource(DataSource ... sources) {
        this.mSources = sources;
        this.mTotalSize = Arrays.stream(sources).mapToLong(src -> src.size()).sum();
    }

    @Override
    public long size() {
        return this.mTotalSize;
    }

    @Override
    public void feed(long offset, long size, DataSink sink) throws IOException {
        if (offset + size > this.mTotalSize) {
            throw new IndexOutOfBoundsException("Requested more than available");
        }
        for (DataSource src : this.mSources) {
            if (offset >= src.size()) {
                offset -= src.size();
                continue;
            }
            long remaining = src.size() - offset;
            if (remaining >= size) {
                src.feed(offset, size, sink);
                break;
            }
            src.feed(offset, remaining, sink);
            size -= remaining;
            offset = 0L;
        }
    }

    @Override
    public ByteBuffer getByteBuffer(long offset, int size) throws IOException {
        int i2;
        if (offset + (long)size > this.mTotalSize) {
            throw new IndexOutOfBoundsException("Requested more than available");
        }
        Pair<Integer, Long> firstSource = this.locateDataSource(offset);
        offset = firstSource.getSecond();
        if (offset + (long)size <= this.mSources[i2].size()) {
            return this.mSources[i2].getByteBuffer(offset, size);
        }
        ByteBuffer buffer = ByteBuffer.allocate(size);
        for (i2 = firstSource.getFirst().intValue(); i2 < this.mSources.length && buffer.hasRemaining(); ++i2) {
            long sizeToCopy = Math.min(this.mSources[i2].size() - offset, (long)buffer.remaining());
            this.mSources[i2].copyTo(offset, Math.toIntExact(sizeToCopy), buffer);
            offset = 0L;
        }
        buffer.rewind();
        return buffer;
    }

    @Override
    public void copyTo(long offset, int size, ByteBuffer dest) throws IOException {
        this.feed(offset, size, new ByteBufferSink(dest));
    }

    @Override
    public DataSource slice(long offset, long size) {
        DataSource beginSource;
        Pair<Integer, Long> firstSource = this.locateDataSource(offset);
        int beginIndex = firstSource.getFirst();
        long beginLocalOffset = firstSource.getSecond();
        if (beginLocalOffset + size <= (beginSource = this.mSources[beginIndex]).size()) {
            return beginSource.slice(beginLocalOffset, size);
        }
        ArrayList<DataSource> sources = new ArrayList<DataSource>();
        sources.add(beginSource.slice(beginLocalOffset, beginSource.size() - beginLocalOffset));
        Pair<Integer, Long> lastSource = this.locateDataSource(offset + size);
        int endIndex = lastSource.getFirst();
        long endLocalOffset = lastSource.getSecond();
        for (int i2 = beginIndex + 1; i2 < endIndex - 1; ++i2) {
            sources.add(this.mSources[i2]);
        }
        sources.add(this.mSources[endIndex].slice(0L, endLocalOffset));
        return new ChainedDataSource(sources.toArray(new DataSource[0]));
    }

    private Pair<Integer, Long> locateDataSource(long offset) {
        long localOffset = offset;
        for (int i2 = 0; i2 < this.mSources.length; ++i2) {
            if (localOffset < this.mSources[i2].size()) {
                return Pair.of(i2, localOffset);
            }
            localOffset -= this.mSources[i2].size();
        }
        throw new IndexOutOfBoundsException("Access is out of bound, offset: " + offset + ", totalSize: " + this.mTotalSize);
    }
}


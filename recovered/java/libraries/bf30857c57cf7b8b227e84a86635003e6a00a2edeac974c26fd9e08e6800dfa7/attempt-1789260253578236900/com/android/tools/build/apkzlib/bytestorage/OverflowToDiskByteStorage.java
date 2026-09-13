/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.AbstractCloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.CloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.bytestorage.InMemoryByteStorage;
import com.android.tools.build.apkzlib.bytestorage.LruTrackedCloseableByteSource;
import com.android.tools.build.apkzlib.bytestorage.LruTracker;
import com.android.tools.build.apkzlib.bytestorage.TemporaryDirectoryFactory;
import com.android.tools.build.apkzlib.bytestorage.TemporaryDirectoryStorage;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.ByteSource;
import java.io.IOException;
import java.io.InputStream;

public class OverflowToDiskByteStorage
implements ByteStorage {
    private static final long DEFAULT_MEMORY_CACHE_BYTES = 0x3200000L;
    private final InMemoryByteStorage memoryStorage = new InMemoryByteStorage();
    @VisibleForTesting
    final TemporaryDirectoryStorage diskStorage;
    private final LruTracker<LruTrackedCloseableByteSource> memorySourcesTracker;
    private final long memoryCacheSize;
    private long maxBytesUsed;

    public OverflowToDiskByteStorage(TemporaryDirectoryFactory temporaryDirectoryFactory) throws IOException {
        this(0x3200000L, temporaryDirectoryFactory);
    }

    public OverflowToDiskByteStorage(long memoryCacheSize, TemporaryDirectoryFactory temporaryDirectoryFactory) throws IOException {
        this.diskStorage = new TemporaryDirectoryStorage(temporaryDirectoryFactory);
        this.memoryCacheSize = memoryCacheSize;
        this.memorySourcesTracker = new LruTracker();
    }

    @Override
    public CloseableByteSource fromStream(InputStream stream) throws IOException {
        LruTrackedCloseableByteSource memSource = new LruTrackedCloseableByteSource(this.memoryStorage.fromStream(stream), this.memorySourcesTracker);
        this.checkMaxUsage();
        this.reviewSources();
        return memSource;
    }

    @Override
    public CloseableByteSourceFromOutputStreamBuilder makeBuilder() throws IOException {
        final CloseableByteSourceFromOutputStreamBuilder memBuilder = this.memoryStorage.makeBuilder();
        return new AbstractCloseableByteSourceFromOutputStreamBuilder(){

            @Override
            protected void doWrite(byte[] b2, int off, int len) throws IOException {
                memBuilder.write(b2, off, len);
            }

            @Override
            protected CloseableByteSource doBuild() throws IOException {
                LruTrackedCloseableByteSource memSource = new LruTrackedCloseableByteSource(memBuilder.build(), OverflowToDiskByteStorage.this.memorySourcesTracker);
                OverflowToDiskByteStorage.this.checkMaxUsage();
                OverflowToDiskByteStorage.this.reviewSources();
                return memSource;
            }
        };
    }

    @Override
    public CloseableByteSource fromSource(ByteSource source) throws IOException {
        LruTrackedCloseableByteSource memSource = new LruTrackedCloseableByteSource(this.memoryStorage.fromSource(source), this.memorySourcesTracker);
        this.checkMaxUsage();
        this.reviewSources();
        return memSource;
    }

    @Override
    public synchronized long getBytesUsed() {
        return this.memoryStorage.getBytesUsed() + this.diskStorage.getBytesUsed();
    }

    @Override
    public synchronized long getMaxBytesUsed() {
        return this.maxBytesUsed;
    }

    private synchronized void checkMaxUsage() {
        if (this.getBytesUsed() > this.maxBytesUsed) {
            this.maxBytesUsed = this.getBytesUsed();
        }
    }

    private synchronized void reviewSources() throws IOException {
        while (this.memoryStorage.getBytesUsed() > this.memoryCacheSize) {
            LruTrackedCloseableByteSource last = this.memorySourcesTracker.last();
            if (last == null) continue;
            LruTrackedCloseableByteSource lastSource = last;
            lastSource.move(this.diskStorage);
        }
    }

    public long getMemoryBytesUsed() {
        return this.memoryStorage.getBytesUsed();
    }

    public long getMaxMemoryBytesUsed() {
        return this.memoryStorage.getMaxBytesUsed();
    }

    public long getDiskBytesUsed() {
        return this.diskStorage.getBytesUsed();
    }

    public long getMaxDiskBytesUsed() {
        return this.diskStorage.getMaxBytesUsed();
    }

    @Override
    public void close() throws IOException {
        this.memoryStorage.close();
        this.diskStorage.close();
    }
}


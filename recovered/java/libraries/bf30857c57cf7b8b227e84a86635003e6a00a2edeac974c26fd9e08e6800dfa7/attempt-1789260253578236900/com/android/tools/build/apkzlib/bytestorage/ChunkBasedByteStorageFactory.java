/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.ByteStorageFactory;
import com.android.tools.build.apkzlib.bytestorage.ChunkBasedByteStorage;
import java.io.IOException;
import javax.annotation.Nullable;

public class ChunkBasedByteStorageFactory
implements ByteStorageFactory {
    private final ByteStorageFactory delegate;
    @Nullable
    private final Long maxChunkSize;

    public ChunkBasedByteStorageFactory(ByteStorageFactory delegate) {
        this(delegate, null);
    }

    public ChunkBasedByteStorageFactory(ByteStorageFactory delegate, @Nullable Long maxChunkSize) {
        this.delegate = delegate;
        this.maxChunkSize = maxChunkSize;
    }

    @Override
    public ByteStorage create() throws IOException {
        if (this.maxChunkSize == null) {
            return new ChunkBasedByteStorage(this.delegate.create());
        }
        return new ChunkBasedByteStorage(this.maxChunkSize, this.delegate.create());
    }
}


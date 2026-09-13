/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.ByteStorageFactory;
import com.android.tools.build.apkzlib.bytestorage.OverflowToDiskByteStorage;
import com.android.tools.build.apkzlib.bytestorage.TemporaryDirectoryFactory;
import java.io.IOException;
import javax.annotation.Nullable;

public class OverflowToDiskByteStorageFactory
implements ByteStorageFactory {
    @Nullable
    private final Long memoryCacheSizeInBytes;
    private final TemporaryDirectoryFactory temporaryDirectoryFactory;

    public OverflowToDiskByteStorageFactory(TemporaryDirectoryFactory temporaryDirectoryFactory) {
        this(null, temporaryDirectoryFactory);
    }

    public OverflowToDiskByteStorageFactory(Long memoryCacheSizeInBytes, TemporaryDirectoryFactory temporaryDirectoryFactory) {
        this.memoryCacheSizeInBytes = memoryCacheSizeInBytes;
        this.temporaryDirectoryFactory = temporaryDirectoryFactory;
    }

    @Override
    public ByteStorage create() throws IOException {
        if (this.memoryCacheSizeInBytes == null) {
            return new OverflowToDiskByteStorage(this.temporaryDirectoryFactory);
        }
        return new OverflowToDiskByteStorage(this.memoryCacheSizeInBytes, this.temporaryDirectoryFactory);
    }
}


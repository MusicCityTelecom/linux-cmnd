/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.ByteStorageFactory;
import com.android.tools.build.apkzlib.bytestorage.InMemoryByteStorage;
import java.io.IOException;

public class InMemoryByteStorageFactory
implements ByteStorageFactory {
    @Override
    public ByteStorage create() throws IOException {
        return new InMemoryByteStorage();
    }
}


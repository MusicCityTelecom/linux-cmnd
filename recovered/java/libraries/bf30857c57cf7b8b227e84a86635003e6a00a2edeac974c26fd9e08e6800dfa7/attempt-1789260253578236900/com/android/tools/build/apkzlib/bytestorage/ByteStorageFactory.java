/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import java.io.IOException;

public interface ByteStorageFactory {
    public ByteStorage create() throws IOException;
}


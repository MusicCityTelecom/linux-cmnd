/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.CloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.io.ByteSource;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public interface ByteStorage
extends Closeable {
    public CloseableByteSource fromStream(InputStream var1) throws IOException;

    public CloseableByteSourceFromOutputStreamBuilder makeBuilder() throws IOException;

    public CloseableByteSource fromSource(ByteSource var1) throws IOException;

    public long getBytesUsed();

    public long getMaxBytesUsed();
}


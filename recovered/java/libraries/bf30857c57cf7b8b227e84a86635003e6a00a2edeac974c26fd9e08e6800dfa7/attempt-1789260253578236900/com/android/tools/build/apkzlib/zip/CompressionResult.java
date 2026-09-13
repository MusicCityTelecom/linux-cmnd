/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.CompressionMethod;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;

public class CompressionResult {
    private final CompressionMethod compressionMethod;
    private final CloseableByteSource source;
    private final long mSize;

    public CompressionResult(CloseableByteSource source, CompressionMethod method, long size) {
        this.compressionMethod = method;
        this.source = source;
        this.mSize = size;
    }

    public CompressionMethod getCompressionMethod() {
        return this.compressionMethod;
    }

    public CloseableByteSource getSource() {
        return this.source;
    }

    public long getSize() {
        return this.mSize;
    }
}


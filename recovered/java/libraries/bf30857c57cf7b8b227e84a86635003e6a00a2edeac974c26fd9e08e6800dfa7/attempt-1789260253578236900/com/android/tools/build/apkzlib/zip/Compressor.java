/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.zip.CompressionResult;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.util.concurrent.ListenableFuture;

public interface Compressor {
    public ListenableFuture<CompressionResult> compress(CloseableByteSource var1, ByteStorage var2);
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.compress;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.zip.CompressionResult;
import com.android.tools.build.apkzlib.zip.Compressor;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.util.concurrent.Executor;

public abstract class ExecutorCompressor
implements Compressor {
    private final Executor executor;

    public ExecutorCompressor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public ListenableFuture<CompressionResult> compress(CloseableByteSource source, ByteStorage storage) {
        SettableFuture<CompressionResult> future = SettableFuture.create();
        this.executor.execute(() -> {
            try {
                future.set(this.immediateCompress(source, storage));
            }
            catch (Throwable e2) {
                future.setException(e2);
            }
        });
        return future;
    }

    protected abstract CompressionResult immediateCompress(CloseableByteSource var1, ByteStorage var2) throws Exception;
}


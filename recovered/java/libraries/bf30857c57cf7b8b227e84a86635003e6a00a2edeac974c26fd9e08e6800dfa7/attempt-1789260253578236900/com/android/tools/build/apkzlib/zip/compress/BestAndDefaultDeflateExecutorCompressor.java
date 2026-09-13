/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.compress;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.zip.CompressionResult;
import com.android.tools.build.apkzlib.zip.compress.DeflateExecutionCompressor;
import com.android.tools.build.apkzlib.zip.compress.ExecutorCompressor;
import com.android.tools.build.apkzlib.zip.utils.ByteTracker;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;

public class BestAndDefaultDeflateExecutorCompressor
extends ExecutorCompressor {
    private final DeflateExecutionCompressor defaultDeflater;
    private final DeflateExecutionCompressor bestDeflater;
    private final double minRatio;

    public BestAndDefaultDeflateExecutorCompressor(Executor executor, double minRatio) {
        super(executor);
        Preconditions.checkArgument(minRatio >= 0.0, "minRatio < 0.0");
        Preconditions.checkArgument(minRatio <= 1.0, "minRatio > 1.0");
        this.defaultDeflater = new DeflateExecutionCompressor(executor, -1);
        this.bestDeflater = new DeflateExecutionCompressor(executor, 9);
        this.minRatio = minRatio;
    }

    @Deprecated
    public BestAndDefaultDeflateExecutorCompressor(Executor executor, ByteTracker tracker, double minRatio) {
        this(executor, minRatio);
    }

    @Override
    protected CompressionResult immediateCompress(CloseableByteSource source, ByteStorage storage) throws Exception {
        CompressionResult defaultResult = this.defaultDeflater.immediateCompress(source, storage);
        CompressionResult bestResult = this.bestDeflater.immediateCompress(source, storage);
        double sizeRatio = (double)bestResult.getSize() / (double)defaultResult.getSize();
        if (sizeRatio >= this.minRatio) {
            return defaultResult;
        }
        return bestResult;
    }
}


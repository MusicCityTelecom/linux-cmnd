/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.compress;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.CloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.zip.CompressionMethod;
import com.android.tools.build.apkzlib.zip.CompressionResult;
import com.android.tools.build.apkzlib.zip.compress.ExecutorCompressor;
import com.android.tools.build.apkzlib.zip.utils.ByteTracker;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.io.ByteStreams;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

public class DeflateExecutionCompressor
extends ExecutorCompressor {
    private final int level;

    public DeflateExecutionCompressor(Executor executor, int level) {
        super(executor);
        this.level = level;
    }

    @Deprecated
    public DeflateExecutionCompressor(Executor executor, ByteTracker tracker, int level) {
        this(executor, level);
    }

    @Override
    protected CompressionResult immediateCompress(CloseableByteSource source, ByteStorage storage) throws Exception {
        Deflater deflater = new Deflater(this.level, true);
        CloseableByteSourceFromOutputStreamBuilder resultBuilder = storage.makeBuilder();
        try (InputStream inputStream = source.openBufferedStream();
             DeflaterOutputStream dos = new DeflaterOutputStream((OutputStream)resultBuilder, deflater);){
            ByteStreams.copy(inputStream, dos);
        }
        CloseableByteSource result = resultBuilder.build();
        if (result.size() >= source.size()) {
            result.close();
            return new CompressionResult(source, CompressionMethod.STORE, source.size());
        }
        return new CompressionResult(result, CompressionMethod.DEFLATE, result.size());
    }
}


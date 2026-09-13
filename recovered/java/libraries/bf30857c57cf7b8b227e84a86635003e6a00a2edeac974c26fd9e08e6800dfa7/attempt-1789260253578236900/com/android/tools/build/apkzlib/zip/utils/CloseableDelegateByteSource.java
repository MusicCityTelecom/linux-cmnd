/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.utils;

import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

public class CloseableDelegateByteSource
extends CloseableByteSource {
    @Nullable
    private ByteSource inner;
    private final long mSize;

    public CloseableDelegateByteSource(ByteSource inner, long size) {
        this.inner = inner;
        this.mSize = size;
    }

    private synchronized ByteSource get() {
        if (this.inner == null) {
            throw new ByteSourceDisposedException();
        }
        return this.inner;
    }

    @Override
    protected synchronized void innerClose() throws IOException {
        if (this.inner == null) {
            return;
        }
        this.inner = null;
    }

    public long sizeNoException() {
        return this.mSize;
    }

    @Override
    public CharSource asCharSource(Charset charset) {
        return this.get().asCharSource(charset);
    }

    @Override
    public InputStream openBufferedStream() throws IOException {
        return this.get().openBufferedStream();
    }

    @Override
    public ByteSource slice(long offset, long length) {
        return this.get().slice(offset, length);
    }

    @Override
    public boolean isEmpty() throws IOException {
        return this.get().isEmpty();
    }

    @Override
    public long size() throws IOException {
        return this.get().size();
    }

    @Override
    public long copyTo(OutputStream output) throws IOException {
        return this.get().copyTo(output);
    }

    @Override
    public long copyTo(ByteSink sink) throws IOException {
        return this.get().copyTo(sink);
    }

    @Override
    public byte[] read() throws IOException {
        return this.get().read();
    }

    @Override
    public <T> T read(ByteProcessor<T> processor) throws IOException {
        return this.get().read(processor);
    }

    @Override
    public HashCode hash(HashFunction hashFunction) throws IOException {
        return this.get().hash(hashFunction);
    }

    @Override
    public boolean contentEquals(ByteSource other) throws IOException {
        return this.get().contentEquals(other);
    }

    @Override
    public InputStream openStream() throws IOException {
        return this.get().openStream();
    }

    private static class ByteSourceDisposedException
    extends RuntimeException {
        private ByteSourceDisposedException() {
            super("Byte source was created by a ByteTracker and is now disposed. If you see this message, then there is a bug.");
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.io.ByteProcessor;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

public class LazyDelegateByteSource
extends CloseableByteSource {
    private final ListenableFuture<CloseableByteSource> delegate;

    public LazyDelegateByteSource(ListenableFuture<CloseableByteSource> delegate) {
        this.delegate = delegate;
    }

    public ListenableFuture<CloseableByteSource> getDelegate() {
        return this.delegate;
    }

    private CloseableByteSource get() throws IOException {
        try {
            CloseableByteSource r3 = (CloseableByteSource)this.delegate.get();
            if (r3 == null) {
                throw new IOException("Delegate byte source computation resulted in null.");
            }
            return r3;
        }
        catch (InterruptedException e2) {
            throw new IOException("Interrupted while waiting for byte source computation.", e2);
        }
        catch (ExecutionException e3) {
            throw new IOException("Failed to compute byte source.", e3);
        }
    }

    @Override
    public CharSource asCharSource(Charset charset) {
        try {
            return this.get().asCharSource(charset);
        }
        catch (IOException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override
    public InputStream openBufferedStream() throws IOException {
        return this.get().openBufferedStream();
    }

    @Override
    public ByteSource slice(long offset, long length) {
        try {
            return this.get().slice(offset, length);
        }
        catch (IOException e2) {
            throw new RuntimeException(e2);
        }
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

    @Override
    public void innerClose() throws IOException {
        this.get().close();
    }
}


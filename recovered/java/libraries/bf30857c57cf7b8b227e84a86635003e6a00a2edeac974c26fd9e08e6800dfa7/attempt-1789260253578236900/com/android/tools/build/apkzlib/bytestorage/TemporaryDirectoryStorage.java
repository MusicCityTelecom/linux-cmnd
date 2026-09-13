/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.AbstractCloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.CloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.bytestorage.TemporaryDirectory;
import com.android.tools.build.apkzlib.bytestorage.TemporaryDirectoryFactory;
import com.android.tools.build.apkzlib.bytestorage.TemporaryFileCloseableByteSource;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TemporaryDirectoryStorage
implements ByteStorage {
    @VisibleForTesting
    final TemporaryDirectory temporaryDirectory;
    private long bytesUsed;
    private long maxBytesUsed;

    public TemporaryDirectoryStorage(TemporaryDirectoryFactory temporaryDirectoryFactory) throws IOException {
        this.temporaryDirectory = temporaryDirectoryFactory.make();
    }

    @Override
    public CloseableByteSource fromStream(InputStream stream) throws IOException {
        File temporaryFile = this.temporaryDirectory.newFile();
        try (FileOutputStream output = new FileOutputStream(temporaryFile);){
            ByteStreams.copy(stream, output);
        }
        long size = temporaryFile.length();
        this.incrementBytesUsed(size);
        return new TemporaryFileCloseableByteSource(temporaryFile, () -> this.incrementBytesUsed(-size));
    }

    @Override
    public CloseableByteSourceFromOutputStreamBuilder makeBuilder() throws IOException {
        final File temporaryFile = this.temporaryDirectory.newFile();
        return new AbstractCloseableByteSourceFromOutputStreamBuilder(){
            private final FileOutputStream output;
            {
                this.output = new FileOutputStream(temporaryFile);
            }

            @Override
            protected void doWrite(byte[] b2, int off, int len) throws IOException {
                this.output.write(b2, off, len);
                TemporaryDirectoryStorage.this.incrementBytesUsed(len);
            }

            @Override
            protected CloseableByteSource doBuild() throws IOException {
                this.output.close();
                long size = temporaryFile.length();
                return new TemporaryFileCloseableByteSource(temporaryFile, () -> TemporaryDirectoryStorage.this.incrementBytesUsed(-size));
            }
        };
    }

    @Override
    public CloseableByteSource fromSource(ByteSource source) throws IOException {
        try (InputStream stream = source.openStream();){
            CloseableByteSource closeableByteSource = this.fromStream(stream);
            return closeableByteSource;
        }
    }

    @Override
    public synchronized long getBytesUsed() {
        return this.bytesUsed;
    }

    @Override
    public synchronized long getMaxBytesUsed() {
        return this.maxBytesUsed;
    }

    private synchronized void incrementBytesUsed(long amount) {
        this.bytesUsed += amount;
        if (this.bytesUsed > this.maxBytesUsed) {
            this.maxBytesUsed = this.bytesUsed;
        }
    }

    @Override
    public void close() throws IOException {
        this.temporaryDirectory.close();
    }
}


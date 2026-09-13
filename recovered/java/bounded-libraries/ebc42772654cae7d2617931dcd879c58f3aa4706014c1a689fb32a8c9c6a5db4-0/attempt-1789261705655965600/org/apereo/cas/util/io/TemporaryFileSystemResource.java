/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.FileSystemResource
 */
package org.apereo.cas.util.io;

import java.io.Closeable;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;

public class TemporaryFileSystemResource
extends FileSystemResource {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(TemporaryFileSystemResource.class);

    public TemporaryFileSystemResource(File file) {
        super(file);
    }

    public ReadableByteChannel readableChannel() throws IOException {
        final ReadableByteChannel readableChannel = super.readableChannel();
        return new ReadableByteChannel(){

            @Override
            public boolean isOpen() {
                return readableChannel.isOpen();
            }

            @Override
            public void close() throws IOException {
                TemporaryFileSystemResource.this.closeThenDeleteFile(readableChannel);
            }

            @Override
            public int read(ByteBuffer dst) throws IOException {
                return readableChannel.read(dst);
            }
        };
    }

    public InputStream getInputStream() throws IOException {
        return new FilterInputStream(super.getInputStream()){

            @Override
            public void close() throws IOException {
                TemporaryFileSystemResource.this.closeThenDeleteFile(this.in);
            }
        };
    }

    private void closeThenDeleteFile(Closeable closeable) throws IOException {
        try {
            closeable.close();
        }
        finally {
            this.deleteFile();
        }
    }

    private void deleteFile() {
        try {
            Files.delete(this.getFile().toPath());
        }
        catch (IOException ex) {
            LOGGER.warn("Failed to delete temporary heap dump file '" + this.getFile() + "'", (Throwable)ex);
        }
    }

    public boolean isFile() {
        return false;
    }
}


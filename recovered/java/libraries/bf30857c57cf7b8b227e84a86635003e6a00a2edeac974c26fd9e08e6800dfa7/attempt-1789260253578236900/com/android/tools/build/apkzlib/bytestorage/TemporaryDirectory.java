/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.TemporaryFile;
import com.google.common.annotations.VisibleForTesting;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

public interface TemporaryDirectory
extends Closeable {
    public File newFile() throws IOException;

    @VisibleForTesting
    public File getDirectory();

    public static TemporaryDirectory newSystemTemporaryDirectory() throws IOException {
        final Path tempDir = Files.createTempDirectory("tempdir_", new FileAttribute[0]);
        final TemporaryFile tempDirFile = new TemporaryFile(tempDir.toFile());
        return new TemporaryDirectory(){

            @Override
            public File newFile() throws IOException {
                return Files.createTempFile(tempDir, "temp_", ".data", new FileAttribute[0]).toFile();
            }

            @Override
            public File getDirectory() {
                return tempDir.toFile();
            }

            @Override
            public void close() throws IOException {
                tempDirFile.close();
            }
        };
    }

    public static TemporaryDirectory fixed(final File directory) {
        return new TemporaryDirectory(){

            @Override
            public File newFile() throws IOException {
                return Files.createTempFile(directory.toPath(), "temp_", ".data", new FileAttribute[0]).toFile();
            }

            @Override
            public File getDirectory() {
                return directory;
            }

            @Override
            public void close() throws IOException {
            }
        };
    }
}


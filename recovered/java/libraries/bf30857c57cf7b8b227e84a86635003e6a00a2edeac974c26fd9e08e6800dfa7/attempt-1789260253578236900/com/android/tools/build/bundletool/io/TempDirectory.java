/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.io;

import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import javax.annotation.Nullable;

public class TempDirectory
implements AutoCloseable {
    private final Path dirPath;

    public TempDirectory() {
        this(null);
    }

    public TempDirectory(@Nullable String prefix) {
        try {
            this.dirPath = Files.createTempDirectory(prefix, new FileAttribute[0]);
        }
        catch (IOException e2) {
            throw new UncheckedIOException(e2);
        }
    }

    public Path getPath() {
        return this.dirPath;
    }

    @Override
    public void close() {
        this.closeWithRetry(1);
    }

    private void closeWithRetry(int numAttempt) {
        block6: {
            try {
                MoreFiles.deleteRecursively(this.dirPath, RecursiveDeleteOption.ALLOW_INSECURE);
            }
            catch (FileSystemException e2) {
                if (!(e2.getCause() instanceof DirectoryNotEmptyException)) break block6;
                if (numAttempt == 5) {
                    throw new UncheckedIOException("Unable to delete temporary directory after 5 attempts.", e2);
                }
                try {
                    Thread.sleep(200L);
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                this.closeWithRetry(numAttempt + 1);
            }
            catch (IOException e3) {
                throw new UncheckedIOException(e3);
            }
        }
    }
}


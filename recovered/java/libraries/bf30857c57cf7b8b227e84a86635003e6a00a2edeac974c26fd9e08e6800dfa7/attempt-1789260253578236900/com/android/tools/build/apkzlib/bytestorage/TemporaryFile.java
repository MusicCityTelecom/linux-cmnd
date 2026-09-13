/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.google.common.base.Preconditions;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class TemporaryFile
implements Closeable {
    private boolean deleted = false;
    private final File file;

    public TemporaryFile(File file) {
        this.file = file;
    }

    public File getFile() {
        Preconditions.checkState(!this.deleted, "File already deleted");
        return this.file;
    }

    @Override
    public void close() throws IOException {
        if (this.deleted) {
            return;
        }
        this.deleted = true;
        this.deleteFile(this.file);
    }

    private void deleteFile(File file) throws IOException {
        File[] contents;
        if (file.isDirectory() && (contents = file.listFiles()) != null) {
            for (File subFile : contents) {
                this.deleteFile(subFile);
            }
        }
        if (file.exists() && !file.delete()) {
            throw new IOException("Failed to delete '" + file.getAbsolutePath() + "'");
        }
    }
}


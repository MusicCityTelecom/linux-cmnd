/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.utils;

import com.google.common.base.Objects;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

public class CachedFileContents<T> {
    private final File file;
    private long lastClosed;
    private long size;
    @Nullable
    private HashCode hash;
    @Nullable
    private T cache;

    public CachedFileContents(File file) {
        this.file = file;
    }

    public void closed(@Nullable T cache) {
        this.cache = cache;
        this.lastClosed = this.file.lastModified();
        this.size = this.file.length();
        this.hash = this.hashFile();
    }

    public boolean isValid() {
        boolean valid = true;
        if (!this.file.exists()) {
            valid = false;
        }
        if (valid && this.file.lastModified() != this.lastClosed) {
            valid = false;
        }
        if (valid && this.file.length() != this.size) {
            valid = false;
        }
        if (valid && !Objects.equal(this.hash, this.hashFile())) {
            valid = false;
        }
        if (!valid) {
            this.cache = null;
        }
        return valid;
    }

    @Nullable
    public T getCache() {
        return this.cache;
    }

    @Nullable
    private HashCode hashFile() {
        try {
            return Files.asByteSource(this.file).hash(Hashing.crc32());
        }
        catch (IOException e2) {
            return null;
        }
    }

    public File getFile() {
        return this.file;
    }
}


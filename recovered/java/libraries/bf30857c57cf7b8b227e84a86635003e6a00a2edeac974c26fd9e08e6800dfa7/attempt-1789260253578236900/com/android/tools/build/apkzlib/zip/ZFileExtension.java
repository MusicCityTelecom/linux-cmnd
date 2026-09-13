/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.utils.IOExceptionRunnable;
import com.android.tools.build.apkzlib.zip.StoredEntry;
import java.io.IOException;
import javax.annotation.Nullable;

public abstract class ZFileExtension {
    @Nullable
    public IOExceptionRunnable open() throws IOException {
        return null;
    }

    @Nullable
    public IOExceptionRunnable beforeUpdate() throws IOException {
        return null;
    }

    public void entriesWritten() throws IOException {
    }

    public void updated() throws IOException {
    }

    public void closed() {
    }

    @Nullable
    public IOExceptionRunnable added(StoredEntry entry, @Nullable StoredEntry replaced) {
        return null;
    }

    @Nullable
    public IOExceptionRunnable removed(StoredEntry entry) {
        return null;
    }
}


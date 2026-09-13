/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.TemporaryDirectory;
import java.io.File;
import java.io.IOException;

public interface TemporaryDirectoryFactory {
    public TemporaryDirectory make() throws IOException;

    public static TemporaryDirectoryFactory fixed(File directory) {
        return () -> TemporaryDirectory.fixed(directory);
    }
}


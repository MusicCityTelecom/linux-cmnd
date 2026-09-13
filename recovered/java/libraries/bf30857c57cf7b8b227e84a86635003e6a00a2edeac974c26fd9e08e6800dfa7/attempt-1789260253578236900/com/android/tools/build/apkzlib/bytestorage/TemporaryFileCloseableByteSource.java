/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.bytestorage.TemporaryFile;
import com.android.tools.build.apkzlib.zip.utils.CloseableDelegateByteSource;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;

class TemporaryFileCloseableByteSource
extends CloseableDelegateByteSource {
    private final TemporaryFile temporaryFile;
    private final Runnable closeCallback;

    TemporaryFileCloseableByteSource(File file, Runnable closeCallback) {
        super(Files.asByteSource(file), file.length());
        this.temporaryFile = new TemporaryFile(file);
        this.closeCallback = closeCallback;
    }

    @Override
    protected synchronized void innerClose() throws IOException {
        super.innerClose();
        this.temporaryFile.close();
        this.closeCallback.run();
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.io.Closer;
import java.io.Closeable;
import java.io.IOException;

public class ProcessedAndRawByteSources
implements Closeable {
    private final CloseableByteSource processedSource;
    private final CloseableByteSource rawSource;

    public ProcessedAndRawByteSources(CloseableByteSource processedSource, CloseableByteSource rawSource) {
        this.processedSource = processedSource;
        this.rawSource = rawSource;
    }

    public CloseableByteSource getProcessedByteSource() {
        return this.processedSource;
    }

    public CloseableByteSource getRawByteSource() {
        return this.rawSource;
    }

    @Override
    public void close() throws IOException {
        Closer closer = Closer.create();
        closer.register(this.processedSource);
        closer.register(this.rawSource);
        closer.close();
    }
}


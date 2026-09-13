/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.bytestorage;

import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import java.io.IOException;
import java.io.OutputStream;

public abstract class CloseableByteSourceFromOutputStreamBuilder
extends OutputStream {
    public abstract CloseableByteSource build() throws IOException;
}


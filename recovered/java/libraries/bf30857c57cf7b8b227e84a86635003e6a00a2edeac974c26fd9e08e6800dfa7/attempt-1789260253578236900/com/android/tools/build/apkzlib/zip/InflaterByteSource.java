/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterByteSource
extends CloseableByteSource {
    private final CloseableByteSource deflatedSource;

    public InflaterByteSource(CloseableByteSource byteSource) {
        this.deflatedSource = byteSource;
    }

    @Override
    public InputStream openStream() throws IOException {
        ByteArrayInputStream hackByte = new ByteArrayInputStream(new byte[]{0});
        return new InflaterInputStream(new SequenceInputStream(this.deflatedSource.openStream(), hackByte), new Inflater(true));
    }

    @Override
    public void innerClose() throws IOException {
        this.deflatedSource.close();
    }
}


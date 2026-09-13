/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.util;

import com.android.apksig.util.DataSink;
import java.io.IOException;
import java.nio.ByteBuffer;

public interface DataSource {
    public long size();

    public void feed(long var1, long var3, DataSink var5) throws IOException;

    public ByteBuffer getByteBuffer(long var1, int var3) throws IOException;

    public void copyTo(long var1, int var3, ByteBuffer var4) throws IOException;

    public DataSource slice(long var1, long var3);
}


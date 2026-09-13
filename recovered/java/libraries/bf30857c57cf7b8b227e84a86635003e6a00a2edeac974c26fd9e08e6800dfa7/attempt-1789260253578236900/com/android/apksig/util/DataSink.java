/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.util;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface DataSink {
    public void consume(byte[] var1, int var2, int var3) throws IOException;

    public void consume(ByteBuffer var1) throws IOException;
}


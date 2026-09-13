/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.util.DataSink;
import java.io.IOException;
import java.nio.ByteBuffer;

public class TeeDataSink
implements DataSink {
    private final DataSink[] mSinks;

    public TeeDataSink(DataSink[] sinks) {
        this.mSinks = sinks;
    }

    @Override
    public void consume(byte[] buf, int offset, int length) throws IOException {
        for (DataSink sink : this.mSinks) {
            sink.consume(buf, offset, length);
        }
    }

    @Override
    public void consume(ByteBuffer buf) throws IOException {
        int originalPosition = buf.position();
        for (int i2 = 0; i2 < this.mSinks.length; ++i2) {
            if (i2 > 0) {
                buf.position(originalPosition);
            }
            this.mSinks[i2].consume(buf);
        }
    }
}


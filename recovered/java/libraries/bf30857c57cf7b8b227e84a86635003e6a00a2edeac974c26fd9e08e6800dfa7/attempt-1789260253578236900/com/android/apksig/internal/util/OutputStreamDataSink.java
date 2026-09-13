/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.util.DataSink;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class OutputStreamDataSink
implements DataSink {
    private static final int MAX_READ_CHUNK_SIZE = 65536;
    private final OutputStream mOut;

    public OutputStreamDataSink(OutputStream out) {
        if (out == null) {
            throw new NullPointerException("out == null");
        }
        this.mOut = out;
    }

    public OutputStream getOutputStream() {
        return this.mOut;
    }

    @Override
    public void consume(byte[] buf, int offset, int length) throws IOException {
        this.mOut.write(buf, offset, length);
    }

    @Override
    public void consume(ByteBuffer buf) throws IOException {
        if (!buf.hasRemaining()) {
            return;
        }
        if (buf.hasArray()) {
            this.mOut.write(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
            buf.position(buf.limit());
        } else {
            byte[] tmp = new byte[Math.min(buf.remaining(), 65536)];
            while (buf.hasRemaining()) {
                int chunkSize = Math.min(buf.remaining(), tmp.length);
                buf.get(tmp, 0, chunkSize);
                this.mOut.write(tmp, 0, chunkSize);
            }
        }
    }
}


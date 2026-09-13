/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.util;

import com.android.apksig.util.DataSink;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class MessageDigestSink
implements DataSink {
    private final MessageDigest[] mMessageDigests;

    public MessageDigestSink(MessageDigest[] digests) {
        this.mMessageDigests = digests;
    }

    @Override
    public void consume(byte[] buf, int offset, int length) {
        for (MessageDigest md : this.mMessageDigests) {
            md.update(buf, offset, length);
        }
    }

    @Override
    public void consume(ByteBuffer buf) {
        int originalPosition = buf.position();
        for (MessageDigest md : this.mMessageDigests) {
            buf.position(originalPosition);
            md.update(buf);
        }
    }
}


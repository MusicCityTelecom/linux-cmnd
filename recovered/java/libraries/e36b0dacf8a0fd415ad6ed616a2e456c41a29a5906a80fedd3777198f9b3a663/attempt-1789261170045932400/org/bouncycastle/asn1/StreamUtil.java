/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.LimitedInputStream;

class StreamUtil {
    private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();

    StreamUtil() {
    }

    static int findLimit(InputStream inputStream) {
        if (inputStream instanceof LimitedInputStream) {
            return ((LimitedInputStream)inputStream).getLimit();
        }
        if (inputStream instanceof ASN1InputStream) {
            return ((ASN1InputStream)inputStream).getLimit();
        }
        if (inputStream instanceof ByteArrayInputStream) {
            return ((ByteArrayInputStream)inputStream).available();
        }
        if (inputStream instanceof FileInputStream) {
            try {
                long l;
                FileChannel fileChannel = ((FileInputStream)inputStream).getChannel();
                long l2 = l = fileChannel != null ? fileChannel.size() : Integer.MAX_VALUE;
                if (l < Integer.MAX_VALUE) {
                    return (int)l;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (MAX_MEMORY > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)MAX_MEMORY;
    }
}


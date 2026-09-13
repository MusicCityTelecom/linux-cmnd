/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

public final class RandomAccessFileUtils {
    private RandomAccessFileUtils() {
    }

    public static void fullyRead(RandomAccessFile raf, byte[] data) throws IOException {
        int r3;
        int p3 = 0;
        while ((r3 = raf.read(data, p3, data.length - p3)) > 0 && (p3 += r3) != data.length) {
        }
        if (p3 < data.length) {
            throw new IOException("Failed to read " + data.length + " bytes from file. Only " + p3 + " bytes could be read.");
        }
    }
}


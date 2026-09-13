/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ByteBufferUtil {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ByteBuffer mapFile(File f2, long offset, ByteOrder byteOrder) throws IOException {
        try (FileInputStream dataFile = new FileInputStream(f2);){
            FileChannel fc = dataFile.getChannel();
            MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, offset, f2.length() - offset);
            buffer.order(byteOrder);
            MappedByteBuffer mappedByteBuffer = buffer;
            return mappedByteBuffer;
        }
    }

    public static String getString(ByteBuffer buf, int len) {
        char[] data = new char[len];
        for (int i2 = 0; i2 < len; ++i2) {
            data[i2] = buf.getChar();
        }
        return new String(data);
    }

    public static void putString(ByteBuffer buf, String str) {
        int len = str.length();
        for (int i2 = 0; i2 < len; ++i2) {
            buf.putChar(str.charAt(i2));
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import java.nio.ByteBuffer;

public class JdwpHandshake {
    public static final int HANDSHAKE_GOOD = 1;
    public static final int HANDSHAKE_NOTYET = 2;
    public static final int HANDSHAKE_BAD = 3;
    private static final byte[] HANDSHAKE = new byte[]{74, 68, 87, 80, 45, 72, 97, 110, 100, 115, 104, 97, 107, 101};
    public static final int HANDSHAKE_LEN = HANDSHAKE.length;

    static int findHandshake(ByteBuffer buf) {
        int count = buf.position();
        if (count < HANDSHAKE.length) {
            return 2;
        }
        for (int i2 = HANDSHAKE.length - 1; i2 >= 0; --i2) {
            if (buf.get(i2) == HANDSHAKE[i2]) continue;
            return 3;
        }
        return 1;
    }

    static void consumeHandshake(ByteBuffer buf) {
        buf.flip();
        buf.position(HANDSHAKE.length);
        buf.compact();
    }

    static void putHandshake(ByteBuffer buf) {
        buf.put(HANDSHAKE);
    }
}


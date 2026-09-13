/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;
import org.cryptacular.EncodingException;
import org.cryptacular.codec.Decoder;

public class HexDecoder
implements Decoder {
    private static final byte[] DECODING_TABLE;
    private int count;

    @Override
    public void decode(CharBuffer input, ByteBuffer output) throws EncodingException {
        if (input.get(0) == '0' && input.get(1) == 'x') {
            input.position(input.position() + 2);
        }
        byte hi = 0;
        while (input.hasRemaining()) {
            char current = input.get();
            if (current == ':' || Character.isWhitespace(current)) continue;
            if ((this.count++ & 1) == 0) {
                hi = HexDecoder.lookup(current);
                continue;
            }
            byte lo = HexDecoder.lookup(current);
            output.put((byte)(hi << 4 | lo));
        }
    }

    @Override
    public void finalize(ByteBuffer output) throws EncodingException {
        this.count = 0;
    }

    @Override
    public int outputSize(int inputSize) {
        return inputSize / 2;
    }

    private static byte lookup(char c) {
        byte b = DECODING_TABLE[c & 0x7F];
        if (b < 0) {
            throw new EncodingException("Invalid hex character " + c);
        }
        return b;
    }

    static {
        int i;
        DECODING_TABLE = new byte[128];
        Arrays.fill(DECODING_TABLE, (byte)-1);
        for (i = 0; i < 10; ++i) {
            HexDecoder.DECODING_TABLE[i + 48] = (byte)i;
        }
        for (i = 0; i < 6; ++i) {
            HexDecoder.DECODING_TABLE[i + 65] = (byte)(10 + i);
            HexDecoder.DECODING_TABLE[i + 97] = (byte)(10 + i);
        }
    }
}


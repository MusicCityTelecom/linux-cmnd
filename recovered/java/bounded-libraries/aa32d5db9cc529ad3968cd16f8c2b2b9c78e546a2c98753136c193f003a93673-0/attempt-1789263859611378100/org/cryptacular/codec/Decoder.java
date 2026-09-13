/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;

public interface Decoder {
    public void decode(CharBuffer var1, ByteBuffer var2) throws EncodingException;

    public void finalize(ByteBuffer var1) throws EncodingException;

    public int outputSize(int var1);
}


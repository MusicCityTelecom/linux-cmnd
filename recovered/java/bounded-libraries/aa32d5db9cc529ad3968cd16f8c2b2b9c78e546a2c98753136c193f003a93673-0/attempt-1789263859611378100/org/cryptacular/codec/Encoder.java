/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;

public interface Encoder {
    public void encode(ByteBuffer var1, CharBuffer var2) throws EncodingException;

    public void finalize(CharBuffer var1) throws EncodingException;

    public int outputSize(int var1);
}


/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import org.cryptacular.codec.Decoder;
import org.cryptacular.codec.Encoder;

public interface Codec {
    public Encoder getEncoder();

    public Decoder getDecoder();

    public Encoder newEncoder();

    public Decoder newDecoder();
}


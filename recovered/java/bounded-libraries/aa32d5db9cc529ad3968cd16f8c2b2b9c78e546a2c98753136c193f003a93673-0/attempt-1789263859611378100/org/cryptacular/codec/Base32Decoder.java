/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import org.cryptacular.codec.AbstractBaseNDecoder;

public class Base32Decoder
extends AbstractBaseNDecoder {
    private static final byte[] DECODING_TABLE = Base32Decoder.decodingTable("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", 32);

    public Base32Decoder() {
        super(DECODING_TABLE);
    }

    public Base32Decoder(String alphabet) {
        super(Base32Decoder.decodingTable(alphabet, 32));
    }

    @Override
    protected int getBlockLength() {
        return 40;
    }

    @Override
    protected int getBitsPerChar() {
        return 5;
    }
}


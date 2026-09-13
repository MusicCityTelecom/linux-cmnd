/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import org.cryptacular.codec.AbstractBaseNEncoder;

public class Base32Encoder
extends AbstractBaseNEncoder {
    private static final char[] ENCODING_TABLE = Base32Encoder.encodingTable("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", 32);

    public Base32Encoder() {
        this(-1);
    }

    public Base32Encoder(int charactersPerLine) {
        super(ENCODING_TABLE, charactersPerLine);
    }

    public Base32Encoder(String alphabet) {
        this(alphabet, -1);
    }

    public Base32Encoder(String alphabet, int charactersPerLine) {
        super(Base32Encoder.encodingTable(alphabet, 32), charactersPerLine);
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


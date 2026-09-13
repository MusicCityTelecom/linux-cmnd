/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import org.cryptacular.codec.Codec;
import org.cryptacular.codec.Decoder;
import org.cryptacular.codec.Encoder;
import org.cryptacular.codec.HexDecoder;
import org.cryptacular.codec.HexEncoder;

public class HexCodec
implements Codec {
    private final Encoder encoder;
    private final Decoder decoder = new HexDecoder();
    private final boolean uppercase;

    public HexCodec() {
        this(false);
    }

    public HexCodec(boolean uppercaseOutput) {
        this.uppercase = uppercaseOutput;
        this.encoder = new HexEncoder(false, this.uppercase);
    }

    @Override
    public Encoder getEncoder() {
        return this.encoder;
    }

    @Override
    public Decoder getDecoder() {
        return this.decoder;
    }

    @Override
    public Encoder newEncoder() {
        return new HexEncoder(false, this.uppercase);
    }

    @Override
    public Decoder newDecoder() {
        return new HexDecoder();
    }
}


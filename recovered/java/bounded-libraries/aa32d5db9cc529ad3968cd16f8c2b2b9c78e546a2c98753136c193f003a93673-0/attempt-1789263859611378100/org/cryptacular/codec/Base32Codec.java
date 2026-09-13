/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import org.cryptacular.codec.Base32Decoder;
import org.cryptacular.codec.Base32Encoder;
import org.cryptacular.codec.Codec;
import org.cryptacular.codec.Decoder;
import org.cryptacular.codec.Encoder;

public class Base32Codec
implements Codec {
    private final Encoder encoder;
    private final Decoder decoder;
    private final String customAlphabet;
    private final boolean padding;

    public Base32Codec() {
        this.encoder = new Base32Encoder();
        this.decoder = new Base32Decoder();
        this.customAlphabet = null;
        this.padding = true;
    }

    public Base32Codec(String alphabet) {
        this(alphabet, true);
    }

    public Base32Codec(String alphabet, boolean inputOutputPadding) {
        this.customAlphabet = alphabet;
        this.padding = inputOutputPadding;
        this.encoder = this.newEncoder();
        this.decoder = this.newDecoder();
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
        Base32Encoder encoder = this.customAlphabet != null ? new Base32Encoder(this.customAlphabet) : new Base32Encoder();
        encoder.setPaddedOutput(this.padding);
        return encoder;
    }

    @Override
    public Decoder newDecoder() {
        Base32Decoder decoder = this.customAlphabet != null ? new Base32Decoder(this.customAlphabet) : new Base32Decoder();
        decoder.setPaddedInput(this.padding);
        return decoder;
    }
}


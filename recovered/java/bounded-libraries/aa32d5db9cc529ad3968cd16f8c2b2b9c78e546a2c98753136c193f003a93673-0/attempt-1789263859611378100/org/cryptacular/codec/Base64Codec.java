/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import org.cryptacular.codec.Base64Decoder;
import org.cryptacular.codec.Base64Encoder;
import org.cryptacular.codec.Codec;
import org.cryptacular.codec.Decoder;
import org.cryptacular.codec.Encoder;

public class Base64Codec
implements Codec {
    private final Encoder encoder;
    private final Decoder decoder;
    private final String customAlphabet;
    private final boolean padding;

    public Base64Codec() {
        this.encoder = new Base64Encoder();
        this.decoder = new Base64Decoder();
        this.customAlphabet = null;
        this.padding = true;
    }

    public Base64Codec(String alphabet) {
        this(alphabet, true);
    }

    public Base64Codec(String alphabet, boolean inputOutputPadding) {
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
        Base64Encoder encoder = this.customAlphabet != null ? new Base64Encoder(this.customAlphabet) : new Base64Encoder();
        encoder.setPaddedOutput(this.padding);
        return encoder;
    }

    @Override
    public Decoder newDecoder() {
        Base64Decoder decoder = this.customAlphabet != null ? new Base64Decoder(this.customAlphabet) : new Base64Decoder();
        decoder.setPaddedInput(this.padding);
        return decoder;
    }
}


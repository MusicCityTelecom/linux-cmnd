/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import org.cryptacular.codec.AbstractBaseNDecoder;

public class Base64Decoder
extends AbstractBaseNDecoder {
    private static final byte[] DEFAULT_DECODING_TABLE = Base64Decoder.decodingTable("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", 64);
    private static final byte[] URLSAFE_DECODING_TABLE = Base64Decoder.decodingTable("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", 64);

    public Base64Decoder() {
        this(false);
    }

    public Base64Decoder(boolean urlSafe) {
        super(urlSafe ? URLSAFE_DECODING_TABLE : DEFAULT_DECODING_TABLE);
    }

    public Base64Decoder(String alphabet) {
        super(Base64Decoder.decodingTable(alphabet, 64));
    }

    @Override
    protected int getBlockLength() {
        return 24;
    }

    @Override
    protected int getBitsPerChar() {
        return 6;
    }

    public static class Builder {
        private boolean urlSafe;
        private String alphabet;
        private boolean padding;

        public Builder setUrlSafe(boolean safe) {
            this.urlSafe = safe;
            return this;
        }

        public Builder setAlphabet(String alpha) {
            this.alphabet = alpha;
            return this;
        }

        public Builder setPadding(boolean pad) {
            this.padding = pad;
            return this;
        }

        public Base64Decoder build() {
            Base64Decoder decoder = this.alphabet != null ? new Base64Decoder(this.alphabet) : new Base64Decoder(this.urlSafe);
            decoder.setPaddedInput(this.padding);
            return decoder;
        }
    }
}


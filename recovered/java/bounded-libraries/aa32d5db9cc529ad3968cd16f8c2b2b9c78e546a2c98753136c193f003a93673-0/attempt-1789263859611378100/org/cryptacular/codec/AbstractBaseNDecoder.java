/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.cryptacular.EncodingException;
import org.cryptacular.codec.Decoder;

public abstract class AbstractBaseNDecoder
implements Decoder {
    private final char[] block = new char[this.getBlockLength() / this.getBitsPerChar()];
    private final byte[] table;
    private int blockPos;
    private boolean paddedInput = true;

    public AbstractBaseNDecoder(byte[] decodingTable) {
        this.table = decodingTable;
    }

    public boolean isPaddedInput() {
        return this.paddedInput;
    }

    public void setPaddedInput(boolean enabled) {
        this.paddedInput = enabled;
    }

    @Override
    public void decode(CharBuffer input, ByteBuffer output) throws EncodingException {
        while (input.hasRemaining()) {
            char current = input.get();
            if (Character.isWhitespace(current) || current == '=') continue;
            this.block[this.blockPos++] = current;
            if (this.blockPos != this.block.length) continue;
            this.writeOutput(output, this.block.length);
        }
    }

    @Override
    public void finalize(ByteBuffer output) throws EncodingException {
        if (this.blockPos > 0) {
            this.writeOutput(output, this.blockPos);
        }
    }

    @Override
    public int outputSize(int inputSize) {
        int size = this.paddedInput ? inputSize : inputSize + this.getBlockLength() / 8 - 1;
        return size * this.getBitsPerChar() / 8;
    }

    protected abstract int getBlockLength();

    protected abstract int getBitsPerChar();

    protected static byte[] decodingTable(String alphabet, int n) {
        if (alphabet.length() != n) {
            throw new IllegalArgumentException("Alphabet must be exactly " + n + " characters long");
        }
        byte[] decodingTable = new byte[128];
        for (int i = 0; i < n; ++i) {
            decodingTable[alphabet.charAt((int)i)] = (byte)i;
        }
        return decodingTable;
    }

    private void writeOutput(ByteBuffer output, int len) {
        long value = 0L;
        int shift = this.getBlockLength();
        for (int i = 0; i < len; ++i) {
            long b = this.table[this.block[i] & 0x7F];
            if (b < 0L) {
                throw new EncodingException("Invalid character " + this.block[i]);
            }
            value |= b << (shift -= this.getBitsPerChar());
        }
        int stop = shift + this.getBitsPerChar() - 1;
        int offset = this.getBlockLength();
        while (offset > stop) {
            output.put((byte)((value & 255L << (offset -= 8)) >> offset));
        }
        this.blockPos = 0;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.asn1.ber;

import com.android.apksig.internal.asn1.ber.BerDataValue;
import com.android.apksig.internal.asn1.ber.BerDataValueFormatException;
import com.android.apksig.internal.asn1.ber.BerDataValueReader;
import com.android.apksig.internal.asn1.ber.BerEncoding;
import java.nio.ByteBuffer;

public class ByteBufferBerDataValueReader
implements BerDataValueReader {
    private final ByteBuffer mBuf;

    public ByteBufferBerDataValueReader(ByteBuffer buf) {
        if (buf == null) {
            throw new NullPointerException("buf == null");
        }
        this.mBuf = buf;
    }

    @Override
    public BerDataValue readDataValue() throws BerDataValueFormatException {
        int contentsOffsetInTag;
        int contentsLength;
        int startPosition = this.mBuf.position();
        if (!this.mBuf.hasRemaining()) {
            return null;
        }
        byte firstIdentifierByte = this.mBuf.get();
        int tagNumber = this.readTagNumber(firstIdentifierByte);
        boolean constructed = BerEncoding.isConstructed(firstIdentifierByte);
        if (!this.mBuf.hasRemaining()) {
            throw new BerDataValueFormatException("Missing length");
        }
        int firstLengthByte = this.mBuf.get() & 0xFF;
        if ((firstLengthByte & 0x80) == 0) {
            contentsLength = this.readShortFormLength(firstLengthByte);
            contentsOffsetInTag = this.mBuf.position() - startPosition;
            this.skipDefiniteLengthContents(contentsLength);
        } else if (firstLengthByte != 128) {
            contentsLength = this.readLongFormLength(firstLengthByte);
            contentsOffsetInTag = this.mBuf.position() - startPosition;
            this.skipDefiniteLengthContents(contentsLength);
        } else {
            contentsOffsetInTag = this.mBuf.position() - startPosition;
            contentsLength = constructed ? this.skipConstructedIndefiniteLengthContents() : this.skipPrimitiveIndefiniteLengthContents();
        }
        int endPosition = this.mBuf.position();
        this.mBuf.position(startPosition);
        int bufOriginalLimit = this.mBuf.limit();
        this.mBuf.limit(endPosition);
        ByteBuffer encoded = this.mBuf.slice();
        this.mBuf.position(this.mBuf.limit());
        this.mBuf.limit(bufOriginalLimit);
        encoded.position(contentsOffsetInTag);
        encoded.limit(contentsOffsetInTag + contentsLength);
        ByteBuffer encodedContents = encoded.slice();
        encoded.clear();
        return new BerDataValue(encoded, encodedContents, BerEncoding.getTagClass(firstIdentifierByte), constructed, tagNumber);
    }

    private int readTagNumber(byte firstIdentifierByte) throws BerDataValueFormatException {
        int tagNumber = BerEncoding.getTagNumber(firstIdentifierByte);
        if (tagNumber == 31) {
            return this.readHighTagNumber();
        }
        return tagNumber;
    }

    private int readHighTagNumber() throws BerDataValueFormatException {
        byte b2;
        int result = 0;
        do {
            if (!this.mBuf.hasRemaining()) {
                throw new BerDataValueFormatException("Truncated tag number");
            }
            b2 = this.mBuf.get();
            if (result > 0xFFFFFF) {
                throw new BerDataValueFormatException("Tag number too large");
            }
            result <<= 7;
            result |= b2 & 0x7F;
        } while ((b2 & 0x80) != 0);
        return result;
    }

    private int readShortFormLength(int firstLengthByte) {
        return firstLengthByte & 0x7F;
    }

    private int readLongFormLength(int firstLengthByte) throws BerDataValueFormatException {
        int byteCount = firstLengthByte & 0x7F;
        if (byteCount > 4) {
            throw new BerDataValueFormatException("Length too large: " + byteCount + " bytes");
        }
        int result = 0;
        for (int i2 = 0; i2 < byteCount; ++i2) {
            if (!this.mBuf.hasRemaining()) {
                throw new BerDataValueFormatException("Truncated length");
            }
            byte b2 = this.mBuf.get();
            if (result > 0x7FFFFF) {
                throw new BerDataValueFormatException("Length too large");
            }
            result <<= 8;
            result |= b2 & 0xFF;
        }
        return result;
    }

    private void skipDefiniteLengthContents(int contentsLength) throws BerDataValueFormatException {
        if (this.mBuf.remaining() < contentsLength) {
            throw new BerDataValueFormatException("Truncated contents. Need: " + contentsLength + " bytes, available: " + this.mBuf.remaining());
        }
        this.mBuf.position(this.mBuf.position() + contentsLength);
    }

    private int skipPrimitiveIndefiniteLengthContents() throws BerDataValueFormatException {
        boolean prevZeroByte = false;
        int bytesRead = 0;
        while (true) {
            if (!this.mBuf.hasRemaining()) {
                throw new BerDataValueFormatException("Truncated indefinite-length contents: " + bytesRead + " bytes read");
            }
            byte b2 = this.mBuf.get();
            if (++bytesRead < 0) {
                throw new BerDataValueFormatException("Indefinite-length contents too long");
            }
            if (b2 == 0) {
                if (prevZeroByte) {
                    return bytesRead - 2;
                }
                prevZeroByte = true;
                continue;
            }
            prevZeroByte = false;
        }
    }

    private int skipConstructedIndefiniteLengthContents() throws BerDataValueFormatException {
        int startPos = this.mBuf.position();
        while (this.mBuf.hasRemaining()) {
            if (this.mBuf.remaining() > 1 && this.mBuf.getShort(this.mBuf.position()) == 0) {
                int contentsLength = this.mBuf.position() - startPos;
                this.mBuf.position(this.mBuf.position() + 2);
                return contentsLength;
            }
            this.readDataValue();
        }
        throw new BerDataValueFormatException("Truncated indefinite-length contents: " + (this.mBuf.position() - startPos) + " bytes read");
    }
}


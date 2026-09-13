/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.asn1.ber;

import com.android.apksig.internal.asn1.ber.BerDataValueFormatException;
import com.android.apksig.internal.asn1.ber.BerDataValueReader;
import com.android.apksig.internal.asn1.ber.ByteBufferBerDataValueReader;
import java.nio.ByteBuffer;

public class BerDataValue {
    private final ByteBuffer mEncoded;
    private final ByteBuffer mEncodedContents;
    private final int mTagClass;
    private final boolean mConstructed;
    private final int mTagNumber;

    BerDataValue(ByteBuffer encoded, ByteBuffer encodedContents, int tagClass, boolean constructed, int tagNumber) {
        this.mEncoded = encoded;
        this.mEncodedContents = encodedContents;
        this.mTagClass = tagClass;
        this.mConstructed = constructed;
        this.mTagNumber = tagNumber;
    }

    public int getTagClass() {
        return this.mTagClass;
    }

    public boolean isConstructed() {
        return this.mConstructed;
    }

    public int getTagNumber() {
        return this.mTagNumber;
    }

    public ByteBuffer getEncoded() {
        return this.mEncoded.slice();
    }

    public ByteBuffer getEncodedContents() {
        return this.mEncodedContents.slice();
    }

    public BerDataValueReader contentsReader() {
        return new ByteBufferBerDataValueReader(this.getEncodedContents());
    }

    public BerDataValueReader dataValueReader() {
        return new ParsedValueReader(this);
    }

    private static final class ParsedValueReader
    implements BerDataValueReader {
        private final BerDataValue mValue;
        private boolean mValueOutput;

        public ParsedValueReader(BerDataValue value) {
            this.mValue = value;
        }

        @Override
        public BerDataValue readDataValue() throws BerDataValueFormatException {
            if (this.mValueOutput) {
                return null;
            }
            this.mValueOutput = true;
            return this.mValue;
        }
    }
}


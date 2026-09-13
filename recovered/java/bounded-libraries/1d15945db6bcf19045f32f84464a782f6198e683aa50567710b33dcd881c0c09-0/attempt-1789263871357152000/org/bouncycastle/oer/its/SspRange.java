/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Choice
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Null
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERNull
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.oer.its;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.BitmapSspRange;
import org.bouncycastle.oer.its.SequenceOfOctetString;

public class SspRange
extends ASN1Object
implements ASN1Choice {
    private static final int opaque = 0;
    private static final int all = 1;
    private static final int extension = 2;
    private static final int bitmapSspRange = 3;
    private final int choice;
    private final ASN1Encodable value;

    public SspRange(int n, ASN1Encodable aSN1Encodable) {
        switch (n) {
            case 0: {
                if (aSN1Encodable instanceof SequenceOfOctetString) break;
                throw new IllegalArgumentException("value is not SequenceOfOctetString");
            }
            case 1: {
                if (aSN1Encodable instanceof ASN1Null) break;
                throw new IllegalArgumentException("value is not ASN1Null");
            }
            case 2: {
                if (aSN1Encodable instanceof ASN1OctetString) break;
                throw new IllegalArgumentException("value is not ASN1OctetString");
            }
            case 3: {
                if (aSN1Encodable instanceof BitmapSspRange) break;
                throw new IllegalArgumentException("value is not BitmapSspRange");
            }
        }
        this.choice = n;
        this.value = aSN1Encodable;
    }

    public static SspRange getInstance(Object object) {
        if (object instanceof SspRange) {
            return (SspRange)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        int n = aSN1TaggedObject.getTagNo();
        switch (n) {
            case 0: {
                return new SspRange(0, (ASN1Encodable)SequenceOfOctetString.getInstance(aSN1TaggedObject.getObject()));
            }
            case 1: {
                return new SspRange(1, (ASN1Encodable)DERNull.INSTANCE);
            }
            case 2: {
                try {
                    return new SspRange(2, (ASN1Encodable)new DEROctetString(aSN1TaggedObject.getObject().getEncoded()));
                }
                catch (IOException iOException) {
                    throw new RuntimeException(iOException.getMessage(), iOException);
                }
            }
            case 3: {
                return new SspRange(3, (ASN1Encodable)BitmapSspRange.getInstance(aSN1TaggedObject.getObject()));
            }
        }
        throw new IllegalStateException("unknown choice " + n);
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value);
    }

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        public Builder setChoice(int n) {
            this.choice = n;
            return this;
        }

        public Builder setValue(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }

        public Builder opaque(SequenceOfOctetString sequenceOfOctetString) {
            this.value = sequenceOfOctetString;
            this.choice = 0;
            return this;
        }

        public Builder all() {
            this.value = DERNull.INSTANCE;
            this.choice = 0;
            return this;
        }

        public Builder extension(byte[] byArray) {
            this.value = new DEROctetString(byArray);
            this.choice = 2;
            return this;
        }

        public Builder bitmapSSPRange(BitmapSspRange bitmapSspRange) {
            this.value = bitmapSspRange;
            this.choice = 3;
            return this;
        }

        public SspRange createSspRange() {
            return new SspRange(this.choice, this.value);
        }
    }
}


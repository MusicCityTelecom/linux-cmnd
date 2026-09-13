/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Null
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERNull
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.util.Arrays
 *  org.bouncycastle.util.BigIntegers
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.EccCurvePoint;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

public class EccP384CurvePoint
extends EccCurvePoint {
    public static final int xOnly = 0;
    public static final int fill = 1;
    public static final int compressedY0 = 2;
    public static final int compressedY1 = 3;
    public static final int uncompressedP384 = 4;
    private final int choice;
    private final ASN1Encodable value;

    public EccP384CurvePoint(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.value = aSN1Encodable;
    }

    public static EccP384CurvePoint getInstance(Object object) {
        ASN1Null aSN1Null;
        if (object instanceof EccP384CurvePoint) {
            return (EccP384CurvePoint)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 1: {
                aSN1Null = ASN1Null.getInstance((Object)aSN1TaggedObject.getObject());
                break;
            }
            case 0: 
            case 2: 
            case 3: {
                aSN1Null = ASN1OctetString.getInstance((Object)aSN1TaggedObject.getObject());
                break;
            }
            case 4: {
                aSN1Null = ASN1Sequence.getInstance((Object)aSN1TaggedObject.getObject());
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown tag " + aSN1TaggedObject.getTagNo());
            }
        }
        return new Builder().setChoice(aSN1TaggedObject.getTagNo()).setValue((ASN1Encodable)aSN1Null).createEccP384CurvePoint();
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getValue() {
        return this.value;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value);
    }

    public byte[] getEncodedPoint() {
        byte[] byArray;
        switch (this.choice) {
            case 2: {
                byte[] byArray2 = DEROctetString.getInstance((Object)this.value).getOctets();
                byArray = new byte[byArray2.length + 1];
                byArray[0] = 2;
                System.arraycopy(byArray2, 0, byArray, 1, byArray2.length);
                break;
            }
            case 3: {
                byte[] byArray3 = DEROctetString.getInstance((Object)this.value).getOctets();
                byArray = new byte[byArray3.length + 1];
                byArray[0] = 3;
                System.arraycopy(byArray3, 0, byArray, 1, byArray3.length);
                break;
            }
            case 4: {
                ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)this.value);
                byte[] byArray4 = DEROctetString.getInstance((Object)aSN1Sequence.getObjectAt(0)).getOctets();
                byte[] byArray5 = DEROctetString.getInstance((Object)aSN1Sequence.getObjectAt(1)).getOctets();
                byArray = Arrays.concatenate((byte[])new byte[]{4}, (byte[])byArray4, (byte[])byArray5);
                break;
            }
            case 0: {
                throw new IllegalStateException("x Only not implemented");
            }
            default: {
                throw new IllegalStateException("unknown point choice");
            }
        }
        return byArray;
    }

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        Builder setChoice(int n) {
            this.choice = n;
            return this;
        }

        Builder setValue(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }

        public EccP384CurvePoint createXOnly(BigInteger bigInteger) {
            this.choice = 0;
            this.value = new DEROctetString(BigIntegers.asUnsignedByteArray((BigInteger)bigInteger));
            return this.createEccP384CurvePoint();
        }

        public EccP384CurvePoint createFill() {
            this.choice = 1;
            this.value = DERNull.INSTANCE;
            return this.createEccP384CurvePoint();
        }

        public EccP384CurvePoint createCompressedY0(BigInteger bigInteger) {
            this.choice = 2;
            throw new IllegalStateException("not fully implemented.");
        }

        public EccP384CurvePoint createCompressedY1(BigInteger bigInteger) {
            this.choice = 3;
            throw new IllegalStateException("not fully implemented.");
        }

        public EccP384CurvePoint createUncompressedP384(BigInteger bigInteger, BigInteger bigInteger2) {
            this.choice = 4;
            this.value = new DERSequence(new ASN1Encodable[]{new DEROctetString(BigIntegers.asUnsignedByteArray((int)48, (BigInteger)bigInteger)), new DEROctetString(BigIntegers.asUnsignedByteArray((int)48, (BigInteger)bigInteger2))});
            return this.createEccP384CurvePoint();
        }

        private EccP384CurvePoint createEccP384CurvePoint() {
            return new EccP384CurvePoint(this.choice, this.value);
        }
    }
}


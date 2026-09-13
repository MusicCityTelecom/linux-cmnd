/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Choice
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.EccCurvePoint;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.EccP384CurvePoint;

public class PublicVerificationKey
extends ASN1Object
implements ASN1Choice {
    public static final int ecdsaNistP256 = 0;
    public static final int ecdsaBrainpoolP256r1 = 1;
    public static final int extension = 2;
    public static final int ecdsaBrainpoolP384r1 = 3;
    final int choice;
    final ASN1Encodable curvePoint;

    public PublicVerificationKey(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.curvePoint = aSN1Encodable;
    }

    public static PublicVerificationKey getInstance(Object object) {
        EccCurvePoint eccCurvePoint;
        if (object instanceof PublicVerificationKey) {
            return (PublicVerificationKey)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: 
            case 1: {
                eccCurvePoint = EccP256CurvePoint.getInstance(aSN1TaggedObject.getObject());
                break;
            }
            case 2: {
                eccCurvePoint = DEROctetString.getInstance((Object)aSN1TaggedObject.getObject());
                break;
            }
            case 3: {
                eccCurvePoint = EccP384CurvePoint.getInstance(aSN1TaggedObject.getObject());
                break;
            }
            default: {
                throw new IllegalArgumentException("unknown tag value " + aSN1TaggedObject.getTagNo());
            }
        }
        return new PublicVerificationKey(aSN1TaggedObject.getTagNo(), (ASN1Encodable)eccCurvePoint);
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getCurvePoint() {
        return this.curvePoint;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.curvePoint);
    }

    public static class Builder {
        private int choice;
        private ASN1Encodable curvePoint;

        public Builder setChoice(int n) {
            this.choice = n;
            return this;
        }

        public Builder setCurvePoint(EccCurvePoint eccCurvePoint) {
            this.curvePoint = eccCurvePoint;
            return this;
        }

        public Builder ecdsaNistP256(EccP256CurvePoint eccP256CurvePoint) {
            this.curvePoint = eccP256CurvePoint;
            return this;
        }

        public Builder ecdsaBrainpoolP256r1(EccP256CurvePoint eccP256CurvePoint) {
            this.curvePoint = eccP256CurvePoint;
            return this;
        }

        public Builder ecdsaBrainpoolP384r1(EccP384CurvePoint eccP384CurvePoint) {
            this.curvePoint = eccP384CurvePoint;
            return this;
        }

        public Builder extension(byte[] byArray) {
            this.curvePoint = new DEROctetString(byArray);
            return this;
        }

        public PublicVerificationKey createPublicVerificationKey() {
            return new PublicVerificationKey(this.choice, this.curvePoint);
        }
    }
}


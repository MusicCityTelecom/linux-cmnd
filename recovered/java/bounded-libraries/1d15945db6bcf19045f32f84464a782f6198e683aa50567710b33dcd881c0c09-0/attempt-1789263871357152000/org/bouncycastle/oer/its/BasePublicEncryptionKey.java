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

public class BasePublicEncryptionKey
extends ASN1Object
implements ASN1Choice {
    public static final int eciesNistP256 = 0;
    public static final int eciesBrainpoolP256r1 = 1;
    public static final int extension = 2;
    private final int choice;
    private final ASN1Encodable value;

    public BasePublicEncryptionKey(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.value = aSN1Encodable;
    }

    public static BasePublicEncryptionKey getInstance(Object object) {
        EccP256CurvePoint eccP256CurvePoint;
        if (object instanceof BasePublicEncryptionKey) {
            return (BasePublicEncryptionKey)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: 
            case 1: {
                eccP256CurvePoint = EccP256CurvePoint.getInstance(aSN1TaggedObject.getObject());
                break;
            }
            case 2: {
                eccP256CurvePoint = DEROctetString.getInstance((Object)aSN1TaggedObject.getObject());
                break;
            }
            default: {
                throw new IllegalStateException("unknown choice " + aSN1TaggedObject.getTagNo());
            }
        }
        return new BasePublicEncryptionKey(aSN1TaggedObject.getTagNo(), (ASN1Encodable)eccP256CurvePoint);
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

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        public Builder setChoice(int n) {
            this.choice = n;
            return this;
        }

        public Builder setValue(EccCurvePoint eccCurvePoint) {
            this.value = eccCurvePoint;
            return this;
        }

        public BasePublicEncryptionKey createBasePublicEncryptionKey() {
            return new BasePublicEncryptionKey(this.choice, this.value);
        }
    }
}


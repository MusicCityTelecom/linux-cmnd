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
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.PublicVerificationKey;

public class VerificationKeyIndicator
extends ASN1Object
implements ASN1Choice {
    public static final int verificationKey = 0;
    public static final int reconstructionValue = 1;
    public static final int extension = 2;
    private final int choice;
    private final ASN1Encodable object;

    public VerificationKeyIndicator(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.object = aSN1Encodable;
    }

    public static VerificationKeyIndicator getInstance(Object object) {
        if (object instanceof VerificationKeyIndicator) {
            return (VerificationKeyIndicator)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: {
                return new Builder().setChoice(0).setObject((ASN1Encodable)PublicVerificationKey.getInstance(aSN1TaggedObject.getObject())).createVerificationKeyIndicator();
            }
            case 1: {
                return new Builder().setChoice(1).setObject((ASN1Encodable)EccP256CurvePoint.getInstance(aSN1TaggedObject.getObject())).createVerificationKeyIndicator();
            }
            case 2: {
                return new VerificationKeyIndicator(2, (ASN1Encodable)DEROctetString.getInstance((Object)aSN1TaggedObject.getLoadedObject()));
            }
        }
        throw new IllegalArgumentException("unhandled tag " + aSN1TaggedObject.getTagNo());
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getObject() {
        return this.object;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.object);
    }

    public static class Builder {
        private int choice;
        private ASN1Encodable object;

        public Builder setChoice(int n) {
            this.choice = n;
            return this;
        }

        public Builder setObject(ASN1Encodable aSN1Encodable) {
            this.object = aSN1Encodable;
            return this;
        }

        public Builder publicVerificationKey(PublicVerificationKey publicVerificationKey) {
            this.object = publicVerificationKey;
            this.choice = 0;
            return this;
        }

        public Builder reconstructionValue(EccP256CurvePoint eccP256CurvePoint) {
            this.object = eccP256CurvePoint;
            this.choice = 1;
            return this;
        }

        public Builder extension(byte[] byArray) {
            this.object = new DEROctetString(byArray);
            this.choice = 2;
            return this;
        }

        public VerificationKeyIndicator createVerificationKeyIndicator() {
            return new VerificationKeyIndicator(this.choice, this.object);
        }
    }
}


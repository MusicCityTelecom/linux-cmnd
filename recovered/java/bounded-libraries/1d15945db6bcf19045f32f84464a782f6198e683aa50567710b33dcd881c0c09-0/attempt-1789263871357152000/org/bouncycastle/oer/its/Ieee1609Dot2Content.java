/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Choice
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.EncryptedData;
import org.bouncycastle.oer.its.SignedData;

public class Ieee1609Dot2Content
extends ASN1Object
implements ASN1Choice {
    public static final int unsecuredData = 0;
    public static final int signedData = 1;
    public static final int encryptedData = 2;
    public static final int signedCertificateRequest = 3;
    public static final int extension = 4;
    private final int choice;
    private final ASN1Encodable object;

    public Ieee1609Dot2Content(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.object = aSN1Encodable;
    }

    public static Ieee1609Dot2Content getInstance(Object object) {
        if (object instanceof Ieee1609Dot2Content) {
            return (Ieee1609Dot2Content)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: 
            case 3: 
            case 4: {
                return new Ieee1609Dot2Content(aSN1TaggedObject.getTagNo(), (ASN1Encodable)ASN1OctetString.getInstance((Object)aSN1TaggedObject.getObject()));
            }
            case 1: {
                return new Ieee1609Dot2Content(aSN1TaggedObject.getTagNo(), (ASN1Encodable)SignedData.getInstance(aSN1TaggedObject.getObject()));
            }
            case 2: {
                return new Ieee1609Dot2Content(aSN1TaggedObject.getTagNo(), (ASN1Encodable)EncryptedData.getInstance(aSN1TaggedObject.getObject()));
            }
        }
        throw new IllegalArgumentException("unknown tag value " + aSN1TaggedObject.getTagNo());
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.object);
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getObject() {
        return this.object;
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

        public Builder unsecuredData(ASN1OctetString aSN1OctetString) {
            this.object = aSN1OctetString;
            this.choice = 0;
            return this;
        }

        public Builder signedData(SignedData signedData) {
            this.object = signedData;
            this.choice = 1;
            return this;
        }

        public Builder encryptedData(EncryptedData encryptedData) {
            this.object = encryptedData;
            this.choice = 2;
            return this;
        }

        public Builder signedCertificateRequest(ASN1OctetString aSN1OctetString) {
            this.object = aSN1OctetString;
            this.choice = 3;
            return this;
        }

        public Builder extension(byte[] byArray) {
            this.object = new DEROctetString(byArray);
            this.choice = 4;
            return this;
        }

        public Ieee1609Dot2Content build() {
            return new Ieee1609Dot2Content(this.choice, this.object);
        }
    }
}


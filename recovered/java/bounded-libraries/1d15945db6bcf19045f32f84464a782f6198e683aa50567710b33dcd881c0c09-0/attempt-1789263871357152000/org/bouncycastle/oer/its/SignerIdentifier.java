/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Choice
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERNull
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.SequenceOfCertificate;

public class SignerIdentifier
extends ASN1Object
implements ASN1Choice {
    public static final int digest = 0;
    public static final int certificate = 1;
    public static final int self = 2;
    public static final int extension = 3;
    private final int choice;
    private final ASN1Encodable object;

    public SignerIdentifier(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.object = aSN1Encodable;
    }

    public static final SignerIdentifier getInstance(Object object) {
        if (object instanceof SignerIdentifier) {
            return (SignerIdentifier)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: {
                return new SignerIdentifier(aSN1TaggedObject.getTagNo(), (ASN1Encodable)HashedId.HashedId8.getInstance(aSN1TaggedObject.getObject()));
            }
            case 1: {
                return new SignerIdentifier(aSN1TaggedObject.getTagNo(), (ASN1Encodable)SequenceOfCertificate.getInstance(aSN1TaggedObject.getObject()));
            }
            case 2: {
                break;
            }
        }
        throw new IllegalArgumentException("unknown choice " + aSN1TaggedObject.getTagNo());
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
        private ASN1Encodable encodable;
        private int choice;

        public Builder setEncodable(ASN1Encodable aSN1Encodable) {
            this.encodable = aSN1Encodable;
            return this;
        }

        public Builder setChoice(int n) {
            this.choice = n;
            return this;
        }

        public Builder digest(HashedId.HashedId8 hashedId8) {
            this.choice = 0;
            this.encodable = hashedId8;
            return this;
        }

        public Builder certificate(SequenceOfCertificate sequenceOfCertificate) {
            this.choice = 1;
            this.encodable = sequenceOfCertificate;
            return this;
        }

        public Builder self() {
            this.choice = 2;
            this.encodable = DERNull.INSTANCE;
            return this;
        }

        public Builder extension(byte[] byArray) {
            this.choice = 3;
            this.encodable = new DEROctetString(byArray);
            return this;
        }

        public SignerIdentifier build() {
            return new SignerIdentifier(this.choice, this.encodable);
        }
    }
}


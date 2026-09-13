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
import org.bouncycastle.oer.its.HashAlgorithm;
import org.bouncycastle.oer.its.HashedId;

public class IssuerIdentifier
extends ASN1Object
implements ASN1Choice {
    public static final int sha256AndDigest = 0;
    public static final int self = 1;
    public static final int extension = 2;
    public static final int sha384AndDigest = 3;
    private final int choice;
    private final ASN1Encodable value;

    public IssuerIdentifier(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.value = aSN1Encodable;
    }

    public static IssuerIdentifier getInstance(Object object) {
        if (object instanceof IssuerIdentifier) {
            return (IssuerIdentifier)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        int n = aSN1TaggedObject.getTagNo();
        switch (n) {
            case 0: {
                return new IssuerIdentifier(0, (ASN1Encodable)HashedId.HashedId8.getInstance(aSN1TaggedObject.getObject()));
            }
            case 1: {
                return new IssuerIdentifier(1, (ASN1Encodable)HashAlgorithm.getInstance(aSN1TaggedObject.getObject()));
            }
            case 2: {
                return new IssuerIdentifier(2, (ASN1Encodable)DEROctetString.getInstance((Object)aSN1TaggedObject.getObject()));
            }
            case 3: {
                return new IssuerIdentifier(3, (ASN1Encodable)HashedId.HashedId8.getInstance(aSN1TaggedObject.getObject()));
            }
        }
        throw new IllegalArgumentException("unable to decode into known choice" + n);
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isSelf() {
        return this.choice == 1;
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
        public int choice;
        public ASN1Encodable value;

        public Builder setChoice(int n) {
            this.choice = n;
            return this;
        }

        public Builder setValue(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }

        public Builder sha256AndDigest(HashedId hashedId) {
            this.choice = 0;
            this.value = hashedId;
            return this;
        }

        public Builder self(HashAlgorithm hashAlgorithm) {
            this.choice = 1;
            this.value = hashAlgorithm;
            return this;
        }

        public Builder extension(byte[] byArray) {
            this.choice = 2;
            this.value = new DEROctetString(byArray);
            return this;
        }

        public Builder sha384AndDigest(HashedId hashedId) {
            this.choice = 3;
            this.value = hashedId;
            return this;
        }

        public IssuerIdentifier createIssuerIdentifier() {
            return new IssuerIdentifier(this.choice, this.value);
        }
    }
}


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

public class HashedData
extends ASN1Object
implements ASN1Choice {
    public static final int sha256HashedData = 0;
    public static final int extension = 1;
    public static final int sha384HashedData = 2;
    public static final int reserved = 3;
    private final int choice;
    private final ASN1Encodable value;

    public HashedData(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.value = aSN1Encodable;
    }

    public static HashedData getInstance(Object object) {
        if (object instanceof HashedData) {
            return (HashedData)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                return new HashedData(aSN1TaggedObject.getTagNo(), (ASN1Encodable)DEROctetString.getInstance((Object)aSN1TaggedObject.getObject()));
            }
        }
        throw new IllegalStateException("unknown choice value " + aSN1TaggedObject.getTagNo());
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

        public Builder setSha256HashedData(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }

        public Builder extension(byte[] byArray) {
            this.value = new DEROctetString(byArray);
            return this;
        }

        public Builder sha384HashedData(ASN1OctetString aSN1OctetString) {
            this.value = aSN1OctetString;
            return this;
        }

        public Builder reserved(ASN1OctetString aSN1OctetString) {
            this.value = aSN1OctetString;
            return this;
        }

        public HashedData createHashedData() {
            return new HashedData(this.choice, this.value);
        }
    }
}


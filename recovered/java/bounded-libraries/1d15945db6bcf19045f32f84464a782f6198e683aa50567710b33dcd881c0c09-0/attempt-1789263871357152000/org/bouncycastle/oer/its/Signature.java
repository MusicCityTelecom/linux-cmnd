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
import org.bouncycastle.oer.its.EcdsaP256Signature;
import org.bouncycastle.oer.its.EcdsaP384Signature;

public class Signature
extends ASN1Object
implements ASN1Choice {
    public static final int ecdsaNistP256Signature = 0;
    public static final int ecdsaBrainpoolP256r1Signature = 1;
    public static final int ecdsaBrainpoolP384r1Signature = 3;
    private static final int extension = 2;
    private final int choice;
    private final ASN1Encodable value;

    public Signature(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.value = aSN1Encodable;
    }

    public static Signature getInstance(Object object) {
        ASN1Object aSN1Object;
        if (object instanceof Signature) {
            return (Signature)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: 
            case 1: {
                aSN1Object = EcdsaP256Signature.getInstance(aSN1TaggedObject.getObject());
                break;
            }
            case 2: {
                aSN1Object = DEROctetString.getInstance((Object)aSN1TaggedObject.getObject());
                break;
            }
            case 3: {
                aSN1Object = EcdsaP384Signature.getInstance(aSN1TaggedObject.getObject());
                break;
            }
            default: {
                throw new IllegalStateException("unknown choice " + aSN1TaggedObject.getTagNo());
            }
        }
        return new Signature(aSN1TaggedObject.getTagNo(), (ASN1Encodable)aSN1Object);
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

    public static class Builder {
        private int choice;
        private ASN1Encodable value;

        public Builder ecdsaNistP256Signature(EcdsaP256Signature ecdsaP256Signature) {
            this.choice = 0;
            this.value = ecdsaP256Signature;
            return this;
        }

        public Builder ecdsaBrainpoolP256r1Signature(EcdsaP256Signature ecdsaP256Signature) {
            this.choice = 1;
            this.value = ecdsaP256Signature;
            return this;
        }

        public Builder ecdsaBrainpoolP384r1Signature(EcdsaP384Signature ecdsaP384Signature) {
            this.choice = 3;
            this.value = ecdsaP384Signature;
            return this;
        }

        public Signature createSignature() {
            return new Signature(this.choice, this.value);
        }
    }
}


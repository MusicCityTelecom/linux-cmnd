/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Choice
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;

public class EncryptedDataEncryptionKey
extends ASN1Object
implements ASN1Choice {
    public static final int eciesNistP256 = 0;
    public static final int eciesBrainpoolP256r1 = 1;
    public static final int extension = 2;
    private final int choice;
    private final ASN1Encodable value;

    public EncryptedDataEncryptionKey(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.value = aSN1Encodable;
    }

    public static EncryptedDataEncryptionKey getInstance(Object object) {
        if (object instanceof EncryptedDataEncryptionKey) {
            return (EncryptedDataEncryptionKey)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: 
            case 1: {
                break;
            }
            default: {
                throw new IllegalStateException("unknown choice " + aSN1TaggedObject.getTagNo());
            }
        }
        return new Builder().setChoice(aSN1TaggedObject.getTagNo()).setValue((ASN1Encodable)aSN1TaggedObject.getObject()).createEncryptedDataEncryptionKey();
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

        public Builder setValue(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }

        public EncryptedDataEncryptionKey createEncryptedDataEncryptionKey() {
            return new EncryptedDataEncryptionKey(this.choice, this.value);
        }
    }
}


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
import org.bouncycastle.oer.its.Hostname;
import org.bouncycastle.oer.its.LinkageData;

public class CertificateId
extends ASN1Object
implements ASN1Choice {
    public static final int linkageData = 0;
    public static final int name = 1;
    public static final int binaryId = 2;
    public static final int none = 3;
    public static final int extension = 4;
    private final int choice;
    private final ASN1Encodable value;

    public CertificateId(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.value = aSN1Encodable;
    }

    public static CertificateId getInstance(Object object) {
        if (object instanceof CertificateId) {
            return (CertificateId)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        int n = aSN1TaggedObject.getTagNo();
        switch (n) {
            case 0: {
                return new CertificateId(n, (ASN1Encodable)LinkageData.getInstance(aSN1TaggedObject.getObject()));
            }
            case 1: {
                return new CertificateId(n, (ASN1Encodable)Hostname.getInstance(aSN1TaggedObject.getObject()));
            }
            case 2: 
            case 4: {
                return new CertificateId(n, (ASN1Encodable)DEROctetString.getInstance((Object)aSN1TaggedObject.getObject()));
            }
            case 3: {
                return new CertificateId(n, (ASN1Encodable)aSN1TaggedObject.getObject());
            }
        }
        throw new IllegalArgumentException("unknown choice in CertificateId");
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value).toASN1Primitive();
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getValue() {
        return this.value;
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

        public Builder linkageData(LinkageData linkageData) {
            this.choice = 0;
            this.value = linkageData;
            return this;
        }

        public Builder name(Hostname hostname) {
            this.choice = 1;
            this.value = hostname;
            return this;
        }

        public Builder binaryId(DEROctetString dEROctetString) {
            this.choice = 1;
            this.value = dEROctetString;
            return this;
        }

        public Builder none() {
            this.choice = 1;
            this.value = DERNull.INSTANCE;
            return this;
        }

        public Builder extension(byte[] byArray) {
            this.choice = 4;
            this.value = new DEROctetString(byArray);
            return this;
        }

        public CertificateId createCertificateId() {
            return new CertificateId(this.choice, this.value);
        }
    }
}


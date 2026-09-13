/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.OEROptional;
import org.bouncycastle.oer.its.CertificateType;
import org.bouncycastle.oer.its.IssuerIdentifier;
import org.bouncycastle.oer.its.Signature;
import org.bouncycastle.oer.its.ToBeSignedCertificate;
import org.bouncycastle.oer.its.Utils;

public class CertificateBase
extends ASN1Object {
    private final ASN1Integer version;
    private final CertificateType type;
    private final IssuerIdentifier issuer;
    private final ToBeSignedCertificate toBeSignedCertificate;
    private final Signature signature;

    public CertificateBase(ASN1Integer aSN1Integer, CertificateType certificateType, IssuerIdentifier issuerIdentifier, ToBeSignedCertificate toBeSignedCertificate, Signature signature) {
        this.version = aSN1Integer;
        this.type = certificateType;
        this.issuer = issuerIdentifier;
        this.toBeSignedCertificate = toBeSignedCertificate;
        this.signature = signature;
    }

    public static CertificateBase getInstance(Object object) {
        if (object instanceof CertificateBase) {
            return (CertificateBase)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        ASN1Integer aSN1Integer = ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(0));
        CertificateType certificateType = CertificateType.getInstance(aSN1Sequence.getObjectAt(1));
        IssuerIdentifier issuerIdentifier = IssuerIdentifier.getInstance(aSN1Sequence.getObjectAt(2));
        ToBeSignedCertificate toBeSignedCertificate = ToBeSignedCertificate.getInstance(aSN1Sequence.getObjectAt(3));
        Signature signature = OEROptional.getValue(Signature.class, aSN1Sequence.getObjectAt(4));
        return new Builder().setVersion(aSN1Integer).setType(certificateType).setIssuer(issuerIdentifier).setToBeSignedCertificate(toBeSignedCertificate).setSignature(signature).createCertificateBase();
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public CertificateType getType() {
        return this.type;
    }

    public IssuerIdentifier getIssuer() {
        return this.issuer;
    }

    public ToBeSignedCertificate getToBeSignedCertificate() {
        return this.toBeSignedCertificate;
    }

    public Signature getSignature() {
        return this.signature;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.version, this.type, this.issuer, this.toBeSignedCertificate, OEROptional.getInstance((Object)this.signature)});
    }

    public static class Builder {
        private ASN1Integer version;
        private CertificateType type;
        private IssuerIdentifier issuer;
        private ToBeSignedCertificate toBeSignedCertificate;
        private Signature signature;

        public Builder setVersion(ASN1Integer aSN1Integer) {
            this.version = aSN1Integer;
            return this;
        }

        public Builder setType(CertificateType certificateType) {
            this.type = certificateType;
            return this;
        }

        public Builder setIssuer(IssuerIdentifier issuerIdentifier) {
            this.issuer = issuerIdentifier;
            return this;
        }

        public Builder setToBeSignedCertificate(ToBeSignedCertificate toBeSignedCertificate) {
            this.toBeSignedCertificate = toBeSignedCertificate;
            return this;
        }

        public Builder setSignature(Signature signature) {
            this.signature = signature;
            return this;
        }

        public CertificateBase createCertificateBase() {
            return new CertificateBase(this.version, this.type, this.issuer, this.toBeSignedCertificate, this.signature);
        }
    }
}


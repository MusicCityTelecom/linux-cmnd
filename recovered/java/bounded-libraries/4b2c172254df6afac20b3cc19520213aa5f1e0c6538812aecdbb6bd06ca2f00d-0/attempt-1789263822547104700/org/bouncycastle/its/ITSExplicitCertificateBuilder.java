/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.nist.NISTObjectIdentifiers
 *  org.bouncycastle.asn1.sec.SECObjectIdentifiers
 *  org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers
 *  org.bouncycastle.oer.OERDefinition$Element
 *  org.bouncycastle.oer.OEREncoder
 *  org.bouncycastle.oer.its.Certificate$Builder
 *  org.bouncycastle.oer.its.CertificateBase$Builder
 *  org.bouncycastle.oer.its.CertificateId
 *  org.bouncycastle.oer.its.CertificateType
 *  org.bouncycastle.oer.its.HashAlgorithm
 *  org.bouncycastle.oer.its.HashedId
 *  org.bouncycastle.oer.its.HashedId$HashedId8
 *  org.bouncycastle.oer.its.IssuerIdentifier
 *  org.bouncycastle.oer.its.IssuerIdentifier$Builder
 *  org.bouncycastle.oer.its.Signature
 *  org.bouncycastle.oer.its.ToBeSignedCertificate
 *  org.bouncycastle.oer.its.ToBeSignedCertificate$Builder
 *  org.bouncycastle.oer.its.VerificationKeyIndicator
 *  org.bouncycastle.oer.its.template.IEEE1609dot2
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.its;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.ITSCertificateBuilder;
import org.bouncycastle.its.ITSPublicEncryptionKey;
import org.bouncycastle.its.ITSPublicVerificationKey;
import org.bouncycastle.its.operator.ECDSAEncoder;
import org.bouncycastle.its.operator.ITSContentSigner;
import org.bouncycastle.oer.OERDefinition;
import org.bouncycastle.oer.OEREncoder;
import org.bouncycastle.oer.its.Certificate;
import org.bouncycastle.oer.its.CertificateBase;
import org.bouncycastle.oer.its.CertificateId;
import org.bouncycastle.oer.its.CertificateType;
import org.bouncycastle.oer.its.HashAlgorithm;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.IssuerIdentifier;
import org.bouncycastle.oer.its.Signature;
import org.bouncycastle.oer.its.ToBeSignedCertificate;
import org.bouncycastle.oer.its.VerificationKeyIndicator;
import org.bouncycastle.oer.its.template.IEEE1609dot2;
import org.bouncycastle.util.Arrays;

public class ITSExplicitCertificateBuilder
extends ITSCertificateBuilder {
    private final ITSContentSigner signer;

    public ITSExplicitCertificateBuilder(ITSContentSigner iTSContentSigner, ToBeSignedCertificate.Builder builder) {
        super(builder);
        this.signer = iTSContentSigner;
    }

    public ITSCertificate build(CertificateId certificateId, ITSPublicVerificationKey iTSPublicVerificationKey) {
        return this.build(certificateId, iTSPublicVerificationKey, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ITSCertificate build(CertificateId certificateId, ITSPublicVerificationKey iTSPublicVerificationKey, ITSPublicEncryptionKey iTSPublicEncryptionKey) {
        VerificationKeyIndicator verificationKeyIndicator;
        ToBeSignedCertificate.Builder builder = new ToBeSignedCertificate.Builder(this.tbsCertificateBuilder);
        builder.setCertificateId(certificateId);
        if (iTSPublicEncryptionKey != null) {
            builder.setEncryptionKey(iTSPublicEncryptionKey.toASN1Structure());
        }
        builder.setVerificationKeyIndicator(VerificationKeyIndicator.builder().publicVerificationKey(iTSPublicVerificationKey.toASN1Structure()).createVerificationKeyIndicator());
        ToBeSignedCertificate toBeSignedCertificate = builder.createToBeSignedCertificate();
        ToBeSignedCertificate toBeSignedCertificate2 = null;
        if (this.signer.isForSelfSigning()) {
            verificationKeyIndicator = toBeSignedCertificate.getVerificationKeyIndicator();
        } else {
            toBeSignedCertificate2 = this.signer.getAssociatedCertificate().toASN1Structure().getCertificateBase().getToBeSignedCertificate();
            verificationKeyIndicator = toBeSignedCertificate2.getVerificationKeyIndicator();
        }
        OutputStream outputStream = this.signer.getOutputStream();
        try {
            outputStream.write(OEREncoder.toByteArray((ASN1Encodable)toBeSignedCertificate, (OERDefinition.Element)IEEE1609dot2.tbsCertificate));
            outputStream.close();
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("cannot produce certificate signature");
        }
        Signature signature = null;
        switch (verificationKeyIndicator.getChoice()) {
            case 0: {
                signature = ECDSAEncoder.toITS(SECObjectIdentifiers.secp256r1, this.signer.getSignature());
                break;
            }
            case 1: {
                signature = ECDSAEncoder.toITS(TeleTrusTObjectIdentifiers.brainpoolP256r1, this.signer.getSignature());
                break;
            }
            case 3: {
                signature = ECDSAEncoder.toITS(TeleTrusTObjectIdentifiers.brainpoolP384r1, this.signer.getSignature());
                break;
            }
            default: {
                throw new IllegalStateException("unknown key type");
            }
        }
        CertificateBase.Builder builder2 = new CertificateBase.Builder();
        IssuerIdentifier.Builder builder3 = IssuerIdentifier.builder();
        ASN1ObjectIdentifier aSN1ObjectIdentifier = this.signer.getDigestAlgorithm().getAlgorithm();
        if (this.signer.isForSelfSigning()) {
            if (aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_sha256)) {
                builder3.self(HashAlgorithm.sha256);
            } else {
                if (!aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_sha384)) throw new IllegalStateException("unknown digest");
                builder3.self(HashAlgorithm.sha384);
            }
        } else {
            byte[] byArray = this.signer.getAssociatedCertificateDigest();
            HashedId.HashedId8 hashedId8 = new HashedId.HashedId8(Arrays.copyOfRange((byte[])byArray, (int)(byArray.length - 8), (int)byArray.length));
            if (aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_sha256)) {
                builder3.sha256AndDigest((HashedId)hashedId8);
            } else {
                if (!aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_sha384)) throw new IllegalStateException("unknown digest");
                builder3.sha384AndDigest((HashedId)hashedId8);
            }
        }
        builder2.setVersion(this.version);
        builder2.setType(CertificateType.Explicit);
        builder2.setIssuer(builder3.createIssuerIdentifier());
        builder2.setToBeSignedCertificate(toBeSignedCertificate);
        builder2.setSignature(signature);
        Certificate.Builder builder4 = new Certificate.Builder();
        builder4.setCertificateBase(builder2.createCertificateBase());
        return new ITSCertificate(builder4.createCertificate());
    }
}


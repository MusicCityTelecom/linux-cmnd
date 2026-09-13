/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.nist.NISTObjectIdentifiers
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.oer.its.Certificate$Builder
 *  org.bouncycastle.oer.its.CertificateBase$Builder
 *  org.bouncycastle.oer.its.CertificateId
 *  org.bouncycastle.oer.its.CertificateType
 *  org.bouncycastle.oer.its.EccP256CurvePoint
 *  org.bouncycastle.oer.its.HashedId
 *  org.bouncycastle.oer.its.HashedId$HashedId8
 *  org.bouncycastle.oer.its.IssuerIdentifier
 *  org.bouncycastle.oer.its.IssuerIdentifier$Builder
 *  org.bouncycastle.oer.its.PublicEncryptionKey
 *  org.bouncycastle.oer.its.ToBeSignedCertificate$Builder
 *  org.bouncycastle.oer.its.VerificationKeyIndicator
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.its;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.its.ITSCertificate;
import org.bouncycastle.its.ITSCertificateBuilder;
import org.bouncycastle.oer.its.Certificate;
import org.bouncycastle.oer.its.CertificateBase;
import org.bouncycastle.oer.its.CertificateId;
import org.bouncycastle.oer.its.CertificateType;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.IssuerIdentifier;
import org.bouncycastle.oer.its.PublicEncryptionKey;
import org.bouncycastle.oer.its.ToBeSignedCertificate;
import org.bouncycastle.oer.its.VerificationKeyIndicator;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Arrays;

public class ITSImplicitCertificateBuilder
extends ITSCertificateBuilder {
    private final IssuerIdentifier issuerIdentifier;

    public ITSImplicitCertificateBuilder(ITSCertificate iTSCertificate, DigestCalculatorProvider digestCalculatorProvider, ToBeSignedCertificate.Builder builder) {
        super(iTSCertificate, builder);
        Object object;
        DigestCalculator digestCalculator;
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256);
        ASN1ObjectIdentifier aSN1ObjectIdentifier = algorithmIdentifier.getAlgorithm();
        try {
            digestCalculator = digestCalculatorProvider.get(algorithmIdentifier);
        }
        catch (OperatorCreationException operatorCreationException) {
            throw new IllegalStateException(operatorCreationException.getMessage(), operatorCreationException);
        }
        try {
            object = digestCalculator.getOutputStream();
            ((OutputStream)object).write(iTSCertificate.getEncoded());
            ((OutputStream)object).close();
        }
        catch (IOException iOException) {
            throw new IllegalStateException(iOException.getMessage(), iOException);
        }
        object = digestCalculator.getDigest();
        IssuerIdentifier.Builder builder2 = IssuerIdentifier.builder();
        HashedId.HashedId8 hashedId8 = new HashedId.HashedId8(Arrays.copyOfRange((byte[])object, (int)(((Object)object).length - 8), (int)((Object)object).length));
        if (aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_sha256)) {
            builder2.sha256AndDigest((HashedId)hashedId8);
        } else if (aSN1ObjectIdentifier.equals((ASN1Primitive)NISTObjectIdentifiers.id_sha384)) {
            builder2.sha384AndDigest((HashedId)hashedId8);
        } else {
            throw new IllegalStateException("unknown digest");
        }
        this.issuerIdentifier = builder2.createIssuerIdentifier();
    }

    public ITSCertificate build(CertificateId certificateId, BigInteger bigInteger, BigInteger bigInteger2) {
        return this.build(certificateId, bigInteger, bigInteger2, null);
    }

    public ITSCertificate build(CertificateId certificateId, BigInteger bigInteger, BigInteger bigInteger2, PublicEncryptionKey publicEncryptionKey) {
        EccP256CurvePoint eccP256CurvePoint = EccP256CurvePoint.builder().createUncompressedP256(bigInteger, bigInteger2);
        ToBeSignedCertificate.Builder builder = new ToBeSignedCertificate.Builder(this.tbsCertificateBuilder);
        builder.setCertificateId(certificateId);
        if (publicEncryptionKey != null) {
            builder.setEncryptionKey(publicEncryptionKey);
        }
        builder.setVerificationKeyIndicator(VerificationKeyIndicator.builder().reconstructionValue(eccP256CurvePoint).createVerificationKeyIndicator());
        CertificateBase.Builder builder2 = new CertificateBase.Builder();
        builder2.setVersion(this.version);
        builder2.setType(CertificateType.Implicit);
        builder2.setIssuer(this.issuerIdentifier);
        builder2.setToBeSignedCertificate(builder.createToBeSignedCertificate());
        Certificate.Builder builder3 = new Certificate.Builder();
        builder3.setCertificateBase(builder2.createCertificateBase());
        return new ITSCertificate(builder3.createCertificate());
    }
}


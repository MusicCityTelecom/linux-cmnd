/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.oer.OERDefinition$Element
 *  org.bouncycastle.oer.OEREncoder
 *  org.bouncycastle.oer.its.Certificate
 *  org.bouncycastle.oer.its.IssuerIdentifier
 *  org.bouncycastle.oer.its.PublicEncryptionKey
 *  org.bouncycastle.oer.its.Signature
 *  org.bouncycastle.oer.its.template.IEEE1609dot2
 *  org.bouncycastle.util.Encodable
 */
package org.bouncycastle.its;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.its.ITSPublicEncryptionKey;
import org.bouncycastle.its.ITSValidityPeriod;
import org.bouncycastle.its.operator.ECDSAEncoder;
import org.bouncycastle.its.operator.ITSContentVerifierProvider;
import org.bouncycastle.oer.OERDefinition;
import org.bouncycastle.oer.OEREncoder;
import org.bouncycastle.oer.its.Certificate;
import org.bouncycastle.oer.its.IssuerIdentifier;
import org.bouncycastle.oer.its.PublicEncryptionKey;
import org.bouncycastle.oer.its.Signature;
import org.bouncycastle.oer.its.template.IEEE1609dot2;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.util.Encodable;

public class ITSCertificate
implements Encodable {
    private final Certificate certificate;

    public ITSCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public IssuerIdentifier getIssuer() {
        return this.certificate.getCertificateBase().getIssuer();
    }

    public ITSValidityPeriod getValidityPeriod() {
        return new ITSValidityPeriod(this.certificate.getCertificateBase().getToBeSignedCertificate().getValidityPeriod());
    }

    public ITSPublicEncryptionKey getPublicEncryptionKey() {
        PublicEncryptionKey publicEncryptionKey = this.certificate.getCertificateBase().getToBeSignedCertificate().getEncryptionKey();
        if (publicEncryptionKey != null) {
            return new ITSPublicEncryptionKey(publicEncryptionKey);
        }
        return null;
    }

    public boolean isSignatureValid(ITSContentVerifierProvider iTSContentVerifierProvider) throws Exception {
        ContentVerifier contentVerifier = iTSContentVerifierProvider.get(this.certificate.getCertificateBase().getSignature().getChoice());
        OutputStream outputStream = contentVerifier.getOutputStream();
        outputStream.write(OEREncoder.toByteArray((ASN1Encodable)this.certificate.getCertificateBase().getToBeSignedCertificate(), (OERDefinition.Element)IEEE1609dot2.tbsCertificate));
        outputStream.close();
        Signature signature = this.certificate.getCertificateBase().getSignature();
        return contentVerifier.verify(ECDSAEncoder.toX962(signature));
    }

    public Certificate toASN1Structure() {
        return this.certificate;
    }

    public byte[] getEncoded() throws IOException {
        return OEREncoder.toByteArray((ASN1Encodable)this.certificate.getCertificateBase(), (OERDefinition.Element)IEEE1609dot2.certificate);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.x509.AccessDescription
 *  org.bouncycastle.asn1.x509.AuthorityKeyIdentifier
 *  org.bouncycastle.asn1.x509.BasicConstraints
 *  org.bouncycastle.asn1.x509.DistributionPoint
 *  org.bouncycastle.asn1.x509.GeneralNames
 *  org.bouncycastle.asn1.x509.KeyPurposeId
 *  org.bouncycastle.asn1.x509.KeyUsage
 *  org.bouncycastle.asn1.x509.PolicyInformation
 *  org.bouncycastle.asn1.x509.SubjectKeyIdentifier
 */
package org.cryptacular.x509;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.AccessDescription;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.cryptacular.EncodingException;
import org.cryptacular.x509.ExtensionType;

public final class ExtensionReader {
    private final X509Certificate certificate;

    public ExtensionReader(X509Certificate cert) {
        this.certificate = cert;
    }

    public ASN1Encodable read(String extensionOidOrName) throws EncodingException {
        if (extensionOidOrName == null) {
            throw new IllegalArgumentException("extensionOidOrName cannot be null.");
        }
        if (extensionOidOrName.contains(".")) {
            return this.read(ExtensionType.fromOid(extensionOidOrName));
        }
        return this.read(ExtensionType.fromName(extensionOidOrName));
    }

    public ASN1Encodable read(ExtensionType extension) {
        byte[] data = this.certificate.getExtensionValue(extension.getOid());
        if (data == null) {
            return null;
        }
        try {
            ASN1Primitive der = ASN1Primitive.fromByteArray((byte[])data);
            if (der instanceof ASN1OctetString) {
                data = ((ASN1OctetString)der).getOctets();
                der = ASN1Primitive.fromByteArray((byte[])data);
            }
            return der;
        }
        catch (Exception e) {
            throw new EncodingException("ASN.1 parse error", e);
        }
    }

    public GeneralNames readSubjectAlternativeName() throws EncodingException {
        try {
            return GeneralNames.getInstance((Object)this.read(ExtensionType.SubjectAlternativeName));
        }
        catch (RuntimeException e) {
            throw new EncodingException("GeneralNames parse error", e);
        }
    }

    public GeneralNames readIssuerAlternativeName() throws EncodingException {
        try {
            return GeneralNames.getInstance((Object)this.read(ExtensionType.IssuerAlternativeName));
        }
        catch (RuntimeException e) {
            throw new EncodingException("GeneralNames parse error", e);
        }
    }

    public BasicConstraints readBasicConstraints() throws EncodingException {
        try {
            return BasicConstraints.getInstance((Object)this.read(ExtensionType.BasicConstraints));
        }
        catch (RuntimeException e) {
            throw new EncodingException("BasicConstraints parse error", e);
        }
    }

    public List<PolicyInformation> readCertificatePolicies() throws EncodingException {
        ASN1Encodable data = this.read(ExtensionType.CertificatePolicies);
        if (data == null) {
            return null;
        }
        try {
            ASN1Sequence sequence = ASN1Sequence.getInstance((Object)data);
            ArrayList<PolicyInformation> list = new ArrayList<PolicyInformation>(sequence.size());
            for (int i = 0; i < sequence.size(); ++i) {
                list.add(PolicyInformation.getInstance((Object)sequence.getObjectAt(i)));
            }
            return list;
        }
        catch (RuntimeException e) {
            throw new EncodingException("PolicyInformation parse error", e);
        }
    }

    public SubjectKeyIdentifier readSubjectKeyIdentifier() throws EncodingException {
        try {
            return SubjectKeyIdentifier.getInstance((Object)this.read(ExtensionType.SubjectKeyIdentifier));
        }
        catch (RuntimeException e) {
            throw new EncodingException("SubjectKeyIdentifier parse error", e);
        }
    }

    public AuthorityKeyIdentifier readAuthorityKeyIdentifier() throws EncodingException {
        try {
            return AuthorityKeyIdentifier.getInstance((Object)this.read(ExtensionType.AuthorityKeyIdentifier));
        }
        catch (RuntimeException e) {
            throw new EncodingException("AuthorityKeyIdentifier parse error", e);
        }
    }

    public KeyUsage readKeyUsage() throws EncodingException {
        try {
            return KeyUsage.getInstance((Object)this.read(ExtensionType.KeyUsage));
        }
        catch (RuntimeException e) {
            throw new EncodingException("KeyUsage parse error", e);
        }
    }

    public List<KeyPurposeId> readExtendedKeyUsage() throws EncodingException {
        ASN1Encodable data = this.read(ExtensionType.ExtendedKeyUsage);
        if (data == null) {
            return null;
        }
        try {
            ASN1Sequence sequence = ASN1Sequence.getInstance((Object)data);
            ArrayList<KeyPurposeId> list = new ArrayList<KeyPurposeId>(sequence.size());
            for (int i = 0; i < sequence.size(); ++i) {
                list.add(KeyPurposeId.getInstance((Object)sequence.getObjectAt(i)));
            }
            return list;
        }
        catch (RuntimeException e) {
            throw new EncodingException("KeyPurposeId parse error", e);
        }
    }

    public List<DistributionPoint> readCRLDistributionPoints() throws EncodingException {
        ASN1Encodable data = this.read(ExtensionType.CRLDistributionPoints);
        if (data == null) {
            return null;
        }
        try {
            ASN1Sequence sequence = ASN1Sequence.getInstance((Object)data);
            ArrayList<DistributionPoint> list = new ArrayList<DistributionPoint>(sequence.size());
            for (int i = 0; i < sequence.size(); ++i) {
                list.add(DistributionPoint.getInstance((Object)sequence.getObjectAt(i)));
            }
            return list;
        }
        catch (RuntimeException e) {
            throw new EncodingException("DistributionPoint parse error", e);
        }
    }

    public List<AccessDescription> readAuthorityInformationAccess() throws EncodingException {
        ASN1Encodable data = this.read(ExtensionType.AuthorityInformationAccess);
        if (data == null) {
            return null;
        }
        try {
            ASN1Sequence sequence = ASN1Sequence.getInstance((Object)data);
            ArrayList<AccessDescription> list = new ArrayList<AccessDescription>(sequence.size());
            for (int i = 0; i < sequence.size(); ++i) {
                list.add(AccessDescription.getInstance((Object)sequence.getObjectAt(i)));
            }
            return list;
        }
        catch (RuntimeException e) {
            throw new EncodingException("AccessDescription parse error", e);
        }
    }
}


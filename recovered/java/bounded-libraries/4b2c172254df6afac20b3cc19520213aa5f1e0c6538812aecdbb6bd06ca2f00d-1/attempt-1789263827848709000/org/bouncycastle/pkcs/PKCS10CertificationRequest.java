/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Boolean
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.pkcs.Attribute
 *  org.bouncycastle.asn1.pkcs.CertificationRequest
 *  org.bouncycastle.asn1.pkcs.CertificationRequestInfo
 *  org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
 *  org.bouncycastle.asn1.x500.X500Name
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.asn1.x509.Extensions
 *  org.bouncycastle.asn1.x509.ExtensionsGenerator
 *  org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
 */
package org.bouncycastle.pkcs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.CertificationRequest;
import org.bouncycastle.asn1.pkcs.CertificationRequestInfo;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.pkcs.PKCSException;
import org.bouncycastle.pkcs.PKCSIOException;

public class PKCS10CertificationRequest {
    private static Attribute[] EMPTY_ARRAY = new Attribute[0];
    private CertificationRequest certificationRequest;

    private static CertificationRequest parseBytes(byte[] byArray) throws IOException {
        try {
            CertificationRequest certificationRequest = CertificationRequest.getInstance((Object)ASN1Primitive.fromByteArray((byte[])byArray));
            if (certificationRequest == null) {
                throw new PKCSIOException("empty data passed to constructor");
            }
            return certificationRequest;
        }
        catch (ClassCastException classCastException) {
            throw new PKCSIOException("malformed data: " + classCastException.getMessage(), classCastException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new PKCSIOException("malformed data: " + illegalArgumentException.getMessage(), illegalArgumentException);
        }
    }

    public PKCS10CertificationRequest(CertificationRequest certificationRequest) {
        if (certificationRequest == null) {
            throw new NullPointerException("certificationRequest cannot be null");
        }
        this.certificationRequest = certificationRequest;
    }

    public PKCS10CertificationRequest(byte[] byArray) throws IOException {
        this(PKCS10CertificationRequest.parseBytes(byArray));
    }

    public CertificationRequest toASN1Structure() {
        return this.certificationRequest;
    }

    public X500Name getSubject() {
        return X500Name.getInstance((Object)this.certificationRequest.getCertificationRequestInfo().getSubject());
    }

    public AlgorithmIdentifier getSignatureAlgorithm() {
        return this.certificationRequest.getSignatureAlgorithm();
    }

    public byte[] getSignature() {
        return this.certificationRequest.getSignature().getOctets();
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.certificationRequest.getCertificationRequestInfo().getSubjectPublicKeyInfo();
    }

    public Attribute[] getAttributes() {
        ASN1Set aSN1Set = this.certificationRequest.getCertificationRequestInfo().getAttributes();
        if (aSN1Set == null) {
            return EMPTY_ARRAY;
        }
        Attribute[] attributeArray = new Attribute[aSN1Set.size()];
        for (int i = 0; i != aSN1Set.size(); ++i) {
            attributeArray[i] = Attribute.getInstance((Object)aSN1Set.getObjectAt(i));
        }
        return attributeArray;
    }

    public Attribute[] getAttributes(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        ASN1Set aSN1Set = this.certificationRequest.getCertificationRequestInfo().getAttributes();
        if (aSN1Set == null) {
            return EMPTY_ARRAY;
        }
        ArrayList<Attribute> arrayList = new ArrayList<Attribute>();
        for (int i = 0; i != aSN1Set.size(); ++i) {
            Attribute attribute = Attribute.getInstance((Object)aSN1Set.getObjectAt(i));
            if (!attribute.getAttrType().equals((ASN1Primitive)aSN1ObjectIdentifier)) continue;
            arrayList.add(attribute);
        }
        if (arrayList.size() == 0) {
            return EMPTY_ARRAY;
        }
        return arrayList.toArray(new Attribute[arrayList.size()]);
    }

    public byte[] getEncoded() throws IOException {
        return this.certificationRequest.getEncoded();
    }

    public boolean isSignatureValid(ContentVerifierProvider contentVerifierProvider) throws PKCSException {
        ContentVerifier contentVerifier;
        CertificationRequestInfo certificationRequestInfo = this.certificationRequest.getCertificationRequestInfo();
        try {
            contentVerifier = contentVerifierProvider.get(this.certificationRequest.getSignatureAlgorithm());
            OutputStream outputStream = contentVerifier.getOutputStream();
            outputStream.write(certificationRequestInfo.getEncoded("DER"));
            outputStream.close();
        }
        catch (Exception exception) {
            throw new PKCSException("unable to process signature: " + exception.getMessage(), exception);
        }
        return contentVerifier.verify(this.getSignature());
    }

    public Extensions getRequestedExtensions() {
        Attribute[] attributeArray = this.getAttributes();
        for (int i = 0; i != attributeArray.length; ++i) {
            Attribute attribute = attributeArray[i];
            if (attribute.getAttrType() != PKCSObjectIdentifiers.pkcs_9_at_extensionRequest) continue;
            ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)attribute.getAttrValues().getObjectAt(0));
            Enumeration enumeration = aSN1Sequence.getObjects();
            while (enumeration.hasMoreElements()) {
                boolean bl;
                ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance(enumeration.nextElement());
                boolean bl2 = bl = aSN1Sequence2.size() == 3 && ASN1Boolean.getInstance((Object)aSN1Sequence2.getObjectAt(1)).isTrue();
                if (aSN1Sequence2.size() == 2) {
                    extensionsGenerator.addExtension(ASN1ObjectIdentifier.getInstance((Object)aSN1Sequence2.getObjectAt(0)), false, ASN1OctetString.getInstance((Object)aSN1Sequence2.getObjectAt(1)).getOctets());
                    continue;
                }
                if (aSN1Sequence2.size() == 3) {
                    extensionsGenerator.addExtension(ASN1ObjectIdentifier.getInstance((Object)aSN1Sequence2.getObjectAt(0)), bl, ASN1OctetString.getInstance((Object)aSN1Sequence2.getObjectAt(2)).getOctets());
                    continue;
                }
                throw new IllegalArgumentException("incorrect sequence size of Extension get " + aSN1Sequence2.size() + " expected 2 or three");
            }
            return extensionsGenerator.generate();
        }
        return null;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof PKCS10CertificationRequest)) {
            return false;
        }
        PKCS10CertificationRequest pKCS10CertificationRequest = (PKCS10CertificationRequest)object;
        return this.toASN1Structure().equals((Object)pKCS10CertificationRequest.toASN1Structure());
    }

    public int hashCode() {
        return this.toASN1Structure().hashCode();
    }
}


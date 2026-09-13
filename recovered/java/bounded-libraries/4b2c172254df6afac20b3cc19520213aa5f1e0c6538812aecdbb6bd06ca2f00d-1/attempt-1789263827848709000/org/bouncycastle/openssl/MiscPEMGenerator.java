/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.cms.ContentInfo
 *  org.bouncycastle.asn1.oiw.OIWObjectIdentifiers
 *  org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
 *  org.bouncycastle.asn1.pkcs.PrivateKeyInfo
 *  org.bouncycastle.asn1.x509.DSAParameter
 *  org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
 *  org.bouncycastle.asn1.x9.X9ObjectIdentifiers
 *  org.bouncycastle.util.Strings
 *  org.bouncycastle.util.io.pem.PemGenerationException
 *  org.bouncycastle.util.io.pem.PemHeader
 *  org.bouncycastle.util.io.pem.PemObject
 *  org.bouncycastle.util.io.pem.PemObjectGenerator
 */
package org.bouncycastle.openssl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.DSAParameter;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.PEMEncryptor;
import org.bouncycastle.openssl.X509TrustedCertificateBlock;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.io.pem.PemGenerationException;
import org.bouncycastle.util.io.pem.PemHeader;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemObjectGenerator;

public class MiscPEMGenerator
implements PemObjectGenerator {
    private static final ASN1ObjectIdentifier[] dsaOids = new ASN1ObjectIdentifier[]{X9ObjectIdentifiers.id_dsa, OIWObjectIdentifiers.dsaWithSHA1};
    private static final byte[] hexEncodingTable = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    private final Object obj;
    private final PEMEncryptor encryptor;

    public MiscPEMGenerator(Object object) {
        this.obj = object;
        this.encryptor = null;
    }

    public MiscPEMGenerator(Object object, PEMEncryptor pEMEncryptor) {
        this.obj = object;
        this.encryptor = pEMEncryptor;
    }

    private PemObject createPemObject(Object object) throws IOException {
        ASN1EncodableVector aSN1EncodableVector;
        Object object2;
        Object object3;
        Object object4;
        byte[] byArray;
        String string;
        if (object instanceof PemObject) {
            return (PemObject)object;
        }
        if (object instanceof PemObjectGenerator) {
            return ((PemObjectGenerator)object).generate();
        }
        if (object instanceof X509CertificateHolder) {
            string = "CERTIFICATE";
            byArray = ((X509CertificateHolder)object).getEncoded();
        } else if (object instanceof X509CRLHolder) {
            string = "X509 CRL";
            byArray = ((X509CRLHolder)object).getEncoded();
        } else if (object instanceof X509TrustedCertificateBlock) {
            string = "TRUSTED CERTIFICATE";
            byArray = ((X509TrustedCertificateBlock)object).getEncoded();
        } else if (object instanceof PrivateKeyInfo) {
            object4 = (PrivateKeyInfo)object;
            object3 = object4.getPrivateKeyAlgorithm().getAlgorithm();
            if (object3.equals((ASN1Primitive)PKCSObjectIdentifiers.rsaEncryption)) {
                string = "RSA PRIVATE KEY";
                byArray = object4.parsePrivateKey().toASN1Primitive().getEncoded();
            } else if (object3.equals((ASN1Primitive)dsaOids[0]) || object3.equals((ASN1Primitive)dsaOids[1])) {
                string = "DSA PRIVATE KEY";
                object2 = DSAParameter.getInstance((Object)object4.getPrivateKeyAlgorithm().getParameters());
                aSN1EncodableVector = new ASN1EncodableVector();
                aSN1EncodableVector.add((ASN1Encodable)new ASN1Integer(0L));
                aSN1EncodableVector.add((ASN1Encodable)new ASN1Integer(object2.getP()));
                aSN1EncodableVector.add((ASN1Encodable)new ASN1Integer(object2.getQ()));
                aSN1EncodableVector.add((ASN1Encodable)new ASN1Integer(object2.getG()));
                BigInteger bigInteger = ASN1Integer.getInstance((Object)object4.parsePrivateKey()).getValue();
                BigInteger bigInteger2 = object2.getG().modPow(bigInteger, object2.getP());
                aSN1EncodableVector.add((ASN1Encodable)new ASN1Integer(bigInteger2));
                aSN1EncodableVector.add((ASN1Encodable)new ASN1Integer(bigInteger));
                byArray = new DERSequence(aSN1EncodableVector).getEncoded();
            } else if (object3.equals((ASN1Primitive)X9ObjectIdentifiers.id_ecPublicKey)) {
                string = "EC PRIVATE KEY";
                byArray = object4.parsePrivateKey().toASN1Primitive().getEncoded();
            } else {
                string = "PRIVATE KEY";
                byArray = object4.getEncoded();
            }
        } else if (object instanceof SubjectPublicKeyInfo) {
            string = "PUBLIC KEY";
            byArray = ((SubjectPublicKeyInfo)object).getEncoded();
        } else if (object instanceof X509AttributeCertificateHolder) {
            string = "ATTRIBUTE CERTIFICATE";
            byArray = ((X509AttributeCertificateHolder)object).getEncoded();
        } else if (object instanceof PKCS10CertificationRequest) {
            string = "CERTIFICATE REQUEST";
            byArray = ((PKCS10CertificationRequest)object).getEncoded();
        } else if (object instanceof PKCS8EncryptedPrivateKeyInfo) {
            string = "ENCRYPTED PRIVATE KEY";
            byArray = ((PKCS8EncryptedPrivateKeyInfo)object).getEncoded();
        } else if (object instanceof ContentInfo) {
            string = "PKCS7";
            byArray = ((ContentInfo)object).getEncoded();
        } else {
            throw new PemGenerationException("unknown object passed - can't encode.");
        }
        if (this.encryptor != null) {
            object4 = Strings.toUpperCase((String)this.encryptor.getAlgorithm());
            if (((String)object4).equals("DESEDE")) {
                object4 = "DES-EDE3-CBC";
            }
            object3 = this.encryptor.getIV();
            object2 = this.encryptor.encrypt(byArray);
            aSN1EncodableVector = new ArrayList(2);
            aSN1EncodableVector.add(new PemHeader("Proc-Type", "4,ENCRYPTED"));
            aSN1EncodableVector.add(new PemHeader("DEK-Info", (String)object4 + "," + this.getHexEncoded((byte[])object3)));
            return new PemObject(string, (List)aSN1EncodableVector, object2);
        }
        return new PemObject(string, byArray);
    }

    private String getHexEncoded(byte[] byArray) throws IOException {
        char[] cArray = new char[byArray.length * 2];
        for (int i = 0; i != byArray.length; ++i) {
            int n = byArray[i] & 0xFF;
            cArray[2 * i] = (char)hexEncodingTable[n >>> 4];
            cArray[2 * i + 1] = (char)hexEncodingTable[n & 0xF];
        }
        return new String(cArray);
    }

    public PemObject generate() throws PemGenerationException {
        try {
            return this.createPemObject(this.obj);
        }
        catch (IOException iOException) {
            throw new PemGenerationException("encoding exception: " + iOException.getMessage(), (Throwable)iOException);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.x509.GeneralName
 *  org.bouncycastle.asn1.x509.GeneralNames
 *  org.bouncycastle.asn1.x509.GeneralNamesBuilder
 *  org.bouncycastle.asn1.x509.KeyPurposeId
 *  org.bouncycastle.asn1.x509.KeyUsage
 *  org.bouncycastle.asn1.x509.PolicyInformation
 */
package org.cryptacular.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.GeneralNamesBuilder;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.util.CodecUtil;
import org.cryptacular.util.KeyPairUtil;
import org.cryptacular.util.StreamUtil;
import org.cryptacular.x509.ExtensionReader;
import org.cryptacular.x509.GeneralNameType;
import org.cryptacular.x509.KeyUsageBits;
import org.cryptacular.x509.dn.NameReader;
import org.cryptacular.x509.dn.StandardAttributeType;

public final class CertUtil {
    private CertUtil() {
    }

    public static String subjectCN(X509Certificate cert) throws EncodingException {
        return new NameReader(cert).readSubject().getValue(StandardAttributeType.CommonName);
    }

    public static GeneralNames subjectAltNames(X509Certificate cert) throws EncodingException {
        return new ExtensionReader(cert).readSubjectAlternativeName();
    }

    public static GeneralNames subjectAltNames(X509Certificate cert, GeneralNameType ... types) throws EncodingException {
        GeneralNames names;
        GeneralNamesBuilder builder = new GeneralNamesBuilder();
        GeneralNames altNames = CertUtil.subjectAltNames(cert);
        if (altNames != null) {
            for (GeneralName name : altNames.getNames()) {
                for (GeneralNameType type : types) {
                    if (type.ordinal() != name.getTagNo()) continue;
                    builder.addName(name);
                }
            }
        }
        if ((names = builder.build()).getNames().length == 0) {
            return null;
        }
        return names;
    }

    public static List<String> subjectNames(X509Certificate cert) throws EncodingException {
        GeneralNames altNames;
        ArrayList<String> names = new ArrayList<String>();
        String cn = CertUtil.subjectCN(cert);
        if (cn != null) {
            names.add(cn);
        }
        if ((altNames = CertUtil.subjectAltNames(cert)) == null) {
            return names;
        }
        for (GeneralName name : altNames.getNames()) {
            names.add(name.getName().toString());
        }
        return names;
    }

    public static List<String> subjectNames(X509Certificate cert, GeneralNameType ... types) throws EncodingException {
        GeneralNames altNames;
        ArrayList<String> names = new ArrayList<String>();
        String cn = CertUtil.subjectCN(cert);
        if (cn != null) {
            names.add(cn);
        }
        if ((altNames = CertUtil.subjectAltNames(cert, types)) == null) {
            return names;
        }
        for (GeneralName name : altNames.getNames()) {
            names.add(name.getName().toString());
        }
        return names;
    }

    public static X509Certificate findEntityCertificate(PrivateKey key, X509Certificate ... candidates) throws EncodingException {
        return CertUtil.findEntityCertificate(key, Arrays.asList(candidates));
    }

    public static X509Certificate findEntityCertificate(PrivateKey key, Collection<X509Certificate> candidates) throws EncodingException {
        for (X509Certificate candidate : candidates) {
            if (!KeyPairUtil.isKeyPair(candidate.getPublicKey(), key)) continue;
            return candidate;
        }
        return null;
    }

    public static X509Certificate readCertificate(String path) throws EncodingException, StreamException {
        return CertUtil.readCertificate(StreamUtil.makeStream(new File(path)));
    }

    public static X509Certificate readCertificate(File file) throws EncodingException, StreamException {
        return CertUtil.readCertificate(StreamUtil.makeStream(file));
    }

    public static X509Certificate readCertificate(InputStream in) throws EncodingException, StreamException {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            return (X509Certificate)factory.generateCertificate(in);
        }
        catch (CertificateException e) {
            if (e.getCause() instanceof IOException) {
                throw new StreamException((IOException)e.getCause());
            }
            throw new EncodingException("Cannot decode certificate", e);
        }
    }

    public static X509Certificate decodeCertificate(byte[] encoded) throws EncodingException {
        return CertUtil.readCertificate(new ByteArrayInputStream(encoded));
    }

    public static X509Certificate[] readCertificateChain(String path) throws EncodingException, StreamException {
        return CertUtil.readCertificateChain(StreamUtil.makeStream(new File(path)));
    }

    public static X509Certificate[] readCertificateChain(File file) throws EncodingException, StreamException {
        return CertUtil.readCertificateChain(StreamUtil.makeStream(file));
    }

    public static X509Certificate[] readCertificateChain(InputStream in) throws EncodingException, StreamException {
        try {
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            Collection<? extends Certificate> certs = factory.generateCertificates(in);
            return certs.toArray(new X509Certificate[certs.size()]);
        }
        catch (CertificateException e) {
            if (e.getCause() instanceof IOException) {
                throw new StreamException((IOException)e.getCause());
            }
            throw new EncodingException("Cannot decode certificate", e);
        }
    }

    public static X509Certificate[] decodeCertificateChain(byte[] encoded) throws EncodingException {
        return CertUtil.readCertificateChain(new ByteArrayInputStream(encoded));
    }

    public static boolean allowsUsage(X509Certificate cert, KeyUsageBits ... bits) throws EncodingException {
        KeyUsage usage = new ExtensionReader(cert).readKeyUsage();
        for (KeyUsageBits bit : bits) {
            if (bit.isSet(usage)) continue;
            return false;
        }
        return true;
    }

    public static boolean allowsUsage(X509Certificate cert, KeyPurposeId ... purposes) throws EncodingException {
        List<KeyPurposeId> allowedUses = new ExtensionReader(cert).readExtendedKeyUsage();
        for (KeyPurposeId purpose : purposes) {
            if (allowedUses != null && allowedUses.contains(purpose)) continue;
            return false;
        }
        return true;
    }

    public static boolean hasPolicies(X509Certificate cert, String ... policyOidsToCheck) throws EncodingException {
        List<PolicyInformation> policies = new ExtensionReader(cert).readCertificatePolicies();
        for (String policyOid : policyOidsToCheck) {
            boolean hasPolicy = false;
            if (policies != null) {
                for (PolicyInformation policy : policies) {
                    if (!policy.getPolicyIdentifier().getId().equals(policyOid)) continue;
                    hasPolicy = true;
                    break;
                }
            }
            if (hasPolicy) continue;
            return false;
        }
        return true;
    }

    public static String subjectKeyId(X509Certificate cert) throws EncodingException {
        return CodecUtil.hex(new ExtensionReader(cert).readSubjectKeyIdentifier().getKeyIdentifier(), true);
    }

    public static String authorityKeyId(X509Certificate cert) throws EncodingException {
        return CodecUtil.hex(new ExtensionReader(cert).readAuthorityKeyIdentifier().getKeyIdentifier(), true);
    }
}


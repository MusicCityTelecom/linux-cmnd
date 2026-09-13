/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.apk.v1;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.internal.apk.v1.DigestAlgorithm;
import com.android.apksig.internal.asn1.Asn1DerEncoder;
import com.android.apksig.internal.asn1.Asn1EncodingException;
import com.android.apksig.internal.asn1.Asn1OpaqueObject;
import com.android.apksig.internal.jar.ManifestWriter;
import com.android.apksig.internal.jar.SignatureFileWriter;
import com.android.apksig.internal.pkcs7.AlgorithmIdentifier;
import com.android.apksig.internal.pkcs7.ContentInfo;
import com.android.apksig.internal.pkcs7.EncapsulatedContentInfo;
import com.android.apksig.internal.pkcs7.IssuerAndSerialNumber;
import com.android.apksig.internal.pkcs7.SignedData;
import com.android.apksig.internal.pkcs7.SignerIdentifier;
import com.android.apksig.internal.pkcs7.SignerInfo;
import com.android.apksig.internal.util.Pair;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.security.auth.x500.X500Principal;

public abstract class V1SchemeSigner {
    public static final String MANIFEST_ENTRY_NAME = "META-INF/MANIFEST.MF";
    private static final Attributes.Name ATTRIBUTE_NAME_CREATED_BY = new Attributes.Name("Created-By");
    private static final String ATTRIBUTE_VALUE_MANIFEST_VERSION = "1.0";
    private static final String ATTRIBUTE_VALUE_SIGNATURE_VERSION = "1.0";
    static final String SF_ATTRIBUTE_NAME_ANDROID_APK_SIGNED_NAME_STR = "X-Android-APK-Signed";
    private static final Attributes.Name SF_ATTRIBUTE_NAME_ANDROID_APK_SIGNED_NAME = new Attributes.Name("X-Android-APK-Signed");
    private static final Asn1OpaqueObject ASN1_DER_NULL = new Asn1OpaqueObject(new byte[]{5, 0});

    private V1SchemeSigner() {
    }

    public static DigestAlgorithm getSuggestedSignatureDigestAlgorithm(PublicKey signingKey, int minSdkVersion) throws InvalidKeyException {
        String keyAlgorithm = signingKey.getAlgorithm();
        if ("RSA".equalsIgnoreCase(keyAlgorithm)) {
            if (minSdkVersion < 18) {
                return DigestAlgorithm.SHA1;
            }
            return DigestAlgorithm.SHA256;
        }
        if ("DSA".equalsIgnoreCase(keyAlgorithm)) {
            if (minSdkVersion < 21) {
                return DigestAlgorithm.SHA1;
            }
            return DigestAlgorithm.SHA256;
        }
        if ("EC".equalsIgnoreCase(keyAlgorithm)) {
            if (minSdkVersion < 18) {
                throw new InvalidKeyException("ECDSA signatures only supported for minSdkVersion 18 and higher");
            }
            return DigestAlgorithm.SHA256;
        }
        throw new InvalidKeyException("Unsupported key algorithm: " + keyAlgorithm);
    }

    public static String getSafeSignerName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Empty name");
        }
        StringBuilder result = new StringBuilder();
        char[] nameCharsUpperCase = name.toUpperCase(Locale.US).toCharArray();
        for (int i2 = 0; i2 < Math.min(nameCharsUpperCase.length, 8); ++i2) {
            char c2 = nameCharsUpperCase[i2];
            if (c2 >= 'A' && c2 <= 'Z' || c2 >= '0' && c2 <= '9' || c2 == '-' || c2 == '_') {
                result.append(c2);
                continue;
            }
            result.append('_');
        }
        return result.toString();
    }

    private static MessageDigest getMessageDigestInstance(DigestAlgorithm digestAlgorithm) throws NoSuchAlgorithmException {
        String jcaAlgorithm = digestAlgorithm.getJcaMessageDigestAlgorithm();
        return MessageDigest.getInstance(jcaAlgorithm);
    }

    public static String getJcaMessageDigestAlgorithm(DigestAlgorithm digestAlgorithm) {
        return digestAlgorithm.getJcaMessageDigestAlgorithm();
    }

    public static boolean isJarEntryDigestNeededInManifest(String entryName) {
        if (entryName.endsWith("/")) {
            return false;
        }
        if (!entryName.startsWith("META-INF/")) {
            return true;
        }
        if (entryName.indexOf(47, "META-INF/".length()) != -1) {
            return true;
        }
        String fileNameLowerCase = entryName.substring("META-INF/".length()).toLowerCase(Locale.US);
        return !"manifest.mf".equals(fileNameLowerCase) && !fileNameLowerCase.endsWith(".sf") && !fileNameLowerCase.endsWith(".rsa") && !fileNameLowerCase.endsWith(".dsa") && !fileNameLowerCase.endsWith(".ec") && !fileNameLowerCase.startsWith("sig-");
    }

    public static List<Pair<String, byte[]>> sign(List<SignerConfig> signerConfigs, DigestAlgorithm jarEntryDigestAlgorithm, Map<String, byte[]> jarEntryDigests, List<Integer> apkSigningSchemeIds, byte[] sourceManifestBytes, String createdBy) throws NoSuchAlgorithmException, ApkFormatException, InvalidKeyException, CertificateException, SignatureException {
        if (signerConfigs.isEmpty()) {
            throw new IllegalArgumentException("At least one signer config must be provided");
        }
        OutputManifestFile manifest = V1SchemeSigner.generateManifestFile(jarEntryDigestAlgorithm, jarEntryDigests, sourceManifestBytes);
        return V1SchemeSigner.signManifest(signerConfigs, jarEntryDigestAlgorithm, apkSigningSchemeIds, createdBy, manifest);
    }

    public static List<Pair<String, byte[]>> signManifest(List<SignerConfig> signerConfigs, DigestAlgorithm digestAlgorithm, List<Integer> apkSigningSchemeIds, String createdBy, OutputManifestFile manifest) throws NoSuchAlgorithmException, InvalidKeyException, CertificateException, SignatureException {
        if (signerConfigs.isEmpty()) {
            throw new IllegalArgumentException("At least one signer config must be provided");
        }
        ArrayList<Pair<String, byte[]>> signatureJarEntries = new ArrayList<Pair<String, byte[]>>(2 * signerConfigs.size() + 1);
        byte[] sfBytes = V1SchemeSigner.generateSignatureFile(apkSigningSchemeIds, digestAlgorithm, createdBy, manifest);
        for (SignerConfig signerConfig : signerConfigs) {
            byte[] signatureBlock;
            String signerName = signerConfig.name;
            try {
                signatureBlock = V1SchemeSigner.generateSignatureBlock(signerConfig, sfBytes);
            }
            catch (InvalidKeyException e2) {
                throw new InvalidKeyException("Failed to sign using signer \"" + signerName + "\"", e2);
            }
            catch (CertificateException e3) {
                throw new CertificateException("Failed to sign using signer \"" + signerName + "\"", e3);
            }
            catch (SignatureException e4) {
                throw new SignatureException("Failed to sign using signer \"" + signerName + "\"", e4);
            }
            signatureJarEntries.add(Pair.of("META-INF/" + signerName + ".SF", sfBytes));
            PublicKey publicKey = signerConfig.certificates.get(0).getPublicKey();
            String signatureBlockFileName = "META-INF/" + signerName + "." + publicKey.getAlgorithm().toUpperCase(Locale.US);
            signatureJarEntries.add(Pair.of(signatureBlockFileName, signatureBlock));
        }
        signatureJarEntries.add(Pair.of(MANIFEST_ENTRY_NAME, manifest.contents));
        return signatureJarEntries;
    }

    public static Set<String> getOutputEntryNames(List<SignerConfig> signerConfigs) {
        HashSet<String> result = new HashSet<String>(2 * signerConfigs.size() + 1);
        for (SignerConfig signerConfig : signerConfigs) {
            String signerName = signerConfig.name;
            result.add("META-INF/" + signerName + ".SF");
            PublicKey publicKey = signerConfig.certificates.get(0).getPublicKey();
            String signatureBlockFileName = "META-INF/" + signerName + "." + publicKey.getAlgorithm().toUpperCase(Locale.US);
            result.add(signatureBlockFileName);
        }
        result.add(MANIFEST_ENTRY_NAME);
        return result;
    }

    public static OutputManifestFile generateManifestFile(DigestAlgorithm jarEntryDigestAlgorithm, Map<String, byte[]> jarEntryDigests, byte[] sourceManifestBytes) throws ApkFormatException {
        Manifest sourceManifest = null;
        if (sourceManifestBytes != null) {
            try {
                sourceManifest = new Manifest(new ByteArrayInputStream(sourceManifestBytes));
            }
            catch (IOException e2) {
                throw new ApkFormatException("Malformed source META-INF/MANIFEST.MF", e2);
            }
        }
        ByteArrayOutputStream manifestOut = new ByteArrayOutputStream();
        Attributes mainAttrs = new Attributes();
        if (sourceManifest != null) {
            mainAttrs.putAll((Map<?, ?>)sourceManifest.getMainAttributes());
        } else {
            mainAttrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        }
        try {
            ManifestWriter.writeMainSection(manifestOut, mainAttrs);
        }
        catch (IOException e3) {
            throw new RuntimeException("Failed to write in-memory MANIFEST.MF", e3);
        }
        ArrayList<String> sortedEntryNames = new ArrayList<String>(jarEntryDigests.keySet());
        Collections.sort(sortedEntryNames);
        TreeMap<String, byte[]> invidualSectionsContents = new TreeMap<String, byte[]>();
        String entryDigestAttributeName = V1SchemeSigner.getEntryDigestAttributeName(jarEntryDigestAlgorithm);
        for (String entryName : sortedEntryNames) {
            byte[] sectionBytes;
            V1SchemeSigner.checkEntryNameValid(entryName);
            byte[] entryDigest = jarEntryDigests.get(entryName);
            Attributes entryAttrs = new Attributes();
            entryAttrs.putValue(entryDigestAttributeName, Base64.getEncoder().encodeToString(entryDigest));
            ByteArrayOutputStream sectionOut = new ByteArrayOutputStream();
            try {
                ManifestWriter.writeIndividualSection(sectionOut, entryName, entryAttrs);
                sectionBytes = sectionOut.toByteArray();
                manifestOut.write(sectionBytes);
            }
            catch (IOException e4) {
                throw new RuntimeException("Failed to write in-memory MANIFEST.MF", e4);
            }
            invidualSectionsContents.put(entryName, sectionBytes);
        }
        OutputManifestFile result = new OutputManifestFile();
        result.contents = manifestOut.toByteArray();
        result.mainSectionAttributes = mainAttrs;
        result.individualSectionsContents = invidualSectionsContents;
        return result;
    }

    private static void checkEntryNameValid(String name) throws ApkFormatException {
        for (char c2 : name.toCharArray()) {
            if (c2 != '\r' && c2 != '\n' && c2 != '\u0000') continue;
            throw new ApkFormatException(String.format("Unsupported character 0x%1$02x in ZIP entry name \"%2$s\"", c2, name));
        }
    }

    private static byte[] generateSignatureFile(List<Integer> apkSignatureSchemeIds, DigestAlgorithm manifestDigestAlgorithm, String createdBy, OutputManifestFile manifest) throws NoSuchAlgorithmException {
        Manifest sf = new Manifest();
        Attributes mainAttrs = sf.getMainAttributes();
        mainAttrs.put(Attributes.Name.SIGNATURE_VERSION, "1.0");
        mainAttrs.put(ATTRIBUTE_NAME_CREATED_BY, createdBy);
        if (!apkSignatureSchemeIds.isEmpty()) {
            StringBuilder attrValue = new StringBuilder();
            for (int id : apkSignatureSchemeIds) {
                if (attrValue.length() > 0) {
                    attrValue.append(", ");
                }
                attrValue.append(String.valueOf(id));
            }
            mainAttrs.put(SF_ATTRIBUTE_NAME_ANDROID_APK_SIGNED_NAME, attrValue.toString());
        }
        MessageDigest md = V1SchemeSigner.getMessageDigestInstance(manifestDigestAlgorithm);
        mainAttrs.putValue(V1SchemeSigner.getManifestDigestAttributeName(manifestDigestAlgorithm), Base64.getEncoder().encodeToString(md.digest(manifest.contents)));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            SignatureFileWriter.writeMainSection(out, mainAttrs);
        }
        catch (IOException e2) {
            throw new RuntimeException("Failed to write in-memory .SF file", e2);
        }
        String entryDigestAttributeName = V1SchemeSigner.getEntryDigestAttributeName(manifestDigestAlgorithm);
        for (Map.Entry<String, byte[]> manifestSection : manifest.individualSectionsContents.entrySet()) {
            String sectionName = manifestSection.getKey();
            byte[] sectionContents = manifestSection.getValue();
            byte[] sectionDigest = md.digest(sectionContents);
            Attributes attrs = new Attributes();
            attrs.putValue(entryDigestAttributeName, Base64.getEncoder().encodeToString(sectionDigest));
            try {
                SignatureFileWriter.writeIndividualSection(out, sectionName, attrs);
            }
            catch (IOException e3) {
                throw new RuntimeException("Failed to write in-memory .SF file", e3);
            }
        }
        if (out.size() > 0 && out.size() % 1024 == 0) {
            try {
                SignatureFileWriter.writeSectionDelimiter(out);
            }
            catch (IOException e4) {
                throw new RuntimeException("Failed to write to ByteArrayOutputStream", e4);
            }
        }
        return out.toByteArray();
    }

    private static byte[] generateSignatureBlock(SignerConfig signerConfig, byte[] signatureFileBytes) throws NoSuchAlgorithmException, InvalidKeyException, CertificateException, SignatureException {
        byte[] signatureBytes;
        Signature signature;
        List<X509Certificate> signerCerts = signerConfig.certificates;
        X509Certificate signingCert = signerCerts.get(0);
        PublicKey publicKey = signingCert.getPublicKey();
        DigestAlgorithm digestAlgorithm = signerConfig.signatureDigestAlgorithm;
        Pair<String, AlgorithmIdentifier> signatureAlgs = V1SchemeSigner.getSignerInfoSignatureAlgorithm(publicKey, digestAlgorithm);
        String jcaSignatureAlgorithm = signatureAlgs.getFirst();
        try {
            signature = Signature.getInstance(jcaSignatureAlgorithm);
            signature.initSign(signerConfig.privateKey);
            signature.update(signatureFileBytes);
            signatureBytes = signature.sign();
        }
        catch (InvalidKeyException e2) {
            throw new InvalidKeyException("Failed to sign using " + jcaSignatureAlgorithm, e2);
        }
        catch (SignatureException e3) {
            throw new SignatureException("Failed to sign using " + jcaSignatureAlgorithm, e3);
        }
        try {
            signature = Signature.getInstance(jcaSignatureAlgorithm);
            signature.initVerify(publicKey);
            signature.update(signatureFileBytes);
            if (!signature.verify(signatureBytes)) {
                throw new SignatureException("Signature did not verify");
            }
        }
        catch (InvalidKeyException e4) {
            throw new InvalidKeyException("Failed to verify generated " + jcaSignatureAlgorithm + " signature using public key from certificate", e4);
        }
        catch (SignatureException e5) {
            throw new SignatureException("Failed to verify generated " + jcaSignatureAlgorithm + " signature using public key from certificate", e5);
        }
        try {
            SignerInfo signerInfo = new SignerInfo();
            signerInfo.version = 1;
            X500Principal signerCertIssuer = signingCert.getIssuerX500Principal();
            signerInfo.sid = new SignerIdentifier(new IssuerAndSerialNumber(new Asn1OpaqueObject(signerCertIssuer.getEncoded()), signingCert.getSerialNumber()));
            AlgorithmIdentifier digestAlgorithmId = V1SchemeSigner.getSignerInfoDigestAlgorithmOid(digestAlgorithm);
            AlgorithmIdentifier signatureAlgorithmId = signatureAlgs.getSecond();
            signerInfo.digestAlgorithm = digestAlgorithmId;
            signerInfo.signatureAlgorithm = signatureAlgorithmId;
            signerInfo.signature = ByteBuffer.wrap(signatureBytes);
            SignedData signedData = new SignedData();
            signedData.certificates = new ArrayList<Asn1OpaqueObject>(signerCerts.size());
            for (X509Certificate cert : signerCerts) {
                signedData.certificates.add(new Asn1OpaqueObject(cert.getEncoded()));
            }
            signedData.version = 1;
            signedData.digestAlgorithms = Collections.singletonList(digestAlgorithmId);
            signedData.encapContentInfo = new EncapsulatedContentInfo("1.2.840.113549.1.7.1");
            signedData.signerInfos = Collections.singletonList(signerInfo);
            ContentInfo contentInfo = new ContentInfo();
            contentInfo.contentType = "1.2.840.113549.1.7.2";
            contentInfo.content = new Asn1OpaqueObject(Asn1DerEncoder.encode(signedData));
            return Asn1DerEncoder.encode(contentInfo);
        }
        catch (Asn1EncodingException e6) {
            throw new SignatureException("Failed to encode signature block", e6);
        }
    }

    private static AlgorithmIdentifier getSignerInfoDigestAlgorithmOid(DigestAlgorithm digestAlgorithm) {
        switch (digestAlgorithm) {
            case SHA1: {
                return new AlgorithmIdentifier("1.3.14.3.2.26", ASN1_DER_NULL);
            }
            case SHA256: {
                return new AlgorithmIdentifier("2.16.840.1.101.3.4.2.1", ASN1_DER_NULL);
            }
        }
        throw new RuntimeException("Unsupported digest algorithm: " + (Object)((Object)digestAlgorithm));
    }

    private static Pair<String, AlgorithmIdentifier> getSignerInfoSignatureAlgorithm(PublicKey publicKey, DigestAlgorithm digestAlgorithm) throws InvalidKeyException {
        String jcaDigestPrefixForSigAlg;
        String keyAlgorithm = publicKey.getAlgorithm();
        switch (digestAlgorithm) {
            case SHA1: {
                jcaDigestPrefixForSigAlg = "SHA1";
                break;
            }
            case SHA256: {
                jcaDigestPrefixForSigAlg = "SHA256";
                break;
            }
            default: {
                throw new IllegalArgumentException("Unexpected digest algorithm: " + (Object)((Object)digestAlgorithm));
            }
        }
        if ("RSA".equalsIgnoreCase(keyAlgorithm)) {
            return Pair.of(jcaDigestPrefixForSigAlg + "withRSA", new AlgorithmIdentifier("1.2.840.113549.1.1.1", ASN1_DER_NULL));
        }
        if ("DSA".equalsIgnoreCase(keyAlgorithm)) {
            AlgorithmIdentifier sigAlgId;
            switch (digestAlgorithm) {
                case SHA1: {
                    sigAlgId = new AlgorithmIdentifier("1.2.840.10040.4.1", ASN1_DER_NULL);
                    break;
                }
                case SHA256: {
                    sigAlgId = new AlgorithmIdentifier("2.16.840.1.101.3.4.3.2", ASN1_DER_NULL);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unexpected digest algorithm: " + (Object)((Object)digestAlgorithm));
                }
            }
            return Pair.of(jcaDigestPrefixForSigAlg + "withDSA", sigAlgId);
        }
        if ("EC".equalsIgnoreCase(keyAlgorithm)) {
            return Pair.of(jcaDigestPrefixForSigAlg + "withECDSA", new AlgorithmIdentifier("1.2.840.10045.2.1", ASN1_DER_NULL));
        }
        throw new InvalidKeyException("Unsupported key algorithm: " + keyAlgorithm);
    }

    private static String getEntryDigestAttributeName(DigestAlgorithm digestAlgorithm) {
        switch (digestAlgorithm) {
            case SHA1: {
                return "SHA1-Digest";
            }
            case SHA256: {
                return "SHA-256-Digest";
            }
        }
        throw new IllegalArgumentException("Unexpected content digest algorithm: " + (Object)((Object)digestAlgorithm));
    }

    private static String getManifestDigestAttributeName(DigestAlgorithm digestAlgorithm) {
        switch (digestAlgorithm) {
            case SHA1: {
                return "SHA1-Digest-Manifest";
            }
            case SHA256: {
                return "SHA-256-Digest-Manifest";
            }
        }
        throw new IllegalArgumentException("Unexpected content digest algorithm: " + (Object)((Object)digestAlgorithm));
    }

    public static class OutputManifestFile {
        public byte[] contents;
        public SortedMap<String, byte[]> individualSectionsContents;
        public Attributes mainSectionAttributes;
    }

    public static class SignerConfig {
        public String name;
        public PrivateKey privateKey;
        public List<X509Certificate> certificates;
        public DigestAlgorithm signatureDigestAlgorithm;
    }
}


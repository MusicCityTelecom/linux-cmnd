/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.apk.v1;

import com.android.apksig.ApkVerifier;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.asn1.Asn1BerParser;
import com.android.apksig.internal.asn1.Asn1Class;
import com.android.apksig.internal.asn1.Asn1DecodingException;
import com.android.apksig.internal.asn1.Asn1Field;
import com.android.apksig.internal.asn1.Asn1OpaqueObject;
import com.android.apksig.internal.asn1.Asn1Type;
import com.android.apksig.internal.jar.ManifestParser;
import com.android.apksig.internal.pkcs7.Attribute;
import com.android.apksig.internal.pkcs7.ContentInfo;
import com.android.apksig.internal.pkcs7.IssuerAndSerialNumber;
import com.android.apksig.internal.pkcs7.Pkcs7DecodingException;
import com.android.apksig.internal.pkcs7.SignedData;
import com.android.apksig.internal.pkcs7.SignerIdentifier;
import com.android.apksig.internal.pkcs7.SignerInfo;
import com.android.apksig.internal.util.ByteBufferUtils;
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate;
import com.android.apksig.internal.util.InclusiveIntRange;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.LocalFileRecord;
import com.android.apksig.util.DataSinks;
import com.android.apksig.util.DataSource;
import com.android.apksig.zip.ZipFormatException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.Attributes;
import javax.security.auth.x500.X500Principal;

public abstract class V1SchemeVerifier {
    private static final String MANIFEST_ENTRY_NAME = "META-INF/MANIFEST.MF";
    private static final String[] JB_MR2_AND_NEWER_DIGEST_ALGS = new String[]{"SHA-512", "SHA-384", "SHA-256", "SHA-1"};
    private static final Map<String, String> UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL = new HashMap<String, String>(8);
    private static final Map<String, Integer> MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST;

    private V1SchemeVerifier() {
    }

    public static Result verify(DataSource apk, ApkUtils.ZipSections apkSections, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds, int minSdkVersion, int maxSdkVersion) throws IOException, ApkFormatException, NoSuchAlgorithmException {
        if (minSdkVersion > maxSdkVersion) {
            throw new IllegalArgumentException("minSdkVersion (" + minSdkVersion + ") > maxSdkVersion (" + maxSdkVersion + ")");
        }
        Result result = new Result();
        List<CentralDirectoryRecord> cdRecords = V1SchemeVerifier.parseZipCentralDirectory(apk, apkSections);
        Set<String> cdEntryNames = V1SchemeVerifier.checkForDuplicateEntries(cdRecords, result);
        if (result.containsErrors()) {
            return result;
        }
        Signers.verify(apk, apkSections.getZipCentralDirectoryOffset(), cdRecords, cdEntryNames, supportedApkSigSchemeNames, foundApkSigSchemeIds, minSdkVersion, maxSdkVersion, result);
        return result;
    }

    private static Set<String> checkForDuplicateEntries(List<CentralDirectoryRecord> cdRecords, Result result) {
        HashSet<String> cdEntryNames = new HashSet<String>(cdRecords.size());
        HashSet<String> duplicateCdEntryNames = null;
        for (CentralDirectoryRecord cdRecord : cdRecords) {
            String entryName = cdRecord.getName();
            if (cdEntryNames.add(entryName)) continue;
            if (duplicateCdEntryNames == null) {
                duplicateCdEntryNames = new HashSet<String>();
            }
            if (!duplicateCdEntryNames.add(entryName)) continue;
            result.addError(ApkVerifier.Issue.JAR_SIG_DUPLICATE_ZIP_ENTRY, new Object[]{entryName});
        }
        return cdEntryNames;
    }

    private static Collection<NamedDigest> getDigestsToVerify(ManifestParser.Section section, String digestAttrSuffix, int minSdkVersion, int maxSdkVersion) {
        Base64.Decoder base64Decoder = Base64.getDecoder();
        ArrayList<NamedDigest> result = new ArrayList<NamedDigest>(1);
        if (minSdkVersion < 18) {
            String algs = section.getAttributeValue("Digest-Algorithms");
            if (algs == null) {
                algs = "SHA SHA1";
            }
            StringTokenizer tokens = new StringTokenizer(algs);
            while (tokens.hasMoreTokens()) {
                String alg = tokens.nextToken();
                String attrName = alg + digestAttrSuffix;
                String digestBase64 = section.getAttributeValue(attrName);
                if (digestBase64 == null || (alg = V1SchemeVerifier.getCanonicalJcaMessageDigestAlgorithm(alg)) == null || V1SchemeVerifier.getMinSdkVersionFromWhichSupportedInManifestOrSignatureFile(alg) > minSdkVersion) continue;
                result.add(new NamedDigest(alg, base64Decoder.decode(digestBase64)));
                break;
            }
            if (result.isEmpty()) {
                return result;
            }
        }
        if (maxSdkVersion >= 18) {
            for (String alg : JB_MR2_AND_NEWER_DIGEST_ALGS) {
                String attrName = V1SchemeVerifier.getJarDigestAttributeName(alg, digestAttrSuffix);
                String digestBase64 = section.getAttributeValue(attrName);
                if (digestBase64 == null) continue;
                byte[] digest = base64Decoder.decode(digestBase64);
                byte[] digestInResult = V1SchemeVerifier.getDigest(result, alg);
                if (digestInResult != null && Arrays.equals(digestInResult, digest)) break;
                result.add(new NamedDigest(alg, digest));
                break;
            }
        }
        return result;
    }

    private static String getCanonicalJcaMessageDigestAlgorithm(String algorithm) {
        return UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.get(algorithm.toUpperCase(Locale.US));
    }

    public static int getMinSdkVersionFromWhichSupportedInManifestOrSignatureFile(String jcaAlgorithmName) {
        Integer result = MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST.get(jcaAlgorithmName.toUpperCase(Locale.US));
        return result != null ? result : Integer.MAX_VALUE;
    }

    private static String getJarDigestAttributeName(String jcaDigestAlgorithm, String attrNameSuffix) {
        if ("SHA-1".equalsIgnoreCase(jcaDigestAlgorithm)) {
            return "SHA1" + attrNameSuffix;
        }
        return jcaDigestAlgorithm + attrNameSuffix;
    }

    private static byte[] getDigest(Collection<NamedDigest> digests, String jcaDigestAlgorithm) {
        for (NamedDigest digest : digests) {
            if (!digest.jcaDigestAlgorithm.equalsIgnoreCase(jcaDigestAlgorithm)) continue;
            return digest.digest;
        }
        return null;
    }

    public static List<CentralDirectoryRecord> parseZipCentralDirectory(DataSource apk, ApkUtils.ZipSections apkSections) throws IOException, ApkFormatException {
        long cdSizeBytes = apkSections.getZipCentralDirectorySizeBytes();
        if (cdSizeBytes > Integer.MAX_VALUE) {
            throw new ApkFormatException("ZIP Central Directory too large: " + cdSizeBytes);
        }
        long cdOffset = apkSections.getZipCentralDirectoryOffset();
        ByteBuffer cd2 = apk.getByteBuffer(cdOffset, (int)cdSizeBytes);
        cd2.order(ByteOrder.LITTLE_ENDIAN);
        int expectedCdRecordCount = apkSections.getZipCentralDirectoryRecordCount();
        ArrayList<CentralDirectoryRecord> cdRecords = new ArrayList<CentralDirectoryRecord>(expectedCdRecordCount);
        for (int i2 = 0; i2 < expectedCdRecordCount; ++i2) {
            CentralDirectoryRecord cdRecord;
            int offsetInsideCd = cd2.position();
            try {
                cdRecord = CentralDirectoryRecord.getRecord(cd2);
            }
            catch (ZipFormatException e2) {
                throw new ApkFormatException("Malformed ZIP Central Directory record #" + (i2 + 1) + " at file offset " + (cdOffset + (long)offsetInsideCd), e2);
            }
            String entryName = cdRecord.getName();
            if (entryName.endsWith("/")) continue;
            cdRecords.add(cdRecord);
        }
        return cdRecords;
    }

    private static boolean isJarEntryDigestNeededInManifest(String entryName) {
        if (entryName.startsWith("META-INF/")) {
            return false;
        }
        return !entryName.endsWith("/");
    }

    private static Set<Signer> verifyJarEntriesAgainstManifestAndSigners(DataSource apk, long cdOffsetInApk, Collection<CentralDirectoryRecord> cdRecords, Map<String, ManifestParser.Section> entryNameToManifestSection, List<Signer> signers, int minSdkVersion, int maxSdkVersion, Result result) throws ApkFormatException, IOException, NoSuchAlgorithmException {
        ArrayList<CentralDirectoryRecord> cdRecordsSortedByLocalFileHeaderOffset = new ArrayList<CentralDirectoryRecord>(cdRecords);
        Collections.sort(cdRecordsSortedByLocalFileHeaderOffset, CentralDirectoryRecord.BY_LOCAL_FILE_HEADER_OFFSET_COMPARATOR);
        HashSet<String> manifestEntryNamesMissingFromApk = new HashSet<String>(entryNameToManifestSection.keySet());
        ArrayList<Signer> firstSignedEntrySigners = null;
        String firstSignedEntryName = null;
        for (CentralDirectoryRecord cdRecord : cdRecordsSortedByLocalFileHeaderOffset) {
            int i2;
            String entryName = cdRecord.getName();
            manifestEntryNamesMissingFromApk.remove(entryName);
            if (!V1SchemeVerifier.isJarEntryDigestNeededInManifest(entryName)) continue;
            ManifestParser.Section manifestSection = entryNameToManifestSection.get(entryName);
            if (manifestSection == null) {
                result.addError(ApkVerifier.Issue.JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_MANIFEST, new Object[]{entryName});
                continue;
            }
            ArrayList<Signer> entrySigners = new ArrayList<Signer>(signers.size());
            for (Signer signer : signers) {
                if (!signer.getSigFileEntryNames().contains(entryName)) continue;
                entrySigners.add(signer);
            }
            if (entrySigners.isEmpty()) {
                result.addError(ApkVerifier.Issue.JAR_SIG_ZIP_ENTRY_NOT_SIGNED, new Object[]{entryName});
                continue;
            }
            if (firstSignedEntrySigners == null) {
                firstSignedEntrySigners = entrySigners;
                firstSignedEntryName = entryName;
            } else if (!entrySigners.equals(firstSignedEntrySigners)) {
                result.addError(ApkVerifier.Issue.JAR_SIG_ZIP_ENTRY_SIGNERS_MISMATCH, new Object[]{firstSignedEntryName, V1SchemeVerifier.getSignerNames(firstSignedEntrySigners), entryName, V1SchemeVerifier.getSignerNames(entrySigners)});
                continue;
            }
            ArrayList<NamedDigest> expectedDigests = new ArrayList<NamedDigest>(V1SchemeVerifier.getDigestsToVerify(manifestSection, "-Digest", minSdkVersion, maxSdkVersion));
            if (expectedDigests.isEmpty()) {
                result.addError(ApkVerifier.Issue.JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_MANIFEST, new Object[]{entryName});
                continue;
            }
            MessageDigest[] mds = new MessageDigest[expectedDigests.size()];
            for (i2 = 0; i2 < expectedDigests.size(); ++i2) {
                mds[i2] = V1SchemeVerifier.getMessageDigest(((NamedDigest)expectedDigests.get(i2)).jcaDigestAlgorithm);
            }
            try {
                LocalFileRecord.outputUncompressedData(apk, cdRecord, cdOffsetInApk, DataSinks.asDataSink(mds));
            }
            catch (ZipFormatException e2) {
                throw new ApkFormatException("Malformed ZIP entry: " + entryName, e2);
            }
            catch (IOException e3) {
                throw new IOException("Failed to read entry: " + entryName, e3);
            }
            for (i2 = 0; i2 < expectedDigests.size(); ++i2) {
                NamedDigest expectedDigest = (NamedDigest)expectedDigests.get(i2);
                byte[] actualDigest = mds[i2].digest();
                if (Arrays.equals(expectedDigest.digest, actualDigest)) continue;
                result.addError(ApkVerifier.Issue.JAR_SIG_ZIP_ENTRY_DIGEST_DID_NOT_VERIFY, new Object[]{entryName, expectedDigest.jcaDigestAlgorithm, MANIFEST_ENTRY_NAME, Base64.getEncoder().encodeToString(actualDigest), Base64.getEncoder().encodeToString(expectedDigest.digest)});
            }
        }
        if (firstSignedEntrySigners == null) {
            result.addError(ApkVerifier.Issue.JAR_SIG_NO_SIGNED_ZIP_ENTRIES, new Object[0]);
            return Collections.emptySet();
        }
        return new HashSet<Signer>(firstSignedEntrySigners);
    }

    private static List<String> getSignerNames(List<Signer> signers) {
        if (signers.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> result = new ArrayList<String>(signers.size());
        for (Signer signer : signers) {
            result.add(signer.getName());
        }
        return result;
    }

    private static MessageDigest getMessageDigest(String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }

    private static byte[] digest(String algorithm, byte[] data, int offset, int length) throws NoSuchAlgorithmException {
        MessageDigest md = V1SchemeVerifier.getMessageDigest(algorithm);
        md.update(data, offset, length);
        return md.digest();
    }

    private static byte[] digest(String algorithm, byte[] data) throws NoSuchAlgorithmException {
        return V1SchemeVerifier.getMessageDigest(algorithm).digest(data);
    }

    static {
        UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.put("MD5", "MD5");
        UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.put("SHA", "SHA-1");
        UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.put("SHA1", "SHA-1");
        UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.put("SHA-1", "SHA-1");
        UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.put("SHA-256", "SHA-256");
        UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.put("SHA-384", "SHA-384");
        UPPER_CASE_JCA_DIGEST_ALG_TO_CANONICAL.put("SHA-512", "SHA-512");
        MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST = new HashMap<String, Integer>(5);
        MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST.put("MD5", 0);
        MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST.put("SHA-1", 0);
        MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST.put("SHA-256", 0);
        MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST.put("SHA-384", 9);
        MIN_SDK_VESION_FROM_WHICH_DIGEST_SUPPORTED_IN_MANIFEST.put("SHA-512", 9);
    }

    @Asn1Class(type=Asn1Type.CHOICE)
    public static class ObjectIdentifierChoice {
        @Asn1Field(type=Asn1Type.OBJECT_IDENTIFIER)
        public String value;
    }

    @Asn1Class(type=Asn1Type.CHOICE)
    public static class OctetStringChoice {
        @Asn1Field(type=Asn1Type.OCTET_STRING)
        public byte[] value;
    }

    private static class SignedAttributes {
        private Map<String, List<Asn1OpaqueObject>> mAttrs;

        public SignedAttributes(Collection<Attribute> attrs) throws Pkcs7DecodingException {
            HashMap<String, List<Asn1OpaqueObject>> result = new HashMap<String, List<Asn1OpaqueObject>>(attrs.size());
            for (Attribute attr : attrs) {
                if (result.put(attr.attrType, attr.attrValues) == null) continue;
                throw new Pkcs7DecodingException("Duplicate signed attribute: " + attr.attrType);
            }
            this.mAttrs = result;
        }

        private Asn1OpaqueObject getSingleValue(String attrOid) throws Pkcs7DecodingException {
            List<Asn1OpaqueObject> values2 = this.mAttrs.get(attrOid);
            if (values2 == null || values2.isEmpty()) {
                return null;
            }
            if (values2.size() > 1) {
                throw new Pkcs7DecodingException("Attribute " + attrOid + " has multiple values");
            }
            return values2.get(0);
        }

        public String getSingleObjectIdentifierValue(String attrOid) throws Pkcs7DecodingException {
            Asn1OpaqueObject value = this.getSingleValue(attrOid);
            if (value == null) {
                return null;
            }
            try {
                return Asn1BerParser.parse((ByteBuffer)value.getEncoded(), ObjectIdentifierChoice.class).value;
            }
            catch (Asn1DecodingException e2) {
                throw new Pkcs7DecodingException("Failed to decode OBJECT IDENTIFIER", e2);
            }
        }

        public byte[] getSingleOctetStringValue(String attrOid) throws Pkcs7DecodingException {
            Asn1OpaqueObject value = this.getSingleValue(attrOid);
            if (value == null) {
                return null;
            }
            try {
                return Asn1BerParser.parse((ByteBuffer)value.getEncoded(), OctetStringChoice.class).value;
            }
            catch (Asn1DecodingException e2) {
                throw new Pkcs7DecodingException("Failed to decode OBJECT IDENTIFIER", e2);
            }
        }
    }

    public static class Result {
        public boolean verified;
        public final List<SignerInfo> signers = new ArrayList<SignerInfo>();
        public final List<SignerInfo> ignoredSigners = new ArrayList<SignerInfo>();
        private final List<ApkVerifier.IssueWithParams> mWarnings = new ArrayList<ApkVerifier.IssueWithParams>();
        private final List<ApkVerifier.IssueWithParams> mErrors = new ArrayList<ApkVerifier.IssueWithParams>();

        private boolean containsErrors() {
            if (!this.mErrors.isEmpty()) {
                return true;
            }
            for (SignerInfo signer : this.signers) {
                if (!signer.containsErrors()) continue;
                return true;
            }
            return false;
        }

        private void addError(ApkVerifier.Issue msg, Object ... parameters) {
            this.mErrors.add(new ApkVerifier.IssueWithParams(msg, parameters));
        }

        private void addWarning(ApkVerifier.Issue msg, Object ... parameters) {
            this.mWarnings.add(new ApkVerifier.IssueWithParams(msg, parameters));
        }

        public List<ApkVerifier.IssueWithParams> getErrors() {
            return this.mErrors;
        }

        public List<ApkVerifier.IssueWithParams> getWarnings() {
            return this.mWarnings;
        }

        public static class SignerInfo {
            public final String name;
            public final String signatureFileName;
            public final String signatureBlockFileName;
            public final List<X509Certificate> certChain = new ArrayList<X509Certificate>();
            private final List<ApkVerifier.IssueWithParams> mWarnings = new ArrayList<ApkVerifier.IssueWithParams>();
            private final List<ApkVerifier.IssueWithParams> mErrors = new ArrayList<ApkVerifier.IssueWithParams>();

            private SignerInfo(String name, String signatureBlockFileName, String signatureFileName) {
                this.name = name;
                this.signatureBlockFileName = signatureBlockFileName;
                this.signatureFileName = signatureFileName;
            }

            private boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            private void addError(ApkVerifier.Issue msg, Object ... parameters) {
                this.mErrors.add(new ApkVerifier.IssueWithParams(msg, parameters));
            }

            private void addWarning(ApkVerifier.Issue msg, Object ... parameters) {
                this.mWarnings.add(new ApkVerifier.IssueWithParams(msg, parameters));
            }

            public List<ApkVerifier.IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<ApkVerifier.IssueWithParams> getWarnings() {
                return this.mWarnings;
            }
        }
    }

    private static class NamedDigest {
        private final String jcaDigestAlgorithm;
        private final byte[] digest;

        private NamedDigest(String jcaDigestAlgorithm, byte[] digest) {
            this.jcaDigestAlgorithm = jcaDigestAlgorithm;
            this.digest = digest;
        }
    }

    static class Signer {
        private final String mName;
        private final Result.SignerInfo mResult;
        private final CentralDirectoryRecord mSignatureFileEntry;
        private final CentralDirectoryRecord mSignatureBlockEntry;
        private boolean mIgnored;
        private byte[] mSigFileBytes;
        private Set<String> mSigFileEntryNames;
        private static final String OID_DIGEST_MD5 = "1.2.840.113549.2.5";
        static final String OID_DIGEST_SHA1 = "1.3.14.3.2.26";
        private static final String OID_DIGEST_SHA224 = "2.16.840.1.101.3.4.2.4";
        static final String OID_DIGEST_SHA256 = "2.16.840.1.101.3.4.2.1";
        private static final String OID_DIGEST_SHA384 = "2.16.840.1.101.3.4.2.2";
        private static final String OID_DIGEST_SHA512 = "2.16.840.1.101.3.4.2.3";
        static final String OID_SIG_RSA = "1.2.840.113549.1.1.1";
        private static final String OID_SIG_MD5_WITH_RSA = "1.2.840.113549.1.1.4";
        private static final String OID_SIG_SHA1_WITH_RSA = "1.2.840.113549.1.1.5";
        private static final String OID_SIG_SHA224_WITH_RSA = "1.2.840.113549.1.1.14";
        private static final String OID_SIG_SHA256_WITH_RSA = "1.2.840.113549.1.1.11";
        private static final String OID_SIG_SHA384_WITH_RSA = "1.2.840.113549.1.1.12";
        private static final String OID_SIG_SHA512_WITH_RSA = "1.2.840.113549.1.1.13";
        static final String OID_SIG_DSA = "1.2.840.10040.4.1";
        private static final String OID_SIG_SHA1_WITH_DSA = "1.2.840.10040.4.3";
        private static final String OID_SIG_SHA224_WITH_DSA = "2.16.840.1.101.3.4.3.1";
        static final String OID_SIG_SHA256_WITH_DSA = "2.16.840.1.101.3.4.3.2";
        static final String OID_SIG_EC_PUBLIC_KEY = "1.2.840.10045.2.1";
        private static final String OID_SIG_SHA1_WITH_ECDSA = "1.2.840.10045.4.1";
        private static final String OID_SIG_SHA224_WITH_ECDSA = "1.2.840.10045.4.3.1";
        private static final String OID_SIG_SHA256_WITH_ECDSA = "1.2.840.10045.4.3.2";
        private static final String OID_SIG_SHA384_WITH_ECDSA = "1.2.840.10045.4.3.3";
        private static final String OID_SIG_SHA512_WITH_ECDSA = "1.2.840.10045.4.3.4";
        private static final Map<String, List<InclusiveIntRange>> SUPPORTED_SIG_ALG_OIDS = new HashMap<String, List<InclusiveIntRange>>();
        private static final Map<String, String> OID_TO_JCA_DIGEST_ALG = new HashMap<String, String>();
        private static final Map<String, String> OID_TO_JCA_SIGNATURE_ALG;

        private Signer(String name, CentralDirectoryRecord sigBlockEntry, CentralDirectoryRecord sigFileEntry, Result.SignerInfo result) {
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_RSA, InclusiveIntRange.from(0));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_RSA, InclusiveIntRange.from(0));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.from(0));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(0, 8), InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_RSA, InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_RSA, InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_MD5_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA1_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA224_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA256_WITH_RSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA384_WITH_RSA, InclusiveIntRange.fromTo(21, 21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA512_WITH_RSA, InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_DSA, InclusiveIntRange.from(0));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.from(9));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_DSA, InclusiveIntRange.from(22));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_DSA, InclusiveIntRange.from(22));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA1_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA224_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA256_WITH_DSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_EC_PUBLIC_KEY, InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_MD5, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.from(18));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA1, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA224, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA256, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.from(21));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA384, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA1_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA224_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA256_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA384_WITH_ECDSA, InclusiveIntRange.fromTo(21, 23));
            Signer.addSupportedSigAlg(OID_DIGEST_SHA512, OID_SIG_SHA512_WITH_ECDSA, InclusiveIntRange.from(21));
            this.mName = name;
            this.mResult = result;
            this.mSignatureBlockEntry = sigBlockEntry;
            this.mSignatureFileEntry = sigFileEntry;
        }

        public String getName() {
            return this.mName;
        }

        public String getSignatureFileEntryName() {
            return this.mSignatureFileEntry.getName();
        }

        public String getSignatureBlockEntryName() {
            return this.mSignatureBlockEntry.getName();
        }

        void setIgnored() {
            this.mIgnored = true;
        }

        public boolean isIgnored() {
            return this.mIgnored;
        }

        public Set<String> getSigFileEntryNames() {
            return this.mSigFileEntryNames;
        }

        public Result.SignerInfo getResult() {
            return this.mResult;
        }

        public void verifySigBlockAgainstSigFile(DataSource apk, long cdStartOffset, int minSdkVersion, int maxSdkVersion) throws IOException, ApkFormatException, NoSuchAlgorithmException {
            SignedData signedData;
            byte[] sigBlockBytes;
            try {
                sigBlockBytes = LocalFileRecord.getUncompressedData(apk, this.mSignatureBlockEntry, cdStartOffset);
            }
            catch (ZipFormatException e2) {
                throw new ApkFormatException("Malformed ZIP entry: " + this.mSignatureBlockEntry.getName(), e2);
            }
            try {
                this.mSigFileBytes = LocalFileRecord.getUncompressedData(apk, this.mSignatureFileEntry, cdStartOffset);
            }
            catch (ZipFormatException e3) {
                throw new ApkFormatException("Malformed ZIP entry: " + this.mSignatureFileEntry.getName(), e3);
            }
            try {
                ContentInfo contentInfo = Asn1BerParser.parse(ByteBuffer.wrap(sigBlockBytes), ContentInfo.class);
                if (!"1.2.840.113549.1.7.2".equals(contentInfo.contentType)) {
                    throw new Asn1DecodingException("Unsupported ContentInfo.contentType: " + contentInfo.contentType);
                }
                signedData = Asn1BerParser.parse(contentInfo.content.getEncoded(), SignedData.class);
            }
            catch (Asn1DecodingException e4) {
                e4.printStackTrace();
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_PARSE_EXCEPTION, new Object[]{this.mSignatureBlockEntry.getName(), e4});
                return;
            }
            if (signedData.signerInfos.isEmpty()) {
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_NO_SIGNERS, new Object[]{this.mSignatureBlockEntry.getName()});
                return;
            }
            SignerInfo firstVerifiedSignerInfo = null;
            X509Certificate firstVerifiedSignerInfoSigningCertificate = null;
            List<SignerInfo> unverifiedSignerInfosToTry = minSdkVersion < 24 ? Collections.singletonList(signedData.signerInfos.get(0)) : signedData.signerInfos;
            List<X509Certificate> signedDataCertificates = null;
            for (SignerInfo unverifiedSignerInfo : unverifiedSignerInfosToTry) {
                if (signedDataCertificates == null) {
                    try {
                        signedDataCertificates = Signer.parseCertificates(signedData.certificates);
                    }
                    catch (CertificateException e5) {
                        this.mResult.addError(ApkVerifier.Issue.JAR_SIG_PARSE_EXCEPTION, new Object[]{this.mSignatureBlockEntry.getName(), e5});
                        return;
                    }
                }
                try {
                    X509Certificate signingCertificate = this.verifySignerInfoAgainstSigFile(signedData, signedDataCertificates, unverifiedSignerInfo, this.mSigFileBytes, minSdkVersion, maxSdkVersion);
                    if (this.mResult.containsErrors()) {
                        return;
                    }
                    if (signingCertificate == null || firstVerifiedSignerInfo != null) continue;
                    firstVerifiedSignerInfo = unverifiedSignerInfo;
                    firstVerifiedSignerInfoSigningCertificate = signingCertificate;
                }
                catch (Pkcs7DecodingException e6) {
                    this.mResult.addError(ApkVerifier.Issue.JAR_SIG_PARSE_EXCEPTION, new Object[]{this.mSignatureBlockEntry.getName(), e6});
                    return;
                }
                catch (InvalidKeyException | SignatureException e7) {
                    this.mResult.addError(ApkVerifier.Issue.JAR_SIG_VERIFY_EXCEPTION, new Object[]{this.mSignatureBlockEntry.getName(), this.mSignatureFileEntry.getName(), e7});
                    return;
                }
            }
            if (firstVerifiedSignerInfo == null) {
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_DID_NOT_VERIFY, new Object[]{this.mSignatureBlockEntry.getName(), this.mSignatureFileEntry.getName()});
                return;
            }
            List<X509Certificate> signingCertChain = Signer.getCertificateChain(signedDataCertificates, firstVerifiedSignerInfoSigningCertificate);
            this.mResult.certChain.clear();
            this.mResult.certChain.addAll(signingCertChain);
        }

        private X509Certificate verifySignerInfoAgainstSigFile(SignedData signedData, Collection<X509Certificate> signedDataCertificates, SignerInfo signerInfo, byte[] signatureFile, int minSdkVersion, int maxSdkVersion) throws Pkcs7DecodingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
            List<InclusiveIntRange> apiLevelsWhereDigestAndSigAlgorithmSupported;
            String digestAlgorithmOid = signerInfo.digestAlgorithm.algorithm;
            String signatureAlgorithmOid = signerInfo.signatureAlgorithm.algorithm;
            InclusiveIntRange desiredApiLevels = InclusiveIntRange.fromTo(minSdkVersion, maxSdkVersion);
            List<InclusiveIntRange> apiLevelsWhereDigestAlgorithmNotSupported = desiredApiLevels.getValuesNotIn(apiLevelsWhereDigestAndSigAlgorithmSupported = this.getSigAlgSupportedApiLevels(digestAlgorithmOid, signatureAlgorithmOid));
            if (!apiLevelsWhereDigestAlgorithmNotSupported.isEmpty()) {
                String signatureAlgorithmUserFriendly;
                String digestAlgorithmUserFriendly = OidToUserFriendlyNameMapper.getUserFriendlyNameForOid(digestAlgorithmOid);
                if (digestAlgorithmUserFriendly == null) {
                    digestAlgorithmUserFriendly = digestAlgorithmOid;
                }
                if ((signatureAlgorithmUserFriendly = OidToUserFriendlyNameMapper.getUserFriendlyNameForOid(signatureAlgorithmOid)) == null) {
                    signatureAlgorithmUserFriendly = signatureAlgorithmOid;
                }
                StringBuilder apiLevelsUserFriendly = new StringBuilder();
                for (InclusiveIntRange range : apiLevelsWhereDigestAlgorithmNotSupported) {
                    if (apiLevelsUserFriendly.length() > 0) {
                        apiLevelsUserFriendly.append(", ");
                    }
                    if (range.getMin() == range.getMax()) {
                        apiLevelsUserFriendly.append(String.valueOf(range.getMin()));
                        continue;
                    }
                    if (range.getMax() == Integer.MAX_VALUE) {
                        apiLevelsUserFriendly.append(range.getMin() + "+");
                        continue;
                    }
                    apiLevelsUserFriendly.append(range.getMin() + "-" + range.getMax());
                }
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_UNSUPPORTED_SIG_ALG, new Object[]{this.mSignatureBlockEntry.getName(), digestAlgorithmOid, signatureAlgorithmOid, apiLevelsUserFriendly.toString(), digestAlgorithmUserFriendly, signatureAlgorithmUserFriendly});
                return null;
            }
            X509Certificate signingCertificate = Signer.findCertificate(signedDataCertificates, signerInfo.sid);
            if (signingCertificate == null) {
                throw new SignatureException("Signing certificate referenced in SignerInfo not found in SignedData");
            }
            if (signingCertificate.hasUnsupportedCriticalExtension()) {
                throw new SignatureException("Signing certificate has unsupported critical extensions");
            }
            boolean[] keyUsageExtension = signingCertificate.getKeyUsage();
            if (keyUsageExtension != null) {
                boolean nonRepudiation;
                boolean digitalSignature = keyUsageExtension.length >= 1 && keyUsageExtension[0];
                boolean bl = nonRepudiation = keyUsageExtension.length >= 2 && keyUsageExtension[1];
                if (!digitalSignature && !nonRepudiation) {
                    throw new SignatureException("Signing certificate not authorized for use in digital signatures: keyUsage extension missing digitalSignature and nonRepudiation");
                }
            }
            String jcaSignatureAlgorithm = Signer.getJcaSignatureAlgorithm(digestAlgorithmOid, signatureAlgorithmOid);
            Signature s3 = Signature.getInstance(jcaSignatureAlgorithm);
            s3.initVerify(signingCertificate.getPublicKey());
            if (signerInfo.signedAttrs != null) {
                if (minSdkVersion < 19) {
                    throw new SignatureException("APKs with Signed Attributes broken on platforms with API Level < 19");
                }
                try {
                    byte[] expectedSignatureFileDigest;
                    List<Attribute> signedAttributes = Asn1BerParser.parseImplicitSetOf(signerInfo.signedAttrs.getEncoded(), Attribute.class);
                    SignedAttributes signedAttrs = new SignedAttributes(signedAttributes);
                    if (maxSdkVersion >= 24) {
                        String contentType = signedAttrs.getSingleObjectIdentifierValue("1.2.840.113549.1.9.3");
                        if (contentType == null) {
                            throw new SignatureException("No Content Type in signed attributes");
                        }
                        if (!contentType.equals(signedData.encapContentInfo.contentType)) {
                            return null;
                        }
                    }
                    if ((expectedSignatureFileDigest = signedAttrs.getSingleOctetStringValue("1.2.840.113549.1.9.4")) == null) {
                        throw new SignatureException("No content digest in signed attributes");
                    }
                    byte[] actualSignatureFileDigest = MessageDigest.getInstance(Signer.getJcaDigestAlgorithm(digestAlgorithmOid)).digest(signatureFile);
                    if (!Arrays.equals(expectedSignatureFileDigest, actualSignatureFileDigest)) {
                        return null;
                    }
                }
                catch (Asn1DecodingException e2) {
                    throw new SignatureException("Failed to parse signed attributes", e2);
                }
                ByteBuffer signedAttrsOriginalEncoding = signerInfo.signedAttrs.getEncoded();
                s3.update((byte)49);
                signedAttrsOriginalEncoding.position(1);
                s3.update(signedAttrsOriginalEncoding);
            } else {
                s3.update(signatureFile);
            }
            byte[] sigBytes = ByteBufferUtils.toByteArray(signerInfo.signature.slice());
            if (!s3.verify(sigBytes)) {
                return null;
            }
            return signingCertificate;
        }

        private static List<X509Certificate> parseCertificates(List<Asn1OpaqueObject> encodedCertificates) throws CertificateException {
            CertificateFactory certFactory;
            if (encodedCertificates.isEmpty()) {
                return Collections.emptyList();
            }
            try {
                certFactory = CertificateFactory.getInstance("X.509");
            }
            catch (CertificateException e2) {
                throw new RuntimeException("Failed to create X.509 CertificateFactory", e2);
            }
            ArrayList<X509Certificate> result = new ArrayList<X509Certificate>(encodedCertificates.size());
            for (int i2 = 0; i2 < encodedCertificates.size(); ++i2) {
                X509Certificate certificate;
                Asn1OpaqueObject encodedCertificate = encodedCertificates.get(i2);
                byte[] encodedForm = ByteBufferUtils.toByteArray(encodedCertificate.getEncoded());
                try {
                    certificate = (X509Certificate)certFactory.generateCertificate(new ByteArrayInputStream(encodedForm));
                }
                catch (CertificateException e3) {
                    throw new CertificateException("Failed to parse certificate #" + (i2 + 1), e3);
                }
                certificate = new GuaranteedEncodedFormX509Certificate(certificate, encodedForm);
                result.add(certificate);
            }
            return result;
        }

        public static X509Certificate findCertificate(Collection<X509Certificate> certs, SignerIdentifier id) {
            for (X509Certificate cert : certs) {
                if (!Signer.isMatchingCerticicate(cert, id)) continue;
                return cert;
            }
            return null;
        }

        public static List<X509Certificate> getCertificateChain(List<X509Certificate> certs, X509Certificate leaf) {
            ArrayList<X509Certificate> unusedCerts = new ArrayList<X509Certificate>(certs);
            ArrayList<X509Certificate> result = new ArrayList<X509Certificate>(1);
            result.add(leaf);
            unusedCerts.remove(leaf);
            X509Certificate root = leaf;
            while (!root.getSubjectDN().equals(root.getIssuerDN())) {
                Principal targetDn = root.getIssuerDN();
                boolean issuerFound = false;
                for (int i2 = 0; i2 < unusedCerts.size(); ++i2) {
                    X509Certificate unusedCert = (X509Certificate)unusedCerts.get(i2);
                    if (!targetDn.equals(unusedCert.getSubjectDN())) continue;
                    issuerFound = true;
                    unusedCerts.remove(i2);
                    result.add(unusedCert);
                    root = unusedCert;
                    break;
                }
                if (issuerFound) continue;
                break;
            }
            return result;
        }

        private static boolean isMatchingCerticicate(X509Certificate cert, SignerIdentifier id) {
            if (id.issuerAndSerialNumber == null) {
                return false;
            }
            IssuerAndSerialNumber issuerAndSerialNumber = id.issuerAndSerialNumber;
            byte[] encodedIssuer = ByteBufferUtils.toByteArray(issuerAndSerialNumber.issuer.getEncoded());
            X500Principal idIssuer = new X500Principal(encodedIssuer);
            BigInteger idSerialNumber = issuerAndSerialNumber.certificateSerialNumber;
            return idSerialNumber.equals(cert.getSerialNumber()) && idIssuer.equals(cert.getIssuerX500Principal());
        }

        private static void addSupportedSigAlg(String digestAlgorithmOid, String signatureAlgorithmOid, InclusiveIntRange ... supportedApiLevels) {
            SUPPORTED_SIG_ALG_OIDS.put(digestAlgorithmOid + "with" + signatureAlgorithmOid, Arrays.asList(supportedApiLevels));
        }

        private List<InclusiveIntRange> getSigAlgSupportedApiLevels(String digestAlgorithmOid, String signatureAlgorithmOid) {
            List<InclusiveIntRange> result = SUPPORTED_SIG_ALG_OIDS.get(digestAlgorithmOid + "with" + signatureAlgorithmOid);
            return result != null ? result : Collections.emptyList();
        }

        private static String getJcaDigestAlgorithm(String oid) throws SignatureException {
            String result = OID_TO_JCA_DIGEST_ALG.get(oid);
            if (result == null) {
                throw new SignatureException("Unsupported digest algorithm: " + oid);
            }
            return result;
        }

        private static String getJcaSignatureAlgorithm(String digestAlgorithmOid, String signatureAlgorithmOid) throws SignatureException {
            String suffix;
            String result = OID_TO_JCA_SIGNATURE_ALG.get(signatureAlgorithmOid);
            if (result != null) {
                return result;
            }
            if (OID_SIG_RSA.equals(signatureAlgorithmOid)) {
                suffix = "RSA";
            } else if (OID_SIG_DSA.equals(signatureAlgorithmOid)) {
                suffix = "DSA";
            } else if (OID_SIG_EC_PUBLIC_KEY.equals(signatureAlgorithmOid)) {
                suffix = "ECDSA";
            } else {
                throw new SignatureException("Unsupported JCA Signature algorithm . Digest algorithm: " + digestAlgorithmOid + ", signature algorithm: " + signatureAlgorithmOid);
            }
            String jcaDigestAlg = Signer.getJcaDigestAlgorithm(digestAlgorithmOid);
            if (jcaDigestAlg.startsWith("SHA-")) {
                jcaDigestAlg = "SHA" + jcaDigestAlg.substring("SHA-".length());
            }
            return jcaDigestAlg + "with" + suffix;
        }

        public void verifySigFileAgainstManifest(byte[] manifestBytes, ManifestParser.Section manifestMainSection, Map<String, ManifestParser.Section> entryNameToManifestSection, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException {
            ManifestParser sf = new ManifestParser(this.mSigFileBytes);
            ManifestParser.Section sfMainSection = sf.readSection();
            if (sfMainSection.getAttributeValue(Attributes.Name.SIGNATURE_VERSION) == null) {
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_MISSING_VERSION_ATTR_IN_SIG_FILE, new Object[]{this.mSignatureFileEntry.getName()});
                this.setIgnored();
                return;
            }
            if (maxSdkVersion >= 24) {
                this.checkForStrippedApkSignatures(sfMainSection, supportedApkSigSchemeNames, foundApkSigSchemeIds);
                if (this.mResult.containsErrors()) {
                    return;
                }
            }
            boolean createdBySigntool = false;
            String createdBy = sfMainSection.getAttributeValue("Created-By");
            if (createdBy != null) {
                createdBySigntool = createdBy.indexOf("signtool") != -1;
            }
            boolean manifestDigestVerified = this.verifyManifestDigest(sfMainSection, createdBySigntool, manifestBytes, minSdkVersion, maxSdkVersion);
            if (!createdBySigntool) {
                this.verifyManifestMainSectionDigest(sfMainSection, manifestMainSection, manifestBytes, minSdkVersion, maxSdkVersion);
            }
            if (this.mResult.containsErrors()) {
                return;
            }
            List<ManifestParser.Section> sfSections = sf.readAllSections();
            HashSet<String> sfEntryNames = new HashSet<String>(sfSections.size());
            int sfSectionNumber = 0;
            for (ManifestParser.Section sfSection : sfSections) {
                ++sfSectionNumber;
                String entryName = sfSection.getName();
                if (entryName == null) {
                    this.mResult.addError(ApkVerifier.Issue.JAR_SIG_UNNNAMED_SIG_FILE_SECTION, new Object[]{this.mSignatureFileEntry.getName(), sfSectionNumber});
                    this.setIgnored();
                    return;
                }
                if (!sfEntryNames.add(entryName)) {
                    this.mResult.addError(ApkVerifier.Issue.JAR_SIG_DUPLICATE_SIG_FILE_SECTION, new Object[]{this.mSignatureFileEntry.getName(), entryName});
                    this.setIgnored();
                    return;
                }
                if (manifestDigestVerified) continue;
                ManifestParser.Section manifestSection = entryNameToManifestSection.get(entryName);
                if (manifestSection == null) {
                    this.mResult.addError(ApkVerifier.Issue.JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_SIG_FILE, new Object[]{entryName, this.mSignatureFileEntry.getName()});
                    this.setIgnored();
                    continue;
                }
                this.verifyManifestIndividualSectionDigest(sfSection, createdBySigntool, manifestSection, manifestBytes, minSdkVersion, maxSdkVersion);
            }
            this.mSigFileEntryNames = sfEntryNames;
        }

        private boolean verifyManifestDigest(ManifestParser.Section sfMainSection, boolean createdBySigntool, byte[] manifestBytes, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException {
            boolean digestFound;
            Collection expectedDigests = V1SchemeVerifier.getDigestsToVerify(sfMainSection, createdBySigntool ? "-Digest" : "-Digest-Manifest", minSdkVersion, maxSdkVersion);
            boolean bl = digestFound = !expectedDigests.isEmpty();
            if (!digestFound) {
                this.mResult.addWarning(ApkVerifier.Issue.JAR_SIG_NO_MANIFEST_DIGEST_IN_SIG_FILE, new Object[]{this.mSignatureFileEntry.getName()});
                return false;
            }
            boolean verified = true;
            for (NamedDigest expectedDigest : expectedDigests) {
                String jcaDigestAlgorithm = expectedDigest.jcaDigestAlgorithm;
                byte[] actual = V1SchemeVerifier.digest(jcaDigestAlgorithm, manifestBytes);
                byte[] expected = expectedDigest.digest;
                if (Arrays.equals(expected, actual)) continue;
                this.mResult.addWarning(ApkVerifier.Issue.JAR_SIG_ZIP_ENTRY_DIGEST_DID_NOT_VERIFY, new Object[]{V1SchemeVerifier.MANIFEST_ENTRY_NAME, jcaDigestAlgorithm, this.mSignatureFileEntry.getName(), Base64.getEncoder().encodeToString(actual), Base64.getEncoder().encodeToString(expected)});
                verified = false;
            }
            return verified;
        }

        private void verifyManifestMainSectionDigest(ManifestParser.Section sfMainSection, ManifestParser.Section manifestMainSection, byte[] manifestBytes, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException {
            Collection expectedDigests = V1SchemeVerifier.getDigestsToVerify(sfMainSection, "-Digest-Manifest-Main-Attributes", minSdkVersion, maxSdkVersion);
            if (expectedDigests.isEmpty()) {
                return;
            }
            for (NamedDigest expectedDigest : expectedDigests) {
                String jcaDigestAlgorithm = expectedDigest.jcaDigestAlgorithm;
                byte[] actual = V1SchemeVerifier.digest(jcaDigestAlgorithm, manifestBytes, manifestMainSection.getStartOffset(), manifestMainSection.getSizeBytes());
                byte[] expected = expectedDigest.digest;
                if (Arrays.equals(expected, actual)) continue;
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_MANIFEST_MAIN_SECTION_DIGEST_DID_NOT_VERIFY, new Object[]{jcaDigestAlgorithm, this.mSignatureFileEntry.getName(), Base64.getEncoder().encodeToString(actual), Base64.getEncoder().encodeToString(expected)});
            }
        }

        private void verifyManifestIndividualSectionDigest(ManifestParser.Section sfIndividualSection, boolean createdBySigntool, ManifestParser.Section manifestIndividualSection, byte[] manifestBytes, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException {
            int sectionEndIndex;
            String entryName = sfIndividualSection.getName();
            Collection expectedDigests = V1SchemeVerifier.getDigestsToVerify(sfIndividualSection, "-Digest", minSdkVersion, maxSdkVersion);
            if (expectedDigests.isEmpty()) {
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_SIG_FILE, new Object[]{entryName, this.mSignatureFileEntry.getName()});
                return;
            }
            int sectionStartIndex = manifestIndividualSection.getStartOffset();
            int sectionSizeBytes = manifestIndividualSection.getSizeBytes();
            if (createdBySigntool && manifestBytes[(sectionEndIndex = sectionStartIndex + sectionSizeBytes) - 1] == 10 && manifestBytes[sectionEndIndex - 2] == 10) {
                --sectionSizeBytes;
            }
            for (NamedDigest expectedDigest : expectedDigests) {
                String jcaDigestAlgorithm = expectedDigest.jcaDigestAlgorithm;
                byte[] actual = V1SchemeVerifier.digest(jcaDigestAlgorithm, manifestBytes, sectionStartIndex, sectionSizeBytes);
                byte[] expected = expectedDigest.digest;
                if (Arrays.equals(expected, actual)) continue;
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_MANIFEST_SECTION_DIGEST_DID_NOT_VERIFY, new Object[]{entryName, jcaDigestAlgorithm, this.mSignatureFileEntry.getName(), Base64.getEncoder().encodeToString(actual), Base64.getEncoder().encodeToString(expected)});
            }
        }

        private void checkForStrippedApkSignatures(ManifestParser.Section sfMainSection, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds) {
            int id;
            String signedWithApkSchemes = sfMainSection.getAttributeValue("X-Android-APK-Signed");
            if (signedWithApkSchemes == null) {
                if (!foundApkSigSchemeIds.isEmpty()) {
                    this.mResult.addWarning(ApkVerifier.Issue.JAR_SIG_NO_APK_SIG_STRIP_PROTECTION, new Object[]{this.mSignatureFileEntry.getName()});
                }
                return;
            }
            if (supportedApkSigSchemeNames.isEmpty()) {
                return;
            }
            Set<Integer> supportedApkSigSchemeIds = supportedApkSigSchemeNames.keySet();
            HashSet<Integer> supportedExpectedApkSigSchemeIds = new HashSet<Integer>(1);
            StringTokenizer tokenizer = new StringTokenizer(signedWithApkSchemes, ",");
            while (tokenizer.hasMoreTokens()) {
                String idText = tokenizer.nextToken().trim();
                if (idText.isEmpty()) continue;
                try {
                    id = Integer.parseInt(idText);
                }
                catch (Exception ignored) {
                    continue;
                }
                if (supportedApkSigSchemeIds.contains(id)) {
                    supportedExpectedApkSigSchemeIds.add(id);
                    continue;
                }
                this.mResult.addWarning(ApkVerifier.Issue.JAR_SIG_UNKNOWN_APK_SIG_SCHEME_ID, new Object[]{this.mSignatureFileEntry.getName(), id});
            }
            Iterator iterator2 = supportedExpectedApkSigSchemeIds.iterator();
            while (iterator2.hasNext()) {
                id = (Integer)iterator2.next();
                if (foundApkSigSchemeIds.contains(id)) continue;
                String apkSigSchemeName = supportedApkSigSchemeNames.get(id);
                this.mResult.addError(ApkVerifier.Issue.JAR_SIG_MISSING_APK_SIG_REFERENCED, new Object[]{this.mSignatureFileEntry.getName(), id, apkSigSchemeName});
            }
        }

        static {
            OID_TO_JCA_DIGEST_ALG.put(OID_DIGEST_MD5, "MD5");
            OID_TO_JCA_DIGEST_ALG.put(OID_DIGEST_SHA1, "SHA-1");
            OID_TO_JCA_DIGEST_ALG.put(OID_DIGEST_SHA224, "SHA-224");
            OID_TO_JCA_DIGEST_ALG.put(OID_DIGEST_SHA256, "SHA-256");
            OID_TO_JCA_DIGEST_ALG.put(OID_DIGEST_SHA384, "SHA-384");
            OID_TO_JCA_DIGEST_ALG.put(OID_DIGEST_SHA512, "SHA-512");
            OID_TO_JCA_SIGNATURE_ALG = new HashMap<String, String>();
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_MD5_WITH_RSA, "MD5withRSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA1_WITH_RSA, "SHA1withRSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA224_WITH_RSA, "SHA224withRSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA256_WITH_RSA, "SHA256withRSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA384_WITH_RSA, "SHA384withRSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA512_WITH_RSA, "SHA512withRSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA1_WITH_DSA, "SHA1withDSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA224_WITH_DSA, "SHA224withDSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA256_WITH_DSA, "SHA256withDSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA1_WITH_ECDSA, "SHA1withECDSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA224_WITH_ECDSA, "SHA224withECDSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA256_WITH_ECDSA, "SHA256withECDSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA384_WITH_ECDSA, "SHA384withECDSA");
            OID_TO_JCA_SIGNATURE_ALG.put(OID_SIG_SHA512_WITH_ECDSA, "SHA512withECDSA");
        }

        private static class OidToUserFriendlyNameMapper {
            private static final Map<String, String> OID_TO_USER_FRIENDLY_NAME = new HashMap<String, String>();

            private OidToUserFriendlyNameMapper() {
            }

            public static String getUserFriendlyNameForOid(String oid) {
                return OID_TO_USER_FRIENDLY_NAME.get(oid);
            }

            static {
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_DIGEST_MD5, "MD5");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_DIGEST_SHA1, "SHA-1");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_DIGEST_SHA224, "SHA-224");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_DIGEST_SHA256, "SHA-256");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_DIGEST_SHA384, "SHA-384");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_DIGEST_SHA512, "SHA-512");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_RSA, "RSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_MD5_WITH_RSA, "MD5 with RSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA1_WITH_RSA, "SHA-1 with RSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA224_WITH_RSA, "SHA-224 with RSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA256_WITH_RSA, "SHA-256 with RSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA384_WITH_RSA, "SHA-384 with RSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA512_WITH_RSA, "SHA-512 with RSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_DSA, "DSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA1_WITH_DSA, "SHA-1 with DSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA224_WITH_DSA, "SHA-224 with DSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA256_WITH_DSA, "SHA-256 with DSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_EC_PUBLIC_KEY, "ECDSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA1_WITH_ECDSA, "SHA-1 with ECDSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA224_WITH_ECDSA, "SHA-224 with ECDSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA256_WITH_ECDSA, "SHA-256 with ECDSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA384_WITH_ECDSA, "SHA-384 with ECDSA");
                OID_TO_USER_FRIENDLY_NAME.put(Signer.OID_SIG_SHA512_WITH_ECDSA, "SHA-512 with ECDSA");
            }
        }
    }

    private static class Signers {
        private Signers() {
        }

        private static void verify(DataSource apk, long cdStartOffset, List<CentralDirectoryRecord> cdRecords, Set<String> cdEntryNames, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds, int minSdkVersion, int maxSdkVersion, Result result) throws ApkFormatException, IOException, NoSuchAlgorithmException {
            byte[] manifestBytes;
            CentralDirectoryRecord manifestEntry = null;
            HashMap<String, CentralDirectoryRecord> sigFileEntries = new HashMap<String, CentralDirectoryRecord>(1);
            ArrayList<CentralDirectoryRecord> sigBlockEntries = new ArrayList<CentralDirectoryRecord>(1);
            for (CentralDirectoryRecord cdRecord : cdRecords) {
                String entryName = cdRecord.getName();
                if (!entryName.startsWith("META-INF/")) continue;
                if (manifestEntry == null && V1SchemeVerifier.MANIFEST_ENTRY_NAME.equals(entryName)) {
                    manifestEntry = cdRecord;
                    continue;
                }
                if (entryName.endsWith(".SF")) {
                    sigFileEntries.put(entryName, cdRecord);
                    continue;
                }
                if (!entryName.endsWith(".RSA") && !entryName.endsWith(".DSA") && !entryName.endsWith(".EC")) continue;
                sigBlockEntries.add(cdRecord);
            }
            if (manifestEntry == null) {
                result.addError(ApkVerifier.Issue.JAR_SIG_NO_MANIFEST, new Object[0]);
                return;
            }
            try {
                manifestBytes = LocalFileRecord.getUncompressedData(apk, manifestEntry, cdStartOffset);
            }
            catch (ZipFormatException e2) {
                throw new ApkFormatException("Malformed ZIP entry: " + manifestEntry.getName(), e2);
            }
            HashMap<String, ManifestParser.Section> entryNameToManifestSection = null;
            ManifestParser manifest = new ManifestParser(manifestBytes);
            ManifestParser.Section manifestMainSection = manifest.readSection();
            List<ManifestParser.Section> manifestIndividualSections = manifest.readAllSections();
            entryNameToManifestSection = new HashMap<String, ManifestParser.Section>(manifestIndividualSections.size());
            int manifestSectionNumber = 0;
            for (ManifestParser.Section section : manifestIndividualSections) {
                ++manifestSectionNumber;
                String string = section.getName();
                if (string == null) {
                    result.addError(ApkVerifier.Issue.JAR_SIG_UNNNAMED_MANIFEST_SECTION, new Object[]{manifestSectionNumber});
                    continue;
                }
                if (entryNameToManifestSection.put(string, section) != null) {
                    result.addError(ApkVerifier.Issue.JAR_SIG_DUPLICATE_MANIFEST_SECTION, new Object[]{string});
                    continue;
                }
                if (cdEntryNames.contains(string)) continue;
                result.addError(ApkVerifier.Issue.JAR_SIG_MISSING_ZIP_ENTRY_REFERENCED_IN_MANIFEST, new Object[]{string});
            }
            if (result.containsErrors()) {
                return;
            }
            ArrayList<Signer> signers = new ArrayList<Signer>(sigBlockEntries.size());
            for (CentralDirectoryRecord centralDirectoryRecord : sigBlockEntries) {
                String sigBlockEntryName = centralDirectoryRecord.getName();
                int extensionDelimiterIndex = sigBlockEntryName.lastIndexOf(46);
                if (extensionDelimiterIndex == -1) {
                    throw new RuntimeException("Signature block file name does not contain extension: " + sigBlockEntryName);
                }
                String sigFileEntryName = sigBlockEntryName.substring(0, extensionDelimiterIndex) + ".SF";
                CentralDirectoryRecord sigFileEntry = (CentralDirectoryRecord)sigFileEntries.get(sigFileEntryName);
                if (sigFileEntry == null) {
                    result.addWarning(ApkVerifier.Issue.JAR_SIG_MISSING_FILE, new Object[]{sigBlockEntryName, sigFileEntryName});
                    continue;
                }
                String signerName = sigBlockEntryName.substring("META-INF/".length());
                Result.SignerInfo signerInfo = new Result.SignerInfo(signerName, sigBlockEntryName, sigFileEntry.getName());
                Signer signer = new Signer(signerName, centralDirectoryRecord, sigFileEntry, signerInfo);
                signers.add(signer);
            }
            if (signers.isEmpty()) {
                result.addError(ApkVerifier.Issue.JAR_SIG_NO_SIGNATURES, new Object[0]);
                return;
            }
            for (Signer signer : signers) {
                signer.verifySigBlockAgainstSigFile(apk, cdStartOffset, minSdkVersion, maxSdkVersion);
                if (!signer.getResult().containsErrors()) continue;
                result.signers.add(signer.getResult());
            }
            if (result.containsErrors()) {
                return;
            }
            ArrayList<Signer> arrayList = new ArrayList<Signer>(signers.size());
            for (Signer signer : signers) {
                signer.verifySigFileAgainstManifest(manifestBytes, manifestMainSection, entryNameToManifestSection, supportedApkSigSchemeNames, foundApkSigSchemeIds, minSdkVersion, maxSdkVersion);
                if (signer.isIgnored()) {
                    result.ignoredSigners.add(signer.getResult());
                    continue;
                }
                if (signer.getResult().containsErrors()) {
                    result.signers.add(signer.getResult());
                    continue;
                }
                arrayList.add(signer);
            }
            if (result.containsErrors()) {
                return;
            }
            signers = arrayList;
            if (signers.isEmpty()) {
                result.addError(ApkVerifier.Issue.JAR_SIG_NO_SIGNATURES, new Object[0]);
                return;
            }
            Set set = V1SchemeVerifier.verifyJarEntriesAgainstManifestAndSigners(apk, cdStartOffset, cdRecords, entryNameToManifestSection, signers, minSdkVersion, maxSdkVersion, result);
            if (result.containsErrors()) {
                return;
            }
            HashSet<String> signatureEntryNames = new HashSet<String>(1 + result.signers.size() * 2);
            signatureEntryNames.add(manifestEntry.getName());
            for (Signer signer : set) {
                signatureEntryNames.add(signer.getSignatureBlockEntryName());
                signatureEntryNames.add(signer.getSignatureFileEntryName());
            }
            for (CentralDirectoryRecord cdRecord : cdRecords) {
                String entryName = cdRecord.getName();
                if (!entryName.startsWith("META-INF/") || entryName.endsWith("/") || signatureEntryNames.contains(entryName)) continue;
                result.addWarning(ApkVerifier.Issue.JAR_SIG_UNPROTECTED_ZIP_ENTRY, new Object[]{entryName});
            }
            for (Signer signer : signers) {
                if (set.contains(signer)) {
                    result.signers.add(signer.getResult());
                    continue;
                }
                result.ignoredSigners.add(signer.getResult());
            }
            result.verified = true;
        }
    }
}


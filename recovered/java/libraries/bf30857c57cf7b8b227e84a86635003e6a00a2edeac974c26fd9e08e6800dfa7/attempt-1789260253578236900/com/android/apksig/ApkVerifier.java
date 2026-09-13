/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig;

import com.android.apksig.ApkSigner;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.android.apksig.internal.apk.v1.V1SchemeVerifier;
import com.android.apksig.internal.apk.v2.V2SchemeVerifier;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import com.android.apksig.zip.ZipFormatException;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApkVerifier {
    private static final int APK_SIGNATURE_SCHEME_V2_ID = 2;
    private static final Map<Integer, String> SUPPORTED_APK_SIG_SCHEME_NAMES = Collections.singletonMap(2, "APK Signature Scheme v2");
    private final File mApkFile;
    private final DataSource mApkDataSource;
    private final Integer mMinSdkVersion;
    private final int mMaxSdkVersion;
    private static final int TARGET_SANDBOX_VERSION_ATTR_ID = 16844108;

    private ApkVerifier(File apkFile, DataSource apkDataSource, Integer minSdkVersion, int maxSdkVersion) {
        this.mApkFile = apkFile;
        this.mApkDataSource = apkDataSource;
        this.mMinSdkVersion = minSdkVersion;
        this.mMaxSdkVersion = maxSdkVersion;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Result verify() throws IOException, ApkFormatException, NoSuchAlgorithmException, IllegalStateException {
        try (Closeable in = null;){
            DataSource apk;
            if (this.mApkDataSource != null) {
                apk = this.mApkDataSource;
            } else if (this.mApkFile != null) {
                RandomAccessFile f2 = new RandomAccessFile(this.mApkFile, "r");
                in = f2;
                apk = DataSources.asDataSource(f2, 0L, f2.length());
            } else {
                throw new IllegalStateException("APK not provided");
            }
            Result result = this.verify(apk);
            return result;
        }
    }

    private Result verify(DataSource apk) throws IOException, ApkFormatException, NoSuchAlgorithmException {
        Set<Integer> foundApkSigSchemeIds;
        int minSdkVersion;
        ApkUtils.ZipSections zipSections;
        if (this.mMinSdkVersion != null) {
            if (this.mMinSdkVersion < 0) {
                throw new IllegalArgumentException("minSdkVersion must not be negative: " + this.mMinSdkVersion);
            }
            if (this.mMinSdkVersion != null && this.mMinSdkVersion > this.mMaxSdkVersion) {
                throw new IllegalArgumentException("minSdkVersion (" + this.mMinSdkVersion + ") > maxSdkVersion (" + this.mMaxSdkVersion + ")");
            }
        }
        int maxSdkVersion = this.mMaxSdkVersion;
        try {
            zipSections = ApkUtils.findZipSections(apk);
        }
        catch (ZipFormatException e2) {
            throw new ApkFormatException("Malformed APK: not a ZIP archive", e2);
        }
        ByteBuffer androidManifest = null;
        if (this.mMinSdkVersion != null) {
            minSdkVersion = this.mMinSdkVersion;
        } else {
            if (androidManifest == null) {
                androidManifest = ApkVerifier.getAndroidManifestFromApk(apk, zipSections);
            }
            if ((minSdkVersion = ApkUtils.getMinSdkVersionFromBinaryAndroidManifest(androidManifest.slice())) > this.mMaxSdkVersion) {
                throw new IllegalArgumentException("minSdkVersion from APK (" + minSdkVersion + ") > maxSdkVersion (" + this.mMaxSdkVersion + ")");
            }
        }
        Result result = new Result();
        if (maxSdkVersion >= 24) {
            foundApkSigSchemeIds = new HashSet(1);
            try {
                V2SchemeVerifier.Result v2Result = V2SchemeVerifier.verify(apk, zipSections, Math.max(minSdkVersion, 24), maxSdkVersion);
                foundApkSigSchemeIds.add(2);
                result.mergeFrom(v2Result);
            }
            catch (V2SchemeVerifier.SignatureNotFoundException v2Result) {
                // empty catch block
            }
            if (result.containsErrors()) {
                return result;
            }
        } else {
            foundApkSigSchemeIds = Collections.emptySet();
        }
        if (maxSdkVersion >= 26) {
            int targetSandboxVersion;
            if (androidManifest == null) {
                androidManifest = ApkVerifier.getAndroidManifestFromApk(apk, zipSections);
            }
            if ((targetSandboxVersion = ApkVerifier.getTargetSandboxVersionFromBinaryAndroidManifest(androidManifest.slice())) > 1 && foundApkSigSchemeIds.isEmpty()) {
                result.addError(Issue.NO_SIG_FOR_TARGET_SANDBOX_VERSION, targetSandboxVersion);
            }
        }
        if (minSdkVersion < 24 || foundApkSigSchemeIds.isEmpty()) {
            V1SchemeVerifier.Result v1Result = V1SchemeVerifier.verify(apk, zipSections, SUPPORTED_APK_SIG_SCHEME_NAMES, foundApkSigSchemeIds, minSdkVersion, maxSdkVersion);
            result.mergeFrom(v1Result);
        }
        if (result.containsErrors()) {
            return result;
        }
        if (result.isVerifiedUsingV1Scheme() && result.isVerifiedUsingV2Scheme()) {
            ArrayList<Result.V1SchemeSignerInfo> v1Signers = new ArrayList<Result.V1SchemeSignerInfo>(result.getV1SchemeSigners());
            ArrayList<Result.V2SchemeSignerInfo> arrayList = new ArrayList<Result.V2SchemeSignerInfo>(result.getV2SchemeSigners());
            ArrayList<ByteArray> v1SignerCerts = new ArrayList<ByteArray>();
            ArrayList<ByteArray> v2SignerCerts = new ArrayList<ByteArray>();
            for (Result.V1SchemeSignerInfo v1SchemeSignerInfo : v1Signers) {
                try {
                    v1SignerCerts.add(new ByteArray(v1SchemeSignerInfo.getCertificate().getEncoded()));
                }
                catch (CertificateEncodingException e3) {
                    throw new RuntimeException("Failed to encode JAR signer " + v1SchemeSignerInfo.getName() + " certs", e3);
                }
            }
            for (Result.V2SchemeSignerInfo v2SchemeSignerInfo : arrayList) {
                try {
                    v2SignerCerts.add(new ByteArray(v2SchemeSignerInfo.getCertificate().getEncoded()));
                }
                catch (CertificateEncodingException e4) {
                    throw new RuntimeException("Failed to encode APK Signature Scheme v2 signer (index: " + v2SchemeSignerInfo.getIndex() + ") certs", e4);
                }
            }
            for (int i2 = 0; i2 < v1SignerCerts.size(); ++i2) {
                ByteArray byteArray = (ByteArray)v1SignerCerts.get(i2);
                if (v2SignerCerts.contains(byteArray)) continue;
                Result.V1SchemeSignerInfo v1Signer = v1Signers.get(i2);
                v1Signer.addError(Issue.V2_SIG_MISSING, new Object[0]);
                break;
            }
            for (int i2 = 0; i2 < v2SignerCerts.size(); ++i2) {
                ByteArray byteArray = (ByteArray)v2SignerCerts.get(i2);
                if (v1SignerCerts.contains(byteArray)) continue;
                Result.V2SchemeSignerInfo v2Signer = arrayList.get(i2);
                v2Signer.addError(Issue.JAR_SIG_MISSING, new Object[0]);
                break;
            }
        }
        if (result.containsErrors()) {
            return result;
        }
        result.setVerified();
        if (result.isVerifiedUsingV2Scheme()) {
            for (Result.V2SchemeSignerInfo v2SchemeSignerInfo : result.getV2SchemeSigners()) {
                result.addSignerCertificate(v2SchemeSignerInfo.getCertificate());
            }
        } else if (result.isVerifiedUsingV1Scheme()) {
            for (Result.V1SchemeSignerInfo v1SchemeSignerInfo : result.getV1SchemeSigners()) {
                result.addSignerCertificate(v1SchemeSignerInfo.getCertificate());
            }
        } else {
            throw new RuntimeException("APK considered verified, but has not verified using either v1 or v2 schemes");
        }
        return result;
    }

    private static ByteBuffer getAndroidManifestFromApk(DataSource apk, ApkUtils.ZipSections zipSections) throws IOException, ApkFormatException {
        List<CentralDirectoryRecord> cdRecords = V1SchemeVerifier.parseZipCentralDirectory(apk, zipSections);
        try {
            return ApkSigner.getAndroidManifestFromApk(cdRecords, apk.slice(0L, zipSections.getZipCentralDirectoryOffset()));
        }
        catch (ZipFormatException e2) {
            throw new ApkFormatException("Failed to read AndroidManifest.xml", e2);
        }
    }

    private static int getTargetSandboxVersionFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws ApkFormatException {
        try {
            AndroidBinXmlParser parser = new AndroidBinXmlParser(androidManifestContents);
            int eventType = parser.getEventType();
            while (eventType != 2) {
                if (eventType == 3 && parser.getDepth() == 1 && "manifest".equals(parser.getName()) && parser.getNamespace().isEmpty()) {
                    int result = 1;
                    block6: for (int i2 = 0; i2 < parser.getAttributeCount(); ++i2) {
                        if (parser.getAttributeNameResourceId(i2) != 16844108) continue;
                        int valueType = parser.getAttributeValueType(i2);
                        switch (valueType) {
                            case 2: {
                                result = parser.getAttributeIntValue(i2);
                                break block6;
                            }
                            default: {
                                throw new ApkFormatException("Failed to determine APK's target sandbox version: unsupported value type of AndroidManifest.xml android:targetSandboxVersion. Only integer values supported.");
                            }
                        }
                    }
                    return result;
                }
                eventType = parser.next();
            }
            throw new ApkFormatException("Failed to determine APK's target sandbox version : no manifest element in AndroidManifest.xml");
        }
        catch (AndroidBinXmlParser.XmlParserException e2) {
            throw new ApkFormatException("Failed to determine APK's target sandbox version: malformed AndroidManifest.xml", e2);
        }
    }

    public static class Builder {
        private final File mApkFile;
        private final DataSource mApkDataSource;
        private Integer mMinSdkVersion;
        private int mMaxSdkVersion = Integer.MAX_VALUE;

        public Builder(File apk) {
            if (apk == null) {
                throw new NullPointerException("apk == null");
            }
            this.mApkFile = apk;
            this.mApkDataSource = null;
        }

        public Builder(DataSource apk) {
            if (apk == null) {
                throw new NullPointerException("apk == null");
            }
            this.mApkDataSource = apk;
            this.mApkFile = null;
        }

        public Builder setMinCheckedPlatformVersion(int minSdkVersion) {
            this.mMinSdkVersion = minSdkVersion;
            return this;
        }

        public Builder setMaxCheckedPlatformVersion(int maxSdkVersion) {
            this.mMaxSdkVersion = maxSdkVersion;
            return this;
        }

        public ApkVerifier build() {
            return new ApkVerifier(this.mApkFile, this.mApkDataSource, this.mMinSdkVersion, this.mMaxSdkVersion);
        }
    }

    private static class ByteArray {
        private final byte[] mArray;
        private final int mHashCode;

        private ByteArray(byte[] arr) {
            this.mArray = arr;
            this.mHashCode = Arrays.hashCode(this.mArray);
        }

        public int hashCode() {
            return this.mHashCode;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            ByteArray other = (ByteArray)obj;
            if (this.hashCode() != other.hashCode()) {
                return false;
            }
            return Arrays.equals(this.mArray, other.mArray);
        }
    }

    public static class IssueWithParams {
        private final Issue mIssue;
        private final Object[] mParams;

        public IssueWithParams(Issue issue, Object[] params) {
            this.mIssue = issue;
            this.mParams = params;
        }

        public Issue getIssue() {
            return this.mIssue;
        }

        public Object[] getParams() {
            return (Object[])this.mParams.clone();
        }

        public String toString() {
            return String.format(this.mIssue.getFormat(), this.mParams);
        }
    }

    public static enum Issue {
        JAR_SIG_NO_SIGNATURES("No JAR signatures"),
        JAR_SIG_NO_SIGNED_ZIP_ENTRIES("No JAR entries covered by JAR signatures"),
        JAR_SIG_DUPLICATE_ZIP_ENTRY("Duplicate entry: %1$s"),
        JAR_SIG_DUPLICATE_MANIFEST_SECTION("Duplicate section in META-INF/MANIFEST.MF: %1$s"),
        JAR_SIG_UNNNAMED_MANIFEST_SECTION("Malformed META-INF/MANIFEST.MF: invidual section #%1$d does not have a name"),
        JAR_SIG_UNNNAMED_SIG_FILE_SECTION("Malformed %1$s: invidual section #%2$d does not have a name"),
        JAR_SIG_NO_MANIFEST("Missing META-INF/MANIFEST.MF"),
        JAR_SIG_MISSING_ZIP_ENTRY_REFERENCED_IN_MANIFEST("%1$s entry referenced by META-INF/MANIFEST.MF not found in the APK"),
        JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_MANIFEST("No digest for %1$s in META-INF/MANIFEST.MF"),
        JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_SIG_FILE("No digest for %1$s in %2$s"),
        JAR_SIG_ZIP_ENTRY_NOT_SIGNED("%1$s entry not signed"),
        JAR_SIG_ZIP_ENTRY_SIGNERS_MISMATCH("Entries %1$s and %3$s are signed with different sets of signers : <%2$s> vs <%4$s>"),
        JAR_SIG_ZIP_ENTRY_DIGEST_DID_NOT_VERIFY("%2$s digest of %1$s does not match the digest specified in %3$s. Expected: <%5$s>, actual: <%4$s>"),
        JAR_SIG_MANIFEST_MAIN_SECTION_DIGEST_DID_NOT_VERIFY("%1$s digest of META-INF/MANIFEST.MF main section does not match the digest specified in %2$s. Expected: <%4$s>, actual: <%3$s>"),
        JAR_SIG_MANIFEST_SECTION_DIGEST_DID_NOT_VERIFY("%2$s digest of META-INF/MANIFEST.MF section for %1$s does not match the digest specified in %3$s. Expected: <%5$s>, actual: <%4$s>"),
        JAR_SIG_NO_MANIFEST_DIGEST_IN_SIG_FILE("%1$s does not specify digest of META-INF/MANIFEST.MF. This slows down verification."),
        JAR_SIG_NO_APK_SIG_STRIP_PROTECTION("APK is signed using APK Signature Scheme v2 but these signatures may be stripped without being detected because %1$s does not contain anti-stripping protections."),
        JAR_SIG_MISSING_FILE("Partial JAR signature. Found: %1$s, missing: %2$s"),
        JAR_SIG_VERIFY_EXCEPTION("Failed to verify JAR signature %1$s against %2$s: %3$s"),
        JAR_SIG_UNSUPPORTED_SIG_ALG("JAR signature %1$s uses digest algorithm %5$s and signature algorithm %6$s which is not supported on API Level(s) %4$s for which this APK is being verified"),
        JAR_SIG_PARSE_EXCEPTION("Failed to parse JAR signature %1$s: %2$s"),
        JAR_SIG_MALFORMED_CERTIFICATE("Malformed certificate in JAR signature %1$s: %2$s"),
        JAR_SIG_DID_NOT_VERIFY("JAR signature %1$s did not verify against %2$s"),
        JAR_SIG_NO_SIGNERS("JAR signature %1$s contains no signers"),
        JAR_SIG_DUPLICATE_SIG_FILE_SECTION("Duplicate section in %1$s: %2$s"),
        JAR_SIG_MISSING_VERSION_ATTR_IN_SIG_FILE("Malformed %1$s: missing Signature-Version attribute"),
        JAR_SIG_UNKNOWN_APK_SIG_SCHEME_ID("JAR signature %1$s references unknown APK signature scheme ID: %2$d"),
        JAR_SIG_MISSING_APK_SIG_REFERENCED("JAR signature %1$s indicates the APK is signed using %3$s but no such signature was found. Signature stripped?"),
        JAR_SIG_UNPROTECTED_ZIP_ENTRY("%1$s not protected by signature. Unauthorized modifications to this JAR entry will not be detected. Delete or move the entry outside of META-INF/."),
        JAR_SIG_MISSING("No JAR signature from this signer"),
        NO_SIG_FOR_TARGET_SANDBOX_VERSION("Missing APK Signature Scheme v2 signature required for target sandbox version %1$d"),
        V2_SIG_MISSING("No APK Signature Scheme v2 signature from this signer"),
        V2_SIG_MALFORMED_SIGNERS("Malformed list of signers"),
        V2_SIG_MALFORMED_SIGNER("Malformed signer block"),
        V2_SIG_MALFORMED_PUBLIC_KEY("Malformed public key: %1$s"),
        V2_SIG_MALFORMED_CERTIFICATE("Malformed certificate #%2$d: %3$s"),
        V2_SIG_MALFORMED_SIGNATURE("Malformed APK Signature Scheme v2 signature record #%1$d"),
        V2_SIG_MALFORMED_DIGEST("Malformed APK Signature Scheme v2 digest record #%1$d"),
        V2_SIG_MALFORMED_ADDITIONAL_ATTRIBUTE("Malformed additional attribute #%1$d"),
        V2_SIG_NO_SIGNERS("No signers in APK Signature Scheme v2 signature"),
        V2_SIG_UNKNOWN_SIG_ALGORITHM("Unknown signature algorithm: %1$#x"),
        V2_SIG_UNKNOWN_ADDITIONAL_ATTRIBUTE("Unknown additional attribute: ID %1$#x"),
        V2_SIG_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        V2_SIG_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        V2_SIG_NO_SIGNATURES("No signatures"),
        V2_SIG_NO_SUPPORTED_SIGNATURES("No supported signatures"),
        V2_SIG_NO_CERTIFICATES("No certificates"),
        V2_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD("Public key mismatch between certificate and signature record: <%1$s> vs <%2$s>"),
        V2_SIG_SIG_ALG_MISMATCH_BETWEEN_SIGNATURES_AND_DIGESTS_RECORDS("Signature algorithms mismatch between signatures and digests records: %1$s vs %2$s"),
        V2_SIG_APK_DIGEST_DID_NOT_VERIFY("APK integrity check failed. %1$s digest mismatch. Expected: <%2$s>, actual: <%3$s>"),
        APK_SIG_BLOCK_UNKNOWN_ENTRY_ID("APK Signing Block contains unknown entry: ID %1$#x");

        private final String mFormat;

        private Issue(String format) {
            this.mFormat = format;
        }

        private String getFormat() {
            return this.mFormat;
        }
    }

    public static class Result {
        private final List<IssueWithParams> mErrors = new ArrayList<IssueWithParams>();
        private final List<IssueWithParams> mWarnings = new ArrayList<IssueWithParams>();
        private final List<X509Certificate> mSignerCerts = new ArrayList<X509Certificate>();
        private final List<V1SchemeSignerInfo> mV1SchemeSigners = new ArrayList<V1SchemeSignerInfo>();
        private final List<V1SchemeSignerInfo> mV1SchemeIgnoredSigners = new ArrayList<V1SchemeSignerInfo>();
        private final List<V2SchemeSignerInfo> mV2SchemeSigners = new ArrayList<V2SchemeSignerInfo>();
        private boolean mVerified;
        private boolean mVerifiedUsingV1Scheme;
        private boolean mVerifiedUsingV2Scheme;

        public boolean isVerified() {
            return this.mVerified;
        }

        private void setVerified() {
            this.mVerified = true;
        }

        public boolean isVerifiedUsingV1Scheme() {
            return this.mVerifiedUsingV1Scheme;
        }

        public boolean isVerifiedUsingV2Scheme() {
            return this.mVerifiedUsingV2Scheme;
        }

        public List<X509Certificate> getSignerCertificates() {
            return this.mSignerCerts;
        }

        private void addSignerCertificate(X509Certificate cert) {
            this.mSignerCerts.add(cert);
        }

        public List<V1SchemeSignerInfo> getV1SchemeSigners() {
            return this.mV1SchemeSigners;
        }

        public List<V1SchemeSignerInfo> getV1SchemeIgnoredSigners() {
            return this.mV1SchemeIgnoredSigners;
        }

        public List<V2SchemeSignerInfo> getV2SchemeSigners() {
            return this.mV2SchemeSigners;
        }

        void addError(Issue msg, Object ... parameters) {
            this.mErrors.add(new IssueWithParams(msg, parameters));
        }

        public List<IssueWithParams> getErrors() {
            return this.mErrors;
        }

        public List<IssueWithParams> getWarnings() {
            return this.mWarnings;
        }

        private void mergeFrom(V1SchemeVerifier.Result source) {
            this.mVerifiedUsingV1Scheme = source.verified;
            this.mErrors.addAll(source.getErrors());
            this.mWarnings.addAll(source.getWarnings());
            for (V1SchemeVerifier.Result.SignerInfo signer : source.signers) {
                this.mV1SchemeSigners.add(new V1SchemeSignerInfo(signer));
            }
            for (V1SchemeVerifier.Result.SignerInfo signer : source.ignoredSigners) {
                this.mV1SchemeIgnoredSigners.add(new V1SchemeSignerInfo(signer));
            }
        }

        private void mergeFrom(V2SchemeVerifier.Result source) {
            this.mVerifiedUsingV2Scheme = source.verified;
            this.mErrors.addAll(source.getErrors());
            this.mWarnings.addAll(source.getWarnings());
            for (V2SchemeVerifier.Result.SignerInfo signer : source.signers) {
                this.mV2SchemeSigners.add(new V2SchemeSignerInfo(signer));
            }
        }

        public boolean containsErrors() {
            if (!this.mErrors.isEmpty()) {
                return true;
            }
            if (!this.mV1SchemeSigners.isEmpty()) {
                for (V1SchemeSignerInfo v1SchemeSignerInfo : this.mV1SchemeSigners) {
                    if (!v1SchemeSignerInfo.containsErrors()) continue;
                    return true;
                }
            }
            if (!this.mV2SchemeSigners.isEmpty()) {
                for (V2SchemeSignerInfo v2SchemeSignerInfo : this.mV2SchemeSigners) {
                    if (!v2SchemeSignerInfo.containsErrors()) continue;
                    return true;
                }
            }
            return false;
        }

        public static class V2SchemeSignerInfo {
            private final int mIndex;
            private final List<X509Certificate> mCerts;
            private final List<IssueWithParams> mErrors;
            private final List<IssueWithParams> mWarnings;

            private V2SchemeSignerInfo(V2SchemeVerifier.Result.SignerInfo result) {
                this.mIndex = result.index;
                this.mCerts = result.certs;
                this.mErrors = result.getErrors();
                this.mWarnings = result.getWarnings();
            }

            public int getIndex() {
                return this.mIndex;
            }

            public X509Certificate getCertificate() {
                return this.mCerts.isEmpty() ? null : this.mCerts.get(0);
            }

            public List<X509Certificate> getCertificates() {
                return this.mCerts;
            }

            private void addError(Issue msg, Object ... parameters) {
                this.mErrors.add(new IssueWithParams(msg, parameters));
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings;
            }
        }

        public static class V1SchemeSignerInfo {
            private final String mName;
            private final List<X509Certificate> mCertChain;
            private final String mSignatureBlockFileName;
            private final String mSignatureFileName;
            private final List<IssueWithParams> mErrors;
            private final List<IssueWithParams> mWarnings;

            private V1SchemeSignerInfo(V1SchemeVerifier.Result.SignerInfo result) {
                this.mName = result.name;
                this.mCertChain = result.certChain;
                this.mSignatureBlockFileName = result.signatureBlockFileName;
                this.mSignatureFileName = result.signatureFileName;
                this.mErrors = result.getErrors();
                this.mWarnings = result.getWarnings();
            }

            public String getName() {
                return this.mName;
            }

            public String getSignatureBlockFileName() {
                return this.mSignatureBlockFileName;
            }

            public String getSignatureFileName() {
                return this.mSignatureFileName;
            }

            public X509Certificate getCertificate() {
                return this.mCertChain.isEmpty() ? null : this.mCertChain.get(0);
            }

            public List<X509Certificate> getCertificateChain() {
                return this.mCertChain;
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings;
            }

            private void addError(Issue msg, Object ... parameters) {
                this.mErrors.add(new IssueWithParams(msg, parameters));
            }
        }
    }
}


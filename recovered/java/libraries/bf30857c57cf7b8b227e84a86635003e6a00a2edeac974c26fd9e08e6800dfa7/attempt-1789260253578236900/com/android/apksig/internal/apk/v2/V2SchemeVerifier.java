/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.apk.v2;

import com.android.apksig.ApkVerifier;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkSigningBlockNotFoundException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.v2.ContentDigestAlgorithm;
import com.android.apksig.internal.apk.v2.SignatureAlgorithm;
import com.android.apksig.internal.apk.v2.V2SchemeSigner;
import com.android.apksig.internal.util.ByteBufferDataSource;
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class V2SchemeVerifier {
    private static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;
    private static final char[] HEX_DIGITS = "01234567890abcdef".toCharArray();

    private V2SchemeVerifier() {
    }

    public static Result verify(DataSource apk, ApkUtils.ZipSections zipSections, int minSdkVersion, int maxSdkVersion) throws IOException, ApkFormatException, NoSuchAlgorithmException, SignatureNotFoundException {
        Result result = new Result();
        SignatureInfo signatureInfo = V2SchemeVerifier.findSignature(apk, zipSections, result);
        DataSource beforeApkSigningBlock = apk.slice(0L, signatureInfo.apkSigningBlockOffset);
        DataSource centralDir = apk.slice(signatureInfo.centralDirOffset, signatureInfo.eocdOffset - signatureInfo.centralDirOffset);
        ByteBuffer eocd = signatureInfo.eocd;
        V2SchemeVerifier.verify(beforeApkSigningBlock, signatureInfo.signatureBlock, centralDir, eocd, minSdkVersion, maxSdkVersion, result);
        return result;
    }

    private static void verify(DataSource beforeApkSigningBlock, ByteBuffer apkSignatureSchemeV2Block, DataSource centralDir, ByteBuffer eocd, int minSdkVersion, int maxSdkVersion, Result result) throws IOException, NoSuchAlgorithmException {
        HashSet<ContentDigestAlgorithm> contentDigestsToVerify = new HashSet<ContentDigestAlgorithm>(1);
        V2SchemeVerifier.parseSigners(apkSignatureSchemeV2Block, contentDigestsToVerify, minSdkVersion, maxSdkVersion, result);
        if (result.containsErrors()) {
            return;
        }
        V2SchemeVerifier.verifyIntegrity(beforeApkSigningBlock, centralDir, eocd, contentDigestsToVerify, result);
        if (!result.containsErrors()) {
            result.verified = true;
        }
    }

    private static void parseSigners(ByteBuffer apkSignatureSchemeV2Block, Set<ContentDigestAlgorithm> contentDigestsToVerify, int minSdkVersion, int maxSdkVersion, Result result) throws NoSuchAlgorithmException {
        CertificateFactory certFactory;
        ByteBuffer signers;
        try {
            signers = V2SchemeVerifier.getLengthPrefixedSlice(apkSignatureSchemeV2Block);
        }
        catch (ApkFormatException e2) {
            result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_SIGNERS, new Object[0]);
            return;
        }
        if (!signers.hasRemaining()) {
            result.addError(ApkVerifier.Issue.V2_SIG_NO_SIGNERS, new Object[0]);
            return;
        }
        try {
            certFactory = CertificateFactory.getInstance("X.509");
        }
        catch (CertificateException e3) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e3);
        }
        int signerCount = 0;
        while (signers.hasRemaining()) {
            int signerIndex = signerCount++;
            Result.SignerInfo signerInfo = new Result.SignerInfo();
            signerInfo.index = signerIndex;
            result.signers.add(signerInfo);
            try {
                ByteBuffer signer = V2SchemeVerifier.getLengthPrefixedSlice(signers);
                V2SchemeVerifier.parseSigner(signer, certFactory, signerInfo, contentDigestsToVerify, minSdkVersion, maxSdkVersion);
            }
            catch (ApkFormatException | BufferUnderflowException e4) {
                signerInfo.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_SIGNER, new Object[0]);
                return;
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void parseSigner(ByteBuffer signerBlock, CertificateFactory certFactory, Result.SignerInfo result, Set<ContentDigestAlgorithm> contentDigestsToVerify, int minSdkVersion, int maxSdkVersion) throws ApkFormatException, NoSuchAlgorithmException {
        SignatureAlgorithm signatureAlgorithm;
        ByteBuffer signedData = V2SchemeVerifier.getLengthPrefixedSlice(signerBlock);
        byte[] signedDataBytes = new byte[signedData.remaining()];
        signedData.get(signedDataBytes);
        signedData.flip();
        result.signedData = signedDataBytes;
        ByteBuffer signatures = V2SchemeVerifier.getLengthPrefixedSlice(signerBlock);
        byte[] publicKeyBytes = V2SchemeVerifier.readLengthPrefixedByteArray(signerBlock);
        int signatureCount = 0;
        ArrayList<SupportedSignature> supportedSignatures = new ArrayList<SupportedSignature>(1);
        while (signatures.hasRemaining()) {
            ++signatureCount;
            try {
                ByteBuffer signature = V2SchemeVerifier.getLengthPrefixedSlice(signatures);
                int sigAlgorithmId = signature.getInt();
                byte[] sigBytes = V2SchemeVerifier.readLengthPrefixedByteArray(signature);
                result.signatures.add(new Result.SignerInfo.Signature(sigAlgorithmId, sigBytes));
                signatureAlgorithm = SignatureAlgorithm.findById(sigAlgorithmId);
                if (signatureAlgorithm == null) {
                    result.addWarning(ApkVerifier.Issue.V2_SIG_UNKNOWN_SIG_ALGORITHM, sigAlgorithmId);
                    continue;
                }
                supportedSignatures.add(new SupportedSignature(signatureAlgorithm, sigBytes));
            }
            catch (ApkFormatException | BufferUnderflowException e2) {
                result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_SIGNATURE, signatureCount);
                return;
            }
        }
        if (result.signatures.isEmpty()) {
            result.addError(ApkVerifier.Issue.V2_SIG_NO_SIGNATURES, new Object[0]);
            return;
        }
        List<SupportedSignature> signaturesToVerify = null;
        try {
            signaturesToVerify = V2SchemeVerifier.getSignaturesToVerify(supportedSignatures, minSdkVersion, maxSdkVersion);
        }
        catch (NoSupportedSignaturesException e3) {
            result.addError(ApkVerifier.Issue.V2_SIG_NO_SUPPORTED_SIGNATURES, new Object[0]);
            return;
        }
        for (SupportedSignature signature : signaturesToVerify) {
            PublicKey publicKey;
            signatureAlgorithm = signature.algorithm;
            String jcaSignatureAlgorithm = signatureAlgorithm.getJcaSignatureAlgorithmAndParams().getFirst();
            AlgorithmParameterSpec jcaSignatureAlgorithmParams = signatureAlgorithm.getJcaSignatureAlgorithmAndParams().getSecond();
            String keyAlgorithm = signatureAlgorithm.getJcaKeyAlgorithm();
            try {
                publicKey = KeyFactory.getInstance(keyAlgorithm).generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            }
            catch (Exception e4) {
                result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_PUBLIC_KEY, e4);
                return;
            }
            try {
                Signature sig = Signature.getInstance(jcaSignatureAlgorithm);
                sig.initVerify(publicKey);
                if (jcaSignatureAlgorithmParams != null) {
                    sig.setParameter(jcaSignatureAlgorithmParams);
                }
                signedData.position(0);
                sig.update(signedData);
                byte[] sigBytes = signature.signature;
                if (!sig.verify(sigBytes)) {
                    result.addError(ApkVerifier.Issue.V2_SIG_DID_NOT_VERIFY, new Object[]{signatureAlgorithm});
                    return;
                }
                result.verifiedSignatures.put(signatureAlgorithm, sigBytes);
                contentDigestsToVerify.add(signatureAlgorithm.getContentDigestAlgorithm());
            }
            catch (InvalidAlgorithmParameterException | InvalidKeyException | SignatureException e5) {
                result.addError(ApkVerifier.Issue.V2_SIG_VERIFY_EXCEPTION, new Object[]{signatureAlgorithm, e5});
                return;
            }
        }
        signedData.position(0);
        ByteBuffer digests = V2SchemeVerifier.getLengthPrefixedSlice(signedData);
        ByteBuffer certificates = V2SchemeVerifier.getLengthPrefixedSlice(signedData);
        ByteBuffer additionalAttributes = V2SchemeVerifier.getLengthPrefixedSlice(signedData);
        int certificateIndex = -1;
        while (certificates.hasRemaining()) {
            X509Certificate certificate;
            ++certificateIndex;
            byte[] encodedCert = V2SchemeVerifier.readLengthPrefixedByteArray(certificates);
            try {
                certificate = (X509Certificate)certFactory.generateCertificate(new ByteArrayInputStream(encodedCert));
            }
            catch (CertificateException e6) {
                result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_CERTIFICATE, certificateIndex, certificateIndex + 1, e6);
                return;
            }
            certificate = new GuaranteedEncodedFormX509Certificate(certificate, encodedCert);
            result.certs.add(certificate);
        }
        if (result.certs.isEmpty()) {
            result.addError(ApkVerifier.Issue.V2_SIG_NO_CERTIFICATES, new Object[0]);
            return;
        }
        X509Certificate mainCertificate = result.certs.get(0);
        byte[] certificatePublicKeyBytes = mainCertificate.getPublicKey().getEncoded();
        if (!Arrays.equals(publicKeyBytes, certificatePublicKeyBytes)) {
            result.addError(ApkVerifier.Issue.V2_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD, V2SchemeVerifier.toHex(certificatePublicKeyBytes), V2SchemeVerifier.toHex(publicKeyBytes));
            return;
        }
        int digestCount = 0;
        while (digests.hasRemaining()) {
            ++digestCount;
            try {
                ByteBuffer digest = V2SchemeVerifier.getLengthPrefixedSlice(digests);
                int sigAlgorithmId = digest.getInt();
                byte[] byArray = V2SchemeVerifier.readLengthPrefixedByteArray(digest);
                result.contentDigests.add(new Result.SignerInfo.ContentDigest(sigAlgorithmId, byArray));
            }
            catch (ApkFormatException | BufferUnderflowException e7) {
                result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_DIGEST, digestCount);
                return;
            }
        }
        ArrayList<Integer> sigAlgsFromSignaturesRecord = new ArrayList<Integer>(result.signatures.size());
        for (Result.SignerInfo.Signature signature : result.signatures) {
            sigAlgsFromSignaturesRecord.add(signature.getAlgorithmId());
        }
        ArrayList<Integer> sigAlgsFromDigestsRecord = new ArrayList<Integer>(result.contentDigests.size());
        for (Result.SignerInfo.ContentDigest digest : result.contentDigests) {
            sigAlgsFromDigestsRecord.add(digest.getSignatureAlgorithmId());
        }
        if (!sigAlgsFromSignaturesRecord.equals(sigAlgsFromDigestsRecord)) {
            result.addError(ApkVerifier.Issue.V2_SIG_SIG_ALG_MISMATCH_BETWEEN_SIGNATURES_AND_DIGESTS_RECORDS, sigAlgsFromSignaturesRecord, sigAlgsFromDigestsRecord);
            return;
        }
        boolean bl = false;
        while (additionalAttributes.hasRemaining()) {
            void var22_38;
            ++var22_38;
            try {
                ByteBuffer attribute = V2SchemeVerifier.getLengthPrefixedSlice(additionalAttributes);
                int id = attribute.getInt();
                byte[] value = V2SchemeVerifier.readLengthPrefixedByteArray(attribute);
                result.additionalAttributes.add(new Result.SignerInfo.AdditionalAttribute(id, value));
                result.addWarning(ApkVerifier.Issue.V2_SIG_UNKNOWN_ADDITIONAL_ATTRIBUTE, id);
            }
            catch (ApkFormatException | BufferUnderflowException e8) {
                result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_ADDITIONAL_ATTRIBUTE, (int)var22_38);
                return;
            }
        }
    }

    private static List<SupportedSignature> getSignaturesToVerify(List<SupportedSignature> signatures, int minSdkVersion, int maxSdkVersion) throws NoSupportedSignaturesException {
        HashMap<Integer, SupportedSignature> bestSigAlgorithmOnSdkVersion = new HashMap<Integer, SupportedSignature>();
        int minProvidedSignaturesVersion = Integer.MAX_VALUE;
        for (SupportedSignature sig : signatures) {
            SupportedSignature candidate;
            SignatureAlgorithm sigAlgorithm = sig.algorithm;
            int sigMinSdkVersion = sigAlgorithm.getMinSdkVersion();
            if (sigMinSdkVersion > maxSdkVersion) continue;
            if (sigMinSdkVersion < minProvidedSignaturesVersion) {
                minProvidedSignaturesVersion = sigMinSdkVersion;
            }
            if ((candidate = (SupportedSignature)bestSigAlgorithmOnSdkVersion.get(sigMinSdkVersion)) != null && V2SchemeVerifier.compareSignatureAlgorithm(sigAlgorithm, candidate.algorithm) <= 0) continue;
            bestSigAlgorithmOnSdkVersion.put(sigMinSdkVersion, sig);
        }
        if (minSdkVersion < minProvidedSignaturesVersion) {
            throw new NoSupportedSignaturesException("Minimum provided signature version " + minProvidedSignaturesVersion + " < minSdkVersion " + minSdkVersion);
        }
        if (bestSigAlgorithmOnSdkVersion.isEmpty()) {
            throw new NoSupportedSignaturesException("No supported signature");
        }
        return bestSigAlgorithmOnSdkVersion.values().stream().sorted((sig1, sig2) -> Integer.compare(((SupportedSignature)sig1).algorithm.getId(), ((SupportedSignature)sig2).algorithm.getId())).collect(Collectors.toList());
    }

    private static int compareSignatureAlgorithm(SignatureAlgorithm alg1, SignatureAlgorithm alg2) {
        ContentDigestAlgorithm digestAlg1 = alg1.getContentDigestAlgorithm();
        ContentDigestAlgorithm digestAlg2 = alg2.getContentDigestAlgorithm();
        return V2SchemeVerifier.compareContentDigestAlgorithm(digestAlg1, digestAlg2);
    }

    private static int compareContentDigestAlgorithm(ContentDigestAlgorithm alg1, ContentDigestAlgorithm alg2) {
        switch (alg1) {
            case CHUNKED_SHA256: {
                switch (alg2) {
                    case CHUNKED_SHA256: {
                        return 0;
                    }
                    case CHUNKED_SHA512: 
                    case VERITY_CHUNKED_SHA256: {
                        return -1;
                    }
                }
                throw new IllegalArgumentException("Unknown alg2: " + (Object)((Object)alg2));
            }
            case CHUNKED_SHA512: {
                switch (alg2) {
                    case CHUNKED_SHA256: 
                    case VERITY_CHUNKED_SHA256: {
                        return 1;
                    }
                    case CHUNKED_SHA512: {
                        return 0;
                    }
                }
                throw new IllegalArgumentException("Unknown alg2: " + (Object)((Object)alg2));
            }
            case VERITY_CHUNKED_SHA256: {
                switch (alg2) {
                    case CHUNKED_SHA256: {
                        return 1;
                    }
                    case VERITY_CHUNKED_SHA256: {
                        return 0;
                    }
                    case CHUNKED_SHA512: {
                        return -1;
                    }
                }
                throw new IllegalArgumentException("Unknown alg2: " + (Object)((Object)alg2));
            }
        }
        throw new IllegalArgumentException("Unknown alg1: " + (Object)((Object)alg1));
    }

    private static void verifyIntegrity(DataSource beforeApkSigningBlock, DataSource centralDir, ByteBuffer eocd, Set<ContentDigestAlgorithm> contentDigestAlgorithms, Result result) throws IOException, NoSuchAlgorithmException {
        Map<ContentDigestAlgorithm, byte[]> actualContentDigests;
        if (contentDigestAlgorithms.isEmpty()) {
            throw new RuntimeException("No content digests found");
        }
        ByteBuffer modifiedEocd = ByteBuffer.allocate(eocd.remaining());
        modifiedEocd.order(ByteOrder.LITTLE_ENDIAN);
        modifiedEocd.put(eocd);
        modifiedEocd.flip();
        ZipUtils.setZipEocdCentralDirectoryOffset(modifiedEocd, beforeApkSigningBlock.size());
        try {
            actualContentDigests = V2SchemeSigner.computeContentDigests(contentDigestAlgorithms, beforeApkSigningBlock, centralDir, new ByteBufferDataSource(modifiedEocd));
        }
        catch (DigestException e2) {
            throw new RuntimeException("Failed to compute content digests", e2);
        }
        if (!contentDigestAlgorithms.equals(actualContentDigests.keySet())) {
            throw new RuntimeException("Mismatch between sets of requested and computed content digests . Requested: " + contentDigestAlgorithms + ", computed: " + actualContentDigests.keySet());
        }
        for (Result.SignerInfo signerInfo : result.signers) {
            for (Result.SignerInfo.ContentDigest expected : signerInfo.contentDigests) {
                byte[] actualDigest;
                SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.findById(expected.getSignatureAlgorithmId());
                if (signatureAlgorithm == null) continue;
                ContentDigestAlgorithm contentDigestAlgorithm = signatureAlgorithm.getContentDigestAlgorithm();
                byte[] expectedDigest = expected.getValue();
                if (!Arrays.equals(expectedDigest, actualDigest = actualContentDigests.get((Object)contentDigestAlgorithm))) {
                    signerInfo.addError(ApkVerifier.Issue.V2_SIG_APK_DIGEST_DID_NOT_VERIFY, new Object[]{contentDigestAlgorithm, V2SchemeVerifier.toHex(expectedDigest), V2SchemeVerifier.toHex(actualDigest)});
                    continue;
                }
                signerInfo.verifiedContentDigests.put(contentDigestAlgorithm, actualDigest);
            }
        }
    }

    private static SignatureInfo findSignature(DataSource apk, ApkUtils.ZipSections zipSections, Result result) throws IOException, SignatureNotFoundException {
        DataSource apkSigningBlock;
        long apkSigningBlockOffset;
        try {
            ApkUtils.ApkSigningBlock apkSigningBlockInfo = ApkUtils.findApkSigningBlock(apk, zipSections);
            apkSigningBlockOffset = apkSigningBlockInfo.getStartOffset();
            apkSigningBlock = apkSigningBlockInfo.getContents();
        }
        catch (ApkSigningBlockNotFoundException e2) {
            throw new SignatureNotFoundException(e2.getMessage(), e2);
        }
        ByteBuffer apkSigningBlockBuf = apkSigningBlock.getByteBuffer(0L, (int)apkSigningBlock.size());
        apkSigningBlockBuf.order(ByteOrder.LITTLE_ENDIAN);
        ByteBuffer apkSignatureSchemeV2Block = V2SchemeVerifier.findApkSignatureSchemeV2Block(apkSigningBlockBuf, result);
        return new SignatureInfo(apkSignatureSchemeV2Block, apkSigningBlockOffset, zipSections.getZipCentralDirectoryOffset(), zipSections.getZipEndOfCentralDirectoryOffset(), zipSections.getZipEndOfCentralDirectory());
    }

    private static ByteBuffer findApkSignatureSchemeV2Block(ByteBuffer apkSigningBlock, Result result) throws SignatureNotFoundException {
        V2SchemeVerifier.checkByteOrderLittleEndian(apkSigningBlock);
        ByteBuffer pairs = V2SchemeVerifier.sliceFromTo(apkSigningBlock, 8, apkSigningBlock.capacity() - 24);
        int entryCount = 0;
        while (pairs.hasRemaining()) {
            ++entryCount;
            if (pairs.remaining() < 8) {
                throw new SignatureNotFoundException("Insufficient data to read size of APK Signing Block entry #" + entryCount);
            }
            long lenLong = pairs.getLong();
            if (lenLong < 4L || lenLong > Integer.MAX_VALUE) {
                throw new SignatureNotFoundException("APK Signing Block entry #" + entryCount + " size out of range: " + lenLong);
            }
            int len = (int)lenLong;
            int nextEntryPos = pairs.position() + len;
            if (len > pairs.remaining()) {
                throw new SignatureNotFoundException("APK Signing Block entry #" + entryCount + " size out of range: " + len + ", available: " + pairs.remaining());
            }
            int id = pairs.getInt();
            if (id == 1896449818) {
                return V2SchemeVerifier.getByteBuffer(pairs, len - 4);
            }
            result.addWarning(ApkVerifier.Issue.APK_SIG_BLOCK_UNKNOWN_ENTRY_ID, id);
            pairs.position(nextEntryPos);
        }
        throw new SignatureNotFoundException("No APK Signature Scheme v2 block in APK Signing Block");
    }

    private static void checkByteOrderLittleEndian(ByteBuffer buffer) {
        if (buffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ByteBuffer sliceFromTo(ByteBuffer source, int start, int end) {
        if (start < 0) {
            throw new IllegalArgumentException("start: " + start);
        }
        if (end < start) {
            throw new IllegalArgumentException("end < start: " + end + " < " + start);
        }
        int capacity = source.capacity();
        if (end > source.capacity()) {
            throw new IllegalArgumentException("end > capacity: " + end + " > " + capacity);
        }
        int originalLimit = source.limit();
        int originalPosition = source.position();
        try {
            source.position(0);
            source.limit(end);
            source.position(start);
            ByteBuffer result = source.slice();
            result.order(source.order());
            ByteBuffer byteBuffer = result;
            return byteBuffer;
        }
        finally {
            source.position(0);
            source.limit(originalLimit);
            source.position(originalPosition);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static ByteBuffer getByteBuffer(ByteBuffer source, int size) throws BufferUnderflowException {
        if (size < 0) {
            throw new IllegalArgumentException("size: " + size);
        }
        int originalLimit = source.limit();
        int position = source.position();
        int limit = position + size;
        if (limit < position || limit > originalLimit) {
            throw new BufferUnderflowException();
        }
        source.limit(limit);
        try {
            ByteBuffer result = source.slice();
            result.order(source.order());
            source.position(limit);
            ByteBuffer byteBuffer = result;
            return byteBuffer;
        }
        finally {
            source.limit(originalLimit);
        }
    }

    private static ByteBuffer getLengthPrefixedSlice(ByteBuffer source) throws ApkFormatException {
        if (source.remaining() < 4) {
            throw new ApkFormatException("Remaining buffer too short to contain length of length-prefixed field. Remaining: " + source.remaining());
        }
        int len = source.getInt();
        if (len < 0) {
            throw new IllegalArgumentException("Negative length");
        }
        if (len > source.remaining()) {
            throw new ApkFormatException("Length-prefixed field longer than remaining buffer. Field length: " + len + ", remaining: " + source.remaining());
        }
        return V2SchemeVerifier.getByteBuffer(source, len);
    }

    private static byte[] readLengthPrefixedByteArray(ByteBuffer buf) throws ApkFormatException {
        int len = buf.getInt();
        if (len < 0) {
            throw new ApkFormatException("Negative length");
        }
        if (len > buf.remaining()) {
            throw new ApkFormatException("Underflow while reading length-prefixed value. Length: " + len + ", available: " + buf.remaining());
        }
        byte[] result = new byte[len];
        buf.get(result);
        return result;
    }

    private static String toHex(byte[] value) {
        StringBuilder sb = new StringBuilder(value.length * 2);
        int len = value.length;
        for (int i2 = 0; i2 < len; ++i2) {
            int hi = (value[i2] & 0xFF) >>> 4;
            int lo = value[i2] & 0xF;
            sb.append(HEX_DIGITS[hi]).append(HEX_DIGITS[lo]);
        }
        return sb.toString();
    }

    public static class Result {
        public boolean verified;
        public final List<SignerInfo> signers = new ArrayList<SignerInfo>();
        private final List<ApkVerifier.IssueWithParams> mWarnings = new ArrayList<ApkVerifier.IssueWithParams>();
        private final List<ApkVerifier.IssueWithParams> mErrors = new ArrayList<ApkVerifier.IssueWithParams>();

        public boolean containsErrors() {
            if (!this.mErrors.isEmpty()) {
                return true;
            }
            if (!this.signers.isEmpty()) {
                for (SignerInfo signer : this.signers) {
                    if (!signer.containsErrors()) continue;
                    return true;
                }
            }
            return false;
        }

        public void addError(ApkVerifier.Issue msg, Object ... parameters) {
            this.mErrors.add(new ApkVerifier.IssueWithParams(msg, parameters));
        }

        public void addWarning(ApkVerifier.Issue msg, Object ... parameters) {
            this.mWarnings.add(new ApkVerifier.IssueWithParams(msg, parameters));
        }

        public List<ApkVerifier.IssueWithParams> getErrors() {
            return this.mErrors;
        }

        public List<ApkVerifier.IssueWithParams> getWarnings() {
            return this.mWarnings;
        }

        public static class SignerInfo {
            public int index;
            public List<X509Certificate> certs = new ArrayList<X509Certificate>();
            public List<ContentDigest> contentDigests = new ArrayList<ContentDigest>();
            public Map<ContentDigestAlgorithm, byte[]> verifiedContentDigests = new HashMap<ContentDigestAlgorithm, byte[]>();
            public List<Signature> signatures = new ArrayList<Signature>();
            public Map<SignatureAlgorithm, byte[]> verifiedSignatures = new HashMap<SignatureAlgorithm, byte[]>();
            public List<AdditionalAttribute> additionalAttributes = new ArrayList<AdditionalAttribute>();
            public byte[] signedData;
            private final List<ApkVerifier.IssueWithParams> mWarnings = new ArrayList<ApkVerifier.IssueWithParams>();
            private final List<ApkVerifier.IssueWithParams> mErrors = new ArrayList<ApkVerifier.IssueWithParams>();

            public void addError(ApkVerifier.Issue msg, Object ... parameters) {
                this.mErrors.add(new ApkVerifier.IssueWithParams(msg, parameters));
            }

            public void addWarning(ApkVerifier.Issue msg, Object ... parameters) {
                this.mWarnings.add(new ApkVerifier.IssueWithParams(msg, parameters));
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            public List<ApkVerifier.IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<ApkVerifier.IssueWithParams> getWarnings() {
                return this.mWarnings;
            }

            public static class AdditionalAttribute {
                private final int mId;
                private final byte[] mValue;

                public AdditionalAttribute(int id, byte[] value) {
                    this.mId = id;
                    this.mValue = (byte[])value.clone();
                }

                public int getId() {
                    return this.mId;
                }

                public byte[] getValue() {
                    return (byte[])this.mValue.clone();
                }
            }

            public static class Signature {
                private final int mAlgorithmId;
                private final byte[] mValue;

                public Signature(int algorithmId, byte[] value) {
                    this.mAlgorithmId = algorithmId;
                    this.mValue = value;
                }

                public int getAlgorithmId() {
                    return this.mAlgorithmId;
                }

                public byte[] getValue() {
                    return this.mValue;
                }
            }

            public static class ContentDigest {
                private final int mSignatureAlgorithmId;
                private final byte[] mValue;

                public ContentDigest(int signatureAlgorithmId, byte[] value) {
                    this.mSignatureAlgorithmId = signatureAlgorithmId;
                    this.mValue = value;
                }

                public int getSignatureAlgorithmId() {
                    return this.mSignatureAlgorithmId;
                }

                public byte[] getValue() {
                    return this.mValue;
                }
            }
        }
    }

    public static class SignatureNotFoundException
    extends Exception {
        private static final long serialVersionUID = 1L;

        public SignatureNotFoundException(String message) {
            super(message);
        }

        public SignatureNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static class SignatureInfo {
        private final ByteBuffer signatureBlock;
        private final long apkSigningBlockOffset;
        private final long centralDirOffset;
        private final long eocdOffset;
        private final ByteBuffer eocd;

        private SignatureInfo(ByteBuffer signatureBlock, long apkSigningBlockOffset, long centralDirOffset, long eocdOffset, ByteBuffer eocd) {
            this.signatureBlock = signatureBlock;
            this.apkSigningBlockOffset = apkSigningBlockOffset;
            this.centralDirOffset = centralDirOffset;
            this.eocdOffset = eocdOffset;
            this.eocd = eocd;
        }
    }

    private static class SupportedSignature {
        private final SignatureAlgorithm algorithm;
        private final byte[] signature;

        private SupportedSignature(SignatureAlgorithm algorithm, byte[] signature) {
            this.algorithm = algorithm;
            this.signature = signature;
        }
    }

    private static class NoSupportedSignaturesException
    extends Exception {
        private static final long serialVersionUID = 1L;

        public NoSupportedSignaturesException(String message) {
            super(message);
        }
    }
}


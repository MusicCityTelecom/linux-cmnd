/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig;

import com.android.apksig.ApkSignerEngine;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.v1.DigestAlgorithm;
import com.android.apksig.internal.apk.v1.V1SchemeSigner;
import com.android.apksig.internal.apk.v2.V2SchemeSigner;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.internal.util.TeeDataSink;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSinks;
import com.android.apksig.util.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultApkSignerEngine
implements ApkSignerEngine {
    private final boolean mV1SigningEnabled;
    private final boolean mV2SigningEnabled;
    private final boolean mDebuggableApkPermitted;
    private final boolean mOtherSignersSignaturesPreserved;
    private final String mCreatedBy;
    private final List<SignerConfig> mSignerConfigs;
    private final int mMinSdkVersion;
    private List<V1SchemeSigner.SignerConfig> mV1SignerConfigs = Collections.emptyList();
    private DigestAlgorithm mV1ContentDigestAlgorithm;
    private boolean mClosed;
    private boolean mV1SignaturePending;
    private Set<String> mSignatureExpectedOutputJarEntryNames = Collections.emptySet();
    private final Map<String, GetJarEntryDataDigestRequest> mOutputJarEntryDigestRequests = new HashMap<String, GetJarEntryDataDigestRequest>();
    private final Map<String, byte[]> mOutputJarEntryDigests = new HashMap<String, byte[]>();
    private final Map<String, byte[]> mEmittedSignatureJarEntryData = new HashMap<String, byte[]>();
    private final Map<String, GetJarEntryDataRequest> mOutputSignatureJarEntryDataRequests = new HashMap<String, GetJarEntryDataRequest>();
    private GetJarEntryDataRequest mInputJarManifestEntryDataRequest;
    private GetJarEntryDataRequest mOutputAndroidManifestEntryDataRequest;
    private Boolean mDebuggable;
    private OutputJarSignatureRequestImpl mAddV1SignatureRequest;
    private boolean mV2SignaturePending;
    private OutputApkSigningBlockRequestImpl mAddV2SignatureRequest;

    private DefaultApkSignerEngine(List<SignerConfig> signerConfigs, int minSdkVersion, boolean v1SigningEnabled, boolean v2SigningEnabled, boolean debuggableApkPermitted, boolean otherSignersSignaturesPreserved, String createdBy) throws InvalidKeyException {
        if (signerConfigs.isEmpty()) {
            throw new IllegalArgumentException("At least one signer config must be provided");
        }
        if (otherSignersSignaturesPreserved) {
            throw new UnsupportedOperationException("Preserving other signer's signatures is not yet implemented");
        }
        this.mV1SigningEnabled = v1SigningEnabled;
        this.mV2SigningEnabled = v2SigningEnabled;
        this.mV1SignaturePending = v1SigningEnabled;
        this.mV2SignaturePending = v2SigningEnabled;
        this.mDebuggableApkPermitted = debuggableApkPermitted;
        this.mOtherSignersSignaturesPreserved = otherSignersSignaturesPreserved;
        this.mCreatedBy = createdBy;
        this.mSignerConfigs = signerConfigs;
        this.mMinSdkVersion = minSdkVersion;
        if (v1SigningEnabled) {
            this.createV1SignerConfigs(signerConfigs, minSdkVersion);
        }
    }

    private void createV1SignerConfigs(List<SignerConfig> signerConfigs, int minSdkVersion) throws InvalidKeyException {
        this.mV1SignerConfigs = new ArrayList<V1SchemeSigner.SignerConfig>(signerConfigs.size());
        HashMap<String, Integer> v1SignerNameToSignerIndex = new HashMap<String, Integer>(signerConfigs.size());
        DigestAlgorithm v1ContentDigestAlgorithm = null;
        for (int i2 = 0; i2 < signerConfigs.size(); ++i2) {
            SignerConfig signerConfig = signerConfigs.get(i2);
            List<X509Certificate> certificates = signerConfig.getCertificates();
            PublicKey publicKey = certificates.get(0).getPublicKey();
            String v1SignerName = V1SchemeSigner.getSafeSignerName(signerConfig.getName());
            Integer indexOfOtherSignerWithSameName = v1SignerNameToSignerIndex.put(v1SignerName, i2);
            if (indexOfOtherSignerWithSameName != null) {
                throw new IllegalArgumentException("Signers #" + (indexOfOtherSignerWithSameName + 1) + " and #" + (i2 + 1) + " have the same name: " + v1SignerName + ". v1 signer names must be unique");
            }
            DigestAlgorithm v1SignatureDigestAlgorithm = V1SchemeSigner.getSuggestedSignatureDigestAlgorithm(publicKey, minSdkVersion);
            V1SchemeSigner.SignerConfig v1SignerConfig = new V1SchemeSigner.SignerConfig();
            v1SignerConfig.name = v1SignerName;
            v1SignerConfig.privateKey = signerConfig.getPrivateKey();
            v1SignerConfig.certificates = certificates;
            v1SignerConfig.signatureDigestAlgorithm = v1SignatureDigestAlgorithm;
            if (v1ContentDigestAlgorithm == null) {
                v1ContentDigestAlgorithm = v1SignatureDigestAlgorithm;
            } else if (DigestAlgorithm.BY_STRENGTH_COMPARATOR.compare(v1SignatureDigestAlgorithm, v1ContentDigestAlgorithm) > 0) {
                v1ContentDigestAlgorithm = v1SignatureDigestAlgorithm;
            }
            this.mV1SignerConfigs.add(v1SignerConfig);
        }
        this.mV1ContentDigestAlgorithm = v1ContentDigestAlgorithm;
        this.mSignatureExpectedOutputJarEntryNames = V1SchemeSigner.getOutputEntryNames(this.mV1SignerConfigs);
    }

    private List<V2SchemeSigner.SignerConfig> createV2SignerConfigs(boolean apkSigningBlockPaddingSupported) throws InvalidKeyException {
        ArrayList<V2SchemeSigner.SignerConfig> v2SignerConfigs = new ArrayList<V2SchemeSigner.SignerConfig>(this.mSignerConfigs.size());
        for (int i2 = 0; i2 < this.mSignerConfigs.size(); ++i2) {
            SignerConfig signerConfig = this.mSignerConfigs.get(i2);
            List<X509Certificate> certificates = signerConfig.getCertificates();
            PublicKey publicKey = certificates.get(0).getPublicKey();
            V2SchemeSigner.SignerConfig v2SignerConfig = new V2SchemeSigner.SignerConfig();
            v2SignerConfig.privateKey = signerConfig.getPrivateKey();
            v2SignerConfig.certificates = certificates;
            v2SignerConfig.signatureAlgorithms = V2SchemeSigner.getSuggestedSignatureAlgorithms(publicKey, this.mMinSdkVersion, apkSigningBlockPaddingSupported);
            v2SignerConfigs.add(v2SignerConfig);
        }
        return v2SignerConfigs;
    }

    @Override
    public void inputApkSigningBlock(DataSource apkSigningBlock) {
        this.checkNotClosed();
        if (apkSigningBlock == null || apkSigningBlock.size() == 0L) {
            return;
        }
        if (this.mOtherSignersSignaturesPreserved) {
            return;
        }
    }

    @Override
    public ApkSignerEngine.InputJarEntryInstructions inputJarEntry(String entryName) {
        this.checkNotClosed();
        ApkSignerEngine.InputJarEntryInstructions.OutputPolicy outputPolicy = this.getInputJarEntryOutputPolicy(entryName);
        switch (outputPolicy) {
            case SKIP: {
                return new ApkSignerEngine.InputJarEntryInstructions(ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.SKIP);
            }
            case OUTPUT: {
                return new ApkSignerEngine.InputJarEntryInstructions(ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT);
            }
            case OUTPUT_BY_ENGINE: {
                if ("META-INF/MANIFEST.MF".equals(entryName)) {
                    this.mInputJarManifestEntryDataRequest = new GetJarEntryDataRequest(entryName);
                    return new ApkSignerEngine.InputJarEntryInstructions(ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT_BY_ENGINE, this.mInputJarManifestEntryDataRequest);
                }
                return new ApkSignerEngine.InputJarEntryInstructions(ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT_BY_ENGINE);
            }
        }
        throw new RuntimeException("Unsupported output policy: " + (Object)((Object)outputPolicy));
    }

    @Override
    public ApkSignerEngine.InspectJarEntryRequest outputJarEntry(String entryName) {
        this.checkNotClosed();
        this.invalidateV2Signature();
        if (!this.mDebuggableApkPermitted && "AndroidManifest.xml".equals(entryName)) {
            this.forgetOutputApkDebuggableStatus();
        }
        if (!this.mV1SigningEnabled) {
            if (!this.mDebuggableApkPermitted && "AndroidManifest.xml".equals(entryName)) {
                this.mOutputAndroidManifestEntryDataRequest = new GetJarEntryDataRequest(entryName);
                return this.mOutputAndroidManifestEntryDataRequest;
            }
            return null;
        }
        if (V1SchemeSigner.isJarEntryDigestNeededInManifest(entryName)) {
            this.invalidateV1Signature();
            GetJarEntryDataDigestRequest dataDigestRequest = new GetJarEntryDataDigestRequest(entryName, V1SchemeSigner.getJcaMessageDigestAlgorithm(this.mV1ContentDigestAlgorithm));
            this.mOutputJarEntryDigestRequests.put(entryName, dataDigestRequest);
            this.mOutputJarEntryDigests.remove(entryName);
            if (!this.mDebuggableApkPermitted && "AndroidManifest.xml".equals(entryName)) {
                this.mOutputAndroidManifestEntryDataRequest = new GetJarEntryDataRequest(entryName);
                return new CompoundInspectJarEntryRequest(entryName, new ApkSignerEngine.InspectJarEntryRequest[]{this.mOutputAndroidManifestEntryDataRequest, dataDigestRequest});
            }
            return dataDigestRequest;
        }
        if (this.mSignatureExpectedOutputJarEntryNames.contains(entryName)) {
            GetJarEntryDataRequest dataRequest;
            this.invalidateV1Signature();
            if ("META-INF/MANIFEST.MF".equals(entryName)) {
                this.mInputJarManifestEntryDataRequest = dataRequest = new GetJarEntryDataRequest(entryName);
            } else {
                GetJarEntryDataRequest getJarEntryDataRequest = dataRequest = this.mEmittedSignatureJarEntryData.containsKey(entryName) ? new GetJarEntryDataRequest(entryName) : null;
            }
            if (dataRequest != null) {
                this.mOutputSignatureJarEntryDataRequests.put(entryName, dataRequest);
            }
            return dataRequest;
        }
        return null;
    }

    @Override
    public ApkSignerEngine.InputJarEntryInstructions.OutputPolicy inputJarEntryRemoved(String entryName) {
        this.checkNotClosed();
        return this.getInputJarEntryOutputPolicy(entryName);
    }

    @Override
    public void outputJarEntryRemoved(String entryName) {
        this.checkNotClosed();
        this.invalidateV2Signature();
        if (!this.mV1SigningEnabled) {
            return;
        }
        if (V1SchemeSigner.isJarEntryDigestNeededInManifest(entryName)) {
            this.invalidateV1Signature();
            this.mOutputJarEntryDigests.remove(entryName);
            this.mOutputJarEntryDigestRequests.remove(entryName);
            this.mOutputSignatureJarEntryDataRequests.remove(entryName);
            return;
        }
        if (this.mSignatureExpectedOutputJarEntryNames.contains(entryName)) {
            this.invalidateV1Signature();
            return;
        }
    }

    @Override
    public ApkSignerEngine.OutputJarSignatureRequest outputJarEntries() throws ApkFormatException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        List<Pair<String, byte[]>> signatureZipEntries;
        this.checkNotClosed();
        if (!this.mV1SignaturePending) {
            return null;
        }
        if (this.mInputJarManifestEntryDataRequest != null && !this.mInputJarManifestEntryDataRequest.isDone()) {
            throw new IllegalStateException("Still waiting to inspect input APK's " + this.mInputJarManifestEntryDataRequest.getEntryName());
        }
        for (GetJarEntryDataDigestRequest digestRequest : this.mOutputJarEntryDigestRequests.values()) {
            String entryName = digestRequest.getEntryName();
            if (!digestRequest.isDone()) {
                throw new IllegalStateException("Still waiting to inspect output APK's " + entryName);
            }
            this.mOutputJarEntryDigests.put(entryName, digestRequest.getDigest());
        }
        this.mOutputJarEntryDigestRequests.clear();
        for (GetJarEntryDataRequest dataRequest : this.mOutputSignatureJarEntryDataRequests.values()) {
            if (dataRequest.isDone()) continue;
            throw new IllegalStateException("Still waiting to inspect output APK's " + dataRequest.getEntryName());
        }
        List<Integer> apkSigningSchemeIds = this.mV2SigningEnabled ? Collections.singletonList(2) : Collections.emptyList();
        byte[] inputJarManifest = this.mInputJarManifestEntryDataRequest != null ? this.mInputJarManifestEntryDataRequest.getData() : null;
        this.checkOutputApkNotDebuggableIfDebuggableMustBeRejected();
        if (this.mAddV1SignatureRequest == null || !this.mAddV1SignatureRequest.isDone()) {
            try {
                signatureZipEntries = V1SchemeSigner.sign(this.mV1SignerConfigs, this.mV1ContentDigestAlgorithm, this.mOutputJarEntryDigests, apkSigningSchemeIds, inputJarManifest, this.mCreatedBy);
            }
            catch (CertificateException e2) {
                throw new SignatureException("Failed to generate v1 signature", e2);
            }
        } else {
            V1SchemeSigner.OutputManifestFile newManifest = V1SchemeSigner.generateManifestFile(this.mV1ContentDigestAlgorithm, this.mOutputJarEntryDigests, inputJarManifest);
            byte[] emittedSignatureManifest = this.mEmittedSignatureJarEntryData.get("META-INF/MANIFEST.MF");
            if (!Arrays.equals(newManifest.contents, emittedSignatureManifest)) {
                try {
                    signatureZipEntries = V1SchemeSigner.signManifest(this.mV1SignerConfigs, this.mV1ContentDigestAlgorithm, apkSigningSchemeIds, this.mCreatedBy, newManifest);
                }
                catch (CertificateException e3) {
                    throw new SignatureException("Failed to generate v1 signature", e3);
                }
            } else {
                signatureZipEntries = new ArrayList<Pair<String, byte[]>>();
                for (Map.Entry<String, byte[]> expectedOutputEntry : this.mEmittedSignatureJarEntryData.entrySet()) {
                    String entryName = expectedOutputEntry.getKey();
                    byte[] expectedData = expectedOutputEntry.getValue();
                    GetJarEntryDataRequest actualDataRequest = this.mOutputSignatureJarEntryDataRequests.get(entryName);
                    if (actualDataRequest == null) {
                        signatureZipEntries.add(Pair.of(entryName, expectedData));
                        continue;
                    }
                    byte[] actualData = actualDataRequest.getData();
                    if (Arrays.equals(expectedData, actualData)) continue;
                    signatureZipEntries.add(Pair.of(entryName, expectedData));
                }
                if (signatureZipEntries.isEmpty()) {
                    return null;
                }
            }
        }
        if (signatureZipEntries.isEmpty()) {
            this.mV1SignaturePending = false;
            return null;
        }
        ArrayList<ApkSignerEngine.OutputJarSignatureRequest.JarEntry> sigEntries = new ArrayList<ApkSignerEngine.OutputJarSignatureRequest.JarEntry>(signatureZipEntries.size());
        for (Pair<String, byte[]> entry : signatureZipEntries) {
            String entryName = entry.getFirst();
            byte[] entryData = entry.getSecond();
            sigEntries.add(new ApkSignerEngine.OutputJarSignatureRequest.JarEntry(entryName, entryData));
            this.mEmittedSignatureJarEntryData.put(entryName, entryData);
        }
        this.mAddV1SignatureRequest = new OutputJarSignatureRequestImpl(sigEntries);
        return this.mAddV1SignatureRequest;
    }

    @Override
    @Deprecated
    public ApkSignerEngine.OutputApkSigningBlockRequest outputZipSections(DataSource zipEntries, DataSource zipCentralDirectory, DataSource zipEocd) throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        return this.outputZipSectionsInternal(zipEntries, zipCentralDirectory, zipEocd, false);
    }

    @Override
    public ApkSignerEngine.OutputApkSigningBlockRequest2 outputZipSections2(DataSource zipEntries, DataSource zipCentralDirectory, DataSource zipEocd) throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        return this.outputZipSectionsInternal(zipEntries, zipCentralDirectory, zipEocd, true);
    }

    private OutputApkSigningBlockRequestImpl outputZipSectionsInternal(DataSource zipEntries, DataSource zipCentralDirectory, DataSource zipEocd, boolean apkSigningBlockPaddingSupported) throws IOException, InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        this.checkNotClosed();
        this.checkV1SigningDoneIfEnabled();
        if (!this.mV2SigningEnabled) {
            return null;
        }
        this.invalidateV2Signature();
        List<V2SchemeSigner.SignerConfig> v2SignerConfigs = this.createV2SignerConfigs(apkSigningBlockPaddingSupported);
        this.checkOutputApkNotDebuggableIfDebuggableMustBeRejected();
        Pair<byte[], Integer> result = V2SchemeSigner.generateApkSigningBlock(zipEntries, zipCentralDirectory, zipEocd, v2SignerConfigs, apkSigningBlockPaddingSupported);
        byte[] apkSigningBlock = result.getFirst();
        int padSizeBeforeApkSigningBlock = result.getSecond();
        this.mAddV2SignatureRequest = new OutputApkSigningBlockRequestImpl(apkSigningBlock, padSizeBeforeApkSigningBlock);
        return this.mAddV2SignatureRequest;
    }

    @Override
    public void outputDone() {
        this.checkNotClosed();
        this.checkV1SigningDoneIfEnabled();
        this.checkV2SigningDoneIfEnabled();
    }

    @Override
    public void close() {
        this.mClosed = true;
        this.mAddV1SignatureRequest = null;
        this.mInputJarManifestEntryDataRequest = null;
        this.mOutputAndroidManifestEntryDataRequest = null;
        this.mDebuggable = null;
        this.mOutputJarEntryDigestRequests.clear();
        this.mOutputJarEntryDigests.clear();
        this.mEmittedSignatureJarEntryData.clear();
        this.mOutputSignatureJarEntryDataRequests.clear();
        this.mAddV2SignatureRequest = null;
    }

    private void invalidateV1Signature() {
        if (this.mV1SigningEnabled) {
            this.mV1SignaturePending = true;
        }
        this.invalidateV2Signature();
    }

    private void invalidateV2Signature() {
        if (this.mV2SigningEnabled) {
            this.mV2SignaturePending = true;
            this.mAddV2SignatureRequest = null;
        }
    }

    private void checkNotClosed() {
        if (this.mClosed) {
            throw new IllegalStateException("Engine closed");
        }
    }

    private void checkV1SigningDoneIfEnabled() {
        if (!this.mV1SignaturePending) {
            return;
        }
        if (this.mAddV1SignatureRequest == null) {
            throw new IllegalStateException("v1 signature (JAR signature) not yet generated. Skipped outputJarEntries()?");
        }
        if (!this.mAddV1SignatureRequest.isDone()) {
            throw new IllegalStateException("v1 signature (JAR signature) addition requested by outputJarEntries() hasn't been fulfilled");
        }
        for (Map.Entry<String, byte[]> expectedOutputEntry : this.mEmittedSignatureJarEntryData.entrySet()) {
            String entryName = expectedOutputEntry.getKey();
            byte[] expectedData = expectedOutputEntry.getValue();
            GetJarEntryDataRequest actualDataRequest = this.mOutputSignatureJarEntryDataRequests.get(entryName);
            if (actualDataRequest == null) {
                throw new IllegalStateException("APK entry " + entryName + " not yet output despite this having been requested");
            }
            if (!actualDataRequest.isDone()) {
                throw new IllegalStateException("Still waiting to inspect output APK's " + entryName);
            }
            byte[] actualData = actualDataRequest.getData();
            if (Arrays.equals(expectedData, actualData)) continue;
            throw new IllegalStateException("Output APK entry " + entryName + " data differs from what was requested");
        }
        this.mV1SignaturePending = false;
    }

    private void checkV2SigningDoneIfEnabled() {
        if (!this.mV2SignaturePending) {
            return;
        }
        if (this.mAddV2SignatureRequest == null) {
            throw new IllegalStateException("v2 signature (APK Signature Scheme v2 signature) not yet generated. Skipped outputZipSections()?");
        }
        if (!this.mAddV2SignatureRequest.isDone()) {
            throw new IllegalStateException("v2 signature (APK Signature Scheme v2 signature) addition requested by outputZipSections() hasn't been fulfilled yet");
        }
        this.mAddV2SignatureRequest = null;
        this.mV2SignaturePending = false;
    }

    private void checkOutputApkNotDebuggableIfDebuggableMustBeRejected() throws SignatureException {
        if (this.mDebuggableApkPermitted) {
            return;
        }
        try {
            if (this.isOutputApkDebuggable()) {
                throw new SignatureException("APK is debuggable (see android:debuggable attribute) and this engine is configured to refuse to sign debuggable APKs");
            }
        }
        catch (ApkFormatException e2) {
            throw new SignatureException("Failed to determine whether the APK is debuggable", e2);
        }
    }

    private boolean isOutputApkDebuggable() throws ApkFormatException {
        if (this.mDebuggable != null) {
            return this.mDebuggable;
        }
        if (this.mOutputAndroidManifestEntryDataRequest == null) {
            throw new IllegalStateException("Cannot determine debuggable status of output APK because AndroidManifest.xml entry contents have not yet been requested");
        }
        if (!this.mOutputAndroidManifestEntryDataRequest.isDone()) {
            throw new IllegalStateException("Still waiting to inspect output APK's " + this.mOutputAndroidManifestEntryDataRequest.getEntryName());
        }
        this.mDebuggable = ApkUtils.getDebuggableFromBinaryAndroidManifest(ByteBuffer.wrap(this.mOutputAndroidManifestEntryDataRequest.getData()));
        return this.mDebuggable;
    }

    private void forgetOutputApkDebuggableStatus() {
        this.mDebuggable = null;
    }

    private ApkSignerEngine.InputJarEntryInstructions.OutputPolicy getInputJarEntryOutputPolicy(String entryName) {
        if (this.mSignatureExpectedOutputJarEntryNames.contains(entryName)) {
            return ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT_BY_ENGINE;
        }
        if (this.mOtherSignersSignaturesPreserved || V1SchemeSigner.isJarEntryDigestNeededInManifest(entryName)) {
            return ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT;
        }
        return ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.SKIP;
    }

    public static class Builder {
        private final List<SignerConfig> mSignerConfigs;
        private final int mMinSdkVersion;
        private boolean mV1SigningEnabled = true;
        private boolean mV2SigningEnabled = true;
        private boolean mDebuggableApkPermitted = true;
        private boolean mOtherSignersSignaturesPreserved;
        private String mCreatedBy = "1.0 (Android)";

        public Builder(List<SignerConfig> signerConfigs, int minSdkVersion) {
            if (signerConfigs.isEmpty()) {
                throw new IllegalArgumentException("At least one signer config must be provided");
            }
            this.mSignerConfigs = new ArrayList<SignerConfig>(signerConfigs);
            this.mMinSdkVersion = minSdkVersion;
        }

        public DefaultApkSignerEngine build() throws InvalidKeyException {
            return new DefaultApkSignerEngine(this.mSignerConfigs, this.mMinSdkVersion, this.mV1SigningEnabled, this.mV2SigningEnabled, this.mDebuggableApkPermitted, this.mOtherSignersSignaturesPreserved, this.mCreatedBy);
        }

        public Builder setV1SigningEnabled(boolean enabled) {
            this.mV1SigningEnabled = enabled;
            return this;
        }

        public Builder setV2SigningEnabled(boolean enabled) {
            this.mV2SigningEnabled = enabled;
            return this;
        }

        public Builder setDebuggableApkPermitted(boolean permitted) {
            this.mDebuggableApkPermitted = permitted;
            return this;
        }

        public Builder setOtherSignersSignaturesPreserved(boolean preserved) {
            this.mOtherSignersSignaturesPreserved = preserved;
            return this;
        }

        public Builder setCreatedBy(String createdBy) {
            if (createdBy == null) {
                throw new NullPointerException();
            }
            this.mCreatedBy = createdBy;
            return this;
        }
    }

    public static class SignerConfig {
        private final String mName;
        private final PrivateKey mPrivateKey;
        private final List<X509Certificate> mCertificates;

        private SignerConfig(String name, PrivateKey privateKey, List<X509Certificate> certificates) {
            this.mName = name;
            this.mPrivateKey = privateKey;
            this.mCertificates = Collections.unmodifiableList(new ArrayList<X509Certificate>(certificates));
        }

        public String getName() {
            return this.mName;
        }

        public PrivateKey getPrivateKey() {
            return this.mPrivateKey;
        }

        public List<X509Certificate> getCertificates() {
            return this.mCertificates;
        }

        public static class Builder {
            private final String mName;
            private final PrivateKey mPrivateKey;
            private final List<X509Certificate> mCertificates;

            public Builder(String name, PrivateKey privateKey, List<X509Certificate> certificates) {
                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Empty name");
                }
                this.mName = name;
                this.mPrivateKey = privateKey;
                this.mCertificates = new ArrayList<X509Certificate>(certificates);
            }

            public SignerConfig build() {
                return new SignerConfig(this.mName, this.mPrivateKey, this.mCertificates);
            }
        }
    }

    private static class CompoundInspectJarEntryRequest
    implements ApkSignerEngine.InspectJarEntryRequest {
        private final String mEntryName;
        private final ApkSignerEngine.InspectJarEntryRequest[] mRequests;
        private final Object mLock = new Object();
        private DataSink mSink;

        private CompoundInspectJarEntryRequest(String entryName, ApkSignerEngine.InspectJarEntryRequest ... requests) {
            this.mEntryName = entryName;
            this.mRequests = requests;
        }

        @Override
        public String getEntryName() {
            return this.mEntryName;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DataSink getDataSink() {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mSink == null) {
                    DataSink[] sinks = new DataSink[this.mRequests.length];
                    for (int i2 = 0; i2 < sinks.length; ++i2) {
                        sinks[i2] = this.mRequests[i2].getDataSink();
                    }
                    this.mSink = new TeeDataSink(sinks);
                }
                return this.mSink;
            }
        }

        @Override
        public void done() {
            for (ApkSignerEngine.InspectJarEntryRequest request : this.mRequests) {
                request.done();
            }
        }
    }

    private static class GetJarEntryDataDigestRequest
    implements ApkSignerEngine.InspectJarEntryRequest {
        private final String mEntryName;
        private final String mJcaDigestAlgorithm;
        private final Object mLock = new Object();
        private boolean mDone;
        private DataSink mDataSink;
        private MessageDigest mMessageDigest;
        private byte[] mDigest;

        private GetJarEntryDataDigestRequest(String entryName, String jcaDigestAlgorithm) {
            this.mEntryName = entryName;
            this.mJcaDigestAlgorithm = jcaDigestAlgorithm;
        }

        @Override
        public String getEntryName() {
            return this.mEntryName;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DataSink getDataSink() {
            Object object = this.mLock;
            synchronized (object) {
                this.checkNotDone();
                if (this.mDataSink == null) {
                    this.mDataSink = DataSinks.asDataSink(this.getMessageDigest());
                }
                return this.mDataSink;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private MessageDigest getMessageDigest() {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mMessageDigest == null) {
                    try {
                        this.mMessageDigest = MessageDigest.getInstance(this.mJcaDigestAlgorithm);
                    }
                    catch (NoSuchAlgorithmException e2) {
                        throw new RuntimeException(this.mJcaDigestAlgorithm + " MessageDigest not available", e2);
                    }
                }
                return this.mMessageDigest;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void done() {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mDone) {
                    return;
                }
                this.mDone = true;
                this.mDigest = this.getMessageDigest().digest();
                this.mMessageDigest = null;
                this.mDataSink = null;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean isDone() {
            Object object = this.mLock;
            synchronized (object) {
                return this.mDone;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void checkNotDone() throws IllegalStateException {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mDone) {
                    throw new IllegalStateException("Already done");
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private byte[] getDigest() {
            Object object = this.mLock;
            synchronized (object) {
                if (!this.mDone) {
                    throw new IllegalStateException("Not yet done");
                }
                return (byte[])this.mDigest.clone();
            }
        }
    }

    private static class GetJarEntryDataRequest
    implements ApkSignerEngine.InspectJarEntryRequest {
        private final String mEntryName;
        private final Object mLock = new Object();
        private boolean mDone;
        private DataSink mDataSink;
        private ByteArrayOutputStream mDataSinkBuf;

        private GetJarEntryDataRequest(String entryName) {
            this.mEntryName = entryName;
        }

        @Override
        public String getEntryName() {
            return this.mEntryName;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public DataSink getDataSink() {
            Object object = this.mLock;
            synchronized (object) {
                this.checkNotDone();
                if (this.mDataSinkBuf == null) {
                    this.mDataSinkBuf = new ByteArrayOutputStream();
                }
                if (this.mDataSink == null) {
                    this.mDataSink = DataSinks.asDataSink(this.mDataSinkBuf);
                }
                return this.mDataSink;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void done() {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mDone) {
                    return;
                }
                this.mDone = true;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean isDone() {
            Object object = this.mLock;
            synchronized (object) {
                return this.mDone;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void checkNotDone() throws IllegalStateException {
            Object object = this.mLock;
            synchronized (object) {
                if (this.mDone) {
                    throw new IllegalStateException("Already done");
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private byte[] getData() {
            Object object = this.mLock;
            synchronized (object) {
                if (!this.mDone) {
                    throw new IllegalStateException("Not yet done");
                }
                return this.mDataSinkBuf != null ? this.mDataSinkBuf.toByteArray() : new byte[]{};
            }
        }
    }

    private static class OutputApkSigningBlockRequestImpl
    implements ApkSignerEngine.OutputApkSigningBlockRequest,
    ApkSignerEngine.OutputApkSigningBlockRequest2 {
        private final byte[] mApkSigningBlock;
        private final int mPaddingBeforeApkSigningBlock;
        private volatile boolean mDone;

        private OutputApkSigningBlockRequestImpl(byte[] apkSigingBlock, int paddingBefore) {
            this.mApkSigningBlock = (byte[])apkSigingBlock.clone();
            this.mPaddingBeforeApkSigningBlock = paddingBefore;
        }

        @Override
        public byte[] getApkSigningBlock() {
            return (byte[])this.mApkSigningBlock.clone();
        }

        @Override
        public void done() {
            this.mDone = true;
        }

        private boolean isDone() {
            return this.mDone;
        }

        @Override
        public int getPaddingSizeBeforeApkSigningBlock() {
            return this.mPaddingBeforeApkSigningBlock;
        }
    }

    private static class OutputJarSignatureRequestImpl
    implements ApkSignerEngine.OutputJarSignatureRequest {
        private final List<ApkSignerEngine.OutputJarSignatureRequest.JarEntry> mAdditionalJarEntries;
        private volatile boolean mDone;

        private OutputJarSignatureRequestImpl(List<ApkSignerEngine.OutputJarSignatureRequest.JarEntry> additionalZipEntries) {
            this.mAdditionalJarEntries = Collections.unmodifiableList(new ArrayList<ApkSignerEngine.OutputJarSignatureRequest.JarEntry>(additionalZipEntries));
        }

        @Override
        public List<ApkSignerEngine.OutputJarSignatureRequest.JarEntry> getAdditionalJarEntries() {
            return this.mAdditionalJarEntries;
        }

        @Override
        public void done() {
            this.mDone = true;
        }

        private boolean isDone() {
            return this.mDone;
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.sign;

import com.android.apksig.ApkSignerEngine;
import com.android.apksig.ApkVerifier;
import com.android.apksig.DefaultApkSignerEngine;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import com.android.tools.build.apkzlib.sign.SigningOptions;
import com.android.tools.build.apkzlib.sign.ZFileDataSource;
import com.android.tools.build.apkzlib.utils.IOExceptionRunnable;
import com.android.tools.build.apkzlib.zip.StoredEntry;
import com.android.tools.build.apkzlib.zip.ZFile;
import com.android.tools.build.apkzlib.zip.ZFileExtension;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public class SigningExtension {
    private static final int MAX_READ_CHUNK_SIZE = 65536;
    private final ApkSignerEngine signer;
    private final Set<String> signerProcessedOutputEntryNames = new HashSet<String>();
    @Nullable
    private byte[] cachedApkSigningBlock;
    private boolean dirty;
    @Nullable
    private ZFileExtension extension;
    @Nullable
    private ZFile zFile;
    private final Supplier<byte[]> digestBuffer = Suppliers.memoize(() -> new byte[65536]);
    private final SigningOptions options;

    public SigningExtension(SigningOptions opts) throws InvalidKeyException {
        DefaultApkSignerEngine.SignerConfig signerConfig = new DefaultApkSignerEngine.SignerConfig.Builder("CERT", opts.getKey(), opts.getCertificates()).build();
        this.signer = new DefaultApkSignerEngine.Builder(ImmutableList.of(signerConfig), opts.getMinSdkVersion()).setOtherSignersSignaturesPreserved(false).setV1SigningEnabled(opts.isV1SigningEnabled()).setV2SigningEnabled(opts.isV2SigningEnabled()).setCreatedBy("1.0 (Android)").build();
        this.options = opts;
    }

    public void register(ZFile zFile) throws NoSuchAlgorithmException, IOException {
        Preconditions.checkState(this.extension == null, "register() already invoked");
        this.zFile = zFile;
        switch (this.options.getValidation()) {
            case ALWAYS_VALIDATE: {
                this.dirty = !this.isCurrentSignatureAsRequested();
                break;
            }
            case ASSUME_VALID: {
                this.dirty = false;
                break;
            }
            case ASSUME_INVALID: {
                this.dirty = true;
            }
        }
        this.extension = new ZFileExtension(){

            @Override
            public IOExceptionRunnable added(StoredEntry entry, @Nullable StoredEntry replaced) {
                return () -> SigningExtension.this.onZipEntryOutput(entry);
            }

            @Override
            public IOExceptionRunnable removed(StoredEntry entry) {
                String entryName = entry.getCentralDirectoryHeader().getName();
                return () -> SigningExtension.this.onZipEntryRemovedFromOutput(entryName);
            }

            @Override
            public IOExceptionRunnable beforeUpdate() throws IOException {
                return () -> SigningExtension.this.onOutputZipReadyForUpdate();
            }

            @Override
            public void entriesWritten() throws IOException {
                SigningExtension.this.onOutputZipEntriesWritten();
            }

            @Override
            public void closed() {
                SigningExtension.this.onOutputClosed();
            }
        };
        this.zFile.addZFileExtension(this.extension);
    }

    private boolean isCurrentSignatureAsRequested() throws IOException, NoSuchAlgorithmException {
        byte[] actualEncodedCert;
        byte[] expectedEncodedCert;
        ApkVerifier.Result result;
        try {
            result = new ApkVerifier.Builder(new ZFileDataSource(this.zFile)).setMinCheckedPlatformVersion(this.options.getMinSdkVersion()).build().verify();
        }
        catch (ApkFormatException e2) {
            return false;
        }
        if (!result.isVerified()) {
            return false;
        }
        if (result.isVerifiedUsingV1Scheme() != this.options.isV1SigningEnabled() || result.isVerifiedUsingV2Scheme() != this.options.isV2SigningEnabled()) {
            return false;
        }
        List<X509Certificate> verifiedSignerCerts = result.getSignerCertificates();
        if (verifiedSignerCerts.size() != 1) {
            return false;
        }
        try {
            expectedEncodedCert = ((X509Certificate)this.options.getCertificates().get(0)).getEncoded();
            actualEncodedCert = verifiedSignerCerts.get(0).getEncoded();
        }
        catch (CertificateEncodingException e3) {
            return false;
        }
        return Arrays.equals(expectedEncodedCert, actualEncodedCert);
    }

    private void onZipEntryOutput(StoredEntry entry) throws IOException {
        this.setDirty();
        String entryName = entry.getCentralDirectoryHeader().getName();
        if (entry.isDeleted()) {
            return;
        }
        ApkSignerEngine.InspectJarEntryRequest inspectEntryRequest = this.signer.outputJarEntry(entryName);
        this.signerProcessedOutputEntryNames.add(entryName);
        if (inspectEntryRequest != null) {
            try (BufferedInputStream inputStream = new BufferedInputStream(entry.open());){
                this.copyStreamToDataSink(inputStream, inspectEntryRequest.getDataSink());
            }
            inspectEntryRequest.done();
        }
    }

    private void copyStreamToDataSink(InputStream inputStream, DataSink dataSink) throws IOException {
        int bytesRead;
        byte[] buffer = this.digestBuffer.get();
        while ((bytesRead = inputStream.read(buffer)) > 0) {
            dataSink.consume(buffer, 0, bytesRead);
        }
    }

    private void onZipEntryRemovedFromOutput(String entryName) {
        this.setDirty();
        this.signer.outputJarEntryRemoved(entryName);
        this.signerProcessedOutputEntryNames.remove(entryName);
    }

    private void onOutputZipReadyForUpdate() throws IOException {
        String name;
        ApkSignerEngine.OutputJarSignatureRequest addV1SignatureRequest;
        if (!this.dirty) {
            return;
        }
        HashSet<String> unprocessedRemovedEntryNames = new HashSet<String>(this.signerProcessedOutputEntryNames);
        for (StoredEntry entry : this.zFile.entries()) {
            String entryName = entry.getCentralDirectoryHeader().getName();
            unprocessedRemovedEntryNames.remove(entryName);
            if (this.signerProcessedOutputEntryNames.contains(entryName)) continue;
            this.onZipEntryOutput(entry);
        }
        for (String entryName : unprocessedRemovedEntryNames) {
            this.onZipEntryRemovedFromOutput(entryName);
        }
        try {
            addV1SignatureRequest = this.signer.outputJarEntries();
        }
        catch (Exception e2) {
            throw new IOException("Failed to generate v1 signature", e2);
        }
        if (addV1SignatureRequest == null) {
            return;
        }
        ArrayList<ApkSignerEngine.OutputJarSignatureRequest.JarEntry> v1SignatureEntries = new ArrayList<ApkSignerEngine.OutputJarSignatureRequest.JarEntry>(addV1SignatureRequest.getAdditionalJarEntries());
        for (int i2 = 0; i2 < v1SignatureEntries.size(); ++i2) {
            ApkSignerEngine.OutputJarSignatureRequest.JarEntry entry = (ApkSignerEngine.OutputJarSignatureRequest.JarEntry)v1SignatureEntries.get(i2);
            name = entry.getName();
            if (!"META-INF/MANIFEST.MF".equals(name)) continue;
            if (i2 == 0) break;
            v1SignatureEntries.remove(i2);
            v1SignatureEntries.add(0, entry);
            break;
        }
        for (ApkSignerEngine.OutputJarSignatureRequest.JarEntry entry : v1SignatureEntries) {
            name = entry.getName();
            byte[] data = entry.getData();
            this.zFile.add(name, new ByteArrayInputStream(data));
        }
        addV1SignatureRequest.done();
    }

    private void onOutputZipEntriesWritten() throws IOException {
        ApkSignerEngine.OutputApkSigningBlockRequest addV2SignatureRequest;
        byte[] apkSigningBlock;
        if (!this.dirty) {
            return;
        }
        byte[] centralDirBytes = this.zFile.getCentralDirectoryBytes();
        byte[] eocdBytes = this.zFile.getEocdBytes();
        if (this.cachedApkSigningBlock != null) {
            apkSigningBlock = this.cachedApkSigningBlock;
            addV2SignatureRequest = null;
        } else {
            DataSource centralDir = DataSources.asDataSource(ByteBuffer.wrap(centralDirBytes));
            DataSource eocd = DataSources.asDataSource(ByteBuffer.wrap(eocdBytes));
            long zipEntriesSizeBytes = this.zFile.getCentralDirectoryOffset() - this.zFile.getExtraDirectoryOffset();
            ZFileDataSource zipEntries = new ZFileDataSource(this.zFile, 0L, zipEntriesSizeBytes);
            try {
                addV2SignatureRequest = this.signer.outputZipSections(zipEntries, centralDir, eocd);
            }
            catch (ApkFormatException | IOException | InvalidKeyException | NoSuchAlgorithmException | SignatureException e2) {
                throw new IOException("Failed to generate v2 signature", e2);
            }
            this.cachedApkSigningBlock = apkSigningBlock = addV2SignatureRequest != null ? addV2SignatureRequest.getApkSigningBlock() : new byte[]{};
        }
        this.zFile.directWrite(this.zFile.getCentralDirectoryOffset() - this.zFile.getExtraDirectoryOffset(), apkSigningBlock);
        this.zFile.setExtraDirectoryOffset(apkSigningBlock.length);
        if (addV2SignatureRequest != null) {
            addV2SignatureRequest.done();
        }
    }

    private void onOutputClosed() {
        if (!this.dirty) {
            return;
        }
        this.signer.outputDone();
        this.dirty = false;
    }

    private void setDirty() {
        this.dirty = true;
        this.cachedApkSigningBlock = null;
    }
}


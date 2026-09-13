/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig;

import com.android.apksig.ApkSignerEngine;
import com.android.apksig.DefaultApkSignerEngine;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkSigningBlockNotFoundException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.apk.MinSdkVersionException;
import com.android.apksig.internal.util.ByteBufferDataSource;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.EocdRecord;
import com.android.apksig.internal.zip.LocalFileRecord;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSinks;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import com.android.apksig.util.ReadableDataSink;
import com.android.apksig.zip.ZipFormatException;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ApkSigner {
    private static final short ALIGNMENT_ZIP_EXTRA_DATA_FIELD_HEADER_ID = -9931;
    private static final short ALIGNMENT_ZIP_EXTRA_DATA_FIELD_MIN_SIZE_BYTES = 6;
    private static final short ANDROID_COMMON_PAGE_ALIGNMENT_BYTES = 4096;
    private static final String ANDROID_MANIFEST_ZIP_ENTRY_NAME = "AndroidManifest.xml";
    private final List<SignerConfig> mSignerConfigs;
    private final Integer mMinSdkVersion;
    private final boolean mV1SigningEnabled;
    private final boolean mV2SigningEnabled;
    private final boolean mDebuggableApkPermitted;
    private final boolean mOtherSignersSignaturesPreserved;
    private final String mCreatedBy;
    private final ApkSignerEngine mSignerEngine;
    private final File mInputApkFile;
    private final DataSource mInputApkDataSource;
    private final File mOutputApkFile;
    private final DataSink mOutputApkDataSink;
    private final DataSource mOutputApkDataSource;

    private ApkSigner(List<SignerConfig> signerConfigs, Integer minSdkVersion, boolean v1SigningEnabled, boolean v2SigningEnabled, boolean debuggableApkPermitted, boolean otherSignersSignaturesPreserved, String createdBy, ApkSignerEngine signerEngine, File inputApkFile, DataSource inputApkDataSource, File outputApkFile, DataSink outputApkDataSink, DataSource outputApkDataSource) {
        this.mSignerConfigs = signerConfigs;
        this.mMinSdkVersion = minSdkVersion;
        this.mV1SigningEnabled = v1SigningEnabled;
        this.mV2SigningEnabled = v2SigningEnabled;
        this.mDebuggableApkPermitted = debuggableApkPermitted;
        this.mOtherSignersSignaturesPreserved = otherSignersSignaturesPreserved;
        this.mCreatedBy = createdBy;
        this.mSignerEngine = signerEngine;
        this.mInputApkFile = inputApkFile;
        this.mInputApkDataSource = inputApkDataSource;
        this.mOutputApkFile = outputApkFile;
        this.mOutputApkDataSink = outputApkDataSink;
        this.mOutputApkDataSource = outputApkDataSource;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void sign() throws IOException, ApkFormatException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, IllegalStateException {
        try (Closeable in = null;){
            DataSource inputApk;
            if (this.mInputApkDataSource != null) {
                inputApk = this.mInputApkDataSource;
            } else if (this.mInputApkFile != null) {
                RandomAccessFile inputFile = new RandomAccessFile(this.mInputApkFile, "r");
                in = inputFile;
                inputApk = DataSources.asDataSource(inputFile);
            } else {
                throw new IllegalStateException("Input APK not specified");
            }
            try (Closeable out = null;){
                DataSource outputApkIn;
                DataSink outputApkOut;
                if (this.mOutputApkDataSink != null) {
                    outputApkOut = this.mOutputApkDataSink;
                    outputApkIn = this.mOutputApkDataSource;
                } else if (this.mOutputApkFile != null) {
                    RandomAccessFile outputFile = new RandomAccessFile(this.mOutputApkFile, "rw");
                    out = outputFile;
                    outputFile.setLength(0L);
                    outputApkOut = DataSinks.asDataSink(outputFile);
                    outputApkIn = DataSources.asDataSource(outputFile);
                } else {
                    throw new IllegalStateException("Output APK not specified");
                }
                this.sign(inputApk, outputApkOut, outputApkIn);
            }
        }
    }

    private void sign(DataSource inputApk, DataSink outputApkOut, DataSource outputApkIn) throws IOException, ApkFormatException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ApkSignerEngine signerEngine;
        ApkUtils.ZipSections inputZipSections;
        try {
            inputZipSections = ApkUtils.findZipSections(inputApk);
        }
        catch (ZipFormatException e2) {
            throw new ApkFormatException("Malformed APK: not a ZIP archive", e2);
        }
        long inputApkSigningBlockOffset = -1L;
        DataSource inputApkSigningBlock = null;
        try {
            ApkUtils.ApkSigningBlock apkSigningBlockInfo = ApkUtils.findApkSigningBlock(inputApk, inputZipSections);
            inputApkSigningBlockOffset = apkSigningBlockInfo.getStartOffset();
            inputApkSigningBlock = apkSigningBlockInfo.getContents();
        }
        catch (ApkSigningBlockNotFoundException apkSigningBlockInfo) {
            // empty catch block
        }
        DataSource inputApkLfhSection = inputApk.slice(0L, inputApkSigningBlockOffset != -1L ? inputApkSigningBlockOffset : inputZipSections.getZipCentralDirectoryOffset());
        ByteBuffer inputCd = ApkSigner.getZipCentralDirectory(inputApk, inputZipSections);
        List<CentralDirectoryRecord> inputCdRecords = ApkSigner.parseZipCentralDirectory(inputCd, inputZipSections);
        if (this.mSignerEngine != null) {
            signerEngine = this.mSignerEngine;
        } else {
            int minSdkVersion = this.mMinSdkVersion != null ? this.mMinSdkVersion : ApkSigner.getMinSdkVersionFromApk(inputCdRecords, inputApkLfhSection);
            ArrayList<DefaultApkSignerEngine.SignerConfig> engineSignerConfigs = new ArrayList<DefaultApkSignerEngine.SignerConfig>(this.mSignerConfigs.size());
            for (SignerConfig signerConfig : this.mSignerConfigs) {
                engineSignerConfigs.add(new DefaultApkSignerEngine.SignerConfig.Builder(signerConfig.getName(), signerConfig.getPrivateKey(), signerConfig.getCertificates()).build());
            }
            DefaultApkSignerEngine.Builder signerEngineBuilder = new DefaultApkSignerEngine.Builder(engineSignerConfigs, minSdkVersion).setV1SigningEnabled(this.mV1SigningEnabled).setV2SigningEnabled(this.mV2SigningEnabled).setDebuggableApkPermitted(this.mDebuggableApkPermitted).setOtherSignersSignaturesPreserved(this.mOtherSignersSignaturesPreserved);
            if (this.mCreatedBy != null) {
                signerEngineBuilder.setCreatedBy(this.mCreatedBy);
            }
            signerEngine = signerEngineBuilder.build();
        }
        if (inputApkSigningBlock != null) {
            signerEngine.inputApkSigningBlock(inputApkSigningBlock);
        }
        ArrayList<CentralDirectoryRecord> inputCdRecordsSortedByLfhOffset = new ArrayList<CentralDirectoryRecord>(inputCdRecords);
        Collections.sort(inputCdRecordsSortedByLfhOffset, CentralDirectoryRecord.BY_LOCAL_FILE_HEADER_OFFSET_COMPARATOR);
        int lastModifiedDateForNewEntries = -1;
        int lastModifiedTimeForNewEntries = -1;
        long inputOffset = 0L;
        long outputOffset = 0L;
        HashMap<String, CentralDirectoryRecord> outputCdRecordsByName = new HashMap<String, CentralDirectoryRecord>(inputCdRecords.size());
        for (CentralDirectoryRecord inputCdRecord : inputCdRecordsSortedByLfhOffset) {
            LocalFileRecord localFileRecord;
            boolean shouldOutput;
            String entryName = inputCdRecord.getName();
            ApkSignerEngine.InputJarEntryInstructions entryInstructions = signerEngine.inputJarEntry(entryName);
            switch (entryInstructions.getOutputPolicy()) {
                case OUTPUT: {
                    shouldOutput = true;
                    break;
                }
                case OUTPUT_BY_ENGINE: 
                case SKIP: {
                    shouldOutput = false;
                    break;
                }
                default: {
                    throw new RuntimeException("Unknown output policy: " + (Object)((Object)entryInstructions.getOutputPolicy()));
                }
            }
            long inputLocalFileHeaderStartOffset = inputCdRecord.getLocalFileHeaderOffset();
            if (inputLocalFileHeaderStartOffset > inputOffset) {
                long l2 = inputLocalFileHeaderStartOffset - inputOffset;
                inputApkLfhSection.feed(inputOffset, l2, outputApkOut);
                outputOffset += l2;
                inputOffset = inputLocalFileHeaderStartOffset;
            }
            try {
                localFileRecord = LocalFileRecord.getRecord(inputApkLfhSection, inputCdRecord, inputApkLfhSection.size());
            }
            catch (ZipFormatException e3) {
                throw new ApkFormatException("Malformed ZIP entry: " + inputCdRecord.getName(), e3);
            }
            inputOffset += localFileRecord.getSize();
            ApkSignerEngine.InspectJarEntryRequest inspectEntryRequest = entryInstructions.getInspectJarEntryRequest();
            if (inspectEntryRequest != null) {
                ApkSigner.fulfillInspectInputJarEntryRequest(inputApkLfhSection, localFileRecord, inspectEntryRequest);
            }
            if (!shouldOutput) continue;
            int lastModifiedDate = inputCdRecord.getLastModificationDate();
            int lastModifiedTime = inputCdRecord.getLastModificationTime();
            if (lastModifiedDateForNewEntries == -1 || lastModifiedDate > lastModifiedDateForNewEntries || lastModifiedDate == lastModifiedDateForNewEntries && lastModifiedTime > lastModifiedTimeForNewEntries) {
                lastModifiedDateForNewEntries = lastModifiedDate;
                lastModifiedTimeForNewEntries = lastModifiedTime;
            }
            if ((inspectEntryRequest = signerEngine.outputJarEntry(entryName)) != null) {
                ApkSigner.fulfillInspectInputJarEntryRequest(inputApkLfhSection, localFileRecord, inspectEntryRequest);
            }
            long outputLocalFileHeaderOffset = outputOffset;
            long outputLocalFileRecordSize = ApkSigner.outputInputJarEntryLfhRecordPreservingDataAlignment(inputApkLfhSection, localFileRecord, outputApkOut, outputLocalFileHeaderOffset);
            outputOffset += outputLocalFileRecordSize;
            CentralDirectoryRecord outputCdRecord = outputLocalFileHeaderOffset == localFileRecord.getStartOffsetInArchive() ? inputCdRecord : inputCdRecord.createWithModifiedLocalFileHeaderOffset(outputLocalFileHeaderOffset);
            outputCdRecordsByName.put(entryName, outputCdRecord);
        }
        long inputLfhSectionSize = inputApkLfhSection.size();
        if (inputOffset < inputLfhSectionSize) {
            long chunkSize = inputLfhSectionSize - inputOffset;
            inputApkLfhSection.feed(inputOffset, chunkSize, outputApkOut);
            outputOffset += chunkSize;
            inputOffset = inputLfhSectionSize;
        }
        ArrayList<CentralDirectoryRecord> outputCdRecords = new ArrayList<CentralDirectoryRecord>(inputCdRecords.size() + 10);
        for (CentralDirectoryRecord inputCdRecord : inputCdRecords) {
            String entryName = inputCdRecord.getName();
            CentralDirectoryRecord outputCdRecord = (CentralDirectoryRecord)outputCdRecordsByName.get(entryName);
            if (outputCdRecord == null) continue;
            outputCdRecords.add(outputCdRecord);
        }
        ApkSignerEngine.OutputJarSignatureRequest outputJarSignatureRequest = signerEngine.outputJarEntries();
        if (outputJarSignatureRequest != null) {
            if (lastModifiedDateForNewEntries == -1) {
                lastModifiedDateForNewEntries = 14881;
                lastModifiedTimeForNewEntries = 0;
            }
            for (ApkSignerEngine.OutputJarSignatureRequest.JarEntry entry : outputJarSignatureRequest.getAdditionalJarEntries()) {
                String entryName = entry.getName();
                byte[] byArray = entry.getData();
                ZipUtils.DeflateResult deflateResult = ZipUtils.deflate(ByteBuffer.wrap(byArray));
                byte[] compressedData = deflateResult.output;
                long uncompressedDataCrc32 = deflateResult.inputCrc32;
                ApkSignerEngine.InspectJarEntryRequest inspectEntryRequest = signerEngine.outputJarEntry(entryName);
                if (inspectEntryRequest != null) {
                    inspectEntryRequest.getDataSink().consume(byArray, 0, byArray.length);
                    inspectEntryRequest.done();
                }
                long localFileHeaderOffset = outputOffset;
                outputOffset += LocalFileRecord.outputRecordWithDeflateCompressedData(entryName, lastModifiedTimeForNewEntries, lastModifiedDateForNewEntries, compressedData, uncompressedDataCrc32, byArray.length, outputApkOut);
                outputCdRecords.add(CentralDirectoryRecord.createWithDeflateCompressedData(entryName, lastModifiedTimeForNewEntries, lastModifiedDateForNewEntries, uncompressedDataCrc32, compressedData.length, byArray.length, localFileHeaderOffset));
            }
            outputJarSignatureRequest.done();
        }
        long outputCentralDirSizeBytes = 0L;
        for (CentralDirectoryRecord centralDirectoryRecord : outputCdRecords) {
            outputCentralDirSizeBytes += (long)centralDirectoryRecord.getSize();
        }
        if (outputCentralDirSizeBytes > Integer.MAX_VALUE) {
            throw new IOException("Output ZIP Central Directory too large: " + outputCentralDirSizeBytes + " bytes");
        }
        ByteBuffer outputCentralDir = ByteBuffer.allocate((int)outputCentralDirSizeBytes);
        for (CentralDirectoryRecord record : outputCdRecords) {
            record.copyTo(outputCentralDir);
        }
        outputCentralDir.flip();
        ByteBufferDataSource byteBufferDataSource = new ByteBufferDataSource(outputCentralDir);
        long outputCentralDirStartOffset = outputOffset;
        int outputCentralDirRecordCount = outputCdRecords.size();
        ByteBuffer outputEocd = EocdRecord.createWithModifiedCentralDirectoryInfo(inputZipSections.getZipEndOfCentralDirectory(), outputCentralDirRecordCount, byteBufferDataSource.size(), outputCentralDirStartOffset);
        ApkSignerEngine.OutputApkSigningBlockRequest2 outputApkSigningBlockRequest = signerEngine.outputZipSections2(outputApkIn, byteBufferDataSource, DataSources.asDataSource(outputEocd));
        if (outputApkSigningBlockRequest != null) {
            int padding = outputApkSigningBlockRequest.getPaddingSizeBeforeApkSigningBlock();
            outputApkOut.consume(ByteBuffer.allocate(padding));
            byte[] outputApkSigningBlock = outputApkSigningBlockRequest.getApkSigningBlock();
            outputApkOut.consume(outputApkSigningBlock, 0, outputApkSigningBlock.length);
            ZipUtils.setZipEocdCentralDirectoryOffset(outputEocd, outputCentralDirStartOffset + (long)padding + (long)outputApkSigningBlock.length);
            outputApkSigningBlockRequest.done();
        }
        byteBufferDataSource.feed(0L, byteBufferDataSource.size(), outputApkOut);
        outputApkOut.consume(outputEocd);
        signerEngine.outputDone();
    }

    private static void fulfillInspectInputJarEntryRequest(DataSource lfhSection, LocalFileRecord localFileRecord, ApkSignerEngine.InspectJarEntryRequest inspectEntryRequest) throws IOException, ApkFormatException {
        try {
            localFileRecord.outputUncompressedData(lfhSection, inspectEntryRequest.getDataSink());
        }
        catch (ZipFormatException e2) {
            throw new ApkFormatException("Malformed ZIP entry: " + localFileRecord.getName(), e2);
        }
        inspectEntryRequest.done();
    }

    private static long outputInputJarEntryLfhRecordPreservingDataAlignment(DataSource inputLfhSection, LocalFileRecord inputRecord, DataSink outputLfhSection, long outputOffset) throws IOException {
        long inputOffset = inputRecord.getStartOffsetInArchive();
        if (inputOffset == outputOffset) {
            return inputRecord.outputRecord(inputLfhSection, outputLfhSection);
        }
        int dataAlignmentMultiple = ApkSigner.getInputJarEntryDataAlignmentMultiple(inputRecord);
        if (dataAlignmentMultiple <= 1 || inputOffset % (long)dataAlignmentMultiple == outputOffset % (long)dataAlignmentMultiple) {
            return inputRecord.outputRecord(inputLfhSection, outputLfhSection);
        }
        long inputDataStartOffset = inputOffset + (long)inputRecord.getDataStartOffsetInRecord();
        if (inputDataStartOffset % (long)dataAlignmentMultiple != 0L) {
            return inputRecord.outputRecord(inputLfhSection, outputLfhSection);
        }
        ByteBuffer aligningExtra = ApkSigner.createExtraFieldToAlignData(inputRecord.getExtra(), outputOffset + (long)inputRecord.getExtraFieldStartOffsetInsideRecord(), dataAlignmentMultiple);
        return inputRecord.outputRecordWithModifiedExtra(inputLfhSection, aligningExtra, outputLfhSection);
    }

    private static int getInputJarEntryDataAlignmentMultiple(LocalFileRecord entry) {
        if (entry.isDataCompressed()) {
            return 1;
        }
        ByteBuffer extra = entry.getExtra();
        if (extra.hasRemaining()) {
            extra.order(ByteOrder.LITTLE_ENDIAN);
            while (extra.remaining() >= 4) {
                short headerId = extra.getShort();
                int dataSize = ZipUtils.getUnsignedInt16(extra);
                if (dataSize > extra.remaining()) break;
                if (headerId != -9931) {
                    extra.position(extra.position() + dataSize);
                    continue;
                }
                if (dataSize < 2) break;
                return ZipUtils.getUnsignedInt16(extra);
            }
        }
        return entry.getName().endsWith(".so") ? 4096 : 4;
    }

    private static ByteBuffer createExtraFieldToAlignData(ByteBuffer original, long extraStartOffset, int dataAlignmentMultiple) {
        if (dataAlignmentMultiple <= 1) {
            return original;
        }
        ByteBuffer result = ByteBuffer.allocate(original.remaining() + 5 + dataAlignmentMultiple);
        result.order(ByteOrder.LITTLE_ENDIAN);
        while (original.remaining() >= 4) {
            short headerId = original.getShort();
            int dataSize = ZipUtils.getUnsignedInt16(original);
            if (dataSize > original.remaining()) break;
            if (headerId == 0 && dataSize == 0 || headerId == -9931) {
                original.position(original.position() + dataSize);
                continue;
            }
            original.position(original.position() - 4);
            int originalLimit = original.limit();
            original.limit(original.position() + 4 + dataSize);
            result.put(original);
            original.limit(originalLimit);
        }
        long dataMinStartOffset = extraStartOffset + (long)result.position() + 6L;
        int paddingSizeBytes = (dataAlignmentMultiple - (int)(dataMinStartOffset % (long)dataAlignmentMultiple)) % dataAlignmentMultiple;
        result.putShort((short)-9931);
        ZipUtils.putUnsignedInt16(result, 2 + paddingSizeBytes);
        ZipUtils.putUnsignedInt16(result, dataAlignmentMultiple);
        result.position(result.position() + paddingSizeBytes);
        result.flip();
        return result;
    }

    private static ByteBuffer getZipCentralDirectory(DataSource apk, ApkUtils.ZipSections apkSections) throws IOException, ApkFormatException {
        long cdSizeBytes = apkSections.getZipCentralDirectorySizeBytes();
        if (cdSizeBytes > Integer.MAX_VALUE) {
            throw new ApkFormatException("ZIP Central Directory too large: " + cdSizeBytes);
        }
        long cdOffset = apkSections.getZipCentralDirectoryOffset();
        ByteBuffer cd2 = apk.getByteBuffer(cdOffset, (int)cdSizeBytes);
        cd2.order(ByteOrder.LITTLE_ENDIAN);
        return cd2;
    }

    private static List<CentralDirectoryRecord> parseZipCentralDirectory(ByteBuffer cd2, ApkUtils.ZipSections apkSections) throws ApkFormatException {
        long cdOffset = apkSections.getZipCentralDirectoryOffset();
        int expectedCdRecordCount = apkSections.getZipCentralDirectoryRecordCount();
        ArrayList<CentralDirectoryRecord> cdRecords = new ArrayList<CentralDirectoryRecord>(expectedCdRecordCount);
        HashSet<String> entryNames = new HashSet<String>(expectedCdRecordCount);
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
            if (!entryNames.add(entryName)) {
                throw new ApkFormatException("Multiple ZIP entries with the same name: " + entryName);
            }
            cdRecords.add(cdRecord);
        }
        if (cd2.hasRemaining()) {
            throw new ApkFormatException("Unused space at the end of ZIP Central Directory: " + cd2.remaining() + " bytes starting at file offset " + (cdOffset + (long)cd2.position()));
        }
        return cdRecords;
    }

    static ByteBuffer getAndroidManifestFromApk(List<CentralDirectoryRecord> cdRecords, DataSource lhfSection) throws IOException, ApkFormatException, ZipFormatException {
        CentralDirectoryRecord androidManifestCdRecord = null;
        for (CentralDirectoryRecord cdRecord : cdRecords) {
            if (!ANDROID_MANIFEST_ZIP_ENTRY_NAME.equals(cdRecord.getName())) continue;
            androidManifestCdRecord = cdRecord;
            break;
        }
        if (androidManifestCdRecord == null) {
            throw new ApkFormatException("Missing AndroidManifest.xml");
        }
        return ByteBuffer.wrap(LocalFileRecord.getUncompressedData(lhfSection, androidManifestCdRecord, lhfSection.size()));
    }

    private static int getMinSdkVersionFromApk(List<CentralDirectoryRecord> cdRecords, DataSource lhfSection) throws IOException, MinSdkVersionException {
        ByteBuffer androidManifest;
        try {
            androidManifest = ApkSigner.getAndroidManifestFromApk(cdRecords, lhfSection);
        }
        catch (ApkFormatException | ZipFormatException e2) {
            throw new MinSdkVersionException("Failed to determine APK's minimum supported Android platform version", e2);
        }
        return ApkUtils.getMinSdkVersionFromBinaryAndroidManifest(androidManifest);
    }

    public static class Builder {
        private final List<SignerConfig> mSignerConfigs;
        private boolean mV1SigningEnabled = true;
        private boolean mV2SigningEnabled = true;
        private boolean mDebuggableApkPermitted = true;
        private boolean mOtherSignersSignaturesPreserved;
        private String mCreatedBy;
        private Integer mMinSdkVersion;
        private final ApkSignerEngine mSignerEngine;
        private File mInputApkFile;
        private DataSource mInputApkDataSource;
        private File mOutputApkFile;
        private DataSink mOutputApkDataSink;
        private DataSource mOutputApkDataSource;

        public Builder(List<SignerConfig> signerConfigs) {
            if (signerConfigs.isEmpty()) {
                throw new IllegalArgumentException("At least one signer config must be provided");
            }
            this.mSignerConfigs = new ArrayList<SignerConfig>(signerConfigs);
            this.mSignerEngine = null;
        }

        public Builder(ApkSignerEngine signerEngine) {
            if (signerEngine == null) {
                throw new NullPointerException("signerEngine == null");
            }
            this.mSignerEngine = signerEngine;
            this.mSignerConfigs = null;
        }

        public Builder setInputApk(File inputApk) {
            if (inputApk == null) {
                throw new NullPointerException("inputApk == null");
            }
            this.mInputApkFile = inputApk;
            this.mInputApkDataSource = null;
            return this;
        }

        public Builder setInputApk(DataSource inputApk) {
            if (inputApk == null) {
                throw new NullPointerException("inputApk == null");
            }
            this.mInputApkDataSource = inputApk;
            this.mInputApkFile = null;
            return this;
        }

        public Builder setOutputApk(File outputApk) {
            if (outputApk == null) {
                throw new NullPointerException("outputApk == null");
            }
            this.mOutputApkFile = outputApk;
            this.mOutputApkDataSink = null;
            this.mOutputApkDataSource = null;
            return this;
        }

        public Builder setOutputApk(ReadableDataSink outputApk) {
            if (outputApk == null) {
                throw new NullPointerException("outputApk == null");
            }
            return this.setOutputApk(outputApk, outputApk);
        }

        public Builder setOutputApk(DataSink outputApkOut, DataSource outputApkIn) {
            if (outputApkOut == null) {
                throw new NullPointerException("outputApkOut == null");
            }
            if (outputApkIn == null) {
                throw new NullPointerException("outputApkIn == null");
            }
            this.mOutputApkFile = null;
            this.mOutputApkDataSink = outputApkOut;
            this.mOutputApkDataSource = outputApkIn;
            return this;
        }

        public Builder setMinSdkVersion(int minSdkVersion) {
            this.checkInitializedWithoutEngine();
            this.mMinSdkVersion = minSdkVersion;
            return this;
        }

        public Builder setV1SigningEnabled(boolean enabled) {
            this.checkInitializedWithoutEngine();
            this.mV1SigningEnabled = enabled;
            return this;
        }

        public Builder setV2SigningEnabled(boolean enabled) {
            this.checkInitializedWithoutEngine();
            this.mV2SigningEnabled = enabled;
            return this;
        }

        public Builder setDebuggableApkPermitted(boolean permitted) {
            this.checkInitializedWithoutEngine();
            this.mDebuggableApkPermitted = permitted;
            return this;
        }

        public Builder setOtherSignersSignaturesPreserved(boolean preserved) {
            this.checkInitializedWithoutEngine();
            this.mOtherSignersSignaturesPreserved = preserved;
            return this;
        }

        public Builder setCreatedBy(String createdBy) {
            this.checkInitializedWithoutEngine();
            if (createdBy == null) {
                throw new NullPointerException();
            }
            this.mCreatedBy = createdBy;
            return this;
        }

        private void checkInitializedWithoutEngine() {
            if (this.mSignerEngine != null) {
                throw new IllegalStateException("Operation is not available when builder initialized with an engine");
            }
        }

        public ApkSigner build() {
            return new ApkSigner(this.mSignerConfigs, this.mMinSdkVersion, this.mV1SigningEnabled, this.mV2SigningEnabled, this.mDebuggableApkPermitted, this.mOtherSignersSignaturesPreserved, this.mCreatedBy, this.mSignerEngine, this.mInputApkFile, this.mInputApkDataSource, this.mOutputApkFile, this.mOutputApkDataSink, this.mOutputApkDataSource);
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
}


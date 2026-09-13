/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.bytestorage.ByteStorage;
import com.android.tools.build.apkzlib.bytestorage.CloseableByteSourceFromOutputStreamBuilder;
import com.android.tools.build.apkzlib.zip.CentralDirectoryHeader;
import com.android.tools.build.apkzlib.zip.CentralDirectoryHeaderCompressInfo;
import com.android.tools.build.apkzlib.zip.CompressionMethod;
import com.android.tools.build.apkzlib.zip.DataDescriptorType;
import com.android.tools.build.apkzlib.zip.EncodeUtils;
import com.android.tools.build.apkzlib.zip.ExtraField;
import com.android.tools.build.apkzlib.zip.InflaterByteSource;
import com.android.tools.build.apkzlib.zip.ProcessedAndRawByteSources;
import com.android.tools.build.apkzlib.zip.StoredEntryType;
import com.android.tools.build.apkzlib.zip.VerifyLog;
import com.android.tools.build.apkzlib.zip.ZFile;
import com.android.tools.build.apkzlib.zip.ZipField;
import com.android.tools.build.apkzlib.zip.ZipFieldInvariant;
import com.android.tools.build.apkzlib.zip.ZipFieldInvariantNonNegative;
import com.android.tools.build.apkzlib.zip.utils.CloseableByteSource;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StoredEntry {
    static final Comparator<StoredEntry> COMPARE_BY_NAME = (o12, o22) -> {
        if (o12 == null && o22 == null) {
            return 0;
        }
        if (o12 == null) {
            return -1;
        }
        if (o22 == null) {
            return 1;
        }
        String name1 = o12.getCentralDirectoryHeader().getName();
        String name2 = o22.getCentralDirectoryHeader().getName();
        return name1.compareTo(name2);
    };
    private static final int DATA_DESC_SIGNATURE = 134695760;
    private static final ZipField.F4 F_LOCAL_SIGNATURE = new ZipField.F4(0, 67324752L, "Signature");
    @VisibleForTesting
    static final ZipField.F2 F_VERSION_EXTRACT = new ZipField.F2(F_LOCAL_SIGNATURE.endOffset(), "Version to extract", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_GP_BIT = new ZipField.F2(F_VERSION_EXTRACT.endOffset(), "GP bit flag", new ZipFieldInvariant[0]);
    private static final ZipField.F2 F_METHOD = new ZipField.F2(F_GP_BIT.endOffset(), "Compression method", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_LAST_MOD_TIME = new ZipField.F2(F_METHOD.endOffset(), "Last modification time", new ZipFieldInvariant[0]);
    private static final ZipField.F2 F_LAST_MOD_DATE = new ZipField.F2(F_LAST_MOD_TIME.endOffset(), "Last modification date", new ZipFieldInvariant[0]);
    private static final ZipField.F4 F_CRC32 = new ZipField.F4(F_LAST_MOD_DATE.endOffset(), "CRC32", new ZipFieldInvariant[0]);
    private static final ZipField.F4 F_COMPRESSED_SIZE = new ZipField.F4(F_CRC32.endOffset(), "Compressed size", new ZipFieldInvariantNonNegative());
    private static final ZipField.F4 F_UNCOMPRESSED_SIZE = new ZipField.F4(F_COMPRESSED_SIZE.endOffset(), "Uncompressed size", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_FILE_NAME_LENGTH = new ZipField.F2(F_UNCOMPRESSED_SIZE.endOffset(), "@File name length", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_EXTRA_LENGTH = new ZipField.F2(F_FILE_NAME_LENGTH.endOffset(), "Extra length", new ZipFieldInvariantNonNegative());
    static final int FIXED_LOCAL_FILE_HEADER_SIZE = F_EXTRA_LENGTH.endOffset();
    private final StoredEntryType type;
    private final CentralDirectoryHeader cdh;
    private final ZFile file;
    private boolean deleted;
    @Nonnull
    private ExtraField localExtra;
    private DataDescriptorType dataDescriptorType;
    private ProcessedAndRawByteSources source;
    private final VerifyLog verifyLog;
    private final ByteStorage storage;

    StoredEntry(CentralDirectoryHeader header, ZFile file, @Nullable ProcessedAndRawByteSources source, ByteStorage storage) throws IOException {
        this.cdh = header;
        this.file = file;
        this.deleted = false;
        this.verifyLog = file.makeVerifyLog();
        this.storage = storage;
        if (this.cdh.getOffset() >= 0L) {
            this.readLocalHeader();
            Preconditions.checkArgument(source == null, "Source was defined but contents already exist on file.");
            this.source = this.createSourceFromZip(this.cdh.getOffset());
        } else {
            this.localExtra = this.cdh.getExtraField();
            Preconditions.checkNotNull(source, "Source was not defined, but contents are not on file.");
            this.source = source;
        }
        if (this.cdh.getName().endsWith(Character.toString('/'))) {
            this.type = StoredEntryType.DIRECTORY;
            this.verifyLog.verify(this.source.getProcessedByteSource().isEmpty(), "Directory source is not empty.", new Object[0]);
            this.verifyLog.verify(this.cdh.getCrc32() == 0L, "Directory has CRC32 = %s.", this.cdh.getCrc32());
            this.verifyLog.verify(this.cdh.getUncompressedSize() == 0L, "Directory has uncompressed size = %s.", this.cdh.getUncompressedSize());
            long compressedSize = this.cdh.getCompressionInfoWithWait().getCompressedSize();
            this.verifyLog.verify(compressedSize == 0L || compressedSize == 2L, "Directory has compressed size = %s.", compressedSize);
        } else {
            this.type = StoredEntryType.FILE;
        }
        this.dataDescriptorType = DataDescriptorType.NO_DATA_DESCRIPTOR;
        if (header.getGpBit().isDeferredCrc()) {
            Verify.verify(header.getOffset() >= 0L, "Files that are not on disk cannot have the deferred CRC bit set.", new Object[0]);
            try {
                this.readDataDescriptorRecord();
            }
            catch (IOException e2) {
                throw new IOException("Failed to read data descriptor record.", e2);
            }
        }
    }

    public int getLocalHeaderSize() {
        Preconditions.checkState(!this.deleted, "deleted");
        return FIXED_LOCAL_FILE_HEADER_SIZE + this.cdh.getEncodedFileName().length + this.localExtra.size();
    }

    long getInFileSize() throws IOException {
        Preconditions.checkState(!this.deleted, "deleted");
        return this.cdh.getCompressionInfoWithWait().getCompressedSize() + (long)this.getLocalHeaderSize() + (long)this.dataDescriptorType.size;
    }

    public InputStream open() throws IOException {
        return this.source.getProcessedByteSource().openStream();
    }

    public byte[] read() throws IOException {
        try (BufferedInputStream is = new BufferedInputStream(this.open());){
            byte[] byArray = ByteStreams.toByteArray(is);
            return byArray;
        }
    }

    public int read(byte[] bytes) throws IOException {
        if ((long)bytes.length < this.getCentralDirectoryHeader().getUncompressedSize()) {
            throw new RuntimeException("Buffer to small while reading {}" + this.getCentralDirectoryHeader().getName());
        }
        try (BufferedInputStream is = new BufferedInputStream(this.open());){
            int n2 = ByteStreams.read(is, bytes, 0, bytes.length);
            return n2;
        }
    }

    public StoredEntryType getType() {
        Preconditions.checkState(!this.deleted, "deleted");
        return this.type;
    }

    public void delete() throws IOException {
        this.delete(true);
    }

    void delete(boolean notify) throws IOException {
        Preconditions.checkState(!this.deleted, "deleted");
        this.file.delete(this, notify);
        this.deleted = true;
        this.source.close();
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public CentralDirectoryHeader getCentralDirectoryHeader() {
        return this.cdh;
    }

    private void readLocalHeader() throws IOException {
        byte[] localHeader = new byte[FIXED_LOCAL_FILE_HEADER_SIZE];
        this.file.directFullyRead(this.cdh.getOffset(), localHeader);
        CentralDirectoryHeaderCompressInfo compressInfo = this.cdh.getCompressionInfoWithWait();
        ByteBuffer bytes = ByteBuffer.wrap(localHeader);
        F_LOCAL_SIGNATURE.verify(bytes);
        F_VERSION_EXTRACT.verify(bytes, compressInfo.getVersionExtract(), this.verifyLog);
        F_GP_BIT.verify(bytes, this.cdh.getGpBit().getValue(), this.verifyLog);
        F_METHOD.verify(bytes, compressInfo.getMethod().methodCode, this.verifyLog);
        if (this.file.areTimestampsIgnored()) {
            F_LAST_MOD_TIME.skip(bytes);
            F_LAST_MOD_DATE.skip(bytes);
        } else {
            F_LAST_MOD_TIME.verify(bytes, this.cdh.getLastModTime(), this.verifyLog);
            F_LAST_MOD_DATE.verify(bytes, this.cdh.getLastModDate(), this.verifyLog);
        }
        if (this.cdh.getGpBit().isDeferredCrc()) {
            F_CRC32.skip(bytes);
            F_COMPRESSED_SIZE.skip(bytes);
            F_UNCOMPRESSED_SIZE.skip(bytes);
        } else {
            F_CRC32.verify(bytes, this.cdh.getCrc32(), this.verifyLog);
            F_COMPRESSED_SIZE.verify(bytes, compressInfo.getCompressedSize(), this.verifyLog);
            F_UNCOMPRESSED_SIZE.verify(bytes, this.cdh.getUncompressedSize(), this.verifyLog);
        }
        F_FILE_NAME_LENGTH.verify(bytes, this.cdh.getEncodedFileName().length);
        long extraLength = F_EXTRA_LENGTH.read(bytes);
        long fileNameStart = this.cdh.getOffset() + (long)F_EXTRA_LENGTH.endOffset();
        byte[] fileNameData = new byte[this.cdh.getEncodedFileName().length];
        this.file.directFullyRead(fileNameStart, fileNameData);
        String fileName = EncodeUtils.decode(fileNameData, this.cdh.getGpBit());
        if (!fileName.equals(this.cdh.getName())) {
            this.verifyLog.log(String.format("Central directory reports file as being named '%s' but local headerreports file being named '%s'.", this.cdh.getName(), fileName));
        }
        long localExtraStart = fileNameStart + (long)this.cdh.getEncodedFileName().length;
        byte[] localExtraRaw = new byte[Ints.checkedCast(extraLength)];
        this.file.directFullyRead(localExtraStart, localExtraRaw);
        this.localExtra = new ExtraField(localExtraRaw);
        byte[] cdhExtraField = new byte[this.cdh.getExtraField().size()];
        this.cdh.getExtraField().write(ByteBuffer.wrap(cdhExtraField));
        if (!Arrays.equals(localExtraRaw, cdhExtraField)) {
            this.verifyLog.log(String.format("Central directory and local header extra fields for file '%s' do not match", fileName));
        }
    }

    private void readDataDescriptorRecord() throws IOException {
        CentralDirectoryHeaderCompressInfo compressInfo = this.cdh.getCompressionInfoWithWait();
        long ddStart = this.cdh.getOffset() + (long)FIXED_LOCAL_FILE_HEADER_SIZE + (long)this.cdh.getName().length() + (long)this.localExtra.size() + compressInfo.getCompressedSize();
        byte[] ddData = new byte[DataDescriptorType.DATA_DESCRIPTOR_WITH_SIGNATURE.size];
        this.file.directFullyRead(ddStart, ddData);
        ByteBuffer ddBytes = ByteBuffer.wrap(ddData);
        ZipField.F4 signatureField = new ZipField.F4(0, "Data descriptor signature", new ZipFieldInvariant[0]);
        int cpos = ddBytes.position();
        long sig = signatureField.read(ddBytes);
        if (sig == 134695760L) {
            this.dataDescriptorType = DataDescriptorType.DATA_DESCRIPTOR_WITH_SIGNATURE;
        } else {
            this.dataDescriptorType = DataDescriptorType.DATA_DESCRIPTOR_WITHOUT_SIGNATURE;
            ddBytes.position(cpos);
        }
        ZipField.F4 crc32Field = new ZipField.F4(0, "CRC32", new ZipFieldInvariant[0]);
        ZipField.F4 compressedField = new ZipField.F4(crc32Field.endOffset(), "Compressed size", new ZipFieldInvariant[0]);
        ZipField.F4 uncompressedField = new ZipField.F4(compressedField.endOffset(), "Uncompressed size", new ZipFieldInvariant[0]);
        crc32Field.verify(ddBytes, this.cdh.getCrc32(), this.verifyLog);
        compressedField.verify(ddBytes, compressInfo.getCompressedSize(), this.verifyLog);
        uncompressedField.verify(ddBytes, this.cdh.getUncompressedSize(), this.verifyLog);
    }

    private ProcessedAndRawByteSources createSourceFromZip(final long zipOffset) throws IOException {
        CentralDirectoryHeaderCompressInfo compressInfo;
        Preconditions.checkArgument(zipOffset >= 0L, "zipOffset < 0");
        try {
            compressInfo = this.cdh.getCompressionInfoWithWait();
        }
        catch (IOException e2) {
            throw new RuntimeException("IOException should never occur here because compression information should be immediately available if reading from zip.", e2);
        }
        CloseableByteSource rawContents = new CloseableByteSource(){

            @Override
            public long size() throws IOException {
                return compressInfo.getCompressedSize();
            }

            @Override
            public InputStream openStream() throws IOException {
                Preconditions.checkState(!StoredEntry.this.deleted, "deleted");
                long dataStart = zipOffset + (long)StoredEntry.this.getLocalHeaderSize();
                long dataEnd = dataStart + compressInfo.getCompressedSize();
                StoredEntry.this.file.openReadOnlyIfClosed();
                return StoredEntry.this.file.directOpen(dataStart, dataEnd);
            }

            @Override
            protected void innerClose() throws IOException {
            }
        };
        return this.createSourcesFromRawContents(rawContents);
    }

    private ProcessedAndRawByteSources createSourcesFromRawContents(CloseableByteSource rawContents) {
        CentralDirectoryHeaderCompressInfo compressInfo;
        try {
            compressInfo = this.cdh.getCompressionInfoWithWait();
        }
        catch (IOException e2) {
            throw new RuntimeException("IOException should never occur here because compression information should be immediately available if creating from raw contents.", e2);
        }
        CloseableByteSource contents = compressInfo.getMethod() == CompressionMethod.DEFLATE ? new InflaterByteSource(rawContents) : rawContents;
        return new ProcessedAndRawByteSources(contents, rawContents);
    }

    void replaceSourceFromZip(long zipFileOffset) throws IOException {
        Preconditions.checkArgument(zipFileOffset >= 0L, "zipFileOffset < 0");
        ProcessedAndRawByteSources oldSource = this.source;
        this.source = this.createSourceFromZip(zipFileOffset);
        this.cdh.setOffset(zipFileOffset);
        oldSource.close();
    }

    void loadSourceIntoMemory() throws IOException {
        if (this.cdh.getOffset() == -1L) {
            return;
        }
        CloseableByteSourceFromOutputStreamBuilder rawBuilder = this.storage.makeBuilder();
        try (InputStream input = this.source.getRawByteSource().openStream();){
            ByteStreams.copy(input, rawBuilder);
        }
        CloseableByteSource newRaw = rawBuilder.build();
        ProcessedAndRawByteSources newSources = this.createSourcesFromRawContents(newRaw);
        try (ProcessedAndRawByteSources oldSource = this.source;){
            this.source = newSources;
            this.cdh.setOffset(-1L);
        }
    }

    ProcessedAndRawByteSources getSource() {
        return this.source;
    }

    public DataDescriptorType getDataDescriptorType() {
        return this.dataDescriptorType;
    }

    boolean removeDataDescriptor() {
        if (this.dataDescriptorType == DataDescriptorType.NO_DATA_DESCRIPTOR) {
            return false;
        }
        this.dataDescriptorType = DataDescriptorType.NO_DATA_DESCRIPTOR;
        this.cdh.resetDeferredCrc();
        return true;
    }

    int toHeaderData(byte[] buffer) throws IOException {
        Preconditions.checkArgument(buffer.length >= F_EXTRA_LENGTH.endOffset() + this.cdh.getEncodedFileName().length + this.localExtra.size(), "Buffer should be at least the header size");
        ByteBuffer out = ByteBuffer.wrap(buffer);
        this.writeData(out);
        return out.position();
    }

    private void writeData(ByteBuffer out) throws IOException {
        CentralDirectoryHeaderCompressInfo compressInfo = this.cdh.getCompressionInfoWithWait();
        F_LOCAL_SIGNATURE.write(out);
        F_VERSION_EXTRACT.write(out, compressInfo.getVersionExtract());
        F_GP_BIT.write(out, this.cdh.getGpBit().getValue());
        F_METHOD.write(out, compressInfo.getMethod().methodCode);
        if (this.file.areTimestampsIgnored()) {
            F_LAST_MOD_TIME.write(out, 0L);
            F_LAST_MOD_DATE.write(out, 0L);
        } else {
            F_LAST_MOD_TIME.write(out, this.cdh.getLastModTime());
            F_LAST_MOD_DATE.write(out, this.cdh.getLastModDate());
        }
        F_CRC32.write(out, this.cdh.getCrc32());
        F_COMPRESSED_SIZE.write(out, compressInfo.getCompressedSize());
        F_UNCOMPRESSED_SIZE.write(out, this.cdh.getUncompressedSize());
        F_FILE_NAME_LENGTH.write(out, this.cdh.getEncodedFileName().length);
        F_EXTRA_LENGTH.write(out, this.localExtra.size());
        out.put(this.cdh.getEncodedFileName());
        this.localExtra.write(out);
    }

    public boolean realign() throws IOException {
        Preconditions.checkState(!this.deleted, "Entry has been deleted.");
        return this.file.realign(this);
    }

    @Nonnull
    public ExtraField getLocalExtra() {
        return this.localExtra;
    }

    public void setLocalExtra(@Nonnull ExtraField localExtra) throws IOException {
        boolean resized = this.setLocalExtraNoNotify(localExtra);
        this.file.localHeaderChanged(this, resized);
    }

    boolean setLocalExtraNoNotify(@Nonnull ExtraField localExtra) throws IOException {
        this.loadSourceIntoMemory();
        boolean sizeChanged = this.localExtra.size() != localExtra.size();
        this.localExtra = localExtra;
        this.cdh.setExtraField(localExtra);
        return sizeChanged;
    }

    public VerifyLog getVerifyLog() {
        return this.verifyLog;
    }
}


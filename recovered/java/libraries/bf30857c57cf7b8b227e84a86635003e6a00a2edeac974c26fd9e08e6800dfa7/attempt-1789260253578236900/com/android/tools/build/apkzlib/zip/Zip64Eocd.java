/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.utils.CachedSupplier;
import com.android.tools.build.apkzlib.utils.IOExceptionWrapper;
import com.android.tools.build.apkzlib.zip.Zip64ExtensibleDataSector;
import com.android.tools.build.apkzlib.zip.ZipField;
import com.android.tools.build.apkzlib.zip.ZipFieldInvariantMinValue;
import com.android.tools.build.apkzlib.zip.ZipFieldInvariantNonNegative;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Zip64Eocd {
    private static final int DEFAULT_VERSION_MADE_BY = 24;
    private static final int MIN_EOCD_SIZE = 44;
    private static final ZipField.F4 F_SIGNATURE = new ZipField.F4(0, 101075792L, "Zip64 EOCD signature");
    private static final ZipField.F8 F_EOCD_SIZE = new ZipField.F8(F_SIGNATURE.endOffset(), "Zip64 EOCD size", new ZipFieldInvariantMinValue(44L));
    private static final ZipField.F2 F_MADE_BY = new ZipField.F2(F_EOCD_SIZE.endOffset(), "Made by", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_VERSION_EXTRACT = new ZipField.F2(F_MADE_BY.endOffset(), "Version to extract", new ZipFieldInvariantMinValue(45L));
    private static final ZipField.F4 F_NUMBER_OF_DISK = new ZipField.F4(F_VERSION_EXTRACT.endOffset(), 0L, "Number of this disk");
    private static final ZipField.F4 F_DISK_CD_START = new ZipField.F4(F_NUMBER_OF_DISK.endOffset(), 0L, "Disk where CD starts");
    private static final ZipField.F8 F_RECORDS_DISK = new ZipField.F8(F_DISK_CD_START.endOffset(), "Record on disk count", new ZipFieldInvariantNonNegative());
    private static final ZipField.F8 F_RECORDS_TOTAL = new ZipField.F8(F_RECORDS_DISK.endOffset(), "Total records", new ZipFieldInvariantNonNegative());
    private static final ZipField.F8 F_CD_SIZE = new ZipField.F8(F_RECORDS_TOTAL.endOffset(), "Directory size", new ZipFieldInvariantNonNegative());
    private static final ZipField.F8 F_CD_OFFSET = new ZipField.F8(F_CD_SIZE.endOffset(), "Directory offset", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_V2_CD_COMPRESSION_METHOD = new ZipField.F2(F_CD_OFFSET.endOffset(), 0L, "Version 2: Directory Compression method");
    private static final ZipField.F8 F_V2_CD_COMPRESSED_SIZE = new ZipField.F8(F_V2_CD_COMPRESSION_METHOD.endOffset(), "Version 2: Directory Compressed Size", new ZipFieldInvariantNonNegative());
    private static final ZipField.F8 F_V2_CD_UNCOMPRESSED_SIZE = new ZipField.F8(F_V2_CD_COMPRESSED_SIZE.endOffset(), "Version 2: Directory Uncompressed Size", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_V2_CD_ENCRYPTION_ID = new ZipField.F2(F_V2_CD_UNCOMPRESSED_SIZE.endOffset(), 0L, "Version 2: Directory Encryption");
    private static final ZipField.F2 F_V2_CD_ENCRYPTION_KEY_LENGTH = new ZipField.F2(F_V2_CD_ENCRYPTION_ID.endOffset(), 0L, "Version 2: Directory Encryption key length");
    private static final ZipField.F2 F_V2_CD_ENCRYPTION_FLAGS = new ZipField.F2(F_V2_CD_ENCRYPTION_KEY_LENGTH.endOffset(), 0L, "Version 2: Directory Encryption Flags");
    private static final ZipField.F2 F_V2_HASH_ID = new ZipField.F2(F_V2_CD_ENCRYPTION_FLAGS.endOffset(), 0L, "Version 2: Hash algorithm ID");
    private static final ZipField.F2 F_V2_HASH_LENGTH = new ZipField.F2(F_V2_HASH_ID.endOffset(), 0L, "Version 2: Hash length");
    private final long madeBy;
    private final long versionToExtract;
    private final long totalRecords;
    private final long directoryOffset;
    private final long directorySize;
    private final Zip64ExtensibleDataSector extraFields;
    private final CachedSupplier<byte[]> byteSupplier;

    Zip64Eocd(long totalRecords, long directoryOffset, long directorySize, boolean useVersion2, Zip64ExtensibleDataSector dataSector) {
        this.madeBy = 24L;
        this.totalRecords = totalRecords;
        this.directorySize = directorySize;
        this.directoryOffset = directoryOffset;
        this.versionToExtract = useVersion2 ? 62L : 45L;
        this.extraFields = dataSector;
        this.byteSupplier = new CachedSupplier<byte[]>(this::computeByteRepresentation);
    }

    Zip64Eocd(ByteBuffer bytes) throws IOException {
        F_SIGNATURE.verify(bytes);
        long eocdSize = F_EOCD_SIZE.read(bytes);
        long madeBy = F_MADE_BY.read(bytes);
        long versionToExtract = F_VERSION_EXTRACT.read(bytes);
        F_NUMBER_OF_DISK.verify(bytes);
        F_DISK_CD_START.verify(bytes);
        long totalRecords1 = F_RECORDS_DISK.read(bytes);
        long totalRecords2 = F_RECORDS_TOTAL.read(bytes);
        long directorySize = F_CD_SIZE.read(bytes);
        long directoryOffset = F_CD_OFFSET.read(bytes);
        long sizeOfFixedFields = F_CD_OFFSET.endOffset();
        if (totalRecords1 != totalRecords2) {
            throw new IOException("Zip states records split in multiple disks, which is not supported");
        }
        if (versionToExtract >= 62L) {
            if (eocdSize < (long)(F_V2_HASH_LENGTH.endOffset() - F_EOCD_SIZE.endOffset())) {
                throw new IOException("Zip states the size of Zip64 EOCD is too small for version 2 format.");
            }
            F_V2_CD_COMPRESSION_METHOD.verify(bytes);
            long compressedSize = F_V2_CD_COMPRESSED_SIZE.read(bytes);
            long uncompressedSize = F_V2_CD_UNCOMPRESSED_SIZE.read(bytes);
            F_V2_CD_ENCRYPTION_ID.verify(bytes);
            F_V2_CD_ENCRYPTION_KEY_LENGTH.verify(bytes);
            F_V2_CD_ENCRYPTION_FLAGS.verify(bytes);
            F_V2_HASH_ID.verify(bytes);
            F_V2_HASH_LENGTH.verify(bytes);
            sizeOfFixedFields = F_V2_HASH_LENGTH.endOffset();
            if (compressedSize != uncompressedSize) {
                throw new IOException("Zip states Central Directory Compression is used, which is not supported");
            }
            directorySize = uncompressedSize;
        }
        this.madeBy = madeBy;
        this.versionToExtract = versionToExtract;
        this.totalRecords = totalRecords1;
        this.directorySize = directorySize;
        this.directoryOffset = directoryOffset;
        long extensibleDataSize = eocdSize - (sizeOfFixedFields - (long)F_EOCD_SIZE.endOffset());
        if (extensibleDataSize > Integer.MAX_VALUE) {
            throw new IOException("Extensible data of size: " + extensibleDataSize + "not supported");
        }
        byte[] rawData = new byte[Ints.checkedCast(extensibleDataSize)];
        bytes.get(rawData);
        this.extraFields = new Zip64ExtensibleDataSector(rawData);
        this.byteSupplier = new CachedSupplier<byte[]>(this::computeByteRepresentation);
    }

    private int sizeOfFixedFields() {
        return this.versionToExtract >= 62L ? F_V2_HASH_LENGTH.endOffset() : F_CD_OFFSET.endOffset();
    }

    public int size() {
        return this.sizeOfFixedFields() + this.extraFields.size();
    }

    public long getTotalRecords() {
        return this.totalRecords;
    }

    public long getDirectorySize() {
        return this.directorySize;
    }

    public long getDirectoryOffset() {
        return this.directoryOffset;
    }

    public Zip64ExtensibleDataSector getExtraFields() {
        return this.extraFields;
    }

    public long getVersionToExtract() {
        return this.versionToExtract;
    }

    public byte[] toBytes() {
        return this.byteSupplier.get();
    }

    private byte[] computeByteRepresentation() {
        int size = this.size();
        ByteBuffer out = ByteBuffer.allocate(size);
        try {
            F_SIGNATURE.write(out);
            F_EOCD_SIZE.write(out, size - F_EOCD_SIZE.endOffset());
            F_MADE_BY.write(out, this.madeBy);
            F_VERSION_EXTRACT.write(out, this.versionToExtract);
            F_NUMBER_OF_DISK.write(out);
            F_DISK_CD_START.write(out);
            F_RECORDS_DISK.write(out, this.totalRecords);
            F_RECORDS_TOTAL.write(out, this.totalRecords);
            F_CD_SIZE.write(out, this.directorySize);
            F_CD_OFFSET.write(out, this.directoryOffset);
            if (this.versionToExtract >= 62L) {
                F_V2_CD_COMPRESSION_METHOD.write(out);
                F_V2_CD_COMPRESSED_SIZE.write(out, this.directorySize);
                F_V2_CD_UNCOMPRESSED_SIZE.write(out, this.directorySize);
                F_V2_CD_ENCRYPTION_ID.write(out);
                F_V2_CD_ENCRYPTION_KEY_LENGTH.write(out);
                F_V2_CD_ENCRYPTION_FLAGS.write(out);
                F_V2_HASH_ID.write(out);
                F_V2_HASH_LENGTH.write(out);
            }
            this.extraFields.write(out);
            return out.array();
        }
        catch (IOException e2) {
            throw new IOExceptionWrapper(e2);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.utils.CachedSupplier;
import com.android.tools.build.apkzlib.utils.IOExceptionWrapper;
import com.android.tools.build.apkzlib.zip.ZipField;
import com.android.tools.build.apkzlib.zip.ZipFieldInvariantMaxValue;
import com.android.tools.build.apkzlib.zip.ZipFieldInvariantNonNegative;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.nio.ByteBuffer;

class Eocd {
    private static final ZipField.F4 F_SIGNATURE = new ZipField.F4(0, 101010256L, "EOCD signature");
    private static final ZipField.F2 F_NUMBER_OF_DISK = new ZipField.F2(F_SIGNATURE.endOffset(), 0L, "Number of this disk");
    private static final ZipField.F2 F_DISK_CD_START = new ZipField.F2(F_NUMBER_OF_DISK.endOffset(), 0L, "Disk where CD starts");
    private static final ZipField.F2 F_RECORDS_DISK = new ZipField.F2(F_DISK_CD_START.endOffset(), "Record on disk count", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_RECORDS_TOTAL = new ZipField.F2(F_RECORDS_DISK.endOffset(), "Total records", new ZipFieldInvariantNonNegative(), new ZipFieldInvariantMaxValue(Integer.MAX_VALUE));
    @VisibleForTesting
    static final ZipField.F4 F_CD_SIZE = new ZipField.F4(F_RECORDS_TOTAL.endOffset(), "Directory size", new ZipFieldInvariantNonNegative());
    @VisibleForTesting
    static final ZipField.F4 F_CD_OFFSET = new ZipField.F4(F_CD_SIZE.endOffset(), "Directory offset", new ZipFieldInvariantNonNegative());
    private static final ZipField.F2 F_COMMENT_SIZE = new ZipField.F2(F_CD_OFFSET.endOffset(), "File comment size", new ZipFieldInvariantNonNegative());
    private final int totalRecords;
    private final long directoryOffset;
    private final long directorySize;
    private final byte[] comment;
    private final CachedSupplier<byte[]> byteSupplier;

    Eocd(ByteBuffer bytes) throws IOException {
        F_SIGNATURE.verify(bytes);
        F_NUMBER_OF_DISK.verify(bytes);
        F_DISK_CD_START.verify(bytes);
        long totalRecords1 = F_RECORDS_DISK.read(bytes);
        long totalRecords2 = F_RECORDS_TOTAL.read(bytes);
        long directorySize = F_CD_SIZE.read(bytes);
        long directoryOffset = F_CD_OFFSET.read(bytes);
        int commentSize = Ints.checkedCast(F_COMMENT_SIZE.read(bytes));
        if (totalRecords1 != totalRecords2) {
            throw new IOException("Zip states records split in multiple disks, which is not supported.");
        }
        Verify.verify(totalRecords1 <= Integer.MAX_VALUE);
        this.totalRecords = Ints.checkedCast(totalRecords1);
        this.directorySize = directorySize;
        this.directoryOffset = directoryOffset;
        if (bytes.remaining() < commentSize) {
            throw new IOException("Corrupt EOCD record: not enough data for comment (comment size is " + commentSize + ").");
        }
        this.comment = new byte[commentSize];
        bytes.get(this.comment);
        this.byteSupplier = new CachedSupplier<byte[]>(this::computeByteRepresentation);
    }

    Eocd(int totalRecords, long directoryOffset, long directorySize, byte[] comment) {
        Preconditions.checkArgument(totalRecords >= 0, "totalRecords < 0");
        Preconditions.checkArgument(directoryOffset >= 0L, "directoryOffset < 0");
        Preconditions.checkArgument(directorySize >= 0L, "directorySize < 0");
        this.totalRecords = totalRecords;
        this.directoryOffset = directoryOffset;
        this.directorySize = directorySize;
        this.comment = comment;
        this.byteSupplier = new CachedSupplier<byte[]>(this::computeByteRepresentation);
    }

    int getTotalRecords() {
        return this.totalRecords;
    }

    long getDirectoryOffset() {
        return this.directoryOffset;
    }

    long getDirectorySize() {
        return this.directorySize;
    }

    long getEocdSize() {
        return (long)F_COMMENT_SIZE.endOffset() + (long)this.comment.length;
    }

    byte[] toBytes() throws IOException {
        return this.byteSupplier.get();
    }

    byte[] getComment() {
        byte[] commentCopy = new byte[this.comment.length];
        System.arraycopy(this.comment, 0, commentCopy, 0, this.comment.length);
        return commentCopy;
    }

    private byte[] computeByteRepresentation() {
        ByteBuffer out = ByteBuffer.allocate(F_COMMENT_SIZE.endOffset() + this.comment.length);
        try {
            F_SIGNATURE.write(out);
            F_NUMBER_OF_DISK.write(out);
            F_DISK_CD_START.write(out);
            F_RECORDS_DISK.write(out, this.totalRecords);
            F_RECORDS_TOTAL.write(out, this.totalRecords);
            F_CD_SIZE.write(out, this.directorySize);
            F_CD_OFFSET.write(out, this.directoryOffset);
            F_COMMENT_SIZE.write(out, this.comment.length);
            out.put(this.comment);
            return out.array();
        }
        catch (IOException e2) {
            throw new IOExceptionWrapper(e2);
        }
    }
}


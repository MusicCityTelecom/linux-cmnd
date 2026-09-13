/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.zip;

import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.zip.ZipFormatException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

public class CentralDirectoryRecord {
    public static final Comparator<CentralDirectoryRecord> BY_LOCAL_FILE_HEADER_OFFSET_COMPARATOR = new ByLocalFileHeaderOffsetComparator();
    private static final int RECORD_SIGNATURE = 33639248;
    private static final int HEADER_SIZE_BYTES = 46;
    private static final int GP_FLAGS_OFFSET = 8;
    private static final int LOCAL_FILE_HEADER_OFFSET_OFFSET = 42;
    private static final int NAME_OFFSET = 46;
    private final ByteBuffer mData;
    private final short mGpFlags;
    private final short mCompressionMethod;
    private final int mLastModificationTime;
    private final int mLastModificationDate;
    private final long mCrc32;
    private final long mCompressedSize;
    private final long mUncompressedSize;
    private final long mLocalFileHeaderOffset;
    private final String mName;
    private final int mNameSizeBytes;

    private CentralDirectoryRecord(ByteBuffer data, short gpFlags, short compressionMethod, int lastModificationTime, int lastModificationDate, long crc32, long compressedSize, long uncompressedSize, long localFileHeaderOffset, String name, int nameSizeBytes) {
        this.mData = data;
        this.mGpFlags = gpFlags;
        this.mCompressionMethod = compressionMethod;
        this.mLastModificationDate = lastModificationDate;
        this.mLastModificationTime = lastModificationTime;
        this.mCrc32 = crc32;
        this.mCompressedSize = compressedSize;
        this.mUncompressedSize = uncompressedSize;
        this.mLocalFileHeaderOffset = localFileHeaderOffset;
        this.mName = name;
        this.mNameSizeBytes = nameSizeBytes;
    }

    public int getSize() {
        return this.mData.remaining();
    }

    public String getName() {
        return this.mName;
    }

    public int getNameSizeBytes() {
        return this.mNameSizeBytes;
    }

    public short getGpFlags() {
        return this.mGpFlags;
    }

    public short getCompressionMethod() {
        return this.mCompressionMethod;
    }

    public int getLastModificationTime() {
        return this.mLastModificationTime;
    }

    public int getLastModificationDate() {
        return this.mLastModificationDate;
    }

    public long getCrc32() {
        return this.mCrc32;
    }

    public long getCompressedSize() {
        return this.mCompressedSize;
    }

    public long getUncompressedSize() {
        return this.mUncompressedSize;
    }

    public long getLocalFileHeaderOffset() {
        return this.mLocalFileHeaderOffset;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CentralDirectoryRecord getRecord(ByteBuffer buf) throws ZipFormatException {
        ByteBuffer recordBuf;
        ZipUtils.assertByteOrderLittleEndian(buf);
        if (buf.remaining() < 46) {
            throw new ZipFormatException("Input too short. Need at least: 46 bytes, available: " + buf.remaining() + " bytes", new BufferUnderflowException());
        }
        int originalPosition = buf.position();
        int recordSignature = buf.getInt();
        if (recordSignature != 33639248) {
            throw new ZipFormatException("Not a Central Directory record. Signature: 0x" + Long.toHexString((long)recordSignature & 0xFFFFFFFFL));
        }
        buf.position(originalPosition + 8);
        short gpFlags = buf.getShort();
        short compressionMethod = buf.getShort();
        int lastModificationTime = ZipUtils.getUnsignedInt16(buf);
        int lastModificationDate = ZipUtils.getUnsignedInt16(buf);
        long crc32 = ZipUtils.getUnsignedInt32(buf);
        long compressedSize = ZipUtils.getUnsignedInt32(buf);
        long uncompressedSize = ZipUtils.getUnsignedInt32(buf);
        int nameSize = ZipUtils.getUnsignedInt16(buf);
        int extraSize = ZipUtils.getUnsignedInt16(buf);
        int commentSize = ZipUtils.getUnsignedInt16(buf);
        buf.position(originalPosition + 42);
        long localFileHeaderOffset = ZipUtils.getUnsignedInt32(buf);
        buf.position(originalPosition);
        int recordSize = 46 + nameSize + extraSize + commentSize;
        if (recordSize > buf.remaining()) {
            throw new ZipFormatException("Input too short. Need: " + recordSize + " bytes, available: " + buf.remaining() + " bytes", new BufferUnderflowException());
        }
        String name = CentralDirectoryRecord.getName(buf, originalPosition + 46, nameSize);
        buf.position(originalPosition);
        int originalLimit = buf.limit();
        int recordEndInBuf = originalPosition + recordSize;
        try {
            buf.limit(recordEndInBuf);
            recordBuf = buf.slice();
        }
        finally {
            buf.limit(originalLimit);
        }
        buf.position(recordEndInBuf);
        return new CentralDirectoryRecord(recordBuf, gpFlags, compressionMethod, lastModificationTime, lastModificationDate, crc32, compressedSize, uncompressedSize, localFileHeaderOffset, name, nameSize);
    }

    public void copyTo(ByteBuffer output) {
        output.put(this.mData.slice());
    }

    public CentralDirectoryRecord createWithModifiedLocalFileHeaderOffset(long localFileHeaderOffset) {
        ByteBuffer result = ByteBuffer.allocate(this.mData.remaining());
        result.put(this.mData.slice());
        result.flip();
        result.order(ByteOrder.LITTLE_ENDIAN);
        ZipUtils.setUnsignedInt32(result, 42, localFileHeaderOffset);
        return new CentralDirectoryRecord(result, this.mGpFlags, this.mCompressionMethod, this.mLastModificationTime, this.mLastModificationDate, this.mCrc32, this.mCompressedSize, this.mUncompressedSize, localFileHeaderOffset, this.mName, this.mNameSizeBytes);
    }

    public static CentralDirectoryRecord createWithDeflateCompressedData(String name, int lastModifiedTime, int lastModifiedDate, long crc32, long compressedSize, long uncompressedSize, long localFileHeaderOffset) {
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        short gpFlags = 2048;
        short compressionMethod = 8;
        int recordSize = 46 + nameBytes.length;
        ByteBuffer result = ByteBuffer.allocate(recordSize);
        result.order(ByteOrder.LITTLE_ENDIAN);
        result.putInt(33639248);
        ZipUtils.putUnsignedInt16(result, 20);
        ZipUtils.putUnsignedInt16(result, 20);
        result.putShort(gpFlags);
        result.putShort(compressionMethod);
        ZipUtils.putUnsignedInt16(result, lastModifiedTime);
        ZipUtils.putUnsignedInt16(result, lastModifiedDate);
        ZipUtils.putUnsignedInt32(result, crc32);
        ZipUtils.putUnsignedInt32(result, compressedSize);
        ZipUtils.putUnsignedInt32(result, uncompressedSize);
        ZipUtils.putUnsignedInt16(result, nameBytes.length);
        ZipUtils.putUnsignedInt16(result, 0);
        ZipUtils.putUnsignedInt16(result, 0);
        ZipUtils.putUnsignedInt16(result, 0);
        ZipUtils.putUnsignedInt16(result, 0);
        ZipUtils.putUnsignedInt32(result, 0L);
        ZipUtils.putUnsignedInt32(result, localFileHeaderOffset);
        result.put(nameBytes);
        if (result.hasRemaining()) {
            throw new RuntimeException("pos: " + result.position() + ", limit: " + result.limit());
        }
        result.flip();
        return new CentralDirectoryRecord(result, gpFlags, compressionMethod, lastModifiedTime, lastModifiedDate, crc32, compressedSize, uncompressedSize, localFileHeaderOffset, name, nameBytes.length);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static String getName(ByteBuffer record, int position, int nameLengthBytes) {
        int nameBytesOffset;
        byte[] nameBytes;
        if (record.hasArray()) {
            nameBytes = record.array();
            nameBytesOffset = record.arrayOffset() + position;
        } else {
            nameBytes = new byte[nameLengthBytes];
            nameBytesOffset = 0;
            int originalPosition = record.position();
            try {
                record.position(position);
                record.get(nameBytes);
            }
            finally {
                record.position(originalPosition);
            }
        }
        return new String(nameBytes, nameBytesOffset, nameLengthBytes, StandardCharsets.UTF_8);
    }

    private static class ByLocalFileHeaderOffsetComparator
    implements Comparator<CentralDirectoryRecord> {
        private ByLocalFileHeaderOffsetComparator() {
        }

        @Override
        public int compare(CentralDirectoryRecord r1, CentralDirectoryRecord r22) {
            long offset2;
            long offset1 = r1.getLocalFileHeaderOffset();
            if (offset1 > (offset2 = r22.getLocalFileHeaderOffset())) {
                return 1;
            }
            if (offset1 < offset2) {
                return -1;
            }
            return 0;
        }
    }
}


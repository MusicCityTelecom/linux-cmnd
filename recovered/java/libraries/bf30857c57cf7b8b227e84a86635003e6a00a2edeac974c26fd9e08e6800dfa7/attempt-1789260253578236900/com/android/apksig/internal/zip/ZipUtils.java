/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.zip;

import com.android.apksig.internal.util.Pair;
import com.android.apksig.util.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

public abstract class ZipUtils {
    public static final short COMPRESSION_METHOD_STORED = 0;
    public static final short COMPRESSION_METHOD_DEFLATED = 8;
    public static final short GP_FLAG_DATA_DESCRIPTOR_USED = 8;
    public static final short GP_FLAG_EFS = 2048;
    private static final int ZIP_EOCD_REC_MIN_SIZE = 22;
    private static final int ZIP_EOCD_REC_SIG = 101010256;
    private static final int ZIP_EOCD_CENTRAL_DIR_TOTAL_RECORD_COUNT_OFFSET = 10;
    private static final int ZIP_EOCD_CENTRAL_DIR_SIZE_FIELD_OFFSET = 12;
    private static final int ZIP_EOCD_CENTRAL_DIR_OFFSET_FIELD_OFFSET = 16;
    private static final int ZIP_EOCD_COMMENT_LENGTH_FIELD_OFFSET = 20;
    private static final int UINT16_MAX_VALUE = 65535;

    private ZipUtils() {
    }

    public static void setZipEocdCentralDirectoryOffset(ByteBuffer zipEndOfCentralDirectory, long offset) {
        ZipUtils.assertByteOrderLittleEndian(zipEndOfCentralDirectory);
        ZipUtils.setUnsignedInt32(zipEndOfCentralDirectory, zipEndOfCentralDirectory.position() + 16, offset);
    }

    public static long getZipEocdCentralDirectoryOffset(ByteBuffer zipEndOfCentralDirectory) {
        ZipUtils.assertByteOrderLittleEndian(zipEndOfCentralDirectory);
        return ZipUtils.getUnsignedInt32(zipEndOfCentralDirectory, zipEndOfCentralDirectory.position() + 16);
    }

    public static long getZipEocdCentralDirectorySizeBytes(ByteBuffer zipEndOfCentralDirectory) {
        ZipUtils.assertByteOrderLittleEndian(zipEndOfCentralDirectory);
        return ZipUtils.getUnsignedInt32(zipEndOfCentralDirectory, zipEndOfCentralDirectory.position() + 12);
    }

    public static int getZipEocdCentralDirectoryTotalRecordCount(ByteBuffer zipEndOfCentralDirectory) {
        ZipUtils.assertByteOrderLittleEndian(zipEndOfCentralDirectory);
        return ZipUtils.getUnsignedInt16(zipEndOfCentralDirectory, zipEndOfCentralDirectory.position() + 10);
    }

    public static Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord(DataSource zip2) throws IOException {
        long fileSize = zip2.size();
        if (fileSize < 22L) {
            return null;
        }
        Pair<ByteBuffer, Long> result = ZipUtils.findZipEndOfCentralDirectoryRecord(zip2, 0);
        if (result != null) {
            return result;
        }
        return ZipUtils.findZipEndOfCentralDirectoryRecord(zip2, 65535);
    }

    private static Pair<ByteBuffer, Long> findZipEndOfCentralDirectoryRecord(DataSource zip2, int maxCommentSize) throws IOException {
        if (maxCommentSize < 0 || maxCommentSize > 65535) {
            throw new IllegalArgumentException("maxCommentSize: " + maxCommentSize);
        }
        long fileSize = zip2.size();
        if (fileSize < 22L) {
            return null;
        }
        maxCommentSize = (int)Math.min((long)maxCommentSize, fileSize - 22L);
        int maxEocdSize = 22 + maxCommentSize;
        long bufOffsetInFile = fileSize - (long)maxEocdSize;
        ByteBuffer buf = zip2.getByteBuffer(bufOffsetInFile, maxEocdSize);
        buf.order(ByteOrder.LITTLE_ENDIAN);
        int eocdOffsetInBuf = ZipUtils.findZipEndOfCentralDirectoryRecord(buf);
        if (eocdOffsetInBuf == -1) {
            return null;
        }
        buf.position(eocdOffsetInBuf);
        ByteBuffer eocd = buf.slice();
        eocd.order(ByteOrder.LITTLE_ENDIAN);
        return Pair.of(eocd, bufOffsetInFile + (long)eocdOffsetInBuf);
    }

    private static int findZipEndOfCentralDirectoryRecord(ByteBuffer zipContents) {
        ZipUtils.assertByteOrderLittleEndian(zipContents);
        int archiveSize = zipContents.capacity();
        if (archiveSize < 22) {
            return -1;
        }
        int maxCommentLength = Math.min(archiveSize - 22, 65535);
        int eocdWithEmptyCommentStartPosition = archiveSize - 22;
        for (int expectedCommentLength = 0; expectedCommentLength <= maxCommentLength; ++expectedCommentLength) {
            int actualCommentLength;
            int eocdStartPos = eocdWithEmptyCommentStartPosition - expectedCommentLength;
            if (zipContents.getInt(eocdStartPos) != 101010256 || (actualCommentLength = ZipUtils.getUnsignedInt16(zipContents, eocdStartPos + 20)) != expectedCommentLength) continue;
            return eocdStartPos;
        }
        return -1;
    }

    static void assertByteOrderLittleEndian(ByteBuffer buffer) {
        if (buffer.order() != ByteOrder.LITTLE_ENDIAN) {
            throw new IllegalArgumentException("ByteBuffer byte order must be little endian");
        }
    }

    public static int getUnsignedInt16(ByteBuffer buffer, int offset) {
        return buffer.getShort(offset) & 0xFFFF;
    }

    public static int getUnsignedInt16(ByteBuffer buffer) {
        return buffer.getShort() & 0xFFFF;
    }

    static void setUnsignedInt16(ByteBuffer buffer, int offset, int value) {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException("uint16 value of out range: " + value);
        }
        buffer.putShort(offset, (short)value);
    }

    static void setUnsignedInt32(ByteBuffer buffer, int offset, long value) {
        if (value < 0L || value > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("uint32 value of out range: " + value);
        }
        buffer.putInt(offset, (int)value);
    }

    public static void putUnsignedInt16(ByteBuffer buffer, int value) {
        if (value < 0 || value > 65535) {
            throw new IllegalArgumentException("uint16 value of out range: " + value);
        }
        buffer.putShort((short)value);
    }

    static long getUnsignedInt32(ByteBuffer buffer, int offset) {
        return (long)buffer.getInt(offset) & 0xFFFFFFFFL;
    }

    static long getUnsignedInt32(ByteBuffer buffer) {
        return (long)buffer.getInt() & 0xFFFFFFFFL;
    }

    static void putUnsignedInt32(ByteBuffer buffer, long value) {
        if (value < 0L || value > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("uint32 value of out range: " + value);
        }
        buffer.putInt((int)value);
    }

    public static DeflateResult deflate(ByteBuffer input) {
        int inputOffset;
        byte[] inputBuf;
        int inputLength = input.remaining();
        if (input.hasArray()) {
            inputBuf = input.array();
            inputOffset = input.arrayOffset() + input.position();
            input.position(input.limit());
        } else {
            inputBuf = new byte[inputLength];
            inputOffset = 0;
            input.get(inputBuf);
        }
        CRC32 crc32 = new CRC32();
        crc32.update(inputBuf, inputOffset, inputLength);
        long crc32Value = crc32.getValue();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(9, true);
        deflater.setInput(inputBuf, inputOffset, inputLength);
        deflater.finish();
        byte[] buf = new byte[65536];
        while (!deflater.finished()) {
            int chunkSize = deflater.deflate(buf);
            out.write(buf, 0, chunkSize);
        }
        return new DeflateResult(inputLength, crc32Value, out.toByteArray());
    }

    public static class DeflateResult {
        public final int inputSizeBytes;
        public final long inputCrc32;
        public final byte[] output;

        public DeflateResult(int inputSizeBytes, long inputCrc32, byte[] output) {
            this.inputSizeBytes = inputSizeBytes;
            this.inputCrc32 = inputCrc32;
            this.output = output;
        }
    }
}


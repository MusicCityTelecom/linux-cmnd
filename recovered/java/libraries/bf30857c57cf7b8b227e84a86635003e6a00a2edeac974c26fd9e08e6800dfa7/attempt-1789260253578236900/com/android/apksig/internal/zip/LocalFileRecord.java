/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.zip;

import com.android.apksig.internal.util.ByteBufferSink;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import com.android.apksig.zip.ZipFormatException;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class LocalFileRecord {
    private static final int RECORD_SIGNATURE = 67324752;
    private static final int HEADER_SIZE_BYTES = 30;
    private static final int GP_FLAGS_OFFSET = 6;
    private static final int CRC32_OFFSET = 14;
    private static final int COMPRESSED_SIZE_OFFSET = 18;
    private static final int UNCOMPRESSED_SIZE_OFFSET = 22;
    private static final int NAME_LENGTH_OFFSET = 26;
    private static final int EXTRA_LENGTH_OFFSET = 28;
    private static final int NAME_OFFSET = 30;
    private static final int DATA_DESCRIPTOR_SIZE_BYTES_WITHOUT_SIGNATURE = 12;
    private static final int DATA_DESCRIPTOR_SIGNATURE = 134695760;
    private final String mName;
    private final int mNameSizeBytes;
    private final ByteBuffer mExtra;
    private final long mStartOffsetInArchive;
    private final long mSize;
    private final int mDataStartOffset;
    private final long mDataSize;
    private final boolean mDataCompressed;
    private final long mUncompressedDataSize;
    private static final ByteBuffer EMPTY_BYTE_BUFFER = ByteBuffer.allocate(0);

    private LocalFileRecord(String name, int nameSizeBytes, ByteBuffer extra, long startOffsetInArchive, long size, int dataStartOffset, long dataSize, boolean dataCompressed, long uncompressedDataSize) {
        this.mName = name;
        this.mNameSizeBytes = nameSizeBytes;
        this.mExtra = extra;
        this.mStartOffsetInArchive = startOffsetInArchive;
        this.mSize = size;
        this.mDataStartOffset = dataStartOffset;
        this.mDataSize = dataSize;
        this.mDataCompressed = dataCompressed;
        this.mUncompressedDataSize = uncompressedDataSize;
    }

    public String getName() {
        return this.mName;
    }

    public ByteBuffer getExtra() {
        return this.mExtra.capacity() > 0 ? this.mExtra.slice() : this.mExtra;
    }

    public int getExtraFieldStartOffsetInsideRecord() {
        return 30 + this.mNameSizeBytes;
    }

    public long getStartOffsetInArchive() {
        return this.mStartOffsetInArchive;
    }

    public int getDataStartOffsetInRecord() {
        return this.mDataStartOffset;
    }

    public long getSize() {
        return this.mSize;
    }

    public boolean isDataCompressed() {
        return this.mDataCompressed;
    }

    public static LocalFileRecord getRecord(DataSource apk, CentralDirectoryRecord cdRecord, long cdStartOffset) throws ZipFormatException, IOException {
        return LocalFileRecord.getRecord(apk, cdRecord, cdStartOffset, true, true);
    }

    private static LocalFileRecord getRecord(DataSource apk, CentralDirectoryRecord cdRecord, long cdStartOffset, boolean extraFieldContentsNeeded, boolean dataDescriptorIncluded) throws ZipFormatException, IOException {
        boolean compressed;
        long dataSize;
        int nameLength;
        boolean cdDataDescriptorUsed;
        ByteBuffer header;
        String entryName = cdRecord.getName();
        int cdRecordEntryNameSizeBytes = cdRecord.getNameSizeBytes();
        int headerSizeWithName = 30 + cdRecordEntryNameSizeBytes;
        long headerStartOffset = cdRecord.getLocalFileHeaderOffset();
        long headerEndOffset = headerStartOffset + (long)headerSizeWithName;
        if (headerEndOffset > cdStartOffset) {
            throw new ZipFormatException("Local File Header of " + entryName + " extends beyond start of Central Directory. LFH end: " + headerEndOffset + ", CD start: " + cdStartOffset);
        }
        try {
            header = apk.getByteBuffer(headerStartOffset, headerSizeWithName);
        }
        catch (IOException e2) {
            throw new IOException("Failed to read Local File Header of " + entryName, e2);
        }
        header.order(ByteOrder.LITTLE_ENDIAN);
        int recordSignature = header.getInt();
        if (recordSignature != 67324752) {
            throw new ZipFormatException("Not a Local File Header record for entry " + entryName + ". Signature: 0x" + Long.toHexString((long)recordSignature & 0xFFFFFFFFL));
        }
        short gpFlags = header.getShort(6);
        boolean dataDescriptorUsed = (gpFlags & 8) != 0;
        boolean bl = cdDataDescriptorUsed = (cdRecord.getGpFlags() & 8) != 0;
        if (dataDescriptorUsed != cdDataDescriptorUsed) {
            throw new ZipFormatException("Data Descriptor presence mismatch between Local File Header and Central Directory for entry " + entryName + ". LFH: " + dataDescriptorUsed + ", CD: " + cdDataDescriptorUsed);
        }
        long uncompressedDataCrc32FromCdRecord = cdRecord.getCrc32();
        long compressedDataSizeFromCdRecord = cdRecord.getCompressedSize();
        long uncompressedDataSizeFromCdRecord = cdRecord.getUncompressedSize();
        if (!dataDescriptorUsed) {
            long crc32 = ZipUtils.getUnsignedInt32(header, 14);
            if (crc32 != uncompressedDataCrc32FromCdRecord) {
                throw new ZipFormatException("CRC-32 mismatch between Local File Header and Central Directory for entry " + entryName + ". LFH: " + crc32 + ", CD: " + uncompressedDataCrc32FromCdRecord);
            }
            long compressedSize = ZipUtils.getUnsignedInt32(header, 18);
            if (compressedSize != compressedDataSizeFromCdRecord) {
                throw new ZipFormatException("Compressed size mismatch between Local File Header and Central Directory for entry " + entryName + ". LFH: " + compressedSize + ", CD: " + compressedDataSizeFromCdRecord);
            }
            long uncompressedSize = ZipUtils.getUnsignedInt32(header, 22);
            if (uncompressedSize != uncompressedDataSizeFromCdRecord) {
                throw new ZipFormatException("Uncompressed size mismatch between Local File Header and Central Directory for entry " + entryName + ". LFH: " + uncompressedSize + ", CD: " + uncompressedDataSizeFromCdRecord);
            }
        }
        if ((nameLength = ZipUtils.getUnsignedInt16(header, 26)) > cdRecordEntryNameSizeBytes) {
            throw new ZipFormatException("Name mismatch between Local File Header and Central Directory for entry" + entryName + ". LFH: " + nameLength + " bytes, CD: " + cdRecordEntryNameSizeBytes + " bytes");
        }
        String name = CentralDirectoryRecord.getName(header, 30, nameLength);
        if (!entryName.equals(name)) {
            throw new ZipFormatException("Name mismatch between Local File Header and Central Directory. LFH: \"" + name + "\", CD: \"" + entryName + "\"");
        }
        int extraLength = ZipUtils.getUnsignedInt16(header, 28);
        long dataStartOffset = headerStartOffset + 30L + (long)nameLength + (long)extraLength;
        long dataEndOffset = dataStartOffset + (dataSize = (compressed = cdRecord.getCompressionMethod() != 0) ? compressedDataSizeFromCdRecord : uncompressedDataSizeFromCdRecord);
        if (dataEndOffset > cdStartOffset) {
            throw new ZipFormatException("Local File Header data of " + entryName + " overlaps with Central Directory. LFH data start: " + dataStartOffset + ", LFH data end: " + dataEndOffset + ", CD start: " + cdStartOffset);
        }
        ByteBuffer extra = EMPTY_BYTE_BUFFER;
        if (extraFieldContentsNeeded && extraLength > 0) {
            extra = apk.getByteBuffer(headerStartOffset + 30L + (long)nameLength, extraLength);
        }
        long recordEndOffset = dataEndOffset;
        if (dataDescriptorIncluded && (gpFlags & 8) != 0) {
            long dataDescriptorEndOffset = dataEndOffset + 12L;
            if (dataDescriptorEndOffset > cdStartOffset) {
                throw new ZipFormatException("Data Descriptor of " + entryName + " overlaps with Central Directory. Data Descriptor end: " + dataEndOffset + ", CD start: " + cdStartOffset);
            }
            ByteBuffer dataDescriptorPotentialSig = apk.getByteBuffer(dataEndOffset, 4);
            dataDescriptorPotentialSig.order(ByteOrder.LITTLE_ENDIAN);
            if (dataDescriptorPotentialSig.getInt() == 134695760 && (dataDescriptorEndOffset += 4L) > cdStartOffset) {
                throw new ZipFormatException("Data Descriptor of " + entryName + " overlaps with Central Directory. Data Descriptor end: " + dataEndOffset + ", CD start: " + cdStartOffset);
            }
            recordEndOffset = dataDescriptorEndOffset;
        }
        long recordSize = recordEndOffset - headerStartOffset;
        int dataStartOffsetInRecord = 30 + nameLength + extraLength;
        return new LocalFileRecord(entryName, cdRecordEntryNameSizeBytes, extra, headerStartOffset, recordSize, dataStartOffsetInRecord, dataSize, compressed, uncompressedDataSizeFromCdRecord);
    }

    public long outputRecord(DataSource sourceApk, DataSink output) throws IOException {
        long size = this.getSize();
        sourceApk.feed(this.getStartOffsetInArchive(), size, output);
        return size;
    }

    public long outputRecordWithModifiedExtra(DataSource sourceApk, ByteBuffer extra, DataSink output) throws IOException {
        long recordStartOffsetInSource = this.getStartOffsetInArchive();
        int extraStartOffsetInRecord = this.getExtraFieldStartOffsetInsideRecord();
        int extraSizeBytes = extra.remaining();
        int headerSize = extraStartOffsetInRecord + extraSizeBytes;
        ByteBuffer header = ByteBuffer.allocate(headerSize);
        header.order(ByteOrder.LITTLE_ENDIAN);
        sourceApk.copyTo(recordStartOffsetInSource, extraStartOffsetInRecord, header);
        header.put(extra.slice());
        header.flip();
        ZipUtils.setUnsignedInt16(header, 28, extraSizeBytes);
        long outputByteCount = header.remaining();
        output.consume(header);
        long remainingRecordSize = this.getSize() - (long)this.mDataStartOffset;
        sourceApk.feed(recordStartOffsetInSource + (long)this.mDataStartOffset, remainingRecordSize, output);
        return outputByteCount += remainingRecordSize;
    }

    public static long outputRecordWithDeflateCompressedData(String name, int lastModifiedTime, int lastModifiedDate, byte[] compressedData, long crc32, long uncompressedSize, DataSink output) throws IOException {
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
        int recordSize = 30 + nameBytes.length;
        ByteBuffer result = ByteBuffer.allocate(recordSize);
        result.order(ByteOrder.LITTLE_ENDIAN);
        result.putInt(67324752);
        ZipUtils.putUnsignedInt16(result, 20);
        result.putShort((short)2048);
        result.putShort((short)8);
        ZipUtils.putUnsignedInt16(result, lastModifiedTime);
        ZipUtils.putUnsignedInt16(result, lastModifiedDate);
        ZipUtils.putUnsignedInt32(result, crc32);
        ZipUtils.putUnsignedInt32(result, compressedData.length);
        ZipUtils.putUnsignedInt32(result, uncompressedSize);
        ZipUtils.putUnsignedInt16(result, nameBytes.length);
        ZipUtils.putUnsignedInt16(result, 0);
        result.put(nameBytes);
        if (result.hasRemaining()) {
            throw new RuntimeException("pos: " + result.position() + ", limit: " + result.limit());
        }
        result.flip();
        long outputByteCount = result.remaining();
        output.consume(result);
        output.consume(compressedData, 0, compressedData.length);
        return outputByteCount += (long)compressedData.length;
    }

    public void outputUncompressedData(DataSource lfhSection, DataSink sink) throws IOException, ZipFormatException {
        block19: {
            long dataStartOffsetInArchive = this.mStartOffsetInArchive + (long)this.mDataStartOffset;
            try {
                if (this.mDataCompressed) {
                    try (InflateSinkAdapter inflateAdapter = new InflateSinkAdapter(sink);){
                        lfhSection.feed(dataStartOffsetInArchive, this.mDataSize, inflateAdapter);
                        long actualUncompressedSize = inflateAdapter.getOutputByteCount();
                        if (actualUncompressedSize != this.mUncompressedDataSize) {
                            throw new ZipFormatException("Unexpected size of uncompressed data of " + this.mName + ". Expected: " + this.mUncompressedDataSize + " bytes, actual: " + actualUncompressedSize + " bytes");
                        }
                        break block19;
                    }
                    catch (IOException e2) {
                        if (e2.getCause() instanceof DataFormatException) {
                            throw new ZipFormatException("Data of entry " + this.mName + " malformed", e2);
                        }
                        throw e2;
                    }
                }
                lfhSection.feed(dataStartOffsetInArchive, this.mDataSize, sink);
            }
            catch (IOException e3) {
                throw new IOException("Failed to read data of " + (this.mDataCompressed ? "compressed" : "uncompressed") + " entry " + this.mName, e3);
            }
        }
    }

    public static void outputUncompressedData(DataSource source, CentralDirectoryRecord cdRecord, long cdStartOffsetInArchive, DataSink sink) throws ZipFormatException, IOException {
        LocalFileRecord lfhRecord = LocalFileRecord.getRecord(source, cdRecord, cdStartOffsetInArchive, false, false);
        lfhRecord.outputUncompressedData(source, sink);
    }

    public static byte[] getUncompressedData(DataSource source, CentralDirectoryRecord cdRecord, long cdStartOffsetInArchive) throws ZipFormatException, IOException {
        if (cdRecord.getUncompressedSize() > Integer.MAX_VALUE) {
            throw new IOException(cdRecord.getName() + " too large: " + cdRecord.getUncompressedSize());
        }
        byte[] result = new byte[(int)cdRecord.getUncompressedSize()];
        ByteBuffer resultBuf = ByteBuffer.wrap(result);
        ByteBufferSink resultSink = new ByteBufferSink(resultBuf);
        LocalFileRecord.outputUncompressedData(source, cdRecord, cdStartOffsetInArchive, resultSink);
        return result;
    }

    private static class InflateSinkAdapter
    implements DataSink,
    Closeable {
        private final DataSink mDelegate;
        private Inflater mInflater = new Inflater(true);
        private byte[] mOutputBuffer;
        private byte[] mInputBuffer;
        private long mOutputByteCount;
        private boolean mClosed;

        private InflateSinkAdapter(DataSink delegate) {
            this.mDelegate = delegate;
        }

        @Override
        public void consume(byte[] buf, int offset, int length) throws IOException {
            this.checkNotClosed();
            this.mInflater.setInput(buf, offset, length);
            if (this.mOutputBuffer == null) {
                this.mOutputBuffer = new byte[65536];
            }
            while (!this.mInflater.finished()) {
                int outputChunkSize;
                try {
                    outputChunkSize = this.mInflater.inflate(this.mOutputBuffer);
                }
                catch (DataFormatException e2) {
                    throw new IOException("Failed to inflate data", e2);
                }
                if (outputChunkSize == 0) {
                    return;
                }
                this.mDelegate.consume(this.mOutputBuffer, 0, outputChunkSize);
                this.mOutputByteCount += (long)outputChunkSize;
            }
        }

        @Override
        public void consume(ByteBuffer buf) throws IOException {
            this.checkNotClosed();
            if (buf.hasArray()) {
                this.consume(buf.array(), buf.arrayOffset() + buf.position(), buf.remaining());
                buf.position(buf.limit());
            } else {
                if (this.mInputBuffer == null) {
                    this.mInputBuffer = new byte[65536];
                }
                while (buf.hasRemaining()) {
                    int chunkSize = Math.min(buf.remaining(), this.mInputBuffer.length);
                    buf.get(this.mInputBuffer, 0, chunkSize);
                    this.consume(this.mInputBuffer, 0, chunkSize);
                }
            }
        }

        public long getOutputByteCount() {
            return this.mOutputByteCount;
        }

        @Override
        public void close() throws IOException {
            this.mClosed = true;
            this.mInputBuffer = null;
            this.mOutputBuffer = null;
            if (this.mInflater != null) {
                this.mInflater.end();
                this.mInflater = null;
            }
        }

        private void checkNotClosed() {
            if (this.mClosed) {
                throw new IllegalStateException("Closed");
            }
        }
    }
}


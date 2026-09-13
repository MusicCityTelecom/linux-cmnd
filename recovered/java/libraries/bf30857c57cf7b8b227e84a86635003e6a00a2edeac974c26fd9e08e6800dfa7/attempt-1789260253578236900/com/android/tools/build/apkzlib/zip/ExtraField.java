/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.utils.LittleEndianUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.annotation.Nullable;

public class ExtraField {
    static final int ALIGNMENT_ZIP_EXTRA_DATA_FIELD_HEADER_ID = 55605;
    @Nullable
    private final byte[] rawData;
    @Nullable
    private ImmutableList<Segment> segments;

    public ExtraField(byte[] rawData) {
        this.rawData = rawData;
        this.segments = null;
    }

    public ExtraField() {
        this.rawData = null;
        this.segments = ImmutableList.of();
    }

    public ExtraField(ImmutableList<Segment> segments) {
        this.rawData = null;
        this.segments = segments;
    }

    public ImmutableList<Segment> getSegments() throws IOException {
        if (this.segments == null) {
            this.parseSegments();
        }
        Preconditions.checkNotNull(this.segments);
        return this.segments;
    }

    @Nullable
    public Segment getSingleSegment(int headerId) throws IOException {
        ArrayList<Segment> found = new ArrayList<Segment>();
        for (Segment s3 : this.getSegments()) {
            if (s3.getHeaderId() != headerId) continue;
            found.add(s3);
        }
        if (found.isEmpty()) {
            return null;
        }
        if (found.size() == 1) {
            return (Segment)found.get(0);
        }
        throw new IOException(found.size() + " segments with header ID " + headerId + "found");
    }

    private void parseSegments() throws IOException {
        Preconditions.checkNotNull(this.rawData);
        Preconditions.checkState(this.segments == null);
        ArrayList<Segment> segments = new ArrayList<Segment>();
        ByteBuffer buffer = ByteBuffer.wrap(this.rawData);
        while (buffer.remaining() > 0) {
            int headerId = LittleEndianUtils.readUnsigned2Le(buffer);
            int dataSize = LittleEndianUtils.readUnsigned2Le(buffer);
            if (dataSize < 0) {
                throw new IOException("Invalid data size for extra field segment with header ID " + headerId + ": " + dataSize);
            }
            byte[] data = new byte[dataSize];
            if (buffer.remaining() < dataSize) {
                throw new IOException("Invalid data size for extra field segment with header ID " + headerId + ": " + dataSize + " (only " + buffer.remaining() + " bytes are available)");
            }
            buffer.get(data);
            SegmentFactory factory = ExtraField.identifySegmentFactory(headerId);
            Segment seg = factory.make(headerId, data);
            segments.add(seg);
        }
        this.segments = ImmutableList.copyOf(segments);
    }

    public int size() {
        if (this.rawData != null) {
            return this.rawData.length;
        }
        Preconditions.checkNotNull(this.segments);
        int sz = 0;
        for (Segment s3 : this.segments) {
            sz += s3.size();
        }
        return sz;
    }

    public void write(ByteBuffer out) throws IOException {
        if (this.rawData != null) {
            out.put(this.rawData);
        } else {
            Preconditions.checkNotNull(this.segments);
            for (Segment s3 : this.segments) {
                s3.write(out);
            }
        }
    }

    private static SegmentFactory identifySegmentFactory(int headerId) {
        if (headerId == 55605) {
            return AlignmentSegment::new;
        }
        return RawDataSegment::new;
    }

    public static class AlignmentSegment
    implements Segment {
        public static final int MINIMUM_SIZE = 6;
        private int alignment;
        private int padding;

        public AlignmentSegment(int alignment, int totalSize) {
            Preconditions.checkArgument(alignment > 0, "alignment <= 0");
            Preconditions.checkArgument(totalSize >= 6, "totalSize < MINIMUM_SIZE");
            this.alignment = alignment;
            this.padding = totalSize - 6;
        }

        public AlignmentSegment(int headerId, byte[] data) throws IOException {
            Preconditions.checkArgument(headerId == 55605);
            ByteBuffer dataBuffer = ByteBuffer.wrap(data);
            this.alignment = LittleEndianUtils.readUnsigned2Le(dataBuffer);
            if (this.alignment <= 0) {
                throw new IOException("Invalid alignment in alignment field: " + this.alignment);
            }
            this.padding = data.length - 2;
        }

        @Override
        public void write(ByteBuffer out) throws IOException {
            LittleEndianUtils.writeUnsigned2Le(out, 55605);
            LittleEndianUtils.writeUnsigned2Le(out, this.padding + 2);
            LittleEndianUtils.writeUnsigned2Le(out, this.alignment);
            out.put(new byte[this.padding]);
        }

        @Override
        public int size() {
            return this.padding + 6;
        }

        @Override
        public int getHeaderId() {
            return 55605;
        }
    }

    public static class RawDataSegment
    implements Segment {
        private final int headerId;
        private final byte[] data;

        RawDataSegment(int headerId, byte[] data) {
            this.headerId = headerId;
            this.data = data;
        }

        @Override
        public int getHeaderId() {
            return this.headerId;
        }

        @Override
        public void write(ByteBuffer out) throws IOException {
            LittleEndianUtils.writeUnsigned2Le(out, this.headerId);
            LittleEndianUtils.writeUnsigned2Le(out, this.data.length);
            out.put(this.data);
        }

        @Override
        public int size() {
            return 4 + this.data.length;
        }
    }

    static interface SegmentFactory {
        public Segment make(int var1, byte[] var2) throws IOException;
    }

    public static interface Segment {
        public int getHeaderId();

        public int size();

        public void write(ByteBuffer var1) throws IOException;
    }
}


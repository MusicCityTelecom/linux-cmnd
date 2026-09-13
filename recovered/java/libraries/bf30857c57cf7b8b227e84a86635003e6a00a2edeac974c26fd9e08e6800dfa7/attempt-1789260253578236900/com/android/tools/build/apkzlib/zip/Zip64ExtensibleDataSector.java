/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip;

import com.android.tools.build.apkzlib.zip.utils.LittleEndianUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import javax.annotation.Nullable;

public class Zip64ExtensibleDataSector {
    @Nullable
    private final byte[] rawData;
    @Nullable
    private ImmutableList<Z64SpecialPurposeData> fields;

    public Zip64ExtensibleDataSector(byte[] rawData) {
        this.rawData = rawData;
        this.fields = null;
    }

    public Zip64ExtensibleDataSector() {
        this.rawData = null;
        this.fields = ImmutableList.of();
    }

    public Zip64ExtensibleDataSector(ImmutableList<Z64SpecialPurposeData> fields) {
        this.rawData = null;
        this.fields = fields;
    }

    int size() {
        if (this.rawData != null) {
            return this.rawData.length;
        }
        Preconditions.checkNotNull(this.fields);
        int sumSizes = 0;
        for (Z64SpecialPurposeData data : this.fields) {
            sumSizes += data.size();
        }
        return sumSizes;
    }

    void write(ByteBuffer out) throws IOException {
        if (this.rawData != null) {
            out.put(this.rawData);
        } else {
            Preconditions.checkNotNull(this.fields);
            for (Z64SpecialPurposeData data : this.fields) {
                data.write(out);
            }
        }
    }

    public ImmutableList<Z64SpecialPurposeData> getFields() throws IOException {
        if (this.fields == null) {
            this.parseData();
        }
        Preconditions.checkNotNull(this.fields);
        return this.fields;
    }

    private void parseData() throws IOException {
        Preconditions.checkNotNull(this.rawData);
        Preconditions.checkState(this.fields == null);
        ArrayList<Z64SpecialPurposeData> fields = new ArrayList<Z64SpecialPurposeData>();
        ByteBuffer buffer = ByteBuffer.wrap(this.rawData);
        while (buffer.remaining() > 0) {
            int headerId = LittleEndianUtils.readUnsigned2Le(buffer);
            long dataSize = LittleEndianUtils.readUnsigned4Le(buffer);
            byte[] data = new byte[Ints.checkedCast(dataSize)];
            if (dataSize < 0L) {
                throw new IOException("Invalid data size for special purpose data with header ID " + headerId + ": " + dataSize);
            }
            buffer.get(data);
            SpecialPurposeDataFactory factory = RawSpecialPurposeData::new;
            Z64SpecialPurposeData spd = factory.make(headerId, data);
            fields.add(spd);
        }
        this.fields = ImmutableList.copyOf(fields);
    }

    public static class RawSpecialPurposeData
    implements Z64SpecialPurposeData {
        private final int headerId;
        private final byte[] data;

        RawSpecialPurposeData(int headerId, byte[] data) {
            this.headerId = headerId;
            this.data = data;
        }

        @Override
        public int getHeaderId() {
            return this.headerId;
        }

        @Override
        public int size() {
            return 6 + this.data.length;
        }

        @Override
        public void write(ByteBuffer out) throws IOException {
            LittleEndianUtils.writeUnsigned2Le(out, this.headerId);
            LittleEndianUtils.writeUnsigned4Le(out, this.data.length);
            out.put(this.data);
        }
    }

    public static interface SpecialPurposeDataFactory {
        public Z64SpecialPurposeData make(int var1, byte[] var2) throws IOException;
    }

    public static interface Z64SpecialPurposeData {
        public static final int PREFIX_LENGTH = 6;

        public int getHeaderId();

        public int size();

        public void write(ByteBuffer var1) throws IOException;
    }
}


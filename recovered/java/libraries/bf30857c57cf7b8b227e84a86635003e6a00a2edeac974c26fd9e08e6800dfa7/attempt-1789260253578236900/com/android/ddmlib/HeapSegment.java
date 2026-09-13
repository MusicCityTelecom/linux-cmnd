/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;

public final class HeapSegment
implements Comparable<HeapSegment> {
    protected int mHeapId;
    protected int mAllocationUnitSize;
    protected long mStartAddress;
    protected int mOffset;
    protected int mAllocationUnitCount;
    protected ByteBuffer mUsageData;
    private static final long INVALID_START_ADDRESS = -1L;

    public HeapSegment(ByteBuffer hpsgData) throws BufferUnderflowException {
        hpsgData.order(ByteOrder.BIG_ENDIAN);
        this.mHeapId = hpsgData.getInt();
        this.mAllocationUnitSize = hpsgData.get();
        this.mStartAddress = (long)hpsgData.getInt() & 0xFFFFFFFFL;
        this.mOffset = hpsgData.getInt();
        this.mAllocationUnitCount = hpsgData.getInt();
        this.mUsageData = hpsgData.slice();
        this.mUsageData.order(ByteOrder.BIG_ENDIAN);
    }

    public boolean isValid() {
        return this.mStartAddress != -1L;
    }

    public boolean canAppend(HeapSegment other) {
        return this.isValid() && other.isValid() && this.mHeapId == other.mHeapId && this.mAllocationUnitSize == other.mAllocationUnitSize && this.getEndAddress() == other.getStartAddress();
    }

    public boolean append(HeapSegment other) {
        if (this.canAppend(other)) {
            int pos = this.mUsageData.position();
            if (this.mUsageData.capacity() - this.mUsageData.limit() < other.mUsageData.limit()) {
                int newSize = this.mUsageData.limit() + other.mUsageData.limit();
                ByteBuffer newData = ByteBuffer.allocate(newSize * 2);
                this.mUsageData.rewind();
                newData.put(this.mUsageData);
                this.mUsageData = newData;
            }
            other.mUsageData.rewind();
            this.mUsageData.put(other.mUsageData);
            this.mUsageData.position(pos);
            this.mAllocationUnitCount += other.mAllocationUnitCount;
            other.mStartAddress = -1L;
            other.mUsageData = null;
            return true;
        }
        return false;
    }

    public long getStartAddress() {
        return this.mStartAddress + (long)this.mOffset;
    }

    public int getLength() {
        return this.mAllocationUnitSize * this.mAllocationUnitCount;
    }

    public long getEndAddress() {
        return this.getStartAddress() + (long)this.getLength();
    }

    public void rewindElements() {
        if (this.mUsageData != null) {
            this.mUsageData.rewind();
        }
    }

    public HeapSegmentElement getNextElement(HeapSegmentElement reuse) {
        try {
            if (reuse != null) {
                return reuse.set(this);
            }
            return new HeapSegmentElement(this);
        }
        catch (BufferUnderflowException bufferUnderflowException) {
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        return null;
    }

    public boolean equals(Object o3) {
        if (o3 instanceof HeapSegment) {
            return this.compareTo((HeapSegment)o3) == 0;
        }
        return false;
    }

    public int hashCode() {
        return this.mHeapId * 31 + this.mAllocationUnitSize * 31 + (int)this.mStartAddress * 31 + this.mOffset * 31 + this.mAllocationUnitCount * 31 + this.mUsageData.hashCode();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("HeapSegment { heap ").append(this.mHeapId).append(", start 0x").append(Integer.toHexString((int)this.getStartAddress())).append(", length ").append(this.getLength()).append(" }");
        return str.toString();
    }

    @Override
    public int compareTo(HeapSegment other) {
        if (this.mHeapId != other.mHeapId) {
            return this.mHeapId < other.mHeapId ? -1 : 1;
        }
        if (this.getStartAddress() != other.getStartAddress()) {
            return this.getStartAddress() < other.getStartAddress() ? -1 : 1;
        }
        if (this.mAllocationUnitSize != other.mAllocationUnitSize) {
            return this.mAllocationUnitSize < other.mAllocationUnitSize ? -1 : 1;
        }
        if (this.mStartAddress != other.mStartAddress) {
            return this.mStartAddress < other.mStartAddress ? -1 : 1;
        }
        if (this.mOffset != other.mOffset) {
            return this.mOffset < other.mOffset ? -1 : 1;
        }
        if (this.mAllocationUnitCount != other.mAllocationUnitCount) {
            return this.mAllocationUnitCount < other.mAllocationUnitCount ? -1 : 1;
        }
        if (this.mUsageData != other.mUsageData) {
            return this.mUsageData.compareTo(other.mUsageData);
        }
        return 0;
    }

    public static class HeapSegmentElement
    implements Comparable<HeapSegmentElement> {
        public static final int SOLIDITY_FREE = 0;
        public static final int SOLIDITY_HARD = 1;
        public static final int SOLIDITY_SOFT = 2;
        public static final int SOLIDITY_WEAK = 3;
        public static final int SOLIDITY_PHANTOM = 4;
        public static final int SOLIDITY_FINALIZABLE = 5;
        public static final int SOLIDITY_SWEEP = 6;
        public static final int SOLIDITY_INVALID = -1;
        public static final int KIND_OBJECT = 0;
        public static final int KIND_CLASS_OBJECT = 1;
        public static final int KIND_ARRAY_1 = 2;
        public static final int KIND_ARRAY_2 = 3;
        public static final int KIND_ARRAY_4 = 4;
        public static final int KIND_ARRAY_8 = 5;
        public static final int KIND_UNKNOWN = 6;
        public static final int KIND_NATIVE = 7;
        public static final int KIND_INVALID = -1;
        private static final int PARTIAL_MASK = 128;
        private int mSolidity;
        private int mKind;
        private int mLength;

        public HeapSegmentElement() {
            this.setSolidity(-1);
            this.setKind(-1);
            this.setLength(-1);
        }

        public HeapSegmentElement(HeapSegment hs) throws BufferUnderflowException, ParseException {
            this.set(hs);
        }

        public HeapSegmentElement set(HeapSegment hs) throws BufferUnderflowException, ParseException {
            ByteBuffer data = hs.mUsageData;
            int eState = data.get() & 0xFF;
            int eLen = (data.get() & 0xFF) + 1;
            while ((eState & 0x80) != 0) {
                int nextState = data.get() & 0xFF;
                if ((nextState & 0xFFFFFF7F) != (eState & 0xFFFFFF7F)) {
                    throw new ParseException("State mismatch", data.position());
                }
                eState = nextState;
                eLen += (data.get() & 0xFF) + 1;
            }
            this.setSolidity(eState & 7);
            this.setKind(eState >> 3 & 7);
            this.setLength(eLen * hs.mAllocationUnitSize);
            return this;
        }

        public int getSolidity() {
            return this.mSolidity;
        }

        public void setSolidity(int solidity) {
            this.mSolidity = solidity;
        }

        public int getKind() {
            return this.mKind;
        }

        public void setKind(int kind) {
            this.mKind = kind;
        }

        public int getLength() {
            return this.mLength;
        }

        public void setLength(int length) {
            this.mLength = length;
        }

        @Override
        public int compareTo(HeapSegmentElement other) {
            if (this.mLength != other.mLength) {
                return this.mLength < other.mLength ? -1 : 1;
            }
            return 0;
        }
    }
}


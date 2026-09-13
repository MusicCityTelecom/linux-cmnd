/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.log;

import com.android.ddmlib.log.EventContainer;
import com.android.ddmlib.log.EventValueDescription;
import com.android.ddmlib.log.InvalidTypeException;
import com.android.ddmlib.log.InvalidValueTypeException;
import com.android.ddmlib.log.LogReceiver;

final class GcEventContainer
extends EventContainer {
    public static final int GC_EVENT_TAG = 20001;
    private String processId;
    private long gcTime;
    private long bytesFreed;
    private long objectsFreed;
    private long actualSize;
    private long allowedSize;
    private long softLimit;
    private long objectsAllocated;
    private long bytesAllocated;
    private long zActualSize;
    private long zAllowedSize;
    private long zObjectsAllocated;
    private long zBytesAllocated;
    private long dlmallocFootprint;
    private long mallinfoTotalAllocatedSpace;
    private long externalLimit;
    private long externalBytesAllocated;

    GcEventContainer(LogReceiver.LogEntry entry, int tag, Object data) {
        super(entry, tag, data);
        this.init(data);
    }

    GcEventContainer(int tag, int pid, int tid, int sec, int nsec, Object data) {
        super(tag, pid, tid, sec, nsec, data);
        this.init(data);
    }

    private void init(Object data) {
        if (data instanceof Object[]) {
            Object[] values2 = (Object[])data;
            for (int i2 = 0; i2 < values2.length; ++i2) {
                if (!(values2[i2] instanceof Long)) continue;
                this.parseDvmHeapInfo((Long)values2[i2], i2);
            }
        }
    }

    @Override
    public EventContainer.EventValueType getType() {
        return EventContainer.EventValueType.LIST;
    }

    @Override
    public boolean testValue(int index, Object value, EventContainer.CompareMethod compareMethod) throws InvalidTypeException {
        if (index == 0 ? !(value instanceof String) : !(value instanceof Long)) {
            throw new InvalidTypeException();
        }
        switch (compareMethod) {
            case EQUAL_TO: {
                if (index == 0) {
                    return this.processId.equals(value);
                }
                return this.getValueAsLong(index) == ((Long)value).longValue();
            }
            case LESSER_THAN: {
                return this.getValueAsLong(index) <= (Long)value;
            }
            case LESSER_THAN_STRICT: {
                return this.getValueAsLong(index) < (Long)value;
            }
            case GREATER_THAN: {
                return this.getValueAsLong(index) >= (Long)value;
            }
            case GREATER_THAN_STRICT: {
                return this.getValueAsLong(index) > (Long)value;
            }
            case BIT_CHECK: {
                return (this.getValueAsLong(index) & (Long)value) != 0L;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public Object getValue(int valueIndex) {
        if (valueIndex == 0) {
            return this.processId;
        }
        try {
            return this.getValueAsLong(valueIndex);
        }
        catch (InvalidTypeException invalidTypeException) {
            return null;
        }
    }

    @Override
    public double getValueAsDouble(int valueIndex) throws InvalidTypeException {
        return this.getValueAsLong(valueIndex);
    }

    @Override
    public String getValueAsString(int valueIndex) {
        switch (valueIndex) {
            case 0: {
                return this.processId;
            }
        }
        try {
            return Long.toString(this.getValueAsLong(valueIndex));
        }
        catch (InvalidTypeException invalidTypeException) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    static EventValueDescription[] getValueDescriptions() {
        try {
            return new EventValueDescription[]{new EventValueDescription("Process Name", EventContainer.EventValueType.STRING), new EventValueDescription("GC Time", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.MILLISECONDS), new EventValueDescription("Freed Objects", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.OBJECTS), new EventValueDescription("Freed Bytes", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Soft Limit", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Actual Size (aggregate)", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Allowed Size (aggregate)", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Allocated Objects (aggregate)", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.OBJECTS), new EventValueDescription("Allocated Bytes (aggregate)", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Actual Size", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Allowed Size", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Allocated Objects", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.OBJECTS), new EventValueDescription("Allocated Bytes", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Actual Size (zygote)", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Allowed Size (zygote)", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Allocated Objects (zygote)", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.OBJECTS), new EventValueDescription("Allocated Bytes (zygote)", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("External Allocation Limit", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("External Bytes Allocated", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("dlmalloc Footprint", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES), new EventValueDescription("Malloc Info: Total Allocated Space", EventContainer.EventValueType.LONG, EventValueDescription.ValueType.BYTES)};
        }
        catch (InvalidValueTypeException e2) {
            assert (false);
            return null;
        }
    }

    private void parseDvmHeapInfo(long data, int index) {
        switch (index) {
            case 0: {
                this.gcTime = GcEventContainer.float12ToInt((int)(data >> 12 & 0xFFFL));
                this.bytesFreed = GcEventContainer.float12ToInt((int)(data & 0xFFFL));
                byte[] dataArray = new byte[8];
                GcEventContainer.put64bitsToArray(data, dataArray, 0);
                this.processId = new String(dataArray, 0, 5);
                break;
            }
            case 1: {
                this.objectsFreed = GcEventContainer.float12ToInt((int)(data >> 48 & 0xFFFL));
                this.actualSize = GcEventContainer.float12ToInt((int)(data >> 36 & 0xFFFL));
                this.allowedSize = GcEventContainer.float12ToInt((int)(data >> 24 & 0xFFFL));
                this.objectsAllocated = GcEventContainer.float12ToInt((int)(data >> 12 & 0xFFFL));
                this.bytesAllocated = GcEventContainer.float12ToInt((int)(data & 0xFFFL));
                break;
            }
            case 2: {
                this.softLimit = GcEventContainer.float12ToInt((int)(data >> 48 & 0xFFFL));
                this.zActualSize = GcEventContainer.float12ToInt((int)(data >> 36 & 0xFFFL));
                this.zAllowedSize = GcEventContainer.float12ToInt((int)(data >> 24 & 0xFFFL));
                this.zObjectsAllocated = GcEventContainer.float12ToInt((int)(data >> 12 & 0xFFFL));
                this.zBytesAllocated = GcEventContainer.float12ToInt((int)(data & 0xFFFL));
                break;
            }
            case 3: {
                this.dlmallocFootprint = GcEventContainer.float12ToInt((int)(data >> 36 & 0xFFFL));
                this.mallinfoTotalAllocatedSpace = GcEventContainer.float12ToInt((int)(data >> 24 & 0xFFFL));
                this.externalLimit = GcEventContainer.float12ToInt((int)(data >> 12 & 0xFFFL));
                this.externalBytesAllocated = GcEventContainer.float12ToInt((int)(data & 0xFFFL));
                break;
            }
        }
    }

    private static long float12ToInt(int f12) {
        return (f12 & 0x1FF) << (f12 >>> 9) * 4;
    }

    private static void put64bitsToArray(long value, byte[] dest, int offset) {
        dest[offset + 7] = (byte)(value & 0xFFL);
        dest[offset + 6] = (byte)((value & 0xFF00L) >> 8);
        dest[offset + 5] = (byte)((value & 0xFF0000L) >> 16);
        dest[offset + 4] = (byte)((value & 0xFF000000L) >> 24);
        dest[offset + 3] = (byte)((value & 0xFF00000000L) >> 32);
        dest[offset + 2] = (byte)((value & 0xFF0000000000L) >> 40);
        dest[offset + 1] = (byte)((value & 0xFF000000000000L) >> 48);
        dest[offset + 0] = (byte)((value & 0xFF00000000000000L) >> 56);
    }

    private long getValueAsLong(int valueIndex) throws InvalidTypeException {
        switch (valueIndex) {
            case 0: {
                throw new InvalidTypeException();
            }
            case 1: {
                return this.gcTime;
            }
            case 2: {
                return this.objectsFreed;
            }
            case 3: {
                return this.bytesFreed;
            }
            case 4: {
                return this.softLimit;
            }
            case 5: {
                return this.actualSize;
            }
            case 6: {
                return this.allowedSize;
            }
            case 7: {
                return this.objectsAllocated;
            }
            case 8: {
                return this.bytesAllocated;
            }
            case 9: {
                return this.actualSize - this.zActualSize;
            }
            case 10: {
                return this.allowedSize - this.zAllowedSize;
            }
            case 11: {
                return this.objectsAllocated - this.zObjectsAllocated;
            }
            case 12: {
                return this.bytesAllocated - this.zBytesAllocated;
            }
            case 13: {
                return this.zActualSize;
            }
            case 14: {
                return this.zAllowedSize;
            }
            case 15: {
                return this.zObjectsAllocated;
            }
            case 16: {
                return this.zBytesAllocated;
            }
            case 17: {
                return this.externalLimit;
            }
            case 18: {
                return this.externalBytesAllocated;
            }
            case 19: {
                return this.dlmallocFootprint;
            }
            case 20: {
                return this.mallinfoTotalAllocatedSpace;
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}


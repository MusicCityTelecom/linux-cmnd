/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.log;

import com.android.ddmlib.utils.ArrayHelper;
import java.security.InvalidParameterException;

public final class LogReceiver {
    private static final int ENTRY_HEADER_SIZE = 20;
    private LogEntry mCurrentEntry;
    private byte[] mEntryHeaderBuffer = new byte[20];
    private int mEntryHeaderOffset = 0;
    private int mEntryDataOffset = 0;
    private ILogListener mListener;
    private boolean mIsCancelled = false;

    public LogReceiver(ILogListener listener) {
        this.mListener = listener;
    }

    public void parseNewData(byte[] data, int offset, int length) {
        if (this.mListener != null) {
            this.mListener.newData(data, offset, length);
        }
        while (length > 0 && !this.mIsCancelled) {
            if (this.mCurrentEntry == null) {
                if (this.mEntryHeaderOffset + length < 20) {
                    System.arraycopy(data, offset, this.mEntryHeaderBuffer, this.mEntryHeaderOffset, length);
                    this.mEntryHeaderOffset += length;
                    return;
                }
                if (this.mEntryHeaderOffset != 0) {
                    int size = 20 - this.mEntryHeaderOffset;
                    System.arraycopy(data, offset, this.mEntryHeaderBuffer, this.mEntryHeaderOffset, size);
                    this.mCurrentEntry = this.createEntry(this.mEntryHeaderBuffer, 0);
                    this.mEntryHeaderOffset = 0;
                    offset += size;
                    length -= size;
                } else {
                    this.mCurrentEntry = this.createEntry(data, offset);
                    offset += 20;
                    length -= 20;
                }
            }
            if (length >= this.mCurrentEntry.len - this.mEntryDataOffset) {
                int dataSize = this.mCurrentEntry.len - this.mEntryDataOffset;
                System.arraycopy(data, offset, this.mCurrentEntry.data, this.mEntryDataOffset, dataSize);
                if (this.mListener != null) {
                    this.mListener.newEntry(this.mCurrentEntry);
                }
                this.mEntryDataOffset = 0;
                this.mCurrentEntry = null;
                offset += dataSize;
                length -= dataSize;
                continue;
            }
            System.arraycopy(data, offset, this.mCurrentEntry.data, this.mEntryDataOffset, length);
            this.mEntryDataOffset += length;
            return;
        }
    }

    public boolean isCancelled() {
        return this.mIsCancelled;
    }

    public void cancel() {
        this.mIsCancelled = true;
    }

    private LogEntry createEntry(byte[] data, int offset) {
        if (data.length < offset + 20) {
            throw new InvalidParameterException("Buffer not big enough to hold full LoggerEntry header");
        }
        LogEntry entry = new LogEntry();
        entry.len = ArrayHelper.swapU16bitFromArray(data, offset);
        entry.pid = ArrayHelper.swap32bitFromArray(data, offset += 4);
        entry.tid = ArrayHelper.swap32bitFromArray(data, offset += 4);
        entry.sec = ArrayHelper.swap32bitFromArray(data, offset += 4);
        entry.nsec = ArrayHelper.swap32bitFromArray(data, offset += 4);
        offset += 4;
        entry.data = new byte[entry.len];
        return entry;
    }

    public static interface ILogListener {
        public void newEntry(LogEntry var1);

        public void newData(byte[] var1, int var2, int var3);
    }

    public static final class LogEntry {
        public int len;
        public int pid;
        public int tid;
        public int sec;
        public int nsec;
        public byte[] data;
    }
}


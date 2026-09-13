/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.IStackTraceInfo;

public final class ThreadInfo
implements IStackTraceInfo {
    private int mThreadId;
    private String mThreadName;
    private int mStatus;
    private int mTid;
    private int mUtime;
    private int mStime;
    private boolean mIsDaemon;
    private StackTraceElement[] mTrace;
    private long mTraceTime;

    ThreadInfo(int threadId, String threadName) {
        this.mThreadId = threadId;
        this.mThreadName = threadName;
        this.mStatus = -1;
    }

    void updateThread(int status, int tid, int utime, int stime, boolean isDaemon) {
        this.mStatus = status;
        this.mTid = tid;
        this.mUtime = utime;
        this.mStime = stime;
        this.mIsDaemon = isDaemon;
    }

    void setStackCall(StackTraceElement[] trace) {
        this.mTrace = trace;
        this.mTraceTime = System.currentTimeMillis();
    }

    public int getThreadId() {
        return this.mThreadId;
    }

    public String getThreadName() {
        return this.mThreadName;
    }

    void setThreadName(String name) {
        this.mThreadName = name;
    }

    public int getTid() {
        return this.mTid;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getUtime() {
        return this.mUtime;
    }

    public int getStime() {
        return this.mStime;
    }

    public boolean isDaemon() {
        return this.mIsDaemon;
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return this.mTrace;
    }

    public long getStackCallTime() {
        return this.mTraceTime;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.logcat;

import com.android.ddmlib.Log;
import com.android.ddmlib.logcat.LogCatLongEpochMessageParser;
import com.android.ddmlib.logcat.LogCatTimestamp;
import java.time.Instant;
import java.util.Objects;

public final class LogCatHeader {
    private final Log.LogLevel mLogLevel;
    private final int mPid;
    private final int mTid;
    private final String mAppName;
    private final String mTag;
    private final Instant mTimestampInstant;
    private final LogCatTimestamp mTimestamp;

    public LogCatHeader(Log.LogLevel logLevel, int pid, int tid, String appName, String tag, Instant timestampInstant) {
        this.mLogLevel = logLevel;
        this.mPid = pid;
        this.mTid = tid;
        this.mAppName = appName;
        this.mTag = tag;
        this.mTimestampInstant = timestampInstant;
        this.mTimestamp = null;
    }

    @Deprecated
    public LogCatHeader(Log.LogLevel logLevel, int pid, int tid, String appName, String tag, LogCatTimestamp timestamp) {
        this.mLogLevel = logLevel;
        this.mPid = pid;
        this.mTid = tid;
        this.mAppName = appName;
        this.mTag = tag;
        this.mTimestampInstant = null;
        this.mTimestamp = timestamp;
    }

    public Log.LogLevel getLogLevel() {
        return this.mLogLevel;
    }

    public int getPid() {
        return this.mPid;
    }

    public int getTid() {
        return this.mTid;
    }

    public String getAppName() {
        return this.mAppName;
    }

    public String getTag() {
        return this.mTag;
    }

    public boolean isBefore(LogCatHeader header) {
        if (this.mTimestampInstant == null) {
            assert (this.mTimestamp != null);
            assert (header.mTimestamp != null);
            return this.mTimestamp.isBefore(header.mTimestamp);
        }
        assert (header.mTimestampInstant != null);
        return this.mTimestampInstant.isBefore(header.mTimestampInstant);
    }

    public Instant getTimestampInstant() {
        return this.mTimestampInstant;
    }

    @Deprecated
    public LogCatTimestamp getTimestamp() {
        return this.mTimestamp;
    }

    public boolean equals(Object object) {
        if (!(object instanceof LogCatHeader)) {
            return false;
        }
        LogCatHeader header = (LogCatHeader)object;
        return this.mLogLevel.equals((Object)header.mLogLevel) && this.mPid == header.mPid && this.mTid == header.mTid && this.mAppName.equals(header.mAppName) && this.mTag.equals(header.mTag) && Objects.equals(this.mTimestampInstant, header.mTimestampInstant) && Objects.equals(this.mTimestamp, header.mTimestamp);
    }

    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.mLogLevel.hashCode();
        hashCode = 31 * hashCode + this.mPid;
        hashCode = 31 * hashCode + this.mTid;
        hashCode = 31 * hashCode + this.mAppName.hashCode();
        hashCode = 31 * hashCode + this.mTag.hashCode();
        hashCode = 31 * hashCode + Objects.hashCode(this.mTimestampInstant);
        hashCode = 31 * hashCode + Objects.hashCode(this.mTimestamp);
        return hashCode;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (this.mTimestampInstant == null) {
            builder.append(this.mTimestamp);
        } else {
            LogCatLongEpochMessageParser.EPOCH_TIME_FORMATTER.formatTo(this.mTimestampInstant, builder);
        }
        builder.append(": ").append(this.mLogLevel.getPriorityLetter()).append('/').append(this.mTag).append('(').append(this.mPid).append(')');
        return builder.toString();
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.logcat;

import com.android.ddmlib.Log;
import com.android.ddmlib.logcat.LogCatHeader;
import com.android.ddmlib.logcat.LogCatTimestamp;

public final class LogCatMessage {
    private final LogCatHeader mHeader;
    private final String mMessage;

    @Deprecated
    public LogCatMessage(Log.LogLevel logLevel, int pid, int tid, String appName, String tag, LogCatTimestamp timestamp, String msg) {
        this(new LogCatHeader(logLevel, pid, tid, appName, tag, timestamp), msg);
    }

    public LogCatMessage(LogCatHeader header, String msg) {
        this.mHeader = header;
        this.mMessage = msg;
    }

    public LogCatMessage(Log.LogLevel logLevel, String message) {
        this(logLevel, -1, -1, "", "", LogCatTimestamp.ZERO, message);
    }

    public LogCatHeader getHeader() {
        return this.mHeader;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public Log.LogLevel getLogLevel() {
        return this.mHeader.getLogLevel();
    }

    public int getPid() {
        return this.mHeader.getPid();
    }

    public int getTid() {
        return this.mHeader.getTid();
    }

    public String getAppName() {
        return this.mHeader.getAppName();
    }

    public String getTag() {
        return this.mHeader.getTag();
    }

    public LogCatTimestamp getTimestamp() {
        return this.mHeader.getTimestamp();
    }

    public String toString() {
        return this.mHeader.toString() + ": " + this.mMessage;
    }
}


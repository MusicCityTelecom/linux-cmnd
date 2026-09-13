/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.logcat;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.logcat.LogCatHeader;
import com.android.ddmlib.logcat.LogCatMessage;
import com.android.ddmlib.logcat.LogCatTimestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogCatMessageParser {
    private static final Pattern DATE_TIME = Pattern.compile("\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d");
    static final Pattern PROCESS_ID = Pattern.compile("\\d+");
    static final Pattern THREAD_ID = Pattern.compile("\\w+");
    static final Pattern PRIORITY = Pattern.compile("[VDIWEAF]");
    static final Pattern TAG = Pattern.compile(".*?");
    private static final Pattern HEADER = Pattern.compile("^\\[ (" + DATE_TIME + ") +(" + PROCESS_ID + "): *(" + THREAD_ID + ") (" + PRIORITY + ")/(" + TAG + ") +]$");
    LogCatHeader mPrevHeader;

    public LogCatHeader processLogHeader(String line, IDevice device) {
        Matcher matcher = HEADER.matcher(line);
        if (!matcher.matches()) {
            return null;
        }
        LogCatTimestamp dateTime = LogCatTimestamp.fromString(matcher.group(1));
        int processId = LogCatMessageParser.parseProcessId(matcher.group(2));
        int threadId = LogCatMessageParser.parseThreadId(matcher.group(3));
        Log.LogLevel priority = LogCatMessageParser.parsePriority(matcher.group(4));
        String tag = matcher.group(5);
        this.mPrevHeader = new LogCatHeader(priority, processId, threadId, LogCatMessageParser.getPackageName(device, processId), tag, dateTime);
        return this.mPrevHeader;
    }

    static int parseProcessId(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException exception) {
            return -1;
        }
    }

    static int parseThreadId(String string) {
        try {
            return Integer.decode(string);
        }
        catch (NumberFormatException exception) {
            return -1;
        }
    }

    static Log.LogLevel parsePriority(String string) {
        Log.LogLevel priority = Log.LogLevel.getByLetterString(string);
        if (priority == null) {
            if (!string.equals("F")) {
                return Log.LogLevel.WARN;
            }
            return Log.LogLevel.ASSERT;
        }
        return priority;
    }

    static String getPackageName(IDevice device, int processId) {
        if (device == null || processId == -1) {
            return "?";
        }
        String name = device.getClientName(processId);
        if (name == null || name.isEmpty()) {
            return "?";
        }
        return name;
    }

    public List<LogCatMessage> processLogLines(String[] lines, IDevice device) {
        ArrayList<LogCatMessage> messages = new ArrayList<LogCatMessage>(lines.length);
        for (String line : lines) {
            if (line.isEmpty() || this.processLogHeader(line, device) != null || this.mPrevHeader == null) continue;
            messages.add(new LogCatMessage(this.mPrevHeader, line));
        }
        return messages;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.logcat;

import com.android.ddmlib.IDevice;
import com.android.ddmlib.Log;
import com.android.ddmlib.logcat.LogCatHeader;
import com.android.ddmlib.logcat.LogCatMessageParser;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogCatLongEpochMessageParser
extends LogCatMessageParser {
    public static final Pattern EPOCH_TIME = Pattern.compile("\\d+\\.\\d\\d\\d");
    private static final Pattern HEADER = Pattern.compile("^\\[ +(" + EPOCH_TIME + ") +(" + PROCESS_ID + "): *(" + THREAD_ID + ") (" + PRIORITY + ")/(" + TAG + ") +]$");
    public static final DateTimeFormatter EPOCH_TIME_FORMATTER = new DateTimeFormatterBuilder().appendValue(ChronoField.INSTANT_SECONDS).appendFraction(ChronoField.MILLI_OF_SECOND, 3, 3, true).toFormatter(Locale.ROOT);

    @Override
    public LogCatHeader processLogHeader(String string, IDevice device) {
        Matcher matcher = HEADER.matcher(string);
        if (!matcher.matches()) {
            return null;
        }
        Instant timestamp = EPOCH_TIME_FORMATTER.parse((CharSequence)matcher.group(1), Instant::from);
        int processId = LogCatLongEpochMessageParser.parseProcessId(matcher.group(2));
        int threadId = LogCatLongEpochMessageParser.parseThreadId(matcher.group(3));
        Log.LogLevel priority = LogCatLongEpochMessageParser.parsePriority(matcher.group(4));
        String tag = matcher.group(5);
        this.mPrevHeader = new LogCatHeader(priority, processId, threadId, LogCatLongEpochMessageParser.getPackageName(device, processId), tag, timestamp);
        return this.mPrevHeader;
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.logcat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogCatTimestamp {
    public static final LogCatTimestamp ZERO = new LogCatTimestamp(1, 1, 0, 0, 0, 0);
    private final int mMonth;
    private final int mDay;
    private final int mHour;
    private final int mMinute;
    private final int mSecond;
    private final int mMilli;
    private static final Pattern sTimePattern = Pattern.compile("^(\\d\\d)-(\\d\\d)\\s(\\d\\d):(\\d\\d):(\\d\\d)\\.(\\d+)$");

    public static LogCatTimestamp fromString(String timeString) {
        int millisecond;
        Matcher matcher = sTimePattern.matcher(timeString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid timestamp. Expected MM-DD HH:MM:SS:mmm");
        }
        int month = Integer.parseInt(matcher.group(1));
        int day = Integer.parseInt(matcher.group(2));
        int hour = Integer.parseInt(matcher.group(3));
        int minute = Integer.parseInt(matcher.group(4));
        int second = Integer.parseInt(matcher.group(5));
        for (millisecond = Integer.parseInt(matcher.group(6)); millisecond >= 1000; millisecond /= 10) {
        }
        return new LogCatTimestamp(month, day, hour, minute, second, millisecond);
    }

    public LogCatTimestamp(int month, int day, int hour, int minute, int second, int milli) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(String.format("Month should be between 1-12: %d", month));
        }
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException(String.format("Day should be between 1-31: %d", day));
        }
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException(String.format("Hour should be between 0-23: %d", hour));
        }
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException(String.format("Minute should be between 0-59: %d", minute));
        }
        if (second < 0 || second > 59) {
            throw new IllegalArgumentException(String.format("Second should be between 0-59 %d", second));
        }
        if (milli < 0 || milli > 999) {
            throw new IllegalArgumentException(String.format("Millisecond should be between 0-999: %d", milli));
        }
        this.mMonth = month;
        this.mDay = day;
        this.mHour = hour;
        this.mMinute = minute;
        this.mSecond = second;
        this.mMilli = milli;
    }

    public boolean isBefore(LogCatTimestamp other) {
        if (this.mMonth == 12 && other.mMonth == 1) {
            return true;
        }
        if (this.mMonth == 1 && other.mMonth == 12) {
            return false;
        }
        if (this.mMonth < other.mMonth) {
            return true;
        }
        if (this.mMonth > other.mMonth) {
            return false;
        }
        if (this.mDay < other.mDay) {
            return true;
        }
        if (this.mDay > other.mDay) {
            return false;
        }
        if (this.mHour < other.mHour) {
            return true;
        }
        if (this.mHour > other.mHour) {
            return false;
        }
        if (this.mMinute < other.mMinute) {
            return true;
        }
        if (this.mMinute > other.mMinute) {
            return false;
        }
        if (this.mSecond < other.mSecond) {
            return true;
        }
        if (this.mSecond > other.mSecond) {
            return false;
        }
        if (this.mMilli < other.mMilli) {
            return true;
        }
        if (this.mMilli > other.mMilli) {
            return false;
        }
        return false;
    }

    public boolean equals(Object object) {
        if (!(object instanceof LogCatTimestamp)) {
            return false;
        }
        LogCatTimestamp timestamp = (LogCatTimestamp)object;
        return this.mMonth == timestamp.mMonth && this.mDay == timestamp.mDay && this.mHour == timestamp.mHour && this.mMinute == timestamp.mMinute && this.mSecond == timestamp.mSecond && this.mMilli == timestamp.mMilli;
    }

    public int hashCode() {
        int hashCode = 17;
        hashCode = 31 * hashCode + this.mMonth;
        hashCode = 31 * hashCode + this.mDay;
        hashCode = 31 * hashCode + this.mHour;
        hashCode = 31 * hashCode + this.mMinute;
        hashCode = 31 * hashCode + this.mSecond;
        hashCode = 31 * hashCode + this.mMilli;
        return hashCode;
    }

    public String toString() {
        return String.format("%02d-%02d %02d:%02d:%02d.%03d", this.mMonth, this.mDay, this.mHour, this.mMinute, this.mSecond, this.mMilli);
    }
}


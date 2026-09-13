/*
 * Decompiled with CFR 0.152.
 */
package com.terracotta.management.resource.services.utils;

public class TimeStringParser {
    public static long parseTime(String timeString) throws NumberFormatException {
        if (timeString.endsWith("d")) {
            String days = timeString.substring(0, timeString.length() - 1);
            return System.currentTimeMillis() - (long)(Integer.parseInt(days) * 24 * 60 * 60) * 1000L;
        }
        if (timeString.endsWith("h")) {
            String hours = timeString.substring(0, timeString.length() - 1);
            return System.currentTimeMillis() - (long)(Integer.parseInt(hours) * 60 * 60) * 1000L;
        }
        if (timeString.endsWith("m")) {
            String minutes = timeString.substring(0, timeString.length() - 1);
            return System.currentTimeMillis() - (long)(Integer.parseInt(minutes) * 60) * 1000L;
        }
        if (timeString.endsWith("s")) {
            String seconds = timeString.substring(0, timeString.length() - 1);
            return System.currentTimeMillis() - (long)Integer.parseInt(seconds) * 1000L;
        }
        return Long.parseLong(timeString);
    }
}


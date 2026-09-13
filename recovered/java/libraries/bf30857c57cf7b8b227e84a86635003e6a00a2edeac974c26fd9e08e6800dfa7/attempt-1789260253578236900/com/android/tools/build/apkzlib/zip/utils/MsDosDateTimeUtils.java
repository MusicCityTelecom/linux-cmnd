/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.apkzlib.zip.utils;

import com.google.common.base.Verify;
import java.util.Calendar;
import java.util.Date;

public class MsDosDateTimeUtils {
    private MsDosDateTimeUtils() {
    }

    public static int packTime(long time) {
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date(time));
        int seconds = c2.get(13);
        int minutes = c2.get(12);
        int hours = c2.get(11);
        return hours << 11 | minutes << 5 | seconds / 2;
    }

    public static int packCurrentTime() {
        return MsDosDateTimeUtils.packTime(new Date().getTime());
    }

    public static int packDate(long time) {
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date(time));
        int day = c2.get(5);
        int month = c2.get(2) + 1;
        int year = c2.get(1) - 1980;
        Verify.verify(year >= 0 && year < 128);
        return year << 9 | month << 5 | day;
    }

    public static int packCurrentDate() {
        return MsDosDateTimeUtils.packDate(new Date().getTime());
    }
}


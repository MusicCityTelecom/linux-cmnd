/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.savedrequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public final class FastHttpDateFormat {
    protected static final SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
    protected static final SimpleDateFormat[] formats = new SimpleDateFormat[]{new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US), new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US), new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US)};
    protected static final TimeZone gmtZone = TimeZone.getTimeZone("GMT");
    protected static long currentDateGenerated;
    protected static String currentDate;
    protected static final HashMap<Long, String> formatCache;
    protected static final HashMap<String, Long> parseCache;

    private FastHttpDateFormat() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String formatDate(long value, DateFormat threadLocalformat) {
        String newDate;
        String cachedDate = null;
        Long longValue = value;
        try {
            cachedDate = formatCache.get(longValue);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (cachedDate != null) {
            return cachedDate;
        }
        Date dateValue = new Date(value);
        if (threadLocalformat != null) {
            newDate = threadLocalformat.format(dateValue);
            HashMap<Long, String> hashMap = formatCache;
            synchronized (hashMap) {
                FastHttpDateFormat.updateCache(formatCache, longValue, newDate);
            }
        }
        HashMap<Long, String> hashMap = formatCache;
        synchronized (hashMap) {
            newDate = format.format(dateValue);
            FastHttpDateFormat.updateCache(formatCache, longValue, newDate);
        }
        return newDate;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static String getCurrentDate() {
        long now = System.currentTimeMillis();
        if (now - currentDateGenerated > 1000L) {
            SimpleDateFormat simpleDateFormat = format;
            synchronized (simpleDateFormat) {
                if (now - currentDateGenerated > 1000L) {
                    currentDateGenerated = now;
                    currentDate = format.format(new Date(now));
                }
            }
        }
        return currentDate;
    }

    private static Long internalParseDate(String value, DateFormat[] formats) {
        Date date = null;
        for (int i = 0; date == null && i < formats.length; ++i) {
            try {
                date = formats[i].parse(value);
                continue;
            }
            catch (ParseException parseException) {
                // empty catch block
            }
        }
        if (date == null) {
            return null;
        }
        return date.getTime();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static long parseDate(String value, DateFormat[] threadLocalformats) {
        Long date;
        Long cachedDate = null;
        try {
            cachedDate = parseCache.get(value);
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (cachedDate != null) {
            return cachedDate;
        }
        if (threadLocalformats != null) {
            date = FastHttpDateFormat.internalParseDate(value, threadLocalformats);
            HashMap<String, Long> hashMap = parseCache;
            synchronized (hashMap) {
                FastHttpDateFormat.updateCache(parseCache, value, date);
            }
        }
        HashMap<String, Long> hashMap = parseCache;
        synchronized (hashMap) {
            date = FastHttpDateFormat.internalParseDate(value, formats);
            FastHttpDateFormat.updateCache(parseCache, value, date);
        }
        return date != null ? date : -1L;
    }

    private static void updateCache(HashMap cache, Object key, Object value) {
        if (value == null) {
            return;
        }
        if (cache.size() > 1000) {
            cache.clear();
        }
        cache.put(key, value);
    }

    static {
        format.setTimeZone(gmtZone);
        formats[0].setTimeZone(gmtZone);
        formats[1].setTimeZone(gmtZone);
        formats[2].setTimeZone(gmtZone);
        currentDateGenerated = 0L;
        currentDate = null;
        formatCache = new HashMap();
        parseCache = new HashMap();
    }
}


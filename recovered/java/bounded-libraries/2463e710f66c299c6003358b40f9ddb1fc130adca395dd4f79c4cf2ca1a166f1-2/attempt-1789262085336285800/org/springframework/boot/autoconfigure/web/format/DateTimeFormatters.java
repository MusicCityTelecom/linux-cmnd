/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.web.format;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import org.springframework.util.StringUtils;

public class DateTimeFormatters {
    private DateTimeFormatter dateFormatter;
    private String datePattern;
    private DateTimeFormatter timeFormatter;
    private DateTimeFormatter dateTimeFormatter;

    public DateTimeFormatters dateFormat(String pattern) {
        if (DateTimeFormatters.isIso(pattern)) {
            this.dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            this.datePattern = "yyyy-MM-dd";
        } else {
            this.dateFormatter = DateTimeFormatters.formatter(pattern);
            this.datePattern = pattern;
        }
        return this;
    }

    public DateTimeFormatters timeFormat(String pattern) {
        this.timeFormatter = DateTimeFormatters.isIso(pattern) ? DateTimeFormatter.ISO_LOCAL_TIME : (DateTimeFormatters.isIsoOffset(pattern) ? DateTimeFormatter.ISO_OFFSET_TIME : DateTimeFormatters.formatter(pattern));
        return this;
    }

    public DateTimeFormatters dateTimeFormat(String pattern) {
        this.dateTimeFormatter = DateTimeFormatters.isIso(pattern) ? DateTimeFormatter.ISO_LOCAL_DATE_TIME : (DateTimeFormatters.isIsoOffset(pattern) ? DateTimeFormatter.ISO_OFFSET_DATE_TIME : DateTimeFormatters.formatter(pattern));
        return this;
    }

    DateTimeFormatter getDateFormatter() {
        return this.dateFormatter;
    }

    String getDatePattern() {
        return this.datePattern;
    }

    DateTimeFormatter getTimeFormatter() {
        return this.timeFormatter;
    }

    DateTimeFormatter getDateTimeFormatter() {
        return this.dateTimeFormatter;
    }

    boolean isCustomized() {
        return this.dateFormatter != null || this.timeFormatter != null || this.dateTimeFormatter != null;
    }

    private static DateTimeFormatter formatter(String pattern) {
        return StringUtils.hasText((String)pattern) ? DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.SMART) : null;
    }

    private static boolean isIso(String pattern) {
        return "iso".equalsIgnoreCase(pattern);
    }

    private static boolean isIsoOffset(String pattern) {
        return "isooffset".equalsIgnoreCase(pattern) || "iso-offset".equalsIgnoreCase(pattern);
    }
}


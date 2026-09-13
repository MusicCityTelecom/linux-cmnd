/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.datetime.extensions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.groovy.datetime.extensions.DateTimeExtensions;

public final class DateTimeStaticExtensions {
    private DateTimeStaticExtensions() {
    }

    public static LocalDate parse(LocalDate type, CharSequence text, String pattern) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parse(LocalDateTime type, CharSequence text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalTime parse(LocalTime type, CharSequence text, String pattern) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static MonthDay parse(MonthDay type, CharSequence text, String pattern) {
        return MonthDay.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static OffsetDateTime parse(OffsetDateTime type, CharSequence text, String pattern) {
        return OffsetDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static OffsetTime parse(OffsetTime type, CharSequence text, String pattern) {
        return OffsetTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static Year parse(Year type, CharSequence text, String pattern) {
        return Year.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static YearMonth parse(YearMonth type, CharSequence text, String pattern) {
        return YearMonth.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static ZonedDateTime parse(ZonedDateTime type, CharSequence text, String pattern) {
        return ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    public static ZoneOffset systemDefault(ZoneOffset type) {
        return DateTimeExtensions.getOffset(ZoneId.systemDefault());
    }

    public static Period between(Period type, Year startInclusive, Year endExclusive) {
        MonthDay now = MonthDay.of(Month.JANUARY, 1);
        return Period.between(DateTimeExtensions.leftShift(startInclusive, now), DateTimeExtensions.leftShift(endExclusive, now)).withDays(0).withMonths(0);
    }

    public static Period between(Period type, YearMonth startInclusive, YearMonth endExclusive) {
        int dayOfMonth = 1;
        return Period.between(DateTimeExtensions.leftShift(startInclusive, dayOfMonth), DateTimeExtensions.leftShift(endExclusive, dayOfMonth)).withDays(0);
    }
}


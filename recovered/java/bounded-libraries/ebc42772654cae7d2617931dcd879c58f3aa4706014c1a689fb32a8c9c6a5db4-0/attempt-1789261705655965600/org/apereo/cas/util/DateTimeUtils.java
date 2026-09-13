/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import org.apereo.cas.util.function.FunctionUtils;

public final class DateTimeUtils {
    public static LocalDateTime localDateTimeOf(String value) {
        LocalDate ld;
        LocalDateTime result = null;
        try {
            result = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
        catch (Exception e) {
            result = null;
        }
        if (result == null) {
            try {
                result = LocalDateTime.parse(value, DateTimeFormatter.ISO_ZONED_DATE_TIME);
            }
            catch (Exception e) {
                result = null;
            }
        }
        if (result == null) {
            try {
                result = LocalDateTime.parse(value);
            }
            catch (Exception e) {
                result = null;
            }
        }
        if (result == null) {
            try {
                result = LocalDateTime.parse(value.toUpperCase(), DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
            }
            catch (Exception e) {
                result = null;
            }
        }
        if (result == null) {
            try {
                result = LocalDateTime.parse(value.toUpperCase(), DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));
            }
            catch (Exception e) {
                result = null;
            }
        }
        if (result == null) {
            try {
                result = LocalDateTime.parse(value, DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"));
            }
            catch (Exception e) {
                result = null;
            }
        }
        if (result == null) {
            try {
                ld = LocalDate.parse(value, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                result = LocalDateTime.of(ld, LocalTime.now(ZoneId.systemDefault()));
            }
            catch (Exception e) {
                result = null;
            }
        }
        if (result == null) {
            try {
                ld = LocalDate.parse(value);
                result = LocalDateTime.of(ld, LocalTime.now(ZoneId.systemDefault()));
            }
            catch (Exception e) {
                result = null;
            }
        }
        return result;
    }

    public static LocalDateTime localDateTimeOf(long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
    }

    public static LocalDateTime localDateTimeOf(Date time) {
        return DateTimeUtils.localDateTimeOf(time.getTime());
    }

    public static LocalDate localDateTime(long time) {
        return LocalDate.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
    }

    public static ZonedDateTime zonedDateTimeOf(String value) {
        List<DateTimeFormatter> parsers = List.of(DateTimeFormatter.ISO_ZONED_DATE_TIME, DateTimeFormatter.RFC_1123_DATE_TIME);
        return parsers.stream().map(parser -> (ZonedDateTime)FunctionUtils.doAndHandle(() -> ZonedDateTime.parse(value, parser), throwable -> null).get()).filter(Objects::nonNull).findFirst().orElse(null);
    }

    public static ZonedDateTime zonedDateTimeOf(TemporalAccessor time) {
        return ZonedDateTime.from(time);
    }

    public static ZonedDateTime zonedDateTimeOf(Instant time) {
        return time != null ? time.atZone(ZoneOffset.UTC) : null;
    }

    public static ZonedDateTime zonedDateTimeOf(long time) {
        return DateTimeUtils.zonedDateTimeOf(time, ZoneOffset.UTC);
    }

    public static ZonedDateTime zonedDateTimeOf(long time, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), zoneId);
    }

    public static ZonedDateTime zonedDateTimeOf(Date time) {
        return time != null ? DateTimeUtils.zonedDateTimeOf(Instant.ofEpochMilli(time.getTime())) : null;
    }

    public static ZonedDateTime zonedDateTimeOf(Calendar time) {
        return ZonedDateTime.ofInstant(time.toInstant(), time.getTimeZone().toZoneId());
    }

    public static Date dateOf(ChronoZonedDateTime time) {
        return DateTimeUtils.dateOf(time.toInstant());
    }

    public static Date dateOf(LocalDate time) {
        return Date.from(time.atStartOfDay(ZoneOffset.UTC).toInstant());
    }

    public static Date dateOf(LocalDateTime time) {
        return DateTimeUtils.dateOf(time.toInstant(ZoneOffset.UTC));
    }

    public static Date dateOf(Instant time) {
        return Date.from(time);
    }

    public static Timestamp timestampOf(ChronoZonedDateTime time) {
        return DateTimeUtils.timestampOf(time.toInstant());
    }

    private static Timestamp timestampOf(Instant time) {
        return Timestamp.from(time);
    }

    public static ZonedDateTime convertToZonedDateTime(String value) {
        ZonedDateTime dt = DateTimeUtils.zonedDateTimeOf(value);
        if (dt != null) {
            return dt;
        }
        LocalDateTime lt = DateTimeUtils.localDateTimeOf(value);
        return DateTimeUtils.zonedDateTimeOf(lt.atZone(ZoneOffset.UTC));
    }

    public static TimeUnit toTimeUnit(ChronoUnit tu) {
        if (tu == null) {
            return null;
        }
        switch (tu) {
            case DAYS: {
                return TimeUnit.DAYS;
            }
            case HOURS: {
                return TimeUnit.HOURS;
            }
            case MINUTES: {
                return TimeUnit.MINUTES;
            }
            case SECONDS: {
                return TimeUnit.SECONDS;
            }
            case MICROS: {
                return TimeUnit.MICROSECONDS;
            }
            case MILLIS: {
                return TimeUnit.MILLISECONDS;
            }
            case NANOS: {
                return TimeUnit.NANOSECONDS;
            }
        }
        throw new UnsupportedOperationException("Temporal unit is not supported");
    }

    public static ChronoUnit toChronoUnit(TimeUnit tu) {
        if (tu == null) {
            return null;
        }
        switch (tu) {
            case DAYS: {
                return ChronoUnit.DAYS;
            }
            case HOURS: {
                return ChronoUnit.HOURS;
            }
            case MINUTES: {
                return ChronoUnit.MINUTES;
            }
            case MICROSECONDS: {
                return ChronoUnit.MICROS;
            }
            case MILLISECONDS: {
                return ChronoUnit.MILLIS;
            }
            case NANOSECONDS: {
                return ChronoUnit.NANOS;
            }
        }
        return ChronoUnit.SECONDS;
    }

    @Generated
    private DateTimeUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}


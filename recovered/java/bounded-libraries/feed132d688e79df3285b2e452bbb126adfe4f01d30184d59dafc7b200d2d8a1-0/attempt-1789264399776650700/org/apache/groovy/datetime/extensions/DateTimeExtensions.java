/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyRuntimeException
 */
package org.apache.groovy.datetime.extensions;

import groovy.lang.Closure;
import groovy.lang.GroovyRuntimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
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
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.apache.groovy.datetime.extensions.DateTimeStaticExtensions;

public final class DateTimeExtensions {
    private static final DateTimeFormatter ZONE_SHORT_FORMATTER = DateTimeFormatter.ofPattern("z");
    private static Map<Class<? extends Temporal>, TemporalUnit> DEFAULT_UNITS = new HashMap<Class<? extends Temporal>, TemporalUnit>();

    private DateTimeExtensions() {
    }

    private static TemporalUnit defaultUnitFor(Temporal temporal) {
        return DEFAULT_UNITS.entrySet().stream().filter(e -> ((Class)e.getKey()).isAssignableFrom(temporal.getClass())).findFirst().map(Map.Entry::getValue).orElse(ChronoUnit.SECONDS);
    }

    private static int millisFromNanos(int nanos) {
        return nanos / 1000000;
    }

    public static void upto(Temporal from, Temporal to, Closure closure) {
        DateTimeExtensions.upto(from, to, DateTimeExtensions.defaultUnitFor(from), closure);
    }

    public static void upto(Temporal from, Temporal to, TemporalUnit unit, Closure closure) {
        if (DateTimeExtensions.isUptoEligible(from, to)) {
            Temporal i = from;
            while (DateTimeExtensions.isUptoEligible(i, to)) {
                closure.call((Object)i);
                i = i.plus(1L, unit);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to upto() cannot be earlier than the value (" + from + ") it's called on.");
        }
    }

    private static boolean isUptoEligible(Temporal from, Temporal to) {
        TemporalAmount amount = DateTimeExtensions.rightShift(from, to);
        if (amount instanceof Period) {
            return DateTimeExtensions.isNonnegative((Period)amount);
        }
        if (amount instanceof Duration) {
            return DateTimeExtensions.isNonnegative((Duration)amount);
        }
        throw new GroovyRuntimeException("Temporal implementations of " + from.getClass().getCanonicalName() + " are not supported by upto().");
    }

    public static void downto(Temporal from, Temporal to, Closure closure) {
        DateTimeExtensions.downto(from, to, DateTimeExtensions.defaultUnitFor(from), closure);
    }

    public static void downto(Temporal from, Temporal to, TemporalUnit unit, Closure closure) {
        if (DateTimeExtensions.isDowntoEligible(from, to)) {
            Temporal i = from;
            while (DateTimeExtensions.isDowntoEligible(i, to)) {
                closure.call((Object)i);
                i = i.minus(1L, unit);
            }
        } else {
            throw new GroovyRuntimeException("The argument (" + to + ") to downto() cannot be later than the value (" + from + ") it's called on.");
        }
    }

    private static boolean isDowntoEligible(Temporal from, Temporal to) {
        TemporalAmount amount = DateTimeExtensions.rightShift(from, to);
        if (amount instanceof Period) {
            return DateTimeExtensions.isNonpositive((Period)amount);
        }
        if (amount instanceof Duration) {
            return DateTimeExtensions.isNonpositive((Duration)amount);
        }
        throw new GroovyRuntimeException("Temporal implementations of " + from.getClass().getCanonicalName() + " are not supported by downto().");
    }

    public static TemporalAmount rightShift(Temporal self, Temporal other) {
        if (!self.getClass().equals(other.getClass())) {
            throw new GroovyRuntimeException("Temporal arguments must be of the same type.");
        }
        switch ((ChronoUnit)DateTimeExtensions.defaultUnitFor(self)) {
            case YEARS: {
                return DateTimeStaticExtensions.between(null, (Year)self, (Year)other);
            }
            case MONTHS: {
                return DateTimeStaticExtensions.between(null, (YearMonth)self, (YearMonth)other);
            }
            case DAYS: {
                return ChronoPeriod.between((ChronoLocalDate)self, (ChronoLocalDate)other);
            }
        }
        return Duration.between(self, other);
    }

    public static long getAt(TemporalAccessor self, TemporalField field) {
        return self.getLong(field);
    }

    public static long getAt(TemporalAmount self, TemporalUnit unit) {
        return self.get(unit);
    }

    public static Duration plus(Duration self, long seconds) {
        return self.plusSeconds(seconds);
    }

    public static Duration minus(Duration self, long seconds) {
        return self.minusSeconds(seconds);
    }

    public static Duration next(Duration self) {
        return self.plusSeconds(1L);
    }

    public static Duration previous(Duration self) {
        return self.minusSeconds(1L);
    }

    public static Duration negative(Duration self) {
        return self.negated();
    }

    public static Duration positive(Duration self) {
        return self.abs();
    }

    public static Duration multiply(Duration self, long scalar) {
        return self.multipliedBy(scalar);
    }

    public static Duration div(Duration self, long scalar) {
        return self.dividedBy(scalar);
    }

    public static boolean isPositive(Duration self) {
        return !self.isZero() && !self.isNegative();
    }

    public static boolean isNonnegative(Duration self) {
        return self.isZero() || !self.isNegative();
    }

    public static boolean isNonpositive(Duration self) {
        return self.isZero() || self.isNegative();
    }

    public static Instant plus(Instant self, long seconds) {
        return self.plusSeconds(seconds);
    }

    public static Instant minus(Instant self, long seconds) {
        return self.minusSeconds(seconds);
    }

    public static Instant next(Instant self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static Instant previous(Instant self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static Date toDate(Instant self) {
        return new Date(self.toEpochMilli());
    }

    public static Calendar toCalendar(Instant self) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(DateTimeExtensions.toDate(self));
        return cal;
    }

    public static String format(LocalDate self, String pattern) {
        return self.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalDate self, FormatStyle dateStyle) {
        return self.format(DateTimeFormatter.ofLocalizedDate(dateStyle));
    }

    public static String getDateString(LocalDate self) {
        return self.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static LocalDate plus(LocalDate self, long days) {
        return self.plusDays(days);
    }

    public static LocalDate minus(LocalDate self, long days) {
        return self.minusDays(days);
    }

    public static long minus(LocalDate self, LocalDate other) {
        return ChronoUnit.DAYS.between(other, self);
    }

    public static LocalDate next(LocalDate self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static LocalDate previous(LocalDate self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static Period rightShift(LocalDate self, LocalDate other) {
        return Period.between(self, other);
    }

    public static LocalDateTime leftShift(LocalDate self, LocalTime time) {
        return LocalDateTime.of(self, time);
    }

    public static OffsetDateTime leftShift(LocalDate self, OffsetTime time) {
        return time.atDate(self);
    }

    public static Date toDate(LocalDate self) {
        return DateTimeExtensions.toCalendar(self).getTime();
    }

    public static Calendar toCalendar(LocalDate self) {
        Calendar cal = Calendar.getInstance();
        cal.set(5, self.getDayOfMonth());
        cal.set(2, self.getMonthValue() - 1);
        cal.set(1, self.getYear());
        DateTimeExtensions.clearTimeCommon(cal);
        return cal;
    }

    private static void clearTimeCommon(Calendar self) {
        self.set(11, 0);
        self.clear(12);
        self.clear(13);
        self.clear(14);
    }

    public static String format(LocalDateTime self, String pattern) {
        return self.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalDateTime self, FormatStyle dateTimeStyle) {
        return self.format(DateTimeFormatter.ofLocalizedDateTime(dateTimeStyle));
    }

    public static String getDateTimeString(LocalDateTime self) {
        return self.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static String getDateString(LocalDateTime self) {
        return self.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String getTimeString(LocalDateTime self) {
        return self.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static LocalDateTime clearTime(LocalDateTime self) {
        return self.truncatedTo(ChronoUnit.DAYS);
    }

    public static LocalDateTime plus(LocalDateTime self, long seconds) {
        return self.plusSeconds(seconds);
    }

    public static LocalDateTime minus(LocalDateTime self, long seconds) {
        return self.minusSeconds(seconds);
    }

    public static LocalDateTime next(LocalDateTime self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static LocalDateTime previous(LocalDateTime self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static OffsetDateTime leftShift(LocalDateTime self, ZoneOffset offset) {
        return OffsetDateTime.of(self, offset);
    }

    public static ZonedDateTime leftShift(LocalDateTime self, ZoneId zone) {
        return ZonedDateTime.of(self, zone);
    }

    public static Date toDate(LocalDateTime self) {
        return DateTimeExtensions.toCalendar(self).getTime();
    }

    public static Calendar toCalendar(LocalDateTime self) {
        Calendar cal = Calendar.getInstance();
        cal.set(5, self.getDayOfMonth());
        cal.set(2, self.getMonthValue() - 1);
        cal.set(1, self.getYear());
        cal.set(11, self.getHour());
        cal.set(12, self.getMinute());
        cal.set(13, self.getSecond());
        cal.set(14, DateTimeExtensions.millisFromNanos(self.getNano()));
        return cal;
    }

    public static String format(LocalTime self, String pattern) {
        return self.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(LocalTime self, FormatStyle timeStyle) {
        return self.format(DateTimeFormatter.ofLocalizedTime(timeStyle));
    }

    public static String getTimeString(LocalTime self) {
        return self.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    public static LocalTime plus(LocalTime self, long seconds) {
        return self.plusSeconds(seconds);
    }

    public static LocalTime minus(LocalTime self, long seconds) {
        return self.minusSeconds(seconds);
    }

    public static LocalTime next(LocalTime self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static LocalTime previous(LocalTime self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static LocalDateTime leftShift(LocalTime self, LocalDate date) {
        return LocalDateTime.of(date, self);
    }

    public static OffsetTime leftShift(LocalTime self, ZoneOffset offset) {
        return OffsetTime.of(self, offset);
    }

    public static Date toDate(LocalTime self) {
        return DateTimeExtensions.toCalendar(self).getTime();
    }

    public static Calendar toCalendar(LocalTime self) {
        Calendar cal = Calendar.getInstance();
        cal.set(11, self.getHour());
        cal.set(12, self.getMinute());
        cal.set(13, self.getSecond());
        cal.set(14, DateTimeExtensions.millisFromNanos(self.getNano()));
        return cal;
    }

    public static LocalDate leftShift(MonthDay self, int year) {
        return self.atYear(year);
    }

    public static LocalDate leftShift(MonthDay self, Year year) {
        return year.atMonthDay(self);
    }

    public static String format(OffsetDateTime self, String pattern) {
        return self.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(OffsetDateTime self, FormatStyle dateTimeStyle) {
        return self.format(DateTimeFormatter.ofLocalizedDateTime(dateTimeStyle));
    }

    public static String getDateTimeString(OffsetDateTime self) {
        return self.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public static String getDateString(OffsetDateTime self) {
        return self.format(DateTimeFormatter.ISO_OFFSET_DATE);
    }

    public static String getTimeString(OffsetDateTime self) {
        return self.format(DateTimeFormatter.ISO_OFFSET_TIME);
    }

    public static OffsetDateTime clearTime(OffsetDateTime self) {
        return self.truncatedTo(ChronoUnit.DAYS);
    }

    public static OffsetDateTime plus(OffsetDateTime self, long seconds) {
        return self.plusSeconds(seconds);
    }

    public static OffsetDateTime minus(OffsetDateTime self, long seconds) {
        return self.minusSeconds(seconds);
    }

    public static OffsetDateTime next(OffsetDateTime self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static OffsetDateTime previous(OffsetDateTime self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static Date toDate(OffsetDateTime self) {
        return DateTimeExtensions.toCalendar(self).getTime();
    }

    public static Calendar toCalendar(OffsetDateTime self) {
        return DateTimeExtensions.toCalendar(self.toZonedDateTime());
    }

    public static String format(OffsetTime self, String pattern) {
        return self.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(OffsetTime self, FormatStyle timeStyle) {
        return self.format(DateTimeFormatter.ofLocalizedTime(timeStyle));
    }

    public static String getTimeString(OffsetTime self) {
        return self.format(DateTimeFormatter.ISO_OFFSET_TIME);
    }

    public static OffsetTime plus(OffsetTime self, long seconds) {
        return self.plusSeconds(seconds);
    }

    public static OffsetTime minus(OffsetTime self, long seconds) {
        return self.minusSeconds(seconds);
    }

    public static OffsetTime next(OffsetTime self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static OffsetTime previous(OffsetTime self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static OffsetDateTime leftShift(OffsetTime self, LocalDate date) {
        return OffsetDateTime.of(date, self.toLocalTime(), self.getOffset());
    }

    public static Date toDate(OffsetTime self) {
        return DateTimeExtensions.toCalendar(self).getTime();
    }

    public static Calendar toCalendar(OffsetTime self) {
        TimeZone timeZone = DateTimeExtensions.toTimeZone(self.getOffset());
        Calendar cal = Calendar.getInstance(timeZone);
        cal.set(11, self.getHour());
        cal.set(12, self.getMinute());
        cal.set(13, self.getSecond());
        cal.set(14, DateTimeExtensions.millisFromNanos(self.getNano()));
        return cal;
    }

    public static Period plus(Period self, long days) {
        return self.plusDays(days);
    }

    public static Period minus(Period self, long days) {
        return self.minusDays(days);
    }

    public static Period next(Period self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static Period previous(Period self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static Period negative(Period self) {
        return self.negated();
    }

    public static Period positive(Period self) {
        return !self.isNegative() ? self : self.withDays(Math.abs(self.getDays())).withMonths(Math.abs(self.getMonths())).withYears(Math.abs(self.getYears()));
    }

    public static Period multiply(Period self, int scalar) {
        return self.multipliedBy(scalar);
    }

    public static boolean isPositive(ChronoPeriod self) {
        return !self.isZero() && !self.isNegative();
    }

    public static boolean isNonnegative(ChronoPeriod self) {
        return self.isZero() || !self.isNegative();
    }

    public static boolean isNonpositive(ChronoPeriod self) {
        return self.isZero() || self.isNegative();
    }

    public static Year plus(Year self, long years) {
        return self.plusYears(years);
    }

    public static Year minus(Year self, long years) {
        return self.minusYears(years);
    }

    public static Year next(Year self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static Year previous(Year self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static Period rightShift(Year self, Year year) {
        return Period.between(self.atDay(1), year.atDay(1));
    }

    public static YearMonth leftShift(Year self, Month month) {
        return self.atMonth(month);
    }

    public static LocalDate leftShift(Year self, MonthDay monthDay) {
        return self.atMonthDay(monthDay);
    }

    public static int getEra(Year self) {
        return self.get(ChronoField.ERA);
    }

    public static int getYearOfEra(Year self) {
        return self.get(ChronoField.YEAR_OF_ERA);
    }

    public static YearMonth plus(YearMonth self, long months) {
        return self.plusMonths(months);
    }

    public static YearMonth minus(YearMonth self, long months) {
        return self.minusMonths(months);
    }

    public static YearMonth next(YearMonth self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static YearMonth previous(YearMonth self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static LocalDate leftShift(YearMonth self, int dayOfMonth) {
        return self.atDay(dayOfMonth);
    }

    public static Period rightShift(YearMonth self, YearMonth other) {
        return Period.between(self.atDay(1), other.atDay(1));
    }

    public static String format(ZonedDateTime self, String pattern) {
        return self.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(ZonedDateTime self, FormatStyle dateTimeStyle) {
        return self.format(DateTimeFormatter.ofLocalizedDateTime(dateTimeStyle));
    }

    public static String getDateTimeString(ZonedDateTime self) {
        return self.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + self.format(ZONE_SHORT_FORMATTER);
    }

    public static String getDateString(ZonedDateTime self) {
        return self.format(DateTimeFormatter.ISO_LOCAL_DATE) + self.format(ZONE_SHORT_FORMATTER);
    }

    public static String getTimeString(ZonedDateTime self) {
        return self.format(DateTimeFormatter.ISO_LOCAL_TIME) + self.format(ZONE_SHORT_FORMATTER);
    }

    public static ZonedDateTime clearTime(ZonedDateTime self) {
        return self.truncatedTo(ChronoUnit.DAYS);
    }

    public static ZonedDateTime plus(ZonedDateTime self, long seconds) {
        return self.plusSeconds(seconds);
    }

    public static ZonedDateTime minus(ZonedDateTime self, long seconds) {
        return self.minusSeconds(seconds);
    }

    public static ZonedDateTime next(ZonedDateTime self) {
        return DateTimeExtensions.plus(self, 1L);
    }

    public static ZonedDateTime previous(ZonedDateTime self) {
        return DateTimeExtensions.minus(self, 1L);
    }

    public static Date toDate(ZonedDateTime self) {
        return DateTimeExtensions.toCalendar(self).getTime();
    }

    public static Calendar toCalendar(ZonedDateTime self) {
        Calendar cal = Calendar.getInstance(DateTimeExtensions.toTimeZone(self.getZone()));
        cal.set(5, self.getDayOfMonth());
        cal.set(2, self.getMonthValue() - 1);
        cal.set(1, self.getYear());
        cal.set(11, self.getHour());
        cal.set(12, self.getMinute());
        cal.set(13, self.getSecond());
        cal.set(14, DateTimeExtensions.millisFromNanos(self.getNano()));
        return cal;
    }

    public static TimeZone toTimeZone(ZoneId self) {
        return TimeZone.getTimeZone(self);
    }

    public static String getFullName(ZoneId self) {
        return DateTimeExtensions.getFullName(self, Locale.getDefault());
    }

    public static String getFullName(ZoneId self, Locale locale) {
        return self.getDisplayName(TextStyle.FULL, locale);
    }

    public static String getShortName(ZoneId self) {
        return DateTimeExtensions.getShortName(self, Locale.getDefault());
    }

    public static String getShortName(ZoneId self, Locale locale) {
        return self.getDisplayName(TextStyle.SHORT, locale);
    }

    public static ZoneOffset getOffset(ZoneId self) {
        return DateTimeExtensions.getOffset(self, Instant.now());
    }

    public static ZoneOffset getOffset(ZoneId self, Instant instant) {
        return self.getRules().getOffset(instant);
    }

    public static ZonedDateTime leftShift(ZoneId self, LocalDateTime dateTime) {
        return ZonedDateTime.of(dateTime, self);
    }

    public static TimeZone toTimeZone(ZoneOffset self) {
        if (ZoneOffset.UTC.equals(self)) {
            return TimeZone.getTimeZone("GMT");
        }
        if (DateTimeExtensions.getSeconds(self) == 0) {
            return TimeZone.getTimeZone("GMT" + self.getId());
        }
        ZoneOffset noSeconds = ZoneOffset.ofHoursMinutes(DateTimeExtensions.getHours(self), DateTimeExtensions.getMinutes(self));
        return TimeZone.getTimeZone("GMT" + noSeconds.getId());
    }

    private static int offsetFieldValue(ZoneOffset offset, TemporalField field) {
        int offsetSeconds = offset.getTotalSeconds();
        int value = LocalTime.ofSecondOfDay(Math.abs(offsetSeconds)).get(field);
        return offsetSeconds < 0 ? value * -1 : value;
    }

    public static int getHours(ZoneOffset self) {
        return DateTimeExtensions.offsetFieldValue(self, ChronoField.HOUR_OF_DAY);
    }

    public static int getMinutes(ZoneOffset self) {
        return DateTimeExtensions.offsetFieldValue(self, ChronoField.MINUTE_OF_HOUR);
    }

    public static int getSeconds(ZoneOffset self) {
        return DateTimeExtensions.offsetFieldValue(self, ChronoField.SECOND_OF_MINUTE);
    }

    public static long getAt(ZoneOffset self, TemporalField field) {
        return self.getLong(field);
    }

    public static OffsetDateTime leftShift(ZoneOffset self, LocalDateTime dateTime) {
        return OffsetDateTime.of(dateTime, self);
    }

    public static OffsetTime leftShift(ZoneOffset self, LocalTime time) {
        return OffsetTime.of(time, self);
    }

    public static DayOfWeek plus(DayOfWeek self, int days) {
        int daysPerWeek = DayOfWeek.values().length;
        int val = (self.getValue() + days - 1) % daysPerWeek + 1;
        return DayOfWeek.of(val > 0 ? val : daysPerWeek + val);
    }

    public static DayOfWeek minus(DayOfWeek self, int days) {
        return DateTimeExtensions.plus(self, days * -1);
    }

    public static boolean isWeekend(DayOfWeek self) {
        return self == DayOfWeek.SATURDAY || self == DayOfWeek.SUNDAY;
    }

    public static boolean isWeekday(DayOfWeek self) {
        return !DateTimeExtensions.isWeekend(self);
    }

    public static Month plus(Month self, int months) {
        int monthsPerYear = Month.values().length;
        int val = (self.getValue() + months - 1) % monthsPerYear + 1;
        return Month.of(val > 0 ? val : monthsPerYear + val);
    }

    public static Month minus(Month self, int months) {
        return DateTimeExtensions.plus(self, months * -1);
    }

    public static MonthDay leftShift(Month self, int dayOfMonth) {
        return MonthDay.of(self, dayOfMonth);
    }

    public static YearMonth leftShift(Month self, Year year) {
        return YearMonth.of(year.getValue(), self);
    }

    public static ZoneOffset getZoneOffset(Calendar self) {
        int offsetMillis = self.get(15) + self.get(16);
        return ZoneOffset.ofTotalSeconds(offsetMillis / 1000);
    }

    private static Calendar toCalendar(Date self) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(self);
        return cal;
    }

    public static ZoneOffset getZoneOffset(Date self) {
        return DateTimeExtensions.getZoneOffset(DateTimeExtensions.toCalendar(self));
    }

    public static ZoneId getZoneId(Calendar self) {
        return self.getTimeZone().toZoneId();
    }

    public static ZoneId getZoneId(Date self) {
        return DateTimeExtensions.getZoneId(DateTimeExtensions.toCalendar(self));
    }

    public static Year toYear(Calendar self) {
        return Year.of(self.get(1));
    }

    public static Year toYear(Date self) {
        return DateTimeExtensions.toYear(DateTimeExtensions.toCalendar(self));
    }

    public static Month toMonth(Calendar self) {
        return Month.of(self.get(2) + 1);
    }

    public static Month toMonth(Date self) {
        return DateTimeExtensions.toMonth(DateTimeExtensions.toCalendar(self));
    }

    public static MonthDay toMonthDay(Calendar self) {
        return MonthDay.of(DateTimeExtensions.toMonth(self), self.get(5));
    }

    public static MonthDay toMonthDay(Date self) {
        return DateTimeExtensions.toMonthDay(DateTimeExtensions.toCalendar(self));
    }

    public static YearMonth toYearMonth(Calendar self) {
        return DateTimeExtensions.toYear(self).atMonth(DateTimeExtensions.toMonth(self));
    }

    public static YearMonth toYearMonth(Date self) {
        return DateTimeExtensions.toYearMonth(DateTimeExtensions.toCalendar(self));
    }

    public static DayOfWeek toDayOfWeek(Calendar self) {
        return DayOfWeek.of(self.get(7)).minus(1L);
    }

    public static DayOfWeek toDayOfWeek(Date self) {
        return DateTimeExtensions.toDayOfWeek(DateTimeExtensions.toCalendar(self));
    }

    static LocalDate toLocalDate(Calendar self) {
        return LocalDate.of(self.get(1), DateTimeExtensions.toMonth(self), self.get(5));
    }

    public static LocalDate toLocalDate(Date self) {
        return DateTimeExtensions.toLocalDate(DateTimeExtensions.toCalendar(self));
    }

    public static LocalTime toLocalTime(Calendar self) {
        int hour = self.get(11);
        int minute = self.get(12);
        int second = self.get(13);
        int ns = self.get(14) * 1000000;
        return LocalTime.of(hour, minute, second, ns);
    }

    public static LocalTime toLocalTime(Date self) {
        return DateTimeExtensions.toLocalTime(DateTimeExtensions.toCalendar(self));
    }

    public static LocalDateTime toLocalDateTime(Calendar self) {
        return LocalDateTime.of(DateTimeExtensions.toLocalDate(self), DateTimeExtensions.toLocalTime(self));
    }

    public static LocalDateTime toLocalDateTime(Date self) {
        return DateTimeExtensions.toLocalDateTime(DateTimeExtensions.toCalendar(self));
    }

    public static ZonedDateTime toZonedDateTime(Calendar self) {
        if (self instanceof GregorianCalendar) {
            return ((GregorianCalendar)self).toZonedDateTime();
        }
        return ZonedDateTime.of(DateTimeExtensions.toLocalDateTime(self), DateTimeExtensions.getZoneId(self));
    }

    public static ZonedDateTime toZonedDateTime(Date self) {
        return DateTimeExtensions.toZonedDateTime(DateTimeExtensions.toCalendar(self));
    }

    public static OffsetDateTime toOffsetDateTime(Calendar self) {
        return OffsetDateTime.of(DateTimeExtensions.toLocalDateTime(self), DateTimeExtensions.getZoneOffset(self));
    }

    public static OffsetDateTime toOffsetDateTime(Date self) {
        return DateTimeExtensions.toOffsetDateTime(DateTimeExtensions.toCalendar(self));
    }

    public static OffsetTime toOffsetTime(Calendar self) {
        return OffsetTime.of(DateTimeExtensions.toLocalTime(self), DateTimeExtensions.getZoneOffset(self));
    }

    public static OffsetTime toOffsetTime(Date self) {
        return DateTimeExtensions.toOffsetTime(DateTimeExtensions.toCalendar(self));
    }

    public static Instant toInstant(Calendar self) {
        return self.getTime().toInstant();
    }

    public static ZoneOffset toZoneOffset(TimeZone self) {
        return DateTimeExtensions.toZoneOffset(self, Instant.now());
    }

    public static ZoneOffset toZoneOffset(TimeZone self, Instant instant) {
        return self.toZoneId().getRules().getOffset(instant);
    }

    static {
        DEFAULT_UNITS.put(ChronoLocalDate.class, ChronoUnit.DAYS);
        DEFAULT_UNITS.put(YearMonth.class, ChronoUnit.MONTHS);
        DEFAULT_UNITS.put(Year.class, ChronoUnit.YEARS);
    }
}


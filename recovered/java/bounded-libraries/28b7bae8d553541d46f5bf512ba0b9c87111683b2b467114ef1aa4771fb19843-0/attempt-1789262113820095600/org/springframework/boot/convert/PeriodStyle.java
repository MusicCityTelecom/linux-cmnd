/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.boot.convert;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

public enum PeriodStyle {
    SIMPLE("^(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?$", 2){

        @Override
        public Period parse(String value, ChronoUnit unit) {
            try {
                if (NUMERIC.matcher(value).matches()) {
                    return Unit.fromChronoUnit(unit).parse(value);
                }
                Matcher matcher = this.matcher(value);
                Assert.state((boolean)matcher.matches(), (String)"Does not match simple period pattern");
                Assert.isTrue((boolean)this.hasAtLeastOneGroupValue(matcher), () -> "'" + value + "' is not a valid simple period");
                int years = this.parseInt(matcher, 1);
                int months = this.parseInt(matcher, 2);
                int weeks = this.parseInt(matcher, 3);
                int days = this.parseInt(matcher, 4);
                return Period.of(years, months, Math.addExact(Math.multiplyExact(weeks, 7), days));
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("'" + value + "' is not a valid simple period", ex);
            }
        }

        boolean hasAtLeastOneGroupValue(Matcher matcher) {
            for (int i = 0; i < matcher.groupCount(); ++i) {
                if (matcher.group(i + 1) == null) continue;
                return true;
            }
            return false;
        }

        private int parseInt(Matcher matcher, int group) {
            String value = matcher.group(group);
            return value != null ? Integer.parseInt(value) : 0;
        }

        @Override
        protected boolean matches(String value) {
            return NUMERIC.matcher(value).matches() || this.matcher(value).matches();
        }

        @Override
        public String print(Period value, ChronoUnit unit) {
            if (value.isZero()) {
                return Unit.fromChronoUnit(unit).print(value);
            }
            StringBuilder result = new StringBuilder();
            this.append(result, value, Unit.YEARS);
            this.append(result, value, Unit.MONTHS);
            this.append(result, value, Unit.DAYS);
            return result.toString();
        }

        private void append(StringBuilder result, Period value, Unit unit) {
            if (!unit.isZero(value)) {
                result.append(unit.print(value));
            }
        }
    }
    ,
    ISO8601("^[+-]?P.*$", 0){

        @Override
        public Period parse(String value, ChronoUnit unit) {
            try {
                return Period.parse(value);
            }
            catch (Exception ex) {
                throw new IllegalArgumentException("'" + value + "' is not a valid ISO-8601 period", ex);
            }
        }

        @Override
        public String print(Period value, ChronoUnit unit) {
            return value.toString();
        }
    };

    private static final Pattern NUMERIC;
    private final Pattern pattern;

    private PeriodStyle(String pattern, int flags) {
        this.pattern = Pattern.compile(pattern, flags);
    }

    protected boolean matches(String value) {
        return this.pattern.matcher(value).matches();
    }

    protected final Matcher matcher(String value) {
        return this.pattern.matcher(value);
    }

    public Period parse(String value) {
        return this.parse(value, null);
    }

    public abstract Period parse(String var1, ChronoUnit var2);

    public String print(Period value) {
        return this.print(value, null);
    }

    public abstract String print(Period var1, ChronoUnit var2);

    public static Period detectAndParse(String value) {
        return PeriodStyle.detectAndParse(value, null);
    }

    public static Period detectAndParse(String value, ChronoUnit unit) {
        return PeriodStyle.detect(value).parse(value, unit);
    }

    public static PeriodStyle detect(String value) {
        Assert.notNull((Object)value, (String)"Value must not be null");
        for (PeriodStyle candidate : PeriodStyle.values()) {
            if (!candidate.matches(value)) continue;
            return candidate;
        }
        throw new IllegalArgumentException("'" + value + "' is not a valid period");
    }

    static {
        NUMERIC = Pattern.compile("^[-+]?[0-9]+$");
    }

    private static enum Unit {
        DAYS(ChronoUnit.DAYS, "d", Period::getDays, Period::ofDays),
        WEEKS(ChronoUnit.WEEKS, "w", null, Period::ofWeeks),
        MONTHS(ChronoUnit.MONTHS, "m", Period::getMonths, Period::ofMonths),
        YEARS(ChronoUnit.YEARS, "y", Period::getYears, Period::ofYears);

        private final ChronoUnit chronoUnit;
        private final String suffix;
        private final Function<Period, Integer> intValue;
        private final Function<Integer, Period> factory;

        private Unit(ChronoUnit chronoUnit, String suffix, Function<Period, Integer> intValue, Function<Integer, Period> factory) {
            this.chronoUnit = chronoUnit;
            this.suffix = suffix;
            this.intValue = intValue;
            this.factory = factory;
        }

        private Period parse(String value) {
            return this.factory.apply(Integer.parseInt(value));
        }

        private String print(Period value) {
            return this.intValue(value) + this.suffix;
        }

        private boolean isZero(Period value) {
            return this.intValue(value) == 0;
        }

        private int intValue(Period value) {
            Assert.notNull(this.intValue, () -> "intValue cannot be extracted from " + this.name());
            return this.intValue.apply(value);
        }

        private static Unit fromChronoUnit(ChronoUnit chronoUnit) {
            if (chronoUnit == null) {
                return DAYS;
            }
            for (Unit candidate : Unit.values()) {
                if (candidate.chronoUnit != chronoUnit) continue;
                return candidate;
            }
            throw new IllegalArgumentException("Unsupported unit " + chronoUnit);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package com.fasterxml.jackson.datatype.jsr310.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DurationUnitConverter {
    private static final Map<String, DurationSerialization> UNITS;
    final DurationSerialization serialization;

    DurationUnitConverter(DurationSerialization serialization) {
        this.serialization = serialization;
    }

    public Duration convert(long value) {
        return this.serialization.deserializer.apply(value);
    }

    public long convert(Duration duration) {
        return this.serialization.serializer.apply(duration);
    }

    public static String descForAllowed() {
        return "\"" + UNITS.keySet().stream().collect(Collectors.joining("\", \"")) + "\"";
    }

    public static DurationUnitConverter from(String unit) {
        DurationSerialization def = UNITS.get(unit);
        return def == null ? null : new DurationUnitConverter(def);
    }

    static {
        LinkedHashMap<String, DurationSerialization> units = new LinkedHashMap<String, DurationSerialization>();
        units.put(ChronoUnit.NANOS.name(), new DurationSerialization(Duration::toNanos, DurationSerialization.deserializer(ChronoUnit.NANOS)));
        units.put(ChronoUnit.MICROS.name(), new DurationSerialization(d -> d.toNanos() / 1000L, DurationSerialization.deserializer(ChronoUnit.MICROS)));
        units.put(ChronoUnit.MILLIS.name(), new DurationSerialization(Duration::toMillis, DurationSerialization.deserializer(ChronoUnit.MILLIS)));
        units.put(ChronoUnit.SECONDS.name(), new DurationSerialization(Duration::getSeconds, DurationSerialization.deserializer(ChronoUnit.SECONDS)));
        units.put(ChronoUnit.MINUTES.name(), new DurationSerialization(Duration::toMinutes, DurationSerialization.deserializer(ChronoUnit.MINUTES)));
        units.put(ChronoUnit.HOURS.name(), new DurationSerialization(Duration::toHours, DurationSerialization.deserializer(ChronoUnit.HOURS)));
        units.put(ChronoUnit.HALF_DAYS.name(), new DurationSerialization(d -> d.toHours() / 12L, DurationSerialization.deserializer(ChronoUnit.HALF_DAYS)));
        units.put(ChronoUnit.DAYS.name(), new DurationSerialization(Duration::toDays, DurationSerialization.deserializer(ChronoUnit.DAYS)));
        UNITS = units;
    }

    protected static class DurationSerialization {
        final Function<Duration, Long> serializer;
        final Function<Long, Duration> deserializer;

        DurationSerialization(Function<Duration, Long> serializer, Function<Long, Duration> deserializer) {
            this.serializer = serializer;
            this.deserializer = deserializer;
        }

        static Function<Long, Duration> deserializer(TemporalUnit unit) {
            return v -> Duration.of(v, unit);
        }
    }
}


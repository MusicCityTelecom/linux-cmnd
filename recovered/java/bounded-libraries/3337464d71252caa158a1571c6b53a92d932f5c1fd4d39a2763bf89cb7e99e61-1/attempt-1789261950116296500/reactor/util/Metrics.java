/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Metrics
 */
package reactor.util;

import io.micrometer.core.instrument.MeterRegistry;

public class Metrics {
    static final boolean isMicrometerAvailable;

    public static final boolean isInstrumentationAvailable() {
        return isMicrometerAvailable;
    }

    static {
        boolean micrometer;
        try {
            io.micrometer.core.instrument.Metrics.globalRegistry.getRegistries();
            micrometer = true;
        }
        catch (Throwable t) {
            micrometer = false;
        }
        isMicrometerAvailable = micrometer;
    }

    public static class MicrometerConfiguration {
        private static MeterRegistry registry = io.micrometer.core.instrument.Metrics.globalRegistry;

        public static MeterRegistry useRegistry(MeterRegistry registry) {
            MeterRegistry previous = MicrometerConfiguration.registry;
            MicrometerConfiguration.registry = registry;
            return previous;
        }

        public static MeterRegistry getRegistry() {
            return registry;
        }
    }
}


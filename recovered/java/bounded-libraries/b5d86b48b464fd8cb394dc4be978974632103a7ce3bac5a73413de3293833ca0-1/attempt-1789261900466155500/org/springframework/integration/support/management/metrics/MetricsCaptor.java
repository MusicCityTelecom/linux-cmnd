/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.support.management.metrics;

import java.util.function.ToDoubleFunction;
import org.springframework.integration.support.management.metrics.CounterFacade;
import org.springframework.integration.support.management.metrics.GaugeFacade;
import org.springframework.integration.support.management.metrics.MeterFacade;
import org.springframework.integration.support.management.metrics.SampleFacade;
import org.springframework.integration.support.management.metrics.TimerFacade;
import org.springframework.lang.Nullable;

public interface MetricsCaptor {
    public TimerBuilder timerBuilder(String var1);

    public CounterBuilder counterBuilder(String var1);

    public GaugeBuilder gaugeBuilder(String var1, @Nullable Object var2, ToDoubleFunction<Object> var3);

    public SampleFacade start();

    @Nullable
    default public MeterFacade removeMeter(MeterFacade facade) {
        return null;
    }

    public static interface GaugeBuilder {
        public GaugeBuilder tag(String var1, String var2);

        public GaugeBuilder description(String var1);

        public GaugeFacade build();
    }

    public static interface CounterBuilder {
        public CounterBuilder tag(String var1, String var2);

        public CounterBuilder description(String var1);

        public CounterFacade build();
    }

    public static interface TimerBuilder {
        public TimerBuilder tag(String var1, String var2);

        public TimerBuilder description(String var1);

        public TimerFacade build();
    }
}


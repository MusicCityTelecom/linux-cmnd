/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Counter
 *  io.micrometer.core.instrument.Counter$Builder
 *  io.micrometer.core.instrument.Gauge
 *  io.micrometer.core.instrument.Gauge$Builder
 *  io.micrometer.core.instrument.Meter
 *  io.micrometer.core.instrument.MeterRegistry
 *  io.micrometer.core.instrument.Timer
 *  io.micrometer.core.instrument.Timer$Builder
 *  io.micrometer.core.instrument.Timer$Sample
 *  org.springframework.beans.factory.NoSuchBeanDefinitionException
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.config.BeanDefinitionCustomizer
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.support.GenericApplicationContext
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.management.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.util.concurrent.TimeUnit;
import java.util.function.ToDoubleFunction;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.integration.support.management.metrics.CounterFacade;
import org.springframework.integration.support.management.metrics.GaugeFacade;
import org.springframework.integration.support.management.metrics.MeterFacade;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.integration.support.management.metrics.SampleFacade;
import org.springframework.integration.support.management.metrics.TimerFacade;
import org.springframework.util.Assert;

public class MicrometerMetricsCaptor
implements MetricsCaptor {
    public static final String MICROMETER_CAPTOR_NAME = "integrationMicrometerMetricsCaptor";
    private MeterRegistry meterRegistry;
    private ObjectProvider<MeterRegistry> meterRegistryProvider;

    public MicrometerMetricsCaptor(MeterRegistry meterRegistry) {
        Assert.notNull((Object)meterRegistry, (String)"meterRegistry cannot be null");
        this.meterRegistry = meterRegistry;
    }

    MicrometerMetricsCaptor(ObjectProvider<MeterRegistry> meterRegistryProvider) {
        this.meterRegistryProvider = meterRegistryProvider;
    }

    public MeterRegistry getMeterRegistry() {
        if (this.meterRegistry == null) {
            this.meterRegistry = (MeterRegistry)this.meterRegistryProvider.getIfUnique();
        }
        return this.meterRegistry;
    }

    @Override
    public MetricsCaptor.TimerBuilder timerBuilder(String name) {
        return new MicroTimerBuilder(this.getMeterRegistry(), name);
    }

    @Override
    public MetricsCaptor.CounterBuilder counterBuilder(String name) {
        return new MicroCounterBuilder(this.getMeterRegistry(), name);
    }

    @Override
    public MetricsCaptor.GaugeBuilder gaugeBuilder(String name, Object obj, ToDoubleFunction<Object> f) {
        return new MicroGaugeBuilder(this.getMeterRegistry(), name, obj, f);
    }

    @Override
    public SampleFacade start() {
        return new MicroSample(Timer.start((MeterRegistry)this.getMeterRegistry()));
    }

    @Override
    public MeterFacade removeMeter(MeterFacade facade) {
        return facade.remove();
    }

    @Deprecated
    public static MetricsCaptor loadCaptor(ApplicationContext applicationContext) {
        try {
            MeterRegistry registry = (MeterRegistry)applicationContext.getBean(MeterRegistry.class);
            if (applicationContext instanceof GenericApplicationContext && !applicationContext.containsBean(MICROMETER_CAPTOR_NAME)) {
                ((GenericApplicationContext)applicationContext).registerBean(MICROMETER_CAPTOR_NAME, MicrometerMetricsCaptor.class, () -> new MicrometerMetricsCaptor(registry), new BeanDefinitionCustomizer[0]);
            }
            return (MetricsCaptor)applicationContext.getBean(MICROMETER_CAPTOR_NAME, MetricsCaptor.class);
        }
        catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    protected static class MicroTimerBuilder
    implements MetricsCaptor.TimerBuilder {
        protected final MeterRegistry meterRegistry;
        private final Timer.Builder builder;

        MicroTimerBuilder(MeterRegistry meterRegistry, String name) {
            this.meterRegistry = meterRegistry;
            this.builder = Timer.builder((String)name);
        }

        @Override
        public MicroTimerBuilder tag(String key, String value) {
            this.builder.tag(key, value);
            return this;
        }

        @Override
        public MicroTimerBuilder description(String desc) {
            this.builder.description(desc);
            return this;
        }

        @Override
        public MicroTimer build() {
            return new MicroTimer(this.builder.register(this.meterRegistry), this.meterRegistry);
        }
    }

    protected static class MicroCounterBuilder
    implements MetricsCaptor.CounterBuilder {
        protected final MeterRegistry meterRegistry;
        private final Counter.Builder builder;

        protected MicroCounterBuilder(MeterRegistry meterRegistry, String name) {
            this.meterRegistry = meterRegistry;
            this.builder = Counter.builder((String)name);
        }

        @Override
        public MetricsCaptor.CounterBuilder tag(String key, String value) {
            this.builder.tag(key, value);
            return this;
        }

        @Override
        public MetricsCaptor.CounterBuilder description(String desc) {
            this.builder.description(desc);
            return this;
        }

        @Override
        public CounterFacade build() {
            return new MicroCounter(this.builder.register(this.meterRegistry), this.meterRegistry);
        }
    }

    protected static class MicroGaugeBuilder
    implements MetricsCaptor.GaugeBuilder {
        protected final MeterRegistry meterRegistry;
        private final Gauge.Builder<Object> builder;

        protected MicroGaugeBuilder(MeterRegistry meterRegistry, String name, Object obj, ToDoubleFunction<Object> f) {
            this.meterRegistry = meterRegistry;
            this.builder = Gauge.builder((String)name, (Object)obj, f);
        }

        @Override
        public MetricsCaptor.GaugeBuilder tag(String key, String value) {
            this.builder.tag(key, value);
            return this;
        }

        @Override
        public MetricsCaptor.GaugeBuilder description(String desc) {
            this.builder.description(desc);
            return this;
        }

        @Override
        public GaugeFacade build() {
            return new MicroGauge(this.builder.register(this.meterRegistry), this.meterRegistry);
        }
    }

    private static class MicroSample
    implements SampleFacade {
        private final Timer.Sample sample;

        MicroSample(Timer.Sample sample) {
            this.sample = sample;
        }

        @Override
        public void stop(TimerFacade timer) {
            this.sample.stop((Timer)((AbstractMeter)((Object)timer)).getMeter());
        }
    }

    protected static class MicroGauge
    extends AbstractMeter<Gauge>
    implements GaugeFacade {
        private final Gauge gauge;

        protected MicroGauge(Gauge gauge, MeterRegistry meterRegistry) {
            super(meterRegistry);
            this.gauge = gauge;
        }

        @Override
        protected Gauge getMeter() {
            return this.gauge;
        }

        public int hashCode() {
            return this.gauge.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || !this.getClass().equals(obj.getClass())) {
                return false;
            }
            return this.gauge.equals(((MicroGauge)obj).gauge);
        }
    }

    protected static class MicroCounter
    extends AbstractMeter<Counter>
    implements CounterFacade {
        private final Counter counter;

        protected MicroCounter(Counter counter, MeterRegistry meterRegistry) {
            super(meterRegistry);
            this.counter = counter;
        }

        @Override
        protected Counter getMeter() {
            return this.counter;
        }

        @Override
        public void increment() {
            this.counter.increment();
        }

        public int hashCode() {
            return this.counter.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || !this.getClass().equals(obj.getClass())) {
                return false;
            }
            return this.counter.equals(((MicroCounter)obj).counter);
        }
    }

    protected static class MicroTimer
    extends AbstractMeter<Timer>
    implements TimerFacade {
        private final Timer timer;

        protected MicroTimer(Timer timer, MeterRegistry meterRegistry) {
            super(meterRegistry);
            this.timer = timer;
        }

        @Override
        protected Timer getMeter() {
            return this.timer;
        }

        @Override
        public void record(long time, TimeUnit unit) {
            this.timer.record(time, unit);
        }

        public int hashCode() {
            return this.timer.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null || !this.getClass().equals(obj.getClass())) {
                return false;
            }
            return this.timer.equals(((MicroTimer)obj).timer);
        }
    }

    protected static abstract class AbstractMeter<M extends Meter>
    implements MeterFacade {
        protected final MeterRegistry meterRegistry;

        protected AbstractMeter(MeterRegistry meterRegistry) {
            this.meterRegistry = meterRegistry;
        }

        protected abstract M getMeter();

        @Override
        public <T extends MeterFacade> T remove() {
            if (this.meterRegistry.remove(this.getMeter()) != null) {
                return (T)this;
            }
            return null;
        }
    }
}


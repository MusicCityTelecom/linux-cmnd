/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument.push;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.push.PushRegistryConfig;
import io.micrometer.core.instrument.util.TimeUtils;
import io.micrometer.core.lang.Nullable;
import io.micrometer.core.util.internal.logging.InternalLogger;
import io.micrometer.core.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class PushMeterRegistry
extends MeterRegistry {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PushMeterRegistry.class);
    private final PushRegistryConfig config;
    @Nullable
    private ScheduledExecutorService scheduledExecutorService;

    protected PushMeterRegistry(PushRegistryConfig config, Clock clock) {
        super(clock);
        config.requireValid();
        this.config = config;
    }

    protected abstract void publish();

    private void publishSafely() {
        try {
            this.publish();
        }
        catch (Throwable e) {
            logger.warn("Unexpected exception thrown while publishing metrics for " + this.getClass().getSimpleName(), e);
        }
    }

    @Deprecated
    public final void start() {
        this.start(Executors.defaultThreadFactory());
    }

    public void start(ThreadFactory threadFactory) {
        if (this.scheduledExecutorService != null) {
            this.stop();
        }
        if (this.config.enabled()) {
            logger.info("publishing metrics for " + this.getClass().getSimpleName() + " every " + TimeUtils.format(this.config.step()));
            this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
            long stepMillis = this.config.step().toMillis();
            long initialDelayMillis = stepMillis - this.clock.wallTime() % stepMillis + 1L;
            this.scheduledExecutorService.scheduleAtFixedRate(this::publishSafely, initialDelayMillis, stepMillis, TimeUnit.MILLISECONDS);
        }
    }

    public void stop() {
        if (this.scheduledExecutorService != null) {
            this.scheduledExecutorService.shutdown();
            this.scheduledExecutorService = null;
        }
    }

    @Override
    public void close() {
        if (this.config.enabled()) {
            this.publishSafely();
        }
        this.stop();
        super.close();
    }
}


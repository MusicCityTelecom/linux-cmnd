/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.LatencyUtils.IntervalEstimator
 *  org.LatencyUtils.PauseDetector
 *  org.LatencyUtils.SimplePauseDetector
 *  org.LatencyUtils.TimeCappedMovingAverageIntervalEstimator
 */
package io.micrometer.core.instrument;

import io.micrometer.core.instrument.AbstractMeter;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.Histogram;
import io.micrometer.core.instrument.distribution.HistogramSnapshot;
import io.micrometer.core.instrument.distribution.NoopHistogram;
import io.micrometer.core.instrument.distribution.TimeWindowFixedBoundaryHistogram;
import io.micrometer.core.instrument.distribution.TimeWindowPercentileHistogram;
import io.micrometer.core.instrument.distribution.pause.ClockDriftPauseDetector;
import io.micrometer.core.instrument.distribution.pause.NoPauseDetector;
import io.micrometer.core.instrument.distribution.pause.PauseDetector;
import io.micrometer.core.lang.Nullable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.LatencyUtils.IntervalEstimator;
import org.LatencyUtils.SimplePauseDetector;
import org.LatencyUtils.TimeCappedMovingAverageIntervalEstimator;

public abstract class AbstractTimer
extends AbstractMeter
implements Timer {
    private static Map<PauseDetector, Object> pauseDetectorCache = new ConcurrentHashMap<PauseDetector, Object>();
    protected final Clock clock;
    protected final Histogram histogram;
    private final TimeUnit baseTimeUnit;
    @Nullable
    private Object intervalEstimator;
    @Nullable
    private org.LatencyUtils.PauseDetector pauseDetector;

    @Deprecated
    protected AbstractTimer(Meter.Id id, Clock clock, DistributionStatisticConfig distributionStatisticConfig, PauseDetector pauseDetector, TimeUnit baseTimeUnit) {
        this(id, clock, distributionStatisticConfig, pauseDetector, baseTimeUnit, false);
    }

    protected AbstractTimer(Meter.Id id, Clock clock, DistributionStatisticConfig distributionStatisticConfig, PauseDetector pauseDetector, TimeUnit baseTimeUnit, boolean supportsAggregablePercentiles) {
        super(id);
        this.clock = clock;
        this.baseTimeUnit = baseTimeUnit;
        this.initPauseDetector(pauseDetector);
        this.histogram = distributionStatisticConfig.isPublishingPercentiles() ? new TimeWindowPercentileHistogram(clock, distributionStatisticConfig, supportsAggregablePercentiles) : (distributionStatisticConfig.isPublishingHistogram() ? new TimeWindowFixedBoundaryHistogram(clock, distributionStatisticConfig, supportsAggregablePercentiles) : NoopHistogram.INSTANCE);
    }

    private void initPauseDetector(PauseDetector pauseDetectorType) {
        if (pauseDetectorType instanceof NoPauseDetector) {
            return;
        }
        this.pauseDetector = (org.LatencyUtils.PauseDetector)pauseDetectorCache.computeIfAbsent(pauseDetectorType, detector -> {
            if (detector instanceof ClockDriftPauseDetector) {
                ClockDriftPauseDetector clockDriftPauseDetector = (ClockDriftPauseDetector)detector;
                return new SimplePauseDetector(clockDriftPauseDetector.getSleepInterval().toNanos(), clockDriftPauseDetector.getPauseThreshold().toNanos(), 1, false);
            }
            return null;
        });
        if (this.pauseDetector instanceof SimplePauseDetector) {
            this.intervalEstimator = new TimeCappedMovingAverageIntervalEstimator(128, 10000000000L, this.pauseDetector);
            this.pauseDetector.addListener((pauseLength, pauseEndTime) -> {
                long estimatedInterval;
                long observedLatencyMinbar;
                if (this.intervalEstimator != null && (observedLatencyMinbar = pauseLength - (estimatedInterval = ((IntervalEstimator)this.intervalEstimator).getEstimatedInterval(pauseEndTime))) >= estimatedInterval) {
                    this.recordValueWithExpectedInterval(observedLatencyMinbar, estimatedInterval);
                }
            });
        }
    }

    private void recordValueWithExpectedInterval(long nanoValue, long expectedIntervalBetweenValueSamples) {
        this.record(nanoValue, TimeUnit.NANOSECONDS);
        if (expectedIntervalBetweenValueSamples <= 0L) {
            return;
        }
        for (long missingValue = nanoValue - expectedIntervalBetweenValueSamples; missingValue >= expectedIntervalBetweenValueSamples; missingValue -= expectedIntervalBetweenValueSamples) {
            this.record(missingValue, TimeUnit.NANOSECONDS);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T recordCallable(Callable<T> f) throws Exception {
        long s = this.clock.monotonicTime();
        try {
            T t = f.call();
            return t;
        }
        finally {
            long e = this.clock.monotonicTime();
            this.record(e - s, TimeUnit.NANOSECONDS);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public <T> T record(Supplier<T> f) {
        long s = this.clock.monotonicTime();
        try {
            T t = f.get();
            return t;
        }
        finally {
            long e = this.clock.monotonicTime();
            this.record(e - s, TimeUnit.NANOSECONDS);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void record(Runnable f) {
        long s = this.clock.monotonicTime();
        try {
            f.run();
        }
        finally {
            long e = this.clock.monotonicTime();
            this.record(e - s, TimeUnit.NANOSECONDS);
        }
    }

    @Override
    public final void record(long amount, TimeUnit unit) {
        if (amount >= 0L) {
            this.histogram.recordLong(TimeUnit.NANOSECONDS.convert(amount, unit));
            this.recordNonNegative(amount, unit);
            if (this.intervalEstimator != null) {
                ((IntervalEstimator)this.intervalEstimator).recordInterval(this.clock.monotonicTime());
            }
        }
    }

    protected abstract void recordNonNegative(long var1, TimeUnit var3);

    @Override
    public HistogramSnapshot takeSnapshot() {
        return this.histogram.takeSnapshot(this.count(), this.totalTime(TimeUnit.NANOSECONDS), this.max(TimeUnit.NANOSECONDS));
    }

    @Override
    public TimeUnit baseTimeUnit() {
        return this.baseTimeUnit;
    }

    @Override
    public void close() {
        this.histogram.close();
        if (this.pauseDetector != null) {
            this.pauseDetector.shutdown();
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument;

import io.micrometer.core.instrument.AbstractMeter;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.Histogram;
import io.micrometer.core.instrument.distribution.HistogramSnapshot;
import io.micrometer.core.instrument.distribution.NoopHistogram;
import io.micrometer.core.instrument.distribution.TimeWindowFixedBoundaryHistogram;
import io.micrometer.core.instrument.distribution.TimeWindowPercentileHistogram;

public abstract class AbstractDistributionSummary
extends AbstractMeter
implements DistributionSummary {
    protected final Histogram histogram;
    private final double scale;

    protected AbstractDistributionSummary(Meter.Id id, Clock clock, DistributionStatisticConfig distributionStatisticConfig, double scale, boolean supportsAggregablePercentiles) {
        super(id);
        this.scale = scale;
        this.histogram = distributionStatisticConfig.isPublishingPercentiles() ? new TimeWindowPercentileHistogram(clock, distributionStatisticConfig, supportsAggregablePercentiles) : (distributionStatisticConfig.isPublishingHistogram() ? new TimeWindowFixedBoundaryHistogram(clock, distributionStatisticConfig, supportsAggregablePercentiles) : NoopHistogram.INSTANCE);
    }

    @Override
    public final void record(double amount) {
        if (amount >= 0.0) {
            double scaledAmount = this.scale * amount;
            this.histogram.recordDouble(scaledAmount);
            this.recordNonNegative(scaledAmount);
        }
    }

    protected abstract void recordNonNegative(double var1);

    @Override
    public HistogramSnapshot takeSnapshot() {
        return this.histogram.takeSnapshot(this.count(), this.totalAmount(), this.max());
    }
}


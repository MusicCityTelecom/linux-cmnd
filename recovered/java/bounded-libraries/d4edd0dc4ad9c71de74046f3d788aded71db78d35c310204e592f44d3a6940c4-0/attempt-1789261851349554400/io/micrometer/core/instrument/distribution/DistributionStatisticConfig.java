/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument.distribution;

import io.micrometer.core.instrument.distribution.PercentileHistogramBuckets;
import io.micrometer.core.instrument.internal.Mergeable;
import io.micrometer.core.lang.Nullable;
import java.time.Duration;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.LongStream;

public class DistributionStatisticConfig
implements Mergeable<DistributionStatisticConfig> {
    public static final DistributionStatisticConfig DEFAULT = DistributionStatisticConfig.builder().percentilesHistogram(false).percentilePrecision(1).minimumExpectedValue(1.0).maximumExpectedValue(Double.POSITIVE_INFINITY).expiry(Duration.ofMinutes(2L)).bufferLength(3).build();
    public static final DistributionStatisticConfig NONE = DistributionStatisticConfig.builder().build();
    @Nullable
    private Boolean percentileHistogram;
    @Nullable
    private double[] percentiles;
    @Nullable
    private Integer percentilePrecision;
    @Nullable
    private double[] serviceLevelObjectives;
    @Nullable
    private Double minimumExpectedValue;
    @Nullable
    private Double maximumExpectedValue;
    @Nullable
    private Duration expiry;
    @Nullable
    private Integer bufferLength;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public DistributionStatisticConfig merge(DistributionStatisticConfig parent) {
        return DistributionStatisticConfig.builder().percentilesHistogram(this.percentileHistogram == null ? parent.percentileHistogram : this.percentileHistogram).percentiles(this.percentiles == null ? parent.percentiles : this.percentiles).serviceLevelObjectives(this.serviceLevelObjectives == null ? parent.serviceLevelObjectives : this.serviceLevelObjectives).percentilePrecision(this.percentilePrecision == null ? parent.percentilePrecision : this.percentilePrecision).minimumExpectedValue(this.minimumExpectedValue == null ? parent.minimumExpectedValue : this.minimumExpectedValue).maximumExpectedValue(this.maximumExpectedValue == null ? parent.maximumExpectedValue : this.maximumExpectedValue).expiry(this.expiry == null ? parent.expiry : this.expiry).bufferLength(this.bufferLength == null ? parent.bufferLength : this.bufferLength).build();
    }

    public NavigableSet<Double> getHistogramBuckets(boolean supportsAggregablePercentiles) {
        TreeSet<Double> buckets = new TreeSet<Double>();
        if (this.percentileHistogram != null && this.percentileHistogram.booleanValue() && supportsAggregablePercentiles) {
            buckets.addAll(PercentileHistogramBuckets.buckets(this));
            buckets.add(this.minimumExpectedValue);
            buckets.add(this.maximumExpectedValue);
        }
        if (this.serviceLevelObjectives != null) {
            for (double slaBoundary : this.serviceLevelObjectives) {
                buckets.add(slaBoundary);
            }
        }
        return buckets;
    }

    @Nullable
    public Boolean isPercentileHistogram() {
        return this.percentileHistogram;
    }

    @Nullable
    public double[] getPercentiles() {
        return this.percentiles;
    }

    @Nullable
    public Integer getPercentilePrecision() {
        return this.percentilePrecision;
    }

    @Deprecated
    @Nullable
    public Double getMinimumExpectedValue() {
        return this.getMinimumExpectedValueAsDouble();
    }

    @Nullable
    public Double getMinimumExpectedValueAsDouble() {
        return this.minimumExpectedValue;
    }

    @Deprecated
    @Nullable
    public Double getMaximumExpectedValue() {
        return this.getMaximumExpectedValueAsDouble();
    }

    @Nullable
    public Double getMaximumExpectedValueAsDouble() {
        return this.maximumExpectedValue;
    }

    @Nullable
    public Duration getExpiry() {
        return this.expiry;
    }

    @Nullable
    public Integer getBufferLength() {
        return this.bufferLength;
    }

    @Nullable
    @Deprecated
    public double[] getSlaBoundaries() {
        return this.getServiceLevelObjectiveBoundaries();
    }

    @Nullable
    public double[] getServiceLevelObjectiveBoundaries() {
        return this.serviceLevelObjectives;
    }

    public boolean isPublishingPercentiles() {
        return this.percentiles != null && this.percentiles.length > 0;
    }

    public boolean isPublishingHistogram() {
        return this.percentileHistogram != null && this.percentileHistogram != false || this.serviceLevelObjectives != null && this.serviceLevelObjectives.length > 0;
    }

    static /* synthetic */ double[] access$102(DistributionStatisticConfig x0, double[] x1) {
        x0.percentiles = x1;
        return x1;
    }

    static /* synthetic */ double[] access$302(DistributionStatisticConfig x0, double[] x1) {
        x0.serviceLevelObjectives = x1;
        return x1;
    }

    public static class Builder {
        private final DistributionStatisticConfig config = new DistributionStatisticConfig();

        public Builder percentilesHistogram(@Nullable Boolean enabled) {
            this.config.percentileHistogram = enabled;
            return this;
        }

        public Builder percentiles(double ... percentiles) {
            DistributionStatisticConfig.access$102(this.config, percentiles);
            return this;
        }

        public Builder percentilePrecision(@Nullable Integer digitsOfPrecision) {
            this.config.percentilePrecision = digitsOfPrecision;
            return this;
        }

        public Builder serviceLevelObjectives(double ... slos) {
            DistributionStatisticConfig.access$302(this.config, slos);
            return this;
        }

        @Deprecated
        public Builder sla(double ... sla) {
            return this.serviceLevelObjectives(sla);
        }

        @Deprecated
        public Builder sla(long ... sla) {
            return sla == null ? this : this.serviceLevelObjectives(LongStream.of(sla).asDoubleStream().toArray());
        }

        @Deprecated
        public Builder minimumExpectedValue(@Nullable Long min) {
            return min == null ? this : this.minimumExpectedValue((double)min);
        }

        public Builder minimumExpectedValue(@Nullable Double min) {
            this.config.minimumExpectedValue = min;
            return this;
        }

        @Deprecated
        public Builder maximumExpectedValue(@Nullable Long max) {
            return max == null ? this : this.maximumExpectedValue((double)max);
        }

        public Builder maximumExpectedValue(@Nullable Double max) {
            this.config.maximumExpectedValue = max;
            return this;
        }

        public Builder expiry(@Nullable Duration expiry) {
            this.config.expiry = expiry;
            return this;
        }

        public Builder bufferLength(@Nullable Integer bufferLength) {
            this.config.bufferLength = bufferLength;
            return this;
        }

        public DistributionStatisticConfig build() {
            return this.config;
        }
    }
}


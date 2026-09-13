/*
 * Decompiled with CFR 0.152.
 */
package io.micrometer.core.instrument.distribution;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.distribution.AbstractTimeWindowHistogram;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.distribution.PercentileHistogramBuckets;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongArray;

public class TimeWindowFixedBoundaryHistogram
extends AbstractTimeWindowHistogram<FixedBoundaryHistogram, Void> {
    private final double[] buckets;
    private final boolean cumulativeBucketCounts;

    public TimeWindowFixedBoundaryHistogram(Clock clock, DistributionStatisticConfig config, boolean supportsAggregablePercentiles) {
        this(clock, config, supportsAggregablePercentiles, true);
    }

    public TimeWindowFixedBoundaryHistogram(Clock clock, DistributionStatisticConfig config, boolean supportsAggregablePercentiles, boolean cumulativeBucketCounts) {
        super(clock, config, FixedBoundaryHistogram.class, supportsAggregablePercentiles);
        this.cumulativeBucketCounts = cumulativeBucketCounts;
        NavigableSet<Double> histogramBuckets = this.distributionStatisticConfig.getHistogramBuckets(supportsAggregablePercentiles);
        Boolean percentileHistogram = this.distributionStatisticConfig.isPercentileHistogram();
        if (percentileHistogram != null && percentileHistogram.booleanValue()) {
            histogramBuckets.addAll(PercentileHistogramBuckets.buckets(this.distributionStatisticConfig));
        }
        this.buckets = histogramBuckets.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).toArray();
        this.initRingBuffer();
    }

    @Override
    FixedBoundaryHistogram newBucket() {
        return new FixedBoundaryHistogram();
    }

    @Override
    void recordLong(FixedBoundaryHistogram bucket, long value) {
        bucket.record(value);
    }

    @Override
    final void recordDouble(FixedBoundaryHistogram bucket, double value) {
        this.recordLong(bucket, (long)Math.ceil(value));
    }

    @Override
    void resetBucket(FixedBoundaryHistogram bucket) {
        bucket.reset();
    }

    Void newAccumulatedHistogram(FixedBoundaryHistogram[] ringBuffer) {
        return null;
    }

    @Override
    void accumulate() {
    }

    @Override
    void resetAccumulatedHistogram() {
    }

    @Override
    double valueAtPercentile(double percentile) {
        return 0.0;
    }

    @Override
    double countAtValue(double value) {
        return this.cumulativeBucketCounts ? (double)((FixedBoundaryHistogram)this.currentHistogram()).countAtValueCumulative(value) : (double)((FixedBoundaryHistogram)this.currentHistogram()).countAtValue(value);
    }

    @Override
    void outputSummary(PrintStream printStream, double bucketScaling) {
        printStream.format("%14s %10s\n\n", "Bucket", "TotalCount");
        String bucketFormatString = "%14.1f %10d\n";
        for (int i = 0; i < this.buckets.length; ++i) {
            printStream.format(Locale.US, bucketFormatString, this.buckets[i] / bucketScaling, ((FixedBoundaryHistogram)this.currentHistogram()).values.get(i));
        }
        printStream.write(10);
    }

    protected double[] getBuckets() {
        return this.buckets;
    }

    class FixedBoundaryHistogram {
        final AtomicLongArray values;

        FixedBoundaryHistogram() {
            this.values = new AtomicLongArray(TimeWindowFixedBoundaryHistogram.this.buckets.length);
        }

        long countAtValueCumulative(double value) {
            int index = Arrays.binarySearch(TimeWindowFixedBoundaryHistogram.this.buckets, value);
            if (index < 0) {
                return 0L;
            }
            long count = 0L;
            for (int i = 0; i <= index; ++i) {
                count += this.values.get(i);
            }
            return count;
        }

        long countAtValue(double value) {
            int index = Arrays.binarySearch(TimeWindowFixedBoundaryHistogram.this.buckets, value);
            if (index < 0) {
                return 0L;
            }
            return this.values.get(index);
        }

        void reset() {
            for (int i = 0; i < this.values.length(); ++i) {
                this.values.set(i, 0L);
            }
        }

        void record(long value) {
            int index = this.leastLessThanOrEqualTo(value);
            if (index > -1) {
                this.values.incrementAndGet(index);
            }
        }

        int leastLessThanOrEqualTo(long key) {
            int low = 0;
            int high = TimeWindowFixedBoundaryHistogram.this.buckets.length - 1;
            while (low <= high) {
                int mid = low + high >>> 1;
                if (TimeWindowFixedBoundaryHistogram.this.buckets[mid] < (double)key) {
                    low = mid + 1;
                    continue;
                }
                if (TimeWindowFixedBoundaryHistogram.this.buckets[mid] > (double)key) {
                    high = mid - 1;
                    continue;
                }
                return mid;
            }
            return low < TimeWindowFixedBoundaryHistogram.this.buckets.length ? low : -1;
        }
    }
}


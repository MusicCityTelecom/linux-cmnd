/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

public class TimerStats {
    private final long count;
    private final double mean;
    private final double max;

    public TimerStats(long count, double mean, double max) {
        this.count = count;
        this.mean = mean;
        this.max = max;
    }

    public long getCount() {
        return this.count;
    }

    public double getMean() {
        return this.mean;
    }

    public double getMax() {
        return this.max;
    }
}


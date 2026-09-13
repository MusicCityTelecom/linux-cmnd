/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.stats;

import org.springframework.retry.stats.ExponentialAverageRetryStatistics;
import org.springframework.retry.stats.MutableRetryStatistics;
import org.springframework.retry.stats.RetryStatisticsFactory;

public class DefaultRetryStatisticsFactory
implements RetryStatisticsFactory {
    private long window = 15000L;

    public void setWindow(long window) {
        this.window = window;
    }

    @Override
    public MutableRetryStatistics create(String name) {
        ExponentialAverageRetryStatistics stats = new ExponentialAverageRetryStatistics(name);
        stats.setWindow(this.window);
        return stats;
    }
}


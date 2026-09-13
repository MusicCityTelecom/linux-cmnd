/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.stats;

import org.springframework.retry.stats.MutableRetryStatistics;

public interface RetryStatisticsFactory {
    public MutableRetryStatistics create(String var1);
}


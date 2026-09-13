/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.retry.stats;

import org.springframework.retry.RetryStatistics;

public interface StatisticsRepository {
    public RetryStatistics findOne(String var1);

    public Iterable<RetryStatistics> findAll();

    public void addStarted(String var1);

    public void addError(String var1);

    public void addRecovery(String var1);

    public void addComplete(String var1);

    public void addAbort(String var1);
}


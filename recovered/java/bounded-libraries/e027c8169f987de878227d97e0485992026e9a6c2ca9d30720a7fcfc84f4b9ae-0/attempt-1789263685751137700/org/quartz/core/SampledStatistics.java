/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

public interface SampledStatistics {
    public long getJobsScheduledMostRecentSample();

    public long getJobsExecutingMostRecentSample();

    public long getJobsCompletedMostRecentSample();

    public void shutdown();
}


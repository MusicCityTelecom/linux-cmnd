/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import org.quartz.core.SampledStatistics;

public class NullSampledStatisticsImpl
implements SampledStatistics {
    @Override
    public long getJobsCompletedMostRecentSample() {
        return 0L;
    }

    @Override
    public long getJobsExecutingMostRecentSample() {
        return 0L;
    }

    @Override
    public long getJobsScheduledMostRecentSample() {
        return 0L;
    }

    @Override
    public void shutdown() {
    }
}


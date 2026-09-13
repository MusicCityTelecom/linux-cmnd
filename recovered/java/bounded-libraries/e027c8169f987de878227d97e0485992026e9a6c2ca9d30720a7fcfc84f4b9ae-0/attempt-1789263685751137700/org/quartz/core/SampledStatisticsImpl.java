/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import java.util.Timer;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.SampledStatistics;
import org.quartz.listeners.SchedulerListenerSupport;
import org.quartz.utils.counter.CounterConfig;
import org.quartz.utils.counter.CounterManager;
import org.quartz.utils.counter.CounterManagerImpl;
import org.quartz.utils.counter.sampled.SampledCounter;
import org.quartz.utils.counter.sampled.SampledCounterConfig;
import org.quartz.utils.counter.sampled.SampledRateCounterConfig;

public class SampledStatisticsImpl
extends SchedulerListenerSupport
implements SampledStatistics,
JobListener,
SchedulerListener {
    private final QuartzScheduler scheduler;
    private static final String NAME = "QuartzSampledStatistics";
    private static final int DEFAULT_HISTORY_SIZE = 30;
    private static final int DEFAULT_INTERVAL_SECS = 1;
    private static final SampledCounterConfig DEFAULT_SAMPLED_COUNTER_CONFIG = new SampledCounterConfig(1, 30, true, 0L);
    private static final SampledRateCounterConfig DEFAULT_SAMPLED_RATE_COUNTER_CONFIG = new SampledRateCounterConfig(1, 30, true);
    private volatile CounterManager counterManager;
    private final SampledCounter jobsScheduledCount;
    private final SampledCounter jobsExecutingCount;
    private final SampledCounter jobsCompletedCount;

    SampledStatisticsImpl(QuartzScheduler scheduler) {
        this.scheduler = scheduler;
        this.counterManager = new CounterManagerImpl(new Timer("QuartzSampledStatisticsTimer"));
        this.jobsScheduledCount = this.createSampledCounter(DEFAULT_SAMPLED_COUNTER_CONFIG);
        this.jobsExecutingCount = this.createSampledCounter(DEFAULT_SAMPLED_COUNTER_CONFIG);
        this.jobsCompletedCount = this.createSampledCounter(DEFAULT_SAMPLED_COUNTER_CONFIG);
        scheduler.addInternalSchedulerListener(this);
        scheduler.addInternalJobListener(this);
    }

    @Override
    public void shutdown() {
        this.counterManager.shutdown(true);
    }

    private SampledCounter createSampledCounter(CounterConfig defaultCounterConfig) {
        return (SampledCounter)this.counterManager.createCounter(defaultCounterConfig);
    }

    public void clearStatistics() {
        this.jobsScheduledCount.getAndReset();
        this.jobsExecutingCount.getAndReset();
        this.jobsCompletedCount.getAndReset();
    }

    @Override
    public long getJobsCompletedMostRecentSample() {
        return this.jobsCompletedCount.getMostRecentSample().getCounterValue();
    }

    @Override
    public long getJobsExecutingMostRecentSample() {
        return this.jobsExecutingCount.getMostRecentSample().getCounterValue();
    }

    @Override
    public long getJobsScheduledMostRecentSample() {
        return this.jobsScheduledCount.getMostRecentSample().getCounterValue();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void jobScheduled(Trigger trigger) {
        this.jobsScheduledCount.increment();
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        this.jobsExecutingCount.increment();
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        this.jobsCompletedCount.increment();
    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
    }

    public void jobDeleted(String jobName, String groupName) {
    }
}


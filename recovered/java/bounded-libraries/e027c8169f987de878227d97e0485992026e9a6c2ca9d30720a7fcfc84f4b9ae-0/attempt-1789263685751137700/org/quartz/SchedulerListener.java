/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public interface SchedulerListener {
    public void jobScheduled(Trigger var1);

    public void jobUnscheduled(TriggerKey var1);

    public void triggerFinalized(Trigger var1);

    public void triggerPaused(TriggerKey var1);

    public void triggersPaused(String var1);

    public void triggerResumed(TriggerKey var1);

    public void triggersResumed(String var1);

    public void jobAdded(JobDetail var1);

    public void jobDeleted(JobKey var1);

    public void jobPaused(JobKey var1);

    public void jobsPaused(String var1);

    public void jobResumed(JobKey var1);

    public void jobsResumed(String var1);

    public void schedulerError(String var1, SchedulerException var2);

    public void schedulerInStandbyMode();

    public void schedulerStarted();

    public void schedulerStarting();

    public void schedulerShutdown();

    public void schedulerShuttingdown();

    public void schedulingDataCleared();
}


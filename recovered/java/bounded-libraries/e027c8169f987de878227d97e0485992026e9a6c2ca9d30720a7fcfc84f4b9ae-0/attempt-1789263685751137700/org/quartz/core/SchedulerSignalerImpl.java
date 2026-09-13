/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.core;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.QuartzSchedulerThread;
import org.quartz.spi.SchedulerSignaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerSignalerImpl
implements SchedulerSignaler {
    Logger log = LoggerFactory.getLogger(SchedulerSignalerImpl.class);
    protected QuartzScheduler sched;
    protected QuartzSchedulerThread schedThread;

    public SchedulerSignalerImpl(QuartzScheduler sched, QuartzSchedulerThread schedThread) {
        this.sched = sched;
        this.schedThread = schedThread;
        this.log.info("Initialized Scheduler Signaller of type: " + this.getClass());
    }

    @Override
    public void notifyTriggerListenersMisfired(Trigger trigger) {
        try {
            this.sched.notifyTriggerListenersMisfired(trigger);
        }
        catch (SchedulerException se) {
            this.sched.getLog().error("Error notifying listeners of trigger misfire.", (Throwable)se);
            this.sched.notifySchedulerListenersError("Error notifying listeners of trigger misfire.", se);
        }
    }

    @Override
    public void notifySchedulerListenersFinalized(Trigger trigger) {
        this.sched.notifySchedulerListenersFinalized(trigger);
    }

    @Override
    public void signalSchedulingChange(long candidateNewNextFireTime) {
        this.schedThread.signalSchedulingChange(candidateNewNextFireTime);
    }

    @Override
    public void notifySchedulerListenersJobDeleted(JobKey jobKey) {
        this.sched.notifySchedulerListenersJobDeleted(jobKey);
    }

    @Override
    public void notifySchedulerListenersError(String string, SchedulerException jpe) {
        this.sched.notifySchedulerListenersError(string, jpe);
    }
}


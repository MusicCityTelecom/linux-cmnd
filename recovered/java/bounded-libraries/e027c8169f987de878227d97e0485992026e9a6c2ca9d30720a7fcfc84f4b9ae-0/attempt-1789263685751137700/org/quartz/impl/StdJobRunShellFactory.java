/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.core.JobRunShellFactory;
import org.quartz.spi.TriggerFiredBundle;

public class StdJobRunShellFactory
implements JobRunShellFactory {
    private Scheduler scheduler;

    @Override
    public void initialize(Scheduler sched) {
        this.scheduler = sched;
    }

    @Override
    public JobRunShell createJobRunShell(TriggerFiredBundle bndle) throws SchedulerException {
        return new JobRunShell(this.scheduler, bndle);
    }
}


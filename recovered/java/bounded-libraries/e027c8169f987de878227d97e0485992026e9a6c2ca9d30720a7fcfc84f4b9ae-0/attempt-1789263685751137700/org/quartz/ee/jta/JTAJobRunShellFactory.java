/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.ee.jta;

import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.core.JobRunShellFactory;
import org.quartz.ee.jta.JTAJobRunShell;
import org.quartz.spi.TriggerFiredBundle;

public class JTAJobRunShellFactory
implements JobRunShellFactory {
    private Scheduler scheduler;

    @Override
    public void initialize(Scheduler sched) throws SchedulerConfigException {
        this.scheduler = sched;
    }

    @Override
    public JobRunShell createJobRunShell(TriggerFiredBundle bundle) throws SchedulerException {
        return new JTAJobRunShell(this.scheduler, bundle);
    }
}


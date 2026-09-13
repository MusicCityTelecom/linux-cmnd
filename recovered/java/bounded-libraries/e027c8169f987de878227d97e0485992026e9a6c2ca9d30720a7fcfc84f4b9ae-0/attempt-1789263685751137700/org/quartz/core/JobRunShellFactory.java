/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.spi.TriggerFiredBundle;

public interface JobRunShellFactory {
    public void initialize(Scheduler var1) throws SchedulerConfigException;

    public JobRunShell createJobRunShell(TriggerFiredBundle var1) throws SchedulerException;
}


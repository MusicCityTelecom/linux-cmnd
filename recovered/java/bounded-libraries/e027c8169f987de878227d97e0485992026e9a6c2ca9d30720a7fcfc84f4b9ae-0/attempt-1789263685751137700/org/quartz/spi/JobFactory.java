/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.TriggerFiredBundle;

public interface JobFactory {
    public Job newJob(TriggerFiredBundle var1, Scheduler var2) throws SchedulerException;
}


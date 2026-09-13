/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.util.Collection;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public interface SchedulerFactory {
    public Scheduler getScheduler() throws SchedulerException;

    public Scheduler getScheduler(String var1) throws SchedulerException;

    public Collection<Scheduler> getAllSchedulers() throws SchedulerException;
}


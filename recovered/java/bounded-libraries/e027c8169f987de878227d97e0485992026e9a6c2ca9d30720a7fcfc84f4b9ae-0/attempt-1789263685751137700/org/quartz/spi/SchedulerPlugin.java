/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.ClassLoadHelper;

public interface SchedulerPlugin {
    public void initialize(String var1, Scheduler var2, ClassLoadHelper var3) throws SchedulerException;

    public void start();

    public void shutdown();
}


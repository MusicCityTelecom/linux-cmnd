/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import java.util.Date;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;

public interface TimeBroker {
    public Date getCurrentTime() throws SchedulerException;

    public void initialize() throws SchedulerConfigException;

    public void shutdown();
}


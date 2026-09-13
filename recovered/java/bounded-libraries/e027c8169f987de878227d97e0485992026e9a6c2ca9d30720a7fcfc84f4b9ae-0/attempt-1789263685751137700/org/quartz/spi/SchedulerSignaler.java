/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

public interface SchedulerSignaler {
    public void notifyTriggerListenersMisfired(Trigger var1);

    public void notifySchedulerListenersFinalized(Trigger var1);

    public void notifySchedulerListenersJobDeleted(JobKey var1);

    public void signalSchedulingChange(long var1);

    public void notifySchedulerListenersError(String var1, SchedulerException var2);
}


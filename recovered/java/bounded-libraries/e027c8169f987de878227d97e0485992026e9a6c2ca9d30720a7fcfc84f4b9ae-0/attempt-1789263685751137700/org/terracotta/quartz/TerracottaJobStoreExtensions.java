/*
 * Decompiled with CFR 0.152.
 */
package org.terracotta.quartz;

import org.quartz.JobListener;
import org.quartz.spi.JobStore;

public interface TerracottaJobStoreExtensions
extends JobStore,
JobListener {
    public void setMisfireThreshold(long var1);

    public void setEstimatedTimeToReleaseAndAcquireTrigger(long var1);

    public void setSynchronousWrite(String var1);

    @Override
    public void setThreadPoolSize(int var1);

    public String getUUID();

    public void setTcRetryInterval(long var1);
}


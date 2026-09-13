/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import org.quartz.SchedulerConfigException;

public interface ThreadPool {
    public boolean runInThread(Runnable var1);

    public int blockForAvailableThreads();

    public void initialize() throws SchedulerConfigException;

    public void shutdown(boolean var1);

    public int getPoolSize();

    public void setInstanceId(String var1);

    public void setInstanceName(String var1);
}


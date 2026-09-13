/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.management;

import org.quartz.core.QuartzScheduler;

public interface ManagementServer {
    public void start();

    public void stop();

    public void register(QuartzScheduler var1);

    public void unregister(QuartzScheduler var1);

    public boolean hasRegistered();
}


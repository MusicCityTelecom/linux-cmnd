/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.toolkit.cluster.ClusterListener
 */
package org.terracotta.quartz;

import org.quartz.spi.JobStore;
import org.terracotta.toolkit.cluster.ClusterListener;

public interface ClusteredJobStore
extends JobStore,
ClusterListener {
    public void setMisfireThreshold(long var1);

    public void setEstimatedTimeToReleaseAndAcquireTrigger(long var1);

    public void setTcRetryInterval(long var1);
}


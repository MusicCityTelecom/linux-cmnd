/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface JobListener {
    public String getName();

    public void jobToBeExecuted(JobExecutionContext var1);

    public void jobExecutionVetoed(JobExecutionContext var1);

    public void jobWasExecuted(JobExecutionContext var1, JobExecutionException var2);
}


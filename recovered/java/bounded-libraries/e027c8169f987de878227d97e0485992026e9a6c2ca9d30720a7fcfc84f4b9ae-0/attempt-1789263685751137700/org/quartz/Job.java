/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public interface Job {
    public void execute(JobExecutionContext var1) throws JobExecutionException;
}


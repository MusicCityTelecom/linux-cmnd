/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.io.Serializable;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobKey;

public interface JobDetail
extends Serializable,
Cloneable {
    public JobKey getKey();

    public String getDescription();

    public Class<? extends Job> getJobClass();

    public JobDataMap getJobDataMap();

    public boolean isDurable();

    public boolean isPersistJobDataAfterExecution();

    public boolean isConcurrentExectionDisallowed();

    public boolean requestsRecovery();

    public Object clone();

    public JobBuilder getJobBuilder();
}


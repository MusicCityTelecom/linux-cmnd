/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.listeners;

import java.util.HashMap;
import java.util.Map;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.listeners.JobListenerSupport;

public class JobChainingJobListener
extends JobListenerSupport {
    private String name;
    private Map<JobKey, JobKey> chainLinks;

    public JobChainingJobListener(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Listener name cannot be null!");
        }
        this.name = name;
        this.chainLinks = new HashMap<JobKey, JobKey>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void addJobChainLink(JobKey firstJob, JobKey secondJob) {
        if (firstJob == null || secondJob == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
        if (firstJob.getName() == null || secondJob.getName() == null) {
            throw new IllegalArgumentException("Key cannot have a null name!");
        }
        this.chainLinks.put(firstJob, secondJob);
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        JobKey sj = this.chainLinks.get(context.getJobDetail().getKey());
        if (sj == null) {
            return;
        }
        this.getLog().info("Job '" + context.getJobDetail().getKey() + "' will now chain to Job '" + sj + "'");
        try {
            context.getScheduler().triggerJob(sj);
        }
        catch (SchedulerException se) {
            this.getLog().error("Error encountered during chaining to Job '" + sj + "'", (Throwable)se);
        }
    }
}


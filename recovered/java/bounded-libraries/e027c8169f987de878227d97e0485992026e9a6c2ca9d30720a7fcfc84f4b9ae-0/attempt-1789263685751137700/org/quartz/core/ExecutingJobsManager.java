/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.spi.OperableTrigger;

class ExecutingJobsManager
implements JobListener {
    HashMap<String, JobExecutionContext> executingJobs = new HashMap();
    AtomicInteger numJobsFired = new AtomicInteger(0);

    ExecutingJobsManager() {
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getNumJobsCurrentlyExecuting() {
        HashMap<String, JobExecutionContext> hashMap = this.executingJobs;
        synchronized (hashMap) {
            return this.executingJobs.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        this.numJobsFired.incrementAndGet();
        HashMap<String, JobExecutionContext> hashMap = this.executingJobs;
        synchronized (hashMap) {
            this.executingJobs.put(((OperableTrigger)context.getTrigger()).getFireInstanceId(), context);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        HashMap<String, JobExecutionContext> hashMap = this.executingJobs;
        synchronized (hashMap) {
            this.executingJobs.remove(((OperableTrigger)context.getTrigger()).getFireInstanceId());
        }
    }

    public int getNumJobsFired() {
        return this.numJobsFired.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<JobExecutionContext> getExecutingJobs() {
        HashMap<String, JobExecutionContext> hashMap = this.executingJobs;
        synchronized (hashMap) {
            return Collections.unmodifiableList(new ArrayList<JobExecutionContext>(this.executingJobs.values()));
        }
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }
}


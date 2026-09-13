/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.core;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.core.QuartzScheduler;
import org.quartz.impl.JobExecutionContextImpl;
import org.quartz.listeners.SchedulerListenerSupport;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobRunShell
extends SchedulerListenerSupport
implements Runnable {
    protected JobExecutionContextImpl jec = null;
    protected QuartzScheduler qs = null;
    protected TriggerFiredBundle firedTriggerBundle = null;
    protected Scheduler scheduler = null;
    protected volatile boolean shutdownRequested = false;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public JobRunShell(Scheduler scheduler, TriggerFiredBundle bndle) {
        this.scheduler = scheduler;
        this.firedTriggerBundle = bndle;
    }

    @Override
    public void schedulerShuttingdown() {
        this.requestShutdown();
    }

    @Override
    protected Logger getLog() {
        return this.log;
    }

    public void initialize(QuartzScheduler sched) throws SchedulerException {
        this.qs = sched;
        Job job = null;
        JobDetail jobDetail = this.firedTriggerBundle.getJobDetail();
        try {
            job = sched.getJobFactory().newJob(this.firedTriggerBundle, this.scheduler);
        }
        catch (SchedulerException se) {
            sched.notifySchedulerListenersError("An error occured instantiating job to be executed. job= '" + jobDetail.getKey() + "'", se);
            throw se;
        }
        catch (Throwable ncdfe) {
            SchedulerException se = new SchedulerException("Problem instantiating class '" + jobDetail.getJobClass().getName() + "' - ", ncdfe);
            sched.notifySchedulerListenersError("An error occured instantiating job to be executed. job= '" + jobDetail.getKey() + "'", se);
            throw se;
        }
        this.jec = new JobExecutionContextImpl(this.scheduler, this.firedTriggerBundle, job);
    }

    public void requestShutdown() {
        this.shutdownRequested = true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        block24: {
            this.qs.addInternalSchedulerListener(this);
            try {
                Trigger.CompletedExecutionInstruction instCode;
                OperableTrigger trigger = (OperableTrigger)this.jec.getTrigger();
                JobDetail jobDetail = this.jec.getJobDetail();
                while (true) {
                    long startTime;
                    Job job;
                    JobExecutionException jobExEx;
                    block25: {
                        jobExEx = null;
                        job = this.jec.getJobInstance();
                        try {
                            this.begin();
                        }
                        catch (SchedulerException se) {
                            this.qs.notifySchedulerListenersError("Error executing Job (" + this.jec.getJobDetail().getKey() + ": couldn't begin execution.", se);
                            break block24;
                        }
                        try {
                            if (!this.notifyListenersBeginning(this.jec)) {
                            }
                            break block25;
                        }
                        catch (VetoedException ve) {
                            try {
                                Trigger.CompletedExecutionInstruction instCode2 = trigger.executionComplete(this.jec, null);
                                this.qs.notifyJobStoreJobVetoed(trigger, jobDetail, instCode2);
                                if (this.jec.getTrigger().getNextFireTime() == null) {
                                    this.qs.notifySchedulerListenersFinalized(this.jec.getTrigger());
                                }
                                this.complete(true);
                                break block24;
                            }
                            catch (SchedulerException se) {
                                this.qs.notifySchedulerListenersError("Error during veto of Job (" + this.jec.getJobDetail().getKey() + ": couldn't finalize execution.", se);
                            }
                        }
                        break block24;
                    }
                    long endTime = startTime = System.currentTimeMillis();
                    try {
                        this.log.debug("Calling execute on job " + jobDetail.getKey());
                        job.execute(this.jec);
                        endTime = System.currentTimeMillis();
                    }
                    catch (JobExecutionException jee) {
                        endTime = System.currentTimeMillis();
                        jobExEx = jee;
                        this.getLog().info("Job " + jobDetail.getKey() + " threw a JobExecutionException: ", (Throwable)jobExEx);
                    }
                    catch (Throwable e) {
                        endTime = System.currentTimeMillis();
                        this.getLog().error("Job " + jobDetail.getKey() + " threw an unhandled Exception: ", e);
                        SchedulerException se = new SchedulerException("Job threw an unhandled exception.", e);
                        this.qs.notifySchedulerListenersError("Job (" + this.jec.getJobDetail().getKey() + " threw an exception.", se);
                        jobExEx = new JobExecutionException(se, false);
                    }
                    this.jec.setJobRunTime(endTime - startTime);
                    if (!this.notifyJobListenersComplete(this.jec, jobExEx)) {
                        break block24;
                    }
                    instCode = Trigger.CompletedExecutionInstruction.NOOP;
                    try {
                        instCode = trigger.executionComplete(this.jec, jobExEx);
                    }
                    catch (Exception e) {
                        SchedulerException se = new SchedulerException("Trigger threw an unhandled exception.", e);
                        this.qs.notifySchedulerListenersError("Please report this error to the Quartz developers.", se);
                    }
                    if (!this.notifyTriggerListenersComplete(this.jec, instCode)) {
                        break block24;
                    }
                    if (instCode == Trigger.CompletedExecutionInstruction.RE_EXECUTE_JOB) {
                        this.jec.incrementRefireCount();
                        try {
                            this.complete(false);
                        }
                        catch (SchedulerException se) {
                            this.qs.notifySchedulerListenersError("Error executing Job (" + this.jec.getJobDetail().getKey() + ": couldn't finalize execution.", se);
                        }
                        continue;
                    }
                    try {
                        this.complete(true);
                    }
                    catch (SchedulerException se) {
                        this.qs.notifySchedulerListenersError("Error executing Job (" + this.jec.getJobDetail().getKey() + ": couldn't finalize execution.", se);
                        continue;
                    }
                    break;
                }
                this.qs.notifyJobStoreJobComplete(trigger, jobDetail, instCode);
            }
            finally {
                this.qs.removeInternalSchedulerListener(this);
            }
        }
    }

    protected void begin() throws SchedulerException {
    }

    protected void complete(boolean successfulExecution) throws SchedulerException {
    }

    public void passivate() {
        this.jec = null;
        this.qs = null;
    }

    private boolean notifyListenersBeginning(JobExecutionContext jobExCtxt) throws VetoedException {
        boolean vetoed = false;
        try {
            vetoed = this.qs.notifyTriggerListenersFired(jobExCtxt);
        }
        catch (SchedulerException se) {
            this.qs.notifySchedulerListenersError("Unable to notify TriggerListener(s) while firing trigger (Trigger and Job will NOT be fired!). trigger= " + jobExCtxt.getTrigger().getKey() + " job= " + jobExCtxt.getJobDetail().getKey(), se);
            return false;
        }
        if (vetoed) {
            try {
                this.qs.notifyJobListenersWasVetoed(jobExCtxt);
            }
            catch (SchedulerException se) {
                this.qs.notifySchedulerListenersError("Unable to notify JobListener(s) of vetoed execution while firing trigger (Trigger and Job will NOT be fired!). trigger= " + jobExCtxt.getTrigger().getKey() + " job= " + jobExCtxt.getJobDetail().getKey(), se);
            }
            throw new VetoedException();
        }
        try {
            this.qs.notifyJobListenersToBeExecuted(jobExCtxt);
        }
        catch (SchedulerException se) {
            this.qs.notifySchedulerListenersError("Unable to notify JobListener(s) of Job to be executed: (Job will NOT be executed!). trigger= " + jobExCtxt.getTrigger().getKey() + " job= " + jobExCtxt.getJobDetail().getKey(), se);
            return false;
        }
        return true;
    }

    private boolean notifyJobListenersComplete(JobExecutionContext jobExCtxt, JobExecutionException jobExEx) {
        try {
            this.qs.notifyJobListenersWasExecuted(jobExCtxt, jobExEx);
        }
        catch (SchedulerException se) {
            this.qs.notifySchedulerListenersError("Unable to notify JobListener(s) of Job that was executed: (error will be ignored). trigger= " + jobExCtxt.getTrigger().getKey() + " job= " + jobExCtxt.getJobDetail().getKey(), se);
            return false;
        }
        return true;
    }

    private boolean notifyTriggerListenersComplete(JobExecutionContext jobExCtxt, Trigger.CompletedExecutionInstruction instCode) {
        try {
            this.qs.notifyTriggerListenersComplete(jobExCtxt, instCode);
        }
        catch (SchedulerException se) {
            this.qs.notifySchedulerListenersError("Unable to notify TriggerListener(s) of Job that was executed: (error will be ignored). trigger= " + jobExCtxt.getTrigger().getKey() + " job= " + jobExCtxt.getJobDetail().getKey(), se);
            return false;
        }
        if (jobExCtxt.getTrigger().getNextFireTime() == null) {
            this.qs.notifySchedulerListenersFinalized(jobExCtxt.getTrigger());
        }
        return true;
    }

    static class VetoedException
    extends Exception {
        private static final long serialVersionUID = 1539955697495918463L;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.plugins.interrupt;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.SchedulerPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobInterruptMonitorPlugin
extends TriggerListenerSupport
implements SchedulerPlugin {
    private static final String JOB_INTERRUPT_MONITOR_KEY = "JOB_INTERRUPT_MONITOR_KEY";
    private long DEFAULT_MAX_RUNTIME = 300000L;
    private String name;
    private ScheduledExecutorService executor;
    private ScheduledFuture future;
    private Scheduler scheduler;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public static final String AUTO_INTERRUPTIBLE = "AutoInterruptable";
    public static final String MAX_RUN_TIME = "MaxRunTime";

    @Override
    public void start() {
    }

    @Override
    public void shutdown() {
        this.executor.shutdown();
    }

    @Override
    protected Logger getLog() {
        return this.log;
    }

    public ScheduledFuture scheduleJobInterruptMonitor(JobKey jobkey, long delay) {
        return this.executor.schedule(new InterruptMonitor(jobkey, this.scheduler), delay, TimeUnit.MILLISECONDS);
    }

    public long getDefaultMaxRunTime() {
        return this.DEFAULT_MAX_RUNTIME;
    }

    public void setDefaultMaxRunTime(long defaultMaxRunTime) {
        this.DEFAULT_MAX_RUNTIME = defaultMaxRunTime;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        try {
            if (context.getJobDetail().getJobDataMap().getBoolean(AUTO_INTERRUPTIBLE)) {
                JobInterruptMonitorPlugin monitorPlugin = (JobInterruptMonitorPlugin)context.getScheduler().getContext().get(JOB_INTERRUPT_MONITOR_KEY);
                long jobDataDelay = this.DEFAULT_MAX_RUNTIME;
                if (context.getJobDetail().getJobDataMap().get(MAX_RUN_TIME) != null) {
                    jobDataDelay = context.getJobDetail().getJobDataMap().getLong(MAX_RUN_TIME);
                }
                this.future = monitorPlugin.scheduleJobInterruptMonitor(context.getJobDetail().getKey(), jobDataDelay);
                this.getLog().debug("Job's Interrupt Monitor has been scheduled to interrupt with the delay :" + this.DEFAULT_MAX_RUNTIME);
            }
        }
        catch (SchedulerException e) {
            this.getLog().info("Error scheduling interrupt monitor " + e.getMessage(), (Throwable)e);
        }
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        if (this.future != null) {
            this.future.cancel(true);
        }
    }

    @Override
    public void initialize(String name, Scheduler scheduler, ClassLoadHelper helper) throws SchedulerException {
        this.getLog().info("Registering Job Interrupt Monitor Plugin");
        this.name = name;
        this.executor = Executors.newScheduledThreadPool(1);
        scheduler.getContext().put(JOB_INTERRUPT_MONITOR_KEY, (Object)this);
        this.scheduler = scheduler;
        this.scheduler.getListenerManager().addTriggerListener(this);
    }

    static class InterruptMonitor
    implements Runnable {
        private final JobKey jobKey;
        private final Scheduler scheduler;
        private final Logger log = LoggerFactory.getLogger(this.getClass());

        InterruptMonitor(JobKey jobKey, Scheduler scheduler) {
            this.jobKey = jobKey;
            this.scheduler = scheduler;
        }

        protected Logger getLog() {
            return this.log;
        }

        @Override
        public void run() {
            try {
                this.getLog().info("Interrupting Job as it ran more than the configured max time. Job Details [" + this.jobKey.getName() + ":" + this.jobKey.getGroup() + "]");
                this.scheduler.interrupt(this.jobKey);
            }
            catch (SchedulerException x) {
                this.getLog().info("Error interrupting Job: " + x.getMessage(), (Throwable)x);
            }
        }
    }
}


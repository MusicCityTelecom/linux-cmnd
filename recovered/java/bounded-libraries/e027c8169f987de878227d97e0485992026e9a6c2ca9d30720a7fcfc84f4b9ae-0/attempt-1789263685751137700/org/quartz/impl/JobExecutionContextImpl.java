/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.TriggerFiredBundle;

public class JobExecutionContextImpl
implements Serializable,
JobExecutionContext {
    private static final long serialVersionUID = -8139417614523942021L;
    private transient Scheduler scheduler;
    private Trigger trigger;
    private JobDetail jobDetail;
    private JobDataMap jobDataMap;
    private transient Job job;
    private Calendar calendar;
    private boolean recovering = false;
    private int numRefires = 0;
    private Date fireTime;
    private Date scheduledFireTime;
    private Date prevFireTime;
    private Date nextFireTime;
    private long jobRunTime = -1L;
    private Object result;
    private HashMap<Object, Object> data = new HashMap();

    public JobExecutionContextImpl(Scheduler scheduler, TriggerFiredBundle firedBundle, Job job) {
        this.scheduler = scheduler;
        this.trigger = firedBundle.getTrigger();
        this.calendar = firedBundle.getCalendar();
        this.jobDetail = firedBundle.getJobDetail();
        this.job = job;
        this.recovering = firedBundle.isRecovering();
        this.fireTime = firedBundle.getFireTime();
        this.scheduledFireTime = firedBundle.getScheduledFireTime();
        this.prevFireTime = firedBundle.getPrevFireTime();
        this.nextFireTime = firedBundle.getNextFireTime();
        this.jobDataMap = new JobDataMap();
        this.jobDataMap.putAll(this.jobDetail.getJobDataMap());
        this.jobDataMap.putAll(this.trigger.getJobDataMap());
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public Trigger getTrigger() {
        return this.trigger;
    }

    @Override
    public Calendar getCalendar() {
        return this.calendar;
    }

    @Override
    public boolean isRecovering() {
        return this.recovering;
    }

    @Override
    public TriggerKey getRecoveringTriggerKey() {
        if (this.isRecovering()) {
            return new TriggerKey(this.jobDataMap.getString("QRTZ_FAILED_JOB_ORIG_TRIGGER_NAME"), this.jobDataMap.getString("QRTZ_FAILED_JOB_ORIG_TRIGGER_GROUP"));
        }
        throw new IllegalStateException("Not a recovering job");
    }

    public void incrementRefireCount() {
        ++this.numRefires;
    }

    @Override
    public int getRefireCount() {
        return this.numRefires;
    }

    @Override
    public JobDataMap getMergedJobDataMap() {
        return this.jobDataMap;
    }

    @Override
    public JobDetail getJobDetail() {
        return this.jobDetail;
    }

    @Override
    public Job getJobInstance() {
        return this.job;
    }

    @Override
    public Date getFireTime() {
        return this.fireTime;
    }

    @Override
    public Date getScheduledFireTime() {
        return this.scheduledFireTime;
    }

    @Override
    public Date getPreviousFireTime() {
        return this.prevFireTime;
    }

    @Override
    public Date getNextFireTime() {
        return this.nextFireTime;
    }

    public String toString() {
        return "JobExecutionContext: trigger: '" + this.getTrigger().getKey() + " job: " + this.getJobDetail().getKey() + " fireTime: '" + this.getFireTime() + " scheduledFireTime: " + this.getScheduledFireTime() + " previousFireTime: '" + this.getPreviousFireTime() + " nextFireTime: " + this.getNextFireTime() + " isRecovering: " + this.isRecovering() + " refireCount: " + this.getRefireCount();
    }

    @Override
    public Object getResult() {
        return this.result;
    }

    @Override
    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public long getJobRunTime() {
        return this.jobRunTime;
    }

    public void setJobRunTime(long jobRunTime) {
        this.jobRunTime = jobRunTime;
    }

    @Override
    public void put(Object key, Object value) {
        this.data.put(key, value);
    }

    @Override
    public Object get(Object key) {
        return this.data.get(key);
    }

    @Override
    public String getFireInstanceId() {
        return ((OperableTrigger)this.trigger).getFireInstanceId();
    }
}


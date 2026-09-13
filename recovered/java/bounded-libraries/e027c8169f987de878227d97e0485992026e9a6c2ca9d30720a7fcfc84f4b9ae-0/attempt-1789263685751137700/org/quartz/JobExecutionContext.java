/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.util.Date;
import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public interface JobExecutionContext {
    public Scheduler getScheduler();

    public Trigger getTrigger();

    public Calendar getCalendar();

    public boolean isRecovering();

    public TriggerKey getRecoveringTriggerKey() throws IllegalStateException;

    public int getRefireCount();

    public JobDataMap getMergedJobDataMap();

    public JobDetail getJobDetail();

    public Job getJobInstance();

    public Date getFireTime();

    public Date getScheduledFireTime();

    public Date getPreviousFireTime();

    public Date getNextFireTime();

    public String getFireInstanceId();

    public Object getResult();

    public void setResult(Object var1);

    public long getJobRunTime();

    public void put(Object var1, Object var2);

    public Object get(Object var1);
}


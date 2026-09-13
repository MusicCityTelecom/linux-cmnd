/*
 * Decompiled with CFR 0.152.
 */
package org.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;

public interface Scheduler {
    public static final String DEFAULT_GROUP = "DEFAULT";
    public static final String DEFAULT_RECOVERY_GROUP = "RECOVERING_JOBS";
    public static final String DEFAULT_FAIL_OVER_GROUP = "FAILED_OVER_JOBS";
    public static final String FAILED_JOB_ORIGINAL_TRIGGER_NAME = "QRTZ_FAILED_JOB_ORIG_TRIGGER_NAME";
    public static final String FAILED_JOB_ORIGINAL_TRIGGER_GROUP = "QRTZ_FAILED_JOB_ORIG_TRIGGER_GROUP";
    public static final String FAILED_JOB_ORIGINAL_TRIGGER_FIRETIME_IN_MILLISECONDS = "QRTZ_FAILED_JOB_ORIG_TRIGGER_FIRETIME_IN_MILLISECONDS_AS_STRING";
    public static final String FAILED_JOB_ORIGINAL_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS = "QRTZ_FAILED_JOB_ORIG_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS_AS_STRING";

    public String getSchedulerName() throws SchedulerException;

    public String getSchedulerInstanceId() throws SchedulerException;

    public SchedulerContext getContext() throws SchedulerException;

    public void start() throws SchedulerException;

    public void startDelayed(int var1) throws SchedulerException;

    public boolean isStarted() throws SchedulerException;

    public void standby() throws SchedulerException;

    public boolean isInStandbyMode() throws SchedulerException;

    public void shutdown() throws SchedulerException;

    public void shutdown(boolean var1) throws SchedulerException;

    public boolean isShutdown() throws SchedulerException;

    public SchedulerMetaData getMetaData() throws SchedulerException;

    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException;

    public void setJobFactory(JobFactory var1) throws SchedulerException;

    public ListenerManager getListenerManager() throws SchedulerException;

    public Date scheduleJob(JobDetail var1, Trigger var2) throws SchedulerException;

    public Date scheduleJob(Trigger var1) throws SchedulerException;

    public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> var1, boolean var2) throws SchedulerException;

    public void scheduleJob(JobDetail var1, Set<? extends Trigger> var2, boolean var3) throws SchedulerException;

    public boolean unscheduleJob(TriggerKey var1) throws SchedulerException;

    public boolean unscheduleJobs(List<TriggerKey> var1) throws SchedulerException;

    public Date rescheduleJob(TriggerKey var1, Trigger var2) throws SchedulerException;

    public void addJob(JobDetail var1, boolean var2) throws SchedulerException;

    public void addJob(JobDetail var1, boolean var2, boolean var3) throws SchedulerException;

    public boolean deleteJob(JobKey var1) throws SchedulerException;

    public boolean deleteJobs(List<JobKey> var1) throws SchedulerException;

    public void triggerJob(JobKey var1) throws SchedulerException;

    public void triggerJob(JobKey var1, JobDataMap var2) throws SchedulerException;

    public void pauseJob(JobKey var1) throws SchedulerException;

    public void pauseJobs(GroupMatcher<JobKey> var1) throws SchedulerException;

    public void pauseTrigger(TriggerKey var1) throws SchedulerException;

    public void pauseTriggers(GroupMatcher<TriggerKey> var1) throws SchedulerException;

    public void resumeJob(JobKey var1) throws SchedulerException;

    public void resumeJobs(GroupMatcher<JobKey> var1) throws SchedulerException;

    public void resumeTrigger(TriggerKey var1) throws SchedulerException;

    public void resumeTriggers(GroupMatcher<TriggerKey> var1) throws SchedulerException;

    public void pauseAll() throws SchedulerException;

    public void resumeAll() throws SchedulerException;

    public List<String> getJobGroupNames() throws SchedulerException;

    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> var1) throws SchedulerException;

    public List<? extends Trigger> getTriggersOfJob(JobKey var1) throws SchedulerException;

    public List<String> getTriggerGroupNames() throws SchedulerException;

    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> var1) throws SchedulerException;

    public Set<String> getPausedTriggerGroups() throws SchedulerException;

    public JobDetail getJobDetail(JobKey var1) throws SchedulerException;

    public Trigger getTrigger(TriggerKey var1) throws SchedulerException;

    public Trigger.TriggerState getTriggerState(TriggerKey var1) throws SchedulerException;

    public void resetTriggerFromErrorState(TriggerKey var1) throws SchedulerException;

    public void addCalendar(String var1, Calendar var2, boolean var3, boolean var4) throws SchedulerException;

    public boolean deleteCalendar(String var1) throws SchedulerException;

    public Calendar getCalendar(String var1) throws SchedulerException;

    public List<String> getCalendarNames() throws SchedulerException;

    public boolean interrupt(JobKey var1) throws UnableToInterruptJobException;

    public boolean interrupt(String var1) throws UnableToInterruptJobException;

    public boolean checkExists(JobKey var1) throws SchedulerException;

    public boolean checkExists(TriggerKey var1) throws SchedulerException;

    public void clear() throws SchedulerException;
}


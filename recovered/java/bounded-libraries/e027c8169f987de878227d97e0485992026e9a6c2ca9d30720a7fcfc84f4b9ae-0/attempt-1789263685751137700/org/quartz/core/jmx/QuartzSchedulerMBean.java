/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core.jmx;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.TabularData;

public interface QuartzSchedulerMBean {
    public static final String SCHEDULER_STARTED = "schedulerStarted";
    public static final String SCHEDULER_PAUSED = "schedulerPaused";
    public static final String SCHEDULER_SHUTDOWN = "schedulerShutdown";
    public static final String SCHEDULER_ERROR = "schedulerError";
    public static final String JOB_ADDED = "jobAdded";
    public static final String JOB_DELETED = "jobDeleted";
    public static final String JOB_SCHEDULED = "jobScheduled";
    public static final String JOB_UNSCHEDULED = "jobUnscheduled";
    public static final String JOBS_PAUSED = "jobsPaused";
    public static final String JOBS_RESUMED = "jobsResumed";
    public static final String JOB_EXECUTION_VETOED = "jobExecutionVetoed";
    public static final String JOB_TO_BE_EXECUTED = "jobToBeExecuted";
    public static final String JOB_WAS_EXECUTED = "jobWasExecuted";
    public static final String TRIGGER_FINALIZED = "triggerFinalized";
    public static final String TRIGGERS_PAUSED = "triggersPaused";
    public static final String TRIGGERS_RESUMED = "triggersResumed";
    public static final String SCHEDULING_DATA_CLEARED = "schedulingDataCleared";
    public static final String SAMPLED_STATISTICS_ENABLED = "sampledStatisticsEnabled";
    public static final String SAMPLED_STATISTICS_RESET = "sampledStatisticsReset";

    public String getSchedulerName();

    public String getSchedulerInstanceId();

    public boolean isStandbyMode();

    public boolean isShutdown();

    public String getVersion();

    public String getJobStoreClassName();

    public String getThreadPoolClassName();

    public int getThreadPoolSize();

    public long getJobsScheduledMostRecentSample();

    public long getJobsExecutedMostRecentSample();

    public long getJobsCompletedMostRecentSample();

    public Map<String, Long> getPerformanceMetrics();

    public TabularData getCurrentlyExecutingJobs() throws Exception;

    public TabularData getAllJobDetails() throws Exception;

    public List<CompositeData> getAllTriggers() throws Exception;

    public List<String> getJobGroupNames() throws Exception;

    public List<String> getJobNames(String var1) throws Exception;

    public CompositeData getJobDetail(String var1, String var2) throws Exception;

    public boolean isStarted();

    public void start() throws Exception;

    public void shutdown();

    public void standby();

    public void clear() throws Exception;

    public Date scheduleJob(String var1, String var2, String var3, String var4) throws Exception;

    public void scheduleBasicJob(Map<String, Object> var1, Map<String, Object> var2) throws Exception;

    public void scheduleJob(Map<String, Object> var1, Map<String, Object> var2) throws Exception;

    public void scheduleJob(String var1, String var2, Map<String, Object> var3) throws Exception;

    public boolean unscheduleJob(String var1, String var2) throws Exception;

    public boolean interruptJob(String var1, String var2) throws Exception;

    public boolean interruptJob(String var1) throws Exception;

    public void triggerJob(String var1, String var2, Map<String, String> var3) throws Exception;

    public boolean deleteJob(String var1, String var2) throws Exception;

    public void addJob(CompositeData var1, boolean var2) throws Exception;

    public void addJob(Map<String, Object> var1, boolean var2) throws Exception;

    public void pauseJobGroup(String var1) throws Exception;

    public void pauseJobsStartingWith(String var1) throws Exception;

    public void pauseJobsEndingWith(String var1) throws Exception;

    public void pauseJobsContaining(String var1) throws Exception;

    public void pauseJobsAll() throws Exception;

    public void resumeJobGroup(String var1) throws Exception;

    public void resumeJobsStartingWith(String var1) throws Exception;

    public void resumeJobsEndingWith(String var1) throws Exception;

    public void resumeJobsContaining(String var1) throws Exception;

    public void resumeJobsAll() throws Exception;

    public void pauseJob(String var1, String var2) throws Exception;

    public void resumeJob(String var1, String var2) throws Exception;

    public List<String> getTriggerGroupNames() throws Exception;

    public List<String> getTriggerNames(String var1) throws Exception;

    public CompositeData getTrigger(String var1, String var2) throws Exception;

    public String getTriggerState(String var1, String var2) throws Exception;

    public List<CompositeData> getTriggersOfJob(String var1, String var2) throws Exception;

    public Set<String> getPausedTriggerGroups() throws Exception;

    public void pauseAllTriggers() throws Exception;

    public void resumeAllTriggers() throws Exception;

    public void pauseTriggerGroup(String var1) throws Exception;

    public void pauseTriggersStartingWith(String var1) throws Exception;

    public void pauseTriggersEndingWith(String var1) throws Exception;

    public void pauseTriggersContaining(String var1) throws Exception;

    public void pauseTriggersAll() throws Exception;

    public void resumeTriggerGroup(String var1) throws Exception;

    public void resumeTriggersStartingWith(String var1) throws Exception;

    public void resumeTriggersEndingWith(String var1) throws Exception;

    public void resumeTriggersContaining(String var1) throws Exception;

    public void resumeTriggersAll() throws Exception;

    public void pauseTrigger(String var1, String var2) throws Exception;

    public void resumeTrigger(String var1, String var2) throws Exception;

    public List<String> getCalendarNames() throws Exception;

    public void deleteCalendar(String var1) throws Exception;

    public void setSampledStatisticsEnabled(boolean var1);

    public boolean isSampledStatisticsEnabled();
}


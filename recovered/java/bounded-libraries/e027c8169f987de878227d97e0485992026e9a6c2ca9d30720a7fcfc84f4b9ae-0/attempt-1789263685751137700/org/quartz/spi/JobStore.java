/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.spi.TriggerFiredResult;

public interface JobStore {
    public void initialize(ClassLoadHelper var1, SchedulerSignaler var2) throws SchedulerConfigException;

    public void schedulerStarted() throws SchedulerException;

    public void schedulerPaused();

    public void schedulerResumed();

    public void shutdown();

    public boolean supportsPersistence();

    public long getEstimatedTimeToReleaseAndAcquireTrigger();

    public boolean isClustered();

    public void storeJobAndTrigger(JobDetail var1, OperableTrigger var2) throws ObjectAlreadyExistsException, JobPersistenceException;

    public void storeJob(JobDetail var1, boolean var2) throws ObjectAlreadyExistsException, JobPersistenceException;

    public void storeJobsAndTriggers(Map<JobDetail, Set<? extends Trigger>> var1, boolean var2) throws ObjectAlreadyExistsException, JobPersistenceException;

    public boolean removeJob(JobKey var1) throws JobPersistenceException;

    public boolean removeJobs(List<JobKey> var1) throws JobPersistenceException;

    public JobDetail retrieveJob(JobKey var1) throws JobPersistenceException;

    public void storeTrigger(OperableTrigger var1, boolean var2) throws ObjectAlreadyExistsException, JobPersistenceException;

    public boolean removeTrigger(TriggerKey var1) throws JobPersistenceException;

    public boolean removeTriggers(List<TriggerKey> var1) throws JobPersistenceException;

    public boolean replaceTrigger(TriggerKey var1, OperableTrigger var2) throws JobPersistenceException;

    public OperableTrigger retrieveTrigger(TriggerKey var1) throws JobPersistenceException;

    public boolean checkExists(JobKey var1) throws JobPersistenceException;

    public boolean checkExists(TriggerKey var1) throws JobPersistenceException;

    public void clearAllSchedulingData() throws JobPersistenceException;

    public void storeCalendar(String var1, Calendar var2, boolean var3, boolean var4) throws ObjectAlreadyExistsException, JobPersistenceException;

    public boolean removeCalendar(String var1) throws JobPersistenceException;

    public Calendar retrieveCalendar(String var1) throws JobPersistenceException;

    public int getNumberOfJobs() throws JobPersistenceException;

    public int getNumberOfTriggers() throws JobPersistenceException;

    public int getNumberOfCalendars() throws JobPersistenceException;

    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> var1) throws JobPersistenceException;

    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> var1) throws JobPersistenceException;

    public List<String> getJobGroupNames() throws JobPersistenceException;

    public List<String> getTriggerGroupNames() throws JobPersistenceException;

    public List<String> getCalendarNames() throws JobPersistenceException;

    public List<OperableTrigger> getTriggersForJob(JobKey var1) throws JobPersistenceException;

    public Trigger.TriggerState getTriggerState(TriggerKey var1) throws JobPersistenceException;

    public void resetTriggerFromErrorState(TriggerKey var1) throws JobPersistenceException;

    public void pauseTrigger(TriggerKey var1) throws JobPersistenceException;

    public Collection<String> pauseTriggers(GroupMatcher<TriggerKey> var1) throws JobPersistenceException;

    public void pauseJob(JobKey var1) throws JobPersistenceException;

    public Collection<String> pauseJobs(GroupMatcher<JobKey> var1) throws JobPersistenceException;

    public void resumeTrigger(TriggerKey var1) throws JobPersistenceException;

    public Collection<String> resumeTriggers(GroupMatcher<TriggerKey> var1) throws JobPersistenceException;

    public Set<String> getPausedTriggerGroups() throws JobPersistenceException;

    public void resumeJob(JobKey var1) throws JobPersistenceException;

    public Collection<String> resumeJobs(GroupMatcher<JobKey> var1) throws JobPersistenceException;

    public void pauseAll() throws JobPersistenceException;

    public void resumeAll() throws JobPersistenceException;

    public List<OperableTrigger> acquireNextTriggers(long var1, int var3, long var4) throws JobPersistenceException;

    public void releaseAcquiredTrigger(OperableTrigger var1);

    public List<TriggerFiredResult> triggersFired(List<OperableTrigger> var1) throws JobPersistenceException;

    public void triggeredJobComplete(OperableTrigger var1, JobDetail var2, Trigger.CompletedExecutionInstruction var3);

    public void setInstanceId(String var1);

    public void setInstanceName(String var1);

    public void setThreadPoolSize(int var1);

    public long getAcquireRetryDelay(int var1);
}


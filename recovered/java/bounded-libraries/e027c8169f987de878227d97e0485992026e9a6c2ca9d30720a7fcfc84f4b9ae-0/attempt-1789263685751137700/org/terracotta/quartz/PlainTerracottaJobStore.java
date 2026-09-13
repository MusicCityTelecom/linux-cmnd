/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.terracotta.toolkit.Toolkit
 *  org.terracotta.toolkit.internal.ToolkitInternal
 */
package org.terracotta.quartz;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.spi.TriggerFiredResult;
import org.terracotta.quartz.ClusteredJobStore;
import org.terracotta.quartz.DefaultClusteredJobStore;
import org.terracotta.quartz.TerracottaJobStoreExtensions;
import org.terracotta.toolkit.Toolkit;
import org.terracotta.toolkit.internal.ToolkitInternal;

public class PlainTerracottaJobStore<T extends ClusteredJobStore>
implements TerracottaJobStoreExtensions {
    private static final long WEEKLY = 604800000L;
    private Timer updateCheckTimer;
    private volatile T clusteredJobStore = null;
    private Long misfireThreshold = null;
    private String schedName;
    private String synchWrite = "false";
    private Long estimatedTimeToReleaseAndAcquireTrigger = null;
    private String schedInstanceId;
    private long tcRetryInterval;
    private int threadPoolSize;
    protected final ToolkitInternal toolkit;

    public PlainTerracottaJobStore(ToolkitInternal toolkit) {
        this.toolkit = toolkit;
    }

    @Override
    public void setSynchronousWrite(String synchWrite) {
        this.synchWrite = synchWrite;
    }

    @Override
    public void setThreadPoolSize(int size) {
        this.threadPoolSize = size;
    }

    @Override
    public List<OperableTrigger> acquireNextTriggers(long noLaterThan, int maxCount, long timeWindow) throws JobPersistenceException {
        return this.clusteredJobStore.acquireNextTriggers(noLaterThan, maxCount, timeWindow);
    }

    @Override
    public List<String> getCalendarNames() throws JobPersistenceException {
        return this.clusteredJobStore.getCalendarNames();
    }

    @Override
    public List<String> getJobGroupNames() throws JobPersistenceException {
        return this.clusteredJobStore.getJobGroupNames();
    }

    @Override
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        return this.clusteredJobStore.getJobKeys(matcher);
    }

    @Override
    public int getNumberOfCalendars() throws JobPersistenceException {
        return this.clusteredJobStore.getNumberOfCalendars();
    }

    @Override
    public int getNumberOfJobs() throws JobPersistenceException {
        return this.clusteredJobStore.getNumberOfJobs();
    }

    @Override
    public int getNumberOfTriggers() throws JobPersistenceException {
        return this.clusteredJobStore.getNumberOfTriggers();
    }

    @Override
    public Set<String> getPausedTriggerGroups() throws JobPersistenceException {
        return this.clusteredJobStore.getPausedTriggerGroups();
    }

    @Override
    public List<String> getTriggerGroupNames() throws JobPersistenceException {
        return this.clusteredJobStore.getTriggerGroupNames();
    }

    @Override
    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        return this.clusteredJobStore.getTriggerKeys(matcher);
    }

    @Override
    public List<OperableTrigger> getTriggersForJob(JobKey jobKey) throws JobPersistenceException {
        return this.clusteredJobStore.getTriggersForJob(jobKey);
    }

    @Override
    public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) throws JobPersistenceException {
        return this.clusteredJobStore.getTriggerState(triggerKey);
    }

    @Override
    public void resetTriggerFromErrorState(TriggerKey triggerKey) throws JobPersistenceException {
        this.clusteredJobStore.resetTriggerFromErrorState(triggerKey);
    }

    @Override
    public synchronized void initialize(ClassLoadHelper loadHelper, SchedulerSignaler signaler) throws SchedulerConfigException {
        if (this.clusteredJobStore != null) {
            throw new IllegalStateException("already initialized");
        }
        this.clusteredJobStore = this.createNewJobStoreInstance(this.schedName, Boolean.valueOf(this.synchWrite));
        this.clusteredJobStore.setThreadPoolSize(this.threadPoolSize);
        if (this.misfireThreshold != null) {
            this.clusteredJobStore.setMisfireThreshold(this.misfireThreshold);
            this.misfireThreshold = null;
        }
        if (this.estimatedTimeToReleaseAndAcquireTrigger != null) {
            this.clusteredJobStore.setEstimatedTimeToReleaseAndAcquireTrigger(this.estimatedTimeToReleaseAndAcquireTrigger);
            this.estimatedTimeToReleaseAndAcquireTrigger = null;
        }
        this.clusteredJobStore.setInstanceId(this.schedInstanceId);
        this.clusteredJobStore.setTcRetryInterval(this.tcRetryInterval);
        this.clusteredJobStore.initialize(loadHelper, signaler);
    }

    @Override
    public void pauseAll() throws JobPersistenceException {
        this.clusteredJobStore.pauseAll();
    }

    @Override
    public void pauseJob(JobKey jobKey) throws JobPersistenceException {
        this.clusteredJobStore.pauseJob(jobKey);
    }

    @Override
    public Collection<String> pauseJobs(GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        return this.clusteredJobStore.pauseJobs(matcher);
    }

    @Override
    public void pauseTrigger(TriggerKey triggerKey) throws JobPersistenceException {
        this.clusteredJobStore.pauseTrigger(triggerKey);
    }

    @Override
    public Collection<String> pauseTriggers(GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        return this.clusteredJobStore.pauseTriggers(matcher);
    }

    @Override
    public void releaseAcquiredTrigger(OperableTrigger trigger) {
        this.clusteredJobStore.releaseAcquiredTrigger(trigger);
    }

    @Override
    public List<TriggerFiredResult> triggersFired(List<OperableTrigger> triggers) throws JobPersistenceException {
        return this.clusteredJobStore.triggersFired(triggers);
    }

    @Override
    public boolean removeCalendar(String calName) throws JobPersistenceException {
        return this.clusteredJobStore.removeCalendar(calName);
    }

    @Override
    public boolean removeJob(JobKey jobKey) throws JobPersistenceException {
        return this.clusteredJobStore.removeJob(jobKey);
    }

    @Override
    public boolean removeTrigger(TriggerKey triggerKey) throws JobPersistenceException {
        return this.clusteredJobStore.removeTrigger(triggerKey);
    }

    @Override
    public boolean removeJobs(List<JobKey> jobKeys) throws JobPersistenceException {
        return this.clusteredJobStore.removeJobs(jobKeys);
    }

    @Override
    public boolean removeTriggers(List<TriggerKey> triggerKeys) throws JobPersistenceException {
        return this.clusteredJobStore.removeTriggers(triggerKeys);
    }

    @Override
    public void storeJobsAndTriggers(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws JobPersistenceException {
        this.clusteredJobStore.storeJobsAndTriggers(triggersAndJobs, replace);
    }

    @Override
    public boolean replaceTrigger(TriggerKey triggerKey, OperableTrigger newTrigger) throws JobPersistenceException {
        return this.clusteredJobStore.replaceTrigger(triggerKey, newTrigger);
    }

    @Override
    public void resumeAll() throws JobPersistenceException {
        this.clusteredJobStore.resumeAll();
    }

    @Override
    public void resumeJob(JobKey jobKey) throws JobPersistenceException {
        this.clusteredJobStore.resumeJob(jobKey);
    }

    @Override
    public Collection<String> resumeJobs(GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        return this.clusteredJobStore.resumeJobs(matcher);
    }

    @Override
    public void resumeTrigger(TriggerKey triggerKey) throws JobPersistenceException {
        this.clusteredJobStore.resumeTrigger(triggerKey);
    }

    @Override
    public Collection<String> resumeTriggers(GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        return this.clusteredJobStore.resumeTriggers(matcher);
    }

    @Override
    public Calendar retrieveCalendar(String calName) throws JobPersistenceException {
        return this.clusteredJobStore.retrieveCalendar(calName);
    }

    @Override
    public JobDetail retrieveJob(JobKey jobKey) throws JobPersistenceException {
        return this.clusteredJobStore.retrieveJob(jobKey);
    }

    @Override
    public OperableTrigger retrieveTrigger(TriggerKey triggerKey) throws JobPersistenceException {
        return this.clusteredJobStore.retrieveTrigger(triggerKey);
    }

    @Override
    public boolean checkExists(JobKey jobKey) throws JobPersistenceException {
        return this.clusteredJobStore.checkExists(jobKey);
    }

    @Override
    public boolean checkExists(TriggerKey triggerKey) throws JobPersistenceException {
        return this.clusteredJobStore.checkExists(triggerKey);
    }

    @Override
    public void clearAllSchedulingData() throws JobPersistenceException {
        this.clusteredJobStore.clearAllSchedulingData();
    }

    @Override
    public void schedulerStarted() throws SchedulerException {
        this.clusteredJobStore.schedulerStarted();
    }

    @Override
    public void schedulerPaused() {
        if (this.clusteredJobStore != null) {
            this.clusteredJobStore.schedulerPaused();
        }
    }

    @Override
    public void schedulerResumed() {
        this.clusteredJobStore.schedulerResumed();
    }

    @Override
    public void shutdown() {
        if (this.clusteredJobStore != null) {
            this.clusteredJobStore.shutdown();
        }
        if (this.updateCheckTimer != null) {
            this.updateCheckTimer.cancel();
        }
    }

    @Override
    public void storeCalendar(String name, Calendar calendar, boolean replaceExisting, boolean updateTriggers) throws JobPersistenceException {
        this.clusteredJobStore.storeCalendar(name, calendar, replaceExisting, updateTriggers);
    }

    @Override
    public void storeJob(JobDetail newJob, boolean replaceExisting) throws JobPersistenceException {
        this.clusteredJobStore.storeJob(newJob, replaceExisting);
    }

    @Override
    public void storeJobAndTrigger(JobDetail newJob, OperableTrigger newTrigger) throws JobPersistenceException {
        this.clusteredJobStore.storeJobAndTrigger(newJob, newTrigger);
    }

    @Override
    public void storeTrigger(OperableTrigger newTrigger, boolean replaceExisting) throws JobPersistenceException {
        this.clusteredJobStore.storeTrigger(newTrigger, replaceExisting);
    }

    @Override
    public boolean supportsPersistence() {
        return true;
    }

    public String toString() {
        return this.clusteredJobStore.toString();
    }

    @Override
    public void triggeredJobComplete(OperableTrigger trigger, JobDetail jobDetail, Trigger.CompletedExecutionInstruction triggerInstCode) {
        this.clusteredJobStore.triggeredJobComplete(trigger, jobDetail, triggerInstCode);
    }

    @Override
    public synchronized void setMisfireThreshold(long threshold) {
        T cjs = this.clusteredJobStore;
        if (cjs != null) {
            cjs.setMisfireThreshold(threshold);
        } else {
            this.misfireThreshold = threshold;
        }
    }

    @Override
    public synchronized void setEstimatedTimeToReleaseAndAcquireTrigger(long estimate) {
        T cjs = this.clusteredJobStore;
        if (cjs != null) {
            cjs.setEstimatedTimeToReleaseAndAcquireTrigger(estimate);
        } else {
            this.estimatedTimeToReleaseAndAcquireTrigger = estimate;
        }
    }

    @Override
    public void setInstanceId(String schedInstId) {
        this.schedInstanceId = schedInstId;
    }

    @Override
    public void setInstanceName(String schedName) {
        this.schedName = schedName;
    }

    @Override
    public void setTcRetryInterval(long retryInterval) {
        this.tcRetryInterval = retryInterval;
    }

    @Override
    public long getAcquireRetryDelay(int failureCount) {
        return this.tcRetryInterval;
    }

    @Override
    public String getUUID() {
        return this.toolkit.getClientUUID();
    }

    protected T createNewJobStoreInstance(String schedulerName, boolean useSynchWrite) {
        return (T)new DefaultClusteredJobStore(useSynchWrite, (Toolkit)this.toolkit, schedulerName);
    }

    @Override
    public long getEstimatedTimeToReleaseAndAcquireTrigger() {
        return this.clusteredJobStore.getEstimatedTimeToReleaseAndAcquireTrigger();
    }

    @Override
    public boolean isClustered() {
        return true;
    }

    protected T getClusteredJobStore() {
        return this.clusteredJobStore;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
    }
}


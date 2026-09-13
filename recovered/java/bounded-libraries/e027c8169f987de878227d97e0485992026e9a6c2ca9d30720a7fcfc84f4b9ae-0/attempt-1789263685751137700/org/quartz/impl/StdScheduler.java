/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl;

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
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.core.QuartzScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;

public class StdScheduler
implements Scheduler {
    private QuartzScheduler sched;

    public StdScheduler(QuartzScheduler sched) {
        this.sched = sched;
    }

    @Override
    public String getSchedulerName() {
        return this.sched.getSchedulerName();
    }

    @Override
    public String getSchedulerInstanceId() {
        return this.sched.getSchedulerInstanceId();
    }

    @Override
    public SchedulerMetaData getMetaData() {
        return new SchedulerMetaData(this.getSchedulerName(), this.getSchedulerInstanceId(), this.getClass(), false, this.isStarted(), this.isInStandbyMode(), this.isShutdown(), this.sched.runningSince(), this.sched.numJobsExecuted(), this.sched.getJobStoreClass(), this.sched.supportsPersistence(), this.sched.isClustered(), this.sched.getThreadPoolClass(), this.sched.getThreadPoolSize(), this.sched.getVersion());
    }

    @Override
    public SchedulerContext getContext() throws SchedulerException {
        return this.sched.getSchedulerContext();
    }

    @Override
    public void start() throws SchedulerException {
        this.sched.start();
    }

    @Override
    public void startDelayed(int seconds) throws SchedulerException {
        this.sched.startDelayed(seconds);
    }

    @Override
    public void standby() {
        this.sched.standby();
    }

    @Override
    public boolean isStarted() {
        return this.sched.runningSince() != null;
    }

    @Override
    public boolean isInStandbyMode() {
        return this.sched.isInStandbyMode();
    }

    @Override
    public void shutdown() {
        this.sched.shutdown();
    }

    @Override
    public void shutdown(boolean waitForJobsToComplete) {
        this.sched.shutdown(waitForJobsToComplete);
    }

    @Override
    public boolean isShutdown() {
        return this.sched.isShutdown();
    }

    @Override
    public List<JobExecutionContext> getCurrentlyExecutingJobs() {
        return this.sched.getCurrentlyExecutingJobs();
    }

    @Override
    public void clear() throws SchedulerException {
        this.sched.clear();
    }

    @Override
    public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        return this.sched.scheduleJob(jobDetail, trigger);
    }

    @Override
    public Date scheduleJob(Trigger trigger) throws SchedulerException {
        return this.sched.scheduleJob(trigger);
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace) throws SchedulerException {
        this.sched.addJob(jobDetail, replace);
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace, boolean storeNonDurableWhileAwaitingScheduling) throws SchedulerException {
        this.sched.addJob(jobDetail, replace, storeNonDurableWhileAwaitingScheduling);
    }

    @Override
    public boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException {
        return this.sched.deleteJobs(jobKeys);
    }

    @Override
    public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException {
        this.sched.scheduleJobs(triggersAndJobs, replace);
    }

    @Override
    public void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggersForJob, boolean replace) throws SchedulerException {
        this.sched.scheduleJob(jobDetail, triggersForJob, replace);
    }

    @Override
    public boolean unscheduleJobs(List<TriggerKey> triggerKeys) throws SchedulerException {
        return this.sched.unscheduleJobs(triggerKeys);
    }

    @Override
    public boolean deleteJob(JobKey jobKey) throws SchedulerException {
        return this.sched.deleteJob(jobKey);
    }

    @Override
    public boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException {
        return this.sched.unscheduleJob(triggerKey);
    }

    @Override
    public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger) throws SchedulerException {
        return this.sched.rescheduleJob(triggerKey, newTrigger);
    }

    @Override
    public void triggerJob(JobKey jobKey) throws SchedulerException {
        this.triggerJob(jobKey, null);
    }

    @Override
    public void triggerJob(JobKey jobKey, JobDataMap data) throws SchedulerException {
        this.sched.triggerJob(jobKey, data);
    }

    @Override
    public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
        this.sched.pauseTrigger(triggerKey);
    }

    @Override
    public void pauseTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        this.sched.pauseTriggers(matcher);
    }

    @Override
    public void pauseJob(JobKey jobKey) throws SchedulerException {
        this.sched.pauseJob(jobKey);
    }

    @Override
    public Set<String> getPausedTriggerGroups() throws SchedulerException {
        return this.sched.getPausedTriggerGroups();
    }

    @Override
    public void pauseJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
        this.sched.pauseJobs(matcher);
    }

    @Override
    public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
        this.sched.resumeTrigger(triggerKey);
    }

    @Override
    public void resumeTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        this.sched.resumeTriggers(matcher);
    }

    @Override
    public void resumeJob(JobKey jobKey) throws SchedulerException {
        this.sched.resumeJob(jobKey);
    }

    @Override
    public void resumeJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
        this.sched.resumeJobs(matcher);
    }

    @Override
    public void pauseAll() throws SchedulerException {
        this.sched.pauseAll();
    }

    @Override
    public void resumeAll() throws SchedulerException {
        this.sched.resumeAll();
    }

    @Override
    public List<String> getJobGroupNames() throws SchedulerException {
        return this.sched.getJobGroupNames();
    }

    @Override
    public List<? extends Trigger> getTriggersOfJob(JobKey jobKey) throws SchedulerException {
        return this.sched.getTriggersOfJob(jobKey);
    }

    @Override
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws SchedulerException {
        return this.sched.getJobKeys(matcher);
    }

    @Override
    public List<String> getTriggerGroupNames() throws SchedulerException {
        return this.sched.getTriggerGroupNames();
    }

    @Override
    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        return this.sched.getTriggerKeys(matcher);
    }

    @Override
    public JobDetail getJobDetail(JobKey jobKey) throws SchedulerException {
        return this.sched.getJobDetail(jobKey);
    }

    @Override
    public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
        return this.sched.getTrigger(triggerKey);
    }

    @Override
    public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) throws SchedulerException {
        return this.sched.getTriggerState(triggerKey);
    }

    @Override
    public void resetTriggerFromErrorState(TriggerKey triggerKey) throws SchedulerException {
        this.sched.resetTriggerFromErrorState(triggerKey);
    }

    @Override
    public void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers) throws SchedulerException {
        this.sched.addCalendar(calName, calendar, replace, updateTriggers);
    }

    @Override
    public boolean deleteCalendar(String calName) throws SchedulerException {
        return this.sched.deleteCalendar(calName);
    }

    @Override
    public Calendar getCalendar(String calName) throws SchedulerException {
        return this.sched.getCalendar(calName);
    }

    @Override
    public List<String> getCalendarNames() throws SchedulerException {
        return this.sched.getCalendarNames();
    }

    @Override
    public boolean checkExists(JobKey jobKey) throws SchedulerException {
        return this.sched.checkExists(jobKey);
    }

    @Override
    public boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
        return this.sched.checkExists(triggerKey);
    }

    @Override
    public void setJobFactory(JobFactory factory) throws SchedulerException {
        this.sched.setJobFactory(factory);
    }

    @Override
    public ListenerManager getListenerManager() throws SchedulerException {
        return this.sched.getListenerManager();
    }

    @Override
    public boolean interrupt(JobKey jobKey) throws UnableToInterruptJobException {
        return this.sched.interrupt(jobKey);
    }

    @Override
    public boolean interrupt(String fireInstanceId) throws UnableToInterruptJobException {
        return this.sched.interrupt(fireInstanceId);
    }
}


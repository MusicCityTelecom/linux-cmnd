/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
import org.quartz.core.RemotableQuartzScheduler;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;

public class RemoteScheduler
implements Scheduler {
    private RemotableQuartzScheduler rsched;
    private String schedId;
    private String rmiHost;
    private int rmiPort;

    public RemoteScheduler(String schedId, String host, int port) {
        this.schedId = schedId;
        this.rmiHost = host;
        this.rmiPort = port;
    }

    protected RemotableQuartzScheduler getRemoteScheduler() throws SchedulerException {
        if (this.rsched != null) {
            return this.rsched;
        }
        try {
            Registry registry = LocateRegistry.getRegistry(this.rmiHost, this.rmiPort);
            this.rsched = (RemotableQuartzScheduler)registry.lookup(this.schedId);
        }
        catch (Exception e) {
            SchedulerException initException = new SchedulerException("Could not get handle to remote scheduler: " + e.getMessage(), e);
            throw initException;
        }
        return this.rsched;
    }

    protected SchedulerException invalidateHandleCreateException(String msg, Exception cause) {
        this.rsched = null;
        SchedulerException ex = new SchedulerException(msg, cause);
        return ex;
    }

    @Override
    public String getSchedulerName() throws SchedulerException {
        try {
            return this.getRemoteScheduler().getSchedulerName();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public String getSchedulerInstanceId() throws SchedulerException {
        try {
            return this.getRemoteScheduler().getSchedulerInstanceId();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public SchedulerMetaData getMetaData() throws SchedulerException {
        try {
            RemotableQuartzScheduler sched = this.getRemoteScheduler();
            return new SchedulerMetaData(this.getSchedulerName(), this.getSchedulerInstanceId(), this.getClass(), true, this.isStarted(), this.isInStandbyMode(), this.isShutdown(), sched.runningSince(), sched.numJobsExecuted(), sched.getJobStoreClass(), sched.supportsPersistence(), sched.isClustered(), sched.getThreadPoolClass(), sched.getThreadPoolSize(), sched.getVersion());
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public SchedulerContext getContext() throws SchedulerException {
        try {
            return this.getRemoteScheduler().getSchedulerContext();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void start() throws SchedulerException {
        try {
            this.getRemoteScheduler().start();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void startDelayed(int seconds) throws SchedulerException {
        try {
            this.getRemoteScheduler().startDelayed(seconds);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void standby() throws SchedulerException {
        try {
            this.getRemoteScheduler().standby();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean isStarted() throws SchedulerException {
        try {
            return this.getRemoteScheduler().runningSince() != null;
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean isInStandbyMode() throws SchedulerException {
        try {
            return this.getRemoteScheduler().isInStandbyMode();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void shutdown() throws SchedulerException {
        try {
            String schedulerName = this.getSchedulerName();
            this.getRemoteScheduler().shutdown();
            SchedulerRepository.getInstance().remove(schedulerName);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
        try {
            String schedulerName = this.getSchedulerName();
            this.getRemoteScheduler().shutdown(waitForJobsToComplete);
            SchedulerRepository.getInstance().remove(schedulerName);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean isShutdown() throws SchedulerException {
        try {
            return this.getRemoteScheduler().isShutdown();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException {
        try {
            return this.getRemoteScheduler().getCurrentlyExecutingJobs();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        try {
            return this.getRemoteScheduler().scheduleJob(jobDetail, trigger);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Date scheduleJob(Trigger trigger) throws SchedulerException {
        try {
            return this.getRemoteScheduler().scheduleJob(trigger);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace) throws SchedulerException {
        try {
            this.getRemoteScheduler().addJob(jobDetail, replace);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace, boolean storeNonDurableWhileAwaitingScheduling) throws SchedulerException {
        try {
            this.getRemoteScheduler().addJob(jobDetail, replace, storeNonDurableWhileAwaitingScheduling);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException {
        try {
            return this.getRemoteScheduler().deleteJobs(jobKeys);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException {
        try {
            this.getRemoteScheduler().scheduleJobs(triggersAndJobs, replace);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggersForJob, boolean replace) throws SchedulerException {
        try {
            this.getRemoteScheduler().scheduleJob(jobDetail, triggersForJob, replace);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean unscheduleJobs(List<TriggerKey> triggerKeys) throws SchedulerException {
        try {
            return this.getRemoteScheduler().unscheduleJobs(triggerKeys);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean deleteJob(JobKey jobKey) throws SchedulerException {
        try {
            return this.getRemoteScheduler().deleteJob(jobKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException {
        try {
            return this.getRemoteScheduler().unscheduleJob(triggerKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger) throws SchedulerException {
        try {
            return this.getRemoteScheduler().rescheduleJob(triggerKey, newTrigger);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void triggerJob(JobKey jobKey) throws SchedulerException {
        this.triggerJob(jobKey, null);
    }

    @Override
    public void triggerJob(JobKey jobKey, JobDataMap data) throws SchedulerException {
        try {
            this.getRemoteScheduler().triggerJob(jobKey, data);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
        try {
            this.getRemoteScheduler().pauseTrigger(triggerKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void pauseTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        try {
            this.getRemoteScheduler().pauseTriggers(matcher);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void pauseJob(JobKey jobKey) throws SchedulerException {
        try {
            this.getRemoteScheduler().pauseJob(jobKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void pauseJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
        try {
            this.getRemoteScheduler().pauseJobs(matcher);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
        try {
            this.getRemoteScheduler().resumeTrigger(triggerKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void resumeTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        try {
            this.getRemoteScheduler().resumeTriggers(matcher);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void resumeJob(JobKey jobKey) throws SchedulerException {
        try {
            this.getRemoteScheduler().resumeJob(jobKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void resumeJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
        try {
            this.getRemoteScheduler().resumeJobs(matcher);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void pauseAll() throws SchedulerException {
        try {
            this.getRemoteScheduler().pauseAll();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void resumeAll() throws SchedulerException {
        try {
            this.getRemoteScheduler().resumeAll();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public List<String> getJobGroupNames() throws SchedulerException {
        try {
            return this.getRemoteScheduler().getJobGroupNames();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws SchedulerException {
        try {
            return this.getRemoteScheduler().getJobKeys(matcher);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public List<? extends Trigger> getTriggersOfJob(JobKey jobKey) throws SchedulerException {
        try {
            return this.getRemoteScheduler().getTriggersOfJob(jobKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public List<String> getTriggerGroupNames() throws SchedulerException {
        try {
            return this.getRemoteScheduler().getTriggerGroupNames();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        try {
            return this.getRemoteScheduler().getTriggerKeys(matcher);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public JobDetail getJobDetail(JobKey jobKey) throws SchedulerException {
        try {
            return this.getRemoteScheduler().getJobDetail(jobKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean checkExists(JobKey jobKey) throws SchedulerException {
        try {
            return this.getRemoteScheduler().checkExists(jobKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
        try {
            return this.getRemoteScheduler().checkExists(triggerKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void clear() throws SchedulerException {
        try {
            this.getRemoteScheduler().clear();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
        try {
            return this.getRemoteScheduler().getTrigger(triggerKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) throws SchedulerException {
        try {
            return this.getRemoteScheduler().getTriggerState(triggerKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void resetTriggerFromErrorState(TriggerKey triggerKey) throws SchedulerException {
        try {
            this.getRemoteScheduler().resetTriggerFromErrorState(triggerKey);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers) throws SchedulerException {
        try {
            this.getRemoteScheduler().addCalendar(calName, calendar, replace, updateTriggers);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public boolean deleteCalendar(String calName) throws SchedulerException {
        try {
            return this.getRemoteScheduler().deleteCalendar(calName);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Calendar getCalendar(String calName) throws SchedulerException {
        try {
            return this.getRemoteScheduler().getCalendar(calName);
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public List<String> getCalendarNames() throws SchedulerException {
        try {
            return this.getRemoteScheduler().getCalendarNames();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public Set<String> getPausedTriggerGroups() throws SchedulerException {
        try {
            return this.getRemoteScheduler().getPausedTriggerGroups();
        }
        catch (RemoteException re) {
            throw this.invalidateHandleCreateException("Error communicating with remote scheduler.", re);
        }
    }

    @Override
    public ListenerManager getListenerManager() throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }

    @Override
    public boolean interrupt(JobKey jobKey) throws UnableToInterruptJobException {
        try {
            return this.getRemoteScheduler().interrupt(jobKey);
        }
        catch (RemoteException re) {
            throw new UnableToInterruptJobException(this.invalidateHandleCreateException("Error communicating with remote scheduler.", re));
        }
        catch (SchedulerException se) {
            throw new UnableToInterruptJobException(se);
        }
    }

    @Override
    public boolean interrupt(String fireInstanceId) throws UnableToInterruptJobException {
        try {
            return this.getRemoteScheduler().interrupt(fireInstanceId);
        }
        catch (RemoteException re) {
            throw new UnableToInterruptJobException(this.invalidateHandleCreateException("Error communicating with remote scheduler.", re));
        }
        catch (SchedulerException se) {
            throw new UnableToInterruptJobException(se);
        }
    }

    @Override
    public void setJobFactory(JobFactory factory) throws SchedulerException {
        throw new SchedulerException("Operation not supported for remote schedulers.");
    }
}


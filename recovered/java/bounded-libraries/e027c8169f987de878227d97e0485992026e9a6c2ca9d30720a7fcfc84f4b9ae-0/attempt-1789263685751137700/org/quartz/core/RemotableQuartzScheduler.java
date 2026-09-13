/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.core;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.UnableToInterruptJobException;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.OperableTrigger;

public interface RemotableQuartzScheduler
extends Remote {
    public String getSchedulerName() throws RemoteException;

    public String getSchedulerInstanceId() throws RemoteException;

    public SchedulerContext getSchedulerContext() throws SchedulerException, RemoteException;

    public void start() throws SchedulerException, RemoteException;

    public void startDelayed(int var1) throws SchedulerException, RemoteException;

    public void standby() throws RemoteException;

    public boolean isInStandbyMode() throws RemoteException;

    public void shutdown() throws RemoteException;

    public void shutdown(boolean var1) throws RemoteException;

    public boolean isShutdown() throws RemoteException;

    public Date runningSince() throws RemoteException;

    public String getVersion() throws RemoteException;

    public int numJobsExecuted() throws RemoteException;

    public Class<?> getJobStoreClass() throws RemoteException;

    public boolean supportsPersistence() throws RemoteException;

    public boolean isClustered() throws RemoteException;

    public Class<?> getThreadPoolClass() throws RemoteException;

    public int getThreadPoolSize() throws RemoteException;

    public void clear() throws SchedulerException, RemoteException;

    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException, RemoteException;

    public Date scheduleJob(JobDetail var1, Trigger var2) throws SchedulerException, RemoteException;

    public Date scheduleJob(Trigger var1) throws SchedulerException, RemoteException;

    public void addJob(JobDetail var1, boolean var2) throws SchedulerException, RemoteException;

    public void addJob(JobDetail var1, boolean var2, boolean var3) throws SchedulerException, RemoteException;

    public boolean deleteJob(JobKey var1) throws SchedulerException, RemoteException;

    public boolean unscheduleJob(TriggerKey var1) throws SchedulerException, RemoteException;

    public Date rescheduleJob(TriggerKey var1, Trigger var2) throws SchedulerException, RemoteException;

    public void triggerJob(JobKey var1, JobDataMap var2) throws SchedulerException, RemoteException;

    public void triggerJob(OperableTrigger var1) throws SchedulerException, RemoteException;

    public void pauseTrigger(TriggerKey var1) throws SchedulerException, RemoteException;

    public void pauseTriggers(GroupMatcher<TriggerKey> var1) throws SchedulerException, RemoteException;

    public void pauseJob(JobKey var1) throws SchedulerException, RemoteException;

    public void pauseJobs(GroupMatcher<JobKey> var1) throws SchedulerException, RemoteException;

    public void resumeTrigger(TriggerKey var1) throws SchedulerException, RemoteException;

    public void resumeTriggers(GroupMatcher<TriggerKey> var1) throws SchedulerException, RemoteException;

    public Set<String> getPausedTriggerGroups() throws SchedulerException, RemoteException;

    public void resumeJob(JobKey var1) throws SchedulerException, RemoteException;

    public void resumeJobs(GroupMatcher<JobKey> var1) throws SchedulerException, RemoteException;

    public void pauseAll() throws SchedulerException, RemoteException;

    public void resumeAll() throws SchedulerException, RemoteException;

    public List<String> getJobGroupNames() throws SchedulerException, RemoteException;

    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> var1) throws SchedulerException, RemoteException;

    public List<? extends Trigger> getTriggersOfJob(JobKey var1) throws SchedulerException, RemoteException;

    public List<String> getTriggerGroupNames() throws SchedulerException, RemoteException;

    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> var1) throws SchedulerException, RemoteException;

    public JobDetail getJobDetail(JobKey var1) throws SchedulerException, RemoteException;

    public Trigger getTrigger(TriggerKey var1) throws SchedulerException, RemoteException;

    public Trigger.TriggerState getTriggerState(TriggerKey var1) throws SchedulerException, RemoteException;

    public void resetTriggerFromErrorState(TriggerKey var1) throws SchedulerException, RemoteException;

    public void addCalendar(String var1, Calendar var2, boolean var3, boolean var4) throws SchedulerException, RemoteException;

    public boolean deleteCalendar(String var1) throws SchedulerException, RemoteException;

    public Calendar getCalendar(String var1) throws SchedulerException, RemoteException;

    public List<String> getCalendarNames() throws SchedulerException, RemoteException;

    public boolean interrupt(JobKey var1) throws UnableToInterruptJobException, RemoteException;

    public boolean interrupt(String var1) throws UnableToInterruptJobException, RemoteException;

    public boolean checkExists(JobKey var1) throws SchedulerException, RemoteException;

    public boolean checkExists(TriggerKey var1) throws SchedulerException, RemoteException;

    public boolean deleteJobs(List<JobKey> var1) throws SchedulerException, RemoteException;

    public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> var1, boolean var2) throws SchedulerException, RemoteException;

    public void scheduleJob(JobDetail var1, Set<? extends Trigger> var2, boolean var3) throws SchedulerException, RemoteException;

    public boolean unscheduleJobs(List<TriggerKey> var1) throws SchedulerException, RemoteException;
}


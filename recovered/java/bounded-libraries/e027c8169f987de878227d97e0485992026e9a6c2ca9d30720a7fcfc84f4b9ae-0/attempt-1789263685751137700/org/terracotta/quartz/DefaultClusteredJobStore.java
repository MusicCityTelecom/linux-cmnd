/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.terracotta.toolkit.Toolkit
 *  org.terracotta.toolkit.atomic.ToolkitTransactionType
 *  org.terracotta.toolkit.cluster.ClusterEvent
 *  org.terracotta.toolkit.cluster.ClusterInfo
 *  org.terracotta.toolkit.cluster.ClusterListener
 *  org.terracotta.toolkit.cluster.ClusterNode
 *  org.terracotta.toolkit.concurrent.locks.ToolkitLock
 *  org.terracotta.toolkit.internal.ToolkitInternal
 *  org.terracotta.toolkit.internal.concurrent.locks.ToolkitLockTypeInternal
 *  org.terracotta.toolkit.rejoin.RejoinException
 *  org.terracotta.toolkit.store.ToolkitStore
 */
package org.terracotta.quartz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.StringMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.spi.TriggerFiredBundle;
import org.quartz.spi.TriggerFiredResult;
import org.quartz.utils.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.quartz.ClusteredJobStore;
import org.terracotta.quartz.TransactionControllingLock;
import org.terracotta.quartz.collections.TimeTriggerSet;
import org.terracotta.quartz.collections.ToolkitDSHolder;
import org.terracotta.quartz.wrappers.DefaultWrapperFactory;
import org.terracotta.quartz.wrappers.FiredTrigger;
import org.terracotta.quartz.wrappers.JobFacade;
import org.terracotta.quartz.wrappers.JobWrapper;
import org.terracotta.quartz.wrappers.TriggerFacade;
import org.terracotta.quartz.wrappers.TriggerWrapper;
import org.terracotta.quartz.wrappers.WrapperFactory;
import org.terracotta.toolkit.Toolkit;
import org.terracotta.toolkit.atomic.ToolkitTransactionType;
import org.terracotta.toolkit.cluster.ClusterEvent;
import org.terracotta.toolkit.cluster.ClusterInfo;
import org.terracotta.toolkit.cluster.ClusterListener;
import org.terracotta.toolkit.cluster.ClusterNode;
import org.terracotta.toolkit.concurrent.locks.ToolkitLock;
import org.terracotta.toolkit.internal.ToolkitInternal;
import org.terracotta.toolkit.internal.concurrent.locks.ToolkitLockTypeInternal;
import org.terracotta.toolkit.rejoin.RejoinException;
import org.terracotta.toolkit.store.ToolkitStore;

class DefaultClusteredJobStore
implements ClusteredJobStore {
    private final ToolkitDSHolder toolkitDSHolder;
    private final Toolkit toolkit;
    private final JobFacade jobFacade;
    private final TriggerFacade triggerFacade;
    private final TimeTriggerSet timeTriggers;
    private final ToolkitStore<String, Calendar> calendarsByName;
    private long misfireThreshold = 60000L;
    private final ToolkitLockTypeInternal lockType;
    private final transient ToolkitLock lock;
    private final ClusterInfo clusterInfo;
    private final WrapperFactory wrapperFactory;
    private long ftrCtr;
    private volatile SchedulerSignaler signaler;
    private final Logger logger;
    private volatile String terracottaClientId;
    private long estimatedTimeToReleaseAndAcquireTrigger = 15L;
    private volatile LocalLockState localStateLock;
    private volatile TriggerRemovedFromCandidateFiringListHandler triggerRemovedFromCandidateFiringListHandler;
    private volatile boolean toolkitShutdown;
    private long retryInterval;

    public DefaultClusteredJobStore(boolean synchWrite, Toolkit toolkit, String jobStoreName) {
        this(synchWrite, toolkit, jobStoreName, new ToolkitDSHolder(jobStoreName, toolkit), new DefaultWrapperFactory());
    }

    public DefaultClusteredJobStore(boolean synchWrite, Toolkit toolkit, String jobStoreName, ToolkitDSHolder toolkitDSHolder, WrapperFactory wrapperFactory) {
        this.toolkit = toolkit;
        this.wrapperFactory = wrapperFactory;
        this.clusterInfo = toolkit.getClusterInfo();
        this.toolkitDSHolder = toolkitDSHolder;
        this.jobFacade = new JobFacade(toolkitDSHolder);
        this.triggerFacade = new TriggerFacade(toolkitDSHolder);
        this.timeTriggers = toolkitDSHolder.getOrCreateTimeTriggerSet();
        this.calendarsByName = toolkitDSHolder.getOrCreateCalendarWrapperMap();
        this.lockType = synchWrite ? ToolkitLockTypeInternal.SYNCHRONOUS_WRITE : ToolkitLockTypeInternal.WRITE;
        ToolkitTransactionType txnType = synchWrite ? ToolkitTransactionType.SYNC : ToolkitTransactionType.NORMAL;
        this.lock = new TransactionControllingLock((ToolkitInternal)toolkit, toolkitDSHolder.getLock(this.lockType), txnType);
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.getLog().info("Synchronous write locking is [" + synchWrite + "]");
    }

    private Logger getLog() {
        return this.logger;
    }

    private void disable() {
        this.toolkitShutdown = true;
        try {
            this.getLocalLockState().disableLocking();
        }
        catch (InterruptedException e) {
            this.getLog().error("failed to disable the job store", (Throwable)e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private LocalLockState getLocalLockState() {
        LocalLockState rv = this.localStateLock;
        if (rv != null) {
            return rv;
        }
        Class<DefaultClusteredJobStore> clazz = DefaultClusteredJobStore.class;
        synchronized (DefaultClusteredJobStore.class) {
            if (this.localStateLock == null) {
                this.localStateLock = new LocalLockState();
            }
            // ** MonitorExit[var2_2] (shouldn't be in output)
            return this.localStateLock;
        }
    }

    void lock() throws JobPersistenceException {
        this.getLocalLockState().attemptAcquireBegin();
        try {
            this.lock.lock();
        }
        catch (RejoinException e) {
            this.getLocalLockState().release();
            throw e;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void unlock() {
        try {
            this.lock.unlock();
        }
        finally {
            this.getLocalLockState().release();
        }
    }

    @Override
    public void initialize(ClassLoadHelper loadHelper, SchedulerSignaler schedulerSignaler) {
        this.terracottaClientId = this.clusterInfo.getCurrentNode().getId();
        this.ftrCtr = System.currentTimeMillis();
        this.signaler = schedulerSignaler;
        this.getLog().info(this.getClass().getSimpleName() + " initialized.");
        ((ToolkitInternal)this.toolkit).registerBeforeShutdownHook((Runnable)new ShutdownHook(this));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void schedulerStarted() throws SchedulerException {
        this.clusterInfo.addClusterListener((ClusterListener)this);
        Set nodes = this.clusterInfo.getNodes();
        HashSet<String> activeClientIDs = new HashSet<String>();
        for (ClusterNode node : nodes) {
            boolean added = activeClientIDs.add(node.getId());
            if (added) continue;
            this.getLog().error("DUPLICATE node ID detected: " + node);
        }
        this.lock();
        try {
            TriggerWrapper tw;
            ArrayList<TriggerWrapper> toEval = new ArrayList<TriggerWrapper>();
            for (TriggerKey triggerKey : this.triggerFacade.allTriggerKeys()) {
                tw = this.triggerFacade.get(triggerKey);
                String lastTerracotaClientId = tw.getLastTerracotaClientId();
                if (lastTerracotaClientId == null || activeClientIDs.contains(lastTerracotaClientId) && tw.getState() != TriggerWrapper.TriggerState.ERROR) continue;
                toEval.add(tw);
            }
            for (TriggerWrapper tw2 : toEval) {
                this.evalOrphanedTrigger(tw2, true);
            }
            Iterator<FiredTrigger> iter = this.triggerFacade.allFiredTriggers().iterator();
            while (iter.hasNext()) {
                FiredTrigger ft = iter.next();
                if (activeClientIDs.contains(ft.getClientId())) continue;
                this.getLog().info("Found non-complete fired trigger: " + ft);
                iter.remove();
                tw = this.triggerFacade.get(ft.getTriggerKey());
                if (tw == null) {
                    this.getLog().error("no trigger found for executing trigger: " + ft.getTriggerKey());
                    continue;
                }
                this.scheduleRecoveryIfNeeded(tw, ft);
            }
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void schedulerPaused() {
    }

    @Override
    public void schedulerResumed() {
    }

    private void evalOrphanedTrigger(TriggerWrapper tw, boolean newNode) {
        this.getLog().info("Evaluating orphaned trigger " + tw);
        JobWrapper jobWrapper = this.jobFacade.get(tw.getJobKey());
        if (jobWrapper == null) {
            this.getLog().error("No job found for orphaned trigger: " + tw);
            this.jobFacade.removeBlockedJob(tw.getJobKey());
            return;
        }
        if (newNode && tw.getState() == TriggerWrapper.TriggerState.ERROR) {
            tw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
            this.timeTriggers.add(tw);
        }
        if (tw.getState() == TriggerWrapper.TriggerState.BLOCKED) {
            tw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
            this.timeTriggers.add(tw);
        } else if (tw.getState() == TriggerWrapper.TriggerState.PAUSED_BLOCKED) {
            tw.setState(TriggerWrapper.TriggerState.PAUSED, this.terracottaClientId, this.triggerFacade);
        }
        if (tw.getState() == TriggerWrapper.TriggerState.ACQUIRED) {
            tw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
            this.timeTriggers.add(tw);
        }
        if (!tw.mayFireAgain() && !jobWrapper.requestsRecovery()) {
            try {
                this.removeTrigger(tw.getKey());
            }
            catch (JobPersistenceException e) {
                this.getLog().error("Can't remove completed trigger (and related job) " + tw, (Throwable)e);
            }
        }
        if (jobWrapper.isConcurrentExectionDisallowed()) {
            this.jobFacade.removeBlockedJob(jobWrapper.getKey());
            List<TriggerWrapper> triggersForJob = this.triggerFacade.getTriggerWrappersForJob(jobWrapper.getKey());
            for (TriggerWrapper trigger : triggersForJob) {
                if (trigger.getState() == TriggerWrapper.TriggerState.BLOCKED) {
                    trigger.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
                    this.timeTriggers.add(trigger);
                    continue;
                }
                if (trigger.getState() != TriggerWrapper.TriggerState.PAUSED_BLOCKED) continue;
                trigger.setState(TriggerWrapper.TriggerState.PAUSED, this.terracottaClientId, this.triggerFacade);
            }
        }
    }

    private void scheduleRecoveryIfNeeded(TriggerWrapper tw, FiredTrigger recovering) {
        JobWrapper jobWrapper = this.jobFacade.get(tw.getJobKey());
        if (jobWrapper == null) {
            this.getLog().error("No job found for orphaned trigger: " + tw);
            return;
        }
        if (jobWrapper.requestsRecovery()) {
            OperableTrigger recoveryTrigger = this.createRecoveryTrigger(tw, jobWrapper, "recover_" + this.terracottaClientId + "_" + this.ftrCtr++, recovering);
            JobDataMap jd = tw.getTriggerClone().getJobDataMap();
            jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_NAME", tw.getKey().getName());
            jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_GROUP", tw.getKey().getGroup());
            jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_FIRETIME_IN_MILLISECONDS_AS_STRING", String.valueOf(recovering.getFireTime()));
            jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS_AS_STRING", String.valueOf(recovering.getScheduledFireTime()));
            recoveryTrigger.setJobDataMap(jd);
            recoveryTrigger.computeFirstFireTime(null);
            try {
                this.storeTrigger(recoveryTrigger, false);
                if (!tw.mayFireAgain()) {
                    this.removeTrigger(tw.getKey());
                }
                this.getLog().info("Recovered job " + jobWrapper + " for trigger " + tw);
            }
            catch (JobPersistenceException e) {
                this.getLog().error("Can't recover job " + jobWrapper + " for trigger " + tw, (Throwable)e);
            }
        }
    }

    protected OperableTrigger createRecoveryTrigger(TriggerWrapper tw, JobWrapper jw, String name, FiredTrigger recovering) {
        SimpleTriggerImpl recoveryTrigger = new SimpleTriggerImpl(name, "RECOVERING_JOBS", new Date(recovering.getScheduledFireTime()));
        recoveryTrigger.setJobName(jw.getKey().getName());
        recoveryTrigger.setJobGroup(jw.getKey().getGroup());
        recoveryTrigger.setMisfireInstruction(-1);
        recoveryTrigger.setPriority(tw.getPriority());
        return recoveryTrigger;
    }

    private long getMisfireThreshold() {
        return this.misfireThreshold;
    }

    @Override
    public void setMisfireThreshold(long misfireThreshold) {
        if (misfireThreshold < 1L) {
            throw new IllegalArgumentException("Misfirethreashold must be larger than 0");
        }
        this.misfireThreshold = misfireThreshold;
    }

    @Override
    public void shutdown() {
    }

    @Override
    public boolean supportsPersistence() {
        throw new AssertionError();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeJobAndTrigger(JobDetail newJob, OperableTrigger newTrigger) throws JobPersistenceException {
        this.lock();
        try {
            this.storeJob(newJob, false);
            this.storeTrigger(newTrigger, false);
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeJob(JobDetail newJob, boolean replaceExisting) throws ObjectAlreadyExistsException, JobPersistenceException {
        JobDetail clone = (JobDetail)newJob.clone();
        this.lock();
        try {
            JobWrapper jw = this.wrapperFactory.createJobWrapper(clone);
            if (this.jobFacade.containsKey(jw.getKey())) {
                if (!replaceExisting) {
                    throw new ObjectAlreadyExistsException(newJob);
                }
            } else {
                Set<String> grpSet = this.toolkitDSHolder.getOrCreateJobsGroupMap(newJob.getKey().getGroup());
                grpSet.add(jw.getKey().getName());
                if (!this.jobFacade.hasGroup(jw.getKey().getGroup())) {
                    this.jobFacade.addGroup(jw.getKey().getGroup());
                }
            }
            this.jobFacade.put(jw.getKey(), jw);
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeJob(JobKey jobKey) throws JobPersistenceException {
        boolean found = false;
        this.lock();
        try {
            List<OperableTrigger> trigger = this.getTriggersForJob(jobKey);
            for (OperableTrigger trig : trigger) {
                this.removeTrigger(trig.getKey());
                found = true;
            }
            found = this.jobFacade.remove(jobKey) != null | found;
            if (found) {
                Set<String> grpSet = this.toolkitDSHolder.getOrCreateJobsGroupMap(jobKey.getGroup());
                grpSet.remove(jobKey.getName());
                if (grpSet.isEmpty()) {
                    this.toolkitDSHolder.removeJobsGroupMap(jobKey.getGroup());
                    this.jobFacade.removeGroup(jobKey.getGroup());
                }
            }
        }
        finally {
            this.unlock();
        }
        return found;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeJobs(List<JobKey> jobKeys) throws JobPersistenceException {
        boolean allFound = true;
        this.lock();
        try {
            for (JobKey key : jobKeys) {
                allFound = this.removeJob(key) && allFound;
            }
        }
        finally {
            this.unlock();
        }
        return allFound;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeTriggers(List<TriggerKey> triggerKeys) throws JobPersistenceException {
        boolean allFound = true;
        this.lock();
        try {
            for (TriggerKey key : triggerKeys) {
                allFound = this.removeTrigger(key) && allFound;
            }
        }
        finally {
            this.unlock();
        }
        return allFound;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeJobsAndTriggers(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws ObjectAlreadyExistsException, JobPersistenceException {
        this.lock();
        try {
            if (!replace) {
                for (JobDetail job : triggersAndJobs.keySet()) {
                    if (this.checkExists(job.getKey())) {
                        throw new ObjectAlreadyExistsException(job);
                    }
                    for (Trigger trigger : triggersAndJobs.get(job)) {
                        if (!this.checkExists(trigger.getKey())) continue;
                        throw new ObjectAlreadyExistsException(trigger);
                    }
                }
            }
            for (JobDetail job : triggersAndJobs.keySet()) {
                this.storeJob(job, true);
                for (Trigger trigger : triggersAndJobs.get(job)) {
                    this.storeTrigger((OperableTrigger)trigger, true);
                }
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeTrigger(OperableTrigger newTrigger, boolean replaceExisting) throws JobPersistenceException {
        OperableTrigger clone = (OperableTrigger)newTrigger.clone();
        this.lock();
        try {
            JobDetail job = this.retrieveJob(newTrigger.getJobKey());
            if (job == null) {
                throw new JobPersistenceException("The job (" + newTrigger.getJobKey() + ") referenced by the trigger does not exist.");
            }
            TriggerWrapper tw = this.wrapperFactory.createTriggerWrapper(clone, job.isConcurrentExectionDisallowed());
            if (this.triggerFacade.containsKey(tw.getKey())) {
                if (!replaceExisting) {
                    throw new ObjectAlreadyExistsException(newTrigger);
                }
                this.removeTrigger(newTrigger.getKey(), false);
            }
            Set<String> grpSet = this.toolkitDSHolder.getOrCreateTriggersGroupMap(newTrigger.getKey().getGroup());
            grpSet.add(newTrigger.getKey().getName());
            if (!this.triggerFacade.hasGroup(newTrigger.getKey().getGroup())) {
                this.triggerFacade.addGroup(newTrigger.getKey().getGroup());
            }
            if (this.triggerFacade.pausedGroupsContain(newTrigger.getKey().getGroup()) || this.jobFacade.pausedGroupsContain(newTrigger.getJobKey().getGroup())) {
                tw.setState(TriggerWrapper.TriggerState.PAUSED, this.terracottaClientId, this.triggerFacade);
                if (this.jobFacade.blockedJobsContain(tw.getJobKey())) {
                    tw.setState(TriggerWrapper.TriggerState.PAUSED_BLOCKED, this.terracottaClientId, this.triggerFacade);
                }
            } else if (this.jobFacade.blockedJobsContain(tw.getJobKey())) {
                tw.setState(TriggerWrapper.TriggerState.BLOCKED, this.terracottaClientId, this.triggerFacade);
            } else {
                this.timeTriggers.add(tw);
            }
            this.triggerFacade.put(tw.getKey(), tw);
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public boolean removeTrigger(TriggerKey triggerKey) throws JobPersistenceException {
        return this.removeTrigger(triggerKey, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean removeTrigger(TriggerKey triggerKey, boolean removeOrphanedJob) throws JobPersistenceException {
        this.lock();
        TriggerWrapper tw = null;
        try {
            tw = this.triggerFacade.remove(triggerKey);
            if (tw != null) {
                Set<String> grpSet = this.toolkitDSHolder.getOrCreateTriggersGroupMap(triggerKey.getGroup());
                grpSet.remove(triggerKey.getName());
                if (grpSet.size() == 0) {
                    this.toolkitDSHolder.removeTriggersGroupMap(triggerKey.getGroup());
                    this.triggerFacade.removeGroup(triggerKey.getGroup());
                }
                this.timeTriggers.remove(tw);
                if (removeOrphanedJob) {
                    JobKey jobKey;
                    JobWrapper jw = this.jobFacade.get(tw.getJobKey());
                    List<OperableTrigger> trigs = this.getTriggersForJob(tw.getJobKey());
                    if ((trigs == null || trigs.size() == 0) && !jw.isDurable() && this.removeJob(jobKey = tw.getJobKey())) {
                        this.signaler.notifySchedulerListenersJobDeleted(jobKey);
                    }
                }
            }
        }
        finally {
            this.unlock();
        }
        return tw != null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean replaceTrigger(TriggerKey triggerKey, OperableTrigger newTrigger) throws JobPersistenceException {
        boolean found;
        block7: {
            found = false;
            this.lock();
            try {
                TriggerWrapper tw = this.triggerFacade.remove(triggerKey);
                boolean bl = found = tw != null;
                if (tw == null) break block7;
                if (!tw.getJobKey().equals(newTrigger.getJobKey())) {
                    throw new JobPersistenceException("New trigger is not related to the same job as the old trigger.");
                }
                Set<String> grpSet = this.toolkitDSHolder.getOrCreateTriggersGroupMap(triggerKey.getGroup());
                grpSet.remove(triggerKey.getName());
                if (grpSet.size() == 0) {
                    this.toolkitDSHolder.removeTriggersGroupMap(triggerKey.getGroup());
                    this.triggerFacade.removeGroup(triggerKey.getGroup());
                }
                this.timeTriggers.remove(tw);
                try {
                    this.storeTrigger(newTrigger, false);
                }
                catch (JobPersistenceException jpe) {
                    this.storeTrigger(tw.getTriggerClone(), false);
                    throw jpe;
                }
            }
            finally {
                this.unlock();
            }
        }
        return found;
    }

    @Override
    public JobDetail retrieveJob(JobKey jobKey) throws JobPersistenceException {
        JobWrapper jobWrapper = this.getJob(jobKey);
        return jobWrapper == null ? null : jobWrapper.getJobDetailClone();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    JobWrapper getJob(JobKey key) throws JobPersistenceException {
        this.lock();
        try {
            JobWrapper jobWrapper = this.jobFacade.get(key);
            return jobWrapper;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public OperableTrigger retrieveTrigger(TriggerKey triggerKey) throws JobPersistenceException {
        this.lock();
        try {
            TriggerWrapper tw = this.triggerFacade.get(triggerKey);
            OperableTrigger operableTrigger = tw != null ? tw.getTriggerClone() : null;
            return operableTrigger;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public boolean checkExists(JobKey jobKey) {
        return this.jobFacade.containsKey(jobKey);
    }

    @Override
    public boolean checkExists(TriggerKey triggerKey) throws JobPersistenceException {
        return this.triggerFacade.containsKey(triggerKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clearAllSchedulingData() throws JobPersistenceException {
        this.lock();
        try {
            Set<Key> keys;
            List<String> lst = this.getTriggerGroupNames();
            for (String group : lst) {
                keys = this.getTriggerKeys(GroupMatcher.triggerGroupEquals(group));
                for (TriggerKey triggerKey : keys) {
                    this.removeTrigger(triggerKey);
                }
            }
            lst = this.getJobGroupNames();
            for (String group : lst) {
                keys = this.getJobKeys(GroupMatcher.jobGroupEquals(group));
                for (JobKey jobKey : keys) {
                    this.removeJob(jobKey);
                }
            }
            lst = this.getCalendarNames();
            for (String name : lst) {
                this.removeCalendar(name);
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Trigger.TriggerState getTriggerState(TriggerKey key) throws JobPersistenceException {
        TriggerWrapper tw;
        this.lock();
        try {
            tw = this.triggerFacade.get(key);
        }
        finally {
            this.unlock();
        }
        if (tw == null) {
            return Trigger.TriggerState.NONE;
        }
        if (tw.getState() == TriggerWrapper.TriggerState.COMPLETE) {
            return Trigger.TriggerState.COMPLETE;
        }
        if (tw.getState() == TriggerWrapper.TriggerState.PAUSED) {
            return Trigger.TriggerState.PAUSED;
        }
        if (tw.getState() == TriggerWrapper.TriggerState.PAUSED_BLOCKED) {
            return Trigger.TriggerState.PAUSED;
        }
        if (tw.getState() == TriggerWrapper.TriggerState.BLOCKED) {
            return Trigger.TriggerState.BLOCKED;
        }
        if (tw.getState() == TriggerWrapper.TriggerState.ERROR) {
            return Trigger.TriggerState.ERROR;
        }
        return Trigger.TriggerState.NORMAL;
    }

    @Override
    public void resetTriggerFromErrorState(TriggerKey triggerKey) throws JobPersistenceException {
        TriggerWrapper tw = this.triggerFacade.get(triggerKey);
        if (tw == null) {
            return;
        }
        if (tw.getState() != TriggerWrapper.TriggerState.ERROR) {
            return;
        }
        if (this.triggerFacade.pausedGroupsContain(triggerKey.getGroup())) {
            tw.setState(TriggerWrapper.TriggerState.PAUSED, this.terracottaClientId, this.triggerFacade);
        } else {
            tw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
            this.timeTriggers.add(tw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void storeCalendar(String name, Calendar calendar, boolean replaceExisting, boolean updateTriggers) throws ObjectAlreadyExistsException, JobPersistenceException {
        Calendar clone = (Calendar)calendar.clone();
        this.lock();
        try {
            Calendar cal = (Calendar)this.calendarsByName.get((Object)name);
            if (cal != null && !replaceExisting) {
                throw new ObjectAlreadyExistsException("Calendar with name '" + name + "' already exists.");
            }
            if (cal != null) {
                this.calendarsByName.remove((Object)name);
            }
            Calendar cw = clone;
            this.calendarsByName.putNoReturn((Object)name, (Object)cw);
            if (cal != null && updateTriggers) {
                for (TriggerWrapper tw : this.triggerFacade.getTriggerWrappersForCalendar(name)) {
                    boolean removed = this.timeTriggers.remove(tw);
                    tw.updateWithNewCalendar(clone, this.getMisfireThreshold(), this.triggerFacade);
                    if (!removed) continue;
                    this.timeTriggers.add(tw);
                }
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean removeCalendar(String calName) throws JobPersistenceException {
        int numRefs = 0;
        this.lock();
        try {
            for (TriggerKey triggerKey : this.triggerFacade.allTriggerKeys()) {
                TriggerWrapper tw = this.triggerFacade.get(triggerKey);
                if (tw.getCalendarName() == null || !tw.getCalendarName().equals(calName)) continue;
                ++numRefs;
            }
            if (numRefs > 0) {
                throw new JobPersistenceException("Calender cannot be removed if it referenced by a Trigger!");
            }
            boolean bl = this.calendarsByName.remove((Object)calName) != null;
            return bl;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Calendar retrieveCalendar(String calName) throws JobPersistenceException {
        this.lock();
        try {
            Calendar cw = (Calendar)this.calendarsByName.get((Object)calName);
            Calendar calendar = (Calendar)(cw == null ? null : cw.clone());
            return calendar;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getNumberOfJobs() throws JobPersistenceException {
        this.lock();
        try {
            int n = this.jobFacade.numberOfJobs();
            return n;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getNumberOfTriggers() throws JobPersistenceException {
        this.lock();
        try {
            int n = this.triggerFacade.numberOfTriggers();
            return n;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getNumberOfCalendars() throws JobPersistenceException {
        this.lock();
        try {
            int n = this.calendarsByName.size();
            return n;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        this.lock();
        try {
            HashSet<String> matchingGroups = new HashSet<String>();
            switch (matcher.getCompareWithOperator()) {
                case EQUALS: {
                    matchingGroups.add(matcher.getCompareToValue());
                    break;
                }
                default: {
                    for (String group : this.jobFacade.getAllGroupNames()) {
                        if (!matcher.getCompareWithOperator().evaluate(group, matcher.getCompareToValue())) continue;
                        matchingGroups.add(group);
                    }
                }
            }
            HashSet<JobKey> out = new HashSet<JobKey>();
            for (String matchingGroup : matchingGroups) {
                Set<String> grpJobNames = this.toolkitDSHolder.getOrCreateJobsGroupMap(matchingGroup);
                for (String jobName : grpJobNames) {
                    JobKey jobKey = new JobKey(jobName, matchingGroup);
                    if (!this.jobFacade.containsKey(jobKey)) continue;
                    out.add(jobKey);
                }
            }
            HashSet<JobKey> hashSet = out;
            return hashSet;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getCalendarNames() throws JobPersistenceException {
        this.lock();
        try {
            Set names = this.calendarsByName.keySet();
            ArrayList<String> arrayList = new ArrayList<String>(names);
            return arrayList;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        this.lock();
        try {
            HashSet<String> groupNames = new HashSet<String>();
            switch (matcher.getCompareWithOperator()) {
                case EQUALS: {
                    groupNames.add(matcher.getCompareToValue());
                    break;
                }
                default: {
                    for (String group : this.triggerFacade.allTriggersGroupNames()) {
                        if (!matcher.getCompareWithOperator().evaluate(group, matcher.getCompareToValue())) continue;
                        groupNames.add(group);
                    }
                }
            }
            HashSet<TriggerKey> out = new HashSet<TriggerKey>();
            for (String groupName : groupNames) {
                Set<String> grpSet = this.toolkitDSHolder.getOrCreateTriggersGroupMap(groupName);
                for (String key : grpSet) {
                    TriggerKey triggerKey = new TriggerKey(key, groupName);
                    TriggerWrapper tw = this.triggerFacade.get(triggerKey);
                    if (tw == null) continue;
                    out.add(triggerKey);
                }
            }
            HashSet<TriggerKey> hashSet = out;
            return hashSet;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getJobGroupNames() throws JobPersistenceException {
        this.lock();
        try {
            ArrayList<String> arrayList = new ArrayList<String>(this.jobFacade.getAllGroupNames());
            return arrayList;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<String> getTriggerGroupNames() throws JobPersistenceException {
        this.lock();
        try {
            ArrayList<String> arrayList = new ArrayList<String>(this.triggerFacade.allTriggersGroupNames());
            return arrayList;
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<OperableTrigger> getTriggersForJob(JobKey jobKey) throws JobPersistenceException {
        ArrayList<OperableTrigger> trigList = new ArrayList<OperableTrigger>();
        this.lock();
        try {
            for (TriggerKey triggerKey : this.triggerFacade.allTriggerKeys()) {
                TriggerWrapper tw = this.triggerFacade.get(triggerKey);
                if (!tw.getJobKey().equals(jobKey)) continue;
                trigList.add(tw.getTriggerClone());
            }
        }
        finally {
            this.unlock();
        }
        return trigList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void pauseTrigger(TriggerKey triggerKey) throws JobPersistenceException {
        this.lock();
        try {
            TriggerWrapper tw = this.triggerFacade.get(triggerKey);
            if (tw == null) {
                return;
            }
            if (tw.getState() == TriggerWrapper.TriggerState.COMPLETE) {
                return;
            }
            if (tw.getState() == TriggerWrapper.TriggerState.BLOCKED) {
                tw.setState(TriggerWrapper.TriggerState.PAUSED_BLOCKED, this.terracottaClientId, this.triggerFacade);
            } else {
                tw.setState(TriggerWrapper.TriggerState.PAUSED, this.terracottaClientId, this.triggerFacade);
            }
            this.timeTriggers.remove(tw);
            if (this.triggerRemovedFromCandidateFiringListHandler != null) {
                this.triggerRemovedFromCandidateFiringListHandler.removeCandidateTrigger(tw);
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> pauseTriggers(GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        HashSet<String> pausedGroups = new HashSet<String>();
        this.lock();
        try {
            Set<TriggerKey> triggerKeys = this.getTriggerKeys(matcher);
            for (TriggerKey key : triggerKeys) {
                this.triggerFacade.addPausedGroup(key.getGroup());
                pausedGroups.add(key.getGroup());
                this.pauseTrigger(key);
            }
            StringMatcher.StringOperatorName operator = matcher.getCompareWithOperator();
            if (operator.equals((Object)StringMatcher.StringOperatorName.EQUALS)) {
                this.triggerFacade.addPausedGroup(matcher.getCompareToValue());
                pausedGroups.add(matcher.getCompareToValue());
            }
        }
        finally {
            this.unlock();
        }
        return pausedGroups;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void pauseJob(JobKey jobKey) throws JobPersistenceException {
        this.lock();
        try {
            for (OperableTrigger trigger : this.getTriggersForJob(jobKey)) {
                this.pauseTrigger(trigger.getKey());
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> pauseJobs(GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        HashSet<String> pausedGroups = new HashSet<String>();
        this.lock();
        try {
            Set<JobKey> jobKeys = this.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                for (OperableTrigger trigger : this.getTriggersForJob(jobKey)) {
                    this.pauseTrigger(trigger.getKey());
                }
                pausedGroups.add(jobKey.getGroup());
            }
            StringMatcher.StringOperatorName operator = matcher.getCompareWithOperator();
            if (operator.equals((Object)StringMatcher.StringOperatorName.EQUALS)) {
                this.jobFacade.addPausedGroup(matcher.getCompareToValue());
                pausedGroups.add(matcher.getCompareToValue());
            }
        }
        finally {
            this.unlock();
        }
        return pausedGroups;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resumeTrigger(TriggerKey triggerKey) throws JobPersistenceException {
        this.lock();
        try {
            TriggerWrapper tw = this.triggerFacade.get(triggerKey);
            if (tw == null) {
                return;
            }
            if (tw.getState() != TriggerWrapper.TriggerState.PAUSED && tw.getState() != TriggerWrapper.TriggerState.PAUSED_BLOCKED) {
                return;
            }
            if (this.jobFacade.blockedJobsContain(tw.getJobKey())) {
                tw.setState(TriggerWrapper.TriggerState.BLOCKED, this.terracottaClientId, this.triggerFacade);
            } else {
                tw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
            }
            this.applyMisfire(tw);
            if (tw.getState() == TriggerWrapper.TriggerState.WAITING) {
                this.timeTriggers.add(tw);
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> resumeTriggers(GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        HashSet<String> groups = new HashSet<String>();
        this.lock();
        try {
            Set<TriggerKey> triggerKeys = this.getTriggerKeys(matcher);
            for (TriggerKey triggerKey : triggerKeys) {
                TriggerWrapper tw = this.triggerFacade.get(triggerKey);
                if (tw != null) {
                    String jobGroup = tw.getJobKey().getGroup();
                    if (this.jobFacade.pausedGroupsContain(jobGroup)) continue;
                    groups.add(triggerKey.getGroup());
                }
                this.resumeTrigger(triggerKey);
            }
            this.triggerFacade.removeAllPausedGroups(groups);
        }
        finally {
            this.unlock();
        }
        return groups;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resumeJob(JobKey jobKey) throws JobPersistenceException {
        this.lock();
        try {
            for (OperableTrigger trigger : this.getTriggersForJob(jobKey)) {
                this.resumeTrigger(trigger.getKey());
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Collection<String> resumeJobs(GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        HashSet<String> groups = new HashSet<String>();
        this.lock();
        try {
            Set<JobKey> jobKeys = this.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                if (groups.add(jobKey.getGroup())) {
                    this.jobFacade.removePausedJobGroup(jobKey.getGroup());
                }
                for (OperableTrigger trigger : this.getTriggersForJob(jobKey)) {
                    this.resumeTrigger(trigger.getKey());
                }
            }
        }
        finally {
            this.unlock();
        }
        return groups;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void pauseAll() throws JobPersistenceException {
        this.lock();
        try {
            List<String> names = this.getTriggerGroupNames();
            for (String name : names) {
                this.pauseTriggers(GroupMatcher.triggerGroupEquals(name));
            }
        }
        finally {
            this.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void resumeAll() throws JobPersistenceException {
        this.lock();
        try {
            this.jobFacade.clearPausedJobGroups();
            List<String> names = this.getTriggerGroupNames();
            for (String name : names) {
                this.resumeTriggers(GroupMatcher.triggerGroupEquals(name));
            }
        }
        finally {
            this.unlock();
        }
    }

    boolean applyMisfire(TriggerWrapper tw) throws JobPersistenceException {
        Date tnft;
        long misfireTime = System.currentTimeMillis();
        if (this.getMisfireThreshold() > 0L) {
            misfireTime -= this.getMisfireThreshold();
        }
        if ((tnft = tw.getNextFireTime()) == null || tnft.getTime() > misfireTime || tw.getMisfireInstruction() == -1) {
            return false;
        }
        Calendar cal = null;
        if (tw.getCalendarName() != null) {
            cal = this.retrieveCalendar(tw.getCalendarName());
        }
        this.signaler.notifyTriggerListenersMisfired(tw.getTriggerClone());
        tw.updateAfterMisfire(cal, this.triggerFacade);
        if (tw.getNextFireTime() == null) {
            tw.setState(TriggerWrapper.TriggerState.COMPLETE, this.terracottaClientId, this.triggerFacade);
            this.signaler.notifySchedulerListenersFinalized(tw.getTriggerClone());
            this.timeTriggers.remove(tw);
        } else if (tnft.equals(tw.getNextFireTime())) {
            return false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<OperableTrigger> acquireNextTriggers(long noLaterThan, int maxCount, long timeWindow) throws JobPersistenceException {
        ArrayList<OperableTrigger> result = new ArrayList<OperableTrigger>();
        this.lock();
        try {
            for (TriggerWrapper tw : this.getNextTriggerWrappers(this.timeTriggers, noLaterThan, maxCount, timeWindow)) {
                result.add(this.markAndCloneTrigger(tw));
            }
            ArrayList<OperableTrigger> arrayList = result;
            return arrayList;
        }
        finally {
            block8: {
                try {
                    this.unlock();
                }
                catch (RejoinException e) {
                    if (this.validateAcquired(result)) break block8;
                    throw e;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean validateAcquired(List<OperableTrigger> result) {
        if (result.isEmpty()) {
            return false;
        }
        while (!this.toolkitShutdown) {
            try {
                this.lock();
                try {
                    for (OperableTrigger ot : result) {
                        TriggerWrapper tw = this.triggerFacade.get(ot.getKey());
                        if (ot.getFireInstanceId().equals(tw.getTriggerClone().getFireInstanceId()) && TriggerWrapper.TriggerState.ACQUIRED.equals((Object)tw.getState())) continue;
                        boolean bl = false;
                        return bl;
                    }
                    boolean i$ = true;
                    return i$;
                }
                finally {
                    this.unlock();
                }
            }
            catch (JobPersistenceException e) {
                try {
                    Thread.sleep(this.retryInterval);
                }
                catch (InterruptedException f) {
                    throw new IllegalStateException("Received interrupted exception", f);
                }
            }
            catch (RejoinException e) {
                try {
                    Thread.sleep(this.retryInterval);
                }
                catch (InterruptedException f) {
                    throw new IllegalStateException("Received interrupted exception", f);
                }
            }
        }
        throw new IllegalStateException("Scheduler has been shutdown");
    }

    OperableTrigger markAndCloneTrigger(TriggerWrapper tw) {
        tw.setState(TriggerWrapper.TriggerState.ACQUIRED, this.terracottaClientId, this.triggerFacade);
        String firedInstanceId = this.terracottaClientId + "-" + String.valueOf(this.ftrCtr++);
        tw.setFireInstanceId(firedInstanceId, this.triggerFacade);
        return tw.getTriggerClone();
    }

    List<TriggerWrapper> getNextTriggerWrappers(long noLaterThan, int maxCount, long timeWindow) throws JobPersistenceException {
        return this.getNextTriggerWrappers(this.timeTriggers, noLaterThan, maxCount, timeWindow);
    }

    List<TriggerWrapper> getNextTriggerWrappers(TimeTriggerSet source, long noLaterThan, int maxCount, long timeWindow) throws JobPersistenceException {
        ArrayList<TriggerWrapper> wrappers = new ArrayList<TriggerWrapper>();
        HashSet<JobKey> acquiredJobKeysForNoConcurrentExec = new HashSet<JobKey>();
        HashSet<TriggerWrapper> excludedTriggers = new HashSet<TriggerWrapper>();
        JobPersistenceException caughtJpe = null;
        long batchEnd = noLaterThan;
        try {
            while (true) {
                TriggerWrapper tw = null;
                try {
                    TriggerKey triggerKey = source.removeFirst();
                    if (triggerKey != null) {
                        tw = this.triggerFacade.get(triggerKey);
                    }
                    if (tw == null) {
                    }
                }
                catch (NoSuchElementException nsee) {}
                break;
                if (tw.getNextFireTime() == null) continue;
                if (this.applyMisfire(tw)) {
                    if (tw.getNextFireTime() == null) continue;
                    source.add(tw);
                    continue;
                }
                if (tw.getNextFireTime().getTime() > batchEnd) {
                    source.add(tw);
                    break;
                }
                if (tw.jobDisallowsConcurrence()) {
                    if (acquiredJobKeysForNoConcurrentExec.contains(tw.getJobKey())) {
                        excludedTriggers.add(tw);
                        continue;
                    }
                    acquiredJobKeysForNoConcurrentExec.add(tw.getJobKey());
                }
                if (wrappers.isEmpty()) {
                    batchEnd = Math.max(tw.getNextFireTime().getTime(), System.currentTimeMillis()) + timeWindow;
                }
                wrappers.add(tw);
                if (wrappers.size() == maxCount) break;
            }
        }
        catch (JobPersistenceException jpe) {
            caughtJpe = jpe;
        }
        if (excludedTriggers.size() > 0) {
            for (TriggerWrapper tw : excludedTriggers) {
                source.add(tw);
            }
        }
        if (caughtJpe != null) {
            for (TriggerWrapper tw : wrappers) {
                source.add(tw);
            }
            throw new JobPersistenceException("Exception encountered while trying to select triggers for firing.", caughtJpe);
        }
        return wrappers;
    }

    public void setTriggerRemovedFromCandidateFiringListHandler(TriggerRemovedFromCandidateFiringListHandler triggerRemovedFromCandidateFiringListHandler) {
        this.triggerRemovedFromCandidateFiringListHandler = triggerRemovedFromCandidateFiringListHandler;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void releaseAcquiredTrigger(OperableTrigger trigger) {
        while (!this.toolkitShutdown) {
            try {
                this.lock();
                try {
                    TriggerWrapper tw = this.triggerFacade.get(trigger.getKey());
                    if (tw != null && trigger.getFireInstanceId().equals(tw.getTriggerClone().getFireInstanceId()) && tw.getState() == TriggerWrapper.TriggerState.ACQUIRED) {
                        tw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
                        this.timeTriggers.add(tw);
                    }
                    break;
                }
                finally {
                    this.unlock();
                }
            }
            catch (RejoinException e) {
                try {
                    Thread.sleep(this.retryInterval);
                }
                catch (InterruptedException f) {
                    throw new IllegalStateException("Received interrupted exception", f);
                }
            }
            catch (JobPersistenceException e) {
                try {
                    Thread.sleep(this.retryInterval);
                }
                catch (InterruptedException f) {
                    throw new IllegalStateException("Received interrupted exception", f);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<TriggerFiredResult> triggersFired(List<OperableTrigger> triggersFired) throws JobPersistenceException {
        ArrayList<TriggerFiredResult> results = new ArrayList<TriggerFiredResult>();
        this.lock();
        try {
            for (OperableTrigger trigger : triggersFired) {
                TriggerWrapper tw = this.triggerFacade.get(trigger.getKey());
                if (tw == null) {
                    results.add(new TriggerFiredResult((TriggerFiredBundle)null));
                    continue;
                }
                if (tw.getState() != TriggerWrapper.TriggerState.ACQUIRED) {
                    results.add(new TriggerFiredResult((TriggerFiredBundle)null));
                    continue;
                }
                Calendar cal = null;
                if (tw.getCalendarName() != null && (cal = this.retrieveCalendar(tw.getCalendarName())) == null) {
                    results.add(new TriggerFiredResult((TriggerFiredBundle)null));
                    continue;
                }
                Date prevFireTime = trigger.getPreviousFireTime();
                this.timeTriggers.remove(tw);
                tw.triggered(cal, this.triggerFacade);
                trigger.triggered(cal);
                tw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
                TriggerFiredBundle bndle = new TriggerFiredBundle(this.retrieveJob(trigger.getJobKey()), trigger, cal, false, new Date(), trigger.getPreviousFireTime(), prevFireTime, trigger.getNextFireTime());
                String fireInstanceId = trigger.getFireInstanceId();
                FiredTrigger prev = this.triggerFacade.getFiredTrigger(fireInstanceId);
                this.triggerFacade.putFiredTrigger(fireInstanceId, new FiredTrigger(this.terracottaClientId, tw.getKey(), trigger.getPreviousFireTime().getTime()));
                this.getLog().trace("Tracking " + trigger + " has fired on " + fireInstanceId);
                if (prev != null) {
                    throw new AssertionError((Object)("duplicate fireInstanceId detected (" + fireInstanceId + ") for " + trigger + ", previous is " + prev));
                }
                JobDetail job = bndle.getJobDetail();
                if (job.isConcurrentExectionDisallowed()) {
                    List<TriggerWrapper> trigs = this.triggerFacade.getTriggerWrappersForJob(job.getKey());
                    for (TriggerWrapper ttw : trigs) {
                        if (ttw.getKey().equals(tw.getKey())) continue;
                        if (ttw.getState() == TriggerWrapper.TriggerState.WAITING) {
                            ttw.setState(TriggerWrapper.TriggerState.BLOCKED, this.terracottaClientId, this.triggerFacade);
                        }
                        if (ttw.getState() == TriggerWrapper.TriggerState.PAUSED) {
                            ttw.setState(TriggerWrapper.TriggerState.PAUSED_BLOCKED, this.terracottaClientId, this.triggerFacade);
                        }
                        this.timeTriggers.remove(ttw);
                        if (this.triggerRemovedFromCandidateFiringListHandler == null) continue;
                        this.triggerRemovedFromCandidateFiringListHandler.removeCandidateTrigger(ttw);
                    }
                    this.jobFacade.addBlockedJob(job.getKey());
                } else if (tw.getNextFireTime() != null) {
                    this.timeTriggers.add(tw);
                }
                results.add(new TriggerFiredResult(bndle));
            }
            ArrayList<TriggerFiredResult> arrayList = results;
            return arrayList;
        }
        finally {
            block18: {
                try {
                    this.unlock();
                }
                catch (RejoinException e) {
                    if (this.validateFiring(results)) break block18;
                    throw e;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean validateFiring(List<TriggerFiredResult> result) {
        if (result.isEmpty()) {
            return false;
        }
        while (!this.toolkitShutdown) {
            try {
                this.lock();
                try {
                    for (TriggerFiredResult tfr : result) {
                        TriggerFiredBundle tfb = tfr.getTriggerFiredBundle();
                        if (tfb == null || this.triggerFacade.containsFiredTrigger(tfb.getTrigger().getFireInstanceId())) continue;
                        boolean bl = false;
                        return bl;
                    }
                    boolean i$ = true;
                    return i$;
                }
                finally {
                    this.unlock();
                }
            }
            catch (JobPersistenceException e) {
                try {
                    Thread.sleep(this.retryInterval);
                }
                catch (InterruptedException f) {
                    throw new IllegalStateException("Received interrupted exception", f);
                }
            }
            catch (RejoinException e) {
                try {
                    Thread.sleep(this.retryInterval);
                }
                catch (InterruptedException f) {
                    throw new IllegalStateException("Received interrupted exception", f);
                }
            }
        }
        throw new IllegalStateException("Scheduler has been shutdown");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void triggeredJobComplete(OperableTrigger trigger, JobDetail jobDetail, Trigger.CompletedExecutionInstruction triggerInstCode) {
        while (!this.toolkitShutdown) {
            try {
                this.lock();
                try {
                    String fireId = trigger.getFireInstanceId();
                    FiredTrigger removed = this.triggerFacade.removeFiredTrigger(fireId);
                    if (removed == null) {
                        this.getLog().warn("No fired trigger record found for " + trigger + " (" + fireId + ")");
                    } else {
                        JobKey jobKey = jobDetail.getKey();
                        JobWrapper jw = this.jobFacade.get(jobKey);
                        TriggerWrapper tw = this.triggerFacade.get(trigger.getKey());
                        if (jw != null) {
                            if (jw.isPersistJobDataAfterExecution()) {
                                JobDataMap newData = jobDetail.getJobDataMap();
                                if (newData != null) {
                                    newData = (JobDataMap)newData.clone();
                                    newData.clearDirtyFlag();
                                }
                                jw.setJobDataMap(newData, this.jobFacade);
                            }
                            if (jw.isConcurrentExectionDisallowed()) {
                                this.jobFacade.removeBlockedJob(jw.getKey());
                                tw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
                                this.timeTriggers.add(tw);
                                List<TriggerWrapper> trigs = this.triggerFacade.getTriggerWrappersForJob(jw.getKey());
                                for (TriggerWrapper ttw : trigs) {
                                    if (ttw.getState() == TriggerWrapper.TriggerState.BLOCKED) {
                                        ttw.setState(TriggerWrapper.TriggerState.WAITING, this.terracottaClientId, this.triggerFacade);
                                        this.timeTriggers.add(ttw);
                                    }
                                    if (ttw.getState() != TriggerWrapper.TriggerState.PAUSED_BLOCKED) continue;
                                    ttw.setState(TriggerWrapper.TriggerState.PAUSED, this.terracottaClientId, this.triggerFacade);
                                }
                                this.signaler.signalSchedulingChange(0L);
                            }
                        } else {
                            this.jobFacade.removeBlockedJob(jobKey);
                        }
                        if (tw != null) {
                            if (triggerInstCode == Trigger.CompletedExecutionInstruction.DELETE_TRIGGER) {
                                if (trigger.getNextFireTime() == null) {
                                    if (tw.getNextFireTime() == null) {
                                        this.removeTrigger(trigger.getKey());
                                    }
                                } else {
                                    this.removeTrigger(trigger.getKey());
                                    this.signaler.signalSchedulingChange(0L);
                                }
                            } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_COMPLETE) {
                                tw.setState(TriggerWrapper.TriggerState.COMPLETE, this.terracottaClientId, this.triggerFacade);
                                this.timeTriggers.remove(tw);
                                this.signaler.signalSchedulingChange(0L);
                            } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_ERROR) {
                                this.getLog().info("Trigger " + trigger.getKey() + " set to ERROR state.");
                                tw.setState(TriggerWrapper.TriggerState.ERROR, this.terracottaClientId, this.triggerFacade);
                                this.signaler.signalSchedulingChange(0L);
                            } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR) {
                                this.getLog().info("All triggers of Job " + trigger.getJobKey() + " set to ERROR state.");
                                this.setAllTriggersOfJobToState(trigger.getJobKey(), TriggerWrapper.TriggerState.ERROR);
                                this.signaler.signalSchedulingChange(0L);
                            } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE) {
                                this.setAllTriggersOfJobToState(trigger.getJobKey(), TriggerWrapper.TriggerState.COMPLETE);
                                this.signaler.signalSchedulingChange(0L);
                            }
                        }
                    }
                    break;
                }
                finally {
                    this.unlock();
                }
            }
            catch (RejoinException e) {
                try {
                    Thread.sleep(this.retryInterval);
                }
                catch (InterruptedException f) {
                    throw new IllegalStateException("Received interrupted exception", f);
                }
            }
            catch (JobPersistenceException e) {
                try {
                    Thread.sleep(this.retryInterval);
                }
                catch (InterruptedException f) {
                    throw new IllegalStateException("Received interrupted exception", f);
                }
            }
        }
    }

    private void setAllTriggersOfJobToState(JobKey jobKey, TriggerWrapper.TriggerState state) {
        List<TriggerWrapper> tws = this.triggerFacade.getTriggerWrappersForJob(jobKey);
        for (TriggerWrapper tw : tws) {
            tw.setState(state, this.terracottaClientId, this.triggerFacade);
            if (state == TriggerWrapper.TriggerState.WAITING) continue;
            this.timeTriggers.remove(tw);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Set<String> getPausedTriggerGroups() throws JobPersistenceException {
        this.lock();
        try {
            HashSet<String> hashSet = new HashSet<String>(this.triggerFacade.allPausedTriggersGroupNames());
            return hashSet;
        }
        finally {
            this.unlock();
        }
    }

    @Override
    public void setInstanceId(String schedInstId) {
    }

    @Override
    public void setInstanceName(String schedName) {
    }

    @Override
    public void setTcRetryInterval(long retryInterval) {
        this.retryInterval = retryInterval;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void nodeLeft(ClusterEvent event) {
        String nodeLeft = event.getNode().getId();
        try {
            this.lock();
        }
        catch (JobPersistenceException e) {
            this.getLog().info("Job store is already disabled, not processing nodeLeft() for " + nodeLeft);
            return;
        }
        try {
            TriggerWrapper tw;
            ArrayList<TriggerWrapper> toEval = new ArrayList<TriggerWrapper>();
            for (TriggerKey triggerKey : this.triggerFacade.allTriggerKeys()) {
                tw = this.triggerFacade.get(triggerKey);
                String clientId = tw.getLastTerracotaClientId();
                if (clientId == null || !clientId.equals(nodeLeft)) continue;
                toEval.add(tw);
            }
            for (TriggerWrapper tw2 : toEval) {
                this.evalOrphanedTrigger(tw2, false);
            }
            Iterator<FiredTrigger> iter = this.triggerFacade.allFiredTriggers().iterator();
            while (iter.hasNext()) {
                FiredTrigger ft = iter.next();
                if (!nodeLeft.equals(ft.getClientId())) continue;
                this.getLog().info("Found non-complete fired trigger: " + ft);
                iter.remove();
                tw = this.triggerFacade.get(ft.getTriggerKey());
                if (tw == null) {
                    this.getLog().error("no trigger found for executing trigger: " + ft.getTriggerKey());
                    continue;
                }
                this.scheduleRecoveryIfNeeded(tw, ft);
            }
        }
        finally {
            this.unlock();
        }
        this.signaler.signalSchedulingChange(0L);
    }

    @Override
    public long getEstimatedTimeToReleaseAndAcquireTrigger() {
        return this.estimatedTimeToReleaseAndAcquireTrigger;
    }

    @Override
    public void setEstimatedTimeToReleaseAndAcquireTrigger(long estimate) {
        this.estimatedTimeToReleaseAndAcquireTrigger = estimate;
    }

    @Override
    public void setThreadPoolSize(int size) {
    }

    @Override
    public boolean isClustered() {
        throw new AssertionError();
    }

    @Override
    public long getAcquireRetryDelay(int failureCount) {
        return this.retryInterval;
    }

    void injectTriggerWrapper(TriggerWrapper triggerWrapper) {
        this.timeTriggers.add(triggerWrapper);
    }

    ClusterInfo getDsoCluster() {
        return this.clusterInfo;
    }

    public void onClusterEvent(ClusterEvent event) {
        switch (event.getType()) {
            case NODE_JOINED: 
            case OPERATIONS_DISABLED: 
            case OPERATIONS_ENABLED: {
                break;
            }
            case NODE_LEFT: {
                this.getLog().info("Received node left notification for " + event.getNode().getId());
                this.nodeLeft(event);
                break;
            }
            case NODE_REJOINED: {
                this.getLog().info("Received rejoin notification " + this.terracottaClientId + " => " + event.getNode().getId());
                this.terracottaClientId = event.getNode().getId();
            }
        }
    }

    protected TriggerFacade getTriggersFacade() {
        return this.triggerFacade;
    }

    static interface TriggerRemovedFromCandidateFiringListHandler {
        public boolean removeCandidateTrigger(TriggerWrapper var1);
    }

    private static class LocalLockState {
        private int acquires = 0;
        private boolean disabled;

        private LocalLockState() {
        }

        synchronized void attemptAcquireBegin() throws JobPersistenceException {
            if (this.disabled) {
                throw new JobPersistenceException("org.terracotta.quartz.TerracottaJobStore is disabled");
            }
            ++this.acquires;
        }

        synchronized void release() {
            --this.acquires;
            this.notifyAll();
        }

        synchronized void disableLocking() throws InterruptedException {
            this.disabled = true;
            while (this.acquires > 0) {
                this.wait();
            }
        }
    }

    private static class ShutdownHook
    implements Runnable {
        private final DefaultClusteredJobStore store;

        ShutdownHook(DefaultClusteredJobStore store) {
            this.store = store;
        }

        @Override
        public void run() {
            this.store.disable();
        }
    }
}


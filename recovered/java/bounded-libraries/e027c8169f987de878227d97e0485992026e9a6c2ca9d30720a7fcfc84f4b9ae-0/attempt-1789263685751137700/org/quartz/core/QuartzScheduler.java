/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.core;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.quartz.Calendar;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.ListenerManager;
import org.quartz.Matcher;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.TriggerListener;
import org.quartz.UnableToInterruptJobException;
import org.quartz.core.ErrorLogger;
import org.quartz.core.ExecutingJobsManager;
import org.quartz.core.ListenerManagerImpl;
import org.quartz.core.QuartzSchedulerMBeanImpl;
import org.quartz.core.QuartzSchedulerResources;
import org.quartz.core.QuartzSchedulerThread;
import org.quartz.core.RemotableQuartzScheduler;
import org.quartz.core.SchedulerSignalerImpl;
import org.quartz.core.jmx.QuartzSchedulerMBean;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.simpl.PropertySettingJobFactory;
import org.quartz.spi.JobFactory;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.SchedulerPlugin;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.spi.ThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzScheduler
implements RemotableQuartzScheduler {
    private static String VERSION_MAJOR = "UNKNOWN";
    private static String VERSION_MINOR = "UNKNOWN";
    private static String VERSION_ITERATION = "UNKNOWN";
    private QuartzSchedulerResources resources;
    private QuartzSchedulerThread schedThread;
    private ThreadGroup threadGroup;
    private SchedulerContext context = new SchedulerContext();
    private ListenerManager listenerManager = new ListenerManagerImpl();
    private HashMap<String, JobListener> internalJobListeners = new HashMap(10);
    private HashMap<String, TriggerListener> internalTriggerListeners = new HashMap(10);
    private ArrayList<SchedulerListener> internalSchedulerListeners = new ArrayList(10);
    private JobFactory jobFactory = new PropertySettingJobFactory();
    ExecutingJobsManager jobMgr = null;
    ErrorLogger errLogger = null;
    private SchedulerSignaler signaler;
    private Random random = new Random();
    private ArrayList<Object> holdToPreventGC = new ArrayList(5);
    private boolean signalOnSchedulingChange = true;
    private volatile boolean closed = false;
    private volatile boolean shuttingDown = false;
    private boolean boundRemotely = false;
    private QuartzSchedulerMBean jmxBean = null;
    private Date initialStart = null;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public QuartzScheduler(QuartzSchedulerResources resources, long idleWaitTime, @Deprecated long dbRetryInterval) throws SchedulerException {
        this.resources = resources;
        if (resources.getJobStore() instanceof JobListener) {
            this.addInternalJobListener((JobListener)((Object)resources.getJobStore()));
        }
        this.schedThread = new QuartzSchedulerThread(this, resources);
        ThreadExecutor schedThreadExecutor = resources.getThreadExecutor();
        schedThreadExecutor.execute(this.schedThread);
        if (idleWaitTime > 0L) {
            this.schedThread.setIdleWaitTime(idleWaitTime);
        }
        this.jobMgr = new ExecutingJobsManager();
        this.addInternalJobListener(this.jobMgr);
        this.errLogger = new ErrorLogger();
        this.addInternalSchedulerListener(this.errLogger);
        this.signaler = new SchedulerSignalerImpl(this, this.schedThread);
        this.getLog().info("Quartz Scheduler v." + this.getVersion() + " created.");
    }

    public void initialize() throws SchedulerException {
        try {
            this.bind();
        }
        catch (Exception re) {
            throw new SchedulerException("Unable to bind scheduler to RMI Registry.", re);
        }
        if (this.resources.getJMXExport()) {
            try {
                this.registerJMX();
            }
            catch (Exception e) {
                throw new SchedulerException("Unable to register scheduler with MBeanServer.", e);
            }
        }
        this.getLog().info("Scheduler meta-data: " + new SchedulerMetaData(this.getSchedulerName(), this.getSchedulerInstanceId(), this.getClass(), this.boundRemotely, this.runningSince() != null, this.isInStandbyMode(), this.isShutdown(), this.runningSince(), this.numJobsExecuted(), this.getJobStoreClass(), this.supportsPersistence(), this.isClustered(), this.getThreadPoolClass(), this.getThreadPoolSize(), this.getVersion()).toString());
    }

    @Override
    public String getVersion() {
        return QuartzScheduler.getVersionMajor() + "." + QuartzScheduler.getVersionMinor() + "." + QuartzScheduler.getVersionIteration();
    }

    public static String getVersionMajor() {
        return VERSION_MAJOR;
    }

    public static String getVersionMinor() {
        return VERSION_MINOR;
    }

    public static String getVersionIteration() {
        return VERSION_ITERATION;
    }

    public SchedulerSignaler getSchedulerSignaler() {
        return this.signaler;
    }

    public Logger getLog() {
        return this.log;
    }

    private void registerJMX() throws Exception {
        String jmxObjectName = this.resources.getJMXObjectName();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        this.jmxBean = new QuartzSchedulerMBeanImpl(this);
        mbs.registerMBean(this.jmxBean, new ObjectName(jmxObjectName));
    }

    private void unregisterJMX() throws Exception {
        String jmxObjectName = this.resources.getJMXObjectName();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        mbs.unregisterMBean(new ObjectName(jmxObjectName));
        this.jmxBean.setSampledStatisticsEnabled(false);
        this.getLog().info("Scheduler unregistered from name '" + jmxObjectName + "' in the local MBeanServer.");
    }

    private void bind() throws RemoteException {
        String host = this.resources.getRMIRegistryHost();
        if (host == null || host.length() == 0) {
            return;
        }
        RemotableQuartzScheduler exportable = null;
        exportable = this.resources.getRMIServerPort() > 0 ? (RemotableQuartzScheduler)UnicastRemoteObject.exportObject((Remote)this, this.resources.getRMIServerPort()) : (RemotableQuartzScheduler)((Object)UnicastRemoteObject.exportObject(this));
        Registry registry = null;
        if (this.resources.getRMICreateRegistryStrategy().equals("as_needed")) {
            try {
                registry = LocateRegistry.getRegistry(this.resources.getRMIRegistryPort());
                registry.list();
            }
            catch (Exception e) {
                registry = LocateRegistry.createRegistry(this.resources.getRMIRegistryPort());
            }
        } else if (this.resources.getRMICreateRegistryStrategy().equals("always")) {
            try {
                registry = LocateRegistry.createRegistry(this.resources.getRMIRegistryPort());
            }
            catch (Exception e) {
                registry = LocateRegistry.getRegistry(this.resources.getRMIRegistryPort());
            }
        } else {
            registry = LocateRegistry.getRegistry(this.resources.getRMIRegistryHost(), this.resources.getRMIRegistryPort());
        }
        String bindName = this.resources.getRMIBindName();
        registry.rebind(bindName, exportable);
        this.boundRemotely = true;
        this.getLog().info("Scheduler bound to RMI registry under name '" + bindName + "'");
    }

    private void unBind() throws RemoteException {
        String host = this.resources.getRMIRegistryHost();
        if (host == null || host.length() == 0) {
            return;
        }
        Registry registry = LocateRegistry.getRegistry(this.resources.getRMIRegistryHost(), this.resources.getRMIRegistryPort());
        String bindName = this.resources.getRMIBindName();
        try {
            registry.unbind(bindName);
            UnicastRemoteObject.unexportObject(this, true);
        }
        catch (NotBoundException notBoundException) {
            // empty catch block
        }
        this.getLog().info("Scheduler un-bound from name '" + bindName + "' in RMI registry");
    }

    @Override
    public String getSchedulerName() {
        return this.resources.getName();
    }

    @Override
    public String getSchedulerInstanceId() {
        return this.resources.getInstanceId();
    }

    public ThreadGroup getSchedulerThreadGroup() {
        if (this.threadGroup == null) {
            this.threadGroup = new ThreadGroup("QuartzScheduler:" + this.getSchedulerName());
            if (this.resources.getMakeSchedulerThreadDaemon()) {
                this.threadGroup.setDaemon(true);
            }
        }
        return this.threadGroup;
    }

    public void addNoGCObject(Object obj) {
        this.holdToPreventGC.add(obj);
    }

    public boolean removeNoGCObject(Object obj) {
        return this.holdToPreventGC.remove(obj);
    }

    @Override
    public SchedulerContext getSchedulerContext() throws SchedulerException {
        return this.context;
    }

    public boolean isSignalOnSchedulingChange() {
        return this.signalOnSchedulingChange;
    }

    public void setSignalOnSchedulingChange(boolean signalOnSchedulingChange) {
        this.signalOnSchedulingChange = signalOnSchedulingChange;
    }

    @Override
    public void start() throws SchedulerException {
        if (this.shuttingDown || this.closed) {
            throw new SchedulerException("The Scheduler cannot be restarted after shutdown() has been called.");
        }
        this.notifySchedulerListenersStarting();
        if (this.initialStart == null) {
            this.initialStart = new Date();
            this.resources.getJobStore().schedulerStarted();
            this.startPlugins();
        } else {
            this.resources.getJobStore().schedulerResumed();
        }
        this.schedThread.togglePause(false);
        this.getLog().info("Scheduler " + this.resources.getUniqueIdentifier() + " started.");
        this.notifySchedulerListenersStarted();
    }

    @Override
    public void startDelayed(final int seconds) throws SchedulerException {
        if (this.shuttingDown || this.closed) {
            throw new SchedulerException("The Scheduler cannot be restarted after shutdown() has been called.");
        }
        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    Thread.sleep((long)seconds * 1000L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                try {
                    QuartzScheduler.this.start();
                }
                catch (SchedulerException se) {
                    QuartzScheduler.this.getLog().error("Unable to start scheduler after startup delay.", (Throwable)se);
                }
            }
        });
        t.start();
    }

    @Override
    public void standby() {
        this.resources.getJobStore().schedulerPaused();
        this.schedThread.togglePause(true);
        this.getLog().info("Scheduler " + this.resources.getUniqueIdentifier() + " paused.");
        this.notifySchedulerListenersInStandbyMode();
    }

    @Override
    public boolean isInStandbyMode() {
        return this.schedThread.isPaused();
    }

    @Override
    public Date runningSince() {
        if (this.initialStart == null) {
            return null;
        }
        return new Date(this.initialStart.getTime());
    }

    @Override
    public int numJobsExecuted() {
        return this.jobMgr.getNumJobsFired();
    }

    @Override
    public Class<?> getJobStoreClass() {
        return this.resources.getJobStore().getClass();
    }

    @Override
    public boolean supportsPersistence() {
        return this.resources.getJobStore().supportsPersistence();
    }

    @Override
    public boolean isClustered() {
        return this.resources.getJobStore().isClustered();
    }

    @Override
    public Class<?> getThreadPoolClass() {
        return this.resources.getThreadPool().getClass();
    }

    @Override
    public int getThreadPoolSize() {
        return this.resources.getThreadPool().getPoolSize();
    }

    @Override
    public void shutdown() {
        this.shutdown(false);
    }

    @Override
    public void shutdown(boolean waitForJobsToComplete) {
        if (this.shuttingDown || this.closed) {
            return;
        }
        this.shuttingDown = true;
        this.getLog().info("Scheduler " + this.resources.getUniqueIdentifier() + " shutting down.");
        this.standby();
        this.schedThread.halt(waitForJobsToComplete);
        this.notifySchedulerListenersShuttingdown();
        if (this.resources.isInterruptJobsOnShutdown() && !waitForJobsToComplete || this.resources.isInterruptJobsOnShutdownWithWait() && waitForJobsToComplete) {
            List<JobExecutionContext> jobs = this.getCurrentlyExecutingJobs();
            for (JobExecutionContext job : jobs) {
                if (!(job.getJobInstance() instanceof InterruptableJob)) continue;
                try {
                    ((InterruptableJob)job.getJobInstance()).interrupt();
                }
                catch (Throwable e) {
                    this.getLog().warn("Encountered error when interrupting job {} during shutdown: {}", (Object)job.getJobDetail().getKey(), (Object)e);
                }
            }
        }
        this.resources.getThreadPool().shutdown(waitForJobsToComplete);
        this.closed = true;
        if (this.resources.getJMXExport()) {
            try {
                this.unregisterJMX();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.boundRemotely) {
            try {
                this.unBind();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        this.shutdownPlugins();
        this.resources.getJobStore().shutdown();
        this.notifySchedulerListenersShutdown();
        SchedulerRepository.getInstance().remove(this.resources.getName());
        this.holdToPreventGC.clear();
        this.getLog().info("Scheduler " + this.resources.getUniqueIdentifier() + " shutdown complete.");
    }

    @Override
    public boolean isShutdown() {
        return this.closed;
    }

    public boolean isShuttingDown() {
        return this.shuttingDown;
    }

    public boolean isStarted() {
        return !this.shuttingDown && !this.closed && !this.isInStandbyMode() && this.initialStart != null;
    }

    public void validateState() throws SchedulerException {
        if (this.isShutdown()) {
            throw new SchedulerException("The Scheduler has been shutdown.");
        }
    }

    @Override
    public List<JobExecutionContext> getCurrentlyExecutingJobs() {
        return this.jobMgr.getExecutingJobs();
    }

    @Override
    public Date scheduleJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        Date ft;
        this.validateState();
        if (jobDetail == null) {
            throw new SchedulerException("JobDetail cannot be null");
        }
        if (trigger == null) {
            throw new SchedulerException("Trigger cannot be null");
        }
        if (jobDetail.getKey() == null) {
            throw new SchedulerException("Job's key cannot be null");
        }
        if (jobDetail.getJobClass() == null) {
            throw new SchedulerException("Job's class cannot be null");
        }
        OperableTrigger trig = (OperableTrigger)trigger;
        if (trigger.getJobKey() == null) {
            trig.setJobKey(jobDetail.getKey());
        } else if (!trigger.getJobKey().equals(jobDetail.getKey())) {
            throw new SchedulerException("Trigger does not reference given job!");
        }
        trig.validate();
        Calendar cal = null;
        if (trigger.getCalendarName() != null) {
            cal = this.resources.getJobStore().retrieveCalendar(trigger.getCalendarName());
        }
        if ((ft = trig.computeFirstFireTime(cal)) == null) {
            throw new SchedulerException("Based on configured schedule, the given trigger '" + trigger.getKey() + "' will never fire.");
        }
        this.resources.getJobStore().storeJobAndTrigger(jobDetail, trig);
        this.notifySchedulerListenersJobAdded(jobDetail);
        this.notifySchedulerThread(trigger.getNextFireTime().getTime());
        this.notifySchedulerListenersSchduled(trigger);
        return ft;
    }

    @Override
    public Date scheduleJob(Trigger trigger) throws SchedulerException {
        this.validateState();
        if (trigger == null) {
            throw new SchedulerException("Trigger cannot be null");
        }
        OperableTrigger trig = (OperableTrigger)trigger;
        trig.validate();
        Calendar cal = null;
        if (trigger.getCalendarName() != null && (cal = this.resources.getJobStore().retrieveCalendar(trigger.getCalendarName())) == null) {
            throw new SchedulerException("Calendar not found: " + trigger.getCalendarName());
        }
        Date ft = trig.computeFirstFireTime(cal);
        if (ft == null) {
            throw new SchedulerException("Based on configured schedule, the given trigger '" + trigger.getKey() + "' will never fire.");
        }
        this.resources.getJobStore().storeTrigger(trig, false);
        this.notifySchedulerThread(trigger.getNextFireTime().getTime());
        this.notifySchedulerListenersSchduled(trigger);
        return ft;
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace) throws SchedulerException {
        this.addJob(jobDetail, replace, false);
    }

    @Override
    public void addJob(JobDetail jobDetail, boolean replace, boolean storeNonDurableWhileAwaitingScheduling) throws SchedulerException {
        this.validateState();
        if (!storeNonDurableWhileAwaitingScheduling && !jobDetail.isDurable()) {
            throw new SchedulerException("Jobs added with no trigger must be durable.");
        }
        this.resources.getJobStore().storeJob(jobDetail, replace);
        this.notifySchedulerThread(0L);
        this.notifySchedulerListenersJobAdded(jobDetail);
    }

    @Override
    public boolean deleteJob(JobKey jobKey) throws SchedulerException {
        this.validateState();
        boolean result = false;
        List<? extends Trigger> triggers = this.getTriggersOfJob(jobKey);
        for (Trigger trigger : triggers) {
            if (!this.unscheduleJob(trigger.getKey())) {
                StringBuilder sb = new StringBuilder().append("Unable to unschedule trigger [").append(trigger.getKey()).append("] while deleting job [").append(jobKey).append("]");
                throw new SchedulerException(sb.toString());
            }
            result = true;
        }
        boolean bl = result = this.resources.getJobStore().removeJob(jobKey) || result;
        if (result) {
            this.notifySchedulerThread(0L);
            this.notifySchedulerListenersJobDeleted(jobKey);
        }
        return result;
    }

    @Override
    public boolean deleteJobs(List<JobKey> jobKeys) throws SchedulerException {
        this.validateState();
        boolean result = false;
        result = this.resources.getJobStore().removeJobs(jobKeys);
        this.notifySchedulerThread(0L);
        for (JobKey key : jobKeys) {
            this.notifySchedulerListenersJobDeleted(key);
        }
        return result;
    }

    @Override
    public void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException {
        this.validateState();
        for (Map.Entry<JobDetail, Set<? extends Trigger>> e : triggersAndJobs.entrySet()) {
            Set<? extends Trigger> triggers;
            JobDetail job = e.getKey();
            if (job == null || (triggers = e.getValue()) == null) continue;
            for (Trigger trigger : triggers) {
                OperableTrigger opt = (OperableTrigger)trigger;
                opt.setJobKey(job.getKey());
                opt.validate();
                Calendar cal = null;
                if (trigger.getCalendarName() != null && (cal = this.resources.getJobStore().retrieveCalendar(trigger.getCalendarName())) == null) {
                    throw new SchedulerException("Calendar '" + trigger.getCalendarName() + "' not found for trigger: " + trigger.getKey());
                }
                Date ft = opt.computeFirstFireTime(cal);
                if (ft != null) continue;
                throw new SchedulerException("Based on configured schedule, the given trigger will never fire.");
            }
        }
        this.resources.getJobStore().storeJobsAndTriggers(triggersAndJobs, replace);
        this.notifySchedulerThread(0L);
        for (JobDetail job : triggersAndJobs.keySet()) {
            this.notifySchedulerListenersJobAdded(job);
            Set<? extends Trigger> triggers = triggersAndJobs.get(job);
            for (Trigger trigger : triggers) {
                this.notifySchedulerListenersSchduled(trigger);
            }
        }
    }

    @Override
    public void scheduleJob(JobDetail jobDetail, Set<? extends Trigger> triggersForJob, boolean replace) throws SchedulerException {
        HashMap<JobDetail, Set<? extends Trigger>> triggersAndJobs = new HashMap<JobDetail, Set<? extends Trigger>>();
        triggersAndJobs.put(jobDetail, triggersForJob);
        this.scheduleJobs(triggersAndJobs, replace);
    }

    @Override
    public boolean unscheduleJobs(List<TriggerKey> triggerKeys) throws SchedulerException {
        this.validateState();
        boolean result = false;
        result = this.resources.getJobStore().removeTriggers(triggerKeys);
        this.notifySchedulerThread(0L);
        for (TriggerKey key : triggerKeys) {
            this.notifySchedulerListenersUnscheduled(key);
        }
        return result;
    }

    @Override
    public boolean unscheduleJob(TriggerKey triggerKey) throws SchedulerException {
        this.validateState();
        if (!this.resources.getJobStore().removeTrigger(triggerKey)) {
            return false;
        }
        this.notifySchedulerThread(0L);
        this.notifySchedulerListenersUnscheduled(triggerKey);
        return true;
    }

    @Override
    public Date rescheduleJob(TriggerKey triggerKey, Trigger newTrigger) throws SchedulerException {
        Date ft;
        this.validateState();
        if (triggerKey == null) {
            throw new IllegalArgumentException("triggerKey cannot be null");
        }
        if (newTrigger == null) {
            throw new IllegalArgumentException("newTrigger cannot be null");
        }
        OperableTrigger trig = (OperableTrigger)newTrigger;
        Trigger oldTrigger = this.getTrigger(triggerKey);
        if (oldTrigger == null) {
            return null;
        }
        trig.setJobKey(oldTrigger.getJobKey());
        trig.validate();
        Calendar cal = null;
        if (newTrigger.getCalendarName() != null) {
            cal = this.resources.getJobStore().retrieveCalendar(newTrigger.getCalendarName());
        }
        if ((ft = trig.computeFirstFireTime(cal)) == null) {
            throw new SchedulerException("Based on configured schedule, the given trigger will never fire.");
        }
        if (!this.resources.getJobStore().replaceTrigger(triggerKey, trig)) {
            return null;
        }
        this.notifySchedulerThread(newTrigger.getNextFireTime().getTime());
        this.notifySchedulerListenersUnscheduled(triggerKey);
        this.notifySchedulerListenersSchduled(newTrigger);
        return ft;
    }

    private String newTriggerId() {
        long r = this.random.nextLong();
        if (r < 0L) {
            r = -r;
        }
        return "MT_" + Long.toString(r, 30 + (int)(System.currentTimeMillis() % 7L));
    }

    @Override
    public void triggerJob(JobKey jobKey, JobDataMap data) throws SchedulerException {
        this.validateState();
        OperableTrigger trig = (OperableTrigger)TriggerBuilder.newTrigger().withIdentity(this.newTriggerId(), "DEFAULT").forJob(jobKey).build();
        trig.computeFirstFireTime(null);
        if (data != null) {
            trig.setJobDataMap(data);
        }
        boolean collision = true;
        while (collision) {
            try {
                this.resources.getJobStore().storeTrigger(trig, false);
                collision = false;
            }
            catch (ObjectAlreadyExistsException oaee) {
                trig.setKey(new TriggerKey(this.newTriggerId(), "DEFAULT"));
            }
        }
        this.notifySchedulerThread(trig.getNextFireTime().getTime());
        this.notifySchedulerListenersSchduled(trig);
    }

    @Override
    public void triggerJob(OperableTrigger trig) throws SchedulerException {
        this.validateState();
        trig.computeFirstFireTime(null);
        boolean collision = true;
        while (collision) {
            try {
                this.resources.getJobStore().storeTrigger(trig, false);
                collision = false;
            }
            catch (ObjectAlreadyExistsException oaee) {
                trig.setKey(new TriggerKey(this.newTriggerId(), "DEFAULT"));
            }
        }
        this.notifySchedulerThread(trig.getNextFireTime().getTime());
        this.notifySchedulerListenersSchduled(trig);
    }

    @Override
    public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().pauseTrigger(triggerKey);
        this.notifySchedulerThread(0L);
        this.notifySchedulerListenersPausedTrigger(triggerKey);
    }

    @Override
    public void pauseTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        this.validateState();
        if (matcher == null) {
            matcher = GroupMatcher.groupEquals("DEFAULT");
        }
        Collection<String> pausedGroups = this.resources.getJobStore().pauseTriggers(matcher);
        this.notifySchedulerThread(0L);
        for (String pausedGroup : pausedGroups) {
            this.notifySchedulerListenersPausedTriggers(pausedGroup);
        }
    }

    @Override
    public void pauseJob(JobKey jobKey) throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().pauseJob(jobKey);
        this.notifySchedulerThread(0L);
        this.notifySchedulerListenersPausedJob(jobKey);
    }

    @Override
    public void pauseJobs(GroupMatcher<JobKey> groupMatcher) throws SchedulerException {
        this.validateState();
        if (groupMatcher == null) {
            groupMatcher = GroupMatcher.groupEquals("DEFAULT");
        }
        Collection<String> pausedGroups = this.resources.getJobStore().pauseJobs(groupMatcher);
        this.notifySchedulerThread(0L);
        for (String pausedGroup : pausedGroups) {
            this.notifySchedulerListenersPausedJobs(pausedGroup);
        }
    }

    @Override
    public void resumeTrigger(TriggerKey triggerKey) throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().resumeTrigger(triggerKey);
        this.notifySchedulerThread(0L);
        this.notifySchedulerListenersResumedTrigger(triggerKey);
    }

    @Override
    public void resumeTriggers(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        this.validateState();
        if (matcher == null) {
            matcher = GroupMatcher.groupEquals("DEFAULT");
        }
        Collection<String> pausedGroups = this.resources.getJobStore().resumeTriggers(matcher);
        this.notifySchedulerThread(0L);
        for (String pausedGroup : pausedGroups) {
            this.notifySchedulerListenersResumedTriggers(pausedGroup);
        }
    }

    @Override
    public Set<String> getPausedTriggerGroups() throws SchedulerException {
        return this.resources.getJobStore().getPausedTriggerGroups();
    }

    @Override
    public void resumeJob(JobKey jobKey) throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().resumeJob(jobKey);
        this.notifySchedulerThread(0L);
        this.notifySchedulerListenersResumedJob(jobKey);
    }

    @Override
    public void resumeJobs(GroupMatcher<JobKey> matcher) throws SchedulerException {
        this.validateState();
        if (matcher == null) {
            matcher = GroupMatcher.groupEquals("DEFAULT");
        }
        Collection<String> resumedGroups = this.resources.getJobStore().resumeJobs(matcher);
        this.notifySchedulerThread(0L);
        for (String pausedGroup : resumedGroups) {
            this.notifySchedulerListenersResumedJobs(pausedGroup);
        }
    }

    @Override
    public void pauseAll() throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().pauseAll();
        this.notifySchedulerThread(0L);
        this.notifySchedulerListenersPausedTriggers(null);
    }

    @Override
    public void resumeAll() throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().resumeAll();
        this.notifySchedulerThread(0L);
        this.notifySchedulerListenersResumedTrigger(null);
    }

    @Override
    public List<String> getJobGroupNames() throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().getJobGroupNames();
    }

    @Override
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> matcher) throws SchedulerException {
        this.validateState();
        if (matcher == null) {
            matcher = GroupMatcher.groupEquals("DEFAULT");
        }
        return this.resources.getJobStore().getJobKeys(matcher);
    }

    @Override
    public List<? extends Trigger> getTriggersOfJob(JobKey jobKey) throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().getTriggersForJob(jobKey);
    }

    @Override
    public List<String> getTriggerGroupNames() throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().getTriggerGroupNames();
    }

    @Override
    public Set<TriggerKey> getTriggerKeys(GroupMatcher<TriggerKey> matcher) throws SchedulerException {
        this.validateState();
        if (matcher == null) {
            matcher = GroupMatcher.groupEquals("DEFAULT");
        }
        return this.resources.getJobStore().getTriggerKeys(matcher);
    }

    @Override
    public JobDetail getJobDetail(JobKey jobKey) throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().retrieveJob(jobKey);
    }

    @Override
    public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().retrieveTrigger(triggerKey);
    }

    @Override
    public boolean checkExists(JobKey jobKey) throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().checkExists(jobKey);
    }

    @Override
    public boolean checkExists(TriggerKey triggerKey) throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().checkExists(triggerKey);
    }

    @Override
    public void clear() throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().clearAllSchedulingData();
        this.notifySchedulerListenersUnscheduled(null);
    }

    @Override
    public Trigger.TriggerState getTriggerState(TriggerKey triggerKey) throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().getTriggerState(triggerKey);
    }

    @Override
    public void resetTriggerFromErrorState(TriggerKey triggerKey) throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().resetTriggerFromErrorState(triggerKey);
    }

    @Override
    public void addCalendar(String calName, Calendar calendar, boolean replace, boolean updateTriggers) throws SchedulerException {
        this.validateState();
        this.resources.getJobStore().storeCalendar(calName, calendar, replace, updateTriggers);
    }

    @Override
    public boolean deleteCalendar(String calName) throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().removeCalendar(calName);
    }

    @Override
    public Calendar getCalendar(String calName) throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().retrieveCalendar(calName);
    }

    @Override
    public List<String> getCalendarNames() throws SchedulerException {
        this.validateState();
        return this.resources.getJobStore().getCalendarNames();
    }

    public ListenerManager getListenerManager() {
        return this.listenerManager;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addInternalJobListener(JobListener jobListener) {
        if (jobListener.getName() == null || jobListener.getName().length() == 0) {
            throw new IllegalArgumentException("JobListener name cannot be empty.");
        }
        HashMap<String, JobListener> hashMap = this.internalJobListeners;
        synchronized (hashMap) {
            this.internalJobListeners.put(jobListener.getName(), jobListener);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removeInternalJobListener(String name) {
        HashMap<String, JobListener> hashMap = this.internalJobListeners;
        synchronized (hashMap) {
            return this.internalJobListeners.remove(name) != null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<JobListener> getInternalJobListeners() {
        HashMap<String, JobListener> hashMap = this.internalJobListeners;
        synchronized (hashMap) {
            return Collections.unmodifiableList(new LinkedList<JobListener>(this.internalJobListeners.values()));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public JobListener getInternalJobListener(String name) {
        HashMap<String, JobListener> hashMap = this.internalJobListeners;
        synchronized (hashMap) {
            return this.internalJobListeners.get(name);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addInternalTriggerListener(TriggerListener triggerListener) {
        if (triggerListener.getName() == null || triggerListener.getName().length() == 0) {
            throw new IllegalArgumentException("TriggerListener name cannot be empty.");
        }
        HashMap<String, TriggerListener> hashMap = this.internalTriggerListeners;
        synchronized (hashMap) {
            this.internalTriggerListeners.put(triggerListener.getName(), triggerListener);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removeinternalTriggerListener(String name) {
        HashMap<String, TriggerListener> hashMap = this.internalTriggerListeners;
        synchronized (hashMap) {
            return this.internalTriggerListeners.remove(name) != null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<TriggerListener> getInternalTriggerListeners() {
        HashMap<String, TriggerListener> hashMap = this.internalTriggerListeners;
        synchronized (hashMap) {
            return Collections.unmodifiableList(new LinkedList<TriggerListener>(this.internalTriggerListeners.values()));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TriggerListener getInternalTriggerListener(String name) {
        HashMap<String, TriggerListener> hashMap = this.internalTriggerListeners;
        synchronized (hashMap) {
            return this.internalTriggerListeners.get(name);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addInternalSchedulerListener(SchedulerListener schedulerListener) {
        ArrayList<SchedulerListener> arrayList = this.internalSchedulerListeners;
        synchronized (arrayList) {
            this.internalSchedulerListeners.add(schedulerListener);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removeInternalSchedulerListener(SchedulerListener schedulerListener) {
        ArrayList<SchedulerListener> arrayList = this.internalSchedulerListeners;
        synchronized (arrayList) {
            return this.internalSchedulerListeners.remove(schedulerListener);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<SchedulerListener> getInternalSchedulerListeners() {
        ArrayList<SchedulerListener> arrayList = this.internalSchedulerListeners;
        synchronized (arrayList) {
            return Collections.unmodifiableList(new ArrayList<SchedulerListener>(this.internalSchedulerListeners));
        }
    }

    protected void notifyJobStoreJobComplete(OperableTrigger trigger, JobDetail detail, Trigger.CompletedExecutionInstruction instCode) {
        this.resources.getJobStore().triggeredJobComplete(trigger, detail, instCode);
    }

    protected void notifyJobStoreJobVetoed(OperableTrigger trigger, JobDetail detail, Trigger.CompletedExecutionInstruction instCode) {
        this.resources.getJobStore().triggeredJobComplete(trigger, detail, instCode);
    }

    protected void notifySchedulerThread(long candidateNewNextFireTime) {
        if (this.isSignalOnSchedulingChange()) {
            this.signaler.signalSchedulingChange(candidateNewNextFireTime);
        }
    }

    private List<TriggerListener> buildTriggerListenerList() throws SchedulerException {
        LinkedList<TriggerListener> allListeners = new LinkedList<TriggerListener>();
        allListeners.addAll(this.getListenerManager().getTriggerListeners());
        allListeners.addAll(this.getInternalTriggerListeners());
        return allListeners;
    }

    private List<JobListener> buildJobListenerList() throws SchedulerException {
        LinkedList<JobListener> allListeners = new LinkedList<JobListener>();
        allListeners.addAll(this.getListenerManager().getJobListeners());
        allListeners.addAll(this.getInternalJobListeners());
        return allListeners;
    }

    private List<SchedulerListener> buildSchedulerListenerList() {
        LinkedList<SchedulerListener> allListeners = new LinkedList<SchedulerListener>();
        allListeners.addAll(this.getListenerManager().getSchedulerListeners());
        allListeners.addAll(this.getInternalSchedulerListeners());
        return allListeners;
    }

    private boolean matchJobListener(JobListener listener, JobKey key) {
        List<Matcher<JobKey>> matchers = this.getListenerManager().getJobListenerMatchers(listener.getName());
        if (matchers == null) {
            return true;
        }
        for (Matcher<JobKey> matcher : matchers) {
            if (!matcher.isMatch(key)) continue;
            return true;
        }
        return false;
    }

    private boolean matchTriggerListener(TriggerListener listener, TriggerKey key) {
        List<Matcher<TriggerKey>> matchers = this.getListenerManager().getTriggerListenerMatchers(listener.getName());
        if (matchers == null) {
            return true;
        }
        for (Matcher<TriggerKey> matcher : matchers) {
            if (!matcher.isMatch(key)) continue;
            return true;
        }
        return false;
    }

    public boolean notifyTriggerListenersFired(JobExecutionContext jec) throws SchedulerException {
        boolean vetoedExecution = false;
        List<TriggerListener> triggerListeners = this.buildTriggerListenerList();
        for (TriggerListener tl : triggerListeners) {
            try {
                if (!this.matchTriggerListener(tl, jec.getTrigger().getKey())) continue;
                tl.triggerFired(jec.getTrigger(), jec);
                if (!tl.vetoJobExecution(jec.getTrigger(), jec)) continue;
                vetoedExecution = true;
            }
            catch (Exception e) {
                SchedulerException se = new SchedulerException("TriggerListener '" + tl.getName() + "' threw exception: " + e.getMessage(), e);
                throw se;
            }
        }
        return vetoedExecution;
    }

    public void notifyTriggerListenersMisfired(Trigger trigger) throws SchedulerException {
        List<TriggerListener> triggerListeners = this.buildTriggerListenerList();
        for (TriggerListener tl : triggerListeners) {
            try {
                if (!this.matchTriggerListener(tl, trigger.getKey())) continue;
                tl.triggerMisfired(trigger);
            }
            catch (Exception e) {
                SchedulerException se = new SchedulerException("TriggerListener '" + tl.getName() + "' threw exception: " + e.getMessage(), e);
                throw se;
            }
        }
    }

    public void notifyTriggerListenersComplete(JobExecutionContext jec, Trigger.CompletedExecutionInstruction instCode) throws SchedulerException {
        List<TriggerListener> triggerListeners = this.buildTriggerListenerList();
        for (TriggerListener tl : triggerListeners) {
            try {
                if (!this.matchTriggerListener(tl, jec.getTrigger().getKey())) continue;
                tl.triggerComplete(jec.getTrigger(), jec, instCode);
            }
            catch (Exception e) {
                SchedulerException se = new SchedulerException("TriggerListener '" + tl.getName() + "' threw exception: " + e.getMessage(), e);
                throw se;
            }
        }
    }

    public void notifyJobListenersToBeExecuted(JobExecutionContext jec) throws SchedulerException {
        List<JobListener> jobListeners = this.buildJobListenerList();
        for (JobListener jl : jobListeners) {
            try {
                if (!this.matchJobListener(jl, jec.getJobDetail().getKey())) continue;
                jl.jobToBeExecuted(jec);
            }
            catch (Exception e) {
                SchedulerException se = new SchedulerException("JobListener '" + jl.getName() + "' threw exception: " + e.getMessage(), e);
                throw se;
            }
        }
    }

    public void notifyJobListenersWasVetoed(JobExecutionContext jec) throws SchedulerException {
        List<JobListener> jobListeners = this.buildJobListenerList();
        for (JobListener jl : jobListeners) {
            try {
                if (!this.matchJobListener(jl, jec.getJobDetail().getKey())) continue;
                jl.jobExecutionVetoed(jec);
            }
            catch (Exception e) {
                SchedulerException se = new SchedulerException("JobListener '" + jl.getName() + "' threw exception: " + e.getMessage(), e);
                throw se;
            }
        }
    }

    public void notifyJobListenersWasExecuted(JobExecutionContext jec, JobExecutionException je) throws SchedulerException {
        List<JobListener> jobListeners = this.buildJobListenerList();
        for (JobListener jl : jobListeners) {
            try {
                if (!this.matchJobListener(jl, jec.getJobDetail().getKey())) continue;
                jl.jobWasExecuted(jec, je);
            }
            catch (Exception e) {
                SchedulerException se = new SchedulerException("JobListener '" + jl.getName() + "' threw exception: " + e.getMessage(), e);
                throw se;
            }
        }
    }

    public void notifySchedulerListenersError(String msg, SchedulerException se) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.schedulerError(msg, se);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of error: ", (Throwable)e);
                this.getLog().error("  Original error (for notification) was: " + msg, (Throwable)se);
            }
        }
    }

    public void notifySchedulerListenersSchduled(Trigger trigger) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.jobScheduled(trigger);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of scheduled job.  Triger=" + trigger.getKey(), (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersUnscheduled(TriggerKey triggerKey) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                if (triggerKey == null) {
                    sl.schedulingDataCleared();
                    continue;
                }
                sl.jobUnscheduled(triggerKey);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of unscheduled job.  Triger=" + (triggerKey == null ? "ALL DATA" : triggerKey), (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersFinalized(Trigger trigger) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.triggerFinalized(trigger);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of finalized trigger.  Triger=" + trigger.getKey(), (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersPausedTrigger(TriggerKey triggerKey) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.triggerPaused(triggerKey);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of paused trigger: " + triggerKey, (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersPausedTriggers(String group) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.triggersPaused(group);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of paused trigger group." + group, (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersResumedTrigger(TriggerKey key) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.triggerResumed(key);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of resumed trigger: " + key, (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersResumedTriggers(String group) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.triggersResumed(group);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of resumed group: " + group, (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersPausedJob(JobKey key) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.jobPaused(key);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of paused job: " + key, (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersPausedJobs(String group) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.jobsPaused(group);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of paused job group: " + group, (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersResumedJob(JobKey key) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.jobResumed(key);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of resumed job: " + key, (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersResumedJobs(String group) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.jobsResumed(group);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of resumed job group: " + group, (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersInStandbyMode() {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.schedulerInStandbyMode();
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of inStandByMode.", (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersStarted() {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.schedulerStarted();
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of startup.", (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersStarting() {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.schedulerStarting();
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of startup.", (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersShutdown() {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.schedulerShutdown();
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of shutdown.", (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersShuttingdown() {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.schedulerShuttingdown();
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of shutdown.", (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersJobAdded(JobDetail jobDetail) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.jobAdded(jobDetail);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of JobAdded.", (Throwable)e);
            }
        }
    }

    public void notifySchedulerListenersJobDeleted(JobKey jobKey) {
        List<SchedulerListener> schedListeners = this.buildSchedulerListenerList();
        for (SchedulerListener sl : schedListeners) {
            try {
                sl.jobDeleted(jobKey);
            }
            catch (Exception e) {
                this.getLog().error("Error while notifying SchedulerListener of JobAdded.", (Throwable)e);
            }
        }
    }

    public void setJobFactory(JobFactory factory) throws SchedulerException {
        if (factory == null) {
            throw new IllegalArgumentException("JobFactory cannot be set to null!");
        }
        this.getLog().info("JobFactory set to: " + factory);
        this.jobFactory = factory;
    }

    public JobFactory getJobFactory() {
        return this.jobFactory;
    }

    @Override
    public boolean interrupt(JobKey jobKey) throws UnableToInterruptJobException {
        List<JobExecutionContext> jobs = this.getCurrentlyExecutingJobs();
        JobDetail jobDetail = null;
        Job job = null;
        boolean interrupted = false;
        for (JobExecutionContext jec : jobs) {
            jobDetail = jec.getJobDetail();
            if (!jobKey.equals(jobDetail.getKey())) continue;
            job = jec.getJobInstance();
            if (job instanceof InterruptableJob) {
                ((InterruptableJob)job).interrupt();
                interrupted = true;
                continue;
            }
            throw new UnableToInterruptJobException("Job " + jobDetail.getKey() + " can not be interrupted, since it does not implement " + InterruptableJob.class.getName());
        }
        return interrupted;
    }

    @Override
    public boolean interrupt(String fireInstanceId) throws UnableToInterruptJobException {
        List<JobExecutionContext> jobs = this.getCurrentlyExecutingJobs();
        Job job = null;
        for (JobExecutionContext jec : jobs) {
            if (!jec.getFireInstanceId().equals(fireInstanceId)) continue;
            job = jec.getJobInstance();
            if (job instanceof InterruptableJob) {
                ((InterruptableJob)job).interrupt();
                return true;
            }
            throw new UnableToInterruptJobException("Job " + jec.getJobDetail().getKey() + " can not be interrupted, since it does not implement " + InterruptableJob.class.getName());
        }
        return false;
    }

    private void shutdownPlugins() {
        for (SchedulerPlugin plugin : this.resources.getSchedulerPlugins()) {
            plugin.shutdown();
        }
    }

    private void startPlugins() {
        for (SchedulerPlugin plugin : this.resources.getSchedulerPlugins()) {
            plugin.start();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        Properties props = new Properties();
        InputStream is = null;
        try {
            is = QuartzScheduler.class.getResourceAsStream("quartz-build.properties");
            if (is != null) {
                props.load(is);
                String version = props.getProperty("version");
                if (version != null) {
                    String[] versionComponents = version.split("\\.");
                    VERSION_MAJOR = versionComponents[0];
                    VERSION_MINOR = versionComponents[1];
                    VERSION_ITERATION = versionComponents.length > 2 ? versionComponents[2] : "0";
                } else {
                    LoggerFactory.getLogger(QuartzScheduler.class).error("Can't parse Quartz version from quartz-build.properties");
                }
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(QuartzScheduler.class).error("Error loading version info from quartz-build.properties.", (Throwable)e);
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (Exception exception) {}
            }
        }
    }
}


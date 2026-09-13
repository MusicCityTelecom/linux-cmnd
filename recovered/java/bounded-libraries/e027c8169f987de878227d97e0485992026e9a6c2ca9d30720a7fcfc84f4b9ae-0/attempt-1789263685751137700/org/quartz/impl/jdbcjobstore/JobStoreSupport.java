/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.impl.jdbcjobstore;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.quartz.Calendar;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.SchedulerConfigException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.DefaultThreadExecutor;
import org.quartz.impl.jdbcjobstore.AttributeRestoringConnectionInvocationHandler;
import org.quartz.impl.jdbcjobstore.Constants;
import org.quartz.impl.jdbcjobstore.DriverDelegate;
import org.quartz.impl.jdbcjobstore.FiredTriggerRecord;
import org.quartz.impl.jdbcjobstore.InvalidConfigurationException;
import org.quartz.impl.jdbcjobstore.LockException;
import org.quartz.impl.jdbcjobstore.MSSQLDelegate;
import org.quartz.impl.jdbcjobstore.NoSuchDelegateException;
import org.quartz.impl.jdbcjobstore.SchedulerStateRecord;
import org.quartz.impl.jdbcjobstore.Semaphore;
import org.quartz.impl.jdbcjobstore.SimpleSemaphore;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;
import org.quartz.impl.jdbcjobstore.StdRowLockSemaphore;
import org.quartz.impl.jdbcjobstore.TriggerStatus;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.StringMatcher;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.ClassLoadHelper;
import org.quartz.spi.JobStore;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.SchedulerSignaler;
import org.quartz.spi.ThreadExecutor;
import org.quartz.spi.TriggerFiredBundle;
import org.quartz.spi.TriggerFiredResult;
import org.quartz.utils.DBConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JobStoreSupport
implements JobStore,
Constants {
    protected static final String LOCK_TRIGGER_ACCESS = "TRIGGER_ACCESS";
    protected static final String LOCK_STATE_ACCESS = "STATE_ACCESS";
    protected String dsName;
    protected String tablePrefix = "QRTZ_";
    protected boolean useProperties = false;
    protected String instanceId;
    protected String instanceName;
    protected String delegateClassName;
    protected String delegateInitString;
    protected Class<? extends DriverDelegate> delegateClass = StdJDBCDelegate.class;
    protected HashMap<String, Calendar> calendarCache = new HashMap();
    private DriverDelegate delegate;
    private long misfireThreshold = 60000L;
    private boolean dontSetAutoCommitFalse = false;
    private boolean isClustered = false;
    private boolean useDBLocks = false;
    private boolean lockOnInsert = true;
    private Semaphore lockHandler = null;
    private String selectWithLockSQL = null;
    private long clusterCheckinInterval = 7500L;
    private ClusterManager clusterManagementThread = null;
    private MisfireHandler misfireHandler = null;
    private ClassLoadHelper classLoadHelper;
    private SchedulerSignaler schedSignaler;
    protected int maxToRecoverAtATime = 20;
    private boolean setTxIsolationLevelSequential = false;
    private boolean acquireTriggersWithinLock = false;
    private long dbRetryInterval = 15000L;
    private boolean makeThreadsDaemons = false;
    private boolean threadsInheritInitializersClassLoadContext = false;
    private ClassLoader initializersLoader = null;
    private boolean doubleCheckLockMisfireHandler = true;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ThreadExecutor threadExecutor = new DefaultThreadExecutor();
    private volatile boolean schedulerRunning = false;
    private volatile boolean shutdown = false;
    private static long ftrCtr = System.currentTimeMillis();
    protected ThreadLocal<Long> sigChangeForTxCompletion = new ThreadLocal();
    protected boolean firstCheckIn = true;
    protected long lastCheckin = System.currentTimeMillis();

    public void setDataSource(String dsName) {
        this.dsName = dsName;
    }

    public String getDataSource() {
        return this.dsName;
    }

    public void setTablePrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        this.tablePrefix = prefix;
    }

    public String getTablePrefix() {
        return this.tablePrefix;
    }

    public void setUseProperties(String useProp) {
        if (useProp == null) {
            useProp = "false";
        }
        this.useProperties = Boolean.valueOf(useProp);
    }

    public boolean canUseProperties() {
        return this.useProperties;
    }

    @Override
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    @Override
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    @Override
    public void setThreadPoolSize(int poolSize) {
    }

    public void setThreadExecutor(ThreadExecutor threadExecutor) {
        this.threadExecutor = threadExecutor;
    }

    public ThreadExecutor getThreadExecutor() {
        return this.threadExecutor;
    }

    public String getInstanceName() {
        return this.instanceName;
    }

    @Override
    public long getEstimatedTimeToReleaseAndAcquireTrigger() {
        return 70L;
    }

    public void setIsClustered(boolean isClustered) {
        this.isClustered = isClustered;
    }

    @Override
    public boolean isClustered() {
        return this.isClustered;
    }

    public long getClusterCheckinInterval() {
        return this.clusterCheckinInterval;
    }

    public void setClusterCheckinInterval(long l) {
        this.clusterCheckinInterval = l;
    }

    public int getMaxMisfiresToHandleAtATime() {
        return this.maxToRecoverAtATime;
    }

    public void setMaxMisfiresToHandleAtATime(int maxToRecoverAtATime) {
        this.maxToRecoverAtATime = maxToRecoverAtATime;
    }

    public long getDbRetryInterval() {
        return this.dbRetryInterval;
    }

    public void setDbRetryInterval(long dbRetryInterval) {
        this.dbRetryInterval = dbRetryInterval;
    }

    public void setUseDBLocks(boolean useDBLocks) {
        this.useDBLocks = useDBLocks;
    }

    public boolean getUseDBLocks() {
        return this.useDBLocks;
    }

    public boolean isLockOnInsert() {
        return this.lockOnInsert;
    }

    public void setLockOnInsert(boolean lockOnInsert) {
        this.lockOnInsert = lockOnInsert;
    }

    public long getMisfireThreshold() {
        return this.misfireThreshold;
    }

    public void setMisfireThreshold(long misfireThreshold) {
        if (misfireThreshold < 1L) {
            throw new IllegalArgumentException("Misfirethreshold must be larger than 0");
        }
        this.misfireThreshold = misfireThreshold;
    }

    public boolean isDontSetAutoCommitFalse() {
        return this.dontSetAutoCommitFalse;
    }

    public void setDontSetAutoCommitFalse(boolean b) {
        this.dontSetAutoCommitFalse = b;
    }

    public boolean isTxIsolationLevelSerializable() {
        return this.setTxIsolationLevelSequential;
    }

    public void setTxIsolationLevelSerializable(boolean b) {
        this.setTxIsolationLevelSequential = b;
    }

    public boolean isAcquireTriggersWithinLock() {
        return this.acquireTriggersWithinLock;
    }

    public void setAcquireTriggersWithinLock(boolean acquireTriggersWithinLock) {
        this.acquireTriggersWithinLock = acquireTriggersWithinLock;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setDriverDelegateClass(String delegateClassName) throws InvalidConfigurationException {
        JobStoreSupport jobStoreSupport = this;
        synchronized (jobStoreSupport) {
            this.delegateClassName = delegateClassName;
        }
    }

    public String getDriverDelegateClass() {
        return this.delegateClassName;
    }

    public void setDriverDelegateInitString(String delegateInitString) throws InvalidConfigurationException {
        this.delegateInitString = delegateInitString;
    }

    public String getDriverDelegateInitString() {
        return this.delegateInitString;
    }

    public String getSelectWithLockSQL() {
        return this.selectWithLockSQL;
    }

    public void setSelectWithLockSQL(String string) {
        this.selectWithLockSQL = string;
    }

    protected ClassLoadHelper getClassLoadHelper() {
        return this.classLoadHelper;
    }

    public boolean getMakeThreadsDaemons() {
        return this.makeThreadsDaemons;
    }

    public void setMakeThreadsDaemons(boolean makeThreadsDaemons) {
        this.makeThreadsDaemons = makeThreadsDaemons;
    }

    public boolean isThreadsInheritInitializersClassLoadContext() {
        return this.threadsInheritInitializersClassLoadContext;
    }

    public void setThreadsInheritInitializersClassLoadContext(boolean threadsInheritInitializersClassLoadContext) {
        this.threadsInheritInitializersClassLoadContext = threadsInheritInitializersClassLoadContext;
    }

    public boolean getDoubleCheckLockMisfireHandler() {
        return this.doubleCheckLockMisfireHandler;
    }

    public void setDoubleCheckLockMisfireHandler(boolean doubleCheckLockMisfireHandler) {
        this.doubleCheckLockMisfireHandler = doubleCheckLockMisfireHandler;
    }

    @Override
    public long getAcquireRetryDelay(int failureCount) {
        return this.dbRetryInterval;
    }

    protected Logger getLog() {
        return this.log;
    }

    @Override
    public void initialize(ClassLoadHelper loadHelper, SchedulerSignaler signaler) throws SchedulerConfigException {
        if (this.dsName == null) {
            throw new SchedulerConfigException("DataSource name not set.");
        }
        this.classLoadHelper = loadHelper;
        if (this.isThreadsInheritInitializersClassLoadContext()) {
            this.log.info("JDBCJobStore threads will inherit ContextClassLoader of thread: " + Thread.currentThread().getName());
            this.initializersLoader = Thread.currentThread().getContextClassLoader();
        }
        this.schedSignaler = signaler;
        if (this.getLockHandler() == null) {
            if (this.isClustered()) {
                this.setUseDBLocks(true);
            }
            if (this.getUseDBLocks()) {
                if (this.getDriverDelegateClass() != null && this.getDriverDelegateClass().equals(MSSQLDelegate.class.getName()) && this.getSelectWithLockSQL() == null) {
                    String msSqlDflt = "SELECT * FROM {0}LOCKS WITH (UPDLOCK,ROWLOCK) WHERE SCHED_NAME = {1} AND LOCK_NAME = ?";
                    this.getLog().info("Detected usage of MSSQLDelegate class - defaulting 'selectWithLockSQL' to '" + msSqlDflt + "'.");
                    this.setSelectWithLockSQL(msSqlDflt);
                }
                this.getLog().info("Using db table-based data access locking (synchronization).");
                this.setLockHandler(new StdRowLockSemaphore(this.getTablePrefix(), this.getInstanceName(), this.getSelectWithLockSQL()));
            } else {
                this.getLog().info("Using thread monitor-based data access locking (synchronization).");
                this.setLockHandler(new SimpleSemaphore());
            }
        }
    }

    @Override
    public void schedulerStarted() throws SchedulerException {
        if (this.isClustered()) {
            this.clusterManagementThread = new ClusterManager();
            if (this.initializersLoader != null) {
                this.clusterManagementThread.setContextClassLoader(this.initializersLoader);
            }
            this.clusterManagementThread.initialize();
        } else {
            try {
                this.recoverJobs();
            }
            catch (SchedulerException se) {
                throw new SchedulerConfigException("Failure occured during job recovery.", se);
            }
        }
        this.misfireHandler = new MisfireHandler();
        if (this.initializersLoader != null) {
            this.misfireHandler.setContextClassLoader(this.initializersLoader);
        }
        this.misfireHandler.initialize();
        this.schedulerRunning = true;
        this.getLog().debug("JobStore background threads started (as scheduler was started).");
    }

    @Override
    public void schedulerPaused() {
        this.schedulerRunning = false;
    }

    @Override
    public void schedulerResumed() {
        this.schedulerRunning = true;
    }

    @Override
    public void shutdown() {
        this.shutdown = true;
        if (this.misfireHandler != null) {
            this.misfireHandler.shutdown();
            try {
                this.misfireHandler.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        if (this.clusterManagementThread != null) {
            this.clusterManagementThread.shutdown();
            try {
                this.clusterManagementThread.join();
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        try {
            DBConnectionManager.getInstance().shutdown(this.getDataSource());
        }
        catch (SQLException sqle) {
            this.getLog().warn("Database connection shutdown unsuccessful.", (Throwable)sqle);
        }
        this.getLog().debug("JobStore background threads shutdown.");
    }

    @Override
    public boolean supportsPersistence() {
        return true;
    }

    protected abstract Connection getNonManagedTXConnection() throws JobPersistenceException;

    protected Connection getAttributeRestoringConnection(Connection conn) {
        return (Connection)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{Connection.class}, (InvocationHandler)new AttributeRestoringConnectionInvocationHandler(conn));
    }

    protected Connection getConnection() throws JobPersistenceException {
        Connection conn;
        try {
            conn = DBConnectionManager.getInstance().getConnection(this.getDataSource());
        }
        catch (SQLException sqle) {
            throw new JobPersistenceException("Failed to obtain DB connection from data source '" + this.getDataSource() + "': " + sqle.toString(), sqle);
        }
        catch (Throwable e) {
            throw new JobPersistenceException("Failed to obtain DB connection from data source '" + this.getDataSource() + "': " + e.toString(), e);
        }
        if (conn == null) {
            throw new JobPersistenceException("Could not get connection from DataSource '" + this.getDataSource() + "'");
        }
        conn = this.getAttributeRestoringConnection(conn);
        try {
            if (!this.isDontSetAutoCommitFalse()) {
                conn.setAutoCommit(false);
            }
            if (this.isTxIsolationLevelSerializable()) {
                conn.setTransactionIsolation(8);
            }
        }
        catch (SQLException sqle) {
            this.getLog().warn("Failed to override connection auto commit/transaction isolation.", (Throwable)sqle);
        }
        catch (Throwable e) {
            try {
                conn.close();
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            throw new JobPersistenceException("Failure setting up connection.", e);
        }
        return conn;
    }

    protected void releaseLock(String lockName, boolean doIt) {
        if (doIt) {
            try {
                this.getLockHandler().releaseLock(lockName);
            }
            catch (LockException le) {
                this.getLog().error("Error returning lock: " + le.getMessage(), (Throwable)le);
            }
        }
    }

    protected void recoverJobs() throws JobPersistenceException {
        this.executeInNonManagedTXLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.recoverJobs(conn);
            }
        }, null);
    }

    protected void recoverJobs(Connection conn) throws JobPersistenceException {
        try {
            int rows = this.getDelegate().updateTriggerStatesFromOtherStates(conn, "WAITING", "ACQUIRED", "BLOCKED");
            this.getLog().info("Freed " + (rows += this.getDelegate().updateTriggerStatesFromOtherStates(conn, "PAUSED", "PAUSED_BLOCKED", "PAUSED_BLOCKED")) + " triggers from 'acquired' / 'blocked' state.");
            this.recoverMisfiredJobs(conn, true);
            List<OperableTrigger> recoveringJobTriggers = this.getDelegate().selectTriggersForRecoveringJobs(conn);
            this.getLog().info("Recovering " + recoveringJobTriggers.size() + " jobs that were in-progress at the time of the last shut-down.");
            for (OperableTrigger recoveringJobTrigger : recoveringJobTriggers) {
                if (!this.jobExists(conn, recoveringJobTrigger.getJobKey())) continue;
                recoveringJobTrigger.computeFirstFireTime(null);
                this.storeTrigger(conn, recoveringJobTrigger, null, false, "WAITING", false, true);
            }
            this.getLog().info("Recovery complete.");
            List<TriggerKey> cts = this.getDelegate().selectTriggersInState(conn, "COMPLETE");
            for (TriggerKey ct : cts) {
                this.removeTrigger(conn, ct);
            }
            this.getLog().info("Removed " + cts.size() + " 'complete' triggers.");
            int n = this.getDelegate().deleteFiredTriggers(conn);
            this.getLog().info("Removed " + n + " stale fired job entries.");
        }
        catch (JobPersistenceException e) {
            throw e;
        }
        catch (Exception e) {
            throw new JobPersistenceException("Couldn't recover jobs: " + e.getMessage(), e);
        }
    }

    protected long getMisfireTime() {
        long misfireTime = System.currentTimeMillis();
        if (this.getMisfireThreshold() > 0L) {
            misfireTime -= this.getMisfireThreshold();
        }
        return misfireTime > 0L ? misfireTime : 0L;
    }

    protected RecoverMisfiredJobsResult recoverMisfiredJobs(Connection conn, boolean recovering) throws JobPersistenceException, SQLException {
        int maxMisfiresToHandleAtATime = recovering ? -1 : this.getMaxMisfiresToHandleAtATime();
        LinkedList<TriggerKey> misfiredTriggers = new LinkedList<TriggerKey>();
        long earliestNewTime = Long.MAX_VALUE;
        boolean hasMoreMisfiredTriggers = this.getDelegate().hasMisfiredTriggersInState(conn, "WAITING", this.getMisfireTime(), maxMisfiresToHandleAtATime, misfiredTriggers);
        if (hasMoreMisfiredTriggers) {
            this.getLog().info("Handling the first " + misfiredTriggers.size() + " triggers that missed their scheduled fire-time.  " + "More misfired triggers remain to be processed.");
        } else if (misfiredTriggers.size() > 0) {
            this.getLog().info("Handling " + misfiredTriggers.size() + " trigger(s) that missed their scheduled fire-time.");
        } else {
            this.getLog().debug("Found 0 triggers that missed their scheduled fire-time.");
            return RecoverMisfiredJobsResult.NO_OP;
        }
        for (TriggerKey triggerKey : misfiredTriggers) {
            OperableTrigger trig = this.retrieveTrigger(conn, triggerKey);
            if (trig == null) continue;
            this.doUpdateOfMisfiredTrigger(conn, trig, false, "WAITING", recovering);
            if (trig.getNextFireTime() == null || trig.getNextFireTime().getTime() >= earliestNewTime) continue;
            earliestNewTime = trig.getNextFireTime().getTime();
        }
        return new RecoverMisfiredJobsResult(hasMoreMisfiredTriggers, misfiredTriggers.size(), earliestNewTime);
    }

    protected boolean updateMisfiredTrigger(Connection conn, TriggerKey triggerKey, String newStateIfNotComplete, boolean forceState) throws JobPersistenceException {
        try {
            OperableTrigger trig = this.retrieveTrigger(conn, triggerKey);
            long misfireTime = System.currentTimeMillis();
            if (this.getMisfireThreshold() > 0L) {
                misfireTime -= this.getMisfireThreshold();
            }
            if (trig.getNextFireTime().getTime() > misfireTime) {
                return false;
            }
            this.doUpdateOfMisfiredTrigger(conn, trig, forceState, newStateIfNotComplete, false);
            return true;
        }
        catch (Exception e) {
            throw new JobPersistenceException("Couldn't update misfired trigger '" + triggerKey + "': " + e.getMessage(), e);
        }
    }

    private void doUpdateOfMisfiredTrigger(Connection conn, OperableTrigger trig, boolean forceState, String newStateIfNotComplete, boolean recovering) throws JobPersistenceException {
        Calendar cal = null;
        if (trig.getCalendarName() != null) {
            cal = this.retrieveCalendar(conn, trig.getCalendarName());
        }
        this.schedSignaler.notifyTriggerListenersMisfired(trig);
        trig.updateAfterMisfire(cal);
        if (trig.getNextFireTime() == null) {
            this.storeTrigger(conn, trig, null, true, "COMPLETE", forceState, recovering);
            this.schedSignaler.notifySchedulerListenersFinalized(trig);
        } else {
            this.storeTrigger(conn, trig, null, true, newStateIfNotComplete, forceState, recovering);
        }
    }

    @Override
    public void storeJobAndTrigger(final JobDetail newJob, final OperableTrigger newTrigger) throws JobPersistenceException {
        this.executeInLock(this.isLockOnInsert() ? LOCK_TRIGGER_ACCESS : null, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.storeJob(conn, newJob, false);
                JobStoreSupport.this.storeTrigger(conn, newTrigger, newJob, false, "WAITING", false, false);
            }
        });
    }

    @Override
    public void storeJob(final JobDetail newJob, final boolean replaceExisting) throws JobPersistenceException {
        this.executeInLock(this.isLockOnInsert() || replaceExisting ? LOCK_TRIGGER_ACCESS : null, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.storeJob(conn, newJob, replaceExisting);
            }
        });
    }

    protected void storeJob(Connection conn, JobDetail newJob, boolean replaceExisting) throws JobPersistenceException {
        boolean existingJob = this.jobExists(conn, newJob.getKey());
        try {
            if (existingJob) {
                if (!replaceExisting) {
                    throw new ObjectAlreadyExistsException(newJob);
                }
                this.getDelegate().updateJobDetail(conn, newJob);
            } else {
                this.getDelegate().insertJobDetail(conn, newJob);
            }
        }
        catch (IOException e) {
            throw new JobPersistenceException("Couldn't store job: " + e.getMessage(), e);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't store job: " + e.getMessage(), e);
        }
    }

    protected boolean jobExists(Connection conn, JobKey jobKey) throws JobPersistenceException {
        try {
            return this.getDelegate().jobExists(conn, jobKey);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't determine job existence (" + jobKey + "): " + e.getMessage(), e);
        }
    }

    @Override
    public void storeTrigger(final OperableTrigger newTrigger, final boolean replaceExisting) throws JobPersistenceException {
        this.executeInLock(this.isLockOnInsert() || replaceExisting ? LOCK_TRIGGER_ACCESS : null, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.storeTrigger(conn, newTrigger, null, replaceExisting, "WAITING", false, false);
            }
        });
    }

    protected void storeTrigger(Connection conn, OperableTrigger newTrigger, JobDetail job, boolean replaceExisting, String state, boolean forceState, boolean recovering) throws JobPersistenceException {
        boolean existingTrigger = this.triggerExists(conn, newTrigger.getKey());
        if (existingTrigger && !replaceExisting) {
            throw new ObjectAlreadyExistsException(newTrigger);
        }
        try {
            if (!forceState) {
                boolean shouldBepaused = this.getDelegate().isTriggerGroupPaused(conn, newTrigger.getKey().getGroup());
                if (!shouldBepaused && (shouldBepaused = this.getDelegate().isTriggerGroupPaused(conn, "_$_ALL_GROUPS_PAUSED_$_"))) {
                    this.getDelegate().insertPausedTriggerGroup(conn, newTrigger.getKey().getGroup());
                }
                if (shouldBepaused && (state.equals("WAITING") || state.equals("ACQUIRED"))) {
                    state = "PAUSED";
                }
            }
            if (job == null) {
                job = this.retrieveJob(conn, newTrigger.getJobKey());
            }
            if (job == null) {
                throw new JobPersistenceException("The job (" + newTrigger.getJobKey() + ") referenced by the trigger does not exist.");
            }
            if (job.isConcurrentExectionDisallowed() && !recovering) {
                state = this.checkBlockedState(conn, job.getKey(), state);
            }
            if (existingTrigger) {
                this.getDelegate().updateTrigger(conn, newTrigger, state, job);
            } else {
                this.getDelegate().insertTrigger(conn, newTrigger, state, job);
            }
        }
        catch (Exception e) {
            throw new JobPersistenceException("Couldn't store trigger '" + newTrigger.getKey() + "' for '" + newTrigger.getJobKey() + "' job:" + e.getMessage(), e);
        }
    }

    protected boolean triggerExists(Connection conn, TriggerKey key) throws JobPersistenceException {
        try {
            return this.getDelegate().triggerExists(conn, key);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't determine trigger existence (" + key + "): " + e.getMessage(), e);
        }
    }

    @Override
    public boolean removeJob(final JobKey jobKey) throws JobPersistenceException {
        return (Boolean)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.removeJob(conn, jobKey) ? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }

    protected boolean removeJob(Connection conn, JobKey jobKey) throws JobPersistenceException {
        try {
            List<TriggerKey> jobTriggers = this.getDelegate().selectTriggerKeysForJob(conn, jobKey);
            for (TriggerKey jobTrigger : jobTriggers) {
                this.deleteTriggerAndChildren(conn, jobTrigger);
            }
            return this.deleteJobAndChildren(conn, jobKey);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't remove job: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean removeJobs(final List<JobKey> jobKeys) throws JobPersistenceException {
        return (Boolean)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                boolean allFound = true;
                for (JobKey jobKey : jobKeys) {
                    allFound = JobStoreSupport.this.removeJob(conn, jobKey) && allFound;
                }
                return allFound ? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }

    @Override
    public boolean removeTriggers(final List<TriggerKey> triggerKeys) throws JobPersistenceException {
        return (Boolean)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                boolean allFound = true;
                for (TriggerKey triggerKey : triggerKeys) {
                    allFound = JobStoreSupport.this.removeTrigger(conn, triggerKey) && allFound;
                }
                return allFound ? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }

    @Override
    public void storeJobsAndTriggers(final Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, final boolean replace) throws JobPersistenceException {
        this.executeInLock(this.isLockOnInsert() || replace ? LOCK_TRIGGER_ACCESS : null, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                for (JobDetail job : triggersAndJobs.keySet()) {
                    JobStoreSupport.this.storeJob(conn, job, replace);
                    for (Trigger trigger : (Set)triggersAndJobs.get(job)) {
                        JobStoreSupport.this.storeTrigger(conn, (OperableTrigger)trigger, job, replace, "WAITING", false, false);
                    }
                }
            }
        });
    }

    private boolean deleteJobAndChildren(Connection conn, JobKey key) throws NoSuchDelegateException, SQLException {
        return this.getDelegate().deleteJobDetail(conn, key) > 0;
    }

    private boolean deleteTriggerAndChildren(Connection conn, TriggerKey key) throws SQLException, NoSuchDelegateException {
        return this.getDelegate().deleteTrigger(conn, key) > 0;
    }

    @Override
    public JobDetail retrieveJob(final JobKey jobKey) throws JobPersistenceException {
        return (JobDetail)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.retrieveJob(conn, jobKey);
            }
        });
    }

    protected JobDetail retrieveJob(Connection conn, JobKey key) throws JobPersistenceException {
        try {
            return this.getDelegate().selectJobDetail(conn, key, this.getClassLoadHelper());
        }
        catch (ClassNotFoundException e) {
            throw new JobPersistenceException("Couldn't retrieve job because a required class was not found: " + e.getMessage(), e);
        }
        catch (IOException e) {
            throw new JobPersistenceException("Couldn't retrieve job because the BLOB couldn't be deserialized: " + e.getMessage(), e);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't retrieve job: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean removeTrigger(final TriggerKey triggerKey) throws JobPersistenceException {
        return (Boolean)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.removeTrigger(conn, triggerKey) ? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }

    protected boolean removeTrigger(Connection conn, TriggerKey key) throws JobPersistenceException {
        boolean removedTrigger;
        try {
            int numTriggers;
            JobDetail job = this.getDelegate().selectJobForTrigger(conn, this.getClassLoadHelper(), key, false);
            removedTrigger = this.deleteTriggerAndChildren(conn, key);
            if (null != job && !job.isDurable() && (numTriggers = this.getDelegate().selectNumTriggersForJob(conn, job.getKey())) == 0) {
                this.deleteJobAndChildren(conn, job.getKey());
            }
        }
        catch (ClassNotFoundException e) {
            throw new JobPersistenceException("Couldn't remove trigger: " + e.getMessage(), e);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't remove trigger: " + e.getMessage(), e);
        }
        return removedTrigger;
    }

    @Override
    public boolean replaceTrigger(final TriggerKey triggerKey, final OperableTrigger newTrigger) throws JobPersistenceException {
        return (Boolean)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.replaceTrigger(conn, triggerKey, newTrigger) ? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }

    protected boolean replaceTrigger(Connection conn, TriggerKey key, OperableTrigger newTrigger) throws JobPersistenceException {
        try {
            JobDetail job = this.getDelegate().selectJobForTrigger(conn, this.getClassLoadHelper(), key);
            if (job == null) {
                return false;
            }
            if (!newTrigger.getJobKey().equals(job.getKey())) {
                throw new JobPersistenceException("New trigger is not related to the same job as the old trigger.");
            }
            boolean removedTrigger = this.deleteTriggerAndChildren(conn, key);
            this.storeTrigger(conn, newTrigger, job, false, "WAITING", false, false);
            return removedTrigger;
        }
        catch (ClassNotFoundException e) {
            throw new JobPersistenceException("Couldn't remove trigger: " + e.getMessage(), e);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't remove trigger: " + e.getMessage(), e);
        }
    }

    @Override
    public OperableTrigger retrieveTrigger(final TriggerKey triggerKey) throws JobPersistenceException {
        return (OperableTrigger)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.retrieveTrigger(conn, triggerKey);
            }
        });
    }

    protected OperableTrigger retrieveTrigger(Connection conn, TriggerKey key) throws JobPersistenceException {
        try {
            return this.getDelegate().selectTrigger(conn, key);
        }
        catch (Exception e) {
            throw new JobPersistenceException("Couldn't retrieve trigger: " + e.getMessage(), e);
        }
    }

    @Override
    public Trigger.TriggerState getTriggerState(final TriggerKey triggerKey) throws JobPersistenceException {
        return (Trigger.TriggerState)((Object)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getTriggerState(conn, triggerKey);
            }
        }));
    }

    public Trigger.TriggerState getTriggerState(Connection conn, TriggerKey key) throws JobPersistenceException {
        try {
            String ts = this.getDelegate().selectTriggerState(conn, key);
            if (ts == null) {
                return Trigger.TriggerState.NONE;
            }
            if (ts.equals("DELETED")) {
                return Trigger.TriggerState.NONE;
            }
            if (ts.equals("COMPLETE")) {
                return Trigger.TriggerState.COMPLETE;
            }
            if (ts.equals("PAUSED")) {
                return Trigger.TriggerState.PAUSED;
            }
            if (ts.equals("PAUSED_BLOCKED")) {
                return Trigger.TriggerState.PAUSED;
            }
            if (ts.equals("ERROR")) {
                return Trigger.TriggerState.ERROR;
            }
            if (ts.equals("BLOCKED")) {
                return Trigger.TriggerState.BLOCKED;
            }
            return Trigger.TriggerState.NORMAL;
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't determine state of trigger (" + key + "): " + e.getMessage(), e);
        }
    }

    @Override
    public void resetTriggerFromErrorState(final TriggerKey triggerKey) throws JobPersistenceException {
        this.executeInLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.resetTriggerFromErrorState(conn, triggerKey);
            }
        });
    }

    void resetTriggerFromErrorState(Connection conn, TriggerKey triggerKey) throws JobPersistenceException {
        try {
            String newState = "WAITING";
            if (this.getDelegate().isTriggerGroupPaused(conn, triggerKey.getGroup())) {
                newState = "PAUSED";
            }
            this.getDelegate().updateTriggerStateFromOtherState(conn, triggerKey, newState, "ERROR");
            this.getLog().info("Trigger " + triggerKey + " reset from ERROR state to: " + newState);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't reset from error state of trigger (" + triggerKey + "): " + e.getMessage(), e);
        }
    }

    @Override
    public void storeCalendar(final String calName, final Calendar calendar, final boolean replaceExisting, final boolean updateTriggers) throws JobPersistenceException {
        this.executeInLock(this.isLockOnInsert() || updateTriggers ? LOCK_TRIGGER_ACCESS : null, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.storeCalendar(conn, calName, calendar, replaceExisting, updateTriggers);
            }
        });
    }

    protected void storeCalendar(Connection conn, String calName, Calendar calendar, boolean replaceExisting, boolean updateTriggers) throws JobPersistenceException {
        try {
            boolean existingCal = this.calendarExists(conn, calName);
            if (existingCal && !replaceExisting) {
                throw new ObjectAlreadyExistsException("Calendar with name '" + calName + "' already exists.");
            }
            if (existingCal) {
                if (this.getDelegate().updateCalendar(conn, calName, calendar) < 1) {
                    throw new JobPersistenceException("Couldn't store calendar.  Update failed.");
                }
                if (updateTriggers) {
                    List<OperableTrigger> trigs = this.getDelegate().selectTriggersForCalendar(conn, calName);
                    for (OperableTrigger trigger : trigs) {
                        trigger.updateWithNewCalendar(calendar, this.getMisfireThreshold());
                        this.storeTrigger(conn, trigger, null, true, "WAITING", false, false);
                    }
                }
            } else if (this.getDelegate().insertCalendar(conn, calName, calendar) < 1) {
                throw new JobPersistenceException("Couldn't store calendar.  Insert failed.");
            }
            if (!this.isClustered) {
                this.calendarCache.put(calName, calendar);
            }
        }
        catch (IOException e) {
            throw new JobPersistenceException("Couldn't store calendar because the BLOB couldn't be serialized: " + e.getMessage(), e);
        }
        catch (ClassNotFoundException e) {
            throw new JobPersistenceException("Couldn't store calendar: " + e.getMessage(), e);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't store calendar: " + e.getMessage(), e);
        }
    }

    protected boolean calendarExists(Connection conn, String calName) throws JobPersistenceException {
        try {
            return this.getDelegate().calendarExists(conn, calName);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't determine calendar existence (" + calName + "): " + e.getMessage(), e);
        }
    }

    @Override
    public boolean removeCalendar(final String calName) throws JobPersistenceException {
        return (Boolean)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.removeCalendar(conn, calName) ? Boolean.TRUE : Boolean.FALSE;
            }
        });
    }

    protected boolean removeCalendar(Connection conn, String calName) throws JobPersistenceException {
        try {
            if (this.getDelegate().calendarIsReferenced(conn, calName)) {
                throw new JobPersistenceException("Calender cannot be removed if it referenced by a trigger!");
            }
            if (!this.isClustered) {
                this.calendarCache.remove(calName);
            }
            return this.getDelegate().deleteCalendar(conn, calName) > 0;
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't remove calendar: " + e.getMessage(), e);
        }
    }

    @Override
    public Calendar retrieveCalendar(final String calName) throws JobPersistenceException {
        return (Calendar)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.retrieveCalendar(conn, calName);
            }
        });
    }

    protected Calendar retrieveCalendar(Connection conn, String calName) throws JobPersistenceException {
        Calendar cal;
        Calendar calendar = cal = this.isClustered ? null : this.calendarCache.get(calName);
        if (cal != null) {
            return cal;
        }
        try {
            cal = this.getDelegate().selectCalendar(conn, calName);
            if (!this.isClustered) {
                this.calendarCache.put(calName, cal);
            }
            return cal;
        }
        catch (ClassNotFoundException e) {
            throw new JobPersistenceException("Couldn't retrieve calendar because a required class was not found: " + e.getMessage(), e);
        }
        catch (IOException e) {
            throw new JobPersistenceException("Couldn't retrieve calendar because the BLOB couldn't be deserialized: " + e.getMessage(), e);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't retrieve calendar: " + e.getMessage(), e);
        }
    }

    @Override
    public int getNumberOfJobs() throws JobPersistenceException {
        return (Integer)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getNumberOfJobs(conn);
            }
        });
    }

    protected int getNumberOfJobs(Connection conn) throws JobPersistenceException {
        try {
            return this.getDelegate().selectNumJobs(conn);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't obtain number of jobs: " + e.getMessage(), e);
        }
    }

    @Override
    public int getNumberOfTriggers() throws JobPersistenceException {
        return (Integer)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getNumberOfTriggers(conn);
            }
        });
    }

    protected int getNumberOfTriggers(Connection conn) throws JobPersistenceException {
        try {
            return this.getDelegate().selectNumTriggers(conn);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't obtain number of triggers: " + e.getMessage(), e);
        }
    }

    @Override
    public int getNumberOfCalendars() throws JobPersistenceException {
        return (Integer)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getNumberOfCalendars(conn);
            }
        });
    }

    protected int getNumberOfCalendars(Connection conn) throws JobPersistenceException {
        try {
            return this.getDelegate().selectNumCalendars(conn);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't obtain number of calendars: " + e.getMessage(), e);
        }
    }

    @Override
    public Set<JobKey> getJobKeys(final GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        return (Set)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getJobNames(conn, matcher);
            }
        });
    }

    protected Set<JobKey> getJobNames(Connection conn, GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        Set<JobKey> jobNames;
        try {
            jobNames = this.getDelegate().selectJobsInGroup(conn, matcher);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't obtain job names: " + e.getMessage(), e);
        }
        return jobNames;
    }

    @Override
    public boolean checkExists(final JobKey jobKey) throws JobPersistenceException {
        return (Boolean)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.checkExists(conn, jobKey);
            }
        });
    }

    protected boolean checkExists(Connection conn, JobKey jobKey) throws JobPersistenceException {
        try {
            return this.getDelegate().jobExists(conn, jobKey);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't check for existence of job: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean checkExists(final TriggerKey triggerKey) throws JobPersistenceException {
        return (Boolean)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.checkExists(conn, triggerKey);
            }
        });
    }

    protected boolean checkExists(Connection conn, TriggerKey triggerKey) throws JobPersistenceException {
        try {
            return this.getDelegate().triggerExists(conn, triggerKey);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't check for existence of job: " + e.getMessage(), e);
        }
    }

    @Override
    public void clearAllSchedulingData() throws JobPersistenceException {
        this.executeInLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.clearAllSchedulingData(conn);
            }
        });
    }

    protected void clearAllSchedulingData(Connection conn) throws JobPersistenceException {
        try {
            this.getDelegate().clearData(conn);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Error clearing scheduling data: " + e.getMessage(), e);
        }
    }

    @Override
    public Set<TriggerKey> getTriggerKeys(final GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        return (Set)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getTriggerNames(conn, matcher);
            }
        });
    }

    protected Set<TriggerKey> getTriggerNames(Connection conn, GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        Set<TriggerKey> trigNames;
        try {
            trigNames = this.getDelegate().selectTriggersInGroup(conn, matcher);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't obtain trigger names: " + e.getMessage(), e);
        }
        return trigNames;
    }

    @Override
    public List<String> getJobGroupNames() throws JobPersistenceException {
        return (List)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getJobGroupNames(conn);
            }
        });
    }

    protected List<String> getJobGroupNames(Connection conn) throws JobPersistenceException {
        List<String> groupNames;
        try {
            groupNames = this.getDelegate().selectJobGroups(conn);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't obtain job groups: " + e.getMessage(), e);
        }
        return groupNames;
    }

    @Override
    public List<String> getTriggerGroupNames() throws JobPersistenceException {
        return (List)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getTriggerGroupNames(conn);
            }
        });
    }

    protected List<String> getTriggerGroupNames(Connection conn) throws JobPersistenceException {
        List<String> groupNames;
        try {
            groupNames = this.getDelegate().selectTriggerGroups(conn);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't obtain trigger groups: " + e.getMessage(), e);
        }
        return groupNames;
    }

    @Override
    public List<String> getCalendarNames() throws JobPersistenceException {
        return (List)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getCalendarNames(conn);
            }
        });
    }

    protected List<String> getCalendarNames(Connection conn) throws JobPersistenceException {
        try {
            return this.getDelegate().selectCalendars(conn);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't obtain trigger groups: " + e.getMessage(), e);
        }
    }

    @Override
    public List<OperableTrigger> getTriggersForJob(final JobKey jobKey) throws JobPersistenceException {
        return (List)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getTriggersForJob(conn, jobKey);
            }
        });
    }

    protected List<OperableTrigger> getTriggersForJob(Connection conn, JobKey key) throws JobPersistenceException {
        List<OperableTrigger> list;
        try {
            list = this.getDelegate().selectTriggersForJob(conn, key);
        }
        catch (Exception e) {
            throw new JobPersistenceException("Couldn't obtain triggers for job: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void pauseTrigger(final TriggerKey triggerKey) throws JobPersistenceException {
        this.executeInLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.pauseTrigger(conn, triggerKey);
            }
        });
    }

    public void pauseTrigger(Connection conn, TriggerKey triggerKey) throws JobPersistenceException {
        try {
            String oldState = this.getDelegate().selectTriggerState(conn, triggerKey);
            if (oldState.equals("WAITING") || oldState.equals("ACQUIRED")) {
                this.getDelegate().updateTriggerState(conn, triggerKey, "PAUSED");
            } else if (oldState.equals("BLOCKED")) {
                this.getDelegate().updateTriggerState(conn, triggerKey, "PAUSED_BLOCKED");
            }
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't pause trigger '" + triggerKey + "': " + e.getMessage(), e);
        }
    }

    @Override
    public void pauseJob(final JobKey jobKey) throws JobPersistenceException {
        this.executeInLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                List<OperableTrigger> triggers = JobStoreSupport.this.getTriggersForJob(conn, jobKey);
                for (OperableTrigger trigger : triggers) {
                    JobStoreSupport.this.pauseTrigger(conn, trigger.getKey());
                }
            }
        });
    }

    public Set<String> pauseJobs(final GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        return (Set)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Set<String> execute(Connection conn) throws JobPersistenceException {
                HashSet<String> groupNames = new HashSet<String>();
                Set<JobKey> jobNames = JobStoreSupport.this.getJobNames(conn, matcher);
                for (JobKey jobKey : jobNames) {
                    List<OperableTrigger> triggers = JobStoreSupport.this.getTriggersForJob(conn, jobKey);
                    for (OperableTrigger trigger : triggers) {
                        JobStoreSupport.this.pauseTrigger(conn, trigger.getKey());
                    }
                    groupNames.add(jobKey.getGroup());
                }
                return groupNames;
            }
        });
    }

    protected String checkBlockedState(Connection conn, JobKey jobKey, String currentState) throws JobPersistenceException {
        if (!currentState.equals("WAITING") && !currentState.equals("PAUSED")) {
            return currentState;
        }
        try {
            FiredTriggerRecord rec;
            List<FiredTriggerRecord> lst = this.getDelegate().selectFiredTriggerRecordsByJob(conn, jobKey.getName(), jobKey.getGroup());
            if (lst.size() > 0 && (rec = lst.get(0)).isJobDisallowsConcurrentExecution()) {
                return "PAUSED".equals(currentState) ? "PAUSED_BLOCKED" : "BLOCKED";
            }
            return currentState;
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't determine if trigger should be in a blocked state '" + jobKey + "': " + e.getMessage(), e);
        }
    }

    @Override
    public void resumeTrigger(final TriggerKey triggerKey) throws JobPersistenceException {
        this.executeInLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.resumeTrigger(conn, triggerKey);
            }
        });
    }

    public void resumeTrigger(Connection conn, TriggerKey key) throws JobPersistenceException {
        try {
            TriggerStatus status = this.getDelegate().selectTriggerStatus(conn, key);
            if (status == null || status.getNextFireTime() == null) {
                return;
            }
            boolean blocked = false;
            if ("PAUSED_BLOCKED".equals(status.getStatus())) {
                blocked = true;
            }
            String newState = this.checkBlockedState(conn, status.getJobKey(), "WAITING");
            boolean misfired = false;
            if (this.schedulerRunning && status.getNextFireTime().before(new Date())) {
                misfired = this.updateMisfiredTrigger(conn, key, newState, true);
            }
            if (!misfired) {
                if (blocked) {
                    this.getDelegate().updateTriggerStateFromOtherState(conn, key, newState, "PAUSED_BLOCKED");
                } else {
                    this.getDelegate().updateTriggerStateFromOtherState(conn, key, newState, "PAUSED");
                }
            }
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't resume trigger '" + key + "': " + e.getMessage(), e);
        }
    }

    @Override
    public void resumeJob(final JobKey jobKey) throws JobPersistenceException {
        this.executeInLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                List<OperableTrigger> triggers = JobStoreSupport.this.getTriggersForJob(conn, jobKey);
                for (OperableTrigger trigger : triggers) {
                    JobStoreSupport.this.resumeTrigger(conn, trigger.getKey());
                }
            }
        });
    }

    public Set<String> resumeJobs(final GroupMatcher<JobKey> matcher) throws JobPersistenceException {
        return (Set)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Set<String> execute(Connection conn) throws JobPersistenceException {
                Set<JobKey> jobKeys = JobStoreSupport.this.getJobNames(conn, matcher);
                HashSet<String> groupNames = new HashSet<String>();
                for (JobKey jobKey : jobKeys) {
                    List<OperableTrigger> triggers = JobStoreSupport.this.getTriggersForJob(conn, jobKey);
                    for (OperableTrigger trigger : triggers) {
                        JobStoreSupport.this.resumeTrigger(conn, trigger.getKey());
                    }
                    groupNames.add(jobKey.getGroup());
                }
                return groupNames;
            }
        });
    }

    public Set<String> pauseTriggers(final GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        return (Set)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Set<String> execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.pauseTriggerGroup(conn, matcher);
            }
        });
    }

    public Set<String> pauseTriggerGroup(Connection conn, GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        try {
            this.getDelegate().updateTriggerGroupStateFromOtherStates(conn, matcher, "PAUSED", "ACQUIRED", "WAITING", "WAITING");
            this.getDelegate().updateTriggerGroupStateFromOtherState(conn, matcher, "PAUSED_BLOCKED", "BLOCKED");
            List<String> groups = this.getDelegate().selectTriggerGroups(conn, matcher);
            StringMatcher.StringOperatorName operator = matcher.getCompareWithOperator();
            if (operator.equals((Object)StringMatcher.StringOperatorName.EQUALS) && !groups.contains(matcher.getCompareToValue())) {
                groups.add(matcher.getCompareToValue());
            }
            for (String group : groups) {
                if (this.getDelegate().isTriggerGroupPaused(conn, group)) continue;
                this.getDelegate().insertPausedTriggerGroup(conn, group);
            }
            return new HashSet<String>(groups);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't pause trigger group '" + matcher + "': " + e.getMessage(), e);
        }
    }

    @Override
    public Set<String> getPausedTriggerGroups() throws JobPersistenceException {
        return (Set)this.executeWithoutLock(new TransactionCallback(){

            public Object execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.getPausedTriggerGroups(conn);
            }
        });
    }

    public Set<String> getPausedTriggerGroups(Connection conn) throws JobPersistenceException {
        try {
            return this.getDelegate().selectPausedTriggerGroups(conn);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't determine paused trigger groups: " + e.getMessage(), e);
        }
    }

    public Set<String> resumeTriggers(final GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        return (Set)this.executeInLock(LOCK_TRIGGER_ACCESS, new TransactionCallback(){

            public Set<String> execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.resumeTriggerGroup(conn, matcher);
            }
        });
    }

    public Set<String> resumeTriggerGroup(Connection conn, GroupMatcher<TriggerKey> matcher) throws JobPersistenceException {
        try {
            this.getDelegate().deletePausedTriggerGroup(conn, matcher);
            HashSet<String> groups = new HashSet<String>();
            Set<TriggerKey> keys = this.getDelegate().selectTriggersInGroup(conn, matcher);
            for (TriggerKey key : keys) {
                this.resumeTrigger(conn, key);
                groups.add(key.getGroup());
            }
            return groups;
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't pause trigger group '" + matcher + "': " + e.getMessage(), e);
        }
    }

    @Override
    public void pauseAll() throws JobPersistenceException {
        this.executeInLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.pauseAll(conn);
            }
        });
    }

    public void pauseAll(Connection conn) throws JobPersistenceException {
        List<String> names = this.getTriggerGroupNames(conn);
        for (String name : names) {
            this.pauseTriggerGroup(conn, GroupMatcher.triggerGroupEquals(name));
        }
        try {
            if (!this.getDelegate().isTriggerGroupPaused(conn, "_$_ALL_GROUPS_PAUSED_$_")) {
                this.getDelegate().insertPausedTriggerGroup(conn, "_$_ALL_GROUPS_PAUSED_$_");
            }
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't pause all trigger groups: " + e.getMessage(), e);
        }
    }

    @Override
    public void resumeAll() throws JobPersistenceException {
        this.executeInLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.resumeAll(conn);
            }
        });
    }

    public void resumeAll(Connection conn) throws JobPersistenceException {
        List<String> names = this.getTriggerGroupNames(conn);
        for (String name : names) {
            this.resumeTriggerGroup(conn, GroupMatcher.triggerGroupEquals(name));
        }
        try {
            this.getDelegate().deletePausedTriggerGroup(conn, "_$_ALL_GROUPS_PAUSED_$_");
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't resume all trigger groups: " + e.getMessage(), e);
        }
    }

    protected synchronized String getFiredTriggerRecordId() {
        return this.getInstanceId() + ftrCtr++;
    }

    @Override
    public List<OperableTrigger> acquireNextTriggers(final long noLaterThan, final int maxCount, final long timeWindow) throws JobPersistenceException {
        String lockName = this.isAcquireTriggersWithinLock() || maxCount > 1 ? LOCK_TRIGGER_ACCESS : null;
        return this.executeInNonManagedTXLock(lockName, new TransactionCallback<List<OperableTrigger>>(){

            @Override
            public List<OperableTrigger> execute(Connection conn) throws JobPersistenceException {
                return JobStoreSupport.this.acquireNextTrigger(conn, noLaterThan, maxCount, timeWindow);
            }
        }, new TransactionValidator<List<OperableTrigger>>(){

            @Override
            public Boolean validate(Connection conn, List<OperableTrigger> result) throws JobPersistenceException {
                try {
                    List<FiredTriggerRecord> acquired = JobStoreSupport.this.getDelegate().selectInstancesFiredTriggerRecords(conn, JobStoreSupport.this.getInstanceId());
                    HashSet<String> fireInstanceIds = new HashSet<String>();
                    for (FiredTriggerRecord ft : acquired) {
                        fireInstanceIds.add(ft.getFireInstanceId());
                    }
                    for (OperableTrigger tr : result) {
                        if (!fireInstanceIds.contains(tr.getFireInstanceId())) continue;
                        return true;
                    }
                    return false;
                }
                catch (SQLException e) {
                    throw new JobPersistenceException("error validating trigger acquisition", e);
                }
            }
        });
    }

    protected List<OperableTrigger> acquireNextTrigger(Connection conn, long noLaterThan, int maxCount, long timeWindow) throws JobPersistenceException {
        if (timeWindow < 0L) {
            throw new IllegalArgumentException();
        }
        ArrayList<OperableTrigger> acquiredTriggers = new ArrayList<OperableTrigger>();
        HashSet<JobKey> acquiredJobKeysForNoConcurrentExec = new HashSet<JobKey>();
        int MAX_DO_LOOP_RETRY = 3;
        int currentLoopCount = 0;
        while (true) {
            ++currentLoopCount;
            try {
                List<TriggerKey> keys = this.getDelegate().selectTriggerToAcquire(conn, noLaterThan + timeWindow, this.getMisfireTime(), maxCount);
                if (keys == null || keys.size() == 0) {
                    return acquiredTriggers;
                }
                long batchEnd = noLaterThan;
                for (TriggerKey triggerKey : keys) {
                    Date nextFireTime;
                    JobDetail job;
                    OperableTrigger nextTrigger = this.retrieveTrigger(conn, triggerKey);
                    if (nextTrigger == null) continue;
                    JobKey jobKey = nextTrigger.getJobKey();
                    try {
                        job = this.retrieveJob(conn, jobKey);
                    }
                    catch (JobPersistenceException jpe) {
                        try {
                            this.getLog().error("Error retrieving job, setting trigger state to ERROR.", (Throwable)jpe);
                            this.getDelegate().updateTriggerState(conn, triggerKey, "ERROR");
                        }
                        catch (SQLException sqle) {
                            this.getLog().error("Unable to set trigger state to ERROR.", (Throwable)sqle);
                        }
                        continue;
                    }
                    if (job.isConcurrentExectionDisallowed()) {
                        if (acquiredJobKeysForNoConcurrentExec.contains(jobKey)) continue;
                        acquiredJobKeysForNoConcurrentExec.add(jobKey);
                    }
                    if ((nextFireTime = nextTrigger.getNextFireTime()) == null) {
                        this.log.warn("Trigger {} returned null on nextFireTime and yet still exists in DB!", (Object)nextTrigger.getKey());
                        continue;
                    }
                    if (nextFireTime.getTime() > batchEnd) break;
                    int rowsUpdated = this.getDelegate().updateTriggerStateFromOtherState(conn, triggerKey, "ACQUIRED", "WAITING");
                    if (rowsUpdated <= 0) continue;
                    nextTrigger.setFireInstanceId(this.getFiredTriggerRecordId());
                    this.getDelegate().insertFiredTrigger(conn, nextTrigger, "ACQUIRED", null);
                    if (acquiredTriggers.isEmpty()) {
                        batchEnd = Math.max(nextFireTime.getTime(), System.currentTimeMillis()) + timeWindow;
                    }
                    acquiredTriggers.add(nextTrigger);
                }
                if (acquiredTriggers.size() != 0 || currentLoopCount >= 3) break;
            }
            catch (Exception e) {
                throw new JobPersistenceException("Couldn't acquire next trigger: " + e.getMessage(), e);
            }
        }
        return acquiredTriggers;
    }

    @Override
    public void releaseAcquiredTrigger(final OperableTrigger trigger) {
        this.retryExecuteInNonManagedTXLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.releaseAcquiredTrigger(conn, trigger);
            }
        });
    }

    protected void releaseAcquiredTrigger(Connection conn, OperableTrigger trigger) throws JobPersistenceException {
        try {
            this.getDelegate().updateTriggerStateFromOtherState(conn, trigger.getKey(), "WAITING", "ACQUIRED");
            this.getDelegate().updateTriggerStateFromOtherState(conn, trigger.getKey(), "WAITING", "BLOCKED");
            this.getDelegate().deleteFiredTrigger(conn, trigger.getFireInstanceId());
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't release acquired trigger: " + e.getMessage(), e);
        }
    }

    @Override
    public List<TriggerFiredResult> triggersFired(final List<OperableTrigger> triggers) throws JobPersistenceException {
        return this.executeInNonManagedTXLock(LOCK_TRIGGER_ACCESS, new TransactionCallback<List<TriggerFiredResult>>(){

            @Override
            public List<TriggerFiredResult> execute(Connection conn) throws JobPersistenceException {
                ArrayList<TriggerFiredResult> results = new ArrayList<TriggerFiredResult>();
                for (OperableTrigger trigger : triggers) {
                    TriggerFiredResult result;
                    try {
                        TriggerFiredBundle bundle = JobStoreSupport.this.triggerFired(conn, trigger);
                        result = new TriggerFiredResult(bundle);
                    }
                    catch (JobPersistenceException jpe) {
                        result = new TriggerFiredResult(jpe);
                    }
                    catch (RuntimeException re) {
                        result = new TriggerFiredResult(re);
                    }
                    results.add(result);
                }
                return results;
            }
        }, new TransactionValidator<List<TriggerFiredResult>>(){

            @Override
            public Boolean validate(Connection conn, List<TriggerFiredResult> result) throws JobPersistenceException {
                try {
                    List<FiredTriggerRecord> acquired = JobStoreSupport.this.getDelegate().selectInstancesFiredTriggerRecords(conn, JobStoreSupport.this.getInstanceId());
                    HashSet<String> executingTriggers = new HashSet<String>();
                    for (FiredTriggerRecord ft : acquired) {
                        if (!"EXECUTING".equals(ft.getFireInstanceState())) continue;
                        executingTriggers.add(ft.getFireInstanceId());
                    }
                    for (TriggerFiredResult tr : result) {
                        if (tr.getTriggerFiredBundle() == null || !executingTriggers.contains(tr.getTriggerFiredBundle().getTrigger().getFireInstanceId())) continue;
                        return true;
                    }
                    return false;
                }
                catch (SQLException e) {
                    throw new JobPersistenceException("error validating trigger acquisition", e);
                }
            }
        });
    }

    protected TriggerFiredBundle triggerFired(Connection conn, OperableTrigger trigger) throws JobPersistenceException {
        JobDetail job;
        Calendar cal = null;
        try {
            String state = this.getDelegate().selectTriggerState(conn, trigger.getKey());
            if (!state.equals("ACQUIRED")) {
                return null;
            }
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't select trigger state: " + e.getMessage(), e);
        }
        try {
            job = this.retrieveJob(conn, trigger.getJobKey());
            if (job == null) {
                return null;
            }
        }
        catch (JobPersistenceException jpe) {
            try {
                this.getLog().error("Error retrieving job, setting trigger state to ERROR.", (Throwable)jpe);
                this.getDelegate().updateTriggerState(conn, trigger.getKey(), "ERROR");
            }
            catch (SQLException sqle) {
                this.getLog().error("Unable to set trigger state to ERROR.", (Throwable)sqle);
            }
            throw jpe;
        }
        if (trigger.getCalendarName() != null && (cal = this.retrieveCalendar(conn, trigger.getCalendarName())) == null) {
            return null;
        }
        try {
            this.getDelegate().updateFiredTrigger(conn, trigger, "EXECUTING", job);
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't insert fired trigger: " + e.getMessage(), e);
        }
        Date prevFireTime = trigger.getPreviousFireTime();
        trigger.triggered(cal);
        String state = "WAITING";
        boolean force = true;
        if (job.isConcurrentExectionDisallowed()) {
            state = "BLOCKED";
            force = false;
            try {
                this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, job.getKey(), "BLOCKED", "WAITING");
                this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, job.getKey(), "BLOCKED", "ACQUIRED");
                this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, job.getKey(), "PAUSED_BLOCKED", "PAUSED");
            }
            catch (SQLException e) {
                throw new JobPersistenceException("Couldn't update states of blocked triggers: " + e.getMessage(), e);
            }
        }
        if (trigger.getNextFireTime() == null) {
            state = "COMPLETE";
            force = true;
        }
        this.storeTrigger(conn, trigger, job, true, state, force, false);
        job.getJobDataMap().clearDirtyFlag();
        return new TriggerFiredBundle(job, trigger, cal, trigger.getKey().getGroup().equals("RECOVERING_JOBS"), new Date(), trigger.getPreviousFireTime(), prevFireTime, trigger.getNextFireTime());
    }

    @Override
    public void triggeredJobComplete(final OperableTrigger trigger, final JobDetail jobDetail, final Trigger.CompletedExecutionInstruction triggerInstCode) {
        this.retryExecuteInNonManagedTXLock(LOCK_TRIGGER_ACCESS, new VoidTransactionCallback(){

            @Override
            public void executeVoid(Connection conn) throws JobPersistenceException {
                JobStoreSupport.this.triggeredJobComplete(conn, trigger, jobDetail, triggerInstCode);
            }
        });
    }

    protected void triggeredJobComplete(Connection conn, OperableTrigger trigger, JobDetail jobDetail, Trigger.CompletedExecutionInstruction triggerInstCode) throws JobPersistenceException {
        block21: {
            try {
                if (triggerInstCode == Trigger.CompletedExecutionInstruction.DELETE_TRIGGER) {
                    if (trigger.getNextFireTime() == null) {
                        TriggerStatus stat = this.getDelegate().selectTriggerStatus(conn, trigger.getKey());
                        if (stat != null && stat.getNextFireTime() == null) {
                            this.removeTrigger(conn, trigger.getKey());
                        }
                    } else {
                        this.removeTrigger(conn, trigger.getKey());
                        this.signalSchedulingChangeOnTxCompletion(0L);
                    }
                } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_COMPLETE) {
                    this.getDelegate().updateTriggerState(conn, trigger.getKey(), "COMPLETE");
                    this.signalSchedulingChangeOnTxCompletion(0L);
                } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_ERROR) {
                    this.getLog().info("Trigger " + trigger.getKey() + " set to ERROR state.");
                    this.getDelegate().updateTriggerState(conn, trigger.getKey(), "ERROR");
                    this.signalSchedulingChangeOnTxCompletion(0L);
                } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_COMPLETE) {
                    this.getDelegate().updateTriggerStatesForJob(conn, trigger.getJobKey(), "COMPLETE");
                    this.signalSchedulingChangeOnTxCompletion(0L);
                } else if (triggerInstCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR) {
                    this.getLog().info("All triggers of Job " + trigger.getKey() + " set to ERROR state.");
                    this.getDelegate().updateTriggerStatesForJob(conn, trigger.getJobKey(), "ERROR");
                    this.signalSchedulingChangeOnTxCompletion(0L);
                }
                if (jobDetail.isConcurrentExectionDisallowed()) {
                    this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, jobDetail.getKey(), "WAITING", "BLOCKED");
                    this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, jobDetail.getKey(), "PAUSED", "PAUSED_BLOCKED");
                    this.signalSchedulingChangeOnTxCompletion(0L);
                }
                if (!jobDetail.isPersistJobDataAfterExecution()) break block21;
                try {
                    if (jobDetail.getJobDataMap().isDirty()) {
                        this.getDelegate().updateJobData(conn, jobDetail);
                    }
                }
                catch (IOException e) {
                    throw new JobPersistenceException("Couldn't serialize job data: " + e.getMessage(), e);
                }
                catch (SQLException e) {
                    throw new JobPersistenceException("Couldn't update job data: " + e.getMessage(), e);
                }
            }
            catch (SQLException e) {
                throw new JobPersistenceException("Couldn't update trigger state(s): " + e.getMessage(), e);
            }
        }
        try {
            this.getDelegate().deleteFiredTrigger(conn, trigger.getFireInstanceId());
        }
        catch (SQLException e) {
            throw new JobPersistenceException("Couldn't delete fired trigger: " + e.getMessage(), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected DriverDelegate getDelegate() throws NoSuchDelegateException {
        JobStoreSupport jobStoreSupport = this;
        synchronized (jobStoreSupport) {
            if (null == this.delegate) {
                try {
                    if (this.delegateClassName != null) {
                        this.delegateClass = this.getClassLoadHelper().loadClass(this.delegateClassName, DriverDelegate.class);
                    }
                    this.delegate = this.delegateClass.newInstance();
                    this.delegate.initialize(this.getLog(), this.tablePrefix, this.instanceName, this.instanceId, this.getClassLoadHelper(), this.canUseProperties(), this.getDriverDelegateInitString());
                }
                catch (InstantiationException e) {
                    throw new NoSuchDelegateException("Couldn't create delegate: " + e.getMessage(), e);
                }
                catch (IllegalAccessException e) {
                    throw new NoSuchDelegateException("Couldn't create delegate: " + e.getMessage(), e);
                }
                catch (ClassNotFoundException e) {
                    throw new NoSuchDelegateException("Couldn't load delegate class: " + e.getMessage(), e);
                }
            }
            return this.delegate;
        }
    }

    protected Semaphore getLockHandler() {
        return this.lockHandler;
    }

    public void setLockHandler(Semaphore lockHandler) {
        this.lockHandler = lockHandler;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected RecoverMisfiredJobsResult doRecoverMisfires() throws JobPersistenceException {
        boolean transOwner = false;
        Connection conn = this.getNonManagedTXConnection();
        try {
            int misfireCount;
            RecoverMisfiredJobsResult result = RecoverMisfiredJobsResult.NO_OP;
            int n = misfireCount = this.getDoubleCheckLockMisfireHandler() ? this.getDelegate().countMisfiredTriggersInState(conn, "WAITING", this.getMisfireTime()) : Integer.MAX_VALUE;
            if (misfireCount == 0) {
                this.getLog().debug("Found 0 triggers that missed their scheduled fire-time.");
            } else {
                transOwner = this.getLockHandler().obtainLock(conn, LOCK_TRIGGER_ACCESS);
                result = this.recoverMisfiredJobs(conn, false);
            }
            this.commitConnection(conn);
            RecoverMisfiredJobsResult recoverMisfiredJobsResult = result;
            return recoverMisfiredJobsResult;
        }
        catch (JobPersistenceException e) {
            this.rollbackConnection(conn);
            throw e;
        }
        catch (SQLException e) {
            this.rollbackConnection(conn);
            throw new JobPersistenceException("Database error recovering from misfires.", e);
        }
        catch (RuntimeException e) {
            this.rollbackConnection(conn);
            throw new JobPersistenceException("Unexpected runtime exception: " + e.getMessage(), e);
        }
        finally {
            try {
                this.releaseLock(LOCK_TRIGGER_ACCESS, transOwner);
            }
            finally {
                this.cleanupConnection(conn);
            }
        }
    }

    protected void signalSchedulingChangeOnTxCompletion(long candidateNewNextFireTime) {
        Long sigTime = this.sigChangeForTxCompletion.get();
        if (sigTime == null && candidateNewNextFireTime >= 0L) {
            this.sigChangeForTxCompletion.set(candidateNewNextFireTime);
        } else if (sigTime == null || candidateNewNextFireTime < sigTime) {
            this.sigChangeForTxCompletion.set(candidateNewNextFireTime);
        }
    }

    protected Long clearAndGetSignalSchedulingChangeOnTxCompletion() {
        Long t = this.sigChangeForTxCompletion.get();
        this.sigChangeForTxCompletion.set(null);
        return t;
    }

    protected void signalSchedulingChangeImmediately(long candidateNewNextFireTime) {
        this.schedSignaler.signalSchedulingChange(candidateNewNextFireTime);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean doCheckin() throws JobPersistenceException {
        boolean transOwner = false;
        boolean transStateOwner = false;
        boolean recovered = false;
        Connection conn = this.getNonManagedTXConnection();
        try {
            List<SchedulerStateRecord> failedRecords = null;
            if (!this.firstCheckIn) {
                failedRecords = this.clusterCheckIn(conn);
                this.commitConnection(conn);
            }
            if (this.firstCheckIn || failedRecords.size() > 0) {
                this.getLockHandler().obtainLock(conn, LOCK_STATE_ACCESS);
                transStateOwner = true;
                List<SchedulerStateRecord> list = failedRecords = this.firstCheckIn ? this.clusterCheckIn(conn) : this.findFailedInstances(conn);
                if (failedRecords.size() > 0) {
                    this.getLockHandler().obtainLock(conn, LOCK_TRIGGER_ACCESS);
                    transOwner = true;
                    this.clusterRecover(conn, failedRecords);
                    recovered = true;
                }
            }
            this.commitConnection(conn);
        }
        catch (JobPersistenceException e) {
            this.rollbackConnection(conn);
            throw e;
        }
        finally {
            try {
                this.releaseLock(LOCK_TRIGGER_ACCESS, transOwner);
            }
            finally {
                try {
                    this.releaseLock(LOCK_STATE_ACCESS, transStateOwner);
                }
                finally {
                    this.cleanupConnection(conn);
                }
            }
        }
        this.firstCheckIn = false;
        return recovered;
    }

    protected List<SchedulerStateRecord> findFailedInstances(Connection conn) throws JobPersistenceException {
        try {
            LinkedList<SchedulerStateRecord> failedInstances = new LinkedList<SchedulerStateRecord>();
            boolean foundThisScheduler = false;
            long timeNow = System.currentTimeMillis();
            List<SchedulerStateRecord> states = this.getDelegate().selectSchedulerStateRecords(conn, null);
            for (SchedulerStateRecord rec : states) {
                if (rec.getSchedulerInstanceId().equals(this.getInstanceId())) {
                    foundThisScheduler = true;
                    if (!this.firstCheckIn) continue;
                    failedInstances.add(rec);
                    continue;
                }
                if (this.calcFailedIfAfter(rec) >= timeNow) continue;
                failedInstances.add(rec);
            }
            if (this.firstCheckIn) {
                failedInstances.addAll(this.findOrphanedFailedInstances(conn, states));
            }
            if (!foundThisScheduler && !this.firstCheckIn) {
                this.getLog().warn("This scheduler instance (" + this.getInstanceId() + ") is still " + "active but was recovered by another instance in the cluster.  " + "This may cause inconsistent behavior.");
            }
            return failedInstances;
        }
        catch (Exception e) {
            this.lastCheckin = System.currentTimeMillis();
            throw new JobPersistenceException("Failure identifying failed instances when checking-in: " + e.getMessage(), e);
        }
    }

    private List<SchedulerStateRecord> findOrphanedFailedInstances(Connection conn, List<SchedulerStateRecord> schedulerStateRecords) throws SQLException, NoSuchDelegateException {
        ArrayList<SchedulerStateRecord> orphanedInstances = new ArrayList<SchedulerStateRecord>();
        Set<String> allFiredTriggerInstanceNames = this.getDelegate().selectFiredTriggerInstanceNames(conn);
        if (!allFiredTriggerInstanceNames.isEmpty()) {
            for (SchedulerStateRecord rec : schedulerStateRecords) {
                allFiredTriggerInstanceNames.remove(rec.getSchedulerInstanceId());
            }
            for (String inst : allFiredTriggerInstanceNames) {
                SchedulerStateRecord orphanedInstance = new SchedulerStateRecord();
                orphanedInstance.setSchedulerInstanceId(inst);
                orphanedInstances.add(orphanedInstance);
                this.getLog().warn("Found orphaned fired triggers for instance: " + orphanedInstance.getSchedulerInstanceId());
            }
        }
        return orphanedInstances;
    }

    protected long calcFailedIfAfter(SchedulerStateRecord rec) {
        return rec.getCheckinTimestamp() + Math.max(rec.getCheckinInterval(), System.currentTimeMillis() - this.lastCheckin) + 7500L;
    }

    protected List<SchedulerStateRecord> clusterCheckIn(Connection conn) throws JobPersistenceException {
        List<SchedulerStateRecord> failedInstances = this.findFailedInstances(conn);
        try {
            this.lastCheckin = System.currentTimeMillis();
            if (this.getDelegate().updateSchedulerState(conn, this.getInstanceId(), this.lastCheckin) == 0) {
                this.getDelegate().insertSchedulerState(conn, this.getInstanceId(), this.lastCheckin, this.getClusterCheckinInterval());
            }
        }
        catch (Exception e) {
            throw new JobPersistenceException("Failure updating scheduler state when checking-in: " + e.getMessage(), e);
        }
        return failedInstances;
    }

    protected void clusterRecover(Connection conn, List<SchedulerStateRecord> failedInstances) throws JobPersistenceException {
        if (failedInstances.size() > 0) {
            long recoverIds = System.currentTimeMillis();
            this.logWarnIfNonZero(failedInstances.size(), "ClusterManager: detected " + failedInstances.size() + " failed or restarted instances.");
            try {
                for (SchedulerStateRecord rec : failedInstances) {
                    this.getLog().info("ClusterManager: Scanning for instance \"" + rec.getSchedulerInstanceId() + "\"'s failed in-progress jobs.");
                    List<FiredTriggerRecord> firedTriggerRecs = this.getDelegate().selectInstancesFiredTriggerRecords(conn, rec.getSchedulerInstanceId());
                    int acquiredCount = 0;
                    int recoveredCount = 0;
                    int otherCount = 0;
                    HashSet<TriggerKey> triggerKeys = new HashSet<TriggerKey>();
                    for (FiredTriggerRecord ftRec : firedTriggerRecs) {
                        TriggerKey tKey = ftRec.getTriggerKey();
                        JobKey jKey = ftRec.getJobKey();
                        triggerKeys.add(tKey);
                        if (ftRec.getFireInstanceState().equals("BLOCKED")) {
                            this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, jKey, "WAITING", "BLOCKED");
                        } else if (ftRec.getFireInstanceState().equals("PAUSED_BLOCKED")) {
                            this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, jKey, "PAUSED", "PAUSED_BLOCKED");
                        }
                        if (ftRec.getFireInstanceState().equals("ACQUIRED")) {
                            this.getDelegate().updateTriggerStateFromOtherState(conn, tKey, "WAITING", "ACQUIRED");
                            ++acquiredCount;
                        } else if (ftRec.isJobRequestsRecovery()) {
                            if (this.jobExists(conn, jKey)) {
                                SimpleTriggerImpl rcvryTrig = new SimpleTriggerImpl("recover_" + rec.getSchedulerInstanceId() + "_" + String.valueOf(recoverIds++), "RECOVERING_JOBS", new Date(ftRec.getScheduleTimestamp()));
                                rcvryTrig.setJobName(jKey.getName());
                                rcvryTrig.setJobGroup(jKey.getGroup());
                                rcvryTrig.setMisfireInstruction(-1);
                                rcvryTrig.setPriority(ftRec.getPriority());
                                JobDataMap jd = this.getDelegate().selectTriggerJobDataMap(conn, tKey.getName(), tKey.getGroup());
                                jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_NAME", tKey.getName());
                                jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_GROUP", tKey.getGroup());
                                jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_FIRETIME_IN_MILLISECONDS_AS_STRING", String.valueOf(ftRec.getFireTimestamp()));
                                jd.put("QRTZ_FAILED_JOB_ORIG_TRIGGER_SCHEDULED_FIRETIME_IN_MILLISECONDS_AS_STRING", String.valueOf(ftRec.getScheduleTimestamp()));
                                rcvryTrig.setJobDataMap(jd);
                                rcvryTrig.computeFirstFireTime(null);
                                this.storeTrigger(conn, rcvryTrig, null, false, "WAITING", false, true);
                                ++recoveredCount;
                            } else {
                                this.getLog().warn("ClusterManager: failed job '" + jKey + "' no longer exists, cannot schedule recovery.");
                                ++otherCount;
                            }
                        } else {
                            ++otherCount;
                        }
                        if (!ftRec.isJobDisallowsConcurrentExecution()) continue;
                        this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, jKey, "WAITING", "BLOCKED");
                        this.getDelegate().updateTriggerStatesForJobFromOtherState(conn, jKey, "PAUSED", "PAUSED_BLOCKED");
                    }
                    this.getDelegate().deleteFiredTriggers(conn, rec.getSchedulerInstanceId());
                    int completeCount = 0;
                    for (TriggerKey triggerKey : triggerKeys) {
                        List<FiredTriggerRecord> firedTriggers;
                        if (!this.getDelegate().selectTriggerState(conn, triggerKey).equals("COMPLETE") || !(firedTriggers = this.getDelegate().selectFiredTriggerRecords(conn, triggerKey.getName(), triggerKey.getGroup())).isEmpty() || !this.removeTrigger(conn, triggerKey)) continue;
                        ++completeCount;
                    }
                    this.logWarnIfNonZero(acquiredCount, "ClusterManager: ......Freed " + acquiredCount + " acquired trigger(s).");
                    this.logWarnIfNonZero(completeCount, "ClusterManager: ......Deleted " + completeCount + " complete triggers(s).");
                    this.logWarnIfNonZero(recoveredCount, "ClusterManager: ......Scheduled " + recoveredCount + " recoverable job(s) for recovery.");
                    this.logWarnIfNonZero(otherCount, "ClusterManager: ......Cleaned-up " + otherCount + " other failed job(s).");
                    if (rec.getSchedulerInstanceId().equals(this.getInstanceId())) continue;
                    this.getDelegate().deleteSchedulerState(conn, rec.getSchedulerInstanceId());
                }
            }
            catch (Throwable e) {
                throw new JobPersistenceException("Failure recovering jobs: " + e.getMessage(), e);
            }
        }
    }

    protected void logWarnIfNonZero(int val, String warning) {
        if (val > 0) {
            this.getLog().info(warning);
        } else {
            this.getLog().debug(warning);
        }
    }

    protected void cleanupConnection(Connection conn) {
        if (conn != null) {
            Proxy connProxy;
            InvocationHandler invocationHandler;
            if (conn instanceof Proxy && (invocationHandler = Proxy.getInvocationHandler(connProxy = (Proxy)((Object)conn))) instanceof AttributeRestoringConnectionInvocationHandler) {
                AttributeRestoringConnectionInvocationHandler connHandler = (AttributeRestoringConnectionInvocationHandler)invocationHandler;
                connHandler.restoreOriginalAtributes();
                this.closeConnection(connHandler.getWrappedConnection());
                return;
            }
            this.closeConnection(conn);
        }
    }

    protected void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                this.getLog().error("Failed to close Connection", (Throwable)e);
            }
            catch (Throwable e) {
                this.getLog().error("Unexpected exception closing Connection.  This is often due to a Connection being returned after or during shutdown.", e);
            }
        }
    }

    protected void rollbackConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            }
            catch (SQLException e) {
                this.getLog().error("Couldn't rollback jdbc connection. " + e.getMessage(), (Throwable)e);
            }
        }
    }

    protected void commitConnection(Connection conn) throws JobPersistenceException {
        if (conn != null) {
            try {
                conn.commit();
            }
            catch (SQLException e) {
                throw new JobPersistenceException("Couldn't commit jdbc connection. " + e.getMessage(), e);
            }
        }
    }

    public <T> T executeWithoutLock(TransactionCallback<T> txCallback) throws JobPersistenceException {
        return this.executeInLock(null, txCallback);
    }

    protected abstract <T> T executeInLock(String var1, TransactionCallback<T> var2) throws JobPersistenceException;

    protected <T> T retryExecuteInNonManagedTXLock(String lockName, TransactionCallback<T> txCallback) {
        int retry = 1;
        while (!this.shutdown) {
            try {
                return this.executeInNonManagedTXLock(lockName, txCallback, null);
            }
            catch (JobPersistenceException jpe) {
                if (retry % 4 == 0) {
                    this.schedSignaler.notifySchedulerListenersError("An error occurred while " + txCallback, jpe);
                }
            }
            catch (RuntimeException e) {
                this.getLog().error("retryExecuteInNonManagedTXLock: RuntimeException " + e.getMessage(), (Throwable)e);
            }
            try {
                Thread.sleep(this.getDbRetryInterval());
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("Received interrupted exception", e);
            }
            ++retry;
        }
        throw new IllegalStateException("JobStore is shutdown - aborting retry");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected <T> T executeInNonManagedTXLock(String lockName, TransactionCallback<T> txCallback, final TransactionValidator<T> txValidator) throws JobPersistenceException {
        boolean transOwner = false;
        Connection conn = null;
        try {
            T result;
            block17: {
                if (lockName != null) {
                    if (this.getLockHandler().requiresConnection()) {
                        conn = this.getNonManagedTXConnection();
                    }
                    transOwner = this.getLockHandler().obtainLock(conn, lockName);
                }
                if (conn == null) {
                    conn = this.getNonManagedTXConnection();
                }
                result = txCallback.execute(conn);
                try {
                    this.commitConnection(conn);
                }
                catch (JobPersistenceException e) {
                    this.rollbackConnection(conn);
                    if (txValidator != null && this.retryExecuteInNonManagedTXLock(lockName, new TransactionCallback<Boolean>(){

                        @Override
                        public Boolean execute(Connection conn) throws JobPersistenceException {
                            return txValidator.validate(conn, result);
                        }
                    }).booleanValue()) break block17;
                    throw e;
                }
            }
            Long sigTime = this.clearAndGetSignalSchedulingChangeOnTxCompletion();
            if (sigTime != null && sigTime >= 0L) {
                this.signalSchedulingChangeImmediately(sigTime);
            }
            T t = result;
            return t;
        }
        catch (JobPersistenceException e) {
            this.rollbackConnection(conn);
            throw e;
        }
        catch (RuntimeException e) {
            this.rollbackConnection(conn);
            throw new JobPersistenceException("Unexpected runtime exception: " + e.getMessage(), e);
        }
        finally {
            try {
                this.releaseLock(lockName, transOwner);
            }
            finally {
                this.cleanupConnection(conn);
            }
        }
    }

    class MisfireHandler
    extends Thread {
        private volatile boolean shutdown = false;
        private int numFails = 0;

        MisfireHandler() {
            this.setName("QuartzScheduler_" + JobStoreSupport.this.instanceName + "-" + JobStoreSupport.this.instanceId + "_MisfireHandler");
            this.setDaemon(JobStoreSupport.this.getMakeThreadsDaemons());
        }

        public void initialize() {
            ThreadExecutor executor = JobStoreSupport.this.getThreadExecutor();
            executor.execute(this);
        }

        public void shutdown() {
            this.shutdown = true;
            this.interrupt();
        }

        private RecoverMisfiredJobsResult manage() {
            try {
                JobStoreSupport.this.getLog().debug("MisfireHandler: scanning for misfires...");
                RecoverMisfiredJobsResult res = JobStoreSupport.this.doRecoverMisfires();
                this.numFails = 0;
                return res;
            }
            catch (Exception e) {
                if (this.numFails % 4 == 0) {
                    JobStoreSupport.this.getLog().error("MisfireHandler: Error handling misfires: " + e.getMessage(), (Throwable)e);
                }
                ++this.numFails;
                return RecoverMisfiredJobsResult.NO_OP;
            }
        }

        @Override
        public void run() {
            while (!this.shutdown) {
                long sTime = System.currentTimeMillis();
                RecoverMisfiredJobsResult recoverMisfiredJobsResult = this.manage();
                if (recoverMisfiredJobsResult.getProcessedMisfiredTriggerCount() > 0) {
                    JobStoreSupport.this.signalSchedulingChangeImmediately(recoverMisfiredJobsResult.getEarliestNewTime());
                }
                if (this.shutdown) continue;
                long timeToSleep = 50L;
                if (!recoverMisfiredJobsResult.hasMoreMisfiredTriggers()) {
                    timeToSleep = JobStoreSupport.this.getMisfireThreshold() - (System.currentTimeMillis() - sTime);
                    if (timeToSleep <= 0L) {
                        timeToSleep = 50L;
                    }
                    if (this.numFails > 0) {
                        timeToSleep = Math.max(JobStoreSupport.this.getDbRetryInterval(), timeToSleep);
                    }
                }
                try {
                    Thread.sleep(timeToSleep);
                }
                catch (Exception exception) {}
            }
        }
    }

    class ClusterManager
    extends Thread {
        private volatile boolean shutdown = false;
        private int numFails = 0;

        ClusterManager() {
            this.setPriority(7);
            this.setName("QuartzScheduler_" + JobStoreSupport.this.instanceName + "-" + JobStoreSupport.this.instanceId + "_ClusterManager");
            this.setDaemon(JobStoreSupport.this.getMakeThreadsDaemons());
        }

        public void initialize() {
            this.manage();
            ThreadExecutor executor = JobStoreSupport.this.getThreadExecutor();
            executor.execute(this);
        }

        public void shutdown() {
            this.shutdown = true;
            this.interrupt();
        }

        private boolean manage() {
            boolean res = false;
            try {
                res = JobStoreSupport.this.doCheckin();
                this.numFails = 0;
                JobStoreSupport.this.getLog().debug("ClusterManager: Check-in complete.");
            }
            catch (Exception e) {
                if (this.numFails % 4 == 0) {
                    JobStoreSupport.this.getLog().error("ClusterManager: Error managing cluster: " + e.getMessage(), (Throwable)e);
                }
                ++this.numFails;
            }
            return res;
        }

        @Override
        public void run() {
            while (!this.shutdown) {
                if (!this.shutdown) {
                    long timeToSleep = JobStoreSupport.this.getClusterCheckinInterval();
                    long transpiredTime = System.currentTimeMillis() - JobStoreSupport.this.lastCheckin;
                    if ((timeToSleep -= transpiredTime) <= 0L) {
                        timeToSleep = 100L;
                    }
                    if (this.numFails > 0) {
                        timeToSleep = Math.max(JobStoreSupport.this.getDbRetryInterval(), timeToSleep);
                    }
                    try {
                        Thread.sleep(timeToSleep);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (this.shutdown || !this.manage()) continue;
                JobStoreSupport.this.signalSchedulingChangeImmediately(0L);
            }
        }
    }

    protected abstract class VoidTransactionCallback
    implements TransactionCallback<Void> {
        protected VoidTransactionCallback() {
        }

        @Override
        public final Void execute(Connection conn) throws JobPersistenceException {
            this.executeVoid(conn);
            return null;
        }

        abstract void executeVoid(Connection var1) throws JobPersistenceException;
    }

    protected static interface TransactionValidator<T> {
        public Boolean validate(Connection var1, T var2) throws JobPersistenceException;
    }

    protected static interface TransactionCallback<T> {
        public T execute(Connection var1) throws JobPersistenceException;
    }

    protected static class RecoverMisfiredJobsResult {
        public static final RecoverMisfiredJobsResult NO_OP = new RecoverMisfiredJobsResult(false, 0, Long.MAX_VALUE);
        private boolean _hasMoreMisfiredTriggers;
        private int _processedMisfiredTriggerCount;
        private long _earliestNewTime;

        public RecoverMisfiredJobsResult(boolean hasMoreMisfiredTriggers, int processedMisfiredTriggerCount, long earliestNewTime) {
            this._hasMoreMisfiredTriggers = hasMoreMisfiredTriggers;
            this._processedMisfiredTriggerCount = processedMisfiredTriggerCount;
            this._earliestNewTime = earliestNewTime;
        }

        public boolean hasMoreMisfiredTriggers() {
            return this._hasMoreMisfiredTriggers;
        }

        public int getProcessedMisfiredTriggerCount() {
            return this._processedMisfiredTriggerCount;
        }

        public long getEarliestNewTime() {
            return this._earliestNewTime;
        }
    }
}


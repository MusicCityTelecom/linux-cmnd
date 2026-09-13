/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.Connection
 *  javax.jms.JMSException
 *  javax.jms.MessageConsumer
 *  javax.jms.Session
 *  org.springframework.core.Constants
 *  org.springframework.core.task.SimpleAsyncTaskExecutor
 *  org.springframework.core.task.TaskExecutor
 *  org.springframework.lang.Nullable
 *  org.springframework.scheduling.SchedulingAwareRunnable
 *  org.springframework.scheduling.SchedulingTaskExecutor
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.backoff.BackOff
 *  org.springframework.util.backoff.BackOffExecution
 *  org.springframework.util.backoff.FixedBackOff
 */
package org.springframework.jms.listener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.springframework.core.Constants;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.JmsException;
import org.springframework.jms.listener.AbstractJmsListeningContainer;
import org.springframework.jms.listener.AbstractPollingMessageListenerContainer;
import org.springframework.jms.support.JmsUtils;
import org.springframework.jms.support.destination.CachingDestinationResolver;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.SchedulingAwareRunnable;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.FixedBackOff;

public class DefaultMessageListenerContainer
extends AbstractPollingMessageListenerContainer {
    public static final String DEFAULT_THREAD_NAME_PREFIX = ClassUtils.getShortName(DefaultMessageListenerContainer.class) + "-";
    public static final long DEFAULT_RECOVERY_INTERVAL = 5000L;
    public static final int CACHE_NONE = 0;
    public static final int CACHE_CONNECTION = 1;
    public static final int CACHE_SESSION = 2;
    public static final int CACHE_CONSUMER = 3;
    public static final int CACHE_AUTO = 4;
    private static final Constants constants = new Constants(DefaultMessageListenerContainer.class);
    @Nullable
    private Executor taskExecutor;
    private BackOff backOff = new FixedBackOff(5000L, Long.MAX_VALUE);
    private int cacheLevel = 4;
    private int concurrentConsumers = 1;
    private int maxConcurrentConsumers = 1;
    private int maxMessagesPerTask = Integer.MIN_VALUE;
    private int idleConsumerLimit = 1;
    private int idleTaskExecutionLimit = 1;
    private int idleReceivesPerTaskLimit = Integer.MIN_VALUE;
    private final Set<AsyncMessageListenerInvoker> scheduledInvokers = new HashSet<AsyncMessageListenerInvoker>();
    private int activeInvokerCount = 0;
    private int registeredWithDestination = 0;
    private volatile boolean recovering;
    private volatile boolean interrupted;
    @Nullable
    private Runnable stopCallback;
    private Object currentRecoveryMarker = new Object();
    private final Object recoveryMonitor = new Object();

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setBackOff(BackOff backOff) {
        this.backOff = backOff;
    }

    public void setRecoveryInterval(long recoveryInterval) {
        this.backOff = new FixedBackOff(recoveryInterval, Long.MAX_VALUE);
    }

    public void setCacheLevelName(String constantName) throws IllegalArgumentException {
        if (!constantName.startsWith("CACHE_")) {
            throw new IllegalArgumentException("Only cache constants allowed");
        }
        this.setCacheLevel(constants.asNumber(constantName).intValue());
    }

    public void setCacheLevel(int cacheLevel) {
        this.cacheLevel = cacheLevel;
    }

    public int getCacheLevel() {
        return this.cacheLevel;
    }

    @Override
    public void setConcurrency(String concurrency) {
        try {
            int separatorIndex = concurrency.indexOf(45);
            if (separatorIndex != -1) {
                this.setConcurrentConsumers(Integer.parseInt(concurrency.substring(0, separatorIndex)));
                this.setMaxConcurrentConsumers(Integer.parseInt(concurrency.substring(separatorIndex + 1)));
            } else {
                this.setConcurrentConsumers(1);
                this.setMaxConcurrentConsumers(Integer.parseInt(concurrency));
            }
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid concurrency value [" + concurrency + "]: only single maximum integer (e.g. \"5\") and minimum-maximum combo (e.g. \"3-5\") supported.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setConcurrentConsumers(int concurrentConsumers) {
        Assert.isTrue((concurrentConsumers > 0 ? 1 : 0) != 0, (String)"'concurrentConsumers' value must be at least 1 (one)");
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.concurrentConsumers = concurrentConsumers;
            if (this.maxConcurrentConsumers < concurrentConsumers) {
                this.maxConcurrentConsumers = concurrentConsumers;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final int getConcurrentConsumers() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.concurrentConsumers;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setMaxConcurrentConsumers(int maxConcurrentConsumers) {
        Assert.isTrue((maxConcurrentConsumers > 0 ? 1 : 0) != 0, (String)"'maxConcurrentConsumers' value must be at least 1 (one)");
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.maxConcurrentConsumers = Math.max(maxConcurrentConsumers, this.concurrentConsumers);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final int getMaxConcurrentConsumers() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.maxConcurrentConsumers;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setMaxMessagesPerTask(int maxMessagesPerTask) {
        Assert.isTrue((maxMessagesPerTask != 0 ? 1 : 0) != 0, (String)"'maxMessagesPerTask' must not be 0");
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.maxMessagesPerTask = maxMessagesPerTask;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final int getMaxMessagesPerTask() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.maxMessagesPerTask;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setIdleConsumerLimit(int idleConsumerLimit) {
        Assert.isTrue((idleConsumerLimit > 0 ? 1 : 0) != 0, (String)"'idleConsumerLimit' must be 1 or higher");
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.idleConsumerLimit = idleConsumerLimit;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final int getIdleConsumerLimit() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.idleConsumerLimit;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setIdleTaskExecutionLimit(int idleTaskExecutionLimit) {
        Assert.isTrue((idleTaskExecutionLimit > 0 ? 1 : 0) != 0, (String)"'idleTaskExecutionLimit' must be 1 or higher");
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.idleTaskExecutionLimit = idleTaskExecutionLimit;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final int getIdleTaskExecutionLimit() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.idleTaskExecutionLimit;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setIdleReceivesPerTaskLimit(int idleReceivesPerTaskLimit) {
        Assert.isTrue((idleReceivesPerTaskLimit != 0 ? 1 : 0) != 0, (String)"'idleReceivesPerTaskLimit' must not be 0)");
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.idleReceivesPerTaskLimit = idleReceivesPerTaskLimit;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getIdleReceivesPerTaskLimit() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.idleReceivesPerTaskLimit;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void initialize() {
        if (this.cacheLevel == 4) {
            this.cacheLevel = this.getTransactionManager() != null ? 0 : 3;
        }
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            if (this.taskExecutor == null) {
                this.taskExecutor = this.createDefaultTaskExecutor();
            } else if (this.taskExecutor instanceof SchedulingTaskExecutor && ((SchedulingTaskExecutor)this.taskExecutor).prefersShortLivedTasks() && this.maxMessagesPerTask == Integer.MIN_VALUE) {
                this.maxMessagesPerTask = 10;
            }
        }
        super.initialize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doInitialize() throws JMSException {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            for (int i = 0; i < this.concurrentConsumers; ++i) {
                this.scheduleNewInvoker();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doShutdown() throws JMSException {
        this.logger.debug((Object)"Waiting for shutdown of message listener invokers");
        try {
            Object object = this.lifecycleMonitor;
            synchronized (object) {
                long receiveTimeout = this.getReceiveTimeout();
                long waitStartTime = System.currentTimeMillis();
                int waitCount = 0;
                while (this.activeInvokerCount > 0) {
                    if (waitCount > 0 && !this.isAcceptMessagesWhileStopping() && System.currentTimeMillis() - waitStartTime >= receiveTimeout) {
                        for (AsyncMessageListenerInvoker scheduledInvoker : this.scheduledInvokers) {
                            scheduledInvoker.interruptIfNecessary();
                        }
                    }
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug((Object)("Still waiting for shutdown of " + this.activeInvokerCount + " message listener invokers (iteration " + waitCount + ")"));
                    }
                    if (receiveTimeout > 0L) {
                        this.lifecycleMonitor.wait(receiveTimeout);
                    } else {
                        this.lifecycleMonitor.wait();
                    }
                    ++waitCount;
                }
                for (AsyncMessageListenerInvoker scheduledInvoker : this.scheduledInvokers) {
                    scheduledInvoker.clearResources();
                }
                this.scheduledInvokers.clear();
            }
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() throws JmsException {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            this.stopCallback = null;
        }
        super.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stop(Runnable callback) throws JmsException {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            if (!this.isRunning() || this.stopCallback != null) {
                callback.run();
                return;
            }
            this.stopCallback = callback;
        }
        this.stop();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final int getScheduledConsumerCount() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.scheduledInvokers.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final int getActiveConsumerCount() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.activeInvokerCount;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isRegisteredWithDestination() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.registeredWithDestination > 0;
        }
    }

    protected TaskExecutor createDefaultTaskExecutor() {
        String beanName = this.getBeanName();
        String threadNamePrefix = beanName != null ? beanName + "-" : DEFAULT_THREAD_NAME_PREFIX;
        return new SimpleAsyncTaskExecutor(threadNamePrefix);
    }

    private void scheduleNewInvoker() {
        AsyncMessageListenerInvoker invoker = new AsyncMessageListenerInvoker();
        if (this.rescheduleTaskIfNecessary(invoker)) {
            this.scheduledInvokers.add(invoker);
        }
    }

    @Override
    protected final boolean sharedConnectionEnabled() {
        return this.getCacheLevel() >= 1;
    }

    @Override
    protected void doRescheduleTask(Object task) {
        Assert.state((this.taskExecutor != null ? 1 : 0) != 0, (String)"No TaskExecutor available");
        this.taskExecutor.execute((Runnable)task);
    }

    @Override
    protected void messageReceived(Object invoker, Session session) {
        ((AsyncMessageListenerInvoker)invoker).setIdle(false);
        this.scheduleNewInvokerIfAppropriate();
    }

    @Override
    protected void noMessageReceived(Object invoker, Session session) {
        ((AsyncMessageListenerInvoker)invoker).setIdle(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void scheduleNewInvokerIfAppropriate() {
        if (this.isRunning()) {
            this.resumePausedTasks();
            Object object = this.lifecycleMonitor;
            synchronized (object) {
                if (this.scheduledInvokers.size() < this.maxConcurrentConsumers && this.getIdleInvokerCount() < this.idleConsumerLimit) {
                    this.scheduleNewInvoker();
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug((Object)("Raised scheduled invoker count: " + this.scheduledInvokers.size()));
                    }
                }
            }
        }
    }

    private boolean shouldRescheduleInvoker(int idleTaskExecutionCount) {
        boolean superfluous = idleTaskExecutionCount >= this.idleTaskExecutionLimit && this.getIdleInvokerCount() > 1;
        return this.scheduledInvokers.size() <= (superfluous ? this.concurrentConsumers : this.maxConcurrentConsumers);
    }

    private int getIdleInvokerCount() {
        int count = 0;
        for (AsyncMessageListenerInvoker invoker : this.scheduledInvokers) {
            if (!invoker.isIdle()) continue;
            ++count;
        }
        return count;
    }

    @Override
    protected void establishSharedConnection() {
        try {
            super.establishSharedConnection();
        }
        catch (Exception ex) {
            if (ex instanceof JMSException) {
                this.invokeExceptionListener((JMSException)((Object)ex));
            }
            this.logger.debug((Object)"Could not establish shared JMS Connection - leaving it up to asynchronous invokers to establish a Connection as soon as possible", (Throwable)ex);
        }
    }

    @Override
    protected void startSharedConnection() {
        try {
            super.startSharedConnection();
        }
        catch (Exception ex) {
            this.logger.debug((Object)"Connection start failed - relying on listeners to perform recovery", (Throwable)ex);
        }
    }

    @Override
    protected void stopSharedConnection() {
        try {
            super.stopSharedConnection();
        }
        catch (Exception ex) {
            this.logger.debug((Object)"Connection stop failed - relying on listeners to perform recovery after restart", (Throwable)ex);
        }
    }

    protected void handleListenerSetupFailure(Throwable ex, boolean alreadyRecovered) {
        if (ex instanceof JMSException) {
            this.invokeExceptionListener((JMSException)ex);
        }
        if (ex instanceof AbstractJmsListeningContainer.SharedConnectionNotInitializedException) {
            if (!alreadyRecovered) {
                this.logger.debug((Object)"JMS message listener invoker needs to establish shared Connection");
            }
        } else if (alreadyRecovered) {
            this.logger.debug((Object)"Setup of JMS message listener invoker failed - already recovered by other invoker", ex);
        } else {
            StringBuilder msg = new StringBuilder();
            msg.append("Setup of JMS message listener invoker failed for destination '");
            msg.append(this.getDestinationDescription()).append("' - trying to recover. Cause: ");
            msg.append(ex instanceof JMSException ? JmsUtils.buildExceptionMessage((JMSException)ex) : ex.getMessage());
            if (this.logger.isDebugEnabled()) {
                this.logger.warn((Object)msg, ex);
            } else {
                this.logger.warn((Object)msg);
            }
        }
    }

    protected void recoverAfterListenerSetupFailure() {
        this.recovering = true;
        try {
            this.refreshConnectionUntilSuccessful();
            this.refreshDestination();
        }
        finally {
            this.recovering = false;
            this.interrupted = false;
        }
    }

    protected void refreshConnectionUntilSuccessful() {
        BackOffExecution execution = this.backOff.start();
        while (this.isRunning()) {
            try {
                if (this.sharedConnectionEnabled()) {
                    this.refreshSharedConnection();
                } else {
                    Connection con = this.createConnection();
                    JmsUtils.closeConnection(con);
                }
                this.logger.debug((Object)"Successfully refreshed JMS Connection");
                break;
            }
            catch (Exception ex) {
                if (ex instanceof JMSException) {
                    this.invokeExceptionListener((JMSException)((Object)ex));
                }
                StringBuilder msg = new StringBuilder();
                msg.append("Could not refresh JMS Connection for destination '");
                msg.append(this.getDestinationDescription()).append("' - retrying using ");
                msg.append(execution).append(". Cause: ");
                msg.append(ex instanceof JMSException ? JmsUtils.buildExceptionMessage((JMSException)((Object)ex)) : ex.getMessage());
                if (this.logger.isDebugEnabled()) {
                    this.logger.error((Object)msg, (Throwable)ex);
                } else {
                    this.logger.error((Object)msg);
                }
                if (this.applyBackOffTime(execution)) continue;
                this.logger.error((Object)("Stopping container for destination '" + this.getDestinationDescription() + "': back-off policy does not allow for further attempts."));
                this.stop();
            }
        }
    }

    protected void refreshDestination() {
        DestinationResolver destResolver;
        String destName = this.getDestinationName();
        if (destName != null && (destResolver = this.getDestinationResolver()) instanceof CachingDestinationResolver) {
            ((CachingDestinationResolver)destResolver).removeFromCache(destName);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected boolean applyBackOffTime(BackOffExecution execution) {
        block7: {
            if (this.recovering && this.interrupted) {
                return false;
            }
            long interval = execution.nextBackOff();
            if (interval == -1L) {
                return false;
            }
            try {
                Object object = this.lifecycleMonitor;
                synchronized (object) {
                    this.lifecycleMonitor.wait(interval);
                }
            }
            catch (InterruptedException interEx) {
                Thread.currentThread().interrupt();
                if (!this.recovering) break block7;
                this.interrupted = true;
            }
        }
        return true;
    }

    public final boolean isRecovering() {
        return this.recovering;
    }

    private class AsyncMessageListenerInvoker
    implements SchedulingAwareRunnable {
        @Nullable
        private Session session;
        @Nullable
        private MessageConsumer consumer;
        @Nullable
        private Object lastRecoveryMarker;
        private boolean lastMessageSucceeded;
        private int idleTaskExecutionCount = 0;
        private volatile boolean idle = true;
        @Nullable
        private volatile Thread currentReceiveThread;

        private AsyncMessageListenerInvoker() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void run() {
            Object object = DefaultMessageListenerContainer.this.lifecycleMonitor;
            synchronized (object) {
                DefaultMessageListenerContainer.this.activeInvokerCount++;
                DefaultMessageListenerContainer.this.lifecycleMonitor.notifyAll();
            }
            boolean messageReceived = false;
            try {
                int messageLimit = DefaultMessageListenerContainer.this.maxMessagesPerTask;
                int idleLimit = DefaultMessageListenerContainer.this.idleReceivesPerTaskLimit;
                if (messageLimit < 0 && idleLimit < 0) {
                    messageReceived = this.executeOngoingLoop();
                } else {
                    int idleCount = 0;
                    for (int messageCount = 0; !(!DefaultMessageListenerContainer.this.isRunning() || messageLimit >= 0 && messageCount >= messageLimit || idleLimit >= 0 && idleCount >= idleLimit); ++messageCount) {
                        boolean currentReceived = this.invokeListener();
                        messageReceived |= currentReceived;
                        idleCount = currentReceived ? 0 : idleCount + 1;
                    }
                }
            }
            catch (Throwable ex) {
                this.clearResources();
                if (!this.lastMessageSucceeded) {
                    this.waitBeforeRecoveryAttempt();
                }
                this.lastMessageSucceeded = false;
                boolean alreadyRecovered = false;
                Object object2 = DefaultMessageListenerContainer.this.recoveryMonitor;
                synchronized (object2) {
                    if (this.lastRecoveryMarker == DefaultMessageListenerContainer.this.currentRecoveryMarker) {
                        DefaultMessageListenerContainer.this.handleListenerSetupFailure(ex, false);
                        DefaultMessageListenerContainer.this.recoverAfterListenerSetupFailure();
                        DefaultMessageListenerContainer.this.currentRecoveryMarker = new Object();
                    } else {
                        alreadyRecovered = true;
                    }
                }
                if (alreadyRecovered) {
                    DefaultMessageListenerContainer.this.handleListenerSetupFailure(ex, true);
                }
            }
            finally {
                Object messageLimit = DefaultMessageListenerContainer.this.lifecycleMonitor;
                synchronized (messageLimit) {
                    this.decreaseActiveInvokerCount();
                    DefaultMessageListenerContainer.this.lifecycleMonitor.notifyAll();
                }
                this.idleTaskExecutionCount = !messageReceived ? ++this.idleTaskExecutionCount : 0;
                messageLimit = DefaultMessageListenerContainer.this.lifecycleMonitor;
                synchronized (messageLimit) {
                    if (!DefaultMessageListenerContainer.this.shouldRescheduleInvoker(this.idleTaskExecutionCount) || !DefaultMessageListenerContainer.this.rescheduleTaskIfNecessary(this)) {
                        DefaultMessageListenerContainer.this.scheduledInvokers.remove(this);
                        if (DefaultMessageListenerContainer.this.logger.isDebugEnabled()) {
                            DefaultMessageListenerContainer.this.logger.debug((Object)("Lowered scheduled invoker count: " + DefaultMessageListenerContainer.this.scheduledInvokers.size()));
                        }
                        DefaultMessageListenerContainer.this.lifecycleMonitor.notifyAll();
                        this.clearResources();
                    } else if (DefaultMessageListenerContainer.this.isRunning()) {
                        int nonPausedConsumers = DefaultMessageListenerContainer.this.getScheduledConsumerCount() - DefaultMessageListenerContainer.this.getPausedTaskCount();
                        if (nonPausedConsumers < 1) {
                            DefaultMessageListenerContainer.this.logger.error((Object)"All scheduled consumers have been paused, probably due to tasks having been rejected. Check your thread pool configuration! Manual recovery necessary through a start() call.");
                        } else if (nonPausedConsumers < DefaultMessageListenerContainer.this.getConcurrentConsumers()) {
                            DefaultMessageListenerContainer.this.logger.warn((Object)"Number of scheduled consumers has dropped below concurrentConsumers limit, probably due to tasks having been rejected. Check your thread pool configuration! Automatic recovery to be triggered by remaining consumers.");
                        }
                    }
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeOngoingLoop() throws JMSException {
            boolean messageReceived = false;
            boolean active = true;
            while (active) {
                Object object = DefaultMessageListenerContainer.this.lifecycleMonitor;
                synchronized (object) {
                    boolean interrupted = false;
                    boolean wasWaiting = false;
                    while ((active = DefaultMessageListenerContainer.this.isActive()) && !DefaultMessageListenerContainer.this.isRunning()) {
                        if (interrupted) {
                            throw new IllegalStateException("Thread was interrupted while waiting for a restart of the listener container, but container is still stopped");
                        }
                        if (!wasWaiting) {
                            this.decreaseActiveInvokerCount();
                        }
                        wasWaiting = true;
                        try {
                            DefaultMessageListenerContainer.this.lifecycleMonitor.wait();
                        }
                        catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                            interrupted = true;
                        }
                    }
                    if (wasWaiting) {
                        DefaultMessageListenerContainer.this.activeInvokerCount++;
                    }
                    if (DefaultMessageListenerContainer.this.scheduledInvokers.size() > DefaultMessageListenerContainer.this.maxConcurrentConsumers) {
                        active = false;
                    }
                }
                if (!active) continue;
                messageReceived = this.invokeListener() || messageReceived;
            }
            return messageReceived;
        }

        private boolean invokeListener() throws JMSException {
            this.currentReceiveThread = Thread.currentThread();
            try {
                this.initResourcesIfNecessary();
                boolean messageReceived = DefaultMessageListenerContainer.this.receiveAndExecute(this, this.session, this.consumer);
                this.lastMessageSucceeded = true;
                boolean bl = messageReceived;
                return bl;
            }
            finally {
                this.currentReceiveThread = null;
            }
        }

        private void decreaseActiveInvokerCount() {
            DefaultMessageListenerContainer.this.activeInvokerCount--;
            if (DefaultMessageListenerContainer.this.stopCallback != null && DefaultMessageListenerContainer.this.activeInvokerCount == 0) {
                DefaultMessageListenerContainer.this.stopCallback.run();
                DefaultMessageListenerContainer.this.stopCallback = null;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void initResourcesIfNecessary() throws JMSException {
            if (DefaultMessageListenerContainer.this.getCacheLevel() <= 1) {
                this.updateRecoveryMarker();
            } else {
                if (this.session == null && DefaultMessageListenerContainer.this.getCacheLevel() >= 2) {
                    this.updateRecoveryMarker();
                    this.session = DefaultMessageListenerContainer.this.createSession(DefaultMessageListenerContainer.this.getSharedConnection());
                }
                if (this.consumer == null && DefaultMessageListenerContainer.this.getCacheLevel() >= 3) {
                    this.consumer = DefaultMessageListenerContainer.this.createListenerConsumer(this.session);
                    Object object = DefaultMessageListenerContainer.this.lifecycleMonitor;
                    synchronized (object) {
                        DefaultMessageListenerContainer.this.registeredWithDestination++;
                    }
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void updateRecoveryMarker() {
            Object object = DefaultMessageListenerContainer.this.recoveryMonitor;
            synchronized (object) {
                this.lastRecoveryMarker = DefaultMessageListenerContainer.this.currentRecoveryMarker;
            }
        }

        private void interruptIfNecessary() {
            Thread currentReceiveThread = this.currentReceiveThread;
            if (currentReceiveThread != null && !currentReceiveThread.isInterrupted()) {
                currentReceiveThread.interrupt();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void clearResources() {
            Object object;
            if (DefaultMessageListenerContainer.this.sharedConnectionEnabled()) {
                object = DefaultMessageListenerContainer.this.sharedConnectionMonitor;
                synchronized (object) {
                    JmsUtils.closeMessageConsumer(this.consumer);
                    JmsUtils.closeSession(this.session);
                }
            } else {
                JmsUtils.closeMessageConsumer(this.consumer);
                JmsUtils.closeSession(this.session);
            }
            if (this.consumer != null) {
                object = DefaultMessageListenerContainer.this.lifecycleMonitor;
                synchronized (object) {
                    DefaultMessageListenerContainer.this.registeredWithDestination--;
                }
            }
            this.consumer = null;
            this.session = null;
        }

        private void waitBeforeRecoveryAttempt() {
            BackOffExecution execution = DefaultMessageListenerContainer.this.backOff.start();
            DefaultMessageListenerContainer.this.applyBackOffTime(execution);
        }

        public boolean isLongLived() {
            return DefaultMessageListenerContainer.this.maxMessagesPerTask < 0;
        }

        public void setIdle(boolean idle) {
            this.idle = idle;
        }

        public boolean isIdle() {
            return this.idle;
        }
    }
}


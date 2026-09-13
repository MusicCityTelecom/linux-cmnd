/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.context.ApplicationEventPublisher
 *  org.springframework.context.ApplicationEventPublisherAware
 *  org.springframework.context.SmartLifecycle
 *  org.springframework.scheduling.concurrent.CustomizableThreadFactory
 *  org.springframework.util.Assert
 */
package org.springframework.integration.support.leader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.integration.leader.Candidate;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.DefaultCandidate;
import org.springframework.integration.leader.event.DefaultLeaderEventPublisher;
import org.springframework.integration.leader.event.LeaderEventPublisher;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.util.Assert;

public class LockRegistryLeaderInitiator
implements SmartLifecycle,
DisposableBean,
ApplicationEventPublisherAware {
    public static final long DEFAULT_HEART_BEAT_TIME = 500L;
    public static final long DEFAULT_BUSY_WAIT_TIME = 50L;
    private static final Log LOGGER = LogFactory.getLog(LockRegistryLeaderInitiator.class);
    private final Object lifecycleMonitor = new Object();
    private final LockRegistry locks;
    private final Candidate candidate;
    private final Context nullContext = new Context(){

        @Override
        public boolean isLeader() {
            return false;
        }

        @Override
        public String getRole() {
            return LockRegistryLeaderInitiator.this.candidate.getRole();
        }
    };
    private ExecutorService executorService = Executors.newSingleThreadExecutor((ThreadFactory)new CustomizableThreadFactory("lock-leadership-"));
    private boolean executorServiceExplicitlySet;
    private long heartBeatMillis = 500L;
    private boolean publishFailedEvents = false;
    private LeaderSelector leaderSelector;
    private ApplicationEventPublisher applicationEventPublisher;
    private LeaderEventPublisher leaderEventPublisher;
    private boolean autoStartup = true;
    private int phase = 2147482647;
    private volatile long busyWaitMillis = 50L;
    private volatile boolean running;
    private volatile Future<?> future;

    public LockRegistryLeaderInitiator(LockRegistry locks) {
        this(locks, new DefaultCandidate());
    }

    public LockRegistryLeaderInitiator(LockRegistry locks, Candidate candidate) {
        Assert.notNull((Object)locks, (String)"'locks' must not be null");
        Assert.notNull((Object)candidate, (String)"'candidate' must not be null");
        this.locks = locks;
        this.candidate = candidate;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
        this.executorServiceExplicitlySet = true;
    }

    public void setHeartBeatMillis(long heartBeatMillis) {
        this.heartBeatMillis = heartBeatMillis;
    }

    public void setBusyWaitMillis(long busyWaitMillis) {
        this.busyWaitMillis = busyWaitMillis;
    }

    public void setLeaderEventPublisher(LeaderEventPublisher leaderEventPublisher) {
        this.leaderEventPublisher = leaderEventPublisher;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isRunning() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            return this.running;
        }
    }

    public int getPhase() {
        return this.phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public boolean isAutoStartup() {
        return this.autoStartup;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public Context getContext() {
        if (this.leaderSelector == null) {
            return this.nullContext;
        }
        return this.leaderSelector.context;
    }

    public boolean isPublishFailedEvents() {
        return this.publishFailedEvents;
    }

    public void setPublishFailedEvents(boolean publishFailedEvents) {
        this.publishFailedEvents = publishFailedEvents;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void start() {
        if (this.leaderEventPublisher == null && this.applicationEventPublisher != null) {
            this.leaderEventPublisher = new DefaultLeaderEventPublisher(this.applicationEventPublisher);
        }
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            if (!this.running) {
                this.leaderSelector = new LeaderSelector(this.buildLeaderPath());
                this.running = true;
                this.future = this.executorService.submit(this.leaderSelector);
                LOGGER.debug((Object)"Started LeaderInitiator");
            }
        }
    }

    public void destroy() {
        this.stop();
        if (!this.executorServiceExplicitlySet) {
            this.executorService.shutdown();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stop() {
        Object object = this.lifecycleMonitor;
        synchronized (object) {
            if (this.running) {
                this.running = false;
                if (this.future != null) {
                    this.future.cancel(true);
                }
                this.future = null;
                LOGGER.debug((Object)("Stopped LeaderInitiator for " + this.getContext()));
            }
        }
    }

    private String buildLeaderPath() {
        return this.candidate.getRole();
    }

    protected class LeaderSelector
    implements Callable<Void> {
        private final Lock lock;
        private final String lockKey;
        private final LockContext context;
        private volatile boolean locked;

        LeaderSelector(String lockKey) {
            this.context = new LockContext();
            this.locked = false;
            this.lock = LockRegistryLeaderInitiator.this.locks.obtain(lockKey);
            this.lockKey = lockKey;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Void call() {
            block12: while (true) {
                while (LockRegistryLeaderInitiator.this.isRunning()) {
                    try {
                        this.tryAcquireLock();
                    }
                    catch (Exception e) {
                        if (this.handleLockException(e)) {
                            Void void_ = null;
                            if (!this.locked) return void_;
                            this.locked = false;
                            try {
                                this.lock.unlock();
                            }
                            catch (Exception e2) {
                                LOGGER.debug((Object)("Could not unlock during stop for " + this.context + " - treat as broken. Revoking..."), (Throwable)e2);
                            }
                            this.handleRevoked();
                            return void_;
                        }
                        try {
                            continue block12;
                        }
                        catch (Throwable throwable) {
                            throw throwable;
                            return null;
                        }
                    }
                }
            }
            finally {
                if (this.locked) {
                    this.locked = false;
                    try {
                        this.lock.unlock();
                    }
                    catch (Exception e) {
                        LOGGER.debug((Object)("Could not unlock during stop for " + this.context + " - treat as broken. Revoking..."), (Throwable)e);
                    }
                    this.handleRevoked();
                }
            }
        }

        private void tryAcquireLock() throws InterruptedException {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Acquiring the lock for " + this.context));
            }
            boolean acquired = this.lock.tryLock(LockRegistryLeaderInitiator.this.heartBeatMillis, TimeUnit.MILLISECONDS);
            if (!this.locked) {
                if (acquired) {
                    this.locked = true;
                    this.handleGranted();
                } else if (LockRegistryLeaderInitiator.this.isPublishFailedEvents()) {
                    this.publishFailedToAcquire();
                }
            } else if (acquired) {
                this.lock.unlock();
                if (LockRegistryLeaderInitiator.this.isRunning()) {
                    Thread.sleep(LockRegistryLeaderInitiator.this.heartBeatMillis);
                }
            } else {
                this.locked = false;
                this.handleRevoked();
                if (LockRegistryLeaderInitiator.this.isRunning()) {
                    Thread.sleep(LockRegistryLeaderInitiator.this.busyWaitMillis);
                }
            }
        }

        private boolean handleLockException(Exception ex) {
            if (this.locked) {
                this.locked = false;
                try {
                    this.lock.unlock();
                }
                catch (Exception e1) {
                    LOGGER.debug((Object)("Could not unlock - treat as broken " + this.context + ". Revoking " + (LockRegistryLeaderInitiator.this.isRunning() ? " and retrying..." : "...")), (Throwable)e1);
                }
                this.handleRevoked();
            }
            if (ex instanceof InterruptedException || Thread.currentThread().isInterrupted()) {
                Thread.currentThread().interrupt();
                if (LockRegistryLeaderInitiator.this.isRunning()) {
                    this.restartSelectorBecauseOfError(ex);
                }
                return true;
            }
            if (LockRegistryLeaderInitiator.this.isRunning()) {
                try {
                    Thread.sleep(LockRegistryLeaderInitiator.this.busyWaitMillis);
                }
                catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Error acquiring the lock for " + this.context + ". " + (LockRegistryLeaderInitiator.this.isRunning() ? "Retrying..." : "")), (Throwable)ex);
            }
            return false;
        }

        private void restartSelectorBecauseOfError(Exception ex) {
            LOGGER.warn((Object)("Restarting LeaderSelector for " + this.context + " because of error."), (Throwable)ex);
            LockRegistryLeaderInitiator.this.future = LockRegistryLeaderInitiator.this.executorService.submit(() -> {
                Thread.sleep(LockRegistryLeaderInitiator.this.busyWaitMillis);
                return this.call();
            });
        }

        public boolean isLeader() {
            return this.locked;
        }

        private void handleGranted() throws InterruptedException {
            LockRegistryLeaderInitiator.this.candidate.onGranted(this.context);
            if (LockRegistryLeaderInitiator.this.leaderEventPublisher != null) {
                try {
                    LockRegistryLeaderInitiator.this.leaderEventPublisher.publishOnGranted(LockRegistryLeaderInitiator.this, this.context, this.lockKey);
                }
                catch (Exception e) {
                    LOGGER.warn((Object)"Error publishing OnGranted event.", (Throwable)e);
                }
            }
        }

        private void handleRevoked() {
            LockRegistryLeaderInitiator.this.candidate.onRevoked(this.context);
            if (LockRegistryLeaderInitiator.this.leaderEventPublisher != null) {
                try {
                    LockRegistryLeaderInitiator.this.leaderEventPublisher.publishOnRevoked(LockRegistryLeaderInitiator.this, this.context, LockRegistryLeaderInitiator.this.candidate.getRole());
                }
                catch (Exception e) {
                    LOGGER.warn((Object)"Error publishing OnRevoked event.", (Throwable)e);
                }
            }
        }

        private void publishFailedToAcquire() {
            if (LockRegistryLeaderInitiator.this.leaderEventPublisher != null) {
                try {
                    LockRegistryLeaderInitiator.this.leaderEventPublisher.publishOnFailedToAcquire(LockRegistryLeaderInitiator.this, this.context, LockRegistryLeaderInitiator.this.candidate.getRole());
                }
                catch (Exception e) {
                    LOGGER.warn((Object)"Error publishing OnFailedToAcquire event.", (Throwable)e);
                }
            }
        }
    }

    private class LockContext
    implements Context {
        LockContext() {
        }

        @Override
        public boolean isLeader() {
            return LockRegistryLeaderInitiator.this.leaderSelector.isLeader();
        }

        @Override
        public void yield() {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug((Object)("Yielding leadership from " + this));
            }
            if (LockRegistryLeaderInitiator.this.future != null) {
                LockRegistryLeaderInitiator.this.future.cancel(true);
            }
        }

        @Override
        public String getRole() {
            return LockRegistryLeaderInitiator.this.candidate.getRole();
        }

        public String toString() {
            return "LockContext{role=" + LockRegistryLeaderInitiator.this.candidate.getRole() + ", id=" + LockRegistryLeaderInitiator.this.candidate.getId() + ", isLeader=" + this.isLeader() + "}";
        }
    }
}


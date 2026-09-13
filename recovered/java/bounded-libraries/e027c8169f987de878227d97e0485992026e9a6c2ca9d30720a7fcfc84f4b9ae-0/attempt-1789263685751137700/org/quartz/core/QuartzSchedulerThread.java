/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import org.quartz.JobPersistenceException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.core.JobRunShell;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.QuartzSchedulerResources;
import org.quartz.spi.JobStore;
import org.quartz.spi.OperableTrigger;
import org.quartz.spi.TriggerFiredBundle;
import org.quartz.spi.TriggerFiredResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzSchedulerThread
extends Thread {
    private QuartzScheduler qs;
    private QuartzSchedulerResources qsRsrcs;
    private final Object sigLock = new Object();
    private boolean signaled;
    private long signaledNextFireTime;
    private boolean paused;
    private AtomicBoolean halted;
    private Random random = new Random(System.currentTimeMillis());
    private static long DEFAULT_IDLE_WAIT_TIME = 30000L;
    private long idleWaitTime = DEFAULT_IDLE_WAIT_TIME;
    private int idleWaitVariablness = 7000;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final long MIN_DELAY = 20L;
    private static final long MAX_DELAY = 600000L;

    QuartzSchedulerThread(QuartzScheduler qs, QuartzSchedulerResources qsRsrcs) {
        this(qs, qsRsrcs, qsRsrcs.getMakeSchedulerThreadDaemon(), 5);
    }

    QuartzSchedulerThread(QuartzScheduler qs, QuartzSchedulerResources qsRsrcs, boolean setDaemon, int threadPrio) {
        super(qs.getSchedulerThreadGroup(), qsRsrcs.getThreadName());
        this.qs = qs;
        this.qsRsrcs = qsRsrcs;
        this.setDaemon(setDaemon);
        if (qsRsrcs.isThreadsInheritInitializersClassLoadContext()) {
            this.log.info("QuartzSchedulerThread Inheriting ContextClassLoader of thread: " + Thread.currentThread().getName());
            this.setContextClassLoader(Thread.currentThread().getContextClassLoader());
        }
        this.setPriority(threadPrio);
        this.paused = true;
        this.halted = new AtomicBoolean(false);
    }

    void setIdleWaitTime(long waitTime) {
        this.idleWaitTime = waitTime;
        this.idleWaitVariablness = (int)((double)waitTime * 0.2);
    }

    private long getRandomizedIdleWaitTime() {
        return this.idleWaitTime - (long)this.random.nextInt(this.idleWaitVariablness);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void togglePause(boolean pause) {
        Object object = this.sigLock;
        synchronized (object) {
            this.paused = pause;
            if (this.paused) {
                this.signalSchedulingChange(0L);
            } else {
                this.sigLock.notifyAll();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void halt(boolean wait) {
        Object object = this.sigLock;
        synchronized (object) {
            this.halted.set(true);
            if (this.paused) {
                this.sigLock.notifyAll();
            } else {
                this.signalSchedulingChange(0L);
            }
        }
        if (wait) {
            boolean interrupted = false;
            try {
                while (true) {
                    try {
                        this.join();
                    }
                    catch (InterruptedException _) {
                        interrupted = true;
                        continue;
                    }
                    break;
                }
            }
            finally {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    boolean isPaused() {
        return this.paused;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void signalSchedulingChange(long candidateNewNextFireTime) {
        Object object = this.sigLock;
        synchronized (object) {
            this.signaled = true;
            this.signaledNextFireTime = candidateNewNextFireTime;
            this.sigLock.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clearSignaledSchedulingChange() {
        Object object = this.sigLock;
        synchronized (object) {
            this.signaled = false;
            this.signaledNextFireTime = 0L;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isScheduleChanged() {
        Object object = this.sigLock;
        synchronized (object) {
            return this.signaled;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long getSignaledNextFireTime() {
        Object object = this.sigLock;
        synchronized (object) {
            return this.signaledNextFireTime;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        int acquiresFailed = 0;
        while (!this.halted.get()) {
            try {
                List<OperableTrigger> triggers;
                long now;
                block46: {
                    int availThreadCount;
                    Object object = this.sigLock;
                    synchronized (object) {
                        while (this.paused && !this.halted.get()) {
                            try {
                                this.sigLock.wait(1000L);
                            }
                            catch (InterruptedException interruptedException) {
                                // empty catch block
                            }
                            acquiresFailed = 0;
                        }
                        if (this.halted.get()) {
                            break;
                        }
                    }
                    if (acquiresFailed > 1) {
                        try {
                            long delay = QuartzSchedulerThread.computeDelayForRepeatedErrors(this.qsRsrcs.getJobStore(), acquiresFailed);
                            Thread.sleep(delay);
                        }
                        catch (Exception delay) {
                            // empty catch block
                        }
                    }
                    if ((availThreadCount = this.qsRsrcs.getThreadPool().blockForAvailableThreads()) <= 0) continue;
                    now = System.currentTimeMillis();
                    this.clearSignaledSchedulingChange();
                    try {
                        triggers = this.qsRsrcs.getJobStore().acquireNextTriggers(now + this.idleWaitTime, Math.min(availThreadCount, this.qsRsrcs.getMaxBatchSize()), this.qsRsrcs.getBatchTimeWindow());
                        acquiresFailed = 0;
                        if (!this.log.isDebugEnabled()) break block46;
                        this.log.debug("batch acquisition of " + (triggers == null ? 0 : triggers.size()) + " triggers");
                    }
                    catch (JobPersistenceException jpe) {
                        if (acquiresFailed == 0) {
                            this.qs.notifySchedulerListenersError("An error occurred while scanning for the next triggers to fire.", jpe);
                        }
                        if (acquiresFailed >= Integer.MAX_VALUE) continue;
                        ++acquiresFailed;
                        continue;
                    }
                    catch (RuntimeException e) {
                        if (acquiresFailed == 0) {
                            this.getLog().error("quartzSchedulerThreadLoop: RuntimeException " + e.getMessage(), (Throwable)e);
                        }
                        if (acquiresFailed >= Integer.MAX_VALUE) continue;
                        ++acquiresFailed;
                        continue;
                    }
                }
                if (triggers != null && !triggers.isEmpty()) {
                    List<Object> bndles;
                    block47: {
                        now = System.currentTimeMillis();
                        long triggerTime = triggers.get(0).getNextFireTime().getTime();
                        long timeUntilTrigger = triggerTime - now;
                        while (timeUntilTrigger > 2L) {
                            Object object = this.sigLock;
                            synchronized (object) {
                                if (this.halted.get()) {
                                    break;
                                }
                                if (!this.isCandidateNewTimeEarlierWithinReason(triggerTime, false)) {
                                    try {
                                        now = System.currentTimeMillis();
                                        timeUntilTrigger = triggerTime - now;
                                        if (timeUntilTrigger >= 1L) {
                                            this.sigLock.wait(timeUntilTrigger);
                                        }
                                    }
                                    catch (InterruptedException interruptedException) {
                                        // empty catch block
                                    }
                                }
                            }
                            if (this.releaseIfScheduleChangedSignificantly(triggers, triggerTime)) break;
                            now = System.currentTimeMillis();
                            timeUntilTrigger = triggerTime - now;
                        }
                        if (triggers.isEmpty()) continue;
                        bndles = new ArrayList();
                        boolean goAhead = true;
                        Object object = this.sigLock;
                        synchronized (object) {
                            goAhead = !this.halted.get();
                        }
                        if (goAhead) {
                            try {
                                List<TriggerFiredResult> res = this.qsRsrcs.getJobStore().triggersFired(triggers);
                                if (res == null) break block47;
                                bndles = res;
                            }
                            catch (SchedulerException se) {
                                this.qs.notifySchedulerListenersError("An error occurred while firing triggers '" + triggers + "'", se);
                                for (int i = 0; i < triggers.size(); ++i) {
                                    this.qsRsrcs.getJobStore().releaseAcquiredTrigger(triggers.get(i));
                                }
                                continue;
                            }
                        }
                    }
                    for (int i = 0; i < bndles.size(); ++i) {
                        TriggerFiredResult result = (TriggerFiredResult)bndles.get(i);
                        TriggerFiredBundle bndle = result.getTriggerFiredBundle();
                        Exception exception = result.getException();
                        if (exception instanceof RuntimeException) {
                            this.getLog().error("RuntimeException while firing trigger " + triggers.get(i), (Throwable)exception);
                            this.qsRsrcs.getJobStore().releaseAcquiredTrigger(triggers.get(i));
                            continue;
                        }
                        if (bndle == null) {
                            this.qsRsrcs.getJobStore().releaseAcquiredTrigger(triggers.get(i));
                            continue;
                        }
                        JobRunShell shell = null;
                        try {
                            shell = this.qsRsrcs.getJobRunShellFactory().createJobRunShell(bndle);
                            shell.initialize(this.qs);
                        }
                        catch (SchedulerException se) {
                            this.qsRsrcs.getJobStore().triggeredJobComplete(triggers.get(i), bndle.getJobDetail(), Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR);
                            continue;
                        }
                        if (this.qsRsrcs.getThreadPool().runInThread(shell)) continue;
                        this.getLog().error("ThreadPool.runInThread() return false!");
                        this.qsRsrcs.getJobStore().triggeredJobComplete(triggers.get(i), bndle.getJobDetail(), Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR);
                    }
                    continue;
                }
                long now2 = System.currentTimeMillis();
                long waitTime = now2 + this.getRandomizedIdleWaitTime();
                long timeUntilContinue = waitTime - now2;
                Object object = this.sigLock;
                synchronized (object) {
                    try {
                        if (!this.halted.get() && !this.isScheduleChanged()) {
                            this.sigLock.wait(timeUntilContinue);
                        }
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            }
            catch (RuntimeException re) {
                this.getLog().error("Runtime error occurred in main trigger firing loop.", (Throwable)re);
            }
        }
        this.qs = null;
        this.qsRsrcs = null;
    }

    private static long computeDelayForRepeatedErrors(JobStore jobStore, int acquiresFailed) {
        long delay;
        try {
            delay = jobStore.getAcquireRetryDelay(acquiresFailed);
        }
        catch (Exception ignored) {
            delay = 100L;
        }
        if (delay < 20L) {
            delay = 20L;
        }
        if (delay > 600000L) {
            delay = 600000L;
        }
        return delay;
    }

    private boolean releaseIfScheduleChangedSignificantly(List<OperableTrigger> triggers, long triggerTime) {
        if (this.isCandidateNewTimeEarlierWithinReason(triggerTime, true)) {
            for (OperableTrigger trigger : triggers) {
                this.qsRsrcs.getJobStore().releaseAcquiredTrigger(trigger);
            }
            triggers.clear();
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean isCandidateNewTimeEarlierWithinReason(long oldTime, boolean clearSignal) {
        Object object = this.sigLock;
        synchronized (object) {
            long diff;
            if (!this.isScheduleChanged()) {
                return false;
            }
            boolean earlier = false;
            if (this.getSignaledNextFireTime() == 0L) {
                earlier = true;
            } else if (this.getSignaledNextFireTime() < oldTime) {
                earlier = true;
            }
            if (earlier && (diff = oldTime - System.currentTimeMillis()) < (this.qsRsrcs.getJobStore().supportsPersistence() ? 70L : 7L)) {
                earlier = false;
            }
            if (clearSignal) {
                this.clearSignaledSchedulingChange();
            }
            return earlier;
        }
    }

    public Logger getLog() {
        return this.log;
    }
}


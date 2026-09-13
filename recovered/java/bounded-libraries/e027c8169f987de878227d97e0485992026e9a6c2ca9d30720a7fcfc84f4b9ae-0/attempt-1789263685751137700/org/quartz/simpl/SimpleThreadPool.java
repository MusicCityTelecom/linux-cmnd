/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.quartz.simpl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.quartz.SchedulerConfigException;
import org.quartz.spi.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleThreadPool
implements ThreadPool {
    private int count = -1;
    private int prio = 5;
    private boolean isShutdown = false;
    private boolean handoffPending = false;
    private boolean inheritLoader = false;
    private boolean inheritGroup = true;
    private boolean makeThreadsDaemons = false;
    private ThreadGroup threadGroup;
    private final Object nextRunnableLock = new Object();
    private List<WorkerThread> workers;
    private LinkedList<WorkerThread> availWorkers = new LinkedList();
    private LinkedList<WorkerThread> busyWorkers = new LinkedList();
    private String threadNamePrefix;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String schedulerInstanceName;

    public SimpleThreadPool() {
    }

    public SimpleThreadPool(int threadCount, int threadPriority) {
        this.setThreadCount(threadCount);
        this.setThreadPriority(threadPriority);
    }

    public Logger getLog() {
        return this.log;
    }

    @Override
    public int getPoolSize() {
        return this.getThreadCount();
    }

    public void setThreadCount(int count) {
        this.count = count;
    }

    public int getThreadCount() {
        return this.count;
    }

    public void setThreadPriority(int prio) {
        this.prio = prio;
    }

    public int getThreadPriority() {
        return this.prio;
    }

    public void setThreadNamePrefix(String prfx) {
        this.threadNamePrefix = prfx;
    }

    public String getThreadNamePrefix() {
        return this.threadNamePrefix;
    }

    public boolean isThreadsInheritContextClassLoaderOfInitializingThread() {
        return this.inheritLoader;
    }

    public void setThreadsInheritContextClassLoaderOfInitializingThread(boolean inheritLoader) {
        this.inheritLoader = inheritLoader;
    }

    public boolean isThreadsInheritGroupOfInitializingThread() {
        return this.inheritGroup;
    }

    public void setThreadsInheritGroupOfInitializingThread(boolean inheritGroup) {
        this.inheritGroup = inheritGroup;
    }

    public boolean isMakeThreadsDaemons() {
        return this.makeThreadsDaemons;
    }

    public void setMakeThreadsDaemons(boolean makeThreadsDaemons) {
        this.makeThreadsDaemons = makeThreadsDaemons;
    }

    @Override
    public void setInstanceId(String schedInstId) {
    }

    @Override
    public void setInstanceName(String schedName) {
        this.schedulerInstanceName = schedName;
    }

    @Override
    public void initialize() throws SchedulerConfigException {
        if (this.workers != null && this.workers.size() > 0) {
            return;
        }
        if (this.count <= 0) {
            throw new SchedulerConfigException("Thread count must be > 0");
        }
        if (this.prio <= 0 || this.prio > 9) {
            throw new SchedulerConfigException("Thread priority must be > 0 and <= 9");
        }
        if (this.isThreadsInheritGroupOfInitializingThread()) {
            this.threadGroup = Thread.currentThread().getThreadGroup();
        } else {
            ThreadGroup parent = this.threadGroup = Thread.currentThread().getThreadGroup();
            while (!parent.getName().equals("main")) {
                this.threadGroup = parent;
                parent = this.threadGroup.getParent();
            }
            this.threadGroup = new ThreadGroup(parent, this.schedulerInstanceName + "-SimpleThreadPool");
            if (this.isMakeThreadsDaemons()) {
                this.threadGroup.setDaemon(true);
            }
        }
        if (this.isThreadsInheritContextClassLoaderOfInitializingThread()) {
            this.getLog().info("Job execution threads will use class loader of thread: " + Thread.currentThread().getName());
        }
        for (WorkerThread wt : this.createWorkerThreads(this.count)) {
            wt.start();
            this.availWorkers.add(wt);
        }
    }

    protected List<WorkerThread> createWorkerThreads(int createCount) {
        this.workers = new LinkedList<WorkerThread>();
        for (int i = 1; i <= createCount; ++i) {
            String threadPrefix = this.getThreadNamePrefix();
            if (threadPrefix == null) {
                threadPrefix = this.schedulerInstanceName + "_Worker";
            }
            WorkerThread wt = new WorkerThread(this, this.threadGroup, threadPrefix + "-" + i, this.getThreadPriority(), this.isMakeThreadsDaemons());
            if (this.isThreadsInheritContextClassLoaderOfInitializingThread()) {
                wt.setContextClassLoader(Thread.currentThread().getContextClassLoader());
            }
            this.workers.add(wt);
        }
        return this.workers;
    }

    public void shutdown() {
        this.shutdown(true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown(boolean waitForJobsToComplete) {
        Object object = this.nextRunnableLock;
        synchronized (object) {
            this.getLog().debug("Shutting down threadpool...");
            this.isShutdown = true;
            if (this.workers == null) {
                return;
            }
            for (WorkerThread wt : this.workers) {
                wt.shutdown();
                this.availWorkers.remove(wt);
            }
            this.nextRunnableLock.notifyAll();
            if (waitForJobsToComplete) {
                boolean interrupted = false;
                try {
                    WorkerThread wt;
                    while (this.handoffPending) {
                        try {
                            this.nextRunnableLock.wait(100L);
                        }
                        catch (InterruptedException _) {
                            interrupted = true;
                        }
                    }
                    while (this.busyWorkers.size() > 0) {
                        wt = this.busyWorkers.getFirst();
                        try {
                            this.getLog().debug("Waiting for thread " + wt.getName() + " to shut down");
                            this.nextRunnableLock.wait(2000L);
                        }
                        catch (InterruptedException _) {
                            interrupted = true;
                        }
                    }
                    Iterator<WorkerThread> workerThreads = this.workers.iterator();
                    while (workerThreads.hasNext()) {
                        wt = workerThreads.next();
                        try {
                            wt.join();
                            workerThreads.remove();
                        }
                        catch (InterruptedException _) {
                            interrupted = true;
                        }
                    }
                }
                finally {
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                }
                this.getLog().debug("No executing jobs remaining, all threads stopped.");
            }
            this.getLog().debug("Shutdown of threadpool complete.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean runInThread(Runnable runnable) {
        if (runnable == null) {
            return false;
        }
        Object object = this.nextRunnableLock;
        synchronized (object) {
            this.handoffPending = true;
            while (this.availWorkers.size() < 1 && !this.isShutdown) {
                try {
                    this.nextRunnableLock.wait(500L);
                }
                catch (InterruptedException interruptedException) {}
            }
            if (!this.isShutdown) {
                WorkerThread wt = this.availWorkers.removeFirst();
                this.busyWorkers.add(wt);
                wt.run(runnable);
            } else {
                WorkerThread wt = new WorkerThread(this, this.threadGroup, "WorkerThread-LastJob", this.prio, this.isMakeThreadsDaemons(), runnable);
                this.busyWorkers.add(wt);
                this.workers.add(wt);
                wt.start();
            }
            this.nextRunnableLock.notifyAll();
            this.handoffPending = false;
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int blockForAvailableThreads() {
        Object object = this.nextRunnableLock;
        synchronized (object) {
            while ((this.availWorkers.size() < 1 || this.handoffPending) && !this.isShutdown) {
                try {
                    this.nextRunnableLock.wait(500L);
                }
                catch (InterruptedException interruptedException) {}
            }
            return this.availWorkers.size();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void makeAvailable(WorkerThread wt) {
        Object object = this.nextRunnableLock;
        synchronized (object) {
            if (!this.isShutdown) {
                this.availWorkers.add(wt);
            }
            this.busyWorkers.remove(wt);
            this.nextRunnableLock.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void clearFromBusyWorkersList(WorkerThread wt) {
        Object object = this.nextRunnableLock;
        synchronized (object) {
            this.busyWorkers.remove(wt);
            this.nextRunnableLock.notifyAll();
        }
    }

    class WorkerThread
    extends Thread {
        private final Object lock;
        private AtomicBoolean run;
        private SimpleThreadPool tp;
        private Runnable runnable;
        private boolean runOnce;

        WorkerThread(SimpleThreadPool tp, ThreadGroup threadGroup, String name, int prio, boolean isDaemon) {
            this(tp, threadGroup, name, prio, isDaemon, null);
        }

        WorkerThread(SimpleThreadPool tp, ThreadGroup threadGroup, String name, int prio, boolean isDaemon, Runnable runnable) {
            super(threadGroup, name);
            this.lock = new Object();
            this.run = new AtomicBoolean(true);
            this.runnable = null;
            this.runOnce = false;
            this.tp = tp;
            this.runnable = runnable;
            if (runnable != null) {
                this.runOnce = true;
            }
            this.setPriority(prio);
            this.setDaemon(isDaemon);
        }

        void shutdown() {
            this.run.set(false);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void run(Runnable newRunnable) {
            Object object = this.lock;
            synchronized (object) {
                if (this.runnable != null) {
                    throw new IllegalStateException("Already running a Runnable!");
                }
                this.runnable = newRunnable;
                this.lock.notifyAll();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            boolean ran = false;
            while (this.run.get()) {
                try {
                    Object object = this.lock;
                    synchronized (object) {
                        while (this.runnable == null && this.run.get()) {
                            this.lock.wait(500L);
                        }
                        if (this.runnable != null) {
                            ran = true;
                            this.runnable.run();
                        }
                    }
                }
                catch (InterruptedException unblock) {
                    try {
                        SimpleThreadPool.this.getLog().error("Worker thread was interrupt()'ed.", (Throwable)unblock);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                catch (Throwable exceptionInRunnable) {
                    try {
                        SimpleThreadPool.this.getLog().error("Error while executing the Runnable: ", exceptionInRunnable);
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                finally {
                    Object unblock = this.lock;
                    synchronized (unblock) {
                        this.runnable = null;
                    }
                    if (this.getPriority() != this.tp.getThreadPriority()) {
                        this.setPriority(this.tp.getThreadPriority());
                    }
                    if (this.runOnce) {
                        this.run.set(false);
                        SimpleThreadPool.this.clearFromBusyWorkersList(this);
                        continue;
                    }
                    if (!ran) continue;
                    ran = false;
                    SimpleThreadPool.this.makeAvailable(this);
                }
            }
            try {
                SimpleThreadPool.this.getLog().debug("WorkerThread is shut down.");
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}


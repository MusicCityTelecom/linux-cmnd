/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.pool2.impl;

import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.commons.pool2.impl.BaseGenericObjectPool;

class EvictionTimer {
    private static ScheduledThreadPoolExecutor executor;
    private static final HashMap<WeakReference<Runnable>, WeakRunner> taskMap;

    static synchronized void cancel(BaseGenericObjectPool.Evictor evictor, Duration timeout, boolean restarting) {
        if (evictor != null) {
            evictor.cancel();
            EvictionTimer.remove(evictor);
        }
        if (!restarting && executor != null && taskMap.isEmpty()) {
            executor.shutdown();
            try {
                executor.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            executor.setCorePoolSize(0);
            executor = null;
        }
    }

    static synchronized int getNumTasks() {
        return taskMap.size();
    }

    private static void remove(BaseGenericObjectPool.Evictor evictor) {
        for (Map.Entry<WeakReference<Runnable>, WeakRunner> entry : taskMap.entrySet()) {
            if (entry.getKey().get() != evictor) continue;
            executor.remove(entry.getValue());
            taskMap.remove(entry.getKey());
            break;
        }
    }

    static synchronized void schedule(BaseGenericObjectPool.Evictor task, Duration delay, Duration period) {
        if (null == executor) {
            executor = new ScheduledThreadPoolExecutor(1, new EvictorThreadFactory());
            executor.setRemoveOnCancelPolicy(true);
            executor.scheduleAtFixedRate(new Reaper(), delay.toMillis(), period.toMillis(), TimeUnit.MILLISECONDS);
        }
        WeakReference<BaseGenericObjectPool.Evictor> ref = new WeakReference<BaseGenericObjectPool.Evictor>(task);
        WeakRunner runner = new WeakRunner(ref);
        ScheduledFuture<?> scheduledFuture = executor.scheduleWithFixedDelay(runner, delay.toMillis(), period.toMillis(), TimeUnit.MILLISECONDS);
        task.setScheduledFuture(scheduledFuture);
        taskMap.put(ref, runner);
    }

    private EvictionTimer() {
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EvictionTimer []");
        return builder.toString();
    }

    static {
        taskMap = new HashMap();
    }

    private static class WeakRunner
    implements Runnable {
        private final WeakReference<Runnable> ref;

        private WeakRunner(WeakReference<Runnable> ref) {
            this.ref = ref;
        }

        @Override
        public void run() {
            Runnable task = (Runnable)this.ref.get();
            if (task != null) {
                task.run();
            } else {
                executor.remove(this);
                taskMap.remove(this.ref);
            }
        }
    }

    private static class Reaper
    implements Runnable {
        private Reaper() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            Class<EvictionTimer> clazz = EvictionTimer.class;
            synchronized (EvictionTimer.class) {
                for (Map.Entry entry : taskMap.entrySet()) {
                    if (((WeakReference)entry.getKey()).get() != null) continue;
                    executor.remove((Runnable)entry.getValue());
                    taskMap.remove(entry.getKey());
                }
                if (taskMap.isEmpty() && executor != null) {
                    executor.shutdown();
                    executor.setCorePoolSize(0);
                    executor = null;
                }
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
        }
    }

    private static class EvictorThreadFactory
    implements ThreadFactory {
        private EvictorThreadFactory() {
        }

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(null, runnable, "commons-pool-evictor");
            thread.setDaemon(true);
            AccessController.doPrivileged(() -> {
                thread.setContextClassLoader(EvictorThreadFactory.class.getClassLoader());
                return null;
            });
            return thread;
        }
    }
}


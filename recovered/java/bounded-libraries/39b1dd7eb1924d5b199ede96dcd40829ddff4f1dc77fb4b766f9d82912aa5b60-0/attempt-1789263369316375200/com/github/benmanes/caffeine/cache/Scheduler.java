/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.DisabledScheduler;
import com.github.benmanes.caffeine.cache.ExecutorServiceScheduler;
import com.github.benmanes.caffeine.cache.GuardedScheduler;
import com.github.benmanes.caffeine.cache.SystemScheduler;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface Scheduler {
    public Future<?> schedule(Executor var1, Runnable var2, long var3, TimeUnit var5);

    public static Scheduler disabledScheduler() {
        return DisabledScheduler.INSTANCE;
    }

    public static Scheduler systemScheduler() {
        return SystemScheduler.INSTANCE;
    }

    public static Scheduler forScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        return new ExecutorServiceScheduler(scheduledExecutorService);
    }

    public static Scheduler guardedScheduler(Scheduler scheduler) {
        return scheduler instanceof GuardedScheduler ? scheduler : new GuardedScheduler(scheduler);
    }
}


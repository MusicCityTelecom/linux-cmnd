/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.DisabledFuture;
import com.github.benmanes.caffeine.cache.Scheduler;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

final class GuardedScheduler
implements Scheduler,
Serializable {
    private static final System.Logger logger = System.getLogger(GuardedScheduler.class.getName());
    private static final long serialVersionUID = 1L;
    final Scheduler delegate;

    GuardedScheduler(Scheduler delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public Future<?> schedule(Executor executor, Runnable command, long delay, TimeUnit unit) {
        try {
            Future<?> future = this.delegate.schedule(executor, command, delay, unit);
            return future == null ? DisabledFuture.INSTANCE : future;
        }
        catch (Throwable t) {
            logger.log(System.Logger.Level.WARNING, "Exception thrown by scheduler; discarded task", t);
            return DisabledFuture.INSTANCE;
        }
    }
}


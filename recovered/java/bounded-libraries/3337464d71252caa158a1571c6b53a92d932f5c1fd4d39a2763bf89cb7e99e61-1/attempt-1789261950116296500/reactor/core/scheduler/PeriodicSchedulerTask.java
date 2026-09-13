/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;

final class PeriodicSchedulerTask
implements Runnable,
Disposable,
Callable<Void> {
    final Runnable task;
    static final Future<Void> CANCELLED = new FutureTask<Void>(() -> null);
    volatile Future<?> future;
    static final AtomicReferenceFieldUpdater<PeriodicSchedulerTask, Future> FUTURE = AtomicReferenceFieldUpdater.newUpdater(PeriodicSchedulerTask.class, Future.class, "future");
    Thread thread;

    PeriodicSchedulerTask(Runnable task) {
        this.task = task;
    }

    @Override
    @Nullable
    public Void call() {
        this.thread = Thread.currentThread();
        try {
            try {
                this.task.run();
            }
            catch (Throwable ex) {
                Schedulers.handleError(ex);
            }
        }
        finally {
            this.thread = null;
        }
        return null;
    }

    @Override
    public void run() {
        this.call();
    }

    void setFuture(Future<?> f) {
        Future<?> o;
        do {
            if ((o = this.future) != CANCELLED) continue;
            f.cancel(this.thread != Thread.currentThread());
            return;
        } while (!FUTURE.compareAndSet(this, o, f));
    }

    @Override
    public boolean isDisposed() {
        return this.future == CANCELLED;
    }

    @Override
    public void dispose() {
        Future<?> f;
        while ((f = this.future) != CANCELLED) {
            if (!FUTURE.compareAndSet(this, f, CANCELLED)) continue;
            if (f == null) break;
            f.cancel(this.thread != Thread.currentThread());
            break;
        }
    }
}


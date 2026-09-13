/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;

final class SchedulerTask
implements Runnable,
Disposable,
Callable<Void> {
    final Runnable task;
    static final Future<Void> FINISHED = new FutureTask<Void>(() -> null);
    static final Future<Void> CANCELLED = new FutureTask<Void>(() -> null);
    static final Disposable TAKEN = Disposables.disposed();
    volatile Future<?> future;
    static final AtomicReferenceFieldUpdater<SchedulerTask, Future> FUTURE = AtomicReferenceFieldUpdater.newUpdater(SchedulerTask.class, Future.class, "future");
    volatile Disposable parent;
    static final AtomicReferenceFieldUpdater<SchedulerTask, Disposable> PARENT = AtomicReferenceFieldUpdater.newUpdater(SchedulerTask.class, Disposable.class, "parent");
    Thread thread;

    SchedulerTask(Runnable task, @Nullable Disposable parent) {
        this.task = task;
        PARENT.lazySet(this, parent);
    }

    @Override
    @Nullable
    public Void call() {
        this.thread = Thread.currentThread();
        Disposable d = null;
        try {
            while ((d = this.parent) != TAKEN && d != null && !PARENT.compareAndSet(this, d, TAKEN)) {
            }
            try {
                this.task.run();
            }
            catch (Throwable ex) {
                Schedulers.handleError(ex);
            }
        }
        finally {
            Future<?> f;
            this.thread = null;
            while ((f = this.future) != CANCELLED && !FUTURE.compareAndSet(this, f, FINISHED)) {
            }
            if (d != null) {
                d.dispose();
            }
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
            if ((o = this.future) == FINISHED) {
                return;
            }
            if (o != CANCELLED) continue;
            f.cancel(this.thread != Thread.currentThread());
            return;
        } while (!FUTURE.compareAndSet(this, o, f));
    }

    @Override
    public boolean isDisposed() {
        Future<?> a = this.future;
        return FINISHED == a || CANCELLED == a;
    }

    @Override
    public void dispose() {
        Disposable d;
        Future<?> f;
        while ((f = this.future) != FINISHED && f != CANCELLED) {
            if (!FUTURE.compareAndSet(this, f, CANCELLED)) continue;
            if (f == null) break;
            f.cancel(this.thread != Thread.currentThread());
            break;
        }
        while ((d = this.parent) != TAKEN && d != null) {
            if (!PARENT.compareAndSet(this, d, TAKEN)) continue;
            d.dispose();
            break;
        }
    }
}


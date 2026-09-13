/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.core.scheduler.EmptyCompositeDisposable;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;

final class InstantPeriodicWorkerTask
implements Disposable,
Callable<Void> {
    final Runnable task;
    final ExecutorService executor;
    static final Disposable.Composite DISPOSED = new EmptyCompositeDisposable();
    static final Future<Void> CANCELLED = new FutureTask<Void>(() -> null);
    volatile Future<?> rest;
    static final AtomicReferenceFieldUpdater<InstantPeriodicWorkerTask, Future> REST = AtomicReferenceFieldUpdater.newUpdater(InstantPeriodicWorkerTask.class, Future.class, "rest");
    volatile Future<?> first;
    static final AtomicReferenceFieldUpdater<InstantPeriodicWorkerTask, Future> FIRST = AtomicReferenceFieldUpdater.newUpdater(InstantPeriodicWorkerTask.class, Future.class, "first");
    volatile Disposable.Composite parent;
    static final AtomicReferenceFieldUpdater<InstantPeriodicWorkerTask, Disposable.Composite> PARENT = AtomicReferenceFieldUpdater.newUpdater(InstantPeriodicWorkerTask.class, Disposable.Composite.class, "parent");
    Thread thread;

    InstantPeriodicWorkerTask(Runnable task, ExecutorService executor) {
        this.task = task;
        this.executor = executor;
    }

    InstantPeriodicWorkerTask(Runnable task, ExecutorService executor, Disposable.Composite parent) {
        this.task = task;
        this.executor = executor;
        PARENT.lazySet(this, parent);
    }

    @Override
    @Nullable
    public Void call() {
        this.thread = Thread.currentThread();
        try {
            try {
                this.task.run();
                this.setRest(this.executor.submit(this));
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

    void setRest(Future<?> f) {
        Future<?> o;
        do {
            if ((o = this.rest) != CANCELLED) continue;
            f.cancel(this.thread != Thread.currentThread());
            return;
        } while (!REST.compareAndSet(this, o, f));
    }

    void setFirst(Future<?> f) {
        Future<?> o;
        do {
            if ((o = this.first) != CANCELLED) continue;
            f.cancel(this.thread != Thread.currentThread());
            return;
        } while (!FIRST.compareAndSet(this, o, f));
    }

    @Override
    public boolean isDisposed() {
        return this.rest == CANCELLED;
    }

    @Override
    public void dispose() {
        Disposable.Composite o;
        Future<?> f;
        while ((f = this.first) != CANCELLED) {
            if (!FIRST.compareAndSet(this, f, CANCELLED)) continue;
            if (f == null) break;
            f.cancel(this.thread != Thread.currentThread());
            break;
        }
        while ((f = this.rest) != CANCELLED) {
            if (!REST.compareAndSet(this, f, CANCELLED)) continue;
            if (f == null) break;
            f.cancel(this.thread != Thread.currentThread());
            break;
        }
        do {
            if ((o = this.parent) != DISPOSED && o != null) continue;
            return;
        } while (!PARENT.compareAndSet(this, o, DISPOSED));
        o.remove(this);
    }
}


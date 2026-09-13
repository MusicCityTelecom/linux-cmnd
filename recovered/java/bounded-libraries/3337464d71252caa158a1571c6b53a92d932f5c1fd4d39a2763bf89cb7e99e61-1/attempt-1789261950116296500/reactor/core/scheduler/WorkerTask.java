/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import reactor.core.Disposable;
import reactor.core.scheduler.EmptyCompositeDisposable;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;

final class WorkerTask
implements Runnable,
Disposable,
Callable<Void> {
    final Runnable task;
    static final Disposable.Composite DISPOSED = new EmptyCompositeDisposable();
    static final Disposable.Composite DONE = new EmptyCompositeDisposable();
    static final Future<Void> FINISHED = new FutureTask<Void>(() -> null);
    static final Future<Void> SYNC_CANCELLED = new FutureTask<Void>(() -> null);
    static final Future<Void> ASYNC_CANCELLED = new FutureTask<Void>(() -> null);
    volatile Future<?> future;
    static final AtomicReferenceFieldUpdater<WorkerTask, Future> FUTURE = AtomicReferenceFieldUpdater.newUpdater(WorkerTask.class, Future.class, "future");
    volatile Disposable.Composite parent;
    static final AtomicReferenceFieldUpdater<WorkerTask, Disposable.Composite> PARENT = AtomicReferenceFieldUpdater.newUpdater(WorkerTask.class, Disposable.Composite.class, "parent");
    volatile Thread thread;
    static final AtomicReferenceFieldUpdater<WorkerTask, Thread> THREAD = AtomicReferenceFieldUpdater.newUpdater(WorkerTask.class, Thread.class, "thread");

    WorkerTask(Runnable task, Disposable.Composite parent) {
        this.task = task;
        PARENT.lazySet(this, parent);
    }

    @Override
    @Nullable
    public Void call() {
        THREAD.lazySet(this, Thread.currentThread());
        try {
            try {
                this.task.run();
            }
            catch (Throwable ex) {
                Schedulers.handleError(ex);
            }
        }
        finally {
            Future<?> f;
            THREAD.lazySet(this, null);
            Disposable.Composite o = this.parent;
            if (o != DISPOSED && PARENT.compareAndSet(this, o, DONE) && o != null) {
                o.remove(this);
            }
            while ((f = this.future) != SYNC_CANCELLED && f != ASYNC_CANCELLED && !FUTURE.compareAndSet(this, f, FINISHED)) {
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
            if (o == SYNC_CANCELLED) {
                f.cancel(false);
                return;
            }
            if (o != ASYNC_CANCELLED) continue;
            f.cancel(true);
            return;
        } while (!FUTURE.compareAndSet(this, o, f));
    }

    @Override
    public boolean isDisposed() {
        Disposable.Composite o = PARENT.get(this);
        return o == DISPOSED || o == DONE;
    }

    @Override
    public void dispose() {
        Disposable.Composite o;
        Future<?> f;
        while ((f = this.future) != FINISHED && f != SYNC_CANCELLED && f != ASYNC_CANCELLED) {
            boolean async = this.thread != Thread.currentThread();
            if (!FUTURE.compareAndSet(this, f, async ? ASYNC_CANCELLED : SYNC_CANCELLED)) continue;
            if (f == null) break;
            f.cancel(async);
            break;
        }
        do {
            if ((o = this.parent) != DONE && o != DISPOSED && o != null) continue;
            return;
        } while (!PARENT.compareAndSet(this, o, DISPOSED));
        o.remove(this);
    }
}


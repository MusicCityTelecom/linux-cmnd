/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.core.scheduler.SingleWorkerScheduler;

final class ExecutorScheduler
implements Scheduler,
Scannable {
    final Executor executor;
    final boolean trampoline;
    volatile boolean terminated;

    ExecutorScheduler(Executor executor, boolean trampoline) {
        this.executor = executor;
        this.trampoline = trampoline;
    }

    @Override
    public Disposable schedule(Runnable task) {
        if (this.terminated) {
            throw Exceptions.failWithRejected();
        }
        Objects.requireNonNull(task, "task");
        ExecutorPlainRunnable r = new ExecutorPlainRunnable(task);
        try {
            this.executor.execute(r);
        }
        catch (Throwable ex) {
            if (this.executor instanceof ExecutorService && ((ExecutorService)this.executor).isShutdown()) {
                this.terminated = true;
            }
            Schedulers.handleError(ex);
            throw Exceptions.failWithRejected(ex);
        }
        return r;
    }

    @Override
    public void dispose() {
        this.terminated = true;
    }

    @Override
    public boolean isDisposed() {
        return this.terminated;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return this.trampoline ? new ExecutorSchedulerTrampolineWorker(this.executor) : new ExecutorSchedulerWorker(this.executor);
    }

    public String toString() {
        StringBuilder ts = new StringBuilder("fromExecutor").append('(').append(this.executor);
        if (this.trampoline) {
            ts.append(",trampolining");
        }
        ts.append(')');
        return ts.toString();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        return null;
    }

    static final class ExecutorSchedulerTrampolineWorker
    implements Scheduler.Worker,
    WorkerDelete,
    Runnable,
    Scannable {
        final Executor executor;
        final Queue<ExecutorTrackedRunnable> queue;
        volatile boolean terminated;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ExecutorSchedulerTrampolineWorker> WIP = AtomicIntegerFieldUpdater.newUpdater(ExecutorSchedulerTrampolineWorker.class, "wip");

        ExecutorSchedulerTrampolineWorker(Executor executor) {
            this.executor = executor;
            this.queue = new ConcurrentLinkedQueue<ExecutorTrackedRunnable>();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Disposable schedule(Runnable task) {
            Objects.requireNonNull(task, "task");
            if (this.terminated) {
                throw Exceptions.failWithRejected();
            }
            ExecutorTrackedRunnable r = new ExecutorTrackedRunnable(task, this, false);
            ExecutorSchedulerTrampolineWorker executorSchedulerTrampolineWorker = this;
            synchronized (executorSchedulerTrampolineWorker) {
                if (this.terminated) {
                    throw Exceptions.failWithRejected();
                }
                this.queue.offer(r);
            }
            if (WIP.getAndIncrement(this) == 0) {
                try {
                    this.executor.execute(this);
                }
                catch (Throwable ex) {
                    r.dispose();
                    Schedulers.handleError(ex);
                    throw Exceptions.failWithRejected(ex);
                }
            }
            return r;
        }

        @Override
        public void dispose() {
            ExecutorTrackedRunnable r;
            if (this.terminated) {
                return;
            }
            this.terminated = true;
            Queue<ExecutorTrackedRunnable> q = this.queue;
            while ((r = q.poll()) != null && !q.isEmpty()) {
                r.dispose();
            }
        }

        @Override
        public boolean isDisposed() {
            return this.terminated;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void delete(ExecutorTrackedRunnable r) {
            ExecutorSchedulerTrampolineWorker executorSchedulerTrampolineWorker = this;
            synchronized (executorSchedulerTrampolineWorker) {
                if (!this.terminated) {
                    this.queue.remove(r);
                }
            }
        }

        @Override
        public void run() {
            int e;
            Queue<ExecutorTrackedRunnable> q = this.queue;
            do {
                int r = this.wip;
                for (e = 0; e != r; ++e) {
                    if (this.terminated) {
                        return;
                    }
                    ExecutorTrackedRunnable task = q.poll();
                    if (task == null) break;
                    task.run();
                }
                if (e != r || !this.terminated) continue;
                return;
            } while (WIP.addAndGet(this, -e) != 0);
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.executor instanceof Scannable ? this.executor : null;
            }
            if (key == Scannable.Attr.NAME) {
                return "fromExecutor(" + this.executor + ",trampolining).worker";
            }
            if (key == Scannable.Attr.BUFFERED || key == Scannable.Attr.LARGE_BUFFERED) {
                return this.queue.size();
            }
            return Schedulers.scanExecutor(this.executor, key);
        }
    }

    static final class ExecutorSchedulerWorker
    implements Scheduler.Worker,
    WorkerDelete,
    Scannable {
        final Executor executor;
        final Disposable.Composite tasks;

        ExecutorSchedulerWorker(Executor executor) {
            this.executor = executor;
            this.tasks = Disposables.composite();
        }

        @Override
        public Disposable schedule(Runnable task) {
            Objects.requireNonNull(task, "task");
            ExecutorTrackedRunnable r = new ExecutorTrackedRunnable(task, this, true);
            if (!this.tasks.add(r)) {
                throw Exceptions.failWithRejected();
            }
            try {
                this.executor.execute(r);
            }
            catch (Throwable ex) {
                this.tasks.remove(r);
                Schedulers.handleError(ex);
                throw Exceptions.failWithRejected(ex);
            }
            return r;
        }

        @Override
        public void dispose() {
            this.tasks.dispose();
        }

        @Override
        public boolean isDisposed() {
            return this.tasks.isDisposed();
        }

        @Override
        public void delete(ExecutorTrackedRunnable r) {
            this.tasks.remove(r);
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.tasks.size();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.executor instanceof Scannable ? this.executor : null;
            }
            if (key == Scannable.Attr.NAME) {
                if (this.executor instanceof SingleWorkerScheduler) {
                    return this.executor + ".worker";
                }
                return "fromExecutor(" + this.executor + ").worker";
            }
            return Schedulers.scanExecutor(this.executor, key);
        }
    }

    static final class ExecutorTrackedRunnable
    extends AtomicBoolean
    implements Runnable,
    Disposable {
        private static final long serialVersionUID = 3503344795919906192L;
        final Runnable task;
        final WorkerDelete parent;
        final boolean callRemoveOnFinish;

        ExecutorTrackedRunnable(Runnable task, WorkerDelete parent, boolean callRemoveOnFinish) {
            this.task = task;
            this.parent = parent;
            this.callRemoveOnFinish = callRemoveOnFinish;
        }

        @Override
        public void run() {
            if (!this.get()) {
                try {
                    this.task.run();
                }
                catch (Throwable ex) {
                    Schedulers.handleError(ex);
                }
                finally {
                    if (this.callRemoveOnFinish) {
                        this.dispose();
                    } else {
                        this.lazySet(true);
                    }
                }
            }
        }

        @Override
        public void dispose() {
            if (this.compareAndSet(false, true)) {
                this.parent.delete(this);
            }
        }

        @Override
        public boolean isDisposed() {
            return this.get();
        }
    }

    static interface WorkerDelete {
        public void delete(ExecutorTrackedRunnable var1);
    }

    static final class ExecutorPlainRunnable
    extends AtomicBoolean
    implements Runnable,
    Disposable {
        private static final long serialVersionUID = 5116223460201378097L;
        final Runnable task;

        ExecutorPlainRunnable(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            if (!this.get()) {
                try {
                    this.task.run();
                }
                catch (Throwable ex) {
                    Schedulers.handleError(ex);
                }
                finally {
                    this.lazySet(true);
                }
            }
        }

        @Override
        public boolean isDisposed() {
            return this.get();
        }

        @Override
        public void dispose() {
            this.set(true);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.Scheduler;

final class ImmediateScheduler
implements Scheduler,
Scannable {
    private static final ImmediateScheduler INSTANCE = new ImmediateScheduler();
    static final Disposable FINISHED;

    public static Scheduler instance() {
        return INSTANCE;
    }

    private ImmediateScheduler() {
    }

    @Override
    public Disposable schedule(Runnable task) {
        task.run();
        return FINISHED;
    }

    @Override
    public void dispose() {
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.NAME) {
            return "immediate";
        }
        return null;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new ImmediateSchedulerWorker();
    }

    static {
        INSTANCE.start();
        FINISHED = Disposables.disposed();
    }

    static final class ImmediateSchedulerWorker
    implements Scheduler.Worker,
    Scannable {
        volatile boolean shutdown;

        ImmediateSchedulerWorker() {
        }

        @Override
        public Disposable schedule(Runnable task) {
            if (this.shutdown) {
                throw Exceptions.failWithRejected();
            }
            task.run();
            return FINISHED;
        }

        @Override
        public void dispose() {
            this.shutdown = true;
        }

        @Override
        public boolean isDisposed() {
            return this.shutdown;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
                return this.shutdown;
            }
            if (key == Scannable.Attr.NAME) {
                return "immediate.worker";
            }
            return null;
        }
    }
}


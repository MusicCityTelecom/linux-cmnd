/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.scheduler.ExecutorServiceWorker;
import reactor.core.scheduler.ReactorThreadFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

final class ParallelScheduler
implements Scheduler,
Supplier<ScheduledExecutorService>,
Scannable {
    static final AtomicLong COUNTER = new AtomicLong();
    final int n;
    final ThreadFactory factory;
    volatile ScheduledExecutorService[] executors;
    static final AtomicReferenceFieldUpdater<ParallelScheduler, ScheduledExecutorService[]> EXECUTORS = AtomicReferenceFieldUpdater.newUpdater(ParallelScheduler.class, ScheduledExecutorService[].class, "executors");
    static final ScheduledExecutorService[] SHUTDOWN = new ScheduledExecutorService[0];
    static final ScheduledExecutorService TERMINATED = Executors.newSingleThreadScheduledExecutor();
    int roundRobin;

    ParallelScheduler(int n, ThreadFactory factory) {
        if (n <= 0) {
            throw new IllegalArgumentException("n > 0 required but it was " + n);
        }
        this.n = n;
        this.factory = factory;
    }

    @Override
    public ScheduledExecutorService get() {
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1, this.factory);
        poolExecutor.setMaximumPoolSize(1);
        poolExecutor.setRemoveOnCancelPolicy(true);
        return poolExecutor;
    }

    @Override
    public boolean isDisposed() {
        return this.executors == SHUTDOWN;
    }

    @Override
    public void start() {
        ScheduledExecutorService[] a;
        ScheduledExecutorService[] b = null;
        do {
            if ((a = this.executors) != SHUTDOWN && a != null) {
                if (b != null) {
                    for (ScheduledExecutorService exec : b) {
                        exec.shutdownNow();
                    }
                }
                return;
            }
            if (b != null) continue;
            b = new ScheduledExecutorService[this.n];
            for (int i = 0; i < this.n; ++i) {
                b[i] = Schedulers.decorateExecutorService(this, this.get());
            }
        } while (!EXECUTORS.compareAndSet(this, a, b));
    }

    @Override
    public void dispose() {
        ScheduledExecutorService[] a = this.executors;
        if (a != SHUTDOWN && (a = EXECUTORS.getAndSet(this, SHUTDOWN)) != SHUTDOWN && a != null) {
            for (ScheduledExecutorService exec : a) {
                exec.shutdownNow();
            }
        }
    }

    ScheduledExecutorService pick() {
        ScheduledExecutorService[] a = this.executors;
        if (a == null) {
            this.start();
            a = this.executors;
            if (a == null) {
                throw new IllegalStateException("executors uninitialized after implicit start()");
            }
        }
        if (a != SHUTDOWN) {
            int idx = this.roundRobin;
            if (idx == this.n) {
                idx = 0;
                this.roundRobin = 1;
            } else {
                this.roundRobin = idx + 1;
            }
            return a[idx];
        }
        return TERMINATED;
    }

    @Override
    public Disposable schedule(Runnable task) {
        return Schedulers.directSchedule(this.pick(), task, null, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        return Schedulers.directSchedule(this.pick(), task, null, delay, unit);
    }

    @Override
    public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return Schedulers.directSchedulePeriodically(this.pick(), task, initialDelay, period, unit);
    }

    public String toString() {
        StringBuilder ts = new StringBuilder("parallel").append('(').append(this.n);
        if (this.factory instanceof ReactorThreadFactory) {
            ts.append(",\"").append(((ReactorThreadFactory)this.factory).get()).append('\"');
        }
        ts.append(')');
        return ts.toString();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.CAPACITY || key == Scannable.Attr.BUFFERED) {
            return this.n;
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        return null;
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.of(this.executors).map(exec -> key -> Schedulers.scanExecutor(exec, key));
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new ExecutorServiceWorker(this.pick());
    }

    static {
        TERMINATED.shutdownNow();
    }
}


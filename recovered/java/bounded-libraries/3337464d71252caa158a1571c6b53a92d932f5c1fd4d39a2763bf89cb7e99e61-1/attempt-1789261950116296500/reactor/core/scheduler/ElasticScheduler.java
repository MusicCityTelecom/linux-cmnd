/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Scannable;
import reactor.core.scheduler.ReactorThreadFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;

final class ElasticScheduler
implements Scheduler,
Scannable {
    static final AtomicLong COUNTER = new AtomicLong();
    static final ThreadFactory EVICTOR_FACTORY = r -> {
        Thread t = new Thread(r, "elastic-evictor-" + COUNTER.incrementAndGet());
        t.setDaemon(true);
        return t;
    };
    static final CachedService SHUTDOWN = new CachedService(null);
    static final int DEFAULT_TTL_SECONDS = 60;
    final ThreadFactory factory;
    final int ttlSeconds;
    final Deque<ScheduledExecutorServiceExpiry> cache;
    final Queue<CachedService> all;
    ScheduledExecutorService evictor;
    volatile boolean shutdown;

    ElasticScheduler(ThreadFactory factory, int ttlSeconds) {
        if (ttlSeconds < 0) {
            throw new IllegalArgumentException("ttlSeconds must be positive, was: " + ttlSeconds);
        }
        this.ttlSeconds = ttlSeconds;
        this.factory = factory;
        this.cache = new ConcurrentLinkedDeque<ScheduledExecutorServiceExpiry>();
        this.all = new ConcurrentLinkedQueue<CachedService>();
        this.shutdown = true;
    }

    public ScheduledExecutorService createUndecoratedService() {
        ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1, this.factory);
        poolExecutor.setMaximumPoolSize(1);
        poolExecutor.setRemoveOnCancelPolicy(true);
        return poolExecutor;
    }

    @Override
    public void start() {
        if (!this.shutdown) {
            return;
        }
        this.evictor = Executors.newScheduledThreadPool(1, EVICTOR_FACTORY);
        this.evictor.scheduleAtFixedRate(this::eviction, this.ttlSeconds, this.ttlSeconds, TimeUnit.SECONDS);
        this.shutdown = false;
    }

    @Override
    public boolean isDisposed() {
        return this.shutdown;
    }

    @Override
    public void dispose() {
        CachedService cached;
        if (this.shutdown) {
            return;
        }
        this.shutdown = true;
        this.evictor.shutdownNow();
        this.cache.clear();
        while ((cached = this.all.poll()) != null) {
            cached.exec.shutdownNow();
        }
    }

    CachedService pick() {
        if (this.shutdown) {
            return SHUTDOWN;
        }
        ScheduledExecutorServiceExpiry e = this.cache.pollLast();
        if (e != null) {
            return e.cached;
        }
        CachedService result = new CachedService(this);
        this.all.offer(result);
        if (this.shutdown) {
            this.all.remove(result);
            return SHUTDOWN;
        }
        return result;
    }

    @Override
    public Disposable schedule(Runnable task) {
        CachedService cached = this.pick();
        return Schedulers.directSchedule(cached.exec, task, cached, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        CachedService cached = this.pick();
        return Schedulers.directSchedule(cached.exec, task, cached, delay, unit);
    }

    @Override
    public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        CachedService cached = this.pick();
        return Disposables.composite(Schedulers.directSchedulePeriodically(cached.exec, task, initialDelay, period, unit), cached);
    }

    public String toString() {
        StringBuilder ts = new StringBuilder("elastic").append('(');
        if (this.factory instanceof ReactorThreadFactory) {
            ts.append('\"').append(((ReactorThreadFactory)this.factory).get()).append('\"');
        }
        ts.append(')');
        return ts.toString();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.CAPACITY) {
            return Integer.MAX_VALUE;
        }
        if (key == Scannable.Attr.BUFFERED) {
            return this.cache.size();
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        return null;
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return this.cache.stream().map(cached -> cached.cached);
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new ElasticWorker(this.pick());
    }

    void eviction() {
        long now = System.currentTimeMillis();
        ArrayList<ScheduledExecutorServiceExpiry> list = new ArrayList<ScheduledExecutorServiceExpiry>(this.cache);
        for (ScheduledExecutorServiceExpiry e : list) {
            if (e.expireMillis >= now || !this.cache.remove(e)) continue;
            e.cached.exec.shutdownNow();
            this.all.remove(e.cached);
        }
    }

    static final class ElasticWorker
    extends AtomicBoolean
    implements Scheduler.Worker,
    Scannable {
        final CachedService cached;
        final Disposable.Composite tasks;

        ElasticWorker(CachedService cached) {
            this.cached = cached;
            this.tasks = Disposables.composite();
        }

        @Override
        public Disposable schedule(Runnable task) {
            return Schedulers.workerSchedule(this.cached.exec, this.tasks, task, 0L, TimeUnit.MILLISECONDS);
        }

        @Override
        public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
            return Schedulers.workerSchedule(this.cached.exec, this.tasks, task, delay, unit);
        }

        @Override
        public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
            return Schedulers.workerSchedulePeriodically(this.cached.exec, this.tasks, task, initialDelay, period, unit);
        }

        @Override
        public void dispose() {
            if (this.compareAndSet(false, true)) {
                this.tasks.dispose();
                this.cached.dispose();
            }
        }

        @Override
        public boolean isDisposed() {
            return this.tasks.isDisposed();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.NAME) {
                return this.cached.scanUnsafe(key) + ".worker";
            }
            if (key == Scannable.Attr.PARENT) {
                return this.cached.parent;
            }
            return this.cached.scanUnsafe(key);
        }
    }

    static final class ScheduledExecutorServiceExpiry {
        final CachedService cached;
        final long expireMillis;

        ScheduledExecutorServiceExpiry(CachedService cached, long expireMillis) {
            this.cached = cached;
            this.expireMillis = expireMillis;
        }
    }

    static final class CachedService
    implements Disposable,
    Scannable {
        final ElasticScheduler parent;
        final ScheduledExecutorService exec;

        CachedService(@Nullable ElasticScheduler parent) {
            this.parent = parent;
            if (parent != null) {
                this.exec = Schedulers.decorateExecutorService(parent, parent.createUndecoratedService());
            } else {
                this.exec = Executors.newSingleThreadScheduledExecutor();
                this.exec.shutdownNow();
            }
        }

        @Override
        public void dispose() {
            if (this.exec != null && this != SHUTDOWN && !this.parent.shutdown) {
                ScheduledExecutorServiceExpiry e = new ScheduledExecutorServiceExpiry(this, System.currentTimeMillis() + (long)this.parent.ttlSeconds * 1000L);
                this.parent.cache.offerLast(e);
                if (this.parent.shutdown && this.parent.cache.remove(e)) {
                    this.exec.shutdownNow();
                }
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            Integer capacity;
            if (key == Scannable.Attr.NAME) {
                return this.parent.scanUnsafe(key);
            }
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.CAPACITY && ((capacity = (Integer)Schedulers.scanExecutor(this.exec, key)) == null || capacity == -1)) {
                return 1;
            }
            return Schedulers.scanExecutor(this.exec, key);
        }
    }
}


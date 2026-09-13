/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.ExecutorServiceWorker;
import reactor.core.scheduler.ReactorThreadFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;

final class BoundedElasticScheduler
implements Scheduler,
Scannable {
    static final Logger LOGGER = Loggers.getLogger(BoundedElasticScheduler.class);
    static final int DEFAULT_TTL_SECONDS = 60;
    static final AtomicLong EVICTOR_COUNTER = new AtomicLong();
    static final ThreadFactory EVICTOR_FACTORY = r -> {
        Thread t = new Thread(r, "boundedElastic-evictor-" + EVICTOR_COUNTER.incrementAndGet());
        t.setDaemon(true);
        return t;
    };
    static final BoundedServices SHUTDOWN = new BoundedServices();
    static final BoundedState CREATING;
    final int maxThreads;
    final int maxTaskQueuedPerThread;
    final Clock clock;
    final ThreadFactory factory;
    final long ttlMillis;
    volatile BoundedServices boundedServices;
    static final AtomicReferenceFieldUpdater<BoundedElasticScheduler, BoundedServices> BOUNDED_SERVICES;
    volatile ScheduledExecutorService evictor;
    static final AtomicReferenceFieldUpdater<BoundedElasticScheduler, ScheduledExecutorService> EVICTOR;

    BoundedElasticScheduler(int maxThreads, int maxTaskQueuedPerThread, ThreadFactory threadFactory, long ttlMillis, Clock clock) {
        if (ttlMillis <= 0L) {
            throw new IllegalArgumentException("TTL must be strictly positive, was " + ttlMillis + "ms");
        }
        if (maxThreads <= 0) {
            throw new IllegalArgumentException("maxThreads must be strictly positive, was " + maxThreads);
        }
        if (maxTaskQueuedPerThread <= 0) {
            throw new IllegalArgumentException("maxTaskQueuedPerThread must be strictly positive, was " + maxTaskQueuedPerThread);
        }
        this.maxThreads = maxThreads;
        this.maxTaskQueuedPerThread = maxTaskQueuedPerThread;
        this.factory = threadFactory;
        this.clock = Objects.requireNonNull(clock, "A Clock must be provided");
        this.ttlMillis = ttlMillis;
        this.boundedServices = SHUTDOWN;
    }

    BoundedElasticScheduler(int maxThreads, int maxTaskQueuedPerThread, ThreadFactory factory, int ttlSeconds) {
        this(maxThreads, maxTaskQueuedPerThread, factory, (long)ttlSeconds * 1000L, Clock.tickSeconds(BoundedServices.ZONE_UTC));
    }

    BoundedScheduledExecutorService createBoundedExecutorService() {
        return new BoundedScheduledExecutorService(this.maxTaskQueuedPerThread, this.factory);
    }

    @Override
    public boolean isDisposed() {
        return BOUNDED_SERVICES.get(this) == SHUTDOWN;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void start() {
        BoundedServices newServices;
        BoundedServices services;
        do {
            if ((services = BOUNDED_SERVICES.get(this)) == SHUTDOWN) continue;
            return;
        } while (!BOUNDED_SERVICES.compareAndSet(this, services, newServices = new BoundedServices(this)));
        ScheduledExecutorService e = Executors.newScheduledThreadPool(1, EVICTOR_FACTORY);
        if (EVICTOR.compareAndSet(this, null, e)) {
            try {
                e.scheduleAtFixedRate(newServices::eviction, this.ttlMillis, this.ttlMillis, TimeUnit.MILLISECONDS);
                return;
            }
            catch (RejectedExecutionException ree) {
                if (this.isDisposed()) return;
                throw ree;
            }
        } else {
            e.shutdownNow();
        }
    }

    @Override
    public void dispose() {
        BoundedServices services = BOUNDED_SERVICES.get(this);
        if (services != SHUTDOWN && BOUNDED_SERVICES.compareAndSet(this, services, SHUTDOWN)) {
            ScheduledExecutorService e = EVICTOR.getAndSet(this, null);
            if (e != null) {
                e.shutdownNow();
            }
            services.dispose();
        }
    }

    @Override
    public Disposable schedule(Runnable task) {
        BoundedState picked = BOUNDED_SERVICES.get(this).pick();
        return Schedulers.directSchedule(picked.executor, task, picked, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        BoundedState picked = BOUNDED_SERVICES.get(this).pick();
        return Schedulers.directSchedule(picked.executor, task, picked, delay, unit);
    }

    @Override
    public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        BoundedState picked = BOUNDED_SERVICES.get(this).pick();
        Disposable scheduledTask = Schedulers.directSchedulePeriodically(picked.executor, task, initialDelay, period, unit);
        return Disposables.composite(scheduledTask, picked);
    }

    public String toString() {
        StringBuilder ts = new StringBuilder("boundedElastic").append('(');
        if (this.factory instanceof ReactorThreadFactory) {
            ts.append('\"').append(((ReactorThreadFactory)this.factory).get()).append("\",");
        }
        ts.append("maxThreads=").append(this.maxThreads).append(",maxTaskQueuedPerThread=").append(this.maxTaskQueuedPerThread == Integer.MAX_VALUE ? "unbounded" : Integer.valueOf(this.maxTaskQueuedPerThread)).append(",ttl=");
        if (this.ttlMillis < 1000L) {
            ts.append(this.ttlMillis).append("ms)");
        } else {
            ts.append(this.ttlMillis / 1000L).append("s)");
        }
        return ts.toString();
    }

    int estimateSize() {
        return BOUNDED_SERVICES.get(this).get();
    }

    int estimateBusy() {
        return BoundedElasticScheduler.BOUNDED_SERVICES.get((BoundedElasticScheduler)this).busyArray.length;
    }

    int estimateIdle() {
        return BoundedElasticScheduler.BOUNDED_SERVICES.get((BoundedElasticScheduler)this).idleQueue.size();
    }

    int estimateRemainingTaskCapacity() {
        BoundedState[] busyArray = BoundedElasticScheduler.BOUNDED_SERVICES.get((BoundedElasticScheduler)this).busyArray;
        int totalTaskCapacity = this.maxTaskQueuedPerThread * this.maxThreads;
        for (BoundedState state : busyArray) {
            int stateQueueSize = state.estimateQueueSize();
            if (stateQueueSize >= 0) {
                totalTaskCapacity -= stateQueueSize;
                continue;
            }
            return -1;
        }
        return totalTaskCapacity;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.BUFFERED) {
            return this.estimateSize();
        }
        if (key == Scannable.Attr.CAPACITY) {
            return this.maxThreads;
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        return null;
    }

    @Override
    public Stream<? extends Scannable> inners() {
        BoundedServices services = BOUNDED_SERVICES.get(this);
        return Stream.concat(Stream.of(services.busyArray), services.idleQueue.stream()).filter(obj -> obj != null && obj != CREATING);
    }

    @Override
    public Scheduler.Worker createWorker() {
        BoundedState picked = BOUNDED_SERVICES.get(this).pick();
        ExecutorServiceWorker worker = new ExecutorServiceWorker(picked.executor);
        worker.disposables.add(picked);
        return worker;
    }

    static {
        SHUTDOWN.dispose();
        ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();
        s.shutdownNow();
        CREATING = new BoundedState(SHUTDOWN, s){

            @Override
            public String toString() {
                return "CREATING BoundedState";
            }
        };
        BoundedElasticScheduler.CREATING.markCount = -1;
        BoundedElasticScheduler.CREATING.idleSinceTimestamp = -1L;
        BOUNDED_SERVICES = AtomicReferenceFieldUpdater.newUpdater(BoundedElasticScheduler.class, BoundedServices.class, "boundedServices");
        EVICTOR = AtomicReferenceFieldUpdater.newUpdater(BoundedElasticScheduler.class, ScheduledExecutorService.class, "evictor");
    }

    static final class BoundedScheduledExecutorService
    extends ScheduledThreadPoolExecutor
    implements Scannable {
        final int queueCapacity;

        BoundedScheduledExecutorService(int queueCapacity, ThreadFactory factory) {
            super(1, factory);
            this.setMaximumPoolSize(1);
            this.setRemoveOnCancelPolicy(true);
            if (queueCapacity < 1) {
                throw new IllegalArgumentException("was expecting a non-zero positive queue capacity");
            }
            this.queueCapacity = queueCapacity;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (Scannable.Attr.TERMINATED == key) {
                return this.isTerminated();
            }
            if (Scannable.Attr.BUFFERED == key) {
                return this.getQueue().size();
            }
            if (Scannable.Attr.CAPACITY == key) {
                return this.queueCapacity;
            }
            return null;
        }

        @Override
        public String toString() {
            String state;
            int queued = this.getQueue().size();
            long completed = this.getCompletedTaskCount();
            String string = state = this.getActiveCount() > 0 ? "ACTIVE" : "IDLE";
            if (this.queueCapacity == Integer.MAX_VALUE) {
                return "BoundedScheduledExecutorService{" + state + ", queued=" + queued + "/unbounded, completed=" + completed + '}';
            }
            return "BoundedScheduledExecutorService{" + state + ", queued=" + queued + "/" + this.queueCapacity + ", completed=" + completed + '}';
        }

        void ensureQueueCapacity(int taskCount) {
            if (this.queueCapacity == Integer.MAX_VALUE) {
                return;
            }
            int queueSize = super.getQueue().size();
            if (queueSize + taskCount > this.queueCapacity) {
                throw Exceptions.failWithRejected("Task capacity of bounded elastic scheduler reached while scheduling " + taskCount + " tasks (" + (queueSize + taskCount) + "/" + this.queueCapacity + ")");
            }
        }

        @Override
        public synchronized ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            this.ensureQueueCapacity(1);
            return super.schedule(command, delay, unit);
        }

        @Override
        public synchronized <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            this.ensureQueueCapacity(1);
            return super.schedule(callable, delay, unit);
        }

        @Override
        public synchronized ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            this.ensureQueueCapacity(1);
            return super.scheduleAtFixedRate(command, initialDelay, period, unit);
        }

        @Override
        public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            this.ensureQueueCapacity(1);
            return super.scheduleWithFixedDelay(command, initialDelay, delay, unit);
        }

        @Override
        public void shutdown() {
            super.shutdown();
        }

        @Override
        public List<Runnable> shutdownNow() {
            return super.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return super.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return super.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return super.awaitTermination(timeout, unit);
        }

        @Override
        public synchronized <T> Future<T> submit(Callable<T> task) {
            this.ensureQueueCapacity(1);
            return super.submit(task);
        }

        @Override
        public synchronized <T> Future<T> submit(Runnable task, T result) {
            this.ensureQueueCapacity(1);
            return super.submit(task, result);
        }

        @Override
        public synchronized Future<?> submit(Runnable task) {
            this.ensureQueueCapacity(1);
            return super.submit(task);
        }

        @Override
        public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
            this.ensureQueueCapacity(tasks.size());
            return super.invokeAll(tasks);
        }

        @Override
        public synchronized <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
            this.ensureQueueCapacity(tasks.size());
            return super.invokeAll(tasks, timeout, unit);
        }

        @Override
        public synchronized <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            this.ensureQueueCapacity(tasks.size());
            return super.invokeAny(tasks);
        }

        @Override
        public synchronized <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            this.ensureQueueCapacity(tasks.size());
            return super.invokeAny(tasks, timeout, unit);
        }

        @Override
        public synchronized void execute(Runnable command) {
            this.ensureQueueCapacity(1);
            super.submit(command);
        }
    }

    static class BoundedState
    implements Disposable,
    Scannable {
        static final int EVICTED = -1;
        final BoundedServices parent;
        final ScheduledExecutorService executor;
        long idleSinceTimestamp = -1L;
        volatile int markCount;
        static final AtomicIntegerFieldUpdater<BoundedState> MARK_COUNT = AtomicIntegerFieldUpdater.newUpdater(BoundedState.class, "markCount");

        BoundedState(BoundedServices parent, ScheduledExecutorService executor) {
            this.parent = parent;
            this.executor = executor;
        }

        int estimateQueueSize() {
            if (this.executor instanceof ScheduledThreadPoolExecutor) {
                return ((ScheduledThreadPoolExecutor)this.executor).getQueue().size();
            }
            return -1;
        }

        boolean markPicked() {
            int i;
            do {
                if ((i = MARK_COUNT.get(this)) != -1) continue;
                return false;
            } while (!MARK_COUNT.compareAndSet(this, i, i + 1));
            return true;
        }

        boolean tryEvict(long evictionTimestamp, long ttlMillis) {
            long idleSince = this.idleSinceTimestamp;
            if (idleSince < 0L) {
                return false;
            }
            long elapsed = evictionTimestamp - idleSince;
            if (elapsed >= ttlMillis && MARK_COUNT.compareAndSet(this, 0, -1)) {
                this.executor.shutdownNow();
                return true;
            }
            return false;
        }

        void release() {
            int picked = MARK_COUNT.decrementAndGet(this);
            if (picked < 0) {
                return;
            }
            if (picked == 0) {
                this.idleSinceTimestamp = this.parent.clock.millis();
                this.parent.setIdle(this);
            } else {
                this.idleSinceTimestamp = -1L;
            }
        }

        void shutdown() {
            this.idleSinceTimestamp = -1L;
            MARK_COUNT.set(this, -1);
            this.executor.shutdownNow();
        }

        @Override
        public void dispose() {
            this.release();
        }

        @Override
        public boolean isDisposed() {
            return MARK_COUNT.get(this) <= 0;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            return Schedulers.scanExecutor(this.executor, key);
        }

        public String toString() {
            return "BoundedState@" + System.identityHashCode(this) + "{ backing=" + MARK_COUNT.get(this) + ", idleSince=" + this.idleSinceTimestamp + ", executor=" + this.executor + '}';
        }
    }

    static final class BoundedServices
    extends AtomicInteger
    implements Disposable {
        static final int DISPOSED = -1;
        static final ZoneId ZONE_UTC = ZoneId.of("UTC");
        final BoundedElasticScheduler parent;
        final Clock clock;
        final Deque<BoundedState> idleQueue;
        volatile BoundedState[] busyArray;
        static final AtomicReferenceFieldUpdater<BoundedServices, BoundedState[]> BUSY_ARRAY = AtomicReferenceFieldUpdater.newUpdater(BoundedServices.class, BoundedState[].class, "busyArray");
        static final BoundedState[] ALL_IDLE = new BoundedState[0];
        static final BoundedState[] ALL_SHUTDOWN = new BoundedState[0];

        private BoundedServices() {
            this.parent = null;
            this.clock = Clock.fixed(Instant.EPOCH, ZONE_UTC);
            this.idleQueue = new ConcurrentLinkedDeque<BoundedState>();
            this.busyArray = ALL_SHUTDOWN;
        }

        BoundedServices(BoundedElasticScheduler parent) {
            this.parent = parent;
            this.clock = parent.clock;
            this.idleQueue = new ConcurrentLinkedDeque<BoundedState>();
            this.busyArray = ALL_IDLE;
        }

        void eviction() {
            long evictionTimestamp = this.parent.clock.millis();
            ArrayList<BoundedState> idleCandidates = new ArrayList<BoundedState>(this.idleQueue);
            for (BoundedState candidate : idleCandidates) {
                if (!candidate.tryEvict(evictionTimestamp, this.parent.ttlMillis)) continue;
                this.idleQueue.remove(candidate);
                this.decrementAndGet();
            }
        }

        boolean setBusy(BoundedState bs) {
            BoundedState[] replacement;
            BoundedState[] previous;
            do {
                if ((previous = this.busyArray) == ALL_SHUTDOWN) {
                    return false;
                }
                int len = previous.length;
                replacement = new BoundedState[len + 1];
                System.arraycopy(previous, 0, replacement, 0, len);
                replacement[len] = bs;
            } while (!BUSY_ARRAY.compareAndSet(this, previous, replacement));
            return true;
        }

        void setIdle(BoundedState boundedState) {
            BoundedState[] replacement;
            BoundedState[] arr;
            do {
                block4: {
                    int len;
                    block3: {
                        if ((len = (arr = this.busyArray).length) == 0) {
                            return;
                        }
                        replacement = null;
                        if (len != 1) break block3;
                        if (arr[0] != boundedState) break block4;
                        replacement = ALL_IDLE;
                        break block4;
                    }
                    for (int i = 0; i < len; ++i) {
                        BoundedState state = arr[i];
                        if (state != boundedState) continue;
                        replacement = new BoundedState[len - 1];
                        System.arraycopy(arr, 0, replacement, 0, i);
                        System.arraycopy(arr, i + 1, replacement, i, len - i - 1);
                        break;
                    }
                }
                if (replacement != null) continue;
                return;
            } while (!BUSY_ARRAY.compareAndSet(this, arr, replacement));
            this.idleQueue.add(boundedState);
        }

        BoundedState pick() {
            Object s;
            while (true) {
                int a;
                if ((a = this.get()) == -1 || this.busyArray == ALL_SHUTDOWN) {
                    return CREATING;
                }
                if (!this.idleQueue.isEmpty()) {
                    BoundedState bs = this.idleQueue.pollLast();
                    if (bs == null || !bs.markPicked()) continue;
                    this.setBusy(bs);
                    return bs;
                }
                if (a < this.parent.maxThreads) {
                    BoundedState newState;
                    if (!this.compareAndSet(a, a + 1) || !(newState = new BoundedState(this, (ScheduledExecutorService)(s = Schedulers.decorateExecutorService(this.parent, this.parent.createBoundedExecutorService())))).markPicked()) continue;
                    this.setBusy(newState);
                    return newState;
                }
                s = this.choseOneBusy();
                if (s != null && ((BoundedState)s).markPicked()) break;
            }
            return s;
        }

        @Nullable
        private BoundedState choseOneBusy() {
            BoundedState[] arr = this.busyArray;
            int len = arr.length;
            if (len == 0) {
                return null;
            }
            if (len == 1) {
                return arr[0];
            }
            BoundedState choice = arr[0];
            int leastBusy = Integer.MAX_VALUE;
            for (int i = 0; i < arr.length; ++i) {
                BoundedState state = arr[i];
                int busy = state.markCount;
                if (busy >= leastBusy) continue;
                leastBusy = busy;
                choice = state;
            }
            return choice;
        }

        @Override
        public boolean isDisposed() {
            return this.get() == -1;
        }

        @Override
        public void dispose() {
            this.set(-1);
            this.idleQueue.forEach(BoundedState::shutdown);
            BoundedState[] arr = BUSY_ARRAY.getAndSet(this, ALL_SHUTDOWN);
            for (int i = 0; i < arr.length; ++i) {
                arr[i].shutdown();
            }
        }
    }
}


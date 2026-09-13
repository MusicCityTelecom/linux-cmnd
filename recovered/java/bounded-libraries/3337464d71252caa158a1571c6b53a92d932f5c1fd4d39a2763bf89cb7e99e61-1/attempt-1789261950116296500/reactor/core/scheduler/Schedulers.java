/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.BoundedElasticScheduler;
import reactor.core.scheduler.DelegateServiceScheduler;
import reactor.core.scheduler.ElasticScheduler;
import reactor.core.scheduler.ExecutorScheduler;
import reactor.core.scheduler.ImmediateScheduler;
import reactor.core.scheduler.InstantPeriodicWorkerTask;
import reactor.core.scheduler.NonBlocking;
import reactor.core.scheduler.ParallelScheduler;
import reactor.core.scheduler.PeriodicSchedulerTask;
import reactor.core.scheduler.PeriodicWorkerTask;
import reactor.core.scheduler.ReactorThreadFactory;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.SchedulerMetricDecorator;
import reactor.core.scheduler.SchedulerTask;
import reactor.core.scheduler.SingleScheduler;
import reactor.core.scheduler.SingleWorkerScheduler;
import reactor.core.scheduler.WorkerTask;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.Metrics;
import reactor.util.annotation.Nullable;

public abstract class Schedulers {
    public static final int DEFAULT_POOL_SIZE = Optional.ofNullable(System.getProperty("reactor.schedulers.defaultPoolSize")).map(Integer::parseInt).orElseGet(() -> Runtime.getRuntime().availableProcessors());
    public static final int DEFAULT_BOUNDED_ELASTIC_SIZE = Optional.ofNullable(System.getProperty("reactor.schedulers.defaultBoundedElasticSize")).map(Integer::parseInt).orElseGet(() -> 10 * Runtime.getRuntime().availableProcessors());
    public static final int DEFAULT_BOUNDED_ELASTIC_QUEUESIZE = Optional.ofNullable(System.getProperty("reactor.schedulers.defaultBoundedElasticQueueSize")).map(Integer::parseInt).orElse(100000);
    static final String ELASTIC = "elastic";
    static final String BOUNDED_ELASTIC = "boundedElastic";
    static final String PARALLEL = "parallel";
    static final String SINGLE = "single";
    static final String IMMEDIATE = "immediate";
    static final String FROM_EXECUTOR = "fromExecutor";
    static final String FROM_EXECUTOR_SERVICE = "fromExecutorService";
    static AtomicReference<CachedScheduler> CACHED_ELASTIC = new AtomicReference();
    static AtomicReference<CachedScheduler> CACHED_BOUNDED_ELASTIC = new AtomicReference();
    static AtomicReference<CachedScheduler> CACHED_PARALLEL = new AtomicReference();
    static AtomicReference<CachedScheduler> CACHED_SINGLE = new AtomicReference();
    static final Supplier<Scheduler> ELASTIC_SUPPLIER = () -> Schedulers.newElastic(ELASTIC, 60, true);
    static final Supplier<Scheduler> BOUNDED_ELASTIC_SUPPLIER = () -> Schedulers.newBoundedElastic(DEFAULT_BOUNDED_ELASTIC_SIZE, DEFAULT_BOUNDED_ELASTIC_QUEUESIZE, BOUNDED_ELASTIC, 60, true);
    static final Supplier<Scheduler> PARALLEL_SUPPLIER = () -> Schedulers.newParallel(PARALLEL, DEFAULT_POOL_SIZE, true);
    static final Supplier<Scheduler> SINGLE_SUPPLIER = () -> Schedulers.newSingle(SINGLE, true);
    static final Factory DEFAULT = new Factory(){};
    static final Map<String, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService>> DECORATORS = new LinkedHashMap<String, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService>>();
    static volatile Factory factory = DEFAULT;
    private static final LinkedHashMap<String, BiConsumer<Thread, Throwable>> onHandleErrorHooks = new LinkedHashMap(1);
    @Nullable
    static BiConsumer<Thread, ? super Throwable> onHandleErrorHook;
    private static final LinkedHashMap<String, Function<Runnable, Runnable>> onScheduleHooks;
    @Nullable
    private static Function<Runnable, Runnable> onScheduleHook;
    static final Logger LOGGER;

    public static Scheduler fromExecutor(Executor executor) {
        return Schedulers.fromExecutor(executor, false);
    }

    public static Scheduler fromExecutor(Executor executor, boolean trampoline) {
        if (!trampoline && executor instanceof ExecutorService) {
            return Schedulers.fromExecutorService((ExecutorService)executor);
        }
        ExecutorScheduler scheduler = new ExecutorScheduler(executor, trampoline);
        scheduler.start();
        return scheduler;
    }

    public static Scheduler fromExecutorService(ExecutorService executorService) {
        String executorServiceHashcode = Integer.toHexString(System.identityHashCode(executorService));
        return Schedulers.fromExecutorService(executorService, "anonymousExecutor@" + executorServiceHashcode);
    }

    public static Scheduler fromExecutorService(ExecutorService executorService, String executorName) {
        DelegateServiceScheduler scheduler = new DelegateServiceScheduler(executorName, executorService);
        scheduler.start();
        return scheduler;
    }

    @Deprecated
    public static Scheduler elastic() {
        return Schedulers.cache(CACHED_ELASTIC, ELASTIC, ELASTIC_SUPPLIER);
    }

    public static Scheduler boundedElastic() {
        return Schedulers.cache(CACHED_BOUNDED_ELASTIC, BOUNDED_ELASTIC, BOUNDED_ELASTIC_SUPPLIER);
    }

    public static Scheduler parallel() {
        return Schedulers.cache(CACHED_PARALLEL, PARALLEL, PARALLEL_SUPPLIER);
    }

    public static Scheduler immediate() {
        return ImmediateScheduler.instance();
    }

    @Deprecated
    public static Scheduler newElastic(String name) {
        return Schedulers.newElastic(name, 60);
    }

    @Deprecated
    public static Scheduler newElastic(String name, int ttlSeconds) {
        return Schedulers.newElastic(name, ttlSeconds, false);
    }

    @Deprecated
    public static Scheduler newElastic(String name, int ttlSeconds, boolean daemon) {
        return Schedulers.newElastic(ttlSeconds, new ReactorThreadFactory(name, ElasticScheduler.COUNTER, daemon, false, Schedulers::defaultUncaughtException));
    }

    @Deprecated
    public static Scheduler newElastic(int ttlSeconds, ThreadFactory threadFactory) {
        Scheduler fromFactory = factory.newElastic(ttlSeconds, threadFactory);
        fromFactory.start();
        return fromFactory;
    }

    public static Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, String name) {
        return Schedulers.newBoundedElastic(threadCap, queuedTaskCap, name, 60, false);
    }

    public static Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, String name, int ttlSeconds) {
        return Schedulers.newBoundedElastic(threadCap, queuedTaskCap, name, ttlSeconds, false);
    }

    public static Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, String name, int ttlSeconds, boolean daemon) {
        return Schedulers.newBoundedElastic(threadCap, queuedTaskCap, new ReactorThreadFactory(name, ElasticScheduler.COUNTER, daemon, false, Schedulers::defaultUncaughtException), ttlSeconds);
    }

    public static Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, ThreadFactory threadFactory, int ttlSeconds) {
        Scheduler fromFactory = factory.newBoundedElastic(threadCap, queuedTaskCap, threadFactory, ttlSeconds);
        fromFactory.start();
        return fromFactory;
    }

    public static Scheduler newParallel(String name) {
        return Schedulers.newParallel(name, DEFAULT_POOL_SIZE);
    }

    public static Scheduler newParallel(String name, int parallelism) {
        return Schedulers.newParallel(name, parallelism, false);
    }

    public static Scheduler newParallel(String name, int parallelism, boolean daemon) {
        return Schedulers.newParallel(parallelism, new ReactorThreadFactory(name, ParallelScheduler.COUNTER, daemon, true, Schedulers::defaultUncaughtException));
    }

    public static Scheduler newParallel(int parallelism, ThreadFactory threadFactory) {
        Scheduler fromFactory = factory.newParallel(parallelism, threadFactory);
        fromFactory.start();
        return fromFactory;
    }

    public static Scheduler newSingle(String name) {
        return Schedulers.newSingle(name, false);
    }

    public static Scheduler newSingle(String name, boolean daemon) {
        return Schedulers.newSingle(new ReactorThreadFactory(name, SingleScheduler.COUNTER, daemon, true, Schedulers::defaultUncaughtException));
    }

    public static Scheduler newSingle(ThreadFactory threadFactory) {
        Scheduler fromFactory = factory.newSingle(threadFactory);
        fromFactory.start();
        return fromFactory;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onHandleError(BiConsumer<Thread, ? super Throwable> subHook) {
        Objects.requireNonNull(subHook, "onHandleError");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Hooking onHandleError anonymous part");
        }
        Logger logger = LOGGER;
        synchronized (logger) {
            onHandleErrorHooks.put(Schedulers.class.getName() + ".ON_HANDLE_ERROR_ANONYMOUS_PART", subHook);
            onHandleErrorHook = Schedulers.createOrAppendHandleError(onHandleErrorHooks.values());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onHandleError(String key, BiConsumer<Thread, ? super Throwable> subHook) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(subHook, "onHandleError");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Hooking onHandleError part with key {}", key);
        }
        Logger logger = LOGGER;
        synchronized (logger) {
            onHandleErrorHooks.put(key, subHook);
            onHandleErrorHook = Schedulers.createOrAppendHandleError(onHandleErrorHooks.values());
        }
    }

    @Nullable
    private static BiConsumer<Thread, ? super Throwable> createOrAppendHandleError(Collection<BiConsumer<Thread, Throwable>> subHooks) {
        BiConsumer<Thread, Throwable> composite = null;
        for (BiConsumer<Thread, Throwable> value : subHooks) {
            if (composite != null) {
                composite = composite.andThen(value);
                continue;
            }
            composite = value;
        }
        return composite;
    }

    public static boolean isInNonBlockingThread() {
        return Thread.currentThread() instanceof NonBlocking;
    }

    public static boolean isNonBlockingThread(Thread t) {
        return t instanceof NonBlocking;
    }

    public static void enableMetrics() {
        if (Metrics.isInstrumentationAvailable()) {
            Schedulers.addExecutorServiceDecorator("reactor.metrics.decorator", new SchedulerMetricDecorator());
        }
    }

    public static void disableMetrics() {
        Schedulers.removeExecutorServiceDecorator("reactor.metrics.decorator");
    }

    public static void resetFactory() {
        Schedulers.setFactory(DEFAULT);
    }

    public static Snapshot setFactoryWithSnapshot(Factory newFactory) {
        Snapshot snapshot = new Snapshot(CACHED_ELASTIC.getAndSet(null), CACHED_BOUNDED_ELASTIC.getAndSet(null), CACHED_PARALLEL.getAndSet(null), CACHED_SINGLE.getAndSet(null), factory);
        Schedulers.setFactory(newFactory);
        return snapshot;
    }

    public static void resetFrom(@Nullable Snapshot snapshot) {
        if (snapshot == null) {
            Schedulers.resetFactory();
            return;
        }
        CachedScheduler oldElastic = CACHED_ELASTIC.getAndSet(snapshot.oldElasticScheduler);
        CachedScheduler oldBoundedElastic = CACHED_BOUNDED_ELASTIC.getAndSet(snapshot.oldBoundedElasticScheduler);
        CachedScheduler oldParallel = CACHED_PARALLEL.getAndSet(snapshot.oldParallelScheduler);
        CachedScheduler oldSingle = CACHED_SINGLE.getAndSet(snapshot.oldSingleScheduler);
        factory = snapshot.oldFactory;
        if (oldElastic != null) {
            oldElastic._dispose();
        }
        if (oldBoundedElastic != null) {
            oldBoundedElastic._dispose();
        }
        if (oldParallel != null) {
            oldParallel._dispose();
        }
        if (oldSingle != null) {
            oldSingle._dispose();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnHandleError() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Reset to factory defaults: onHandleError");
        }
        Logger logger = LOGGER;
        synchronized (logger) {
            onHandleErrorHooks.clear();
            onHandleErrorHook = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnHandleError(String key) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Remove onHandleError sub-hook {}", key);
        }
        Logger logger = LOGGER;
        synchronized (logger) {
            if (onHandleErrorHooks.remove(key) != null) {
                onHandleErrorHook = Schedulers.createOrAppendHandleError(onHandleErrorHooks.values());
            }
        }
    }

    public static void setFactory(Factory factoryInstance) {
        Objects.requireNonNull(factoryInstance, "factoryInstance");
        Schedulers.shutdownNow();
        factory = factoryInstance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean addExecutorServiceDecorator(String key, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> decorator) {
        Map<String, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService>> map = DECORATORS;
        synchronized (map) {
            return DECORATORS.putIfAbsent(key, decorator) == null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setExecutorServiceDecorator(String key, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> decorator) {
        Map<String, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService>> map = DECORATORS;
        synchronized (map) {
            DECORATORS.put(key, decorator);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> removeExecutorServiceDecorator(String key) {
        BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> removed;
        Map<String, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService>> map = DECORATORS;
        synchronized (map) {
            removed = DECORATORS.remove(key);
        }
        if (removed instanceof Disposable) {
            ((Disposable)((Object)removed)).dispose();
        }
        return removed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static ScheduledExecutorService decorateExecutorService(Scheduler owner, ScheduledExecutorService original) {
        Map<String, BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService>> map = DECORATORS;
        synchronized (map) {
            for (BiFunction<Scheduler, ScheduledExecutorService, ScheduledExecutorService> decorator : DECORATORS.values()) {
                original = decorator.apply(owner, original);
            }
        }
        return original;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onScheduleHook(String key, Function<Runnable, Runnable> decorator) {
        LinkedHashMap<String, Function<Runnable, Runnable>> linkedHashMap = onScheduleHooks;
        synchronized (linkedHashMap) {
            onScheduleHooks.put(key, decorator);
            Function<Runnable, Runnable> newHook = null;
            for (Function<Runnable, Runnable> function : onScheduleHooks.values()) {
                if (newHook == null) {
                    newHook = function;
                    continue;
                }
                newHook = newHook.andThen(function);
            }
            onScheduleHook = newHook;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnScheduleHook(String key) {
        LinkedHashMap<String, Function<Runnable, Runnable>> linkedHashMap = onScheduleHooks;
        synchronized (linkedHashMap) {
            onScheduleHooks.remove(key);
            if (onScheduleHooks.isEmpty()) {
                onScheduleHook = Function.identity();
            } else {
                Function<Runnable, Runnable> newHook = null;
                for (Function<Runnable, Runnable> function : onScheduleHooks.values()) {
                    if (newHook == null) {
                        newHook = function;
                        continue;
                    }
                    newHook = newHook.andThen(function);
                }
                onScheduleHook = newHook;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnScheduleHooks() {
        LinkedHashMap<String, Function<Runnable, Runnable>> linkedHashMap = onScheduleHooks;
        synchronized (linkedHashMap) {
            onScheduleHooks.clear();
            onScheduleHook = null;
        }
    }

    public static Runnable onSchedule(Runnable runnable) {
        Function<Runnable, Runnable> hook = onScheduleHook;
        if (hook != null) {
            return hook.apply(runnable);
        }
        return runnable;
    }

    public static void shutdownNow() {
        CachedScheduler oldElastic = CACHED_ELASTIC.getAndSet(null);
        CachedScheduler oldBoundedElastic = CACHED_BOUNDED_ELASTIC.getAndSet(null);
        CachedScheduler oldParallel = CACHED_PARALLEL.getAndSet(null);
        CachedScheduler oldSingle = CACHED_SINGLE.getAndSet(null);
        if (oldElastic != null) {
            oldElastic._dispose();
        }
        if (oldBoundedElastic != null) {
            oldBoundedElastic._dispose();
        }
        if (oldParallel != null) {
            oldParallel._dispose();
        }
        if (oldSingle != null) {
            oldSingle._dispose();
        }
    }

    public static Scheduler single() {
        return Schedulers.cache(CACHED_SINGLE, SINGLE, SINGLE_SUPPLIER);
    }

    public static Scheduler single(Scheduler original) {
        return new SingleWorkerScheduler(original);
    }

    static CachedScheduler cache(AtomicReference<CachedScheduler> reference, String key, Supplier<Scheduler> supplier) {
        CachedScheduler s = reference.get();
        if (s != null) {
            return s;
        }
        s = new CachedScheduler(key, supplier.get());
        if (reference.compareAndSet(null, s)) {
            return s;
        }
        s._dispose();
        return reference.get();
    }

    static final void defaultUncaughtException(Thread t, Throwable e) {
        LOGGER.error("Scheduler worker in group " + t.getThreadGroup().getName() + " failed with an uncaught exception", e);
    }

    static void handleError(Throwable ex) {
        Thread thread = Thread.currentThread();
        Throwable t = Exceptions.unwrap(ex);
        Thread.UncaughtExceptionHandler x = thread.getUncaughtExceptionHandler();
        if (x != null) {
            x.uncaughtException(thread, t);
        } else {
            LOGGER.error("Scheduler worker failed with an uncaught exception", t);
        }
        BiConsumer<Thread, ? super Throwable> hook = onHandleErrorHook;
        if (hook != null) {
            hook.accept(thread, t);
        }
    }

    static Disposable directSchedule(ScheduledExecutorService exec, Runnable task, @Nullable Disposable parent, long delay, TimeUnit unit) {
        task = Schedulers.onSchedule(task);
        SchedulerTask sr = new SchedulerTask(task, parent);
        Future<Void> f = delay <= 0L ? exec.submit(sr) : exec.schedule(sr, delay, unit);
        sr.setFuture(f);
        return sr;
    }

    static Disposable directSchedulePeriodically(ScheduledExecutorService exec, Runnable task, long initialDelay, long period, TimeUnit unit) {
        task = Schedulers.onSchedule(task);
        if (period <= 0L) {
            InstantPeriodicWorkerTask isr = new InstantPeriodicWorkerTask(task, exec);
            Future<Void> f = initialDelay <= 0L ? exec.submit(isr) : exec.schedule(isr, initialDelay, unit);
            isr.setFirst(f);
            return isr;
        }
        PeriodicSchedulerTask sr = new PeriodicSchedulerTask(task);
        ScheduledFuture<?> f = exec.scheduleAtFixedRate(sr, initialDelay, period, unit);
        sr.setFuture(f);
        return sr;
    }

    static Disposable workerSchedule(ScheduledExecutorService exec, Disposable.Composite tasks, Runnable task, long delay, TimeUnit unit) {
        WorkerTask sr = new WorkerTask(task = Schedulers.onSchedule(task), tasks);
        if (!tasks.add(sr)) {
            throw Exceptions.failWithRejected();
        }
        try {
            Future<Void> f = delay <= 0L ? exec.submit(sr) : exec.schedule(sr, delay, unit);
            sr.setFuture(f);
        }
        catch (RejectedExecutionException ex) {
            sr.dispose();
            throw ex;
        }
        return sr;
    }

    static Disposable workerSchedulePeriodically(ScheduledExecutorService exec, Disposable.Composite tasks, Runnable task, long initialDelay, long period, TimeUnit unit) {
        task = Schedulers.onSchedule(task);
        if (period <= 0L) {
            InstantPeriodicWorkerTask isr = new InstantPeriodicWorkerTask(task, exec, tasks);
            if (!tasks.add(isr)) {
                throw Exceptions.failWithRejected();
            }
            try {
                Future<Void> f = initialDelay <= 0L ? exec.submit(isr) : exec.schedule(isr, initialDelay, unit);
                isr.setFirst(f);
            }
            catch (RejectedExecutionException ex) {
                isr.dispose();
                throw ex;
            }
            catch (IllegalArgumentException | NullPointerException ex) {
                isr.dispose();
                throw new RejectedExecutionException(ex);
            }
            return isr;
        }
        PeriodicWorkerTask sr = new PeriodicWorkerTask(task, tasks);
        if (!tasks.add(sr)) {
            throw Exceptions.failWithRejected();
        }
        try {
            ScheduledFuture<?> f = exec.scheduleAtFixedRate(sr, initialDelay, period, unit);
            sr.setFuture(f);
        }
        catch (RejectedExecutionException ex) {
            sr.dispose();
            throw ex;
        }
        catch (IllegalArgumentException | NullPointerException ex) {
            sr.dispose();
            throw new RejectedExecutionException(ex);
        }
        return sr;
    }

    @Nullable
    static final Object scanExecutor(Executor executor, Scannable.Attr key) {
        if (executor instanceof DelegateServiceScheduler.UnsupportedScheduledExecutorService) {
            executor = ((DelegateServiceScheduler.UnsupportedScheduledExecutorService)executor).get();
        }
        if (executor instanceof Scannable) {
            return ((Scannable)((Object)executor)).scanUnsafe(key);
        }
        if (executor instanceof ExecutorService) {
            ExecutorService service = (ExecutorService)executor;
            if (key == Scannable.Attr.TERMINATED) {
                return service.isTerminated();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return service.isShutdown();
            }
        }
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor)executor;
            if (key == Scannable.Attr.CAPACITY) {
                return poolExecutor.getMaximumPoolSize();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return Long.valueOf(poolExecutor.getTaskCount() - poolExecutor.getCompletedTaskCount()).intValue();
            }
            if (key == Scannable.Attr.LARGE_BUFFERED) {
                return poolExecutor.getTaskCount() - poolExecutor.getCompletedTaskCount();
            }
        }
        return null;
    }

    static {
        onScheduleHooks = new LinkedHashMap(1);
        LOGGER = Loggers.getLogger(Schedulers.class);
    }

    static class CachedScheduler
    implements Scheduler,
    Supplier<Scheduler>,
    Scannable {
        final Scheduler cached;
        final String stringRepresentation;

        CachedScheduler(String key, Scheduler cached) {
            this.cached = cached;
            this.stringRepresentation = "Schedulers." + key + "()";
        }

        @Override
        public Disposable schedule(Runnable task) {
            return this.cached.schedule(task);
        }

        @Override
        public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
            return this.cached.schedule(task, delay, unit);
        }

        @Override
        public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
            return this.cached.schedulePeriodically(task, initialDelay, period, unit);
        }

        @Override
        public Scheduler.Worker createWorker() {
            return this.cached.createWorker();
        }

        @Override
        public long now(TimeUnit unit) {
            return this.cached.now(unit);
        }

        @Override
        public void start() {
            this.cached.start();
        }

        @Override
        public void dispose() {
        }

        @Override
        public boolean isDisposed() {
            return this.cached.isDisposed();
        }

        public String toString() {
            return this.stringRepresentation;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (Scannable.Attr.NAME == key) {
                return this.stringRepresentation;
            }
            return Scannable.from(this.cached).scanUnsafe(key);
        }

        @Override
        public Scheduler get() {
            return this.cached;
        }

        void _dispose() {
            this.cached.dispose();
        }
    }

    public static final class Snapshot
    implements Disposable {
        @Nullable
        final CachedScheduler oldElasticScheduler;
        @Nullable
        final CachedScheduler oldBoundedElasticScheduler;
        @Nullable
        final CachedScheduler oldParallelScheduler;
        @Nullable
        final CachedScheduler oldSingleScheduler;
        final Factory oldFactory;

        private Snapshot(@Nullable CachedScheduler oldElasticScheduler, @Nullable CachedScheduler oldBoundedElasticScheduler, @Nullable CachedScheduler oldParallelScheduler, @Nullable CachedScheduler oldSingleScheduler, Factory factory) {
            this.oldElasticScheduler = oldElasticScheduler;
            this.oldBoundedElasticScheduler = oldBoundedElasticScheduler;
            this.oldParallelScheduler = oldParallelScheduler;
            this.oldSingleScheduler = oldSingleScheduler;
            this.oldFactory = factory;
        }

        @Override
        public boolean isDisposed() {
            return !(this.oldElasticScheduler != null && !this.oldElasticScheduler.isDisposed() || this.oldBoundedElasticScheduler != null && !this.oldBoundedElasticScheduler.isDisposed() || this.oldParallelScheduler != null && !this.oldParallelScheduler.isDisposed() || this.oldSingleScheduler != null && !this.oldSingleScheduler.isDisposed());
        }

        @Override
        public void dispose() {
            if (this.oldElasticScheduler != null) {
                this.oldElasticScheduler._dispose();
            }
            if (this.oldBoundedElasticScheduler != null) {
                this.oldBoundedElasticScheduler._dispose();
            }
            if (this.oldParallelScheduler != null) {
                this.oldParallelScheduler._dispose();
            }
            if (this.oldSingleScheduler != null) {
                this.oldSingleScheduler._dispose();
            }
        }
    }

    public static interface Factory {
        @Deprecated
        default public Scheduler newElastic(int ttlSeconds, ThreadFactory threadFactory) {
            return new ElasticScheduler(threadFactory, ttlSeconds);
        }

        default public Scheduler newBoundedElastic(int threadCap, int queuedTaskCap, ThreadFactory threadFactory, int ttlSeconds) {
            return new BoundedElasticScheduler(threadCap, queuedTaskCap, threadFactory, ttlSeconds);
        }

        default public Scheduler newParallel(int parallelism, ThreadFactory threadFactory) {
            return new ParallelScheduler(parallelism, threadFactory);
        }

        default public Scheduler newSingle(ThreadFactory threadFactory) {
            return new SingleScheduler(threadFactory);
        }
    }
}


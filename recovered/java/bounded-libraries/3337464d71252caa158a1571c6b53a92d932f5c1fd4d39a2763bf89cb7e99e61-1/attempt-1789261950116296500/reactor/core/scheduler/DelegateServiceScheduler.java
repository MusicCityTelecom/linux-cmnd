/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.scheduler;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.scheduler.ExecutorServiceWorker;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

final class DelegateServiceScheduler
implements Scheduler,
Scannable {
    final String executorName;
    final ScheduledExecutorService original;
    @Nullable
    volatile ScheduledExecutorService executor;
    static final AtomicReferenceFieldUpdater<DelegateServiceScheduler, ScheduledExecutorService> EXECUTOR = AtomicReferenceFieldUpdater.newUpdater(DelegateServiceScheduler.class, ScheduledExecutorService.class, "executor");

    DelegateServiceScheduler(String executorName, ExecutorService executorService) {
        this.executorName = executorName;
        this.original = DelegateServiceScheduler.convert(executorService);
        this.executor = null;
    }

    ScheduledExecutorService getOrCreate() {
        ScheduledExecutorService e = this.executor;
        if (e == null) {
            this.start();
            e = this.executor;
            if (e == null) {
                throw new IllegalStateException("executor is null after implicit start()");
            }
        }
        return e;
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new ExecutorServiceWorker(this.getOrCreate());
    }

    @Override
    public Disposable schedule(Runnable task) {
        return Schedulers.directSchedule(this.getOrCreate(), task, null, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    public Disposable schedule(Runnable task, long delay, TimeUnit unit) {
        return Schedulers.directSchedule(this.getOrCreate(), task, null, delay, unit);
    }

    @Override
    public Disposable schedulePeriodically(Runnable task, long initialDelay, long period, TimeUnit unit) {
        return Schedulers.directSchedulePeriodically(this.getOrCreate(), task, initialDelay, period, unit);
    }

    @Override
    public void start() {
        EXECUTOR.compareAndSet(this, null, Schedulers.decorateExecutorService(this, this.original));
    }

    @Override
    public boolean isDisposed() {
        ScheduledExecutorService e = this.executor;
        return e != null && e.isShutdown();
    }

    @Override
    public void dispose() {
        ScheduledExecutorService e = this.executor;
        if (e != null) {
            e.shutdownNow();
        }
    }

    static ScheduledExecutorService convert(ExecutorService executor) {
        if (executor instanceof ScheduledExecutorService) {
            return (ScheduledExecutorService)executor;
        }
        return new UnsupportedScheduledExecutorService(executor);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.NAME) {
            return this.toString();
        }
        ScheduledExecutorService e = this.executor;
        if (e != null) {
            return Schedulers.scanExecutor(e, key);
        }
        return null;
    }

    public String toString() {
        return "fromExecutorService(" + this.executorName + ')';
    }

    static final class UnsupportedScheduledExecutorService
    implements ScheduledExecutorService,
    Supplier<ExecutorService> {
        final ExecutorService exec;

        UnsupportedScheduledExecutorService(ExecutorService exec) {
            this.exec = exec;
        }

        @Override
        public ExecutorService get() {
            return this.exec;
        }

        @Override
        public void shutdown() {
            this.exec.shutdown();
        }

        @Override
        @NonNull
        public List<Runnable> shutdownNow() {
            return this.exec.shutdownNow();
        }

        @Override
        public boolean isShutdown() {
            return this.exec.isShutdown();
        }

        @Override
        public boolean isTerminated() {
            return this.exec.isTerminated();
        }

        @Override
        public boolean awaitTermination(long timeout, @NonNull TimeUnit unit) throws InterruptedException {
            return this.exec.awaitTermination(timeout, unit);
        }

        @Override
        @NonNull
        public <T> Future<T> submit(@NonNull Callable<T> task) {
            return this.exec.submit(task);
        }

        @Override
        @NonNull
        public <T> Future<T> submit(@NonNull Runnable task, T result) {
            return this.exec.submit(task, result);
        }

        @Override
        @NonNull
        public Future<?> submit(@NonNull Runnable task) {
            return this.exec.submit(task);
        }

        @Override
        @NonNull
        public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks) throws InterruptedException {
            return this.exec.invokeAll(tasks);
        }

        @Override
        @NonNull
        public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks, long timeout, @NonNull TimeUnit unit) throws InterruptedException {
            return this.exec.invokeAll(tasks, timeout, unit);
        }

        @Override
        @NonNull
        public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
            return this.exec.invokeAny(tasks);
        }

        @Override
        public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks, long timeout, @NonNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.exec.invokeAny(tasks, timeout, unit);
        }

        @Override
        public void execute(@NonNull Runnable command) {
            this.exec.execute(command);
        }

        @Override
        @NonNull
        public ScheduledFuture<?> schedule(@NonNull Runnable command, long delay, @NonNull TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override
        @NonNull
        public <V> ScheduledFuture<V> schedule(@NonNull Callable<V> callable, long delay, @NonNull TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override
        @NonNull
        public ScheduledFuture<?> scheduleAtFixedRate(@NonNull Runnable command, long initialDelay, long period, @NonNull TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        @Override
        @NonNull
        public ScheduledFuture<?> scheduleWithFixedDelay(@NonNull Runnable command, long initialDelay, long delay, @NonNull TimeUnit unit) {
            throw Exceptions.failWithRejectedNotTimeCapable();
        }

        public String toString() {
            return this.exec.toString();
        }
    }
}


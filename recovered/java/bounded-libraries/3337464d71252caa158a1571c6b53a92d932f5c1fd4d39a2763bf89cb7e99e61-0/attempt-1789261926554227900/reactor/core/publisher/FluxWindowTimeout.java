/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.StateLogger;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxWindowTimeout<T>
extends InternalFluxOperator<T, Flux<T>> {
    final int maxSize;
    final long timespan;
    final TimeUnit unit;
    final Scheduler timer;
    final boolean fairBackpressure;

    FluxWindowTimeout(Flux<T> source, int maxSize, long timespan, TimeUnit unit, Scheduler timer, boolean fairBackpressure) {
        super(source);
        if (timespan <= 0L) {
            throw new IllegalArgumentException("Timeout period must be strictly positive");
        }
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize must be strictly positive");
        }
        this.fairBackpressure = fairBackpressure;
        this.timer = Objects.requireNonNull(timer, "Timer");
        this.timespan = timespan;
        this.unit = Objects.requireNonNull(unit, "unit");
        this.maxSize = maxSize;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> actual) {
        if (this.fairBackpressure) {
            return new WindowTimeoutWithBackpressureSubscriber(actual, this.maxSize, this.timespan, this.unit, this.timer, null);
        }
        return new WindowTimeoutSubscriber(actual, this.maxSize, this.timespan, this.unit, this.timer);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.timer;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class WindowTimeoutSubscriber<T>
    implements InnerOperator<T, Flux<T>> {
        final CoreSubscriber<? super Flux<T>> actual;
        final long timespan;
        final TimeUnit unit;
        final Scheduler scheduler;
        final int maxSize;
        final Scheduler.Worker worker;
        final Queue<Object> queue;
        Throwable error;
        volatile boolean done;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<WindowTimeoutSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowTimeoutSubscriber.class, "requested");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<WindowTimeoutSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(WindowTimeoutSubscriber.class, "wip");
        int count;
        long producerIndex;
        Subscription s;
        Sinks.Many<T> window;
        volatile boolean terminated;
        volatile Disposable timer;
        static final AtomicReferenceFieldUpdater<WindowTimeoutSubscriber, Disposable> TIMER = AtomicReferenceFieldUpdater.newUpdater(WindowTimeoutSubscriber.class, Disposable.class, "timer");

        WindowTimeoutSubscriber(CoreSubscriber<? super Flux<T>> actual, int maxSize, long timespan, TimeUnit unit, Scheduler scheduler) {
            this.actual = actual;
            this.queue = Queues.unboundedMultiproducer().get();
            this.timespan = timespan;
            this.unit = unit;
            this.scheduler = scheduler;
            this.maxSize = maxSize;
            this.worker = scheduler.createWorker();
        }

        @Override
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            Sinks.Many<T> w = this.window;
            return w == null ? Stream.empty() : Stream.of(Scannable.from(w));
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.maxSize;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size();
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.worker;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                CoreSubscriber<? super Flux<T>> a = this.actual;
                a.onSubscribe(this);
                if (this.cancelled) {
                    return;
                }
                Sinks.Many w = Sinks.unsafe().many().unicast().onBackpressureBuffer();
                this.window = w;
                long r = this.requested;
                if (r != 0L) {
                    a.onNext(w.asFlux());
                    if (r != Long.MAX_VALUE) {
                        REQUESTED.decrementAndGet(this);
                    }
                } else {
                    a.onError(Operators.onOperatorError(s, Exceptions.failWithOverflow(), this.actual.currentContext()));
                    return;
                }
                if (OperatorDisposables.replace(TIMER, this, this.newPeriod())) {
                    s.request(Long.MAX_VALUE);
                }
            }
        }

        Disposable newPeriod() {
            try {
                return this.worker.schedulePeriodically(new ConsumerIndexHolder(this.producerIndex, this), this.timespan, this.timespan, this.unit);
            }
            catch (Exception e) {
                this.actual.onError(Operators.onRejectedExecution(e, this.s, null, null, this.actual.currentContext()));
                return Disposables.disposed();
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onNext(T t) {
            if (this.terminated) {
                return;
            }
            if (WIP.get(this) == 0 && WIP.compareAndSet(this, 0, 1)) {
                Sinks.Many<T> w = this.window;
                w.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
                int c = this.count + 1;
                if (c >= this.maxSize) {
                    ++this.producerIndex;
                    this.count = 0;
                    w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                    long r = this.requested;
                    if (r == 0L) {
                        this.window = null;
                        this.actual.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow(), t, this.actual.currentContext()));
                        this.timer.dispose();
                        this.worker.dispose();
                        return;
                    }
                    w = Sinks.unsafe().many().unicast().onBackpressureBuffer();
                    this.window = w;
                    this.actual.onNext(w.asFlux());
                    if (r != Long.MAX_VALUE) {
                        REQUESTED.decrementAndGet(this);
                    }
                    Disposable tm = this.timer;
                    tm.dispose();
                    Disposable task = this.newPeriod();
                    if (!TIMER.compareAndSet(this, tm, task)) {
                        task.dispose();
                    }
                } else {
                    this.count = c;
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return;
                }
            } else {
                this.queue.offer(t);
                if (!this.enter()) {
                    return;
                }
            }
            this.drainLoop();
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            if (this.enter()) {
                this.drainLoop();
            }
            this.actual.onError(t);
            this.timer.dispose();
            this.worker.dispose();
        }

        public void onComplete() {
            this.done = true;
            if (this.enter()) {
                this.drainLoop();
            }
            this.actual.onComplete();
            this.timer.dispose();
            this.worker.dispose();
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        public void cancel() {
            this.cancelled = true;
        }

        void drainLoop() {
            Queue<Object> q = this.queue;
            CoreSubscriber<? super Flux<T>> a = this.actual;
            Sinks.Many<Object> w = this.window;
            int missed = 1;
            while (true) {
                if (this.terminated) {
                    this.s.cancel();
                    q.clear();
                    this.timer.dispose();
                    this.worker.dispose();
                    return;
                }
                boolean d = this.done;
                Object o = q.poll();
                boolean empty = o == null;
                boolean isHolder = o instanceof ConsumerIndexHolder;
                if (d && (empty || isHolder)) {
                    this.window = null;
                    q.clear();
                    Throwable err = this.error;
                    if (err != null) {
                        w.emitError(err, Sinks.EmitFailureHandler.FAIL_FAST);
                    } else {
                        w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                    }
                    this.timer.dispose();
                    this.worker.dispose();
                    return;
                }
                if (!empty) {
                    if (isHolder) {
                        w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        this.count = 0;
                        w = Sinks.unsafe().many().unicast().onBackpressureBuffer();
                        this.window = w;
                        long r = this.requested;
                        if (r != 0L) {
                            a.onNext(w.asFlux());
                            if (r == Long.MAX_VALUE) continue;
                            REQUESTED.decrementAndGet(this);
                            continue;
                        }
                        this.window = null;
                        this.queue.clear();
                        a.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow(), this.actual.currentContext()));
                        this.timer.dispose();
                        this.worker.dispose();
                        return;
                    }
                    w.emitNext(o, Sinks.EmitFailureHandler.FAIL_FAST);
                    int c = this.count + 1;
                    if (c >= this.maxSize) {
                        ++this.producerIndex;
                        this.count = 0;
                        w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        long r = this.requested;
                        if (r != 0L) {
                            w = Sinks.unsafe().many().unicast().onBackpressureBuffer();
                            this.window = w;
                            this.actual.onNext(w.asFlux());
                            if (r != Long.MAX_VALUE) {
                                REQUESTED.decrementAndGet(this);
                            }
                            Disposable tm = this.timer;
                            tm.dispose();
                            Disposable task = this.newPeriod();
                            if (TIMER.compareAndSet(this, tm, task)) continue;
                            task.dispose();
                            continue;
                        }
                        this.window = null;
                        a.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow(), o, this.actual.currentContext()));
                        this.timer.dispose();
                        this.worker.dispose();
                        return;
                    }
                    this.count = c;
                    continue;
                }
                if ((missed = WIP.addAndGet(this, -missed)) == 0) break;
            }
        }

        boolean enter() {
            return WIP.getAndIncrement(this) == 0;
        }

        static final class ConsumerIndexHolder
        implements Runnable {
            final long index;
            final WindowTimeoutSubscriber<?> parent;

            ConsumerIndexHolder(long index, WindowTimeoutSubscriber<?> parent) {
                this.index = index;
                this.parent = parent;
            }

            @Override
            public void run() {
                WindowTimeoutSubscriber<?> p = this.parent;
                if (!p.cancelled) {
                    p.queue.offer(this);
                } else {
                    p.terminated = true;
                    p.timer.dispose();
                    p.worker.dispose();
                }
                if (p.enter()) {
                    p.drainLoop();
                }
            }
        }
    }

    static final class InnerWindow<T>
    extends Flux<T>
    implements InnerProducer<T>,
    Runnable {
        static final Disposable DISPOSED = Disposables.disposed();
        @Nullable
        final StateLogger logger;
        final WindowTimeoutWithBackpressureSubscriber<T> parent;
        final int max;
        final Queue<T> queue;
        final long createTime;
        final int index;
        volatile long requested;
        static final AtomicLongFieldUpdater<InnerWindow> REQUESTED = AtomicLongFieldUpdater.newUpdater(InnerWindow.class, "requested");
        volatile long state;
        static final AtomicLongFieldUpdater<InnerWindow> STATE = AtomicLongFieldUpdater.newUpdater(InnerWindow.class, "state");
        volatile Disposable timer;
        static final AtomicReferenceFieldUpdater<InnerWindow, Disposable> TIMER = AtomicReferenceFieldUpdater.newUpdater(InnerWindow.class, Disposable.class, "timer");
        CoreSubscriber<? super T> actual;
        Throwable error;
        int received = 0;
        int produced = 0;
        static final long FINALIZED_STATE = Long.MIN_VALUE;
        static final long TERMINATED_STATE = 0x4000000000000000L;
        static final long PARENT_CANCELLED_STATE = 0x2000000000000000L;
        static final long CANCELLED_STATE = 0x1000000000000000L;
        static final long TIMEOUT_STATE = 0x800000000000000L;
        static final long HAS_VALUES_STATE = 0x400000000000000L;
        static final long HAS_SUBSCRIBER_STATE = 0x200000000000000L;
        static final long HAS_SUBSCRIBER_SET_STATE = 0x100000000000000L;
        static final long UNSENT_STATE = 0x80000000000000L;
        static final long RECEIVED_MASK = 0x7FFFFFFF000000L;
        static final long WORK_IN_PROGRESS_MAX = 0xFFFFFFL;
        static final long RECEIVED_SHIFT_BITS = 24L;

        InnerWindow(int max, WindowTimeoutWithBackpressureSubscriber<T> parent, int index, boolean markUnsent, @Nullable StateLogger logger) {
            this.max = max;
            this.parent = parent;
            this.queue = Queues.get(max).get();
            this.index = index;
            this.logger = logger;
            if (markUnsent) {
                STATE.lazySet(this, 0x80000000000000L);
                if (this.logger != null) {
                    this.logger.log(this.toString(), "mct", 0L, 0x80000000000000L);
                }
            } else if (this.logger != null) {
                this.logger.log(this.toString(), "mct", 0, 0);
            }
            this.createTime = parent.now();
        }

        @Override
        public void subscribe(CoreSubscriber<? super T> actual) {
            long previousState = InnerWindow.markSubscribedOnce(this);
            if (InnerWindow.hasSubscribedOnce(previousState)) {
                Operators.error(actual, new IllegalStateException("Only one subscriber allowed"));
                return;
            }
            this.actual = actual;
            actual.onSubscribe(this);
            previousState = InnerWindow.markSubscriberSet(this);
            if (InnerWindow.isFinalized(previousState) || InnerWindow.hasWorkInProgress(previousState)) {
                return;
            }
            if (!InnerWindow.hasValues(previousState) && InnerWindow.isTerminated(previousState)) {
                Throwable t = this.error;
                if (t != null) {
                    actual.onError(t);
                } else {
                    actual.onComplete();
                }
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            Operators.addCap(REQUESTED, this, n);
            long previousState = InnerWindow.markHasRequest(this);
            if (InnerWindow.hasWorkInProgress(previousState) || InnerWindow.isCancelled(previousState) || InnerWindow.isFinalized(previousState)) {
                return;
            }
            if (InnerWindow.hasValues(previousState)) {
                this.drain((previousState | 0x100000000000000L) + 1L);
            }
        }

        public void cancel() {
            long previousState = InnerWindow.markCancelled(this);
            if (InnerWindow.isCancelled(previousState) || InnerWindow.isFinalized(previousState) || InnerWindow.hasWorkInProgress(previousState)) {
                return;
            }
            this.clearAndFinalize();
        }

        long sendCancel() {
            Disposable timer;
            long l;
            long cleanState;
            long nextState;
            long state;
            do {
                if (InnerWindow.isCancelledByParent(state = this.state)) {
                    return state;
                }
                cleanState = state & 0xFFFFFFFFFF000000L;
                if (InnerWindow.hasSubscriberSet(state)) {
                    if (InnerWindow.hasValues(state)) {
                        l = InnerWindow.incrementWork(state & 0xFFFFFFL);
                        continue;
                    }
                    l = Long.MIN_VALUE;
                    continue;
                }
                l = 0L;
            } while (!STATE.compareAndSet(this, state, nextState = cleanState | 0x4000000000000000L | 0x2000000000000000L | l));
            if (InnerWindow.isFinalized(state)) {
                return state;
            }
            if (!InnerWindow.isTimeout(state) && (timer = TIMER.getAndSet(this, DISPOSED)) != null) {
                timer.dispose();
            }
            if (InnerWindow.hasSubscriberSet(state)) {
                if (InnerWindow.hasWorkInProgress(state)) {
                    return state;
                }
                if (InnerWindow.isCancelled(state)) {
                    this.clearAndFinalize();
                    return state;
                }
                if (InnerWindow.hasValues(state)) {
                    this.drain(nextState);
                } else {
                    this.actual.onComplete();
                }
            }
            return state;
        }

        boolean sendNext(T t) {
            long nextState;
            long previousState;
            int received = this.received + 1;
            if (received > this.max) {
                return false;
            }
            this.received = received;
            this.queue.offer(t);
            if (received == this.max) {
                previousState = InnerWindow.markHasValuesAndTerminated(this);
                nextState = (previousState | 0x4000000000000000L | 0x400000000000000L) + 1L;
                if (!InnerWindow.isTimeout(previousState)) {
                    Disposable timer = TIMER.getAndSet(this, DISPOSED);
                    if (timer != null) {
                        timer.dispose();
                    }
                    if (!InnerWindow.isCancelledByParent(previousState)) {
                        this.parent.tryCreateNextWindow(this.index);
                    }
                }
            } else {
                previousState = InnerWindow.markHasValues(this);
                nextState = (previousState | 0x400000000000000L) + 1L;
            }
            if (InnerWindow.isFinalized(previousState)) {
                if (InnerWindow.isCancelledByParent(previousState)) {
                    this.clearQueue();
                    return true;
                }
                if (InnerWindow.isCancelled(previousState)) {
                    this.clearQueue();
                    this.parent.s.request(1L);
                    return true;
                }
                if (this.queue.poll() != t) {
                    this.parent.s.request(1L);
                    return true;
                }
                return false;
            }
            if (InnerWindow.isTimeout(previousState) && InnerWindow.isTerminated(previousState)) {
                this.parent.s.request(1L);
            }
            if (InnerWindow.hasSubscriberSet(previousState)) {
                if (InnerWindow.hasWorkInProgress(previousState)) {
                    return true;
                }
                if (InnerWindow.isCancelled(previousState)) {
                    this.clearAndFinalize();
                    return true;
                }
                this.drain(nextState);
            }
            return true;
        }

        long sendComplete() {
            Disposable timer;
            long previousState = InnerWindow.markTerminated(this);
            if (InnerWindow.isFinalized(previousState) || InnerWindow.isTerminated(previousState)) {
                return previousState;
            }
            if (!InnerWindow.isTimeout(previousState) && (timer = TIMER.getAndSet(this, DISPOSED)) != null) {
                timer.dispose();
            }
            if (InnerWindow.hasSubscriberSet(previousState)) {
                if (InnerWindow.hasWorkInProgress(previousState)) {
                    return previousState;
                }
                if (InnerWindow.isCancelled(previousState)) {
                    this.clearAndFinalize();
                    return previousState;
                }
                if (InnerWindow.hasValues(previousState)) {
                    this.drain((previousState | 0x4000000000000000L) + 1L);
                } else {
                    this.actual.onComplete();
                }
            }
            return previousState;
        }

        long sendError(Throwable error) {
            Disposable timer;
            this.error = error;
            long previousState = InnerWindow.markTerminated(this);
            if (InnerWindow.isFinalized(previousState) || InnerWindow.isTerminated(previousState)) {
                return previousState;
            }
            if (!InnerWindow.isTimeout(previousState) && (timer = TIMER.getAndSet(this, DISPOSED)) != null) {
                timer.dispose();
            }
            if (InnerWindow.hasSubscriberSet(previousState)) {
                if (InnerWindow.hasWorkInProgress(previousState)) {
                    return previousState;
                }
                if (InnerWindow.isCancelled(previousState)) {
                    this.clearAndFinalize();
                    return previousState;
                }
                if (InnerWindow.hasValues(previousState)) {
                    this.drain((previousState | 0x4000000000000000L) + 1L);
                } else {
                    this.actual.onError(error);
                }
            }
            return previousState;
        }

        long sendSent() {
            long previousState = InnerWindow.markSent(this);
            if (InnerWindow.isFinalized(previousState) || !InnerWindow.isTerminated(previousState) && !InnerWindow.isTimeout(previousState)) {
                return previousState;
            }
            if (InnerWindow.hasSubscriberSet(previousState)) {
                if (InnerWindow.hasWorkInProgress(previousState)) {
                    return previousState;
                }
                if (InnerWindow.isCancelled(previousState)) {
                    this.clearAndFinalize();
                    return previousState;
                }
                if (InnerWindow.hasValues(previousState)) {
                    this.drain((previousState ^ 0x80000000000000L | 0x4000000000000000L) + 1L);
                } else {
                    this.actual.onComplete();
                }
            }
            return previousState;
        }

        void drain(long expectedState) {
            Queue<T> q = this.queue;
            CoreSubscriber<? super T> a = this.actual;
            do {
                long r = this.requested;
                int e = 0;
                boolean empty = false;
                while ((long)e < r) {
                    T v = q.poll();
                    boolean bl = empty = v == null;
                    if (this.checkTerminated(this.produced + e, a, v)) {
                        return;
                    }
                    if (empty) break;
                    a.onNext(v);
                    ++e;
                }
                this.produced += e;
                if (this.checkTerminated(this.produced, a, null)) {
                    return;
                }
                if (e != 0 && r != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -e);
                }
                if (!InnerWindow.isCancelled(expectedState = InnerWindow.markWorkDone(this, expectedState, !empty))) continue;
                this.clearAndFinalize();
                return;
            } while (InnerWindow.hasWorkInProgress(expectedState));
        }

        boolean checkTerminated(int totalProduced, CoreSubscriber<? super T> actual, @Nullable T value) {
            long state = this.state;
            if (InnerWindow.isCancelled(state)) {
                if (value != null) {
                    Operators.onDiscard(value, actual.currentContext());
                }
                this.clearAndFinalize();
                return true;
            }
            if (value == null && InnerWindow.received(state) <= (long)totalProduced && InnerWindow.isTerminated(state)) {
                if (!this.markFinalized(state)) {
                    return false;
                }
                Throwable e = this.error;
                if (e != null) {
                    actual.onError(e);
                } else {
                    actual.onComplete();
                }
                return true;
            }
            return false;
        }

        void scheduleTimeout() {
            Disposable nextTimer = this.parent.schedule(this, this.createTime);
            if (!TIMER.compareAndSet(this, null, nextTimer)) {
                nextTimer.dispose();
            }
        }

        @Override
        public void run() {
            long previousState = InnerWindow.markTimeout(this);
            if (InnerWindow.isTerminated(previousState) || InnerWindow.isCancelledByParent(previousState)) {
                return;
            }
            this.parent.tryCreateNextWindow(this.index);
        }

        void clearAndFinalize() {
            long nextState;
            long state;
            do {
                state = this.state;
                this.clearQueue();
                if (!InnerWindow.isFinalized(state)) continue;
                return;
            } while (!STATE.compareAndSet(this, state, nextState = (state | Long.MIN_VALUE) & 0xFFFFFFFFFF000000L ^ (InnerWindow.hasValues(state) ? 0x400000000000000L : 0L)));
        }

        boolean markFinalized(long state) {
            long nextState = (state | Long.MIN_VALUE) & 0xFFFFFFFFFF000000L ^ (InnerWindow.hasValues(state) ? 0x400000000000000L : 0L);
            return STATE.compareAndSet(this, state, nextState);
        }

        void clearQueue() {
            T v;
            Context context;
            Queue<T> q = this.queue;
            Context context2 = context = this.actual != null ? this.actual.currentContext() : this.parent.currentContext();
            while ((v = q.poll()) != null) {
                Operators.onDiscard(v, context);
            }
        }

        static <T> long markSent(InnerWindow<T> instance) {
            long l;
            long nextState;
            long state;
            do {
                if (InnerWindow.isCancelled(state = instance.state)) {
                    return state;
                }
                if (InnerWindow.isTimeout(state) || InnerWindow.isTerminated(state)) {
                    l = 0x4000000000000000L | (InnerWindow.hasSubscriberSet(state) ? (InnerWindow.hasValues(state) ? InnerWindow.incrementWork(state & 0xFFFFFFL) : Long.MIN_VALUE) : 0L);
                    continue;
                }
                l = 0L;
            } while (!STATE.compareAndSet(instance, state, nextState = state ^ 0x80000000000000L | l));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mst", state, nextState);
            }
            return state;
        }

        static boolean isSent(long state) {
            return (state & 0x80000000000000L) == 0L;
        }

        static <T> long markTimeout(InnerWindow<T> instance) {
            long nextState;
            long state;
            do {
                if (!InnerWindow.isTerminated(state = instance.state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state | 0x800000000000000L));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mtt", state, nextState);
            }
            return state;
        }

        static boolean isTimeout(long state) {
            return (state & 0x800000000000000L) == 0x800000000000000L;
        }

        static <T> long markCancelled(InnerWindow<T> instance) {
            long cleanState;
            long nextState;
            long state;
            do {
                if (!InnerWindow.isCancelled(state = instance.state) && !InnerWindow.isFinalized(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = (cleanState = state & 0xFFFFFFFFFF000000L) | 0x1000000000000000L | 0x100000000000000L | InnerWindow.incrementWork(state & 0xFFFFFFL)));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mcd", state, nextState);
            }
            return state;
        }

        static boolean isCancelled(long state) {
            return (state & 0x1000000000000000L) == 0x1000000000000000L;
        }

        static boolean isCancelledByParent(long state) {
            return (state & 0x2000000000000000L) == 0x2000000000000000L;
        }

        static <T> long markHasValues(InnerWindow<T> instance) {
            long nextState;
            long state;
            do {
                long cleanState;
                if (InnerWindow.isFinalized(state = instance.state)) {
                    if (instance.logger != null) {
                        instance.logger.log(instance.toString(), "fhv", state, state);
                    }
                    return state;
                }
                if (InnerWindow.hasSubscriberSet(state)) {
                    cleanState = state & 0xFFFFFFFFFF000000L & 0xFF80000000FFFFFFL;
                    nextState = cleanState | 0x400000000000000L | InnerWindow.incrementReceived(state & 0x7FFFFFFF000000L) | InnerWindow.incrementWork(state & 0xFFFFFFL);
                    continue;
                }
                cleanState = state & 0xFFFFFFFFFF000000L & 0xFF80000000FFFFFFL;
                nextState = cleanState | 0x400000000000000L | InnerWindow.incrementReceived(state & 0x7FFFFFFF000000L) | (InnerWindow.hasWorkInProgress(state) ? InnerWindow.incrementWork(state & 0xFFFFFFL) : 0L);
            } while (!STATE.compareAndSet(instance, state, nextState));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mhv", state, nextState);
            }
            return state;
        }

        static <T> long markHasValuesAndTerminated(InnerWindow<T> instance) {
            long cleanState;
            long nextState;
            long state;
            do {
                if (InnerWindow.isFinalized(state = instance.state)) {
                    if (instance.logger != null) {
                        instance.logger.log(instance.toString(), "fht", state, state);
                    }
                    return state;
                }
                cleanState = state & 0xFFFFFFFFFF000000L & 0xFF80000000FFFFFFL;
            } while (!STATE.compareAndSet(instance, state, nextState = InnerWindow.hasSubscriberSet(state) ? cleanState | 0x400000000000000L | 0x4000000000000000L | InnerWindow.incrementReceived(state & 0x7FFFFFFF000000L) | InnerWindow.incrementWork(state & 0xFFFFFFL) : cleanState | 0x400000000000000L | 0x4000000000000000L | InnerWindow.incrementReceived(state & 0x7FFFFFFF000000L) | (InnerWindow.hasWorkInProgress(state) ? InnerWindow.incrementWork(state & 0xFFFFFFL) : 0L)));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "hvt", state, nextState);
            }
            return state;
        }

        static boolean hasValues(long state) {
            return (state & 0x400000000000000L) == 0x400000000000000L;
        }

        static long received(long state) {
            return (state & 0x7FFFFFFF000000L) >> 24;
        }

        static <T> long markHasRequest(InnerWindow<T> instance) {
            long cleanState;
            long nextState;
            long state;
            do {
                if (!InnerWindow.isCancelled(state = instance.state) && !InnerWindow.isFinalized(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = (cleanState = state & 0xFFFFFFFFFF000000L) | 0x100000000000000L | (InnerWindow.hasValues(state) ? InnerWindow.incrementWork(state & 0xFFFFFFL) : 0L)));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mhr", state, nextState);
            }
            return state;
        }

        static <T> long markTerminated(InnerWindow<T> instance) {
            long l;
            long cleanState;
            long nextState;
            long state;
            do {
                if (InnerWindow.isFinalized(state = instance.state) || InnerWindow.isTerminated(state)) {
                    return state;
                }
                cleanState = state & 0xFFFFFFFFFF000000L;
                if (InnerWindow.hasSubscriberSet(state)) {
                    if (InnerWindow.hasValues(state)) {
                        l = InnerWindow.incrementWork(state & 0xFFFFFFL);
                        continue;
                    }
                    l = Long.MIN_VALUE;
                    continue;
                }
                l = 0L;
            } while (!STATE.compareAndSet(instance, state, nextState = cleanState | 0x4000000000000000L | l));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mtd", state, nextState);
            }
            return state;
        }

        static boolean isTerminated(long state) {
            return (state & 0x4000000000000000L) == 0x4000000000000000L;
        }

        static long incrementWork(long currentWork) {
            return currentWork == 0xFFFFFFL ? 1L : currentWork + 1L;
        }

        static long incrementReceived(long currentWork) {
            return (currentWork >> 24) + 1L << 24;
        }

        static <T> long markSubscribedOnce(InnerWindow<T> instance) {
            long nextState;
            long state;
            do {
                if (!InnerWindow.hasSubscribedOnce(state = instance.state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state | 0x200000000000000L));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mso", state, nextState);
            }
            return state;
        }

        static boolean hasSubscribedOnce(long state) {
            return (state & 0x200000000000000L) == 0x200000000000000L;
        }

        static <T> long markSubscriberSet(InnerWindow<T> instance) {
            long nextState;
            long state;
            do {
                if (!InnerWindow.isFinalized(state = instance.state) && !InnerWindow.hasWorkInProgress(state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state | 0x100000000000000L | (InnerWindow.isTerminated(state) && !InnerWindow.hasValues(state) ? Long.MIN_VALUE : 0L)));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mss", state, nextState);
            }
            return state;
        }

        static boolean hasSubscriberSet(long state) {
            return (state & 0x100000000000000L) == 0x100000000000000L;
        }

        static <T> long markWorkDone(InnerWindow<T> instance, long expectedState, boolean hasValues) {
            long state = instance.state;
            if (expectedState != state) {
                return state;
            }
            long nextState = (state ^ (hasValues ? 0L : 0x400000000000000L)) & 0xFFFFFFFFFF000000L;
            if (STATE.compareAndSet(instance, state, nextState)) {
                if (instance.logger != null) {
                    instance.logger.log(instance.toString(), "mwd", state, nextState);
                }
                return nextState;
            }
            return instance.state;
        }

        static boolean hasWorkInProgress(long state) {
            return (state & 0xFFFFFFL) > 0L;
        }

        static boolean isFinalized(long state) {
            return (state & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        @Override
        public String toString() {
            return super.toString() + " " + this.index;
        }
    }

    static final class WindowTimeoutWithBackpressureSubscriber<T>
    implements InnerOperator<T, Flux<T>> {
        @Nullable
        final StateLogger logger;
        final CoreSubscriber<? super Flux<T>> actual;
        final long timespan;
        final TimeUnit unit;
        final Scheduler scheduler;
        final int maxSize;
        final Scheduler.Worker worker;
        final int limit;
        volatile long requested;
        static final AtomicLongFieldUpdater<WindowTimeoutWithBackpressureSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowTimeoutWithBackpressureSubscriber.class, "requested");
        volatile long state;
        static final AtomicLongFieldUpdater<WindowTimeoutWithBackpressureSubscriber> STATE = AtomicLongFieldUpdater.newUpdater(WindowTimeoutWithBackpressureSubscriber.class, "state");
        static final long CANCELLED_FLAG = Long.MIN_VALUE;
        static final long TERMINATED_FLAG = 0x4000000000000000L;
        static final long HAS_UNSENT_WINDOW = 0x2000000000000000L;
        static final long HAS_WORK_IN_PROGRESS = 0x1000000000000000L;
        static final long REQUEST_INDEX_MASK = 0xFFFFF0000000000L;
        static final long ACTIVE_WINDOW_INDEX_MASK = 0xFFFFF00000L;
        static final long NEXT_WINDOW_INDEX_MASK = 1048575L;
        static final int ACTIVE_WINDOW_INDEX_SHIFT = 20;
        static final int REQUEST_INDEX_SHIFT = 40;
        boolean done;
        Throwable error;
        Subscription s;
        InnerWindow<T> window;

        WindowTimeoutWithBackpressureSubscriber(CoreSubscriber<? super Flux<T>> actual, int maxSize, long timespan, TimeUnit unit, Scheduler scheduler, @Nullable StateLogger logger) {
            this.actual = actual;
            this.timespan = timespan;
            this.unit = unit;
            this.scheduler = scheduler;
            this.maxSize = maxSize;
            this.limit = Operators.unboundedOrLimit(maxSize);
            this.worker = scheduler.createWorker();
            this.logger = logger;
            STATE.lazySet(this, 1L);
        }

        @Override
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            InnerWindow<T> window;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            do {
                if (!WindowTimeoutWithBackpressureSubscriber.isCancelled(this.state)) continue;
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            } while (!(window = this.window).sendNext(t));
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.error = t;
            this.done = true;
            long previousState = WindowTimeoutWithBackpressureSubscriber.markTerminated(this);
            if (WindowTimeoutWithBackpressureSubscriber.isCancelled(previousState) || WindowTimeoutWithBackpressureSubscriber.isTerminated(previousState)) {
                return;
            }
            InnerWindow<T> window = this.window;
            if (window != null) {
                window.sendError(t);
                if (WindowTimeoutWithBackpressureSubscriber.hasUnsentWindow(previousState)) {
                    return;
                }
            }
            if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(previousState)) {
                return;
            }
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long previousState = WindowTimeoutWithBackpressureSubscriber.markTerminated(this);
            if (WindowTimeoutWithBackpressureSubscriber.isCancelled(previousState) || WindowTimeoutWithBackpressureSubscriber.isTerminated(previousState)) {
                return;
            }
            InnerWindow<T> window = this.window;
            if (window != null) {
                window.sendComplete();
                if (WindowTimeoutWithBackpressureSubscriber.hasUnsentWindow(previousState)) {
                    return;
                }
            }
            if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(previousState)) {
                return;
            }
            this.actual.onComplete();
        }

        public void request(long n) {
            long expectedState;
            long previousState;
            long previousRequested = Operators.addCap(REQUESTED, this, n);
            if (previousRequested == Long.MAX_VALUE) {
                return;
            }
            while (true) {
                if (WindowTimeoutWithBackpressureSubscriber.isCancelled(previousState = this.state)) {
                    return;
                }
                if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(previousState)) {
                    long nextState = previousState & 0xF00000FFFFFFFFFFL | WindowTimeoutWithBackpressureSubscriber.incrementRequestIndex(previousState);
                    if (!STATE.compareAndSet(this, previousState, nextState)) continue;
                    if (this.logger != null) {
                        this.logger.log(this.toString(), "mre", previousState, nextState);
                    }
                    return;
                }
                boolean hasUnsentWindow = WindowTimeoutWithBackpressureSubscriber.hasUnsentWindow(previousState);
                if (!hasUnsentWindow && (WindowTimeoutWithBackpressureSubscriber.isTerminated(previousState) || WindowTimeoutWithBackpressureSubscriber.activeWindowIndex(previousState) == WindowTimeoutWithBackpressureSubscriber.nextWindowIndex(previousState))) {
                    return;
                }
                expectedState = previousState & 0xDFFFFFFFFFFFFFFFL | 0x1000000000000000L;
                if (STATE.compareAndSet(this, previousState, expectedState)) break;
            }
            if (this.logger != null) {
                this.logger.log(this.toString(), "mre", previousState, expectedState);
            }
            this.drain(previousState, expectedState);
        }

        void tryCreateNextWindow(int windowIndex) {
            boolean hasWorkInProgress;
            long expectedState;
            long previousState;
            do {
                if (WindowTimeoutWithBackpressureSubscriber.isCancelled(previousState = this.state)) {
                    return;
                }
                if (WindowTimeoutWithBackpressureSubscriber.nextWindowIndex(previousState) != windowIndex) {
                    return;
                }
                hasWorkInProgress = WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(previousState);
                if (hasWorkInProgress || !WindowTimeoutWithBackpressureSubscriber.isTerminated(previousState) || WindowTimeoutWithBackpressureSubscriber.hasUnsentWindow(previousState)) continue;
                return;
            } while (!STATE.compareAndSet(this, previousState, expectedState = previousState & 0xFFFFFFFFFFF00000L | WindowTimeoutWithBackpressureSubscriber.incrementNextWindowIndex(previousState) | 0x1000000000000000L));
            if (hasWorkInProgress) {
                return;
            }
            this.drain(previousState, expectedState);
        }

        /*
         * Unable to fully structure code
         */
        void drain(long previousState, long expectedState) {
            do lbl-1000:
            // 7 sources

            {
                block55: {
                    block52: {
                        block53: {
                            block54: {
                                block51: {
                                    n = this.requested;
                                    if (this.logger != null) {
                                        this.logger.log(this.toString(), "dr" + n, previousState, expectedState);
                                    }
                                    hasUnsentWindow = WindowTimeoutWithBackpressureSubscriber.hasUnsentWindow(previousState);
                                    activeWindowIndex = WindowTimeoutWithBackpressureSubscriber.activeWindowIndex(expectedState);
                                    if (activeWindowIndex != (nextWindowIndex = WindowTimeoutWithBackpressureSubscriber.nextWindowIndex(expectedState)) || hasUnsentWindow) break block51;
                                    expectedState = WindowTimeoutWithBackpressureSubscriber.markWorkDone(this, expectedState);
                                    previousState = expectedState | 0x1000000000000000L;
                                    if (WindowTimeoutWithBackpressureSubscriber.isCancelled(expectedState)) {
                                        return;
                                    }
                                    if (WindowTimeoutWithBackpressureSubscriber.isTerminated(expectedState)) {
                                        e = this.error;
                                        if (e != null) {
                                            this.actual.onError(e);
                                        } else {
                                            this.actual.onComplete();
                                        }
                                        return;
                                    }
                                    if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState)) ** GOTO lbl-1000
                                    return;
                                }
                                if (n <= 0L) break block52;
                                if (!hasUnsentWindow) break block53;
                                currentUnsentWindow = this.window;
                                this.actual.onNext(currentUnsentWindow);
                                if (n != 0x7FFFFFFFFFFFFFFFL) {
                                    n = WindowTimeoutWithBackpressureSubscriber.REQUESTED.decrementAndGet(this);
                                    if (this.logger != null) {
                                        this.logger.log(this.toString(), "dec", n, n);
                                    }
                                }
                                previousInnerWindowState = currentUnsentWindow.sendSent();
                                if (WindowTimeoutWithBackpressureSubscriber.isTerminated(expectedState)) {
                                    e = this.error;
                                    if (e != null) {
                                        if (!WindowTimeoutWithBackpressureSubscriber.isTerminated(previousInnerWindowState)) {
                                            currentUnsentWindow.sendError(e);
                                        }
                                        this.actual.onError(e);
                                    } else {
                                        if (!WindowTimeoutWithBackpressureSubscriber.isTerminated(previousInnerWindowState)) {
                                            currentUnsentWindow.sendComplete();
                                        }
                                        this.actual.onComplete();
                                    }
                                    return;
                                }
                                if (nextWindowIndex <= activeWindowIndex || !InnerWindow.isTimeout(previousInnerWindowState) && !InnerWindow.isTerminated(previousInnerWindowState)) break block54;
                                shouldBeUnsent = n == 0L;
                                nextWindow = new InnerWindow<T>(this.maxSize, this, nextWindowIndex, shouldBeUnsent, this.logger);
                                this.window = nextWindow;
                                if (!shouldBeUnsent) {
                                    this.actual.onNext(nextWindow);
                                    if (n != 0x7FFFFFFFFFFFFFFFL) {
                                        WindowTimeoutWithBackpressureSubscriber.REQUESTED.decrementAndGet(this);
                                    }
                                }
                                expectedState = previousState & -1099510579201L & -2305843009213693953L ^ (expectedState == (previousState = WindowTimeoutWithBackpressureSubscriber.commitWork(this, expectedState, shouldBeUnsent)) ? 0x1000000000000000L : 0L) | WindowTimeoutWithBackpressureSubscriber.incrementActiveWindowIndex(previousState) | (shouldBeUnsent != false ? 0x2000000000000000L : 0L);
                                previousState = previousState & -2305843009213693953L | (shouldBeUnsent != false ? 0x2000000000000000L : 0L);
                                if (WindowTimeoutWithBackpressureSubscriber.isCancelled(expectedState)) {
                                    nextWindow.sendCancel();
                                    if (shouldBeUnsent) {
                                        nextWindow.cancel();
                                    }
                                    return;
                                }
                                if (WindowTimeoutWithBackpressureSubscriber.isTerminated(expectedState) && !shouldBeUnsent) {
                                    e = this.error;
                                    if (e != null) {
                                        nextWindow.sendError(e);
                                        this.actual.onError(e);
                                    } else {
                                        nextWindow.sendComplete();
                                        this.actual.onComplete();
                                    }
                                    return;
                                }
                                try {
                                    nextWindow.scheduleTimeout();
                                }
                                catch (Exception e) {
                                    if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState)) {
                                        this.actual.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                                    } else {
                                        this.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                                    }
                                    return;
                                }
                                nextRequest = InnerWindow.received(previousInnerWindowState);
                                if (nextRequest > 0L) {
                                    this.s.request(nextRequest);
                                }
                                if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState)) ** GOTO lbl-1000
                                return;
                            }
                            expectedState = previousState & -2305843009213693953L ^ (expectedState == (previousState = WindowTimeoutWithBackpressureSubscriber.commitSent(this, expectedState)) ? 0x1000000000000000L : 0L);
                            previousState &= -2305843009213693953L;
                            if (WindowTimeoutWithBackpressureSubscriber.isCancelled(expectedState)) {
                                return;
                            }
                            if (WindowTimeoutWithBackpressureSubscriber.isTerminated(expectedState)) {
                                e = this.error;
                                if (e != null) {
                                    this.actual.onError(e);
                                } else {
                                    this.actual.onComplete();
                                }
                                return;
                            }
                            if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState)) ** GOTO lbl-1000
                            return;
                        }
                        nextWindow = new InnerWindow<T>(this.maxSize, this, nextWindowIndex, false, this.logger);
                        previousWindow = this.window;
                        this.window = nextWindow;
                        this.actual.onNext(nextWindow);
                        if (n != 0x7FFFFFFFFFFFFFFFL) {
                            WindowTimeoutWithBackpressureSubscriber.REQUESTED.decrementAndGet(this);
                        }
                        if (WindowTimeoutWithBackpressureSubscriber.isCancelled(expectedState = previousState & -1099510579201L & -2305843009213693953L ^ (expectedState == (previousState = WindowTimeoutWithBackpressureSubscriber.commitWork(this, expectedState, false)) ? 0x1000000000000000L : 0L) | WindowTimeoutWithBackpressureSubscriber.incrementActiveWindowIndex(previousState))) {
                            previousWindow.sendCancel();
                            nextWindow.sendCancel();
                            return;
                        }
                        if (WindowTimeoutWithBackpressureSubscriber.isTerminated(expectedState)) {
                            e = this.error;
                            if (e != null) {
                                previousWindow.sendError(e);
                                nextWindow.sendError(e);
                                this.actual.onError(e);
                            } else {
                                previousWindow.sendComplete();
                                nextWindow.sendComplete();
                                this.actual.onComplete();
                            }
                            return;
                        }
                        try {
                            nextWindow.scheduleTimeout();
                        }
                        catch (Exception e) {
                            if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState)) {
                                this.actual.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                            } else {
                                this.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                            }
                            return;
                        }
                        if (previousWindow == null) {
                            nextRequest = this.maxSize;
                        } else {
                            previousActiveWindowState = previousWindow.sendComplete();
                            nextRequest = InnerWindow.received(previousActiveWindowState);
                        }
                        if (nextRequest > 0L) {
                            this.s.request(nextRequest);
                        }
                        if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState)) ** GOTO lbl-1000
                        return;
                    }
                    if (n != 0L || hasUnsentWindow) break block55;
                    nextWindow = new InnerWindow<T>(this.maxSize, this, nextWindowIndex, true, this.logger);
                    previousWindow = this.window;
                    this.window = nextWindow;
                    expectedState = previousState & -1099510579201L & -2305843009213693953L ^ (expectedState == (previousState = WindowTimeoutWithBackpressureSubscriber.commitWork(this, expectedState, true)) ? 0x1000000000000000L : 0L) | WindowTimeoutWithBackpressureSubscriber.incrementActiveWindowIndex(previousState) | 0x2000000000000000L;
                    previousState |= 0x2000000000000000L;
                    if (WindowTimeoutWithBackpressureSubscriber.isCancelled(expectedState)) {
                        previousWindow.sendCancel();
                        nextWindow.sendCancel();
                        nextWindow.cancel();
                        return;
                    }
                    try {
                        nextWindow.scheduleTimeout();
                    }
                    catch (Exception e) {
                        if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState)) {
                            this.actual.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                        } else {
                            this.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                        }
                        return;
                    }
                    previousActiveWindowState = previousWindow.sendComplete();
                    nextRequest = InnerWindow.received(previousActiveWindowState);
                    if (nextRequest > 0L) {
                        this.s.request(nextRequest);
                    }
                    if (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState)) ** GOTO lbl-1000
                    return;
                }
                expectedState = WindowTimeoutWithBackpressureSubscriber.markWorkDone(this, expectedState);
                previousState = expectedState | 0x1000000000000000L;
                if (WindowTimeoutWithBackpressureSubscriber.isCancelled(expectedState)) {
                    currentWindow = this.window;
                    previousWindowState = currentWindow.sendCancel();
                    if (!InnerWindow.isSent(previousWindowState)) {
                        currentWindow.cancel();
                    }
                    return;
                }
                if (!WindowTimeoutWithBackpressureSubscriber.isTerminated(expectedState) || WindowTimeoutWithBackpressureSubscriber.hasUnsentWindow(expectedState)) continue;
                e = this.error;
                if (e != null) {
                    this.actual.onError(e);
                } else {
                    this.actual.onComplete();
                }
                return;
            } while (WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(expectedState));
        }

        public void cancel() {
            long previousState = WindowTimeoutWithBackpressureSubscriber.markCancelled(this);
            if (!WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(previousState) && WindowTimeoutWithBackpressureSubscriber.isTerminated(previousState) && !WindowTimeoutWithBackpressureSubscriber.hasUnsentWindow(previousState) || WindowTimeoutWithBackpressureSubscriber.isCancelled(previousState)) {
                return;
            }
            this.s.cancel();
            InnerWindow<T> currentActiveWindow = this.window;
            if (currentActiveWindow != null && !InnerWindow.isSent(currentActiveWindow.sendCancel()) && !WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(previousState)) {
                currentActiveWindow.cancel();
            }
        }

        Disposable schedule(Runnable runnable, long createTime) {
            long delayedNanos = this.scheduler.now(TimeUnit.NANOSECONDS) - createTime;
            long timeSpanInNanos = this.unit.toNanos(this.timespan);
            long newTimeSpanInNanos = timeSpanInNanos - delayedNanos;
            if (newTimeSpanInNanos > 0L) {
                return this.worker.schedule(runnable, this.timespan, this.unit);
            }
            runnable.run();
            return InnerWindow.DISPOSED;
        }

        long now() {
            return this.scheduler.now(TimeUnit.NANOSECONDS);
        }

        static boolean hasUnsentWindow(long state) {
            return (state & 0x2000000000000000L) == 0x2000000000000000L;
        }

        static boolean isCancelled(long state) {
            return (state & Long.MIN_VALUE) == Long.MIN_VALUE;
        }

        static boolean isTerminated(long state) {
            return (state & 0x4000000000000000L) == 0x4000000000000000L;
        }

        static boolean hasWorkInProgress(long state) {
            return (state & 0x1000000000000000L) == 0x1000000000000000L;
        }

        static long incrementRequestIndex(long state) {
            return ((state & 0xFFFFF0000000000L) >> 40) + 1L << 40 & 0xFFFFF0000000000L;
        }

        static long incrementActiveWindowIndex(long state) {
            return ((state & 0xFFFFF00000L) >> 20) + 1L << 20 & 0xFFFFF00000L;
        }

        static int activeWindowIndex(long state) {
            return (int)((state & 0xFFFFF00000L) >> 20);
        }

        static long incrementNextWindowIndex(long state) {
            return (state & 0xFFFFFL) + 1L & 0xFFFFFL;
        }

        static int nextWindowIndex(long state) {
            return (int)(state & 0xFFFFFL);
        }

        static <T> long markTerminated(WindowTimeoutWithBackpressureSubscriber<T> instance) {
            long nextState;
            long previousState;
            do {
                if (!WindowTimeoutWithBackpressureSubscriber.isTerminated(previousState = instance.state) && !WindowTimeoutWithBackpressureSubscriber.isCancelled(previousState)) continue;
                return previousState;
            } while (!STATE.compareAndSet(instance, previousState, nextState = previousState | 0x4000000000000000L));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mtd", previousState, nextState);
            }
            return previousState;
        }

        static <T> long markCancelled(WindowTimeoutWithBackpressureSubscriber<T> instance) {
            long nextState;
            long previousState;
            do {
                if ((WindowTimeoutWithBackpressureSubscriber.hasWorkInProgress(previousState = instance.state) || !WindowTimeoutWithBackpressureSubscriber.isTerminated(previousState) || WindowTimeoutWithBackpressureSubscriber.hasUnsentWindow(previousState)) && !WindowTimeoutWithBackpressureSubscriber.isCancelled(previousState)) continue;
                return previousState;
            } while (!STATE.compareAndSet(instance, previousState, nextState = previousState | Long.MIN_VALUE));
            return previousState;
        }

        static <T> long markWorkDone(WindowTimeoutWithBackpressureSubscriber<T> instance, long expectedState) {
            long nextState;
            long currentState;
            do {
                if (expectedState == (currentState = instance.state)) continue;
                if (instance.logger != null) {
                    instance.logger.log(instance.toString(), "fwd", currentState, currentState);
                }
                return currentState;
            } while (!STATE.compareAndSet(instance, currentState, nextState = currentState ^ 0x1000000000000000L));
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "mwd", currentState, nextState);
            }
            return nextState;
        }

        static <T> long commitSent(WindowTimeoutWithBackpressureSubscriber<T> instance, long expectedState) {
            long clearState;
            long nextState;
            long currentState;
            while (!STATE.compareAndSet(instance, currentState = instance.state, nextState = (clearState = currentState & 0xDFFFFFFFFFFFFFFFL) ^ (expectedState == currentState ? 0x1000000000000000L : 0L))) {
            }
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "cts", currentState, nextState);
            }
            return currentState;
        }

        static <T> long commitWork(WindowTimeoutWithBackpressureSubscriber<T> instance, long expectedState, boolean setUnsentFlag) {
            long clearState;
            long nextState;
            long currentState;
            while (!STATE.compareAndSet(instance, currentState = instance.state, nextState = (clearState = currentState & 0xFFFFFF00000FFFFFL & 0xDFFFFFFFFFFFFFFFL) ^ (expectedState == currentState ? 0x1000000000000000L : 0L) | WindowTimeoutWithBackpressureSubscriber.incrementActiveWindowIndex(currentState) | (setUnsentFlag ? 0x2000000000000000L : 0L))) {
            }
            if (instance.logger != null) {
                instance.logger.log(instance.toString(), "ctw", currentState, nextState);
            }
            return currentState;
        }
    }
}


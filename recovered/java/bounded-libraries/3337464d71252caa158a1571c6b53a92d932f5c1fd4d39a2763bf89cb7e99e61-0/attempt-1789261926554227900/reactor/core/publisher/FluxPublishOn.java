/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class FluxPublishOn<T>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final Scheduler scheduler;
    final boolean delayError;
    final Supplier<? extends Queue<T>> queueSupplier;
    final int prefetch;
    final int lowTide;

    FluxPublishOn(Flux<? extends T> source, Scheduler scheduler, boolean delayError, int prefetch, int lowTide, Supplier<? extends Queue<T>> queueSupplier) {
        super(source);
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
        this.delayError = delayError;
        this.prefetch = prefetch;
        this.lowTide = lowTide;
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.scheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        Scheduler.Worker worker = Objects.requireNonNull(this.scheduler.createWorker(), "The scheduler returned a null worker");
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
            this.source.subscribe(new PublishOnConditionalSubscriber(cs, this.scheduler, worker, this.delayError, this.prefetch, this.lowTide, this.queueSupplier));
            return null;
        }
        return new PublishOnSubscriber<T>(actual, this.scheduler, worker, this.delayError, this.prefetch, this.lowTide, this.queueSupplier);
    }

    static final class PublishOnConditionalSubscriber<T>
    implements Fuseable.QueueSubscription<T>,
    Runnable,
    InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Scheduler.Worker worker;
        final Scheduler scheduler;
        final boolean delayError;
        final int prefetch;
        final int limit;
        final Supplier<? extends Queue<T>> queueSupplier;
        Subscription s;
        Queue<T> queue;
        volatile boolean cancelled;
        volatile boolean done;
        Throwable error;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<PublishOnConditionalSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(PublishOnConditionalSubscriber.class, "wip");
        volatile int discardGuard;
        static final AtomicIntegerFieldUpdater<PublishOnConditionalSubscriber> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(PublishOnConditionalSubscriber.class, "discardGuard");
        volatile long requested;
        static final AtomicLongFieldUpdater<PublishOnConditionalSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(PublishOnConditionalSubscriber.class, "requested");
        int sourceMode;
        long produced;
        long consumed;
        boolean outputFused;

        PublishOnConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Scheduler scheduler, Scheduler.Worker worker, boolean delayError, int prefetch, int lowTide, Supplier<? extends Queue<T>> queueSupplier) {
            this.actual = actual;
            this.worker = worker;
            this.scheduler = scheduler;
            this.delayError = delayError;
            this.prefetch = prefetch;
            this.queueSupplier = queueSupplier;
            this.limit = Operators.unboundedOrLimit(prefetch, lowTide);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                if (s instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription f = (Fuseable.QueueSubscription)s;
                    int m = f.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = 1;
                        this.queue = f;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        return;
                    }
                    if (m == 2) {
                        this.sourceMode = 2;
                        this.queue = f;
                        this.actual.onSubscribe(this);
                        s.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.actual.onSubscribe(this);
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.trySchedule(this, null, null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (this.cancelled) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (!this.queue.offer(t)) {
                Operators.onDiscard(t, this.actual.currentContext());
                this.error = Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t, this.actual.currentContext());
                this.done = true;
            }
            this.trySchedule(this, null, t);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.error = t;
            this.done = true;
            this.trySchedule(null, t, null);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.trySchedule(null, null, null);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.trySchedule(this, null, null);
            }
        }

        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.s.cancel();
            this.worker.dispose();
            if (WIP.getAndIncrement(this) == 0) {
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else if (!this.outputFused) {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
            }
        }

        void trySchedule(@Nullable Subscription subscription, @Nullable Throwable suppressed, @Nullable Object dataSignal) {
            if (WIP.getAndIncrement(this) != 0) {
                if (this.cancelled) {
                    if (this.sourceMode == 2) {
                        this.queue.clear();
                    } else {
                        Operators.onDiscard(dataSignal, this.actual.currentContext());
                    }
                }
                return;
            }
            try {
                this.worker.schedule(this);
            }
            catch (RejectedExecutionException ree) {
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else if (this.outputFused) {
                    this.clear();
                } else {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
                this.actual.onError(Operators.onRejectedExecution(ree, subscription, suppressed, dataSignal, this.actual.currentContext()));
            }
        }

        void runSync() {
            int missed = 1;
            Fuseable.ConditionalSubscriber<T> a = this.actual;
            Queue<T> q = this.queue;
            long e = this.produced;
            while (true) {
                long r = this.requested;
                while (e != r) {
                    T v;
                    try {
                        v = q.poll();
                    }
                    catch (Throwable ex) {
                        this.doError(a, Operators.onOperatorError(this.s, ex, this.actual.currentContext()));
                        return;
                    }
                    if (this.cancelled) {
                        Operators.onDiscard(v, this.actual.currentContext());
                        Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), null);
                        return;
                    }
                    if (v == null) {
                        this.doComplete(a);
                        return;
                    }
                    if (!a.tryOnNext(v)) continue;
                    ++e;
                }
                if (this.cancelled) {
                    Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), null);
                    return;
                }
                if (q.isEmpty()) {
                    this.doComplete(a);
                    return;
                }
                int w = this.wip;
                if (missed == w) {
                    this.produced = e;
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }

        void runAsync() {
            int missed = 1;
            Fuseable.ConditionalSubscriber<T> a = this.actual;
            Queue<T> q = this.queue;
            long emitted = this.produced;
            long polled = this.consumed;
            while (true) {
                long r = this.requested;
                while (emitted != r) {
                    boolean empty;
                    T v;
                    boolean d = this.done;
                    try {
                        v = q.poll();
                    }
                    catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        this.s.cancel();
                        q.clear();
                        this.doError(a, Operators.onOperatorError(ex, this.actual.currentContext()));
                        return;
                    }
                    boolean bl = empty = v == null;
                    if (this.checkTerminated(d, empty, a, v)) {
                        return;
                    }
                    if (empty) break;
                    if (a.tryOnNext(v)) {
                        ++emitted;
                    }
                    if (++polled != (long)this.limit) continue;
                    this.s.request(polled);
                    polled = 0L;
                }
                if (emitted == r && this.checkTerminated(this.done, q.isEmpty(), a, null)) {
                    return;
                }
                int w = this.wip;
                if (missed == w) {
                    this.produced = emitted;
                    this.consumed = polled;
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }

        void runBackfused() {
            int missed = 1;
            do {
                if (this.cancelled) {
                    this.clear();
                    return;
                }
                boolean d = this.done;
                this.actual.onNext(null);
                if (!d) continue;
                Throwable e = this.error;
                if (e != null) {
                    this.doError(this.actual, e);
                } else {
                    this.doComplete(this.actual);
                }
                return;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        @Override
        public void run() {
            if (this.outputFused) {
                this.runBackfused();
            } else if (this.sourceMode == 1) {
                this.runSync();
            } else {
                this.runAsync();
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.delayError;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.worker;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        void doComplete(Subscriber<?> a) {
            a.onComplete();
            this.worker.dispose();
        }

        void doError(Subscriber<?> a, Throwable e) {
            try {
                a.onError(e);
            }
            finally {
                this.worker.dispose();
            }
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<?> a, @Nullable T v) {
            if (this.cancelled) {
                Operators.onDiscard(v, this.actual.currentContext());
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
                return true;
            }
            if (d) {
                if (this.delayError) {
                    if (empty) {
                        Throwable e = this.error;
                        if (e != null) {
                            this.doError(a, e);
                        } else {
                            this.doComplete(a);
                        }
                        return true;
                    }
                } else {
                    Throwable e = this.error;
                    if (e != null) {
                        Operators.onDiscard(v, this.actual.currentContext());
                        if (this.sourceMode == 2) {
                            this.queue.clear();
                        } else {
                            Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                        }
                        this.doError(a, e);
                        return true;
                    }
                    if (empty) {
                        this.doComplete(a);
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void clear() {
            if (DISCARD_GUARD.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            while (true) {
                Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                int dg = this.discardGuard;
                if (missed == dg) {
                    if ((missed = DISCARD_GUARD.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = dg;
            }
        }

        @Override
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override
        @Nullable
        public T poll() {
            T v = this.queue.poll();
            if (v != null && this.sourceMode != 1) {
                long p = this.consumed + 1L;
                if (p == (long)this.limit) {
                    this.consumed = 0L;
                    this.s.request(p);
                } else {
                    this.consumed = p;
                }
            }
            return v;
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) != 0) {
                this.outputFused = true;
                return 2;
            }
            return 0;
        }

        @Override
        public int size() {
            return this.queue.size();
        }
    }

    static final class PublishOnSubscriber<T>
    implements Fuseable.QueueSubscription<T>,
    Runnable,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Scheduler scheduler;
        final Scheduler.Worker worker;
        final boolean delayError;
        final int prefetch;
        final int limit;
        final Supplier<? extends Queue<T>> queueSupplier;
        Subscription s;
        Queue<T> queue;
        volatile boolean cancelled;
        volatile boolean done;
        Throwable error;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<PublishOnSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(PublishOnSubscriber.class, "wip");
        volatile int discardGuard;
        static final AtomicIntegerFieldUpdater<PublishOnSubscriber> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(PublishOnSubscriber.class, "discardGuard");
        volatile long requested;
        static final AtomicLongFieldUpdater<PublishOnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(PublishOnSubscriber.class, "requested");
        int sourceMode;
        long produced;
        boolean outputFused;

        PublishOnSubscriber(CoreSubscriber<? super T> actual, Scheduler scheduler, Scheduler.Worker worker, boolean delayError, int prefetch, int lowTide, Supplier<? extends Queue<T>> queueSupplier) {
            this.actual = actual;
            this.worker = worker;
            this.scheduler = scheduler;
            this.delayError = delayError;
            this.prefetch = prefetch;
            this.queueSupplier = queueSupplier;
            this.limit = Operators.unboundedOrLimit(prefetch, lowTide);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                if (s instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription f = (Fuseable.QueueSubscription)s;
                    int m = f.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = 1;
                        this.queue = f;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        return;
                    }
                    if (m == 2) {
                        this.sourceMode = 2;
                        this.queue = f;
                        this.actual.onSubscribe(this);
                        s.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.actual.onSubscribe(this);
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.trySchedule(this, null, null);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (this.cancelled) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (!this.queue.offer(t)) {
                Operators.onDiscard(t, this.actual.currentContext());
                this.error = Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t, this.actual.currentContext());
                this.done = true;
            }
            this.trySchedule(this, null, t);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.error = t;
            this.done = true;
            this.trySchedule(null, t, null);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.trySchedule(null, null, null);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.trySchedule(this, null, null);
            }
        }

        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.s.cancel();
            this.worker.dispose();
            if (WIP.getAndIncrement(this) == 0) {
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else if (!this.outputFused) {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
            }
        }

        void trySchedule(@Nullable Subscription subscription, @Nullable Throwable suppressed, @Nullable Object dataSignal) {
            if (WIP.getAndIncrement(this) != 0) {
                if (this.cancelled) {
                    if (this.sourceMode == 2) {
                        this.queue.clear();
                    } else {
                        Operators.onDiscard(dataSignal, this.actual.currentContext());
                    }
                }
                return;
            }
            try {
                this.worker.schedule(this);
            }
            catch (RejectedExecutionException ree) {
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else if (this.outputFused) {
                    this.clear();
                } else {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
                this.actual.onError(Operators.onRejectedExecution(ree, subscription, suppressed, dataSignal, this.actual.currentContext()));
            }
        }

        void runSync() {
            int missed = 1;
            CoreSubscriber<? super T> a = this.actual;
            Queue<T> q = this.queue;
            long e = this.produced;
            while (true) {
                long r = this.requested;
                while (e != r) {
                    T v;
                    try {
                        v = q.poll();
                    }
                    catch (Throwable ex) {
                        this.doError(a, Operators.onOperatorError(this.s, ex, this.actual.currentContext()));
                        return;
                    }
                    if (this.cancelled) {
                        Operators.onDiscard(v, this.actual.currentContext());
                        Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), null);
                        return;
                    }
                    if (v == null) {
                        this.doComplete(a);
                        return;
                    }
                    a.onNext(v);
                    ++e;
                }
                if (this.cancelled) {
                    Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), null);
                    return;
                }
                if (q.isEmpty()) {
                    this.doComplete(a);
                    return;
                }
                int w = this.wip;
                if (missed == w) {
                    this.produced = e;
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }

        void runAsync() {
            int missed = 1;
            CoreSubscriber<? super T> a = this.actual;
            Queue<T> q = this.queue;
            long e = this.produced;
            while (true) {
                long r = this.requested;
                while (e != r) {
                    boolean empty;
                    T v;
                    boolean d = this.done;
                    try {
                        v = q.poll();
                    }
                    catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        this.s.cancel();
                        if (this.sourceMode == 2) {
                            this.queue.clear();
                        } else {
                            Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                        }
                        this.doError(a, Operators.onOperatorError(ex, this.actual.currentContext()));
                        return;
                    }
                    boolean bl = empty = v == null;
                    if (this.checkTerminated(d, empty, a, v)) {
                        return;
                    }
                    if (empty) break;
                    a.onNext(v);
                    if (++e != (long)this.limit) continue;
                    if (r != Long.MAX_VALUE) {
                        r = REQUESTED.addAndGet(this, -e);
                    }
                    this.s.request(e);
                    e = 0L;
                }
                if (e == r && this.checkTerminated(this.done, q.isEmpty(), a, null)) {
                    return;
                }
                int w = this.wip;
                if (missed == w) {
                    this.produced = e;
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }

        void runBackfused() {
            int missed = 1;
            do {
                if (this.cancelled) {
                    this.clear();
                    return;
                }
                boolean d = this.done;
                this.actual.onNext(null);
                if (!d) continue;
                Throwable e = this.error;
                if (e != null) {
                    this.doError(this.actual, e);
                } else {
                    this.doComplete(this.actual);
                }
                return;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        void doComplete(Subscriber<?> a) {
            a.onComplete();
            this.worker.dispose();
        }

        void doError(Subscriber<?> a, Throwable e) {
            try {
                a.onError(e);
            }
            finally {
                this.worker.dispose();
            }
        }

        @Override
        public void run() {
            if (this.outputFused) {
                this.runBackfused();
            } else if (this.sourceMode == 1) {
                this.runSync();
            } else {
                this.runAsync();
            }
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<?> a, @Nullable T v) {
            if (this.cancelled) {
                Operators.onDiscard(v, this.actual.currentContext());
                if (this.sourceMode == 2) {
                    this.queue.clear();
                } else {
                    Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                }
                return true;
            }
            if (d) {
                if (this.delayError) {
                    if (empty) {
                        Throwable e = this.error;
                        if (e != null) {
                            this.doError(a, e);
                        } else {
                            this.doComplete(a);
                        }
                        return true;
                    }
                } else {
                    Throwable e = this.error;
                    if (e != null) {
                        Operators.onDiscard(v, this.actual.currentContext());
                        if (this.sourceMode == 2) {
                            this.queue.clear();
                        } else {
                            Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                        }
                        this.doError(a, e);
                        return true;
                    }
                    if (empty) {
                        this.doComplete(a);
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.delayError;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
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
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void clear() {
            if (DISCARD_GUARD.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            while (true) {
                Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                int dg = this.discardGuard;
                if (missed == dg) {
                    if ((missed = DISCARD_GUARD.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = dg;
            }
        }

        @Override
        public boolean isEmpty() {
            return this.queue.isEmpty();
        }

        @Override
        @Nullable
        public T poll() {
            T v = this.queue.poll();
            if (v != null && this.sourceMode != 1) {
                long p = this.produced + 1L;
                if (p == (long)this.limit) {
                    this.produced = 0L;
                    this.s.request(p);
                } else {
                    this.produced = p;
                }
            }
            return v;
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) != 0) {
                this.outputFused = true;
                return 2;
            }
            return 0;
        }

        @Override
        public int size() {
            return this.queue.size();
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxWindow<T>
extends InternalFluxOperator<T, Flux<T>> {
    final int size;
    final int skip;
    final Supplier<? extends Queue<T>> processorQueueSupplier;
    final Supplier<? extends Queue<Sinks.Many<T>>> overflowQueueSupplier;

    FluxWindow(Flux<? extends T> source, int size, Supplier<? extends Queue<T>> processorQueueSupplier) {
        super(source);
        if (size <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + size);
        }
        this.size = size;
        this.skip = size;
        this.processorQueueSupplier = Objects.requireNonNull(processorQueueSupplier, "processorQueueSupplier");
        this.overflowQueueSupplier = null;
    }

    FluxWindow(Flux<? extends T> source, int size, int skip, Supplier<? extends Queue<T>> processorQueueSupplier, Supplier<? extends Queue<Sinks.Many<T>>> overflowQueueSupplier) {
        super(source);
        if (size <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + size);
        }
        if (skip <= 0) {
            throw new IllegalArgumentException("skip > 0 required but it was " + skip);
        }
        this.size = size;
        this.skip = skip;
        this.processorQueueSupplier = Objects.requireNonNull(processorQueueSupplier, "processorQueueSupplier");
        this.overflowQueueSupplier = Objects.requireNonNull(overflowQueueSupplier, "overflowQueueSupplier");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Flux<T>> actual) {
        if (this.skip == this.size) {
            return new WindowExactSubscriber(actual, this.size, this.processorQueueSupplier);
        }
        if (this.skip > this.size) {
            return new WindowSkipSubscriber(actual, this.size, this.skip, this.processorQueueSupplier);
        }
        return new WindowOverlapSubscriber<T>(actual, this.size, this.skip, this.processorQueueSupplier, this.overflowQueueSupplier.get());
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class WindowOverlapSubscriber<T>
    extends ArrayDeque<Sinks.Many<T>>
    implements Disposable,
    InnerOperator<T, Flux<T>> {
        final CoreSubscriber<? super Flux<T>> actual;
        final Supplier<? extends Queue<T>> processorQueueSupplier;
        final Queue<Sinks.Many<T>> queue;
        final int size;
        final int skip;
        volatile int cancelled;
        static final AtomicIntegerFieldUpdater<WindowOverlapSubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "cancelled");
        volatile int windowCount;
        static final AtomicIntegerFieldUpdater<WindowOverlapSubscriber> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "windowCount");
        volatile int firstRequest;
        static final AtomicIntegerFieldUpdater<WindowOverlapSubscriber> FIRST_REQUEST = AtomicIntegerFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "firstRequest");
        volatile long requested;
        static final AtomicLongFieldUpdater<WindowOverlapSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "requested");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<WindowOverlapSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(WindowOverlapSubscriber.class, "wip");
        int index;
        int produced;
        Subscription s;
        volatile boolean done;
        Throwable error;

        WindowOverlapSubscriber(CoreSubscriber<? super Flux<T>> actual, int size, int skip, Supplier<? extends Queue<T>> processorQueueSupplier, Queue<Sinks.Many<T>> overflowQueue) {
            this.actual = actual;
            this.size = size;
            this.skip = skip;
            this.processorQueueSupplier = processorQueueSupplier;
            WINDOW_COUNT.lazySet(this, 1);
            this.queue = overflowQueue;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            int i = this.index;
            if (i == 0 && this.cancelled == 0) {
                WINDOW_COUNT.getAndIncrement(this);
                Sinks.Many<T> w = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get(), this);
                this.offer(w);
                this.queue.offer(w);
                this.drain();
            }
            ++i;
            for (Sinks.Many w : this) {
                w.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
            }
            int p = this.produced + 1;
            if (p == this.size) {
                Sinks.Many w;
                this.produced = p - this.skip;
                w = (Sinks.Many)this.poll();
                if (w != null) {
                    w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                }
            } else {
                this.produced = p;
            }
            this.index = i == this.skip ? 0 : i;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            for (Sinks.Many w : this) {
                w.emitError(t, Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.clear();
            this.error = t;
            this.drain();
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            for (Sinks.Many w : this) {
                w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.clear();
            this.drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super Flux<T>> a = this.actual;
            Queue<Sinks.Many<T>> q = this.queue;
            int missed = 1;
            do {
                long e;
                long r = this.requested;
                for (e = 0L; e != r; ++e) {
                    boolean empty;
                    boolean d = this.done;
                    Sinks.Many<T> t = q.poll();
                    boolean bl = empty = t == null;
                    if (this.checkTerminated(d, empty, a, q)) {
                        return;
                    }
                    if (empty) break;
                    a.onNext(t.asFlux());
                }
                if (e == r && this.checkTerminated(this.done, q.isEmpty(), a, q)) {
                    return;
                }
                if (e == 0L || r == Long.MAX_VALUE) continue;
                REQUESTED.addAndGet(this, -e);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<?> a, Queue<?> q) {
            if (this.cancelled == 1) {
                q.clear();
                return true;
            }
            if (d) {
                Throwable e = this.error;
                if (e != null) {
                    q.clear();
                    a.onError(e);
                    return true;
                }
                if (empty) {
                    a.onComplete();
                    return true;
                }
            }
            return false;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                if (this.firstRequest == 0 && FIRST_REQUEST.compareAndSet(this, 0, 1)) {
                    long u = Operators.multiplyCap(this.skip, n - 1L);
                    long v = Operators.addCap(this.size, u);
                    this.s.request(v);
                } else {
                    long u = Operators.multiplyCap(this.skip, n);
                    this.s.request(u);
                }
                this.drain();
            }
        }

        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                this.dispose();
            }
        }

        @Override
        public void dispose() {
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.s.cancel();
            }
        }

        @Override
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled == 1 || this.done;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled == 1;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.size;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.LARGE_BUFFERED) {
                return (long)this.queue.size() + (long)this.size();
            }
            if (key == Scannable.Attr.BUFFERED) {
                long realBuffered = (long)this.queue.size() + (long)this.size();
                if (realBuffered < Integer.MAX_VALUE) {
                    return (int)realBuffered;
                }
                return Integer.MIN_VALUE;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.toArray()).map(Scannable::from);
        }
    }

    static final class WindowSkipSubscriber<T>
    implements Disposable,
    InnerOperator<T, Flux<T>> {
        final CoreSubscriber<? super Flux<T>> actual;
        final Context ctx;
        final Supplier<? extends Queue<T>> processorQueueSupplier;
        final int size;
        final int skip;
        volatile int cancelled;
        static final AtomicIntegerFieldUpdater<WindowSkipSubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowSkipSubscriber.class, "cancelled");
        volatile int windowCount;
        static final AtomicIntegerFieldUpdater<WindowSkipSubscriber> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowSkipSubscriber.class, "windowCount");
        volatile int firstRequest;
        static final AtomicIntegerFieldUpdater<WindowSkipSubscriber> FIRST_REQUEST = AtomicIntegerFieldUpdater.newUpdater(WindowSkipSubscriber.class, "firstRequest");
        int index;
        Subscription s;
        Sinks.Many<T> window;
        boolean done;

        WindowSkipSubscriber(CoreSubscriber<? super Flux<T>> actual, int size, int skip, Supplier<? extends Queue<T>> processorQueueSupplier) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.size = size;
            this.skip = skip;
            this.processorQueueSupplier = processorQueueSupplier;
            WINDOW_COUNT.lazySet(this, 1);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            int i = this.index;
            Sinks.Many<T> w = this.window;
            if (i == 0) {
                WINDOW_COUNT.getAndIncrement(this);
                w = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get(), this);
                this.window = w;
                this.actual.onNext(w.asFlux());
            }
            ++i;
            if (w != null) {
                w.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
            } else {
                Operators.onDiscard(t, this.ctx);
            }
            if (i == this.size) {
                this.window = null;
                if (w != null) {
                    w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                }
            }
            this.index = i == this.skip ? 0 : i;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.ctx);
                return;
            }
            this.done = true;
            Sinks.Many<T> w = this.window;
            if (w != null) {
                this.window = null;
                w.emitError(t, Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Sinks.Many<T> w = this.window;
            if (w != null) {
                this.window = null;
                w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.actual.onComplete();
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                if (this.firstRequest == 0 && FIRST_REQUEST.compareAndSet(this, 0, 1)) {
                    long u = Operators.multiplyCap(this.size, n);
                    long v = Operators.multiplyCap(this.skip - this.size, n - 1L);
                    long w = Operators.addCap(u, v);
                    this.s.request(w);
                } else {
                    long u = Operators.multiplyCap(this.skip, n);
                    this.s.request(u);
                }
            }
        }

        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                this.dispose();
            }
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled == 1 || this.done;
        }

        @Override
        public void dispose() {
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.s.cancel();
            }
        }

        @Override
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled == 1;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.size;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.window));
        }
    }

    static final class WindowExactSubscriber<T>
    implements Disposable,
    InnerOperator<T, Flux<T>> {
        final CoreSubscriber<? super Flux<T>> actual;
        final Supplier<? extends Queue<T>> processorQueueSupplier;
        final int size;
        volatile int cancelled;
        static final AtomicIntegerFieldUpdater<WindowExactSubscriber> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(WindowExactSubscriber.class, "cancelled");
        volatile int windowCount;
        static final AtomicIntegerFieldUpdater<WindowExactSubscriber> WINDOW_COUNT = AtomicIntegerFieldUpdater.newUpdater(WindowExactSubscriber.class, "windowCount");
        int index;
        Subscription s;
        Sinks.Many<T> window;
        boolean done;

        WindowExactSubscriber(CoreSubscriber<? super Flux<T>> actual, int size, Supplier<? extends Queue<T>> processorQueueSupplier) {
            this.actual = actual;
            this.size = size;
            this.processorQueueSupplier = processorQueueSupplier;
            WINDOW_COUNT.lazySet(this, 1);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            int i = this.index;
            Sinks.Many<T> w = this.window;
            if (this.cancelled == 0 && i == 0) {
                WINDOW_COUNT.getAndIncrement(this);
                w = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get(), this);
                this.window = w;
                this.actual.onNext(w.asFlux());
            }
            w.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
            if (++i == this.size) {
                this.index = 0;
                this.window = null;
                w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
            } else {
                this.index = i;
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            Sinks.Many<T> w = this.window;
            if (w != null) {
                this.window = null;
                w.emitError(t, Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Sinks.Many<T> w = this.window;
            if (w != null) {
                this.window = null;
                w.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.actual.onComplete();
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                long u = Operators.multiplyCap(this.size, n);
                this.s.request(u);
            }
        }

        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                this.dispose();
            }
        }

        @Override
        public void dispose() {
            if (WINDOW_COUNT.decrementAndGet(this) == 0) {
                this.s.cancel();
            }
        }

        @Override
        public CoreSubscriber<? super Flux<T>> actual() {
            return this.actual;
        }

        @Override
        public boolean isDisposed() {
            return this.cancelled == 1 || this.done;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled == 1;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.size;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.window));
        }
    }
}


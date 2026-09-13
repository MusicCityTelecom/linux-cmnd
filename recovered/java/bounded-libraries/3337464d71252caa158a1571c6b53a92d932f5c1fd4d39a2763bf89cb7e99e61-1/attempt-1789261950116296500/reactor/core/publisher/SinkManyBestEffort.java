/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.DirectInnerContainer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.InternalManySink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class SinkManyBestEffort<T>
extends Flux<T>
implements InternalManySink<T>,
Scannable,
DirectInnerContainer<T> {
    static final DirectInner[] EMPTY = new DirectInner[0];
    static final DirectInner[] TERMINATED = new DirectInner[0];
    final boolean allOrNothing;
    Throwable error;
    volatile DirectInner<T>[] subscribers;
    static final AtomicReferenceFieldUpdater<SinkManyBestEffort, DirectInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(SinkManyBestEffort.class, DirectInner[].class, "subscribers");

    static final <T> SinkManyBestEffort<T> createBestEffort() {
        return new SinkManyBestEffort<T>(false);
    }

    static final <T> SinkManyBestEffort<T> createAllOrNothing() {
        return new SinkManyBestEffort<T>(true);
    }

    SinkManyBestEffort(boolean allOrNothing) {
        this.allOrNothing = allOrNothing;
        SUBSCRIBERS.lazySet(this, EMPTY);
    }

    @Override
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.of(this.subscribers);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED) {
            return this.subscribers == TERMINATED;
        }
        if (key == Scannable.Attr.ERROR) {
            return this.error;
        }
        return null;
    }

    @Override
    public Sinks.EmitResult tryEmitNext(T t) {
        Objects.requireNonNull(t, "tryEmitNext(null) is forbidden");
        DirectInner<T>[] subs = this.subscribers;
        if (subs == EMPTY) {
            return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
        }
        if (subs == TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        int expectedEmitted = subs.length;
        int cancelledCount = 0;
        if (this.allOrNothing) {
            long commonRequest = Long.MAX_VALUE;
            DirectInner<T>[] directInnerArray = subs;
            int n = directInnerArray.length;
            for (int i = 0; i < n; ++i) {
                DirectInner<T> sub = directInnerArray[i];
                long subRequest = sub.requested;
                if (sub.isCancelled()) {
                    ++cancelledCount;
                    continue;
                }
                if (subRequest >= commonRequest) continue;
                commonRequest = subRequest;
            }
            if (commonRequest == 0L) {
                return Sinks.EmitResult.FAIL_OVERFLOW;
            }
            if (cancelledCount == expectedEmitted) {
                return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
            }
        }
        int emittedCount = 0;
        cancelledCount = 0;
        for (DirectInner<T> sub : subs) {
            if (sub.isCancelled()) {
                ++cancelledCount;
                continue;
            }
            if (sub.tryEmitNext(t)) {
                ++emittedCount;
                continue;
            }
            if (!sub.isCancelled()) continue;
            ++cancelledCount;
        }
        if (cancelledCount == expectedEmitted) {
            return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
        }
        if (cancelledCount + emittedCount == expectedEmitted) {
            return Sinks.EmitResult.OK;
        }
        if (emittedCount > 0 && !this.allOrNothing) {
            return Sinks.EmitResult.OK;
        }
        return Sinks.EmitResult.FAIL_OVERFLOW;
    }

    @Override
    public Sinks.EmitResult tryEmitComplete() {
        DirectInner[] subs = SUBSCRIBERS.getAndSet(this, TERMINATED);
        if (subs == TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        for (DirectInner s : subs) {
            s.emitComplete();
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable error) {
        Objects.requireNonNull(error, "tryEmitError(null) is forbidden");
        DirectInner[] subs = SUBSCRIBERS.getAndSet(this, TERMINATED);
        if (subs == TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = error;
        for (DirectInner s : subs) {
            s.emitError(error);
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public int currentSubscriberCount() {
        return this.subscribers.length;
    }

    @Override
    public Flux<T> asFlux() {
        return this;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Objects.requireNonNull(actual, "subscribe(null) is forbidden");
        DirectInner<T> p = new DirectInner<T>(actual, this);
        actual.onSubscribe(p);
        if (p.isCancelled()) {
            return;
        }
        if (this.add(p)) {
            if (p.isCancelled()) {
                this.remove(p);
            }
        } else {
            Throwable e = this.error;
            if (e != null) {
                actual.onError(e);
            } else {
                actual.onComplete();
            }
        }
    }

    @Override
    public boolean add(DirectInner<T> s) {
        DirectInner[] b;
        DirectInner<T>[] a = this.subscribers;
        if (a == TERMINATED) {
            return false;
        }
        do {
            if ((a = this.subscribers) == TERMINATED) {
                return false;
            }
            int len = a.length;
            b = new DirectInner[len + 1];
            System.arraycopy(a, 0, b, 0, len);
            b[len] = s;
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        return true;
    }

    @Override
    public void remove(DirectInner<T> s) {
        DirectInner[] b;
        DirectInner<T>[] a = this.subscribers;
        if (a == TERMINATED || a == EMPTY) {
            return;
        }
        do {
            if ((a = this.subscribers) == TERMINATED || a == EMPTY) {
                return;
            }
            int len = a.length;
            int j = -1;
            for (int i = 0; i < len; ++i) {
                if (a[i] != s) continue;
                j = i;
                break;
            }
            if (j < 0) {
                return;
            }
            if (len == 1) {
                b = EMPTY;
                continue;
            }
            b = new DirectInner[len - 1];
            System.arraycopy(a, 0, b, 0, j);
            System.arraycopy(a, j + 1, b, j, len - j - 1);
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
    }

    static class DirectInner<T>
    extends AtomicBoolean
    implements InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        final DirectInnerContainer<T> parent;
        volatile long requested;
        static final AtomicLongFieldUpdater<DirectInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(DirectInner.class, "requested");

        DirectInner(CoreSubscriber<? super T> actual, DirectInnerContainer<T> parent) {
            this.actual = actual;
            this.parent = parent;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        public void cancel() {
            if (this.compareAndSet(false, true)) {
                this.parent.remove(this);
            }
        }

        boolean isCancelled() {
            return this.get();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isCancelled();
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        boolean tryEmitNext(T value) {
            if (this.requested != 0L) {
                if (this.isCancelled()) {
                    return false;
                }
                this.actual.onNext(value);
                if (this.requested != Long.MAX_VALUE) {
                    REQUESTED.decrementAndGet(this);
                }
                return true;
            }
            return false;
        }

        void directEmitNext(T value) {
            if (this.requested != 0L) {
                this.actual.onNext(value);
                if (this.requested != Long.MAX_VALUE) {
                    REQUESTED.decrementAndGet(this);
                }
                return;
            }
            this.parent.remove(this);
            this.actual.onError(Exceptions.failWithOverflow("Can't deliver value due to lack of requests"));
        }

        void emitError(Throwable e) {
            if (this.isCancelled()) {
                return;
            }
            this.actual.onError(e);
        }

        void emitComplete() {
            if (this.isCancelled()) {
                return;
            }
            this.actual.onComplete();
        }
    }
}


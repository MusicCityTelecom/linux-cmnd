/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalManySink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class UnicastManySinkNoBackpressure<T>
extends Flux<T>
implements InternalManySink<T>,
Subscription,
ContextHolder {
    volatile State state;
    private static final AtomicReferenceFieldUpdater<UnicastManySinkNoBackpressure, State> STATE = AtomicReferenceFieldUpdater.newUpdater(UnicastManySinkNoBackpressure.class, State.class, "state");
    private volatile CoreSubscriber<? super T> actual = null;
    volatile long requested;
    static final AtomicLongFieldUpdater<UnicastManySinkNoBackpressure> REQUESTED = AtomicLongFieldUpdater.newUpdater(UnicastManySinkNoBackpressure.class, "requested");

    public static <E> UnicastManySinkNoBackpressure<E> create() {
        return new UnicastManySinkNoBackpressure();
    }

    UnicastManySinkNoBackpressure() {
        STATE.lazySet(this, State.INITIAL);
    }

    @Override
    public int currentSubscriberCount() {
        return this.state == State.SUBSCRIBED ? 1 : 0;
    }

    @Override
    public Flux<T> asFlux() {
        return this;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Objects.requireNonNull(actual, "subscribe");
        if (!STATE.compareAndSet(this, State.INITIAL, State.SUBSCRIBED)) {
            Operators.reportThrowInSubscribe(actual, new IllegalStateException("Unicast Sinks.Many allows only a single Subscriber"));
            return;
        }
        this.actual = actual;
        actual.onSubscribe(this);
    }

    public void request(long n) {
        if (Operators.validate(n)) {
            Operators.addCap(REQUESTED, this, n);
        }
    }

    public void cancel() {
        if (STATE.getAndSet(this, State.CANCELLED) == State.SUBSCRIBED) {
            this.actual = null;
        }
    }

    @Override
    public Context currentContext() {
        CoreSubscriber<T> actual = this.actual;
        return actual != null ? actual.currentContext() : Context.empty();
    }

    @Override
    public Sinks.EmitResult tryEmitNext(T t) {
        Objects.requireNonNull(t, "t");
        switch (this.state) {
            case INITIAL: {
                return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
            }
            case SUBSCRIBED: {
                if (this.requested == 0L) {
                    return Sinks.EmitResult.FAIL_OVERFLOW;
                }
                this.actual.onNext(t);
                Operators.produced(REQUESTED, this, 1L);
                return Sinks.EmitResult.OK;
            }
            case TERMINATED: {
                return Sinks.EmitResult.FAIL_TERMINATED;
            }
            case CANCELLED: {
                return Sinks.EmitResult.FAIL_CANCELLED;
            }
        }
        throw new IllegalStateException();
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable t) {
        Objects.requireNonNull(t, "t");
        block6: while (true) {
            State s = this.state;
            switch (s) {
                case INITIAL: {
                    return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
                }
                case SUBSCRIBED: {
                    if (!STATE.compareAndSet(this, s, State.TERMINATED)) continue block6;
                    this.actual.onError(t);
                    this.actual = null;
                    return Sinks.EmitResult.OK;
                }
                case TERMINATED: {
                    return Sinks.EmitResult.FAIL_TERMINATED;
                }
                case CANCELLED: {
                    return Sinks.EmitResult.FAIL_CANCELLED;
                }
            }
            break;
        }
        throw new IllegalStateException();
    }

    @Override
    public Sinks.EmitResult tryEmitComplete() {
        block6: while (true) {
            State s = this.state;
            switch (s) {
                case INITIAL: {
                    return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
                }
                case SUBSCRIBED: {
                    if (!STATE.compareAndSet(this, s, State.TERMINATED)) continue block6;
                    this.actual.onComplete();
                    this.actual = null;
                    return Sinks.EmitResult.OK;
                }
                case TERMINATED: {
                    return Sinks.EmitResult.FAIL_TERMINATED;
                }
                case CANCELLED: {
                    return Sinks.EmitResult.FAIL_CANCELLED;
                }
            }
            break;
        }
        throw new IllegalStateException();
    }

    @Override
    public Stream<? extends Scannable> inners() {
        CoreSubscriber<? super T> a = this.actual;
        return a == null ? Stream.empty() : Stream.of(Scannable.from(a));
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.ACTUAL) {
            return this.actual;
        }
        if (key == Scannable.Attr.TERMINATED) {
            return this.state == State.TERMINATED;
        }
        if (key == Scannable.Attr.CANCELLED) {
            return this.state == State.CANCELLED;
        }
        return null;
    }

    static enum State {
        INITIAL,
        SUBSCRIBED,
        TERMINATED,
        CANCELLED;

    }
}


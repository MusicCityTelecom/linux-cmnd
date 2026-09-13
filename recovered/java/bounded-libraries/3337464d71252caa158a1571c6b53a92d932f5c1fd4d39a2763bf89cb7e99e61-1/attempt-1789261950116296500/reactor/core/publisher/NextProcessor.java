/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

class NextProcessor<O>
extends MonoProcessor<O> {
    final boolean isRefCounted;
    volatile NextInner<O>[] subscribers;
    static final AtomicReferenceFieldUpdater<NextProcessor, NextInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(NextProcessor.class, NextInner[].class, "subscribers");
    static final NextInner[] EMPTY = new NextInner[0];
    static final NextInner[] TERMINATED = new NextInner[0];
    static final NextInner[] EMPTY_WITH_SOURCE = new NextInner[0];
    volatile Subscription subscription;
    static final AtomicReferenceFieldUpdater<NextProcessor, Subscription> UPSTREAM = AtomicReferenceFieldUpdater.newUpdater(NextProcessor.class, Subscription.class, "subscription");
    @Nullable
    CorePublisher<? extends O> source;
    @Nullable
    Throwable error;
    @Nullable
    O value;

    NextProcessor(@Nullable CorePublisher<? extends O> source) {
        this(source, false);
    }

    NextProcessor(@Nullable CorePublisher<? extends O> source, boolean isRefCounted) {
        this.source = source;
        this.isRefCounted = isRefCounted;
        SUBSCRIBERS.lazySet(this, source != null ? EMPTY_WITH_SOURCE : EMPTY);
    }

    @Override
    public O peek() {
        if (!this.isTerminated()) {
            return null;
        }
        if (this.value != null) {
            return this.value;
        }
        if (this.error != null) {
            RuntimeException re = Exceptions.propagate(this.error);
            re = Exceptions.addSuppressed(re, (Throwable)new Exception("Mono#peek terminated with an error"));
            throw re;
        }
        return null;
    }

    @Override
    @Nullable
    public O block(@Nullable Duration timeout) {
        try {
            if (this.isTerminated()) {
                return this.peek();
            }
            this.connect();
            long delay = null == timeout ? 0L : System.nanoTime() + timeout.toNanos();
            while (true) {
                if (this.isTerminated()) {
                    if (this.error != null) {
                        RuntimeException re = Exceptions.propagate(this.error);
                        re = Exceptions.addSuppressed(re, (Throwable)new Exception("Mono#block terminated with an error"));
                        throw re;
                    }
                    return this.value;
                }
                if (timeout != null && delay < System.nanoTime()) {
                    this.cancel();
                    throw new IllegalStateException("Timeout on Mono blocking read");
                }
                Thread.sleep(1L);
            }
        }
        catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Thread Interruption on Mono blocking read");
        }
    }

    public final void onComplete() {
        Sinks.EmitResult emitResult = this.tryEmitValue(null);
    }

    void emitEmpty(Sinks.EmitFailureHandler failureHandler) {
        Sinks.EmitResult emitResult;
        boolean shouldRetry;
        do {
            if (!(emitResult = this.tryEmitValue(null)).isSuccess()) continue;
            return;
        } while (shouldRetry = failureHandler.onEmitFailure(SignalType.ON_COMPLETE, emitResult));
        switch (emitResult) {
            case FAIL_ZERO_SUBSCRIBER: 
            case FAIL_OVERFLOW: 
            case FAIL_CANCELLED: 
            case FAIL_TERMINATED: {
                return;
            }
            case FAIL_NON_SERIALIZED: {
                throw new Sinks.EmissionException(emitResult, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
            }
        }
        throw new Sinks.EmissionException(emitResult, "Unknown emitResult value");
    }

    public final void onError(Throwable cause) {
        Sinks.EmitResult emitResult;
        boolean shouldRetry;
        do {
            if (!(emitResult = this.tryEmitError(cause)).isSuccess()) continue;
            return;
        } while (shouldRetry = Sinks.EmitFailureHandler.FAIL_FAST.onEmitFailure(SignalType.ON_ERROR, emitResult));
        switch (emitResult) {
            case FAIL_ZERO_SUBSCRIBER: 
            case FAIL_OVERFLOW: 
            case FAIL_CANCELLED: {
                return;
            }
            case FAIL_TERMINATED: {
                Operators.onErrorDropped(cause, this.currentContext());
                return;
            }
            case FAIL_NON_SERIALIZED: {
                throw new Sinks.EmissionException(emitResult, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
            }
        }
        throw new Sinks.EmissionException(emitResult, "Unknown emitResult value");
    }

    Sinks.EmitResult tryEmitError(Throwable cause) {
        Objects.requireNonNull(cause, "onError cannot be null");
        if (UPSTREAM.getAndSet(this, Operators.cancelledSubscription()) == Operators.cancelledSubscription()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = cause;
        this.value = null;
        this.source = null;
        for (NextInner as : SUBSCRIBERS.getAndSet(this, TERMINATED)) {
            as.onError(cause);
        }
        return Sinks.EmitResult.OK;
    }

    public final void onNext(@Nullable O value) {
        Sinks.EmitResult emitResult;
        boolean shouldRetry;
        if (value == null) {
            this.emitEmpty(Sinks.EmitFailureHandler.FAIL_FAST);
            return;
        }
        do {
            if (!(emitResult = this.tryEmitValue(value)).isSuccess()) continue;
            return;
        } while (shouldRetry = Sinks.EmitFailureHandler.FAIL_FAST.onEmitFailure(SignalType.ON_NEXT, emitResult));
        switch (emitResult) {
            case FAIL_ZERO_SUBSCRIBER: {
                return;
            }
            case FAIL_OVERFLOW: {
                Operators.onDiscard(value, this.currentContext());
                this.onError(Exceptions.failWithOverflow("Backpressure overflow during Sinks.Many#emitNext"));
                return;
            }
            case FAIL_CANCELLED: {
                Operators.onDiscard(value, this.currentContext());
                return;
            }
            case FAIL_TERMINATED: {
                Operators.onNextDropped(value, this.currentContext());
                return;
            }
            case FAIL_NON_SERIALIZED: {
                throw new Sinks.EmissionException(emitResult, "Spec. Rule 1.3 - onSubscribe, onNext, onError and onComplete signaled to a Subscriber MUST be signaled serially.");
            }
        }
        throw new Sinks.EmissionException(emitResult, "Unknown emitResult value");
    }

    Sinks.EmitResult tryEmitValue(@Nullable O value) {
        Subscription s = UPSTREAM.getAndSet(this, Operators.cancelledSubscription());
        if (s == Operators.cancelledSubscription()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.value = value;
        CorePublisher<? extends O> parent = this.source;
        this.source = null;
        NextInner[] array = SUBSCRIBERS.getAndSet(this, TERMINATED);
        if (value == null) {
            for (NextInner as : array) {
                as.onComplete();
            }
        } else {
            if (s != null && !(parent instanceof Mono)) {
                s.cancel();
            }
            for (NextInner as : array) {
                as.complete(value);
            }
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.subscription;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    @Override
    public long downstreamCount() {
        return this.subscribers.length;
    }

    @Override
    public void dispose() {
        NextInner[] a;
        Subscription s = UPSTREAM.getAndSet(this, Operators.cancelledSubscription());
        if (s == Operators.cancelledSubscription()) {
            return;
        }
        this.source = null;
        if (s != null) {
            s.cancel();
        }
        if ((a = SUBSCRIBERS.getAndSet(this, TERMINATED)) != TERMINATED) {
            CancellationException e = new CancellationException("Disposed");
            this.error = e;
            this.value = null;
            for (NextInner as : a) {
                as.onError(e);
            }
        }
    }

    @Override
    public void cancel() {
        if (this.isTerminated()) {
            return;
        }
        Subscription s = UPSTREAM.getAndSet(this, Operators.cancelledSubscription());
        if (s == Operators.cancelledSubscription()) {
            return;
        }
        this.source = null;
        if (s != null) {
            s.cancel();
        }
    }

    @Override
    public final void onSubscribe(Subscription subscription) {
        if (Operators.setOnce(UPSTREAM, this, subscription)) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    @Override
    public boolean isCancelled() {
        return this.subscription == Operators.cancelledSubscription() && !this.isTerminated();
    }

    @Override
    public boolean isTerminated() {
        return this.subscribers == TERMINATED;
    }

    @Override
    @Nullable
    public Throwable getError() {
        return this.error;
    }

    boolean add(NextInner<O> ps) {
        NextInner[] b;
        NextInner<O>[] a;
        do {
            if ((a = this.subscribers) == TERMINATED) {
                return false;
            }
            int n = a.length;
            b = new NextInner[n + 1];
            System.arraycopy(a, 0, b, 0, n);
            b[n] = ps;
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        CorePublisher<? extends O> parent = this.source;
        if (parent != null && a == EMPTY_WITH_SOURCE) {
            parent.subscribe(this);
        }
        return true;
    }

    void remove(NextInner<O> ps) {
        Subscription oldSubscription;
        boolean disconnect;
        NextInner[] b;
        NextInner<O>[] a;
        do {
            int n;
            if ((n = (a = this.subscribers).length) == 0) {
                return;
            }
            int j = -1;
            for (int i = 0; i < n; ++i) {
                if (a[i] != ps) continue;
                j = i;
                break;
            }
            if (j < 0) {
                return;
            }
            disconnect = false;
            if (n == 1) {
                if (this.isRefCounted && this.source != null) {
                    b = EMPTY_WITH_SOURCE;
                    disconnect = true;
                    continue;
                }
                b = EMPTY;
                continue;
            }
            b = new NextInner[n - 1];
            System.arraycopy(a, 0, b, 0, j);
            System.arraycopy(a, j + 1, b, j, n - j - 1);
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        if (disconnect && (oldSubscription = (Subscription)UPSTREAM.getAndSet(this, null)) != null) {
            oldSubscription.cancel();
        }
    }

    @Override
    public void subscribe(CoreSubscriber<? super O> actual) {
        NextInner<O> as = new NextInner<O>(actual, this);
        actual.onSubscribe(as);
        if (this.add(as)) {
            if (as.isCancelled()) {
                this.remove(as);
            }
        } else {
            Throwable ex = this.error;
            if (ex != null) {
                actual.onError(ex);
            } else {
                O v = this.value;
                if (v != null) {
                    as.complete(v);
                } else {
                    as.onComplete();
                }
            }
        }
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.of(this.subscribers);
    }

    void connect() {
        CorePublisher<? extends O> parent = this.source;
        if (parent != null && SUBSCRIBERS.compareAndSet(this, EMPTY_WITH_SOURCE, EMPTY)) {
            parent.subscribe(this);
        }
    }

    static final class NextInner<T>
    extends Operators.MonoSubscriber<T, T> {
        final NextProcessor<T> parent;

        NextInner(CoreSubscriber<? super T> actual, NextProcessor<T> parent) {
            super(actual);
            this.parent = parent;
        }

        @Override
        public void cancel() {
            if (STATE.getAndSet(this, 4) != 4) {
                this.parent.remove(this);
            }
        }

        @Override
        public void onComplete() {
            if (!this.isCancelled()) {
                this.actual.onComplete();
            }
        }

        @Override
        public void onError(Throwable t) {
            if (!this.isCancelled()) {
                this.actual.onError(t);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}


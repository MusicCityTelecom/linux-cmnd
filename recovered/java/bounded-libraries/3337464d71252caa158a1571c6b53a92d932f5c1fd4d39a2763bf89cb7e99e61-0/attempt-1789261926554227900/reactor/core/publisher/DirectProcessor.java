/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.DirectInnerContainer;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SinkManyBestEffort;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

@Deprecated
public final class DirectProcessor<T>
extends FluxProcessor<T, T>
implements DirectInnerContainer<T> {
    private volatile SinkManyBestEffort.DirectInner<T>[] subscribers = SinkManyBestEffort.EMPTY;
    private static final AtomicReferenceFieldUpdater<DirectProcessor, SinkManyBestEffort.DirectInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(DirectProcessor.class, SinkManyBestEffort.DirectInner[].class, "subscribers");
    Throwable error;

    @Deprecated
    public static <E> DirectProcessor<E> create() {
        return new DirectProcessor();
    }

    DirectProcessor() {
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    @Override
    public void onSubscribe(Subscription s) {
        Objects.requireNonNull(s, "s");
        if (this.subscribers != SinkManyBestEffort.TERMINATED) {
            s.request(Long.MAX_VALUE);
        } else {
            s.cancel();
        }
    }

    public void onComplete() {
        Sinks.EmitResult emitResult = this.tryEmitComplete();
    }

    private void emitComplete() {
        Sinks.EmitResult emitResult = this.tryEmitComplete();
    }

    private Sinks.EmitResult tryEmitComplete() {
        SinkManyBestEffort.DirectInner[] inners = SUBSCRIBERS.getAndSet(this, SinkManyBestEffort.TERMINATED);
        if (inners == SinkManyBestEffort.TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        for (SinkManyBestEffort.DirectInner s : inners) {
            s.emitComplete();
        }
        return Sinks.EmitResult.OK;
    }

    public void onError(Throwable throwable) {
        this.emitError(throwable);
    }

    private void emitError(Throwable error) {
        Sinks.EmitResult result = this.tryEmitError(error);
        if (result == Sinks.EmitResult.FAIL_TERMINATED) {
            Operators.onErrorDroppedMulticast(error, this.subscribers);
        }
    }

    private Sinks.EmitResult tryEmitError(Throwable t) {
        Objects.requireNonNull(t, "t");
        SinkManyBestEffort.DirectInner[] inners = SUBSCRIBERS.getAndSet(this, SinkManyBestEffort.TERMINATED);
        if (inners == SinkManyBestEffort.TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = t;
        for (SinkManyBestEffort.DirectInner s : inners) {
            s.emitError(t);
        }
        return Sinks.EmitResult.OK;
    }

    private void emitNext(T value) {
        switch (this.tryEmitNext(value)) {
            case FAIL_ZERO_SUBSCRIBER: {
                break;
            }
            case FAIL_OVERFLOW: {
                Operators.onDiscard(value, this.currentContext());
                this.emitError(Exceptions.failWithOverflow("Backpressure overflow during Sinks.Many#emitNext"));
                break;
            }
            case FAIL_CANCELLED: {
                Operators.onDiscard(value, this.currentContext());
                break;
            }
            case FAIL_TERMINATED: {
                Operators.onNextDroppedMulticast(value, this.subscribers);
                break;
            }
            case OK: {
                break;
            }
            default: {
                throw new IllegalStateException("unexpected return code");
            }
        }
    }

    public void onNext(T t) {
        this.emitNext(t);
    }

    private Sinks.EmitResult tryEmitNext(T t) {
        Objects.requireNonNull(t, "t");
        SinkManyBestEffort.DirectInner<T>[] inners = this.subscribers;
        if (inners == SinkManyBestEffort.TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (inners == SinkManyBestEffort.EMPTY) {
            return Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
        }
        for (SinkManyBestEffort.DirectInner<T> s : inners) {
            s.directEmitNext(t);
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    protected boolean isIdentityProcessor() {
        return true;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Objects.requireNonNull(actual, "subscribe");
        SinkManyBestEffort.DirectInner<T> p = new SinkManyBestEffort.DirectInner<T>(actual, this);
        actual.onSubscribe(p);
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
    public Stream<? extends Scannable> inners() {
        return Stream.of(this.subscribers);
    }

    @Override
    public boolean isTerminated() {
        return SinkManyBestEffort.TERMINATED == this.subscribers;
    }

    @Override
    public long downstreamCount() {
        return this.subscribers.length;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean add(SinkManyBestEffort.DirectInner<T> s) {
        SinkManyBestEffort.DirectInner<T>[] a = this.subscribers;
        if (a == SinkManyBestEffort.TERMINATED) {
            return false;
        }
        DirectProcessor directProcessor = this;
        synchronized (directProcessor) {
            a = this.subscribers;
            if (a == SinkManyBestEffort.TERMINATED) {
                return false;
            }
            int len = a.length;
            SinkManyBestEffort.DirectInner[] b = new SinkManyBestEffort.DirectInner[len + 1];
            System.arraycopy(a, 0, b, 0, len);
            b[len] = s;
            this.subscribers = b;
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void remove(SinkManyBestEffort.DirectInner<T> s) {
        SinkManyBestEffort.DirectInner<T>[] a = this.subscribers;
        if (a == SinkManyBestEffort.TERMINATED || a == SinkManyBestEffort.EMPTY) {
            return;
        }
        DirectProcessor directProcessor = this;
        synchronized (directProcessor) {
            a = this.subscribers;
            if (a == SinkManyBestEffort.TERMINATED || a == SinkManyBestEffort.EMPTY) {
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
                this.subscribers = SinkManyBestEffort.EMPTY;
                return;
            }
            SinkManyBestEffort.DirectInner[] b = new SinkManyBestEffort.DirectInner[len - 1];
            System.arraycopy(a, 0, b, 0, j);
            System.arraycopy(a, j + 1, b, j, len - j - 1);
            this.subscribers = b;
        }
    }

    @Override
    public boolean hasDownstreams() {
        SinkManyBestEffort.DirectInner<T>[] s = this.subscribers;
        return s != SinkManyBestEffort.EMPTY && s != SinkManyBestEffort.TERMINATED;
    }

    @Override
    @Nullable
    public Throwable getError() {
        if (this.subscribers == SinkManyBestEffort.TERMINATED) {
            return this.error;
        }
        return null;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalManySink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

@Deprecated
public final class UnicastProcessor<T>
extends FluxProcessor<T, T>
implements Fuseable.QueueSubscription<T>,
Fuseable,
InnerOperator<T, T>,
InternalManySink<T> {
    final Queue<T> queue;
    final Consumer<? super T> onOverflow;
    volatile Disposable onTerminate;
    static final AtomicReferenceFieldUpdater<UnicastProcessor, Disposable> ON_TERMINATE = AtomicReferenceFieldUpdater.newUpdater(UnicastProcessor.class, Disposable.class, "onTerminate");
    volatile boolean done;
    Throwable error;
    boolean hasDownstream;
    volatile CoreSubscriber<? super T> actual;
    volatile boolean cancelled;
    volatile int once;
    static final AtomicIntegerFieldUpdater<UnicastProcessor> ONCE = AtomicIntegerFieldUpdater.newUpdater(UnicastProcessor.class, "once");
    volatile int wip;
    static final AtomicIntegerFieldUpdater<UnicastProcessor> WIP = AtomicIntegerFieldUpdater.newUpdater(UnicastProcessor.class, "wip");
    volatile int discardGuard;
    static final AtomicIntegerFieldUpdater<UnicastProcessor> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(UnicastProcessor.class, "discardGuard");
    volatile long requested;
    static final AtomicLongFieldUpdater<UnicastProcessor> REQUESTED = AtomicLongFieldUpdater.newUpdater(UnicastProcessor.class, "requested");
    boolean outputFused;

    @Deprecated
    public static <E> UnicastProcessor<E> create() {
        return new UnicastProcessor(Queues.unbounded().get());
    }

    @Deprecated
    public static <E> UnicastProcessor<E> create(Queue<E> queue) {
        return new UnicastProcessor<E>(Hooks.wrapQueue(queue));
    }

    @Deprecated
    public static <E> UnicastProcessor<E> create(Queue<E> queue, Disposable endcallback) {
        return new UnicastProcessor<E>(Hooks.wrapQueue(queue), endcallback);
    }

    @Deprecated
    public static <E> UnicastProcessor<E> create(Queue<E> queue, Consumer<? super E> onOverflow, Disposable endcallback) {
        return new UnicastProcessor<E>(Hooks.wrapQueue(queue), onOverflow, endcallback);
    }

    public UnicastProcessor(Queue<T> queue) {
        this.queue = Objects.requireNonNull(queue, "queue");
        this.onTerminate = null;
        this.onOverflow = null;
    }

    public UnicastProcessor(Queue<T> queue, Disposable onTerminate) {
        this.queue = Objects.requireNonNull(queue, "queue");
        this.onTerminate = Objects.requireNonNull(onTerminate, "onTerminate");
        this.onOverflow = null;
    }

    @Deprecated
    public UnicastProcessor(Queue<T> queue, Consumer<? super T> onOverflow, Disposable onTerminate) {
        this.queue = Objects.requireNonNull(queue, "queue");
        this.onOverflow = Objects.requireNonNull(onOverflow, "onOverflow");
        this.onTerminate = Objects.requireNonNull(onTerminate, "onTerminate");
    }

    @Override
    public int getBufferSize() {
        return Queues.capacity(this.queue);
    }

    @Override
    public Stream<Scannable> inners() {
        return this.hasDownstream ? Stream.of(Scannable.from(this.actual)) : Stream.empty();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (Scannable.Attr.ACTUAL == key) {
            return this.actual();
        }
        if (Scannable.Attr.BUFFERED == key) {
            return this.queue.size();
        }
        if (Scannable.Attr.PREFETCH == key) {
            return Integer.MAX_VALUE;
        }
        if (Scannable.Attr.CANCELLED == key) {
            return this.cancelled;
        }
        return super.scanUnsafe(key);
    }

    public void onComplete() {
        Sinks.EmitResult emitResult = this.tryEmitComplete();
    }

    @Override
    public Sinks.EmitResult tryEmitComplete() {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        this.done = true;
        this.doTerminate();
        this.drain(null);
        return Sinks.EmitResult.OK;
    }

    public void onError(Throwable throwable) {
        this.emitError(throwable, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable t) {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        this.error = t;
        this.done = true;
        this.doTerminate();
        this.drain(null);
        return Sinks.EmitResult.OK;
    }

    public void onNext(T t) {
        this.emitNext(t, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public void emitNext(T value, Sinks.EmitFailureHandler failureHandler) {
        if (this.onOverflow == null) {
            InternalManySink.super.emitNext(value, failureHandler);
            return;
        }
        InternalManySink.super.emitNext(value, (signalType, emission) -> {
            boolean shouldRetry = failureHandler.onEmitFailure(SignalType.ON_NEXT, emission);
            if (!shouldRetry) {
                switch (emission) {
                    case FAIL_ZERO_SUBSCRIBER: 
                    case FAIL_OVERFLOW: {
                        try {
                            this.onOverflow.accept(value);
                            break;
                        }
                        catch (Throwable e) {
                            Exceptions.throwIfFatal(e);
                            this.emitError(e, Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                    }
                }
            }
            return shouldRetry;
        });
    }

    @Override
    public Sinks.EmitResult tryEmitNext(T t) {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        if (!this.queue.offer(t)) {
            return this.once > 0 ? Sinks.EmitResult.FAIL_OVERFLOW : Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
        }
        this.drain(t);
        return Sinks.EmitResult.OK;
    }

    @Override
    public int currentSubscriberCount() {
        return this.hasDownstream ? 1 : 0;
    }

    @Override
    public Flux<T> asFlux() {
        return this;
    }

    @Override
    protected boolean isIdentityProcessor() {
        return true;
    }

    void doTerminate() {
        Disposable r = this.onTerminate;
        if (r != null && ON_TERMINATE.compareAndSet(this, r, null)) {
            r.dispose();
        }
    }

    void drainRegular(CoreSubscriber<? super T> a) {
        int missed = 1;
        Queue<T> q = this.queue;
        do {
            long e;
            long r = this.requested;
            for (e = 0L; r != e; ++e) {
                boolean empty;
                boolean d = this.done;
                T t = q.poll();
                boolean bl = empty = t == null;
                if (this.checkTerminated(d, empty, a, q, t)) {
                    return;
                }
                if (empty) break;
                a.onNext(t);
            }
            if (r == e && this.checkTerminated(this.done, q.isEmpty(), a, q, null)) {
                return;
            }
            if (e == 0L || r == Long.MAX_VALUE) continue;
            REQUESTED.addAndGet(this, -e);
        } while ((missed = WIP.addAndGet(this, -missed)) != 0);
    }

    void drainFused(CoreSubscriber<? super T> a) {
        int missed = 1;
        do {
            if (this.cancelled) {
                this.clear();
                this.hasDownstream = false;
                return;
            }
            boolean d = this.done;
            a.onNext(null);
            if (!d) continue;
            this.hasDownstream = false;
            Throwable ex = this.error;
            if (ex != null) {
                a.onError(ex);
            } else {
                a.onComplete();
            }
            return;
        } while ((missed = WIP.addAndGet(this, -missed)) != 0);
    }

    void drain(@Nullable T dataSignalOfferedBeforeDrain) {
        if (WIP.getAndIncrement(this) != 0) {
            if (dataSignalOfferedBeforeDrain != null) {
                if (this.cancelled) {
                    Operators.onDiscard(dataSignalOfferedBeforeDrain, this.actual.currentContext());
                } else if (this.done) {
                    Operators.onNextDropped(dataSignalOfferedBeforeDrain, this.currentContext());
                }
            }
            return;
        }
        int missed = 1;
        do {
            CoreSubscriber<? super T> a;
            if ((a = this.actual) == null) continue;
            if (this.outputFused) {
                this.drainFused(a);
            } else {
                this.drainRegular(a);
            }
            return;
        } while ((missed = WIP.addAndGet(this, -missed)) != 0);
    }

    boolean checkTerminated(boolean d, boolean empty, CoreSubscriber<? super T> a, Queue<T> q, @Nullable T t) {
        if (this.cancelled) {
            Operators.onDiscard(t, a.currentContext());
            Operators.onDiscardQueueWithClear(q, a.currentContext(), null);
            this.hasDownstream = false;
            return true;
        }
        if (d && empty) {
            Throwable e = this.error;
            this.hasDownstream = false;
            if (e != null) {
                a.onError(e);
            } else {
                a.onComplete();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (this.done || this.cancelled) {
            s.cancel();
        } else {
            s.request(Long.MAX_VALUE);
        }
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Context currentContext() {
        CoreSubscriber<T> actual = this.actual;
        return actual != null ? actual.currentContext() : Context.empty();
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Objects.requireNonNull(actual, "subscribe");
        if (this.once == 0 && ONCE.compareAndSet(this, 0, 1)) {
            this.hasDownstream = true;
            actual.onSubscribe(this);
            this.actual = actual;
            if (this.cancelled) {
                this.hasDownstream = false;
            } else {
                this.drain(null);
            }
        } else {
            Operators.error(actual, new IllegalStateException("UnicastProcessor allows only a single Subscriber"));
        }
    }

    public void request(long n) {
        if (Operators.validate(n)) {
            Operators.addCap(REQUESTED, this, n);
            this.drain(null);
        }
    }

    public void cancel() {
        if (this.cancelled) {
            return;
        }
        this.cancelled = true;
        this.doTerminate();
        if (WIP.getAndIncrement(this) == 0) {
            if (!this.outputFused) {
                Operators.onDiscardQueueWithClear(this.queue, this.currentContext(), null);
            }
            this.hasDownstream = false;
        }
    }

    @Override
    @Nullable
    public T poll() {
        return this.queue.poll();
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override
    public void clear() {
        if (DISCARD_GUARD.getAndIncrement(this) != 0) {
            return;
        }
        int missed = 1;
        while (true) {
            Operators.onDiscardQueueWithClear(this.queue, this.currentContext(), null);
            int dg = this.discardGuard;
            if (missed == dg) {
                if ((missed = DISCARD_GUARD.addAndGet(this, -missed)) != 0) continue;
                break;
            }
            missed = dg;
        }
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
    public boolean isDisposed() {
        return this.cancelled || this.done;
    }

    @Override
    public boolean isTerminated() {
        return this.done;
    }

    @Override
    @Nullable
    public Throwable getError() {
        return this.error;
    }

    @Override
    public CoreSubscriber<? super T> actual() {
        return this.actual;
    }

    @Override
    public long downstreamCount() {
        return this.hasDownstreams() ? 1L : 0L;
    }

    @Override
    public boolean hasDownstreams() {
        return this.hasDownstream;
    }
}


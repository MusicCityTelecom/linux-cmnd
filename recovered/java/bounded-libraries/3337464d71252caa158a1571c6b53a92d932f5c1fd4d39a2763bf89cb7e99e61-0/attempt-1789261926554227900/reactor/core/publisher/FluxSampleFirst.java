/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxSampleFirst<T, U>
extends InternalFluxOperator<T, T> {
    final Function<? super T, ? extends Publisher<U>> throttler;

    FluxSampleFirst(Flux<? extends T> source, Function<? super T, ? extends Publisher<U>> throttler) {
        super(source);
        this.throttler = Objects.requireNonNull(throttler, "throttler");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        SampleFirstMain main = new SampleFirstMain(actual, this.throttler);
        actual.onSubscribe(main);
        return main;
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SampleFirstOther<U>
    extends Operators.DeferredSubscription
    implements InnerConsumer<U> {
        final SampleFirstMain<?, U> main;

        SampleFirstOther(SampleFirstMain<?, U> main) {
            this.main = main;
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (this.set(s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(U t) {
            this.cancel();
            this.main.otherNext();
        }

        public void onError(Throwable t) {
            this.main.otherError(t);
        }

        public void onComplete() {
            this.main.otherNext();
        }
    }

    static final class SampleFirstMain<T, U>
    implements InnerOperator<T, T> {
        final Function<? super T, ? extends Publisher<U>> throttler;
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile boolean gate;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<SampleFirstMain, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(SampleFirstMain.class, Subscription.class, "s");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<SampleFirstMain, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SampleFirstMain.class, Subscription.class, "other");
        volatile long requested;
        static final AtomicLongFieldUpdater<SampleFirstMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(SampleFirstMain.class, "requested");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<SampleFirstMain> WIP = AtomicIntegerFieldUpdater.newUpdater(SampleFirstMain.class, "wip");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<SampleFirstMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SampleFirstMain.class, Throwable.class, "error");

        SampleFirstMain(CoreSubscriber<? super T> actual, Function<? super T, ? extends Publisher<U>> throttler) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.throttler = throttler;
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
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

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        public void cancel() {
            Operators.terminate(S, this);
            Operators.terminate(OTHER, this);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.gate) {
                Publisher<U> p;
                this.gate = true;
                if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                    this.actual.onNext(t);
                    if (WIP.decrementAndGet(this) != 0) {
                        this.handleTermination();
                        return;
                    }
                } else {
                    return;
                }
                try {
                    p = Objects.requireNonNull(this.throttler.apply(t), "The throttler returned a null publisher");
                }
                catch (Throwable e) {
                    Operators.terminate(S, this);
                    this.error(Operators.onOperatorError(null, e, t, this.ctx));
                    return;
                }
                SampleFirstOther other = new SampleFirstOther(this);
                if (Operators.replace(OTHER, this, other)) {
                    p.subscribe(other);
                }
            } else {
                Operators.onDiscard(t, this.ctx);
            }
        }

        void handleTermination() {
            Throwable e = Exceptions.terminate(ERROR, this);
            if (e != null && e != Exceptions.TERMINATED) {
                this.actual.onError(e);
            } else {
                this.actual.onComplete();
            }
        }

        void error(Throwable e) {
            if (Exceptions.addThrowable(ERROR, this, e)) {
                if (WIP.getAndIncrement(this) == 0) {
                    this.handleTermination();
                }
            } else {
                Operators.onErrorDropped(e, this.ctx);
            }
        }

        public void onError(Throwable t) {
            Operators.terminate(OTHER, this);
            this.error(t);
        }

        public void onComplete() {
            Operators.terminate(OTHER, this);
            if (WIP.getAndIncrement(this) == 0) {
                this.handleTermination();
            }
        }

        void otherNext() {
            this.gate = false;
        }

        void otherError(Throwable e) {
            Operators.terminate(S, this);
            this.error(e);
        }
    }
}


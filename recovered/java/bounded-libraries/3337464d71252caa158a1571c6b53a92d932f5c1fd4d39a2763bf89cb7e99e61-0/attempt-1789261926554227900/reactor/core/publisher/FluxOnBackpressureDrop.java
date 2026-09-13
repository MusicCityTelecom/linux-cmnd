/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxOnBackpressureDrop<T>
extends InternalFluxOperator<T, T> {
    static final Consumer<Object> NOOP = t -> {};
    final Consumer<? super T> onDrop;

    FluxOnBackpressureDrop(Flux<? extends T> source) {
        super(source);
        this.onDrop = NOOP;
    }

    FluxOnBackpressureDrop(Flux<? extends T> source, Consumer<? super T> onDrop) {
        super(source);
        this.onDrop = Objects.requireNonNull(onDrop, "onDrop");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new DropSubscriber<T>(actual, this.onDrop);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DropSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final Consumer<? super T> onDrop;
        Subscription s;
        volatile long requested;
        static final AtomicLongFieldUpdater<DropSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(DropSubscriber.class, "requested");
        boolean done;

        DropSubscriber(CoreSubscriber<? super T> actual, Consumer<? super T> onDrop) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.onDrop = onDrop;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                try {
                    this.onDrop.accept(t);
                }
                catch (Throwable e) {
                    Operators.onErrorDropped(e, this.ctx);
                }
                Operators.onDiscard(t, this.ctx);
                return;
            }
            long r = this.requested;
            if (r != 0L) {
                this.actual.onNext(t);
                if (r != Long.MAX_VALUE) {
                    Operators.produced(REQUESTED, this, 1L);
                }
            } else {
                try {
                    this.onDrop.accept(t);
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                }
                Operators.onDiscard(t, this.ctx);
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class ParallelThen
extends Mono<Void>
implements Scannable,
Fuseable {
    final ParallelFlux<?> source;

    ParallelThen(ParallelFlux<?> source) {
        this.source = source;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super Void> actual) {
        ThenMain parent = new ThenMain(actual, this.source.parallelism());
        actual.onSubscribe(parent);
        this.source.subscribe(parent.subscribers);
    }

    static final class ThenInner
    implements InnerConsumer<Object> {
        final ThenMain parent;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<ThenInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(ThenInner.class, Subscription.class, "s");

        ThenInner(ThenMain parent) {
            this.parent = parent;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
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
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object t) {
            Operators.onDiscard(t, this.parent.currentContext());
        }

        public void onError(Throwable t) {
            this.parent.innerError(t);
        }

        public void onComplete() {
            this.parent.innerComplete();
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class ThenMain
    extends Operators.MonoSubscriber<Object, Void> {
        final ThenInner[] subscribers;
        volatile int remaining;
        static final AtomicIntegerFieldUpdater<ThenMain> REMAINING = AtomicIntegerFieldUpdater.newUpdater(ThenMain.class, "remaining");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<ThenMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ThenMain.class, Throwable.class, "error");

        ThenMain(CoreSubscriber<? super Void> subscriber, int n) {
            super(subscriber);
            ThenInner[] a = new ThenInner[n];
            for (int i = 0; i < n; ++i) {
                a[i] = new ThenInner(this);
            }
            this.subscribers = a;
            REMAINING.lazySet(this, n);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return REMAINING.get(this) == 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void cancel() {
            for (ThenInner inner : this.subscribers) {
                inner.cancel();
            }
            super.cancel();
        }

        void innerError(Throwable ex) {
            if (ERROR.compareAndSet(this, null, ex)) {
                this.cancel();
                this.actual.onError(ex);
            } else if (this.error != ex) {
                Operators.onErrorDropped(ex, this.actual.currentContext());
            }
        }

        void innerComplete() {
            if (REMAINING.decrementAndGet(this) == 0) {
                this.actual.onComplete();
            }
        }
    }
}


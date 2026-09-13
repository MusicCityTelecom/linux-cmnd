/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxArray;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelGroup<T>
extends Flux<GroupedFlux<Integer, T>>
implements Scannable,
Fuseable {
    final ParallelFlux<? extends T> source;

    ParallelGroup(ParallelFlux<? extends T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(CoreSubscriber<? super GroupedFlux<Integer, T>> actual) {
        int n = this.source.parallelism();
        ParallelInnerGroup[] groups = new ParallelInnerGroup[n];
        for (int i = 0; i < n; ++i) {
            groups[i] = new ParallelInnerGroup(i);
        }
        FluxArray.subscribe(actual, groups);
        this.source.subscribe(groups);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class ParallelInnerGroup<T>
    extends GroupedFlux<Integer, T>
    implements InnerOperator<T, T> {
        final int key;
        volatile int once;
        static final AtomicIntegerFieldUpdater<ParallelInnerGroup> ONCE = AtomicIntegerFieldUpdater.newUpdater(ParallelInnerGroup.class, "once");
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<ParallelInnerGroup, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(ParallelInnerGroup.class, Subscription.class, "s");
        volatile long requested;
        static final AtomicLongFieldUpdater<ParallelInnerGroup> REQUESTED = AtomicLongFieldUpdater.newUpdater(ParallelInnerGroup.class, "requested");
        CoreSubscriber<? super T> actual;

        ParallelInnerGroup(int key) {
            this.key = key;
        }

        @Override
        public Integer key() {
            return this.key;
        }

        @Override
        public void subscribe(CoreSubscriber<? super T> actual) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                this.actual = actual;
                actual.onSubscribe(this);
            } else {
                Operators.error(actual, new IllegalStateException("This ParallelGroup can be subscribed to at most once."));
            }
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
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            long r;
            if (Operators.setOnce(S, this, s) && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                s.request(r);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Subscription a = this.s;
                if (a == null) {
                    long r;
                    Operators.addCap(REQUESTED, this, n);
                    a = this.s;
                    if (a != null && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                        a.request(n);
                    }
                } else {
                    a.request(n);
                }
            }
        }

        public void cancel() {
            Operators.terminate(S, this);
        }
    }
}


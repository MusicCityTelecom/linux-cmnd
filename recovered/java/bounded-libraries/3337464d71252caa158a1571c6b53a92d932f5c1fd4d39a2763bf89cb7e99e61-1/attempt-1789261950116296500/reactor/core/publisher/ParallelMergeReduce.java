/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
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

final class ParallelMergeReduce<T>
extends Mono<T>
implements Scannable,
Fuseable {
    final ParallelFlux<? extends T> source;
    final BiFunction<T, T, T> reducer;

    ParallelMergeReduce(ParallelFlux<? extends T> source, BiFunction<T, T, T> reducer) {
        this.source = source;
        this.reducer = reducer;
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
    public void subscribe(CoreSubscriber<? super T> actual) {
        MergeReduceMain<? super T> parent = new MergeReduceMain<T>(actual, this.source.parallelism(), this.reducer);
        actual.onSubscribe(parent);
        this.source.subscribe(parent.subscribers);
    }

    static final class SlotPair<T> {
        T first;
        T second;
        volatile int acquireIndex;
        static final AtomicIntegerFieldUpdater<SlotPair> ACQ = AtomicIntegerFieldUpdater.newUpdater(SlotPair.class, "acquireIndex");
        volatile int releaseIndex;
        static final AtomicIntegerFieldUpdater<SlotPair> REL = AtomicIntegerFieldUpdater.newUpdater(SlotPair.class, "releaseIndex");

        SlotPair() {
        }

        int tryAcquireSlot() {
            int acquired;
            do {
                if ((acquired = this.acquireIndex) < 2) continue;
                return -1;
            } while (!ACQ.compareAndSet(this, acquired, acquired + 1));
            return acquired;
        }

        boolean releaseSlot() {
            return REL.incrementAndGet(this) == 2;
        }
    }

    static final class MergeReduceInner<T>
    implements InnerConsumer<T> {
        final MergeReduceMain<T> parent;
        final BiFunction<T, T, T> reducer;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<MergeReduceInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(MergeReduceInner.class, Subscription.class, "s");
        T value;
        boolean done;

        MergeReduceInner(MergeReduceMain<T> parent, BiFunction<T, T, T> reducer) {
            this.parent = parent;
            this.reducer = reducer;
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
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.value != null ? 1 : 0;
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

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            T v = this.value;
            if (v == null) {
                this.value = t;
            } else {
                try {
                    v = Objects.requireNonNull(this.reducer.apply(v, t), "The reducer returned a null value");
                }
                catch (Throwable ex) {
                    this.onError(Operators.onOperatorError(this.s, ex, t, this.currentContext()));
                    return;
                }
                this.value = v;
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.parent.currentContext());
                return;
            }
            this.done = true;
            this.parent.innerError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerComplete(this.value);
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class MergeReduceMain<T>
    extends Operators.MonoSubscriber<T, T> {
        final MergeReduceInner<T>[] subscribers;
        final BiFunction<T, T, T> reducer;
        volatile SlotPair<T> current;
        static final AtomicReferenceFieldUpdater<MergeReduceMain, SlotPair> CURRENT = AtomicReferenceFieldUpdater.newUpdater(MergeReduceMain.class, SlotPair.class, "current");
        volatile int remaining;
        static final AtomicIntegerFieldUpdater<MergeReduceMain> REMAINING = AtomicIntegerFieldUpdater.newUpdater(MergeReduceMain.class, "remaining");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<MergeReduceMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeReduceMain.class, Throwable.class, "error");

        MergeReduceMain(CoreSubscriber<? super T> subscriber, int n, BiFunction<T, T, T> reducer) {
            super(subscriber);
            MergeReduceInner[] a = new MergeReduceInner[n];
            for (int i = 0; i < n; ++i) {
                a[i] = new MergeReduceInner<T>(this, reducer);
            }
            this.subscribers = a;
            this.reducer = reducer;
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

        @Nullable
        SlotPair<T> addValue(T value) {
            int c;
            SlotPair<T> curr;
            while (true) {
                if ((curr = this.current) == null && !CURRENT.compareAndSet(this, null, curr = new SlotPair())) {
                    continue;
                }
                c = curr.tryAcquireSlot();
                if (c >= 0) break;
                CURRENT.compareAndSet(this, curr, null);
            }
            if (c == 0) {
                curr.first = value;
            } else {
                curr.second = value;
            }
            if (curr.releaseSlot()) {
                CURRENT.compareAndSet(this, curr, null);
                return curr;
            }
            return null;
        }

        @Override
        public void cancel() {
            for (MergeReduceInner<T> inner : this.subscribers) {
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

        void innerComplete(@Nullable T value) {
            SlotPair<T> sp;
            if (value != null) {
                while ((sp = this.addValue(value)) != null) {
                    try {
                        value = Objects.requireNonNull(this.reducer.apply(sp.first, sp.second), "The reducer returned a null value");
                    }
                    catch (Throwable ex) {
                        this.innerError(Operators.onOperatorError(this, ex, this.actual.currentContext()));
                        return;
                    }
                }
            }
            if (REMAINING.decrementAndGet(this) == 0) {
                sp = this.current;
                CURRENT.lazySet(this, null);
                if (sp != null) {
                    this.complete(sp.first);
                } else {
                    this.actual.onComplete();
                }
            }
        }
    }
}


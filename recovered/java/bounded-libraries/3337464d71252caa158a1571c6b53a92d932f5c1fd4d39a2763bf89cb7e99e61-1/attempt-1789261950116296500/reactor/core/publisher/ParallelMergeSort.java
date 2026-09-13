/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class ParallelMergeSort<T>
extends Flux<T>
implements Scannable {
    final ParallelFlux<List<T>> source;
    final Comparator<? super T> comparator;

    ParallelMergeSort(ParallelFlux<List<T>> source, Comparator<? super T> comparator) {
        this.source = source;
        this.comparator = comparator;
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        MergeSortMain<? super T> parent = new MergeSortMain<T>(actual, this.source.parallelism(), this.comparator);
        actual.onSubscribe(parent);
        this.source.subscribe(parent.subscribers);
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

    static final class MergeSortInner<T>
    implements InnerConsumer<List<T>> {
        final MergeSortMain<T> parent;
        final int index;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<MergeSortInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(MergeSortInner.class, Subscription.class, "s");

        MergeSortInner(MergeSortMain<T> parent, int index) {
            this.parent = parent;
            this.index = index;
        }

        @Override
        public Context currentContext() {
            return this.parent.actual.currentContext();
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

        public void onNext(List<T> t) {
            this.parent.innerNext(t, this.index);
        }

        public void onError(Throwable t) {
            this.parent.innerError(t);
        }

        public void onComplete() {
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class MergeSortMain<T>
    implements InnerProducer<T> {
        final MergeSortInner<T>[] subscribers;
        final List<T>[] lists;
        final int[] indexes;
        final Comparator<? super T> comparator;
        final CoreSubscriber<? super T> actual;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<MergeSortMain> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeSortMain.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<MergeSortMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeSortMain.class, "requested");
        volatile boolean cancelled;
        volatile int remaining;
        static final AtomicIntegerFieldUpdater<MergeSortMain> REMAINING = AtomicIntegerFieldUpdater.newUpdater(MergeSortMain.class, "remaining");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<MergeSortMain, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeSortMain.class, Throwable.class, "error");

        MergeSortMain(CoreSubscriber<? super T> actual, int n, Comparator<? super T> comparator) {
            this.comparator = comparator;
            this.actual = actual;
            MergeSortInner[] s = new MergeSortInner[n];
            for (int i = 0; i < n; ++i) {
                s[i] = new MergeSortInner(this, i);
            }
            this.subscribers = s;
            this.lists = new List[n];
            this.indexes = new int[n];
            REMAINING.lazySet(this, n);
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.subscribers.length - this.remaining;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                if (this.remaining == 0) {
                    this.drain();
                }
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.cancelAll();
                if (WIP.getAndIncrement(this) == 0) {
                    Arrays.fill(this.lists, null);
                }
            }
        }

        void cancelAll() {
            for (MergeSortInner<T> s : this.subscribers) {
                s.cancel();
            }
        }

        void innerNext(List<T> value, int index) {
            this.lists[index] = value;
            if (REMAINING.decrementAndGet(this) == 0) {
                this.drain();
            }
        }

        void innerError(Throwable ex) {
            if (ERROR.compareAndSet(this, null, ex)) {
                this.cancelAll();
                this.drain();
            } else if (this.error != ex) {
                Operators.onErrorDropped(ex, this.actual.currentContext());
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            CoreSubscriber<? super T> a = this.actual;
            Object[] lists = this.lists;
            int[] indexes = this.indexes;
            int n = indexes.length;
            while (true) {
                int w;
                Throwable ex;
                long e;
                long r = this.requested;
                for (e = 0L; e != r; ++e) {
                    if (this.cancelled) {
                        Arrays.fill(lists, null);
                        return;
                    }
                    ex = this.error;
                    if (ex != null) {
                        this.cancelAll();
                        Arrays.fill(lists, null);
                        a.onError(ex);
                        return;
                    }
                    Object min = null;
                    int minIndex = -1;
                    for (int i = 0; i < n; ++i) {
                        Object list = lists[i];
                        int index = indexes[i];
                        if (list.size() == index) continue;
                        if (min == null) {
                            min = list.get(index);
                            minIndex = i;
                            continue;
                        }
                        Object b = list.get(index);
                        if (this.comparator.compare(min, b) <= 0) continue;
                        min = b;
                        minIndex = i;
                    }
                    if (min == null) {
                        Arrays.fill(lists, null);
                        a.onComplete();
                        return;
                    }
                    a.onNext(min);
                    int n2 = minIndex;
                    indexes[n2] = indexes[n2] + 1;
                }
                if (e == r) {
                    if (this.cancelled) {
                        Arrays.fill(lists, null);
                        return;
                    }
                    ex = this.error;
                    if (ex != null) {
                        this.cancelAll();
                        Arrays.fill(lists, null);
                        a.onError(ex);
                        return;
                    }
                    boolean empty = true;
                    for (int i = 0; i < n; ++i) {
                        if (indexes[i] == lists[i].size()) continue;
                        empty = false;
                        break;
                    }
                    if (empty) {
                        Arrays.fill(lists, null);
                        a.onComplete();
                        return;
                    }
                }
                if (e != 0L && r != Long.MAX_VALUE) {
                    REQUESTED.addAndGet(this, -e);
                }
                if ((w = this.wip) == missed) {
                    if ((missed = WIP.addAndGet(this, -missed)) != 0) continue;
                    break;
                }
                missed = w;
            }
        }
    }
}


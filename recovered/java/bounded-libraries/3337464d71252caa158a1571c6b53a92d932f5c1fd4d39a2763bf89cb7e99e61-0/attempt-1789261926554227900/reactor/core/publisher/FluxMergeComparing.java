/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxMergeComparing<T>
extends Flux<T>
implements SourceProducer<T> {
    final int prefetch;
    final Comparator<? super T> valueComparator;
    final Publisher<? extends T>[] sources;
    final boolean delayError;

    @SafeVarargs
    FluxMergeComparing(int prefetch, Comparator<? super T> valueComparator, boolean delayError, Publisher<? extends T> ... sources) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.sources = Objects.requireNonNull(sources, "sources must be non-null");
        for (int i = 0; i < sources.length; ++i) {
            Publisher<? extends T> source = sources[i];
            if (source != null) continue;
            throw new NullPointerException("sources[" + i + "] is null");
        }
        this.prefetch = prefetch;
        this.valueComparator = valueComparator;
        this.delayError = delayError;
    }

    FluxMergeComparing<T> mergeAdditionalSource(Publisher<? extends T> source, Comparator<? super T> otherComparator) {
        int n = this.sources.length;
        Publisher[] newArray = new Publisher[n + 1];
        System.arraycopy(this.sources, 0, newArray, 0, n);
        newArray[n] = source;
        if (!this.valueComparator.equals(otherComparator)) {
            Comparator<? super T> currentComparator = this.valueComparator;
            Comparator<? super T> newComparator = currentComparator.thenComparing(otherComparator);
            return new FluxMergeComparing<T>(this.prefetch, newComparator, this.delayError, newArray);
        }
        return new FluxMergeComparing<T>(this.prefetch, this.valueComparator, this.delayError, newArray);
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.sources.length > 0 ? this.sources[0] : null;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.prefetch;
        }
        if (key == Scannable.Attr.DELAY_ERROR) {
            return this.delayError;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        MergeOrderedMainProducer<? extends T> main = new MergeOrderedMainProducer<T>(actual, this.valueComparator, this.prefetch, this.sources.length, this.delayError);
        actual.onSubscribe(main);
        main.subscribe(this.sources);
    }

    static final class MergeOrderedInnerSubscriber<T>
    implements InnerOperator<T, T> {
        final MergeOrderedMainProducer<T> parent;
        final int prefetch;
        final int limit;
        final Queue<T> queue;
        int consumed;
        volatile boolean done;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<MergeOrderedInnerSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(MergeOrderedInnerSubscriber.class, Subscription.class, "s");

        MergeOrderedInnerSubscriber(MergeOrderedMainProducer<T> parent, int prefetch) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
            this.queue = Queues.get(prefetch).get();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request((long)this.prefetch);
            }
        }

        public void onNext(T item) {
            if (this.parent.done || this.done) {
                Operators.onNextDropped(item, this.actual().currentContext());
                return;
            }
            this.queue.offer(item);
            this.parent.drain();
        }

        public void onError(Throwable throwable) {
            this.parent.onInnerError(this, throwable);
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        public void request(long n) {
            int c = this.consumed + 1;
            if (c == this.limit) {
                this.consumed = 0;
                Subscription sub = this.s;
                if (sub != this) {
                    sub.request((long)c);
                }
            } else {
                this.consumed = c;
            }
        }

        public void cancel() {
            Subscription sub = S.getAndSet(this, this);
            if (sub != null && sub != this) {
                sub.cancel();
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.parent.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class MergeOrderedMainProducer<T>
    implements InnerProducer<T> {
        static final Object DONE = new Object();
        final CoreSubscriber<? super T> actual;
        final MergeOrderedInnerSubscriber<T>[] subscribers;
        final Comparator<? super T> comparator;
        final Object[] values;
        final boolean delayError;
        boolean done;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<MergeOrderedMainProducer, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeOrderedMainProducer.class, Throwable.class, "error");
        volatile int cancelled;
        static final AtomicIntegerFieldUpdater<MergeOrderedMainProducer> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "cancelled");
        volatile long requested;
        static final AtomicLongFieldUpdater<MergeOrderedMainProducer> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "requested");
        volatile long emitted;
        static final AtomicLongFieldUpdater<MergeOrderedMainProducer> EMITTED = AtomicLongFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "emitted");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<MergeOrderedMainProducer> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "wip");

        MergeOrderedMainProducer(CoreSubscriber<? super T> actual, Comparator<? super T> comparator, int prefetch, int n, boolean delayError) {
            this.actual = actual;
            this.comparator = comparator;
            this.delayError = delayError;
            MergeOrderedInnerSubscriber[] mergeOrderedInnerSub = new MergeOrderedInnerSubscriber[n];
            this.subscribers = mergeOrderedInnerSub;
            for (int i = 0; i < n; ++i) {
                this.subscribers[i] = new MergeOrderedInnerSubscriber(this, prefetch);
            }
            this.values = new Object[n];
        }

        void subscribe(Publisher<? extends T>[] sources) {
            if (sources.length != this.subscribers.length) {
                throw new IllegalArgumentException("must subscribe with " + this.subscribers.length + " sources");
            }
            for (int i = 0; i < sources.length; ++i) {
                Objects.requireNonNull(sources[i], "subscribed with a null source: sources[" + i + "]");
                sources[i].subscribe(this.subscribers[i]);
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            Operators.addCap(REQUESTED, this, n);
            this.drain();
        }

        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                for (MergeOrderedInnerSubscriber<T> subscriber : this.subscribers) {
                    subscriber.cancel();
                }
                if (WIP.getAndIncrement(this) == 0) {
                    this.discardData();
                }
            }
        }

        void onInnerError(MergeOrderedInnerSubscriber<T> inner, Throwable ex) {
            Throwable e = Operators.onNextInnerError(ex, this.actual().currentContext(), this);
            if (e != null) {
                if (Exceptions.addThrowable(ERROR, this, e)) {
                    if (!this.delayError) {
                        this.done = true;
                    }
                    inner.done = true;
                    this.drain();
                } else {
                    inner.done = true;
                    Operators.onErrorDropped(e, this.actual.currentContext());
                }
            } else {
                inner.done = true;
                this.drain();
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            CoreSubscriber<? super T> actual = this.actual;
            Comparator<Object> comparator = this.comparator;
            MergeOrderedInnerSubscriber<T>[] subscribers = this.subscribers;
            int n = subscribers.length;
            Object[] values = this.values;
            long e = this.emitted;
            do {
                long r = this.requested;
                while (true) {
                    boolean d = this.done;
                    if (this.cancelled != 0) {
                        Arrays.fill(values, null);
                        for (MergeOrderedInnerSubscriber<T> inner : subscribers) {
                            inner.queue.clear();
                        }
                        return;
                    }
                    int innerDoneCount = 0;
                    int nonEmpty = 0;
                    for (int i = 0; i < n; ++i) {
                        Object o = values[i];
                        if (o == DONE) {
                            ++innerDoneCount;
                            ++nonEmpty;
                            continue;
                        }
                        if (o == null) {
                            boolean innerDone = subscribers[i].done;
                            o = subscribers[i].queue.poll();
                            if (o != null) {
                                values[i] = o;
                                ++nonEmpty;
                                continue;
                            }
                            if (!innerDone) continue;
                            values[i] = DONE;
                            ++innerDoneCount;
                            ++nonEmpty;
                            continue;
                        }
                        ++nonEmpty;
                    }
                    if (this.checkTerminated(d || innerDoneCount == n, actual)) {
                        return;
                    }
                    if (nonEmpty != n || e >= r) break;
                    Object min = null;
                    int minIndex = -1;
                    int i = 0;
                    for (Object o : values) {
                        if (o != DONE) {
                            boolean smaller;
                            Object t;
                            try {
                                t = o;
                                smaller = min == null || comparator.compare(min, t) > 0;
                            }
                            catch (Throwable ex) {
                                Exceptions.addThrowable(ERROR, this, ex);
                                this.cancel();
                                actual.onError(Exceptions.terminate(ERROR, this));
                                return;
                            }
                            if (smaller) {
                                min = t = o;
                                minIndex = i;
                            }
                        }
                        ++i;
                    }
                    values[minIndex] = null;
                    actual.onNext(min);
                    ++e;
                    subscribers[minIndex].request(1L);
                }
                this.emitted = e;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        boolean checkTerminated(boolean d, Subscriber<?> a) {
            if (this.cancelled != 0) {
                this.discardData();
                return true;
            }
            if (!d) {
                return false;
            }
            if (this.delayError) {
                Throwable e = this.error;
                if (e != null && e != Exceptions.TERMINATED) {
                    e = Exceptions.terminate(ERROR, this);
                    a.onError(e);
                } else {
                    a.onComplete();
                }
            } else {
                Throwable e = this.error;
                if (e != null && e != Exceptions.TERMINATED) {
                    e = Exceptions.terminate(ERROR, this);
                    this.cancel();
                    this.discardData();
                    a.onError(e);
                } else {
                    a.onComplete();
                }
            }
            return true;
        }

        private void discardData() {
            Context ctx = this.actual().currentContext();
            for (Object v : this.values) {
                if (v == DONE) continue;
                Operators.onDiscard(v, ctx);
            }
            Arrays.fill(this.values, null);
            for (MergeOrderedInnerSubscriber<T> subscriber : this.subscribers) {
                Operators.onDiscardQueueWithClear(subscriber.queue, ctx, null);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled > 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.delayError;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested - this.emitted;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}


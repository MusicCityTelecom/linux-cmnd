/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxZip<T, R>
extends Flux<R>
implements SourceProducer<R> {
    final Publisher<? extends T>[] sources;
    final Iterable<? extends Publisher<? extends T>> sourcesIterable;
    final Function<? super Object[], ? extends R> zipper;
    final Supplier<? extends Queue<T>> queueSupplier;
    final int prefetch;

    <U> FluxZip(Publisher<? extends T> p1, Publisher<? extends U> p2, BiFunction<? super T, ? super U, ? extends R> zipper2, Supplier<? extends Queue<T>> queueSupplier, int prefetch) {
        this(new Publisher[]{Objects.requireNonNull(p1, "p1"), Objects.requireNonNull(p2, "p2")}, new PairwiseZipper(new BiFunction[]{Objects.requireNonNull(zipper2, "zipper2")}), queueSupplier, prefetch);
    }

    FluxZip(Publisher<? extends T>[] sources, Function<? super Object[], ? extends R> zipper, Supplier<? extends Queue<T>> queueSupplier, int prefetch) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.sources = Objects.requireNonNull(sources, "sources");
        if (sources.length == 0) {
            throw new IllegalArgumentException("at least one source is required");
        }
        this.sourcesIterable = null;
        this.zipper = Objects.requireNonNull(zipper, "zipper");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
        this.prefetch = prefetch;
    }

    FluxZip(Iterable<? extends Publisher<? extends T>> sourcesIterable, Function<? super Object[], ? extends R> zipper, Supplier<? extends Queue<T>> queueSupplier, int prefetch) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.sources = null;
        this.sourcesIterable = Objects.requireNonNull(sourcesIterable, "sourcesIterable");
        this.zipper = Objects.requireNonNull(zipper, "zipper");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
        this.prefetch = prefetch;
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Nullable
    FluxZip<T, R> zipAdditionalSource(Publisher source, BiFunction zipper) {
        Publisher<? extends T>[] oldSources = this.sources;
        if (oldSources != null && this.zipper instanceof PairwiseZipper) {
            int oldLen = oldSources.length;
            Publisher[] newSources = new Publisher[oldLen + 1];
            System.arraycopy(oldSources, 0, newSources, 0, oldLen);
            newSources[oldLen] = source;
            PairwiseZipper z = ((PairwiseZipper)this.zipper).then(zipper);
            return new FluxZip<T, R>(newSources, z, this.queueSupplier, this.prefetch);
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super R> actual) {
        Publisher<? extends T>[] srcs = this.sources;
        try {
            if (srcs != null) {
                this.handleArrayMode(actual, srcs);
            } else {
                this.handleIterableMode(actual, this.sourcesIterable);
            }
        }
        catch (Throwable e) {
            Operators.reportThrowInSubscribe(actual, e);
            return;
        }
    }

    void handleIterableMode(CoreSubscriber<? super R> s, Iterable<? extends Publisher<? extends T>> sourcesIterable) {
        Object[] scalars = new Object[8];
        Publisher[] srcs = new Publisher[8];
        int n = 0;
        int sc = 0;
        for (Publisher<T> publisher : sourcesIterable) {
            if (publisher == null) {
                Operators.error(s, Operators.onOperatorError(new NullPointerException("The sourcesIterable returned a null Publisher"), s.currentContext()));
                return;
            }
            if (publisher instanceof Callable) {
                Object v;
                Callable callable = (Callable)publisher;
                try {
                    v = callable.call();
                }
                catch (Throwable e) {
                    Operators.error(s, Operators.onOperatorError(e, s.currentContext()));
                    return;
                }
                if (v == null) {
                    Operators.complete(s);
                    return;
                }
                if (n == scalars.length) {
                    Object[] b = new Object[n + (n >> 1)];
                    System.arraycopy(scalars, 0, b, 0, n);
                    Publisher[] c = new Publisher[b.length];
                    System.arraycopy(srcs, 0, c, 0, n);
                    scalars = b;
                    srcs = c;
                }
                scalars[n] = v;
                ++sc;
            } else {
                if (n == srcs.length) {
                    Object[] b = new Object[n + (n >> 1)];
                    System.arraycopy(scalars, 0, b, 0, n);
                    Publisher[] c = new Publisher[b.length];
                    System.arraycopy(srcs, 0, c, 0, n);
                    scalars = b;
                    srcs = c;
                }
                srcs[n] = publisher;
            }
            ++n;
        }
        if (n == 0) {
            Operators.complete(s);
            return;
        }
        if (n < scalars.length) {
            scalars = Arrays.copyOfRange(scalars, 0, n, scalars.getClass());
        }
        this.handleBoth(s, srcs, scalars, n, sc);
    }

    void handleArrayMode(CoreSubscriber<? super R> s, Publisher<? extends T>[] srcs) {
        Object[] scalars = null;
        int n = srcs.length;
        int sc = 0;
        for (int j = 0; j < n; ++j) {
            Object v;
            Publisher<? extends T> p = srcs[j];
            if (p == null) {
                Operators.error(s, new NullPointerException("The sources contained a null Publisher"));
                return;
            }
            if (!(p instanceof Callable)) continue;
            try {
                v = ((Callable)p).call();
            }
            catch (Throwable e) {
                Operators.error(s, Operators.onOperatorError(e, s.currentContext()));
                return;
            }
            if (v == null) {
                Operators.complete(s);
                return;
            }
            if (scalars == null) {
                scalars = new Object[n];
            }
            scalars[j] = v;
            ++sc;
        }
        this.handleBoth(s, srcs, scalars, n, sc);
    }

    void handleBoth(CoreSubscriber<? super R> s, Publisher<? extends T>[] srcs, @Nullable Object[] scalars, int n, int sc) {
        if (sc != 0 && scalars != null) {
            if (n != sc) {
                ZipSingleCoordinator<? extends T, R> coordinator = new ZipSingleCoordinator<T, R>(s, scalars, n, this.zipper);
                s.onSubscribe(coordinator);
                coordinator.subscribe(n, sc, srcs);
            } else {
                R r;
                Operators.MonoSubscriber sds = new Operators.MonoSubscriber(s);
                s.onSubscribe(sds);
                try {
                    r = Objects.requireNonNull(this.zipper.apply((Object[])scalars), "The zipper returned a null value");
                }
                catch (Throwable e) {
                    s.onError(Operators.onOperatorError(e, s.currentContext()));
                    return;
                }
                sds.complete(r);
            }
        } else {
            ZipCoordinator<? extends T, R> coordinator = new ZipCoordinator<T, R>(s, this.zipper, n, this.queueSupplier, this.prefetch);
            s.onSubscribe(coordinator);
            coordinator.subscribe(srcs, n);
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.prefetch;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class PairwiseZipper<R>
    implements Function<Object[], R> {
        final BiFunction[] zippers;

        PairwiseZipper(BiFunction[] zippers) {
            this.zippers = zippers;
        }

        @Override
        public R apply(Object[] args) {
            Object o = this.zippers[0].apply(args[0], args[1]);
            for (int i = 1; i < this.zippers.length; ++i) {
                o = this.zippers[i].apply(o, args[i + 1]);
            }
            return o;
        }

        public PairwiseZipper then(BiFunction zipper) {
            BiFunction[] zippers = this.zippers;
            int n = zippers.length;
            BiFunction[] newZippers = new BiFunction[n + 1];
            System.arraycopy(zippers, 0, newZippers, 0, n);
            newZippers[n] = zipper;
            return new PairwiseZipper<R>(newZippers);
        }
    }

    static final class ZipInner<T>
    implements InnerConsumer<T> {
        final ZipCoordinator<T, ?> parent;
        final int prefetch;
        final int limit;
        final int index;
        final Supplier<? extends Queue<T>> queueSupplier;
        volatile Queue<T> queue;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<ZipInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(ZipInner.class, Subscription.class, "s");
        long produced;
        volatile boolean done;
        int sourceMode;

        ZipInner(ZipCoordinator<T, ?> parent, int prefetch, int index, Supplier<? extends Queue<T>> queueSupplier) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.index = index;
            this.queueSupplier = queueSupplier;
            this.limit = Operators.unboundedOrLimit(prefetch);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                if (s instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription f = (Fuseable.QueueSubscription)s;
                    int m = f.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = 1;
                        this.queue = f;
                        this.done = true;
                        this.parent.drain();
                        return;
                    }
                    if (m == 2) {
                        this.sourceMode = 2;
                        this.queue = f;
                    } else {
                        this.queue = this.queueSupplier.get();
                    }
                } else {
                    this.queue = this.queueSupplier.get();
                }
                s.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        public void onNext(T t) {
            if (this.sourceMode != 2 && !this.queue.offer(t)) {
                this.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), this.currentContext()));
                return;
            }
            this.parent.drain();
        }

        @Override
        public Context currentContext() {
            return this.parent.actual.currentContext();
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.currentContext());
                return;
            }
            this.done = true;
            this.parent.error(t, this.index);
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done && (this.queue == null || this.queue.isEmpty());
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        void cancel() {
            Operators.terminate(S, this);
        }

        void request(long n) {
            if (this.sourceMode != 1) {
                long p = this.produced + n;
                if (p >= (long)this.limit) {
                    this.produced = 0L;
                    this.s.request(p);
                } else {
                    this.produced = p;
                }
            }
        }
    }

    static final class ZipCoordinator<T, R>
    implements InnerProducer<R> {
        final CoreSubscriber<? super R> actual;
        final ZipInner<T>[] subscribers;
        final Function<? super Object[], ? extends R> zipper;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ZipCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(ZipCoordinator.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<ZipCoordinator> REQUESTED = AtomicLongFieldUpdater.newUpdater(ZipCoordinator.class, "requested");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<ZipCoordinator, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ZipCoordinator.class, Throwable.class, "error");
        volatile boolean cancelled;
        final Object[] current;

        ZipCoordinator(CoreSubscriber<? super R> actual, Function<? super Object[], ? extends R> zipper, int n, Supplier<? extends Queue<T>> queueSupplier, int prefetch) {
            this.actual = actual;
            this.zipper = zipper;
            ZipInner[] a = new ZipInner[n];
            for (int i = 0; i < n; ++i) {
                a[i] = new ZipInner(this, prefetch, i, queueSupplier);
            }
            this.current = new Object[n];
            this.subscribers = a;
        }

        void subscribe(Publisher<? extends T>[] sources, int n) {
            ZipInner<T>[] a = this.subscribers;
            for (int i = 0; i < n; ++i) {
                if (this.cancelled || this.error != null) {
                    return;
                }
                ZipInner<T> s = a[i];
                try {
                    sources[i].subscribe(s);
                    continue;
                }
                catch (Throwable e) {
                    Operators.reportThrowInSubscribe(s, e);
                }
            }
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.cancelAll();
            }
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        void error(Throwable e, int index) {
            if (Exceptions.addThrowable(ERROR, this, e)) {
                this.drain();
            } else {
                Operators.onErrorDropped(e, this.actual.currentContext());
            }
        }

        void cancelAll() {
            for (ZipInner<T> s : this.subscribers) {
                s.cancel();
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber<? super R> a = this.actual;
            ZipInner<T>[] qs = this.subscribers;
            int n = qs.length;
            Object[] values = this.current;
            int missed = 1;
            do {
                int j;
                long e;
                long r = this.requested;
                for (e = 0L; r != e; ++e) {
                    R v;
                    if (this.cancelled) {
                        return;
                    }
                    if (this.error != null) {
                        this.cancelAll();
                        Throwable ex = Exceptions.terminate(ERROR, this);
                        a.onError(ex);
                        return;
                    }
                    boolean empty = false;
                    for (int j2 = 0; j2 < n; ++j2) {
                        ZipInner<T> inner = qs[j2];
                        if (values[j2] != null) continue;
                        try {
                            boolean sourceEmpty;
                            boolean d = inner.done;
                            Queue q = inner.queue;
                            Object v2 = q != null ? q.poll() : null;
                            boolean bl = sourceEmpty = v2 == null;
                            if (d && sourceEmpty) {
                                this.cancelAll();
                                a.onComplete();
                                return;
                            }
                            if (!sourceEmpty) {
                                values[j2] = v2;
                                continue;
                            }
                            empty = true;
                            continue;
                        }
                        catch (Throwable ex) {
                            ex = Operators.onOperatorError(ex, this.actual.currentContext());
                            this.cancelAll();
                            Exceptions.addThrowable(ERROR, this, ex);
                            ex = Exceptions.terminate(ERROR, this);
                            a.onError(ex);
                            return;
                        }
                    }
                    if (empty) break;
                    try {
                        v = Objects.requireNonNull(this.zipper.apply((Object[])values.clone()), "The zipper returned a null value");
                    }
                    catch (Throwable ex) {
                        ex = Operators.onOperatorError(null, ex, values.clone(), this.actual.currentContext());
                        this.cancelAll();
                        Exceptions.addThrowable(ERROR, this, ex);
                        ex = Exceptions.terminate(ERROR, this);
                        a.onError(ex);
                        return;
                    }
                    a.onNext(v);
                    Arrays.fill(values, null);
                }
                if (r == e) {
                    if (this.cancelled) {
                        return;
                    }
                    if (this.error != null) {
                        this.cancelAll();
                        Throwable ex = Exceptions.terminate(ERROR, this);
                        a.onError(ex);
                        return;
                    }
                    for (j = 0; j < n; ++j) {
                        ZipInner<T> inner = qs[j];
                        if (values[j] != null) continue;
                        try {
                            boolean empty;
                            boolean d = inner.done;
                            Queue q = inner.queue;
                            Object v = q != null ? q.poll() : null;
                            boolean bl = empty = v == null;
                            if (d && empty) {
                                this.cancelAll();
                                a.onComplete();
                                return;
                            }
                            if (empty) continue;
                            values[j] = v;
                            continue;
                        }
                        catch (Throwable ex) {
                            ex = Operators.onOperatorError(null, ex, values, this.actual.currentContext());
                            this.cancelAll();
                            Exceptions.addThrowable(ERROR, this, ex);
                            ex = Exceptions.terminate(ERROR, this);
                            a.onError(ex);
                            return;
                        }
                    }
                }
                if (e == 0L) continue;
                for (j = 0; j < n; ++j) {
                    ZipInner<T> inner = qs[j];
                    inner.request(e);
                }
                if (r == Long.MAX_VALUE) continue;
                REQUESTED.addAndGet(this, -e);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }
    }

    static final class ZipSingleSubscriber<T>
    implements InnerConsumer<T>,
    Disposable {
        final ZipSingleCoordinator<T, ?> parent;
        final int index;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<ZipSingleSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(ZipSingleSubscriber.class, Subscription.class, "s");
        boolean done;

        ZipSingleSubscriber(ZipSingleCoordinator<T, ?> parent, int index) {
            this.parent = parent;
            this.index = index;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.parent.scalars[this.index] == null ? 0 : 1;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                this.s = s;
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.parent.currentContext());
                return;
            }
            this.done = true;
            Operators.terminate(S, this);
            this.parent.next(t, this.index);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.parent.currentContext());
                return;
            }
            this.done = true;
            this.parent.error(t, this.index);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.complete(this.index);
        }

        @Override
        public void dispose() {
            Operators.terminate(S, this);
        }
    }

    static final class ZipSingleCoordinator<T, R>
    extends Operators.MonoSubscriber<R, R> {
        final Function<? super Object[], ? extends R> zipper;
        final Object[] scalars;
        final ZipSingleSubscriber<T>[] subscribers;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ZipSingleCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(ZipSingleCoordinator.class, "wip");

        ZipSingleCoordinator(CoreSubscriber<? super R> subscriber, Object[] scalars, int n, Function<? super Object[], ? extends R> zipper) {
            super(subscriber);
            this.zipper = zipper;
            this.scalars = scalars;
            ZipSingleSubscriber[] a = new ZipSingleSubscriber[n];
            for (int i = 0; i < n; ++i) {
                if (scalars[i] != null) continue;
                a[i] = new ZipSingleSubscriber(this, i);
            }
            this.subscribers = a;
        }

        void subscribe(int n, int sc, Publisher<? extends T>[] sources) {
            WIP.lazySet(this, n - sc);
            ZipSingleSubscriber<T>[] a = this.subscribers;
            for (int i = 0; i < n && this.wip > 0 && !this.isCancelled(); ++i) {
                ZipSingleSubscriber<T> s = a[i];
                if (s == null) continue;
                try {
                    sources[i].subscribe(s);
                    continue;
                }
                catch (Throwable e) {
                    Operators.reportThrowInSubscribe(s, e);
                }
            }
        }

        void next(T value, int index) {
            Object[] a = this.scalars;
            a[index] = value;
            if (WIP.decrementAndGet(this) == 0) {
                R r;
                try {
                    r = Objects.requireNonNull(this.zipper.apply((Object[])a), "The zipper returned a null value");
                }
                catch (Throwable e) {
                    this.actual.onError(Operators.onOperatorError(this, e, value, this.actual.currentContext()));
                    return;
                }
                this.complete(r);
            }
        }

        void error(Throwable e, int index) {
            if (WIP.getAndSet(this, 0) > 0) {
                this.cancelAll();
                this.actual.onError(e);
            } else {
                Operators.onErrorDropped(e, this.actual.currentContext());
            }
        }

        @Override
        void complete(int index) {
            if (WIP.getAndSet(this, 0) > 0) {
                this.cancelAll();
                this.actual.onComplete();
            }
        }

        @Override
        public void cancel() {
            super.cancel();
            this.cancelAll();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.wip == 0;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.wip > 0 ? this.scalars.length : 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        void cancelAll() {
            for (ZipSingleSubscriber<T> s : this.subscribers) {
                if (s == null) continue;
                s.dispose();
            }
        }
    }
}


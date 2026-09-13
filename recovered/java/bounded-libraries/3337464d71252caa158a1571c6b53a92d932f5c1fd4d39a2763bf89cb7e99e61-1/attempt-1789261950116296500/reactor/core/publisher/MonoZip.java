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
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.FluxZip;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoZip<T, R>
extends Mono<R>
implements SourceProducer<R> {
    final boolean delayError;
    final Publisher<?>[] sources;
    final Iterable<? extends Publisher<?>> sourcesIterable;
    final Function<? super Object[], ? extends R> zipper;

    <U> MonoZip(boolean delayError, Publisher<? extends T> p1, Publisher<? extends U> p2, BiFunction<? super T, ? super U, ? extends R> zipper2) {
        this(delayError, new FluxZip.PairwiseZipper(new BiFunction[]{Objects.requireNonNull(zipper2, "zipper2")}), Objects.requireNonNull(p1, "p1"), Objects.requireNonNull(p2, "p2"));
    }

    MonoZip(boolean delayError, Function<? super Object[], ? extends R> zipper, Publisher<?> ... sources) {
        this.delayError = delayError;
        this.zipper = Objects.requireNonNull(zipper, "zipper");
        this.sources = Objects.requireNonNull(sources, "sources");
        this.sourcesIterable = null;
    }

    MonoZip(boolean delayError, Function<? super Object[], ? extends R> zipper, Iterable<? extends Publisher<?>> sourcesIterable) {
        this.delayError = delayError;
        this.zipper = Objects.requireNonNull(zipper, "zipper");
        this.sources = null;
        this.sourcesIterable = Objects.requireNonNull(sourcesIterable, "sourcesIterable");
    }

    @Nullable
    Mono<R> zipAdditionalSource(Publisher source, BiFunction zipper) {
        Publisher<?>[] oldSources = this.sources;
        if (oldSources != null && this.zipper instanceof FluxZip.PairwiseZipper) {
            int oldLen = oldSources.length;
            Publisher[] newSources = new Publisher[oldLen + 1];
            System.arraycopy(oldSources, 0, newSources, 0, oldLen);
            newSources[oldLen] = source;
            FluxZip.PairwiseZipper z = ((FluxZip.PairwiseZipper)this.zipper).then(zipper);
            return new MonoZip<T, R>(this.delayError, z, newSources);
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super R> actual) {
        Publisher<?>[] a;
        int n = 0;
        if (this.sources != null) {
            a = this.sources;
            n = a.length;
        } else {
            a = new Publisher[8];
            for (Publisher<?> m : this.sourcesIterable) {
                if (n == a.length) {
                    Publisher[] b = new Publisher[n + (n >> 2)];
                    System.arraycopy(a, 0, b, 0, n);
                    a = b;
                }
                a[n++] = m;
            }
        }
        if (n == 0) {
            Operators.complete(actual);
            return;
        }
        ZipCoordinator<? extends R> parent = new ZipCoordinator<R>(actual, n, this.delayError, this.zipper);
        actual.onSubscribe(parent);
        ZipInner<R>[] subs = parent.subscribers;
        for (int i = 0; i < n; ++i) {
            a[i].subscribe(subs[i]);
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.DELAY_ERROR) {
            return this.delayError;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class ZipInner<R>
    implements InnerConsumer<Object> {
        final ZipCoordinator<R> parent;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<ZipInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(ZipInner.class, Subscription.class, "s");
        Object value;
        Throwable error;

        ZipInner(ZipCoordinator<R> parent) {
            this.parent = parent;
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
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            } else {
                s.cancel();
            }
        }

        public void onNext(Object t) {
            if (this.value == null) {
                this.value = t;
                this.parent.signal();
            }
        }

        public void onError(Throwable t) {
            this.error = t;
            if (this.parent.delayError) {
                this.parent.signal();
            } else {
                int n = this.parent.subscribers.length;
                if (ZipCoordinator.DONE.getAndSet(this.parent, n) != n) {
                    this.parent.cancelExcept(this);
                    this.parent.actual.onError(t);
                }
            }
        }

        public void onComplete() {
            if (this.value == null) {
                if (this.parent.delayError) {
                    this.parent.signal();
                } else {
                    int n = this.parent.subscribers.length;
                    if (ZipCoordinator.DONE.getAndSet(this.parent, n) != n) {
                        this.parent.cancelExcept(this);
                        this.parent.actual.onComplete();
                    }
                }
            }
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class ZipCoordinator<R>
    extends Operators.MonoSubscriber<Object, R> {
        final ZipInner<R>[] subscribers;
        final boolean delayError;
        final Function<? super Object[], ? extends R> zipper;
        volatile int done;
        static final AtomicIntegerFieldUpdater<ZipCoordinator> DONE = AtomicIntegerFieldUpdater.newUpdater(ZipCoordinator.class, "done");

        ZipCoordinator(CoreSubscriber<? super R> subscriber, int n, boolean delayError, Function<? super Object[], ? extends R> zipper) {
            super(subscriber);
            this.delayError = delayError;
            this.zipper = zipper;
            this.subscribers = new ZipInner[n];
            for (int i = 0; i < n; ++i) {
                this.subscribers[i] = new ZipInner(this);
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done == this.subscribers.length;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.subscribers.length;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.delayError;
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

        void signal() {
            ZipInner<R>[] a = this.subscribers;
            int n = a.length;
            if (DONE.incrementAndGet(this) != n) {
                return;
            }
            Object[] o = new Object[n];
            Throwable error = null;
            Throwable compositeError = null;
            boolean hasEmpty = false;
            for (int i = 0; i < a.length; ++i) {
                ZipInner<R> m = a[i];
                Object v = m.value;
                if (v != null) {
                    o[i] = v;
                    continue;
                }
                Throwable e = m.error;
                if (e != null) {
                    if (compositeError != null) {
                        compositeError.addSuppressed(e);
                        continue;
                    }
                    if (error != null) {
                        compositeError = Exceptions.multiple(error, e);
                        continue;
                    }
                    error = e;
                    continue;
                }
                hasEmpty = true;
            }
            if (compositeError != null) {
                this.actual.onError(compositeError);
            } else if (error != null) {
                this.actual.onError(error);
            } else if (hasEmpty) {
                this.actual.onComplete();
            } else {
                R r;
                try {
                    r = Objects.requireNonNull(this.zipper.apply((Object[])o), "zipper produced a null value");
                }
                catch (Throwable t) {
                    this.actual.onError(Operators.onOperatorError(null, t, o, this.actual.currentContext()));
                    return;
                }
                this.complete(r);
            }
        }

        @Override
        public void cancel() {
            if (!this.isCancelled()) {
                super.cancel();
                for (ZipInner<R> ms : this.subscribers) {
                    ms.cancel();
                }
            }
        }

        void cancelExcept(ZipInner<R> source) {
            if (!this.isCancelled()) {
                super.cancel();
                for (ZipInner<R> ms : this.subscribers) {
                    if (ms == source) continue;
                    ms.cancel();
                }
            }
        }
    }
}


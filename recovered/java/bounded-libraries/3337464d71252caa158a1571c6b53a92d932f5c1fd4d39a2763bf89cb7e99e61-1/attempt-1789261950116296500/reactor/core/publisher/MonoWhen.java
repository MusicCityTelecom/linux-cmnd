/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoWhen
extends Mono<Void>
implements SourceProducer<Void> {
    final boolean delayError;
    final Publisher<?>[] sources;
    final Iterable<? extends Publisher<?>> sourcesIterable;

    MonoWhen(boolean delayError, Publisher<?> ... sources) {
        this.delayError = delayError;
        this.sources = Objects.requireNonNull(sources, "sources");
        this.sourcesIterable = null;
    }

    MonoWhen(boolean delayError, Iterable<? extends Publisher<?>> sourcesIterable) {
        this.delayError = delayError;
        this.sources = null;
        this.sourcesIterable = Objects.requireNonNull(sourcesIterable, "sourcesIterable");
    }

    @Nullable
    Mono<Void> whenAdditionalSource(Publisher<?> source) {
        Publisher<?>[] oldSources = this.sources;
        if (oldSources != null) {
            int oldLen = oldSources.length;
            Publisher[] newSources = new Publisher[oldLen + 1];
            System.arraycopy(oldSources, 0, newSources, 0, oldLen);
            newSources[oldLen] = source;
            return new MonoWhen(this.delayError, newSources);
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super Void> actual) {
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
        WhenCoordinator parent = new WhenCoordinator(actual, n, this.delayError);
        actual.onSubscribe(parent);
        parent.subscribe(a);
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

    static final class WhenInner
    implements InnerConsumer<Object> {
        final WhenCoordinator parent;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<WhenInner, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(WhenInner.class, Subscription.class, "s");
        Throwable error;

        WhenInner(WhenCoordinator parent) {
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
        }

        public void onError(Throwable t) {
            this.error = t;
            this.parent.signalError(t);
        }

        public void onComplete() {
            this.parent.signal();
        }

        void cancel() {
            Operators.terminate(S, this);
        }
    }

    static final class WhenCoordinator
    extends Operators.MonoSubscriber<Object, Void> {
        final WhenInner[] subscribers;
        final boolean delayError;
        volatile int done;
        static final AtomicIntegerFieldUpdater<WhenCoordinator> DONE = AtomicIntegerFieldUpdater.newUpdater(WhenCoordinator.class, "done");

        WhenCoordinator(CoreSubscriber<? super Void> subscriber, int n, boolean delayError) {
            super(subscriber);
            this.delayError = delayError;
            this.subscribers = new WhenInner[n];
            for (int i = 0; i < n; ++i) {
                this.subscribers[i] = new WhenInner(this);
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

        void subscribe(Publisher<?>[] sources) {
            WhenInner[] a = this.subscribers;
            for (int i = 0; i < a.length; ++i) {
                sources[i].subscribe((Subscriber)a[i]);
            }
        }

        void signalError(Throwable t) {
            if (this.delayError) {
                this.signal();
            } else {
                int n = this.subscribers.length;
                if (DONE.getAndSet(this, n) != n) {
                    this.cancel();
                    this.actual.onError(t);
                }
            }
        }

        void signal() {
            WhenInner[] a = this.subscribers;
            int n = a.length;
            if (DONE.incrementAndGet(this) != n) {
                return;
            }
            Throwable error = null;
            Throwable compositeError = null;
            for (int i = 0; i < a.length; ++i) {
                WhenInner m = a[i];
                Throwable e = m.error;
                if (e == null) continue;
                if (compositeError != null) {
                    compositeError.addSuppressed(e);
                    continue;
                }
                if (error != null) {
                    compositeError = Exceptions.multiple(error, e);
                    continue;
                }
                error = e;
            }
            if (compositeError != null) {
                this.actual.onError(compositeError);
            } else if (error != null) {
                this.actual.onError(error);
            } else {
                this.actual.onComplete();
            }
        }

        @Override
        public void cancel() {
            if (!this.isCancelled()) {
                super.cancel();
                for (WhenInner ms : this.subscribers) {
                    ms.cancel();
                }
            }
        }
    }
}


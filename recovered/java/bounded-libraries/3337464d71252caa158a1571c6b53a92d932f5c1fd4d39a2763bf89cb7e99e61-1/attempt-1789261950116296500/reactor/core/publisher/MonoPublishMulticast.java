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
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPublishMulticast;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoPublishMulticast<T, R>
extends InternalMonoOperator<T, R>
implements Fuseable {
    final Function<? super Mono<T>, ? extends Mono<? extends R>> transform;

    MonoPublishMulticast(Mono<? extends T> source, Function<? super Mono<T>, ? extends Mono<? extends R>> transform) {
        super(source);
        this.transform = Objects.requireNonNull(transform, "transform");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        MonoPublishMulticaster multicast = new MonoPublishMulticaster(actual.currentContext());
        Mono<R> out = Objects.requireNonNull(this.transform.apply(MonoPublishMulticast.fromDirect(multicast)), "The transform returned a null Mono");
        if (out instanceof Fuseable) {
            out.subscribe((CoreSubscriber<R>)new FluxPublishMulticast.CancelFuseableMulticaster<R>(actual, multicast));
        } else {
            out.subscribe((CoreSubscriber<R>)new FluxPublishMulticast.CancelMulticaster<R>(actual, multicast));
        }
        return multicast;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class PublishMulticastInner<T>
    implements InnerProducer<T> {
        final MonoPublishMulticaster<T> parent;
        final CoreSubscriber<? super T> actual;
        volatile int cancelled;
        static final AtomicIntegerFieldUpdater<PublishMulticastInner> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(PublishMulticastInner.class, "cancelled");

        PublishMulticastInner(MonoPublishMulticaster<T> parent, CoreSubscriber<? super T> actual) {
            this.parent = parent;
            this.actual = actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled == 1;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                this.parent.drain();
            }
        }

        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                this.parent.remove(this);
                this.parent.drain();
            }
        }
    }

    static final class MonoPublishMulticaster<T>
    extends Mono<T>
    implements InnerConsumer<T>,
    FluxPublishMulticast.PublishMulticasterParent {
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<MonoPublishMulticaster, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(MonoPublishMulticaster.class, Subscription.class, "s");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<MonoPublishMulticaster> WIP = AtomicIntegerFieldUpdater.newUpdater(MonoPublishMulticaster.class, "wip");
        volatile PublishMulticastInner<T>[] subscribers;
        static final AtomicReferenceFieldUpdater<MonoPublishMulticaster, PublishMulticastInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(MonoPublishMulticaster.class, PublishMulticastInner[].class, "subscribers");
        static final PublishMulticastInner[] EMPTY = new PublishMulticastInner[0];
        static final PublishMulticastInner[] TERMINATED = new PublishMulticastInner[0];
        volatile boolean done;
        @Nullable
        T value;
        Throwable error;
        volatile boolean connected;
        final Context context;

        MonoPublishMulticaster(Context ctx) {
            SUBSCRIBERS.lazySet(this, EMPTY);
            this.context = ctx;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 1;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.value != null ? 1 : 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        @Override
        public Context currentContext() {
            return this.context;
        }

        @Override
        public void subscribe(CoreSubscriber<? super T> actual) {
            PublishMulticastInner<? super T> pcs = new PublishMulticastInner<T>(this, actual);
            actual.onSubscribe(pcs);
            if (this.add(pcs)) {
                if (pcs.cancelled == 1) {
                    this.remove(pcs);
                    return;
                }
                this.drain();
            } else {
                Throwable ex = this.error;
                if (ex != null) {
                    actual.onError(ex);
                } else {
                    actual.onComplete();
                }
            }
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                this.connected = true;
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.context);
                return;
            }
            this.value = t;
            this.done = true;
            this.drain();
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.context);
                return;
            }
            this.error = t;
            this.done = true;
            this.drain();
        }

        public void onComplete() {
            this.done = true;
            this.drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            do {
                if (!this.connected) continue;
                if (this.s == Operators.cancelledSubscription()) {
                    this.value = null;
                    return;
                }
                T v = this.value;
                PublishMulticastInner<T>[] a = this.subscribers;
                int n = a.length;
                if (n == 0) continue;
                if (this.s == Operators.cancelledSubscription()) {
                    this.value = null;
                    return;
                }
                PublishMulticastInner[] castedArray = SUBSCRIBERS.getAndSet(this, TERMINATED);
                a = castedArray;
                n = a.length;
                Throwable ex = this.error;
                if (ex != null) {
                    for (int i = 0; i < n; ++i) {
                        a[i].actual.onError(ex);
                    }
                } else if (v == null) {
                    for (int i = 0; i < n; ++i) {
                        a[i].actual.onComplete();
                    }
                } else {
                    for (int i = 0; i < n; ++i) {
                        a[i].actual.onNext(v);
                        a[i].actual.onComplete();
                    }
                    this.value = null;
                }
                return;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        boolean add(PublishMulticastInner<T> s) {
            PublishMulticastInner[] b;
            PublishMulticastInner<T>[] a;
            do {
                if ((a = this.subscribers) == TERMINATED) {
                    return false;
                }
                int n = a.length;
                b = new PublishMulticastInner[n + 1];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = s;
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
            return true;
        }

        void remove(PublishMulticastInner<T> s) {
            PublishMulticastInner[] b;
            PublishMulticastInner<T>[] a;
            do {
                if ((a = this.subscribers) == TERMINATED || a == EMPTY) {
                    return;
                }
                int n = a.length;
                int j = -1;
                for (int i = 0; i < n; ++i) {
                    if (a[i] != s) continue;
                    j = i;
                    break;
                }
                if (j < 0) {
                    return;
                }
                if (n == 1) {
                    b = EMPTY;
                    continue;
                }
                b = new PublishMulticastInner[n - 1];
                System.arraycopy(a, 0, b, 0, j);
                System.arraycopy(a, j + 1, b, j, n - j - 1);
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        }

        @Override
        public void terminate() {
            Operators.terminate(S, this);
            if (WIP.getAndIncrement(this) == 0 && this.connected) {
                this.value = null;
            }
        }
    }
}


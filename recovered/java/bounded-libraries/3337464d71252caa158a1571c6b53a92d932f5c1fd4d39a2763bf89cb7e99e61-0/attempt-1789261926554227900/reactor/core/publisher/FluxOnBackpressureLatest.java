/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxOnBackpressureLatest<T>
extends InternalFluxOperator<T, T> {
    FluxOnBackpressureLatest(Flux<? extends T> source) {
        super(source);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new LatestSubscriber<T>(actual);
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class LatestSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile long requested;
        static final AtomicLongFieldUpdater<LatestSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(LatestSubscriber.class, "requested");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<LatestSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(LatestSubscriber.class, "wip");
        Subscription s;
        Throwable error;
        volatile boolean done;
        volatile boolean cancelled;
        volatile T value;
        static final AtomicReferenceFieldUpdater<LatestSubscriber, Object> VALUE = AtomicReferenceFieldUpdater.newUpdater(LatestSubscriber.class, Object.class, "value");

        LatestSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = actual;
            this.ctx = actual.currentContext();
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                this.drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                Object toDiscard;
                this.cancelled = true;
                this.s.cancel();
                if (WIP.getAndIncrement(this) == 0 && (toDiscard = VALUE.getAndSet(this, null)) != null) {
                    Operators.onDiscard(toDiscard, this.ctx);
                }
            }
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
            Object toDiscard = VALUE.getAndSet(this, t);
            if (toDiscard != null) {
                Operators.onDiscard(toDiscard, this.ctx);
            }
            this.drain();
        }

        public void onError(Throwable t) {
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
            CoreSubscriber<? super T> a = this.actual;
            int missed = 1;
            do {
                long e;
                if (this.checkTerminated(this.done, this.value == null, a)) {
                    return;
                }
                long r = this.requested;
                for (e = 0L; r != e; ++e) {
                    boolean empty;
                    boolean d = this.done;
                    Object v = VALUE.getAndSet(this, null);
                    boolean bl = empty = v == null;
                    if (this.checkTerminated(d, empty, a)) {
                        return;
                    }
                    if (empty) break;
                    a.onNext(v);
                }
                if (r == e && this.checkTerminated(this.done, this.value == null, a)) {
                    return;
                }
                if (e == 0L || r == Long.MAX_VALUE) continue;
                Operators.produced(REQUESTED, this, e);
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        boolean checkTerminated(boolean d, boolean empty, Subscriber<? super T> a) {
            if (this.cancelled) {
                Object toDiscard = VALUE.getAndSet(this, null);
                if (toDiscard != null) {
                    Operators.onDiscard(toDiscard, this.ctx);
                }
                return true;
            }
            if (d) {
                Throwable e = this.error;
                if (e != null) {
                    Object toDiscard = VALUE.getAndSet(this, null);
                    if (toDiscard != null) {
                        Operators.onDiscard(toDiscard, this.ctx);
                    }
                    a.onError(e);
                    return true;
                }
                if (empty) {
                    a.onComplete();
                    return true;
                }
            }
            return false;
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
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.value != null ? 1 : 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
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


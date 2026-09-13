/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxSample<T, U>
extends InternalFluxOperator<T, T> {
    final Publisher<U> other;

    FluxSample(Flux<? extends T> source, Publisher<U> other) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        CoreSubscriber<? super T> serial = Operators.serialize(actual);
        SampleMainSubscriber<? super T> main = new SampleMainSubscriber<T>(serial);
        actual.onSubscribe(main);
        this.other.subscribe(new SampleOther(main));
        return main;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SampleOther<T, U>
    implements InnerConsumer<U> {
        final SampleMainSubscriber<T> main;

        SampleOther(SampleMainSubscriber<T> main) {
            this.main = main;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.main.other;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.main.other == Operators.cancelledSubscription();
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
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.main.setOther(s);
        }

        public void onNext(U t) {
            SampleMainSubscriber<T> m = this.main;
            T v = m.getAndNullValue();
            if (v != null) {
                if (m.requested != 0L) {
                    m.actual.onNext(v);
                    if (m.requested != Long.MAX_VALUE) {
                        m.decrement();
                    }
                    return;
                }
                m.cancel();
                m.actual.onError(Exceptions.failWithOverflow("Can't signal value due to lack of requests"));
                Operators.onDiscard(v, m.ctx);
            }
        }

        public void onError(Throwable t) {
            SampleMainSubscriber<T> m = this.main;
            m.cancelMain();
            m.actual.onError(t);
        }

        public void onComplete() {
            SampleMainSubscriber<T> m = this.main;
            m.cancelMain();
            m.actual.onComplete();
        }
    }

    static final class SampleMainSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile T value;
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Object> VALUE = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Object.class, "value");
        volatile Subscription main;
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Subscription.class, "main");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<SampleMainSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(SampleMainSubscriber.class, Subscription.class, "other");
        volatile long requested;
        static final AtomicLongFieldUpdater<SampleMainSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(SampleMainSubscriber.class, "requested");

        SampleMainSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = actual;
            this.ctx = actual.currentContext();
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.main == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.value != null ? 1 : 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (!MAIN.compareAndSet(this, null, s)) {
                s.cancel();
                if (this.main != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
                return;
            }
            s.request(Long.MAX_VALUE);
        }

        void cancelMain() {
            Subscription s = this.main;
            if (s != Operators.cancelledSubscription() && (s = MAIN.getAndSet(this, Operators.cancelledSubscription())) != null && s != Operators.cancelledSubscription()) {
                s.cancel();
            }
        }

        void cancelOther() {
            Subscription s = this.other;
            if (s != Operators.cancelledSubscription() && (s = OTHER.getAndSet(this, Operators.cancelledSubscription())) != null && s != Operators.cancelledSubscription()) {
                s.cancel();
            }
        }

        void setOther(Subscription s) {
            if (!OTHER.compareAndSet(this, null, s)) {
                s.cancel();
                if (this.other != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
                return;
            }
            s.request(Long.MAX_VALUE);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        public void cancel() {
            this.cancelMain();
            this.cancelOther();
        }

        public void onNext(T t) {
            Object old = VALUE.getAndSet(this, t);
            if (old != null) {
                Operators.onDiscard(old, this.ctx);
            }
        }

        public void onError(Throwable t) {
            this.cancelOther();
            this.actual.onError(t);
        }

        public void onComplete() {
            this.cancelOther();
            T v = this.value;
            if (v != null) {
                this.actual.onNext(this.value);
            }
            this.actual.onComplete();
        }

        @Nullable
        T getAndNullValue() {
            return VALUE.getAndSet(this, null);
        }

        void decrement() {
            REQUESTED.decrementAndGet(this);
        }
    }
}


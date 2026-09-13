/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxTakeUntilOther<T, U>
extends InternalFluxOperator<T, T> {
    final Publisher<U> other;

    FluxTakeUntilOther(Flux<? extends T> source, Publisher<U> other) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        TakeUntilMainSubscriber<? super T> mainSubscriber = new TakeUntilMainSubscriber<T>(actual);
        TakeUntilOtherSubscriber otherSubscriber = new TakeUntilOtherSubscriber(mainSubscriber);
        this.other.subscribe(otherSubscriber);
        return mainSubscriber;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TakeUntilMainSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        volatile Subscription main;
        static final AtomicReferenceFieldUpdater<TakeUntilMainSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(TakeUntilMainSubscriber.class, Subscription.class, "main");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<TakeUntilMainSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(TakeUntilMainSubscriber.class, Subscription.class, "other");

        TakeUntilMainSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = Operators.serialize(actual);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.main == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
        }

        void setOther(Subscription s) {
            if (!OTHER.compareAndSet(this, null, s)) {
                s.cancel();
                if (this.other != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
            }
        }

        public void request(long n) {
            this.main.request(n);
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

        public void cancel() {
            this.cancelMain();
            this.cancelOther();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (!MAIN.compareAndSet(this, null, s)) {
                s.cancel();
                if (this.main != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
            } else {
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                Operators.error(this.actual, t);
                return;
            }
            this.cancel();
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                this.cancelOther();
                Operators.complete(this.actual);
                return;
            }
            this.cancel();
            this.actual.onComplete();
        }
    }

    static final class TakeUntilOtherSubscriber<U>
    implements InnerConsumer<U> {
        final TakeUntilMainSubscriber<?> main;
        boolean once;

        TakeUntilOtherSubscriber(TakeUntilMainSubscriber<?> main) {
            this.main = main;
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.main.other == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.main.other;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.main.setOther(s);
            s.request(Long.MAX_VALUE);
        }

        public void onNext(U t) {
            this.onComplete();
        }

        public void onError(Throwable t) {
            if (this.once) {
                return;
            }
            this.once = true;
            this.main.onError(t);
        }

        public void onComplete() {
            if (this.once) {
                return;
            }
            this.once = true;
            this.main.onComplete();
        }
    }
}


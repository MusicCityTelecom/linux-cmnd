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
import java.util.function.BiFunction;
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

final class FluxWithLatestFrom<T, U, R>
extends InternalFluxOperator<T, R> {
    final Publisher<? extends U> other;
    final BiFunction<? super T, ? super U, ? extends R> combiner;

    FluxWithLatestFrom(Flux<? extends T> source, Publisher<? extends U> other, BiFunction<? super T, ? super U, ? extends R> combiner) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
        this.combiner = Objects.requireNonNull(combiner, "combiner");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        CoreSubscriber<? super R> serial = Operators.serialize(actual);
        WithLatestFromSubscriber<? super T, ? super U, ? extends R> main = new WithLatestFromSubscriber<T, U, R>(serial, this.combiner);
        WithLatestFromOtherSubscriber<? super U> secondary = new WithLatestFromOtherSubscriber<U>(main);
        this.other.subscribe(secondary);
        return main;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class WithLatestFromOtherSubscriber<U>
    implements InnerConsumer<U> {
        final WithLatestFromSubscriber<?, U, ?> main;

        WithLatestFromOtherSubscriber(WithLatestFromSubscriber<?, U, ?> main) {
            this.main = main;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.main.setOther(s);
            s.request(Long.MAX_VALUE);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
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

        public void onNext(U t) {
            this.main.otherValue = t;
        }

        public void onError(Throwable t) {
            this.main.otherError(t);
        }

        public void onComplete() {
            this.main.otherComplete();
        }
    }

    static final class WithLatestFromSubscriber<T, U, R>
    implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        final BiFunction<? super T, ? super U, ? extends R> combiner;
        volatile Subscription main;
        static final AtomicReferenceFieldUpdater<WithLatestFromSubscriber, Subscription> MAIN = AtomicReferenceFieldUpdater.newUpdater(WithLatestFromSubscriber.class, Subscription.class, "main");
        volatile Subscription other;
        static final AtomicReferenceFieldUpdater<WithLatestFromSubscriber, Subscription> OTHER = AtomicReferenceFieldUpdater.newUpdater(WithLatestFromSubscriber.class, Subscription.class, "other");
        volatile U otherValue;

        WithLatestFromSubscriber(CoreSubscriber<? super R> actual, BiFunction<? super T, ? super U, ? extends R> combiner) {
            this.actual = actual;
            this.combiner = combiner;
        }

        void setOther(Subscription s) {
            if (!OTHER.compareAndSet(this, null, s)) {
                s.cancel();
                if (this.other != Operators.cancelledSubscription()) {
                    Operators.reportSubscriptionSet();
                }
            }
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.main == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.other));
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
            U u = this.otherValue;
            if (u != null) {
                R r;
                try {
                    r = Objects.requireNonNull(this.combiner.apply(t, u), "The combiner returned a null value");
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this, e, t, this.actual.currentContext()));
                    return;
                }
                this.actual.onNext(r);
            } else {
                this.main.request(1L);
            }
        }

        public void onError(Throwable t) {
            if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                this.cancelOther();
                Operators.error(this.actual, t);
                return;
            }
            this.cancelOther();
            this.otherValue = null;
            this.actual.onError(t);
        }

        public void onComplete() {
            this.cancelOther();
            this.otherValue = null;
            this.actual.onComplete();
        }

        void otherError(Throwable t) {
            if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                this.cancelMain();
                Operators.error(this.actual, t);
                return;
            }
            this.cancelMain();
            this.otherValue = null;
            this.actual.onError(t);
        }

        void otherComplete() {
            if (this.otherValue == null) {
                if (this.main == null && MAIN.compareAndSet(this, null, Operators.cancelledSubscription())) {
                    this.cancelMain();
                    Operators.complete(this.actual);
                    return;
                }
                this.cancelMain();
                this.actual.onComplete();
            }
        }
    }
}


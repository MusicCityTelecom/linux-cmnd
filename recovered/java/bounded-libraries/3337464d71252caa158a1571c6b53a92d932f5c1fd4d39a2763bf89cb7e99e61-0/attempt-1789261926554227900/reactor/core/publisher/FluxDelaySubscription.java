/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
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

final class FluxDelaySubscription<T, U>
extends InternalFluxOperator<T, T>
implements Consumer<DelaySubscriptionOtherSubscriber<T, U>> {
    final Publisher<U> other;

    FluxDelaySubscription(Flux<? extends T> source, Publisher<U> other) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        this.other.subscribe(new DelaySubscriptionOtherSubscriber(actual, this));
        return null;
    }

    @Override
    public void accept(DelaySubscriptionOtherSubscriber<T, U> s) {
        this.source.subscribe(new DelaySubscriptionMainSubscriber(s.actual, s));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DelaySubscriptionMainSubscriber<T>
    implements InnerConsumer<T> {
        final CoreSubscriber<? super T> actual;
        final DelaySubscriptionOtherSubscriber<?, ?> arbiter;

        DelaySubscriptionMainSubscriber(CoreSubscriber<? super T> actual, DelaySubscriptionOtherSubscriber<?, ?> arbiter) {
            this.actual = actual;
            this.arbiter = arbiter;
        }

        @Override
        public Context currentContext() {
            return this.arbiter.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.arbiter.set(s);
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }

    static final class DelaySubscriptionOtherSubscriber<T, U>
    extends Operators.DeferredSubscription
    implements InnerOperator<U, T> {
        final Consumer<DelaySubscriptionOtherSubscriber<T, U>> source;
        final CoreSubscriber<? super T> actual;
        Subscription s;
        boolean done;

        DelaySubscriptionOtherSubscriber(CoreSubscriber<? super T> actual, Consumer<DelaySubscriptionOtherSubscriber<T, U>> source) {
            this.actual = actual;
            this.source = source;
        }

        @Override
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void cancel() {
            this.s.cancel();
            super.cancel();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(U t) {
            if (this.done) {
                return;
            }
            this.done = true;
            this.s.cancel();
            this.source.accept(this);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.source.accept(this);
        }
    }
}


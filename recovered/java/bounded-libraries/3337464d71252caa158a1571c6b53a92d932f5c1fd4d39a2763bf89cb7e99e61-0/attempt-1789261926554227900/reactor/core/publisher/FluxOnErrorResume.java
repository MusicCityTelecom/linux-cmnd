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
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;

final class FluxOnErrorResume<T>
extends InternalFluxOperator<T, T> {
    final Function<? super Throwable, ? extends Publisher<? extends T>> nextFactory;

    FluxOnErrorResume(Flux<? extends T> source, Function<? super Throwable, ? extends Publisher<? extends T>> nextFactory) {
        super(source);
        this.nextFactory = Objects.requireNonNull(nextFactory, "nextFactory");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new ResumeSubscriber<T>(actual, this.nextFactory);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ResumeSubscriber<T>
    extends Operators.MultiSubscriptionSubscriber<T, T> {
        final Function<? super Throwable, ? extends Publisher<? extends T>> nextFactory;
        boolean second;

        ResumeSubscriber(CoreSubscriber<? super T> actual, Function<? super Throwable, ? extends Publisher<? extends T>> nextFactory) {
            super(actual);
            this.nextFactory = nextFactory;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (!this.second) {
                this.actual.onSubscribe(this);
            }
            this.set(s);
        }

        public void onNext(T t) {
            this.actual.onNext(t);
            if (!this.second) {
                this.producedOne();
            }
        }

        @Override
        public void onError(Throwable t) {
            if (!this.second) {
                Publisher<? extends T> p;
                this.second = true;
                try {
                    p = Objects.requireNonNull(this.nextFactory.apply(t), "The nextFactory returned a null Publisher");
                }
                catch (Throwable e) {
                    Throwable _e = Operators.onOperatorError(e, this.actual.currentContext());
                    _e = Exceptions.addSuppressed(_e, t);
                    this.actual.onError(_e);
                    return;
                }
                p.subscribe((Subscriber)this);
            } else {
                this.actual.onError(t);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}


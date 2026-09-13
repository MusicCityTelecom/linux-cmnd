/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Signal;
import reactor.util.annotation.Nullable;

final class FluxDematerialize<T>
extends InternalFluxOperator<Signal<T>, T> {
    FluxDematerialize(Flux<Signal<T>> source) {
        super(source);
    }

    @Override
    public CoreSubscriber<? super Signal<T>> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new DematerializeSubscriber<T>(actual, false);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DematerializeSubscriber<T>
    implements InnerOperator<Signal<T>, T> {
        final CoreSubscriber<? super T> actual;
        final boolean completeAfterOnNext;
        Subscription s;
        boolean done;
        volatile boolean cancelled;

        DematerializeSubscriber(CoreSubscriber<? super T> subscriber, boolean completeAfterOnNext) {
            this.actual = subscriber;
            this.completeAfterOnNext = completeAfterOnNext;
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
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(Signal<T> t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (t.isOnComplete()) {
                this.s.cancel();
                this.onComplete();
            } else if (t.isOnError()) {
                this.s.cancel();
                this.onError(t.getThrowable());
            } else if (t.isOnNext()) {
                this.actual.onNext(t.get());
                if (this.completeAfterOnNext) {
                    this.onComplete();
                }
            }
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
            this.actual.onComplete();
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                this.s.request(n);
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.cancel();
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}


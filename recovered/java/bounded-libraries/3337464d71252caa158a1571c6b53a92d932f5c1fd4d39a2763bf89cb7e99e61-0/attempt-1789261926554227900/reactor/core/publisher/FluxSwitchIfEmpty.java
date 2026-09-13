/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;

final class FluxSwitchIfEmpty<T>
extends InternalFluxOperator<T, T> {
    final Publisher<? extends T> other;

    FluxSwitchIfEmpty(Flux<? extends T> source, Publisher<? extends T> other) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        SwitchIfEmptySubscriber<? extends T> parent = new SwitchIfEmptySubscriber<T>(actual, this.other);
        actual.onSubscribe(parent);
        return parent;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SwitchIfEmptySubscriber<T>
    extends Operators.MultiSubscriptionSubscriber<T, T> {
        final Publisher<? extends T> other;
        boolean once;

        SwitchIfEmptySubscriber(CoreSubscriber<? super T> actual, Publisher<? extends T> other) {
            super(actual);
            this.other = other;
        }

        public void onNext(T t) {
            if (!this.once) {
                this.once = true;
            }
            this.actual.onNext(t);
        }

        @Override
        public void onComplete() {
            if (!this.once) {
                this.once = true;
                this.other.subscribe((Subscriber)this);
            } else {
                this.actual.onComplete();
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


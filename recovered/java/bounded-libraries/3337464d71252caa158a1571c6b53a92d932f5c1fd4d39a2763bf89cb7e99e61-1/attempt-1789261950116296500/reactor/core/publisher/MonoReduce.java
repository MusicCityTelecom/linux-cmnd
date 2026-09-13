/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoReduce<T>
extends MonoFromFluxOperator<T, T>
implements Fuseable {
    final BiFunction<T, T, T> aggregator;

    MonoReduce(Flux<? extends T> source, BiFunction<T, T, T> aggregator) {
        super(source);
        this.aggregator = Objects.requireNonNull(aggregator, "aggregator");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new ReduceSubscriber<T>(actual, this.aggregator);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ReduceSubscriber<T>
    extends Operators.MonoSubscriber<T, T> {
        final BiFunction<T, T, T> aggregator;
        Subscription s;
        boolean done;

        ReduceSubscriber(CoreSubscriber<? super T> actual, BiFunction<T, T, T> aggregator) {
            super(actual);
            this.aggregator = aggregator;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            Object r = this.value;
            if (r == null) {
                this.setValue(t);
            } else {
                try {
                    r = Objects.requireNonNull(this.aggregator.apply(r, t), "The aggregator returned a null value");
                }
                catch (Throwable ex) {
                    this.done = true;
                    Context ctx = this.actual.currentContext();
                    Operators.onDiscard(t, ctx);
                    Operators.onDiscard(this.value, ctx);
                    this.value = null;
                    this.actual.onError(Operators.onOperatorError(this.s, ex, t, this.actual.currentContext()));
                    return;
                }
                this.setValue(r);
            }
        }

        @Override
        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.discard(this.value);
            this.value = null;
            this.actual.onError(t);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            Object r = this.value;
            if (r != null) {
                this.complete(r);
            } else {
                this.actual.onComplete();
            }
        }

        @Override
        public void cancel() {
            super.cancel();
            this.s.cancel();
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoStreamCollector<T, A, R>
extends MonoFromFluxOperator<T, R>
implements Fuseable {
    final Collector<? super T, A, ? extends R> collector;

    MonoStreamCollector(Flux<? extends T> source, Collector<? super T, A, ? extends R> collector) {
        super(source);
        this.collector = Objects.requireNonNull(collector, "collector");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        A container = this.collector.supplier().get();
        BiConsumer<A, ? super T> accumulator = this.collector.accumulator();
        Function<A, ? extends R> finisher = this.collector.finisher();
        return new StreamCollectorSubscriber<T, A, R>(actual, container, accumulator, finisher);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class StreamCollectorSubscriber<T, A, R>
    extends Operators.MonoSubscriber<T, R> {
        final BiConsumer<? super A, ? super T> accumulator;
        final Function<? super A, ? extends R> finisher;
        A container;
        Subscription s;
        boolean done;

        StreamCollectorSubscriber(CoreSubscriber<? super R> actual, A container, BiConsumer<? super A, ? super T> accumulator, Function<? super A, ? extends R> finisher) {
            super(actual);
            this.container = container;
            this.accumulator = accumulator;
            this.finisher = finisher;
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

        protected void discardIntermediateContainer(A a) {
            Context ctx = this.actual.currentContext();
            if (a instanceof Collection) {
                Operators.onDiscardMultiple((Collection)a, ctx);
            } else {
                Operators.onDiscard(a, ctx);
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

        @Override
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                this.accumulator.accept(this.container, t);
            }
            catch (Throwable ex) {
                Context ctx = this.actual.currentContext();
                Operators.onDiscard(t, ctx);
                this.onError(Operators.onOperatorError(this.s, ex, t, ctx));
            }
        }

        @Override
        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.discardIntermediateContainer(this.container);
            this.container = null;
            this.actual.onError(t);
        }

        @Override
        public void onComplete() {
            R r;
            if (this.done) {
                return;
            }
            this.done = true;
            A a = this.container;
            this.container = null;
            try {
                r = this.finisher.apply(a);
            }
            catch (Throwable ex) {
                this.discardIntermediateContainer(a);
                this.actual.onError(Operators.onOperatorError(ex, this.actual.currentContext()));
                return;
            }
            if (r == null) {
                this.actual.onError(Operators.onOperatorError(new NullPointerException("Collector returned null"), this.actual.currentContext()));
                return;
            }
            this.complete(r);
        }

        @Override
        public void cancel() {
            super.cancel();
            this.s.cancel();
            this.discardIntermediateContainer(this.container);
            this.container = null;
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Operators;
import reactor.core.publisher.ParallelFlux;
import reactor.util.annotation.Nullable;

final class ParallelCollect<T, C>
extends ParallelFlux<C>
implements Scannable,
Fuseable {
    final ParallelFlux<? extends T> source;
    final Supplier<? extends C> initialCollection;
    final BiConsumer<? super C, ? super T> collector;

    ParallelCollect(ParallelFlux<? extends T> source, Supplier<? extends C> initialCollection, BiConsumer<? super C, ? super T> collector) {
        this.source = source;
        this.initialCollection = initialCollection;
        this.collector = collector;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void subscribe(CoreSubscriber<? super C>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        for (int i = 0; i < n; ++i) {
            C initialValue;
            try {
                initialValue = Objects.requireNonNull(this.initialCollection.get(), "The initialSupplier returned a null value");
            }
            catch (Throwable ex) {
                this.reportError(subscribers, Operators.onOperatorError(ex, subscribers[i].currentContext()));
                return;
            }
            parents[i] = new ParallelCollectSubscriber<T, C>(subscribers[i], initialValue, this.collector);
        }
        this.source.subscribe(parents);
    }

    void reportError(Subscriber<?>[] subscribers, Throwable ex) {
        for (Subscriber<?> s : subscribers) {
            Operators.error(s, ex);
        }
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }

    static final class ParallelCollectSubscriber<T, C>
    extends Operators.MonoSubscriber<T, C> {
        final BiConsumer<? super C, ? super T> collector;
        C collection;
        Subscription s;
        boolean done;

        ParallelCollectSubscriber(CoreSubscriber<? super C> subscriber, C initialValue, BiConsumer<? super C, ? super T> collector) {
            super(subscriber);
            this.collection = initialValue;
            this.collector = collector;
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
                this.collector.accept(this.collection, t);
            }
            catch (Throwable ex) {
                this.onError(Operators.onOperatorError(this, ex, t, this.actual.currentContext()));
            }
        }

        @Override
        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.collection = null;
            this.actual.onError(t);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            C c = this.collection;
            this.collection = null;
            this.complete(c);
        }

        @Override
        public void cancel() {
            super.cancel();
            this.s.cancel();
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


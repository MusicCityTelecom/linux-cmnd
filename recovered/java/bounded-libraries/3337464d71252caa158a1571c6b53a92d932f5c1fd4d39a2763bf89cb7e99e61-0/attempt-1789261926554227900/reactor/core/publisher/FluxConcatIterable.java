/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;

final class FluxConcatIterable<T>
extends Flux<T>
implements SourceProducer<T> {
    final Iterable<? extends Publisher<? extends T>> iterable;

    FluxConcatIterable(Iterable<? extends Publisher<? extends T>> iterable) {
        this.iterable = Objects.requireNonNull(iterable, "iterable");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Iterator<? extends Publisher<? extends T>> it;
        try {
            it = Objects.requireNonNull(this.iterable.iterator(), "The Iterator returned is null");
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        ConcatIterableSubscriber<T> parent = new ConcatIterableSubscriber<T>(actual, it);
        actual.onSubscribe(parent);
        if (!parent.isCancelled()) {
            parent.onComplete();
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class ConcatIterableSubscriber<T>
    extends Operators.MultiSubscriptionSubscriber<T, T> {
        final Iterator<? extends Publisher<? extends T>> it;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ConcatIterableSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(ConcatIterableSubscriber.class, "wip");
        long produced;

        ConcatIterableSubscriber(CoreSubscriber<? super T> actual, Iterator<? extends Publisher<? extends T>> it) {
            super(actual);
            this.it = it;
        }

        public void onNext(T t) {
            ++this.produced;
            this.actual.onNext(t);
        }

        @Override
        public void onComplete() {
            if (WIP.getAndIncrement(this) == 0) {
                Iterator<Publisher<T>> a = this.it;
                do {
                    Publisher<? extends T> p;
                    boolean b;
                    if (this.isCancelled()) {
                        return;
                    }
                    try {
                        b = a.hasNext();
                    }
                    catch (Throwable e) {
                        this.onError(Operators.onOperatorError(this, e, this.actual.currentContext()));
                        return;
                    }
                    if (this.isCancelled()) {
                        return;
                    }
                    if (!b) {
                        this.actual.onComplete();
                        return;
                    }
                    try {
                        p = Objects.requireNonNull(this.it.next(), "The Publisher returned by the iterator is null");
                    }
                    catch (Throwable e) {
                        this.actual.onError(Operators.onOperatorError(this, e, this.actual.currentContext()));
                        return;
                    }
                    if (this.isCancelled()) {
                        return;
                    }
                    long c = this.produced;
                    if (c != 0L) {
                        this.produced = 0L;
                        this.produced(c);
                    }
                    p.subscribe((Subscriber)this);
                    if (!this.isCancelled()) continue;
                    return;
                } while (WIP.decrementAndGet(this) != 0);
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


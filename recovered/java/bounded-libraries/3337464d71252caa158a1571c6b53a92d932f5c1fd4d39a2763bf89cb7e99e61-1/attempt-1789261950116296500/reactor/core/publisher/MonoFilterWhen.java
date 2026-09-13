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
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

class MonoFilterWhen<T>
extends InternalMonoOperator<T, T> {
    final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;

    MonoFilterWhen(Mono<T> source, Function<? super T, ? extends Publisher<Boolean>> asyncPredicate) {
        super(source);
        this.asyncPredicate = asyncPredicate;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new MonoFilterWhenMain<T>(actual, this.asyncPredicate);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FilterWhenInner
    implements InnerConsumer<Boolean> {
        final MonoFilterWhenMain<?> main;
        final boolean cancelOnNext;
        boolean done;
        volatile Subscription sub;
        static final AtomicReferenceFieldUpdater<FilterWhenInner, Subscription> SUB = AtomicReferenceFieldUpdater.newUpdater(FilterWhenInner.class, Subscription.class, "sub");

        FilterWhenInner(MonoFilterWhenMain<?> main, boolean cancelOnNext) {
            this.main = main;
            this.cancelOnNext = cancelOnNext;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUB, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Boolean t) {
            if (!this.done) {
                if (this.cancelOnNext) {
                    this.sub.cancel();
                }
                this.done = true;
                this.main.innerResult(t);
            }
        }

        public void onError(Throwable t) {
            if (!this.done) {
                this.done = true;
                this.main.innerError(t);
            } else {
                Operators.onErrorDropped(t, this.main.currentContext());
            }
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.main.innerResult(null);
            }
        }

        void cancel() {
            Operators.terminate(SUB, this);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.sub;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.sub == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.done ? 0L : 1L;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class MonoFilterWhenMain<T>
    extends Operators.MonoSubscriber<T, T> {
        final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;
        boolean sourceValued;
        Subscription upstream;
        volatile FilterWhenInner asyncFilter;
        static final AtomicReferenceFieldUpdater<MonoFilterWhenMain, FilterWhenInner> ASYNC_FILTER = AtomicReferenceFieldUpdater.newUpdater(MonoFilterWhenMain.class, FilterWhenInner.class, "asyncFilter");
        static final FilterWhenInner INNER_CANCELLED = new FilterWhenInner(null, false);

        MonoFilterWhenMain(CoreSubscriber<? super T> actual, Function<? super T, ? extends Publisher<Boolean>> asyncPredicate) {
            super(actual);
            this.asyncPredicate = asyncPredicate;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.upstream, s)) {
                this.upstream = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(T t) {
            Publisher<Boolean> p;
            this.sourceValued = true;
            this.setValue(t);
            try {
                p = Objects.requireNonNull(this.asyncPredicate.apply(t), "The asyncPredicate returned a null value");
            }
            catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                super.onError(ex);
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (p instanceof Callable) {
                Boolean u;
                try {
                    u = (Boolean)((Callable)p).call();
                }
                catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    super.onError(ex);
                    Operators.onDiscard(t, this.actual.currentContext());
                    return;
                }
                if (u != null && u.booleanValue()) {
                    this.complete(t);
                } else {
                    this.actual.onComplete();
                    Operators.onDiscard(t, this.actual.currentContext());
                }
            } else {
                FilterWhenInner inner = new FilterWhenInner(this, !(p instanceof Mono));
                if (ASYNC_FILTER.compareAndSet(this, null, inner)) {
                    p.subscribe((Subscriber)inner);
                }
            }
        }

        @Override
        public void onComplete() {
            if (!this.sourceValued) {
                super.onComplete();
            }
        }

        @Override
        public void cancel() {
            if (this.state != 4) {
                super.cancel();
                this.upstream.cancel();
                this.cancelInner();
            }
        }

        void cancelInner() {
            FilterWhenInner a = this.asyncFilter;
            if (a != INNER_CANCELLED && (a = ASYNC_FILTER.getAndSet(this, INNER_CANCELLED)) != null && a != INNER_CANCELLED) {
                a.cancel();
            }
        }

        void innerResult(@Nullable Boolean item) {
            if (item != null && item.booleanValue()) {
                this.complete(this.value);
            } else {
                super.onComplete();
                this.discard(this.value);
            }
        }

        void innerError(Throwable ex) {
            super.onError(ex);
            this.discard(this.value);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.upstream;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.asyncFilter != null ? this.asyncFilter.scanUnsafe(Scannable.Attr.TERMINATED) : super.scanUnsafe(Scannable.Attr.TERMINATED);
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            FilterWhenInner c = this.asyncFilter;
            return c == null ? Stream.empty() : Stream.of(c);
        }
    }
}


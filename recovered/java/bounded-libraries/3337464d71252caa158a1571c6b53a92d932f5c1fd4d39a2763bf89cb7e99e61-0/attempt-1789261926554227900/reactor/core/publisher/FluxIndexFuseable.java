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
import reactor.core.publisher.FluxIndex;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxIndexFuseable<T, I>
extends InternalFluxOperator<T, I>
implements Fuseable {
    private final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

    FluxIndexFuseable(Flux<T> source, BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
        super(source);
        this.indexMapper = FluxIndex.NullSafeIndexMapper.create(Objects.requireNonNull(indexMapper, "indexMapper must be non null"));
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super I> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
            return new IndexFuseableConditionalSubscriber<I, T>(cs, this.indexMapper);
        }
        return new IndexFuseableSubscriber<I, T>(actual, this.indexMapper);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class IndexFuseableConditionalSubscriber<I, T>
    implements InnerOperator<T, I>,
    Fuseable.ConditionalSubscriber<T>,
    Fuseable.QueueSubscription<I> {
        final Fuseable.ConditionalSubscriber<? super I> actual;
        final BiFunction<? super Long, ? super T, ? extends I> indexMapper;
        boolean done;
        long index;
        Fuseable.QueueSubscription<T> s;
        int sourceMode;

        IndexFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super I> cs, BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
            this.actual = cs;
            this.indexMapper = indexMapper;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                Fuseable.QueueSubscription qs;
                this.s = qs = (Fuseable.QueueSubscription)s;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        @Nullable
        public I poll() {
            Object v = this.s.poll();
            if (v != null) {
                long i = this.index;
                I indexed = this.indexMapper.apply((Long)((Long)Long.valueOf(i)), (Long)v);
                this.index = i + 1L;
                return indexed;
            }
            return null;
        }

        @Override
        public boolean tryOnNext(T t) {
            I indexed;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            long i = this.index;
            try {
                indexed = this.indexMapper.apply((Long)((Long)Long.valueOf(i)), (Long)t);
                this.index = i + 1L;
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                return true;
            }
            return this.actual.tryOnNext(indexed);
        }

        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
            } else {
                if (this.done) {
                    Operators.onNextDropped(t, this.actual.currentContext());
                    return;
                }
                long i = this.index;
                try {
                    I indexed = this.indexMapper.apply((Long)((Long)Long.valueOf(i)), (Long)t);
                    this.index = i + 1L;
                    this.actual.onNext(indexed);
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                }
            }
        }

        public void onError(Throwable throwable) {
            if (this.done) {
                Operators.onErrorDropped(throwable, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(throwable);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override
        public CoreSubscriber<? super I> actual() {
            return this.actual;
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m;
            if (this.indexMapper != Flux.TUPLE2_BIFUNCTION && (requestedMode & 4) != 0) {
                return 0;
            }
            this.sourceMode = m = this.s.requestFusion(requestedMode);
            return m;
        }

        @Override
        public int size() {
            return this.s.size();
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
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static final class IndexFuseableSubscriber<I, T>
    implements InnerOperator<T, I>,
    Fuseable.QueueSubscription<I> {
        final CoreSubscriber<? super I> actual;
        final BiFunction<? super Long, ? super T, ? extends I> indexMapper;
        boolean done;
        long index;
        Fuseable.QueueSubscription<T> s;
        int sourceMode;

        IndexFuseableSubscriber(CoreSubscriber<? super I> actual, BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
            this.actual = actual;
            this.indexMapper = indexMapper;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                Fuseable.QueueSubscription qs;
                this.s = qs = (Fuseable.QueueSubscription)s;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        @Nullable
        public I poll() {
            Object v = this.s.poll();
            if (v != null) {
                long i = this.index;
                I indexed = this.indexMapper.apply((Long)((Long)Long.valueOf(i)), (Long)v);
                this.index = i + 1L;
                return indexed;
            }
            return null;
        }

        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
            } else {
                if (this.done) {
                    Operators.onNextDropped(t, this.actual.currentContext());
                    return;
                }
                long i = this.index;
                try {
                    I indexed = this.indexMapper.apply((Long)((Long)Long.valueOf(i)), (Long)t);
                    this.index = i + 1L;
                    this.actual.onNext(indexed);
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
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

        @Override
        public CoreSubscriber<? super I> actual() {
            return this.actual;
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m;
            if (this.indexMapper != Flux.TUPLE2_BIFUNCTION && (requestedMode & 4) != 0) {
                return 0;
            }
            this.sourceMode = m = this.s.requestFusion(requestedMode);
            return m;
        }

        @Override
        public int size() {
            return this.s.size();
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
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}


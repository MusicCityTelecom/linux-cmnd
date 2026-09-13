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
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxIndex<T, I>
extends InternalFluxOperator<T, I> {
    private final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

    FluxIndex(Flux<T> source, BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
        super(source);
        this.indexMapper = NullSafeIndexMapper.create(Objects.requireNonNull(indexMapper, "indexMapper must be non null"));
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super I> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
            return new IndexConditionalSubscriber<T, I>(cs, this.indexMapper);
        }
        return new IndexSubscriber<T, I>(actual, this.indexMapper);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static class NullSafeIndexMapper<T, I>
    implements BiFunction<Long, T, I> {
        private final BiFunction<? super Long, ? super T, ? extends I> indexMapper;

        private NullSafeIndexMapper(BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
            this.indexMapper = indexMapper;
        }

        @Override
        public I apply(Long i, T t) {
            I typedIndex = this.indexMapper.apply((Long)((Long)i), (Long)t);
            if (typedIndex == null) {
                throw new NullPointerException("indexMapper returned a null value at raw index " + i + " for value " + t);
            }
            return typedIndex;
        }

        static <T, I> BiFunction<? super Long, ? super T, ? extends I> create(BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
            if (indexMapper == Flux.TUPLE2_BIFUNCTION) {
                return indexMapper;
            }
            return new NullSafeIndexMapper<T, I>(indexMapper);
        }
    }

    static final class IndexConditionalSubscriber<T, I>
    implements InnerOperator<T, I>,
    Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super I> actual;
        final BiFunction<? super Long, ? super T, ? extends I> indexMapper;
        Subscription s;
        boolean done;
        long index;

        IndexConditionalSubscriber(Fuseable.ConditionalSubscriber<? super I> cs, BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
            this.actual = cs;
            this.indexMapper = indexMapper;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public boolean tryOnNext(T t) {
            I typedIndex;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            long i = this.index;
            try {
                typedIndex = this.indexMapper.apply((Long)((Long)Long.valueOf(i)), (Long)t);
                this.index = i + 1L;
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                return true;
            }
            return this.actual.tryOnNext(typedIndex);
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long i = this.index;
            try {
                I typedIndex = this.indexMapper.apply((Long)((Long)Long.valueOf(i)), (Long)t);
                this.index = i + 1L;
                this.actual.onNext(typedIndex);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
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

    static final class IndexSubscriber<T, I>
    implements InnerOperator<T, I> {
        final CoreSubscriber<? super I> actual;
        final BiFunction<? super Long, ? super T, ? extends I> indexMapper;
        boolean done;
        Subscription s;
        long index = 0L;

        IndexSubscriber(CoreSubscriber<? super I> actual, BiFunction<? super Long, ? super T, ? extends I> indexMapper) {
            this.actual = actual;
            this.indexMapper = indexMapper;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long i = this.index;
            try {
                I typedIndex = this.indexMapper.apply((Long)((Long)Long.valueOf(i)), (Long)t);
                this.index = i + 1L;
                this.actual.onNext(typedIndex);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
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


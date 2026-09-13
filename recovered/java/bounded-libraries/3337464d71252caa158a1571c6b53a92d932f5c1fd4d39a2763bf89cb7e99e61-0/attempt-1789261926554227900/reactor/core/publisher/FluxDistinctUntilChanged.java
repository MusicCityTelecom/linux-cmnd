/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxDistinctUntilChanged<T, K>
extends InternalFluxOperator<T, T> {
    final Function<? super T, K> keyExtractor;
    final BiPredicate<? super K, ? super K> keyComparator;

    FluxDistinctUntilChanged(Flux<? extends T> source, Function<? super T, K> keyExtractor, BiPredicate<? super K, ? super K> keyComparator) {
        super(source);
        this.keyExtractor = Objects.requireNonNull(keyExtractor, "keyExtractor");
        this.keyComparator = Objects.requireNonNull(keyComparator, "keyComparator");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new DistinctUntilChangedConditionalSubscriber<T, K>((Fuseable.ConditionalSubscriber)actual, this.keyExtractor, this.keyComparator);
        }
        return new DistinctUntilChangedSubscriber<T, K>(actual, this.keyExtractor, this.keyComparator);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DistinctUntilChangedConditionalSubscriber<T, K>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Context ctx;
        final Function<? super T, K> keyExtractor;
        final BiPredicate<? super K, ? super K> keyComparator;
        Subscription s;
        boolean done;
        @Nullable
        K lastKey;

        DistinctUntilChangedConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Function<? super T, K> keyExtractor, BiPredicate<? super K, ? super K> keyComparator) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.keyExtractor = keyExtractor;
            this.keyComparator = keyComparator;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.tryOnNext(t)) {
                this.s.request(1L);
            }
        }

        @Override
        public boolean tryOnNext(T t) {
            boolean equiv;
            K k;
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            try {
                k = Objects.requireNonNull(this.keyExtractor.apply(t), "The distinct extractor returned a null value.");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
            if (null == this.lastKey) {
                this.lastKey = k;
                return this.actual.tryOnNext(t);
            }
            try {
                equiv = this.keyComparator.test(this.lastKey, k);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
            if (equiv) {
                Operators.onDiscard(t, this.ctx);
                return false;
            }
            this.lastKey = k;
            return this.actual.tryOnNext(t);
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.ctx);
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onComplete();
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

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
            this.lastKey = null;
        }
    }

    static final class DistinctUntilChangedSubscriber<T, K>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final Function<? super T, K> keyExtractor;
        final BiPredicate<? super K, ? super K> keyComparator;
        Subscription s;
        boolean done;
        @Nullable
        K lastKey;

        DistinctUntilChangedSubscriber(CoreSubscriber<? super T> actual, Function<? super T, K> keyExtractor, BiPredicate<? super K, ? super K> keyComparator) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.keyExtractor = keyExtractor;
            this.keyComparator = keyComparator;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.tryOnNext(t)) {
                this.s.request(1L);
            }
        }

        @Override
        public boolean tryOnNext(T t) {
            boolean equiv;
            K k;
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            try {
                k = Objects.requireNonNull(this.keyExtractor.apply(t), "The distinct extractor returned a null value.");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
            if (null == this.lastKey) {
                this.lastKey = k;
                this.actual.onNext(t);
                return true;
            }
            try {
                equiv = this.keyComparator.test(this.lastKey, k);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                Operators.onDiscard(t, this.ctx);
                return true;
            }
            if (equiv) {
                Operators.onDiscard(t, this.ctx);
                return false;
            }
            this.lastKey = k;
            this.actual.onNext(t);
            return true;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.ctx);
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onComplete();
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

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
            this.lastKey = null;
        }
    }
}


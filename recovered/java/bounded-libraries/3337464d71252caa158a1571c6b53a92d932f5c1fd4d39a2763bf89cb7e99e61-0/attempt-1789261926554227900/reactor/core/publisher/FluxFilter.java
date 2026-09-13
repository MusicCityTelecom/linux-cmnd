/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
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

final class FluxFilter<T>
extends InternalFluxOperator<T, T> {
    final Predicate<? super T> predicate;

    FluxFilter(Flux<? extends T> source, Predicate<? super T> predicate) {
        super(source);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new FilterConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)actual, this.predicate);
        }
        return new FilterSubscriber<T>(actual, this.predicate);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FilterConditionalSubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Context ctx;
        final Predicate<? super T> predicate;
        Subscription s;
        boolean done;

        FilterConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Predicate<? super T> predicate) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.predicate = predicate;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            boolean b;
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            try {
                b = this.predicate.test(t);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, e, this.ctx, this.s);
                if (e_ != null) {
                    this.onError(e_);
                } else {
                    this.s.request(1L);
                }
                Operators.onDiscard(t, this.ctx);
                return;
            }
            if (b) {
                this.actual.onNext(t);
            } else {
                this.s.request(1L);
                Operators.onDiscard(t, this.ctx);
            }
        }

        @Override
        public boolean tryOnNext(T t) {
            boolean b;
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return false;
            }
            try {
                b = this.predicate.test(t);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, e, this.ctx, this.s);
                if (e_ != null) {
                    this.onError(e_);
                }
                Operators.onDiscard(t, this.ctx);
                return false;
            }
            if (b) {
                return this.actual.tryOnNext(t);
            }
            Operators.onDiscard(t, this.ctx);
            return false;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.ctx);
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
        }
    }

    static final class FilterSubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable.ConditionalSubscriber<T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final Predicate<? super T> predicate;
        Subscription s;
        boolean done;

        FilterSubscriber(CoreSubscriber<? super T> actual, Predicate<? super T> predicate) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.predicate = predicate;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            boolean b;
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            try {
                b = this.predicate.test(t);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, e, this.ctx, this.s);
                if (e_ != null) {
                    this.onError(e_);
                } else {
                    this.s.request(1L);
                }
                Operators.onDiscard(t, this.ctx);
                return;
            }
            if (b) {
                this.actual.onNext(t);
            } else {
                Operators.onDiscard(t, this.ctx);
                this.s.request(1L);
            }
        }

        @Override
        public boolean tryOnNext(T t) {
            boolean b;
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return false;
            }
            try {
                b = this.predicate.test(t);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, e, this.ctx, this.s);
                if (e_ != null) {
                    this.onError(e_);
                }
                Operators.onDiscard(t, this.ctx);
                return false;
            }
            if (b) {
                this.actual.onNext(t);
            } else {
                Operators.onDiscard(t, this.ctx);
            }
            return b;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.ctx);
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
        }
    }
}


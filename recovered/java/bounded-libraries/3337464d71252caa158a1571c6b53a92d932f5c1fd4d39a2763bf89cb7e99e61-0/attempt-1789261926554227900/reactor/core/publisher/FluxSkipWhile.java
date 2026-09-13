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

final class FluxSkipWhile<T>
extends InternalFluxOperator<T, T> {
    final Predicate<? super T> predicate;

    FluxSkipWhile(Flux<? extends T> source, Predicate<? super T> predicate) {
        super(source);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new SkipWhileSubscriber<T>(actual, this.predicate);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SkipWhileSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final Predicate<? super T> predicate;
        Subscription s;
        boolean done;
        boolean skipped;

        SkipWhileSubscriber(CoreSubscriber<? super T> actual, Predicate<? super T> predicate) {
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
            if (this.skipped) {
                this.actual.onNext(t);
                return;
            }
            try {
                b = this.predicate.test(t);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                return;
            }
            if (b) {
                Operators.onDiscard(t, this.ctx);
                this.s.request(1L);
                return;
            }
            this.skipped = true;
            this.actual.onNext(t);
        }

        @Override
        public boolean tryOnNext(T t) {
            boolean b;
            if (this.done) {
                Operators.onNextDropped(t, this.ctx);
                return true;
            }
            if (this.skipped) {
                this.actual.onNext(t);
                return true;
            }
            try {
                b = this.predicate.test(t);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                return true;
            }
            if (b) {
                Operators.onDiscard(t, this.ctx);
                return false;
            }
            this.skipped = true;
            this.actual.onNext(t);
            return true;
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


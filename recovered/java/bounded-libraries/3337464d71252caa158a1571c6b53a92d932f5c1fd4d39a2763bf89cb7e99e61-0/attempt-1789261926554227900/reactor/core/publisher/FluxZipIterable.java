/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxZipIterable<T, U, R>
extends InternalFluxOperator<T, R> {
    final Iterable<? extends U> other;
    final BiFunction<? super T, ? super U, ? extends R> zipper;

    FluxZipIterable(Flux<? extends T> source, Iterable<? extends U> other, BiFunction<? super T, ? super U, ? extends R> zipper) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
        this.zipper = Objects.requireNonNull(zipper, "zipper");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        Iterator<U> it = Objects.requireNonNull(this.other.iterator(), "The other iterable produced a null iterator");
        boolean b = it.hasNext();
        if (!b) {
            Operators.complete(actual);
            return null;
        }
        return new ZipSubscriber<T, U, R>(actual, it, this.zipper);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ZipSubscriber<T, U, R>
    implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        final Iterator<? extends U> it;
        final BiFunction<? super T, ? super U, ? extends R> zipper;
        Subscription s;
        boolean done;

        ZipSubscriber(CoreSubscriber<? super R> actual, Iterator<? extends U> it, BiFunction<? super T, ? super U, ? extends R> zipper) {
            this.actual = actual;
            this.it = it;
            this.zipper = zipper;
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
            return InnerOperator.super.scanUnsafe(key);
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
            R r;
            U u;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                u = this.it.next();
            }
            catch (Throwable e) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                return;
            }
            try {
                r = Objects.requireNonNull(this.zipper.apply(t, u), "The zipper returned a null value");
            }
            catch (Throwable e) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                return;
            }
            this.actual.onNext(r);
            try {
                b = this.it.hasNext();
            }
            catch (Throwable e) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                return;
            }
            if (!b) {
                this.done = true;
                this.s.cancel();
                this.actual.onComplete();
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
        public CoreSubscriber<? super R> actual() {
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


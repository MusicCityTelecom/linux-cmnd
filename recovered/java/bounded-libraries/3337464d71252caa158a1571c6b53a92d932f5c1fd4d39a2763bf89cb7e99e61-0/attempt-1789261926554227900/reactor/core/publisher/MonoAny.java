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
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoAny<T>
extends MonoFromFluxOperator<T, Boolean>
implements Fuseable {
    final Predicate<? super T> predicate;

    MonoAny(Flux<? extends T> source, Predicate<? super T> predicate) {
        super(source);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Boolean> actual) {
        return new AnySubscriber<T>(actual, this.predicate);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class AnySubscriber<T>
    extends Operators.MonoSubscriber<T, Boolean> {
        final Predicate<? super T> predicate;
        Subscription s;
        boolean done;

        AnySubscriber(CoreSubscriber<? super Boolean> actual, Predicate<? super T> predicate) {
            super(actual);
            this.predicate = predicate;
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

        @Override
        public void cancel() {
            this.s.cancel();
            super.cancel();
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
            boolean b;
            if (this.done) {
                return;
            }
            try {
                b = this.predicate.test(t);
            }
            catch (Throwable e) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                return;
            }
            if (b) {
                this.done = true;
                this.s.cancel();
                this.complete(true);
            }
        }

        @Override
        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.complete(false);
        }
    }
}


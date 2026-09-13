/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;
import reactor.util.annotation.Nullable;

final class FluxDoFinally<T>
extends InternalFluxOperator<T, T> {
    final Consumer<SignalType> onFinally;

    static <T> CoreSubscriber<T> createSubscriber(CoreSubscriber<? super T> s, Consumer<SignalType> onFinally) {
        if (s instanceof Fuseable.ConditionalSubscriber) {
            return new DoFinallyConditionalSubscriber((Fuseable.ConditionalSubscriber)s, onFinally);
        }
        return new DoFinallySubscriber<T>(s, onFinally);
    }

    FluxDoFinally(Flux<? extends T> source, Consumer<SignalType> onFinally) {
        super(source);
        this.onFinally = onFinally;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return FluxDoFinally.createSubscriber(actual, this.onFinally);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DoFinallyConditionalSubscriber<T>
    extends DoFinallySubscriber<T>
    implements Fuseable.ConditionalSubscriber<T> {
        DoFinallyConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Consumer<SignalType> onFinally) {
            super(actual, onFinally);
        }

        @Override
        public boolean tryOnNext(T t) {
            return ((Fuseable.ConditionalSubscriber)this.actual).tryOnNext(t);
        }
    }

    static class DoFinallySubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Consumer<SignalType> onFinally;
        volatile int once;
        static final AtomicIntegerFieldUpdater<DoFinallySubscriber> ONCE = AtomicIntegerFieldUpdater.newUpdater(DoFinallySubscriber.class, "once");
        Subscription s;

        DoFinallySubscriber(CoreSubscriber<? super T> actual, Consumer<SignalType> onFinally) {
            this.actual = actual;
            this.onFinally = onFinally;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
                return this.once == 1;
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
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            try {
                this.actual.onError(t);
            }
            finally {
                this.runFinally(SignalType.ON_ERROR);
            }
        }

        public void onComplete() {
            this.actual.onComplete();
            this.runFinally(SignalType.ON_COMPLETE);
        }

        public void cancel() {
            this.s.cancel();
            this.runFinally(SignalType.CANCEL);
        }

        public void request(long n) {
            this.s.request(n);
        }

        void runFinally(SignalType signalType) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                try {
                    this.onFinally.accept(signalType);
                }
                catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    Operators.onErrorDropped(ex, this.actual.currentContext());
                }
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}


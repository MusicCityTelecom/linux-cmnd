/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
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
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class FluxDoOnEach<T>
extends InternalFluxOperator<T, T> {
    final Consumer<? super Signal<T>> onSignal;

    FluxDoOnEach(Flux<? extends T> source, Consumer<? super Signal<T>> onSignal) {
        super(source);
        this.onSignal = Objects.requireNonNull(onSignal, "onSignal");
    }

    static <T> DoOnEachSubscriber<T> createSubscriber(CoreSubscriber<? super T> actual, Consumer<? super Signal<T>> onSignal, boolean fuseable, boolean isMono) {
        if (fuseable) {
            if (actual instanceof Fuseable.ConditionalSubscriber) {
                return new DoOnEachFuseableConditionalSubscriber((Fuseable.ConditionalSubscriber)actual, onSignal, isMono);
            }
            return new DoOnEachFuseableSubscriber<T>(actual, onSignal, isMono);
        }
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new DoOnEachConditionalSubscriber((Fuseable.ConditionalSubscriber)actual, onSignal, isMono);
        }
        return new DoOnEachSubscriber<T>(actual, onSignal, isMono);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return FluxDoOnEach.createSubscriber(actual, this.onSignal, false, false);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DoOnEachFuseableConditionalSubscriber<T>
    extends DoOnEachFuseableSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T> {
        DoOnEachFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Consumer<? super Signal<T>> onSignal, boolean isMono) {
            super(actual, onSignal, isMono);
        }

        @Override
        public boolean tryOnNext(T t) {
            boolean result = ((Fuseable.ConditionalSubscriber)this.actual).tryOnNext(t);
            if (result) {
                this.t = t;
                this.onSignal.accept(this);
            }
            return result;
        }
    }

    static final class DoOnEachConditionalSubscriber<T>
    extends DoOnEachSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T> {
        DoOnEachConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Consumer<? super Signal<T>> onSignal, boolean isMono) {
            super(actual, onSignal, isMono);
        }

        @Override
        public boolean tryOnNext(T t) {
            boolean result = ((Fuseable.ConditionalSubscriber)this.actual).tryOnNext(t);
            if (result) {
                this.t = t;
                this.onSignal.accept(this);
            }
            return result;
        }
    }

    static class DoOnEachFuseableSubscriber<T>
    extends DoOnEachSubscriber<T>
    implements Fuseable,
    Fuseable.QueueSubscription<T> {
        int fusionMode;

        DoOnEachFuseableSubscriber(CoreSubscriber<? super T> actual, Consumer<? super Signal<T>> onSignal, boolean isMono) {
            super(actual, onSignal, isMono);
        }

        @Override
        public void onNext(T t) {
            if (this.fusionMode == 2) {
                this.actual.onNext(null);
                return;
            }
            super.onNext(t);
        }

        @Override
        public int requestFusion(int mode) {
            int m;
            Fuseable.QueueSubscription qs = this.qs;
            if (qs != null && (mode & 4) == 0 && ((m = qs.requestFusion(mode)) == 1 || m == 2)) {
                this.fusionMode = m;
                return m;
            }
            this.fusionMode = 0;
            return 0;
        }

        @Override
        public void clear() {
            this.qs.clear();
        }

        @Override
        public boolean isEmpty() {
            return this.qs == null || this.qs.isEmpty();
        }

        @Override
        @Nullable
        public T poll() {
            if (this.qs == null) {
                return null;
            }
            Object v = this.qs.poll();
            if (v == null && this.fusionMode == 1) {
                this.state = (short)3;
                this.onSignal.accept(Signal.complete(this.cachedContext));
            } else if (v != null) {
                this.t = v;
                this.onSignal.accept(this);
            }
            return (T)v;
        }

        @Override
        public int size() {
            return this.qs == null ? 0 : this.qs.size();
        }
    }

    static class DoOnEachSubscriber<T>
    implements InnerOperator<T, T>,
    Signal<T> {
        static final short STATE_FLUX_START = 0;
        static final short STATE_MONO_START = 1;
        static final short STATE_SKIP_HANDLER = 2;
        static final short STATE_DONE = 3;
        final CoreSubscriber<? super T> actual;
        final Context cachedContext;
        final Consumer<? super Signal<T>> onSignal;
        T t;
        Subscription s;
        @Nullable
        Fuseable.QueueSubscription<T> qs;
        short state;

        DoOnEachSubscriber(CoreSubscriber<? super T> actual, Consumer<? super Signal<T>> onSignal, boolean monoFlavor) {
            this.actual = actual;
            this.cachedContext = actual.currentContext();
            this.onSignal = onSignal;
            this.state = monoFlavor ? (short)1 : 0;
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.qs = Operators.as(s);
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public Context currentContext() {
            return this.cachedContext;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.state == 3;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        public void onNext(T t) {
            if (this.state == 3) {
                Operators.onNextDropped(t, this.cachedContext);
                return;
            }
            try {
                this.t = t;
                this.onSignal.accept(this);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.cachedContext));
                return;
            }
            if (this.state == 1) {
                this.state = (short)2;
                try {
                    this.onSignal.accept(Signal.complete(this.cachedContext));
                }
                catch (Throwable e) {
                    this.state = 1;
                    this.onError(Operators.onOperatorError(this.s, e, this.cachedContext));
                    return;
                }
            }
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            block6: {
                if (this.state == 3) {
                    Operators.onErrorDropped(t, this.cachedContext);
                    return;
                }
                boolean applyHandler = this.state < 2;
                this.state = (short)3;
                if (applyHandler) {
                    try {
                        this.onSignal.accept(Signal.error(t, this.cachedContext));
                    }
                    catch (Throwable e) {
                        t = Operators.onOperatorError(null, e, t, this.cachedContext);
                    }
                }
                try {
                    this.actual.onError(t);
                }
                catch (UnsupportedOperationException use) {
                    if (Exceptions.isErrorCallbackNotImplemented(use) || use.getCause() == t) break block6;
                    throw use;
                }
            }
        }

        public void onComplete() {
            if (this.state == 3) {
                return;
            }
            short oldState = this.state;
            this.state = (short)3;
            if (oldState < 2) {
                try {
                    this.onSignal.accept(Signal.complete(this.cachedContext));
                }
                catch (Throwable e) {
                    this.state = oldState;
                    this.onError(Operators.onOperatorError(this.s, e, this.cachedContext));
                    return;
                }
            }
            this.actual.onComplete();
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Throwable getThrowable() {
            return null;
        }

        @Override
        @Nullable
        public Subscription getSubscription() {
            return null;
        }

        @Override
        @Nullable
        public T get() {
            return this.t;
        }

        @Override
        public ContextView getContextView() {
            return this.cachedContext;
        }

        @Override
        public SignalType getType() {
            return SignalType.ON_NEXT;
        }

        public String toString() {
            return "doOnEach_onNext(" + this.t + ")";
        }
    }
}


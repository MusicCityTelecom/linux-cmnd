/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalPeek;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxPeek<T>
extends InternalFluxOperator<T, T>
implements SignalPeek<T> {
    final Consumer<? super Subscription> onSubscribeCall;
    final Consumer<? super T> onNextCall;
    final Consumer<? super Throwable> onErrorCall;
    final Runnable onCompleteCall;
    final Runnable onAfterTerminateCall;
    final LongConsumer onRequestCall;
    final Runnable onCancelCall;

    FluxPeek(Flux<? extends T> source, @Nullable Consumer<? super Subscription> onSubscribeCall, @Nullable Consumer<? super T> onNextCall, @Nullable Consumer<? super Throwable> onErrorCall, @Nullable Runnable onCompleteCall, @Nullable Runnable onAfterTerminateCall, @Nullable LongConsumer onRequestCall, @Nullable Runnable onCancelCall) {
        super(source);
        this.onSubscribeCall = onSubscribeCall;
        this.onNextCall = onNextCall;
        this.onErrorCall = onErrorCall;
        this.onCompleteCall = onCompleteCall;
        this.onAfterTerminateCall = onAfterTerminateCall;
        this.onRequestCall = onRequestCall;
        this.onCancelCall = onCancelCall;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber s2 = (Fuseable.ConditionalSubscriber)actual;
            return new FluxPeekFuseable.PeekConditionalSubscriber(s2, this);
        }
        return new PeekSubscriber<T>(actual, this);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    @Nullable
    public Consumer<? super Subscription> onSubscribeCall() {
        return this.onSubscribeCall;
    }

    @Override
    @Nullable
    public Consumer<? super T> onNextCall() {
        return this.onNextCall;
    }

    @Override
    @Nullable
    public Consumer<? super Throwable> onErrorCall() {
        return this.onErrorCall;
    }

    @Override
    @Nullable
    public Runnable onCompleteCall() {
        return this.onCompleteCall;
    }

    @Override
    @Nullable
    public Runnable onAfterTerminateCall() {
        return this.onAfterTerminateCall;
    }

    @Override
    @Nullable
    public LongConsumer onRequestCall() {
        return this.onRequestCall;
    }

    @Override
    @Nullable
    public Runnable onCancelCall() {
        return this.onCancelCall;
    }

    static <T> void afterCompleteWithFailure(SignalPeek<T> parent, Throwable callbackFailure, Context context) {
        Exceptions.throwIfFatal(callbackFailure);
        Throwable _e = Operators.onOperatorError(callbackFailure, context);
        Operators.onErrorDropped(_e, context);
    }

    static <T> void afterErrorWithFailure(SignalPeek<T> parent, Throwable callbackFailure, Throwable originalError, Context context) {
        Exceptions.throwIfFatal(callbackFailure);
        Throwable _e = Operators.onOperatorError(null, callbackFailure, originalError, context);
        Operators.onErrorDropped(_e, context);
    }

    static final class PeekSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final SignalPeek<T> parent;
        Subscription s;
        boolean done;

        PeekSubscriber(CoreSubscriber<? super T> actual, SignalPeek<T> parent) {
            this.actual = actual;
            this.parent = parent;
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
        public Context currentContext() {
            Context c = this.actual.currentContext();
            if (!c.isEmpty() && this.parent.onCurrentContextCall() != null) {
                this.parent.onCurrentContextCall().accept(c);
            }
            return c;
        }

        public void request(long n) {
            LongConsumer requestHook = this.parent.onRequestCall();
            if (requestHook != null) {
                try {
                    requestHook.accept(n);
                }
                catch (Throwable e) {
                    Operators.onOperatorError(e, this.actual.currentContext());
                }
            }
            this.s.request(n);
        }

        public void cancel() {
            Runnable cancelHook = this.parent.onCancelCall();
            if (cancelHook != null) {
                try {
                    cancelHook.run();
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                    return;
                }
            }
            this.s.cancel();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                Consumer<Subscription> subscribeHook = this.parent.onSubscribeCall();
                if (subscribeHook != null) {
                    try {
                        subscribeHook.accept(s);
                    }
                    catch (Throwable e) {
                        Operators.error(this.actual, Operators.onOperatorError(s, e, this.actual.currentContext()));
                        return;
                    }
                }
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            Consumer<T> nextHook = this.parent.onNextCall();
            if (nextHook != null) {
                try {
                    nextHook.accept(t);
                }
                catch (Throwable e) {
                    Throwable e_ = Operators.onNextError(t, e, this.actual.currentContext(), this.s);
                    if (e_ == null) {
                        this.request(1L);
                        return;
                    }
                    this.onError(e_);
                    return;
                }
            }
            this.actual.onNext(t);
        }

        public void onError(Throwable t) {
            block9: {
                if (this.done) {
                    Operators.onErrorDropped(t, this.actual.currentContext());
                    return;
                }
                this.done = true;
                Consumer<Throwable> errorHook = this.parent.onErrorCall();
                if (errorHook != null) {
                    try {
                        errorHook.accept(t);
                    }
                    catch (Throwable e) {
                        t = Operators.onOperatorError(null, e, t, this.actual.currentContext());
                    }
                }
                try {
                    this.actual.onError(t);
                }
                catch (UnsupportedOperationException use) {
                    if (errorHook != null && (Exceptions.isErrorCallbackNotImplemented(use) || use.getCause() == t)) break block9;
                    throw use;
                }
            }
            Runnable afterTerminateHook = this.parent.onAfterTerminateCall();
            if (afterTerminateHook != null) {
                try {
                    afterTerminateHook.run();
                }
                catch (Throwable e) {
                    FluxPeek.afterErrorWithFailure(this.parent, e, t, this.actual.currentContext());
                }
            }
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            Runnable completeHook = this.parent.onCompleteCall();
            if (completeHook != null) {
                try {
                    completeHook.run();
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                    return;
                }
            }
            this.done = true;
            this.actual.onComplete();
            Runnable afterTerminateHook = this.parent.onAfterTerminateCall();
            if (afterTerminateHook != null) {
                try {
                    afterTerminateHook.run();
                }
                catch (Throwable e) {
                    FluxPeek.afterCompleteWithFailure(this.parent, e, this.actual.currentContext());
                }
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}


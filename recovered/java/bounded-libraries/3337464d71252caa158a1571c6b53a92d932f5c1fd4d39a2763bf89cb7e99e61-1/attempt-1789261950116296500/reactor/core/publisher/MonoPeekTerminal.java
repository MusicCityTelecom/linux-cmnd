/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoPeekTerminal<T>
extends InternalMonoOperator<T, T>
implements Fuseable {
    final BiConsumer<? super T, Throwable> onAfterTerminateCall;
    final Consumer<? super T> onSuccessCall;
    final Consumer<? super Throwable> onErrorCall;

    MonoPeekTerminal(Mono<? extends T> source, @Nullable Consumer<? super T> onSuccessCall, @Nullable Consumer<? super Throwable> onErrorCall, @Nullable BiConsumer<? super T, Throwable> onAfterTerminateCall) {
        super(source);
        this.onAfterTerminateCall = onAfterTerminateCall;
        this.onSuccessCall = onSuccessCall;
        this.onErrorCall = onErrorCall;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new MonoTerminalPeekSubscriber((Fuseable.ConditionalSubscriber)actual, this);
        }
        return new MonoTerminalPeekSubscriber<T>(actual, this);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class MonoTerminalPeekSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;
        final MonoPeekTerminal<T> parent;
        Subscription s;
        @Nullable
        Fuseable.QueueSubscription<T> queueSubscription;
        int sourceMode;
        volatile boolean done;
        boolean valued;

        MonoTerminalPeekSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, MonoPeekTerminal<T> parent) {
            this.actualConditional = actual;
            this.actual = actual;
            this.parent = parent;
        }

        MonoTerminalPeekSubscriber(CoreSubscriber<? super T> actual, MonoPeekTerminal<T> parent) {
            this.actual = actual;
            this.actualConditional = null;
            this.parent = parent;
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

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.s = s;
            this.queueSubscription = Operators.as(s);
            this.actual.onSubscribe(this);
        }

        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
            } else {
                if (this.done) {
                    Operators.onNextDropped(t, this.actual.currentContext());
                    return;
                }
                this.valued = true;
                if (this.parent.onSuccessCall != null) {
                    try {
                        this.parent.onSuccessCall.accept(t);
                    }
                    catch (Throwable e) {
                        this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                        return;
                    }
                }
                this.actual.onNext(t);
                if (this.parent.onAfterTerminateCall != null) {
                    try {
                        this.parent.onAfterTerminateCall.accept(t, null);
                    }
                    catch (Throwable e) {
                        Operators.onErrorDropped(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()), this.actual.currentContext());
                    }
                }
            }
        }

        @Override
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return false;
            }
            if (this.actualConditional == null) {
                this.onNext(t);
                return false;
            }
            this.valued = true;
            if (this.parent.onSuccessCall != null) {
                try {
                    this.parent.onSuccessCall.accept(t);
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                    return false;
                }
            }
            boolean r = this.actualConditional.tryOnNext(t);
            if (this.parent.onAfterTerminateCall != null) {
                try {
                    this.parent.onAfterTerminateCall.accept(t, null);
                }
                catch (Throwable e) {
                    Operators.onErrorDropped(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()), this.actual.currentContext());
                }
            }
            return r;
        }

        public void onError(Throwable t) {
            block9: {
                if (this.done) {
                    Operators.onErrorDropped(t, this.actual.currentContext());
                    return;
                }
                this.done = true;
                Consumer<? super Throwable> onError = this.parent.onErrorCall;
                if (!this.valued && onError != null) {
                    try {
                        onError.accept(t);
                    }
                    catch (Throwable e) {
                        t = Operators.onOperatorError(null, e, t, this.actual.currentContext());
                    }
                }
                try {
                    this.actual.onError(t);
                }
                catch (UnsupportedOperationException use) {
                    if (onError != null && (Exceptions.isErrorCallbackNotImplemented(use) || use.getCause() == t)) break block9;
                    throw use;
                }
            }
            if (!this.valued && this.parent.onAfterTerminateCall != null) {
                try {
                    this.parent.onAfterTerminateCall.accept(null, t);
                }
                catch (Throwable e) {
                    Operators.onErrorDropped(Operators.onOperatorError(e, this.actual.currentContext()), this.actual.currentContext());
                }
            }
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            if (this.sourceMode == 0 && !this.valued && this.parent.onSuccessCall != null) {
                try {
                    this.parent.onSuccessCall.accept(null);
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                    return;
                }
            }
            this.done = true;
            this.actual.onComplete();
            if (this.sourceMode == 0 && !this.valued && this.parent.onAfterTerminateCall != null) {
                try {
                    this.parent.onAfterTerminateCall.accept(null, null);
                }
                catch (Throwable e) {
                    Operators.onErrorDropped(Operators.onOperatorError(e, this.actual.currentContext()), this.actual.currentContext());
                }
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public T poll() {
            Object v;
            assert (this.queueSubscription != null);
            boolean d = this.done;
            try {
                v = this.queueSubscription.poll();
            }
            catch (Throwable pe) {
                if (this.parent.onErrorCall != null) {
                    try {
                        this.parent.onErrorCall.accept(pe);
                    }
                    catch (Throwable t) {
                        t = Operators.onOperatorError(null, pe, t, this.actual.currentContext());
                        throw Exceptions.propagate(t);
                    }
                }
                throw pe;
            }
            if (!this.valued && (v != null || d || this.sourceMode == 1)) {
                this.valued = true;
                if (this.parent.onSuccessCall != null) {
                    try {
                        this.parent.onSuccessCall.accept(v);
                    }
                    catch (Throwable e) {
                        throw Exceptions.propagate(Operators.onOperatorError(this.s, e, v, this.actual.currentContext()));
                    }
                }
            }
            return (T)v;
        }

        @Override
        public boolean isEmpty() {
            return this.queueSubscription == null || this.queueSubscription.isEmpty();
        }

        @Override
        public void clear() {
            assert (this.queueSubscription != null);
            this.queueSubscription.clear();
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m = this.queueSubscription == null || this.parent.onAfterTerminateCall != null ? 0 : ((requestedMode & 4) != 0 ? 0 : this.queueSubscription.requestFusion(requestedMode));
            this.sourceMode = m;
            return m;
        }

        @Override
        public int size() {
            return this.queueSubscription == null ? 0 : this.queueSubscription.size();
        }
    }
}


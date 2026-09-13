/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SynchronousSink;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class FluxHandle<T, R>
extends InternalFluxOperator<T, R> {
    final BiConsumer<? super T, SynchronousSink<R>> handler;

    FluxHandle(Flux<? extends T> source, BiConsumer<? super T, SynchronousSink<R>> handler) {
        super(source);
        this.handler = Objects.requireNonNull(handler, "handler");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
            return new HandleConditionalSubscriber<T, R>(cs, this.handler);
        }
        return new HandleSubscriber<T, R>(actual, this.handler);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class HandleConditionalSubscriber<T, R>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, R>,
    SynchronousSink<R> {
        final Fuseable.ConditionalSubscriber<? super R> actual;
        final BiConsumer<? super T, SynchronousSink<R>> handler;
        boolean done;
        boolean stop;
        Throwable error;
        R data;
        Subscription s;

        HandleConditionalSubscriber(Fuseable.ConditionalSubscriber<? super R> actual, BiConsumer<? super T, SynchronousSink<R>> handler) {
            this.actual = actual;
            this.handler = handler;
        }

        @Override
        @Deprecated
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        public ContextView contextView() {
            return this.actual.currentContext();
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
            try {
                this.handler.accept(t, this);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, e, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.onError(e_);
                } else {
                    this.error = null;
                    this.s.request(1L);
                }
                return;
            }
            R v = this.data;
            this.data = null;
            if (v != null) {
                this.actual.onNext(v);
            }
            if (this.stop) {
                this.done = true;
                if (this.error != null) {
                    Throwable e_ = Operators.onNextError(t, this.error, this.actual.currentContext(), this.s);
                    if (e_ != null) {
                        this.actual.onError(e_);
                    } else {
                        this.reset();
                        this.s.request(1L);
                    }
                } else {
                    this.s.cancel();
                    this.actual.onComplete();
                }
            } else if (v == null) {
                this.s.request(1L);
            }
        }

        private void reset() {
            this.done = false;
            this.stop = false;
            this.error = null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return false;
            }
            try {
                this.handler.accept(t, this);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, e, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.onError(e_);
                    return true;
                }
                this.reset();
                return false;
            }
            R v = this.data;
            boolean emit = false;
            this.data = null;
            if (v != null) {
                emit = this.actual.tryOnNext(v);
            }
            if (!this.stop) return emit;
            this.done = true;
            if (this.error == null) {
                this.s.cancel();
                this.actual.onComplete();
                return true;
            }
            Throwable e_ = Operators.onNextError(t, this.error, this.actual.currentContext(), this.s);
            if (e_ != null) {
                this.actual.onError(e_);
                return true;
            }
            this.reset();
            return false;
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

        @Override
        public void complete() {
            if (this.stop) {
                throw new IllegalStateException("Cannot complete after a complete or error");
            }
            this.stop = true;
        }

        @Override
        public void error(Throwable e) {
            if (this.stop) {
                throw new IllegalStateException("Cannot error after a complete or error");
            }
            this.error = Objects.requireNonNull(e, "error");
            this.stop = true;
        }

        @Override
        public void next(R o) {
            if (this.data != null) {
                throw new IllegalStateException("Cannot emit more than one data");
            }
            if (this.stop) {
                throw new IllegalStateException("Cannot emit after a complete or error");
            }
            this.data = Objects.requireNonNull(o, "data");
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
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static final class HandleSubscriber<T, R>
    implements InnerOperator<T, R>,
    Fuseable.ConditionalSubscriber<T>,
    SynchronousSink<R> {
        final CoreSubscriber<? super R> actual;
        final BiConsumer<? super T, SynchronousSink<R>> handler;
        boolean done;
        boolean stop;
        Throwable error;
        R data;
        Subscription s;

        HandleSubscriber(CoreSubscriber<? super R> actual, BiConsumer<? super T, SynchronousSink<R>> handler) {
            this.actual = actual;
            this.handler = handler;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        @Deprecated
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        public ContextView contextView() {
            return this.actual.currentContext();
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            try {
                this.handler.accept(t, this);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, e, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.onError(e_);
                } else {
                    this.reset();
                    this.s.request(1L);
                }
                return;
            }
            R v = this.data;
            this.data = null;
            if (v != null) {
                this.actual.onNext(v);
            }
            if (this.stop) {
                if (this.error != null) {
                    Throwable e_ = Operators.onNextError(t, this.error, this.actual.currentContext(), this.s);
                    if (e_ != null) {
                        this.onError(e_);
                    } else {
                        this.reset();
                        this.s.request(1L);
                    }
                } else {
                    this.s.cancel();
                    this.onComplete();
                }
            } else if (v == null) {
                this.s.request(1L);
            }
        }

        private void reset() {
            this.done = false;
            this.stop = false;
            this.error = null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return false;
            }
            try {
                this.handler.accept(t, this);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, e, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.onError(e_);
                    return true;
                }
                this.reset();
                return false;
            }
            R v = this.data;
            this.data = null;
            if (v != null) {
                this.actual.onNext(v);
            }
            if (this.stop) {
                if (this.error == null) {
                    this.s.cancel();
                    this.onComplete();
                    return true;
                }
                Throwable e_ = Operators.onNextError(t, this.error, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.onError(e_);
                    return true;
                }
                this.reset();
                return false;
            }
            if (v == null) return false;
            return true;
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
        public void complete() {
            if (this.stop) {
                throw new IllegalStateException("Cannot complete after a complete or error");
            }
            this.stop = true;
        }

        @Override
        public void error(Throwable e) {
            if (this.stop) {
                throw new IllegalStateException("Cannot error after a complete or error");
            }
            this.error = Objects.requireNonNull(e, "error");
            this.stop = true;
        }

        @Override
        public void next(R o) {
            if (this.data != null) {
                throw new IllegalStateException("Cannot emit more than one data");
            }
            if (this.stop) {
                throw new IllegalStateException("Cannot emit after a complete or error");
            }
            this.data = Objects.requireNonNull(o, "data");
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
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
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


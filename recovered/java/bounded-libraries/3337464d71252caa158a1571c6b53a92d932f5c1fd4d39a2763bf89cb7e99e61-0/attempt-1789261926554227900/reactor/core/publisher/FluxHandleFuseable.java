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
import reactor.core.Exceptions;
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

final class FluxHandleFuseable<T, R>
extends InternalFluxOperator<T, R>
implements Fuseable {
    final BiConsumer<? super T, SynchronousSink<R>> handler;

    FluxHandleFuseable(Flux<? extends T> source, BiConsumer<? super T, SynchronousSink<R>> handler) {
        super(source);
        this.handler = Objects.requireNonNull(handler, "handler");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
            return new HandleFuseableConditionalSubscriber<T, R>(cs, this.handler);
        }
        return new HandleFuseableSubscriber<T, R>(actual, this.handler);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class HandleFuseableConditionalSubscriber<T, R>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, R>,
    Fuseable.QueueSubscription<R>,
    SynchronousSink<R> {
        final Fuseable.ConditionalSubscriber<? super R> actual;
        final BiConsumer<? super T, SynchronousSink<R>> handler;
        boolean done;
        boolean stop;
        Throwable error;
        R data;
        Fuseable.QueueSubscription<T> s;
        int sourceMode;

        HandleFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super R> actual, BiConsumer<? super T, SynchronousSink<R>> handler) {
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
                this.s = (Fuseable.QueueSubscription)s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
            } else {
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
                            this.done = true;
                            this.actual.onError(e_);
                        } else {
                            this.reset();
                            this.s.request(1L);
                        }
                    } else {
                        this.done = true;
                        this.s.cancel();
                        this.actual.onComplete();
                    }
                } else if (v == null) {
                    this.s.request(1L);
                }
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
                return true;
            }
            try {
                this.handler.accept(t, this);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t, this.error, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.onError(e_);
                    return true;
                }
                this.reset();
                return false;
            }
            R v = this.data;
            this.data = null;
            boolean emit = false;
            if (v != null) {
                emit = this.actual.tryOnNext(v);
            }
            if (!this.stop) return emit;
            if (this.error == null) {
                this.done = true;
                this.s.cancel();
                this.actual.onComplete();
                return true;
            }
            Throwable e_ = Operators.onNextError(t, this.error, this.actual.currentContext(), this.s);
            if (e_ != null) {
                this.done = true;
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

        @Override
        @Nullable
        public R poll() {
            Object v;
            if (this.sourceMode == 2) {
                if (this.done) {
                    return null;
                }
                long dropped = 0L;
                while (true) {
                    Object v2;
                    if ((v2 = this.s.poll()) != null) {
                        try {
                            this.handler.accept(v2, this);
                        }
                        catch (Throwable error) {
                            RuntimeException e_ = Operators.onNextPollError(v2, error, this.actual.currentContext());
                            if (e_ != null) {
                                throw e_;
                            }
                            this.reset();
                            continue;
                        }
                        R u = this.data;
                        this.data = null;
                        if (this.stop) {
                            if (this.error != null) {
                                Throwable e_ = Operators.onNextError(v2, this.error, this.actual.currentContext(), this.s);
                                if (e_ != null) {
                                    this.done = true;
                                    throw Exceptions.propagate(e_);
                                }
                                this.reset();
                                continue;
                            }
                            this.done = true;
                            this.s.cancel();
                            this.actual.onComplete();
                            return u;
                        }
                        if (u != null) {
                            return u;
                        }
                        ++dropped;
                        continue;
                    }
                    if (dropped == 0L) break;
                    this.request(dropped);
                    dropped = 0L;
                }
                return null;
            }
            while ((v = this.s.poll()) != null) {
                RuntimeException e_;
                try {
                    this.handler.accept(v, this);
                }
                catch (Throwable error) {
                    e_ = Operators.onNextPollError(v, error, this.actual.currentContext());
                    if (e_ != null) {
                        throw e_;
                    }
                    this.reset();
                    continue;
                }
                R u = this.data;
                this.data = null;
                if (this.stop) {
                    this.done = true;
                    if (this.error != null) {
                        e_ = Operators.onNextPollError(v, this.error, this.actual.currentContext());
                        if (e_ != null) {
                            throw e_;
                        }
                        this.reset();
                        continue;
                    }
                    return u;
                }
                if (u == null) continue;
                return u;
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m;
            if ((requestedMode & 4) != 0) {
                return 0;
            }
            this.sourceMode = m = this.s.requestFusion(requestedMode);
            return m;
        }

        @Override
        public int size() {
            return this.s.size();
        }
    }

    static final class HandleFuseableSubscriber<T, R>
    implements InnerOperator<T, R>,
    Fuseable.ConditionalSubscriber<T>,
    Fuseable.QueueSubscription<R>,
    SynchronousSink<R> {
        final CoreSubscriber<? super R> actual;
        final BiConsumer<? super T, SynchronousSink<R>> handler;
        boolean done;
        boolean stop;
        Throwable error;
        R data;
        Fuseable.QueueSubscription<T> s;
        int sourceMode;

        HandleFuseableSubscriber(CoreSubscriber<? super R> actual, BiConsumer<? super T, SynchronousSink<R>> handler) {
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

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
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
                    this.done = true;
                    this.s.cancel();
                    this.actual.onComplete();
                    return true;
                }
                Throwable e_ = Operators.onNextError(t, this.error, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.done = true;
                    this.actual.onError(e_);
                    return true;
                }
                this.reset();
                return false;
            }
            if (v == null) return false;
            return true;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = (Fuseable.QueueSubscription)s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
            } else {
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
                            this.done = true;
                            this.actual.onError(e_);
                        } else {
                            this.reset();
                            this.s.request(1L);
                        }
                    } else {
                        this.done = true;
                        this.s.cancel();
                        this.actual.onComplete();
                    }
                } else if (v == null) {
                    this.s.request(1L);
                }
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

        @Override
        @Nullable
        public R poll() {
            Object v;
            if (this.sourceMode == 2) {
                if (this.done) {
                    return null;
                }
                long dropped = 0L;
                while (true) {
                    Object v2;
                    if ((v2 = this.s.poll()) != null) {
                        try {
                            this.handler.accept(v2, this);
                        }
                        catch (Throwable error) {
                            RuntimeException e_ = Operators.onNextPollError(v2, error, this.actual.currentContext());
                            if (e_ != null) {
                                throw e_;
                            }
                            this.reset();
                            continue;
                        }
                        R u = this.data;
                        this.data = null;
                        if (this.stop) {
                            if (this.error != null) {
                                RuntimeException e_ = Operators.onNextPollError(v2, this.error, this.actual.currentContext());
                                if (e_ != null) {
                                    this.done = true;
                                    throw e_;
                                }
                            } else {
                                this.done = true;
                                this.s.cancel();
                                this.actual.onComplete();
                            }
                            return u;
                        }
                        if (u != null) {
                            return u;
                        }
                        ++dropped;
                        continue;
                    }
                    if (dropped == 0L) break;
                    this.request(dropped);
                    dropped = 0L;
                }
                return null;
            }
            while ((v = this.s.poll()) != null) {
                RuntimeException e_;
                try {
                    this.handler.accept(v, this);
                }
                catch (Throwable error) {
                    e_ = Operators.onNextPollError(v, error, this.actual.currentContext());
                    if (e_ != null) {
                        throw e_;
                    }
                    this.reset();
                    continue;
                }
                R u = this.data;
                this.data = null;
                if (this.stop) {
                    if (this.error != null) {
                        e_ = Operators.onNextPollError(v, this.error, this.actual.currentContext());
                        if (e_ != null) {
                            this.done = true;
                            throw e_;
                        }
                        this.reset();
                        continue;
                    }
                    this.done = true;
                    return u;
                }
                if (u == null) continue;
                return u;
            }
            return null;
        }

        private void reset() {
            this.done = false;
            this.stop = false;
            this.error = null;
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m;
            if ((requestedMode & 4) != 0) {
                return 0;
            }
            this.sourceMode = m = this.s.requestFusion(requestedMode);
            return m;
        }

        @Override
        public int size() {
            return this.s.size();
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
    }
}


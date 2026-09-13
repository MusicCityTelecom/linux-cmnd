/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Scannable;
import reactor.core.publisher.FluxCreate;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class MonoCreate<T>
extends Mono<T>
implements SourceProducer<T> {
    static final Disposable TERMINATED = OperatorDisposables.DISPOSED;
    static final Disposable CANCELLED = Disposables.disposed();
    final Consumer<MonoSink<T>> callback;

    MonoCreate(Consumer<MonoSink<T>> callback) {
        this.callback = callback;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        DefaultMonoSink<T> emitter = new DefaultMonoSink<T>(actual);
        actual.onSubscribe(emitter);
        try {
            this.callback.accept(emitter);
        }
        catch (Throwable ex) {
            emitter.error(Operators.onOperatorError(ex, actual.currentContext()));
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return null;
    }

    static final class DefaultMonoSink<T>
    extends AtomicBoolean
    implements MonoSink<T>,
    InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        volatile Disposable disposable;
        static final AtomicReferenceFieldUpdater<DefaultMonoSink, Disposable> DISPOSABLE = AtomicReferenceFieldUpdater.newUpdater(DefaultMonoSink.class, Disposable.class, "disposable");
        volatile int state;
        static final AtomicIntegerFieldUpdater<DefaultMonoSink> STATE = AtomicIntegerFieldUpdater.newUpdater(DefaultMonoSink.class, "state");
        volatile LongConsumer requestConsumer;
        static final AtomicReferenceFieldUpdater<DefaultMonoSink, LongConsumer> REQUEST_CONSUMER = AtomicReferenceFieldUpdater.newUpdater(DefaultMonoSink.class, LongConsumer.class, "requestConsumer");
        T value;
        static final int NO_REQUEST_HAS_VALUE = 1;
        static final int HAS_REQUEST_NO_VALUE = 2;
        static final int HAS_REQUEST_HAS_VALUE = 3;

        DefaultMonoSink(CoreSubscriber<? super T> actual) {
            this.actual = actual;
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
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.state == 3 || this.state == 1 || this.disposable == TERMINATED;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.disposable == CANCELLED;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public void success() {
            if (this.isDisposed()) {
                return;
            }
            if (STATE.getAndSet(this, 3) != 3) {
                try {
                    this.actual.onComplete();
                }
                finally {
                    this.disposeResource(false);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void success(@Nullable T value) {
            int s;
            if (value == null) {
                this.success();
                return;
            }
            Disposable d = this.disposable;
            if (d == CANCELLED) {
                Operators.onDiscard(value, this.actual.currentContext());
                return;
            }
            if (d == TERMINATED) {
                Operators.onNextDropped(value, this.actual.currentContext());
                return;
            }
            do {
                if ((s = this.state) == 3 || s == 1) {
                    Operators.onNextDropped(value, this.actual.currentContext());
                    return;
                }
                if (s == 2) {
                    if (STATE.compareAndSet(this, s, 3)) {
                        try {
                            this.actual.onNext(value);
                            this.actual.onComplete();
                        }
                        catch (Throwable t) {
                            this.actual.onError(t);
                        }
                        finally {
                            this.disposeResource(false);
                        }
                    } else {
                        Operators.onNextDropped(value, this.actual.currentContext());
                    }
                    return;
                }
                this.value = value;
            } while (!STATE.compareAndSet(this, s, 1));
        }

        @Override
        public void error(Throwable e) {
            if (this.isDisposed()) {
                Operators.onOperatorError(e, this.actual.currentContext());
                return;
            }
            if (STATE.getAndSet(this, 3) != 3) {
                try {
                    this.actual.onError(e);
                }
                finally {
                    this.disposeResource(false);
                }
            } else {
                Operators.onOperatorError(e, this.actual.currentContext());
            }
        }

        @Override
        public MonoSink<T> onRequest(LongConsumer consumer) {
            Objects.requireNonNull(consumer, "onRequest");
            if (!REQUEST_CONSUMER.compareAndSet(this, null, consumer)) {
                throw new IllegalStateException("A consumer has already been assigned to consume requests");
            }
            int s = this.state;
            if (s == 2 || s == 3) {
                consumer.accept(Long.MAX_VALUE);
            }
            return this;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public MonoSink<T> onCancel(Disposable d) {
            Objects.requireNonNull(d, "onCancel");
            FluxCreate.SinkDisposable sd = new FluxCreate.SinkDisposable(null, d);
            if (!DISPOSABLE.compareAndSet(this, null, sd)) {
                Disposable c = this.disposable;
                if (c == CANCELLED) {
                    d.dispose();
                } else if (c instanceof FluxCreate.SinkDisposable) {
                    FluxCreate.SinkDisposable current = (FluxCreate.SinkDisposable)c;
                    if (current.onCancel == null) {
                        current.onCancel = d;
                    } else {
                        d.dispose();
                    }
                }
            }
            return this;
        }

        @Override
        public MonoSink<T> onDispose(Disposable d) {
            Objects.requireNonNull(d, "onDispose");
            FluxCreate.SinkDisposable sd = new FluxCreate.SinkDisposable(d, null);
            if (!DISPOSABLE.compareAndSet(this, null, sd)) {
                Disposable c = this.disposable;
                if (this.isDisposed()) {
                    d.dispose();
                } else if (c instanceof FluxCreate.SinkDisposable) {
                    FluxCreate.SinkDisposable current = (FluxCreate.SinkDisposable)c;
                    if (current.disposable == null) {
                        current.disposable = d;
                    } else {
                        d.dispose();
                    }
                }
            }
            return this;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void request(long n) {
            if (Operators.validate(n)) {
                int s;
                LongConsumer consumer = this.requestConsumer;
                if (consumer != null) {
                    consumer.accept(n);
                }
                do {
                    if ((s = this.state) == 2 || s == 3) {
                        return;
                    }
                    if (s != 1) continue;
                    if (STATE.compareAndSet(this, s, 3)) {
                        try {
                            this.actual.onNext(this.value);
                            this.actual.onComplete();
                        }
                        finally {
                            this.disposeResource(false);
                        }
                    }
                    return;
                } while (!STATE.compareAndSet(this, s, 2));
                return;
            }
        }

        public void cancel() {
            if (STATE.getAndSet(this, 3) != 3) {
                T old = this.value;
                this.value = null;
                Operators.onDiscard(old, this.actual.currentContext());
                this.disposeResource(true);
            }
        }

        void disposeResource(boolean isCancel) {
            Disposable target = isCancel ? CANCELLED : TERMINATED;
            Disposable d = this.disposable;
            if (d != TERMINATED && d != CANCELLED && (d = DISPOSABLE.getAndSet(this, target)) != null && d != TERMINATED && d != CANCELLED) {
                if (isCancel && d instanceof FluxCreate.SinkDisposable) {
                    ((FluxCreate.SinkDisposable)d).cancel();
                }
                d.dispose();
            }
        }

        @Override
        public String toString() {
            return "MonoSink";
        }

        boolean isDisposed() {
            Disposable d = this.disposable;
            return d == CANCELLED || d == TERMINATED;
        }
    }
}


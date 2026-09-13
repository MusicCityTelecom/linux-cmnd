/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxInterval;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxBufferBoundary<T, U, C extends Collection<? super T>>
extends InternalFluxOperator<T, C> {
    final Publisher<U> other;
    final Supplier<C> bufferSupplier;

    FluxBufferBoundary(Flux<? extends T> source, Publisher<U> other, Supplier<C> bufferSupplier) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
        this.bufferSupplier = Objects.requireNonNull(bufferSupplier, "bufferSupplier");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super C> actual) {
        Collection buffer = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
        BufferBoundaryMain parent = new BufferBoundaryMain(this.source instanceof FluxInterval ? actual : Operators.serialize(actual), buffer, this.bufferSupplier);
        actual.onSubscribe(parent);
        this.other.subscribe(parent.other);
        return parent;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class BufferBoundaryOther<U>
    extends Operators.DeferredSubscription
    implements InnerConsumer<U> {
        final BufferBoundaryMain<?, U, ?> main;

        BufferBoundaryOther(BufferBoundaryMain<?, U, ?> main) {
            this.main = main;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (this.set(s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        @Override
        public Context currentContext() {
            return this.main.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        public void onNext(U t) {
            this.main.otherNext();
        }

        public void onError(Throwable t) {
            this.main.otherError(t);
        }

        public void onComplete() {
            this.main.otherComplete();
        }
    }

    static final class BufferBoundaryMain<T, U, C extends Collection<? super T>>
    implements InnerOperator<T, C> {
        final Supplier<C> bufferSupplier;
        final CoreSubscriber<? super C> actual;
        final Context ctx;
        final BufferBoundaryOther<U> other;
        C buffer;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<BufferBoundaryMain, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(BufferBoundaryMain.class, Subscription.class, "s");
        volatile long requested;
        static final AtomicLongFieldUpdater<BufferBoundaryMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferBoundaryMain.class, "requested");

        BufferBoundaryMain(CoreSubscriber<? super C> actual, C buffer, Supplier<C> bufferSupplier) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.buffer = buffer;
            this.bufferSupplier = bufferSupplier;
            this.other = new BufferBoundaryOther(this);
        }

        @Override
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.CAPACITY) {
                C buffer = this.buffer;
                return buffer != null ? buffer.size() : 0;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        public void cancel() {
            Operators.terminate(S, this);
            Operators.onDiscardMultiple(this.buffer, this.ctx);
            this.other.cancel();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onNext(T t) {
            BufferBoundaryMain bufferBoundaryMain = this;
            synchronized (bufferBoundaryMain) {
                C b = this.buffer;
                if (b != null) {
                    b.add(t);
                    return;
                }
            }
            Operators.onNextDropped(t, this.ctx);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onError(Throwable t) {
            if (Operators.terminate(S, this)) {
                C b;
                BufferBoundaryMain bufferBoundaryMain = this;
                synchronized (bufferBoundaryMain) {
                    b = this.buffer;
                    this.buffer = null;
                }
                this.other.cancel();
                this.actual.onError(t);
                Operators.onDiscardMultiple(b, this.ctx);
                return;
            }
            Operators.onErrorDropped(t, this.ctx);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onComplete() {
            if (Operators.terminate(S, this)) {
                C b;
                BufferBoundaryMain bufferBoundaryMain = this;
                synchronized (bufferBoundaryMain) {
                    b = this.buffer;
                    this.buffer = null;
                }
                this.other.cancel();
                if (!b.isEmpty()) {
                    if (this.emit(b)) {
                        this.actual.onComplete();
                    }
                } else {
                    this.actual.onComplete();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void otherComplete() {
            Subscription s = S.getAndSet(this, Operators.cancelledSubscription());
            if (s != Operators.cancelledSubscription()) {
                C b;
                BufferBoundaryMain bufferBoundaryMain = this;
                synchronized (bufferBoundaryMain) {
                    b = this.buffer;
                    this.buffer = null;
                }
                if (s != null) {
                    s.cancel();
                }
                if (b != null && !b.isEmpty()) {
                    if (this.emit(b)) {
                        this.actual.onComplete();
                    }
                } else {
                    this.actual.onComplete();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void otherError(Throwable t) {
            Subscription s = S.getAndSet(this, Operators.cancelledSubscription());
            if (s != Operators.cancelledSubscription()) {
                C b;
                BufferBoundaryMain bufferBoundaryMain = this;
                synchronized (bufferBoundaryMain) {
                    b = this.buffer;
                    this.buffer = null;
                }
                if (s != null) {
                    s.cancel();
                }
                this.actual.onError(t);
                Operators.onDiscardMultiple(b, this.ctx);
                return;
            }
            Operators.onErrorDropped(t, this.ctx);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void otherNext() {
            C b;
            Collection c;
            try {
                c = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
            }
            catch (Throwable e) {
                this.otherError(Operators.onOperatorError(this.other, e, this.ctx));
                return;
            }
            BufferBoundaryMain bufferBoundaryMain = this;
            synchronized (bufferBoundaryMain) {
                b = this.buffer;
                this.buffer = c;
            }
            if (b == null || b.isEmpty()) {
                return;
            }
            this.emit(b);
        }

        boolean emit(C b) {
            long r = this.requested;
            if (r != 0L) {
                this.actual.onNext(b);
                if (r != Long.MAX_VALUE) {
                    REQUESTED.decrementAndGet(this);
                }
                return true;
            }
            this.actual.onError(Operators.onOperatorError(this, Exceptions.failWithOverflow(), b, this.ctx));
            Operators.onDiscardMultiple(b, this.ctx);
            return false;
        }
    }
}


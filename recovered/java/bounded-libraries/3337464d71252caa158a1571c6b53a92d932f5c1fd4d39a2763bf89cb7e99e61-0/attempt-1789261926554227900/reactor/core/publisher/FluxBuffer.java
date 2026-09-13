/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.DrainUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxBuffer<T, C extends Collection<? super T>>
extends InternalFluxOperator<T, C> {
    final int size;
    final int skip;
    final Supplier<C> bufferSupplier;

    FluxBuffer(Flux<? extends T> source, int size, Supplier<C> bufferSupplier) {
        this(source, size, size, bufferSupplier);
    }

    FluxBuffer(Flux<? extends T> source, int size, int skip, Supplier<C> bufferSupplier) {
        super(source);
        if (size <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + size);
        }
        if (skip <= 0) {
            throw new IllegalArgumentException("skip > 0 required but it was " + skip);
        }
        this.size = size;
        this.skip = skip;
        this.bufferSupplier = Objects.requireNonNull(bufferSupplier, "bufferSupplier");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super C> actual) {
        if (this.size == this.skip) {
            return new BufferExactSubscriber(actual, this.size, this.bufferSupplier);
        }
        if (this.skip > this.size) {
            return new BufferSkipSubscriber(actual, this.size, this.skip, this.bufferSupplier);
        }
        return new BufferOverlappingSubscriber(actual, this.size, this.skip, this.bufferSupplier);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class BufferOverlappingSubscriber<T, C extends Collection<? super T>>
    extends ArrayDeque<C>
    implements BooleanSupplier,
    InnerOperator<T, C> {
        final CoreSubscriber<? super C> actual;
        final Supplier<C> bufferSupplier;
        final int size;
        final int skip;
        Subscription s;
        boolean done;
        long index;
        volatile boolean cancelled;
        long produced;
        volatile int once;
        static final AtomicIntegerFieldUpdater<BufferOverlappingSubscriber> ONCE = AtomicIntegerFieldUpdater.newUpdater(BufferOverlappingSubscriber.class, "once");
        volatile long requested;
        static final AtomicLongFieldUpdater<BufferOverlappingSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferOverlappingSubscriber.class, "requested");

        BufferOverlappingSubscriber(CoreSubscriber<? super C> actual, int size, int skip, Supplier<C> bufferSupplier) {
            this.actual = actual;
            this.size = size;
            this.skip = skip;
            this.bufferSupplier = bufferSupplier;
        }

        @Override
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        public void request(long n) {
            if (!Operators.validate(n)) {
                return;
            }
            if (DrainUtils.postCompleteRequest(n, this.actual, this, REQUESTED, this, this)) {
                return;
            }
            if (this.once == 0 && ONCE.compareAndSet(this, 0, 1)) {
                long u = Operators.multiplyCap(this.skip, n - 1L);
                long r = Operators.addCap(this.size, u);
                this.s.request(r);
            } else {
                long r = Operators.multiplyCap(this.skip, n);
                this.s.request(r);
            }
        }

        public void cancel() {
            this.cancelled = true;
            this.s.cancel();
            this.clear();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            Collection b;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            long i = this.index;
            if (i % (long)this.skip == 0L) {
                try {
                    b = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                }
                catch (Throwable e) {
                    Context ctx = this.actual.currentContext();
                    this.onError(Operators.onOperatorError(this.s, e, t, ctx));
                    Operators.onDiscard(t, ctx);
                    return;
                }
                this.offer(b);
            }
            if ((b = (Collection)this.peek()) != null && b.size() + 1 == this.size) {
                this.poll();
                b.add(t);
                this.actual.onNext(b);
                ++this.produced;
            }
            for (Collection b0 : this) {
                b0.add(t);
            }
            this.index = i + 1L;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.clear();
            this.actual.onError(t);
        }

        @Override
        public void clear() {
            Context ctx = this.actual.currentContext();
            for (Collection b : this) {
                Operators.onDiscardMultiple(b, ctx);
            }
            super.clear();
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long p = this.produced;
            if (p != 0L) {
                Operators.produced(REQUESTED, this, p);
            }
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
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
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.size() * this.size;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.stream().mapToInt(Collection::size).sum();
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
    }

    static final class BufferSkipSubscriber<T, C extends Collection<? super T>>
    implements InnerOperator<T, C> {
        final CoreSubscriber<? super C> actual;
        final Context ctx;
        final Supplier<C> bufferSupplier;
        final int size;
        final int skip;
        C buffer;
        Subscription s;
        boolean done;
        long index;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<BufferSkipSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(BufferSkipSubscriber.class, "wip");

        BufferSkipSubscriber(CoreSubscriber<? super C> actual, int size, int skip, Supplier<C> bufferSupplier) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.size = size;
            this.skip = skip;
            this.bufferSupplier = bufferSupplier;
        }

        public void request(long n) {
            if (!Operators.validate(n)) {
                return;
            }
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                long u = Operators.multiplyCap(n, this.size);
                long v = Operators.multiplyCap(this.skip - this.size, n - 1L);
                this.s.request(Operators.addCap(u, v));
            } else {
                this.s.request(Operators.multiplyCap(this.skip, n));
            }
        }

        public void cancel() {
            this.s.cancel();
            Operators.onDiscardMultiple(this.buffer, this.ctx);
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
                Operators.onNextDropped(t, this.ctx);
                return;
            }
            Object b = this.buffer;
            long i = this.index;
            if (i % (long)this.skip == 0L) {
                try {
                    b = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, t, this.ctx));
                    Operators.onDiscard(t, this.ctx);
                    return;
                }
                this.buffer = b;
            }
            if (b != null) {
                b.add(t);
                if (b.size() == this.size) {
                    this.buffer = null;
                    this.actual.onNext(b);
                }
            } else {
                Operators.onDiscard(t, this.ctx);
            }
            this.index = i + 1L;
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.ctx);
                return;
            }
            this.done = true;
            C b = this.buffer;
            this.buffer = null;
            this.actual.onError(t);
            Operators.onDiscardMultiple(b, this.ctx);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            C b = this.buffer;
            this.buffer = null;
            if (b != null) {
                this.actual.onNext(b);
            }
            this.actual.onComplete();
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
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.size;
            }
            if (key == Scannable.Attr.BUFFERED) {
                C b = this.buffer;
                return b != null ? b.size() : 0;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.size;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static final class BufferExactSubscriber<T, C extends Collection<? super T>>
    implements InnerOperator<T, C> {
        final CoreSubscriber<? super C> actual;
        final Supplier<C> bufferSupplier;
        final int size;
        C buffer;
        Subscription s;
        boolean done;

        BufferExactSubscriber(CoreSubscriber<? super C> actual, int size, Supplier<C> bufferSupplier) {
            this.actual = actual;
            this.size = size;
            this.bufferSupplier = bufferSupplier;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                this.s.request(Operators.multiplyCap(n, this.size));
            }
        }

        public void cancel() {
            this.s.cancel();
            Operators.onDiscardMultiple(this.buffer, this.actual.currentContext());
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
            Object b = this.buffer;
            if (b == null) {
                try {
                    b = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
                }
                catch (Throwable e) {
                    Context ctx = this.actual.currentContext();
                    this.onError(Operators.onOperatorError(this.s, e, t, ctx));
                    Operators.onDiscard(t, ctx);
                    return;
                }
                this.buffer = b;
            }
            b.add(t);
            if (b.size() == this.size) {
                this.buffer = null;
                this.actual.onNext(b);
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t);
            Operators.onDiscardMultiple(this.buffer, this.actual.currentContext());
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            C b = this.buffer;
            if (b != null && !b.isEmpty()) {
                this.actual.onNext(b);
            }
            this.actual.onComplete();
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
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.BUFFERED) {
                C b = this.buffer;
                return b != null ? b.size() : 0;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.size;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.size;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}


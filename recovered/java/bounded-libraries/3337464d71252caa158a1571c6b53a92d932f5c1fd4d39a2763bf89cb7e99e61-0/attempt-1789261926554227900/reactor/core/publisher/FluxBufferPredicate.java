/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.DrainUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxBufferPredicate<T, C extends Collection<? super T>>
extends InternalFluxOperator<T, C> {
    final Predicate<? super T> predicate;
    final Supplier<C> bufferSupplier;
    final Mode mode;

    FluxBufferPredicate(Flux<? extends T> source, Predicate<? super T> predicate, Supplier<C> bufferSupplier, Mode mode) {
        super(source);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
        this.bufferSupplier = Objects.requireNonNull(bufferSupplier, "bufferSupplier");
        this.mode = mode;
    }

    @Override
    public int getPrefetch() {
        return 1;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super C> actual) {
        Collection initialBuffer = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null initial buffer");
        BufferPredicateSubscriber<? super T, Collection> parent = new BufferPredicateSubscriber<T, Collection>(actual, initialBuffer, this.bufferSupplier, this.predicate, this.mode);
        return parent;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static class ChangedPredicate<T, K>
    implements Predicate<T>,
    Disposable {
        private Function<? super T, ? extends K> keySelector;
        private BiPredicate<? super K, ? super K> keyComparator;
        private K lastKey;

        ChangedPredicate(Function<? super T, ? extends K> keySelector, BiPredicate<? super K, ? super K> keyComparator) {
            this.keySelector = keySelector;
            this.keyComparator = keyComparator;
        }

        @Override
        public void dispose() {
            this.lastKey = null;
        }

        @Override
        public boolean test(T t) {
            K k = this.keySelector.apply(t);
            if (null == this.lastKey) {
                this.lastKey = k;
                return false;
            }
            boolean match = this.keyComparator.test(this.lastKey, k);
            this.lastKey = k;
            return !match;
        }
    }

    static final class BufferPredicateSubscriber<T, C extends Collection<? super T>>
    extends AbstractQueue<C>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, C>,
    BooleanSupplier {
        final CoreSubscriber<? super C> actual;
        final Supplier<C> bufferSupplier;
        final Mode mode;
        final Predicate<? super T> predicate;
        @Nullable
        C buffer;
        boolean done;
        volatile boolean fastpath;
        volatile long requestedBuffers;
        static final AtomicLongFieldUpdater<BufferPredicateSubscriber> REQUESTED_BUFFERS = AtomicLongFieldUpdater.newUpdater(BufferPredicateSubscriber.class, "requestedBuffers");
        volatile long requestedFromSource;
        static final AtomicLongFieldUpdater<BufferPredicateSubscriber> REQUESTED_FROM_SOURCE = AtomicLongFieldUpdater.newUpdater(BufferPredicateSubscriber.class, "requestedFromSource");
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<BufferPredicateSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(BufferPredicateSubscriber.class, Subscription.class, "s");

        BufferPredicateSubscriber(CoreSubscriber<? super C> actual, C initialBuffer, Supplier<C> bufferSupplier, Predicate<? super T> predicate, Mode mode) {
            this.actual = actual;
            this.buffer = initialBuffer;
            this.bufferSupplier = bufferSupplier;
            this.predicate = predicate;
            this.mode = mode;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                if (n == Long.MAX_VALUE) {
                    this.fastpath = true;
                    REQUESTED_BUFFERS.set(this, Long.MAX_VALUE);
                    REQUESTED_FROM_SOURCE.set(this, Long.MAX_VALUE);
                    this.s.request(Long.MAX_VALUE);
                } else if (!DrainUtils.postCompleteRequest(n, this.actual, this, REQUESTED_BUFFERS, this, this)) {
                    Operators.addCap(REQUESTED_FROM_SOURCE, this, n);
                    this.s.request(n);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void cancel() {
            BufferPredicateSubscriber bufferPredicateSubscriber = this;
            synchronized (bufferPredicateSubscriber) {
                C b = this.buffer;
                this.buffer = null;
                Operators.onDiscardMultiple(b, this.actual.currentContext());
            }
            this.cleanup();
            Operators.terminate(S, this);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.tryOnNext(t)) {
                this.s.request(1L);
            }
        }

        @Override
        public boolean tryOnNext(T t) {
            boolean isStillExpectingBuffer;
            boolean match;
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return true;
            }
            try {
                match = this.predicate.test(t);
            }
            catch (Throwable e) {
                Context ctx = this.actual.currentContext();
                this.onError(Operators.onOperatorError(this.s, e, t, ctx));
                Operators.onDiscard(t, ctx);
                return true;
            }
            if (this.mode == Mode.UNTIL && match) {
                if (this.cancelledWhileAdding(t)) {
                    return true;
                }
                this.onNextNewBuffer();
            } else if (this.mode == Mode.UNTIL_CUT_BEFORE && match) {
                this.onNextNewBuffer();
                if (this.cancelledWhileAdding(t)) {
                    return true;
                }
            } else if (this.mode == Mode.WHILE && !match) {
                this.onNextNewBuffer();
            } else if (this.cancelledWhileAdding(t)) {
                return true;
            }
            if (this.fastpath) {
                return true;
            }
            boolean isNotExpectingFromSource = REQUESTED_FROM_SOURCE.decrementAndGet(this) == 0L;
            boolean bl = isStillExpectingBuffer = REQUESTED_BUFFERS.get(this) > 0L;
            return !isNotExpectingFromSource || !isStillExpectingBuffer || !REQUESTED_FROM_SOURCE.compareAndSet(this, 0L, 1L);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean cancelledWhileAdding(T value) {
            BufferPredicateSubscriber bufferPredicateSubscriber = this;
            synchronized (bufferPredicateSubscriber) {
                C b = this.buffer;
                if (b == null || this.s == Operators.cancelledSubscription()) {
                    Operators.onDiscard(value, this.actual.currentContext());
                    return true;
                }
                b.add(value);
                return false;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Nullable
        C triggerNewBuffer() {
            Collection c;
            C b;
            BufferPredicateSubscriber bufferPredicateSubscriber = this;
            synchronized (bufferPredicateSubscriber) {
                b = this.buffer;
                if (b == null || this.s == Operators.cancelledSubscription()) {
                    return null;
                }
            }
            if (b.isEmpty()) {
                return null;
            }
            try {
                c = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null buffer");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                return null;
            }
            BufferPredicateSubscriber bufferPredicateSubscriber2 = this;
            synchronized (bufferPredicateSubscriber2) {
                if (this.buffer == null) {
                    return null;
                }
                this.buffer = c;
            }
            return b;
        }

        private void onNextNewBuffer() {
            C b = this.triggerNewBuffer();
            if (b != null) {
                if (this.fastpath) {
                    this.actual.onNext(b);
                    return;
                }
                long r = REQUESTED_BUFFERS.getAndDecrement(this);
                if (r > 0L) {
                    this.actual.onNext(b);
                    return;
                }
                this.cancel();
                this.actual.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
            }
        }

        @Override
        public CoreSubscriber<? super C> actual() {
            return this.actual;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onError(Throwable t) {
            C b;
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            BufferPredicateSubscriber bufferPredicateSubscriber = this;
            synchronized (bufferPredicateSubscriber) {
                b = this.buffer;
                this.buffer = null;
            }
            this.cleanup();
            Operators.onDiscardMultiple(b, this.actual.currentContext());
            this.actual.onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.cleanup();
            DrainUtils.postComplete(this.actual, this, REQUESTED_BUFFERS, this, this);
        }

        void cleanup() {
            if (this.predicate instanceof Disposable) {
                ((Disposable)((Object)this.predicate)).dispose();
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.getAsBoolean();
            }
            if (key == Scannable.Attr.CAPACITY) {
                C b = this.buffer;
                return b != null ? b.size() : 0;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requestedBuffers;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public boolean getAsBoolean() {
            return this.s == Operators.cancelledSubscription();
        }

        @Override
        public Iterator<C> iterator() {
            if (this.isEmpty()) {
                return Collections.emptyIterator();
            }
            return Collections.singleton(this.buffer).iterator();
        }

        @Override
        public boolean offer(C objects) {
            throw new IllegalArgumentException();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @Nullable
        public C poll() {
            C b = this.buffer;
            if (b != null && !b.isEmpty()) {
                BufferPredicateSubscriber bufferPredicateSubscriber = this;
                synchronized (bufferPredicateSubscriber) {
                    this.buffer = null;
                }
                return b;
            }
            return null;
        }

        @Override
        @Nullable
        public C peek() {
            return this.buffer;
        }

        @Override
        public int size() {
            C b = this.buffer;
            return b == null || b.isEmpty() ? 0 : 1;
        }

        @Override
        public String toString() {
            return "FluxBufferPredicate";
        }
    }

    public static enum Mode {
        UNTIL,
        UNTIL_CUT_BEFORE,
        WHILE;

    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxBufferWhen<T, OPEN, CLOSE, BUFFER extends Collection<? super T>>
extends InternalFluxOperator<T, BUFFER> {
    final Publisher<OPEN> start;
    final Function<? super OPEN, ? extends Publisher<CLOSE>> end;
    final Supplier<BUFFER> bufferSupplier;
    final Supplier<? extends Queue<BUFFER>> queueSupplier;

    FluxBufferWhen(Flux<? extends T> source, Publisher<OPEN> start, Function<? super OPEN, ? extends Publisher<CLOSE>> end, Supplier<BUFFER> bufferSupplier, Supplier<? extends Queue<BUFFER>> queueSupplier) {
        super(source);
        this.start = Objects.requireNonNull(start, "start");
        this.end = Objects.requireNonNull(end, "end");
        this.bufferSupplier = Objects.requireNonNull(bufferSupplier, "bufferSupplier");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super BUFFER> actual) {
        BufferWhenMainSubscriber main = new BufferWhenMainSubscriber(actual, this.bufferSupplier, this.queueSupplier, this.start, this.end);
        actual.onSubscribe(main);
        BufferWhenOpenSubscriber<? super OPEN> bos = new BufferWhenOpenSubscriber<OPEN>(main);
        if (main.subscribers.add(bos)) {
            this.start.subscribe(bos);
            return main;
        }
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class BufferWhenCloseSubscriber<T, BUFFER extends Collection<? super T>>
    implements Disposable,
    InnerConsumer<Object> {
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<BufferWhenCloseSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(BufferWhenCloseSubscriber.class, Subscription.class, "subscription");
        final BufferWhenMainSubscriber<T, ?, ?, BUFFER> parent;
        final long index;

        BufferWhenCloseSubscriber(BufferWhenMainSubscriber<T, ?, ?, BUFFER> parent, long index) {
            this.parent = parent;
            this.index = index;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUBSCRIPTION, this, s)) {
                this.subscription.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override
        public boolean isDisposed() {
            return this.subscription == Operators.cancelledSubscription();
        }

        public void onNext(Object t) {
            Subscription s = this.subscription;
            if (s != Operators.cancelledSubscription()) {
                SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
                s.cancel();
                this.parent.close(this, this.index);
            }
        }

        public void onError(Throwable t) {
            if (this.subscription != Operators.cancelledSubscription()) {
                SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
                this.parent.boundaryError(this, t);
            } else {
                Operators.onErrorDropped(t, this.parent.ctx);
            }
        }

        public void onComplete() {
            if (this.subscription != Operators.cancelledSubscription()) {
                SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
                this.parent.close(this, this.index);
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.MAX_VALUE;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class BufferWhenOpenSubscriber<OPEN>
    implements Disposable,
    InnerConsumer<OPEN> {
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<BufferWhenOpenSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(BufferWhenOpenSubscriber.class, Subscription.class, "subscription");
        final BufferWhenMainSubscriber<?, OPEN, ?, ?> parent;

        BufferWhenOpenSubscriber(BufferWhenMainSubscriber<?, OPEN, ?, ?> parent) {
            this.parent = parent;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUBSCRIPTION, this, s)) {
                this.subscription.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override
        public boolean isDisposed() {
            return this.subscription == Operators.cancelledSubscription();
        }

        public void onNext(OPEN t) {
            this.parent.open(t);
        }

        public void onError(Throwable t) {
            SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
            this.parent.boundaryError(this, t);
        }

        public void onComplete() {
            SUBSCRIPTION.lazySet(this, Operators.cancelledSubscription());
            this.parent.openComplete(this);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Long.MAX_VALUE;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class BufferWhenMainSubscriber<T, OPEN, CLOSE, BUFFER extends Collection<? super T>>
    implements InnerOperator<T, BUFFER> {
        final CoreSubscriber<? super BUFFER> actual;
        final Context ctx;
        final Publisher<? extends OPEN> bufferOpen;
        final Function<? super OPEN, ? extends Publisher<? extends CLOSE>> bufferClose;
        final Supplier<BUFFER> bufferSupplier;
        final Disposable.Composite subscribers;
        final Queue<BUFFER> queue;
        volatile long requested;
        static final AtomicLongFieldUpdater<BufferWhenMainSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(BufferWhenMainSubscriber.class, "requested");
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<BufferWhenMainSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(BufferWhenMainSubscriber.class, Subscription.class, "s");
        volatile Throwable errors;
        static final AtomicReferenceFieldUpdater<BufferWhenMainSubscriber, Throwable> ERRORS = AtomicReferenceFieldUpdater.newUpdater(BufferWhenMainSubscriber.class, Throwable.class, "errors");
        volatile int windows;
        static final AtomicIntegerFieldUpdater<BufferWhenMainSubscriber> WINDOWS = AtomicIntegerFieldUpdater.newUpdater(BufferWhenMainSubscriber.class, "windows");
        volatile boolean done;
        volatile boolean cancelled;
        long index;
        LinkedHashMap<Long, BUFFER> buffers;
        long emitted;

        BufferWhenMainSubscriber(CoreSubscriber<? super BUFFER> actual, Supplier<BUFFER> bufferSupplier, Supplier<? extends Queue<BUFFER>> queueSupplier, Publisher<? extends OPEN> bufferOpen, Function<? super OPEN, ? extends Publisher<? extends CLOSE>> bufferClose) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.bufferOpen = bufferOpen;
            this.bufferClose = bufferClose;
            this.bufferSupplier = bufferSupplier;
            this.queue = queueSupplier.get();
            this.buffers = new LinkedHashMap();
            this.subscribers = Disposables.composite();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        @Override
        public CoreSubscriber<? super BUFFER> actual() {
            return this.actual;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onNext(T t) {
            BufferWhenMainSubscriber bufferWhenMainSubscriber = this;
            synchronized (bufferWhenMainSubscriber) {
                LinkedHashMap<Long, BUFFER> bufs = this.buffers;
                if (bufs == null) {
                    return;
                }
                if (bufs.isEmpty()) {
                    Operators.onDiscard(t, this.ctx);
                    return;
                }
                for (Collection b : bufs.values()) {
                    b.add(t);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onError(Throwable t) {
            if (Exceptions.addThrowable(ERRORS, this, t)) {
                LinkedHashMap<Long, BUFFER> bufs;
                this.subscribers.dispose();
                BufferWhenMainSubscriber bufferWhenMainSubscriber = this;
                synchronized (bufferWhenMainSubscriber) {
                    bufs = this.buffers;
                    this.buffers = null;
                }
                this.done = true;
                this.drain();
                if (bufs != null) {
                    for (Collection b : bufs.values()) {
                        Operators.onDiscardMultiple(b, this.ctx);
                    }
                }
            } else {
                Operators.onErrorDropped(t, this.ctx);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void onComplete() {
            this.subscribers.dispose();
            BufferWhenMainSubscriber bufferWhenMainSubscriber = this;
            synchronized (bufferWhenMainSubscriber) {
                LinkedHashMap<Long, BUFFER> bufs = this.buffers;
                if (bufs == null) {
                    return;
                }
                for (Collection b : bufs.values()) {
                    this.queue.offer(b);
                }
                this.buffers = null;
            }
            this.done = true;
            this.drain();
        }

        public void request(long n) {
            Operators.addCap(REQUESTED, this, n);
            this.drain();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void cancel() {
            if (Operators.terminate(S, this)) {
                LinkedHashMap<Long, BUFFER> bufs;
                this.cancelled = true;
                this.subscribers.dispose();
                BufferWhenMainSubscriber bufferWhenMainSubscriber = this;
                synchronized (bufferWhenMainSubscriber) {
                    bufs = this.buffers;
                    this.buffers = null;
                }
                if (WINDOWS.getAndIncrement(this) == 0) {
                    Operators.onDiscardQueueWithClear(this.queue, this.ctx, Collection::stream);
                }
                if (bufs != null && !bufs.isEmpty()) {
                    for (Collection buffer : bufs.values()) {
                        Operators.onDiscardMultiple(buffer, this.ctx);
                    }
                }
            }
        }

        void drain() {
            if (WINDOWS.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            long e = this.emitted;
            CoreSubscriber<? super BUFFER> a = this.actual;
            Queue<BUFFER> q = this.queue;
            do {
                long r = this.requested;
                while (e != r) {
                    boolean empty;
                    if (this.cancelled) {
                        Operators.onDiscardQueueWithClear(q, this.ctx, Collection::stream);
                        return;
                    }
                    boolean d = this.done;
                    if (d && this.errors != null) {
                        Operators.onDiscardQueueWithClear(q, this.ctx, Collection::stream);
                        Throwable ex = Exceptions.terminate(ERRORS, this);
                        a.onError(ex);
                        return;
                    }
                    Collection v = (Collection)q.poll();
                    boolean bl = empty = v == null;
                    if (d && empty) {
                        a.onComplete();
                        return;
                    }
                    if (empty) break;
                    a.onNext(v);
                    ++e;
                }
                if (e == r) {
                    if (this.cancelled) {
                        Operators.onDiscardQueueWithClear(q, this.ctx, Collection::stream);
                        return;
                    }
                    if (this.done) {
                        if (this.errors != null) {
                            Operators.onDiscardQueueWithClear(q, this.ctx, Collection::stream);
                            Throwable ex = Exceptions.terminate(ERRORS, this);
                            a.onError(ex);
                            return;
                        }
                        if (q.isEmpty()) {
                            a.onComplete();
                            return;
                        }
                    }
                }
                this.emitted = e;
            } while ((missed = WINDOWS.addAndGet(this, -missed)) != 0);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void open(OPEN token) {
            Publisher<? extends CLOSE> p;
            Collection buf;
            try {
                buf = (Collection)Objects.requireNonNull(this.bufferSupplier.get(), "The bufferSupplier returned a null Collection");
                p = Objects.requireNonNull(this.bufferClose.apply(token), "The bufferClose returned a null Publisher");
            }
            catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                Operators.terminate(S, this);
                if (Exceptions.addThrowable(ERRORS, this, ex)) {
                    LinkedHashMap<Long, BUFFER> bufs;
                    this.subscribers.dispose();
                    BufferWhenMainSubscriber bufferWhenMainSubscriber = this;
                    synchronized (bufferWhenMainSubscriber) {
                        bufs = this.buffers;
                        this.buffers = null;
                    }
                    this.done = true;
                    this.drain();
                    if (bufs != null) {
                        for (Collection buffer : bufs.values()) {
                            Operators.onDiscardMultiple(buffer, this.ctx);
                        }
                    }
                } else {
                    Operators.onErrorDropped(ex, this.ctx);
                }
                return;
            }
            long idx = this.index;
            this.index = idx + 1L;
            BufferWhenMainSubscriber bufferWhenMainSubscriber = this;
            synchronized (bufferWhenMainSubscriber) {
                LinkedHashMap<Long, BUFFER> bufs = this.buffers;
                if (bufs == null) {
                    return;
                }
                bufs.put(idx, buf);
            }
            BufferWhenCloseSubscriber bc = new BufferWhenCloseSubscriber(this, idx);
            this.subscribers.add(bc);
            p.subscribe(bc);
        }

        void openComplete(BufferWhenOpenSubscriber<OPEN> os) {
            this.subscribers.remove(os);
            if (this.subscribers.size() == 0) {
                Operators.terminate(S, this);
                this.done = true;
                this.drain();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void close(BufferWhenCloseSubscriber<T, BUFFER> closer, long idx) {
            this.subscribers.remove(closer);
            boolean makeDone = false;
            if (this.subscribers.size() == 0) {
                makeDone = true;
                Operators.terminate(S, this);
            }
            BufferWhenMainSubscriber bufferWhenMainSubscriber = this;
            synchronized (bufferWhenMainSubscriber) {
                LinkedHashMap<Long, BUFFER> bufs = this.buffers;
                if (bufs == null) {
                    return;
                }
                this.queue.offer(this.buffers.remove(idx));
            }
            if (makeDone) {
                this.done = true;
            }
            this.drain();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void boundaryError(Disposable boundary, Throwable ex) {
            Operators.terminate(S, this);
            this.subscribers.remove(boundary);
            if (Exceptions.addThrowable(ERRORS, this, ex)) {
                LinkedHashMap<Long, BUFFER> bufs;
                this.subscribers.dispose();
                BufferWhenMainSubscriber bufferWhenMainSubscriber = this;
                synchronized (bufferWhenMainSubscriber) {
                    bufs = this.buffers;
                    this.buffers = null;
                }
                this.done = true;
                this.drain();
                if (bufs != null) {
                    for (Collection buffer : bufs.values()) {
                        Operators.onDiscardMultiple(buffer, this.ctx);
                    }
                }
            } else {
                Operators.onErrorDropped(ex, this.ctx);
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.buffers.values().stream().mapToInt(Collection::size).sum();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.errors;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class BlockingIterable<T>
implements Iterable<T>,
Scannable {
    final CorePublisher<? extends T> source;
    final int batchSize;
    final Supplier<Queue<T>> queueSupplier;

    BlockingIterable(CorePublisher<? extends T> source, int batchSize, Supplier<Queue<T>> queueSupplier) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException("batchSize > 0 required but it was " + batchSize);
        }
        this.source = Objects.requireNonNull(source, "source");
        this.batchSize = batchSize;
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return Math.min(Integer.MAX_VALUE, this.batchSize);
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        SubscriberIterator<T> it = this.createIterator();
        this.source.subscribe(it);
        return it;
    }

    @Override
    public Spliterator<T> spliterator() {
        return this.stream().spliterator();
    }

    public Stream<T> stream() {
        SubscriberIterator<T> it = this.createIterator();
        this.source.subscribe(it);
        Spliterator<T> sp = Spliterators.spliteratorUnknownSize(it, 0);
        return (Stream)StreamSupport.stream(sp, false).onClose(it);
    }

    SubscriberIterator<T> createIterator() {
        Queue<T> q;
        try {
            q = Objects.requireNonNull(this.queueSupplier.get(), "The queueSupplier returned a null queue");
        }
        catch (Throwable e) {
            throw Exceptions.propagate(e);
        }
        return new SubscriberIterator<T>(q, this.batchSize);
    }

    static final class SubscriberIterator<T>
    implements InnerConsumer<T>,
    Iterator<T>,
    Runnable {
        final Queue<T> queue;
        final int batchSize;
        final int limit;
        final Lock lock;
        final Condition condition;
        long produced;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<SubscriberIterator, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(SubscriberIterator.class, Subscription.class, "s");
        volatile boolean done;
        Throwable error;

        SubscriberIterator(Queue<T> queue, int batchSize) {
            this.queue = queue;
            this.batchSize = batchSize;
            this.limit = Operators.unboundedOrLimit(batchSize);
            this.lock = new ReentrantLock();
            this.condition = this.lock.newCondition();
        }

        @Override
        public Context currentContext() {
            return Context.empty();
        }

        @Override
        public boolean hasNext() {
            if (Schedulers.isInNonBlockingThread()) {
                throw new IllegalStateException("Iterating over a toIterable() / toStream() is blocking, which is not supported in thread " + Thread.currentThread().getName());
            }
            block5: while (true) {
                boolean d = this.done;
                boolean empty = this.queue.isEmpty();
                if (d) {
                    Throwable e = this.error;
                    if (e != null) {
                        throw Exceptions.propagate(e);
                    }
                    if (empty) {
                        return false;
                    }
                }
                if (!empty) break;
                this.lock.lock();
                try {
                    while (true) {
                        if (this.done || !this.queue.isEmpty()) continue block5;
                        this.condition.await();
                    }
                }
                catch (InterruptedException ex) {
                    this.run();
                    throw Exceptions.propagate(ex);
                }
                finally {
                    this.lock.unlock();
                    continue;
                }
                break;
            }
            return true;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                T v = this.queue.poll();
                if (v == null) {
                    this.run();
                    throw new IllegalStateException("Queue is empty: Expected one element to be available from the Reactive Streams source.");
                }
                long p = this.produced + 1L;
                if (p == (long)this.limit) {
                    this.produced = 0L;
                    this.s.request(p);
                } else {
                    this.produced = p;
                }
                return v;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(S, this, s)) {
                s.request(Operators.unboundedOrPrefetch(this.batchSize));
            }
        }

        public void onNext(T t) {
            if (!this.queue.offer(t)) {
                Operators.terminate(S, this);
                this.onError(Operators.onOperatorError(null, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t, this.currentContext()));
            } else {
                this.signalConsumer();
            }
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            this.signalConsumer();
        }

        public void onComplete() {
            this.done = true;
            this.signalConsumer();
        }

        void signalConsumer() {
            this.lock.lock();
            try {
                this.condition.signalAll();
            }
            finally {
                this.lock.unlock();
            }
        }

        @Override
        public void run() {
            Operators.terminate(S, this);
            this.signalConsumer();
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
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.batchSize;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}


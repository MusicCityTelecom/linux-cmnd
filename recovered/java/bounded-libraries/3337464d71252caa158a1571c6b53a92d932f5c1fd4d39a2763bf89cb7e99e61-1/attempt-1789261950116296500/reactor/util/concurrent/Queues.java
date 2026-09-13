/*
 * Decompiled with CFR 0.152.
 */
package reactor.util.concurrent;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import reactor.core.publisher.Hooks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.MpscLinkedQueue;
import reactor.util.concurrent.SpscArrayQueue;
import reactor.util.concurrent.SpscLinkedArrayQueue;

public final class Queues {
    public static final int CAPACITY_UNSURE = Integer.MIN_VALUE;
    public static final int XS_BUFFER_SIZE = Math.max(8, Integer.parseInt(System.getProperty("reactor.bufferSize.x", "32")));
    public static final int SMALL_BUFFER_SIZE = Math.max(16, Integer.parseInt(System.getProperty("reactor.bufferSize.small", "256")));
    static final Supplier ZERO_SUPPLIER = () -> Hooks.wrapQueue(new ZeroQueue());
    static final Supplier ONE_SUPPLIER = () -> Hooks.wrapQueue(new OneQueue());
    static final Supplier XS_SUPPLIER = () -> Hooks.wrapQueue(new SpscArrayQueue(XS_BUFFER_SIZE));
    static final Supplier SMALL_SUPPLIER = () -> Hooks.wrapQueue(new SpscArrayQueue(SMALL_BUFFER_SIZE));
    static final Supplier SMALL_UNBOUNDED = () -> Hooks.wrapQueue(new SpscLinkedArrayQueue(SMALL_BUFFER_SIZE));
    static final Supplier XS_UNBOUNDED = () -> Hooks.wrapQueue(new SpscLinkedArrayQueue(XS_BUFFER_SIZE));

    public static final int capacity(Queue q) {
        if (q instanceof ZeroQueue) {
            return 0;
        }
        if (q instanceof OneQueue) {
            return 1;
        }
        if (q instanceof SpscLinkedArrayQueue) {
            return Integer.MAX_VALUE;
        }
        if (q instanceof SpscArrayQueue) {
            return ((SpscArrayQueue)q).length();
        }
        if (q instanceof MpscLinkedQueue) {
            return Integer.MAX_VALUE;
        }
        if (q instanceof BlockingQueue) {
            return ((BlockingQueue)q).remainingCapacity();
        }
        if (q instanceof ConcurrentLinkedQueue) {
            return Integer.MAX_VALUE;
        }
        return Integer.MIN_VALUE;
    }

    public static int ceilingNextPowerOfTwo(int x) {
        return 1 << 32 - Integer.numberOfLeadingZeros(x - 1);
    }

    public static <T> Supplier<Queue<T>> get(int batchSize) {
        if (batchSize == Integer.MAX_VALUE) {
            return SMALL_UNBOUNDED;
        }
        if (batchSize == XS_BUFFER_SIZE) {
            return XS_SUPPLIER;
        }
        if (batchSize == SMALL_BUFFER_SIZE) {
            return SMALL_SUPPLIER;
        }
        if (batchSize == 1) {
            return ONE_SUPPLIER;
        }
        if (batchSize == 0) {
            return ZERO_SUPPLIER;
        }
        int adjustedBatchSize = Math.max(8, batchSize);
        if (adjustedBatchSize > 10000000) {
            return SMALL_UNBOUNDED;
        }
        return () -> Hooks.wrapQueue(new SpscArrayQueue(adjustedBatchSize));
    }

    public static boolean isPowerOfTwo(int x) {
        return Integer.bitCount(x) == 1;
    }

    public static <T> Supplier<Queue<T>> empty() {
        return ZERO_SUPPLIER;
    }

    public static <T> Supplier<Queue<T>> one() {
        return ONE_SUPPLIER;
    }

    public static <T> Supplier<Queue<T>> small() {
        return SMALL_SUPPLIER;
    }

    public static <T> Supplier<Queue<T>> unbounded() {
        return SMALL_UNBOUNDED;
    }

    public static <T> Supplier<Queue<T>> unbounded(int linkSize) {
        if (linkSize == XS_BUFFER_SIZE) {
            return XS_UNBOUNDED;
        }
        if (linkSize == Integer.MAX_VALUE || linkSize == SMALL_BUFFER_SIZE) {
            return Queues.unbounded();
        }
        return () -> Hooks.wrapQueue(new SpscLinkedArrayQueue(linkSize));
    }

    public static <T> Supplier<Queue<T>> xs() {
        return XS_SUPPLIER;
    }

    public static <T> Supplier<Queue<T>> unboundedMultiproducer() {
        return () -> Hooks.wrapQueue(new MpscLinkedQueue());
    }

    private Queues() {
    }

    static final class QueueIterator<T>
    implements Iterator<T> {
        final Queue<T> queue;

        public QueueIterator(Queue<T> queue) {
            this.queue = queue;
        }

        @Override
        public boolean hasNext() {
            return !this.queue.isEmpty();
        }

        @Override
        public T next() {
            return this.queue.poll();
        }

        @Override
        public void remove() {
            this.queue.remove();
        }
    }

    static final class ZeroQueue<T>
    implements Queue<T>,
    Serializable {
        private static final long serialVersionUID = -8876883675795156827L;

        ZeroQueue() {
        }

        @Override
        public boolean add(T t) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            return false;
        }

        @Override
        public void clear() {
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public T element() {
            throw new NoSuchElementException("immutable empty queue");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterator<T> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public boolean offer(T t) {
            return false;
        }

        @Override
        @Nullable
        public T peek() {
            return null;
        }

        @Override
        @Nullable
        public T poll() {
            return null;
        }

        @Override
        public T remove() {
            throw new NoSuchElementException("immutable empty queue");
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T1> T1[] toArray(T1[] a) {
            if (a.length > 0) {
                a[0] = null;
            }
            return a;
        }
    }

    static final class OneQueue<T>
    extends AtomicReference<T>
    implements Queue<T> {
        private static final long serialVersionUID = -6079491923525372331L;

        OneQueue() {
        }

        @Override
        public boolean add(T t) {
            while (!this.offer(t)) {
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            return false;
        }

        @Override
        public void clear() {
            this.set(null);
        }

        @Override
        public boolean contains(Object o) {
            return Objects.equals(this.get(), o);
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public T element() {
            return (T)this.get();
        }

        @Override
        public boolean isEmpty() {
            return this.get() == null;
        }

        @Override
        public Iterator<T> iterator() {
            return new QueueIterator(this);
        }

        @Override
        public boolean offer(T t) {
            if (this.get() != null) {
                return false;
            }
            this.lazySet(t);
            return true;
        }

        @Override
        @Nullable
        public T peek() {
            return (T)this.get();
        }

        @Override
        @Nullable
        public T poll() {
            Object v = this.get();
            if (v != null) {
                this.lazySet(null);
            }
            return (T)v;
        }

        @Override
        public T remove() {
            return this.getAndSet(null);
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public int size() {
            return this.get() == null ? 0 : 1;
        }

        @Override
        public Object[] toArray() {
            Object t = this.get();
            if (t == null) {
                return new Object[0];
            }
            return new Object[]{t};
        }

        @Override
        public <T1> T1[] toArray(T1[] a) {
            int size = this.size();
            if (a.length < size) {
                a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
            }
            if (size == 1) {
                a[0] = this.get();
            }
            if (a.length > size) {
                a[size] = null;
            }
            return a;
        }
    }
}


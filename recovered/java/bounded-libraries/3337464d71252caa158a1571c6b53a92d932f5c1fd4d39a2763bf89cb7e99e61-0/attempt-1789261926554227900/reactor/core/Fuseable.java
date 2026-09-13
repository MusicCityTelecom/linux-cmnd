/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.util.annotation.Nullable;

public interface Fuseable {
    public static final int NONE = 0;
    public static final int SYNC = 1;
    public static final int ASYNC = 2;
    public static final int ANY = 3;
    public static final int THREAD_BARRIER = 4;

    public static String fusionModeName(int mode) {
        return Fuseable.fusionModeName(mode, false);
    }

    public static String fusionModeName(int mode, boolean ignoreThreadBarrier) {
        int evaluated = mode;
        String threadBarrierSuffix = "";
        if (mode >= 0) {
            evaluated = mode & 0xFFFFFFFB;
            if (!ignoreThreadBarrier && (mode & 4) == 4) {
                threadBarrierSuffix = "+THREAD_BARRIER";
            }
        }
        switch (evaluated) {
            case -1: {
                return "Disabled";
            }
            case 0: {
                return "NONE" + threadBarrierSuffix;
            }
            case 1: {
                return "SYNC" + threadBarrierSuffix;
            }
            case 2: {
                return "ASYNC" + threadBarrierSuffix;
            }
        }
        return "Unknown(" + evaluated + ")" + threadBarrierSuffix;
    }

    public static interface ScalarCallable<T>
    extends Callable<T> {
    }

    public static interface SynchronousSubscription<T>
    extends QueueSubscription<T> {
        @Override
        default public int requestFusion(int requestedMode) {
            if ((requestedMode & 1) != 0) {
                return 1;
            }
            return 0;
        }
    }

    public static interface QueueSubscription<T>
    extends Queue<T>,
    Subscription {
        public static final String NOT_SUPPORTED_MESSAGE = "Although QueueSubscription extends Queue it is purely internal and only guarantees support for poll/clear/size/isEmpty. Instances shouldn't be used/exposed as Queue outside of Reactor operators.";

        public int requestFusion(int var1);

        @Override
        @Nullable
        default public T peek() {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public boolean add(@Nullable T t) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public boolean offer(@Nullable T t) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public T remove() {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public T element() {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public boolean contains(@Nullable Object o) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public Iterator<T> iterator() {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public Object[] toArray() {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public <T1> T1[] toArray(T1[] a) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public boolean remove(@Nullable Object o) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public boolean addAll(Collection<? extends T> c) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }

        @Override
        default public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException(NOT_SUPPORTED_MESSAGE);
        }
    }

    public static interface ConditionalSubscriber<T>
    extends CoreSubscriber<T> {
        public boolean tryOnNext(T var1);
    }
}


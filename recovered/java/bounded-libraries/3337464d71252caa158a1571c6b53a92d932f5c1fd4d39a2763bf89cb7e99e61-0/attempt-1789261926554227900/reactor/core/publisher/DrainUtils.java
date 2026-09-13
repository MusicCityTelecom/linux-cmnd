/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

abstract class DrainUtils {
    static final long COMPLETED_MASK = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;

    static <T, F> boolean postCompleteRequest(long n, Subscriber<? super T> actual, Queue<T> queue, AtomicLongFieldUpdater<F> field, F instance, BooleanSupplier isCancelled) {
        long r0;
        long u;
        long r;
        while (!field.compareAndSet(instance, r = field.get(instance), u = r & Long.MIN_VALUE | Operators.addCap(r0 = r & Long.MAX_VALUE, n))) {
        }
        if (r == Long.MIN_VALUE) {
            DrainUtils.postCompleteDrain(n | Long.MIN_VALUE, actual, queue, field, instance, isCancelled);
            return true;
        }
        return false;
    }

    static <T, F> boolean postCompleteDrain(long n, Subscriber<? super T> actual, Queue<T> queue, AtomicLongFieldUpdater<F> field, F instance, BooleanSupplier isCancelled) {
        long e = n & Long.MIN_VALUE;
        while (true) {
            if (e != n) {
                if (isCancelled.getAsBoolean()) {
                    return true;
                }
                T t = queue.poll();
                if (t == null) {
                    actual.onComplete();
                    return true;
                }
                actual.onNext(t);
                ++e;
                continue;
            }
            if (isCancelled.getAsBoolean()) {
                return true;
            }
            if (queue.isEmpty()) {
                actual.onComplete();
                return true;
            }
            n = field.get(instance);
            if (n != e) continue;
            n = field.addAndGet(instance, -(e & Long.MAX_VALUE));
            if ((n & Long.MAX_VALUE) == 0L) {
                return false;
            }
            e = n & Long.MIN_VALUE;
        }
    }

    public static <T, F> void postComplete(CoreSubscriber<? super T> actual, Queue<T> queue, AtomicLongFieldUpdater<F> field, F instance, BooleanSupplier isCancelled) {
        long u;
        long r;
        if (queue.isEmpty()) {
            actual.onComplete();
            return;
        }
        if (DrainUtils.postCompleteDrain(field.get(instance), actual, queue, field, instance, isCancelled)) {
            return;
        }
        do {
            if (((r = field.get(instance)) & Long.MIN_VALUE) == 0L) continue;
            return;
        } while (!field.compareAndSet(instance, r, u = r | Long.MIN_VALUE));
        if (r != 0L) {
            DrainUtils.postCompleteDrain(u, actual, queue, field, instance, isCancelled);
        }
    }

    public static <T, F> boolean postCompleteRequestDelayError(long n, Subscriber<? super T> actual, Queue<T> queue, AtomicLongFieldUpdater<F> field, F instance, BooleanSupplier isCancelled, Throwable error) {
        long r0;
        long u;
        long r;
        while (!field.compareAndSet(instance, r = field.get(instance), u = r & Long.MIN_VALUE | Operators.addCap(r0 = r & Long.MAX_VALUE, n))) {
        }
        if (r == Long.MIN_VALUE) {
            DrainUtils.postCompleteDrainDelayError(n | Long.MIN_VALUE, actual, queue, field, instance, isCancelled, error);
            return true;
        }
        return false;
    }

    static <T, F> boolean postCompleteDrainDelayError(long n, Subscriber<? super T> actual, Queue<T> queue, AtomicLongFieldUpdater<F> field, F instance, BooleanSupplier isCancelled, @Nullable Throwable error) {
        long e = n & Long.MIN_VALUE;
        while (true) {
            if (e != n) {
                if (isCancelled.getAsBoolean()) {
                    return true;
                }
                T t = queue.poll();
                if (t == null) {
                    if (error == null) {
                        actual.onComplete();
                    } else {
                        actual.onError(error);
                    }
                    return true;
                }
                actual.onNext(t);
                ++e;
                continue;
            }
            if (isCancelled.getAsBoolean()) {
                return true;
            }
            if (queue.isEmpty()) {
                if (error == null) {
                    actual.onComplete();
                } else {
                    actual.onError(error);
                }
                return true;
            }
            n = field.get(instance);
            if (n != e) continue;
            n = field.addAndGet(instance, -(e & Long.MAX_VALUE));
            if ((n & Long.MAX_VALUE) == 0L) {
                return false;
            }
            e = n & Long.MIN_VALUE;
        }
    }

    public static <T, F> void postCompleteDelayError(CoreSubscriber<? super T> actual, Queue<T> queue, AtomicLongFieldUpdater<F> field, F instance, BooleanSupplier isCancelled, @Nullable Throwable error) {
        long u;
        long r;
        if (queue.isEmpty()) {
            if (error == null) {
                actual.onComplete();
            } else {
                actual.onError(error);
            }
            return;
        }
        if (DrainUtils.postCompleteDrainDelayError(field.get(instance), actual, queue, field, instance, isCancelled, error)) {
            return;
        }
        do {
            if (((r = field.get(instance)) & Long.MIN_VALUE) == 0L) continue;
            return;
        } while (!field.compareAndSet(instance, r, u = r | Long.MIN_VALUE));
        if (r != 0L) {
            DrainUtils.postCompleteDrainDelayError(u, actual, queue, field, instance, isCancelled, error);
        }
    }

    DrainUtils() {
    }
}


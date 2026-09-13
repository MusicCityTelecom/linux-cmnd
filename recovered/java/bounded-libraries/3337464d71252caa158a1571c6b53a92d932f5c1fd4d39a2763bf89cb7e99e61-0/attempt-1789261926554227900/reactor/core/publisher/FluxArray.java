/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxArray<T>
extends Flux<T>
implements Fuseable,
SourceProducer<T> {
    final T[] array;

    @SafeVarargs
    public FluxArray(T ... array) {
        this.array = Objects.requireNonNull(array, "array");
    }

    public static <T> void subscribe(CoreSubscriber<? super T> s, T[] array) {
        if (array.length == 0) {
            Operators.complete(s);
            return;
        }
        if (s instanceof Fuseable.ConditionalSubscriber) {
            s.onSubscribe(new ArrayConditionalSubscription<T>((Fuseable.ConditionalSubscriber)s, array));
        } else {
            s.onSubscribe(new ArraySubscription<T>(s, array));
        }
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        FluxArray.subscribe(actual, this.array);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.BUFFERED) {
            return this.array.length;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class ArrayConditionalSubscription<T>
    implements InnerProducer<T>,
    Fuseable.SynchronousSubscription<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final T[] array;
        int index;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<ArrayConditionalSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(ArrayConditionalSubscription.class, "requested");

        ArrayConditionalSubscription(Fuseable.ConditionalSubscriber<? super T> actual, T[] array) {
            this.actual = actual;
            this.array = array;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            if (Operators.validate(n) && Operators.addCap(REQUESTED, this, n) == 0L) {
                if (n == Long.MAX_VALUE) {
                    this.fastPath();
                } else {
                    this.slowPath(n);
                }
            }
        }

        void slowPath(long n) {
            T[] a = this.array;
            int len = a.length;
            Fuseable.ConditionalSubscriber<? super T> s = this.actual;
            int i = this.index;
            int e = 0;
            while (!this.cancelled) {
                while (i != len && (long)e != n) {
                    T t = a[i];
                    if (t == null) {
                        s.onError(new NullPointerException("The " + i + "th array element was null"));
                        return;
                    }
                    boolean b = s.tryOnNext((T)t);
                    if (this.cancelled) {
                        return;
                    }
                    ++i;
                    if (!b) continue;
                    ++e;
                }
                if (i == len) {
                    s.onComplete();
                    return;
                }
                n = this.requested;
                if (n != (long)e) continue;
                this.index = i;
                n = REQUESTED.addAndGet(this, -e);
                if (n == 0L) {
                    return;
                }
                e = 0;
            }
            return;
        }

        void fastPath() {
            T[] a = this.array;
            int len = a.length;
            Fuseable.ConditionalSubscriber<? super T> s = this.actual;
            for (int i = this.index; i != len; ++i) {
                if (this.cancelled) {
                    return;
                }
                T t = a[i];
                if (t == null) {
                    s.onError(new NullPointerException("The " + i + "th array element was null"));
                    return;
                }
                s.onNext(t);
            }
            if (this.cancelled) {
                return;
            }
            s.onComplete();
        }

        public void cancel() {
            this.cancelled = true;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.isEmpty();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.size();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        @Nullable
        public T poll() {
            int i = this.index;
            T[] a = this.array;
            if (i != a.length) {
                T t = Objects.requireNonNull(a[i], "Array returned null value");
                this.index = i + 1;
                return t;
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return this.index == this.array.length;
        }

        @Override
        public void clear() {
            this.index = this.array.length;
        }

        @Override
        public int size() {
            return this.array.length - this.index;
        }
    }

    static final class ArraySubscription<T>
    implements InnerProducer<T>,
    Fuseable.SynchronousSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final T[] array;
        int index;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<ArraySubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(ArraySubscription.class, "requested");

        ArraySubscription(CoreSubscriber<? super T> actual, T[] array) {
            this.actual = actual;
            this.array = array;
        }

        public void request(long n) {
            if (Operators.validate(n) && Operators.addCap(REQUESTED, this, n) == 0L) {
                if (n == Long.MAX_VALUE) {
                    this.fastPath();
                } else {
                    this.slowPath(n);
                }
            }
        }

        void slowPath(long n) {
            T[] a = this.array;
            int len = a.length;
            CoreSubscriber<? super T> s = this.actual;
            int i = this.index;
            int e = 0;
            while (!this.cancelled) {
                while (i != len && (long)e != n) {
                    T t = a[i];
                    if (t == null) {
                        s.onError(new NullPointerException("The " + i + "th array element was null"));
                        return;
                    }
                    s.onNext(t);
                    if (this.cancelled) {
                        return;
                    }
                    ++i;
                    ++e;
                }
                if (i == len) {
                    s.onComplete();
                    return;
                }
                n = this.requested;
                if (n != (long)e) continue;
                this.index = i;
                n = REQUESTED.addAndGet(this, -e);
                if (n == 0L) {
                    return;
                }
                e = 0;
            }
            return;
        }

        void fastPath() {
            T[] a = this.array;
            int len = a.length;
            CoreSubscriber<? super T> s = this.actual;
            for (int i = this.index; i != len; ++i) {
                if (this.cancelled) {
                    return;
                }
                T t = a[i];
                if (t == null) {
                    s.onError(new NullPointerException("The " + i + "th array element was null"));
                    return;
                }
                s.onNext(t);
            }
            if (this.cancelled) {
                return;
            }
            s.onComplete();
        }

        public void cancel() {
            this.cancelled = true;
        }

        @Override
        @Nullable
        public T poll() {
            int i = this.index;
            T[] a = this.array;
            if (i != a.length) {
                T t = a[i];
                Objects.requireNonNull(t);
                this.index = i + 1;
                return t;
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return this.index == this.array.length;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void clear() {
            this.index = this.array.length;
        }

        @Override
        public int size() {
            return this.array.length - this.index;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.isEmpty();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.size();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            return InnerProducer.super.scanUnsafe(key);
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;

final class FluxIterable<T>
extends Flux<T>
implements Fuseable,
SourceProducer<T> {
    final Iterable<? extends T> iterable;
    @Nullable
    private final Runnable onClose;

    static <T> boolean checkFinite(Iterable<T> iterable) {
        return iterable instanceof Collection || iterable.spliterator().hasCharacteristics(64);
    }

    FluxIterable(Iterable<? extends T> iterable, @Nullable Runnable onClose) {
        this.iterable = Objects.requireNonNull(iterable, "iterable");
        this.onClose = onClose;
    }

    FluxIterable(Iterable<? extends T> iterable) {
        this(iterable, null);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Iterator<? extends T> it;
        boolean knownToBeFinite;
        try {
            knownToBeFinite = FluxIterable.checkFinite(this.iterable);
            it = this.iterable.iterator();
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        FluxIterable.subscribe(actual, it, knownToBeFinite, this.onClose);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.BUFFERED) {
            if (this.iterable instanceof Collection) {
                return ((Collection)this.iterable).size();
            }
            if (this.iterable instanceof Tuple2) {
                return ((Tuple2)this.iterable).size();
            }
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static <T> void subscribe(CoreSubscriber<? super T> s, Iterator<? extends T> it, boolean knownToBeFinite) {
        FluxIterable.subscribe(s, it, knownToBeFinite, null);
    }

    static <T> void subscribe(CoreSubscriber<? super T> s, Iterator<? extends T> it, boolean knownToBeFinite, @Nullable Runnable onClose) {
        boolean b;
        if (it == null) {
            Operators.error(s, new NullPointerException("The iterator is null"));
            return;
        }
        try {
            b = it.hasNext();
        }
        catch (Throwable e) {
            Operators.error(s, Operators.onOperatorError(e, s.currentContext()));
            if (onClose != null) {
                try {
                    onClose.run();
                }
                catch (Throwable t) {
                    Operators.onErrorDropped(t, s.currentContext());
                }
            }
            return;
        }
        if (!b) {
            Operators.complete(s);
            if (onClose != null) {
                try {
                    onClose.run();
                }
                catch (Throwable t) {
                    Operators.onErrorDropped(t, s.currentContext());
                }
            }
            return;
        }
        if (s instanceof Fuseable.ConditionalSubscriber) {
            s.onSubscribe(new IterableSubscriptionConditional<T>((Fuseable.ConditionalSubscriber)s, it, knownToBeFinite, onClose));
        } else {
            s.onSubscribe(new IterableSubscription<T>(s, it, knownToBeFinite, onClose));
        }
    }

    static final class IterableSubscriptionConditional<T>
    implements InnerProducer<T>,
    Fuseable.SynchronousSubscription<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Iterator<? extends T> iterator;
        final boolean knownToBeFinite;
        final Runnable onClose;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<IterableSubscriptionConditional> REQUESTED = AtomicLongFieldUpdater.newUpdater(IterableSubscriptionConditional.class, "requested");
        int state;
        static final int STATE_HAS_NEXT_NO_VALUE = 0;
        static final int STATE_HAS_NEXT_HAS_VALUE = 1;
        static final int STATE_NO_NEXT = 2;
        static final int STATE_CALL_HAS_NEXT = 3;
        T current;
        Throwable hasNextFailure;

        IterableSubscriptionConditional(Fuseable.ConditionalSubscriber<? super T> actual, Iterator<? extends T> iterator, boolean knownToBeFinite, @Nullable Runnable onClose) {
            this.actual = actual;
            this.iterator = iterator;
            this.knownToBeFinite = knownToBeFinite;
            this.onClose = onClose;
        }

        IterableSubscriptionConditional(Fuseable.ConditionalSubscriber<? super T> actual, Iterator<? extends T> iterator, boolean knownToBeFinite) {
            this(actual, iterator, knownToBeFinite, null);
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

        private void onCloseWithDropError() {
            if (this.onClose != null) {
                try {
                    this.onClose.run();
                }
                catch (Throwable t) {
                    Operators.onErrorDropped(t, this.actual.currentContext());
                }
            }
        }

        void slowPath(long n) {
            Iterator<T> a = this.iterator;
            Fuseable.ConditionalSubscriber<? super T> s = this.actual;
            long e = 0L;
            while (true) {
                if (e != n) {
                    boolean b;
                    T t;
                    try {
                        t = Objects.requireNonNull(a.next(), "The iterator returned a null value");
                    }
                    catch (Throwable ex) {
                        s.onError(ex);
                        this.onCloseWithDropError();
                        return;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    boolean consumed = s.tryOnNext((T)t);
                    if (this.cancelled) {
                        return;
                    }
                    try {
                        b = a.hasNext();
                    }
                    catch (Throwable ex) {
                        s.onError(ex);
                        this.onCloseWithDropError();
                        return;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    if (!b) {
                        s.onComplete();
                        this.onCloseWithDropError();
                        return;
                    }
                    if (!consumed) continue;
                    ++e;
                    continue;
                }
                n = this.requested;
                if (n != e) continue;
                n = REQUESTED.addAndGet(this, -e);
                if (n == 0L) {
                    return;
                }
                e = 0L;
            }
        }

        void fastPath() {
            boolean b;
            Iterator<T> a = this.iterator;
            Fuseable.ConditionalSubscriber<? super T> s = this.actual;
            do {
                T t;
                if (this.cancelled) {
                    return;
                }
                try {
                    t = Objects.requireNonNull(a.next(), "The iterator returned a null value");
                }
                catch (Exception ex) {
                    s.onError(ex);
                    this.onCloseWithDropError();
                    return;
                }
                if (this.cancelled) {
                    return;
                }
                s.tryOnNext((T)t);
                if (this.cancelled) {
                    return;
                }
                try {
                    b = a.hasNext();
                }
                catch (Exception ex) {
                    s.onError(ex);
                    this.onCloseWithDropError();
                    return;
                }
                if (!this.cancelled) continue;
                return;
            } while (b);
            s.onComplete();
            this.onCloseWithDropError();
        }

        public void cancel() {
            this.onCloseWithDropError();
            this.cancelled = true;
            Operators.onDiscardMultiple(this.iterator, this.knownToBeFinite, this.actual.currentContext());
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.state == 2;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public void clear() {
            Operators.onDiscardMultiple(this.iterator, this.knownToBeFinite, this.actual.currentContext());
            this.state = 2;
        }

        @Override
        public boolean isEmpty() {
            boolean hasNext;
            int s = this.state;
            if (s == 2) {
                return true;
            }
            if (this.cancelled && !this.knownToBeFinite) {
                return true;
            }
            if (s == 1 || s == 0) {
                return false;
            }
            try {
                hasNext = this.iterator.hasNext();
            }
            catch (Throwable t) {
                this.state = 0;
                this.hasNextFailure = t;
                return false;
            }
            if (hasNext) {
                this.state = 0;
                return false;
            }
            this.state = 2;
            return true;
        }

        @Override
        @Nullable
        public T poll() {
            if (this.hasNextFailure != null) {
                this.state = 2;
                throw Exceptions.propagate(this.hasNextFailure);
            }
            if (!this.isEmpty()) {
                T c;
                if (this.state == 0) {
                    c = this.iterator.next();
                } else {
                    c = this.current;
                    this.current = null;
                }
                this.state = 3;
                return c;
            }
            this.onCloseWithDropError();
            return null;
        }

        @Override
        public int size() {
            if (this.state == 2) {
                return 0;
            }
            return 1;
        }
    }

    static final class IterableSubscription<T>
    implements InnerProducer<T>,
    Fuseable.SynchronousSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Iterator<? extends T> iterator;
        final boolean knownToBeFinite;
        final Runnable onClose;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<IterableSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(IterableSubscription.class, "requested");
        int state;
        static final int STATE_HAS_NEXT_NO_VALUE = 0;
        static final int STATE_HAS_NEXT_HAS_VALUE = 1;
        static final int STATE_NO_NEXT = 2;
        static final int STATE_CALL_HAS_NEXT = 3;
        T current;
        Throwable hasNextFailure;

        IterableSubscription(CoreSubscriber<? super T> actual, Iterator<? extends T> iterator, boolean knownToBeFinite, @Nullable Runnable onClose) {
            this.actual = actual;
            this.iterator = iterator;
            this.knownToBeFinite = knownToBeFinite;
            this.onClose = onClose;
        }

        IterableSubscription(CoreSubscriber<? super T> actual, Iterator<? extends T> iterator, boolean knownToBeFinite) {
            this(actual, iterator, knownToBeFinite, null);
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

        private void onCloseWithDropError() {
            if (this.onClose != null) {
                try {
                    this.onClose.run();
                }
                catch (Throwable t) {
                    Operators.onErrorDropped(t, this.actual.currentContext());
                }
            }
        }

        void slowPath(long n) {
            Iterator<T> a = this.iterator;
            CoreSubscriber<? super T> s = this.actual;
            long e = 0L;
            while (true) {
                if (e != n) {
                    boolean b;
                    T t;
                    try {
                        t = Objects.requireNonNull(a.next(), "The iterator returned a null value");
                    }
                    catch (Throwable ex) {
                        s.onError(ex);
                        this.onCloseWithDropError();
                        return;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    s.onNext(t);
                    if (this.cancelled) {
                        return;
                    }
                    try {
                        b = a.hasNext();
                    }
                    catch (Throwable ex) {
                        s.onError(ex);
                        this.onCloseWithDropError();
                        return;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    if (!b) {
                        s.onComplete();
                        this.onCloseWithDropError();
                        return;
                    }
                    ++e;
                    continue;
                }
                n = this.requested;
                if (n != e) continue;
                n = REQUESTED.addAndGet(this, -e);
                if (n == 0L) {
                    return;
                }
                e = 0L;
            }
        }

        void fastPath() {
            boolean b;
            Iterator<T> a = this.iterator;
            CoreSubscriber<? super T> s = this.actual;
            do {
                T t;
                if (this.cancelled) {
                    return;
                }
                try {
                    t = Objects.requireNonNull(a.next(), "The iterator returned a null value");
                }
                catch (Exception ex) {
                    s.onError(ex);
                    this.onCloseWithDropError();
                    return;
                }
                if (this.cancelled) {
                    return;
                }
                s.onNext(t);
                if (this.cancelled) {
                    return;
                }
                try {
                    b = a.hasNext();
                }
                catch (Exception ex) {
                    s.onError(ex);
                    this.onCloseWithDropError();
                    return;
                }
                if (!this.cancelled) continue;
                return;
            } while (b);
            s.onComplete();
            this.onCloseWithDropError();
        }

        public void cancel() {
            this.onCloseWithDropError();
            this.cancelled = true;
            Operators.onDiscardMultiple(this.iterator, this.knownToBeFinite, this.actual.currentContext());
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.state == 2;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public void clear() {
            Operators.onDiscardMultiple(this.iterator, this.knownToBeFinite, this.actual.currentContext());
            this.state = 2;
        }

        @Override
        public boolean isEmpty() {
            boolean hasNext;
            int s = this.state;
            if (s == 2) {
                return true;
            }
            if (this.cancelled && !this.knownToBeFinite) {
                return true;
            }
            if (s == 1 || s == 0) {
                return false;
            }
            try {
                hasNext = this.iterator.hasNext();
            }
            catch (Throwable t) {
                this.state = 0;
                this.hasNextFailure = t;
                return false;
            }
            if (hasNext) {
                this.state = 0;
                return false;
            }
            this.state = 2;
            return true;
        }

        @Override
        @Nullable
        public T poll() {
            if (this.hasNextFailure != null) {
                this.state = 2;
                throw Exceptions.propagate(this.hasNextFailure);
            }
            if (!this.isEmpty()) {
                T c;
                if (this.state == 0) {
                    c = this.iterator.next();
                } else {
                    c = this.current;
                    this.current = null;
                }
                this.state = 3;
                if (c == null) {
                    this.onCloseWithDropError();
                    throw new NullPointerException("iterator returned a null value");
                }
                return c;
            }
            this.onCloseWithDropError();
            return null;
        }

        @Override
        public int size() {
            if (this.state == 2) {
                return 0;
            }
            return 1;
        }
    }
}


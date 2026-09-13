/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.InternalEmptySink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

class SinkEmptyMulticast<T>
extends Mono<T>
implements InternalEmptySink<T> {
    volatile Inner<T>[] subscribers;
    static final AtomicReferenceFieldUpdater<SinkEmptyMulticast, Inner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(SinkEmptyMulticast.class, Inner[].class, "subscribers");
    static final Inner[] EMPTY = new Inner[0];
    static final Inner[] TERMINATED = new Inner[0];
    @Nullable
    Throwable error;

    SinkEmptyMulticast() {
        SUBSCRIBERS.lazySet(this, EMPTY);
    }

    @Override
    public int currentSubscriberCount() {
        return this.subscribers.length;
    }

    @Override
    public Mono<T> asMono() {
        return this;
    }

    @Override
    public Sinks.EmitResult tryEmitEmpty() {
        Inner[] array = SUBSCRIBERS.getAndSet(this, TERMINATED);
        if (array == TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        for (Inner as : array) {
            as.complete();
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable cause) {
        Objects.requireNonNull(cause, "onError cannot be null");
        Inner[] prevSubscribers = SUBSCRIBERS.getAndSet(this, TERMINATED);
        if (prevSubscribers == TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = cause;
        for (Inner as : prevSubscribers) {
            as.error(cause);
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED) {
            return this.subscribers == TERMINATED;
        }
        if (key == Scannable.Attr.ERROR) {
            return this.error;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    boolean add(Inner<T> ps) {
        Inner[] b;
        Inner<T>[] a;
        do {
            if ((a = this.subscribers) == TERMINATED) {
                return false;
            }
            int n = a.length;
            b = new Inner[n + 1];
            System.arraycopy(a, 0, b, 0, n);
            b[n] = ps;
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        return true;
    }

    void remove(Inner<T> ps) {
        Inner[] b;
        Inner<T>[] a;
        do {
            int n;
            if ((n = (a = this.subscribers).length) == 0) {
                return;
            }
            int j = -1;
            for (int i = 0; i < n; ++i) {
                if (a[i] != ps) continue;
                j = i;
                break;
            }
            if (j < 0) {
                return;
            }
            if (n == 1) {
                b = EMPTY;
                continue;
            }
            b = new Inner[n - 1];
            System.arraycopy(a, 0, b, 0, j);
            System.arraycopy(a, j + 1, b, j, n - j - 1);
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        VoidInner<T> as = new VoidInner<T>(actual, this);
        actual.onSubscribe(as);
        if (this.add(as)) {
            if (as.isCancelled()) {
                this.remove(as);
            }
        } else {
            Throwable ex = this.error;
            if (ex != null) {
                actual.onError(ex);
            } else {
                as.complete();
            }
        }
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.of(this.subscribers);
    }

    static final class VoidInner<T>
    extends AtomicBoolean
    implements Inner<T> {
        final SinkEmptyMulticast<T> parent;
        final CoreSubscriber<? super T> actual;

        VoidInner(CoreSubscriber<? super T> actual, SinkEmptyMulticast<T> parent) {
            this.actual = actual;
            this.parent = parent;
        }

        public void cancel() {
            if (this.getAndSet(true)) {
                return;
            }
            this.parent.remove(this);
        }

        @Override
        public boolean isCancelled() {
            return this.get();
        }

        public void request(long l) {
            Operators.validate(l);
        }

        @Override
        public void complete(T value) {
        }

        @Override
        public void complete() {
            if (this.get()) {
                return;
            }
            this.actual.onComplete();
        }

        @Override
        public void error(Throwable t) {
            if (this.get()) {
                Operators.onOperatorError(t, this.actual.currentContext());
                return;
            }
            this.actual.onError(t);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.get();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return Inner.super.scanUnsafe(key);
        }
    }

    static interface Inner<T>
    extends InnerProducer<T> {
        public void error(Throwable var1);

        public void complete(T var1);

        public void complete();

        public boolean isCancelled();
    }
}


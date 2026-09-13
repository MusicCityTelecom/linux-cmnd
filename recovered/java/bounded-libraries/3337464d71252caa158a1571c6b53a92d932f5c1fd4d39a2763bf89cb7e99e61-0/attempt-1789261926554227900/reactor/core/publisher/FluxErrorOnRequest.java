/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxErrorOnRequest<T>
extends Flux<T>
implements SourceProducer<T> {
    final Throwable error;

    FluxErrorOnRequest(Throwable error) {
        this.error = Objects.requireNonNull(error);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        actual.onSubscribe(new ErrorSubscription(actual, this.error));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class ErrorSubscription
    implements InnerProducer {
        final CoreSubscriber<?> actual;
        final Throwable error;
        volatile int once;
        static final AtomicIntegerFieldUpdater<ErrorSubscription> ONCE = AtomicIntegerFieldUpdater.newUpdater(ErrorSubscription.class, "once");

        ErrorSubscription(CoreSubscriber<?> actual, Throwable error) {
            this.actual = actual;
            this.error = error;
        }

        public void request(long n) {
            if (Operators.validate(n) && ONCE.compareAndSet(this, 0, 1)) {
                this.actual.onError(this.error);
            }
        }

        public void cancel() {
            this.once = 1;
        }

        public CoreSubscriber actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.CANCELLED || key == Scannable.Attr.TERMINATED) {
                return this.once == 1;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }
    }
}


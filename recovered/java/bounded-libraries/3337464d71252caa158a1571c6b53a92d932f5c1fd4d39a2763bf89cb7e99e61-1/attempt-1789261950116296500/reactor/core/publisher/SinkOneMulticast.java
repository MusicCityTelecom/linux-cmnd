/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InternalOneSink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SinkEmptyMulticast;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;

final class SinkOneMulticast<O>
extends SinkEmptyMulticast<O>
implements InternalOneSink<O> {
    @Nullable
    O value;

    SinkOneMulticast() {
    }

    @Override
    public Sinks.EmitResult tryEmitEmpty() {
        return this.tryEmitValue((O)null);
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable cause) {
        Objects.requireNonNull(cause, "onError cannot be null");
        SinkEmptyMulticast.Inner[] prevSubscribers = SUBSCRIBERS.getAndSet(this, TERMINATED);
        if (prevSubscribers == TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = cause;
        this.value = null;
        for (SinkEmptyMulticast.Inner as : prevSubscribers) {
            as.error(cause);
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public Sinks.EmitResult tryEmitValue(@Nullable O value) {
        SinkEmptyMulticast.Inner[] array = SUBSCRIBERS.getAndSet(this, TERMINATED);
        if (array == TERMINATED) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.value = value;
        if (value == null) {
            for (SinkEmptyMulticast.Inner as : array) {
                as.complete();
            }
        } else {
            for (SinkEmptyMulticast.Inner as : array) {
                as.complete(value);
            }
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
    public void subscribe(CoreSubscriber<? super O> actual) {
        NextInner<O> as = new NextInner<O>(actual, this);
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
                O v = this.value;
                if (v != null) {
                    as.complete(v);
                } else {
                    as.complete();
                }
            }
        }
    }

    @Override
    @Nullable
    public O block(Duration timeout) {
        if (timeout.isNegative()) {
            return (O)super.block(Duration.ZERO);
        }
        return (O)super.block(timeout);
    }

    static final class NextInner<T>
    extends Operators.MonoInnerProducerBase<T>
    implements SinkEmptyMulticast.Inner<T> {
        final SinkOneMulticast<T> parent;

        NextInner(CoreSubscriber<? super T> actual, SinkOneMulticast<T> parent) {
            super(actual);
            this.parent = parent;
        }

        @Override
        protected void doOnCancel() {
            this.parent.remove(this);
        }

        @Override
        public void error(Throwable t) {
            if (!this.isCancelled()) {
                this.actual().onError(t);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}


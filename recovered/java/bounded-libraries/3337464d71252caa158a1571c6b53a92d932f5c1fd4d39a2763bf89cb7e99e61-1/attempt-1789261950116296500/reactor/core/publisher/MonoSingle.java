/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.NoSuchElementException;
import java.util.Objects;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoSingle<T>
extends MonoFromFluxOperator<T, T> {
    final T defaultValue;
    final boolean completeOnEmpty;

    MonoSingle(Flux<? extends T> source) {
        super(source);
        this.defaultValue = null;
        this.completeOnEmpty = false;
    }

    MonoSingle(Flux<? extends T> source, @Nullable T defaultValue, boolean completeOnEmpty) {
        super(source);
        this.defaultValue = completeOnEmpty ? defaultValue : Objects.requireNonNull(defaultValue, "defaultValue");
        this.completeOnEmpty = completeOnEmpty;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new SingleSubscriber<T>(actual, this.defaultValue, this.completeOnEmpty);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SingleSubscriber<T>
    extends Operators.MonoInnerProducerBase<T>
    implements InnerConsumer<T> {
        @Nullable
        final T defaultValue;
        final boolean completeOnEmpty;
        Subscription s;
        int count;
        boolean done;

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public Context currentContext() {
            return this.actual().currentContext();
        }

        SingleSubscriber(CoreSubscriber<? super T> actual, @Nullable T defaultValue, boolean completeOnEmpty) {
            super(actual);
            this.defaultValue = defaultValue;
            this.completeOnEmpty = completeOnEmpty;
        }

        @Override
        public void doOnRequest(long n) {
            this.s.request(Long.MAX_VALUE);
        }

        @Override
        public void doOnCancel() {
            this.s.cancel();
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual().onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.isCancelled()) {
                this.discard(t);
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual().currentContext());
                return;
            }
            if (++this.count > 1) {
                this.discard(t);
                this.cancel();
                this.onError(new IndexOutOfBoundsException("Source emitted more than one item"));
            } else {
                this.setValue(t);
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual().currentContext());
                return;
            }
            this.done = true;
            this.discardTheValue();
            this.actual().onError(t);
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            int c = this.count;
            if (c == 0) {
                if (this.completeOnEmpty) {
                    this.actual().onComplete();
                    return;
                }
                T t = this.defaultValue;
                if (t != null) {
                    this.complete(t);
                } else {
                    this.actual().onError(Operators.onOperatorError(this, new NoSuchElementException("Source was empty"), this.actual().currentContext()));
                }
            } else if (c == 1) {
                this.complete();
            }
        }
    }
}


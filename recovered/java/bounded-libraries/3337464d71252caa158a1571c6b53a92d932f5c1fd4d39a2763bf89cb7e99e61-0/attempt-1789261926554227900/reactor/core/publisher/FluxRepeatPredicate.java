/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BooleanSupplier;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;

final class FluxRepeatPredicate<T>
extends InternalFluxOperator<T, T> {
    final BooleanSupplier predicate;

    FluxRepeatPredicate(Flux<? extends T> source, BooleanSupplier predicate) {
        super(source);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        RepeatPredicateSubscriber<T> parent = new RepeatPredicateSubscriber<T>(this.source, actual, this.predicate);
        actual.onSubscribe(parent);
        if (!parent.isCancelled()) {
            parent.resubscribe();
        }
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class RepeatPredicateSubscriber<T>
    extends Operators.MultiSubscriptionSubscriber<T, T> {
        final CorePublisher<? extends T> source;
        final BooleanSupplier predicate;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<RepeatPredicateSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(RepeatPredicateSubscriber.class, "wip");
        long produced;

        RepeatPredicateSubscriber(CorePublisher<? extends T> source, CoreSubscriber<? super T> actual, BooleanSupplier predicate) {
            super(actual);
            this.source = source;
            this.predicate = predicate;
        }

        public void onNext(T t) {
            ++this.produced;
            this.actual.onNext(t);
        }

        @Override
        public void onComplete() {
            boolean b;
            try {
                b = this.predicate.getAsBoolean();
            }
            catch (Throwable e) {
                this.actual.onError(Operators.onOperatorError(e, this.actual.currentContext()));
                return;
            }
            if (b) {
                this.resubscribe();
            } else {
                this.actual.onComplete();
            }
        }

        void resubscribe() {
            if (WIP.getAndIncrement(this) == 0) {
                do {
                    if (this.isCancelled()) {
                        return;
                    }
                    long c = this.produced;
                    if (c != 0L) {
                        this.produced = 0L;
                        this.produced(c);
                    }
                    this.source.subscribe(this);
                } while (WIP.decrementAndGet(this) != 0);
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


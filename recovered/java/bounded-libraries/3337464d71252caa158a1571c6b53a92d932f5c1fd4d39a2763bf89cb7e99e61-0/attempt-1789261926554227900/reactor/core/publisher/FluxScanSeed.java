/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxScanSeed<T, R>
extends InternalFluxOperator<T, R> {
    final BiFunction<R, ? super T, R> accumulator;
    final Supplier<R> initialSupplier;

    FluxScanSeed(Flux<? extends T> source, Supplier<R> initialSupplier, BiFunction<R, ? super T, R> accumulator) {
        super(source);
        this.accumulator = Objects.requireNonNull(accumulator, "accumulator");
        this.initialSupplier = Objects.requireNonNull(initialSupplier, "initialSupplier");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        ScanSeedCoordinator<T, R> coordinator = new ScanSeedCoordinator<T, R>(actual, this.source, this.accumulator, this.initialSupplier);
        actual.onSubscribe(coordinator);
        if (!coordinator.isCancelled()) {
            coordinator.onComplete();
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

    static final class ScanSeedSubscriber<T, R>
    implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        final BiFunction<R, ? super T, R> accumulator;
        Subscription s;
        R value;
        boolean done;

        ScanSeedSubscriber(CoreSubscriber<? super R> actual, BiFunction<R, ? super T, R> accumulator, R initialValue) {
            this.actual = actual;
            this.accumulator = accumulator;
            this.value = initialValue;
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        public void cancel() {
            this.s.cancel();
        }

        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onComplete();
        }

        public void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onError(t);
        }

        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            R r = this.value;
            try {
                r = Objects.requireNonNull(this.accumulator.apply(r, t), "The accumulator returned a null value");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t, this.actual.currentContext()));
                return;
            }
            this.actual.onNext(r);
            this.value = r;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void request(long n) {
            this.s.request(n);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static final class ScanSeedCoordinator<T, R>
    extends Operators.MultiSubscriptionSubscriber<R, R> {
        final Supplier<R> initialSupplier;
        final Flux<? extends T> source;
        final BiFunction<R, ? super T, R> accumulator;
        volatile int wip;
        long produced;
        private ScanSeedSubscriber<T, R> seedSubscriber;
        static final AtomicIntegerFieldUpdater<ScanSeedCoordinator> WIP = AtomicIntegerFieldUpdater.newUpdater(ScanSeedCoordinator.class, "wip");

        ScanSeedCoordinator(CoreSubscriber<? super R> actual, Flux<? extends T> source, BiFunction<R, ? super T, R> accumulator, Supplier<R> initialSupplier) {
            super(actual);
            this.source = source;
            this.accumulator = accumulator;
            this.initialSupplier = initialSupplier;
        }

        @Override
        public void onComplete() {
            if (WIP.getAndIncrement(this) == 0) {
                do {
                    if (this.isCancelled()) {
                        return;
                    }
                    if (null != this.seedSubscriber && this.subscription == this.seedSubscriber) {
                        this.actual.onComplete();
                        return;
                    }
                    long c = this.produced;
                    if (c != 0L) {
                        this.produced = 0L;
                        this.produced(c);
                    }
                    if (null == this.seedSubscriber) {
                        R initialValue;
                        try {
                            initialValue = Objects.requireNonNull(this.initialSupplier.get(), "The initial value supplied is null");
                        }
                        catch (Throwable e) {
                            this.onError(Operators.onOperatorError(e, this.actual.currentContext()));
                            return;
                        }
                        this.onSubscribe(Operators.scalarSubscription(this, initialValue));
                        this.seedSubscriber = new ScanSeedSubscriber<T, R>(this, this.accumulator, initialValue);
                    } else {
                        this.source.subscribe(this.seedSubscriber);
                    }
                    if (!this.isCancelled()) continue;
                    return;
                } while (WIP.decrementAndGet(this) != 0);
            }
        }

        public void onNext(R r) {
            ++this.produced;
            this.actual.onNext(r);
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


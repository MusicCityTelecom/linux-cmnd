/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Signal;
import reactor.util.annotation.Nullable;

final class MonoMaterialize<T>
extends InternalMonoOperator<T, Signal<T>> {
    MonoMaterialize(Mono<T> source) {
        super(source);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Signal<T>> actual) {
        return new MaterializeSubscriber(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class MaterializeSubscriber<T>
    implements InnerOperator<T, Signal<T>> {
        final CoreSubscriber<? super Signal<T>> actual;
        boolean alreadyReceivedSignalFromSource;
        Subscription s;
        volatile boolean requested;
        @Nullable
        volatile Signal<T> signalToReplayUponFirstRequest;

        MaterializeSubscriber(CoreSubscriber<? super Signal<T>> actual) {
            this.actual = actual;
        }

        @Override
        public CoreSubscriber<? super Signal<T>> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (this.alreadyReceivedSignalFromSource || !this.requested) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            this.alreadyReceivedSignalFromSource = true;
            Signal<T> signal = Signal.next(t, this.currentContext());
            this.actual.onNext(signal);
            this.actual.onComplete();
        }

        public void onError(Throwable throwable) {
            if (this.alreadyReceivedSignalFromSource) {
                Operators.onErrorDropped(throwable, this.currentContext());
                return;
            }
            this.alreadyReceivedSignalFromSource = true;
            this.signalToReplayUponFirstRequest = Signal.error(throwable, this.currentContext());
            this.drain();
        }

        public void onComplete() {
            if (this.alreadyReceivedSignalFromSource) {
                return;
            }
            this.alreadyReceivedSignalFromSource = true;
            this.signalToReplayUponFirstRequest = Signal.complete(this.currentContext());
            this.drain();
        }

        boolean drain() {
            Signal<T> signal = this.signalToReplayUponFirstRequest;
            if (signal != null && this.requested) {
                this.actual.onNext(signal);
                this.actual.onComplete();
                this.signalToReplayUponFirstRequest = null;
                return true;
            }
            return false;
        }

        public void request(long l) {
            if (!this.requested && Operators.validate(l)) {
                this.requested = true;
                if (this.drain()) {
                    return;
                }
                this.s.request(l);
            }
        }

        public void cancel() {
            this.s.cancel();
        }
    }
}


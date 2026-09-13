/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.DrainUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ImmutableSignal;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxMaterialize<T>
extends InternalFluxOperator<T, Signal<T>> {
    FluxMaterialize(Flux<T> source) {
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
    extends AbstractQueue<Signal<T>>
    implements InnerOperator<T, Signal<T>>,
    BooleanSupplier {
        final CoreSubscriber<? super Signal<T>> actual;
        final Context cachedContext;
        Signal<T> terminalSignal;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<MaterializeSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(MaterializeSubscriber.class, "requested");
        long produced;
        Subscription s;
        static final Signal empty = new ImmutableSignal<Object>(Context.empty(), SignalType.ON_NEXT, null, null, null);

        MaterializeSubscriber(CoreSubscriber<? super Signal<T>> subscriber) {
            this.actual = subscriber;
            this.cachedContext = this.actual.currentContext();
        }

        @Override
        public Context currentContext() {
            return this.cachedContext;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.terminalSignal != null;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.terminalSignal != null ? this.terminalSignal.getThrowable() : null;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.getAsBoolean();
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.size();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super Signal<T>> actual() {
            return this.actual;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T ev) {
            if (this.terminalSignal != null) {
                Operators.onNextDropped(ev, this.cachedContext);
                return;
            }
            ++this.produced;
            this.actual.onNext(Signal.next(ev, this.cachedContext));
        }

        public void onError(Throwable ev) {
            if (this.terminalSignal != null) {
                Operators.onErrorDropped(ev, this.cachedContext);
                return;
            }
            this.terminalSignal = Signal.error(ev, this.cachedContext);
            long p = this.produced;
            if (p != 0L) {
                Operators.addCap(REQUESTED, this, -p);
            }
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        public void onComplete() {
            if (this.terminalSignal != null) {
                return;
            }
            this.terminalSignal = Signal.complete(this.cachedContext);
            long p = this.produced;
            if (p != 0L) {
                Operators.addCap(REQUESTED, this, -p);
            }
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        public void request(long n) {
            if (Operators.validate(n) && !DrainUtils.postCompleteRequest(n, this.actual, this, REQUESTED, this, this)) {
                this.s.request(n);
            }
        }

        public void cancel() {
            if (this.cancelled) {
                return;
            }
            this.cancelled = true;
            this.s.cancel();
        }

        @Override
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override
        public boolean offer(Signal<T> e) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Nullable
        public Signal<T> poll() {
            Signal<T> v = this.terminalSignal;
            if (v != null && v != empty) {
                this.terminalSignal = empty;
                return v;
            }
            return null;
        }

        @Override
        @Nullable
        public Signal<T> peek() {
            return empty == this.terminalSignal ? null : this.terminalSignal;
        }

        @Override
        public Iterator<Signal<T>> iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return this.terminalSignal == null || this.terminalSignal == empty ? 0 : 1;
        }

        @Override
        public String toString() {
            return "MaterializeSubscriber";
        }
    }
}


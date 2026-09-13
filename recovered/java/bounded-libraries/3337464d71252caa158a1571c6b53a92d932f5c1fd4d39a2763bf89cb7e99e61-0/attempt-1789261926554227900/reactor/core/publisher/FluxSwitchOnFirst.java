/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BiFunction;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Signal;
import reactor.core.publisher.StateLogger;
import reactor.util.annotation.Nullable;

final class FluxSwitchOnFirst<T, R>
extends InternalFluxOperator<T, R> {
    final BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer;
    final boolean cancelSourceOnComplete;
    static final int HAS_FIRST_VALUE_RECEIVED_FLAG = 1;
    static final int HAS_INBOUND_SUBSCRIBED_ONCE_FLAG = 2;
    static final int HAS_INBOUND_SUBSCRIBER_SET_FLAG = 4;
    static final int HAS_INBOUND_REQUESTED_ONCE_FLAG = 8;
    static final int HAS_FIRST_VALUE_SENT_FLAG = 16;
    static final int HAS_INBOUND_CANCELLED_FLAG = 32;
    static final int HAS_INBOUND_CLOSED_PREMATURELY_FLAG = 64;
    static final int HAS_INBOUND_TERMINATED_FLAG = 128;
    static final int HAS_OUTBOUND_SUBSCRIBED_FLAG = 256;
    static final int HAS_OUTBOUND_CANCELLED_FLAG = 512;
    static final int HAS_OUTBOUND_TERMINATED_FLAG = 1024;

    FluxSwitchOnFirst(Flux<? extends T> source, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer, boolean cancelSourceOnComplete) {
        super(source);
        this.transformer = Objects.requireNonNull(transformer, "transformer");
        this.cancelSourceOnComplete = cancelSourceOnComplete;
    }

    @Override
    public int getPrefetch() {
        return 1;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new SwitchOnFirstConditionalMain<T, R>((Fuseable.ConditionalSubscriber)actual, this.transformer, this.cancelSourceOnComplete, null);
        }
        return new SwitchOnFirstMain<T, R>(actual, this.transformer, this.cancelSourceOnComplete);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static <T, R> long markFirstValueReceived(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundCancelled(state = instance.state) && !FluxSwitchOnFirst.hasInboundClosedPrematurely(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 1));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "mfvr", state, nextState);
        }
        return state;
    }

    static <T, R> long markInboundSubscribedOnce(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundSubscribedOnce(state = instance.state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 2));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "miso", state, nextState);
        }
        return state;
    }

    static <T, R> long markInboundSubscriberSet(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundCancelled(state = instance.state) && !FluxSwitchOnFirst.hasInboundClosedPrematurely(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 4));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "miss", state, nextState);
        }
        return state;
    }

    static <T, R> long markInboundRequestedOnce(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundCancelled(state = instance.state) && !FluxSwitchOnFirst.hasInboundClosedPrematurely(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 8));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "miro", state, nextState);
        }
        return state;
    }

    static <T, R> long markFirstValueSent(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundCancelled(state = instance.state) && !FluxSwitchOnFirst.hasInboundClosedPrematurely(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 0x10));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "mfvs", state, nextState);
        }
        return state;
    }

    static <T, R> long markInboundTerminated(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundCancelled(state = instance.state) && !FluxSwitchOnFirst.hasInboundClosedPrematurely(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 0x80));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "mitd", state, nextState);
        }
        return state;
    }

    static <T, R> long markInboundCancelled(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundCancelled(state = instance.state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 0x20));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "micd", state, nextState);
        }
        return state;
    }

    static <T, R> long markInboundClosedPrematurely(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundTerminated(state = instance.state) && !FluxSwitchOnFirst.hasInboundCancelled(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 0x40));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "micp", state, nextState);
        }
        return state;
    }

    static <T, R> long markInboundCancelledAndOutboundTerminated(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasInboundCancelled(state = instance.state) && !FluxSwitchOnFirst.hasOutboundCancelled(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 0x20 | 0x400));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "icot", state, nextState);
        }
        return state;
    }

    static <T, R> long markOutboundSubscribed(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasOutboundCancelled(state = instance.state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 0x100));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "mosd", state, nextState);
        }
        return state;
    }

    static <T, R> long markOutboundTerminated(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasOutboundCancelled(state = instance.state) && !FluxSwitchOnFirst.hasOutboundTerminated(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 0x400));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "motd", state, nextState);
        }
        return state;
    }

    static <T, R> long markOutboundCancelled(AbstractSwitchOnFirstMain<T, R> instance) {
        int nextState;
        int state;
        do {
            if (!FluxSwitchOnFirst.hasOutboundTerminated(state = instance.state) && !FluxSwitchOnFirst.hasOutboundCancelled(state)) continue;
            return state;
        } while (!AbstractSwitchOnFirstMain.STATE.compareAndSet(instance, state, nextState = state | 0x200));
        if (instance.logger != null) {
            instance.logger.log(instance.toString(), "mocd", state, nextState);
        }
        return state;
    }

    static boolean hasInboundCancelled(long state) {
        return (state & 0x20L) == 32L;
    }

    static boolean hasInboundClosedPrematurely(long state) {
        return (state & 0x40L) == 64L;
    }

    static boolean hasInboundTerminated(long state) {
        return (state & 0x80L) == 128L;
    }

    static boolean hasFirstValueReceived(long state) {
        return (state & 1L) == 1L;
    }

    static boolean hasFirstValueSent(long state) {
        return (state & 0x10L) == 16L;
    }

    static boolean hasInboundSubscribedOnce(long state) {
        return (state & 2L) == 2L;
    }

    static boolean hasInboundSubscriberSet(long state) {
        return (state & 4L) == 4L;
    }

    static boolean hasInboundRequestedOnce(long state) {
        return (state & 8L) == 8L;
    }

    static boolean hasOutboundSubscribed(long state) {
        return (state & 0x100L) == 256L;
    }

    static boolean hasOutboundCancelled(long state) {
        return (state & 0x200L) == 512L;
    }

    static boolean hasOutboundTerminated(long state) {
        return (state & 0x400L) == 1024L;
    }

    static final class SwitchOnFirstConditionalControlSubscriber<T>
    extends SwitchOnFirstControlSubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> delegate;

        SwitchOnFirstConditionalControlSubscriber(AbstractSwitchOnFirstMain<?, T> parent, Fuseable.ConditionalSubscriber<? super T> delegate, boolean cancelSourceOnComplete) {
            super(parent, delegate, cancelSourceOnComplete);
            this.delegate = delegate;
        }

        @Override
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.currentContext());
                return true;
            }
            return this.delegate.tryOnNext(t);
        }
    }

    static class SwitchOnFirstControlSubscriber<T>
    extends Operators.DeferredSubscription
    implements InnerOperator<T, T>,
    CoreSubscriber<T> {
        final AbstractSwitchOnFirstMain<?, T> parent;
        final CoreSubscriber<? super T> delegate;
        final boolean cancelSourceOnComplete;
        boolean done;

        SwitchOnFirstControlSubscriber(AbstractSwitchOnFirstMain<?, T> parent, CoreSubscriber<? super T> delegate, boolean cancelSourceOnComplete) {
            this.parent = parent;
            this.delegate = delegate;
            this.cancelSourceOnComplete = cancelSourceOnComplete;
        }

        final void sendSubscription() {
            this.delegate.onSubscribe(this);
        }

        @Override
        public final void onSubscribe(Subscription s) {
            long previousState;
            if (this.set(s) && FluxSwitchOnFirst.hasOutboundCancelled(previousState = FluxSwitchOnFirst.markOutboundSubscribed(this.parent))) {
                s.cancel();
            }
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.delegate;
        }

        public final void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            this.delegate.onNext(t);
        }

        public final void onError(Throwable throwable) {
            if (this.done) {
                Operators.onErrorDropped(throwable, this.currentContext());
                return;
            }
            this.done = true;
            AbstractSwitchOnFirstMain<?, T> parent = this.parent;
            long previousState = FluxSwitchOnFirst.markOutboundTerminated(parent);
            if (FluxSwitchOnFirst.hasOutboundCancelled(previousState) || FluxSwitchOnFirst.hasOutboundTerminated(previousState)) {
                Operators.onErrorDropped(throwable, this.delegate.currentContext());
                return;
            }
            if (!FluxSwitchOnFirst.hasInboundCancelled(previousState) && !FluxSwitchOnFirst.hasInboundTerminated(previousState)) {
                parent.cancelAndError();
            }
            this.delegate.onError(throwable);
        }

        final void errorDirectly(Throwable t) {
            this.done = true;
            this.delegate.onError(t);
        }

        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            AbstractSwitchOnFirstMain<?, T> parent = this.parent;
            long previousState = FluxSwitchOnFirst.markOutboundTerminated(parent);
            if (this.cancelSourceOnComplete && !FluxSwitchOnFirst.hasInboundCancelled(previousState) && !FluxSwitchOnFirst.hasInboundTerminated(previousState)) {
                parent.cancelAndError();
            }
            this.delegate.onComplete();
        }

        @Override
        public final void cancel() {
            boolean shouldCancelInbound;
            Operators.DeferredSubscription.REQUESTED.lazySet(this, -2L);
            long previousState = FluxSwitchOnFirst.markOutboundCancelled(this.parent);
            if (FluxSwitchOnFirst.hasOutboundCancelled(previousState) || FluxSwitchOnFirst.hasOutboundTerminated(previousState)) {
                return;
            }
            boolean bl = shouldCancelInbound = !FluxSwitchOnFirst.hasInboundTerminated(previousState) && !FluxSwitchOnFirst.hasInboundCancelled(previousState);
            if (!FluxSwitchOnFirst.hasOutboundSubscribed(previousState)) {
                if (shouldCancelInbound) {
                    this.parent.cancel();
                }
                return;
            }
            this.s.cancel();
            if (shouldCancelInbound) {
                this.parent.cancelAndError();
            }
        }

        @Override
        public final Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.delegate;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return FluxSwitchOnFirst.hasOutboundCancelled(this.parent.state);
            }
            if (key == Scannable.Attr.TERMINATED) {
                return FluxSwitchOnFirst.hasOutboundTerminated(this.parent.state);
            }
            return null;
        }
    }

    static final class SwitchOnFirstConditionalMain<T, R>
    extends AbstractSwitchOnFirstMain<T, R>
    implements Fuseable.ConditionalSubscriber<T> {
        SwitchOnFirstConditionalMain(Fuseable.ConditionalSubscriber<? super R> outer, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer, boolean cancelSourceOnComplete) {
            super(outer, transformer, cancelSourceOnComplete, null);
        }

        SwitchOnFirstConditionalMain(Fuseable.ConditionalSubscriber<? super R> outer, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer, boolean cancelSourceOnComplete, @Nullable StateLogger logger) {
            super(outer, transformer, cancelSourceOnComplete, logger);
        }

        @Override
        CoreSubscriber<? super T> convert(CoreSubscriber<? super T> inboundSubscriber) {
            return Operators.toConditionalSubscriber(inboundSubscriber);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean tryOnNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.currentContext());
                return false;
            }
            if (!this.isFirstOnNextReceivedOnce) {
                Publisher result;
                this.isFirstOnNextReceivedOnce = true;
                this.firstValue = t;
                long previousState = FluxSwitchOnFirst.markFirstValueReceived(this);
                if (FluxSwitchOnFirst.hasInboundCancelled(previousState)) {
                    this.firstValue = null;
                    Operators.onDiscard(t, this.outboundSubscriber.currentContext());
                    return true;
                }
                SwitchOnFirstControlSubscriber o = this.outboundSubscriber;
                try {
                    Signal<T> signal = Signal.next(t, o.currentContext());
                    result = (Publisher)Objects.requireNonNull(this.transformer.apply(signal, this), "The transformer returned a null value");
                }
                catch (Throwable e) {
                    this.done = true;
                    previousState = FluxSwitchOnFirst.markInboundCancelledAndOutboundTerminated(this);
                    if (FluxSwitchOnFirst.hasInboundCancelled(previousState) || FluxSwitchOnFirst.hasOutboundCancelled(previousState)) {
                        Operators.onErrorDropped(e, o.currentContext());
                        return true;
                    }
                    this.firstValue = null;
                    Operators.onDiscard(t, o.currentContext());
                    o.errorDirectly(Operators.onOperatorError(this.s, e, t, o.currentContext()));
                    return true;
                }
                result.subscribe((Subscriber)o);
                return true;
            }
            SwitchOnFirstConditionalMain switchOnFirstConditionalMain = this;
            synchronized (switchOnFirstConditionalMain) {
                return ((Fuseable.ConditionalSubscriber)this.inboundSubscriber).tryOnNext(t);
            }
        }

        @Override
        boolean tryDirectSend(CoreSubscriber<? super T> actual, T t) {
            return ((Fuseable.ConditionalSubscriber)actual).tryOnNext(t);
        }
    }

    static final class SwitchOnFirstMain<T, R>
    extends AbstractSwitchOnFirstMain<T, R> {
        SwitchOnFirstMain(CoreSubscriber<? super R> outer, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer, boolean cancelSourceOnComplete) {
            super(outer, transformer, cancelSourceOnComplete, null);
        }

        SwitchOnFirstMain(CoreSubscriber<? super R> outer, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer, boolean cancelSourceOnComplete, @Nullable StateLogger logger) {
            super(outer, transformer, cancelSourceOnComplete, logger);
        }

        @Override
        CoreSubscriber<? super T> convert(CoreSubscriber<? super T> inboundSubscriber) {
            return inboundSubscriber;
        }

        @Override
        boolean tryDirectSend(CoreSubscriber<? super T> actual, T t) {
            actual.onNext(t);
            return true;
        }
    }

    static abstract class AbstractSwitchOnFirstMain<T, R>
    extends Flux<T>
    implements InnerOperator<T, R> {
        @Nullable
        final StateLogger logger;
        final SwitchOnFirstControlSubscriber<? super R> outboundSubscriber;
        final BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer;
        Subscription s;
        boolean isInboundRequestedOnce;
        boolean isFirstOnNextReceivedOnce;
        T firstValue;
        Throwable throwable;
        boolean done;
        CoreSubscriber<? super T> inboundSubscriber;
        volatile int state;
        static final AtomicIntegerFieldUpdater<AbstractSwitchOnFirstMain> STATE = AtomicIntegerFieldUpdater.newUpdater(AbstractSwitchOnFirstMain.class, "state");

        AbstractSwitchOnFirstMain(CoreSubscriber<? super R> outboundSubscriber, BiFunction<Signal<? extends T>, Flux<T>, Publisher<? extends R>> transformer, boolean cancelSourceOnComplete, @Nullable StateLogger logger) {
            this.outboundSubscriber = outboundSubscriber instanceof Fuseable.ConditionalSubscriber ? new SwitchOnFirstConditionalControlSubscriber(this, (Fuseable.ConditionalSubscriber)outboundSubscriber, cancelSourceOnComplete) : new SwitchOnFirstControlSubscriber<R>(this, outboundSubscriber, cancelSourceOnComplete);
            this.transformer = transformer;
            this.logger = logger;
        }

        @Override
        @Nullable
        public final Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return FluxSwitchOnFirst.hasInboundCancelled(this.state) || FluxSwitchOnFirst.hasInboundClosedPrematurely(this.state);
            }
            if (key == Scannable.Attr.TERMINATED) {
                return FluxSwitchOnFirst.hasInboundTerminated(this.state) || FluxSwitchOnFirst.hasInboundClosedPrematurely(this.state);
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public final CoreSubscriber<? super R> actual() {
            return this.outboundSubscriber;
        }

        @Override
        public final void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.outboundSubscriber.sendSubscription();
                if (!FluxSwitchOnFirst.hasInboundCancelled(this.state)) {
                    s.request(1L);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public final void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            if (!this.isFirstOnNextReceivedOnce) {
                Publisher<? extends R> outboundPublisher;
                this.isFirstOnNextReceivedOnce = true;
                this.firstValue = t;
                long previousState = FluxSwitchOnFirst.markFirstValueReceived(this);
                if (FluxSwitchOnFirst.hasInboundCancelled(previousState) || FluxSwitchOnFirst.hasInboundClosedPrematurely(previousState)) {
                    this.firstValue = null;
                    Operators.onDiscard(t, this.outboundSubscriber.currentContext());
                    return;
                }
                SwitchOnFirstControlSubscriber<? super R> o = this.outboundSubscriber;
                try {
                    Signal<T> signal = Signal.next(t, o.currentContext());
                    outboundPublisher = Objects.requireNonNull(this.transformer.apply(signal, this), "The transformer returned a null value");
                }
                catch (Throwable e) {
                    this.done = true;
                    previousState = FluxSwitchOnFirst.markInboundCancelledAndOutboundTerminated(this);
                    if (FluxSwitchOnFirst.hasInboundCancelled(previousState) || FluxSwitchOnFirst.hasOutboundCancelled(previousState)) {
                        Operators.onErrorDropped(e, o.currentContext());
                        return;
                    }
                    this.firstValue = null;
                    Operators.onDiscard(t, o.currentContext());
                    o.errorDirectly(Operators.onOperatorError(this.s, e, t, o.currentContext()));
                    return;
                }
                outboundPublisher.subscribe(o);
                return;
            }
            AbstractSwitchOnFirstMain abstractSwitchOnFirstMain = this;
            synchronized (abstractSwitchOnFirstMain) {
                this.inboundSubscriber.onNext(t);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public final void onError(Throwable t) {
            if (this.done) {
                Operators.onErrorDropped(t, this.outboundSubscriber.currentContext());
                return;
            }
            this.done = true;
            this.throwable = t;
            long previousState = FluxSwitchOnFirst.markInboundTerminated(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(previousState) || FluxSwitchOnFirst.hasInboundTerminated(previousState) || FluxSwitchOnFirst.hasInboundClosedPrematurely(previousState)) {
                Operators.onErrorDropped(t, this.outboundSubscriber.currentContext());
                return;
            }
            if (FluxSwitchOnFirst.hasFirstValueSent(previousState)) {
                AbstractSwitchOnFirstMain abstractSwitchOnFirstMain = this;
                synchronized (abstractSwitchOnFirstMain) {
                    this.inboundSubscriber.onError(t);
                }
                return;
            }
            if (!FluxSwitchOnFirst.hasFirstValueReceived(previousState)) {
                Publisher<? extends R> result;
                SwitchOnFirstControlSubscriber<? super R> o = this.outboundSubscriber;
                try {
                    Signal signal = Signal.error(t, o.currentContext());
                    result = Objects.requireNonNull(this.transformer.apply(signal, this), "The transformer returned a null value");
                }
                catch (Throwable e) {
                    o.onError(Exceptions.addSuppressed(t, e));
                    return;
                }
                result.subscribe(o);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public final void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            long previousState = FluxSwitchOnFirst.markInboundTerminated(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(previousState) || FluxSwitchOnFirst.hasInboundTerminated(previousState) || FluxSwitchOnFirst.hasInboundClosedPrematurely(previousState)) {
                return;
            }
            if (FluxSwitchOnFirst.hasFirstValueSent(previousState)) {
                AbstractSwitchOnFirstMain abstractSwitchOnFirstMain = this;
                synchronized (abstractSwitchOnFirstMain) {
                    this.inboundSubscriber.onComplete();
                }
                return;
            }
            if (!FluxSwitchOnFirst.hasFirstValueReceived(previousState)) {
                Publisher<? extends R> result;
                SwitchOnFirstControlSubscriber<? super R> o = this.outboundSubscriber;
                try {
                    Signal signal = Signal.complete(o.currentContext());
                    result = Objects.requireNonNull(this.transformer.apply(signal, this), "The transformer returned a null value");
                }
                catch (Throwable e) {
                    o.onError(e);
                    return;
                }
                result.subscribe(o);
            }
        }

        public final void cancel() {
            long previousState = FluxSwitchOnFirst.markInboundCancelled(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(previousState) || FluxSwitchOnFirst.hasInboundTerminated(previousState) || FluxSwitchOnFirst.hasInboundClosedPrematurely(previousState)) {
                return;
            }
            this.s.cancel();
            if (FluxSwitchOnFirst.hasFirstValueReceived(previousState) && !FluxSwitchOnFirst.hasInboundRequestedOnce(previousState)) {
                T f = this.firstValue;
                this.firstValue = null;
                Operators.onDiscard(f, this.currentContext());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        final void cancelAndError() {
            long previousState = FluxSwitchOnFirst.markInboundClosedPrematurely(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(previousState) || FluxSwitchOnFirst.hasInboundTerminated(previousState)) {
                return;
            }
            this.s.cancel();
            if (FluxSwitchOnFirst.hasFirstValueReceived(previousState) && !FluxSwitchOnFirst.hasFirstValueSent(previousState)) {
                if (!FluxSwitchOnFirst.hasInboundRequestedOnce(previousState)) {
                    T f = this.firstValue;
                    this.firstValue = null;
                    Operators.onDiscard(f, this.currentContext());
                    if (FluxSwitchOnFirst.hasInboundSubscriberSet(previousState)) {
                        this.inboundSubscriber.onError(new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                    }
                }
                return;
            }
            if (FluxSwitchOnFirst.hasInboundSubscriberSet(previousState)) {
                AbstractSwitchOnFirstMain abstractSwitchOnFirstMain = this;
                synchronized (abstractSwitchOnFirstMain) {
                    this.inboundSubscriber.onError(new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                }
            }
        }

        public final void request(long n) {
            if (Operators.validate(n)) {
                if (!this.isInboundRequestedOnce) {
                    this.isInboundRequestedOnce = true;
                    if (this.isFirstOnNextReceivedOnce) {
                        long previousState = FluxSwitchOnFirst.markInboundRequestedOnce(this);
                        if (FluxSwitchOnFirst.hasInboundCancelled(previousState) || FluxSwitchOnFirst.hasInboundClosedPrematurely(previousState)) {
                            return;
                        }
                        T first = this.firstValue;
                        this.firstValue = null;
                        boolean wasDelivered = this.sendFirst(first);
                        if (wasDelivered && n != Long.MAX_VALUE) {
                            if (--n > 0L) {
                                this.s.request(n);
                            }
                            return;
                        }
                    }
                }
                this.s.request(n);
            }
        }

        @Override
        public final void subscribe(CoreSubscriber<? super T> inboundSubscriber) {
            long previousState = FluxSwitchOnFirst.markInboundSubscribedOnce(this);
            if (FluxSwitchOnFirst.hasInboundSubscribedOnce(previousState)) {
                Operators.error(inboundSubscriber, new IllegalStateException("FluxSwitchOnFirst allows only one Subscriber"));
                return;
            }
            if (FluxSwitchOnFirst.hasInboundClosedPrematurely(previousState)) {
                Operators.error(inboundSubscriber, new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                return;
            }
            if (!FluxSwitchOnFirst.hasFirstValueReceived(previousState)) {
                Throwable t = this.throwable;
                if (t != null) {
                    Operators.error(inboundSubscriber, t);
                } else {
                    Operators.complete(inboundSubscriber);
                }
                return;
            }
            this.inboundSubscriber = this.convert(inboundSubscriber);
            inboundSubscriber.onSubscribe(this);
            previousState = FluxSwitchOnFirst.markInboundSubscriberSet(this);
            if (FluxSwitchOnFirst.hasInboundClosedPrematurely(previousState) && (!FluxSwitchOnFirst.hasInboundRequestedOnce(previousState) || FluxSwitchOnFirst.hasFirstValueSent(previousState)) && !FluxSwitchOnFirst.hasInboundCancelled(previousState)) {
                inboundSubscriber.onError(new CancellationException("FluxSwitchOnFirst has already been cancelled"));
            }
        }

        abstract CoreSubscriber<? super T> convert(CoreSubscriber<? super T> var1);

        final boolean sendFirst(T firstValue) {
            CoreSubscriber<? super T> a = this.inboundSubscriber;
            boolean sent = this.tryDirectSend(a, firstValue);
            long previousState = FluxSwitchOnFirst.markFirstValueSent(this);
            if (FluxSwitchOnFirst.hasInboundCancelled(previousState)) {
                return sent;
            }
            if (FluxSwitchOnFirst.hasInboundClosedPrematurely(previousState)) {
                a.onError(new CancellationException("FluxSwitchOnFirst has already been cancelled"));
                return sent;
            }
            if (FluxSwitchOnFirst.hasInboundTerminated(previousState)) {
                Throwable t = this.throwable;
                if (t != null) {
                    a.onError(t);
                } else {
                    a.onComplete();
                }
            }
            return sent;
        }

        abstract boolean tryDirectSend(CoreSubscriber<? super T> var1, T var2);
    }
}


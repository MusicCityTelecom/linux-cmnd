/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxGroupJoin<TLeft, TRight, TLeftEnd, TRightEnd, R>
extends InternalFluxOperator<TLeft, R> {
    final Publisher<? extends TRight> other;
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
    final BiFunction<? super TLeft, ? super Flux<TRight>, ? extends R> resultSelector;
    final Supplier<? extends Queue<TRight>> processorQueueSupplier;

    FluxGroupJoin(Flux<TLeft> source, Publisher<? extends TRight> other, Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd, Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, BiFunction<? super TLeft, ? super Flux<TRight>, ? extends R> resultSelector, Supplier<? extends Queue<Object>> queueSupplier, Supplier<? extends Queue<TRight>> processorQueueSupplier) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
        this.leftEnd = Objects.requireNonNull(leftEnd, "leftEnd");
        this.rightEnd = Objects.requireNonNull(rightEnd, "rightEnd");
        this.processorQueueSupplier = Objects.requireNonNull(processorQueueSupplier, "processorQueueSupplier");
        this.resultSelector = Objects.requireNonNull(resultSelector, "resultSelector");
    }

    @Override
    public CoreSubscriber<? super TLeft> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        GroupJoinSubscription parent = new GroupJoinSubscription(actual, this.leftEnd, this.rightEnd, this.resultSelector, this.processorQueueSupplier);
        actual.onSubscribe(parent);
        LeftRightSubscriber left = new LeftRightSubscriber(parent, true);
        parent.cancellations.add(left);
        LeftRightSubscriber right = new LeftRightSubscriber(parent, false);
        parent.cancellations.add(right);
        this.source.subscribe(left);
        this.other.subscribe((Subscriber)right);
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class LeftRightEndSubscriber
    implements InnerConsumer<Object>,
    Disposable {
        final JoinSupport<?> parent;
        final boolean isLeft;
        final int index;
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<LeftRightEndSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(LeftRightEndSubscriber.class, Subscription.class, "subscription");

        LeftRightEndSubscriber(JoinSupport<?> parent, boolean isLeft, int index) {
            this.parent = parent;
            this.isLeft = isLeft;
            this.index = index;
        }

        @Override
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public boolean isDisposed() {
            return Operators.cancelledSubscription() == this.subscription;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUBSCRIPTION, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object t) {
            if (Operators.terminate(SUBSCRIPTION, this)) {
                this.parent.innerClose(this.isLeft, this);
            }
        }

        public void onError(Throwable t) {
            this.parent.innerError(t);
        }

        public void onComplete() {
            this.parent.innerClose(this.isLeft, this);
        }

        @Override
        public Context currentContext() {
            return this.parent.actual().currentContext();
        }
    }

    static final class LeftRightSubscriber
    implements InnerConsumer<Object>,
    Disposable {
        final JoinSupport<?> parent;
        final boolean isLeft;
        volatile Subscription subscription;
        static final AtomicReferenceFieldUpdater<LeftRightSubscriber, Subscription> SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(LeftRightSubscriber.class, Subscription.class, "subscription");

        LeftRightSubscriber(JoinSupport<?> parent, boolean isLeft) {
            this.parent = parent;
            this.isLeft = isLeft;
        }

        @Override
        public void dispose() {
            Operators.terminate(SUBSCRIPTION, this);
        }

        @Override
        public Context currentContext() {
            return this.parent.actual().currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.subscription;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public boolean isDisposed() {
            return Operators.cancelledSubscription() == this.subscription;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.setOnce(SUBSCRIPTION, this, s)) {
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object t) {
            this.parent.innerValue(this.isLeft, t);
        }

        public void onError(Throwable t) {
            this.parent.innerError(t);
        }

        public void onComplete() {
            this.parent.innerComplete(this);
        }
    }

    static final class GroupJoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R>
    implements JoinSupport<R> {
        final Queue<Object> queue;
        final BiPredicate<Object, Object> queueBiOffer;
        final Disposable.Composite cancellations;
        final Map<Integer, Sinks.Many<TRight>> lefts;
        final Map<Integer, TRight> rights;
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
        final BiFunction<? super TLeft, ? super Flux<TRight>, ? extends R> resultSelector;
        final Supplier<? extends Queue<TRight>> processorQueueSupplier;
        final CoreSubscriber<? super R> actual;
        int leftIndex;
        int rightIndex;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<GroupJoinSubscription> WIP = AtomicIntegerFieldUpdater.newUpdater(GroupJoinSubscription.class, "wip");
        volatile int active;
        static final AtomicIntegerFieldUpdater<GroupJoinSubscription> ACTIVE = AtomicIntegerFieldUpdater.newUpdater(GroupJoinSubscription.class, "active");
        volatile long requested;
        static final AtomicLongFieldUpdater<GroupJoinSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(GroupJoinSubscription.class, "requested");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<GroupJoinSubscription, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(GroupJoinSubscription.class, Throwable.class, "error");
        static final Integer LEFT_VALUE = 1;
        static final Integer RIGHT_VALUE = 2;
        static final Integer LEFT_CLOSE = 3;
        static final Integer RIGHT_CLOSE = 4;

        GroupJoinSubscription(CoreSubscriber<? super R> actual, Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd, Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, BiFunction<? super TLeft, ? super Flux<TRight>, ? extends R> resultSelector, Supplier<? extends Queue<TRight>> processorQueueSupplier) {
            this.actual = actual;
            this.cancellations = Disposables.composite();
            this.processorQueueSupplier = processorQueueSupplier;
            this.queue = Queues.unboundedMultiproducer().get();
            this.queueBiOffer = (BiPredicate)((Object)this.queue);
            this.lefts = new LinkedHashMap<Integer, Sinks.Many<TRight>>();
            this.rights = new LinkedHashMap<Integer, TRight>();
            this.leftEnd = leftEnd;
            this.rightEnd = rightEnd;
            this.resultSelector = resultSelector;
            ACTIVE.lazySet(this, 2);
        }

        @Override
        public final CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.concat(this.lefts.values().stream().map(Scannable::from), Scannable.from(this.cancellations).inners());
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancellations.isDisposed();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size() / 2;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.active == 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            return JoinSupport.super.scanUnsafe(key);
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
            }
        }

        public void cancel() {
            if (this.cancellations.isDisposed()) {
                return;
            }
            this.cancellations.dispose();
            if (WIP.getAndIncrement(this) == 0) {
                this.queue.clear();
            }
        }

        void errorAll(Subscriber<?> a) {
            Throwable ex = Exceptions.terminate(ERROR, this);
            for (Sinks.Many<TRight> up : this.lefts.values()) {
                up.emitError(ex, Sinks.EmitFailureHandler.FAIL_FAST);
            }
            this.lefts.clear();
            this.rights.clear();
            a.onError(ex);
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            Queue<Object> q = this.queue;
            CoreSubscriber<? super R> a = this.actual;
            block6: while (true) {
                boolean empty;
                if (this.cancellations.isDisposed()) {
                    q.clear();
                    return;
                }
                Throwable ex = this.error;
                if (ex != null) {
                    q.clear();
                    this.cancellations.dispose();
                    this.errorAll(a);
                    return;
                }
                boolean d = this.active == 0;
                Integer mode = (Integer)q.poll();
                boolean bl = empty = mode == null;
                if (d && empty) {
                    for (Sinks.Many<TRight> up : this.lefts.values()) {
                        up.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                    }
                    this.lefts.clear();
                    this.rights.clear();
                    this.cancellations.dispose();
                    a.onComplete();
                    return;
                }
                if (!empty) {
                    LeftRightEndSubscriber end;
                    Object val = q.poll();
                    if (mode == LEFT_VALUE) {
                        R w;
                        Publisher<TLeftEnd> p;
                        int idx;
                        Object left = val;
                        Sinks.Many<TRight> up = Sinks.unsafe().many().unicast().onBackpressureBuffer(this.processorQueueSupplier.get());
                        ++this.leftIndex;
                        this.lefts.put(idx, up);
                        try {
                            p = Objects.requireNonNull(this.leftEnd.apply(left), "The leftEnd returned a null Publisher");
                        }
                        catch (Throwable exc) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, exc, left, this.actual.currentContext()));
                            this.errorAll(a);
                            return;
                        }
                        LeftRightEndSubscriber end2 = new LeftRightEndSubscriber(this, true, idx);
                        this.cancellations.add(end2);
                        p.subscribe((Subscriber)end2);
                        ex = this.error;
                        if (ex != null) {
                            this.cancellations.dispose();
                            q.clear();
                            this.errorAll(a);
                            return;
                        }
                        try {
                            w = Objects.requireNonNull(this.resultSelector.apply(left, up.asFlux()), "The resultSelector returned a null value");
                        }
                        catch (Throwable exc) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, exc, up, this.actual.currentContext()));
                            this.errorAll(a);
                            return;
                        }
                        long r = this.requested;
                        if (r == 0L) {
                            Exceptions.addThrowable(ERROR, this, Exceptions.failWithOverflow());
                            this.errorAll(a);
                            return;
                        }
                        a.onNext(w);
                        Operators.produced(REQUESTED, this, 1L);
                        Iterator<TRight> iterator = this.rights.values().iterator();
                        while (true) {
                            if (!iterator.hasNext()) continue block6;
                            TRight right = iterator.next();
                            up.emitNext(right, Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                    }
                    if (mode == RIGHT_VALUE) {
                        Publisher<TRightEnd> p;
                        int idx;
                        Object right = val;
                        ++this.rightIndex;
                        this.rights.put(idx, right);
                        try {
                            p = Objects.requireNonNull(this.rightEnd.apply(right), "The rightEnd returned a null Publisher");
                        }
                        catch (Throwable exc) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, exc, right, this.actual.currentContext()));
                            this.errorAll(a);
                            return;
                        }
                        LeftRightEndSubscriber end3 = new LeftRightEndSubscriber(this, false, idx);
                        this.cancellations.add(end3);
                        p.subscribe((Subscriber)end3);
                        ex = this.error;
                        if (ex != null) {
                            q.clear();
                            this.cancellations.dispose();
                            this.errorAll(a);
                            return;
                        }
                        Iterator<Sinks.Many<TRight>> iterator = this.lefts.values().iterator();
                        while (true) {
                            if (!iterator.hasNext()) continue block6;
                            Sinks.Many<TRight> up = iterator.next();
                            up.emitNext(right, Sinks.EmitFailureHandler.FAIL_FAST);
                        }
                    }
                    if (mode == LEFT_CLOSE) {
                        end = (LeftRightEndSubscriber)val;
                        Sinks.Many<TRight> up = this.lefts.remove(end.index);
                        this.cancellations.remove(end);
                        if (up == null) continue;
                        up.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
                        continue;
                    }
                    if (mode != RIGHT_CLOSE) continue;
                    end = (LeftRightEndSubscriber)val;
                    this.rights.remove(end.index);
                    this.cancellations.remove(end);
                    continue;
                }
                if ((missed = WIP.addAndGet(this, -missed)) == 0) break;
            }
        }

        @Override
        public void innerError(Throwable ex) {
            if (Exceptions.addThrowable(ERROR, this, ex)) {
                ACTIVE.decrementAndGet(this);
                this.drain();
            } else {
                Operators.onErrorDropped(ex, this.actual.currentContext());
            }
        }

        @Override
        public void innerComplete(LeftRightSubscriber sender) {
            this.cancellations.remove(sender);
            ACTIVE.decrementAndGet(this);
            this.drain();
        }

        @Override
        public void innerValue(boolean isLeft, Object o) {
            this.queueBiOffer.test(isLeft ? LEFT_VALUE : RIGHT_VALUE, o);
            this.drain();
        }

        @Override
        public void innerClose(boolean isLeft, LeftRightEndSubscriber index) {
            this.queueBiOffer.test(isLeft ? LEFT_CLOSE : RIGHT_CLOSE, index);
            this.drain();
        }

        @Override
        public void innerCloseError(Throwable ex) {
            if (Exceptions.addThrowable(ERROR, this, ex)) {
                this.drain();
            } else {
                Operators.onErrorDropped(ex, this.actual.currentContext());
            }
        }
    }

    static interface JoinSupport<T>
    extends InnerProducer<T> {
        public void innerError(Throwable var1);

        public void innerComplete(LeftRightSubscriber var1);

        public void innerValue(boolean var1, Object var2);

        public void innerClose(boolean var1, LeftRightEndSubscriber var2);

        public void innerCloseError(Throwable var1);
    }
}


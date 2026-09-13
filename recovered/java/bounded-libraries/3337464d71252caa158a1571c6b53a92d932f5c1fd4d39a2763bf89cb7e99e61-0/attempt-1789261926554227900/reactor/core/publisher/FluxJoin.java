/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

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
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxGroupJoin;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;

final class FluxJoin<TLeft, TRight, TLeftEnd, TRightEnd, R>
extends InternalFluxOperator<TLeft, R> {
    final Publisher<? extends TRight> other;
    final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
    final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
    final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;

    FluxJoin(Flux<TLeft> source, Publisher<? extends TRight> other, Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd, Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
        this.leftEnd = Objects.requireNonNull(leftEnd, "leftEnd");
        this.rightEnd = Objects.requireNonNull(rightEnd, "rightEnd");
        this.resultSelector = Objects.requireNonNull(resultSelector, "resultSelector");
    }

    @Override
    public CoreSubscriber<? super TLeft> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        JoinSubscription parent = new JoinSubscription(actual, this.leftEnd, this.rightEnd, this.resultSelector);
        actual.onSubscribe(parent);
        FluxGroupJoin.LeftRightSubscriber left = new FluxGroupJoin.LeftRightSubscriber(parent, true);
        parent.cancellations.add(left);
        FluxGroupJoin.LeftRightSubscriber right = new FluxGroupJoin.LeftRightSubscriber(parent, false);
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

    static final class JoinSubscription<TLeft, TRight, TLeftEnd, TRightEnd, R>
    implements FluxGroupJoin.JoinSupport<R> {
        final Queue<Object> queue;
        final BiPredicate<Object, Object> queueBiOffer;
        final Disposable.Composite cancellations;
        final Map<Integer, TLeft> lefts;
        final Map<Integer, TRight> rights;
        final Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd;
        final Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd;
        final BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector;
        final CoreSubscriber<? super R> actual;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<JoinSubscription> WIP = AtomicIntegerFieldUpdater.newUpdater(JoinSubscription.class, "wip");
        volatile int active;
        static final AtomicIntegerFieldUpdater<JoinSubscription> ACTIVE = AtomicIntegerFieldUpdater.newUpdater(JoinSubscription.class, "active");
        volatile long requested;
        static final AtomicLongFieldUpdater<JoinSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(JoinSubscription.class, "requested");
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<JoinSubscription, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(JoinSubscription.class, Throwable.class, "error");
        int leftIndex;
        int rightIndex;
        static final Integer LEFT_VALUE = 1;
        static final Integer RIGHT_VALUE = 2;
        static final Integer LEFT_CLOSE = 3;
        static final Integer RIGHT_CLOSE = 4;

        JoinSubscription(CoreSubscriber<? super R> actual, Function<? super TLeft, ? extends Publisher<TLeftEnd>> leftEnd, Function<? super TRight, ? extends Publisher<TRightEnd>> rightEnd, BiFunction<? super TLeft, ? super TRight, ? extends R> resultSelector) {
            this.actual = actual;
            this.cancellations = Disposables.composite();
            this.queue = Queues.unboundedMultiproducer().get();
            this.queueBiOffer = (BiPredicate)((Object)this.queue);
            this.lefts = new LinkedHashMap<Integer, TLeft>();
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
            return Scannable.from(this.cancellations).inners();
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
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return FluxGroupJoin.JoinSupport.super.scanUnsafe(key);
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
            while (true) {
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
                    this.lefts.clear();
                    this.rights.clear();
                    this.cancellations.dispose();
                    a.onComplete();
                    return;
                }
                if (!empty) {
                    FluxGroupJoin.LeftRightEndSubscriber end;
                    R w;
                    long e;
                    long r;
                    FluxGroupJoin.LeftRightEndSubscriber end2;
                    Object p;
                    int idx;
                    Object val = q.poll();
                    if (mode == LEFT_VALUE) {
                        Object left = val;
                        ++this.leftIndex;
                        this.lefts.put(idx, left);
                        try {
                            p = Objects.requireNonNull(this.leftEnd.apply(left), "The leftEnd returned a null Publisher");
                        }
                        catch (Throwable exc) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, exc, left, this.actual.currentContext()));
                            this.errorAll(a);
                            return;
                        }
                        end2 = new FluxGroupJoin.LeftRightEndSubscriber(this, true, idx);
                        this.cancellations.add(end2);
                        p.subscribe((Subscriber)end2);
                        ex = this.error;
                        if (ex != null) {
                            q.clear();
                            this.cancellations.dispose();
                            this.errorAll(a);
                            return;
                        }
                        r = this.requested;
                        e = 0L;
                        for (Object right : this.rights.values()) {
                            try {
                                w = Objects.requireNonNull(this.resultSelector.apply(left, right), "The resultSelector returned a null value");
                            }
                            catch (Throwable exc) {
                                Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, exc, right, this.actual.currentContext()));
                                this.errorAll(a);
                                return;
                            }
                            if (e != r) {
                                a.onNext(w);
                                ++e;
                                continue;
                            }
                            Exceptions.addThrowable(ERROR, this, Exceptions.failWithOverflow("Could not emit value due to lack of requests"));
                            q.clear();
                            this.cancellations.dispose();
                            this.errorAll(a);
                            return;
                        }
                        if (e == 0L) continue;
                        Operators.produced(REQUESTED, this, e);
                        continue;
                    }
                    if (mode == RIGHT_VALUE) {
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
                        end2 = new FluxGroupJoin.LeftRightEndSubscriber(this, false, idx);
                        this.cancellations.add(end2);
                        p.subscribe((Subscriber)end2);
                        ex = this.error;
                        if (ex != null) {
                            q.clear();
                            this.cancellations.dispose();
                            this.errorAll(a);
                            return;
                        }
                        r = this.requested;
                        e = 0L;
                        for (Object left : this.lefts.values()) {
                            try {
                                w = Objects.requireNonNull(this.resultSelector.apply(left, right), "The resultSelector returned a null value");
                            }
                            catch (Throwable exc) {
                                Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this, exc, left, this.actual.currentContext()));
                                this.errorAll(a);
                                return;
                            }
                            if (e != r) {
                                a.onNext(w);
                                ++e;
                                continue;
                            }
                            Exceptions.addThrowable(ERROR, this, Exceptions.failWithOverflow("Could not emit value due to lack of requests"));
                            q.clear();
                            this.cancellations.dispose();
                            this.errorAll(a);
                            return;
                        }
                        if (e == 0L) continue;
                        Operators.produced(REQUESTED, this, e);
                        continue;
                    }
                    if (mode == LEFT_CLOSE) {
                        end = (FluxGroupJoin.LeftRightEndSubscriber)val;
                        this.lefts.remove(end.index);
                        this.cancellations.remove(end);
                        continue;
                    }
                    if (mode != RIGHT_CLOSE) continue;
                    end = (FluxGroupJoin.LeftRightEndSubscriber)val;
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
        public void innerComplete(FluxGroupJoin.LeftRightSubscriber sender) {
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
        public void innerClose(boolean isLeft, FluxGroupJoin.LeftRightEndSubscriber index) {
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
}


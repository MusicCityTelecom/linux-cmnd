/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.ConnectableLift;
import reactor.core.publisher.ConnectableLiftFuseable;
import reactor.core.publisher.FluxLift;
import reactor.core.publisher.FluxLiftFuseable;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.GroupedLift;
import reactor.core.publisher.GroupedLiftFuseable;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoLift;
import reactor.core.publisher.MonoLiftFuseable;
import reactor.core.publisher.OnNextFailureStrategy;
import reactor.core.publisher.OptimizableOperator;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.ParallelLift;
import reactor.core.publisher.ParallelLiftFuseable;
import reactor.core.publisher.SerializedSubscriber;
import reactor.core.publisher.StrictSubscriber;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

public abstract class Operators {
    static final Fuseable.ConditionalSubscriber<?> EMPTY_SUBSCRIBER = new Fuseable.ConditionalSubscriber<Object>(){

        @Override
        public void onSubscribe(Subscription s) {
            IllegalStateException e = new IllegalStateException("onSubscribe should not be used");
            log.error("Unexpected call to Operators.emptySubscriber()", e);
        }

        public void onNext(Object o) {
            IllegalStateException e = new IllegalStateException("onNext should not be used, got " + o);
            log.error("Unexpected call to Operators.emptySubscriber()", e);
        }

        @Override
        public boolean tryOnNext(Object o) {
            IllegalStateException e = new IllegalStateException("tryOnNext should not be used, got " + o);
            log.error("Unexpected call to Operators.emptySubscriber()", e);
            return false;
        }

        public void onError(Throwable t) {
            IllegalStateException e = new IllegalStateException("onError should not be used", t);
            log.error("Unexpected call to Operators.emptySubscriber()", e);
        }

        public void onComplete() {
            IllegalStateException e = new IllegalStateException("onComplete should not be used");
            log.error("Unexpected call to Operators.emptySubscriber()", e);
        }

        @Override
        public Context currentContext() {
            return Context.empty();
        }
    };
    static final Logger log = Loggers.getLogger(Operators.class);

    public static long addCap(long a, long b) {
        long res = a + b;
        if (res < 0L) {
            return Long.MAX_VALUE;
        }
        return res;
    }

    public static <T> long addCap(AtomicLongFieldUpdater<T> updater, T instance, long toAdd) {
        long u;
        long r;
        do {
            if ((r = updater.get(instance)) != Long.MAX_VALUE) continue;
            return Long.MAX_VALUE;
        } while (!updater.compareAndSet(instance, r, u = Operators.addCap(r, toAdd)));
        return r;
    }

    @Nullable
    public static <T> Fuseable.QueueSubscription<T> as(Subscription s) {
        if (s instanceof Fuseable.QueueSubscription) {
            return (Fuseable.QueueSubscription)s;
        }
        return null;
    }

    public static Subscription cancelledSubscription() {
        return CancelledSubscription.INSTANCE;
    }

    public static void complete(Subscriber<?> s) {
        s.onSubscribe((Subscription)EmptySubscription.INSTANCE);
        s.onComplete();
    }

    public static <T> CoreSubscriber<T> drainSubscriber() {
        return DrainSubscriber.INSTANCE;
    }

    public static <T> CoreSubscriber<T> emptySubscriber() {
        return EMPTY_SUBSCRIBER;
    }

    public static Subscription emptySubscription() {
        return EmptySubscription.INSTANCE;
    }

    public static boolean canAppearAfterOnSubscribe(Subscription subscription) {
        return subscription == EmptySubscription.FROM_SUBSCRIBE_INSTANCE;
    }

    public static void error(Subscriber<?> s, Throwable e) {
        s.onSubscribe((Subscription)EmptySubscription.INSTANCE);
        s.onError(e);
    }

    public static void reportThrowInSubscribe(CoreSubscriber<?> subscriber, Throwable e) {
        try {
            subscriber.onSubscribe(EmptySubscription.FROM_SUBSCRIBE_INSTANCE);
        }
        catch (Throwable onSubscribeError) {
            Exceptions.throwIfFatal(onSubscribeError);
            e.addSuppressed(onSubscribeError);
        }
        subscriber.onError(Operators.onOperatorError(e, subscriber.currentContext()));
    }

    public static <I, O> Function<? super Publisher<I>, ? extends Publisher<O>> lift(BiFunction<Scannable, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> lifter) {
        return LiftFunction.liftScannable(null, lifter);
    }

    public static <O> Function<? super Publisher<O>, ? extends Publisher<O>> lift(Predicate<Scannable> filter, BiFunction<Scannable, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super O>> lifter) {
        return LiftFunction.liftScannable(filter, lifter);
    }

    public static <I, O> Function<? super Publisher<I>, ? extends Publisher<O>> liftPublisher(BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> lifter) {
        return LiftFunction.liftPublisher(null, lifter);
    }

    public static <O> Function<? super Publisher<O>, ? extends Publisher<O>> liftPublisher(Predicate<Publisher> filter, BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super O>> lifter) {
        return LiftFunction.liftPublisher(filter, lifter);
    }

    public static long multiplyCap(long a, long b) {
        long u = a * b;
        if ((a | b) >>> 31 != 0L && u / a != b) {
            return Long.MAX_VALUE;
        }
        return u;
    }

    static final <R> Function<Context, Context> discardLocalAdapter(Class<R> type, Consumer<? super R> discardHook) {
        Objects.requireNonNull(type, "onDiscard must be based on a type");
        Objects.requireNonNull(discardHook, "onDiscard must be provided a discardHook Consumer");
        Consumer<Object> safeConsumer = obj -> {
            if (type.isInstance(obj)) {
                discardHook.accept((Object)type.cast(obj));
            }
        };
        return ctx -> {
            Consumer consumer = ctx.getOrDefault("reactor.onDiscard.local", null);
            if (consumer == null) {
                return ctx.put("reactor.onDiscard.local", safeConsumer);
            }
            return ctx.put("reactor.onDiscard.local", safeConsumer.andThen(consumer));
        };
    }

    public static final Context enableOnDiscard(@Nullable Context target, Consumer<?> discardConsumer) {
        Objects.requireNonNull(discardConsumer, "discardConsumer must be provided");
        if (target == null) {
            return Context.of("reactor.onDiscard.local", discardConsumer);
        }
        return target.put("reactor.onDiscard.local", discardConsumer);
    }

    public static <T> void onDiscard(@Nullable T element, Context context) {
        Consumer hook = context.getOrDefault("reactor.onDiscard.local", null);
        if (element != null && hook != null) {
            try {
                hook.accept(element);
            }
            catch (Throwable t) {
                log.warn("Error in discard hook", t);
            }
        }
    }

    public static <T> void onDiscardQueueWithClear(@Nullable Queue<T> queue, Context context, @Nullable Function<T, Stream<?>> extract) {
        if (queue == null) {
            return;
        }
        Consumer hook = context.getOrDefault("reactor.onDiscard.local", null);
        if (hook == null) {
            queue.clear();
            return;
        }
        block6: while (true) {
            try {
                T toDiscard;
                while ((toDiscard = queue.poll()) != null) {
                    if (extract != null) {
                        try {
                            extract.apply(toDiscard).forEach(elementToDiscard -> {
                                try {
                                    hook.accept(elementToDiscard);
                                }
                                catch (Throwable t) {
                                    log.warn("Error while discarding item extracted from a queue element, continuing with next item", t);
                                }
                            });
                            continue block6;
                        }
                        catch (Throwable t) {
                            log.warn("Error while extracting items to discard from queue element, continuing with next queue element", t);
                            continue;
                        }
                    }
                    try {
                        hook.accept(toDiscard);
                        continue block6;
                    }
                    catch (Throwable t) {
                        log.warn("Error while discarding a queue element, continuing with next queue element", t);
                    }
                }
                break;
            }
            catch (Throwable t) {
                log.warn("Cannot further apply discard hook while discarding and clearing a queue", t);
                break;
            }
        }
    }

    public static void onDiscardMultiple(Stream<?> multiple, Context context) {
        Consumer hook = context.getOrDefault("reactor.onDiscard.local", null);
        if (hook != null) {
            try {
                multiple.filter(Objects::nonNull).forEach(v -> {
                    try {
                        hook.accept(v);
                    }
                    catch (Throwable t) {
                        log.warn("Error while discarding a stream element, continuing with next element", t);
                    }
                });
            }
            catch (Throwable t) {
                log.warn("Error while discarding stream, stopping", t);
            }
        }
    }

    public static void onDiscardMultiple(@Nullable Collection<?> multiple, Context context) {
        if (multiple == null) {
            return;
        }
        Consumer hook = context.getOrDefault("reactor.onDiscard.local", null);
        if (hook != null) {
            try {
                if (multiple.isEmpty()) {
                    return;
                }
                for (Object o : multiple) {
                    if (o == null) continue;
                    try {
                        hook.accept(o);
                    }
                    catch (Throwable t) {
                        log.warn("Error while discarding element from a Collection, continuing with next element", t);
                    }
                }
            }
            catch (Throwable t) {
                log.warn("Error while discarding collection, stopping", t);
            }
        }
    }

    public static void onDiscardMultiple(@Nullable Iterator<?> multiple, boolean knownToBeFinite, Context context) {
        if (multiple == null) {
            return;
        }
        if (!knownToBeFinite) {
            return;
        }
        Consumer hook = context.getOrDefault("reactor.onDiscard.local", null);
        if (hook != null) {
            try {
                multiple.forEachRemaining(o -> {
                    if (o != null) {
                        try {
                            hook.accept(o);
                        }
                        catch (Throwable t) {
                            log.warn("Error while discarding element from an Iterator, continuing with next element", t);
                        }
                    }
                });
            }
            catch (Throwable t) {
                log.warn("Error while discarding Iterator, stopping", t);
            }
        }
    }

    public static void onErrorDropped(Throwable e, Context context) {
        Consumer<? super Throwable> hook = context.getOrDefault("reactor.onErrorDropped.local", null);
        if (hook == null) {
            hook = Hooks.onErrorDroppedHook;
        }
        if (hook == null) {
            log.error("Operator called default onErrorDropped", e);
            return;
        }
        hook.accept(e);
    }

    public static <T> void onNextDropped(T t, Context context) {
        Objects.requireNonNull(t, "onNext");
        Objects.requireNonNull(context, "context");
        Consumer<Object> hook = context.getOrDefault("reactor.onNextDropped.local", null);
        if (hook == null) {
            hook = Hooks.onNextDroppedHook;
        }
        if (hook != null) {
            hook.accept(t);
        } else if (log.isDebugEnabled()) {
            log.debug("onNextDropped: " + t);
        }
    }

    public static Throwable onOperatorError(Throwable error, Context context) {
        return Operators.onOperatorError(null, error, context);
    }

    public static Throwable onOperatorError(@Nullable Subscription subscription, Throwable error, Context context) {
        return Operators.onOperatorError(subscription, error, null, context);
    }

    public static Throwable onOperatorError(@Nullable Subscription subscription, Throwable error, @Nullable Object dataSignal, Context context) {
        Exceptions.throwIfFatal(error);
        if (subscription != null) {
            subscription.cancel();
        }
        Throwable t = Exceptions.unwrap(error);
        BiFunction<? super Throwable, Object, ? extends Throwable> hook = context.getOrDefault("reactor.onOperatorError.local", null);
        if (hook == null) {
            hook = Hooks.onOperatorErrorHook;
        }
        if (hook == null) {
            if (dataSignal != null && dataSignal != t && dataSignal instanceof Throwable) {
                t = Exceptions.addSuppressed(t, (Throwable)dataSignal);
            }
            return t;
        }
        return hook.apply(error, dataSignal);
    }

    public static RuntimeException onRejectedExecution(Throwable original, Context context) {
        return Operators.onRejectedExecution(original, null, null, null, context);
    }

    static final OnNextFailureStrategy onNextErrorStrategy(Context context) {
        OnNextFailureStrategy strategy = null;
        BiFunction fn = context.getOrDefault("reactor.onNextError.localStrategy", null);
        if (fn instanceof OnNextFailureStrategy) {
            strategy = (OnNextFailureStrategy)fn;
        } else if (fn != null) {
            strategy = new OnNextFailureStrategy.LambdaOnNextErrorStrategy(fn);
        }
        if (strategy == null) {
            strategy = Hooks.onNextErrorHook;
        }
        if (strategy == null) {
            strategy = OnNextFailureStrategy.STOP;
        }
        return strategy;
    }

    public static final BiFunction<? super Throwable, Object, ? extends Throwable> onNextErrorFunction(Context context) {
        return Operators.onNextErrorStrategy(context);
    }

    @Nullable
    public static <T> Throwable onNextError(@Nullable T value, Throwable error, Context context, Subscription subscriptionForCancel) {
        error = Operators.unwrapOnNextError(error);
        OnNextFailureStrategy strategy = Operators.onNextErrorStrategy(context);
        if (strategy.test(error, value)) {
            Throwable t = strategy.process(error, value, context);
            if (t != null) {
                subscriptionForCancel.cancel();
            }
            return t;
        }
        return Operators.onOperatorError(subscriptionForCancel, error, value, context);
    }

    @Nullable
    public static <T> Throwable onNextError(@Nullable T value, Throwable error, Context context) {
        error = Operators.unwrapOnNextError(error);
        OnNextFailureStrategy strategy = Operators.onNextErrorStrategy(context);
        if (strategy.test(error, value)) {
            return strategy.process(error, value, context);
        }
        return Operators.onOperatorError(null, error, value, context);
    }

    public static <T> Throwable onNextInnerError(Throwable error, Context context, @Nullable Subscription subscriptionForCancel) {
        error = Operators.unwrapOnNextError(error);
        OnNextFailureStrategy strategy = Operators.onNextErrorStrategy(context);
        if (strategy.test(error, (Object)null)) {
            Throwable t = strategy.process(error, null, context);
            if (t != null && subscriptionForCancel != null) {
                subscriptionForCancel.cancel();
            }
            return t;
        }
        return error;
    }

    @Nullable
    public static <T> RuntimeException onNextPollError(@Nullable T value, Throwable error, Context context) {
        error = Operators.unwrapOnNextError(error);
        OnNextFailureStrategy strategy = Operators.onNextErrorStrategy(context);
        if (strategy.test(error, value)) {
            Throwable t = strategy.process(error, value, context);
            if (t != null) {
                return Exceptions.propagate(t);
            }
            return null;
        }
        Throwable t = Operators.onOperatorError(null, error, value, context);
        return Exceptions.propagate(t);
    }

    public static <T> CorePublisher<T> onLastAssembly(CorePublisher<T> source) {
        Function<Publisher, Publisher> hook = Hooks.onLastOperatorHook;
        if (hook == null) {
            return source;
        }
        Publisher publisher = Objects.requireNonNull(hook.apply(source), "LastOperator hook returned null");
        if (publisher instanceof CorePublisher) {
            return (CorePublisher)publisher;
        }
        return new CorePublisherAdapter(publisher);
    }

    private static Throwable unwrapOnNextError(Throwable error) {
        return Exceptions.isBubbling(error) ? error : Exceptions.unwrap(error);
    }

    public static RuntimeException onRejectedExecution(Throwable original, @Nullable Subscription subscription, @Nullable Throwable suppressed, @Nullable Object dataSignal, Context context) {
        if (context.hasKey("reactor.onRejectedExecution.local")) {
            context = context.put("reactor.onOperatorError.local", context.get("reactor.onRejectedExecution.local"));
        }
        RejectedExecutionException ree = Exceptions.failWithRejected(original);
        if (suppressed != null) {
            ree.addSuppressed(suppressed);
        }
        if (dataSignal != null) {
            return Exceptions.propagate(Operators.onOperatorError(subscription, ree, dataSignal, context));
        }
        return Exceptions.propagate(Operators.onOperatorError(subscription, ree, context));
    }

    public static <T> long produced(AtomicLongFieldUpdater<T> updater, T instance, long toSub) {
        long u;
        long r;
        do {
            if ((r = updater.get(instance)) != 0L && r != Long.MAX_VALUE) continue;
            return r;
        } while (!updater.compareAndSet(instance, r, u = Operators.subOrZero(r, toSub)));
        return u;
    }

    public static <F> boolean replace(AtomicReferenceFieldUpdater<F, Subscription> field, F instance, Subscription s) {
        Subscription a;
        do {
            if ((a = field.get(instance)) != CancelledSubscription.INSTANCE) continue;
            s.cancel();
            return false;
        } while (!field.compareAndSet(instance, a, s));
        return true;
    }

    public static void reportBadRequest(long n) {
        if (log.isDebugEnabled()) {
            log.debug("Negative request", Exceptions.nullOrNegativeRequestException(n));
        }
    }

    public static void reportMoreProduced() {
        if (log.isDebugEnabled()) {
            log.debug("More data produced than requested", Exceptions.failWithOverflow());
        }
    }

    public static void reportSubscriptionSet() {
        if (log.isDebugEnabled()) {
            log.debug("Duplicate Subscription has been detected", Exceptions.duplicateOnSubscribeException());
        }
    }

    public static <T> Subscription scalarSubscription(CoreSubscriber<? super T> subscriber, T value) {
        return new ScalarSubscription<T>(subscriber, value);
    }

    public static <T> Subscription scalarSubscription(CoreSubscriber<? super T> subscriber, T value, String stepName) {
        return new ScalarSubscription<T>(subscriber, value, stepName);
    }

    public static <T> CoreSubscriber<T> serialize(CoreSubscriber<? super T> subscriber) {
        return new SerializedSubscriber<T>(subscriber);
    }

    public static <F> boolean set(AtomicReferenceFieldUpdater<F, Subscription> field, F instance, Subscription s) {
        Subscription a;
        do {
            if ((a = field.get(instance)) != CancelledSubscription.INSTANCE) continue;
            s.cancel();
            return false;
        } while (!field.compareAndSet(instance, a, s));
        if (a != null) {
            a.cancel();
        }
        return true;
    }

    public static <F> boolean setOnce(AtomicReferenceFieldUpdater<F, Subscription> field, F instance, Subscription s) {
        Objects.requireNonNull(s, "subscription");
        Subscription a = field.get(instance);
        if (a == CancelledSubscription.INSTANCE) {
            s.cancel();
            return false;
        }
        if (a != null) {
            s.cancel();
            Operators.reportSubscriptionSet();
            return false;
        }
        if (field.compareAndSet(instance, null, s)) {
            return true;
        }
        a = field.get(instance);
        if (a == CancelledSubscription.INSTANCE) {
            s.cancel();
            return false;
        }
        s.cancel();
        Operators.reportSubscriptionSet();
        return false;
    }

    public static long subOrZero(long a, long b) {
        long res = a - b;
        if (res < 0L) {
            return 0L;
        }
        return res;
    }

    public static <F> boolean terminate(AtomicReferenceFieldUpdater<F, Subscription> field, F instance) {
        Subscription a = field.get(instance);
        if (a != CancelledSubscription.INSTANCE && (a = field.getAndSet(instance, CancelledSubscription.INSTANCE)) != null && a != CancelledSubscription.INSTANCE) {
            a.cancel();
            return true;
        }
        return false;
    }

    public static boolean validate(@Nullable Subscription current, Subscription next) {
        Objects.requireNonNull(next, "Subscription cannot be null");
        if (current != null) {
            next.cancel();
            return false;
        }
        return true;
    }

    public static boolean validate(long n) {
        if (n <= 0L) {
            Operators.reportBadRequest(n);
            return false;
        }
        return true;
    }

    public static <T> CoreSubscriber<? super T> toCoreSubscriber(Subscriber<? super T> actual) {
        Objects.requireNonNull(actual, "actual");
        StrictSubscriber<? super T> _actual = actual instanceof CoreSubscriber ? (StrictSubscriber<? super T>)actual : new StrictSubscriber<T>(actual);
        return _actual;
    }

    public static <T> Fuseable.ConditionalSubscriber<? super T> toConditionalSubscriber(CoreSubscriber<? super T> actual) {
        Objects.requireNonNull(actual, "actual");
        ConditionalSubscriberAdapter<? super T> _actual = actual instanceof Fuseable.ConditionalSubscriber ? (ConditionalSubscriberAdapter<? super T>)actual : new ConditionalSubscriberAdapter<T>(actual);
        return _actual;
    }

    static Context multiSubscribersContext(InnerProducer<?>[] multicastInners) {
        if (multicastInners.length > 0) {
            return multicastInners[0].actual().currentContext();
        }
        return Context.empty();
    }

    static <T> long addCapCancellable(AtomicLongFieldUpdater<T> updater, T instance, long n) {
        long u;
        long r;
        do {
            if ((r = updater.get(instance)) != Long.MIN_VALUE && r != Long.MAX_VALUE) continue;
            return r;
        } while (!updater.compareAndSet(instance, r, u = Operators.addCap(r, n)));
        return r;
    }

    static void onErrorDroppedMulticast(Throwable e, InnerProducer<?>[] multicastInners) {
        Operators.onErrorDropped(e, Operators.multiSubscribersContext(multicastInners));
    }

    static <T> void onNextDroppedMulticast(T t, InnerProducer<?>[] multicastInners) {
        Operators.onNextDropped(t, Operators.multiSubscribersContext(multicastInners));
    }

    static <T> long producedCancellable(AtomicLongFieldUpdater<T> updater, T instance, long n) {
        long update;
        long current;
        do {
            if ((current = updater.get(instance)) == Long.MIN_VALUE) {
                return Long.MIN_VALUE;
            }
            if (current == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            update = current - n;
            if (update >= 0L) continue;
            Operators.reportBadRequest(update);
            update = 0L;
        } while (!updater.compareAndSet(instance, current, update));
        return update;
    }

    static long unboundedOrPrefetch(int prefetch) {
        return prefetch == Integer.MAX_VALUE ? Long.MAX_VALUE : (long)prefetch;
    }

    static int unboundedOrLimit(int prefetch) {
        return prefetch == Integer.MAX_VALUE ? Integer.MAX_VALUE : prefetch - (prefetch >> 2);
    }

    static int unboundedOrLimit(int prefetch, int lowTide) {
        if (lowTide <= 0) {
            return prefetch;
        }
        if (lowTide >= prefetch) {
            return Operators.unboundedOrLimit(prefetch);
        }
        return prefetch == Integer.MAX_VALUE ? Integer.MAX_VALUE : lowTide;
    }

    Operators() {
    }

    static class MonoInnerProducerBase<O>
    implements InnerProducer<O> {
        private final CoreSubscriber<? super O> actual;
        private O value;
        private volatile int state;
        private static final AtomicIntegerFieldUpdater<MonoInnerProducerBase> STATE = AtomicIntegerFieldUpdater.newUpdater(MonoInnerProducerBase.class, "state");
        private static final int HAS_VALUE = 1;
        private static final int HAS_REQUEST = 2;
        private static final int HAS_COMPLETED = 4;
        private static final int CANCELLED = 128;

        public MonoInnerProducerBase(CoreSubscriber<? super O> actual) {
            this.actual = actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.isCancelled();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return MonoInnerProducerBase.hasCompleted(this.state);
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        public final void complete(O v) {
            int s;
            do {
                if (MonoInnerProducerBase.isCancelled(s = this.state)) {
                    this.discard(v);
                    return;
                }
                if (MonoInnerProducerBase.hasRequest(s) && STATE.compareAndSet(this, s, s | 5)) {
                    this.value = null;
                    this.doOnComplete(v);
                    this.actual.onNext(v);
                    this.actual.onComplete();
                    return;
                }
                this.value = v;
            } while (!STATE.compareAndSet(this, s, s | 5));
        }

        public final void complete() {
            while (true) {
                int s;
                if (MonoInnerProducerBase.isCancelled(s = this.state)) {
                    return;
                }
                if (!STATE.compareAndSet(this, s, s | 4)) continue;
                if (MonoInnerProducerBase.hasValue(s) && MonoInnerProducerBase.hasRequest(s)) {
                    O v = this.value;
                    this.value = null;
                    this.doOnComplete(v);
                    this.actual.onNext(v);
                    this.actual.onComplete();
                    return;
                }
                if (!MonoInnerProducerBase.hasValue(s)) {
                    this.actual.onComplete();
                    return;
                }
                if (!MonoInnerProducerBase.hasRequest(s)) break;
            }
        }

        protected void doOnComplete(O v) {
        }

        protected final void discard(@Nullable O v) {
            Operators.onDiscard(v, this.actual.currentContext());
        }

        protected final void discardTheValue() {
            this.discard(this.value);
            this.value = null;
        }

        @Override
        public final CoreSubscriber<? super O> actual() {
            return this.actual;
        }

        public final boolean isCancelled() {
            return this.state == 128;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                int s;
                do {
                    if (MonoInnerProducerBase.isCancelled(s = this.state)) {
                        return;
                    }
                    if (!MonoInnerProducerBase.hasRequest(s)) continue;
                    return;
                } while (!STATE.compareAndSet(this, s, s | 2));
                this.doOnRequest(n);
                if (MonoInnerProducerBase.hasValue(s) && MonoInnerProducerBase.hasCompleted(s)) {
                    O v = this.value;
                    this.value = null;
                    this.doOnComplete(v);
                    this.actual.onNext(v);
                    this.actual.onComplete();
                }
                return;
            }
        }

        protected void doOnRequest(long n) {
        }

        protected final void setValue(@Nullable O value) {
            int s;
            this.value = value;
            do {
                if (!MonoInnerProducerBase.isCancelled(s = this.state)) continue;
                this.discardTheValue();
                return;
            } while (!STATE.compareAndSet(this, s, s | 1));
        }

        public final void cancel() {
            int previous = STATE.getAndSet(this, 128);
            if (MonoInnerProducerBase.isCancelled(previous)) {
                return;
            }
            this.doOnCancel();
            if (MonoInnerProducerBase.hasValue(previous) && (previous & 6) != 6) {
                this.discardTheValue();
            }
        }

        protected void doOnCancel() {
        }

        private static boolean isCancelled(int s) {
            return s == 128;
        }

        private static boolean hasRequest(int s) {
            return (s & 2) == 2;
        }

        private static boolean hasValue(int s) {
            return (s & 1) == 1;
        }

        private static boolean hasCompleted(int s) {
            return (s & 4) == 4;
        }
    }

    static final class LiftFunction<I, O>
    implements Function<Publisher<I>, Publisher<O>> {
        final Predicate<Publisher> filter;
        final String name;
        final BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> lifter;

        static final <I, O> LiftFunction<I, O> liftScannable(@Nullable Predicate<Scannable> filter, BiFunction<Scannable, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> lifter) {
            Objects.requireNonNull(lifter, "lifter");
            Predicate<Publisher> effectiveFilter = null;
            if (filter != null) {
                effectiveFilter = pub -> filter.test(Scannable.from(pub));
            }
            BiFunction<Publisher, CoreSubscriber, CoreSubscriber> effectiveLifter = (pub, sub) -> (CoreSubscriber)lifter.apply(Scannable.from(pub), (CoreSubscriber)sub);
            return new LiftFunction<I, O>(effectiveFilter, effectiveLifter, lifter.toString());
        }

        static final <I, O> LiftFunction<I, O> liftPublisher(@Nullable Predicate<Publisher> filter, BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> lifter) {
            Objects.requireNonNull(lifter, "lifter");
            return new LiftFunction<I, O>(filter, lifter, lifter.toString());
        }

        private LiftFunction(@Nullable Predicate<Publisher> filter, BiFunction<Publisher, ? super CoreSubscriber<? super O>, ? extends CoreSubscriber<? super I>> lifter, String name) {
            this.filter = filter;
            this.lifter = Objects.requireNonNull(lifter, "lifter");
            this.name = Objects.requireNonNull(name, "name");
        }

        @Override
        public Publisher<O> apply(Publisher<I> publisher) {
            if (this.filter != null && !this.filter.test(publisher)) {
                return publisher;
            }
            if (publisher instanceof Fuseable) {
                if (publisher instanceof Mono) {
                    return new MonoLiftFuseable(publisher, this);
                }
                if (publisher instanceof ParallelFlux) {
                    return new ParallelLiftFuseable((ParallelFlux)publisher, this);
                }
                if (publisher instanceof ConnectableFlux) {
                    return new ConnectableLiftFuseable((ConnectableFlux)publisher, this);
                }
                if (publisher instanceof GroupedFlux) {
                    return new GroupedLiftFuseable((GroupedFlux)publisher, this);
                }
                return new FluxLiftFuseable(publisher, this);
            }
            if (publisher instanceof Mono) {
                return new MonoLift(publisher, this);
            }
            if (publisher instanceof ParallelFlux) {
                return new ParallelLift((ParallelFlux)publisher, this);
            }
            if (publisher instanceof ConnectableFlux) {
                return new ConnectableLift((ConnectableFlux)publisher, this);
            }
            if (publisher instanceof GroupedFlux) {
                return new GroupedLift((GroupedFlux)publisher, this);
            }
            return new FluxLift(publisher, this);
        }
    }

    static final class ConditionalSubscriberAdapter<T>
    implements Fuseable.ConditionalSubscriber<T> {
        final CoreSubscriber<T> delegate;

        ConditionalSubscriberAdapter(CoreSubscriber<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public Context currentContext() {
            return this.delegate.currentContext();
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.delegate.onSubscribe(s);
        }

        public void onNext(T t) {
            this.delegate.onNext(t);
        }

        public void onError(Throwable t) {
            this.delegate.onError(t);
        }

        public void onComplete() {
            this.delegate.onComplete();
        }

        @Override
        public boolean tryOnNext(T t) {
            this.delegate.onNext(t);
            return true;
        }
    }

    static final class DrainSubscriber<T>
    implements CoreSubscriber<T> {
        static final DrainSubscriber INSTANCE = new DrainSubscriber();

        DrainSubscriber() {
        }

        @Override
        public void onSubscribe(Subscription s) {
            s.request(Long.MAX_VALUE);
        }

        public void onNext(Object o) {
        }

        public void onError(Throwable t) {
            Operators.onErrorDropped(Exceptions.errorCallbackNotImplemented(t), Context.empty());
        }

        public void onComplete() {
        }

        @Override
        public Context currentContext() {
            return Context.empty();
        }
    }

    static final class ScalarSubscription<T>
    implements Fuseable.SynchronousSubscription<T>,
    InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        final T value;
        @Nullable
        final String stepName;
        volatile int once;
        static final AtomicIntegerFieldUpdater<ScalarSubscription> ONCE = AtomicIntegerFieldUpdater.newUpdater(ScalarSubscription.class, "once");

        ScalarSubscription(CoreSubscriber<? super T> actual, T value) {
            this(actual, (T)value, null);
        }

        ScalarSubscription(CoreSubscriber<? super T> actual, T value, String stepName) {
            this.value = Objects.requireNonNull(value, "value");
            this.actual = Objects.requireNonNull(actual, "actual");
            this.stepName = stepName;
        }

        public void cancel() {
            if (this.once == 0) {
                Operators.onDiscard(this.value, this.actual.currentContext());
            }
            ONCE.lazySet(this, 2);
        }

        @Override
        public void clear() {
            if (this.once == 0) {
                Operators.onDiscard(this.value, this.actual.currentContext());
            }
            ONCE.lazySet(this, 1);
        }

        @Override
        public boolean isEmpty() {
            return this.once != 0;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public T poll() {
            if (this.once == 0) {
                ONCE.lazySet(this, 1);
                return this.value;
            }
            return null;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.once == 1;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.once == 2;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        public void request(long n) {
            if (Operators.validate(n) && ONCE.compareAndSet(this, 0, 1)) {
                CoreSubscriber<? super T> a = this.actual;
                a.onNext(this.value);
                if (this.once != 2) {
                    a.onComplete();
                }
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 1) != 0) {
                return 1;
            }
            return 0;
        }

        @Override
        public int size() {
            return this.isEmpty() ? 0 : 1;
        }

        @Override
        public String stepName() {
            return this.stepName != null ? this.stepName : "scalarSubscription(" + this.value + ")";
        }
    }

    static abstract class MultiSubscriptionSubscriber<I, O>
    implements InnerOperator<I, O> {
        final CoreSubscriber<? super O> actual;
        protected boolean unbounded;
        Subscription subscription;
        long requested;
        volatile Subscription missedSubscription;
        volatile long missedRequested;
        volatile long missedProduced;
        volatile int wip;
        volatile boolean cancelled;
        static final AtomicReferenceFieldUpdater<MultiSubscriptionSubscriber, Subscription> MISSED_SUBSCRIPTION = AtomicReferenceFieldUpdater.newUpdater(MultiSubscriptionSubscriber.class, Subscription.class, "missedSubscription");
        static final AtomicLongFieldUpdater<MultiSubscriptionSubscriber> MISSED_REQUESTED = AtomicLongFieldUpdater.newUpdater(MultiSubscriptionSubscriber.class, "missedRequested");
        static final AtomicLongFieldUpdater<MultiSubscriptionSubscriber> MISSED_PRODUCED = AtomicLongFieldUpdater.newUpdater(MultiSubscriptionSubscriber.class, "missedProduced");
        static final AtomicIntegerFieldUpdater<MultiSubscriptionSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(MultiSubscriptionSubscriber.class, "wip");

        public MultiSubscriptionSubscriber(CoreSubscriber<? super O> actual) {
            this.actual = actual;
        }

        @Override
        public CoreSubscriber<? super O> actual() {
            return this.actual;
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.drain();
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.missedSubscription != null ? this.missedSubscription : this.subscription;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isCancelled();
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Operators.addCap(this.requested, this.missedRequested);
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        public final boolean isUnbounded() {
            return this.unbounded;
        }

        final boolean isCancelled() {
            return this.cancelled;
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.set(s);
        }

        public final void produced(long n) {
            if (this.unbounded) {
                return;
            }
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                long r = this.requested;
                if (r != Long.MAX_VALUE) {
                    long u = r - n;
                    if (u < 0L) {
                        Operators.reportMoreProduced();
                        u = 0L;
                    }
                    this.requested = u;
                } else {
                    this.unbounded = true;
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return;
                }
                this.drainLoop();
                return;
            }
            Operators.addCap(MISSED_PRODUCED, this, n);
            this.drain();
        }

        final void producedOne() {
            if (this.unbounded) {
                return;
            }
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                long r = this.requested;
                if (r != Long.MAX_VALUE) {
                    if (--r < 0L) {
                        Operators.reportMoreProduced();
                        r = 0L;
                    }
                    this.requested = r;
                } else {
                    this.unbounded = true;
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return;
                }
                this.drainLoop();
                return;
            }
            Operators.addCap(MISSED_PRODUCED, this, 1L);
            this.drain();
        }

        public final void request(long n) {
            if (Operators.validate(n)) {
                if (this.unbounded) {
                    return;
                }
                if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                    long r = this.requested;
                    if (r != Long.MAX_VALUE) {
                        this.requested = r = Operators.addCap(r, n);
                        if (r == Long.MAX_VALUE) {
                            this.unbounded = true;
                        }
                    }
                    Subscription a = this.subscription;
                    if (WIP.decrementAndGet(this) != 0) {
                        this.drainLoop();
                    }
                    if (a != null) {
                        a.request(n);
                    }
                    return;
                }
                Operators.addCap(MISSED_REQUESTED, this, n);
                this.drain();
            }
        }

        public final void set(Subscription s) {
            if (this.cancelled) {
                s.cancel();
                return;
            }
            Objects.requireNonNull(s);
            if (this.wip == 0 && WIP.compareAndSet(this, 0, 1)) {
                Subscription a = this.subscription;
                if (a != null && this.shouldCancelCurrent()) {
                    a.cancel();
                }
                this.subscription = s;
                long r = this.requested;
                if (WIP.decrementAndGet(this) != 0) {
                    this.drainLoop();
                }
                if (r != 0L) {
                    s.request(r);
                }
                return;
            }
            Subscription a = MISSED_SUBSCRIPTION.getAndSet(this, s);
            if (a != null && this.shouldCancelCurrent()) {
                a.cancel();
            }
            this.drain();
        }

        protected boolean shouldCancelCurrent() {
            return false;
        }

        final void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            this.drainLoop();
        }

        final void drainLoop() {
            int missed = 1;
            long requestAmount = 0L;
            long alreadyInRequestAmount = 0L;
            Subscription requestTarget = null;
            do {
                long mp;
                long mr;
                Subscription ms;
                if ((ms = this.missedSubscription) != null) {
                    ms = MISSED_SUBSCRIPTION.getAndSet(this, null);
                }
                if ((mr = this.missedRequested) != 0L) {
                    mr = MISSED_REQUESTED.getAndSet(this, 0L);
                }
                if ((mp = this.missedProduced) != 0L) {
                    mp = MISSED_PRODUCED.getAndSet(this, 0L);
                }
                Subscription a = this.subscription;
                if (this.cancelled) {
                    if (a != null) {
                        a.cancel();
                        this.subscription = null;
                    }
                    if (ms == null) continue;
                    ms.cancel();
                    continue;
                }
                long r = this.requested;
                if (r != Long.MAX_VALUE) {
                    long u = Operators.addCap(r, mr);
                    if (u != Long.MAX_VALUE) {
                        long v = u - mp;
                        if (v < 0L) {
                            Operators.reportMoreProduced();
                            v = 0L;
                        }
                        r = v;
                    } else {
                        r = u;
                    }
                    this.requested = r;
                }
                if (ms != null) {
                    if (a != null && this.shouldCancelCurrent()) {
                        a.cancel();
                    }
                    this.subscription = ms;
                    if (r == 0L) continue;
                    requestAmount = Operators.addCap(requestAmount, r - alreadyInRequestAmount);
                    requestTarget = ms;
                    continue;
                }
                if (mr == 0L || a == null) continue;
                requestAmount = Operators.addCap(requestAmount, mr);
                alreadyInRequestAmount += mr;
                requestTarget = a;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
            if (requestAmount != 0L) {
                requestTarget.request(requestAmount);
            }
        }
    }

    public static class MonoSubscriber<I, O>
    implements InnerOperator<I, O>,
    Fuseable,
    Fuseable.QueueSubscription<O> {
        protected final CoreSubscriber<? super O> actual;
        @Nullable
        protected O value;
        volatile int state;
        static final int NO_REQUEST_NO_VALUE = 0;
        static final int NO_REQUEST_HAS_VALUE = 1;
        static final int HAS_REQUEST_NO_VALUE = 2;
        static final int HAS_REQUEST_HAS_VALUE = 3;
        static final int CANCELLED = 4;
        static final int FUSED_ASYNC_EMPTY = 8;
        static final int FUSED_ASYNC_READY = 16;
        static final int FUSED_ASYNC_CONSUMED = 32;
        static final AtomicIntegerFieldUpdater<MonoSubscriber> STATE = AtomicIntegerFieldUpdater.newUpdater(MonoSubscriber.class, "state");

        public MonoSubscriber(CoreSubscriber<? super O> actual) {
            this.actual = actual;
        }

        public void cancel() {
            O v = this.value;
            this.value = null;
            STATE.set(this, 4);
            this.discard(v);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.isCancelled();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.state == 3 || this.state == 1;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public final void clear() {
            STATE.lazySet(this, 32);
            this.value = null;
        }

        public final void complete(@Nullable O v) {
            int state;
            do {
                if ((state = this.state) == 8) {
                    this.setValue(v);
                    if (STATE.compareAndSet(this, 8, 16)) {
                        CoreSubscriber<? super O> a = this.actual;
                        a.onNext(null);
                        a.onComplete();
                        return;
                    }
                    state = this.state;
                }
                if ((state & 0xFFFFFFFD) != 0) {
                    this.value = null;
                    this.discard(v);
                    return;
                }
                if (state == 2 && STATE.compareAndSet(this, 2, 3)) {
                    this.value = null;
                    CoreSubscriber<? super O> a = this.actual;
                    a.onNext(v);
                    a.onComplete();
                    return;
                }
                this.setValue(v);
            } while (state != 0 || !STATE.compareAndSet(this, 0, 1));
        }

        protected void discard(@Nullable O v) {
            Operators.onDiscard(v, this.actual.currentContext());
        }

        @Override
        public final CoreSubscriber<? super O> actual() {
            return this.actual;
        }

        public final boolean isCancelled() {
            return this.state == 4;
        }

        @Override
        public final boolean isEmpty() {
            return this.state != 16;
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onNext(I t) {
            this.setValue(t);
        }

        @Override
        public void onSubscribe(Subscription s) {
        }

        @Override
        @Nullable
        public final O poll() {
            if (STATE.compareAndSet(this, 16, 32)) {
                O v = this.value;
                this.value = null;
                return v;
            }
            return null;
        }

        public void request(long n) {
            if (Operators.validate(n)) {
                do {
                    int s;
                    if ((s = this.state) == 4) {
                        return;
                    }
                    if ((s & 0xFFFFFFFE) != 0) {
                        return;
                    }
                    if (s != 1 || !STATE.compareAndSet(this, 1, 3)) continue;
                    O v = this.value;
                    if (v != null) {
                        this.value = null;
                        CoreSubscriber<? super O> a = this.actual;
                        a.onNext(v);
                        a.onComplete();
                    }
                    return;
                } while (!STATE.compareAndSet(this, 0, 2));
                return;
            }
        }

        @Override
        public int requestFusion(int mode) {
            if ((mode & 2) != 0) {
                STATE.lazySet(this, 8);
                return 2;
            }
            return 0;
        }

        public void setValue(@Nullable O value) {
            if (STATE.get(this) == 4) {
                this.discard(value);
                return;
            }
            this.value = value;
        }

        @Override
        public int size() {
            return this.isEmpty() ? 0 : 1;
        }
    }

    public static class DeferredSubscription
    implements Subscription,
    Scannable {
        static final int STATE_CANCELLED = -2;
        static final int STATE_SUBSCRIBED = -1;
        Subscription s;
        volatile long requested;
        static final AtomicLongFieldUpdater<DeferredSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(DeferredSubscription.class, "requested");

        protected boolean isCancelled() {
            return this.requested == -2L;
        }

        public void cancel() {
            long state = REQUESTED.getAndSet(this, -2L);
            if (state == -2L) {
                return;
            }
            if (state == -1L) {
                this.s.cancel();
            }
        }

        protected void terminate() {
            REQUESTED.getAndSet(this, -2L);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            long requested = this.requested;
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return requested < 0L ? 0L : requested;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isCancelled();
            }
            return null;
        }

        public void request(long n) {
            long r = this.requested;
            if (r > -1L) {
                do {
                    if (r == Long.MAX_VALUE) {
                        return;
                    }
                    long u = Operators.addCap(r, n);
                    if (!REQUESTED.compareAndSet(this, r, u)) continue;
                    return;
                } while ((r = this.requested) >= 0L);
            }
            if (r == -2L) {
                return;
            }
            this.s.request(n);
        }

        public final boolean set(Subscription s) {
            long r;
            Objects.requireNonNull(s, "s");
            long state = this.requested;
            Subscription a = this.s;
            if (state == -2L) {
                s.cancel();
                return false;
            }
            if (a != null) {
                s.cancel();
                Operators.reportSubscriptionSet();
                return false;
            }
            long accumulated = 0L;
            do {
                if ((r = this.requested) == -2L || r == -1L) {
                    s.cancel();
                    return false;
                }
                this.s = s;
                long toRequest = r - accumulated;
                if (toRequest > 0L) {
                    s.request(toRequest);
                }
                accumulated += toRequest;
            } while (!REQUESTED.compareAndSet(this, r, -1L));
            return true;
        }
    }

    static final class EmptySubscription
    implements Fuseable.QueueSubscription<Object>,
    Scannable {
        static final EmptySubscription INSTANCE = new EmptySubscription();
        static final EmptySubscription FROM_SUBSCRIBE_INSTANCE = new EmptySubscription();

        EmptySubscription() {
        }

        public void cancel() {
        }

        @Override
        public void clear() {
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        @Nullable
        public Object poll() {
            return null;
        }

        public void request(long n) {
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return true;
            }
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public String stepName() {
            return "emptySubscription";
        }
    }

    static final class CancelledSubscription
    implements Subscription,
    Scannable {
        static final CancelledSubscription INSTANCE = new CancelledSubscription();

        CancelledSubscription() {
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return true;
            }
            return null;
        }

        public void cancel() {
        }

        public void request(long n) {
        }

        @Override
        public String stepName() {
            return "cancelledSubscription";
        }
    }

    static final class CorePublisherAdapter<T>
    implements CorePublisher<T>,
    OptimizableOperator<T, T> {
        final Publisher<T> publisher;
        @Nullable
        final OptimizableOperator<?, T> optimizableOperator;

        CorePublisherAdapter(Publisher<T> publisher) {
            OptimizableOperator optimSource;
            this.publisher = publisher;
            this.optimizableOperator = publisher instanceof OptimizableOperator ? (optimSource = (OptimizableOperator)publisher) : null;
        }

        @Override
        public void subscribe(CoreSubscriber<? super T> subscriber) {
            this.publisher.subscribe(subscriber);
        }

        @Override
        public void subscribe(Subscriber<? super T> s) {
            this.publisher.subscribe(s);
        }

        @Override
        public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
            return actual;
        }

        @Override
        public final CorePublisher<? extends T> source() {
            return this;
        }

        @Override
        public final OptimizableOperator<?, ? extends T> nextOptimizableSource() {
            return this.optimizableOperator;
        }
    }
}


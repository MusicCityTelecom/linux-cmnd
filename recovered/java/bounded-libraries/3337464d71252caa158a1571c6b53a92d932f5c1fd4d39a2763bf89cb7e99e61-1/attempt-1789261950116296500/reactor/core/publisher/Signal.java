/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.function.Consumer;
import java.util.function.Supplier;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.ImmutableSignal;
import reactor.core.publisher.SignalType;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

public interface Signal<T>
extends Supplier<T>,
Consumer<Subscriber<? super T>> {
    public static <T> Signal<T> complete() {
        return ImmutableSignal.onComplete();
    }

    public static <T> Signal<T> complete(Context context) {
        if (context.isEmpty()) {
            return ImmutableSignal.onComplete();
        }
        return new ImmutableSignal<Object>(context, SignalType.ON_COMPLETE, null, null, null);
    }

    public static <T> Signal<T> error(Throwable e) {
        return Signal.error(e, Context.empty());
    }

    public static <T> Signal<T> error(Throwable e, Context context) {
        return new ImmutableSignal<Object>(context, SignalType.ON_ERROR, null, e, null);
    }

    public static <T> Signal<T> next(T t) {
        return Signal.next(t, Context.empty());
    }

    public static <T> Signal<T> next(T t, Context context) {
        return new ImmutableSignal<T>(context, SignalType.ON_NEXT, t, null, null);
    }

    public static <T> Signal<T> subscribe(Subscription subscription) {
        return Signal.subscribe(subscription, Context.empty());
    }

    public static <T> Signal<T> subscribe(Subscription subscription, Context context) {
        return new ImmutableSignal<Object>(context, SignalType.ON_SUBSCRIBE, null, null, subscription);
    }

    public static boolean isComplete(Object o) {
        return o == ImmutableSignal.onComplete() || o instanceof Signal && ((Signal)o).getType() == SignalType.ON_COMPLETE;
    }

    public static boolean isError(Object o) {
        return o instanceof Signal && ((Signal)o).getType() == SignalType.ON_ERROR;
    }

    @Nullable
    public Throwable getThrowable();

    @Nullable
    public Subscription getSubscription();

    @Override
    @Nullable
    public T get();

    default public boolean hasValue() {
        return this.isOnNext() && this.get() != null;
    }

    default public boolean hasError() {
        return this.isOnError() && this.getThrowable() != null;
    }

    public SignalType getType();

    @Deprecated
    default public Context getContext() {
        return Context.of(this.getContextView());
    }

    public ContextView getContextView();

    default public boolean isOnError() {
        return this.getType() == SignalType.ON_ERROR;
    }

    default public boolean isOnComplete() {
        return this.getType() == SignalType.ON_COMPLETE;
    }

    default public boolean isOnSubscribe() {
        return this.getType() == SignalType.ON_SUBSCRIBE;
    }

    default public boolean isOnNext() {
        return this.getType() == SignalType.ON_NEXT;
    }

    @Override
    default public void accept(Subscriber<? super T> observer) {
        if (this.isOnNext()) {
            observer.onNext(this.get());
        } else if (this.isOnComplete()) {
            observer.onComplete();
        } else if (this.isOnError()) {
            observer.onError(this.getThrowable());
        } else if (this.isOnSubscribe()) {
            observer.onSubscribe(this.getSubscription());
        }
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.io.Serializable;
import java.util.Objects;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Signal;
import reactor.core.publisher.SignalType;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class ImmutableSignal<T>
implements Signal<T>,
Serializable {
    private static final long serialVersionUID = -2004454746525418508L;
    private final transient ContextView contextView;
    private final SignalType type;
    private final Throwable throwable;
    private final T value;
    private final transient Subscription subscription;
    private static final Signal<?> ON_COMPLETE = new ImmutableSignal<Object>(Context.empty(), SignalType.ON_COMPLETE, null, null, null);

    ImmutableSignal(ContextView contextView, SignalType type, @Nullable T value, @Nullable Throwable e, @Nullable Subscription subscription) {
        this.contextView = contextView;
        this.value = value;
        this.subscription = subscription;
        this.throwable = e;
        this.type = type;
    }

    @Override
    @Nullable
    public Throwable getThrowable() {
        return this.throwable;
    }

    @Override
    @Nullable
    public Subscription getSubscription() {
        return this.subscription;
    }

    @Override
    @Nullable
    public T get() {
        return this.value;
    }

    @Override
    public SignalType getType() {
        return this.type;
    }

    @Override
    public ContextView getContextView() {
        return this.contextView;
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Signal)) {
            return false;
        }
        Signal signal = (Signal)o;
        if (this.getType() != signal.getType()) {
            return false;
        }
        if (this.isOnComplete()) {
            return true;
        }
        if (this.isOnSubscribe()) {
            return Objects.equals(this.getSubscription(), signal.getSubscription());
        }
        if (this.isOnError()) {
            return Objects.equals(this.getThrowable(), signal.getThrowable());
        }
        if (this.isOnNext()) {
            return Objects.equals(this.get(), signal.get());
        }
        return false;
    }

    public int hashCode() {
        int result = this.getType().hashCode();
        if (this.isOnError()) {
            return 31 * result + (this.getThrowable() != null ? this.getThrowable().hashCode() : 0);
        }
        if (this.isOnNext()) {
            return 31 * result + (this.get() != null ? this.get().hashCode() : 0);
        }
        if (this.isOnSubscribe()) {
            return 31 * result + (this.getSubscription() != null ? this.getSubscription().hashCode() : 0);
        }
        return result;
    }

    public String toString() {
        switch (this.getType()) {
            case ON_SUBSCRIBE: {
                return String.format("onSubscribe(%s)", this.getSubscription());
            }
            case ON_NEXT: {
                return String.format("onNext(%s)", this.get());
            }
            case ON_ERROR: {
                return String.format("onError(%s)", this.getThrowable());
            }
            case ON_COMPLETE: {
                return "onComplete()";
            }
        }
        return String.format("Signal type=%s", new Object[]{this.getType()});
    }

    static <U> Signal<U> onComplete() {
        return ON_COMPLETE;
    }
}


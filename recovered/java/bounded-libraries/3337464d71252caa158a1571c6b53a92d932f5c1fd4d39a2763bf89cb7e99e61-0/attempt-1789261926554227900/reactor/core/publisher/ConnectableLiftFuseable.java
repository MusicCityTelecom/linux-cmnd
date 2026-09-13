/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.InternalConnectableFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class ConnectableLiftFuseable<I, O>
extends InternalConnectableFluxOperator<I, O>
implements Scannable,
Fuseable {
    final Operators.LiftFunction<I, O> liftFunction;

    ConnectableLiftFuseable(ConnectableFlux<I> p, Operators.LiftFunction<I, O> liftFunction) {
        super(Objects.requireNonNull(p, "source"));
        this.liftFunction = liftFunction;
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    public void connect(Consumer<? super Disposable> cancelSupport) {
        this.source.connect(cancelSupport);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.source.getPrefetch();
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(key);
        }
        if (key == Scannable.Attr.LIFTER) {
            return this.liftFunction.name;
        }
        return null;
    }

    @Override
    public String stepName() {
        if (this.source instanceof Scannable) {
            return Scannable.from(this.source).stepName();
        }
        return super.stepName();
    }

    @Override
    public final CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> actual) {
        CoreSubscriber input = this.liftFunction.lifter.apply(this.source, actual);
        Objects.requireNonNull(input, "Lifted subscriber MUST NOT be null");
        if (actual instanceof Fuseable.QueueSubscription && !(input instanceof Fuseable.QueueSubscription)) {
            input = new FluxHide.SuppressFuseableSubscriber(input);
        }
        return input;
    }
}


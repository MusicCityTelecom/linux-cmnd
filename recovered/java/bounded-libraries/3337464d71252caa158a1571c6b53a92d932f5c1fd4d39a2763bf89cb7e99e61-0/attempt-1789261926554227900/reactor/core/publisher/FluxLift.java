/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;

final class FluxLift<I, O>
extends InternalFluxOperator<I, O> {
    final Operators.LiftFunction<I, O> liftFunction;

    FluxLift(Publisher<I> p, Operators.LiftFunction<I, O> liftFunction) {
        super(Flux.from(p));
        this.liftFunction = liftFunction;
    }

    @Override
    public String stepName() {
        if (this.source instanceof Scannable) {
            return Scannable.from(this.source).stepName();
        }
        return super.stepName();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(key);
        }
        if (key == Scannable.Attr.LIFTER) {
            return this.liftFunction.name;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super O> actual) {
        CoreSubscriber input = this.liftFunction.lifter.apply(this.source, actual);
        Objects.requireNonNull(input, "Lifted subscriber MUST NOT be null");
        return input;
    }
}


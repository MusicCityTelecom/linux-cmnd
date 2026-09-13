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
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxHide;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

final class MonoLiftFuseable<I, O>
extends InternalMonoOperator<I, O>
implements Fuseable {
    final Operators.LiftFunction<I, O> liftFunction;

    MonoLiftFuseable(Publisher<I> p, Operators.LiftFunction<I, O> liftFunction) {
        super(Mono.from(p));
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
        if (actual instanceof Fuseable.QueueSubscription && !(input instanceof Fuseable.QueueSubscription)) {
            input = new FluxHide.SuppressFuseableSubscriber(input);
        }
        return input;
    }
}


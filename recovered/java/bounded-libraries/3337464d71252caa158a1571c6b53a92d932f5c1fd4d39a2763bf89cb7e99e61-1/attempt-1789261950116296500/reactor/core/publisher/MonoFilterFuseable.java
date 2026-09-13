/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFilterFuseable;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoFilterFuseable<T>
extends InternalMonoOperator<T, T>
implements Fuseable {
    final Predicate<? super T> predicate;

    MonoFilterFuseable(Mono<? extends T> source, Predicate<? super T> predicate) {
        super(source);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new FluxFilterFuseable.FilterFuseableConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)actual, this.predicate);
        }
        return new FluxFilterFuseable.FilterFuseableSubscriber<T>(actual, this.predicate);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


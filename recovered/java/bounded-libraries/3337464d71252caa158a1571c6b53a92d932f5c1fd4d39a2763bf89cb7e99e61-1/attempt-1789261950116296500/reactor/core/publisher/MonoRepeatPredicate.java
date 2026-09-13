/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFromMonoOperator;
import reactor.core.publisher.FluxRepeatPredicate;
import reactor.core.publisher.Mono;

final class MonoRepeatPredicate<T>
extends FluxFromMonoOperator<T, T> {
    final BooleanSupplier predicate;

    MonoRepeatPredicate(Mono<? extends T> source, BooleanSupplier predicate) {
        super(source);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        FluxRepeatPredicate.RepeatPredicateSubscriber<T> parent = new FluxRepeatPredicate.RepeatPredicateSubscriber<T>(this.source, actual, this.predicate);
        actual.onSubscribe(parent);
        if (!parent.isCancelled()) {
            parent.resubscribe();
        }
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


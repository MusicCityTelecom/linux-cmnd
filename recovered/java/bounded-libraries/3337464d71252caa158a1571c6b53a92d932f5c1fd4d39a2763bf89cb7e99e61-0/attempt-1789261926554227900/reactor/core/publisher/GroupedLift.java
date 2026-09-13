/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class GroupedLift<K, I, O>
extends GroupedFlux<K, O>
implements Scannable {
    final Operators.LiftFunction<I, O> liftFunction;
    final GroupedFlux<K, I> source;

    GroupedLift(GroupedFlux<K, I> p, Operators.LiftFunction<I, O> liftFunction) {
        this.source = Objects.requireNonNull(p, "source");
        this.liftFunction = liftFunction;
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    public K key() {
        return this.source.key();
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
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
        return Scannable.super.stepName();
    }

    @Override
    public void subscribe(CoreSubscriber<? super O> actual) {
        CoreSubscriber input = this.liftFunction.lifter.apply(this.source, actual);
        Objects.requireNonNull(input, "Lifted subscriber MUST NOT be null");
        this.source.subscribe(input);
    }
}


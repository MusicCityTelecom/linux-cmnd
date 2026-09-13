/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

public abstract class MonoOperator<I, O>
extends Mono<O>
implements Scannable {
    protected final Mono<? extends I> source;

    protected MonoOperator(Mono<? extends I> source) {
        this.source = Objects.requireNonNull(source);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        return null;
    }
}


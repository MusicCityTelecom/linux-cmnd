/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.util.annotation.Nullable;

public abstract class FluxOperator<I, O>
extends Flux<O>
implements Scannable {
    protected final Flux<? extends I> source;

    protected FluxOperator(Flux<? extends I> source) {
        this.source = Objects.requireNonNull(source);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        return null;
    }
}


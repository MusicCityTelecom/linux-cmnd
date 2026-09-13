/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Set;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;

final class FluxNameFuseable<T>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final String name;
    final Set<Tuple2<String, String>> tags;

    FluxNameFuseable(Flux<? extends T> source, @Nullable String name, @Nullable Set<Tuple2<String, String>> tags) {
        super(source);
        this.name = name;
        this.tags = tags;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return actual;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.NAME) {
            return this.name;
        }
        if (key == Scannable.Attr.TAGS && this.tags != null) {
            return this.tags.stream();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InternalFluxOperator;

final class FluxDoFirstFuseable<T>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final Runnable onFirst;

    FluxDoFirstFuseable(Flux<? extends T> fuseableSource, Runnable onFirst) {
        super(fuseableSource);
        this.onFirst = onFirst;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        this.onFirst.run();
        return actual;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


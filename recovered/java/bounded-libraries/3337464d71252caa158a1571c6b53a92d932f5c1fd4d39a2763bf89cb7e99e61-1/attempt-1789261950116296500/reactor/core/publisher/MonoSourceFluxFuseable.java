/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;

final class MonoSourceFluxFuseable<I>
extends MonoFromFluxOperator<I, I>
implements Fuseable {
    MonoSourceFluxFuseable(Flux<? extends I> source) {
        super(source);
    }

    @Override
    public CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super I> actual) {
        return actual;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(key);
        }
        return super.scanUnsafe(key);
    }
}


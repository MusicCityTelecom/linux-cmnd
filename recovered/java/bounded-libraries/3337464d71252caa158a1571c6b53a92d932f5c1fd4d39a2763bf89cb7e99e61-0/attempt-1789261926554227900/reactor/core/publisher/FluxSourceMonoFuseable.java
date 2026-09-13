/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFromMonoOperator;
import reactor.core.publisher.Mono;

final class FluxSourceMonoFuseable<I>
extends FluxFromMonoOperator<I, I>
implements Fuseable {
    FluxSourceMonoFuseable(Mono<? extends I> source) {
        super(source);
    }

    @Override
    public CoreSubscriber<? super I> subscribeOrReturn(CoreSubscriber<? super I> actual) {
        return actual;
    }

    @Override
    public String stepName() {
        if (this.source instanceof Scannable) {
            return "FluxFromMono(" + Scannable.from(this.source).stepName() + ")";
        }
        return "FluxFromMono(" + this.source.toString() + ")";
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.from(this.source).scanUnsafe(key);
        }
        return super.scanUnsafe(key);
    }
}


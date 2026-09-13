/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoDoFirstFuseable<T>
extends InternalMonoOperator<T, T>
implements Fuseable {
    final Runnable onFirst;

    MonoDoFirstFuseable(Mono<? extends T> source, Runnable onFirst) {
        super(source);
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


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Objects;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxTakeUntilOther;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoTakeUntilOther<T, U>
extends InternalMonoOperator<T, T> {
    private final Publisher<U> other;

    MonoTakeUntilOther(Mono<? extends T> source, Publisher<U> other) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        FluxTakeUntilOther.TakeUntilMainSubscriber<? super T> mainSubscriber = new FluxTakeUntilOther.TakeUntilMainSubscriber<T>(actual);
        FluxTakeUntilOther.TakeUntilOtherSubscriber otherSubscriber = new FluxTakeUntilOther.TakeUntilOtherSubscriber(mainSubscriber);
        this.other.subscribe(otherSubscriber);
        return mainSubscriber;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Consumer;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxDelaySubscription;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoDelaySubscription<T, U>
extends InternalMonoOperator<T, T>
implements Consumer<FluxDelaySubscription.DelaySubscriptionOtherSubscriber<T, U>> {
    final Publisher<U> other;

    MonoDelaySubscription(Mono<? extends T> source, Publisher<U> other) {
        super(source);
        this.other = Objects.requireNonNull(other, "other");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        this.other.subscribe(new FluxDelaySubscription.DelaySubscriptionOtherSubscriber(actual, this));
        return null;
    }

    @Override
    public void accept(FluxDelaySubscription.DelaySubscriptionOtherSubscriber<T, U> s) {
        this.source.subscribe(new FluxDelaySubscription.DelaySubscriptionMainSubscriber(s.actual, s));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


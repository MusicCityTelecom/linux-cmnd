/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxFromMonoOperator;
import reactor.core.publisher.FluxRepeatWhen;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

final class MonoRepeatWhen<T>
extends FluxFromMonoOperator<T, T> {
    final Function<? super Flux<Long>, ? extends Publisher<?>> whenSourceFactory;

    MonoRepeatWhen(Mono<? extends T> source, Function<? super Flux<Long>, ? extends Publisher<?>> whenSourceFactory) {
        super(source);
        this.whenSourceFactory = Objects.requireNonNull(whenSourceFactory, "whenSourceFactory");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        Publisher<?> p;
        FluxRepeatWhen.RepeatWhenOtherSubscriber other = new FluxRepeatWhen.RepeatWhenOtherSubscriber();
        CoreSubscriber<T> serial = Operators.serialize(actual);
        FluxRepeatWhen.RepeatWhenMainSubscriber<? super T> main = new FluxRepeatWhen.RepeatWhenMainSubscriber<T>(serial, other.completionSignal, this.source);
        other.main = main;
        serial.onSubscribe(main);
        try {
            p = Objects.requireNonNull(this.whenSourceFactory.apply(other), "The whenSourceFactory returned a null Publisher");
        }
        catch (Throwable e) {
            actual.onError(Operators.onOperatorError(e, actual.currentContext()));
            return null;
        }
        p.subscribe((Subscriber)other);
        if (!main.cancelled) {
            return main;
        }
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


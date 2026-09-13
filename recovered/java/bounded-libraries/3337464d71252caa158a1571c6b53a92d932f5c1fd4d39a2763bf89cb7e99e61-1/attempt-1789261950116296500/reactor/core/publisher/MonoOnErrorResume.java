/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxOnErrorResume;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;

final class MonoOnErrorResume<T>
extends InternalMonoOperator<T, T> {
    final Function<? super Throwable, ? extends Publisher<? extends T>> nextFactory;

    MonoOnErrorResume(Mono<? extends T> source, Function<? super Throwable, ? extends Mono<? extends T>> nextFactory) {
        super(source);
        this.nextFactory = Objects.requireNonNull(nextFactory, "nextFactory");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new FluxOnErrorResume.ResumeSubscriber<T>(actual, this.nextFactory);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


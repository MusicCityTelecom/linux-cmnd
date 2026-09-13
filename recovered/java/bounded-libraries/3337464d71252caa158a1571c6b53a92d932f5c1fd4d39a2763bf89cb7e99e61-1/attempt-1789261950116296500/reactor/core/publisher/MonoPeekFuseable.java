/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalPeek;
import reactor.util.annotation.Nullable;

final class MonoPeekFuseable<T>
extends InternalMonoOperator<T, T>
implements Fuseable,
SignalPeek<T> {
    final Consumer<? super Subscription> onSubscribeCall;
    final Consumer<? super T> onNextCall;
    final LongConsumer onRequestCall;
    final Runnable onCancelCall;

    MonoPeekFuseable(Mono<? extends T> source, @Nullable Consumer<? super Subscription> onSubscribeCall, @Nullable Consumer<? super T> onNextCall, @Nullable LongConsumer onRequestCall, @Nullable Runnable onCancelCall) {
        super(source);
        this.onSubscribeCall = onSubscribeCall;
        this.onNextCall = onNextCall;
        this.onRequestCall = onRequestCall;
        this.onCancelCall = onCancelCall;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new FluxPeekFuseable.PeekFuseableConditionalSubscriber((Fuseable.ConditionalSubscriber)actual, this);
        }
        return new FluxPeekFuseable.PeekFuseableSubscriber<T>(actual, this);
    }

    @Override
    @Nullable
    public Consumer<? super Subscription> onSubscribeCall() {
        return this.onSubscribeCall;
    }

    @Override
    @Nullable
    public Consumer<? super T> onNextCall() {
        return this.onNextCall;
    }

    @Override
    @Nullable
    public Consumer<? super Throwable> onErrorCall() {
        return null;
    }

    @Override
    @Nullable
    public Runnable onCompleteCall() {
        return null;
    }

    @Override
    @Nullable
    public Runnable onAfterTerminateCall() {
        return null;
    }

    @Override
    @Nullable
    public LongConsumer onRequestCall() {
        return this.onRequestCall;
    }

    @Override
    @Nullable
    public Runnable onCancelCall() {
        return this.onCancelCall;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}


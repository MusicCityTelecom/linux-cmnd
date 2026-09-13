/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxPeek;
import reactor.core.publisher.FluxPeekFuseable;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.SignalPeek;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class ParallelDoOnEach<T>
extends ParallelFlux<T>
implements Scannable {
    final ParallelFlux<T> source;
    final BiConsumer<Context, ? super T> onNext;
    final BiConsumer<Context, ? super Throwable> onError;
    final Consumer<Context> onComplete;

    ParallelDoOnEach(ParallelFlux<T> source, @Nullable BiConsumer<Context, ? super T> onNext, @Nullable BiConsumer<Context, ? super Throwable> onError, @Nullable Consumer<Context> onComplete) {
        this.source = source;
        this.onNext = onNext;
        this.onError = onError;
        this.onComplete = onComplete;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T>[] subscribers) {
        if (!this.validate(subscribers)) {
            return;
        }
        int n = subscribers.length;
        CoreSubscriber[] parents = new CoreSubscriber[n];
        boolean conditional = subscribers[0] instanceof Fuseable.ConditionalSubscriber;
        for (int i = 0; i < n; ++i) {
            CoreSubscriber<T> subscriber = subscribers[i];
            DoOnEachSignalPeek signalPeek = new DoOnEachSignalPeek(subscriber.currentContext());
            parents[i] = conditional ? new FluxPeekFuseable.PeekConditionalSubscriber((Fuseable.ConditionalSubscriber)subscriber, signalPeek) : new FluxPeek.PeekSubscriber<T>(subscriber, signalPeek);
        }
        this.source.subscribe(parents);
    }

    @Override
    public int parallelism() {
        return this.source.parallelism();
    }

    @Override
    public int getPrefetch() {
        return this.source.getPrefetch();
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    private class DoOnEachSignalPeek
    implements SignalPeek<T> {
        Consumer<? super T> onNextCall;
        Consumer<? super Throwable> onErrorCall;
        Runnable onCompleteCall;

        public DoOnEachSignalPeek(Context ctx) {
            this.onNextCall = ParallelDoOnEach.this.onNext != null ? v -> ParallelDoOnEach.this.onNext.accept(ctx, (Context)v) : null;
            this.onErrorCall = ParallelDoOnEach.this.onError != null ? e -> ParallelDoOnEach.this.onError.accept(ctx, (Throwable)e) : null;
            this.onCompleteCall = ParallelDoOnEach.this.onComplete != null ? () -> ParallelDoOnEach.this.onComplete.accept(ctx) : null;
        }

        @Override
        public Consumer<? super Subscription> onSubscribeCall() {
            return null;
        }

        @Override
        public Consumer<? super T> onNextCall() {
            return this.onNextCall;
        }

        @Override
        public Consumer<? super Throwable> onErrorCall() {
            return this.onErrorCall;
        }

        @Override
        public Runnable onCompleteCall() {
            return this.onCompleteCall;
        }

        @Override
        public Runnable onAfterTerminateCall() {
            return null;
        }

        @Override
        public LongConsumer onRequestCall() {
            return null;
        }

        @Override
        public Runnable onCancelCall() {
            return null;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            return null;
        }
    }
}


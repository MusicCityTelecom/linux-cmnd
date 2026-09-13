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
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

interface SignalPeek<T>
extends Scannable {
    @Nullable
    public Consumer<? super Subscription> onSubscribeCall();

    @Nullable
    public Consumer<? super T> onNextCall();

    @Nullable
    public Consumer<? super Throwable> onErrorCall();

    @Nullable
    public Runnable onCompleteCall();

    @Nullable
    public Runnable onAfterTerminateCall();

    @Nullable
    public LongConsumer onRequestCall();

    @Nullable
    public Runnable onCancelCall();

    @Nullable
    default public Consumer<? super T> onAfterNextCall() {
        return null;
    }

    @Nullable
    default public Consumer<? super Context> onCurrentContextCall() {
        return null;
    }
}


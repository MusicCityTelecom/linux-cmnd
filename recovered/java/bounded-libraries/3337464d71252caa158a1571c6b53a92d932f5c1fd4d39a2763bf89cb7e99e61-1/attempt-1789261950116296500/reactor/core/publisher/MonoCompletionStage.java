/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

final class MonoCompletionStage<T>
extends Mono<T>
implements Fuseable,
Scannable {
    final CompletionStage<? extends T> future;

    MonoCompletionStage(CompletionStage<? extends T> future) {
        this.future = Objects.requireNonNull(future, "future");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Operators.MonoSubscriber sds = new Operators.MonoSubscriber(actual);
        actual.onSubscribe(sds);
        if (sds.isCancelled()) {
            return;
        }
        this.future.whenComplete((v, e) -> {
            if (sds.isCancelled()) {
                Context ctx = sds.currentContext();
                if (e == null || e instanceof CancellationException) {
                    Operators.onDiscard(v, ctx);
                } else {
                    Operators.onErrorDropped(e, ctx);
                    Operators.onDiscard(v, ctx);
                }
                return;
            }
            try {
                if (e instanceof CompletionException) {
                    actual.onError(e.getCause());
                } else if (e != null) {
                    actual.onError((Throwable)e);
                } else if (v != null) {
                    sds.complete(v);
                } else {
                    actual.onComplete();
                }
            }
            catch (Throwable e1) {
                Operators.onErrorDropped(e1, actual.currentContext());
                throw Exceptions.bubble(e1);
            }
        });
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return null;
    }
}


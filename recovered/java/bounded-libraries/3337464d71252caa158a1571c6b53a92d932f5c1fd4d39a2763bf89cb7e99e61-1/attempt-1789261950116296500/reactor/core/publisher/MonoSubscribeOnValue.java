/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxSubscribeOnValue;
import reactor.core.publisher.Mono;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class MonoSubscribeOnValue<T>
extends Mono<T>
implements Scannable {
    final T value;
    final Scheduler scheduler;

    MonoSubscribeOnValue(@Nullable T value, Scheduler scheduler) {
        this.value = value;
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        T v = this.value;
        if (v == null) {
            FluxSubscribeOnValue.ScheduledEmpty parent = new FluxSubscribeOnValue.ScheduledEmpty(actual);
            actual.onSubscribe(parent);
            try {
                parent.setFuture(this.scheduler.schedule(parent));
            }
            catch (RejectedExecutionException ree) {
                if (parent.future != OperatorDisposables.DISPOSED) {
                    actual.onError(Operators.onRejectedExecution(ree, actual.currentContext()));
                }
            }
        } else {
            actual.onSubscribe(new FluxSubscribeOnValue.ScheduledScalar<T>(actual, v, this.scheduler));
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.scheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return null;
    }
}


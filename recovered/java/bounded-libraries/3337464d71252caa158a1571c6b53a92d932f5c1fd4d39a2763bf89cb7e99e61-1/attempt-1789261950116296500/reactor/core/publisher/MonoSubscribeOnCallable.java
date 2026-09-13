/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxSubscribeOnCallable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;

final class MonoSubscribeOnCallable<T>
extends Mono<T>
implements Fuseable,
Scannable {
    final Callable<? extends T> callable;
    final Scheduler scheduler;

    MonoSubscribeOnCallable(Callable<? extends T> callable, Scheduler scheduler) {
        this.callable = Objects.requireNonNull(callable, "callable");
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        block2: {
            FluxSubscribeOnCallable.CallableSubscribeOnSubscription<T> parent = new FluxSubscribeOnCallable.CallableSubscribeOnSubscription<T>(actual, this.callable, this.scheduler);
            actual.onSubscribe(parent);
            try {
                parent.setMainFuture(this.scheduler.schedule(parent));
            }
            catch (RejectedExecutionException ree) {
                if (parent.state == 4) break block2;
                actual.onError(Operators.onRejectedExecution(ree, actual.currentContext()));
            }
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


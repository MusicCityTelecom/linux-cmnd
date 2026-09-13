/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class MonoRunnable<T>
extends Mono<T>
implements Callable<Void>,
SourceProducer<T> {
    final Runnable run;

    MonoRunnable(Runnable run) {
        this.run = Objects.requireNonNull(run, "run");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        MonoRunnableEagerSubscription s = new MonoRunnableEagerSubscription();
        actual.onSubscribe(s);
        if (s.isCancelled()) {
            return;
        }
        try {
            this.run.run();
            actual.onComplete();
        }
        catch (Throwable ex) {
            actual.onError(Operators.onOperatorError(ex, actual.currentContext()));
        }
    }

    @Override
    @Nullable
    public T block(Duration m) {
        this.run.run();
        return null;
    }

    @Override
    @Nullable
    public T block() {
        this.run.run();
        return null;
    }

    @Override
    @Nullable
    public Void call() throws Exception {
        this.run.run();
        return null;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class MonoRunnableEagerSubscription
    extends AtomicBoolean
    implements Subscription {
        MonoRunnableEagerSubscription() {
        }

        public void request(long n) {
        }

        public void cancel() {
            this.set(true);
        }

        public boolean isCancelled() {
            return this.get();
        }
    }
}


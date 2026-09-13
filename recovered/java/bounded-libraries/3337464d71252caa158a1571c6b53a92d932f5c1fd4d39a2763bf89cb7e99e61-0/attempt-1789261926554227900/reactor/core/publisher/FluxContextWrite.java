/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxContextWrite<T>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final Function<Context, Context> doOnContext;

    FluxContextWrite(Flux<? extends T> source, Function<Context, Context> doOnContext) {
        super(source);
        this.doOnContext = Objects.requireNonNull(doOnContext, "doOnContext");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        Context c = this.doOnContext.apply(actual.currentContext());
        return new ContextWriteSubscriber<T>(actual, c);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ContextWriteSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;
        final Context context;
        Fuseable.QueueSubscription<T> qs;
        Subscription s;

        ContextWriteSubscriber(CoreSubscriber<? super T> actual, Context context) {
            this.actual = actual;
            this.context = context;
            this.actualConditional = actual instanceof Fuseable.ConditionalSubscriber ? (Fuseable.ConditionalSubscriber)actual : null;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Context currentContext() {
            return this.context;
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                if (s instanceof Fuseable.QueueSubscription) {
                    this.qs = (Fuseable.QueueSubscription)s;
                }
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.actual.onNext(t);
        }

        @Override
        public boolean tryOnNext(T t) {
            if (this.actualConditional != null) {
                return this.actualConditional.tryOnNext(t);
            }
            this.actual.onNext(t);
            return true;
        }

        public void onError(Throwable t) {
            this.actual.onError(t);
        }

        public void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        public void request(long n) {
            this.s.request(n);
        }

        public void cancel() {
            this.s.cancel();
        }

        @Override
        public int requestFusion(int requestedMode) {
            if (this.qs == null) {
                return 0;
            }
            return this.qs.requestFusion(requestedMode);
        }

        @Override
        @Nullable
        public T poll() {
            if (this.qs != null) {
                return (T)this.qs.poll();
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return this.qs == null || this.qs.isEmpty();
        }

        @Override
        public void clear() {
            if (this.qs != null) {
                this.qs.clear();
            }
        }

        @Override
        public int size() {
            return this.qs != null ? this.qs.size() : 0;
        }
    }
}


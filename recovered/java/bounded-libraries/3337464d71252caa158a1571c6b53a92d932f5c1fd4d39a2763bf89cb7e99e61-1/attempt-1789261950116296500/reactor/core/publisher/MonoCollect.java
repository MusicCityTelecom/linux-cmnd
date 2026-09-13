/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoCollect<T, R>
extends MonoFromFluxOperator<T, R>
implements Fuseable {
    final Supplier<R> supplier;
    final BiConsumer<? super R, ? super T> action;

    MonoCollect(Flux<? extends T> source, Supplier<R> supplier, BiConsumer<? super R, ? super T> action) {
        super(source);
        this.supplier = Objects.requireNonNull(supplier, "supplier");
        this.action = Objects.requireNonNull(action);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        R container = Objects.requireNonNull(this.supplier.get(), "The supplier returned a null container");
        return new CollectSubscriber<T, R>(actual, this.action, container);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class CollectSubscriber<T, R>
    extends Operators.MonoSubscriber<T, R> {
        final BiConsumer<? super R, ? super T> action;
        R container;
        Subscription s;
        boolean done;

        CollectSubscriber(CoreSubscriber<? super R> actual, BiConsumer<? super R, ? super T> action, R container) {
            super(actual);
            this.action = action;
            this.container = container;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (Operators.validate(this.s, s)) {
                this.s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onNext(T t) {
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            CollectSubscriber collectSubscriber = this;
            synchronized (collectSubscriber) {
                R c = this.container;
                if (c != null) {
                    try {
                        this.action.accept(c, t);
                    }
                    catch (Throwable e) {
                        Context ctx = this.actual.currentContext();
                        Operators.onDiscard(t, ctx);
                        this.onError(Operators.onOperatorError(this, e, t, ctx));
                    }
                    return;
                }
            }
            Operators.onDiscard(t, this.actual.currentContext());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t) {
            R c;
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            CollectSubscriber collectSubscriber = this;
            synchronized (collectSubscriber) {
                c = this.container;
                this.container = null;
            }
            this.discard(c);
            this.actual.onError(t);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onComplete() {
            R c;
            if (this.done) {
                return;
            }
            this.done = true;
            CollectSubscriber collectSubscriber = this;
            synchronized (collectSubscriber) {
                c = this.container;
                this.container = null;
            }
            if (c != null) {
                this.complete(c);
            }
        }

        @Override
        protected void discard(R v) {
            if (v instanceof Collection) {
                Collection c = (Collection)v;
                Operators.onDiscardMultiple(c, this.actual.currentContext());
            } else {
                super.discard(v);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cancel() {
            R c;
            int state = STATE.getAndSet(this, 4);
            if (state != 4) {
                this.s.cancel();
            }
            if (state <= 2) {
                CollectSubscriber collectSubscriber = this;
                synchronized (collectSubscriber) {
                    c = this.container;
                    this.value = null;
                    this.container = null;
                }
            } else {
                c = null;
            }
            if (c != null) {
                this.discard(c);
            }
        }
    }
}


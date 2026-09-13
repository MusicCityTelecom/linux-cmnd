/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.ArrayList;
import java.util.List;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoCollectList<T>
extends MonoFromFluxOperator<T, List<T>>
implements Fuseable {
    MonoCollectList(Flux<? extends T> source) {
        super(source);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super List<T>> actual) {
        return new MonoCollectListSubscriber(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class MonoCollectListSubscriber<T>
    extends Operators.MonoSubscriber<T, List<T>> {
        List<T> list = new ArrayList<T>();
        Subscription s;
        boolean done;

        MonoCollectListSubscriber(CoreSubscriber<? super List<T>> actual) {
            super(actual);
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
            MonoCollectListSubscriber monoCollectListSubscriber = this;
            synchronized (monoCollectListSubscriber) {
                List<T> l = this.list;
                if (l != null) {
                    l.add(t);
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
            List<T> l;
            if (this.done) {
                Operators.onErrorDropped(t, this.actual.currentContext());
                return;
            }
            this.done = true;
            MonoCollectListSubscriber monoCollectListSubscriber = this;
            synchronized (monoCollectListSubscriber) {
                l = this.list;
                this.list = null;
            }
            this.discard(l);
            this.actual.onError(t);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onComplete() {
            List<T> l;
            if (this.done) {
                return;
            }
            this.done = true;
            MonoCollectListSubscriber monoCollectListSubscriber = this;
            synchronized (monoCollectListSubscriber) {
                l = this.list;
                this.list = null;
            }
            if (l != null) {
                this.complete(l);
            }
        }

        @Override
        protected void discard(List<T> v) {
            Operators.onDiscardMultiple(v, this.actual.currentContext());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cancel() {
            List<T> l;
            int state = STATE.getAndSet(this, 4);
            if (state != 4) {
                this.s.cancel();
            }
            MonoCollectListSubscriber monoCollectListSubscriber = this;
            synchronized (monoCollectListSubscriber) {
                if (state <= 2) {
                    l = this.list;
                    this.value = null;
                    this.list = null;
                } else {
                    l = null;
                }
            }
            if (l != null) {
                this.discard(l);
            }
        }
    }
}


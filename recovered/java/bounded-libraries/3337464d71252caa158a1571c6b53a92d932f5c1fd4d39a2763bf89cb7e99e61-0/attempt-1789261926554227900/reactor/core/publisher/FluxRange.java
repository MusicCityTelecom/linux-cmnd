/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;

final class FluxRange
extends Flux<Integer>
implements Fuseable,
SourceProducer<Integer> {
    final long start;
    final long end;

    FluxRange(int start, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count >= required but it was " + count);
        }
        long e = (long)start + (long)count;
        if (e - 1L > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("start + count must be less than Integer.MAX_VALUE + 1");
        }
        this.start = start;
        this.end = e;
    }

    @Override
    public void subscribe(CoreSubscriber<? super Integer> actual) {
        long st = this.start;
        long en = this.end;
        if (st == en) {
            Operators.complete(actual);
            return;
        }
        if (st + 1L == en) {
            actual.onSubscribe(Operators.scalarSubscription(actual, (int)st));
            return;
        }
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            actual.onSubscribe(new RangeSubscriptionConditional((Fuseable.ConditionalSubscriber)actual, st, en));
            return;
        }
        actual.onSubscribe(new RangeSubscription(actual, st, en));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class RangeSubscriptionConditional
    implements InnerProducer<Integer>,
    Fuseable.SynchronousSubscription<Integer> {
        final Fuseable.ConditionalSubscriber<? super Integer> actual;
        final long end;
        volatile boolean cancelled;
        long index;
        volatile long requested;
        static final AtomicLongFieldUpdater<RangeSubscriptionConditional> REQUESTED = AtomicLongFieldUpdater.newUpdater(RangeSubscriptionConditional.class, "requested");

        RangeSubscriptionConditional(Fuseable.ConditionalSubscriber<? super Integer> actual, long start, long end) {
            this.actual = actual;
            this.index = start;
            this.end = end;
        }

        @Override
        public CoreSubscriber<? super Integer> actual() {
            return this.actual;
        }

        public void request(long n) {
            if (Operators.validate(n) && Operators.addCap(REQUESTED, this, n) == 0L) {
                if (n == Long.MAX_VALUE) {
                    this.fastPath();
                } else {
                    this.slowPath(n);
                }
            }
        }

        public void cancel() {
            this.cancelled = true;
        }

        void fastPath() {
            long e = this.end;
            Fuseable.ConditionalSubscriber<? super Integer> a = this.actual;
            for (long i = this.index; i != e; ++i) {
                if (this.cancelled) {
                    return;
                }
                a.tryOnNext((Integer)((int)i));
            }
            if (this.cancelled) {
                return;
            }
            a.onComplete();
        }

        void slowPath(long n) {
            Fuseable.ConditionalSubscriber<? super Integer> a = this.actual;
            long f = this.end;
            long e = 0L;
            long i = this.index;
            while (!this.cancelled) {
                while (e != n && i != f) {
                    boolean b = a.tryOnNext((Integer)((int)i));
                    if (this.cancelled) {
                        return;
                    }
                    if (b) {
                        ++e;
                    }
                    ++i;
                }
                if (this.cancelled) {
                    return;
                }
                if (i == f) {
                    a.onComplete();
                    return;
                }
                n = this.requested;
                if (n != e) continue;
                this.index = i;
                n = REQUESTED.addAndGet(this, -e);
                if (n == 0L) {
                    return;
                }
                e = 0L;
            }
            return;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.isEmpty();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        @Nullable
        public Integer poll() {
            long i = this.index;
            if (i == this.end) {
                return null;
            }
            this.index = i + 1L;
            return (int)i;
        }

        @Override
        public boolean isEmpty() {
            return this.index == this.end;
        }

        @Override
        public void clear() {
            this.index = this.end;
        }

        @Override
        public int size() {
            return (int)(this.end - this.index);
        }
    }

    static final class RangeSubscription
    implements InnerProducer<Integer>,
    Fuseable.SynchronousSubscription<Integer> {
        final CoreSubscriber<? super Integer> actual;
        final long end;
        volatile boolean cancelled;
        long index;
        volatile long requested;
        static final AtomicLongFieldUpdater<RangeSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(RangeSubscription.class, "requested");

        RangeSubscription(CoreSubscriber<? super Integer> actual, long start, long end) {
            this.actual = actual;
            this.index = start;
            this.end = end;
        }

        @Override
        public CoreSubscriber<? super Integer> actual() {
            return this.actual;
        }

        public void request(long n) {
            if (Operators.validate(n) && Operators.addCap(REQUESTED, this, n) == 0L) {
                if (n == Long.MAX_VALUE) {
                    this.fastPath();
                } else {
                    this.slowPath(n);
                }
            }
        }

        public void cancel() {
            this.cancelled = true;
        }

        void fastPath() {
            long e = this.end;
            CoreSubscriber<? super Integer> a = this.actual;
            for (long i = this.index; i != e; ++i) {
                if (this.cancelled) {
                    return;
                }
                a.onNext((int)i);
            }
            if (this.cancelled) {
                return;
            }
            a.onComplete();
        }

        void slowPath(long n) {
            CoreSubscriber<? super Integer> a = this.actual;
            long f = this.end;
            long e = 0L;
            long i = this.index;
            while (!this.cancelled) {
                while (e != n && i != f) {
                    a.onNext((int)i);
                    if (this.cancelled) {
                        return;
                    }
                    ++e;
                    ++i;
                }
                if (this.cancelled) {
                    return;
                }
                if (i == f) {
                    a.onComplete();
                    return;
                }
                n = this.requested;
                if (n != e) continue;
                this.index = i;
                n = REQUESTED.addAndGet(this, -e);
                if (n == 0L) {
                    return;
                }
                e = 0L;
            }
            return;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.isEmpty();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        @Nullable
        public Integer poll() {
            long i = this.index;
            if (i == this.end) {
                return null;
            }
            this.index = i + 1L;
            return (int)i;
        }

        @Override
        public boolean isEmpty() {
            return this.index == this.end;
        }

        @Override
        public void clear() {
            this.index = this.end;
        }

        @Override
        public int size() {
            return (int)(this.end - this.index);
        }
    }
}


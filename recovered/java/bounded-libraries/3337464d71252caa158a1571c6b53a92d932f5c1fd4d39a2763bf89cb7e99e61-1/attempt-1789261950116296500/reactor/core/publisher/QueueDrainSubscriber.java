/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscriber
 */
package reactor.core.publisher;

import java.util.Queue;
import org.reactivestreams.Subscriber;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.QueueDrainSubscriberPad4;
import reactor.util.annotation.Nullable;

abstract class QueueDrainSubscriber<T, U, V>
extends QueueDrainSubscriberPad4
implements InnerOperator<T, V> {
    final CoreSubscriber<? super V> actual;
    final Queue<U> queue;
    volatile boolean cancelled;
    volatile boolean done;
    Throwable error;

    QueueDrainSubscriber(CoreSubscriber<? super V> actual, Queue<U> queue) {
        this.actual = actual;
        this.queue = queue;
    }

    @Override
    public CoreSubscriber<? super V> actual() {
        return this.actual;
    }

    public final boolean cancelled() {
        return this.cancelled;
    }

    public final boolean done() {
        return this.done;
    }

    public final boolean enter() {
        return this.wip.getAndIncrement() == 0;
    }

    public final boolean fastEnter() {
        return this.wip.get() == 0 && this.wip.compareAndSet(0, 1);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void fastPathEmitMax(U value, boolean delayError, Disposable dispose) {
        CoreSubscriber<? super V> s = this.actual;
        Queue<U> q = this.queue;
        if (this.wip.get() == 0 && this.wip.compareAndSet(0, 1)) {
            long r = this.requested;
            if (r == 0L) {
                dispose.dispose();
                s.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
                return;
            }
            if (this.accept(s, value) && r != Long.MAX_VALUE) {
                this.produced(1L);
            }
            if (this.leave(-1) == 0) {
                return;
            }
        } else {
            q.offer(value);
            if (!this.enter()) {
                return;
            }
        }
        QueueDrainSubscriber.drainMaxLoop(q, s, delayError, dispose, this);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final void fastPathOrderedEmitMax(U value, boolean delayError, Disposable dispose) {
        CoreSubscriber<? super V> s = this.actual;
        Queue<U> q = this.queue;
        if (this.wip.get() == 0 && this.wip.compareAndSet(0, 1)) {
            long r = this.requested;
            if (r == 0L) {
                this.cancelled = true;
                dispose.dispose();
                s.onError(Exceptions.failWithOverflow("Could not emit buffer due to lack of requests"));
                return;
            }
            if (q.isEmpty()) {
                if (this.accept(s, value) && r != Long.MAX_VALUE) {
                    this.produced(1L);
                }
                if (this.leave(-1) == 0) {
                    return;
                }
            } else {
                q.offer(value);
            }
        } else {
            q.offer(value);
            if (!this.enter()) {
                return;
            }
        }
        QueueDrainSubscriber.drainMaxLoop(q, s, delayError, dispose, this);
    }

    public boolean accept(Subscriber<? super V> a, U v) {
        return false;
    }

    public final Throwable error() {
        return this.error;
    }

    public final int leave(int m) {
        return this.wip.addAndGet(m);
    }

    public final long requested() {
        return this.requested;
    }

    public final long produced(long n) {
        return REQUESTED.addAndGet(this, -n);
    }

    public final void requested(long n) {
        if (Operators.validate(n)) {
            Operators.addCap(REQUESTED, this, n);
        }
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED) {
            return this.done;
        }
        if (key == Scannable.Attr.CANCELLED) {
            return this.cancelled;
        }
        if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
            return this.requested;
        }
        if (key == Scannable.Attr.ERROR) {
            return this.error;
        }
        return InnerOperator.super.scanUnsafe(key);
    }

    static <Q, S> void drainMaxLoop(Queue<Q> q, Subscriber<? super S> a, boolean delayError, Disposable dispose, QueueDrainSubscriber<?, Q, S> qd) {
        int missed = 1;
        while (true) {
            boolean empty;
            boolean d = qd.done();
            Q v = q.poll();
            boolean bl = empty = v == null;
            if (QueueDrainSubscriber.checkTerminated(d, empty, a, delayError, q, qd)) {
                if (dispose != null) {
                    dispose.dispose();
                }
                return;
            }
            if (!empty) {
                long r = qd.requested();
                if (r != 0L) {
                    if (!qd.accept(a, v) || r == Long.MAX_VALUE) continue;
                    qd.produced(1L);
                    continue;
                }
                q.clear();
                if (dispose != null) {
                    dispose.dispose();
                }
                a.onError((Throwable)Exceptions.failWithOverflow("Could not emit value due to lack of requests."));
                return;
            }
            if ((missed = qd.leave(-missed)) == 0) break;
        }
    }

    static <Q, S> boolean checkTerminated(boolean d, boolean empty, Subscriber<?> s, boolean delayError, Queue<?> q, QueueDrainSubscriber<?, Q, S> qd) {
        if (qd.cancelled()) {
            q.clear();
            return true;
        }
        if (d) {
            if (delayError) {
                if (empty) {
                    Throwable err = qd.error();
                    if (err != null) {
                        s.onError(err);
                    } else {
                        s.onComplete();
                    }
                    return true;
                }
            } else {
                Throwable err = qd.error();
                if (err != null) {
                    q.clear();
                    s.onError(err);
                    return true;
                }
                if (empty) {
                    s.onComplete();
                    return true;
                }
            }
        }
        return false;
    }
}


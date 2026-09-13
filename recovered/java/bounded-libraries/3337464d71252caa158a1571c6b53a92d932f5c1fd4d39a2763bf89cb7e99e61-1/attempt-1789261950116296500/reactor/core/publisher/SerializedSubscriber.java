/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class SerializedSubscriber<T>
implements InnerOperator<T, T> {
    final CoreSubscriber<? super T> actual;
    boolean drainLoopInProgress;
    boolean concurrentlyAddedContent;
    volatile boolean done;
    volatile boolean cancelled;
    LinkedArrayNode<T> head;
    LinkedArrayNode<T> tail;
    Throwable error;
    Subscription s;

    SerializedSubscriber(CoreSubscriber<? super T> actual) {
        this.actual = actual;
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (Operators.validate(this.s, s)) {
            this.s = s;
            this.actual.onSubscribe(this);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onNext(T t) {
        if (this.cancelled) {
            Operators.onDiscard(t, this.actual.currentContext());
            return;
        }
        if (this.done) {
            Operators.onNextDropped(t, this.actual.currentContext());
            return;
        }
        SerializedSubscriber serializedSubscriber = this;
        synchronized (serializedSubscriber) {
            if (this.cancelled) {
                Operators.onDiscard(t, this.actual.currentContext());
                return;
            }
            if (this.done) {
                Operators.onNextDropped(t, this.actual.currentContext());
                return;
            }
            if (this.drainLoopInProgress) {
                this.serAdd(t);
                this.concurrentlyAddedContent = true;
                return;
            }
            this.drainLoopInProgress = true;
        }
        this.actual.onNext(t);
        this.serDrainLoop(this.actual);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onError(Throwable t) {
        if (this.cancelled || this.done) {
            return;
        }
        SerializedSubscriber serializedSubscriber = this;
        synchronized (serializedSubscriber) {
            if (this.cancelled || this.done) {
                return;
            }
            this.done = true;
            this.error = t;
            if (this.drainLoopInProgress) {
                this.concurrentlyAddedContent = true;
                return;
            }
        }
        this.actual.onError(t);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void onComplete() {
        if (this.cancelled || this.done) {
            return;
        }
        SerializedSubscriber serializedSubscriber = this;
        synchronized (serializedSubscriber) {
            if (this.cancelled || this.done) {
                return;
            }
            this.done = true;
            if (this.drainLoopInProgress) {
                this.concurrentlyAddedContent = true;
                return;
            }
        }
        this.actual.onComplete();
    }

    public void request(long n) {
        this.s.request(n);
    }

    public void cancel() {
        this.cancelled = true;
        this.s.cancel();
    }

    void serAdd(T value) {
        if (this.cancelled) {
            Operators.onDiscard(value, this.actual.currentContext());
            return;
        }
        LinkedArrayNode<T> t = this.tail;
        if (t == null) {
            t = new LinkedArrayNode<T>(value);
            this.head = t;
            this.tail = t;
        } else if (t.count == 16) {
            LinkedArrayNode<T> n = new LinkedArrayNode<T>(value);
            t.next = n;
            this.tail = n;
        } else {
            t.array[t.count++] = value;
        }
        if (this.cancelled) {
            Operators.onDiscard(value, this.actual.currentContext());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void serDrainLoop(CoreSubscriber<? super T> actual) {
        boolean d;
        do {
            LinkedArrayNode<T> n;
            Throwable e;
            if (this.cancelled) {
                SerializedSubscriber serializedSubscriber = this;
                synchronized (serializedSubscriber) {
                    this.discardMultiple(this.head);
                }
                return;
            }
            SerializedSubscriber serializedSubscriber = this;
            synchronized (serializedSubscriber) {
                if (this.cancelled) {
                    this.discardMultiple(this.head);
                    return;
                }
                if (!this.concurrentlyAddedContent) {
                    this.drainLoopInProgress = false;
                    return;
                }
                this.concurrentlyAddedContent = false;
                d = this.done;
                e = this.error;
                n = this.head;
                this.head = null;
                this.tail = null;
            }
            while (n != null) {
                T[] arr = n.array;
                int c = n.count;
                for (int i = 0; i < c; ++i) {
                    if (this.cancelled) {
                        SerializedSubscriber serializedSubscriber2 = this;
                        synchronized (serializedSubscriber2) {
                            this.discardMultiple(n);
                        }
                        return;
                    }
                    actual.onNext(arr[i]);
                }
                n = n.next;
            }
            if (this.cancelled) {
                serializedSubscriber = this;
                synchronized (serializedSubscriber) {
                    this.discardMultiple(this.head);
                }
                return;
            }
            if (e == null) continue;
            actual.onError(e);
            return;
        } while (!d);
        actual.onComplete();
    }

    private void discardMultiple(LinkedArrayNode<T> head) {
        LinkedArrayNode<T> originalHead = head;
        LinkedArrayNode<T> h = head;
        while (h != null) {
            for (int i = 0; i < h.count; ++i) {
                Operators.onDiscard(h.array[i], this.actual.currentContext());
            }
            h = h.next;
            if (h != null || this.head == originalHead) continue;
            h = originalHead = this.head;
        }
    }

    @Override
    public CoreSubscriber<? super T> actual() {
        return this.actual;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.s;
        }
        if (key == Scannable.Attr.ERROR) {
            return this.error;
        }
        if (key == Scannable.Attr.BUFFERED) {
            return this.producerCapacity();
        }
        if (key == Scannable.Attr.CAPACITY) {
            return 16;
        }
        if (key == Scannable.Attr.CANCELLED) {
            return this.cancelled;
        }
        if (key == Scannable.Attr.TERMINATED) {
            return this.done;
        }
        return InnerOperator.super.scanUnsafe(key);
    }

    int producerCapacity() {
        LinkedArrayNode<T> node = this.tail;
        if (node != null) {
            return node.count;
        }
        return 0;
    }

    static final class LinkedArrayNode<T> {
        static final int DEFAULT_CAPACITY = 16;
        final T[] array = new Object[16];
        int count;
        LinkedArrayNode<T> next;

        LinkedArrayNode(T value) {
            this.array[0] = value;
            this.count = 1;
        }
    }
}


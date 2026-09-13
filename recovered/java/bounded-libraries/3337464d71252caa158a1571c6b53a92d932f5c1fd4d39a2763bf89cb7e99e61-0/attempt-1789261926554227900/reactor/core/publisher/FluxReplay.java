/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.OptimizableOperator;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxReplay<T>
extends ConnectableFlux<T>
implements Scannable,
Fuseable,
OptimizableOperator<T, T> {
    final CorePublisher<T> source;
    final int history;
    final long ttl;
    final Scheduler scheduler;
    volatile ReplaySubscriber<T> connection;
    static final AtomicReferenceFieldUpdater<FluxReplay, ReplaySubscriber> CONNECTION = AtomicReferenceFieldUpdater.newUpdater(FluxReplay.class, ReplaySubscriber.class, "connection");
    @Nullable
    final OptimizableOperator<?, T> optimizableOperator;

    FluxReplay(CorePublisher<T> source, int history, long ttl, @Nullable Scheduler scheduler) {
        OptimizableOperator optimSource;
        this.source = Objects.requireNonNull(source, "source");
        this.optimizableOperator = source instanceof OptimizableOperator ? (optimSource = (OptimizableOperator)source) : null;
        if (history <= 0) {
            throw new IllegalArgumentException("History cannot be zero or negative : " + history);
        }
        this.history = history;
        if (scheduler != null && ttl < 0L) {
            throw new IllegalArgumentException("TTL cannot be negative : " + ttl);
        }
        this.ttl = ttl;
        this.scheduler = scheduler;
    }

    @Override
    public int getPrefetch() {
        return this.history;
    }

    ReplaySubscriber<T> newState() {
        if (this.scheduler != null) {
            return new ReplaySubscriber(new SizeAndTimeBoundReplayBuffer(this.history, this.ttl, this.scheduler), this, this.history);
        }
        if (this.history != Integer.MAX_VALUE) {
            return new ReplaySubscriber(new SizeBoundReplayBuffer(this.history), this, this.history);
        }
        return new ReplaySubscriber(new UnboundedReplayBuffer(Queues.SMALL_BUFFER_SIZE), this, Queues.SMALL_BUFFER_SIZE);
    }

    @Override
    public void connect(Consumer<? super Disposable> cancelSupport) {
        ReplaySubscriber<T> s;
        while ((s = this.connection) == null) {
            ReplaySubscriber<T> u = this.newState();
            if (!CONNECTION.compareAndSet(this, null, u)) continue;
            s = u;
            break;
        }
        boolean doConnect = s.tryConnect();
        cancelSupport.accept(s);
        if (doConnect) {
            try {
                this.source.subscribe(s);
            }
            catch (Throwable e) {
                Operators.reportThrowInSubscribe(s, e);
            }
        }
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        try {
            CoreSubscriber<? super T> nextSubscriber = this.subscribeOrReturn((CoreSubscriber<? super T>)actual);
            if (nextSubscriber == null) {
                return;
            }
            this.source.subscribe(nextSubscriber);
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
        }
    }

    @Override
    public final CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) throws Throwable {
        boolean expired;
        ReplaySubscriber<Object> c;
        block3: {
            ReplaySubscriber<T> u;
            do {
                c = this.connection;
                boolean bl = expired = this.scheduler != null && c != null && c.buffer.isExpired();
                if (c != null && !expired) break block3;
            } while (!CONNECTION.compareAndSet(this, c, u = this.newState()));
            c = u;
        }
        ReplayInner<T> inner = new ReplayInner<T>(actual, c);
        actual.onSubscribe(inner);
        c.add(inner);
        if (inner.isCancelled()) {
            c.remove(inner);
            return null;
        }
        c.buffer.replay(inner);
        if (expired) {
            return c;
        }
        return null;
    }

    @Override
    public final CorePublisher<? extends T> source() {
        return this.source;
    }

    @Override
    public final OptimizableOperator<?, ? extends T> nextOptimizableSource() {
        return this.optimizableOperator;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_ON) {
            return this.scheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class ReplayInner<T>
    implements ReplaySubscription<T> {
        final CoreSubscriber<? super T> actual;
        final ReplaySubscriber<T> parent;
        int index;
        int tailIndex;
        Object node;
        int fusionMode;
        long totalRequested;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ReplayInner> WIP = AtomicIntegerFieldUpdater.newUpdater(ReplayInner.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<ReplayInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(ReplayInner.class, "requested");

        ReplayInner(CoreSubscriber<? super T> actual, ReplaySubscriber<T> parent) {
            this.actual = actual;
            this.parent = parent;
        }

        public void request(long n) {
            if (Operators.validate(n) && Operators.addCapCancellable(REQUESTED, this, n) != Long.MIN_VALUE) {
                this.totalRequested = Operators.addCap(this.totalRequested, n);
                this.parent.buffer.replay(this);
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.parent.isTerminated();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.size();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isCancelled();
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return Math.max(0L, this.requested);
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.parent.parent.scheduler;
            }
            return ReplaySubscription.super.scanUnsafe(key);
        }

        public void cancel() {
            if (REQUESTED.getAndSet(this, Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                if (this.enter()) {
                    this.node = null;
                }
            }
        }

        @Override
        public long requested() {
            return this.requested;
        }

        @Override
        public boolean isCancelled() {
            return this.requested == Long.MIN_VALUE;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) != 0) {
                this.fusionMode = 2;
                return 2;
            }
            return 0;
        }

        @Override
        @Nullable
        public T poll() {
            return this.parent.buffer.poll(this);
        }

        @Override
        public void clear() {
            this.parent.buffer.clear(this);
        }

        @Override
        public boolean isEmpty() {
            return this.parent.buffer.isEmpty(this);
        }

        @Override
        public int size() {
            return this.parent.buffer.size(this);
        }

        @Override
        public void node(@Nullable Object node) {
            this.node = node;
        }

        @Override
        public int fusionMode() {
            return this.fusionMode;
        }

        @Override
        @Nullable
        public Object node() {
            return this.node;
        }

        @Override
        public int index() {
            return this.index;
        }

        @Override
        public void index(int index) {
            this.index = index;
        }

        @Override
        public void requestMore(int index) {
            this.index = index;
            long previousState = ReplaySubscriber.markWorkAdded(this.parent);
            if (ReplaySubscriber.isDisposed(previousState)) {
                return;
            }
            if (ReplaySubscriber.isWorkInProgress(previousState)) {
                return;
            }
            this.parent.manageRequest(previousState + 1L);
        }

        @Override
        public int tailIndex() {
            return this.tailIndex;
        }

        @Override
        public void tailIndex(int tailIndex) {
            this.tailIndex = tailIndex;
        }

        @Override
        public boolean enter() {
            return WIP.getAndIncrement(this) == 0;
        }

        @Override
        public int leave(int missed) {
            return WIP.addAndGet(this, -missed);
        }

        @Override
        public void produced(long n) {
            REQUESTED.addAndGet(this, -n);
        }
    }

    static final class ReplaySubscriber<T>
    implements InnerConsumer<T>,
    Disposable {
        final FluxReplay<T> parent;
        final ReplayBuffer<T> buffer;
        final long prefetch;
        final int limit;
        Subscription s;
        int produced;
        int nextPrefetchIndex;
        volatile ReplaySubscription<T>[] subscribers;
        volatile long state;
        static final AtomicLongFieldUpdater<ReplaySubscriber> STATE = AtomicLongFieldUpdater.newUpdater(ReplaySubscriber.class, "state");
        static final ReplaySubscription[] EMPTY = new ReplaySubscription[0];
        static final ReplaySubscription[] TERMINATED = new ReplaySubscription[0];
        static final long CONNECTED_FLAG = 0x1000000000000000L;
        static final long SUBSCRIBED_FLAG = 0x2000000000000000L;
        static final long DISPOSED_FLAG = Long.MIN_VALUE;
        static final long WORK_IN_PROGRESS_MAX_VALUE = 0xFFFFFFFFFFFFFFFL;

        ReplaySubscriber(ReplayBuffer<T> buffer, FluxReplay<T> parent, int prefetch) {
            this.buffer = buffer;
            this.parent = parent;
            this.subscribers = EMPTY;
            this.prefetch = Operators.unboundedOrPrefetch(prefetch);
            this.nextPrefetchIndex = this.limit = Operators.unboundedOrLimit(prefetch);
        }

        @Override
        public void onSubscribe(Subscription s) {
            if (this.buffer.isDone()) {
                s.cancel();
                return;
            }
            if (Operators.validate(this.s, s)) {
                this.s = s;
                long previousState = ReplaySubscriber.markSubscribed(this);
                if (ReplaySubscriber.isDisposed(previousState)) {
                    s.cancel();
                    return;
                }
                s.request(this.prefetch);
            }
        }

        void manageRequest(long currentState) {
            Subscription p = this.s;
            do {
                boolean shouldPrefetch;
                int nextPrefetchIndex = this.nextPrefetchIndex;
                ReplaySubscription<T>[] subscribers = this.subscribers;
                if (subscribers.length > 0) {
                    shouldPrefetch = true;
                    for (ReplaySubscription<T> rp : subscribers) {
                        if (rp.index() >= nextPrefetchIndex) continue;
                        shouldPrefetch = false;
                        break;
                    }
                } else {
                    boolean bl = shouldPrefetch = this.produced >= nextPrefetchIndex;
                }
                if (shouldPrefetch) {
                    int limit = this.limit;
                    this.nextPrefetchIndex = nextPrefetchIndex + limit;
                    p.request((long)limit);
                }
                if (!ReplaySubscriber.isDisposed(currentState = ReplaySubscriber.markWorkDone(this, currentState))) continue;
                return;
            } while (ReplaySubscriber.isWorkInProgress(currentState));
        }

        public void onNext(T t) {
            ReplayBuffer<T> b = this.buffer;
            if (b.isDone()) {
                Operators.onNextDropped(t, this.currentContext());
                return;
            }
            ++this.produced;
            b.add(t);
            ReplaySubscription<T>[] subscribers = this.subscribers;
            if (subscribers.length == 0) {
                if (this.produced % this.limit == 0) {
                    long previousState = ReplaySubscriber.markWorkAdded(this);
                    if (ReplaySubscriber.isDisposed(previousState)) {
                        return;
                    }
                    if (ReplaySubscriber.isWorkInProgress(previousState)) {
                        return;
                    }
                    this.manageRequest(previousState + 1L);
                }
                return;
            }
            for (ReplaySubscription<T> rs : subscribers) {
                b.replay(rs);
            }
        }

        public void onError(Throwable t) {
            ReplayBuffer<T> b = this.buffer;
            if (b.isDone()) {
                Operators.onErrorDropped(t, this.currentContext());
            } else {
                b.onError(t);
                for (ReplaySubscription<T> rs : this.terminate()) {
                    b.replay(rs);
                }
            }
        }

        public void onComplete() {
            ReplayBuffer<T> b = this.buffer;
            if (!b.isDone()) {
                b.onComplete();
                for (ReplaySubscription<T> rs : this.terminate()) {
                    b.replay(rs);
                }
            }
        }

        @Override
        public void dispose() {
            long previousState = ReplaySubscriber.markDisposed(this);
            if (ReplaySubscriber.isDisposed(previousState)) {
                return;
            }
            if (ReplaySubscriber.isSubscribed(previousState)) {
                this.s.cancel();
            }
            CONNECTION.lazySet(this.parent, null);
            CancellationException ex = new CancellationException("Disconnected");
            ReplayBuffer<T> buffer = this.buffer;
            buffer.onError(ex);
            for (ReplaySubscription<T> inner : this.terminate()) {
                buffer.replay(inner);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean add(ReplayInner<T> inner) {
            if (this.subscribers == TERMINATED) {
                return false;
            }
            ReplaySubscriber replaySubscriber = this;
            synchronized (replaySubscriber) {
                ReplaySubscription<T>[] a = this.subscribers;
                if (a == TERMINATED) {
                    return false;
                }
                int n = a.length;
                ReplayInner[] b = new ReplayInner[n + 1];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = inner;
                this.subscribers = b;
                return true;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void remove(ReplaySubscription<T> inner) {
            ReplaySubscription<T>[] a = this.subscribers;
            if (a == TERMINATED || a == EMPTY) {
                return;
            }
            ReplaySubscriber replaySubscriber = this;
            synchronized (replaySubscriber) {
                ReplaySubscription[] b;
                a = this.subscribers;
                if (a == TERMINATED || a == EMPTY) {
                    return;
                }
                int j = -1;
                int n = a.length;
                for (int i = 0; i < n; ++i) {
                    if (a[i] != inner) continue;
                    j = i;
                    break;
                }
                if (j < 0) {
                    return;
                }
                if (n == 1) {
                    b = EMPTY;
                } else {
                    b = new ReplayInner[n - 1];
                    System.arraycopy(a, 0, b, 0, j);
                    System.arraycopy(a, j + 1, b, j, n - j - 1);
                }
                this.subscribers = b;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        ReplaySubscription<T>[] terminate() {
            ReplaySubscription<T>[] a = this.subscribers;
            if (a == TERMINATED) {
                return a;
            }
            ReplaySubscriber replaySubscriber = this;
            synchronized (replaySubscriber) {
                a = this.subscribers;
                if (a != TERMINATED) {
                    this.subscribers = TERMINATED;
                }
                return a;
            }
        }

        boolean isTerminated() {
            return this.subscribers == TERMINATED;
        }

        boolean tryConnect() {
            return ReplaySubscriber.markConnected(this);
        }

        @Override
        public Context currentContext() {
            return Operators.multiSubscribersContext(this.subscribers);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return Integer.MAX_VALUE;
            }
            if (key == Scannable.Attr.CAPACITY) {
                return this.buffer.capacity();
            }
            if (key == Scannable.Attr.ERROR) {
                return this.buffer.getError();
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.buffer.size();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.isTerminated();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        @Override
        public boolean isDisposed() {
            return ReplaySubscriber.isDisposed(this.state);
        }

        static boolean markConnected(ReplaySubscriber<?> instance) {
            long state;
            do {
                if (!ReplaySubscriber.isConnected(state = instance.state)) continue;
                return false;
            } while (!STATE.compareAndSet(instance, state, state | 0x1000000000000000L));
            return true;
        }

        static long markSubscribed(ReplaySubscriber<?> instance) {
            long state;
            do {
                if (!ReplaySubscriber.isDisposed(state = instance.state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, state | 0x2000000000000000L));
            return state;
        }

        static long markWorkAdded(ReplaySubscriber<?> instance) {
            long state;
            do {
                if (ReplaySubscriber.isDisposed(state = instance.state)) {
                    return state;
                }
                if ((state & 0xFFFFFFFFFFFFFFFL) != 0xFFFFFFFFFFFFFFFL) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, state + 1L));
            return state;
        }

        static long markWorkDone(ReplaySubscriber<?> instance, long currentState) {
            long nextState;
            long state;
            do {
                if (currentState == (state = instance.state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, nextState = state & 0xF000000000000000L));
            return nextState;
        }

        static long markDisposed(ReplaySubscriber<?> instance) {
            long state;
            do {
                if (!ReplaySubscriber.isDisposed(state = instance.state)) continue;
                return state;
            } while (!STATE.compareAndSet(instance, state, state | Long.MIN_VALUE));
            return state;
        }

        static boolean isConnected(long state) {
            return (state & 0x1000000000000000L) == 0x1000000000000000L;
        }

        static boolean isSubscribed(long state) {
            return (state & 0x2000000000000000L) == 0x2000000000000000L;
        }

        static boolean isWorkInProgress(long state) {
            return (state & 0xFFFFFFFFFFFFFFFL) > 0L;
        }

        static boolean isDisposed(long state) {
            return (state & Long.MIN_VALUE) == Long.MIN_VALUE;
        }
    }

    static final class SizeBoundReplayBuffer<T>
    implements ReplayBuffer<T> {
        final int limit;
        final int indexUpdateLimit;
        volatile Node<T> head;
        Node<T> tail;
        int size;
        volatile boolean done;
        Throwable error;

        SizeBoundReplayBuffer(int limit) {
            if (limit < 0) {
                throw new IllegalArgumentException("Limit cannot be negative");
            }
            this.limit = limit;
            this.indexUpdateLimit = Operators.unboundedOrLimit(limit);
            Node<Object> n = new Node<Object>(-1, null);
            this.tail = n;
            this.head = n;
        }

        @Override
        public boolean isExpired() {
            return false;
        }

        @Override
        public int capacity() {
            return this.limit;
        }

        @Override
        public void add(T value) {
            Node<T> tail = this.tail;
            Node<T> n = new Node<T>(tail.index + 1, value);
            tail.set(n);
            this.tail = n;
            int s = this.size;
            if (s == this.limit) {
                this.head = (Node)this.head.get();
            } else {
                this.size = s + 1;
            }
        }

        @Override
        public void onError(Throwable ex) {
            this.error = ex;
            this.done = true;
        }

        @Override
        public void onComplete() {
            this.done = true;
        }

        void replayNormal(ReplaySubscription<T> rs) {
            CoreSubscriber<T> a = rs.actual();
            int missed = 1;
            do {
                boolean d;
                long r = rs.requested();
                long e = 0L;
                Node node = (Node)rs.node();
                if (node == null) {
                    node = this.head;
                }
                while (e != r) {
                    boolean empty;
                    if (rs.isCancelled()) {
                        rs.node(null);
                        return;
                    }
                    d = this.done;
                    Node next = (Node)node.get();
                    boolean bl = empty = next == null;
                    if (d && empty) {
                        rs.node(null);
                        Throwable ex = this.error;
                        if (ex != null) {
                            a.onError(ex);
                        } else {
                            a.onComplete();
                        }
                        return;
                    }
                    if (empty) break;
                    a.onNext(next.value);
                    ++e;
                    node = next;
                    if ((next.index + 1) % this.indexUpdateLimit != 0) continue;
                    rs.requestMore(next.index + 1);
                }
                if (e == r) {
                    boolean empty;
                    if (rs.isCancelled()) {
                        rs.node(null);
                        return;
                    }
                    d = this.done;
                    boolean bl = empty = node.get() == null;
                    if (d && empty) {
                        rs.node(null);
                        Throwable ex = this.error;
                        if (ex != null) {
                            a.onError(ex);
                        } else {
                            a.onComplete();
                        }
                        return;
                    }
                }
                if (e != 0L && r != Long.MAX_VALUE) {
                    rs.produced(e);
                }
                rs.node(node);
            } while ((missed = rs.leave(missed)) != 0);
        }

        void replayFused(ReplaySubscription<T> rs) {
            int missed = 1;
            CoreSubscriber<T> a = rs.actual();
            do {
                if (rs.isCancelled()) {
                    rs.node(null);
                    return;
                }
                boolean d = this.done;
                a.onNext(null);
                if (!d) continue;
                Throwable ex = this.error;
                if (ex != null) {
                    a.onError(ex);
                } else {
                    a.onComplete();
                }
                return;
            } while ((missed = rs.leave(missed)) != 0);
        }

        @Override
        public void replay(ReplaySubscription<T> rs) {
            if (!rs.enter()) {
                return;
            }
            if (rs.fusionMode() == 0) {
                this.replayNormal(rs);
            } else {
                this.replayFused(rs);
            }
        }

        @Override
        @Nullable
        public Throwable getError() {
            return this.error;
        }

        @Override
        public boolean isDone() {
            return this.done;
        }

        @Override
        @Nullable
        public T poll(ReplaySubscription<T> rs) {
            Node next;
            Node<T> node = (Node<T>)rs.node();
            if (node == null) {
                node = this.head;
                rs.node(node);
            }
            if ((next = (Node)node.get()) == null) {
                return null;
            }
            rs.node(next);
            if ((next.index + 1) % this.indexUpdateLimit == 0) {
                rs.requestMore(next.index + 1);
            }
            return next.value;
        }

        @Override
        public void clear(ReplaySubscription<T> rs) {
            rs.node(null);
        }

        @Override
        public boolean isEmpty(ReplaySubscription<T> rs) {
            Node<T> node = (Node<T>)rs.node();
            if (node == null) {
                node = this.head;
                rs.node(node);
            }
            return node.get() == null;
        }

        @Override
        public int size(ReplaySubscription<T> rs) {
            Node next;
            int count;
            Node node = (Node)rs.node();
            if (node == null) {
                node = this.head;
            }
            for (count = 0; (next = (Node)node.get()) != null && count != Integer.MAX_VALUE; ++count) {
                node = next;
            }
            return count;
        }

        @Override
        public int size() {
            Node next;
            int count;
            Node node = this.head;
            for (count = 0; (next = (Node)node.get()) != null && count != Integer.MAX_VALUE; ++count) {
                node = next;
            }
            return count;
        }

        static final class Node<T>
        extends AtomicReference<Node<T>> {
            private static final long serialVersionUID = 3713592843205853725L;
            final int index;
            final T value;

            Node(int index, @Nullable T value) {
                this.index = index;
                this.value = value;
            }

            @Override
            public String toString() {
                return "Node(" + this.value + ")";
            }
        }
    }

    static final class UnboundedReplayBuffer<T>
    implements ReplayBuffer<T> {
        final int batchSize;
        final int indexUpdateLimit;
        volatile int size;
        final Object[] head;
        Object[] tail;
        int tailIndex;
        volatile boolean done;
        Throwable error;

        UnboundedReplayBuffer(int batchSize) {
            this.batchSize = batchSize;
            this.indexUpdateLimit = Operators.unboundedOrLimit(batchSize);
            Object[] n = new Object[batchSize + 1];
            this.tail = n;
            this.head = n;
        }

        @Override
        public boolean isExpired() {
            return false;
        }

        @Override
        @Nullable
        public Throwable getError() {
            return this.error;
        }

        @Override
        public int capacity() {
            return Integer.MAX_VALUE;
        }

        @Override
        public void add(T value) {
            int i = this.tailIndex;
            Object[] a = this.tail;
            if (i == a.length - 1) {
                Object[] b = new Object[a.length];
                b[0] = value;
                this.tailIndex = 1;
                a[i] = b;
                this.tail = b;
            } else {
                a[i] = value;
                this.tailIndex = i + 1;
            }
            ++this.size;
        }

        @Override
        public void onError(Throwable ex) {
            this.error = ex;
            this.done = true;
        }

        @Override
        public void onComplete() {
            this.done = true;
        }

        void replayNormal(ReplaySubscription<T> rs) {
            int missed = 1;
            CoreSubscriber<T> a = rs.actual();
            int n = this.batchSize;
            do {
                Throwable ex;
                boolean empty;
                boolean d;
                long r = rs.requested();
                long e = 0L;
                Object[] node = (Object[])rs.node();
                if (node == null) {
                    node = this.head;
                }
                int tailIndex = rs.tailIndex();
                int index = rs.index();
                while (e != r) {
                    if (rs.isCancelled()) {
                        rs.node(null);
                        return;
                    }
                    d = this.done;
                    boolean bl = empty = index == this.size;
                    if (d && empty) {
                        rs.node(null);
                        ex = this.error;
                        if (ex != null) {
                            a.onError(ex);
                        } else {
                            a.onComplete();
                        }
                        return;
                    }
                    if (empty) break;
                    if (tailIndex == n) {
                        node = (Object[])node[tailIndex];
                        tailIndex = 0;
                    }
                    Object v = node[tailIndex];
                    a.onNext(v);
                    ++e;
                    ++tailIndex;
                    if (++index % this.indexUpdateLimit != 0) continue;
                    rs.requestMore(index);
                }
                if (e == r) {
                    if (rs.isCancelled()) {
                        rs.node(null);
                        return;
                    }
                    d = this.done;
                    boolean bl = empty = index == this.size;
                    if (d && empty) {
                        rs.node(null);
                        ex = this.error;
                        if (ex != null) {
                            a.onError(ex);
                        } else {
                            a.onComplete();
                        }
                        return;
                    }
                }
                if (e != 0L && r != Long.MAX_VALUE) {
                    rs.produced(e);
                }
                rs.index(index);
                rs.tailIndex(tailIndex);
                rs.node(node);
            } while ((missed = rs.leave(missed)) != 0);
        }

        void replayFused(ReplaySubscription<T> rs) {
            int missed = 1;
            CoreSubscriber<T> a = rs.actual();
            do {
                if (rs.isCancelled()) {
                    rs.node(null);
                    return;
                }
                boolean d = this.done;
                a.onNext(null);
                if (!d) continue;
                Throwable ex = this.error;
                if (ex != null) {
                    a.onError(ex);
                } else {
                    a.onComplete();
                }
                return;
            } while ((missed = rs.leave(missed)) != 0);
        }

        @Override
        public void replay(ReplaySubscription<T> rs) {
            if (!rs.enter()) {
                return;
            }
            if (rs.fusionMode() == 0) {
                this.replayNormal(rs);
            } else {
                this.replayFused(rs);
            }
        }

        @Override
        public boolean isDone() {
            return this.done;
        }

        @Override
        @Nullable
        public T poll(ReplaySubscription<T> rs) {
            int tailIndex;
            int index = rs.index();
            if (index == this.size) {
                return null;
            }
            Object[] node = (Object[])rs.node();
            if (node == null) {
                node = this.head;
                rs.node(node);
            }
            if ((tailIndex = rs.tailIndex()) == this.batchSize) {
                node = (Object[])node[tailIndex];
                tailIndex = 0;
                rs.node(node);
            }
            Object v = node[tailIndex];
            rs.tailIndex(tailIndex + 1);
            if ((index + 1) % this.indexUpdateLimit == 0) {
                rs.requestMore(index + 1);
            } else {
                rs.index(index + 1);
            }
            return (T)v;
        }

        @Override
        public void clear(ReplaySubscription<T> rs) {
            rs.node(null);
        }

        @Override
        public boolean isEmpty(ReplaySubscription<T> rs) {
            return rs.index() == this.size;
        }

        @Override
        public int size(ReplaySubscription<T> rs) {
            return this.size - rs.index();
        }

        @Override
        public int size() {
            return this.size;
        }
    }

    static final class SizeAndTimeBoundReplayBuffer<T>
    implements ReplayBuffer<T> {
        final int limit;
        final int indexUpdateLimit;
        final long maxAge;
        final Scheduler scheduler;
        int size;
        volatile TimedNode<T> head;
        TimedNode<T> tail;
        Throwable error;
        static final long NOT_DONE = Long.MIN_VALUE;
        volatile long done = Long.MIN_VALUE;

        SizeAndTimeBoundReplayBuffer(int limit, long maxAge, Scheduler scheduler) {
            this.limit = limit;
            this.indexUpdateLimit = Operators.unboundedOrLimit(limit);
            this.maxAge = maxAge;
            this.scheduler = scheduler;
            TimedNode<Object> h = new TimedNode<Object>(-1, null, 0L);
            this.tail = h;
            this.head = h;
        }

        @Override
        public boolean isExpired() {
            long done = this.done;
            return done != Long.MIN_VALUE && this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge > done;
        }

        void replayNormal(ReplaySubscription<T> rs) {
            int missed = 1;
            CoreSubscriber<T> a = rs.actual();
            do {
                boolean d;
                long e;
                TimedNode node;
                if ((node = (TimedNode)rs.node()) == null) {
                    node = this.head;
                    if (this.done == Long.MIN_VALUE) {
                        long ts;
                        long limit = this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge;
                        TimedNode next = node;
                        while (next != null && (ts = next.time) <= limit) {
                            node = next;
                            next = (TimedNode)node.get();
                        }
                    }
                }
                long r = rs.requested();
                for (e = 0L; e != r; ++e) {
                    boolean empty;
                    if (rs.isCancelled()) {
                        rs.node(null);
                        return;
                    }
                    d = this.done != Long.MIN_VALUE;
                    TimedNode next = (TimedNode)node.get();
                    boolean bl = empty = next == null;
                    if (d && empty) {
                        rs.node(null);
                        Throwable ex = this.error;
                        if (ex != null) {
                            a.onError(ex);
                        } else {
                            a.onComplete();
                        }
                        return;
                    }
                    if (empty) break;
                    a.onNext(next.value);
                    node = next;
                    if ((next.index + 1) % this.indexUpdateLimit != 0) continue;
                    rs.requestMore(next.index + 1);
                }
                if (e == r) {
                    boolean empty;
                    if (rs.isCancelled()) {
                        rs.node(null);
                        return;
                    }
                    d = this.done != Long.MIN_VALUE;
                    boolean bl = empty = node.get() == null;
                    if (d && empty) {
                        rs.node(null);
                        Throwable ex = this.error;
                        if (ex != null) {
                            a.onError(ex);
                        } else {
                            a.onComplete();
                        }
                        return;
                    }
                }
                if (e != 0L && r != Long.MAX_VALUE) {
                    rs.produced(e);
                }
                rs.node(node);
            } while ((missed = rs.leave(missed)) != 0);
        }

        void replayFused(ReplaySubscription<T> rs) {
            int missed = 1;
            CoreSubscriber<T> a = rs.actual();
            do {
                if (rs.isCancelled()) {
                    rs.node(null);
                    return;
                }
                boolean d = this.done != Long.MIN_VALUE;
                a.onNext(null);
                if (!d) continue;
                Throwable ex = this.error;
                if (ex != null) {
                    a.onError(ex);
                } else {
                    a.onComplete();
                }
                return;
            } while ((missed = rs.leave(missed)) != 0);
        }

        @Override
        public void onError(Throwable ex) {
            this.done = this.scheduler.now(TimeUnit.NANOSECONDS);
            this.error = ex;
        }

        @Override
        @Nullable
        public Throwable getError() {
            return this.error;
        }

        @Override
        public void onComplete() {
            this.done = this.scheduler.now(TimeUnit.NANOSECONDS);
        }

        @Override
        public boolean isDone() {
            return this.done != Long.MIN_VALUE;
        }

        TimedNode<T> latestHead(ReplaySubscription<T> rs) {
            TimedNode n;
            long now = this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge;
            TimedNode h = (TimedNode)rs.node();
            if (h == null) {
                h = this.head;
            }
            while ((n = (TimedNode)h.get()) != null && n.time <= now) {
                h = n;
            }
            return h;
        }

        @Override
        @Nullable
        public T poll(ReplaySubscription<T> rs) {
            TimedNode next;
            TimedNode node = this.latestHead(rs);
            long now = this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge;
            while ((next = (TimedNode)node.get()) != null) {
                if (next.time > now) {
                    node = next;
                    break;
                }
                node = next;
            }
            if (next == null) {
                if (node.index != -1 && (node.index + 1) % this.indexUpdateLimit == 0) {
                    rs.requestMore(node.index + 1);
                }
                return null;
            }
            rs.node(next);
            if ((next.index + 1) % this.indexUpdateLimit == 0) {
                rs.requestMore(next.index + 1);
            }
            return node.value;
        }

        @Override
        public void clear(ReplaySubscription<T> rs) {
            rs.node(null);
        }

        @Override
        public boolean isEmpty(ReplaySubscription<T> rs) {
            TimedNode<T> node = this.latestHead(rs);
            return node.get() == null;
        }

        @Override
        public int size(ReplaySubscription<T> rs) {
            TimedNode next;
            int count;
            TimedNode node = this.latestHead(rs);
            for (count = 0; (next = (TimedNode)node.get()) != null && count != Integer.MAX_VALUE; ++count) {
                node = next;
            }
            return count;
        }

        @Override
        public int size() {
            TimedNode next;
            int count;
            TimedNode node = this.head;
            for (count = 0; (next = (TimedNode)node.get()) != null && count != Integer.MAX_VALUE; ++count) {
                node = next;
            }
            return count;
        }

        @Override
        public int capacity() {
            return this.limit;
        }

        @Override
        public void add(T value) {
            TimedNode next;
            TimedNode<T> tail = this.tail;
            TimedNode<T> valueNode = new TimedNode<T>(tail.index + 1, value, this.scheduler.now(TimeUnit.NANOSECONDS));
            tail.set(valueNode);
            this.tail = valueNode;
            int s = this.size;
            if (s == this.limit) {
                this.head = (TimedNode)this.head.get();
            } else {
                this.size = s + 1;
            }
            long limit = this.scheduler.now(TimeUnit.NANOSECONDS) - this.maxAge;
            if (this.maxAge == 0L) {
                this.head = valueNode;
                return;
            }
            TimedNode h = this.head;
            int removed = 0;
            while ((next = (TimedNode)h.get()) != null) {
                if (next.time > limit || next == valueNode) {
                    if (removed == 0) break;
                    this.size -= removed;
                    this.head = h;
                    break;
                }
                h = next;
                ++removed;
            }
        }

        @Override
        public void replay(ReplaySubscription<T> rs) {
            if (!rs.enter()) {
                return;
            }
            if (rs.fusionMode() == 0) {
                this.replayNormal(rs);
            } else {
                this.replayFused(rs);
            }
        }

        static final class TimedNode<T>
        extends AtomicReference<TimedNode<T>> {
            final int index;
            final T value;
            final long time;

            TimedNode(int index, @Nullable T value, long time) {
                this.index = index;
                this.value = value;
                this.time = time;
            }

            @Override
            public String toString() {
                return "TimedNode{index=" + this.index + ", value=" + this.value + ", time=" + this.time + '}';
            }
        }
    }

    static interface ReplayBuffer<T> {
        public void add(T var1);

        public void onError(Throwable var1);

        @Nullable
        public Throwable getError();

        public void onComplete();

        public void replay(ReplaySubscription<T> var1);

        public boolean isDone();

        @Nullable
        public T poll(ReplaySubscription<T> var1);

        public void clear(ReplaySubscription<T> var1);

        public boolean isEmpty(ReplaySubscription<T> var1);

        public int size(ReplaySubscription<T> var1);

        public int size();

        public int capacity();

        public boolean isExpired();
    }

    static interface ReplaySubscription<T>
    extends Fuseable.QueueSubscription<T>,
    InnerProducer<T> {
        @Override
        public CoreSubscriber<? super T> actual();

        public boolean enter();

        public int leave(int var1);

        public void produced(long var1);

        public void node(@Nullable Object var1);

        @Nullable
        public Object node();

        public int tailIndex();

        public void tailIndex(int var1);

        public int index();

        public void index(int var1);

        public int fusionMode();

        public boolean isCancelled();

        public long requested();

        public void requestMore(int var1);
    }
}


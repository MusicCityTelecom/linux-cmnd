/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.errorprone.annotations.concurrent.GuardedBy
 *  org.checkerframework.checker.nullness.qual.Nullable
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.AccessOrderDeque;
import com.github.benmanes.caffeine.cache.Async;
import com.github.benmanes.caffeine.cache.AsyncCacheLoader;
import com.github.benmanes.caffeine.cache.BLCHeader;
import com.github.benmanes.caffeine.cache.BoundedBuffer;
import com.github.benmanes.caffeine.cache.Buffer;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.FrequencySketch;
import com.github.benmanes.caffeine.cache.LinkedDeque;
import com.github.benmanes.caffeine.cache.LocalAsyncCache;
import com.github.benmanes.caffeine.cache.LocalAsyncLoadingCache;
import com.github.benmanes.caffeine.cache.LocalCache;
import com.github.benmanes.caffeine.cache.LocalCacheFactory;
import com.github.benmanes.caffeine.cache.LocalLoadingCache;
import com.github.benmanes.caffeine.cache.LocalManualCache;
import com.github.benmanes.caffeine.cache.MpscGrowableArrayQueue;
import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.NodeFactory;
import com.github.benmanes.caffeine.cache.Pacer;
import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.References;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import com.github.benmanes.caffeine.cache.SerializationProxy;
import com.github.benmanes.caffeine.cache.SnapshotEntry;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.benmanes.caffeine.cache.TimerWheel;
import com.github.benmanes.caffeine.cache.Weigher;
import com.github.benmanes.caffeine.cache.WriteOrderDeque;
import com.github.benmanes.caffeine.cache.WriteThroughEntry;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.time.Duration;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.checkerframework.checker.nullness.qual.Nullable;

abstract class BoundedLocalCache<K, V>
extends BLCHeader.DrainStatusRef
implements LocalCache<K, V> {
    static final System.Logger logger = System.getLogger(BoundedLocalCache.class.getName());
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    static final int WRITE_BUFFER_MIN = 4;
    static final int WRITE_BUFFER_MAX = 128 * Caffeine.ceilingPowerOfTwo(NCPU);
    static final int WRITE_BUFFER_RETRIES = 100;
    static final long MAXIMUM_CAPACITY = 9223372034707292160L;
    static final double PERCENT_MAIN = 0.99;
    static final double PERCENT_MAIN_PROTECTED = 0.8;
    static final double HILL_CLIMBER_RESTART_THRESHOLD = 0.05;
    static final double HILL_CLIMBER_STEP_PERCENT = 0.0625;
    static final double HILL_CLIMBER_STEP_DECAY_RATE = 0.98;
    static final int QUEUE_TRANSFER_THRESHOLD = 1000;
    static final long EXPIRE_WRITE_TOLERANCE = TimeUnit.SECONDS.toNanos(1L);
    static final long MAXIMUM_EXPIRY = 0x3FFFFFFFFFFFFFFFL;
    static final long WARN_AFTER_LOCK_WAIT_NANOS = TimeUnit.SECONDS.toNanos(30L);
    static final VarHandle REFRESHES;
    final @Nullable RemovalListener<K, V> evictionListener;
    final @Nullable AsyncCacheLoader<K, V> cacheLoader;
    final MpscGrowableArrayQueue<Runnable> writeBuffer;
    final ConcurrentHashMap<Object, Node<K, V>> data;
    final PerformCleanupTask drainBuffersTask;
    final Consumer<Node<K, V>> accessPolicy;
    final Buffer<Node<K, V>> readBuffer;
    final NodeFactory<K, V> nodeFactory;
    final ReentrantLock evictionLock;
    final Weigher<K, V> weigher;
    final Executor executor;
    final boolean isWeighted;
    final boolean isAsync;
    @Nullable Set<K> keySet;
    @Nullable Collection<V> values;
    @Nullable Set<Map.Entry<K, V>> entrySet;
    volatile @Nullable ConcurrentMap<Object, CompletableFuture<?>> refreshes;

    protected BoundedLocalCache(Caffeine<K, V> builder, @Nullable AsyncCacheLoader<K, V> cacheLoader, boolean isAsync) {
        this.isAsync = isAsync;
        this.cacheLoader = cacheLoader;
        this.executor = builder.getExecutor();
        this.isWeighted = builder.isWeighted();
        this.evictionLock = new ReentrantLock();
        this.weigher = builder.getWeigher(isAsync);
        this.drainBuffersTask = new PerformCleanupTask(this);
        this.nodeFactory = NodeFactory.newFactory(builder, isAsync);
        this.evictionListener = builder.getEvictionListener(isAsync);
        this.data = new ConcurrentHashMap(builder.getInitialCapacity());
        this.readBuffer = this.evicts() || this.collectKeys() || this.collectValues() || this.expiresAfterAccess() ? new BoundedBuffer<Node<K, V>>() : Buffer.disabled();
        this.accessPolicy = this.evicts() || this.expiresAfterAccess() ? this::onAccess : e -> {};
        this.writeBuffer = new MpscGrowableArrayQueue(4, WRITE_BUFFER_MAX);
        if (this.evicts()) {
            this.setMaximumSize(builder.getMaximum());
        }
    }

    @Override
    public boolean isAsync() {
        return this.isAsync;
    }

    final boolean isComputingAsync(Node<?, ?> node) {
        return this.isAsync && !Async.isReady((CompletableFuture)node.getValue());
    }

    @GuardedBy(value="evictionLock")
    protected AccessOrderDeque<Node<K, V>> accessOrderWindowDeque() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected AccessOrderDeque<Node<K, V>> accessOrderProbationDeque() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected AccessOrderDeque<Node<K, V>> accessOrderProtectedDeque() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected WriteOrderDeque<Node<K, V>> writeOrderDeque() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Executor executor() {
        return this.executor;
    }

    @Override
    public ConcurrentMap<Object, CompletableFuture<?>> refreshes() {
        ConcurrentMap<Object, CompletableFuture<?>> pending = this.refreshes;
        if (pending == null && !REFRESHES.compareAndSet(this, null, pending = new ConcurrentHashMap())) {
            pending = this.refreshes;
        }
        return pending;
    }

    void discardRefresh(Object keyReference) {
        ConcurrentMap<Object, CompletableFuture<?>> pending = this.refreshes;
        if (pending != null) {
            pending.remove(keyReference);
        }
    }

    @Override
    public Object referenceKey(K key) {
        return this.nodeFactory.newLookupKey(key);
    }

    @Override
    public boolean isRecordingStats() {
        return false;
    }

    @Override
    public StatsCounter statsCounter() {
        return StatsCounter.disabledStatsCounter();
    }

    @Override
    public Ticker statsTicker() {
        return Ticker.disabledTicker();
    }

    protected RemovalListener<K, V> removalListener() {
        return null;
    }

    protected boolean hasRemovalListener() {
        return false;
    }

    @Override
    public void notifyRemoval(@Nullable K key, @Nullable V value, RemovalCause cause) {
        if (!this.hasRemovalListener()) {
            return;
        }
        Runnable task = () -> {
            try {
                this.removalListener().onRemoval(key, value, cause);
            }
            catch (Throwable t) {
                logger.log(System.Logger.Level.WARNING, "Exception thrown by removal listener", t);
            }
        };
        try {
            this.executor.execute(task);
        }
        catch (Throwable t) {
            logger.log(System.Logger.Level.ERROR, "Exception thrown when submitting removal listener", t);
            task.run();
        }
    }

    void notifyEviction(@Nullable K key, @Nullable V value, RemovalCause cause) {
        if (this.evictionListener == null) {
            return;
        }
        try {
            this.evictionListener.onRemoval(key, value, cause);
        }
        catch (Throwable t) {
            logger.log(System.Logger.Level.WARNING, "Exception thrown by eviction listener", t);
        }
    }

    protected boolean collectKeys() {
        return false;
    }

    protected boolean collectValues() {
        return false;
    }

    protected ReferenceQueue<K> keyReferenceQueue() {
        return null;
    }

    protected ReferenceQueue<V> valueReferenceQueue() {
        return null;
    }

    protected @Nullable Pacer pacer() {
        return null;
    }

    protected boolean expiresVariable() {
        return false;
    }

    protected boolean expiresAfterAccess() {
        return false;
    }

    protected long expiresAfterAccessNanos() {
        throw new UnsupportedOperationException();
    }

    protected void setExpiresAfterAccessNanos(long expireAfterAccessNanos) {
        throw new UnsupportedOperationException();
    }

    protected boolean expiresAfterWrite() {
        return false;
    }

    protected long expiresAfterWriteNanos() {
        throw new UnsupportedOperationException();
    }

    protected void setExpiresAfterWriteNanos(long expireAfterWriteNanos) {
        throw new UnsupportedOperationException();
    }

    protected boolean refreshAfterWrite() {
        return false;
    }

    protected long refreshAfterWriteNanos() {
        throw new UnsupportedOperationException();
    }

    protected void setRefreshAfterWriteNanos(long refreshAfterWriteNanos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasWriteTime() {
        return this.expiresAfterWrite() || this.refreshAfterWrite();
    }

    @Override
    public Expiry<K, V> expiry() {
        return null;
    }

    public Ticker expirationTicker() {
        return Ticker.disabledTicker();
    }

    protected TimerWheel<K, V> timerWheel() {
        throw new UnsupportedOperationException();
    }

    protected boolean evicts() {
        return false;
    }

    protected boolean isWeighted() {
        return this.weigher != Weigher.singletonWeigher();
    }

    protected FrequencySketch<K> frequencySketch() {
        throw new UnsupportedOperationException();
    }

    protected boolean fastpath() {
        return false;
    }

    protected long maximum() {
        throw new UnsupportedOperationException();
    }

    protected long windowMaximum() {
        throw new UnsupportedOperationException();
    }

    protected long mainProtectedMaximum() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setMaximum(long maximum) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setWindowMaximum(long maximum) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setMainProtectedMaximum(long maximum) {
        throw new UnsupportedOperationException();
    }

    protected long weightedSize() {
        throw new UnsupportedOperationException();
    }

    protected long windowWeightedSize() {
        throw new UnsupportedOperationException();
    }

    protected long mainProtectedWeightedSize() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setWeightedSize(long weightedSize) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setWindowWeightedSize(long weightedSize) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setMainProtectedWeightedSize(long weightedSize) {
        throw new UnsupportedOperationException();
    }

    protected int hitsInSample() {
        throw new UnsupportedOperationException();
    }

    protected int missesInSample() {
        throw new UnsupportedOperationException();
    }

    protected int sampleCount() {
        throw new UnsupportedOperationException();
    }

    protected double stepSize() {
        throw new UnsupportedOperationException();
    }

    protected double previousSampleHitRate() {
        throw new UnsupportedOperationException();
    }

    protected long adjustment() {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setHitsInSample(int hitCount) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setMissesInSample(int missCount) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setSampleCount(int sampleCount) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setStepSize(double stepSize) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setPreviousSampleHitRate(double hitRate) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    protected void setAdjustment(long amount) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy(value="evictionLock")
    void setMaximumSize(long maximum) {
        Caffeine.requireArgument(maximum >= 0L, "maximum must not be negative", new Object[0]);
        if (maximum == this.maximum()) {
            return;
        }
        long max = Math.min(maximum, 9223372034707292160L);
        long window = max - (long)(0.99 * (double)max);
        long mainProtected = (long)(0.8 * (double)(max - window));
        this.setMaximum(max);
        this.setWindowMaximum(window);
        this.setMainProtectedMaximum(mainProtected);
        this.setHitsInSample(0);
        this.setMissesInSample(0);
        this.setStepSize(-0.0625 * (double)max);
        if (this.frequencySketch() != null && !this.isWeighted() && this.weightedSize() >= max >>> 1) {
            this.frequencySketch().ensureCapacity(max);
        }
    }

    @GuardedBy(value="evictionLock")
    void evictEntries() {
        if (!this.evicts()) {
            return;
        }
        int candidates = this.evictFromWindow();
        this.evictFromMain(candidates);
    }

    @GuardedBy(value="evictionLock")
    int evictFromWindow() {
        int candidates = 0;
        AccessOrderDeque.AccessOrder node = (Node)this.accessOrderWindowDeque().peek();
        while (this.windowWeightedSize() > this.windowMaximum() && node != null) {
            AccessOrderDeque.AccessOrder next = ((Node)node).getNextInAccessOrder();
            if (((Node)node).getPolicyWeight() != 0) {
                ((Node)node).makeMainProbation();
                this.accessOrderWindowDeque().remove((Node<K, V>)node);
                this.accessOrderProbationDeque().add((Node<K, V>)node);
                ++candidates;
                this.setWindowWeightedSize(this.windowWeightedSize() - (long)((Node)node).getPolicyWeight());
            }
            node = next;
        }
        return candidates;
    }

    @GuardedBy(value="evictionLock")
    void evictFromMain(int candidates) {
        int victimQueue = 1;
        AccessOrderDeque.AccessOrder victim = (Node)this.accessOrderProbationDeque().peekFirst();
        AccessOrderDeque.AccessOrder candidate = (Node)this.accessOrderProbationDeque().peekLast();
        while (this.weightedSize() > this.maximum()) {
            AccessOrderDeque.AccessOrder evict;
            if (candidates == 0) {
                candidate = (Node)this.accessOrderWindowDeque().peekLast();
            }
            if (candidate == null && victim == null) {
                if (victimQueue == 1) {
                    victim = (Node)this.accessOrderProtectedDeque().peekFirst();
                    victimQueue = 2;
                    continue;
                }
                if (victimQueue != 2) break;
                victim = (Node)this.accessOrderWindowDeque().peekFirst();
                victimQueue = 0;
                continue;
            }
            if (victim != null && ((Node)victim).getPolicyWeight() == 0) {
                victim = ((Node)victim).getNextInAccessOrder();
                continue;
            }
            if (candidate != null && ((Node)candidate).getPolicyWeight() == 0) {
                candidate = candidates > 0 ? ((Node)candidate).getPreviousInAccessOrder() : ((Node)candidate).getNextInAccessOrder();
                --candidates;
                continue;
            }
            if (victim == null) {
                AccessOrderDeque.AccessOrder previous = ((Node)candidate).getPreviousInAccessOrder();
                Node evict2 = candidate;
                candidate = previous;
                --candidates;
                this.evictEntry(evict2, RemovalCause.SIZE, 0L);
                continue;
            }
            if (candidate == null) {
                AccessOrderDeque.AccessOrder evict3 = victim;
                victim = ((Node)victim).getNextInAccessOrder();
                this.evictEntry((Node<K, V>)evict3, RemovalCause.SIZE, 0L);
                continue;
            }
            Object victimKey = ((Node)victim).getKey();
            Object candidateKey = ((Node)candidate).getKey();
            if (victimKey == null) {
                evict = victim;
                victim = ((Node)victim).getNextInAccessOrder();
                this.evictEntry((Node<K, V>)evict, RemovalCause.COLLECTED, 0L);
                continue;
            }
            if (candidateKey == null) {
                evict = candidate;
                candidate = candidates > 0 ? ((Node)candidate).getPreviousInAccessOrder() : ((Node)candidate).getNextInAccessOrder();
                --candidates;
                this.evictEntry((Node<K, V>)evict, RemovalCause.COLLECTED, 0L);
                continue;
            }
            if ((long)((Node)candidate).getPolicyWeight() > this.maximum()) {
                evict = candidate;
                candidate = candidates > 0 ? ((Node)candidate).getPreviousInAccessOrder() : ((Node)candidate).getNextInAccessOrder();
                --candidates;
                this.evictEntry((Node<K, V>)evict, RemovalCause.SIZE, 0L);
                continue;
            }
            --candidates;
            if (this.admit(candidateKey, victimKey)) {
                evict = victim;
                victim = ((Node)victim).getNextInAccessOrder();
                this.evictEntry((Node<K, V>)evict, RemovalCause.SIZE, 0L);
                candidate = ((Node)candidate).getPreviousInAccessOrder();
                continue;
            }
            evict = candidate;
            candidate = candidates > 0 ? ((Node)candidate).getPreviousInAccessOrder() : ((Node)candidate).getNextInAccessOrder();
            this.evictEntry((Node<K, V>)evict, RemovalCause.SIZE, 0L);
        }
    }

    @GuardedBy(value="evictionLock")
    boolean admit(K candidateKey, K victimKey) {
        int victimFreq = this.frequencySketch().frequency(victimKey);
        int candidateFreq = this.frequencySketch().frequency(candidateKey);
        if (candidateFreq > victimFreq) {
            return true;
        }
        if (candidateFreq <= 5) {
            return false;
        }
        int random = ThreadLocalRandom.current().nextInt();
        return (random & 0x7F) == 0;
    }

    @GuardedBy(value="evictionLock")
    void expireEntries() {
        long now = this.expirationTicker().read();
        this.expireAfterAccessEntries(now);
        this.expireAfterWriteEntries(now);
        this.expireVariableEntries(now);
        Pacer pacer = this.pacer();
        if (pacer != null) {
            long delay = this.getExpirationDelay(now);
            if (delay == Long.MAX_VALUE) {
                pacer.cancel();
            } else {
                pacer.schedule(this.executor, this.drainBuffersTask, now, delay);
            }
        }
    }

    @GuardedBy(value="evictionLock")
    void expireAfterAccessEntries(long now) {
        if (!this.expiresAfterAccess()) {
            return;
        }
        this.expireAfterAccessEntries(this.accessOrderWindowDeque(), now);
        if (this.evicts()) {
            this.expireAfterAccessEntries(this.accessOrderProbationDeque(), now);
            this.expireAfterAccessEntries(this.accessOrderProtectedDeque(), now);
        }
    }

    @GuardedBy(value="evictionLock")
    void expireAfterAccessEntries(AccessOrderDeque<Node<K, V>> accessOrderDeque, long now) {
        Node node;
        long duration = this.expiresAfterAccessNanos();
        while ((node = (Node)accessOrderDeque.peekFirst()) != null && now - node.getAccessTime() >= duration && this.evictEntry(node, RemovalCause.EXPIRED, now)) {
        }
    }

    @GuardedBy(value="evictionLock")
    void expireAfterWriteEntries(long now) {
        Node node;
        if (!this.expiresAfterWrite()) {
            return;
        }
        long duration = this.expiresAfterWriteNanos();
        while ((node = (Node)this.writeOrderDeque().peekFirst()) != null && now - node.getWriteTime() >= duration && this.evictEntry(node, RemovalCause.EXPIRED, now)) {
        }
    }

    @GuardedBy(value="evictionLock")
    void expireVariableEntries(long now) {
        if (this.expiresVariable()) {
            this.timerWheel().advance(now);
        }
    }

    @GuardedBy(value="evictionLock")
    long getExpirationDelay(long now) {
        Node node;
        long delay = Long.MAX_VALUE;
        if (this.expiresAfterAccess()) {
            node = (Node)this.accessOrderWindowDeque().peekFirst();
            if (node != null) {
                delay = Math.min(delay, this.expiresAfterAccessNanos() - (now - node.getAccessTime()));
            }
            if (this.evicts()) {
                node = (Node)this.accessOrderProbationDeque().peekFirst();
                if (node != null) {
                    delay = Math.min(delay, this.expiresAfterAccessNanos() - (now - node.getAccessTime()));
                }
                if ((node = (Node)this.accessOrderProtectedDeque().peekFirst()) != null) {
                    delay = Math.min(delay, this.expiresAfterAccessNanos() - (now - node.getAccessTime()));
                }
            }
        }
        if (this.expiresAfterWrite() && (node = (Node)this.writeOrderDeque().peekFirst()) != null) {
            delay = Math.min(delay, this.expiresAfterWriteNanos() - (now - node.getWriteTime()));
        }
        if (this.expiresVariable()) {
            delay = Math.min(delay, this.timerWheel().getExpirationDelay());
        }
        return delay;
    }

    boolean hasExpired(Node<K, V> node, long now) {
        if (this.isComputingAsync(node)) {
            return false;
        }
        return (this.expiresAfterAccess() && now - node.getAccessTime() >= this.expiresAfterAccessNanos()) | (this.expiresAfterWrite() && now - node.getWriteTime() >= this.expiresAfterWriteNanos()) | (this.expiresVariable() && now - node.getVariableTime() >= 0L);
    }

    @GuardedBy(value="evictionLock")
    boolean evictEntry(Node<K, V> node, RemovalCause cause, long now) {
        K key = node.getKey();
        Object[] value = new Object[1];
        boolean[] removed = new boolean[1];
        boolean[] resurrect = new boolean[1];
        Object keyReference = node.getKeyReference();
        RemovalCause[] actualCause = new RemovalCause[1];
        this.data.computeIfPresent(keyReference, (k, n) -> {
            if (n != node) {
                return n;
            }
            Node node2 = n;
            synchronized (node2) {
                int weight;
                value[0] = n.getValue();
                if (key == null || value[0] == null) {
                    actualCause[0] = RemovalCause.COLLECTED;
                } else {
                    if (cause == RemovalCause.COLLECTED) {
                        resurrect[0] = true;
                        return n;
                    }
                    actualCause[0] = cause;
                }
                if (actualCause[0] == RemovalCause.EXPIRED) {
                    boolean expired = false;
                    if (this.expiresAfterAccess()) {
                        expired |= now - n.getAccessTime() >= this.expiresAfterAccessNanos();
                    }
                    if (this.expiresAfterWrite()) {
                        expired |= now - n.getWriteTime() >= this.expiresAfterWriteNanos();
                    }
                    if (this.expiresVariable()) {
                        expired |= n.getVariableTime() <= now;
                    }
                    if (!expired) {
                        resurrect[0] = true;
                        return n;
                    }
                } else if (actualCause[0] == RemovalCause.SIZE && (weight = node.getWeight()) == 0) {
                    resurrect[0] = true;
                    return n;
                }
                this.notifyEviction(key, value[0], actualCause[0]);
                this.makeDead((Node<K, V>)n);
            }
            this.discardRefresh(keyReference);
            removed[0] = true;
            return null;
        });
        if (resurrect[0]) {
            return false;
        }
        if (node.inWindow() && (this.evicts() || this.expiresAfterAccess())) {
            this.accessOrderWindowDeque().remove(node);
        } else if (this.evicts()) {
            if (node.inMainProbation()) {
                this.accessOrderProbationDeque().remove(node);
            } else {
                this.accessOrderProtectedDeque().remove(node);
            }
        }
        if (this.expiresAfterWrite()) {
            this.writeOrderDeque().remove(node);
        } else if (this.expiresVariable()) {
            this.timerWheel().deschedule(node);
        }
        if (removed[0]) {
            this.statsCounter().recordEviction(node.getWeight(), actualCause[0]);
            this.notifyRemoval(key, value[0], actualCause[0]);
        } else {
            this.makeDead(node);
        }
        return true;
    }

    @GuardedBy(value="evictionLock")
    void climb() {
        if (!this.evicts()) {
            return;
        }
        this.determineAdjustment();
        this.demoteFromMainProtected();
        long amount = this.adjustment();
        if (amount == 0L) {
            return;
        }
        if (amount > 0L) {
            this.increaseWindow();
        } else {
            this.decreaseWindow();
        }
    }

    @GuardedBy(value="evictionLock")
    void determineAdjustment() {
        double amount;
        if (this.frequencySketch().isNotInitialized()) {
            this.setPreviousSampleHitRate(0.0);
            this.setMissesInSample(0);
            this.setHitsInSample(0);
            return;
        }
        int requestCount = this.hitsInSample() + this.missesInSample();
        if (requestCount < this.frequencySketch().sampleSize) {
            return;
        }
        double hitRate = (double)this.hitsInSample() / (double)requestCount;
        double hitRateChange = hitRate - this.previousSampleHitRate();
        double d = amount = hitRateChange >= 0.0 ? this.stepSize() : -this.stepSize();
        double nextStepSize = Math.abs(hitRateChange) >= 0.05 ? 0.0625 * (double)this.maximum() * (double)(amount >= 0.0 ? 1 : -1) : 0.98 * amount;
        this.setPreviousSampleHitRate(hitRate);
        this.setAdjustment((long)amount);
        this.setStepSize(nextStepSize);
        this.setMissesInSample(0);
        this.setHitsInSample(0);
    }

    @GuardedBy(value="evictionLock")
    void increaseWindow() {
        if (this.mainProtectedMaximum() == 0L) {
            return;
        }
        long quota = Math.min(this.adjustment(), this.mainProtectedMaximum());
        this.setMainProtectedMaximum(this.mainProtectedMaximum() - quota);
        this.setWindowMaximum(this.windowMaximum() + quota);
        this.demoteFromMainProtected();
        for (int i = 0; i < 1000; ++i) {
            int weight;
            Node candidate = (Node)this.accessOrderProbationDeque().peek();
            boolean probation = true;
            if (candidate == null || quota < (long)candidate.getPolicyWeight()) {
                candidate = (Node)this.accessOrderProtectedDeque().peek();
                probation = false;
            }
            if (candidate == null || quota < (long)(weight = candidate.getPolicyWeight())) break;
            quota -= (long)weight;
            if (probation) {
                this.accessOrderProbationDeque().remove(candidate);
            } else {
                this.setMainProtectedWeightedSize(this.mainProtectedWeightedSize() - (long)weight);
                this.accessOrderProtectedDeque().remove(candidate);
            }
            this.setWindowWeightedSize(this.windowWeightedSize() + (long)weight);
            this.accessOrderWindowDeque().add(candidate);
            candidate.makeWindow();
        }
        this.setMainProtectedMaximum(this.mainProtectedMaximum() + quota);
        this.setWindowMaximum(this.windowMaximum() - quota);
        this.setAdjustment(quota);
    }

    @GuardedBy(value="evictionLock")
    void decreaseWindow() {
        Node candidate;
        int weight;
        if (this.windowMaximum() <= 1L) {
            return;
        }
        long quota = Math.min(-this.adjustment(), Math.max(0L, this.windowMaximum() - 1L));
        this.setMainProtectedMaximum(this.mainProtectedMaximum() + quota);
        this.setWindowMaximum(this.windowMaximum() - quota);
        for (int i = 0; i < 1000 && (candidate = (Node)this.accessOrderWindowDeque().peek()) != null && quota >= (long)(weight = candidate.getPolicyWeight()); quota -= (long)weight, ++i) {
            this.setWindowWeightedSize(this.windowWeightedSize() - (long)weight);
            this.accessOrderWindowDeque().remove(candidate);
            this.accessOrderProbationDeque().add(candidate);
            candidate.makeMainProbation();
        }
        this.setMainProtectedMaximum(this.mainProtectedMaximum() - quota);
        this.setWindowMaximum(this.windowMaximum() + quota);
        this.setAdjustment(-quota);
    }

    @GuardedBy(value="evictionLock")
    void demoteFromMainProtected() {
        Node demoted;
        long mainProtectedMaximum = this.mainProtectedMaximum();
        long mainProtectedWeightedSize = this.mainProtectedWeightedSize();
        if (mainProtectedWeightedSize <= mainProtectedMaximum) {
            return;
        }
        for (int i = 0; i < 1000 && mainProtectedWeightedSize > mainProtectedMaximum && (demoted = (Node)this.accessOrderProtectedDeque().poll()) != null; mainProtectedWeightedSize -= (long)demoted.getPolicyWeight(), ++i) {
            demoted.makeMainProbation();
            this.accessOrderProbationDeque().add(demoted);
        }
        this.setMainProtectedWeightedSize(mainProtectedWeightedSize);
    }

    @Nullable V afterRead(Node<K, V> node, long now, boolean recordHit) {
        boolean delayable;
        if (recordHit) {
            this.statsCounter().recordHits(1);
        }
        boolean bl = delayable = this.skipReadBuffer() || this.readBuffer.offer(node) != 1;
        if (this.shouldDrainBuffers(delayable)) {
            this.scheduleDrainBuffers();
        }
        return this.refreshIfNeeded(node, now);
    }

    boolean skipReadBuffer() {
        return this.fastpath() && this.frequencySketch().isNotInitialized();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Nullable V refreshIfNeeded(Node<K, V> node, long now) {
        ConcurrentMap<Object, CompletableFuture<?>> refreshes;
        V oldValue;
        K key;
        if (!this.refreshAfterWrite()) {
            return null;
        }
        long writeTime = node.getWriteTime();
        long refreshWriteTime = writeTime | 1L;
        Object keyReference = node.getKeyReference();
        if (now - writeTime > this.refreshAfterWriteNanos() && keyReference != null && (key = node.getKey()) != null && (oldValue = node.getValue()) != null && (writeTime & 1L) == 0L && !(refreshes = this.refreshes()).containsKey(keyReference) && node.isAlive() && node.casWriteTime(writeTime, refreshWriteTime)) {
            long[] startTime = new long[1];
            CompletableFuture[] refreshFuture = new CompletableFuture[1];
            try {
                refreshes.computeIfAbsent(keyReference, k -> {
                    try {
                        startTime[0] = this.statsTicker().read();
                        if (this.isAsync) {
                            CompletableFuture<V> refresh;
                            CompletableFuture future = (CompletableFuture)oldValue;
                            if (!Async.isReady(future)) return future;
                            refreshFuture[0] = refresh = this.cacheLoader.asyncReload(key, future.join(), this.executor);
                            return refreshFuture[0];
                        } else {
                            CompletableFuture<Object> refresh;
                            refreshFuture[0] = refresh = this.cacheLoader.asyncReload(key, oldValue, this.executor);
                        }
                        return refreshFuture[0];
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        logger.log(System.Logger.Level.WARNING, "Exception thrown when submitting refresh task", (Throwable)e);
                        return null;
                    }
                    catch (Throwable e) {
                        logger.log(System.Logger.Level.WARNING, "Exception thrown when submitting refresh task", e);
                        return null;
                    }
                });
            }
            finally {
                node.casWriteTime(refreshWriteTime, writeTime);
            }
            if (refreshFuture[0] == null) {
                return null;
            }
            CompletionStage refreshed = refreshFuture[0].handle((newValue, error) -> {
                long loadTime = this.statsTicker().read() - startTime[0];
                if (error != null) {
                    if (!(error instanceof CancellationException) && !(error instanceof TimeoutException)) {
                        logger.log(System.Logger.Level.WARNING, "Exception thrown during refresh", (Throwable)error);
                    }
                    refreshes.remove(keyReference, refreshFuture[0]);
                    this.statsCounter().recordLoadFailure(loadTime);
                    return null;
                }
                Object value = this.isAsync && newValue != null ? refreshFuture[0] : newValue;
                boolean[] discard = new boolean[1];
                Object result = this.compute(key, (k, currentValue) -> {
                    if (currentValue == null) {
                        discard[0] = value != null;
                        return null;
                    }
                    if (currentValue == value) {
                        return currentValue;
                    }
                    if (this.isAsync && newValue == Async.getIfReady((CompletableFuture)currentValue)) {
                        return currentValue;
                    }
                    if (currentValue == oldValue && node.getWriteTime() == writeTime) {
                        return value;
                    }
                    discard[0] = true;
                    return currentValue;
                }, this.expiry(), false, true);
                if (discard[0]) {
                    this.notifyRemoval(key, value, RemovalCause.REPLACED);
                }
                if (newValue == null) {
                    this.statsCounter().recordLoadFailure(loadTime);
                } else {
                    this.statsCounter().recordLoadSuccess(loadTime);
                }
                refreshes.remove(keyReference, refreshFuture[0]);
                return result;
            });
            return Async.getIfReady(refreshed);
        }
        return null;
    }

    long expireAfterCreate(@Nullable K key, @Nullable V value, Expiry<? super K, ? super V> expiry, long now) {
        if (this.expiresVariable() && key != null && value != null) {
            long duration = expiry.expireAfterCreate(key, value, now);
            return this.isAsync ? now + duration : now + Math.min(duration, 0x3FFFFFFFFFFFFFFFL);
        }
        return 0L;
    }

    long expireAfterUpdate(Node<K, V> node, @Nullable K key, @Nullable V value, Expiry<? super K, ? super V> expiry, long now) {
        if (this.expiresVariable() && key != null && value != null) {
            long currentDuration = Math.max(1L, node.getVariableTime() - now);
            long duration = expiry.expireAfterUpdate(key, value, now, currentDuration);
            return this.isAsync ? now + duration : now + Math.min(duration, 0x3FFFFFFFFFFFFFFFL);
        }
        return 0L;
    }

    long expireAfterRead(Node<K, V> node, @Nullable K key, @Nullable V value, Expiry<K, V> expiry, long now) {
        if (this.expiresVariable() && key != null && value != null) {
            long currentDuration = Math.max(1L, node.getVariableTime() - now);
            long duration = expiry.expireAfterRead(key, value, now, currentDuration);
            return this.isAsync ? now + duration : now + Math.min(duration, 0x3FFFFFFFFFFFFFFFL);
        }
        return 0L;
    }

    void tryExpireAfterRead(Node<K, V> node, @Nullable K key, @Nullable V value, Expiry<K, V> expiry, long now) {
        if (!this.expiresVariable() || key == null || value == null) {
            return;
        }
        long variableTime = node.getVariableTime();
        long currentDuration = Math.max(1L, variableTime - now);
        if (this.isAsync && currentDuration > 0x3FFFFFFFFFFFFFFFL) {
            return;
        }
        long duration = expiry.expireAfterRead(key, value, now, currentDuration);
        if (duration != currentDuration) {
            long expirationTime = this.isAsync ? now + duration : now + Math.min(duration, 0x3FFFFFFFFFFFFFFFL);
            node.casVariableTime(variableTime, expirationTime);
        }
    }

    void setVariableTime(Node<K, V> node, long expirationTime) {
        if (this.expiresVariable()) {
            node.setVariableTime(expirationTime);
        }
    }

    void setWriteTime(Node<K, V> node, long now) {
        if (this.expiresAfterWrite() || this.refreshAfterWrite()) {
            node.setWriteTime(now & 0xFFFFFFFFFFFFFFFEL);
        }
    }

    void setAccessTime(Node<K, V> node, long now) {
        if (this.expiresAfterAccess()) {
            node.setAccessTime(now);
        }
    }

    void afterWrite(Runnable task) {
        for (int i = 0; i < 100; ++i) {
            if (this.writeBuffer.offer(task)) {
                this.scheduleAfterWrite();
                return;
            }
            this.scheduleDrainBuffers();
        }
        this.lock();
        try {
            this.maintenance(task);
        }
        catch (RuntimeException e) {
            logger.log(System.Logger.Level.ERROR, "Exception thrown when performing the maintenance task", (Throwable)e);
        }
        finally {
            this.evictionLock.unlock();
        }
    }

    void lock() {
        long remainingNanos = WARN_AFTER_LOCK_WAIT_NANOS;
        long end = System.nanoTime() + remainingNanos;
        boolean interrupted = false;
        while (true) {
            try {
                if (this.evictionLock.tryLock(remainingNanos, TimeUnit.NANOSECONDS)) {
                    return;
                }
                logger.log(System.Logger.Level.WARNING, "The cache is experiencing excessive wait times for acquiring the eviction lock. This may indicate that a long-running computation has halted eviction when trying to remove the victim entry. Consider using AsyncCache to decouple the computation from the map operation.", (Throwable)new TimeoutException());
                this.evictionLock.lock();
                return;
            }
            catch (InterruptedException e) {
                remainingNanos = end - System.nanoTime();
                interrupted = true;
                continue;
            }
            break;
        }
        finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void scheduleAfterWrite() {
        block6: while (true) {
            switch (this.drainStatus()) {
                case 0: {
                    this.casDrainStatus(0, 1);
                    this.scheduleDrainBuffers();
                    return;
                }
                case 1: {
                    this.scheduleDrainBuffers();
                    return;
                }
                case 2: {
                    if (!this.casDrainStatus(2, 3)) continue block6;
                    return;
                }
                case 3: {
                    return;
                }
            }
            break;
        }
        throw new IllegalStateException();
    }

    void scheduleDrainBuffers() {
        if (this.drainStatus() >= 2) {
            return;
        }
        if (this.evictionLock.tryLock()) {
            try {
                int drainStatus = this.drainStatus();
                if (drainStatus >= 2) {
                    return;
                }
                this.setDrainStatusRelease(2);
                this.executor.execute(this.drainBuffersTask);
            }
            catch (Throwable t) {
                logger.log(System.Logger.Level.WARNING, "Exception thrown when submitting maintenance task", t);
                this.maintenance(null);
            }
            finally {
                this.evictionLock.unlock();
            }
        }
    }

    @Override
    public void cleanUp() {
        try {
            this.performCleanUp(null);
        }
        catch (RuntimeException e) {
            logger.log(System.Logger.Level.ERROR, "Exception thrown when performing the maintenance task", (Throwable)e);
        }
    }

    void performCleanUp(@Nullable Runnable task) {
        this.evictionLock.lock();
        try {
            this.maintenance(task);
        }
        finally {
            this.evictionLock.unlock();
        }
        if (this.drainStatus() == 1 && this.executor == ForkJoinPool.commonPool()) {
            this.scheduleDrainBuffers();
        }
    }

    @GuardedBy(value="evictionLock")
    void maintenance(@Nullable Runnable task) {
        this.setDrainStatusRelease(2);
        try {
            this.drainReadBuffer();
            this.drainWriteBuffer();
            if (task != null) {
                task.run();
            }
            this.drainKeyReferences();
            this.drainValueReferences();
            this.expireEntries();
            this.evictEntries();
            this.climb();
        }
        finally {
            if (this.drainStatus() != 2 || !this.casDrainStatus(2, 0)) {
                this.setDrainStatusOpaque(1);
            }
        }
    }

    @GuardedBy(value="evictionLock")
    void drainKeyReferences() {
        Reference<K> keyRef;
        if (!this.collectKeys()) {
            return;
        }
        while ((keyRef = this.keyReferenceQueue().poll()) != null) {
            Node<K, V> node = this.data.get(keyRef);
            if (node == null) continue;
            this.evictEntry(node, RemovalCause.COLLECTED, 0L);
        }
    }

    @GuardedBy(value="evictionLock")
    void drainValueReferences() {
        Reference<V> valueRef;
        if (!this.collectValues()) {
            return;
        }
        while ((valueRef = this.valueReferenceQueue().poll()) != null) {
            References.InternalReference ref = (References.InternalReference)((Object)valueRef);
            Node<K, V> node = this.data.get(ref.getKeyReference());
            if (node == null || valueRef != node.getValueReference()) continue;
            this.evictEntry(node, RemovalCause.COLLECTED, 0L);
        }
    }

    @GuardedBy(value="evictionLock")
    void drainReadBuffer() {
        if (!this.skipReadBuffer()) {
            this.readBuffer.drainTo(this.accessPolicy);
        }
    }

    @GuardedBy(value="evictionLock")
    void onAccess(Node<K, V> node) {
        if (this.evicts()) {
            K key = node.getKey();
            if (key == null) {
                return;
            }
            this.frequencySketch().increment(key);
            if (node.inWindow()) {
                BoundedLocalCache.reorder(this.accessOrderWindowDeque(), node);
            } else if (node.inMainProbation()) {
                this.reorderProbation(node);
            } else {
                BoundedLocalCache.reorder(this.accessOrderProtectedDeque(), node);
            }
            this.setHitsInSample(this.hitsInSample() + 1);
        } else if (this.expiresAfterAccess()) {
            BoundedLocalCache.reorder(this.accessOrderWindowDeque(), node);
        }
        if (this.expiresVariable()) {
            this.timerWheel().reschedule(node);
        }
    }

    @GuardedBy(value="evictionLock")
    void reorderProbation(Node<K, V> node) {
        if (!this.accessOrderProbationDeque().contains(node)) {
            return;
        }
        if ((long)node.getPolicyWeight() > this.mainProtectedMaximum()) {
            BoundedLocalCache.reorder(this.accessOrderProbationDeque(), node);
            return;
        }
        this.setMainProtectedWeightedSize(this.mainProtectedWeightedSize() + (long)node.getPolicyWeight());
        this.accessOrderProbationDeque().remove(node);
        this.accessOrderProtectedDeque().add(node);
        node.makeMainProtected();
    }

    static <K, V> void reorder(LinkedDeque<Node<K, V>> deque, Node<K, V> node) {
        if (deque.contains(node)) {
            deque.moveToBack(node);
        }
    }

    @GuardedBy(value="evictionLock")
    void drainWriteBuffer() {
        for (int i = 0; i <= WRITE_BUFFER_MAX; ++i) {
            Runnable task = (Runnable)this.writeBuffer.poll();
            if (task == null) {
                return;
            }
            task.run();
        }
        this.setDrainStatusOpaque(3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @GuardedBy(value="evictionLock")
    void makeDead(Node<K, V> node) {
        Node<K, V> node2 = node;
        synchronized (node2) {
            if (node.isDead()) {
                return;
            }
            if (this.evicts()) {
                if (node.inWindow()) {
                    this.setWindowWeightedSize(this.windowWeightedSize() - (long)node.getWeight());
                } else if (node.inMainProtected()) {
                    this.setMainProtectedWeightedSize(this.mainProtectedWeightedSize() - (long)node.getWeight());
                }
                this.setWeightedSize(this.weightedSize() - (long)node.getWeight());
            }
            node.die();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public long estimatedSize() {
        return this.data.mappingCount();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        this.evictionLock.lock();
        try {
            Runnable task;
            long now = this.expirationTicker().read();
            while ((task = (Runnable)this.writeBuffer.poll()) != null) {
                task.run();
            }
            for (Map.Entry<Object, Node<K, V>> entry : this.data.entrySet()) {
                this.removeNode(entry.getValue(), now);
            }
            Pacer pacer = this.pacer();
            if (pacer != null) {
                pacer.cancel();
            }
            this.readBuffer.drainTo(e -> {});
        }
        finally {
            this.evictionLock.unlock();
        }
    }

    @GuardedBy(value="evictionLock")
    void removeNode(Node<K, V> node, long now) {
        K key = node.getKey();
        Object[] value = new Object[1];
        RemovalCause[] cause = new RemovalCause[1];
        this.data.computeIfPresent(node.getKeyReference(), (k, n) -> {
            if (n != node) {
                return n;
            }
            Node node2 = n;
            synchronized (node2) {
                value[0] = n.getValue();
                cause[0] = key == null || value[0] == null ? RemovalCause.COLLECTED : (this.hasExpired((Node<K, V>)n, now) ? RemovalCause.EXPIRED : RemovalCause.EXPLICIT);
                if (cause[0].wasEvicted()) {
                    this.notifyEviction(key, value[0], cause[0]);
                }
                this.discardRefresh(node.getKeyReference());
                this.makeDead((Node<K, V>)n);
                return null;
            }
        });
        if (node.inWindow() && (this.evicts() || this.expiresAfterAccess())) {
            this.accessOrderWindowDeque().remove(node);
        } else if (this.evicts()) {
            if (node.inMainProbation()) {
                this.accessOrderProbationDeque().remove(node);
            } else {
                this.accessOrderProtectedDeque().remove(node);
            }
        }
        if (this.expiresAfterWrite()) {
            this.writeOrderDeque().remove(node);
        } else if (this.expiresVariable()) {
            this.timerWheel().deschedule(node);
        }
        if (cause[0] != null) {
            this.notifyRemoval(key, value[0], cause[0]);
        }
    }

    @Override
    public boolean containsKey(Object key) {
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        return node != null && node.getValue() != null && !this.hasExpired(node, this.expirationTicker().read());
    }

    @Override
    public boolean containsValue(Object value) {
        Objects.requireNonNull(value);
        long now = this.expirationTicker().read();
        for (Node<K, V> node : this.data.values()) {
            if (!node.containsValue(value) || this.hasExpired(node, now) || node.getKey() == null) continue;
            return true;
        }
        return false;
    }

    @Override
    public @Nullable V get(Object key) {
        return this.getIfPresent(key, false);
    }

    @Override
    public @Nullable V getIfPresent(Object key, boolean recordStats) {
        V refreshed;
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        if (node == null) {
            if (recordStats) {
                this.statsCounter().recordMisses(1);
            }
            if (this.drainStatus() == 1) {
                this.scheduleDrainBuffers();
            }
            return null;
        }
        V value = node.getValue();
        long now = this.expirationTicker().read();
        if (this.hasExpired(node, now) || this.collectValues() && value == null) {
            if (recordStats) {
                this.statsCounter().recordMisses(1);
            }
            this.scheduleDrainBuffers();
            return null;
        }
        if (!this.isComputingAsync(node)) {
            Object castedKey = key;
            this.setAccessTime(node, now);
            this.tryExpireAfterRead(node, castedKey, value, this.expiry(), now);
        }
        return (refreshed = this.afterRead(node, now, recordStats)) == null ? value : refreshed;
    }

    @Override
    public @Nullable V getIfPresentQuietly(Object key) {
        V value;
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        if (node == null || (value = node.getValue()) == null || this.hasExpired(node, this.expirationTicker().read())) {
            return null;
        }
        return value;
    }

    @Override
    public @Nullable V getIfPresentQuietly(K key, long[] writeTime) {
        V value;
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        if (node == null || (value = node.getValue()) == null || this.hasExpired(node, this.expirationTicker().read())) {
            return null;
        }
        writeTime[0] = node.getWriteTime();
        return value;
    }

    public @Nullable K getKey(K key) {
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        if (node == null) {
            if (this.drainStatus() == 1) {
                this.scheduleDrainBuffers();
            }
            return null;
        }
        this.afterRead(node, 0L, false);
        return node.getKey();
    }

    @Override
    public Map<K, V> getAllPresent(Iterable<? extends K> keys) {
        LinkedHashMap result = new LinkedHashMap(Caffeine.calculateHashMapCapacity(keys));
        for (K key : keys) {
            result.put(key, null);
        }
        int uniqueKeys = result.size();
        long now = this.expirationTicker().read();
        Iterator iter = result.entrySet().iterator();
        while (iter.hasNext()) {
            V refreshed;
            V value;
            Map.Entry entry = iter.next();
            Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(entry.getKey()));
            if (node == null || (value = node.getValue()) == null || this.hasExpired(node, now)) {
                iter.remove();
                continue;
            }
            if (!this.isComputingAsync(node)) {
                Object castedKey = entry.getKey();
                this.tryExpireAfterRead(node, castedKey, value, this.expiry(), now);
                this.setAccessTime(node, now);
            }
            if ((refreshed = this.afterRead(node, now, false)) == null) {
                entry.setValue(value);
                continue;
            }
            entry.setValue(refreshed);
        }
        this.statsCounter().recordHits(result.size());
        this.statsCounter().recordMisses(uniqueKeys - result.size());
        LinkedHashMap castedResult = result;
        return Collections.unmodifiableMap(castedResult);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        map.forEach(this::put);
    }

    @Override
    public @Nullable V put(K key, V value) {
        return this.put(key, value, this.expiry(), false);
    }

    @Override
    public @Nullable V putIfAbsent(K key, V value) {
        return this.put(key, value, this.expiry(), true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Nullable V put(K key, V value, Expiry<K, V> expiry, boolean onlyIfAbsent) {
        V v;
        int weightedDifference;
        long varTime;
        boolean exceedsTolerance;
        boolean mayUpdate;
        boolean expired;
        Node prior;
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        Node<K, V> node = null;
        long now = this.expirationTicker().read();
        int newWeight = this.weigher.weigh(key, value);
        while (true) {
            V currentValue;
            if ((prior = this.data.get(this.nodeFactory.newLookupKey(key))) == null) {
                if (node == null) {
                    node = this.nodeFactory.newNode(key, this.keyReferenceQueue(), value, this.valueReferenceQueue(), newWeight, now);
                    this.setVariableTime(node, this.expireAfterCreate(key, value, expiry, now));
                }
                if ((prior = this.data.putIfAbsent(node.getKeyReference(), node)) == null) {
                    this.afterWrite(new AddTask(node, newWeight));
                    return null;
                }
                if (onlyIfAbsent && (currentValue = prior.getValue()) != null && !this.hasExpired(prior, now)) {
                    if (!this.isComputingAsync(prior)) {
                        this.tryExpireAfterRead(prior, key, currentValue, this.expiry(), now);
                        this.setAccessTime(prior, now);
                    }
                    this.afterRead(prior, now, false);
                    return currentValue;
                }
            } else if (onlyIfAbsent) {
                currentValue = prior.getValue();
                if (currentValue != null && !this.hasExpired(prior, now)) {
                    if (!this.isComputingAsync(prior)) {
                        this.tryExpireAfterRead(prior, key, currentValue, this.expiry(), now);
                        this.setAccessTime(prior, now);
                    }
                    this.afterRead(prior, now, false);
                    return currentValue;
                }
            } else {
                this.discardRefresh(prior.getKeyReference());
            }
            expired = false;
            mayUpdate = true;
            exceedsTolerance = false;
            Node node2 = prior;
            // MONITORENTER : node2
            if (prior.isAlive()) break;
            // MONITOREXIT : node2
        }
        Object oldValue = prior.getValue();
        int oldWeight = prior.getWeight();
        if (oldValue == null) {
            varTime = this.expireAfterCreate(key, value, expiry, now);
            this.notifyEviction(key, null, RemovalCause.COLLECTED);
        } else if (this.hasExpired(prior, now)) {
            expired = true;
            varTime = this.expireAfterCreate(key, value, expiry, now);
            this.notifyEviction(key, oldValue, RemovalCause.EXPIRED);
        } else if (onlyIfAbsent) {
            mayUpdate = false;
            varTime = this.expireAfterRead(prior, key, value, expiry, now);
        } else {
            varTime = this.expireAfterUpdate(prior, key, value, expiry, now);
        }
        if (mayUpdate) {
            exceedsTolerance = this.expiresAfterWrite() && now - prior.getWriteTime() > EXPIRE_WRITE_TOLERANCE || this.expiresVariable() && Math.abs(varTime - prior.getVariableTime()) > EXPIRE_WRITE_TOLERANCE;
            this.setWriteTime(prior, now);
            prior.setWeight(newWeight);
            prior.setValue(value, this.valueReferenceQueue());
        }
        this.setVariableTime(prior, varTime);
        this.setAccessTime(prior, now);
        // MONITOREXIT : node2
        if (expired) {
            this.notifyRemoval(key, oldValue, RemovalCause.EXPIRED);
        } else if (oldValue == null) {
            this.notifyRemoval(key, null, RemovalCause.COLLECTED);
        } else if (mayUpdate) {
            this.notifyOnReplace(key, oldValue, value);
        }
        int n = weightedDifference = mayUpdate ? newWeight - oldWeight : 0;
        if (oldValue == null || weightedDifference != 0 || expired) {
            this.afterWrite(new UpdateTask(prior, weightedDifference));
        } else if (!onlyIfAbsent && exceedsTolerance) {
            this.afterWrite(new UpdateTask(prior, weightedDifference));
        } else {
            if (mayUpdate) {
                this.setWriteTime(prior, now);
            }
            this.afterRead(prior, now, false);
        }
        if (expired) {
            v = null;
            return v;
        }
        v = oldValue;
        return v;
    }

    @Override
    public @Nullable V remove(Object key) {
        Object castKey = key;
        Node[] node = new Node[1];
        Object[] oldValue = new Object[1];
        RemovalCause[] cause = new RemovalCause[1];
        Object lookupKey = this.nodeFactory.newLookupKey(key);
        this.data.computeIfPresent(lookupKey, (k, n) -> {
            Node node2 = n;
            synchronized (node2) {
                oldValue[0] = n.getValue();
                cause[0] = oldValue[0] == null ? RemovalCause.COLLECTED : (this.hasExpired((Node<K, V>)n, this.expirationTicker().read()) ? RemovalCause.EXPIRED : RemovalCause.EXPLICIT);
                if (cause[0].wasEvicted()) {
                    this.notifyEviction(castKey, oldValue[0], cause[0]);
                }
                n.retire();
            }
            this.discardRefresh(lookupKey);
            node[0] = n;
            return null;
        });
        if (cause[0] != null) {
            this.afterWrite(new RemovalTask(node[0]));
            this.notifyRemoval(castKey, oldValue[0], cause[0]);
        }
        return (V)(cause[0] == RemovalCause.EXPLICIT ? oldValue[0] : null);
    }

    @Override
    public boolean remove(Object key, Object value) {
        Objects.requireNonNull(key);
        if (value == null) {
            return false;
        }
        Node[] removed = new Node[1];
        Object[] oldKey = new Object[1];
        Object[] oldValue = new Object[1];
        RemovalCause[] cause = new RemovalCause[1];
        Object lookupKey = this.nodeFactory.newLookupKey(key);
        this.data.computeIfPresent(lookupKey, (kR, node) -> {
            Node node2 = node;
            synchronized (node2) {
                oldKey[0] = node.getKey();
                oldValue[0] = node.getValue();
                if (oldKey[0] == null || oldValue[0] == null) {
                    cause[0] = RemovalCause.COLLECTED;
                } else if (this.hasExpired((Node<K, V>)node, this.expirationTicker().read())) {
                    cause[0] = RemovalCause.EXPIRED;
                } else if (node.containsValue(value)) {
                    cause[0] = RemovalCause.EXPLICIT;
                } else {
                    return node;
                }
                if (cause[0].wasEvicted()) {
                    this.notifyEviction(oldKey[0], oldValue[0], cause[0]);
                }
                this.discardRefresh(lookupKey);
                removed[0] = node;
                node.retire();
                return null;
            }
        });
        if (removed[0] == null) {
            return false;
        }
        this.afterWrite(new RemovalTask(removed[0]));
        this.notifyRemoval(oldKey[0], oldValue[0], cause[0]);
        return cause[0] == RemovalCause.EXPLICIT;
    }

    @Override
    public @Nullable V replace(K key, V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        int[] oldWeight = new int[1];
        Object[] nodeKey = new Object[1];
        Object[] oldValue = new Object[1];
        long[] now = new long[1];
        int weight = this.weigher.weigh(key, value);
        Node node = this.data.computeIfPresent(this.nodeFactory.newLookupKey(key), (k, n) -> {
            Node node = n;
            synchronized (node) {
                nodeKey[0] = n.getKey();
                oldValue[0] = n.getValue();
                oldWeight[0] = n.getWeight();
                if (nodeKey[0] == null || oldValue[0] == null || this.hasExpired((Node<K, V>)n, now[0] = this.expirationTicker().read())) {
                    oldValue[0] = null;
                    return n;
                }
                long varTime = this.expireAfterUpdate((Node<K, V>)n, key, value, (Expiry<? super K, ? super V>)this.expiry(), now[0]);
                n.setValue(value, this.valueReferenceQueue());
                n.setWeight(weight);
                this.setVariableTime((Node<K, V>)n, varTime);
                this.setAccessTime((Node<K, V>)n, now[0]);
                this.setWriteTime((Node<K, V>)n, now[0]);
                this.discardRefresh(k);
                return n;
            }
        });
        if (oldValue[0] == null) {
            return null;
        }
        int weightedDifference = weight - oldWeight[0];
        if (this.expiresAfterWrite() || weightedDifference != 0) {
            this.afterWrite(new UpdateTask(node, weightedDifference));
        } else {
            this.afterRead(node, now[0], false);
        }
        this.notifyOnReplace(nodeKey[0], oldValue[0], value);
        return (V)oldValue[0];
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(oldValue);
        Objects.requireNonNull(newValue);
        int weight = this.weigher.weigh(key, newValue);
        boolean[] replaced = new boolean[1];
        Object[] nodeKey = new Object[1];
        Object[] prevValue = new Object[1];
        int[] oldWeight = new int[1];
        long[] now = new long[1];
        Node node = this.data.computeIfPresent(this.nodeFactory.newLookupKey(key), (k, n) -> {
            Node node = n;
            synchronized (node) {
                nodeKey[0] = n.getKey();
                prevValue[0] = n.getValue();
                oldWeight[0] = n.getWeight();
                if (nodeKey[0] == null || prevValue[0] == null || !n.containsValue(oldValue) || this.hasExpired((Node<K, V>)n, now[0] = this.expirationTicker().read())) {
                    return n;
                }
                long varTime = this.expireAfterUpdate((Node<K, V>)n, key, newValue, (Expiry<? super K, ? super V>)this.expiry(), now[0]);
                n.setValue(newValue, this.valueReferenceQueue());
                n.setWeight(weight);
                this.setVariableTime((Node<K, V>)n, varTime);
                this.setAccessTime((Node<K, V>)n, now[0]);
                this.setWriteTime((Node<K, V>)n, now[0]);
                replaced[0] = true;
                this.discardRefresh(k);
            }
            return n;
        });
        if (!replaced[0]) {
            return false;
        }
        int weightedDifference = weight - oldWeight[0];
        if (this.expiresAfterWrite() || weightedDifference != 0) {
            this.afterWrite(new UpdateTask(node, weightedDifference));
        } else {
            this.afterRead(node, now[0], false);
        }
        this.notifyOnReplace(nodeKey[0], prevValue[0], newValue);
        return true;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        BiFunction<Object, Object, Object> remappingFunction = (key, oldValue) -> Objects.requireNonNull(function.apply((Object)key, (Object)oldValue));
        for (K key2 : this.keySet()) {
            long[] now = new long[]{this.expirationTicker().read()};
            Object lookupKey = this.nodeFactory.newLookupKey(key2);
            this.remap(key2, lookupKey, remappingFunction, this.expiry(), now, false);
        }
    }

    @Override
    public @Nullable V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction, boolean recordStats, boolean recordLoad) {
        V value;
        Objects.requireNonNull(key);
        Objects.requireNonNull(mappingFunction);
        long now = this.expirationTicker().read();
        Node<K, V> node = this.data.get(this.nodeFactory.newLookupKey(key));
        if (node != null && (value = node.getValue()) != null && !this.hasExpired(node, now)) {
            V refreshed;
            if (!this.isComputingAsync(node)) {
                this.tryExpireAfterRead(node, key, value, this.expiry(), now);
                this.setAccessTime(node, now);
            }
            return (refreshed = this.afterRead(node, now, recordStats)) == null ? value : refreshed;
        }
        if (recordStats) {
            mappingFunction = this.statsAware(mappingFunction, recordLoad);
        }
        Object keyRef = this.nodeFactory.newReferenceKey(key, this.keyReferenceQueue());
        return this.doComputeIfAbsent(key, keyRef, mappingFunction, new long[]{now}, recordStats);
    }

    @Nullable V doComputeIfAbsent(K key, Object keyRef, Function<? super K, ? extends V> mappingFunction, long[] now, boolean recordStats) {
        Object[] oldValue = new Object[1];
        Object[] newValue = new Object[1];
        Object[] nodeKey = new Object[1];
        Node[] removed = new Node[1];
        int[] weight = new int[2];
        RemovalCause[] cause = new RemovalCause[1];
        Node node = this.data.compute(keyRef, (k, n) -> {
            if (n == null) {
                newValue[0] = mappingFunction.apply((K)key);
                if (newValue[0] == null) {
                    return null;
                }
                now[0] = this.expirationTicker().read();
                weight[1] = this.weigher.weigh(key, newValue[0]);
                n = this.nodeFactory.newNode(key, this.keyReferenceQueue(), newValue[0], this.valueReferenceQueue(), weight[1], now[0]);
                this.setVariableTime((Node<K, V>)n, this.expireAfterCreate(key, newValue[0], this.expiry(), now[0]));
                return n;
            }
            Node<Object, Object> node = n;
            synchronized (node) {
                nodeKey[0] = n.getKey();
                weight[0] = n.getWeight();
                oldValue[0] = n.getValue();
                if (nodeKey[0] == null || oldValue[0] == null) {
                    cause[0] = RemovalCause.COLLECTED;
                } else if (this.hasExpired((Node<K, V>)n, now[0])) {
                    cause[0] = RemovalCause.EXPIRED;
                } else {
                    return n;
                }
                if (cause[0].wasEvicted()) {
                    this.notifyEviction(nodeKey[0], oldValue[0], cause[0]);
                }
                newValue[0] = mappingFunction.apply((K)key);
                if (newValue[0] == null) {
                    removed[0] = n;
                    n.retire();
                    return null;
                }
                weight[1] = this.weigher.weigh(key, newValue[0]);
                n.setValue(newValue[0], this.valueReferenceQueue());
                n.setWeight(weight[1]);
                now[0] = this.expirationTicker().read();
                this.setVariableTime((Node<K, V>)n, this.expireAfterCreate(key, newValue[0], this.expiry(), now[0]));
                this.setAccessTime((Node<K, V>)n, now[0]);
                this.setWriteTime((Node<K, V>)n, now[0]);
                this.discardRefresh(k);
                return n;
            }
        });
        if (cause[0] != null) {
            if (cause[0].wasEvicted()) {
                this.statsCounter().recordEviction(weight[0], cause[0]);
            }
            this.notifyRemoval(nodeKey[0], oldValue[0], cause[0]);
        }
        if (node == null) {
            if (removed[0] != null) {
                this.afterWrite(new RemovalTask(removed[0]));
            }
            return null;
        }
        if (newValue[0] == null) {
            if (!this.isComputingAsync(node)) {
                this.tryExpireAfterRead(node, key, oldValue[0], this.expiry(), now[0]);
                this.setAccessTime(node, now[0]);
            }
            this.afterRead(node, now[0], recordStats);
            return (V)oldValue[0];
        }
        if (oldValue[0] == null && cause[0] == null) {
            this.afterWrite(new AddTask(node, weight[1]));
        } else {
            int weightedDifference = weight[1] - weight[0];
            this.afterWrite(new UpdateTask(node, weightedDifference));
        }
        return (V)newValue[0];
    }

    @Override
    public @Nullable V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        long now;
        Objects.requireNonNull(key);
        Objects.requireNonNull(remappingFunction);
        Object lookupKey = this.nodeFactory.newLookupKey(key);
        @Nullable Node<K, V> node = this.data.get(lookupKey);
        if (node == null) {
            return null;
        }
        if (node.getValue() == null || this.hasExpired(node, now = this.expirationTicker().read())) {
            this.scheduleDrainBuffers();
            return null;
        }
        BiFunction<? super K, ? super V, ? extends V> statsAwareRemappingFunction = this.statsAware(remappingFunction, true, true);
        return this.remap(key, lookupKey, statsAwareRemappingFunction, this.expiry(), new long[]{now}, false);
    }

    @Override
    public @Nullable V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, @Nullable Expiry<? super K, ? super V> expiry, boolean recordLoad, boolean recordLoadFailure) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(remappingFunction);
        long[] now = new long[]{this.expirationTicker().read()};
        Object keyRef = this.nodeFactory.newReferenceKey(key, this.keyReferenceQueue());
        BiFunction<? super K, ? super V, ? extends V> statsAwareRemappingFunction = this.statsAware(remappingFunction, recordLoad, recordLoadFailure);
        return this.remap(key, keyRef, statsAwareRemappingFunction, expiry, now, true);
    }

    @Override
    public @Nullable V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        Objects.requireNonNull(remappingFunction);
        long[] now = new long[]{this.expirationTicker().read()};
        Object keyRef = this.nodeFactory.newReferenceKey(key, this.keyReferenceQueue());
        BiFunction<Object, Object, Object> mergeFunction = (k, oldValue) -> oldValue == null ? value : this.statsAware(remappingFunction).apply(oldValue, value);
        return (V)this.remap(key, keyRef, mergeFunction, this.expiry(), now, true);
    }

    @Nullable V remap(K key, Object keyRef, BiFunction<? super K, ? super V, ? extends V> remappingFunction, Expiry<? super K, ? super V> expiry, long[] now, boolean computeIfAbsent) {
        Object[] nodeKey = new Object[1];
        Object[] oldValue = new Object[1];
        Object[] newValue = new Object[1];
        Node[] removed = new Node[1];
        int[] weight = new int[2];
        RemovalCause[] cause = new RemovalCause[1];
        Node node = this.data.compute(keyRef, (kr, n) -> {
            if (n == null) {
                if (!computeIfAbsent) {
                    return null;
                }
                newValue[0] = remappingFunction.apply((K)key, (V)null);
                if (newValue[0] == null) {
                    return null;
                }
                now[0] = this.expirationTicker().read();
                weight[1] = this.weigher.weigh(key, newValue[0]);
                n = this.nodeFactory.newNode(keyRef, newValue[0], this.valueReferenceQueue(), weight[1], now[0]);
                this.setVariableTime((Node<K, V>)n, this.expireAfterCreate(key, newValue[0], expiry, now[0]));
                this.setAccessTime((Node<K, V>)n, now[0]);
                this.setWriteTime((Node<K, V>)n, now[0]);
                this.discardRefresh(key);
                return n;
            }
            Node<K, Object> node = n;
            synchronized (node) {
                nodeKey[0] = n.getKey();
                oldValue[0] = n.getValue();
                if (nodeKey[0] == null || oldValue[0] == null) {
                    cause[0] = RemovalCause.COLLECTED;
                } else if (this.hasExpired((Node<K, V>)n, this.expirationTicker().read())) {
                    cause[0] = RemovalCause.EXPIRED;
                }
                if (cause[0] != null) {
                    this.notifyEviction(nodeKey[0], oldValue[0], cause[0]);
                    if (!computeIfAbsent) {
                        removed[0] = n;
                        n.retire();
                        return null;
                    }
                }
                newValue[0] = remappingFunction.apply((K)nodeKey[0], (V)(cause[0] == null ? oldValue[0] : null));
                if (newValue[0] == null) {
                    if (cause[0] == null) {
                        cause[0] = RemovalCause.EXPLICIT;
                        this.discardRefresh(kr);
                    }
                    removed[0] = n;
                    n.retire();
                    return null;
                }
                weight[0] = n.getWeight();
                weight[1] = this.weigher.weigh(key, newValue[0]);
                now[0] = this.expirationTicker().read();
                if (cause[0] == null) {
                    if (newValue[0] != oldValue[0]) {
                        cause[0] = RemovalCause.REPLACED;
                    }
                    this.setVariableTime((Node<K, V>)n, this.expireAfterUpdate((Node<K, V>)n, key, (V)newValue[0], expiry, now[0]));
                } else {
                    this.setVariableTime((Node<K, V>)n, this.expireAfterCreate(key, newValue[0], expiry, now[0]));
                }
                n.setValue(newValue[0], this.valueReferenceQueue());
                n.setWeight(weight[1]);
                this.setAccessTime((Node<K, V>)n, now[0]);
                this.setWriteTime((Node<K, V>)n, now[0]);
                this.discardRefresh(kr);
                return n;
            }
        });
        if (cause[0] != null) {
            if (cause[0] == RemovalCause.REPLACED) {
                this.notifyOnReplace(key, oldValue[0], newValue[0]);
            } else {
                if (cause[0].wasEvicted()) {
                    this.statsCounter().recordEviction(weight[0], cause[0]);
                }
                this.notifyRemoval(nodeKey[0], oldValue[0], cause[0]);
            }
        }
        if (removed[0] != null) {
            this.afterWrite(new RemovalTask(removed[0]));
        } else if (node != null) {
            if (oldValue[0] == null && cause[0] == null) {
                this.afterWrite(new AddTask(node, weight[1]));
            } else {
                int weightedDifference = weight[1] - weight[0];
                if (this.expiresAfterWrite() || weightedDifference != 0) {
                    this.afterWrite(new UpdateTask(node, weightedDifference));
                } else {
                    this.afterRead(node, now[0], false);
                    if (cause[0] != null && cause[0].wasEvicted()) {
                        this.scheduleDrainBuffers();
                    }
                }
            }
        }
        return (V)newValue[0];
    }

    @Override
    public Set<K> keySet() {
        Set<K> ks = this.keySet;
        return ks == null ? (this.keySet = new KeySetView(this)) : ks;
    }

    @Override
    public Collection<V> values() {
        Collection<V> vs = this.values;
        return vs == null ? (this.values = new ValuesView(this)) : vs;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es = this.entrySet;
        return es == null ? (this.entrySet = new EntrySetView(this)) : es;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        Map map = (Map)o;
        if (this.size() != map.size()) {
            return false;
        }
        long now = this.expirationTicker().read();
        for (Node<K, V> node : this.data.values()) {
            K key = node.getKey();
            V value = node.getValue();
            if (key == null || value == null || !node.isAlive() || this.hasExpired(node, now)) {
                this.scheduleDrainBuffers();
                return false;
            }
            Object val = map.get(key);
            if (val != null && (val == value || val.equals(value))) continue;
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        long now = this.expirationTicker().read();
        for (Node<K, V> node : this.data.values()) {
            K key = node.getKey();
            V value = node.getValue();
            if (key == null || value == null || !node.isAlive() || this.hasExpired(node, now)) {
                this.scheduleDrainBuffers();
                continue;
            }
            hash += key.hashCode() ^ value.hashCode();
        }
        return hash;
    }

    public String toString() {
        StringBuilder result = new StringBuilder().append('{');
        long now = this.expirationTicker().read();
        for (Node<K, V> node : this.data.values()) {
            K key = node.getKey();
            V value = node.getValue();
            if (key == null || value == null || !node.isAlive() || this.hasExpired(node, now)) {
                this.scheduleDrainBuffers();
                continue;
            }
            if (result.length() != 1) {
                result.append(',').append(' ');
            }
            result.append((Object)(key == this ? "(this Map)" : key));
            result.append('=');
            result.append((Object)(value == this ? "(this Map)" : value));
        }
        return result.append('}').toString();
    }

    <T> T evictionOrder(boolean hottest, Function<V, V> transformer, Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
        Comparator<Node> comparator = Comparator.comparingInt(node -> {
            Object key = node.getKey();
            return key == null ? 0 : this.frequencySketch().frequency(key);
        });
        Iterable<Node<K, V>> iterable = hottest ? () -> {
            LinkedDeque.PeekingIterator secondary = LinkedDeque.PeekingIterator.comparing(this.accessOrderProbationDeque().descendingIterator(), this.accessOrderWindowDeque().descendingIterator(), comparator);
            return LinkedDeque.PeekingIterator.concat(this.accessOrderProtectedDeque().descendingIterator(), secondary);
        } : () -> {
            LinkedDeque.PeekingIterator primary = LinkedDeque.PeekingIterator.comparing(this.accessOrderWindowDeque().iterator(), this.accessOrderProbationDeque().iterator(), comparator.reversed());
            return LinkedDeque.PeekingIterator.concat(primary, this.accessOrderProtectedDeque().iterator());
        };
        return this.snapshot(iterable, transformer, mappingFunction);
    }

    <T> T expireAfterAccessOrder(boolean oldest, Function<V, V> transformer, Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
        Iterable<Node<K, V>> iterable = this.evicts() ? () -> {
            Iterator third;
            Iterator second;
            Iterator first;
            Comparator<Node> comparator = Comparator.comparingLong(Node::getAccessTime);
            if (oldest) {
                first = this.accessOrderWindowDeque().iterator();
                second = this.accessOrderProbationDeque().iterator();
                third = this.accessOrderProtectedDeque().iterator();
            } else {
                comparator = comparator.reversed();
                first = this.accessOrderWindowDeque().descendingIterator();
                second = this.accessOrderProbationDeque().descendingIterator();
                third = this.accessOrderProtectedDeque().descendingIterator();
            }
            return LinkedDeque.PeekingIterator.comparing(LinkedDeque.PeekingIterator.comparing(first, second, comparator), third, comparator);
        } : (oldest ? this.accessOrderWindowDeque() : this.accessOrderWindowDeque()::descendingIterator);
        return this.snapshot(iterable, transformer, mappingFunction);
    }

    <T> T snapshot(Iterable<Node<K, V>> iterable, Function<V, V> transformer, Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        Objects.requireNonNull(transformer);
        Objects.requireNonNull(iterable);
        this.evictionLock.lock();
        try {
            T t;
            block9: {
                this.maintenance(null);
                Stream<Node<K, V>> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 1297), false);
                try {
                    t = mappingFunction.apply(stream.map(node -> this.nodeToCacheEntry((Node<K, V>)node, transformer)).filter(Objects::nonNull));
                    if (stream == null) break block9;
                }
                catch (Throwable throwable) {
                    if (stream != null) {
                        try {
                            stream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                stream.close();
            }
            return t;
        }
        finally {
            this.evictionLock.unlock();
        }
    }

    @Nullable Policy.CacheEntry<K, V> nodeToCacheEntry(Node<K, V> node, Function<V, V> transformer) {
        long now;
        V value = transformer.apply(node.getValue());
        K key = node.getKey();
        if (key == null || value == null || !node.isAlive() || this.hasExpired(node, now = this.expirationTicker().read())) {
            return null;
        }
        long expiresAfter = Long.MAX_VALUE;
        if (this.expiresAfterAccess()) {
            expiresAfter = Math.min(expiresAfter, now - node.getAccessTime() + this.expiresAfterAccessNanos());
        }
        if (this.expiresAfterWrite()) {
            expiresAfter = Math.min(expiresAfter, (now & 0xFFFFFFFFFFFFFFFEL) - (node.getWriteTime() & 0xFFFFFFFFFFFFFFFEL) + this.expiresAfterWriteNanos());
        }
        if (this.expiresVariable()) {
            expiresAfter = node.getVariableTime() - now;
        }
        long refreshableAt = this.refreshAfterWrite() ? node.getWriteTime() + this.refreshAfterWriteNanos() : now + Long.MAX_VALUE;
        int weight = node.getPolicyWeight();
        return SnapshotEntry.forEntry(key, value, now, weight, now + expiresAfter, refreshableAt);
    }

    static <K, V> SerializationProxy<K, V> makeSerializationProxy(BoundedLocalCache<?, ?> cache) {
        SerializationProxy proxy = new SerializationProxy();
        proxy.weakKeys = cache.collectKeys();
        proxy.weakValues = cache.nodeFactory.weakValues();
        proxy.softValues = cache.nodeFactory.softValues();
        proxy.isRecordingStats = cache.isRecordingStats();
        proxy.evictionListener = cache.evictionListener;
        proxy.removalListener = cache.removalListener();
        proxy.ticker = cache.expirationTicker();
        if (cache.expiresAfterAccess()) {
            proxy.expiresAfterAccessNanos = cache.expiresAfterAccessNanos();
        }
        if (cache.expiresAfterWrite()) {
            proxy.expiresAfterWriteNanos = cache.expiresAfterWriteNanos();
        }
        if (cache.expiresVariable()) {
            proxy.expiry = cache.expiry();
        }
        if (cache.refreshAfterWrite()) {
            proxy.refreshAfterWriteNanos = cache.refreshAfterWriteNanos();
        }
        if (cache.evicts()) {
            if (cache.isWeighted) {
                proxy.weigher = cache.weigher;
                proxy.maximumWeight = cache.maximum();
            } else {
                proxy.maximumSize = cache.maximum();
            }
        }
        proxy.cacheLoader = cache.cacheLoader;
        proxy.async = cache.isAsync;
        return proxy;
    }

    static {
        try {
            REFRESHES = MethodHandles.lookup().findVarHandle(BoundedLocalCache.class, "refreshes", ConcurrentMap.class);
        }
        catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    static final class BoundedLocalAsyncLoadingCache<K, V>
    extends LocalAsyncLoadingCache<K, V>
    implements Serializable {
        private static final long serialVersionUID = 1L;
        final BoundedLocalCache<K, CompletableFuture<V>> cache;
        final boolean isWeighted;
        @Nullable ConcurrentMap<K, CompletableFuture<V>> mapView;
        @Nullable Policy<K, V> policy;

        BoundedLocalAsyncLoadingCache(Caffeine<K, V> builder, AsyncCacheLoader<? super K, V> loader) {
            super(loader);
            this.isWeighted = builder.isWeighted();
            this.cache = LocalCacheFactory.newBoundedLocalCache(builder, loader, true);
        }

        @Override
        public BoundedLocalCache<K, CompletableFuture<V>> cache() {
            return this.cache;
        }

        @Override
        public ConcurrentMap<K, CompletableFuture<V>> asMap() {
            return this.mapView == null ? (this.mapView = new LocalAsyncCache.AsyncAsMapView(this)) : this.mapView;
        }

        @Override
        public Policy<K, V> policy() {
            if (this.policy == null) {
                Function<CompletableFuture, Object> transformer;
                BoundedLocalCache<K, CompletableFuture<V>> castCache = this.cache;
                Function<CompletableFuture, Object> castTransformer = transformer = Async::getIfReady;
                this.policy = new BoundedPolicy<K, Object>(castCache, castTransformer, this.isWeighted);
            }
            return this.policy;
        }

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        private Object writeReplace() {
            return BoundedLocalCache.makeSerializationProxy(this.cache);
        }
    }

    static final class BoundedLocalAsyncCache<K, V>
    implements LocalAsyncCache<K, V>,
    Serializable {
        private static final long serialVersionUID = 1L;
        final BoundedLocalCache<K, CompletableFuture<V>> cache;
        final boolean isWeighted;
        @Nullable ConcurrentMap<K, CompletableFuture<V>> mapView;
        @Nullable LocalAsyncCache.CacheView<K, V> cacheView;
        @Nullable Policy<K, V> policy;

        BoundedLocalAsyncCache(Caffeine<K, V> builder) {
            this.cache = LocalCacheFactory.newBoundedLocalCache(builder, null, true);
            this.isWeighted = builder.isWeighted();
        }

        @Override
        public BoundedLocalCache<K, CompletableFuture<V>> cache() {
            return this.cache;
        }

        @Override
        public ConcurrentMap<K, CompletableFuture<V>> asMap() {
            return this.mapView == null ? (this.mapView = new LocalAsyncCache.AsyncAsMapView(this)) : this.mapView;
        }

        @Override
        public Cache<K, V> synchronous() {
            return this.cacheView == null ? (this.cacheView = new LocalAsyncCache.CacheView(this)) : this.cacheView;
        }

        @Override
        public Policy<K, V> policy() {
            if (this.policy == null) {
                Function<CompletableFuture, Object> transformer;
                BoundedLocalCache<K, CompletableFuture<V>> castCache = this.cache;
                Function<CompletableFuture, Object> castTransformer = transformer = Async::getIfReady;
                this.policy = new BoundedPolicy<K, Object>(castCache, castTransformer, this.isWeighted);
            }
            return this.policy;
        }

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        private Object writeReplace() {
            return BoundedLocalCache.makeSerializationProxy(this.cache);
        }
    }

    static final class BoundedLocalLoadingCache<K, V>
    extends BoundedLocalManualCache<K, V>
    implements LocalLoadingCache<K, V> {
        private static final long serialVersionUID = 1L;
        final Function<K, V> mappingFunction;
        final @Nullable Function<Set<? extends K>, Map<K, V>> bulkMappingFunction;

        BoundedLocalLoadingCache(Caffeine<K, V> builder, CacheLoader<? super K, V> loader) {
            super(builder, loader);
            Objects.requireNonNull(loader);
            this.mappingFunction = LocalLoadingCache.newMappingFunction(loader);
            this.bulkMappingFunction = LocalLoadingCache.newBulkMappingFunction(loader);
        }

        @Override
        public AsyncCacheLoader<? super K, V> cacheLoader() {
            return this.cache.cacheLoader;
        }

        @Override
        public Function<K, V> mappingFunction() {
            return this.mappingFunction;
        }

        @Override
        public @Nullable Function<Set<? extends K>, Map<K, V>> bulkMappingFunction() {
            return this.bulkMappingFunction;
        }

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        private Object writeReplace() {
            return BoundedLocalCache.makeSerializationProxy(this.cache);
        }
    }

    static final class BoundedPolicy<K, V>
    implements Policy<K, V> {
        final BoundedLocalCache<K, V> cache;
        final Function<V, V> transformer;
        final boolean isWeighted;
        @Nullable Optional<Policy.Eviction<K, V>> eviction;
        @Nullable Optional<Policy.FixedRefresh<K, V>> refreshes;
        @Nullable Optional<Policy.FixedExpiration<K, V>> afterWrite;
        @Nullable Optional<Policy.FixedExpiration<K, V>> afterAccess;
        @Nullable Optional<Policy.VarExpiration<K, V>> variable;

        BoundedPolicy(BoundedLocalCache<K, V> cache, Function<V, V> transformer, boolean isWeighted) {
            this.transformer = transformer;
            this.isWeighted = isWeighted;
            this.cache = cache;
        }

        @Override
        public boolean isRecordingStats() {
            return this.cache.isRecordingStats();
        }

        @Override
        public @Nullable V getIfPresentQuietly(K key) {
            return this.transformer.apply(this.cache.getIfPresentQuietly(key));
        }

        @Override
        public @Nullable Policy.CacheEntry<K, V> getEntryIfPresentQuietly(K key) {
            Node node = this.cache.data.get(this.cache.nodeFactory.newLookupKey(key));
            return node == null ? null : this.cache.nodeToCacheEntry(node, this.transformer);
        }

        @Override
        public Map<K, CompletableFuture<V>> refreshes() {
            ConcurrentMap<Object, CompletableFuture<?>> refreshes = this.cache.refreshes;
            if (refreshes == null || refreshes.isEmpty()) {
                return Map.of();
            }
            if (this.cache.collectKeys()) {
                IdentityHashMap inFlight = new IdentityHashMap(refreshes.size());
                for (Map.Entry entry : refreshes.entrySet()) {
                    Object key = ((References.InternalReference)entry.getKey()).get();
                    CompletableFuture future = (CompletableFuture)entry.getValue();
                    if (key == null) continue;
                    inFlight.put(key, future);
                }
                return Collections.unmodifiableMap(inFlight);
            }
            Map castedRefreshes = refreshes;
            return Map.copyOf(castedRefreshes);
        }

        @Override
        public Optional<Policy.Eviction<K, V>> eviction() {
            Optional<Policy.Eviction<K, V>> optional;
            if (this.cache.evicts()) {
                if (this.eviction == null) {
                    this.eviction = Optional.of(new BoundedEviction());
                    optional = this.eviction;
                } else {
                    optional = this.eviction;
                }
            } else {
                optional = Optional.empty();
            }
            return optional;
        }

        @Override
        public Optional<Policy.FixedExpiration<K, V>> expireAfterAccess() {
            if (!this.cache.expiresAfterAccess()) {
                return Optional.empty();
            }
            return this.afterAccess == null ? (this.afterAccess = Optional.of(new BoundedExpireAfterAccess())) : this.afterAccess;
        }

        @Override
        public Optional<Policy.FixedExpiration<K, V>> expireAfterWrite() {
            if (!this.cache.expiresAfterWrite()) {
                return Optional.empty();
            }
            return this.afterWrite == null ? (this.afterWrite = Optional.of(new BoundedExpireAfterWrite())) : this.afterWrite;
        }

        @Override
        public Optional<Policy.VarExpiration<K, V>> expireVariably() {
            if (!this.cache.expiresVariable()) {
                return Optional.empty();
            }
            return this.variable == null ? (this.variable = Optional.of(new BoundedVarExpiration())) : this.variable;
        }

        @Override
        public Optional<Policy.FixedRefresh<K, V>> refreshAfterWrite() {
            if (!this.cache.refreshAfterWrite()) {
                return Optional.empty();
            }
            return this.refreshes == null ? (this.refreshes = Optional.of(new BoundedRefreshAfterWrite())) : this.refreshes;
        }

        final class BoundedRefreshAfterWrite
        implements Policy.FixedRefresh<K, V> {
            BoundedRefreshAfterWrite() {
            }

            @Override
            public OptionalLong ageOf(K key, TimeUnit unit) {
                Objects.requireNonNull(key);
                Objects.requireNonNull(unit);
                Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                Node node = BoundedPolicy.this.cache.data.get(lookupKey);
                if (node == null) {
                    return OptionalLong.empty();
                }
                long now = BoundedPolicy.this.cache.expirationTicker().read();
                return BoundedPolicy.this.cache.hasExpired(node, now) ? OptionalLong.empty() : OptionalLong.of(unit.convert(now - node.getWriteTime(), TimeUnit.NANOSECONDS));
            }

            @Override
            public long getRefreshesAfter(TimeUnit unit) {
                return unit.convert(BoundedPolicy.this.cache.refreshAfterWriteNanos(), TimeUnit.NANOSECONDS);
            }

            @Override
            public void setRefreshesAfter(long duration, TimeUnit unit) {
                Caffeine.requireArgument(duration >= 0L);
                BoundedPolicy.this.cache.setRefreshAfterWriteNanos(unit.toNanos(duration));
                BoundedPolicy.this.cache.scheduleAfterWrite();
            }
        }

        static final class FixedExpiry<K, V>
        implements Expiry<K, V> {
            final long duration;
            final TimeUnit unit;

            FixedExpiry(long duration, TimeUnit unit) {
                this.duration = duration;
                this.unit = unit;
            }

            @Override
            public long expireAfterCreate(K key, V value, long currentTime) {
                return this.unit.toNanos(this.duration);
            }

            @Override
            public long expireAfterUpdate(K key, V value, long currentTime, long currentDuration) {
                return this.unit.toNanos(this.duration);
            }

            @Override
            public long expireAfterRead(K key, V value, long currentTime, long currentDuration) {
                return currentDuration;
            }
        }

        final class BoundedVarExpiration
        implements Policy.VarExpiration<K, V> {
            BoundedVarExpiration() {
            }

            @Override
            public OptionalLong getExpiresAfter(K key, TimeUnit unit) {
                Objects.requireNonNull(key);
                Objects.requireNonNull(unit);
                Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                Node node = BoundedPolicy.this.cache.data.get(lookupKey);
                if (node == null) {
                    return OptionalLong.empty();
                }
                long now = BoundedPolicy.this.cache.expirationTicker().read();
                return BoundedPolicy.this.cache.hasExpired(node, now) ? OptionalLong.empty() : OptionalLong.of(unit.convert(node.getVariableTime() - now, TimeUnit.NANOSECONDS));
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void setExpiresAfter(K key, long duration, TimeUnit unit) {
                Objects.requireNonNull(key);
                Objects.requireNonNull(unit);
                Caffeine.requireArgument(duration >= 0L);
                Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                Node node = BoundedPolicy.this.cache.data.get(lookupKey);
                if (node != null) {
                    long now;
                    long durationNanos = TimeUnit.NANOSECONDS.convert(duration, unit);
                    Node node2 = node;
                    synchronized (node2) {
                        now = BoundedPolicy.this.cache.expirationTicker().read();
                        if (BoundedPolicy.this.cache.hasExpired(node, now)) {
                            return;
                        }
                        node.setVariableTime(now + Math.min(durationNanos, 0x3FFFFFFFFFFFFFFFL));
                    }
                    BoundedPolicy.this.cache.afterRead(node, now, false);
                }
            }

            @Override
            public @Nullable V put(K key, V value, long duration, TimeUnit unit) {
                Objects.requireNonNull(unit);
                Objects.requireNonNull(value);
                Caffeine.requireArgument(duration >= 0L);
                return BoundedPolicy.this.cache.isAsync ? this.putAsync(key, value, duration, unit) : this.putSync(key, value, duration, unit, false);
            }

            @Override
            public @Nullable V putIfAbsent(K key, V value, long duration, TimeUnit unit) {
                Objects.requireNonNull(unit);
                Objects.requireNonNull(value);
                Caffeine.requireArgument(duration >= 0L);
                return BoundedPolicy.this.cache.isAsync ? this.putIfAbsentAsync(key, value, duration, unit) : this.putSync(key, value, duration, unit, true);
            }

            @Nullable V putSync(K key, V value, long duration, TimeUnit unit, boolean onlyIfAbsent) {
                FixedExpiry expiry = new FixedExpiry(duration, unit);
                return BoundedPolicy.this.cache.put(key, value, expiry, onlyIfAbsent);
            }

            @Nullable V putIfAbsentAsync(K key, V value, long duration, TimeUnit unit) {
                Object prior;
                Async.AsyncExpiry expiry = new Async.AsyncExpiry(new FixedExpiry(duration, unit));
                CompletableFuture asyncValue = CompletableFuture.completedFuture(value);
                while (true) {
                    CompletableFuture priorFuture;
                    if ((priorFuture = (CompletableFuture)BoundedPolicy.this.cache.getIfPresent(key, false)) != null) {
                        if (!priorFuture.isDone()) {
                            Async.getWhenSuccessful(priorFuture);
                            continue;
                        }
                        Object prior2 = Async.getWhenSuccessful(priorFuture);
                        if (prior2 != null) {
                            return prior2;
                        }
                    }
                    boolean[] added = new boolean[]{false};
                    CompletableFuture computed = (CompletableFuture)BoundedPolicy.this.cache.compute(key, (k, oldValue) -> {
                        CompletableFuture oldValueFuture = (CompletableFuture)oldValue;
                        added[0] = oldValueFuture == null || oldValueFuture.isDone() && Async.getIfReady(oldValueFuture) == null;
                        return added[0] ? asyncValue : oldValue;
                    }, expiry, false, false);
                    if (added[0]) {
                        return null;
                    }
                    prior = Async.getWhenSuccessful(computed);
                    if (prior != null) break;
                }
                return prior;
            }

            @Nullable V putAsync(K key, V value, long duration, TimeUnit unit) {
                Async.AsyncExpiry expiry = new Async.AsyncExpiry(new FixedExpiry(duration, unit));
                CompletableFuture asyncValue = CompletableFuture.completedFuture(value);
                CompletableFuture oldValueFuture = BoundedPolicy.this.cache.put(key, asyncValue, expiry, false);
                return Async.getWhenSuccessful(oldValueFuture);
            }

            @Override
            public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, Duration duration) {
                Objects.requireNonNull(key);
                Objects.requireNonNull(duration);
                Objects.requireNonNull(remappingFunction);
                Caffeine.requireArgument(!duration.isNegative(), "duration cannot be negative: %s", duration);
                FixedExpiry expiry = new FixedExpiry(Caffeine.saturatedToNanos(duration), TimeUnit.NANOSECONDS);
                return BoundedPolicy.this.cache.isAsync ? this.computeAsync(key, remappingFunction, expiry) : BoundedPolicy.this.cache.compute((Object)key, remappingFunction, expiry, true, true);
            }

            @Nullable V computeAsync(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction, Expiry<? super K, ? super V> expiry) {
                CompletableFuture valueFuture;
                BoundedLocalCache delegate = BoundedPolicy.this.cache;
                Object[] newValue = new Object[1];
                do {
                    Async.getWhenSuccessful((CompletableFuture)delegate.getIfPresentQuietly(key));
                    valueFuture = (CompletableFuture)delegate.compute((Object)key, (BiFunction)(k, oldValueFuture) -> {
                        if (oldValueFuture != null && !oldValueFuture.isDone()) {
                            return oldValueFuture;
                        }
                        Object oldValue = Async.getIfReady(oldValueFuture);
                        BiFunction function = delegate.statsAware(remappingFunction, true, true);
                        newValue[0] = function.apply(key, oldValue);
                        return newValue[0] == null ? null : CompletableFuture.completedFuture(newValue[0]);
                    }, new Async.AsyncExpiry(expiry), false, false);
                    if (newValue[0] == null) continue;
                    return newValue[0];
                } while (valueFuture != null);
                return null;
            }

            @Override
            public Map<K, V> oldest(int limit) {
                return (Map)this.oldest(new SizeLimiter(Math.min(limit, BoundedPolicy.this.cache.size()), limit));
            }

            @Override
            public <T> T oldest(Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
                return BoundedPolicy.this.cache.snapshot(BoundedPolicy.this.cache.timerWheel(), BoundedPolicy.this.transformer, mappingFunction);
            }

            @Override
            public Map<K, V> youngest(int limit) {
                return (Map)this.youngest(new SizeLimiter(Math.min(limit, BoundedPolicy.this.cache.size()), limit));
            }

            @Override
            public <T> T youngest(Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
                return BoundedPolicy.this.cache.snapshot(BoundedPolicy.this.cache.timerWheel()::descendingIterator, BoundedPolicy.this.transformer, mappingFunction);
            }
        }

        final class BoundedExpireAfterWrite
        implements Policy.FixedExpiration<K, V> {
            BoundedExpireAfterWrite() {
            }

            @Override
            public OptionalLong ageOf(K key, TimeUnit unit) {
                Objects.requireNonNull(key);
                Objects.requireNonNull(unit);
                Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                Node node = BoundedPolicy.this.cache.data.get(lookupKey);
                if (node == null) {
                    return OptionalLong.empty();
                }
                long now = BoundedPolicy.this.cache.expirationTicker().read();
                return BoundedPolicy.this.cache.hasExpired(node, now) ? OptionalLong.empty() : OptionalLong.of(unit.convert(now - node.getWriteTime(), TimeUnit.NANOSECONDS));
            }

            @Override
            public long getExpiresAfter(TimeUnit unit) {
                return unit.convert(BoundedPolicy.this.cache.expiresAfterWriteNanos(), TimeUnit.NANOSECONDS);
            }

            @Override
            public void setExpiresAfter(long duration, TimeUnit unit) {
                Caffeine.requireArgument(duration >= 0L);
                BoundedPolicy.this.cache.setExpiresAfterWriteNanos(unit.toNanos(duration));
                BoundedPolicy.this.cache.scheduleAfterWrite();
            }

            @Override
            public Map<K, V> oldest(int limit) {
                return (Map)this.oldest(new SizeLimiter(Math.min(limit, BoundedPolicy.this.cache.size()), limit));
            }

            @Override
            public <T> T oldest(Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
                return BoundedPolicy.this.cache.snapshot(BoundedPolicy.this.cache.writeOrderDeque(), BoundedPolicy.this.transformer, mappingFunction);
            }

            @Override
            public Map<K, V> youngest(int limit) {
                return (Map)this.youngest(new SizeLimiter(Math.min(limit, BoundedPolicy.this.cache.size()), limit));
            }

            @Override
            public <T> T youngest(Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
                return BoundedPolicy.this.cache.snapshot(BoundedPolicy.this.cache.writeOrderDeque()::descendingIterator, BoundedPolicy.this.transformer, mappingFunction);
            }
        }

        final class BoundedExpireAfterAccess
        implements Policy.FixedExpiration<K, V> {
            BoundedExpireAfterAccess() {
            }

            @Override
            public OptionalLong ageOf(K key, TimeUnit unit) {
                Objects.requireNonNull(key);
                Objects.requireNonNull(unit);
                Object lookupKey = BoundedPolicy.this.cache.nodeFactory.newLookupKey(key);
                Node node = BoundedPolicy.this.cache.data.get(lookupKey);
                if (node == null) {
                    return OptionalLong.empty();
                }
                long now = BoundedPolicy.this.cache.expirationTicker().read();
                return BoundedPolicy.this.cache.hasExpired(node, now) ? OptionalLong.empty() : OptionalLong.of(unit.convert(now - node.getAccessTime(), TimeUnit.NANOSECONDS));
            }

            @Override
            public long getExpiresAfter(TimeUnit unit) {
                return unit.convert(BoundedPolicy.this.cache.expiresAfterAccessNanos(), TimeUnit.NANOSECONDS);
            }

            @Override
            public void setExpiresAfter(long duration, TimeUnit unit) {
                Caffeine.requireArgument(duration >= 0L);
                BoundedPolicy.this.cache.setExpiresAfterAccessNanos(unit.toNanos(duration));
                BoundedPolicy.this.cache.scheduleAfterWrite();
            }

            @Override
            public Map<K, V> oldest(int limit) {
                return (Map)this.oldest(new SizeLimiter(Math.min(limit, BoundedPolicy.this.cache.size()), limit));
            }

            @Override
            public <T> T oldest(Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
                return BoundedPolicy.this.cache.expireAfterAccessOrder(true, BoundedPolicy.this.transformer, mappingFunction);
            }

            @Override
            public Map<K, V> youngest(int limit) {
                return (Map)this.youngest(new SizeLimiter(Math.min(limit, BoundedPolicy.this.cache.size()), limit));
            }

            @Override
            public <T> T youngest(Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
                return BoundedPolicy.this.cache.expireAfterAccessOrder(false, BoundedPolicy.this.transformer, mappingFunction);
            }
        }

        final class BoundedEviction
        implements Policy.Eviction<K, V> {
            BoundedEviction() {
            }

            @Override
            public boolean isWeighted() {
                return BoundedPolicy.this.isWeighted;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public OptionalInt weightOf(K key) {
                Objects.requireNonNull(key);
                if (!BoundedPolicy.this.isWeighted) {
                    return OptionalInt.empty();
                }
                Node node = BoundedPolicy.this.cache.data.get(BoundedPolicy.this.cache.nodeFactory.newLookupKey(key));
                if (node == null || BoundedPolicy.this.cache.hasExpired(node, BoundedPolicy.this.cache.expirationTicker().read())) {
                    return OptionalInt.empty();
                }
                Node node2 = node;
                synchronized (node2) {
                    return OptionalInt.of(node.getWeight());
                }
            }

            @Override
            public OptionalLong weightedSize() {
                if (BoundedPolicy.this.cache.evicts() && this.isWeighted()) {
                    BoundedPolicy.this.cache.evictionLock.lock();
                    try {
                        OptionalLong optionalLong = OptionalLong.of(Math.max(0L, BoundedPolicy.this.cache.weightedSize()));
                        return optionalLong;
                    }
                    finally {
                        BoundedPolicy.this.cache.evictionLock.unlock();
                    }
                }
                return OptionalLong.empty();
            }

            @Override
            public long getMaximum() {
                BoundedPolicy.this.cache.evictionLock.lock();
                try {
                    long l = BoundedPolicy.this.cache.maximum();
                    return l;
                }
                finally {
                    BoundedPolicy.this.cache.evictionLock.unlock();
                }
            }

            @Override
            public void setMaximum(long maximum) {
                BoundedPolicy.this.cache.evictionLock.lock();
                try {
                    BoundedPolicy.this.cache.setMaximumSize(maximum);
                    BoundedPolicy.this.cache.maintenance(null);
                }
                finally {
                    BoundedPolicy.this.cache.evictionLock.unlock();
                }
            }

            @Override
            public Map<K, V> coldest(int limit) {
                int expectedSize = Math.min(limit, BoundedPolicy.this.cache.size());
                SizeLimiter limiter = new SizeLimiter(expectedSize, limit);
                return (Map)BoundedPolicy.this.cache.evictionOrder(false, BoundedPolicy.this.transformer, limiter);
            }

            @Override
            public Map<K, V> coldestWeighted(long weightLimit) {
                WeightLimiter limiter = new WeightLimiter(weightLimit);
                return (Map)BoundedPolicy.this.cache.evictionOrder(false, BoundedPolicy.this.transformer, limiter);
            }

            @Override
            public <T> T coldest(Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
                Objects.requireNonNull(mappingFunction);
                return BoundedPolicy.this.cache.evictionOrder(false, BoundedPolicy.this.transformer, mappingFunction);
            }

            @Override
            public Map<K, V> hottest(int limit) {
                int expectedSize = Math.min(limit, BoundedPolicy.this.cache.size());
                SizeLimiter limiter = new SizeLimiter(expectedSize, limit);
                return (Map)BoundedPolicy.this.cache.evictionOrder(true, BoundedPolicy.this.transformer, limiter);
            }

            @Override
            public Map<K, V> hottestWeighted(long weightLimit) {
                Function limiter = this.isWeighted() ? new WeightLimiter(weightLimit) : new SizeLimiter((int)Math.min(weightLimit, (long)BoundedPolicy.this.cache.size()), weightLimit);
                return (Map)BoundedPolicy.this.cache.evictionOrder(true, BoundedPolicy.this.transformer, limiter);
            }

            @Override
            public <T> T hottest(Function<Stream<Policy.CacheEntry<K, V>>, T> mappingFunction) {
                Objects.requireNonNull(mappingFunction);
                return BoundedPolicy.this.cache.evictionOrder(true, BoundedPolicy.this.transformer, mappingFunction);
            }
        }
    }

    static class BoundedLocalManualCache<K, V>
    implements LocalManualCache<K, V>,
    Serializable {
        private static final long serialVersionUID = 1L;
        final BoundedLocalCache<K, V> cache;
        @Nullable Policy<K, V> policy;

        BoundedLocalManualCache(Caffeine<K, V> builder) {
            this(builder, null);
        }

        BoundedLocalManualCache(Caffeine<K, V> builder, @Nullable CacheLoader<? super K, V> loader) {
            this.cache = LocalCacheFactory.newBoundedLocalCache(builder, loader, false);
        }

        @Override
        public BoundedLocalCache<K, V> cache() {
            return this.cache;
        }

        @Override
        public Policy<K, V> policy() {
            Policy<K, V> p = this.policy;
            return p == null ? (this.policy = new BoundedPolicy<K, V>(this.cache, Function.identity(), this.cache.isWeighted)) : p;
        }

        private void readObject(ObjectInputStream stream) throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        private Object writeReplace() {
            return BoundedLocalCache.makeSerializationProxy(this.cache);
        }
    }

    static final class PerformCleanupTask
    extends ForkJoinTask<Void>
    implements Runnable {
        private static final long serialVersionUID = 1L;
        final WeakReference<BoundedLocalCache<?, ?>> reference;

        PerformCleanupTask(BoundedLocalCache<?, ?> cache) {
            this.reference = new WeakReference(cache);
        }

        @Override
        public boolean exec() {
            try {
                this.run();
            }
            catch (Throwable t) {
                logger.log(System.Logger.Level.ERROR, "Exception thrown when performing the maintenance task", t);
            }
            return false;
        }

        @Override
        public void run() {
            BoundedLocalCache cache = (BoundedLocalCache)this.reference.get();
            if (cache != null) {
                cache.performCleanUp(null);
            }
        }

        @Override
        public Void getRawResult() {
            return null;
        }

        @Override
        public void setRawResult(Void v) {
        }

        @Override
        public void complete(Void value) {
        }

        @Override
        public void completeExceptionally(Throwable ex) {
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }
    }

    static final class EntrySpliterator<K, V>
    implements Spliterator<Map.Entry<K, V>> {
        final Spliterator<Node<K, V>> spliterator;
        final BoundedLocalCache<K, V> cache;

        EntrySpliterator(BoundedLocalCache<K, V> cache) {
            this(cache, cache.data.values().spliterator());
        }

        EntrySpliterator(BoundedLocalCache<K, V> cache, Spliterator<Node<K, V>> spliterator) {
            this.spliterator = Objects.requireNonNull(spliterator);
            this.cache = Objects.requireNonNull(cache);
        }

        @Override
        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> action) {
            Objects.requireNonNull(action);
            Consumer<Node> consumer = node -> {
                Object key = node.getKey();
                Object value = node.getValue();
                long now = this.cache.expirationTicker().read();
                if (key != null && value != null && node.isAlive() && !this.cache.hasExpired((Node<K, V>)node, now)) {
                    action.accept(new WriteThroughEntry<K, V>(this.cache, key, value));
                }
            };
            this.spliterator.forEachRemaining(consumer);
        }

        @Override
        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> action) {
            Objects.requireNonNull(action);
            boolean[] advanced = new boolean[]{false};
            Consumer<Node> consumer = node -> {
                Object key = node.getKey();
                Object value = node.getValue();
                long now = this.cache.expirationTicker().read();
                if (key != null && value != null && node.isAlive() && !this.cache.hasExpired((Node<K, V>)node, now)) {
                    action.accept(new WriteThroughEntry<K, V>(this.cache, key, value));
                    advanced[0] = true;
                }
            };
            while (this.spliterator.tryAdvance(consumer)) {
                if (!advanced[0]) continue;
                return true;
            }
            return false;
        }

        @Override
        public @Nullable Spliterator<Map.Entry<K, V>> trySplit() {
            Spliterator<Node<K, V>> split = this.spliterator.trySplit();
            return split == null ? null : new EntrySpliterator<K, V>(this.cache, split);
        }

        @Override
        public long estimateSize() {
            return this.spliterator.estimateSize();
        }

        @Override
        public int characteristics() {
            return 4353;
        }
    }

    static final class EntryIterator<K, V>
    implements Iterator<Map.Entry<K, V>> {
        final BoundedLocalCache<K, V> cache;
        final Iterator<Node<K, V>> iterator;
        @Nullable K key;
        @Nullable V value;
        @Nullable K removalKey;
        @Nullable Node<K, V> next;

        EntryIterator(BoundedLocalCache<K, V> cache) {
            this.iterator = cache.data.values().iterator();
            this.cache = cache;
        }

        @Override
        public boolean hasNext() {
            if (this.next != null) {
                return true;
            }
            long now = this.cache.expirationTicker().read();
            while (this.iterator.hasNext()) {
                boolean evictable;
                this.next = this.iterator.next();
                this.value = this.next.getValue();
                this.key = this.next.getKey();
                boolean bl = evictable = this.key == null || this.value == null || this.cache.hasExpired(this.next, now);
                if (evictable || !this.next.isAlive()) {
                    if (evictable) {
                        this.cache.scheduleDrainBuffers();
                    }
                    this.value = null;
                    this.next = null;
                    this.key = null;
                    continue;
                }
                return true;
            }
            return false;
        }

        K nextKey() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.removalKey = this.key;
            this.value = null;
            this.next = null;
            this.key = null;
            return this.removalKey;
        }

        V nextValue() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.removalKey = this.key;
            V val = this.value;
            this.value = null;
            this.next = null;
            this.key = null;
            return val;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            WriteThroughEntry<K, V> entry = new WriteThroughEntry<K, V>(this.cache, this.key, this.value);
            this.removalKey = this.key;
            this.value = null;
            this.next = null;
            this.key = null;
            return entry;
        }

        @Override
        public void remove() {
            if (this.removalKey == null) {
                throw new IllegalStateException();
            }
            this.cache.remove(this.removalKey);
            this.removalKey = null;
        }
    }

    static final class EntrySetView<K, V>
    extends AbstractSet<Map.Entry<K, V>> {
        final BoundedLocalCache<K, V> cache;

        EntrySetView(BoundedLocalCache<K, V> cache) {
            this.cache = Objects.requireNonNull(cache);
        }

        @Override
        public int size() {
            return this.cache.size();
        }

        @Override
        public void clear() {
            this.cache.clear();
        }

        @Override
        public boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key == null || value == null) {
                return false;
            }
            Node node = this.cache.data.get(this.cache.nodeFactory.newLookupKey(key));
            return node != null && node.containsValue(value);
        }

        @Override
        public boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry)obj;
            return this.cache.remove(entry.getKey(), entry.getValue());
        }

        @Override
        public boolean removeIf(Predicate<? super Map.Entry<K, V>> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            for (Map.Entry<K, V> entry : this) {
                if (!filter.test(entry)) continue;
                removed |= this.cache.remove(entry.getKey(), entry.getValue());
            }
            return removed;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator<K, V>(this.cache);
        }

        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator<K, V>(this.cache);
        }
    }

    static final class ValueSpliterator<K, V>
    implements Spliterator<V> {
        final Spliterator<Node<K, V>> spliterator;
        final BoundedLocalCache<K, V> cache;

        ValueSpliterator(BoundedLocalCache<K, V> cache) {
            this(cache, cache.data.values().spliterator());
        }

        ValueSpliterator(BoundedLocalCache<K, V> cache, Spliterator<Node<K, V>> spliterator) {
            this.spliterator = Objects.requireNonNull(spliterator);
            this.cache = Objects.requireNonNull(cache);
        }

        @Override
        public void forEachRemaining(Consumer<? super V> action) {
            Objects.requireNonNull(action);
            Consumer<Node> consumer = node -> {
                Object key = node.getKey();
                Object value = node.getValue();
                long now = this.cache.expirationTicker().read();
                if (key != null && value != null && node.isAlive() && !this.cache.hasExpired((Node<K, V>)node, now)) {
                    action.accept((V)value);
                }
            };
            this.spliterator.forEachRemaining(consumer);
        }

        @Override
        public boolean tryAdvance(Consumer<? super V> action) {
            Objects.requireNonNull(action);
            boolean[] advanced = new boolean[]{false};
            long now = this.cache.expirationTicker().read();
            Consumer<Node> consumer = node -> {
                Object key = node.getKey();
                Object value = node.getValue();
                if (key != null && value != null && !this.cache.hasExpired((Node<K, V>)node, now) && node.isAlive()) {
                    action.accept((V)value);
                    advanced[0] = true;
                }
            };
            while (this.spliterator.tryAdvance(consumer)) {
                if (!advanced[0]) continue;
                return true;
            }
            return false;
        }

        @Override
        public @Nullable Spliterator<V> trySplit() {
            Spliterator<Node<K, V>> split = this.spliterator.trySplit();
            return split == null ? null : new ValueSpliterator<K, V>(this.cache, split);
        }

        @Override
        public long estimateSize() {
            return this.spliterator.estimateSize();
        }

        @Override
        public int characteristics() {
            return 4352;
        }
    }

    static final class ValueIterator<K, V>
    implements Iterator<V> {
        final EntryIterator<K, V> iterator;

        ValueIterator(BoundedLocalCache<K, V> cache) {
            this.iterator = new EntryIterator<K, V>(cache);
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public V next() {
            return this.iterator.nextValue();
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }
    }

    static final class ValuesView<K, V>
    extends AbstractCollection<V> {
        final BoundedLocalCache<K, V> cache;

        ValuesView(BoundedLocalCache<K, V> cache) {
            this.cache = Objects.requireNonNull(cache);
        }

        @Override
        public int size() {
            return this.cache.size();
        }

        @Override
        public void clear() {
            this.cache.clear();
        }

        @Override
        public boolean contains(Object o) {
            return this.cache.containsValue(o);
        }

        @Override
        public boolean removeIf(Predicate<? super V> filter) {
            Objects.requireNonNull(filter);
            boolean removed = false;
            for (Map.Entry<K, V> entry : this.cache.entrySet()) {
                if (!filter.test(entry.getValue())) continue;
                removed |= this.cache.remove(entry.getKey(), entry.getValue());
            }
            return removed;
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator<K, V>(this.cache);
        }

        @Override
        public Spliterator<V> spliterator() {
            return new ValueSpliterator<K, V>(this.cache);
        }
    }

    static final class KeySpliterator<K, V>
    implements Spliterator<K> {
        final Spliterator<Node<K, V>> spliterator;
        final BoundedLocalCache<K, V> cache;

        KeySpliterator(BoundedLocalCache<K, V> cache) {
            this(cache, cache.data.values().spliterator());
        }

        KeySpliterator(BoundedLocalCache<K, V> cache, Spliterator<Node<K, V>> spliterator) {
            this.spliterator = Objects.requireNonNull(spliterator);
            this.cache = Objects.requireNonNull(cache);
        }

        @Override
        public void forEachRemaining(Consumer<? super K> action) {
            Objects.requireNonNull(action);
            Consumer<Node> consumer = node -> {
                Object key = node.getKey();
                Object value = node.getValue();
                long now = this.cache.expirationTicker().read();
                if (key != null && value != null && node.isAlive() && !this.cache.hasExpired((Node<K, V>)node, now)) {
                    action.accept((K)key);
                }
            };
            this.spliterator.forEachRemaining(consumer);
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> action) {
            Objects.requireNonNull(action);
            boolean[] advanced = new boolean[]{false};
            Consumer<Node> consumer = node -> {
                Object key = node.getKey();
                Object value = node.getValue();
                long now = this.cache.expirationTicker().read();
                if (key != null && value != null && node.isAlive() && !this.cache.hasExpired((Node<K, V>)node, now)) {
                    action.accept((K)key);
                    advanced[0] = true;
                }
            };
            while (this.spliterator.tryAdvance(consumer)) {
                if (!advanced[0]) continue;
                return true;
            }
            return false;
        }

        @Override
        public @Nullable Spliterator<K> trySplit() {
            Spliterator<Node<K, V>> split = this.spliterator.trySplit();
            return split == null ? null : new KeySpliterator<K, V>(this.cache, split);
        }

        @Override
        public long estimateSize() {
            return this.spliterator.estimateSize();
        }

        @Override
        public int characteristics() {
            return 4353;
        }
    }

    static final class KeyIterator<K, V>
    implements Iterator<K> {
        final EntryIterator<K, V> iterator;

        KeyIterator(BoundedLocalCache<K, V> cache) {
            this.iterator = new EntryIterator<K, V>(cache);
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public K next() {
            return this.iterator.nextKey();
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }
    }

    static final class KeySetView<K, V>
    extends AbstractSet<K> {
        final BoundedLocalCache<K, V> cache;

        KeySetView(BoundedLocalCache<K, V> cache) {
            this.cache = Objects.requireNonNull(cache);
        }

        @Override
        public int size() {
            return this.cache.size();
        }

        @Override
        public void clear() {
            this.cache.clear();
        }

        @Override
        public boolean contains(Object obj) {
            return this.cache.containsKey(obj);
        }

        @Override
        public boolean remove(Object obj) {
            return this.cache.remove(obj) != null;
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator<K, V>(this.cache);
        }

        @Override
        public Spliterator<K> spliterator() {
            return new KeySpliterator<K, V>(this.cache);
        }
    }

    static final class WeightLimiter<K, V>
    implements Function<Stream<Policy.CacheEntry<K, V>>, Map<K, V>> {
        private final long weightLimit;
        private long weightedSize;

        WeightLimiter(long weightLimit) {
            Caffeine.requireArgument(weightLimit >= 0L);
            this.weightLimit = weightLimit;
        }

        @Override
        public Map<K, V> apply(Stream<Policy.CacheEntry<K, V>> stream) {
            LinkedHashMap map = new LinkedHashMap();
            stream.takeWhile(entry -> {
                this.weightedSize = Math.addExact(this.weightedSize, (long)entry.weight());
                return this.weightedSize <= this.weightLimit;
            }).forEach(entry -> map.put(entry.getKey(), entry.getValue()));
            return Collections.unmodifiableMap(map);
        }
    }

    static final class SizeLimiter<K, V>
    implements Function<Stream<Policy.CacheEntry<K, V>>, Map<K, V>> {
        private final int expectedSize;
        private final long limit;

        SizeLimiter(int expectedSize, long limit) {
            Caffeine.requireArgument(limit >= 0L);
            this.expectedSize = expectedSize;
            this.limit = limit;
        }

        @Override
        public Map<K, V> apply(Stream<Policy.CacheEntry<K, V>> stream) {
            LinkedHashMap map = new LinkedHashMap(Caffeine.calculateHashMapCapacity(this.expectedSize));
            stream.limit(this.limit).forEach(entry -> map.put(entry.getKey(), entry.getValue()));
            return Collections.unmodifiableMap(map);
        }
    }

    final class UpdateTask
    implements Runnable {
        final int weightDifference;
        final Node<K, V> node;

        public UpdateTask(Node<K, V> node, int weightDifference) {
            this.weightDifference = weightDifference;
            this.node = node;
        }

        @Override
        @GuardedBy(value="evictionLock")
        public void run() {
            if (BoundedLocalCache.this.evicts()) {
                int oldWeightedSize = this.node.getPolicyWeight();
                this.node.setPolicyWeight(oldWeightedSize + this.weightDifference);
                if (this.node.inWindow()) {
                    if ((long)this.node.getPolicyWeight() <= BoundedLocalCache.this.windowMaximum()) {
                        BoundedLocalCache.this.onAccess(this.node);
                    } else if (BoundedLocalCache.this.accessOrderWindowDeque().contains(this.node)) {
                        BoundedLocalCache.this.accessOrderWindowDeque().moveToFront(this.node);
                    }
                    BoundedLocalCache.this.setWindowWeightedSize(BoundedLocalCache.this.windowWeightedSize() + (long)this.weightDifference);
                } else if (this.node.inMainProbation()) {
                    if ((long)this.node.getPolicyWeight() <= BoundedLocalCache.this.maximum()) {
                        BoundedLocalCache.this.onAccess(this.node);
                    } else if (BoundedLocalCache.this.accessOrderProbationDeque().remove(this.node)) {
                        BoundedLocalCache.this.accessOrderWindowDeque().addFirst(this.node);
                        BoundedLocalCache.this.setWindowWeightedSize(BoundedLocalCache.this.windowWeightedSize() + (long)this.node.getPolicyWeight());
                    }
                } else if (this.node.inMainProtected()) {
                    if ((long)this.node.getPolicyWeight() <= BoundedLocalCache.this.maximum()) {
                        BoundedLocalCache.this.onAccess(this.node);
                        BoundedLocalCache.this.setMainProtectedWeightedSize(BoundedLocalCache.this.mainProtectedWeightedSize() + (long)this.weightDifference);
                    } else if (BoundedLocalCache.this.accessOrderProtectedDeque().remove(this.node)) {
                        BoundedLocalCache.this.accessOrderWindowDeque().addFirst(this.node);
                        BoundedLocalCache.this.setWindowWeightedSize(BoundedLocalCache.this.windowWeightedSize() + (long)this.node.getPolicyWeight());
                        BoundedLocalCache.this.setMainProtectedWeightedSize(BoundedLocalCache.this.mainProtectedWeightedSize() - (long)oldWeightedSize);
                    } else {
                        BoundedLocalCache.this.setMainProtectedWeightedSize(BoundedLocalCache.this.mainProtectedWeightedSize() - (long)oldWeightedSize);
                    }
                }
                BoundedLocalCache.this.setWeightedSize(BoundedLocalCache.this.weightedSize() + (long)this.weightDifference);
            } else if (BoundedLocalCache.this.expiresAfterAccess()) {
                BoundedLocalCache.this.onAccess(this.node);
            }
            if (BoundedLocalCache.this.expiresAfterWrite()) {
                BoundedLocalCache.reorder(BoundedLocalCache.this.writeOrderDeque(), this.node);
            } else if (BoundedLocalCache.this.expiresVariable()) {
                BoundedLocalCache.this.timerWheel().reschedule(this.node);
            }
        }
    }

    final class RemovalTask
    implements Runnable {
        final Node<K, V> node;

        RemovalTask(Node<K, V> node) {
            this.node = node;
        }

        @Override
        @GuardedBy(value="evictionLock")
        public void run() {
            if (this.node.inWindow() && (BoundedLocalCache.this.evicts() || BoundedLocalCache.this.expiresAfterAccess())) {
                BoundedLocalCache.this.accessOrderWindowDeque().remove(this.node);
            } else if (BoundedLocalCache.this.evicts()) {
                if (this.node.inMainProbation()) {
                    BoundedLocalCache.this.accessOrderProbationDeque().remove(this.node);
                } else {
                    BoundedLocalCache.this.accessOrderProtectedDeque().remove(this.node);
                }
            }
            if (BoundedLocalCache.this.expiresAfterWrite()) {
                BoundedLocalCache.this.writeOrderDeque().remove(this.node);
            } else if (BoundedLocalCache.this.expiresVariable()) {
                BoundedLocalCache.this.timerWheel().deschedule(this.node);
            }
            BoundedLocalCache.this.makeDead(this.node);
        }
    }

    final class AddTask
    implements Runnable {
        final Node<K, V> node;
        final int weight;

        AddTask(Node<K, V> node, int weight) {
            this.weight = weight;
            this.node = node;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @GuardedBy(value="evictionLock")
        public void run() {
            boolean isAlive;
            if (BoundedLocalCache.this.evicts()) {
                Object key;
                long weightedSize = BoundedLocalCache.this.weightedSize();
                BoundedLocalCache.this.setWeightedSize(weightedSize + (long)this.weight);
                BoundedLocalCache.this.setWindowWeightedSize(BoundedLocalCache.this.windowWeightedSize() + (long)this.weight);
                this.node.setPolicyWeight(this.node.getPolicyWeight() + this.weight);
                long maximum = BoundedLocalCache.this.maximum();
                if (weightedSize >= maximum >>> 1) {
                    long capacity = BoundedLocalCache.this.isWeighted() ? BoundedLocalCache.this.data.mappingCount() : maximum;
                    BoundedLocalCache.this.frequencySketch().ensureCapacity(capacity);
                }
                if ((key = this.node.getKey()) != null) {
                    BoundedLocalCache.this.frequencySketch().increment(key);
                }
                BoundedLocalCache.this.setMissesInSample(BoundedLocalCache.this.missesInSample() + 1);
            }
            Node node = this.node;
            synchronized (node) {
                isAlive = this.node.isAlive();
            }
            if (isAlive) {
                if (BoundedLocalCache.this.expiresAfterWrite()) {
                    BoundedLocalCache.this.writeOrderDeque().add(this.node);
                }
                if (BoundedLocalCache.this.evicts() && (long)this.weight > BoundedLocalCache.this.windowMaximum()) {
                    BoundedLocalCache.this.accessOrderWindowDeque().offerFirst(this.node);
                } else if (BoundedLocalCache.this.evicts() || BoundedLocalCache.this.expiresAfterAccess()) {
                    BoundedLocalCache.this.accessOrderWindowDeque().offerLast(this.node);
                }
                if (BoundedLocalCache.this.expiresVariable()) {
                    BoundedLocalCache.this.timerWheel().schedule(this.node);
                }
            }
            if (BoundedLocalCache.this.isComputingAsync(this.node)) {
                node = this.node;
                synchronized (node) {
                    if (!Async.isReady((CompletableFuture)this.node.getValue())) {
                        long expirationTime = BoundedLocalCache.this.expirationTicker().read() + 0x5FFFFFFFFFFFFFFEL;
                        BoundedLocalCache.this.setVariableTime(this.node, expirationTime);
                        BoundedLocalCache.this.setAccessTime(this.node, expirationTime);
                        BoundedLocalCache.this.setWriteTime(this.node, expirationTime);
                    }
                }
            }
        }
    }
}


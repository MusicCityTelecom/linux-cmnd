/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.internal.guava.AbstractSequentialIterator;
import org.glassfish.jersey.internal.guava.Cache;
import org.glassfish.jersey.internal.guava.CacheBuilder;
import org.glassfish.jersey.internal.guava.CacheLoader;
import org.glassfish.jersey.internal.guava.Equivalence;
import org.glassfish.jersey.internal.guava.ExecutionError;
import org.glassfish.jersey.internal.guava.Futures;
import org.glassfish.jersey.internal.guava.Ints;
import org.glassfish.jersey.internal.guava.Iterators;
import org.glassfish.jersey.internal.guava.ListenableFuture;
import org.glassfish.jersey.internal.guava.LoadingCache;
import org.glassfish.jersey.internal.guava.MoreExecutors;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.guava.RemovalCause;
import org.glassfish.jersey.internal.guava.RemovalNotification;
import org.glassfish.jersey.internal.guava.SettableFuture;
import org.glassfish.jersey.internal.guava.Stopwatch;
import org.glassfish.jersey.internal.guava.Ticker;
import org.glassfish.jersey.internal.guava.UncheckedExecutionException;
import org.glassfish.jersey.internal.guava.Uninterruptibles;

class LocalCache<K, V>
extends AbstractMap<K, V>
implements ConcurrentMap<K, V> {
    private static final int MAXIMUM_CAPACITY = 0x40000000;
    private static final int MAX_SEGMENTS = 65536;
    private static final int CONTAINS_VALUE_RETRIES = 3;
    private static final int DRAIN_THRESHOLD = 63;
    private static final int DRAIN_MAX = 16;
    private static final Logger logger = Logger.getLogger(LocalCache.class.getName());
    private static final ValueReference<Object, Object> UNSET = new ValueReference<Object, Object>(){

        @Override
        public Object get() {
            return null;
        }

        @Override
        public int getWeight() {
            return 0;
        }

        @Override
        public ReferenceEntry<Object, Object> getEntry() {
            return null;
        }

        @Override
        public ValueReference<Object, Object> copyFor(ReferenceQueue<Object> queue, Object value, ReferenceEntry<Object, Object> entry) {
            return this;
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public Object waitForValue() {
            return null;
        }

        @Override
        public void notifyNewValue(Object newValue) {
        }
    };
    private static final Queue<?> DISCARDING_QUEUE = new AbstractQueue<Object>(){

        @Override
        public boolean offer(Object o) {
            return true;
        }

        @Override
        public Object peek() {
            return null;
        }

        @Override
        public Object poll() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Iterator<Object> iterator() {
            return Iterators.emptyIterator();
        }
    };
    private final int segmentMask;
    private final int segmentShift;
    private final Segment<K, V>[] segments;
    private final int concurrencyLevel;
    private final Equivalence<Object> keyEquivalence;
    private final Equivalence<Object> valueEquivalence;
    private final Strength keyStrength;
    private final Strength valueStrength;
    private final long maxWeight;
    private final long expireAfterAccessNanos;
    private final long expireAfterWriteNanos;
    private final long refreshNanos;
    private final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    private final Ticker ticker;
    private final EntryFactory entryFactory;
    private final CacheLoader<? super K, V> defaultLoader;
    private Set<K> keySet;
    private Collection<V> values;
    private Set<Map.Entry<K, V>> entrySet;

    private LocalCache(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader) {
        int segmentSize;
        int segmentCount;
        this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
        this.keyStrength = Strength.STRONG;
        this.valueStrength = Strength.STRONG;
        this.keyEquivalence = this.keyStrength.defaultEquivalence();
        this.valueEquivalence = this.valueStrength.defaultEquivalence();
        this.maxWeight = -1L;
        this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = 0L;
        this.refreshNanos = 0L;
        this.removalNotificationQueue = LocalCache.discardingQueue();
        this.ticker = this.recordsTime() ? Ticker.systemTicker() : CacheBuilder.NULL_TICKER;
        this.entryFactory = EntryFactory.getFactory(this.keyStrength, this.usesAccessEntries(), this.usesWriteEntries());
        this.defaultLoader = loader;
        int initialCapacity = Math.min(16, 0x40000000);
        if (this.evictsBySize()) {
            initialCapacity = Math.min(initialCapacity, (int)this.maxWeight);
        }
        int segmentShift = 0;
        for (segmentCount = 1; !(segmentCount >= this.concurrencyLevel || this.evictsBySize() && (long)(segmentCount * 20) > this.maxWeight); segmentCount <<= 1) {
            ++segmentShift;
        }
        this.segmentShift = 32 - segmentShift;
        this.segmentMask = segmentCount - 1;
        this.segments = this.newSegmentArray(segmentCount);
        int segmentCapacity = initialCapacity / segmentCount;
        if (segmentCapacity * segmentCount < initialCapacity) {
            ++segmentCapacity;
        }
        for (segmentSize = 1; segmentSize < segmentCapacity; segmentSize <<= 1) {
        }
        if (this.evictsBySize()) {
            long maxSegmentWeight = this.maxWeight / (long)segmentCount + 1L;
            long remainder = this.maxWeight % (long)segmentCount;
            for (int i = 0; i < this.segments.length; ++i) {
                if ((long)i == remainder) {
                    --maxSegmentWeight;
                }
                this.segments[i] = this.createSegment(segmentSize, maxSegmentWeight);
            }
        } else {
            for (int i = 0; i < this.segments.length; ++i) {
                this.segments[i] = this.createSegment(segmentSize, -1L);
            }
        }
    }

    private static <K, V> ValueReference<K, V> unset() {
        return UNSET;
    }

    private static <K, V> ReferenceEntry<K, V> nullEntry() {
        return NullEntry.INSTANCE;
    }

    private static <E> Queue<E> discardingQueue() {
        return DISCARDING_QUEUE;
    }

    private static int rehash(int h) {
        h += h << 15 ^ 0xFFFFCD7D;
        h ^= h >>> 10;
        h += h << 3;
        h ^= h >>> 6;
        h += (h << 2) + (h << 14);
        return h ^ h >>> 16;
    }

    private static <K, V> void connectAccessOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
        previous.setNextInAccessQueue(next);
        next.setPreviousInAccessQueue(previous);
    }

    private static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> nulled) {
        ReferenceEntry<K, V> nullEntry = LocalCache.nullEntry();
        nulled.setNextInAccessQueue(nullEntry);
        nulled.setPreviousInAccessQueue(nullEntry);
    }

    private static <K, V> void connectWriteOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
        previous.setNextInWriteQueue(next);
        next.setPreviousInWriteQueue(previous);
    }

    private static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> nulled) {
        ReferenceEntry<K, V> nullEntry = LocalCache.nullEntry();
        nulled.setNextInWriteQueue(nullEntry);
        nulled.setPreviousInWriteQueue(nullEntry);
    }

    boolean evictsBySize() {
        return this.maxWeight >= 0L;
    }

    private boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0L;
    }

    private boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0L;
    }

    boolean refreshes() {
        return this.refreshNanos > 0L;
    }

    boolean usesAccessQueue() {
        return this.expiresAfterAccess() || this.evictsBySize();
    }

    boolean usesWriteQueue() {
        return this.expiresAfterWrite();
    }

    boolean recordsWrite() {
        return this.expiresAfterWrite() || this.refreshes();
    }

    boolean recordsAccess() {
        return this.expiresAfterAccess();
    }

    private boolean recordsTime() {
        return this.recordsWrite() || this.recordsAccess();
    }

    private boolean usesWriteEntries() {
        return this.usesWriteQueue() || this.recordsWrite();
    }

    private boolean usesAccessEntries() {
        return this.usesAccessQueue() || this.recordsAccess();
    }

    boolean usesKeyReferences() {
        return this.keyStrength != Strength.STRONG;
    }

    boolean usesValueReferences() {
        return this.valueStrength != Strength.STRONG;
    }

    private int hash(Object key) {
        int h = this.keyEquivalence.hash(key);
        return LocalCache.rehash(h);
    }

    void reclaimValue(ValueReference<K, V> valueReference) {
        ReferenceEntry<K, V> entry = valueReference.getEntry();
        int hash = entry.getHash();
        this.segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }

    void reclaimKey(ReferenceEntry<K, V> entry) {
        int hash = entry.getHash();
        this.segmentFor(hash).reclaimKey(entry, hash);
    }

    private Segment<K, V> segmentFor(int hash) {
        return this.segments[hash >>> this.segmentShift & this.segmentMask];
    }

    private Segment<K, V> createSegment(int initialCapacity, long maxSegmentWeight) {
        return new Segment(this, initialCapacity, maxSegmentWeight);
    }

    private V getLiveValue(ReferenceEntry<K, V> entry, long now) {
        if (entry.getKey() == null) {
            return null;
        }
        V value = entry.getValueReference().get();
        if (value == null) {
            return null;
        }
        if (this.isExpired(entry, now)) {
            return null;
        }
        return value;
    }

    boolean isExpired(ReferenceEntry<K, V> entry, long now) {
        Preconditions.checkNotNull(entry);
        if (this.expiresAfterAccess() && now - entry.getAccessTime() >= this.expireAfterAccessNanos) {
            return true;
        }
        return this.expiresAfterWrite() && now - entry.getWriteTime() >= this.expireAfterWriteNanos;
    }

    private Segment<K, V>[] newSegmentArray(int ssize) {
        return new Segment[ssize];
    }

    @Override
    public boolean isEmpty() {
        int i;
        long sum = 0L;
        Segment<K, V>[] segments = this.segments;
        for (i = 0; i < segments.length; ++i) {
            if (segments[i].count != 0) {
                return false;
            }
            sum += (long)segments[i].modCount;
        }
        if (sum != 0L) {
            for (i = 0; i < segments.length; ++i) {
                if (segments[i].count != 0) {
                    return false;
                }
                sum -= (long)segments[i].modCount;
            }
            if (sum != 0L) {
                return false;
            }
        }
        return true;
    }

    private long longSize() {
        Segment<K, V>[] segments = this.segments;
        long sum = 0L;
        for (int i = 0; i < segments.length; ++i) {
            sum += (long)segments[i].count;
        }
        return sum;
    }

    @Override
    public int size() {
        return Ints.saturatedCast(this.longSize());
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            return null;
        }
        int hash = this.hash(key);
        return this.segmentFor(hash).get(key, hash);
    }

    V getIfPresent(Object key) {
        int hash = this.hash(Preconditions.checkNotNull(key));
        return this.segmentFor(hash).get(key, hash);
    }

    private V get(K key, CacheLoader<? super K, V> loader) throws ExecutionException {
        int hash = this.hash(Preconditions.checkNotNull(key));
        return this.segmentFor(hash).get((K)key, hash, loader);
    }

    V getOrLoad(K key) throws ExecutionException {
        return this.get(key, this.defaultLoader);
    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        int hash = this.hash(key);
        return this.segmentFor(hash).containsKey(key, hash);
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            return false;
        }
        long now = this.ticker.read();
        Segment<K, V>[] segments = this.segments;
        long last = -1L;
        for (int i = 0; i < 3; ++i) {
            long sum = 0L;
            for (Segment segment : segments) {
                int c = segment.count;
                AtomicReferenceArray table = segment.table;
                for (int j = 0; j < table.length(); ++j) {
                    for (ReferenceEntry e = table.get(j); e != null; e = e.getNext()) {
                        V v = segment.getLiveValue(e, now);
                        if (v == null || !this.valueEquivalence.equivalent(value, v)) continue;
                        return true;
                    }
                }
                sum += (long)segment.modCount;
            }
            if (sum == last) break;
            last = sum;
        }
        return false;
    }

    @Override
    public V put(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = this.hash(key);
        return this.segmentFor(hash).put(key, hash, value, false);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = this.hash(key);
        return this.segmentFor(hash).put(key, hash, value, true);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<K, V> e : m.entrySet()) {
            this.put(e.getKey(), e.getValue());
        }
    }

    @Override
    public V remove(Object key) {
        if (key == null) {
            return null;
        }
        int hash = this.hash(key);
        return this.segmentFor(hash).remove(key, hash);
    }

    @Override
    public boolean remove(Object key, Object value) {
        if (key == null || value == null) {
            return false;
        }
        int hash = this.hash(key);
        return this.segmentFor(hash).remove(key, hash, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(newValue);
        if (oldValue == null) {
            return false;
        }
        int hash = this.hash(key);
        return this.segmentFor(hash).replace(key, hash, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = this.hash(key);
        return this.segmentFor(hash).replace(key, hash, value);
    }

    @Override
    public void clear() {
        for (Segment<K, V> segment : this.segments) {
            segment.clear();
        }
    }

    @Override
    public Set<K> keySet() {
        KeySet ks = this.keySet;
        return ks != null ? ks : (this.keySet = new KeySet(this));
    }

    @Override
    public Collection<V> values() {
        Values vs = this.values;
        return vs != null ? vs : (this.values = new Values(this));
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet es = this.entrySet;
        return es != null ? es : (this.entrySet = new EntrySet(this));
    }

    static /* synthetic */ ReferenceEntry access$700() {
        return LocalCache.nullEntry();
    }

    final class EntrySet
    extends AbstractCacheSet<Map.Entry<K, V>> {
        EntrySet(ConcurrentMap<?, ?> map) {
            super(map);
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            Object key = e.getKey();
            if (key == null) {
                return false;
            }
            Object v = LocalCache.this.get(key);
            return v != null && LocalCache.this.valueEquivalence.equivalent(e.getValue(), v);
        }

        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry e = (Map.Entry)o;
            Object key = e.getKey();
            return key != null && LocalCache.this.remove(key, e.getValue());
        }
    }

    final class Values
    extends AbstractCollection<V> {
        private final ConcurrentMap<?, ?> map;

        Values(ConcurrentMap<?, ?> map) {
            this.map = map;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public void clear() {
            this.map.clear();
        }

        @Override
        public Iterator<V> iterator() {
            return new ValueIterator();
        }

        @Override
        public boolean contains(Object o) {
            return this.map.containsValue(o);
        }
    }

    final class KeySet
    extends AbstractCacheSet<K> {
        KeySet(ConcurrentMap<?, ?> map) {
            super(map);
        }

        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }

        @Override
        public boolean contains(Object o) {
            return this.map.containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            return this.map.remove(o) != null;
        }
    }

    abstract class AbstractCacheSet<T>
    extends AbstractSet<T> {
        final ConcurrentMap<?, ?> map;

        AbstractCacheSet(ConcurrentMap<?, ?> map) {
            this.map = map;
        }

        @Override
        public int size() {
            return this.map.size();
        }

        @Override
        public boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override
        public void clear() {
            this.map.clear();
        }
    }

    final class EntryIterator
    extends HashIterator<Map.Entry<K, V>> {
        EntryIterator() {
        }

        @Override
        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }

    final class WriteThroughEntry
    implements Map.Entry<K, V> {
        final K key;
        final V value;

        WriteThroughEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Map.Entry) {
                Map.Entry that = (Map.Entry)object;
                return this.key.equals(that.getKey()) && this.value.equals(that.getValue());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.key.hashCode() ^ this.value.hashCode();
        }

        @Override
        public V setValue(V newValue) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }

    final class ValueIterator
    extends HashIterator<V> {
        ValueIterator() {
        }

        @Override
        public V next() {
            return this.nextEntry().getValue();
        }
    }

    final class KeyIterator
    extends HashIterator<K> {
        KeyIterator() {
        }

        @Override
        public K next() {
            return this.nextEntry().getKey();
        }
    }

    abstract class HashIterator<T>
    implements Iterator<T> {
        int nextSegmentIndex;
        int nextTableIndex;
        Segment<K, V> currentSegment;
        AtomicReferenceArray<ReferenceEntry<K, V>> currentTable;
        ReferenceEntry<K, V> nextEntry;
        WriteThroughEntry nextExternal;
        WriteThroughEntry lastReturned;

        HashIterator() {
            this.nextSegmentIndex = LocalCache.this.segments.length - 1;
            this.nextTableIndex = -1;
            this.advance();
        }

        @Override
        public abstract T next();

        final void advance() {
            this.nextExternal = null;
            if (this.nextInChain()) {
                return;
            }
            if (this.nextInTable()) {
                return;
            }
            while (this.nextSegmentIndex >= 0) {
                this.currentSegment = LocalCache.this.segments[this.nextSegmentIndex--];
                if (this.currentSegment.count == 0) continue;
                this.currentTable = this.currentSegment.table;
                this.nextTableIndex = this.currentTable.length() - 1;
                if (!this.nextInTable()) continue;
                return;
            }
        }

        boolean nextInChain() {
            if (this.nextEntry != null) {
                this.nextEntry = this.nextEntry.getNext();
                while (this.nextEntry != null) {
                    if (this.advanceTo(this.nextEntry)) {
                        return true;
                    }
                    this.nextEntry = this.nextEntry.getNext();
                }
            }
            return false;
        }

        boolean nextInTable() {
            while (this.nextTableIndex >= 0) {
                if ((this.nextEntry = this.currentTable.get(this.nextTableIndex--)) == null || !this.advanceTo(this.nextEntry) && !this.nextInChain()) continue;
                return true;
            }
            return false;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean advanceTo(ReferenceEntry<K, V> entry) {
            try {
                long now = LocalCache.this.ticker.read();
                Object key = entry.getKey();
                Object value = LocalCache.this.getLiveValue(entry, now);
                if (value != null) {
                    this.nextExternal = new WriteThroughEntry(key, value);
                    boolean bl = true;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
            finally {
                this.currentSegment.postReadCleanup();
            }
        }

        @Override
        public boolean hasNext() {
            return this.nextExternal != null;
        }

        WriteThroughEntry nextEntry() {
            if (this.nextExternal == null) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.nextExternal;
            this.advance();
            return this.lastReturned;
        }

        @Override
        public void remove() {
            Preconditions.checkState(this.lastReturned != null);
            LocalCache.this.remove(this.lastReturned.getKey());
            this.lastReturned = null;
        }
    }

    static class LocalLoadingCache<K, V>
    extends LocalManualCache<K, V>
    implements LoadingCache<K, V> {
        private static final long serialVersionUID = 1L;

        LocalLoadingCache(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader) {
            super(new LocalCache(builder, Preconditions.checkNotNull(loader)));
        }

        @Override
        public V get(K key) throws ExecutionException {
            return this.localCache.getOrLoad(key);
        }

        public V getUnchecked(K key) {
            try {
                return this.get(key);
            }
            catch (ExecutionException e) {
                throw new UncheckedExecutionException(e.getCause());
            }
        }

        @Override
        public final V apply(K key) {
            return this.getUnchecked(key);
        }
    }

    static class LocalManualCache<K, V>
    implements Cache<K, V>,
    Serializable {
        private static final long serialVersionUID = 1L;
        final LocalCache<K, V> localCache;

        LocalManualCache(CacheBuilder<? super K, ? super V> builder) {
            this(new LocalCache(builder, null));
        }

        private LocalManualCache(LocalCache<K, V> localCache) {
            this.localCache = localCache;
        }

        @Override
        public V getIfPresent(Object key) {
            return this.localCache.getIfPresent(key);
        }

        @Override
        public void put(K key, V value) {
            this.localCache.put(key, value);
        }
    }

    static final class AccessQueue<K, V>
    extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(){
            ReferenceEntry<K, V> nextAccess = this;
            ReferenceEntry<K, V> previousAccess = this;

            @Override
            public long getAccessTime() {
                return Long.MAX_VALUE;
            }

            @Override
            public void setAccessTime(long time) {
            }

            @Override
            public ReferenceEntry<K, V> getNextInAccessQueue() {
                return this.nextAccess;
            }

            @Override
            public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
                this.nextAccess = next;
            }

            @Override
            public ReferenceEntry<K, V> getPreviousInAccessQueue() {
                return this.previousAccess;
            }

            @Override
            public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
                this.previousAccess = previous;
            }
        };

        AccessQueue() {
        }

        @Override
        public boolean offer(ReferenceEntry<K, V> entry) {
            LocalCache.connectAccessOrder(entry.getPreviousInAccessQueue(), entry.getNextInAccessQueue());
            LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), entry);
            LocalCache.connectAccessOrder(entry, this.head);
            return true;
        }

        @Override
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
            return next == this.head ? null : next;
        }

        @Override
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> next = this.head.getNextInAccessQueue();
            if (next == this.head) {
                return null;
            }
            this.remove(next);
            return next;
        }

        @Override
        public boolean remove(Object o) {
            ReferenceEntry e = (ReferenceEntry)o;
            ReferenceEntry previous = e.getPreviousInAccessQueue();
            ReferenceEntry next = e.getNextInAccessQueue();
            LocalCache.connectAccessOrder(previous, next);
            LocalCache.nullifyAccessOrder(e);
            return next != NullEntry.INSTANCE;
        }

        @Override
        public boolean contains(Object o) {
            ReferenceEntry e = (ReferenceEntry)o;
            return e.getNextInAccessQueue() != NullEntry.INSTANCE;
        }

        @Override
        public boolean isEmpty() {
            return this.head.getNextInAccessQueue() == this.head;
        }

        @Override
        public int size() {
            int size = 0;
            for (ReferenceEntry<K, V> e = this.head.getNextInAccessQueue(); e != this.head; e = e.getNextInAccessQueue()) {
                ++size;
            }
            return size;
        }

        @Override
        public void clear() {
            ReferenceEntry<K, V> e = this.head.getNextInAccessQueue();
            while (e != this.head) {
                ReferenceEntry<K, V> next = e.getNextInAccessQueue();
                LocalCache.nullifyAccessOrder(e);
                e = next;
            }
            this.head.setNextInAccessQueue(this.head);
            this.head.setPreviousInAccessQueue(this.head);
        }

        @Override
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>((ReferenceEntry)this.peek()){

                @Override
                protected ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> previous) {
                    ReferenceEntry next = previous.getNextInAccessQueue();
                    return next == head ? null : next;
                }
            };
        }
    }

    static final class WriteQueue<K, V>
    extends AbstractQueue<ReferenceEntry<K, V>> {
        final ReferenceEntry<K, V> head = new AbstractReferenceEntry<K, V>(){
            ReferenceEntry<K, V> nextWrite = this;
            ReferenceEntry<K, V> previousWrite = this;

            @Override
            public long getWriteTime() {
                return Long.MAX_VALUE;
            }

            @Override
            public void setWriteTime(long time) {
            }

            @Override
            public ReferenceEntry<K, V> getNextInWriteQueue() {
                return this.nextWrite;
            }

            @Override
            public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
                this.nextWrite = next;
            }

            @Override
            public ReferenceEntry<K, V> getPreviousInWriteQueue() {
                return this.previousWrite;
            }

            @Override
            public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
                this.previousWrite = previous;
            }
        };

        WriteQueue() {
        }

        @Override
        public boolean offer(ReferenceEntry<K, V> entry) {
            LocalCache.connectWriteOrder(entry.getPreviousInWriteQueue(), entry.getNextInWriteQueue());
            LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), entry);
            LocalCache.connectWriteOrder(entry, this.head);
            return true;
        }

        @Override
        public ReferenceEntry<K, V> peek() {
            ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
            return next == this.head ? null : next;
        }

        @Override
        public ReferenceEntry<K, V> poll() {
            ReferenceEntry<K, V> next = this.head.getNextInWriteQueue();
            if (next == this.head) {
                return null;
            }
            this.remove(next);
            return next;
        }

        @Override
        public boolean remove(Object o) {
            ReferenceEntry e = (ReferenceEntry)o;
            ReferenceEntry previous = e.getPreviousInWriteQueue();
            ReferenceEntry next = e.getNextInWriteQueue();
            LocalCache.connectWriteOrder(previous, next);
            LocalCache.nullifyWriteOrder(e);
            return next != NullEntry.INSTANCE;
        }

        @Override
        public boolean contains(Object o) {
            ReferenceEntry e = (ReferenceEntry)o;
            return e.getNextInWriteQueue() != NullEntry.INSTANCE;
        }

        @Override
        public boolean isEmpty() {
            return this.head.getNextInWriteQueue() == this.head;
        }

        @Override
        public int size() {
            int size = 0;
            for (ReferenceEntry<K, V> e = this.head.getNextInWriteQueue(); e != this.head; e = e.getNextInWriteQueue()) {
                ++size;
            }
            return size;
        }

        @Override
        public void clear() {
            ReferenceEntry<K, V> e = this.head.getNextInWriteQueue();
            while (e != this.head) {
                ReferenceEntry<K, V> next = e.getNextInWriteQueue();
                LocalCache.nullifyWriteOrder(e);
                e = next;
            }
            this.head.setNextInWriteQueue(this.head);
            this.head.setPreviousInWriteQueue(this.head);
        }

        @Override
        public Iterator<ReferenceEntry<K, V>> iterator() {
            return new AbstractSequentialIterator<ReferenceEntry<K, V>>((ReferenceEntry)this.peek()){

                @Override
                protected ReferenceEntry<K, V> computeNext(ReferenceEntry<K, V> previous) {
                    ReferenceEntry next = previous.getNextInWriteQueue();
                    return next == head ? null : next;
                }
            };
        }
    }

    static class LoadingValueReference<K, V>
    implements ValueReference<K, V> {
        final SettableFuture<V> futureValue = SettableFuture.create();
        final Stopwatch stopwatch = Stopwatch.createUnstarted();
        volatile ValueReference<K, V> oldValue;

        public LoadingValueReference() {
            this(LocalCache.unset());
        }

        public LoadingValueReference(ValueReference<K, V> oldValue) {
            this.oldValue = oldValue;
        }

        @Override
        public boolean isLoading() {
            return true;
        }

        @Override
        public boolean isActive() {
            return this.oldValue.isActive();
        }

        @Override
        public int getWeight() {
            return this.oldValue.getWeight();
        }

        public boolean set(V newValue) {
            return this.futureValue.set(newValue);
        }

        public boolean setException(Throwable t) {
            return this.futureValue.setException(t);
        }

        private ListenableFuture<V> fullyFailedFuture(Throwable t) {
            return Futures.immediateFailedFuture(t);
        }

        @Override
        public void notifyNewValue(V newValue) {
            if (newValue != null) {
                this.set(newValue);
            } else {
                this.oldValue = LocalCache.unset();
            }
        }

        public ListenableFuture<V> loadFuture(K key, CacheLoader<? super K, V> loader) {
            this.stopwatch.start();
            V previousValue = this.oldValue.get();
            try {
                if (previousValue == null) {
                    V newValue = loader.load(key);
                    return this.set(newValue) ? this.futureValue : Futures.immediateFuture(newValue);
                }
                ListenableFuture<V> newValue = loader.reload(key, previousValue);
                if (newValue == null) {
                    return Futures.immediateFuture(null);
                }
                return Futures.transform(newValue, new Function<V, V>(){

                    @Override
                    public V apply(V newValue) {
                        this.set(newValue);
                        return newValue;
                    }
                });
            }
            catch (Throwable t) {
                if (t instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }
                return this.setException(t) ? this.futureValue : this.fullyFailedFuture(t);
            }
        }

        @Override
        public V waitForValue() throws ExecutionException {
            return Uninterruptibles.getUninterruptibly(this.futureValue);
        }

        @Override
        public V get() {
            return this.oldValue.get();
        }

        public ValueReference<K, V> getOldValue() {
            return this.oldValue;
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry) {
            return this;
        }
    }

    static class Segment<K, V>
    extends ReentrantLock {
        final LocalCache<K, V> map;
        final long maxSegmentWeight;
        final ReferenceQueue<K> keyReferenceQueue;
        final ReferenceQueue<V> valueReferenceQueue;
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<ReferenceEntry<K, V>> writeQueue;
        final Queue<ReferenceEntry<K, V>> accessQueue;
        volatile int count;
        long totalWeight;
        int modCount;
        int threshold;
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;

        Segment(LocalCache<K, V> map, int initialCapacity, long maxSegmentWeight) {
            this.map = map;
            this.maxSegmentWeight = maxSegmentWeight;
            this.initTable(this.newEntryArray(initialCapacity));
            this.keyReferenceQueue = map.usesKeyReferences() ? new ReferenceQueue() : null;
            this.valueReferenceQueue = map.usesValueReferences() ? new ReferenceQueue() : null;
            this.recencyQueue = map.usesAccessQueue() ? new ConcurrentLinkedQueue() : LocalCache.discardingQueue();
            this.writeQueue = map.usesWriteQueue() ? new WriteQueue() : LocalCache.discardingQueue();
            this.accessQueue = map.usesAccessQueue() ? new AccessQueue() : LocalCache.discardingQueue();
        }

        AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int size) {
            return new AtomicReferenceArray<ReferenceEntry<K, V>>(size);
        }

        void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> newTable) {
            this.threshold = newTable.length() * 3 / 4;
            if ((long)this.threshold == this.maxSegmentWeight) {
                ++this.threshold;
            }
            this.table = newTable;
        }

        ReferenceEntry<K, V> newEntry(K key, int hash, ReferenceEntry<K, V> next) {
            return ((LocalCache)this.map).entryFactory.newEntry(this, Preconditions.checkNotNull(key), hash, next);
        }

        ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
            if (original.getKey() == null) {
                return null;
            }
            ValueReference<K, V> valueReference = original.getValueReference();
            V value = valueReference.get();
            if (value == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> newEntry = ((LocalCache)this.map).entryFactory.copyEntry(this, original, newNext);
            newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
            return newEntry;
        }

        void setValue(ReferenceEntry<K, V> entry, K key, V value, long now) {
            ValueReference<K, V> previous = entry.getValueReference();
            int weight = 1;
            Preconditions.checkState(weight >= 0, "Weights must be non-negative");
            ValueReference<K, V> valueReference = ((LocalCache)this.map).valueStrength.referenceValue(this, entry, value, weight);
            entry.setValueReference(valueReference);
            this.recordWrite(entry, weight, now);
            previous.notifyNewValue(value);
        }

        V get(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(loader);
            try {
                ReferenceEntry<K, V> e;
                if (this.count != 0 && (e = this.getEntry(key, hash)) != null) {
                    long now = ((LocalCache)this.map).ticker.read();
                    V value = this.getLiveValue(e, now);
                    if (value != null) {
                        this.recordRead(e, now);
                        V v = this.scheduleRefresh(e, key, hash, value, now, loader);
                        return v;
                    }
                    ValueReference<K, V> valueReference = e.getValueReference();
                    if (valueReference.isLoading()) {
                        V v = this.waitForLoadingValue(e, key, valueReference);
                        return v;
                    }
                }
                e = this.lockedGetOrLoad(key, hash, loader);
                return (V)e;
            }
            catch (ExecutionException ee) {
                Throwable cause = ee.getCause();
                if (cause instanceof Error) {
                    throw new ExecutionError((Error)cause);
                }
                if (cause instanceof RuntimeException) {
                    throw new UncheckedExecutionException(cause);
                }
                throw ee;
            }
            finally {
                this.postReadCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V lockedGetOrLoad(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
            ReferenceEntry e;
            ValueReference<K, V> valueReference = null;
            LoadingValueReference loadingValueReference = null;
            boolean createNewEntry = true;
            this.lock();
            try {
                ReferenceEntry first;
                long now = ((LocalCache)this.map).ticker.read();
                this.preWriteCleanup(now);
                int newCount = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (e = first = table.get(index); e != null; e = e.getNext()) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    valueReference = e.getValueReference();
                    if (valueReference.isLoading()) {
                        createNewEntry = false;
                        break;
                    }
                    V value = valueReference.get();
                    if (value == null) {
                        this.enqueueNotification(entryKey, hash, valueReference, RemovalCause.COLLECTED);
                    } else if (this.map.isExpired(e, now)) {
                        this.enqueueNotification(entryKey, hash, valueReference, RemovalCause.EXPIRED);
                    } else {
                        this.recordLockedRead(e, now);
                        V v = value;
                        return v;
                    }
                    this.writeQueue.remove(e);
                    this.accessQueue.remove(e);
                    this.count = newCount;
                    break;
                }
                if (createNewEntry) {
                    loadingValueReference = new LoadingValueReference();
                    if (e == null) {
                        e = this.newEntry(key, hash, first);
                        e.setValueReference(loadingValueReference);
                        table.set(index, e);
                    } else {
                        e.setValueReference(loadingValueReference);
                    }
                }
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
            if (createNewEntry) {
                ReferenceEntry referenceEntry = e;
                synchronized (referenceEntry) {
                    return this.loadSync(key, hash, loadingValueReference, loader);
                }
            }
            return this.waitForLoadingValue(e, key, valueReference);
        }

        V waitForLoadingValue(ReferenceEntry<K, V> e, K key, ValueReference<K, V> valueReference) throws ExecutionException {
            if (!valueReference.isLoading()) {
                throw new AssertionError();
            }
            Preconditions.checkState(!Thread.holdsLock(e), "Recursive load of: %s", key);
            V value = valueReference.waitForValue();
            if (value == null) {
                throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
            }
            long now = ((LocalCache)this.map).ticker.read();
            this.recordRead(e, now);
            return value;
        }

        V loadSync(K key, int hash, LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) throws ExecutionException {
            ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture((K)key, loader);
            return this.getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
        }

        ListenableFuture<V> loadAsync(final K key, final int hash, final LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) {
            final ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture((K)key, loader);
            loadingFuture.addListener(new Runnable(){

                @Override
                public void run() {
                    try {
                        Object v = this.getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
                    }
                    catch (Throwable t) {
                        logger.log(Level.WARNING, "Exception thrown during refresh", t);
                        loadingValueReference.setException(t);
                    }
                }
            }, MoreExecutors.directExecutor());
            return loadingFuture;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V getAndRecordStats(K key, int hash, LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> newValue) throws ExecutionException {
            V value = null;
            try {
                value = Uninterruptibles.getUninterruptibly(newValue);
                if (value == null) {
                    throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
                }
                this.storeLoadedValue(key, hash, loadingValueReference, value);
                V v = value;
                return v;
            }
            finally {
                if (value == null) {
                    this.removeLoadingValue(key, hash, loadingValueReference);
                }
            }
        }

        V scheduleRefresh(ReferenceEntry<K, V> entry, K key, int hash, V oldValue, long now, CacheLoader<? super K, V> loader) {
            V newValue;
            if (this.map.refreshes() && now - entry.getWriteTime() > ((LocalCache)this.map).refreshNanos && !entry.getValueReference().isLoading() && (newValue = this.refresh(key, hash, loader, true)) != null) {
                return newValue;
            }
            return oldValue;
        }

        V refresh(K key, int hash, CacheLoader<? super K, V> loader, boolean checkTime) {
            LoadingValueReference<K, V> loadingValueReference = this.insertLoadingValueReference(key, hash, checkTime);
            if (loadingValueReference == null) {
                return null;
            }
            ListenableFuture<V> result = this.loadAsync(key, hash, loadingValueReference, loader);
            if (result.isDone()) {
                try {
                    return Uninterruptibles.getUninterruptibly(result);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            return null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        LoadingValueReference<K, V> insertLoadingValueReference(K key, int hash, boolean checkTime) {
            ReferenceEntry e = null;
            this.lock();
            try {
                ReferenceEntry first;
                long now = ((LocalCache)this.map).ticker.read();
                this.preWriteCleanup(now);
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (e = first = table.get(index); e != null; e = e.getNext()) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> valueReference = e.getValueReference();
                    if (valueReference.isLoading() || checkTime && now - e.getWriteTime() < ((LocalCache)this.map).refreshNanos) {
                        LoadingValueReference<K, V> loadingValueReference = null;
                        return loadingValueReference;
                    }
                    ++this.modCount;
                    LoadingValueReference<K, V> loadingValueReference = new LoadingValueReference<K, V>(valueReference);
                    e.setValueReference(loadingValueReference);
                    LoadingValueReference<K, V> loadingValueReference2 = loadingValueReference;
                    return loadingValueReference2;
                }
                ++this.modCount;
                LoadingValueReference loadingValueReference = new LoadingValueReference();
                e = this.newEntry(key, hash, first);
                e.setValueReference(loadingValueReference);
                table.set(index, e);
                LoadingValueReference loadingValueReference3 = loadingValueReference;
                return loadingValueReference3;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        void tryDrainReferenceQueues() {
            if (this.tryLock()) {
                try {
                    this.drainReferenceQueues();
                }
                finally {
                    this.unlock();
                }
            }
        }

        void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                this.drainValueReferenceQueue();
            }
        }

        void drainKeyReferenceQueue() {
            Reference<K> ref;
            int i = 0;
            while ((ref = this.keyReferenceQueue.poll()) != null) {
                ReferenceEntry entry = (ReferenceEntry)((Object)ref);
                this.map.reclaimKey(entry);
                if (++i != 16) continue;
                break;
            }
        }

        void drainValueReferenceQueue() {
            Reference<V> ref;
            int i = 0;
            while ((ref = this.valueReferenceQueue.poll()) != null) {
                ValueReference valueReference = (ValueReference)((Object)ref);
                this.map.reclaimValue(valueReference);
                if (++i != 16) continue;
                break;
            }
        }

        void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                this.clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                this.clearValueReferenceQueue();
            }
        }

        void clearKeyReferenceQueue() {
            while (this.keyReferenceQueue.poll() != null) {
            }
        }

        void clearValueReferenceQueue() {
            while (this.valueReferenceQueue.poll() != null) {
            }
        }

        void recordRead(ReferenceEntry<K, V> entry, long now) {
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            this.recencyQueue.add(entry);
        }

        void recordLockedRead(ReferenceEntry<K, V> entry, long now) {
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            this.accessQueue.add(entry);
        }

        void recordWrite(ReferenceEntry<K, V> entry, int weight, long now) {
            this.drainRecencyQueue();
            this.totalWeight += (long)weight;
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            if (this.map.recordsWrite()) {
                entry.setWriteTime(now);
            }
            this.accessQueue.add(entry);
            this.writeQueue.add(entry);
        }

        void drainRecencyQueue() {
            ReferenceEntry<K, V> e;
            while ((e = this.recencyQueue.poll()) != null) {
                if (!this.accessQueue.contains(e)) continue;
                this.accessQueue.add(e);
            }
        }

        void tryExpireEntries(long now) {
            if (this.tryLock()) {
                try {
                    this.expireEntries(now);
                }
                finally {
                    this.unlock();
                }
            }
        }

        void expireEntries(long now) {
            ReferenceEntry<K, V> e;
            this.drainRecencyQueue();
            while ((e = this.writeQueue.peek()) != null && this.map.isExpired(e, now)) {
                if (!this.removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
                    throw new AssertionError();
                }
            }
            while ((e = this.accessQueue.peek()) != null && this.map.isExpired(e, now)) {
                if (!this.removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
                    throw new AssertionError();
                }
            }
        }

        void enqueueNotification(ReferenceEntry<K, V> entry, RemovalCause cause) {
            this.enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference(), cause);
        }

        void enqueueNotification(K key, int hash, ValueReference<K, V> valueReference, RemovalCause cause) {
            this.totalWeight -= (long)valueReference.getWeight();
            if (((LocalCache)this.map).removalNotificationQueue != DISCARDING_QUEUE) {
                V value = valueReference.get();
                RemovalNotification<K, V> notification = new RemovalNotification<K, V>(key, value, cause);
                ((LocalCache)this.map).removalNotificationQueue.offer(notification);
            }
        }

        void evictEntries() {
            if (!this.map.evictsBySize()) {
                return;
            }
            this.drainRecencyQueue();
            while (this.totalWeight > this.maxSegmentWeight) {
                ReferenceEntry<K, V> e = this.getNextEvictable();
                if (!this.removeEntry(e, e.getHash(), RemovalCause.SIZE)) {
                    throw new AssertionError();
                }
            }
        }

        ReferenceEntry<K, V> getNextEvictable() {
            for (ReferenceEntry referenceEntry : this.accessQueue) {
                int weight = referenceEntry.getValueReference().getWeight();
                if (weight <= 0) continue;
                return referenceEntry;
            }
            throw new AssertionError();
        }

        ReferenceEntry<K, V> getFirst(int hash) {
            AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
            return table.get(hash & table.length() - 1);
        }

        ReferenceEntry<K, V> getEntry(Object key, int hash) {
            for (ReferenceEntry<K, V> e = this.getFirst(hash); e != null; e = e.getNext()) {
                if (e.getHash() != hash) continue;
                K entryKey = e.getKey();
                if (entryKey == null) {
                    this.tryDrainReferenceQueues();
                    continue;
                }
                if (!((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                return e;
            }
            return null;
        }

        ReferenceEntry<K, V> getLiveEntry(Object key, int hash, long now) {
            ReferenceEntry<K, V> e = this.getEntry(key, hash);
            if (e == null) {
                return null;
            }
            if (this.map.isExpired(e, now)) {
                this.tryExpireEntries(now);
                return null;
            }
            return e;
        }

        V getLiveValue(ReferenceEntry<K, V> entry, long now) {
            if (entry.getKey() == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            V value = entry.getValueReference().get();
            if (value == null) {
                this.tryDrainReferenceQueues();
                return null;
            }
            if (this.map.isExpired(entry, now)) {
                this.tryExpireEntries(now);
                return null;
            }
            return value;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V get(Object key, int hash) {
            try {
                if (this.count != 0) {
                    long now = ((LocalCache)this.map).ticker.read();
                    ReferenceEntry<K, V> e = this.getLiveEntry(key, hash, now);
                    if (e == null) {
                        V v = null;
                        return v;
                    }
                    V value = e.getValueReference().get();
                    if (value != null) {
                        this.recordRead(e, now);
                        V v = this.scheduleRefresh(e, e.getKey(), hash, value, now, ((LocalCache)this.map).defaultLoader);
                        return v;
                    }
                    this.tryDrainReferenceQueues();
                }
                V v = null;
                return v;
            }
            finally {
                this.postReadCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean containsKey(Object key, int hash) {
            try {
                if (this.count != 0) {
                    long now = ((LocalCache)this.map).ticker.read();
                    ReferenceEntry<K, V> e = this.getLiveEntry(key, hash, now);
                    if (e == null) {
                        boolean bl = false;
                        return bl;
                    }
                    boolean bl = e.getValueReference().get() != null;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
            finally {
                this.postReadCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V put(K key, int hash, V value, boolean onlyIfAbsent) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                long now = ((LocalCache)this.map).ticker.read();
                this.preWriteCleanup(now);
                int newCount = this.count + 1;
                if (newCount > this.threshold) {
                    this.expand();
                    newCount = this.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> valueReference = e.getValueReference();
                    V entryValue = valueReference.get();
                    if (entryValue == null) {
                        ++this.modCount;
                        if (valueReference.isActive()) {
                            this.enqueueNotification(key, hash, valueReference, RemovalCause.COLLECTED);
                            this.setValue(e, key, value, now);
                            newCount = this.count;
                        } else {
                            this.setValue(e, key, value, now);
                            newCount = this.count + 1;
                        }
                        this.count = newCount;
                        this.evictEntries();
                        V v = null;
                        return v;
                    }
                    if (onlyIfAbsent) {
                        this.recordLockedRead(e, now);
                        V v = entryValue;
                        return v;
                    }
                    ++this.modCount;
                    this.enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                    this.setValue(e, key, value, now);
                    this.evictEntries();
                    V v = entryValue;
                    return v;
                }
                ++this.modCount;
                ReferenceEntry<K, V> newEntry = this.newEntry(key, hash, first);
                this.setValue(newEntry, key, value, now);
                table.set(index, newEntry);
                this.count = newCount = this.count + 1;
                this.evictEntries();
                V v = null;
                return v;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> oldTable = this.table;
            int oldCapacity = oldTable.length();
            if (oldCapacity >= 0x40000000) {
                return;
            }
            int newCount = this.count;
            AtomicReferenceArray<ReferenceEntry<K, V>> newTable = this.newEntryArray(oldCapacity << 1);
            this.threshold = newTable.length() * 3 / 4;
            int newMask = newTable.length() - 1;
            for (int oldIndex = 0; oldIndex < oldCapacity; ++oldIndex) {
                int newIndex;
                ReferenceEntry<K, V> e;
                ReferenceEntry<K, V> head = oldTable.get(oldIndex);
                if (head == null) continue;
                ReferenceEntry<K, V> next = head.getNext();
                int headIndex = head.getHash() & newMask;
                if (next == null) {
                    newTable.set(headIndex, head);
                    continue;
                }
                ReferenceEntry<K, V> tail = head;
                int tailIndex = headIndex;
                for (e = next; e != null; e = e.getNext()) {
                    newIndex = e.getHash() & newMask;
                    if (newIndex == tailIndex) continue;
                    tailIndex = newIndex;
                    tail = e;
                }
                newTable.set(tailIndex, tail);
                for (e = head; e != tail; e = e.getNext()) {
                    newIndex = e.getHash() & newMask;
                    ReferenceEntry<K, V> newNext = newTable.get(newIndex);
                    ReferenceEntry<K, V> newFirst = this.copyEntry(e, newNext);
                    if (newFirst != null) {
                        newTable.set(newIndex, newFirst);
                        continue;
                    }
                    this.removeCollectedEntry(e);
                    --newCount;
                }
            }
            this.table = newTable;
            this.count = newCount;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean replace(K key, int hash, V oldValue, V newValue) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                long now = ((LocalCache)this.map).ticker.read();
                this.preWriteCleanup(now);
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> valueReference = e.getValueReference();
                    V entryValue = valueReference.get();
                    if (entryValue == null) {
                        if (valueReference.isActive()) {
                            int newCount = this.count - 1;
                            ++this.modCount;
                            ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
                            newCount = this.count - 1;
                            table.set(index, newFirst);
                            this.count = newCount;
                        }
                        boolean bl = false;
                        return bl;
                    }
                    if (((LocalCache)this.map).valueEquivalence.equivalent(oldValue, entryValue)) {
                        ++this.modCount;
                        this.enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                        this.setValue(e, key, newValue, now);
                        this.evictEntries();
                        boolean bl = true;
                        return bl;
                    }
                    this.recordLockedRead(e, now);
                    boolean bl = false;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V replace(K key, int hash, V newValue) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                long now = ((LocalCache)this.map).ticker.read();
                this.preWriteCleanup(now);
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> valueReference = e.getValueReference();
                    V entryValue = valueReference.get();
                    if (entryValue == null) {
                        if (valueReference.isActive()) {
                            int newCount = this.count - 1;
                            ++this.modCount;
                            ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
                            newCount = this.count - 1;
                            table.set(index, newFirst);
                            this.count = newCount;
                        }
                        V v = null;
                        return v;
                    }
                    ++this.modCount;
                    this.enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                    this.setValue(e, key, newValue, now);
                    this.evictEntries();
                    V v = entryValue;
                    return v;
                }
                V v = null;
                return v;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        V remove(Object key, int hash) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                long now = ((LocalCache)this.map).ticker.read();
                this.preWriteCleanup(now);
                int newCount = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    RemovalCause cause;
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> valueReference = e.getValueReference();
                    V entryValue = valueReference.get();
                    if (entryValue != null) {
                        cause = RemovalCause.EXPLICIT;
                    } else if (valueReference.isActive()) {
                        cause = RemovalCause.COLLECTED;
                    } else {
                        V v = null;
                        return v;
                    }
                    ++this.modCount;
                    ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, cause);
                    newCount = this.count - 1;
                    table.set(index, newFirst);
                    this.count = newCount;
                    V v = entryValue;
                    return v;
                }
                V v = null;
                return v;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean storeLoadedValue(K key, int hash, LoadingValueReference<K, V> oldValueReference, V newValue) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                long now = ((LocalCache)this.map).ticker.read();
                this.preWriteCleanup(now);
                int newCount = this.count + 1;
                if (newCount > this.threshold) {
                    this.expand();
                    newCount = this.count + 1;
                }
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> valueReference = e.getValueReference();
                    V entryValue = valueReference.get();
                    if (oldValueReference == valueReference || entryValue == null && valueReference != UNSET) {
                        ++this.modCount;
                        if (oldValueReference.isActive()) {
                            RemovalCause cause = entryValue == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED;
                            this.enqueueNotification(key, hash, oldValueReference, cause);
                            --newCount;
                        }
                        this.setValue(e, key, newValue, now);
                        this.count = newCount;
                        this.evictEntries();
                        boolean bl = true;
                        return bl;
                    }
                    valueReference = new WeightedStrongValueReference(newValue, 0);
                    this.enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
                    boolean bl = false;
                    return bl;
                }
                ++this.modCount;
                ReferenceEntry<K, V> newEntry = this.newEntry(key, hash, first);
                this.setValue(newEntry, key, newValue, now);
                table.set(index, newEntry);
                this.count = newCount;
                this.evictEntries();
                boolean bl = true;
                return bl;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean remove(Object key, int hash, Object value) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                long now = ((LocalCache)this.map).ticker.read();
                this.preWriteCleanup(now);
                int newCount = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    RemovalCause cause;
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> valueReference = e.getValueReference();
                    V entryValue = valueReference.get();
                    if (((LocalCache)this.map).valueEquivalence.equivalent(value, entryValue)) {
                        cause = RemovalCause.EXPLICIT;
                    } else if (entryValue == null && valueReference.isActive()) {
                        cause = RemovalCause.COLLECTED;
                    } else {
                        boolean bl = false;
                        return bl;
                    }
                    ++this.modCount;
                    ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, cause);
                    newCount = this.count - 1;
                    table.set(index, newFirst);
                    this.count = newCount;
                    boolean bl = cause == RemovalCause.EXPLICIT;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void clear() {
            if (this.count != 0) {
                this.lock();
                try {
                    int i;
                    AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                    for (i = 0; i < table.length(); ++i) {
                        for (ReferenceEntry<K, V> e = table.get(i); e != null; e = e.getNext()) {
                            if (!e.getValueReference().isActive()) continue;
                            this.enqueueNotification(e, RemovalCause.EXPLICIT);
                        }
                    }
                    for (i = 0; i < table.length(); ++i) {
                        table.set(i, null);
                    }
                    this.clearReferenceQueues();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    ++this.modCount;
                    this.count = 0;
                }
                finally {
                    this.unlock();
                    this.postWriteCleanup();
                }
            }
        }

        ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> first, ReferenceEntry<K, V> entry, K key, int hash, ValueReference<K, V> valueReference, RemovalCause cause) {
            this.enqueueNotification(key, hash, valueReference, cause);
            this.writeQueue.remove(entry);
            this.accessQueue.remove(entry);
            if (valueReference.isLoading()) {
                valueReference.notifyNewValue(null);
                return first;
            }
            return this.removeEntryFromChain(first, entry);
        }

        ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> first, ReferenceEntry<K, V> entry) {
            int newCount = this.count;
            ReferenceEntry<K, V> newFirst = entry.getNext();
            for (ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
                ReferenceEntry<K, V> next = this.copyEntry(e, newFirst);
                if (next != null) {
                    newFirst = next;
                    continue;
                }
                this.removeCollectedEntry(e);
                --newCount;
            }
            this.count = newCount;
            return newFirst;
        }

        void removeCollectedEntry(ReferenceEntry<K, V> entry) {
            this.enqueueNotification(entry, RemovalCause.COLLECTED);
            this.writeQueue.remove(entry);
            this.accessQueue.remove(entry);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean reclaimKey(ReferenceEntry<K, V> entry, int hash) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                int newCount = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    if (e != entry) continue;
                    ++this.modCount;
                    ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference(), RemovalCause.COLLECTED);
                    newCount = this.count - 1;
                    table.set(index, newFirst);
                    this.count = newCount;
                    boolean bl = true;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean reclaimValue(K key, int hash, ValueReference<K, V> valueReference) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                int newCount = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> v = e.getValueReference();
                    if (v == valueReference) {
                        ++this.modCount;
                        ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
                        newCount = this.count - 1;
                        table.set(index, newFirst);
                        this.count = newCount;
                        boolean bl = true;
                        return bl;
                    }
                    boolean bl = false;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
            finally {
                this.unlock();
                if (!this.isHeldByCurrentThread()) {
                    this.postWriteCleanup();
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        boolean removeLoadingValue(K key, int hash, LoadingValueReference<K, V> valueReference) {
            this.lock();
            try {
                ReferenceEntry<K, V> first;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = hash & table.length() - 1;
                for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !((LocalCache)this.map).keyEquivalence.equivalent(key, entryKey)) continue;
                    ValueReference<K, V> v = e.getValueReference();
                    if (v == valueReference) {
                        if (valueReference.isActive()) {
                            e.setValueReference(valueReference.getOldValue());
                        } else {
                            ReferenceEntry<K, V> newFirst = this.removeEntryFromChain(first, e);
                            table.set(index, newFirst);
                        }
                        boolean bl = true;
                        return bl;
                    }
                    boolean bl = false;
                    return bl;
                }
                boolean bl = false;
                return bl;
            }
            finally {
                this.unlock();
                this.postWriteCleanup();
            }
        }

        boolean removeEntry(ReferenceEntry<K, V> entry, int hash, RemovalCause cause) {
            ReferenceEntry<K, V> first;
            int newCount = this.count - 1;
            AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
            int index = hash & table.length() - 1;
            for (ReferenceEntry<K, V> e = first = table.get(index); e != null; e = e.getNext()) {
                if (e != entry) continue;
                ++this.modCount;
                ReferenceEntry<K, V> newFirst = this.removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference(), cause);
                newCount = this.count - 1;
                table.set(index, newFirst);
                this.count = newCount;
                return true;
            }
            return false;
        }

        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 0x3F) == 0) {
                this.cleanUp();
            }
        }

        void preWriteCleanup(long now) {
            this.runLockedCleanup(now);
        }

        void postWriteCleanup() {
        }

        void cleanUp() {
            long now = ((LocalCache)this.map).ticker.read();
            this.runLockedCleanup(now);
        }

        void runLockedCleanup(long now) {
            if (this.tryLock()) {
                try {
                    this.drainReferenceQueues();
                    this.expireEntries(now);
                    this.readCount.set(0);
                }
                finally {
                    this.unlock();
                }
            }
        }
    }

    static final class WeightedStrongValueReference<K, V>
    extends StrongValueReference<K, V> {
        final int weight;

        WeightedStrongValueReference(V referent, int weight) {
            super(referent);
            this.weight = weight;
        }

        @Override
        public int getWeight() {
            return this.weight;
        }
    }

    static final class WeightedWeakValueReference<K, V>
    extends WeakValueReference<K, V> {
        final int weight;

        WeightedWeakValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry, int weight) {
            super(queue, referent, entry);
            this.weight = weight;
        }

        @Override
        public int getWeight() {
            return this.weight;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry) {
            return new WeightedWeakValueReference<K, V>(queue, value, entry, this.weight);
        }
    }

    static class StrongValueReference<K, V>
    implements ValueReference<K, V> {
        final V referent;

        StrongValueReference(V referent) {
            this.referent = referent;
        }

        @Override
        public V get() {
            return this.referent;
        }

        @Override
        public int getWeight() {
            return 1;
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return null;
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry) {
            return this;
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public V waitForValue() {
            return this.get();
        }

        @Override
        public void notifyNewValue(V newValue) {
        }
    }

    static class WeakValueReference<K, V>
    extends WeakReference<V>
    implements ValueReference<K, V> {
        final ReferenceEntry<K, V> entry;

        WeakValueReference(ReferenceQueue<V> queue, V referent, ReferenceEntry<K, V> entry) {
            super(referent, queue);
            this.entry = entry;
        }

        @Override
        public int getWeight() {
            return 1;
        }

        @Override
        public ReferenceEntry<K, V> getEntry() {
            return this.entry;
        }

        @Override
        public void notifyNewValue(V newValue) {
        }

        @Override
        public ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, ReferenceEntry<K, V> entry) {
            return new WeakValueReference<K, V>(queue, value, entry);
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public V waitForValue() {
            return (V)this.get();
        }
    }

    static final class WeakAccessWriteEntry<K, V>
    extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.access$700();
        ReferenceEntry<K, V> previousAccess = LocalCache.access$700();
        volatile long writeTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextWrite = LocalCache.access$700();
        ReferenceEntry<K, V> previousWrite = LocalCache.access$700();

        WeakAccessWriteEntry(ReferenceQueue<K> queue, K key, int hash, ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static final class WeakWriteEntry<K, V>
    extends WeakEntry<K, V> {
        volatile long writeTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextWrite = LocalCache.access$700();
        ReferenceEntry<K, V> previousWrite = LocalCache.access$700();

        WeakWriteEntry(ReferenceQueue<K> queue, K key, int hash, ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static final class WeakAccessEntry<K, V>
    extends WeakEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.access$700();
        ReferenceEntry<K, V> previousAccess = LocalCache.access$700();

        WeakAccessEntry(ReferenceQueue<K> queue, K key, int hash, ReferenceEntry<K, V> next) {
            super(queue, key, hash, next);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }
    }

    static class WeakEntry<K, V>
    extends WeakReference<K>
    implements ReferenceEntry<K, V> {
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.access$600();

        WeakEntry(ReferenceQueue<K> queue, K key, int hash, ReferenceEntry<K, V> next) {
            super(key, queue);
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return (K)this.get();
        }

        @Override
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAccessTime(long time) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWriteTime(long time) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }

    static final class StrongAccessWriteEntry<K, V>
    extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.access$700();
        ReferenceEntry<K, V> previousAccess = LocalCache.access$700();
        volatile long writeTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextWrite = LocalCache.access$700();
        ReferenceEntry<K, V> previousWrite = LocalCache.access$700();

        StrongAccessWriteEntry(K key, int hash, ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static final class StrongWriteEntry<K, V>
    extends StrongEntry<K, V> {
        volatile long writeTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextWrite = LocalCache.access$700();
        ReferenceEntry<K, V> previousWrite = LocalCache.access$700();

        StrongWriteEntry(K key, int hash, ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        @Override
        public long getWriteTime() {
            return this.writeTime;
        }

        @Override
        public void setWriteTime(long time) {
            this.writeTime = time;
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            return this.nextWrite;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            this.nextWrite = next;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            return this.previousWrite;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            this.previousWrite = previous;
        }
    }

    static final class StrongAccessEntry<K, V>
    extends StrongEntry<K, V> {
        volatile long accessTime = Long.MAX_VALUE;
        ReferenceEntry<K, V> nextAccess = LocalCache.access$700();
        ReferenceEntry<K, V> previousAccess = LocalCache.access$700();

        StrongAccessEntry(K key, int hash, ReferenceEntry<K, V> next) {
            super(key, hash, next);
        }

        @Override
        public long getAccessTime() {
            return this.accessTime;
        }

        @Override
        public void setAccessTime(long time) {
            this.accessTime = time;
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            return this.nextAccess;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            this.nextAccess = next;
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            return this.previousAccess;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            this.previousAccess = previous;
        }
    }

    static class StrongEntry<K, V>
    extends AbstractReferenceEntry<K, V> {
        final K key;
        final int hash;
        final ReferenceEntry<K, V> next;
        volatile ValueReference<K, V> valueReference = LocalCache.access$600();

        StrongEntry(K key, int hash, ReferenceEntry<K, V> next) {
            this.key = key;
            this.hash = hash;
            this.next = next;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            return this.valueReference;
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            this.valueReference = valueReference;
        }

        @Override
        public int getHash() {
            return this.hash;
        }

        @Override
        public ReferenceEntry<K, V> getNext() {
            return this.next;
        }
    }

    static abstract class AbstractReferenceEntry<K, V>
    implements ReferenceEntry<K, V> {
        AbstractReferenceEntry() {
        }

        @Override
        public ValueReference<K, V> getValueReference() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setValueReference(ValueReference<K, V> valueReference) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNext() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getHash() {
            throw new UnsupportedOperationException();
        }

        @Override
        public K getKey() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getAccessTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setAccessTime(long time) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInAccessQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long getWriteTime() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWriteTime(long time) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getNextInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<K, V> next) {
            throw new UnsupportedOperationException();
        }

        @Override
        public ReferenceEntry<K, V> getPreviousInWriteQueue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<K, V> previous) {
            throw new UnsupportedOperationException();
        }
    }

    static interface ReferenceEntry<K, V> {
        public ValueReference<K, V> getValueReference();

        public void setValueReference(ValueReference<K, V> var1);

        public ReferenceEntry<K, V> getNext();

        public int getHash();

        public K getKey();

        public long getAccessTime();

        public void setAccessTime(long var1);

        public ReferenceEntry<K, V> getNextInAccessQueue();

        public void setNextInAccessQueue(ReferenceEntry<K, V> var1);

        public ReferenceEntry<K, V> getPreviousInAccessQueue();

        public void setPreviousInAccessQueue(ReferenceEntry<K, V> var1);

        public long getWriteTime();

        public void setWriteTime(long var1);

        public ReferenceEntry<K, V> getNextInWriteQueue();

        public void setNextInWriteQueue(ReferenceEntry<K, V> var1);

        public ReferenceEntry<K, V> getPreviousInWriteQueue();

        public void setPreviousInWriteQueue(ReferenceEntry<K, V> var1);
    }

    static interface ValueReference<K, V> {
        public V get();

        public V waitForValue() throws ExecutionException;

        public int getWeight();

        public ReferenceEntry<K, V> getEntry();

        public ValueReference<K, V> copyFor(ReferenceQueue<V> var1, V var2, ReferenceEntry<K, V> var3);

        public void notifyNewValue(V var1);

        public boolean isLoading();

        public boolean isActive();
    }

    private static enum NullEntry implements ReferenceEntry<Object, Object>
    {
        INSTANCE;


        @Override
        public ValueReference<Object, Object> getValueReference() {
            return null;
        }

        @Override
        public void setValueReference(ValueReference<Object, Object> valueReference) {
        }

        @Override
        public ReferenceEntry<Object, Object> getNext() {
            return null;
        }

        @Override
        public int getHash() {
            return 0;
        }

        @Override
        public Object getKey() {
            return null;
        }

        @Override
        public long getAccessTime() {
            return 0L;
        }

        @Override
        public void setAccessTime(long time) {
        }

        @Override
        public ReferenceEntry<Object, Object> getNextInAccessQueue() {
            return this;
        }

        @Override
        public void setNextInAccessQueue(ReferenceEntry<Object, Object> next) {
        }

        @Override
        public ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
            return this;
        }

        @Override
        public void setPreviousInAccessQueue(ReferenceEntry<Object, Object> previous) {
        }

        @Override
        public long getWriteTime() {
            return 0L;
        }

        @Override
        public void setWriteTime(long time) {
        }

        @Override
        public ReferenceEntry<Object, Object> getNextInWriteQueue() {
            return this;
        }

        @Override
        public void setNextInWriteQueue(ReferenceEntry<Object, Object> next) {
        }

        @Override
        public ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
            return this;
        }

        @Override
        public void setPreviousInWriteQueue(ReferenceEntry<Object, Object> previous) {
        }
    }

    static enum EntryFactory {
        STRONG{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, ReferenceEntry<K, V> next) {
                return new StrongEntry<K, V>(key, hash, next);
            }
        }
        ,
        STRONG_ACCESS{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, ReferenceEntry<K, V> next) {
                return new StrongAccessEntry<K, V>(key, hash, next);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyAccessEntry(original, newEntry);
                return newEntry;
            }
        }
        ,
        STRONG_WRITE{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, ReferenceEntry<K, V> next) {
                return new StrongWriteEntry<K, V>(key, hash, next);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyWriteEntry(original, newEntry);
                return newEntry;
            }
        }
        ,
        STRONG_ACCESS_WRITE{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, ReferenceEntry<K, V> next) {
                return new StrongAccessWriteEntry<K, V>(key, hash, next);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyAccessEntry(original, newEntry);
                this.copyWriteEntry(original, newEntry);
                return newEntry;
            }
        }
        ,
        WEAK{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, ReferenceEntry<K, V> next) {
                return new WeakEntry(segment.keyReferenceQueue, key, hash, next);
            }
        }
        ,
        WEAK_ACCESS{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, ReferenceEntry<K, V> next) {
                return new WeakAccessEntry(segment.keyReferenceQueue, key, hash, next);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyAccessEntry(original, newEntry);
                return newEntry;
            }
        }
        ,
        WEAK_WRITE{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, ReferenceEntry<K, V> next) {
                return new WeakWriteEntry(segment.keyReferenceQueue, key, hash, next);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyWriteEntry(original, newEntry);
                return newEntry;
            }
        }
        ,
        WEAK_ACCESS_WRITE{

            @Override
            <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> segment, K key, int hash, ReferenceEntry<K, V> next) {
                return new WeakAccessWriteEntry(segment.keyReferenceQueue, key, hash, next);
            }

            @Override
            <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
                ReferenceEntry<K, V> newEntry = super.copyEntry(segment, original, newNext);
                this.copyAccessEntry(original, newEntry);
                this.copyWriteEntry(original, newEntry);
                return newEntry;
            }
        };

        static final int ACCESS_MASK = 1;
        static final int WRITE_MASK = 2;
        static final int WEAK_MASK = 4;
        static final EntryFactory[] factories;

        static EntryFactory getFactory(Strength keyStrength, boolean usesAccessQueue, boolean usesWriteQueue) {
            int flags = (keyStrength == Strength.WEAK ? 4 : 0) | (usesAccessQueue ? 1 : 0) | (usesWriteQueue ? 2 : 0);
            return factories[flags];
        }

        abstract <K, V> ReferenceEntry<K, V> newEntry(Segment<K, V> var1, K var2, int var3, ReferenceEntry<K, V> var4);

        <K, V> ReferenceEntry<K, V> copyEntry(Segment<K, V> segment, ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
            return this.newEntry(segment, original.getKey(), original.getHash(), newNext);
        }

        <K, V> void copyAccessEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newEntry) {
            newEntry.setAccessTime(original.getAccessTime());
            LocalCache.connectAccessOrder(original.getPreviousInAccessQueue(), newEntry);
            LocalCache.connectAccessOrder(newEntry, original.getNextInAccessQueue());
            LocalCache.nullifyAccessOrder(original);
        }

        <K, V> void copyWriteEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newEntry) {
            newEntry.setWriteTime(original.getWriteTime());
            LocalCache.connectWriteOrder(original.getPreviousInWriteQueue(), newEntry);
            LocalCache.connectWriteOrder(newEntry, original.getNextInWriteQueue());
            LocalCache.nullifyWriteOrder(original);
        }

        static {
            factories = new EntryFactory[]{STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE};
        }
    }

    static enum Strength {
        STRONG{

            @Override
            <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
                return weight == 1 ? new StrongValueReference(value) : new WeightedStrongValueReference(value, weight);
            }

            @Override
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.equals();
            }
        }
        ,
        WEAK{

            @Override
            <K, V> ValueReference<K, V> referenceValue(Segment<K, V> segment, ReferenceEntry<K, V> entry, V value, int weight) {
                return weight == 1 ? new WeakValueReference(segment.valueReferenceQueue, value, entry) : new WeightedWeakValueReference(segment.valueReferenceQueue, value, entry, weight);
            }

            @Override
            Equivalence<Object> defaultEquivalence() {
                return Equivalence.identity();
            }
        };


        abstract <K, V> ValueReference<K, V> referenceValue(Segment<K, V> var1, ReferenceEntry<K, V> var2, V var3, int var4);

        abstract Equivalence<Object> defaultEquivalence();
    }
}


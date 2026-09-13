/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.glassfish.hk2.utilities.cache.CacheKeyFilter;
import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.cache.HybridCacheEntry;

public class LRUHybridCache<K, V>
implements Computable<K, HybridCacheEntry<V>> {
    private final CycleHandler<K> cycleHandler;
    private static final CycleHandler<Object> EMPTY_CYCLE_HANDLER = new CycleHandler<Object>(){

        @Override
        public void handleCycle(Object key) {
        }
    };
    private final ConcurrentHashMap<K, OriginThreadAwareFuture> cache = new ConcurrentHashMap();
    private final Computable<K, HybridCacheEntry<V>> computable;
    private final Object prunningLock = new Object();
    private final int maxCacheSize;
    private static final Comparator<OriginThreadAwareFuture> COMPARATOR = new CacheEntryImplComparator();

    public LRUHybridCache(int maxCacheSize, Computable<K, HybridCacheEntry<V>> computable) {
        this(maxCacheSize, computable, EMPTY_CYCLE_HANDLER);
    }

    public LRUHybridCache(int maxCacheSize, Computable<K, HybridCacheEntry<V>> computable, CycleHandler<K> cycleHandler) {
        this.maxCacheSize = maxCacheSize;
        this.computable = computable;
        this.cycleHandler = cycleHandler;
    }

    public HybridCacheEntry<V> createCacheEntry(K k, V v, boolean dropMe) {
        return new HybridCacheEntryImpl<V>(k, v, dropMe);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public HybridCacheEntry<V> compute(K key) {
        OriginThreadAwareFuture f = this.cache.get(key);
        if (f == null) {
            OriginThreadAwareFuture ft = new OriginThreadAwareFuture(this, key);
            Object object = this.prunningLock;
            synchronized (object) {
                if (this.cache.size() + 1 > this.maxCacheSize) {
                    this.removeLRUItem();
                }
                f = this.cache.putIfAbsent(key, ft);
            }
            if (f == null) {
                f = ft;
                ft.run();
            }
        } else {
            long tid = f.threadId;
            if (tid != -1L && Thread.currentThread().getId() == f.threadId) {
                this.cycleHandler.handleCycle(key);
            }
            f.lastHit = System.nanoTime();
        }
        try {
            Object result = f.get();
            if (result.dropMe()) {
                this.cache.remove(key);
            }
            return result;
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        catch (ExecutionException ex) {
            this.cache.remove(key);
            if (ex.getCause() instanceof RuntimeException) {
                throw (RuntimeException)ex.getCause();
            }
            throw new RuntimeException(ex);
        }
    }

    public void clear() {
        this.cache.clear();
    }

    public int size() {
        return this.cache.size();
    }

    public int getMaximumCacheSize() {
        return this.maxCacheSize;
    }

    public boolean containsKey(K key) {
        return this.cache.containsKey(key);
    }

    public void remove(K key) {
        this.cache.remove(key);
    }

    private void removeLRUItem() {
        Collection<OriginThreadAwareFuture> values = this.cache.values();
        this.cache.remove(Collections.min(values, COMPARATOR).key);
    }

    public void releaseMatching(CacheKeyFilter<K> filter) {
        if (filter == null) {
            return;
        }
        for (Object key : this.cache.keySet()) {
            if (!filter.matches(key)) continue;
            this.cache.remove(key);
        }
    }

    private static class CacheEntryImplComparator<K, V>
    implements Comparator<OriginThreadAwareFuture> {
        private CacheEntryImplComparator() {
        }

        @Override
        public int compare(OriginThreadAwareFuture first, OriginThreadAwareFuture second) {
            long diff = first.lastHit - second.lastHit;
            return diff > 0L ? 1 : (diff == 0L ? 0 : -1);
        }
    }

    private final class HybridCacheEntryImpl<V1>
    implements HybridCacheEntry<V1> {
        private final K key;
        private final V1 value;
        private final boolean dropMe;

        public HybridCacheEntryImpl(K key, V1 value, boolean dropMe) {
            this.key = key;
            this.value = value;
            this.dropMe = dropMe;
        }

        @Override
        public V1 getValue() {
            return this.value;
        }

        @Override
        public boolean dropMe() {
            return this.dropMe;
        }

        @Override
        public void removeFromCache() {
            LRUHybridCache.this.remove(this.key);
        }

        public int hashCode() {
            int hash = 5;
            hash = 23 * hash + (this.key != null ? this.key.hashCode() : 0);
            return hash;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            HybridCacheEntryImpl other = (HybridCacheEntryImpl)obj;
            return this.key == other.key || this.key != null && this.key.equals(other.key);
        }
    }

    private class OriginThreadAwareFuture
    implements Future<HybridCacheEntry<V>> {
        private final K key;
        private final FutureTask<HybridCacheEntry<V>> future;
        private volatile long threadId;
        private volatile long lastHit;

        OriginThreadAwareFuture(LRUHybridCache<K, HybridCacheEntry<V>> cache, final K key) {
            this.key = key;
            this.threadId = Thread.currentThread().getId();
            Callable eval = new Callable<HybridCacheEntry<V>>(){

                @Override
                public HybridCacheEntry<V> call() throws Exception {
                    try {
                        HybridCacheEntry result;
                        HybridCacheEntry hybridCacheEntry = result = (HybridCacheEntry)LRUHybridCache.this.computable.compute(key);
                        return hybridCacheEntry;
                    }
                    finally {
                        OriginThreadAwareFuture.this.threadId = -1L;
                    }
                }
            };
            this.future = new FutureTask(eval);
            this.lastHit = System.nanoTime();
        }

        public int hashCode() {
            return this.future.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            OriginThreadAwareFuture other = (OriginThreadAwareFuture)obj;
            return this.future == other.future || this.future != null && this.future.equals(other.future);
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return this.future.cancel(mayInterruptIfRunning);
        }

        @Override
        public boolean isCancelled() {
            return this.future.isCancelled();
        }

        @Override
        public boolean isDone() {
            return this.future.isDone();
        }

        @Override
        public HybridCacheEntry<V> get() throws InterruptedException, ExecutionException {
            return this.future.get();
        }

        @Override
        public HybridCacheEntry<V> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return this.future.get(timeout, unit);
        }

        public void run() {
            this.future.run();
        }
    }

    public static interface CycleHandler<K> {
        public void handleCycle(K var1);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.cache.internal;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.glassfish.hk2.utilities.cache.CacheKeyFilter;
import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.cache.ComputationErrorException;
import org.glassfish.hk2.utilities.cache.WeakCARCache;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.general.WeakHashClock;
import org.glassfish.hk2.utilities.general.WeakHashLRU;

public class WeakCARCacheImpl<K, V>
implements WeakCARCache<K, V> {
    private final Computable<K, V> computable;
    private final int maxSize;
    private final WeakHashClock<K, CarValue<V>> t1;
    private final WeakHashClock<K, CarValue<V>> t2;
    private final WeakHashLRU<K> b1;
    private final WeakHashLRU<K> b2;
    private int p = 0;
    private final AtomicLong hits = new AtomicLong(0L);
    private final AtomicLong tries = new AtomicLong(0L);

    public WeakCARCacheImpl(Computable<K, V> computable, int maxSize, boolean isWeak) {
        this.computable = computable;
        this.maxSize = maxSize;
        this.t1 = GeneralUtilities.getWeakHashClock(isWeak);
        this.t2 = GeneralUtilities.getWeakHashClock(isWeak);
        this.b1 = GeneralUtilities.getWeakHashLRU(isWeak);
        this.b2 = GeneralUtilities.getWeakHashLRU(isWeak);
    }

    private V getValueFromT(K key) {
        CarValue<V> cValue = this.t1.get(key);
        if (cValue != null) {
            ((CarValue)cValue).referenceBit = true;
            return (V)((CarValue)cValue).value;
        }
        cValue = this.t2.get(key);
        if (cValue != null) {
            ((CarValue)cValue).referenceBit = true;
            return (V)((CarValue)cValue).value;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public V compute(K key) {
        this.tries.getAndIncrement();
        V value = this.getValueFromT(key);
        if (value != null) {
            this.hits.getAndIncrement();
            return value;
        }
        WeakCARCacheImpl weakCARCacheImpl = this;
        synchronized (weakCARCacheImpl) {
            boolean inB2;
            boolean inB1;
            value = this.getValueFromT(key);
            if (value != null) {
                this.hits.getAndIncrement();
                return value;
            }
            try {
                value = this.computable.compute(key);
            }
            catch (ComputationErrorException cee) {
                return (V)cee.getComputation();
            }
            int cacheSize = this.getValueSize();
            if (cacheSize >= this.maxSize) {
                this.replace();
                inB1 = this.b1.contains(key);
                inB2 = this.b2.contains(key);
                if (!inB1 && !inB2) {
                    if (this.t1.size() + this.b1.size() >= this.maxSize) {
                        this.b1.remove();
                    } else if (this.t1.size() + this.t2.size() + this.b1.size() + this.b2.size() >= 2 * this.maxSize) {
                        this.b2.remove();
                    }
                }
            }
            inB1 = this.b1.contains(key);
            inB2 = this.b2.contains(key);
            if (!inB1 && !inB2) {
                this.t1.put(key, new CarValue(value));
            } else if (inB1) {
                int b2size;
                int ratio;
                int b1size = this.b1.size();
                if (b1size == 0) {
                    b1size = 1;
                }
                if ((ratio = (b2size = this.b2.size()) / b1size) <= 0) {
                    ratio = 1;
                }
                this.p += ratio;
                if (this.p > this.maxSize) {
                    this.p = this.maxSize;
                }
                this.b1.remove(key);
                this.t2.put(key, new CarValue(value));
            } else {
                int b1size;
                int ratio;
                int b2size = this.b2.size();
                if (b2size == 0) {
                    b2size = 1;
                }
                if ((ratio = (b1size = this.b1.size()) / b2size) <= 0) {
                    ratio = 1;
                }
                this.p -= ratio;
                if (this.p < 0) {
                    this.p = 0;
                }
                this.b2.remove(key);
                this.t2.put(key, new CarValue(value));
            }
        }
        return value;
    }

    private void replace() {
        boolean found = false;
        while (!found) {
            CarValue<V> entryValue;
            Map.Entry<K, CarValue<Object>> entry;
            int trySize = this.p;
            if (trySize < 1) {
                trySize = 1;
            }
            if (this.t1.size() >= trySize) {
                entry = this.t1.next();
                if (!((CarValue)entry.getValue()).referenceBit) {
                    found = true;
                    this.t1.remove(entry.getKey());
                    this.b1.add(entry.getKey());
                    continue;
                }
                entryValue = entry.getValue();
                ((CarValue)entryValue).referenceBit = false;
                this.t1.remove(entry.getKey());
                this.t2.put(entry.getKey(), entryValue);
                continue;
            }
            entry = this.t2.next();
            if (!((CarValue)entry.getValue()).referenceBit) {
                found = true;
                this.t2.remove(entry.getKey());
                this.b2.add(entry.getKey());
                continue;
            }
            entryValue = entry.getValue();
            ((CarValue)entryValue).referenceBit = false;
        }
    }

    @Override
    public synchronized int getKeySize() {
        return this.t1.size() + this.t2.size() + this.b1.size() + this.b2.size();
    }

    @Override
    public synchronized int getValueSize() {
        return this.t1.size() + this.t2.size();
    }

    @Override
    public synchronized void clear() {
        this.t1.clear();
        this.t2.clear();
        this.b1.clear();
        this.b2.clear();
        this.p = 0;
        this.tries.set(0L);
        this.hits.set(0L);
    }

    @Override
    public int getMaxSize() {
        return this.maxSize;
    }

    @Override
    public Computable<K, V> getComputable() {
        return this.computable;
    }

    @Override
    public synchronized boolean remove(K key) {
        if (this.t1.remove(key) == null) {
            if (this.t2.remove(key) == null) {
                if (!this.b1.remove(key)) {
                    return this.b2.remove(key);
                }
                return true;
            }
            return true;
        }
        return true;
    }

    @Override
    public synchronized void releaseMatching(CacheKeyFilter<K> filter) {
        if (filter == null) {
            return;
        }
        this.b2.releaseMatching(filter);
        this.b1.releaseMatching(filter);
        this.t1.releaseMatching(filter);
        this.t2.releaseMatching(filter);
    }

    @Override
    public synchronized void clearStaleReferences() {
        this.t1.clearStaleReferences();
        this.t2.clearStaleReferences();
        this.b1.clearStaleReferences();
        this.b2.clearStaleReferences();
    }

    @Override
    public int getT1Size() {
        return this.t1.size();
    }

    @Override
    public int getT2Size() {
        return this.t2.size();
    }

    @Override
    public int getB1Size() {
        return this.b1.size();
    }

    @Override
    public int getB2Size() {
        return this.b2.size();
    }

    @Override
    public int getP() {
        return this.p;
    }

    @Override
    public String dumpAllLists() {
        StringBuffer sb = new StringBuffer("p=" + this.p + "\nT1: " + this.t1.toString() + "\n");
        sb.append("T2: " + this.t2.toString() + "\n");
        sb.append("B1: " + this.b1.toString() + "\n");
        sb.append("B2: " + this.b2.toString() + "\n");
        return sb.toString();
    }

    @Override
    public double getHitRate() {
        long localHits = this.hits.get();
        long localTries = this.tries.get();
        if (localTries == 0L) {
            localTries = 1L;
        }
        return (double)localHits / (double)localTries * 100.0;
    }

    public String toString() {
        return "WeakCARCacheImpl(t1size=" + this.t1.size() + ",t2Size=" + this.t2.size() + ",b1Size=" + this.b1.size() + ",b2Size=" + this.b2.size() + ",p=" + this.p + ",hitRate=" + this.getHitRate() + "%," + System.identityHashCode(this) + ")";
    }

    private static class CarValue<V> {
        private final V value;
        private volatile boolean referenceBit = false;

        private CarValue(V value) {
            this.value = value;
        }
    }
}


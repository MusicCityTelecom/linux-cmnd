/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.general.internal;

import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.glassfish.hk2.utilities.cache.CacheKeyFilter;
import org.glassfish.hk2.utilities.general.WeakHashLRU;
import org.glassfish.hk2.utilities.general.internal.DoubleNode;

public class WeakHashLRUImpl<K>
implements WeakHashLRU<K> {
    private static final Object VALUE = new Object();
    private final boolean isWeak;
    private final WeakHashMap<K, DoubleNode<K, Object>> byKey;
    private final ConcurrentHashMap<K, DoubleNode<K, Object>> byKeyNotWeak;
    private final ReferenceQueue<? super K> myQueue = new ReferenceQueue();
    private DoubleNode<K, Object> mru;
    private DoubleNode<K, Object> lru;

    public WeakHashLRUImpl(boolean isWeak) {
        this.isWeak = isWeak;
        if (isWeak) {
            this.byKey = new WeakHashMap();
            this.byKeyNotWeak = null;
        } else {
            this.byKey = null;
            this.byKeyNotWeak = new ConcurrentHashMap();
        }
    }

    private DoubleNode<K, Object> addToHead(K key) {
        DoubleNode<K, Object> added = new DoubleNode<K, Object>(key, VALUE, this.myQueue);
        if (this.mru == null) {
            this.mru = added;
            this.lru = added;
            return added;
        }
        added.setNext(this.mru);
        this.mru.setPrevious(added);
        this.mru = added;
        return added;
    }

    private K remove(DoubleNode<K, Object> removeMe) {
        Object retVal = removeMe.getWeakKey().get();
        if (removeMe.getNext() != null) {
            removeMe.getNext().setPrevious(removeMe.getPrevious());
        }
        if (removeMe.getPrevious() != null) {
            removeMe.getPrevious().setNext(removeMe.getNext());
        }
        if (removeMe == this.mru) {
            this.mru = removeMe.getNext();
        }
        if (removeMe == this.lru) {
            this.lru = removeMe.getPrevious();
        }
        removeMe.setNext(null);
        removeMe.setPrevious(null);
        return (K)retVal;
    }

    @Override
    public synchronized void add(K key) {
        DoubleNode<K, Object> existing;
        if (key == null) {
            throw new IllegalArgumentException("key may not be null");
        }
        if (this.isWeak) {
            this.clearStale();
            existing = this.byKey.get(key);
        } else {
            existing = this.byKeyNotWeak.get(key);
        }
        if (existing != null) {
            this.remove(existing);
        }
        DoubleNode<K, Object> added = this.addToHead(key);
        if (this.isWeak) {
            this.byKey.put(key, added);
        } else {
            this.byKeyNotWeak.put(key, added);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean contains(K key) {
        if (this.isWeak) {
            WeakHashLRUImpl weakHashLRUImpl = this;
            synchronized (weakHashLRUImpl) {
                this.clearStale();
                return this.byKey.containsKey(key);
            }
        }
        return this.byKeyNotWeak.containsKey(key);
    }

    @Override
    public synchronized boolean remove(K key) {
        if (this.isWeak) {
            this.clearStale();
        }
        return this.removeNoClear(key);
    }

    private boolean removeNoClear(K key) {
        if (key == null) {
            return false;
        }
        DoubleNode<K, Object> removeMe = this.isWeak ? this.byKey.remove(key) : this.byKeyNotWeak.remove(key);
        if (removeMe == null) {
            return false;
        }
        this.remove(removeMe);
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int size() {
        if (this.isWeak) {
            WeakHashLRUImpl weakHashLRUImpl = this;
            synchronized (weakHashLRUImpl) {
                this.clearStale();
                return this.byKey.size();
            }
        }
        return this.byKeyNotWeak.size();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized K remove() {
        try {
            if (this.lru == null) {
                K k = null;
                return k;
            }
            DoubleNode<K, Object> current = this.lru;
            while (current != null) {
                DoubleNode<K, Object> previous = current.getPrevious();
                Object retVal = current.getWeakKey().get();
                if (retVal != null) {
                    this.removeNoClear(retVal);
                    Object t = retVal;
                    return (K)t;
                }
                this.remove(current);
                current = previous;
            }
            K k = null;
            return k;
        }
        finally {
            this.clearStale();
        }
    }

    @Override
    public synchronized void releaseMatching(CacheKeyFilter<K> filter) {
        if (filter == null) {
            return;
        }
        if (this.isWeak) {
            this.clearStale();
        }
        LinkedList removeMe = new LinkedList();
        for (DoubleNode<K, Object> current = this.mru; current != null; current = current.getNext()) {
            Object key = current.getWeakKey().get();
            if (key == null || !filter.matches(key)) continue;
            removeMe.add(key);
        }
        for (Object removeKey : removeMe) {
            this.removeNoClear(removeKey);
        }
    }

    @Override
    public synchronized void clear() {
        if (this.isWeak) {
            this.clearStale();
            this.byKey.clear();
        } else {
            this.byKeyNotWeak.clear();
        }
        this.mru = null;
        this.lru = null;
    }

    @Override
    public synchronized void clearStaleReferences() {
        this.clearStale();
    }

    private void clearStale() {
        boolean goOn = false;
        while (this.myQueue.poll() != null) {
            goOn = true;
        }
        if (!goOn) {
            return;
        }
        DoubleNode<K, Object> current = this.mru;
        while (current != null) {
            DoubleNode<K, Object> next = current.getNext();
            if (current.getWeakKey().get() == null) {
                this.remove(current);
            }
            current = next;
        }
    }

    public synchronized String toString() {
        StringBuffer sb = new StringBuffer("WeakHashLRUImpl({");
        boolean first = true;
        for (DoubleNode<K, Object> current = this.mru; current != null; current = current.getNext()) {
            String keyString;
            Object key = current.getWeakKey().get();
            String string = keyString = key == null ? "null" : key.toString();
            if (first) {
                first = false;
                sb.append(keyString);
                continue;
            }
            sb.append("," + keyString);
        }
        sb.append("}," + System.identityHashCode(this) + ")");
        return sb.toString();
    }
}


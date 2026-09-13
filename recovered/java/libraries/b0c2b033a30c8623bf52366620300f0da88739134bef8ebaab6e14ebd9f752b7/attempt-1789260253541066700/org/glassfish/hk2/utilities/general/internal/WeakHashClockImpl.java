/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.general.internal;

import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.glassfish.hk2.utilities.cache.CacheKeyFilter;
import org.glassfish.hk2.utilities.general.WeakHashClock;
import org.glassfish.hk2.utilities.general.internal.DoubleNode;

public class WeakHashClockImpl<K, V>
implements WeakHashClock<K, V> {
    private final boolean isWeak;
    private final ConcurrentHashMap<K, DoubleNode<K, V>> byKeyNotWeak;
    private final WeakHashMap<K, DoubleNode<K, V>> byKey;
    private final ReferenceQueue<? super K> myQueue = new ReferenceQueue();
    private DoubleNode<K, V> head;
    private DoubleNode<K, V> tail;
    private DoubleNode<K, V> dot;

    public WeakHashClockImpl(boolean isWeak) {
        this.isWeak = isWeak;
        if (isWeak) {
            this.byKey = new WeakHashMap();
            this.byKeyNotWeak = null;
        } else {
            this.byKeyNotWeak = new ConcurrentHashMap();
            this.byKey = null;
        }
    }

    private DoubleNode<K, V> addBeforeDot(K key, V value) {
        DoubleNode<K, V> toAdd = new DoubleNode<K, V>(key, value, this.myQueue);
        if (this.dot == null) {
            this.head = toAdd;
            this.tail = toAdd;
            this.dot = toAdd;
            return toAdd;
        }
        if (this.dot.getPrevious() == null) {
            this.dot.setPrevious(toAdd);
            toAdd.setNext(this.dot);
            this.head = toAdd;
            return toAdd;
        }
        toAdd.setNext(this.dot);
        toAdd.setPrevious(this.dot.getPrevious());
        this.dot.getPrevious().setNext(toAdd);
        this.dot.setPrevious(toAdd);
        return toAdd;
    }

    private void removeFromDLL(DoubleNode<K, V> removeMe) {
        if (removeMe.getPrevious() != null) {
            removeMe.getPrevious().setNext(removeMe.getNext());
        }
        if (removeMe.getNext() != null) {
            removeMe.getNext().setPrevious(removeMe.getPrevious());
        }
        if (removeMe == this.head) {
            this.head = removeMe.getNext();
        }
        if (removeMe == this.tail) {
            this.tail = removeMe.getPrevious();
        }
        if (removeMe == this.dot) {
            this.dot = removeMe.getNext();
            if (this.dot == null) {
                this.dot = this.head;
            }
        }
        removeMe.setNext(null);
        removeMe.setPrevious(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key " + key + " or value " + value + " is null");
        }
        WeakHashClockImpl weakHashClockImpl = this;
        synchronized (weakHashClockImpl) {
            if (this.isWeak) {
                this.removeStale();
            }
            DoubleNode<K, V> addMe = this.addBeforeDot(key, value);
            if (this.isWeak) {
                this.byKey.put(key, addMe);
            } else {
                this.byKeyNotWeak.put(key, addMe);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public V get(K key) {
        DoubleNode<K, V> node;
        if (key == null) {
            return null;
        }
        if (this.isWeak) {
            WeakHashClockImpl weakHashClockImpl = this;
            synchronized (weakHashClockImpl) {
                this.removeStale();
                node = this.byKey.get(key);
            }
        } else {
            node = this.byKeyNotWeak.get(key);
        }
        if (node == null) {
            return null;
        }
        return node.getValue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        WeakHashClockImpl weakHashClockImpl = this;
        synchronized (weakHashClockImpl) {
            DoubleNode<K, V> node;
            if (this.isWeak) {
                this.removeStale();
                node = this.byKey.remove(key);
            } else {
                node = this.byKeyNotWeak.remove(key);
            }
            if (node == null) {
                return null;
            }
            this.removeFromDLL(node);
            return node.getValue();
        }
    }

    @Override
    public synchronized void releaseMatching(CacheKeyFilter<K> filter) {
        if (filter == null) {
            return;
        }
        if (this.isWeak) {
            this.removeStale();
        }
        LinkedList removeMe = new LinkedList();
        for (DoubleNode<K, V> current = this.head; current != null; current = current.getNext()) {
            Object key = current.getWeakKey().get();
            if (key == null || !filter.matches(key)) continue;
            removeMe.add(key);
        }
        for (Object removeKey : removeMe) {
            this.remove(removeKey);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int size() {
        if (this.isWeak) {
            WeakHashClockImpl weakHashClockImpl = this;
            synchronized (weakHashClockImpl) {
                this.removeStale();
                return this.byKey.size();
            }
        }
        return this.byKeyNotWeak.size();
    }

    private DoubleNode<K, V> moveDot() {
        if (this.dot == null) {
            return null;
        }
        DoubleNode<K, V> returnSource = this.dot;
        this.dot = returnSource.getNext();
        if (this.dot == null) {
            this.dot = this.head;
        }
        return returnSource;
    }

    private DoubleNode<K, V> moveDotNoWeak() {
        Object key;
        DoubleNode original = this.moveDot();
        DoubleNode<Object, V> retVal = original;
        if (retVal == null) {
            return null;
        }
        while ((key = retVal.getWeakKey().get()) == null) {
            retVal = this.moveDot();
            if (retVal == null) {
                return null;
            }
            if (retVal != original) continue;
            return null;
        }
        retVal.setHardenedKey(key);
        return retVal;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized Map.Entry<K, V> next() {
        DoubleNode<K, V> hardenedNode = this.moveDotNoWeak();
        if (hardenedNode == null) {
            return null;
        }
        try {
            final K key = hardenedNode.getHardenedKey();
            final V value = hardenedNode.getValue();
            Map.Entry entry = new Map.Entry<K, V>(){

                @Override
                public K getKey() {
                    return key;
                }

                @Override
                public V getValue() {
                    return value;
                }

                @Override
                public V setValue(V value2) {
                    throw new AssertionError((Object)"not implemented");
                }
            };
            return entry;
        }
        finally {
            hardenedNode.setHardenedKey(null);
            this.removeStale();
        }
    }

    @Override
    public synchronized void clear() {
        if (this.isWeak) {
            this.byKey.clear();
        } else {
            this.byKeyNotWeak.clear();
        }
        this.dot = null;
        this.tail = null;
        this.head = null;
    }

    @Override
    public synchronized void clearStaleReferences() {
        this.removeStale();
    }

    private void removeStale() {
        boolean goOn = false;
        while (this.myQueue.poll() != null) {
            goOn = true;
        }
        if (!goOn) {
            return;
        }
        DoubleNode<K, V> current = this.head;
        while (current != null) {
            DoubleNode<K, V> next = current.getNext();
            if (current.getWeakKey().get() == null) {
                this.removeFromDLL(current);
            }
            current = next;
        }
    }

    @Override
    public boolean hasWeakKeys() {
        return this.isWeak;
    }

    public synchronized String toString() {
        StringBuffer sb = new StringBuffer("WeakHashClockImpl({");
        boolean first = true;
        DoubleNode<K, V> current = this.dot;
        if (current != null) {
            do {
                Object key;
                String keyString;
                String string = keyString = (key = current.getWeakKey().get()) == null ? "null" : key.toString();
                if (first) {
                    first = false;
                    sb.append(keyString);
                } else {
                    sb.append("," + keyString);
                }
                current = current.getNext();
                if (current != null) continue;
                current = this.head;
            } while (current != this.dot);
        }
        sb.append("}," + System.identityHashCode(this) + ")");
        return sb.toString();
    }
}


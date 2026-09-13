/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.general;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Hk2ThreadLocal<V> {
    private static final Object NULL = new Object();
    private final Map<Key, Object> locals = new ConcurrentHashMap<Key, Object>();
    private final ReferenceQueue<Thread> queue = new ReferenceQueue();

    protected V initialValue() {
        return null;
    }

    public V get() {
        this.removeStaleEntries();
        Key key = Hk2ThreadLocal.newLookupKey();
        Object value = this.locals.get(key);
        if (value == null) {
            value = this.initialValue();
            this.locals.put(Hk2ThreadLocal.newStorageKey(this.queue), Hk2ThreadLocal.maskNull(value));
        } else {
            value = Hk2ThreadLocal.unmaskNull(value);
        }
        return (V)value;
    }

    public void set(V value) {
        Key key = Hk2ThreadLocal.newStorageKey(this.queue);
        this.locals.put(key, Hk2ThreadLocal.maskNull(value));
    }

    public void remove() {
        Key key = Hk2ThreadLocal.newLookupKey();
        this.locals.remove(key);
    }

    public void removeAll() {
        this.locals.clear();
    }

    public int getSize() {
        this.removeStaleEntries();
        return this.locals.size();
    }

    private void removeStaleEntries() {
        Reference<Thread> queued;
        while ((queued = this.queue.poll()) != null) {
            Key key = (Key)queued;
            this.locals.remove(key);
        }
    }

    private static Object maskNull(Object value) {
        return value == null ? NULL : value;
    }

    private static Object unmaskNull(Object value) {
        return value == NULL ? null : value;
    }

    private static Key newStorageKey(ReferenceQueue<Thread> queue) {
        return new Key(Thread.currentThread(), queue);
    }

    private static Key newLookupKey() {
        return new Key(Thread.currentThread(), null);
    }

    private static class Key
    extends WeakReference<Thread> {
        private final long threadId;
        private final int hash;

        private Key(Thread thread, ReferenceQueue<Thread> queue) {
            super(thread, queue);
            this.threadId = thread.getId();
            this.hash = thread.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) {
                return false;
            }
            Key other = (Key)obj;
            return other.threadId == this.threadId;
        }

        public int hashCode() {
            return this.hash;
        }
    }
}


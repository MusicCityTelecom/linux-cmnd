/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import java.util.Collection;
import java.util.LinkedList;
import org.codehaus.groovy.util.LockableObject;

@Deprecated
public abstract class AbstractConcurrentMapBase {
    protected static final int MAXIMUM_CAPACITY = 0x40000000;
    static final int MAX_SEGMENTS = 65536;
    static final int RETRIES_BEFORE_LOCK = 2;
    final int segmentMask;
    final int segmentShift;
    protected final Segment[] segments;

    public AbstractConcurrentMapBase(Object segmentInfo) {
        int cap;
        int ssize;
        int sshift = 0;
        for (ssize = 1; ssize < 16; ssize <<= 1) {
            ++sshift;
        }
        this.segmentShift = 32 - sshift;
        this.segmentMask = ssize - 1;
        this.segments = new Segment[ssize];
        int c = 512 / ssize;
        if (c * ssize < 512) {
            ++c;
        }
        for (cap = 1; cap < c; cap <<= 1) {
        }
        for (int i = 0; i < this.segments.length; ++i) {
            this.segments[i] = this.createSegment(segmentInfo, cap);
        }
    }

    protected abstract Segment createSegment(Object var1, int var2);

    protected static <K> int hash(K key) {
        int h = System.identityHashCode(key);
        h += ~(h << 9);
        h ^= h >>> 14;
        h += h << 4;
        h ^= h >>> 10;
        return h;
    }

    public Segment segmentFor(int hash) {
        return this.segments[hash >>> this.segmentShift & this.segmentMask];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int fullSize() {
        int count = 0;
        for (Segment segment : this.segments) {
            segment.lock();
            try {
                for (int j = 0; j < segment.table.length; ++j) {
                    Object o = segment.table[j];
                    if (o == null) continue;
                    if (o instanceof Entry) {
                        ++count;
                        continue;
                    }
                    Object[] arr = (Object[])o;
                    count += arr.length;
                }
            }
            finally {
                segment.unlock();
            }
        }
        return count;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int size() {
        int count = 0;
        for (Segment segment : this.segments) {
            segment.lock();
            try {
                for (int j = 0; j < segment.table.length; ++j) {
                    Object[] arr;
                    Object o = segment.table[j];
                    if (o == null) continue;
                    if (o instanceof Entry) {
                        Entry e = (Entry)o;
                        if (!e.isValid()) continue;
                        ++count;
                        continue;
                    }
                    for (Object value : arr = (Object[])o) {
                        Entry info = (Entry)value;
                        if (info == null || !info.isValid()) continue;
                        ++count;
                    }
                }
            }
            finally {
                segment.unlock();
            }
        }
        return count;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Collection values() {
        LinkedList<Entry> result = new LinkedList<Entry>();
        for (Segment segment : this.segments) {
            segment.lock();
            try {
                for (int j = 0; j < segment.table.length; ++j) {
                    Object[] arr;
                    Object o = segment.table[j];
                    if (o == null) continue;
                    if (o instanceof Entry) {
                        Entry e = (Entry)o;
                        if (!e.isValid()) continue;
                        result.add(e);
                        continue;
                    }
                    for (Object value : arr = (Object[])o) {
                        Entry info = (Entry)value;
                        if (info == null || !info.isValid()) continue;
                        result.add(info);
                    }
                }
            }
            finally {
                segment.unlock();
            }
        }
        return result;
    }

    public static class Segment
    extends LockableObject {
        private static final long serialVersionUID = -4128828550135386431L;
        volatile int count;
        int threshold;
        protected volatile Object[] table;

        protected Segment(int initialCapacity) {
            this.setTable(new Object[initialCapacity]);
        }

        void setTable(Object[] newTable) {
            this.threshold = (int)((float)newTable.length * 0.75f);
            this.table = newTable;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void removeEntry(Entry e) {
            this.lock();
            int newCount = this.count;
            try {
                Object[] tab = this.table;
                int index = e.getHash() & tab.length - 1;
                Object o = tab[index];
                if (o != null) {
                    if (o instanceof Entry) {
                        if (o == e) {
                            tab[index] = null;
                            --newCount;
                        }
                    } else {
                        Object[] arr = (Object[])o;
                        Object res = null;
                        for (Object value : arr) {
                            Entry info = (Entry)value;
                            if (info == null) continue;
                            if (info != e) {
                                if (info.isValid()) {
                                    res = Segment.put(info, res);
                                    continue;
                                }
                                --newCount;
                                continue;
                            }
                            --newCount;
                        }
                        tab[index] = res;
                    }
                    this.count = newCount;
                }
            }
            finally {
                this.unlock();
            }
        }

        void rehashIfThresholdExceeded() {
            if (this.count > this.threshold) {
                this.rehash();
            }
        }

        void rehash() {
            Object[] oldTable = this.table;
            int oldCapacity = oldTable.length;
            if (oldCapacity >= 0x40000000) {
                return;
            }
            int newCount = 0;
            for (int i = 0; i < oldCapacity; ++i) {
                Object o = oldTable[i];
                if (o == null) continue;
                if (o instanceof Entry) {
                    Entry e = (Entry)o;
                    if (e.isValid()) {
                        ++newCount;
                        continue;
                    }
                    oldTable[i] = null;
                    continue;
                }
                Object[] arr = (Object[])o;
                int localCount = 0;
                for (int index = 0; index < arr.length; ++index) {
                    Entry e = (Entry)arr[index];
                    if (e != null && e.isValid()) {
                        ++localCount;
                        continue;
                    }
                    arr[index] = null;
                }
                if (localCount == 0) {
                    oldTable[i] = null;
                    continue;
                }
                newCount += localCount;
            }
            Object[] newTable = new Object[newCount + 1 < this.threshold ? oldCapacity : oldCapacity << 1];
            int sizeMask = newTable.length - 1;
            newCount = 0;
            for (Object o : oldTable) {
                Object[] arr;
                if (o == null) continue;
                if (o instanceof Entry) {
                    Entry e = (Entry)o;
                    if (!e.isValid()) continue;
                    int index = e.getHash() & sizeMask;
                    Segment.put(e, index, newTable);
                    ++newCount;
                    continue;
                }
                for (Object value : arr = (Object[])o) {
                    Entry e = (Entry)value;
                    if (e == null || !e.isValid()) continue;
                    int index = e.getHash() & sizeMask;
                    Segment.put(e, index, newTable);
                    ++newCount;
                }
            }
            this.threshold = (int)((float)newTable.length * 0.75f);
            this.table = newTable;
            this.count = newCount;
        }

        private static void put(Entry ee, int index, Object[] tab) {
            Object o = tab[index];
            if (o != null) {
                if (o instanceof Entry) {
                    Object[] arr = new Object[]{ee, o};
                    tab[index] = arr;
                    return;
                }
                Object[] arr = (Object[])o;
                Object[] newArr = new Object[arr.length + 1];
                newArr[0] = ee;
                System.arraycopy(arr, 0, newArr, 1, arr.length);
                tab[index] = newArr;
                return;
            }
            tab[index] = ee;
        }

        private static Object put(Entry ee, Object o) {
            if (o != null) {
                if (o instanceof Entry) {
                    Object[] arr = new Object[]{ee, o};
                    return arr;
                }
                Object[] arr = (Object[])o;
                Object[] newArr = new Object[arr.length + 1];
                newArr[0] = ee;
                System.arraycopy(arr, 0, newArr, 1, arr.length);
                return newArr;
            }
            return ee;
        }
    }

    public static interface Entry<V> {
        public V getValue();

        public void setValue(V var1);

        public int getHash();

        public boolean isValid();
    }
}


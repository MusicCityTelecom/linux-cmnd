/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.jexl3.internal.AscLongIterator;
import org.apache.commons.jexl3.internal.DescLongIterator;

public abstract class LongRange
implements Collection<Long> {
    protected final long min;
    protected final long max;

    public static LongRange create(long from, long to) {
        if (from <= to) {
            return new Ascending(from, to);
        }
        return new Descending(to, from);
    }

    protected LongRange(long from, long to) {
        this.min = from;
        this.max = to;
    }

    public long getMin() {
        return this.min;
    }

    public long getMax() {
        return this.max;
    }

    @Override
    public int hashCode() {
        int hash = this.getClass().hashCode();
        hash = 13 * hash + (int)(this.min ^ this.min >>> 32);
        hash = 13 * hash + (int)(this.max ^ this.max >>> 32);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        LongRange other = (LongRange)obj;
        if (this.min != other.min) {
            return false;
        }
        return this.max == other.max;
    }

    @Override
    public abstract Iterator<Long> iterator();

    @Override
    public int size() {
        return (int)(this.max - this.min + 1L);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Number) {
            long v = ((Number)o).longValue();
            return this.min <= v && v <= this.max;
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        int size = this.size();
        Object[] array = new Object[size];
        for (int a = 0; a < size; ++a) {
            array[a] = this.min + (long)a;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Class<Long> ct = array.getClass().getComponentType();
        int length = this.size();
        Object[] copy = array;
        if (ct.isAssignableFrom(Long.class)) {
            if (array.length < length) {
                copy = (Object[])Array.newInstance(ct, length);
            }
            for (int a = 0; a < length; ++a) {
                Array.set(copy, a, this.min + (long)a);
            }
            if (length < copy.length) {
                copy[length] = null;
            }
            return copy;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object cc : c) {
            if (this.contains(cc)) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean add(Long e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Long> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public static class Descending
    extends LongRange {
        protected Descending(long from, long to) {
            super(from, to);
        }

        @Override
        public Iterator<Long> iterator() {
            return new DescLongIterator(this.min, this.max);
        }
    }

    public static class Ascending
    extends LongRange {
        protected Ascending(long from, long to) {
            super(from, to);
        }

        @Override
        public Iterator<Long> iterator() {
            return new AscLongIterator(this.min, this.max);
        }
    }
}


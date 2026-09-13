/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.jexl3.internal.AscIntegerIterator;
import org.apache.commons.jexl3.internal.DescIntegerIterator;

public abstract class IntegerRange
implements Collection<Integer> {
    protected final int min;
    protected final int max;

    public static IntegerRange create(int from, int to) {
        if (from <= to) {
            return new Ascending(from, to);
        }
        return new Descending(to, from);
    }

    public IntegerRange(int from, int to) {
        this.min = from;
        this.max = to;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    @Override
    public int hashCode() {
        int hash = this.getClass().hashCode();
        hash = 13 * hash + this.min;
        hash = 13 * hash + this.max;
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
        IntegerRange other = (IntegerRange)obj;
        if (this.min != other.min) {
            return false;
        }
        return this.max == other.max;
    }

    @Override
    public abstract Iterator<Integer> iterator();

    @Override
    public int size() {
        return this.max - this.min + 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof Number) {
            long v = ((Number)o).intValue();
            return (long)this.min <= v && v <= (long)this.max;
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        int size = this.size();
        Object[] array = new Object[size];
        for (int a = 0; a < size; ++a) {
            array[a] = this.min + a;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Class<Integer> ct = array.getClass().getComponentType();
        int length = this.size();
        Object[] copy = array;
        if (ct.isAssignableFrom(Integer.class)) {
            if (array.length < length) {
                copy = (Object[])Array.newInstance(ct, length);
            }
            for (int a = 0; a < length; ++a) {
                Array.set(copy, a, this.min + a);
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
    public boolean add(Integer e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
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
    extends IntegerRange {
        protected Descending(int from, int to) {
            super(from, to);
        }

        @Override
        public Iterator<Integer> iterator() {
            return new DescIntegerIterator(this.min, this.max);
        }
    }

    public static class Ascending
    extends IntegerRange {
        protected Ascending(int from, int to) {
            super(from, to);
        }

        @Override
        public Iterator<Integer> iterator() {
            return new AscIntegerIterator(this.min, this.max);
        }
    }
}


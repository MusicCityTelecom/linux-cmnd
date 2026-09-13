/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator
implements Iterator<Object> {
    private final Object array;
    private final int size;
    private int pos;

    public ArrayIterator(Object arr) {
        if (arr == null) {
            this.array = null;
            this.pos = 0;
            this.size = 0;
        } else {
            if (!arr.getClass().isArray()) {
                throw new IllegalArgumentException(arr.getClass() + " is not an array");
            }
            this.array = arr;
            this.pos = 0;
            this.size = Array.getLength(this.array);
        }
    }

    @Override
    public Object next() {
        if (this.pos < this.size) {
            return Array.get(this.array, this.pos++);
        }
        throw new NoSuchElementException("No more elements: " + this.pos + " / " + this.size);
    }

    @Override
    public boolean hasNext() {
        return this.pos < this.size;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}


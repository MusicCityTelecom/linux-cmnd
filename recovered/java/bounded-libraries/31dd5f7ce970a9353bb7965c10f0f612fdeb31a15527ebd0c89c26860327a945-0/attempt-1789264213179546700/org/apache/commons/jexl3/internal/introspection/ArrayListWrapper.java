/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.RandomAccess;

public class ArrayListWrapper
extends AbstractList<Object>
implements RandomAccess {
    private final Object array;

    public ArrayListWrapper(Object anArray) {
        if (!anArray.getClass().isArray()) {
            throw new IllegalArgumentException(anArray.getClass() + " is not an array");
        }
        this.array = anArray;
    }

    @Override
    public Object get(int index) {
        return Array.get(this.array, index);
    }

    @Override
    public Object set(int index, Object element) {
        Object old = Array.get(this.array, index);
        Array.set(this.array, index, element);
        return old;
    }

    @Override
    public int size() {
        return Array.getLength(this.array);
    }

    @Override
    public int indexOf(Object o) {
        int size = this.size();
        if (o == null) {
            for (int i = 0; i < size; ++i) {
                if (this.get(i) != null) continue;
                return i;
            }
        } else {
            for (int i = 0; i < size; ++i) {
                if (!o.equals(this.get(i))) continue;
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) != -1;
    }
}


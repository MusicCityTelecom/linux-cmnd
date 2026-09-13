/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import groovy.util.BufferedIterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ListBufferedIterator<T>
implements BufferedIterator<T> {
    private final List<T> list;
    private final ListIterator<T> iter;

    public ListBufferedIterator(List<T> list) {
        this.list = list;
        this.iter = list.listIterator();
    }

    @Override
    public boolean hasNext() {
        return this.iter.hasNext();
    }

    @Override
    public T next() {
        return this.iter.next();
    }

    @Override
    public void remove() {
        this.iter.remove();
    }

    @Override
    public T head() {
        int index = this.iter.nextIndex();
        if (index >= this.list.size()) {
            throw new NoSuchElementException();
        }
        return this.list.get(index);
    }
}


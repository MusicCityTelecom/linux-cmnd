/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

class DescIntegerIterator
implements Iterator<Integer> {
    private final int min;
    private final int max;
    private int cursor;

    public DescIntegerIterator(int l, int h) {
        this.min = l;
        this.cursor = this.max = h;
    }

    @Override
    public boolean hasNext() {
        return this.cursor >= this.min;
    }

    @Override
    public Integer next() {
        if (this.cursor >= this.min) {
            return this.cursor--;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }
}


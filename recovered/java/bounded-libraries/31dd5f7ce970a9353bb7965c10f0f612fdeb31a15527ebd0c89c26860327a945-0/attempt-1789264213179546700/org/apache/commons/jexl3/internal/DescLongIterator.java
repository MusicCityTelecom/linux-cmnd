/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

class DescLongIterator
implements Iterator<Long> {
    private final long min;
    private final long max;
    private long cursor;

    DescLongIterator(long l, long h) {
        this.min = l;
        this.cursor = this.max = h;
    }

    @Override
    public boolean hasNext() {
        return this.cursor >= this.min;
    }

    @Override
    public Long next() {
        if (this.cursor >= this.min) {
            return this.cursor--;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}


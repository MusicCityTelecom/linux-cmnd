/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

class AscLongIterator
implements Iterator<Long> {
    private final long min;
    private final long max;
    private long cursor;

    AscLongIterator(long l, long h) {
        this.min = l;
        this.max = h;
        this.cursor = this.min;
    }

    @Override
    public boolean hasNext() {
        return this.cursor <= this.max;
    }

    @Override
    public Long next() {
        if (this.cursor <= this.max) {
            return this.cursor++;
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}


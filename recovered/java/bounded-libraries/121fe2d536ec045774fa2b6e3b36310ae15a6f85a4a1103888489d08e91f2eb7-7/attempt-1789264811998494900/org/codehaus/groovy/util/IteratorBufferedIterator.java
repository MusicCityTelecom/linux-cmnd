/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import groovy.util.BufferedIterator;
import java.util.Iterator;

public class IteratorBufferedIterator<T>
implements BufferedIterator<T> {
    private final Iterator<T> iter;
    private boolean hasBuffered;
    private T buffered;

    public IteratorBufferedIterator(Iterator<T> iter) {
        this.iter = iter;
        this.hasBuffered = false;
    }

    @Override
    public boolean hasNext() {
        return this.hasBuffered || this.iter.hasNext();
    }

    @Override
    public T next() {
        if (this.hasBuffered) {
            T buffered = this.buffered;
            this.buffered = null;
            this.hasBuffered = false;
            return buffered;
        }
        return this.iter.next();
    }

    @Override
    public void remove() {
        if (this.hasBuffered) {
            throw new IllegalStateException("Can't remove from " + this + " when an item is buffered.");
        }
        this.iter.remove();
    }

    @Override
    public T head() {
        if (!this.hasBuffered) {
            this.buffered = this.iter.next();
            this.hasBuffered = true;
        }
        return this.buffered;
    }
}


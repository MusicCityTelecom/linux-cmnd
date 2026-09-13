/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal.introspection;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationIterator<T>
implements Iterator<T> {
    private final Enumeration<T> enumeration;

    public EnumerationIterator(Enumeration<T> enumer) {
        this.enumeration = enumer;
    }

    @Override
    public T next() {
        return this.enumeration.nextElement();
    }

    @Override
    public boolean hasNext() {
        return this.enumeration.hasMoreElements();
    }

    @Override
    public void remove() {
    }
}


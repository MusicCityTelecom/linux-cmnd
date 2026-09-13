/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

public class ReversedList<E>
extends AbstractList<E>
implements RandomAccess,
Serializable {
    private static final long serialVersionUID = -1640781973848935560L;
    private final List<E> delegate;

    public ReversedList(List<E> list) {
        this.delegate = list;
    }

    @Override
    public E get(int index) {
        return this.delegate.get(this.delegate.size() - 1 - index);
    }

    @Override
    public int size() {
        return this.delegate.size();
    }
}


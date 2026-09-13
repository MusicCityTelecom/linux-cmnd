/*
 * Decompiled with CFR 0.152.
 */
package de.cronn.reflection.util.immutable.collection;

import de.cronn.reflection.util.immutable.collection.DeepImmutableList;
import java.util.ListIterator;

class ImmutableListIterator<E>
implements ListIterator<E> {
    private final DeepImmutableList<E> list;
    private final ListIterator<E> delegate;

    ImmutableListIterator(DeepImmutableList<E> list, ListIterator<E> delegate) {
        this.list = list;
        this.delegate = delegate;
    }

    @Override
    public boolean hasNext() {
        return this.delegate.hasNext();
    }

    @Override
    public E next() {
        E element = this.delegate.next();
        return this.list.getImmutableElement(element);
    }

    @Override
    public boolean hasPrevious() {
        return this.delegate.hasPrevious();
    }

    @Override
    public E previous() {
        E element = this.delegate.previous();
        return this.list.getImmutableElement(element);
    }

    @Override
    public int nextIndex() {
        return this.delegate.nextIndex();
    }

    @Override
    public int previousIndex() {
        return this.delegate.previousIndex();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("This list is immutable");
    }

    @Override
    public void set(E t) {
        throw new UnsupportedOperationException("This list is immutable");
    }

    @Override
    public void add(E t) {
        throw new UnsupportedOperationException("This list is immutable");
    }
}


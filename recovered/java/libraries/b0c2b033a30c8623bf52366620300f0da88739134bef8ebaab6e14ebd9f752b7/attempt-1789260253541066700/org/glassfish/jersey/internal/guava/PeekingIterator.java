/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Iterator;

public interface PeekingIterator<E>
extends Iterator<E> {
    public E peek();

    @Override
    public E next();

    @Override
    public void remove();
}


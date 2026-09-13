/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Iterator;

public abstract class UnmodifiableIterator<E>
implements Iterator<E> {
    UnmodifiableIterator() {
    }

    @Override
    @Deprecated
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}


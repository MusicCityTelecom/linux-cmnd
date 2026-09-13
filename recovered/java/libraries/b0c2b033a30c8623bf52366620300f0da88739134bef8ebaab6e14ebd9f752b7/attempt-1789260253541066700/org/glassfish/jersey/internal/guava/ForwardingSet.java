/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.util.Set;
import org.glassfish.jersey.internal.guava.ForwardingCollection;

public abstract class ForwardingSet<E>
extends ForwardingCollection<E>
implements Set<E> {
    ForwardingSet() {
    }

    @Override
    protected abstract Set<E> delegate();

    @Override
    public boolean equals(Object object) {
        return object == this || this.delegate().equals(object);
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.io.Serializable;
import java.util.Comparator;
import org.glassfish.jersey.internal.guava.Ordering;
import org.glassfish.jersey.internal.guava.Preconditions;

final class ComparatorOrdering<T>
extends Ordering<T>
implements Serializable {
    private static final long serialVersionUID = 0L;
    private final Comparator<T> comparator;

    ComparatorOrdering(Comparator<T> comparator) {
        this.comparator = Preconditions.checkNotNull(comparator);
    }

    @Override
    public int compare(T a, T b) {
        return this.comparator.compare(a, b);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof ComparatorOrdering) {
            ComparatorOrdering that = (ComparatorOrdering)object;
            return this.comparator.equals(that.comparator);
        }
        return false;
    }

    public int hashCode() {
        return this.comparator.hashCode();
    }

    public String toString() {
        return this.comparator.toString();
    }
}


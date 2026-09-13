/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators;

import java.util.Comparator;

public abstract class DelegatingComparator<T>
implements Comparator<T> {
    private final Comparator<? super T> comparator;

    public DelegatingComparator(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(T type1, T type2) {
        return this.comparator.compare(type1, type2);
    }
}


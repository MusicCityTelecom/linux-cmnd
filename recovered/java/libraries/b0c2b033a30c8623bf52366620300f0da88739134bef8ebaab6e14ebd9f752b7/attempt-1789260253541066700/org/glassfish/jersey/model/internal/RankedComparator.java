/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model.internal;

import java.util.Comparator;
import org.glassfish.jersey.model.internal.RankedProvider;

public class RankedComparator<T>
implements Comparator<RankedProvider<T>> {
    private final Order order;

    public RankedComparator() {
        this(Order.ASCENDING);
    }

    public RankedComparator(Order order) {
        this.order = order;
    }

    @Override
    public int compare(RankedProvider<T> o1, RankedProvider<T> o2) {
        return this.getPriority(o1) > this.getPriority(o2) ? this.order.ordering : -this.order.ordering;
    }

    protected int getPriority(RankedProvider<T> rankedProvider) {
        return rankedProvider.getRank();
    }

    public static enum Order {
        ASCENDING(1),
        DESCENDING(-1);

        private final int ordering;

        private Order(int ordering) {
            this.ordering = ordering;
        }
    }
}


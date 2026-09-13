/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.springframework.integration.util.CollectionFilter;

public class AcceptOnceCollectionFilter<T>
implements CollectionFilter<T> {
    private volatile Collection<T> lastSeenElements = Collections.emptyList();

    @Override
    public synchronized Collection<T> filter(Collection<T> unfilteredElements) {
        ArrayList<T> filteredElements = new ArrayList<T>();
        for (T element : unfilteredElements) {
            if (this.lastSeenElements.contains(element)) continue;
            filteredElements.add(element);
        }
        this.lastSeenElements = unfilteredElements;
        return filteredElements;
    }
}


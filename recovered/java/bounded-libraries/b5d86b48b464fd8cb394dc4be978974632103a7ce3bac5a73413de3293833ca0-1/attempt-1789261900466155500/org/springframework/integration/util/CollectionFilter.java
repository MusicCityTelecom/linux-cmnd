/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.util;

import java.util.Collection;

@FunctionalInterface
public interface CollectionFilter<T> {
    public Collection<T> filter(Collection<T> var1);
}


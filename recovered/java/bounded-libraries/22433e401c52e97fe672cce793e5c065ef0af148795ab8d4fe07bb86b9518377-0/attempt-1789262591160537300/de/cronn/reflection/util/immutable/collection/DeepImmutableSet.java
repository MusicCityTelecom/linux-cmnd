/*
 * Decompiled with CFR 0.152.
 */
package de.cronn.reflection.util.immutable.collection;

import de.cronn.reflection.util.immutable.collection.DeepImmutableCollection;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class DeepImmutableSet<E>
extends DeepImmutableCollection<E>
implements Set<E> {
    private static final long serialVersionUID = 1L;

    public DeepImmutableSet(Set<E> set) {
        super(set, "This set is immutable");
    }

    public static <T> DeepImmutableSet<T> of(T element) {
        return new DeepImmutableSet<T>(Collections.singleton(element));
    }

    public static <T> DeepImmutableSet<T> of(T e1, T e2) {
        return new DeepImmutableSet<Object>((Set<Object>)new LinkedHashSet<Object>(Arrays.asList(e1, e2)));
    }

    public static <T> DeepImmutableSet<T> of(T e1, T e2, T e3) {
        return new DeepImmutableSet<Object>((Set<Object>)new LinkedHashSet<Object>(Arrays.asList(e1, e2, e3)));
    }
}


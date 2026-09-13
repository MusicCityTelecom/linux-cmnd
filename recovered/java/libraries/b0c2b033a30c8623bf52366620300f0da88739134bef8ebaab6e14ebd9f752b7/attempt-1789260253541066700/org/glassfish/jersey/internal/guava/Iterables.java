/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import org.glassfish.jersey.internal.guava.Iterators;

final class Iterables {
    private Iterables() {
    }

    public static <T> T getFirst(Iterable<? extends T> iterable, T defaultValue) {
        return Iterators.getNext(iterable.iterator(), defaultValue);
    }
}


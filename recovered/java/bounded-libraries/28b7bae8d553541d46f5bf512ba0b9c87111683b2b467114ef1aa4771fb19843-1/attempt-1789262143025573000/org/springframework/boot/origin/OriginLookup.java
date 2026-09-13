/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.origin;

import org.springframework.boot.origin.Origin;

@FunctionalInterface
public interface OriginLookup<K> {
    public Origin getOrigin(K var1);

    default public boolean isImmutable() {
        return false;
    }

    default public String getPrefix() {
        return null;
    }

    public static <K> Origin getOrigin(Object source, K key) {
        if (!(source instanceof OriginLookup)) {
            return null;
        }
        try {
            return ((OriginLookup)source).getOrigin(key);
        }
        catch (Throwable ex) {
            return null;
        }
    }
}


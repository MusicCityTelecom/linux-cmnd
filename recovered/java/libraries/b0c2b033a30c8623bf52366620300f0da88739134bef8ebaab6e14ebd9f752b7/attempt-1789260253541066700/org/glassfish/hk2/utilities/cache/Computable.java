/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.cache;

import org.glassfish.hk2.utilities.cache.ComputationErrorException;

public interface Computable<K, V> {
    public V compute(K var1) throws ComputationErrorException;
}


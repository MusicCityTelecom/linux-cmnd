/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.cache;

import org.glassfish.hk2.utilities.cache.CacheKeyFilter;
import org.glassfish.hk2.utilities.cache.Computable;

public interface WeakCARCache<K, V> {
    public V compute(K var1);

    public int getKeySize();

    public int getValueSize();

    public int getT1Size();

    public int getT2Size();

    public int getB1Size();

    public int getB2Size();

    public void clear();

    public int getMaxSize();

    public Computable<K, V> getComputable();

    public boolean remove(K var1);

    public void releaseMatching(CacheKeyFilter<K> var1);

    public void clearStaleReferences();

    public int getP();

    public String dumpAllLists();

    public double getHitRate();
}


/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.pool2.impl;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.EvictionConfig;
import org.apache.commons.pool2.impl.EvictionPolicy;

public class DefaultEvictionPolicy<T>
implements EvictionPolicy<T> {
    @Override
    public boolean evict(EvictionConfig config, PooledObject<T> underTest, int idleCount) {
        return config.getIdleSoftEvictDuration().compareTo(underTest.getIdleDuration()) < 0 && config.getMinIdle() < idleCount || config.getIdleEvictDuration().compareTo(underTest.getIdleDuration()) < 0;
    }
}


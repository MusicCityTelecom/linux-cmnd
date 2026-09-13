/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.cache.internal;

import org.hibernate.cache.spi.AbstractCacheTransactionSynchronization;
import org.hibernate.cache.spi.RegionFactory;

public class NoCachingTransactionSynchronizationImpl
extends AbstractCacheTransactionSynchronization {
    public NoCachingTransactionSynchronizationImpl(RegionFactory regionFactory) {
        super(regionFactory);
    }
}


/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.cache.spi;

public interface CacheTransactionSynchronization {
    public long getCurrentTransactionStartTimestamp();

    public void transactionJoined();

    public void transactionCompleting();

    public void transactionCompleted(boolean var1);

    default public void transactionSuspended() {
    }

    default public void transactionResumed() {
    }
}


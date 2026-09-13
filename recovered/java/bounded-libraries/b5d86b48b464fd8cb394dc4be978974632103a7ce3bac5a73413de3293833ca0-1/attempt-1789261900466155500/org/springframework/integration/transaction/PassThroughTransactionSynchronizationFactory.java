/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.support.TransactionSynchronization
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transaction;

import org.springframework.integration.transaction.IntegrationResourceHolder;
import org.springframework.integration.transaction.IntegrationResourceHolderSynchronization;
import org.springframework.integration.transaction.TransactionSynchronizationFactory;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.util.Assert;

public class PassThroughTransactionSynchronizationFactory
implements TransactionSynchronizationFactory {
    @Override
    public TransactionSynchronization create(Object key) {
        Assert.notNull((Object)key, (String)"'key' must not be null");
        return new IntegrationResourceHolderSynchronization(new IntegrationResourceHolder(), key);
    }
}


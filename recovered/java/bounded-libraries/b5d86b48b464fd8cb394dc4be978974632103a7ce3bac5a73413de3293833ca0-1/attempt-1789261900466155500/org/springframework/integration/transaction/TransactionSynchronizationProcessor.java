/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.transaction;

import org.springframework.integration.transaction.IntegrationResourceHolder;

public interface TransactionSynchronizationProcessor {
    public void processBeforeCommit(IntegrationResourceHolder var1);

    public void processAfterCommit(IntegrationResourceHolder var1);

    public void processAfterRollback(IntegrationResourceHolder var1);
}


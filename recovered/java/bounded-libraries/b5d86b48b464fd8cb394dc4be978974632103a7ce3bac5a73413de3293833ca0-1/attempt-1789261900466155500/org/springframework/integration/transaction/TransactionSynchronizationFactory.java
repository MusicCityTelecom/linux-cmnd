/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.support.TransactionSynchronization
 */
package org.springframework.integration.transaction;

import org.springframework.transaction.support.TransactionSynchronization;

public interface TransactionSynchronizationFactory {
    public TransactionSynchronization create(Object var1);
}


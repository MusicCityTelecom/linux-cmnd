/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionException
 *  org.springframework.transaction.support.AbstractPlatformTransactionManager
 *  org.springframework.transaction.support.DefaultTransactionStatus
 */
package org.springframework.integration.transaction;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class PseudoTransactionManager
extends AbstractPlatformTransactionManager {
    private static final long serialVersionUID = 1L;

    protected Object doGetTransaction() throws TransactionException {
        return new Object();
    }

    protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
    }

    protected void doCommit(DefaultTransactionStatus status) throws TransactionException {
    }

    protected void doRollback(DefaultTransactionStatus status) throws TransactionException {
    }
}


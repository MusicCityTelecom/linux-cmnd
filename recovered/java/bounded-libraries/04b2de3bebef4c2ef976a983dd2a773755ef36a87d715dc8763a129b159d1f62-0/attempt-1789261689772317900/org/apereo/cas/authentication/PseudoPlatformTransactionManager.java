/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionException
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionStatus
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class PseudoPlatformTransactionManager
implements PlatformTransactionManager,
Serializable {
    private static final long serialVersionUID = -3501861804821200893L;

    public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {
        return new DefaultTransactionStatus(new Object(), true, true, false, false, new Object());
    }

    public void commit(TransactionStatus transactionStatus) throws TransactionException {
    }

    public void rollback(TransactionStatus transactionStatus) throws TransactionException {
    }
}


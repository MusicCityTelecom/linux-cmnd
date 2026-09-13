/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.Transaction
 *  org.jooq.TransactionContext
 *  org.jooq.TransactionProvider
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package org.springframework.boot.autoconfigure.jooq;

import org.jooq.Transaction;
import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.springframework.boot.autoconfigure.jooq.SpringTransaction;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class SpringTransactionProvider
implements TransactionProvider {
    private final PlatformTransactionManager transactionManager;

    public SpringTransactionProvider(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void begin(TransactionContext context) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition(6);
        TransactionStatus status = this.transactionManager.getTransaction((TransactionDefinition)definition);
        context.transaction((Transaction)new SpringTransaction(status));
    }

    public void commit(TransactionContext ctx) {
        this.transactionManager.commit(this.getTransactionStatus(ctx));
    }

    public void rollback(TransactionContext ctx) {
        this.transactionManager.rollback(this.getTransactionStatus(ctx));
    }

    private TransactionStatus getTransactionStatus(TransactionContext ctx) {
        SpringTransaction transaction = (SpringTransaction)ctx.transaction();
        return transaction.getTxStatus();
    }
}


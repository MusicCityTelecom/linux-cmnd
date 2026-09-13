/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jooq.Transaction
 *  org.springframework.transaction.TransactionStatus
 */
package org.springframework.boot.autoconfigure.jooq;

import org.jooq.Transaction;
import org.springframework.transaction.TransactionStatus;

class SpringTransaction
implements Transaction {
    private final TransactionStatus transactionStatus;

    SpringTransaction(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    TransactionStatus getTxStatus() {
        return this.transactionStatus;
    }
}


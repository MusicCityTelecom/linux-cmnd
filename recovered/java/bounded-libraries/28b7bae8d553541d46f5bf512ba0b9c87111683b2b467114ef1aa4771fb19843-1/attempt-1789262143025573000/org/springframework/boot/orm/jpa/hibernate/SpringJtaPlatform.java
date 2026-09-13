/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.transaction.TransactionManager
 *  javax.transaction.UserTransaction
 *  org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform
 *  org.springframework.transaction.jta.JtaTransactionManager
 *  org.springframework.util.Assert
 */
package org.springframework.boot.orm.jpa.hibernate;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.Assert;

public class SpringJtaPlatform
extends AbstractJtaPlatform {
    private static final long serialVersionUID = 1L;
    private final JtaTransactionManager transactionManager;

    public SpringJtaPlatform(JtaTransactionManager transactionManager) {
        Assert.notNull((Object)transactionManager, (String)"TransactionManager must not be null");
        this.transactionManager = transactionManager;
    }

    protected TransactionManager locateTransactionManager() {
        return this.transactionManager.getTransactionManager();
    }

    protected UserTransaction locateUserTransaction() {
        return this.transactionManager.getUserTransaction();
    }
}


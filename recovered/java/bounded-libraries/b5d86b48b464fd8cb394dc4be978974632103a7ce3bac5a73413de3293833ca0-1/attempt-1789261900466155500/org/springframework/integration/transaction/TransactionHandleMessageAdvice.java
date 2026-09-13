/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.interceptor.TransactionAttributeSource
 *  org.springframework.transaction.interceptor.TransactionInterceptor
 */
package org.springframework.integration.transaction;

import java.util.Properties;
import org.springframework.integration.handler.advice.HandleMessageAdvice;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

public class TransactionHandleMessageAdvice
extends TransactionInterceptor
implements HandleMessageAdvice {
    public TransactionHandleMessageAdvice() {
    }

    public TransactionHandleMessageAdvice(TransactionManager transactionManager, Properties transactionAttributes) {
        this.setTransactionManager(transactionManager);
        this.setTransactionAttributes(transactionAttributes);
    }

    public TransactionHandleMessageAdvice(TransactionManager transactionManager, TransactionAttributeSource transactionAttributeSource) {
        super(transactionManager, transactionAttributeSource);
    }
}


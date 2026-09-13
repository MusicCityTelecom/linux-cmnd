/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.annotation.Isolation
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.interceptor.DefaultTransactionAttribute
 *  org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource
 *  org.springframework.transaction.interceptor.TransactionAttribute
 *  org.springframework.transaction.interceptor.TransactionAttributeSource
 *  org.springframework.transaction.interceptor.TransactionInterceptor
 *  org.springframework.util.Assert
 */
package org.springframework.integration.transaction;

import org.springframework.integration.transaction.TransactionHandleMessageAdvice;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.Assert;

public class TransactionInterceptorBuilder {
    private final DefaultTransactionAttribute transactionAttribute = new DefaultTransactionAttribute();
    private final TransactionInterceptor transactionInterceptor;

    public TransactionInterceptorBuilder() {
        this(false);
    }

    public TransactionInterceptorBuilder(boolean handleMessageAdvice) {
        this.transactionInterceptor = handleMessageAdvice ? new TransactionHandleMessageAdvice() : new TransactionInterceptor();
        this.transactionAttribute((TransactionAttribute)this.transactionAttribute);
    }

    public TransactionInterceptorBuilder propagation(Propagation propagation) {
        Assert.notNull((Object)propagation, (String)"'propagation' must not be null.");
        this.transactionAttribute.setPropagationBehavior(propagation.value());
        return this;
    }

    public TransactionInterceptorBuilder isolation(Isolation isolation) {
        Assert.notNull((Object)isolation, (String)"'isolation' must not be null.");
        this.transactionAttribute.setIsolationLevel(isolation.value());
        return this;
    }

    public TransactionInterceptorBuilder timeout(int timeout) {
        this.transactionAttribute.setTimeout(timeout);
        return this;
    }

    public TransactionInterceptorBuilder readOnly(boolean readOnly) {
        this.transactionAttribute.setReadOnly(readOnly);
        return this;
    }

    public final TransactionInterceptorBuilder transactionAttribute(TransactionAttribute transactionAttribute) {
        MatchAlwaysTransactionAttributeSource txAttributeSource = new MatchAlwaysTransactionAttributeSource();
        txAttributeSource.setTransactionAttribute(transactionAttribute);
        this.transactionInterceptor.setTransactionAttributeSource((TransactionAttributeSource)txAttributeSource);
        return this;
    }

    public TransactionInterceptorBuilder transactionManager(TransactionManager transactionManager) {
        this.transactionInterceptor.setTransactionManager(transactionManager);
        return this;
    }

    public TransactionInterceptor build() {
        return this.transactionInterceptor;
    }
}


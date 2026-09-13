/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.scheduling.Trigger
 *  org.springframework.util.Assert
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.integration.scheduling;

import java.util.List;
import java.util.concurrent.Executor;
import org.aopalliance.aop.Advice;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.integration.transaction.TransactionSynchronizationFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.util.Assert;
import org.springframework.util.ErrorHandler;

public class PollerMetadata {
    public static final int MAX_MESSAGES_UNBOUNDED = Integer.MIN_VALUE;
    public static final long DEFAULT_RECEIVE_TIMEOUT = 1000L;
    public static final String DEFAULT_POLLER_METADATA_BEAN_NAME = "org.springframework.integration.context.defaultPollerMetadata";
    public static final String DEFAULT_POLLER = "org.springframework.integration.context.defaultPollerMetadata";
    private Trigger trigger;
    private long maxMessagesPerPoll = Integer.MIN_VALUE;
    private long receiveTimeout = 1000L;
    private ErrorHandler errorHandler;
    private List<Advice> adviceChain;
    private Executor taskExecutor;
    private long sendTimeout;
    private TransactionSynchronizationFactory transactionSynchronizationFactory;

    public void setTransactionSynchronizationFactory(TransactionSynchronizationFactory transactionSynchronizationFactory) {
        Assert.notNull((Object)transactionSynchronizationFactory, (String)"'transactionSynchronizationFactory' must not be null");
        this.transactionSynchronizationFactory = transactionSynchronizationFactory;
    }

    public TransactionSynchronizationFactory getTransactionSynchronizationFactory() {
        return this.transactionSynchronizationFactory;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }

    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setMaxMessagesPerPoll(long maxMessagesPerPoll) {
        this.maxMessagesPerPoll = maxMessagesPerPoll;
    }

    public long getMaxMessagesPerPoll() {
        return this.maxMessagesPerPoll;
    }

    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public long getReceiveTimeout() {
        return this.receiveTimeout;
    }

    public void setAdviceChain(List<Advice> adviceChain) {
        this.adviceChain = adviceChain;
    }

    public List<Advice> getAdviceChain() {
        return this.adviceChain;
    }

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public Executor getTaskExecutor() {
        return this.taskExecutor;
    }

    public long getSendTimeout() {
        return this.sendTimeout;
    }

    public void setSendTimeout(long sendTimeout) {
        this.sendTimeout = sendTimeout;
    }

    public static PollerMetadata getDefaultPollerMetadata(BeanFactory beanFactory) {
        Assert.notNull((Object)beanFactory, (String)"BeanFactory must not be null");
        if (!beanFactory.containsBean("org.springframework.integration.context.defaultPollerMetadata")) {
            return null;
        }
        return (PollerMetadata)beanFactory.getBean("org.springframework.integration.context.defaultPollerMetadata", PollerMetadata.class);
    }
}


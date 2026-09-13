/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.aop.Advice
 *  org.springframework.messaging.MessageChannel
 *  org.springframework.scheduling.Trigger
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.interceptor.TransactionInterceptor
 *  org.springframework.util.ErrorHandler
 */
package org.springframework.integration.dsl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import org.aopalliance.aop.Advice;
import org.springframework.integration.channel.MessagePublishingErrorHandler;
import org.springframework.integration.dsl.ComponentsRegistration;
import org.springframework.integration.dsl.IntegrationComponentSpec;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transaction.TransactionInterceptorBuilder;
import org.springframework.integration.transaction.TransactionSynchronizationFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.Trigger;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.util.ErrorHandler;

public final class PollerSpec
extends IntegrationComponentSpec<PollerSpec, PollerMetadata>
implements ComponentsRegistration {
    private final List<Advice> adviceChain = new LinkedList<Advice>();
    private final Map<Object, String> componentsToRegister = new LinkedHashMap<Object, String>();

    PollerSpec(Trigger trigger2) {
        this.target = new PollerMetadata();
        ((PollerMetadata)this.target).setAdviceChain(this.adviceChain);
        ((PollerMetadata)this.target).setTrigger(trigger2);
    }

    public PollerSpec transactionSynchronizationFactory(TransactionSynchronizationFactory transactionSynchronizationFactory) {
        ((PollerMetadata)this.target).setTransactionSynchronizationFactory(transactionSynchronizationFactory);
        return this;
    }

    public PollerSpec errorHandler(ErrorHandler errorHandler) {
        ((PollerMetadata)this.target).setErrorHandler(errorHandler);
        return this;
    }

    public PollerSpec errorChannel(MessageChannel errorChannel) {
        MessagePublishingErrorHandler errorHandler = new MessagePublishingErrorHandler();
        errorHandler.setDefaultErrorChannel(errorChannel);
        this.componentsToRegister.put(errorHandler, null);
        return this.errorHandler(errorHandler);
    }

    public PollerSpec errorChannel(String errorChannelName) {
        MessagePublishingErrorHandler errorHandler = new MessagePublishingErrorHandler();
        errorHandler.setDefaultErrorChannelName(errorChannelName);
        this.componentsToRegister.put(errorHandler, null);
        return this.errorHandler(errorHandler);
    }

    public PollerSpec maxMessagesPerPoll(long maxMessagesPerPoll) {
        ((PollerMetadata)this.target).setMaxMessagesPerPoll(maxMessagesPerPoll);
        return this;
    }

    public PollerSpec receiveTimeout(long receiveTimeout) {
        ((PollerMetadata)this.target).setReceiveTimeout(receiveTimeout);
        return this;
    }

    public PollerSpec advice(Advice ... advice) {
        this.adviceChain.addAll(Arrays.asList(advice));
        return this;
    }

    public PollerSpec transactional(TransactionManager transactionManager) {
        return this.transactional(new TransactionInterceptorBuilder().transactionManager(transactionManager).build());
    }

    public PollerSpec transactional() {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptorBuilder().build();
        this.componentsToRegister.put(transactionInterceptor, null);
        return this.transactional(transactionInterceptor);
    }

    public PollerSpec transactional(TransactionInterceptor transactionInterceptor) {
        return this.advice(new Advice[]{transactionInterceptor});
    }

    public PollerSpec taskExecutor(Executor taskExecutor) {
        ((PollerMetadata)this.target).setTaskExecutor(taskExecutor);
        return this;
    }

    public PollerSpec sendTimeout(long sendTimeout) {
        ((PollerMetadata)this.target).setSendTimeout(sendTimeout);
        return this;
    }

    @Override
    public Map<Object, String> getComponentsToRegister() {
        return this.componentsToRegister;
    }
}


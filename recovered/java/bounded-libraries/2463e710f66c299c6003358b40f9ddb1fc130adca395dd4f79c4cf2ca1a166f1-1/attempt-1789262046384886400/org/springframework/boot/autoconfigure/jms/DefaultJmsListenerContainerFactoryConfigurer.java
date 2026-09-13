/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  javax.jms.ExceptionListener
 *  org.springframework.jms.config.DefaultJmsListenerContainerFactory
 *  org.springframework.jms.support.converter.MessageConverter
 *  org.springframework.jms.support.destination.DestinationResolver
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.jta.JtaTransactionManager
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.jms;

import java.time.Duration;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.Assert;

public final class DefaultJmsListenerContainerFactoryConfigurer {
    private DestinationResolver destinationResolver;
    private MessageConverter messageConverter;
    private ExceptionListener exceptionListener;
    private JtaTransactionManager transactionManager;
    private JmsProperties jmsProperties;

    void setDestinationResolver(DestinationResolver destinationResolver) {
        this.destinationResolver = destinationResolver;
    }

    void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    void setExceptionListener(ExceptionListener exceptionListener) {
        this.exceptionListener = exceptionListener;
    }

    void setTransactionManager(JtaTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    void setJmsProperties(JmsProperties jmsProperties) {
        this.jmsProperties = jmsProperties;
    }

    public void configure(DefaultJmsListenerContainerFactory factory, ConnectionFactory connectionFactory) {
        Duration receiveTimeout;
        String concurrency;
        Assert.notNull((Object)factory, (String)"Factory must not be null");
        Assert.notNull((Object)connectionFactory, (String)"ConnectionFactory must not be null");
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(Boolean.valueOf(this.jmsProperties.isPubSubDomain()));
        if (this.transactionManager != null) {
            factory.setTransactionManager((PlatformTransactionManager)this.transactionManager);
        } else {
            factory.setSessionTransacted(Boolean.valueOf(true));
        }
        if (this.destinationResolver != null) {
            factory.setDestinationResolver(this.destinationResolver);
        }
        if (this.messageConverter != null) {
            factory.setMessageConverter(this.messageConverter);
        }
        if (this.exceptionListener != null) {
            factory.setExceptionListener(this.exceptionListener);
        }
        JmsProperties.Listener listener = this.jmsProperties.getListener();
        factory.setAutoStartup(listener.isAutoStartup());
        if (listener.getAcknowledgeMode() != null) {
            factory.setSessionAcknowledgeMode(Integer.valueOf(listener.getAcknowledgeMode().getMode()));
        }
        if ((concurrency = listener.formatConcurrency()) != null) {
            factory.setConcurrency(concurrency);
        }
        if ((receiveTimeout = listener.getReceiveTimeout()) != null) {
            factory.setReceiveTimeout(Long.valueOf(receiveTimeout.toMillis()));
        }
    }
}


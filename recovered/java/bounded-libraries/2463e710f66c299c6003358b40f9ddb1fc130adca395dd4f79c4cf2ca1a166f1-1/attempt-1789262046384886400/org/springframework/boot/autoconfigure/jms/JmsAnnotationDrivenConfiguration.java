/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  javax.jms.ExceptionListener
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.jms.annotation.EnableJms
 *  org.springframework.jms.config.DefaultJmsListenerContainerFactory
 *  org.springframework.jms.support.converter.MessageConverter
 *  org.springframework.jms.support.destination.DestinationResolver
 *  org.springframework.jms.support.destination.JndiDestinationResolver
 *  org.springframework.transaction.jta.JtaTransactionManager
 */
package org.springframework.boot.autoconfigure.jms;

import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJndi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.destination.DestinationResolver;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={EnableJms.class})
class JmsAnnotationDrivenConfiguration {
    private final ObjectProvider<DestinationResolver> destinationResolver;
    private final ObjectProvider<JtaTransactionManager> transactionManager;
    private final ObjectProvider<MessageConverter> messageConverter;
    private final ObjectProvider<ExceptionListener> exceptionListener;
    private final JmsProperties properties;

    JmsAnnotationDrivenConfiguration(ObjectProvider<DestinationResolver> destinationResolver, ObjectProvider<JtaTransactionManager> transactionManager, ObjectProvider<MessageConverter> messageConverter, ObjectProvider<ExceptionListener> exceptionListener, JmsProperties properties) {
        this.destinationResolver = destinationResolver;
        this.transactionManager = transactionManager;
        this.messageConverter = messageConverter;
        this.exceptionListener = exceptionListener;
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    DefaultJmsListenerContainerFactoryConfigurer jmsListenerContainerFactoryConfigurer() {
        DefaultJmsListenerContainerFactoryConfigurer configurer = new DefaultJmsListenerContainerFactoryConfigurer();
        configurer.setDestinationResolver((DestinationResolver)this.destinationResolver.getIfUnique());
        configurer.setTransactionManager((JtaTransactionManager)this.transactionManager.getIfUnique());
        configurer.setMessageConverter((MessageConverter)this.messageConverter.getIfUnique());
        configurer.setExceptionListener((ExceptionListener)this.exceptionListener.getIfUnique());
        configurer.setJmsProperties(this.properties);
        return configurer;
    }

    @Bean
    @ConditionalOnSingleCandidate(value=ConnectionFactory.class)
    @ConditionalOnMissingBean(name={"jmsListenerContainerFactory"})
    DefaultJmsListenerContainerFactory jmsListenerContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnJndi
    static class JndiConfiguration {
        JndiConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={DestinationResolver.class})
        JndiDestinationResolver destinationResolver() {
            JndiDestinationResolver resolver = new JndiDestinationResolver();
            resolver.setFallbackToDynamicDestination(true);
            return resolver;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @EnableJms
    @ConditionalOnMissingBean(name={"org.springframework.jms.config.internalJmsListenerAnnotationProcessor"})
    static class EnableJmsConfiguration {
        EnableJmsConfiguration() {
        }
    }
}


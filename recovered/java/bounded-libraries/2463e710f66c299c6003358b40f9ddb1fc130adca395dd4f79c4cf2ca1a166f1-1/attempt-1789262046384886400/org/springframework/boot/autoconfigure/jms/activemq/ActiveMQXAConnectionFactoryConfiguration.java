/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  javax.jms.XAConnectionFactory
 *  javax.transaction.TransactionManager
 *  org.apache.activemq.ActiveMQConnectionFactory
 *  org.apache.activemq.ActiveMQXAConnectionFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.jms.XAConnectionFactoryWrapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Primary
 */
package org.springframework.boot.autoconfigure.jms.activemq;

import java.util.stream.Collectors;
import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;
import javax.transaction.TransactionManager;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryCustomizer;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.boot.jms.XAConnectionFactoryWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={TransactionManager.class})
@ConditionalOnBean(value={XAConnectionFactoryWrapper.class})
@ConditionalOnMissingBean(value={ConnectionFactory.class})
class ActiveMQXAConnectionFactoryConfiguration {
    ActiveMQXAConnectionFactoryConfiguration() {
    }

    @Primary
    @Bean(name={"jmsConnectionFactory", "xaJmsConnectionFactory"})
    ConnectionFactory jmsConnectionFactory(ActiveMQProperties properties, ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers, XAConnectionFactoryWrapper wrapper) throws Exception {
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQConnectionFactoryFactory(properties, factoryCustomizers.orderedStream().collect(Collectors.toList())).createConnectionFactory(ActiveMQXAConnectionFactory.class);
        return wrapper.wrapConnectionFactory((XAConnectionFactory)connectionFactory);
    }

    @Bean
    @ConditionalOnProperty(prefix="spring.activemq.pool", name={"enabled"}, havingValue="false", matchIfMissing=true)
    ActiveMQConnectionFactory nonXaJmsConnectionFactory(ActiveMQProperties properties, ObjectProvider<ActiveMQConnectionFactoryCustomizer> factoryCustomizers) {
        return new ActiveMQConnectionFactoryFactory(properties, factoryCustomizers.orderedStream().collect(Collectors.toList())).createConnectionFactory(ActiveMQConnectionFactory.class);
    }
}


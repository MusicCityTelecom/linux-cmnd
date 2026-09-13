/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  javax.jms.XAConnectionFactory
 *  javax.transaction.TransactionManager
 *  org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.boot.jms.XAConnectionFactoryWrapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Primary
 */
package org.springframework.boot.autoconfigure.jms.artemis;

import javax.jms.ConnectionFactory;
import javax.jms.XAConnectionFactory;
import javax.transaction.TransactionManager;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.jms.XAConnectionFactoryWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration(proxyBeanMethods=false)
@ConditionalOnMissingBean(value={ConnectionFactory.class})
@ConditionalOnClass(value={TransactionManager.class})
@ConditionalOnBean(value={XAConnectionFactoryWrapper.class})
class ArtemisXAConnectionFactoryConfiguration {
    ArtemisXAConnectionFactoryConfiguration() {
    }

    @Primary
    @Bean(name={"jmsConnectionFactory", "xaJmsConnectionFactory"})
    ConnectionFactory jmsConnectionFactory(ListableBeanFactory beanFactory, ArtemisProperties properties, XAConnectionFactoryWrapper wrapper) throws Exception {
        return wrapper.wrapConnectionFactory((XAConnectionFactory)new ArtemisConnectionFactoryFactory(beanFactory, properties).createConnectionFactory(ActiveMQXAConnectionFactory.class));
    }

    @Bean
    ActiveMQXAConnectionFactory nonXaJmsConnectionFactory(ListableBeanFactory beanFactory, ArtemisProperties properties) {
        return new ArtemisConnectionFactoryFactory(beanFactory, properties).createConnectionFactory(ActiveMQXAConnectionFactory.class);
    }
}


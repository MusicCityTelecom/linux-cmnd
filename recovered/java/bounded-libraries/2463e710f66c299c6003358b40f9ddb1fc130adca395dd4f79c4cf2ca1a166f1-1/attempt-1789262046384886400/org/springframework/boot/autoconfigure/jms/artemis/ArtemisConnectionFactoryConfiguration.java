/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.jms.ConnectionFactory
 *  org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
 *  org.apache.commons.pool2.PooledObject
 *  org.messaginghub.pooled.jms.JmsPoolConnectionFactory
 *  org.springframework.beans.factory.ListableBeanFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.jms.connection.CachingConnectionFactory
 */
package org.springframework.boot.autoconfigure.jms.artemis;

import javax.jms.ConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.commons.pool2.PooledObject;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;

@Configuration(proxyBeanMethods=false)
@ConditionalOnMissingBean(value={ConnectionFactory.class})
class ArtemisConnectionFactoryConfiguration {
    ArtemisConnectionFactoryConfiguration() {
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={JmsPoolConnectionFactory.class, PooledObject.class})
    @ConditionalOnProperty(prefix="spring.artemis.pool", name={"enabled"}, havingValue="true")
    static class PooledConnectionFactoryConfiguration {
        PooledConnectionFactoryConfiguration() {
        }

        @Bean(destroyMethod="stop")
        JmsPoolConnectionFactory jmsConnectionFactory(ListableBeanFactory beanFactory, ArtemisProperties properties) {
            ActiveMQConnectionFactory connectionFactory = new ArtemisConnectionFactoryFactory(beanFactory, properties).createConnectionFactory(ActiveMQConnectionFactory.class);
            return new JmsPoolConnectionFactoryFactory(properties.getPool()).createPooledConnectionFactory((ConnectionFactory)connectionFactory);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={CachingConnectionFactory.class})
    @ConditionalOnProperty(prefix="spring.artemis.pool", name={"enabled"}, havingValue="false", matchIfMissing=true)
    static class SimpleConnectionFactoryConfiguration {
        private final ArtemisProperties properties;
        private final ListableBeanFactory beanFactory;

        SimpleConnectionFactoryConfiguration(ArtemisProperties properties, ListableBeanFactory beanFactory) {
            this.properties = properties;
            this.beanFactory = beanFactory;
        }

        @Bean(name={"jmsConnectionFactory"})
        @ConditionalOnProperty(prefix="spring.jms.cache", name={"enabled"}, havingValue="true", matchIfMissing=true)
        CachingConnectionFactory cachingJmsConnectionFactory(JmsProperties jmsProperties) {
            JmsProperties.Cache cacheProperties = jmsProperties.getCache();
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory((ConnectionFactory)this.createConnectionFactory());
            connectionFactory.setCacheConsumers(cacheProperties.isConsumers());
            connectionFactory.setCacheProducers(cacheProperties.isProducers());
            connectionFactory.setSessionCacheSize(cacheProperties.getSessionCacheSize());
            return connectionFactory;
        }

        @Bean(name={"jmsConnectionFactory"})
        @ConditionalOnProperty(prefix="spring.jms.cache", name={"enabled"}, havingValue="false")
        ActiveMQConnectionFactory jmsConnectionFactory() {
            return this.createConnectionFactory();
        }

        private ActiveMQConnectionFactory createConnectionFactory() {
            return new ArtemisConnectionFactoryFactory(this.beanFactory, this.properties).createConnectionFactory(ActiveMQConnectionFactory.class);
        }
    }
}


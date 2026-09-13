/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.rabbitmq.client.Channel
 *  com.rabbitmq.client.ConnectionFactory
 *  com.rabbitmq.client.impl.CredentialsProvider
 *  com.rabbitmq.client.impl.CredentialsRefreshService
 *  org.springframework.amqp.core.AmqpAdmin
 *  org.springframework.amqp.rabbit.connection.CachingConnectionFactory
 *  org.springframework.amqp.rabbit.connection.ConnectionFactory
 *  org.springframework.amqp.rabbit.connection.ConnectionNameStrategy
 *  org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean
 *  org.springframework.amqp.rabbit.core.RabbitAdmin
 *  org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
 *  org.springframework.amqp.rabbit.core.RabbitOperations
 *  org.springframework.amqp.rabbit.core.RabbitTemplate
 *  org.springframework.amqp.support.converter.MessageConverter
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.io.ResourceLoader
 */
package org.springframework.boot.autoconfigure.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.CredentialsProvider;
import com.rabbitmq.client.impl.CredentialsRefreshService;
import java.util.stream.Collectors;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.CachingConnectionFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.ConnectionFactoryCustomizer;
import org.springframework.boot.autoconfigure.amqp.RabbitAnnotationDrivenConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionFactoryBeanConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.autoconfigure.amqp.RabbitStreamConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;

@AutoConfiguration
@ConditionalOnClass(value={RabbitTemplate.class, Channel.class})
@EnableConfigurationProperties(value={RabbitProperties.class})
@Import(value={RabbitAnnotationDrivenConfiguration.class, RabbitStreamConfiguration.class})
public class RabbitAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={RabbitMessagingTemplate.class})
    @ConditionalOnMissingBean(value={RabbitMessagingTemplate.class})
    @Import(value={RabbitTemplateConfiguration.class})
    protected static class MessagingTemplateConfiguration {
        protected MessagingTemplateConfiguration() {
        }

        @Bean
        @ConditionalOnSingleCandidate(value=RabbitTemplate.class)
        public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
            return new RabbitMessagingTemplate(rabbitTemplate);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @Import(value={RabbitConnectionFactoryCreator.class})
    protected static class RabbitTemplateConfiguration {
        protected RabbitTemplateConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        public RabbitTemplateConfigurer rabbitTemplateConfigurer(RabbitProperties properties, ObjectProvider<MessageConverter> messageConverter, ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers) {
            RabbitTemplateConfigurer configurer = new RabbitTemplateConfigurer(properties);
            configurer.setMessageConverter((MessageConverter)messageConverter.getIfUnique());
            configurer.setRetryTemplateCustomizers(retryTemplateCustomizers.orderedStream().collect(Collectors.toList()));
            return configurer;
        }

        @Bean
        @ConditionalOnSingleCandidate(value=org.springframework.amqp.rabbit.connection.ConnectionFactory.class)
        @ConditionalOnMissingBean(value={RabbitOperations.class})
        public RabbitTemplate rabbitTemplate(RabbitTemplateConfigurer configurer, org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
            RabbitTemplate template = new RabbitTemplate();
            configurer.configure(template, connectionFactory);
            return template;
        }

        @Bean
        @ConditionalOnSingleCandidate(value=org.springframework.amqp.rabbit.connection.ConnectionFactory.class)
        @ConditionalOnProperty(prefix="spring.rabbitmq", name={"dynamic"}, matchIfMissing=true)
        @ConditionalOnMissingBean
        public AmqpAdmin amqpAdmin(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
            return new RabbitAdmin(connectionFactory);
        }
    }

    @Configuration(proxyBeanMethods=false)
    protected static class RabbitConnectionFactoryCreator {
        protected RabbitConnectionFactoryCreator() {
        }

        @Bean
        @ConditionalOnMissingBean
        RabbitConnectionFactoryBeanConfigurer rabbitConnectionFactoryBeanConfigurer(RabbitProperties properties, ResourceLoader resourceLoader, ObjectProvider<CredentialsProvider> credentialsProvider, ObjectProvider<CredentialsRefreshService> credentialsRefreshService) {
            RabbitConnectionFactoryBeanConfigurer configurer = new RabbitConnectionFactoryBeanConfigurer(resourceLoader, properties);
            configurer.setCredentialsProvider((CredentialsProvider)credentialsProvider.getIfUnique());
            configurer.setCredentialsRefreshService((CredentialsRefreshService)credentialsRefreshService.getIfUnique());
            return configurer;
        }

        @Bean
        @ConditionalOnMissingBean
        CachingConnectionFactoryConfigurer rabbitConnectionFactoryConfigurer(RabbitProperties rabbitProperties, ObjectProvider<ConnectionNameStrategy> connectionNameStrategy) {
            CachingConnectionFactoryConfigurer configurer = new CachingConnectionFactoryConfigurer(rabbitProperties);
            configurer.setConnectionNameStrategy((ConnectionNameStrategy)connectionNameStrategy.getIfUnique());
            return configurer;
        }

        @Bean
        @ConditionalOnMissingBean(value={org.springframework.amqp.rabbit.connection.ConnectionFactory.class})
        CachingConnectionFactory rabbitConnectionFactory(RabbitConnectionFactoryBeanConfigurer rabbitConnectionFactoryBeanConfigurer, CachingConnectionFactoryConfigurer rabbitCachingConnectionFactoryConfigurer, ObjectProvider<ConnectionFactoryCustomizer> connectionFactoryCustomizers) throws Exception {
            RabbitConnectionFactoryBean connectionFactoryBean = new RabbitConnectionFactoryBean();
            rabbitConnectionFactoryBeanConfigurer.configure(connectionFactoryBean);
            connectionFactoryBean.afterPropertiesSet();
            ConnectionFactory connectionFactory = (ConnectionFactory)connectionFactoryBean.getObject();
            connectionFactoryCustomizers.orderedStream().forEach(customizer -> customizer.customize(connectionFactory));
            CachingConnectionFactory factory = new CachingConnectionFactory(connectionFactory);
            rabbitCachingConnectionFactoryConfigurer.configure(factory);
            return factory;
        }
    }
}


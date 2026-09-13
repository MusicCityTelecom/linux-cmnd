/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.annotation.EnableRabbit
 *  org.springframework.amqp.rabbit.config.ContainerCustomizer
 *  org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory
 *  org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
 *  org.springframework.amqp.rabbit.connection.ConnectionFactory
 *  org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer
 *  org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
 *  org.springframework.amqp.rabbit.retry.MessageRecoverer
 *  org.springframework.amqp.support.converter.MessageConverter
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package org.springframework.boot.autoconfigure.amqp;

import java.util.stream.Collectors;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.DirectRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={EnableRabbit.class})
class RabbitAnnotationDrivenConfiguration {
    private final ObjectProvider<MessageConverter> messageConverter;
    private final ObjectProvider<MessageRecoverer> messageRecoverer;
    private final ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers;
    private final RabbitProperties properties;

    RabbitAnnotationDrivenConfiguration(ObjectProvider<MessageConverter> messageConverter, ObjectProvider<MessageRecoverer> messageRecoverer, ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers, RabbitProperties properties) {
        this.messageConverter = messageConverter;
        this.messageRecoverer = messageRecoverer;
        this.retryTemplateCustomizers = retryTemplateCustomizers;
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    SimpleRabbitListenerContainerFactoryConfigurer simpleRabbitListenerContainerFactoryConfigurer() {
        SimpleRabbitListenerContainerFactoryConfigurer configurer = new SimpleRabbitListenerContainerFactoryConfigurer(this.properties);
        configurer.setMessageConverter((MessageConverter)this.messageConverter.getIfUnique());
        configurer.setMessageRecoverer((MessageRecoverer)this.messageRecoverer.getIfUnique());
        configurer.setRetryTemplateCustomizers(this.retryTemplateCustomizers.orderedStream().collect(Collectors.toList()));
        return configurer;
    }

    @Bean(name={"rabbitListenerContainerFactory"})
    @ConditionalOnMissingBean(name={"rabbitListenerContainerFactory"})
    @ConditionalOnProperty(prefix="spring.rabbitmq.listener", name={"type"}, havingValue="simple", matchIfMissing=true)
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory, ObjectProvider<ContainerCustomizer<SimpleMessageListenerContainer>> simpleContainerCustomizer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        simpleContainerCustomizer.ifUnique(arg_0 -> ((SimpleRabbitListenerContainerFactory)factory).setContainerCustomizer(arg_0));
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean
    DirectRabbitListenerContainerFactoryConfigurer directRabbitListenerContainerFactoryConfigurer() {
        DirectRabbitListenerContainerFactoryConfigurer configurer = new DirectRabbitListenerContainerFactoryConfigurer(this.properties);
        configurer.setMessageConverter((MessageConverter)this.messageConverter.getIfUnique());
        configurer.setMessageRecoverer((MessageRecoverer)this.messageRecoverer.getIfUnique());
        configurer.setRetryTemplateCustomizers(this.retryTemplateCustomizers.orderedStream().collect(Collectors.toList()));
        return configurer;
    }

    @Bean(name={"rabbitListenerContainerFactory"})
    @ConditionalOnMissingBean(name={"rabbitListenerContainerFactory"})
    @ConditionalOnProperty(prefix="spring.rabbitmq.listener", name={"type"}, havingValue="direct")
    DirectRabbitListenerContainerFactory directRabbitListenerContainerFactory(DirectRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory, ObjectProvider<ContainerCustomizer<DirectMessageListenerContainer>> directContainerCustomizer) {
        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        directContainerCustomizer.ifUnique(arg_0 -> ((DirectRabbitListenerContainerFactory)factory).setContainerCustomizer(arg_0));
        return factory;
    }

    @Configuration(proxyBeanMethods=false)
    @EnableRabbit
    @ConditionalOnMissingBean(name={"org.springframework.amqp.rabbit.config.internalRabbitListenerAnnotationProcessor"})
    static class EnableRabbitConfiguration {
        EnableRabbitConfiguration() {
        }
    }
}


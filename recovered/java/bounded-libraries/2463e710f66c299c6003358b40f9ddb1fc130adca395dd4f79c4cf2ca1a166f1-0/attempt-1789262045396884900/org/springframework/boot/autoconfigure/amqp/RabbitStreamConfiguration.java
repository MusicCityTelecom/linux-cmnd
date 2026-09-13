/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.rabbitmq.stream.Environment
 *  com.rabbitmq.stream.EnvironmentBuilder
 *  org.springframework.amqp.rabbit.config.ContainerCustomizer
 *  org.springframework.amqp.support.converter.MessageConverter
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory
 *  org.springframework.rabbit.stream.listener.ConsumerCustomizer
 *  org.springframework.rabbit.stream.listener.StreamListenerContainer
 *  org.springframework.rabbit.stream.producer.ProducerCustomizer
 *  org.springframework.rabbit.stream.producer.RabbitStreamOperations
 *  org.springframework.rabbit.stream.producer.RabbitStreamTemplate
 *  org.springframework.rabbit.stream.support.converter.StreamMessageConverter
 */
package org.springframework.boot.autoconfigure.amqp;

import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.EnvironmentBuilder;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.amqp.rabbit.config.ContainerCustomizer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitStreamTemplateConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory;
import org.springframework.rabbit.stream.listener.ConsumerCustomizer;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;
import org.springframework.rabbit.stream.producer.ProducerCustomizer;
import org.springframework.rabbit.stream.producer.RabbitStreamOperations;
import org.springframework.rabbit.stream.producer.RabbitStreamTemplate;
import org.springframework.rabbit.stream.support.converter.StreamMessageConverter;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={StreamRabbitListenerContainerFactory.class})
@ConditionalOnProperty(prefix="spring.rabbitmq.listener", name={"type"}, havingValue="stream")
class RabbitStreamConfiguration {
    RabbitStreamConfiguration() {
    }

    @Bean(name={"rabbitListenerContainerFactory"})
    @ConditionalOnMissingBean(name={"rabbitListenerContainerFactory"})
    StreamRabbitListenerContainerFactory streamRabbitListenerContainerFactory(Environment rabbitStreamEnvironment, RabbitProperties properties, ObjectProvider<ConsumerCustomizer> consumerCustomizer, ObjectProvider<ContainerCustomizer<StreamListenerContainer>> containerCustomizer) {
        StreamRabbitListenerContainerFactory factory = new StreamRabbitListenerContainerFactory(rabbitStreamEnvironment);
        factory.setNativeListener(properties.getListener().getStream().isNativeListener());
        consumerCustomizer.ifUnique(arg_0 -> ((StreamRabbitListenerContainerFactory)factory).setConsumerCustomizer(arg_0));
        containerCustomizer.ifUnique(arg_0 -> ((StreamRabbitListenerContainerFactory)factory).setContainerCustomizer(arg_0));
        return factory;
    }

    @Bean(name={"rabbitStreamEnvironment"})
    @ConditionalOnMissingBean(name={"rabbitStreamEnvironment"})
    Environment rabbitStreamEnvironment(RabbitProperties properties) {
        return RabbitStreamConfiguration.configure(Environment.builder(), properties).build();
    }

    @Bean
    @ConditionalOnMissingBean
    RabbitStreamTemplateConfigurer rabbitStreamTemplateConfigurer(RabbitProperties properties, ObjectProvider<MessageConverter> messageConverter, ObjectProvider<StreamMessageConverter> streamMessageConverter, ObjectProvider<ProducerCustomizer> producerCustomizer) {
        RabbitStreamTemplateConfigurer configurer = new RabbitStreamTemplateConfigurer();
        configurer.setMessageConverter((MessageConverter)messageConverter.getIfUnique());
        configurer.setStreamMessageConverter((StreamMessageConverter)streamMessageConverter.getIfUnique());
        configurer.setProducerCustomizer((ProducerCustomizer)producerCustomizer.getIfUnique());
        return configurer;
    }

    @Bean
    @ConditionalOnMissingBean(value={RabbitStreamOperations.class})
    @ConditionalOnProperty(prefix="spring.rabbitmq.stream", name={"name"})
    RabbitStreamTemplate rabbitStreamTemplate(Environment rabbitStreamEnvironment, RabbitProperties properties, RabbitStreamTemplateConfigurer configurer) {
        RabbitStreamTemplate template = new RabbitStreamTemplate(rabbitStreamEnvironment, properties.getStream().getName());
        configurer.configure(template);
        return template;
    }

    static EnvironmentBuilder configure(EnvironmentBuilder builder, RabbitProperties properties) {
        builder.lazyInitialization(true);
        RabbitProperties.Stream stream = properties.getStream();
        PropertyMapper mapper = PropertyMapper.get();
        mapper.from((Object)stream.getHost()).to(arg_0 -> ((EnvironmentBuilder)builder).host(arg_0));
        mapper.from((Object)stream.getPort()).to(arg_0 -> ((EnvironmentBuilder)builder).port(arg_0));
        mapper.from((Object)stream.getUsername()).as(RabbitStreamConfiguration.withFallback(properties::getUsername)).whenNonNull().to(arg_0 -> ((EnvironmentBuilder)builder).username(arg_0));
        mapper.from((Object)stream.getPassword()).as(RabbitStreamConfiguration.withFallback(properties::getPassword)).whenNonNull().to(arg_0 -> ((EnvironmentBuilder)builder).password(arg_0));
        return builder;
    }

    private static Function<String, String> withFallback(Supplier<String> fallback) {
        return value -> value != null ? value : (String)fallback.get();
    }
}


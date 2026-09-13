/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Import
 *  org.springframework.kafka.core.ConsumerFactory
 *  org.springframework.kafka.core.DefaultKafkaConsumerFactory
 *  org.springframework.kafka.core.DefaultKafkaProducerFactory
 *  org.springframework.kafka.core.KafkaAdmin
 *  org.springframework.kafka.core.KafkaTemplate
 *  org.springframework.kafka.core.ProducerFactory
 *  org.springframework.kafka.retrytopic.RetryTopicConfiguration
 *  org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder
 *  org.springframework.kafka.security.jaas.KafkaJaasLoginModuleInitializer
 *  org.springframework.kafka.support.LoggingProducerListener
 *  org.springframework.kafka.support.ProducerListener
 *  org.springframework.kafka.support.converter.RecordMessageConverter
 *  org.springframework.kafka.transaction.KafkaTransactionManager
 *  org.springframework.retry.backoff.BackOffPolicyBuilder
 *  org.springframework.retry.backoff.SleepingBackOffPolicy
 */
package org.springframework.boot.autoconfigure.kafka;

import java.io.IOException;
import java.time.Duration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaAnnotationDrivenConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.autoconfigure.kafka.KafkaStreamsAnnotationDrivenConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.security.jaas.KafkaJaasLoginModuleInitializer;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.retry.backoff.BackOffPolicyBuilder;
import org.springframework.retry.backoff.SleepingBackOffPolicy;

@AutoConfiguration
@ConditionalOnClass(value={KafkaTemplate.class})
@EnableConfigurationProperties(value={KafkaProperties.class})
@Import(value={KafkaAnnotationDrivenConfiguration.class, KafkaStreamsAnnotationDrivenConfiguration.class})
public class KafkaAutoConfiguration {
    private final KafkaProperties properties;

    public KafkaAutoConfiguration(KafkaProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(value={KafkaTemplate.class})
    public KafkaTemplate<?, ?> kafkaTemplate(ProducerFactory<Object, Object> kafkaProducerFactory, ProducerListener<Object, Object> kafkaProducerListener, ObjectProvider<RecordMessageConverter> messageConverter) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        KafkaTemplate kafkaTemplate = new KafkaTemplate(kafkaProducerFactory);
        messageConverter.ifUnique(arg_0 -> ((KafkaTemplate)kafkaTemplate).setMessageConverter(arg_0));
        map.from(kafkaProducerListener).to(arg_0 -> ((KafkaTemplate)kafkaTemplate).setProducerListener(arg_0));
        map.from((Object)this.properties.getTemplate().getDefaultTopic()).to(arg_0 -> ((KafkaTemplate)kafkaTemplate).setDefaultTopic(arg_0));
        map.from((Object)this.properties.getTemplate().getTransactionIdPrefix()).to(arg_0 -> ((KafkaTemplate)kafkaTemplate).setTransactionIdPrefix(arg_0));
        return kafkaTemplate;
    }

    @Bean
    @ConditionalOnMissingBean(value={ProducerListener.class})
    public ProducerListener<Object, Object> kafkaProducerListener() {
        return new LoggingProducerListener();
    }

    @Bean
    @ConditionalOnMissingBean(value={ConsumerFactory.class})
    public ConsumerFactory<?, ?> kafkaConsumerFactory(ObjectProvider<DefaultKafkaConsumerFactoryCustomizer> customizers) {
        DefaultKafkaConsumerFactory factory = new DefaultKafkaConsumerFactory(this.properties.buildConsumerProperties());
        customizers.orderedStream().forEach(customizer -> customizer.customize(factory));
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(value={ProducerFactory.class})
    public ProducerFactory<?, ?> kafkaProducerFactory(ObjectProvider<DefaultKafkaProducerFactoryCustomizer> customizers) {
        DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory(this.properties.buildProducerProperties());
        String transactionIdPrefix = this.properties.getProducer().getTransactionIdPrefix();
        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }
        customizers.orderedStream().forEach(customizer -> customizer.customize(factory));
        return factory;
    }

    @Bean
    @ConditionalOnProperty(name={"spring.kafka.producer.transaction-id-prefix"})
    @ConditionalOnMissingBean
    public KafkaTransactionManager<?, ?> kafkaTransactionManager(ProducerFactory<?, ?> producerFactory) {
        return new KafkaTransactionManager(producerFactory);
    }

    @Bean
    @ConditionalOnProperty(name={"spring.kafka.jaas.enabled"})
    @ConditionalOnMissingBean
    public KafkaJaasLoginModuleInitializer kafkaJaasInitializer() throws IOException {
        KafkaJaasLoginModuleInitializer jaas = new KafkaJaasLoginModuleInitializer();
        KafkaProperties.Jaas jaasProperties = this.properties.getJaas();
        if (jaasProperties.getControlFlag() != null) {
            jaas.setControlFlag(jaasProperties.getControlFlag());
        }
        if (jaasProperties.getLoginModule() != null) {
            jaas.setLoginModule(jaasProperties.getLoginModule());
        }
        jaas.setOptions(jaasProperties.getOptions());
        return jaas;
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaAdmin kafkaAdmin() {
        KafkaAdmin kafkaAdmin = new KafkaAdmin(this.properties.buildAdminProperties());
        kafkaAdmin.setFatalIfBrokerNotAvailable(this.properties.getAdmin().isFailFast());
        return kafkaAdmin;
    }

    @Bean
    @ConditionalOnProperty(name={"spring.kafka.retry.topic.enabled"})
    @ConditionalOnSingleCandidate(value=KafkaTemplate.class)
    public RetryTopicConfiguration kafkaRetryTopicConfiguration(KafkaTemplate<?, ?> kafkaTemplate) {
        KafkaProperties.Retry.Topic retryTopic = this.properties.getRetry().getTopic();
        RetryTopicConfigurationBuilder builder = RetryTopicConfigurationBuilder.newInstance().maxAttempts(retryTopic.getAttempts()).useSingleTopicForFixedDelays().suffixTopicsWithIndexValues().doNotAutoCreateRetryTopics();
        KafkaAutoConfiguration.setBackOffPolicy(builder, retryTopic);
        return builder.create(kafkaTemplate);
    }

    private static void setBackOffPolicy(RetryTopicConfigurationBuilder builder, KafkaProperties.Retry.Topic retryTopic) {
        long delay;
        long l = delay = retryTopic.getDelay() != null ? retryTopic.getDelay().toMillis() : 0L;
        if (delay > 0L) {
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            BackOffPolicyBuilder backOffPolicy = BackOffPolicyBuilder.newBuilder();
            map.from((Object)delay).to(arg_0 -> ((BackOffPolicyBuilder)backOffPolicy).delay(arg_0));
            map.from((Object)retryTopic.getMaxDelay()).as(Duration::toMillis).to(arg_0 -> ((BackOffPolicyBuilder)backOffPolicy).maxDelay(arg_0));
            map.from((Object)retryTopic.getMultiplier()).to(arg_0 -> ((BackOffPolicyBuilder)backOffPolicy).multiplier(arg_0));
            map.from((Object)retryTopic.isRandomBackOff()).to(arg_0 -> ((BackOffPolicyBuilder)backOffPolicy).random(arg_0));
            builder.customBackoff((SleepingBackOffPolicy)backOffPolicy.build());
        } else {
            builder.noBackoff();
        }
    }
}


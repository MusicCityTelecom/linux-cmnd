/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.kafka.annotation.EnableKafka
 *  org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
 *  org.springframework.kafka.core.ConsumerFactory
 *  org.springframework.kafka.core.DefaultKafkaConsumerFactory
 *  org.springframework.kafka.core.KafkaTemplate
 *  org.springframework.kafka.listener.AfterRollbackProcessor
 *  org.springframework.kafka.listener.BatchErrorHandler
 *  org.springframework.kafka.listener.CommonErrorHandler
 *  org.springframework.kafka.listener.ConsumerAwareRebalanceListener
 *  org.springframework.kafka.listener.ErrorHandler
 *  org.springframework.kafka.listener.RecordInterceptor
 *  org.springframework.kafka.listener.adapter.RecordFilterStrategy
 *  org.springframework.kafka.support.converter.BatchMessageConverter
 *  org.springframework.kafka.support.converter.BatchMessagingMessageConverter
 *  org.springframework.kafka.support.converter.MessageConverter
 *  org.springframework.kafka.support.converter.RecordMessageConverter
 *  org.springframework.kafka.transaction.KafkaAwareTransactionManager
 */
package org.springframework.boot.autoconfigure.kafka;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AfterRollbackProcessor;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.converter.BatchMessageConverter;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.transaction.KafkaAwareTransactionManager;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={EnableKafka.class})
class KafkaAnnotationDrivenConfiguration {
    private final KafkaProperties properties;
    private final RecordMessageConverter messageConverter;
    private final RecordFilterStrategy<Object, Object> recordFilterStrategy;
    private final BatchMessageConverter batchMessageConverter;
    private final KafkaTemplate<Object, Object> kafkaTemplate;
    private final KafkaAwareTransactionManager<Object, Object> transactionManager;
    private final ConsumerAwareRebalanceListener rebalanceListener;
    private final ErrorHandler errorHandler;
    private final BatchErrorHandler batchErrorHandler;
    private final CommonErrorHandler commonErrorHandler;
    private final AfterRollbackProcessor<Object, Object> afterRollbackProcessor;
    private final RecordInterceptor<Object, Object> recordInterceptor;

    KafkaAnnotationDrivenConfiguration(KafkaProperties properties, ObjectProvider<RecordMessageConverter> messageConverter, ObjectProvider<RecordFilterStrategy<Object, Object>> recordFilterStrategy, ObjectProvider<BatchMessageConverter> batchMessageConverter, ObjectProvider<KafkaTemplate<Object, Object>> kafkaTemplate, ObjectProvider<KafkaAwareTransactionManager<Object, Object>> kafkaTransactionManager, ObjectProvider<ConsumerAwareRebalanceListener> rebalanceListener, ObjectProvider<ErrorHandler> errorHandler, ObjectProvider<BatchErrorHandler> batchErrorHandler, ObjectProvider<CommonErrorHandler> commonErrorHandler, ObjectProvider<AfterRollbackProcessor<Object, Object>> afterRollbackProcessor, ObjectProvider<RecordInterceptor<Object, Object>> recordInterceptor) {
        this.properties = properties;
        this.messageConverter = (RecordMessageConverter)messageConverter.getIfUnique();
        this.recordFilterStrategy = (RecordFilterStrategy)recordFilterStrategy.getIfUnique();
        this.batchMessageConverter = (BatchMessageConverter)batchMessageConverter.getIfUnique(() -> new BatchMessagingMessageConverter(this.messageConverter));
        this.kafkaTemplate = (KafkaTemplate)kafkaTemplate.getIfUnique();
        this.transactionManager = (KafkaAwareTransactionManager)kafkaTransactionManager.getIfUnique();
        this.rebalanceListener = (ConsumerAwareRebalanceListener)rebalanceListener.getIfUnique();
        this.errorHandler = (ErrorHandler)errorHandler.getIfUnique();
        this.batchErrorHandler = (BatchErrorHandler)batchErrorHandler.getIfUnique();
        this.commonErrorHandler = (CommonErrorHandler)commonErrorHandler.getIfUnique();
        this.afterRollbackProcessor = (AfterRollbackProcessor)afterRollbackProcessor.getIfUnique();
        this.recordInterceptor = (RecordInterceptor)recordInterceptor.getIfUnique();
    }

    @Bean
    @ConditionalOnMissingBean
    ConcurrentKafkaListenerContainerFactoryConfigurer kafkaListenerContainerFactoryConfigurer() {
        ConcurrentKafkaListenerContainerFactoryConfigurer configurer = new ConcurrentKafkaListenerContainerFactoryConfigurer();
        configurer.setKafkaProperties(this.properties);
        BatchMessageConverter messageConverterToUse = this.properties.getListener().getType().equals((Object)KafkaProperties.Listener.Type.BATCH) ? this.batchMessageConverter : this.messageConverter;
        configurer.setMessageConverter((MessageConverter)messageConverterToUse);
        configurer.setRecordFilterStrategy(this.recordFilterStrategy);
        configurer.setReplyTemplate(this.kafkaTemplate);
        configurer.setTransactionManager(this.transactionManager);
        configurer.setRebalanceListener(this.rebalanceListener);
        configurer.setErrorHandler(this.errorHandler);
        configurer.setBatchErrorHandler(this.batchErrorHandler);
        configurer.setCommonErrorHandler(this.commonErrorHandler);
        configurer.setAfterRollbackProcessor(this.afterRollbackProcessor);
        configurer.setRecordInterceptor(this.recordInterceptor);
        return configurer;
    }

    @Bean
    @ConditionalOnMissingBean(name={"kafkaListenerContainerFactory"})
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer, ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        configurer.configure((ConcurrentKafkaListenerContainerFactory<Object, Object>)factory, (ConsumerFactory<Object, Object>)((ConsumerFactory)kafkaConsumerFactory.getIfAvailable(() -> new DefaultKafkaConsumerFactory(this.properties.buildConsumerProperties()))));
        return factory;
    }

    @Configuration(proxyBeanMethods=false)
    @EnableKafka
    @ConditionalOnMissingBean(name={"org.springframework.kafka.config.internalKafkaListenerAnnotationProcessor"})
    static class EnableKafkaConfiguration {
        EnableKafkaConfiguration() {
        }
    }
}


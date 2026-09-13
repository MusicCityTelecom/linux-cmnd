/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
 *  org.springframework.kafka.core.ConsumerFactory
 *  org.springframework.kafka.core.KafkaTemplate
 *  org.springframework.kafka.listener.AfterRollbackProcessor
 *  org.springframework.kafka.listener.BatchErrorHandler
 *  org.springframework.kafka.listener.CommonErrorHandler
 *  org.springframework.kafka.listener.ConsumerAwareRebalanceListener
 *  org.springframework.kafka.listener.ContainerProperties
 *  org.springframework.kafka.listener.ErrorHandler
 *  org.springframework.kafka.listener.RecordInterceptor
 *  org.springframework.kafka.listener.adapter.RecordFilterStrategy
 *  org.springframework.kafka.support.converter.MessageConverter
 *  org.springframework.kafka.transaction.KafkaAwareTransactionManager
 */
package org.springframework.boot.autoconfigure.kafka;

import java.time.Duration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AfterRollbackProcessor;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.RecordInterceptor;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.transaction.KafkaAwareTransactionManager;

public class ConcurrentKafkaListenerContainerFactoryConfigurer {
    private KafkaProperties properties;
    private MessageConverter messageConverter;
    private RecordFilterStrategy<Object, Object> recordFilterStrategy;
    private KafkaTemplate<Object, Object> replyTemplate;
    private KafkaAwareTransactionManager<Object, Object> transactionManager;
    private ConsumerAwareRebalanceListener rebalanceListener;
    private ErrorHandler errorHandler;
    private BatchErrorHandler batchErrorHandler;
    private CommonErrorHandler commonErrorHandler;
    private AfterRollbackProcessor<Object, Object> afterRollbackProcessor;
    private RecordInterceptor<Object, Object> recordInterceptor;

    void setKafkaProperties(KafkaProperties properties) {
        this.properties = properties;
    }

    void setMessageConverter(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    void setRecordFilterStrategy(RecordFilterStrategy<Object, Object> recordFilterStrategy) {
        this.recordFilterStrategy = recordFilterStrategy;
    }

    void setReplyTemplate(KafkaTemplate<Object, Object> replyTemplate) {
        this.replyTemplate = replyTemplate;
    }

    void setTransactionManager(KafkaAwareTransactionManager<Object, Object> transactionManager) {
        this.transactionManager = transactionManager;
    }

    void setRebalanceListener(ConsumerAwareRebalanceListener rebalanceListener) {
        this.rebalanceListener = rebalanceListener;
    }

    void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    void setBatchErrorHandler(BatchErrorHandler batchErrorHandler) {
        this.batchErrorHandler = batchErrorHandler;
    }

    public void setCommonErrorHandler(CommonErrorHandler commonErrorHandler) {
        this.commonErrorHandler = commonErrorHandler;
    }

    void setAfterRollbackProcessor(AfterRollbackProcessor<Object, Object> afterRollbackProcessor) {
        this.afterRollbackProcessor = afterRollbackProcessor;
    }

    void setRecordInterceptor(RecordInterceptor<Object, Object> recordInterceptor) {
        this.recordInterceptor = recordInterceptor;
    }

    public void configure(ConcurrentKafkaListenerContainerFactory<Object, Object> listenerFactory, ConsumerFactory<Object, Object> consumerFactory) {
        listenerFactory.setConsumerFactory(consumerFactory);
        this.configureListenerFactory(listenerFactory);
        this.configureContainer(listenerFactory.getContainerProperties());
    }

    private void configureListenerFactory(ConcurrentKafkaListenerContainerFactory<Object, Object> factory) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        KafkaProperties.Listener properties = this.properties.getListener();
        map.from(properties::getConcurrency).to(arg_0 -> factory.setConcurrency(arg_0));
        map.from((Object)this.messageConverter).to(arg_0 -> factory.setMessageConverter(arg_0));
        map.from(this.recordFilterStrategy).to(arg_0 -> factory.setRecordFilterStrategy(arg_0));
        map.from(this.replyTemplate).to(arg_0 -> factory.setReplyTemplate(arg_0));
        if (properties.getType().equals((Object)KafkaProperties.Listener.Type.BATCH)) {
            factory.setBatchListener(Boolean.valueOf(true));
            factory.setBatchErrorHandler(this.batchErrorHandler);
        } else {
            factory.setErrorHandler(this.errorHandler);
        }
        map.from((Object)this.commonErrorHandler).to(arg_0 -> factory.setCommonErrorHandler(arg_0));
        map.from(this.afterRollbackProcessor).to(arg_0 -> factory.setAfterRollbackProcessor(arg_0));
        map.from(this.recordInterceptor).to(arg_0 -> factory.setRecordInterceptor(arg_0));
    }

    private void configureContainer(ContainerProperties container) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        KafkaProperties.Listener properties = this.properties.getListener();
        map.from(properties::getAckMode).to(arg_0 -> ((ContainerProperties)container).setAckMode(arg_0));
        map.from(properties::getClientId).to(arg_0 -> ((ContainerProperties)container).setClientId(arg_0));
        map.from(properties::getAckCount).to(arg_0 -> ((ContainerProperties)container).setAckCount(arg_0));
        map.from(properties::getAckTime).as(Duration::toMillis).to(arg_0 -> ((ContainerProperties)container).setAckTime(arg_0));
        map.from(properties::getPollTimeout).as(Duration::toMillis).to(arg_0 -> ((ContainerProperties)container).setPollTimeout(arg_0));
        map.from(properties::getNoPollThreshold).to(arg_0 -> ((ContainerProperties)container).setNoPollThreshold(arg_0));
        map.from((Object)properties.getIdleBetweenPolls()).as(Duration::toMillis).to(arg_0 -> ((ContainerProperties)container).setIdleBetweenPolls(arg_0));
        map.from(properties::getIdleEventInterval).as(Duration::toMillis).to(arg_0 -> ((ContainerProperties)container).setIdleEventInterval(arg_0));
        map.from(properties::getIdlePartitionEventInterval).as(Duration::toMillis).to(arg_0 -> ((ContainerProperties)container).setIdlePartitionEventInterval(arg_0));
        map.from(properties::getMonitorInterval).as(Duration::getSeconds).as(Number::intValue).to(arg_0 -> ((ContainerProperties)container).setMonitorInterval(arg_0));
        map.from(properties::getLogContainerConfig).to(arg_0 -> ((ContainerProperties)container).setLogContainerConfig(arg_0));
        map.from(properties::isOnlyLogRecordMetadata).to(arg_0 -> ((ContainerProperties)container).setOnlyLogRecordMetadata(arg_0));
        map.from(properties::isMissingTopicsFatal).to(arg_0 -> ((ContainerProperties)container).setMissingTopicsFatal(arg_0));
        map.from(properties::isImmediateStop).to(arg_0 -> ((ContainerProperties)container).setStopImmediate(arg_0));
        map.from(this.transactionManager).to(arg_0 -> ((ContainerProperties)container).setTransactionManager(arg_0));
        map.from((Object)this.rebalanceListener).to(arg_0 -> ((ContainerProperties)container).setConsumerRebalanceListener(arg_0));
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
 *  org.springframework.amqp.rabbit.connection.ConnectionFactory
 *  org.springframework.boot.context.properties.PropertyMapper
 */
package org.springframework.boot.autoconfigure.amqp;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.AbstractRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.PropertyMapper;

public final class SimpleRabbitListenerContainerFactoryConfigurer
extends AbstractRabbitListenerContainerFactoryConfigurer<SimpleRabbitListenerContainerFactory> {
    @Deprecated
    public SimpleRabbitListenerContainerFactoryConfigurer() {
    }

    public SimpleRabbitListenerContainerFactoryConfigurer(RabbitProperties rabbitProperties) {
        super(rabbitProperties);
    }

    @Override
    public void configure(SimpleRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory) {
        PropertyMapper map = PropertyMapper.get();
        RabbitProperties.SimpleContainer config = this.getRabbitProperties().getListener().getSimple();
        this.configure(factory, connectionFactory, config);
        map.from(config::getConcurrency).whenNonNull().to(arg_0 -> ((SimpleRabbitListenerContainerFactory)factory).setConcurrentConsumers(arg_0));
        map.from(config::getMaxConcurrency).whenNonNull().to(arg_0 -> ((SimpleRabbitListenerContainerFactory)factory).setMaxConcurrentConsumers(arg_0));
        map.from(config::getBatchSize).whenNonNull().to(arg_0 -> ((SimpleRabbitListenerContainerFactory)factory).setBatchSize(arg_0));
        map.from(config::isConsumerBatchEnabled).to(arg_0 -> ((SimpleRabbitListenerContainerFactory)factory).setConsumerBatchEnabled(arg_0));
    }
}


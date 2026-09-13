/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory
 *  org.springframework.amqp.rabbit.connection.ConnectionFactory
 *  org.springframework.boot.context.properties.PropertyMapper
 */
package org.springframework.boot.autoconfigure.amqp;

import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.AbstractRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.PropertyMapper;

public final class DirectRabbitListenerContainerFactoryConfigurer
extends AbstractRabbitListenerContainerFactoryConfigurer<DirectRabbitListenerContainerFactory> {
    @Deprecated
    public DirectRabbitListenerContainerFactoryConfigurer() {
    }

    public DirectRabbitListenerContainerFactoryConfigurer(RabbitProperties rabbitProperties) {
        super(rabbitProperties);
    }

    @Override
    public void configure(DirectRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory) {
        PropertyMapper map = PropertyMapper.get();
        RabbitProperties.DirectContainer config = this.getRabbitProperties().getListener().getDirect();
        this.configure(factory, connectionFactory, config);
        map.from(config::getConsumersPerQueue).whenNonNull().to(arg_0 -> ((DirectRabbitListenerContainerFactory)factory).setConsumersPerQueue(arg_0));
    }
}


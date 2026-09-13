/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.connection.CachingConnectionFactory
 *  org.springframework.boot.context.properties.PropertyMapper
 */
package org.springframework.boot.autoconfigure.amqp;

import java.time.Duration;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.AbstractConnectionFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.PropertyMapper;

public class CachingConnectionFactoryConfigurer
extends AbstractConnectionFactoryConfigurer<CachingConnectionFactory> {
    public CachingConnectionFactoryConfigurer(RabbitProperties properties) {
        super(properties);
    }

    @Override
    public void configure(CachingConnectionFactory connectionFactory, RabbitProperties rabbitProperties) {
        PropertyMapper map = PropertyMapper.get();
        map.from(rabbitProperties::isPublisherReturns).to(arg_0 -> ((CachingConnectionFactory)connectionFactory).setPublisherReturns(arg_0));
        map.from(rabbitProperties::getPublisherConfirmType).whenNonNull().to(arg_0 -> ((CachingConnectionFactory)connectionFactory).setPublisherConfirmType(arg_0));
        RabbitProperties.Cache.Channel channel = rabbitProperties.getCache().getChannel();
        map.from(channel::getSize).whenNonNull().to(arg_0 -> ((CachingConnectionFactory)connectionFactory).setChannelCacheSize(arg_0));
        map.from(channel::getCheckoutTimeout).whenNonNull().as(Duration::toMillis).to(arg_0 -> ((CachingConnectionFactory)connectionFactory).setChannelCheckoutTimeout(arg_0));
        RabbitProperties.Cache.Connection connection = rabbitProperties.getCache().getConnection();
        map.from(connection::getMode).whenNonNull().to(arg_0 -> ((CachingConnectionFactory)connectionFactory).setCacheMode(arg_0));
        map.from(connection::getSize).whenNonNull().to(arg_0 -> ((CachingConnectionFactory)connectionFactory).setConnectionCacheSize(arg_0));
    }
}


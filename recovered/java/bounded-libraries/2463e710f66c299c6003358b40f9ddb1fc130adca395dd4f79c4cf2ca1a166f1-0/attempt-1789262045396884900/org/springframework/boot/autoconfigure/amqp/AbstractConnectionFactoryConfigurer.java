/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.amqp.rabbit.connection.AbstractConnectionFactory
 *  org.springframework.amqp.rabbit.connection.ConnectionNameStrategy
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.amqp;

import org.springframework.amqp.rabbit.connection.AbstractConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.util.Assert;

public abstract class AbstractConnectionFactoryConfigurer<T extends AbstractConnectionFactory> {
    private final RabbitProperties rabbitProperties;
    private ConnectionNameStrategy connectionNameStrategy;

    protected AbstractConnectionFactoryConfigurer(RabbitProperties properties) {
        Assert.notNull((Object)properties, (String)"RabbitProperties must not be null");
        this.rabbitProperties = properties;
    }

    protected final ConnectionNameStrategy getConnectionNameStrategy() {
        return this.connectionNameStrategy;
    }

    public final void setConnectionNameStrategy(ConnectionNameStrategy connectionNameStrategy) {
        this.connectionNameStrategy = connectionNameStrategy;
    }

    public final void configure(T connectionFactory) {
        Assert.notNull(connectionFactory, (String)"ConnectionFactory must not be null");
        PropertyMapper map = PropertyMapper.get();
        map.from(this.rabbitProperties::determineAddresses).to(arg_0 -> connectionFactory.setAddresses(arg_0));
        map.from(this.rabbitProperties::getAddressShuffleMode).whenNonNull().to(arg_0 -> connectionFactory.setAddressShuffleMode(arg_0));
        map.from((Object)this.connectionNameStrategy).whenNonNull().to(arg_0 -> connectionFactory.setConnectionNameStrategy(arg_0));
        this.configure(connectionFactory, this.rabbitProperties);
    }

    protected abstract void configure(T var1, RabbitProperties var2);
}


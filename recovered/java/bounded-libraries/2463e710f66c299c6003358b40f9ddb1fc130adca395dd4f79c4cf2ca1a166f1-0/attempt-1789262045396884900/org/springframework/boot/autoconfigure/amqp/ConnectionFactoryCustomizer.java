/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.rabbitmq.client.ConnectionFactory
 */
package org.springframework.boot.autoconfigure.amqp;

import com.rabbitmq.client.ConnectionFactory;

@FunctionalInterface
public interface ConnectionFactoryCustomizer {
    public void customize(ConnectionFactory var1);
}


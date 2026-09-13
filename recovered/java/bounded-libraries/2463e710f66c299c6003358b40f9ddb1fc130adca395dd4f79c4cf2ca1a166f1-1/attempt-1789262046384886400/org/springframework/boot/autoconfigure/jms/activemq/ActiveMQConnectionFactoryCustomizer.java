/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.ActiveMQConnectionFactory
 */
package org.springframework.boot.autoconfigure.jms.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

@FunctionalInterface
public interface ActiveMQConnectionFactoryCustomizer {
    public void customize(ActiveMQConnectionFactory var1);
}


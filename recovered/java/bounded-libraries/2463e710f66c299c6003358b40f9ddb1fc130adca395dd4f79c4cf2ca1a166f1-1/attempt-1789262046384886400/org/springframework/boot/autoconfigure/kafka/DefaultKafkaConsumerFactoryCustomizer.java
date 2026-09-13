/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.kafka.core.DefaultKafkaConsumerFactory
 */
package org.springframework.boot.autoconfigure.kafka;

import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@FunctionalInterface
public interface DefaultKafkaConsumerFactoryCustomizer {
    public void customize(DefaultKafkaConsumerFactory<?, ?> var1);
}


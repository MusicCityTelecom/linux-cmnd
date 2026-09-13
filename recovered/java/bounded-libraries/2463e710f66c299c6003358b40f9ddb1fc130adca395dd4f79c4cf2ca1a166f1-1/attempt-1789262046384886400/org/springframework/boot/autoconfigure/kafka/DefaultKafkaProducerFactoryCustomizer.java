/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.kafka.core.DefaultKafkaProducerFactory
 */
package org.springframework.boot.autoconfigure.kafka;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;

@FunctionalInterface
public interface DefaultKafkaProducerFactoryCustomizer {
    public void customize(DefaultKafkaProducerFactory<?, ?> var1);
}


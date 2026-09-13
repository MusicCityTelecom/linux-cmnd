/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.kafka.config.StreamsBuilderFactoryBean
 */
package org.springframework.boot.autoconfigure.kafka;

import org.springframework.kafka.config.StreamsBuilderFactoryBean;

@FunctionalInterface
public interface StreamsBuilderFactoryBeanCustomizer {
    public void customize(StreamsBuilderFactoryBean var1);
}


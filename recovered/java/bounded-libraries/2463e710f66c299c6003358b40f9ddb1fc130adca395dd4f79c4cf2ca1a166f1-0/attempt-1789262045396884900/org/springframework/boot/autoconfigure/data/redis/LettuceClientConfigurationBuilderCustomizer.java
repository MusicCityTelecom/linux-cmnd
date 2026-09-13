/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration$LettuceClientConfigurationBuilder
 */
package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;

@FunctionalInterface
public interface LettuceClientConfigurationBuilderCustomizer {
    public void customize(LettuceClientConfiguration.LettuceClientConfigurationBuilder var1);
}


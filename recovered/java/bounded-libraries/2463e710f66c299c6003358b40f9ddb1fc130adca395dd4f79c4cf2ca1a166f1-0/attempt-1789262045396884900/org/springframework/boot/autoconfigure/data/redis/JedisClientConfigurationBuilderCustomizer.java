/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.data.redis.connection.jedis.JedisClientConfiguration$JedisClientConfigurationBuilder
 */
package org.springframework.boot.autoconfigure.data.redis;

import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;

@FunctionalInterface
public interface JedisClientConfigurationBuilderCustomizer {
    public void customize(JedisClientConfiguration.JedisClientConfigurationBuilder var1);
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.pool2.impl.GenericObjectPool
 *  org.apache.commons.pool2.impl.GenericObjectPoolConfig
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.redis.connection.RedisClusterConfiguration
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.connection.RedisSentinelConfiguration
 *  org.springframework.data.redis.connection.RedisStandaloneConfiguration
 *  org.springframework.data.redis.connection.jedis.JedisClientConfiguration
 *  org.springframework.data.redis.connection.jedis.JedisClientConfiguration$JedisClientConfigurationBuilder
 *  org.springframework.data.redis.connection.jedis.JedisConnection
 *  org.springframework.data.redis.connection.jedis.JedisConnectionFactory
 *  org.springframework.util.StringUtils
 *  redis.clients.jedis.Jedis
 *  redis.clients.jedis.JedisPoolConfig
 */
package org.springframework.boot.autoconfigure.data.redis;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.JedisClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisConnectionConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={GenericObjectPool.class, JedisConnection.class, Jedis.class})
@ConditionalOnMissingBean(value={RedisConnectionFactory.class})
@ConditionalOnProperty(name={"spring.redis.client-type"}, havingValue="jedis", matchIfMissing=true)
class JedisConnectionConfiguration
extends RedisConnectionConfiguration {
    JedisConnectionConfiguration(RedisProperties properties, ObjectProvider<RedisStandaloneConfiguration> standaloneConfigurationProvider, ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration, ObjectProvider<RedisClusterConfiguration> clusterConfiguration) {
        super(properties, standaloneConfigurationProvider, sentinelConfiguration, clusterConfiguration);
    }

    @Bean
    JedisConnectionFactory redisConnectionFactory(ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers) {
        return this.createJedisConnectionFactory(builderCustomizers);
    }

    private JedisConnectionFactory createJedisConnectionFactory(ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers) {
        JedisClientConfiguration clientConfiguration = this.getJedisClientConfiguration(builderCustomizers);
        if (this.getSentinelConfig() != null) {
            return new JedisConnectionFactory(this.getSentinelConfig(), clientConfiguration);
        }
        if (this.getClusterConfiguration() != null) {
            return new JedisConnectionFactory(this.getClusterConfiguration(), clientConfiguration);
        }
        return new JedisConnectionFactory(this.getStandaloneConfig(), clientConfiguration);
    }

    private JedisClientConfiguration getJedisClientConfiguration(ObjectProvider<JedisClientConfigurationBuilderCustomizer> builderCustomizers) {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = this.applyProperties(JedisClientConfiguration.builder());
        RedisProperties.Pool pool = this.getProperties().getJedis().getPool();
        if (this.isPoolEnabled(pool)) {
            this.applyPooling(pool, builder);
        }
        if (StringUtils.hasText((String)this.getProperties().getUrl())) {
            this.customizeConfigurationFromUrl(builder);
        }
        builderCustomizers.orderedStream().forEach(customizer -> customizer.customize(builder));
        return builder.build();
    }

    private JedisClientConfiguration.JedisClientConfigurationBuilder applyProperties(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
        map.from((Object)this.getProperties().isSsl()).whenTrue().toCall(() -> ((JedisClientConfiguration.JedisClientConfigurationBuilder)builder).useSsl());
        map.from((Object)this.getProperties().getTimeout()).to(arg_0 -> ((JedisClientConfiguration.JedisClientConfigurationBuilder)builder).readTimeout(arg_0));
        map.from((Object)this.getProperties().getConnectTimeout()).to(arg_0 -> ((JedisClientConfiguration.JedisClientConfigurationBuilder)builder).connectTimeout(arg_0));
        map.from((Object)this.getProperties().getClientName()).whenHasText().to(arg_0 -> ((JedisClientConfiguration.JedisClientConfigurationBuilder)builder).clientName(arg_0));
        return builder;
    }

    private void applyPooling(RedisProperties.Pool pool, JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        builder.usePooling().poolConfig((GenericObjectPoolConfig)this.jedisPoolConfig(pool));
    }

    private JedisPoolConfig jedisPoolConfig(RedisProperties.Pool pool) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getTimeBetweenEvictionRuns() != null) {
            config.setTimeBetweenEvictionRuns(pool.getTimeBetweenEvictionRuns());
        }
        if (pool.getMaxWait() != null) {
            config.setMaxWait(pool.getMaxWait());
        }
        return config;
    }

    private void customizeConfigurationFromUrl(JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        RedisConnectionConfiguration.ConnectionInfo connectionInfo = this.parseUrl(this.getProperties().getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }
}


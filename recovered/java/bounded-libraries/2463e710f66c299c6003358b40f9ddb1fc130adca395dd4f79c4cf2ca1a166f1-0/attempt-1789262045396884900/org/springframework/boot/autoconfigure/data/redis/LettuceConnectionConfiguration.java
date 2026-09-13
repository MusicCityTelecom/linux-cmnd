/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.lettuce.core.ClientOptions
 *  io.lettuce.core.ClientOptions$Builder
 *  io.lettuce.core.RedisClient
 *  io.lettuce.core.SocketOptions
 *  io.lettuce.core.TimeoutOptions
 *  io.lettuce.core.cluster.ClusterClientOptions
 *  io.lettuce.core.cluster.ClusterClientOptions$Builder
 *  io.lettuce.core.cluster.ClusterTopologyRefreshOptions
 *  io.lettuce.core.cluster.ClusterTopologyRefreshOptions$Builder
 *  io.lettuce.core.resource.ClientResources
 *  io.lettuce.core.resource.ClientResources$Builder
 *  io.lettuce.core.resource.DefaultClientResources
 *  io.lettuce.core.resource.DefaultClientResources$Builder
 *  org.apache.commons.pool2.impl.GenericObjectPoolConfig
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.redis.connection.RedisClusterConfiguration
 *  org.springframework.data.redis.connection.RedisConnectionFactory
 *  org.springframework.data.redis.connection.RedisSentinelConfiguration
 *  org.springframework.data.redis.connection.RedisStandaloneConfiguration
 *  org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
 *  org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration$LettuceClientConfigurationBuilder
 *  org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
 *  org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.data.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import java.time.Duration;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.ClientResourcesBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisConnectionConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
@ConditionalOnClass(value={RedisClient.class})
@ConditionalOnProperty(name={"spring.redis.client-type"}, havingValue="lettuce", matchIfMissing=true)
class LettuceConnectionConfiguration
extends RedisConnectionConfiguration {
    LettuceConnectionConfiguration(RedisProperties properties, ObjectProvider<RedisStandaloneConfiguration> standaloneConfigurationProvider, ObjectProvider<RedisSentinelConfiguration> sentinelConfigurationProvider, ObjectProvider<RedisClusterConfiguration> clusterConfigurationProvider) {
        super(properties, standaloneConfigurationProvider, sentinelConfigurationProvider, clusterConfigurationProvider);
    }

    @Bean(destroyMethod="shutdown")
    @ConditionalOnMissingBean(value={ClientResources.class})
    DefaultClientResources lettuceClientResources(ObjectProvider<ClientResourcesBuilderCustomizer> customizers) {
        DefaultClientResources.Builder builder = DefaultClientResources.builder();
        customizers.orderedStream().forEach(customizer -> customizer.customize((ClientResources.Builder)builder));
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean(value={RedisConnectionFactory.class})
    LettuceConnectionFactory redisConnectionFactory(ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers, ClientResources clientResources) {
        LettuceClientConfiguration clientConfig = this.getLettuceClientConfiguration(builderCustomizers, clientResources, this.getProperties().getLettuce().getPool());
        return this.createLettuceConnectionFactory(clientConfig);
    }

    private LettuceConnectionFactory createLettuceConnectionFactory(LettuceClientConfiguration clientConfiguration) {
        if (this.getSentinelConfig() != null) {
            return new LettuceConnectionFactory(this.getSentinelConfig(), clientConfiguration);
        }
        if (this.getClusterConfiguration() != null) {
            return new LettuceConnectionFactory(this.getClusterConfiguration(), clientConfiguration);
        }
        return new LettuceConnectionFactory(this.getStandaloneConfig(), clientConfiguration);
    }

    private LettuceClientConfiguration getLettuceClientConfiguration(ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers, ClientResources clientResources, RedisProperties.Pool pool) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = this.createBuilder(pool);
        this.applyProperties(builder);
        if (StringUtils.hasText((String)this.getProperties().getUrl())) {
            this.customizeConfigurationFromUrl(builder);
        }
        builder.clientOptions(this.createClientOptions());
        builder.clientResources(clientResources);
        builderCustomizers.orderedStream().forEach(customizer -> customizer.customize(builder));
        return builder.build();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool pool) {
        if (this.isPoolEnabled(pool)) {
            return new PoolBuilderFactory().createBuilder(pool);
        }
        return LettuceClientConfiguration.builder();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder applyProperties(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        RedisProperties.Lettuce lettuce;
        if (this.getProperties().isSsl()) {
            builder.useSsl();
        }
        if (this.getProperties().getTimeout() != null) {
            builder.commandTimeout(this.getProperties().getTimeout());
        }
        if (this.getProperties().getLettuce() != null && (lettuce = this.getProperties().getLettuce()).getShutdownTimeout() != null && !lettuce.getShutdownTimeout().isZero()) {
            builder.shutdownTimeout(this.getProperties().getLettuce().getShutdownTimeout());
        }
        if (StringUtils.hasText((String)this.getProperties().getClientName())) {
            builder.clientName(this.getProperties().getClientName());
        }
        return builder;
    }

    private ClientOptions createClientOptions() {
        ClientOptions.Builder builder = this.initializeClientOptionsBuilder();
        Duration connectTimeout = this.getProperties().getConnectTimeout();
        if (connectTimeout != null) {
            builder.socketOptions(SocketOptions.builder().connectTimeout(connectTimeout).build());
        }
        return builder.timeoutOptions(TimeoutOptions.enabled()).build();
    }

    private ClientOptions.Builder initializeClientOptionsBuilder() {
        if (this.getProperties().getCluster() != null) {
            ClusterClientOptions.Builder builder = ClusterClientOptions.builder();
            RedisProperties.Lettuce.Cluster.Refresh refreshProperties = this.getProperties().getLettuce().getCluster().getRefresh();
            ClusterTopologyRefreshOptions.Builder refreshBuilder = ClusterTopologyRefreshOptions.builder().dynamicRefreshSources(refreshProperties.isDynamicRefreshSources());
            if (refreshProperties.getPeriod() != null) {
                refreshBuilder.enablePeriodicRefresh(refreshProperties.getPeriod());
            }
            if (refreshProperties.isAdaptive()) {
                refreshBuilder.enableAllAdaptiveRefreshTriggers();
            }
            return builder.topologyRefreshOptions(refreshBuilder.build());
        }
        return ClientOptions.builder();
    }

    private void customizeConfigurationFromUrl(LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        RedisConnectionConfiguration.ConnectionInfo connectionInfo = this.parseUrl(this.getProperties().getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }

    private static class PoolBuilderFactory {
        private PoolBuilderFactory() {
        }

        LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool properties) {
            return LettucePoolingClientConfiguration.builder().poolConfig(this.getPoolConfig(properties));
        }

        private GenericObjectPoolConfig<?> getPoolConfig(RedisProperties.Pool properties) {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(properties.getMaxActive());
            config.setMaxIdle(properties.getMaxIdle());
            config.setMinIdle(properties.getMinIdle());
            if (properties.getTimeBetweenEvictionRuns() != null) {
                config.setTimeBetweenEvictionRuns(properties.getTimeBetweenEvictionRuns());
            }
            if (properties.getMaxWait() != null) {
                config.setMaxWait(properties.getMaxWait());
            }
            return config;
        }
    }
}


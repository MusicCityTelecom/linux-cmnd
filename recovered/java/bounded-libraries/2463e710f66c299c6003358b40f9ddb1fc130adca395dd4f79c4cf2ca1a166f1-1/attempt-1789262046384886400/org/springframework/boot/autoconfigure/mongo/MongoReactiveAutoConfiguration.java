/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.MongoClientSettings
 *  com.mongodb.MongoClientSettings$Builder
 *  com.mongodb.connection.StreamFactoryFactory
 *  com.mongodb.connection.netty.NettyStreamFactoryFactory
 *  com.mongodb.reactivestreams.client.MongoClient
 *  io.netty.channel.EventLoopGroup
 *  io.netty.channel.nio.NioEventLoopGroup
 *  io.netty.channel.socket.SocketChannel
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.env.Environment
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.mongo;

import com.mongodb.MongoClientSettings;
import com.mongodb.connection.StreamFactoryFactory;
import com.mongodb.connection.netty.NettyStreamFactoryFactory;
import com.mongodb.reactivestreams.client.MongoClient;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import java.util.stream.Collectors;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.autoconfigure.mongo.MongoPropertiesClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.ReactiveMongoClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Flux;

@AutoConfiguration
@ConditionalOnClass(value={MongoClient.class, Flux.class})
@EnableConfigurationProperties(value={MongoProperties.class})
public class MongoReactiveAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MongoClient reactiveStreamsMongoClient(ObjectProvider<MongoClientSettingsBuilderCustomizer> builderCustomizers, MongoClientSettings settings) {
        ReactiveMongoClientFactory factory = new ReactiveMongoClientFactory(builderCustomizers.orderedStream().collect(Collectors.toList()));
        return (MongoClient)factory.createMongoClient(settings);
    }

    static final class NettyDriverMongoClientSettingsBuilderCustomizer
    implements MongoClientSettingsBuilderCustomizer,
    DisposableBean {
        private final ObjectProvider<MongoClientSettings> settings;
        private volatile EventLoopGroup eventLoopGroup;

        NettyDriverMongoClientSettingsBuilderCustomizer(ObjectProvider<MongoClientSettings> settings) {
            this.settings = settings;
        }

        @Override
        public void customize(MongoClientSettings.Builder builder) {
            if (!this.isStreamFactoryFactoryDefined((MongoClientSettings)this.settings.getIfAvailable())) {
                NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
                this.eventLoopGroup = eventLoopGroup;
                builder.streamFactoryFactory((StreamFactoryFactory)NettyStreamFactoryFactory.builder().eventLoopGroup((EventLoopGroup)eventLoopGroup).build());
            }
        }

        public void destroy() {
            EventLoopGroup eventLoopGroup = this.eventLoopGroup;
            if (eventLoopGroup != null) {
                eventLoopGroup.shutdownGracefully().awaitUninterruptibly();
                this.eventLoopGroup = null;
            }
        }

        private boolean isStreamFactoryFactoryDefined(MongoClientSettings settings) {
            return settings != null && settings.getStreamFactoryFactory() != null;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={SocketChannel.class, NioEventLoopGroup.class})
    static class NettyDriverConfiguration {
        NettyDriverConfiguration() {
        }

        @Bean
        @Order(value=-2147483648)
        NettyDriverMongoClientSettingsBuilderCustomizer nettyDriverCustomizer(ObjectProvider<MongoClientSettings> settings) {
            return new NettyDriverMongoClientSettingsBuilderCustomizer(settings);
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnMissingBean(value={MongoClientSettings.class})
    static class MongoClientSettingsConfiguration {
        MongoClientSettingsConfiguration() {
        }

        @Bean
        MongoClientSettings mongoClientSettings() {
            return MongoClientSettings.builder().build();
        }

        @Bean
        MongoPropertiesClientSettingsBuilderCustomizer mongoPropertiesCustomizer(MongoProperties properties, Environment environment) {
            return new MongoPropertiesClientSettingsBuilderCustomizer(properties, environment);
        }
    }
}


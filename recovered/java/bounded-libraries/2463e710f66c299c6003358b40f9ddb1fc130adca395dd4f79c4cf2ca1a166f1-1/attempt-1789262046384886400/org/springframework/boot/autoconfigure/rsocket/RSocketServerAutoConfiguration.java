/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.core.RSocketServer
 *  io.rsocket.frame.decoder.PayloadDecoder
 *  io.rsocket.transport.netty.server.TcpServerTransport
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.rsocket.context.RSocketServerBootstrap
 *  org.springframework.boot.rsocket.netty.NettyRSocketServerFactory
 *  org.springframework.boot.rsocket.server.RSocketServerCustomizer
 *  org.springframework.boot.rsocket.server.RSocketServerFactory
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.core.io.buffer.NettyDataBufferFactory
 *  org.springframework.http.client.reactive.ReactorResourceFactory
 *  org.springframework.messaging.rsocket.RSocketStrategies
 *  org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
 *  reactor.netty.http.server.HttpServer
 */
package org.springframework.boot.autoconfigure.rsocket;

import io.rsocket.core.RSocketServer;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.server.TcpServerTransport;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.boot.autoconfigure.rsocket.RSocketWebSocketNettyRouteProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.rsocket.context.RSocketServerBootstrap;
import org.springframework.boot.rsocket.netty.NettyRSocketServerFactory;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.boot.rsocket.server.RSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import reactor.netty.http.server.HttpServer;

@AutoConfiguration(after={RSocketStrategiesAutoConfiguration.class})
@ConditionalOnClass(value={RSocketServer.class, RSocketStrategies.class, HttpServer.class, TcpServerTransport.class})
@ConditionalOnBean(value={RSocketMessageHandler.class})
@EnableConfigurationProperties(value={RSocketProperties.class})
public class RSocketServerAutoConfiguration {

    static class OnRSocketWebServerCondition
    extends AllNestedConditions {
        OnRSocketWebServerCondition() {
            super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix="spring.rsocket.server", name={"transport"}, havingValue="websocket")
        static class HasWebsocketTransportConfigured {
            HasWebsocketTransportConfigured() {
            }
        }

        @ConditionalOnProperty(prefix="spring.rsocket.server", name={"mapping-path"})
        static class HasMappingPathConfigured {
            HasMappingPathConfigured() {
            }
        }

        @ConditionalOnProperty(prefix="spring.rsocket.server", name={"port"}, matchIfMissing=true)
        static class HasNoPortConfigured {
            HasNoPortConfigured() {
            }
        }

        @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
        static class IsReactiveWebApplication {
            IsReactiveWebApplication() {
            }
        }
    }

    @ConditionalOnProperty(prefix="spring.rsocket.server", name={"port"})
    @ConditionalOnClass(value={ReactorResourceFactory.class})
    @Configuration(proxyBeanMethods=false)
    static class EmbeddedServerConfiguration {
        EmbeddedServerConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        ReactorResourceFactory reactorResourceFactory() {
            return new ReactorResourceFactory();
        }

        @Bean
        @ConditionalOnMissingBean
        RSocketServerFactory rSocketServerFactory(RSocketProperties properties, ReactorResourceFactory resourceFactory, ObjectProvider<RSocketServerCustomizer> customizers) {
            NettyRSocketServerFactory factory = new NettyRSocketServerFactory();
            factory.setResourceFactory(resourceFactory);
            factory.setTransport(properties.getServer().getTransport());
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            map.from((Object)properties.getServer().getAddress()).to(arg_0 -> ((NettyRSocketServerFactory)factory).setAddress(arg_0));
            map.from((Object)properties.getServer().getPort()).to(arg_0 -> ((NettyRSocketServerFactory)factory).setPort(arg_0));
            map.from((Object)properties.getServer().getFragmentSize()).to(arg_0 -> ((NettyRSocketServerFactory)factory).setFragmentSize(arg_0));
            map.from((Object)properties.getServer().getSsl()).to(arg_0 -> ((NettyRSocketServerFactory)factory).setSsl(arg_0));
            factory.setRSocketServerCustomizers((Collection)customizers.orderedStream().collect(Collectors.toList()));
            return factory;
        }

        @Bean
        @ConditionalOnMissingBean
        RSocketServerBootstrap rSocketServerBootstrap(RSocketServerFactory rSocketServerFactory, RSocketMessageHandler rSocketMessageHandler) {
            return new RSocketServerBootstrap(rSocketServerFactory, rSocketMessageHandler.responder());
        }

        @Bean
        RSocketServerCustomizer frameDecoderRSocketServerCustomizer(RSocketMessageHandler rSocketMessageHandler) {
            return server -> {
                if (rSocketMessageHandler.getRSocketStrategies().dataBufferFactory() instanceof NettyDataBufferFactory) {
                    server.payloadDecoder(PayloadDecoder.ZERO_COPY);
                }
            };
        }
    }

    @Conditional(value={OnRSocketWebServerCondition.class})
    @Configuration(proxyBeanMethods=false)
    static class WebFluxServerConfiguration {
        WebFluxServerConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean
        RSocketWebSocketNettyRouteProvider rSocketWebsocketRouteProvider(RSocketProperties properties, RSocketMessageHandler messageHandler, ObjectProvider<RSocketServerCustomizer> customizers) {
            return new RSocketWebSocketNettyRouteProvider(properties.getServer().getMappingPath(), messageHandler.responder(), customizers.orderedStream());
        }
    }
}


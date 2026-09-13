/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.RSocket
 *  io.rsocket.transport.netty.server.TcpServerTransport
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Scope
 *  org.springframework.messaging.rsocket.RSocketConnectorConfigurer
 *  org.springframework.messaging.rsocket.RSocketRequester
 *  org.springframework.messaging.rsocket.RSocketRequester$Builder
 *  org.springframework.messaging.rsocket.RSocketStrategies
 *  reactor.netty.http.server.HttpServer
 */
package org.springframework.boot.autoconfigure.rsocket;

import io.rsocket.RSocket;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.rsocket.RSocketConnectorConfigurer;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.netty.http.server.HttpServer;

@AutoConfiguration(after={RSocketStrategiesAutoConfiguration.class})
@ConditionalOnClass(value={RSocketRequester.class, RSocket.class, HttpServer.class, TcpServerTransport.class})
public class RSocketRequesterAutoConfiguration {
    @Bean
    @Scope(value="prototype")
    @ConditionalOnMissingBean
    public RSocketRequester.Builder rSocketRequesterBuilder(RSocketStrategies strategies, ObjectProvider<RSocketConnectorConfigurer> connectorConfigurers) {
        RSocketRequester.Builder builder = RSocketRequester.builder().rsocketStrategies(strategies);
        connectorConfigurers.orderedStream().forEach(arg_0 -> ((RSocketRequester.Builder)builder).rsocketConnector(arg_0));
        return builder;
    }
}


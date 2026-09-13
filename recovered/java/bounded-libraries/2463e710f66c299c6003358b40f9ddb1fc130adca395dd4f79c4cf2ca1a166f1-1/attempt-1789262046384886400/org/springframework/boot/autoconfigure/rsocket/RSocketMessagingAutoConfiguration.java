/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.rsocket.RSocket
 *  io.rsocket.transport.netty.server.TcpServerTransport
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.messaging.rsocket.RSocketRequester
 *  org.springframework.messaging.rsocket.RSocketStrategies
 *  org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
 */
package org.springframework.boot.autoconfigure.rsocket;

import io.rsocket.RSocket;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessageHandlerCustomizer;
import org.springframework.boot.autoconfigure.rsocket.RSocketStrategiesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;

@AutoConfiguration(after={RSocketStrategiesAutoConfiguration.class})
@ConditionalOnClass(value={RSocketRequester.class, RSocket.class, TcpServerTransport.class})
public class RSocketMessagingAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RSocketMessageHandler messageHandler(RSocketStrategies rSocketStrategies, ObjectProvider<RSocketMessageHandlerCustomizer> customizers) {
        RSocketMessageHandler messageHandler = new RSocketMessageHandler();
        messageHandler.setRSocketStrategies(rSocketStrategies);
        customizers.orderedStream().forEach(customizer -> customizer.customize(messageHandler));
        return messageHandler;
    }
}


/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.GraphQL
 *  io.rsocket.RSocket
 *  io.rsocket.transport.netty.client.TcpClientTransport
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Scope
 *  org.springframework.graphql.client.RSocketGraphQlClient
 *  org.springframework.graphql.client.RSocketGraphQlClient$Builder
 *  org.springframework.messaging.rsocket.RSocketRequester
 *  org.springframework.messaging.rsocket.RSocketRequester$Builder
 *  org.springframework.util.MimeTypeUtils
 */
package org.springframework.boot.autoconfigure.graphql.rsocket;

import graphql.GraphQL;
import io.rsocket.RSocket;
import io.rsocket.transport.netty.client.TcpClientTransport;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.rsocket.RSocketRequesterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.graphql.client.RSocketGraphQlClient;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;

@AutoConfiguration(after={RSocketRequesterAutoConfiguration.class})
@ConditionalOnClass(value={GraphQL.class, RSocketGraphQlClient.class, RSocketRequester.class, RSocket.class, TcpClientTransport.class})
public class RSocketGraphQlClientAutoConfiguration {
    @Bean
    @Scope(value="prototype")
    @ConditionalOnMissingBean
    public RSocketGraphQlClient.Builder<?> rsocketGraphQlClientBuilder(RSocketRequester.Builder rsocketRequesterBuilder) {
        return RSocketGraphQlClient.builder((RSocketRequester.Builder)rsocketRequesterBuilder.dataMimeType(MimeTypeUtils.APPLICATION_JSON));
    }
}


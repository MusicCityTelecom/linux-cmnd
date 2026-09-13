/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  graphql.GraphQL
 *  io.rsocket.core.RSocketServer
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.annotation.Bean
 *  org.springframework.core.codec.Encoder
 *  org.springframework.graphql.ExecutionGraphQlService
 *  org.springframework.graphql.data.method.annotation.support.AnnotatedControllerConfigurer
 *  org.springframework.graphql.execution.GraphQlSource
 *  org.springframework.graphql.server.GraphQlRSocketHandler
 *  org.springframework.graphql.server.RSocketGraphQlInterceptor
 *  org.springframework.http.codec.json.Jackson2JsonEncoder
 *  org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
 *  org.springframework.util.MimeType
 *  reactor.netty.http.server.HttpServer
 */
package org.springframework.boot.autoconfigure.graphql.rsocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQL;
import io.rsocket.core.RSocketServer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.rsocket.GraphQlRSocketController;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessagingAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.codec.Encoder;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.data.method.annotation.support.AnnotatedControllerConfigurer;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.graphql.server.GraphQlRSocketHandler;
import org.springframework.graphql.server.RSocketGraphQlInterceptor;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.util.MimeType;
import reactor.netty.http.server.HttpServer;

@AutoConfiguration(after={GraphQlAutoConfiguration.class, RSocketMessagingAutoConfiguration.class})
@ConditionalOnClass(value={GraphQL.class, GraphQlSource.class, RSocketServer.class, HttpServer.class})
@ConditionalOnBean(value={RSocketMessageHandler.class, AnnotatedControllerConfigurer.class})
@ConditionalOnProperty(prefix="spring.graphql.rsocket", name={"mapping"})
public class GraphQlRSocketAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public GraphQlRSocketHandler graphQlRSocketHandler(ExecutionGraphQlService graphQlService, ObjectProvider<RSocketGraphQlInterceptor> interceptorsProvider, ObjectMapper objectMapper) {
        List interceptors = interceptorsProvider.orderedStream().collect(Collectors.toList());
        return new GraphQlRSocketHandler(graphQlService, interceptors, (Encoder)new Jackson2JsonEncoder(objectMapper, new MimeType[0]));
    }

    @Bean
    @ConditionalOnMissingBean
    public GraphQlRSocketController graphQlRSocketController(GraphQlRSocketHandler handler) {
        return new GraphQlRSocketController(handler);
    }
}


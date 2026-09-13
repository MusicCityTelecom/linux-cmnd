/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.GraphQL
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.annotation.Order
 *  org.springframework.core.log.LogMessage
 *  org.springframework.graphql.ExecutionGraphQlService
 *  org.springframework.graphql.execution.GraphQlSource
 *  org.springframework.graphql.server.WebGraphQlHandler
 *  org.springframework.graphql.server.WebGraphQlInterceptor
 *  org.springframework.graphql.server.webflux.GraphQlHttpHandler
 *  org.springframework.graphql.server.webflux.GraphQlWebSocketHandler
 *  org.springframework.graphql.server.webflux.GraphiQlHandler
 *  org.springframework.graphql.server.webflux.SchemaHandler
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.http.codec.CodecConfigurer
 *  org.springframework.http.codec.ServerCodecConfigurer
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.reactive.HandlerMapping
 *  org.springframework.web.reactive.config.CorsRegistry
 *  org.springframework.web.reactive.config.WebFluxConfigurer
 *  org.springframework.web.reactive.function.server.RequestPredicate
 *  org.springframework.web.reactive.function.server.RequestPredicates
 *  org.springframework.web.reactive.function.server.RouterFunction
 *  org.springframework.web.reactive.function.server.RouterFunctions
 *  org.springframework.web.reactive.function.server.RouterFunctions$Builder
 *  org.springframework.web.reactive.function.server.ServerRequest
 *  org.springframework.web.reactive.function.server.ServerResponse
 *  org.springframework.web.reactive.function.server.ServerResponse$BodyBuilder
 *  org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
 *  org.springframework.web.reactive.socket.server.support.WebSocketUpgradeHandlerPredicate
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.autoconfigure.graphql.reactive;

import graphql.GraphQL;
import java.util.Collections;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration;
import org.springframework.boot.autoconfigure.graphql.GraphQlCorsProperties;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.log.LogMessage;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.webflux.GraphQlHttpHandler;
import org.springframework.graphql.server.webflux.GraphQlWebSocketHandler;
import org.springframework.graphql.server.webflux.GraphiQlHandler;
import org.springframework.graphql.server.webflux.SchemaHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketUpgradeHandlerPredicate;
import reactor.core.publisher.Mono;

@AutoConfiguration(after={GraphQlAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.REACTIVE)
@ConditionalOnClass(value={GraphQL.class, GraphQlHttpHandler.class})
@ConditionalOnBean(value={ExecutionGraphQlService.class})
@EnableConfigurationProperties(value={GraphQlCorsProperties.class})
public class GraphQlWebFluxAutoConfiguration {
    private static final RequestPredicate SUPPORTS_MEDIATYPES = RequestPredicates.accept((MediaType[])new MediaType[]{MediaType.APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON}).and(RequestPredicates.contentType((MediaType[])new MediaType[]{MediaType.APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON}));
    private static final Log logger = LogFactory.getLog(GraphQlWebFluxAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public GraphQlHttpHandler graphQlHttpHandler(WebGraphQlHandler webGraphQlHandler) {
        return new GraphQlHttpHandler(webGraphQlHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public WebGraphQlHandler webGraphQlHandler(ExecutionGraphQlService service, ObjectProvider<WebGraphQlInterceptor> interceptorsProvider) {
        return WebGraphQlHandler.builder((ExecutionGraphQlService)service).interceptors(interceptorsProvider.orderedStream().collect(Collectors.toList())).build();
    }

    @Bean
    @Order(value=0)
    public RouterFunction<ServerResponse> graphQlRouterFunction(GraphQlHttpHandler httpHandler, GraphQlSource graphQlSource, GraphQlProperties properties) {
        String path = properties.getPath();
        logger.info((Object)LogMessage.format((String)"GraphQL endpoint HTTP POST %s", (Object)path));
        RouterFunctions.Builder builder = RouterFunctions.route();
        builder = builder.GET(path, this::onlyAllowPost);
        builder = builder.POST(path, SUPPORTS_MEDIATYPES, arg_0 -> ((GraphQlHttpHandler)httpHandler).handleRequest(arg_0));
        if (properties.getGraphiql().isEnabled()) {
            GraphiQlHandler graphQlHandler = new GraphiQlHandler(path, properties.getWebsocket().getPath());
            builder = builder.GET(properties.getGraphiql().getPath(), arg_0 -> ((GraphiQlHandler)graphQlHandler).handleRequest(arg_0));
        }
        if (properties.getSchema().getPrinter().isEnabled()) {
            SchemaHandler schemaHandler = new SchemaHandler(graphQlSource);
            builder = builder.GET(path + "/schema", arg_0 -> ((SchemaHandler)schemaHandler).handleRequest(arg_0));
        }
        return builder.build();
    }

    private Mono<ServerResponse> onlyAllowPost(ServerRequest request) {
        return ((ServerResponse.BodyBuilder)ServerResponse.status((HttpStatus)HttpStatus.METHOD_NOT_ALLOWED).headers(this::onlyAllowPost)).build();
    }

    private void onlyAllowPost(HttpHeaders headers) {
        headers.setAllow(Collections.singleton(HttpMethod.POST));
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnProperty(prefix="spring.graphql.websocket", name={"path"})
    public static class WebSocketConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public GraphQlWebSocketHandler graphQlWebSocketHandler(WebGraphQlHandler webGraphQlHandler, GraphQlProperties properties, ServerCodecConfigurer configurer) {
            return new GraphQlWebSocketHandler(webGraphQlHandler, (CodecConfigurer)configurer, properties.getWebsocket().getConnectionInitTimeout());
        }

        @Bean
        public HandlerMapping graphQlWebSocketEndpoint(GraphQlWebSocketHandler graphQlWebSocketHandler, GraphQlProperties properties) {
            String path = properties.getWebsocket().getPath();
            logger.info((Object)LogMessage.format((String)"GraphQL endpoint WebSocket %s", (Object)path));
            SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
            mapping.setHandlerPredicate((BiPredicate)new WebSocketUpgradeHandlerPredicate());
            mapping.setUrlMap(Collections.singletonMap(path, graphQlWebSocketHandler));
            mapping.setOrder(-2);
            return mapping;
        }
    }

    @Configuration(proxyBeanMethods=false)
    public static class GraphQlEndpointCorsConfiguration
    implements WebFluxConfigurer {
        final GraphQlProperties graphQlProperties;
        final GraphQlCorsProperties corsProperties;

        public GraphQlEndpointCorsConfiguration(GraphQlProperties graphQlProps, GraphQlCorsProperties corsProps) {
            this.graphQlProperties = graphQlProps;
            this.corsProperties = corsProps;
        }

        public void addCorsMappings(CorsRegistry registry) {
            CorsConfiguration configuration = this.corsProperties.toCorsConfiguration();
            if (configuration != null) {
                registry.addMapping(this.graphQlProperties.getPath()).combine(configuration);
            }
        }
    }
}


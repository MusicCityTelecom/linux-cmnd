/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  graphql.GraphQL
 *  javax.websocket.server.ServerContainer
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
 *  org.springframework.graphql.execution.ThreadLocalAccessor
 *  org.springframework.graphql.server.WebGraphQlHandler
 *  org.springframework.graphql.server.WebGraphQlInterceptor
 *  org.springframework.graphql.server.webmvc.GraphQlHttpHandler
 *  org.springframework.graphql.server.webmvc.GraphQlWebSocketHandler
 *  org.springframework.graphql.server.webmvc.GraphiQlHandler
 *  org.springframework.graphql.server.webmvc.SchemaHandler
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.http.converter.GenericHttpMessageConverter
 *  org.springframework.http.converter.HttpMessageConverter
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.servlet.HandlerMapping
 *  org.springframework.web.servlet.config.annotation.CorsRegistry
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 *  org.springframework.web.servlet.function.RequestPredicates
 *  org.springframework.web.servlet.function.RouterFunction
 *  org.springframework.web.servlet.function.RouterFunctions
 *  org.springframework.web.servlet.function.RouterFunctions$Builder
 *  org.springframework.web.servlet.function.ServerRequest
 *  org.springframework.web.servlet.function.ServerResponse
 *  org.springframework.web.servlet.function.ServerResponse$BodyBuilder
 *  org.springframework.web.socket.WebSocketHandler
 *  org.springframework.web.socket.server.HandshakeHandler
 *  org.springframework.web.socket.server.support.DefaultHandshakeHandler
 *  org.springframework.web.socket.server.support.WebSocketHandlerMapping
 */
package org.springframework.boot.autoconfigure.graphql.servlet;

import graphql.GraphQL;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import javax.websocket.server.ServerContainer;
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
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.log.LogMessage;
import org.springframework.graphql.ExecutionGraphQlService;
import org.springframework.graphql.execution.GraphQlSource;
import org.springframework.graphql.execution.ThreadLocalAccessor;
import org.springframework.graphql.server.WebGraphQlHandler;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.webmvc.GraphQlHttpHandler;
import org.springframework.graphql.server.webmvc.GraphQlWebSocketHandler;
import org.springframework.graphql.server.webmvc.GraphiQlHandler;
import org.springframework.graphql.server.webmvc.SchemaHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.WebSocketHandlerMapping;

@AutoConfiguration(after={GraphQlAutoConfiguration.class})
@ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(value={GraphQL.class, GraphQlHttpHandler.class})
@ConditionalOnBean(value={ExecutionGraphQlService.class})
@EnableConfigurationProperties(value={GraphQlCorsProperties.class})
public class GraphQlWebMvcAutoConfiguration {
    private static final Log logger = LogFactory.getLog(GraphQlWebMvcAutoConfiguration.class);
    private static MediaType[] SUPPORTED_MEDIA_TYPES = new MediaType[]{MediaType.APPLICATION_GRAPHQL, MediaType.APPLICATION_JSON};

    @Bean
    @ConditionalOnMissingBean
    public GraphQlHttpHandler graphQlHttpHandler(WebGraphQlHandler webGraphQlHandler) {
        return new GraphQlHttpHandler(webGraphQlHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public WebGraphQlHandler webGraphQlHandler(ExecutionGraphQlService service, ObjectProvider<WebGraphQlInterceptor> interceptorsProvider, ObjectProvider<ThreadLocalAccessor> accessorsProvider) {
        return WebGraphQlHandler.builder((ExecutionGraphQlService)service).interceptors(interceptorsProvider.orderedStream().collect(Collectors.toList())).threadLocalAccessors(accessorsProvider.orderedStream().collect(Collectors.toList())).build();
    }

    @Bean
    @Order(value=0)
    public RouterFunction<ServerResponse> graphQlRouterFunction(GraphQlHttpHandler httpHandler, GraphQlSource graphQlSource, GraphQlProperties properties) {
        String path = properties.getPath();
        logger.info((Object)LogMessage.format((String)"GraphQL endpoint HTTP POST %s", (Object)path));
        RouterFunctions.Builder builder = RouterFunctions.route();
        builder = builder.GET(path, this::onlyAllowPost);
        builder = builder.POST(path, RequestPredicates.contentType((MediaType[])SUPPORTED_MEDIA_TYPES).and(RequestPredicates.accept((MediaType[])SUPPORTED_MEDIA_TYPES)), arg_0 -> ((GraphQlHttpHandler)httpHandler).handleRequest(arg_0));
        if (properties.getGraphiql().isEnabled()) {
            GraphiQlHandler graphiQLHandler = new GraphiQlHandler(path, properties.getWebsocket().getPath());
            builder = builder.GET(properties.getGraphiql().getPath(), arg_0 -> ((GraphiQlHandler)graphiQLHandler).handleRequest(arg_0));
        }
        if (properties.getSchema().getPrinter().isEnabled()) {
            SchemaHandler schemaHandler = new SchemaHandler(graphQlSource);
            builder = builder.GET(path + "/schema", arg_0 -> ((SchemaHandler)schemaHandler).handleRequest(arg_0));
        }
        return builder.build();
    }

    private ServerResponse onlyAllowPost(ServerRequest request) {
        return ((ServerResponse.BodyBuilder)ServerResponse.status((HttpStatus)HttpStatus.METHOD_NOT_ALLOWED).headers(this::onlyAllowPost)).build();
    }

    private void onlyAllowPost(HttpHeaders headers) {
        headers.setAllow(Collections.singleton(HttpMethod.POST));
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={ServerContainer.class, WebSocketHandler.class})
    @ConditionalOnProperty(prefix="spring.graphql.websocket", name={"path"})
    public static class WebSocketConfiguration {
        @Bean
        @ConditionalOnMissingBean
        public GraphQlWebSocketHandler graphQlWebSocketHandler(WebGraphQlHandler webGraphQlHandler, GraphQlProperties properties, HttpMessageConverters converters) {
            return new GraphQlWebSocketHandler(webGraphQlHandler, this.getJsonConverter(converters), properties.getWebsocket().getConnectionInitTimeout());
        }

        private GenericHttpMessageConverter<Object> getJsonConverter(HttpMessageConverters converters) {
            return converters.getConverters().stream().filter(this::canReadJsonMap).findFirst().map(this::asGenericHttpMessageConverter).orElseThrow(() -> new IllegalStateException("No JSON converter"));
        }

        private boolean canReadJsonMap(HttpMessageConverter<?> candidate) {
            return candidate.canRead(Map.class, MediaType.APPLICATION_JSON);
        }

        private GenericHttpMessageConverter<Object> asGenericHttpMessageConverter(HttpMessageConverter<?> converter) {
            return (GenericHttpMessageConverter)converter;
        }

        @Bean
        public HandlerMapping graphQlWebSocketMapping(GraphQlWebSocketHandler handler, GraphQlProperties properties) {
            String path = properties.getWebsocket().getPath();
            logger.info((Object)LogMessage.format((String)"GraphQL endpoint WebSocket %s", (Object)path));
            WebSocketHandlerMapping mapping = new WebSocketHandlerMapping();
            mapping.setWebSocketUpgradeMatch(true);
            mapping.setUrlMap(Collections.singletonMap(path, handler.asWebSocketHttpRequestHandler((HandshakeHandler)new DefaultHandshakeHandler())));
            mapping.setOrder(2);
            return mapping;
        }
    }

    @Configuration(proxyBeanMethods=false)
    public static class GraphQlEndpointCorsConfiguration
    implements WebMvcConfigurer {
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


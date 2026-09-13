/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.boot.web.context.WebServerApplicationContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.security.access.SecurityConfig
 *  org.springframework.security.access.vote.RoleVoter
 *  org.springframework.security.core.Authentication
 *  org.springframework.security.core.context.ReactiveSecurityContextHolder
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.reactive.HandlerMapping
 *  org.springframework.web.reactive.result.method.RequestMappingInfo
 *  org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping
 *  org.springframework.web.server.ResponseStatusException
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.util.pattern.PathPattern
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 *  reactor.core.scheduler.Schedulers
 */
package org.springframework.boot.actuate.endpoint.web.reactive;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.OperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.ProducibleOperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.WebOperation;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.result.method.RequestMappingInfo;
import org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public abstract class AbstractWebFluxEndpointHandlerMapping
extends RequestMappingInfoHandlerMapping {
    private final EndpointMapping endpointMapping;
    private final Collection<ExposableWebEndpoint> endpoints;
    private final EndpointMediaTypes endpointMediaTypes;
    private final CorsConfiguration corsConfiguration;
    private final Method handleWriteMethod = ReflectionUtils.findMethod(WriteOperationHandler.class, (String)"handle", (Class[])new Class[]{ServerWebExchange.class, Map.class});
    private final Method handleReadMethod = ReflectionUtils.findMethod(ReadOperationHandler.class, (String)"handle", (Class[])new Class[]{ServerWebExchange.class});
    private final boolean shouldRegisterLinksMapping;

    public AbstractWebFluxEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints, EndpointMediaTypes endpointMediaTypes, CorsConfiguration corsConfiguration, boolean shouldRegisterLinksMapping) {
        this.endpointMapping = endpointMapping;
        this.endpoints = endpoints;
        this.endpointMediaTypes = endpointMediaTypes;
        this.corsConfiguration = corsConfiguration;
        this.shouldRegisterLinksMapping = shouldRegisterLinksMapping;
        this.setOrder(-100);
    }

    protected void initHandlerMethods() {
        for (ExposableWebEndpoint endpoint : this.endpoints) {
            for (WebOperation operation : endpoint.getOperations()) {
                this.registerMappingForOperation(endpoint, operation);
            }
        }
        if (this.shouldRegisterLinksMapping) {
            this.registerLinksMapping();
        }
    }

    protected HandlerMethod createHandlerMethod(Object handler, Method method) {
        HandlerMethod handlerMethod = super.createHandlerMethod(handler, method);
        return new WebFluxEndpointHandlerMethod(handlerMethod.getBean(), handlerMethod.getMethod());
    }

    private void registerMappingForOperation(ExposableWebEndpoint endpoint, WebOperation operation) {
        RequestMappingInfo requestMappingInfo = this.createRequestMappingInfo(operation);
        if (operation.getType() == OperationType.WRITE) {
            ReactiveWebOperation reactiveWebOperation = this.wrapReactiveWebOperation(endpoint, operation, new ReactiveWebOperationAdapter(operation));
            this.registerMapping(requestMappingInfo, new WriteOperationHandler(reactiveWebOperation), this.handleWriteMethod);
        } else {
            this.registerReadMapping(requestMappingInfo, endpoint, operation);
        }
    }

    protected void registerReadMapping(RequestMappingInfo requestMappingInfo, ExposableWebEndpoint endpoint, WebOperation operation) {
        ReactiveWebOperation reactiveWebOperation = this.wrapReactiveWebOperation(endpoint, operation, new ReactiveWebOperationAdapter(operation));
        this.registerMapping(requestMappingInfo, new ReadOperationHandler(reactiveWebOperation), this.handleReadMethod);
    }

    protected ReactiveWebOperation wrapReactiveWebOperation(ExposableWebEndpoint endpoint, WebOperation operation, ReactiveWebOperation reactiveWebOperation) {
        return reactiveWebOperation;
    }

    private RequestMappingInfo createRequestMappingInfo(WebOperation operation) {
        WebOperationRequestPredicate predicate = operation.getRequestPredicate();
        String path = this.endpointMapping.createSubPath(predicate.getPath());
        RequestMethod method = RequestMethod.valueOf((String)predicate.getHttpMethod().name());
        String[] consumes = StringUtils.toStringArray(predicate.getConsumes());
        String[] produces = StringUtils.toStringArray(predicate.getProduces());
        return RequestMappingInfo.paths((String[])new String[]{path}).methods(new RequestMethod[]{method}).consumes(consumes).produces(produces).build();
    }

    private void registerLinksMapping() {
        String path = this.endpointMapping.getPath();
        String[] produces = StringUtils.toStringArray(this.endpointMediaTypes.getProduced());
        RequestMappingInfo mapping = RequestMappingInfo.paths((String[])new String[]{path}).methods(new RequestMethod[]{RequestMethod.GET}).produces(produces).build();
        LinksHandler linksHandler = this.getLinksHandler();
        this.registerMapping(mapping, linksHandler, ReflectionUtils.findMethod(linksHandler.getClass(), (String)"links", (Class[])new Class[]{ServerWebExchange.class}));
    }

    protected boolean hasCorsConfigurationSource(Object handler) {
        return this.corsConfiguration != null;
    }

    protected CorsConfiguration initCorsConfiguration(Object handler, Method method, RequestMappingInfo mapping) {
        return this.corsConfiguration;
    }

    protected boolean isHandler(Class<?> beanType) {
        return false;
    }

    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        return null;
    }

    protected abstract LinksHandler getLinksHandler();

    public Collection<ExposableWebEndpoint> getEndpoints() {
        return this.endpoints;
    }

    private static final class ReactiveSecurityContext
    implements SecurityContext {
        private final RoleVoter roleVoter = new RoleVoter();
        private final Authentication authentication;

        ReactiveSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Principal getPrincipal() {
            return this.authentication;
        }

        @Override
        public boolean isUserInRole(String role) {
            if (!role.startsWith(this.roleVoter.getRolePrefix())) {
                role = this.roleVoter.getRolePrefix() + role;
            }
            return this.roleVoter.vote(this.authentication, null, Collections.singletonList(new SecurityConfig(role))) == 1;
        }
    }

    private static class WebFluxEndpointHandlerMethod
    extends HandlerMethod {
        WebFluxEndpointHandlerMethod(Object bean, Method method) {
            super(bean, method);
        }

        public String toString() {
            return this.getBean().toString();
        }

        public HandlerMethod createWithResolvedBean() {
            HandlerMethod handlerMethod = super.createWithResolvedBean();
            return new WebFluxEndpointHandlerMethod(handlerMethod.getBean(), handlerMethod.getMethod());
        }
    }

    private static final class ReadOperationHandler {
        private final ReactiveWebOperation operation;

        ReadOperationHandler(ReactiveWebOperation operation) {
            this.operation = operation;
        }

        @ResponseBody
        Publisher<ResponseEntity<Object>> handle(ServerWebExchange exchange) {
            return this.operation.handle(exchange, null);
        }

        public String toString() {
            return this.operation.toString();
        }
    }

    private static final class WriteOperationHandler {
        private final ReactiveWebOperation operation;

        WriteOperationHandler(ReactiveWebOperation operation) {
            this.operation = operation;
        }

        @ResponseBody
        Publisher<ResponseEntity<Object>> handle(ServerWebExchange exchange, @RequestBody(required=false) Map<String, String> body) {
            return this.operation.handle(exchange, body);
        }

        public String toString() {
            return this.operation.toString();
        }
    }

    private static final class ReactiveWebOperationAdapter
    implements ReactiveWebOperation {
        private static final String PATH_SEPARATOR = "/";
        private final WebOperation operation;
        private final OperationInvoker invoker;
        private final Supplier<Mono<? extends SecurityContext>> securityContextSupplier;

        private ReactiveWebOperationAdapter(WebOperation operation) {
            this.operation = operation;
            this.invoker = this.getInvoker(operation);
            this.securityContextSupplier = this.getSecurityContextSupplier();
        }

        private OperationInvoker getInvoker(WebOperation operation) {
            OperationInvoker invoker = operation::invoke;
            if (operation.isBlocking()) {
                invoker = new ElasticSchedulerInvoker(invoker);
            }
            return invoker;
        }

        private Supplier<Mono<? extends SecurityContext>> getSecurityContextSupplier() {
            if (ClassUtils.isPresent((String)"org.springframework.security.core.context.ReactiveSecurityContextHolder", (ClassLoader)this.getClass().getClassLoader())) {
                return this::springSecurityContext;
            }
            return this::emptySecurityContext;
        }

        Mono<? extends SecurityContext> springSecurityContext() {
            return ReactiveSecurityContextHolder.getContext().map(securityContext -> new ReactiveSecurityContext(securityContext.getAuthentication())).switchIfEmpty(Mono.just((Object)new ReactiveSecurityContext(null)));
        }

        Mono<SecurityContext> emptySecurityContext() {
            return Mono.just((Object)SecurityContext.NONE);
        }

        @Override
        public Mono<ResponseEntity<Object>> handle(ServerWebExchange exchange, Map<String, String> body) {
            Map<String, Object> arguments = this.getArguments(exchange, body);
            OperationArgumentResolver serverNamespaceArgumentResolver = OperationArgumentResolver.of(WebServerNamespace.class, () -> WebServerNamespace.from(WebServerApplicationContext.getServerNamespace((ApplicationContext)exchange.getApplicationContext())));
            return this.securityContextSupplier.get().map(securityContext -> new InvocationContext((SecurityContext)securityContext, arguments, serverNamespaceArgumentResolver, new ProducibleOperationArgumentResolver(() -> exchange.getRequest().getHeaders().get((Object)"Accept")))).flatMap(invocationContext -> this.handleResult((Publisher)this.invoker.invoke((InvocationContext)invocationContext), exchange.getRequest().getMethod()));
        }

        private Map<String, Object> getArguments(ServerWebExchange exchange, Map<String, String> body) {
            LinkedHashMap<String, Object> arguments = new LinkedHashMap<String, Object>(this.getTemplateVariables(exchange));
            String matchAllRemainingPathSegmentsVariable = this.operation.getRequestPredicate().getMatchAllRemainingPathSegmentsVariable();
            if (matchAllRemainingPathSegmentsVariable != null) {
                arguments.put(matchAllRemainingPathSegmentsVariable, (String)this.getRemainingPathSegments(exchange));
            }
            if (body != null) {
                arguments.putAll(body);
            }
            exchange.getRequest().getQueryParams().forEach((name, values) -> arguments.put((String)name, values.size() != 1 ? values : values.get(0)));
            return arguments;
        }

        private Object getRemainingPathSegments(ServerWebExchange exchange) {
            PathPattern pathPattern = (PathPattern)exchange.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
            if (pathPattern.hasPatternSyntax()) {
                String remainingSegments = pathPattern.extractPathWithinPattern(exchange.getRequest().getPath().pathWithinApplication()).value();
                return this.tokenizePathSegments(remainingSegments);
            }
            return this.tokenizePathSegments(pathPattern.toString());
        }

        private String[] tokenizePathSegments(String value) {
            String[] segments = StringUtils.tokenizeToStringArray((String)value, (String)PATH_SEPARATOR, (boolean)false, (boolean)true);
            for (int i = 0; i < segments.length; ++i) {
                if (!segments[i].contains("%")) continue;
                segments[i] = StringUtils.uriDecode((String)segments[i], (Charset)StandardCharsets.UTF_8);
            }
            return segments;
        }

        private Map<String, String> getTemplateVariables(ServerWebExchange exchange) {
            return (Map)exchange.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        }

        private Mono<ResponseEntity<Object>> handleResult(Publisher<?> result, HttpMethod httpMethod) {
            if (result instanceof Flux) {
                result = ((Flux)result).collectList();
            }
            return Mono.from(result).map(this::toResponseEntity).onErrorMap(InvalidEndpointRequestException.class, ex -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getReason())).defaultIfEmpty((Object)new ResponseEntity(httpMethod != HttpMethod.GET ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND));
        }

        private ResponseEntity<Object> toResponseEntity(Object response) {
            if (!(response instanceof WebEndpointResponse)) {
                return new ResponseEntity(response, HttpStatus.OK);
            }
            WebEndpointResponse webEndpointResponse = (WebEndpointResponse)response;
            MediaType contentType = webEndpointResponse.getContentType() != null ? new MediaType(webEndpointResponse.getContentType()) : null;
            return ResponseEntity.status((int)webEndpointResponse.getStatus()).contentType(contentType).body(webEndpointResponse.getBody());
        }

        public String toString() {
            return "Actuator web endpoint '" + this.operation.getId() + "'";
        }
    }

    @FunctionalInterface
    protected static interface ReactiveWebOperation {
        public Mono<ResponseEntity<Object>> handle(ServerWebExchange var1, Map<String, String> var2);
    }

    @FunctionalInterface
    protected static interface LinksHandler {
        public Object links(ServerWebExchange var1);
    }

    protected static final class ElasticSchedulerInvoker
    implements OperationInvoker {
        private final OperationInvoker invoker;

        public ElasticSchedulerInvoker(OperationInvoker invoker) {
            this.invoker = invoker;
        }

        @Override
        public Object invoke(InvocationContext context) {
            return Mono.fromCallable(() -> this.invoker.invoke(context)).subscribeOn(Schedulers.boundedElastic());
        }
    }
}


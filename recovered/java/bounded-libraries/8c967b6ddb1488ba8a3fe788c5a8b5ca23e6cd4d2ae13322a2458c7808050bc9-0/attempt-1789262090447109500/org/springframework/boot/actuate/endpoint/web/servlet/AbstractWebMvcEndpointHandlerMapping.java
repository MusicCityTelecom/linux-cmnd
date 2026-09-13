/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.boot.web.context.WebServerApplicationContext
 *  org.springframework.context.ApplicationContext
 *  org.springframework.http.HttpHeaders
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.http.server.ServletServerHttpRequest
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.ResponseBody
 *  org.springframework.web.context.WebApplicationContext
 *  org.springframework.web.context.support.WebApplicationContextUtils
 *  org.springframework.web.cors.CorsConfiguration
 *  org.springframework.web.method.HandlerMethod
 *  org.springframework.web.server.ResponseStatusException
 *  org.springframework.web.servlet.HandlerMapping
 *  org.springframework.web.servlet.handler.MatchableHandlerMapping
 *  org.springframework.web.servlet.handler.RequestMatchResult
 *  org.springframework.web.servlet.mvc.method.RequestMappingInfo
 *  org.springframework.web.servlet.mvc.method.RequestMappingInfo$BuilderConfiguration
 *  org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping
 *  org.springframework.web.util.pattern.PathPatternParser
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.actuate.endpoint.web.servlet;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.actuate.endpoint.InvalidEndpointRequestException;
import org.springframework.boot.actuate.endpoint.InvocationContext;
import org.springframework.boot.actuate.endpoint.OperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.ProducibleOperationArgumentResolver;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.WebOperation;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
import org.springframework.boot.actuate.endpoint.web.WebServerNamespace;
import org.springframework.boot.actuate.endpoint.web.servlet.SkipPathExtensionContentNegotiation;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.MatchableHandlerMapping;
import org.springframework.web.servlet.handler.RequestMatchResult;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Flux;

public abstract class AbstractWebMvcEndpointHandlerMapping
extends RequestMappingInfoHandlerMapping
implements InitializingBean,
MatchableHandlerMapping {
    private final EndpointMapping endpointMapping;
    private final Collection<ExposableWebEndpoint> endpoints;
    private final EndpointMediaTypes endpointMediaTypes;
    private final CorsConfiguration corsConfiguration;
    private final boolean shouldRegisterLinksMapping;
    private final Method handleMethod = ReflectionUtils.findMethod(OperationHandler.class, (String)"handle", (Class[])new Class[]{HttpServletRequest.class, Map.class});
    private RequestMappingInfo.BuilderConfiguration builderConfig = new RequestMappingInfo.BuilderConfiguration();

    public AbstractWebMvcEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints, EndpointMediaTypes endpointMediaTypes, boolean shouldRegisterLinksMapping) {
        this(endpointMapping, endpoints, endpointMediaTypes, null, shouldRegisterLinksMapping);
    }

    public AbstractWebMvcEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints, EndpointMediaTypes endpointMediaTypes, CorsConfiguration corsConfiguration, boolean shouldRegisterLinksMapping) {
        this(endpointMapping, endpoints, endpointMediaTypes, corsConfiguration, shouldRegisterLinksMapping, null);
    }

    public AbstractWebMvcEndpointHandlerMapping(EndpointMapping endpointMapping, Collection<ExposableWebEndpoint> endpoints, EndpointMediaTypes endpointMediaTypes, CorsConfiguration corsConfiguration, boolean shouldRegisterLinksMapping, PathPatternParser pathPatternParser) {
        this.endpointMapping = endpointMapping;
        this.endpoints = endpoints;
        this.endpointMediaTypes = endpointMediaTypes;
        this.corsConfiguration = corsConfiguration;
        this.shouldRegisterLinksMapping = shouldRegisterLinksMapping;
        this.setPatternParser(pathPatternParser);
        this.setOrder(-100);
    }

    public void afterPropertiesSet() {
        this.builderConfig = new RequestMappingInfo.BuilderConfiguration();
        if (this.getPatternParser() != null) {
            this.builderConfig.setPatternParser(this.getPatternParser());
        } else {
            this.builderConfig.setPathMatcher(null);
            this.builderConfig.setTrailingSlashMatch(true);
            this.builderConfig.setSuffixPatternMatch(false);
        }
        super.afterPropertiesSet();
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
        return new WebMvcEndpointHandlerMethod(handlerMethod.getBean(), handlerMethod.getMethod());
    }

    public RequestMatchResult match(HttpServletRequest request, String pattern) {
        Assert.isNull((Object)this.getPatternParser(), (String)"This HandlerMapping uses PathPatterns.");
        RequestMappingInfo info = RequestMappingInfo.paths((String[])new String[]{pattern}).options(this.builderConfig).build();
        RequestMappingInfo matchingInfo = info.getMatchingCondition(request);
        if (matchingInfo == null) {
            return null;
        }
        Set patterns = matchingInfo.getPatternsCondition().getPatterns();
        String lookupPath = this.getUrlPathHelper().getLookupPathForRequest(request);
        return new RequestMatchResult((String)patterns.iterator().next(), lookupPath, this.getPathMatcher());
    }

    private void registerMappingForOperation(ExposableWebEndpoint endpoint, WebOperation operation) {
        WebOperationRequestPredicate predicate = operation.getRequestPredicate();
        String path = predicate.getPath();
        String matchAllRemainingPathSegmentsVariable = predicate.getMatchAllRemainingPathSegmentsVariable();
        if (matchAllRemainingPathSegmentsVariable != null) {
            path = path.replace("{*" + matchAllRemainingPathSegmentsVariable + "}", "**");
        }
        this.registerMapping(endpoint, predicate, operation, path);
    }

    protected void registerMapping(ExposableWebEndpoint endpoint, WebOperationRequestPredicate predicate, WebOperation operation, String path) {
        ServletWebOperation servletWebOperation = this.wrapServletWebOperation(endpoint, operation, new ServletWebOperationAdapter(operation));
        this.registerMapping(this.createRequestMappingInfo(predicate, path), new OperationHandler(servletWebOperation), this.handleMethod);
    }

    protected ServletWebOperation wrapServletWebOperation(ExposableWebEndpoint endpoint, WebOperation operation, ServletWebOperation servletWebOperation) {
        return servletWebOperation;
    }

    private RequestMappingInfo createRequestMappingInfo(WebOperationRequestPredicate predicate, String path) {
        return RequestMappingInfo.paths((String[])new String[]{this.endpointMapping.createSubPath(path)}).options(this.builderConfig).methods(new RequestMethod[]{RequestMethod.valueOf((String)predicate.getHttpMethod().name())}).consumes(predicate.getConsumes().toArray(new String[0])).produces(predicate.getProduces().toArray(new String[0])).build();
    }

    private void registerLinksMapping() {
        RequestMappingInfo mapping = RequestMappingInfo.paths((String[])new String[]{this.endpointMapping.createSubPath("")}).methods(new RequestMethod[]{RequestMethod.GET}).produces(this.endpointMediaTypes.getProduced().toArray(new String[0])).options(this.builderConfig).build();
        LinksHandler linksHandler = this.getLinksHandler();
        this.registerMapping(mapping, linksHandler, ReflectionUtils.findMethod(linksHandler.getClass(), (String)"links", (Class[])new Class[]{HttpServletRequest.class, HttpServletResponse.class}));
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

    protected void extendInterceptors(List<Object> interceptors) {
        interceptors.add(new SkipPathExtensionContentNegotiation());
    }

    protected abstract LinksHandler getLinksHandler();

    public Collection<ExposableWebEndpoint> getEndpoints() {
        return this.endpoints;
    }

    private static final class ServletSecurityContext
    implements SecurityContext {
        private final HttpServletRequest request;

        private ServletSecurityContext(HttpServletRequest request) {
            this.request = request;
        }

        @Override
        public Principal getPrincipal() {
            return this.request.getUserPrincipal();
        }

        @Override
        public boolean isUserInRole(String role) {
            return this.request.isUserInRole(role);
        }
    }

    private static class InvalidEndpointBadRequestException
    extends ResponseStatusException {
        InvalidEndpointBadRequestException(InvalidEndpointRequestException cause) {
            super(HttpStatus.BAD_REQUEST, cause.getReason(), (Throwable)cause);
        }
    }

    private static class WebMvcEndpointHandlerMethod
    extends HandlerMethod {
        WebMvcEndpointHandlerMethod(Object bean, Method method) {
            super(bean, method);
        }

        public String toString() {
            return this.getBean().toString();
        }

        public HandlerMethod createWithResolvedBean() {
            return this;
        }
    }

    private static final class OperationHandler {
        private final ServletWebOperation operation;

        OperationHandler(ServletWebOperation operation) {
            this.operation = operation;
        }

        @ResponseBody
        Object handle(HttpServletRequest request, @RequestBody(required=false) Map<String, String> body) {
            return this.operation.handle(request, body);
        }

        public String toString() {
            return this.operation.toString();
        }
    }

    private static class ServletWebOperationAdapter
    implements ServletWebOperation {
        private static final String PATH_SEPARATOR = "/";
        private static final List<Function<Object, Object>> BODY_CONVERTERS;
        private final WebOperation operation;

        ServletWebOperationAdapter(WebOperation operation) {
            this.operation = operation;
        }

        @Override
        public Object handle(HttpServletRequest request, @RequestBody(required=false) Map<String, String> body) {
            HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
            Map<String, Object> arguments = this.getArguments(request, body);
            try {
                ServletSecurityContext securityContext = new ServletSecurityContext(request);
                ProducibleOperationArgumentResolver producibleOperationArgumentResolver = new ProducibleOperationArgumentResolver(() -> headers.get((Object)"Accept"));
                OperationArgumentResolver serverNamespaceArgumentResolver = OperationArgumentResolver.of(WebServerNamespace.class, () -> {
                    WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext((ServletContext)request.getServletContext());
                    return WebServerNamespace.from(WebServerApplicationContext.getServerNamespace((ApplicationContext)applicationContext));
                });
                InvocationContext invocationContext = new InvocationContext(securityContext, arguments, serverNamespaceArgumentResolver, producibleOperationArgumentResolver);
                return this.handleResult(this.operation.invoke(invocationContext), HttpMethod.resolve((String)request.getMethod()));
            }
            catch (InvalidEndpointRequestException ex) {
                throw new InvalidEndpointBadRequestException(ex);
            }
        }

        public String toString() {
            return "Actuator web endpoint '" + this.operation.getId() + "'";
        }

        private Map<String, Object> getArguments(HttpServletRequest request, Map<String, String> body) {
            LinkedHashMap<String, Object> arguments = new LinkedHashMap<String, Object>(this.getTemplateVariables(request));
            String matchAllRemainingPathSegmentsVariable = this.operation.getRequestPredicate().getMatchAllRemainingPathSegmentsVariable();
            if (matchAllRemainingPathSegmentsVariable != null) {
                arguments.put(matchAllRemainingPathSegmentsVariable, (String)this.getRemainingPathSegments(request));
            }
            if (body != null && HttpMethod.POST.name().equals(request.getMethod())) {
                arguments.putAll(body);
            }
            request.getParameterMap().forEach((name, values) -> arguments.put((String)name, ((String[])values).length != 1 ? Arrays.asList(values) : values[0]));
            return arguments;
        }

        private Object getRemainingPathSegments(HttpServletRequest request) {
            String[] patternTokens;
            String[] pathTokens = this.tokenize(request, HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, true);
            int numberOfRemainingPathSegments = pathTokens.length - (patternTokens = this.tokenize(request, HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE, false)).length + 1;
            Assert.state((numberOfRemainingPathSegments >= 0 ? 1 : 0) != 0, (String)"Unable to extract remaining path segments");
            String[] remainingPathSegments = new String[numberOfRemainingPathSegments];
            System.arraycopy(pathTokens, patternTokens.length - 1, remainingPathSegments, 0, numberOfRemainingPathSegments);
            return remainingPathSegments;
        }

        private String[] tokenize(HttpServletRequest request, String attributeName, boolean decode) {
            String value = (String)request.getAttribute(attributeName);
            String[] segments = StringUtils.tokenizeToStringArray((String)value, (String)PATH_SEPARATOR, (boolean)false, (boolean)true);
            if (decode) {
                for (int i = 0; i < segments.length; ++i) {
                    if (!segments[i].contains("%")) continue;
                    segments[i] = StringUtils.uriDecode((String)segments[i], (Charset)StandardCharsets.UTF_8);
                }
            }
            return segments;
        }

        private Map<String, String> getTemplateVariables(HttpServletRequest request) {
            return (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        }

        private Object handleResult(Object result, HttpMethod httpMethod) {
            if (result == null) {
                return new ResponseEntity(httpMethod != HttpMethod.GET ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
            }
            if (!(result instanceof WebEndpointResponse)) {
                return this.convertIfNecessary(result);
            }
            WebEndpointResponse response = (WebEndpointResponse)result;
            MediaType contentType = response.getContentType() != null ? new MediaType(response.getContentType()) : null;
            return ResponseEntity.status((int)response.getStatus()).contentType(contentType).body(this.convertIfNecessary(response.getBody()));
        }

        private Object convertIfNecessary(Object body) {
            for (Function<Object, Object> converter : BODY_CONVERTERS) {
                body = converter.apply(body);
            }
            return body;
        }

        static {
            ArrayList<FluxBodyConverter> converters = new ArrayList<FluxBodyConverter>();
            if (ClassUtils.isPresent((String)"reactor.core.publisher.Flux", (ClassLoader)ServletWebOperationAdapter.class.getClassLoader())) {
                converters.add(new FluxBodyConverter());
            }
            BODY_CONVERTERS = Collections.unmodifiableList(converters);
        }

        private static class FluxBodyConverter
        implements Function<Object, Object> {
            private FluxBodyConverter() {
            }

            @Override
            public Object apply(Object body) {
                if (!(body instanceof Flux)) {
                    return body;
                }
                return ((Flux)body).collectList();
            }
        }
    }

    @FunctionalInterface
    protected static interface ServletWebOperation {
        public Object handle(HttpServletRequest var1, Map<String, String> var2);
    }

    @FunctionalInterface
    protected static interface LinksHandler {
        public Object links(HttpServletRequest var1, HttpServletResponse var2);
    }
}


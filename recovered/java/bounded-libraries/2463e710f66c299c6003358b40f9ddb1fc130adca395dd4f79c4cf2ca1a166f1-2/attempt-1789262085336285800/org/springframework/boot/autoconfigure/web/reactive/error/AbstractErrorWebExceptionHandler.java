/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.boot.web.error.ErrorAttributeOptions
 *  org.springframework.boot.web.error.ErrorAttributeOptions$Include
 *  org.springframework.boot.web.reactive.error.ErrorAttributes
 *  org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.NestedExceptionUtils
 *  org.springframework.core.io.Resource
 *  org.springframework.core.log.LogMessage
 *  org.springframework.http.HttpLogging
 *  org.springframework.http.HttpStatus
 *  org.springframework.http.codec.HttpMessageReader
 *  org.springframework.http.codec.HttpMessageWriter
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.web.reactive.function.BodyInserters
 *  org.springframework.web.reactive.function.server.RouterFunction
 *  org.springframework.web.reactive.function.server.ServerRequest
 *  org.springframework.web.reactive.function.server.ServerResponse
 *  org.springframework.web.reactive.function.server.ServerResponse$BodyBuilder
 *  org.springframework.web.reactive.function.server.ServerResponse$Context
 *  org.springframework.web.reactive.result.view.ViewResolver
 *  org.springframework.web.server.ServerWebExchange
 *  org.springframework.web.util.HtmlUtils
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.autoconfigure.web.reactive.error;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProviders;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.log.LogMessage;
import org.springframework.http.HttpLogging;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Mono;

public abstract class AbstractErrorWebExceptionHandler
implements ErrorWebExceptionHandler,
InitializingBean {
    private static final Set<String> DISCONNECTED_CLIENT_EXCEPTIONS;
    private static final Log logger;
    private final ApplicationContext applicationContext;
    private final ErrorAttributes errorAttributes;
    private final WebProperties.Resources resources;
    private final TemplateAvailabilityProviders templateAvailabilityProviders;
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    public AbstractErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext) {
        Assert.notNull((Object)errorAttributes, (String)"ErrorAttributes must not be null");
        Assert.notNull((Object)resources, (String)"Resources must not be null");
        Assert.notNull((Object)applicationContext, (String)"ApplicationContext must not be null");
        this.errorAttributes = errorAttributes;
        this.resources = resources;
        this.applicationContext = applicationContext;
        this.templateAvailabilityProviders = new TemplateAvailabilityProviders(applicationContext);
    }

    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, (String)"'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, (String)"'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    @Deprecated
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        return this.getErrorAttributes(request, includeStackTrace ? ErrorAttributeOptions.of((ErrorAttributeOptions.Include[])new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.STACK_TRACE}) : ErrorAttributeOptions.defaults());
    }

    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        return this.errorAttributes.getErrorAttributes(request, options);
    }

    protected Throwable getError(ServerRequest request) {
        return this.errorAttributes.getError(request);
    }

    protected boolean isTraceEnabled(ServerRequest request) {
        return this.getBooleanParameter(request, "trace");
    }

    protected boolean isMessageEnabled(ServerRequest request) {
        return this.getBooleanParameter(request, "message");
    }

    protected boolean isBindingErrorsEnabled(ServerRequest request) {
        return this.getBooleanParameter(request, "errors");
    }

    private boolean getBooleanParameter(ServerRequest request, String parameterName) {
        String parameter = request.queryParam(parameterName).orElse("false");
        return !"false".equalsIgnoreCase(parameter);
    }

    protected Mono<ServerResponse> renderErrorView(String viewName, ServerResponse.BodyBuilder responseBody, Map<String, Object> error) {
        if (this.isTemplateAvailable(viewName)) {
            return responseBody.render(viewName, error);
        }
        Resource resource = this.resolveResource(viewName);
        if (resource != null) {
            return responseBody.body(BodyInserters.fromResource((Resource)resource));
        }
        return Mono.empty();
    }

    private boolean isTemplateAvailable(String viewName) {
        return this.templateAvailabilityProviders.getProvider(viewName, this.applicationContext) != null;
    }

    private Resource resolveResource(String viewName) {
        for (String location : this.resources.getStaticLocations()) {
            try {
                Resource resource = this.applicationContext.getResource(location);
                resource = resource.createRelative(viewName + ".html");
                if (!resource.exists()) continue;
                return resource;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return null;
    }

    protected Mono<ServerResponse> renderDefaultErrorView(ServerResponse.BodyBuilder responseBody, Map<String, Object> error) {
        StringBuilder builder = new StringBuilder();
        Date timestamp = (Date)error.get("timestamp");
        Object message = error.get("message");
        Object trace = error.get("trace");
        Object requestId = error.get("requestId");
        builder.append("<html><body><h1>Whitelabel Error Page</h1>").append("<p>This application has no configured error view, so you are seeing this as a fallback.</p>").append("<div id='created'>").append(timestamp).append("</div>").append("<div>[").append(requestId).append("] There was an unexpected error (type=").append(this.htmlEscape(error.get("error"))).append(", status=").append(this.htmlEscape(error.get("status"))).append(").</div>");
        if (message != null) {
            builder.append("<div>").append(this.htmlEscape(message)).append("</div>");
        }
        if (trace != null) {
            builder.append("<div style='white-space:pre-wrap;'>").append(this.htmlEscape(trace)).append("</div>");
        }
        builder.append("</body></html>");
        return responseBody.bodyValue((Object)builder.toString());
    }

    private String htmlEscape(Object input) {
        return input != null ? HtmlUtils.htmlEscape((String)input.toString()) : null;
    }

    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(this.messageWriters)) {
            throw new IllegalArgumentException("Property 'messageWriters' is required");
        }
    }

    protected abstract RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes var1);

    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        if (exchange.getResponse().isCommitted() || this.isDisconnectedClientError(throwable)) {
            return Mono.error((Throwable)throwable);
        }
        this.errorAttributes.storeErrorInformation(throwable, exchange);
        ServerRequest request = ServerRequest.create((ServerWebExchange)exchange, this.messageReaders);
        return this.getRoutingFunction(this.errorAttributes).route(request).switchIfEmpty(Mono.error((Throwable)throwable)).flatMap(handler -> handler.handle(request)).doOnNext(response -> this.logError(request, (ServerResponse)response, throwable)).flatMap(response -> this.write(exchange, (ServerResponse)response));
    }

    private boolean isDisconnectedClientError(Throwable ex) {
        return DISCONNECTED_CLIENT_EXCEPTIONS.contains(ex.getClass().getSimpleName()) || this.isDisconnectedClientErrorMessage(NestedExceptionUtils.getMostSpecificCause((Throwable)ex).getMessage());
    }

    private boolean isDisconnectedClientErrorMessage(String message) {
        message = message != null ? message.toLowerCase() : "";
        return message.contains("broken pipe") || message.contains("connection reset by peer");
    }

    protected void logError(ServerRequest request, ServerResponse response, Throwable throwable) {
        if (logger.isDebugEnabled()) {
            logger.debug((Object)(request.exchange().getLogPrefix() + this.formatError(throwable, request)));
        }
        if (HttpStatus.resolve((int)response.rawStatusCode()) != null && response.statusCode().equals((Object)HttpStatus.INTERNAL_SERVER_ERROR)) {
            logger.error((Object)LogMessage.of(() -> String.format("%s 500 Server Error for %s", request.exchange().getLogPrefix(), this.formatRequest(request))), throwable);
        }
    }

    private String formatError(Throwable ex, ServerRequest request) {
        String reason = ex.getClass().getSimpleName() + ": " + ex.getMessage();
        return "Resolved [" + reason + "] for HTTP " + request.methodName() + " " + request.path();
    }

    private String formatRequest(ServerRequest request) {
        String rawQuery = request.uri().getRawQuery();
        String query = StringUtils.hasText((String)rawQuery) ? "?" + rawQuery : "";
        return "HTTP " + request.methodName() + " \"" + request.path() + query + "\"";
    }

    private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
        exchange.getResponse().getHeaders().setContentType(response.headers().getContentType());
        return response.writeTo(exchange, (ServerResponse.Context)new ResponseContext());
    }

    static {
        HashSet<String> exceptions = new HashSet<String>();
        exceptions.add("AbortedException");
        exceptions.add("ClientAbortException");
        exceptions.add("EOFException");
        exceptions.add("EofException");
        DISCONNECTED_CLIENT_EXCEPTIONS = Collections.unmodifiableSet(exceptions);
        logger = HttpLogging.forLogName(AbstractErrorWebExceptionHandler.class);
    }

    private class ResponseContext
    implements ServerResponse.Context {
        private ResponseContext() {
        }

        public List<HttpMessageWriter<?>> messageWriters() {
            return AbstractErrorWebExceptionHandler.this.messageWriters;
        }

        public List<ViewResolver> viewResolvers() {
            return AbstractErrorWebExceptionHandler.this.viewResolvers;
        }
    }
}


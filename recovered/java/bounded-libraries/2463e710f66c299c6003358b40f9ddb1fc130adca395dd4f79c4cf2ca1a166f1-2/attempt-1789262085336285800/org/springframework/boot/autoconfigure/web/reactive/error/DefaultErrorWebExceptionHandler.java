/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.springframework.boot.web.error.ErrorAttributeOptions
 *  org.springframework.boot.web.error.ErrorAttributeOptions$Include
 *  org.springframework.boot.web.reactive.error.ErrorAttributes
 *  org.springframework.context.ApplicationContext
 *  org.springframework.http.HttpStatus$Series
 *  org.springframework.http.InvalidMediaTypeException
 *  org.springframework.http.MediaType
 *  org.springframework.web.reactive.function.BodyInserters
 *  org.springframework.web.reactive.function.server.RequestPredicate
 *  org.springframework.web.reactive.function.server.RequestPredicates
 *  org.springframework.web.reactive.function.server.RouterFunction
 *  org.springframework.web.reactive.function.server.RouterFunctions
 *  org.springframework.web.reactive.function.server.ServerRequest
 *  org.springframework.web.reactive.function.server.ServerResponse
 *  org.springframework.web.reactive.function.server.ServerResponse$BodyBuilder
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.autoconfigure.web.reactive.error;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.reactivestreams.Publisher;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DefaultErrorWebExceptionHandler
extends AbstractErrorWebExceptionHandler {
    private static final MediaType TEXT_HTML_UTF8 = new MediaType("text", "html", StandardCharsets.UTF_8);
    private static final Map<HttpStatus.Series, String> SERIES_VIEWS;
    private final ErrorProperties errorProperties;

    public DefaultErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resources, applicationContext);
        this.errorProperties = errorProperties;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route((RequestPredicate)this.acceptsTextHtml(), this::renderErrorView).andRoute(RequestPredicates.all(), this::renderErrorResponse);
    }

    protected Mono<ServerResponse> renderErrorView(ServerRequest request) {
        Map<String, Object> error = this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.TEXT_HTML));
        int errorStatus = this.getHttpStatus(error);
        ServerResponse.BodyBuilder responseBody = ServerResponse.status((int)errorStatus).contentType(TEXT_HTML_UTF8);
        return Flux.just((Object[])this.getData(errorStatus).toArray(new String[0])).flatMap(viewName -> this.renderErrorView((String)viewName, responseBody, error)).switchIfEmpty((Publisher)(this.errorProperties.getWhitelabel().isEnabled() ? this.renderDefaultErrorView(responseBody, error) : Mono.error((Throwable)this.getError(request)))).next();
    }

    private List<String> getData(int errorStatus) {
        ArrayList<String> data = new ArrayList<String>();
        data.add("error/" + errorStatus);
        HttpStatus.Series series = HttpStatus.Series.resolve((int)errorStatus);
        if (series != null) {
            data.add("error/" + SERIES_VIEWS.get(series));
        }
        data.add("error/error");
        return data;
    }

    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> error = this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.ALL));
        return ServerResponse.status((int)this.getHttpStatus(error)).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(error));
    }

    protected ErrorAttributeOptions getErrorAttributeOptions(ServerRequest request, MediaType mediaType) {
        ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
        if (this.errorProperties.isIncludeException()) {
            options = options.including(new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.EXCEPTION});
        }
        if (this.isIncludeStackTrace(request, mediaType)) {
            options = options.including(new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.STACK_TRACE});
        }
        if (this.isIncludeMessage(request, mediaType)) {
            options = options.including(new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.MESSAGE});
        }
        if (this.isIncludeBindingErrors(request, mediaType)) {
            options = options.including(new ErrorAttributeOptions.Include[]{ErrorAttributeOptions.Include.BINDING_ERRORS});
        }
        return options;
    }

    protected boolean isIncludeStackTrace(ServerRequest request, MediaType produces) {
        switch (this.errorProperties.getIncludeStacktrace()) {
            case ALWAYS: {
                return true;
            }
            case ON_PARAM: {
                return this.isTraceEnabled(request);
            }
        }
        return false;
    }

    protected boolean isIncludeMessage(ServerRequest request, MediaType produces) {
        switch (this.errorProperties.getIncludeMessage()) {
            case ALWAYS: {
                return true;
            }
            case ON_PARAM: {
                return this.isMessageEnabled(request);
            }
        }
        return false;
    }

    protected boolean isIncludeBindingErrors(ServerRequest request, MediaType produces) {
        switch (this.errorProperties.getIncludeBindingErrors()) {
            case ALWAYS: {
                return true;
            }
            case ON_PARAM: {
                return this.isBindingErrorsEnabled(request);
            }
        }
        return false;
    }

    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return (Integer)errorAttributes.get("status");
    }

    protected RequestPredicate acceptsTextHtml() {
        return serverRequest -> {
            try {
                List acceptedMediaTypes = serverRequest.headers().accept();
                acceptedMediaTypes.removeIf(arg_0 -> ((MediaType)MediaType.ALL).equalsTypeAndSubtype(arg_0));
                MediaType.sortBySpecificityAndQuality((List)acceptedMediaTypes);
                return acceptedMediaTypes.stream().anyMatch(arg_0 -> ((MediaType)MediaType.TEXT_HTML).isCompatibleWith(arg_0));
            }
            catch (InvalidMediaTypeException ex) {
                return false;
            }
        };
    }

    static {
        EnumMap<HttpStatus.Series, String> views = new EnumMap<HttpStatus.Series, String>(HttpStatus.Series.class);
        views.put(HttpStatus.Series.CLIENT_ERROR, "4xx");
        views.put(HttpStatus.Series.SERVER_ERROR, "5xx");
        SERIES_VIEWS = Collections.unmodifiableMap(views);
    }
}


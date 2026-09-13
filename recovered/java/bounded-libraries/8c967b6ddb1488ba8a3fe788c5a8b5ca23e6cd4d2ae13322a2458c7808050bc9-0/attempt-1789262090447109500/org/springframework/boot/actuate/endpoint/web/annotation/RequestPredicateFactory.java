/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.ResolvableType
 *  org.springframework.core.io.Resource
 *  org.springframework.util.Assert
 */
package org.springframework.boot.actuate.endpoint.web.annotation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import org.springframework.boot.actuate.endpoint.OperationType;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationMethod;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.WebEndpointHttpMethod;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.WebOperationRequestPredicate;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

class RequestPredicateFactory {
    private final EndpointMediaTypes endpointMediaTypes;

    RequestPredicateFactory(EndpointMediaTypes endpointMediaTypes) {
        Assert.notNull((Object)endpointMediaTypes, (String)"EndpointMediaTypes must not be null");
        this.endpointMediaTypes = endpointMediaTypes;
    }

    WebOperationRequestPredicate getRequestPredicate(String rootPath, DiscoveredOperationMethod operationMethod) {
        Parameter[] selectorParameters;
        Method method = operationMethod.getMethod();
        Parameter allRemainingPathSegmentsParameter = this.getAllRemainingPathSegmentsParameter(selectorParameters = (Parameter[])Arrays.stream(method.getParameters()).filter(this::hasSelector).toArray(Parameter[]::new));
        String path = this.getPath(rootPath, selectorParameters, allRemainingPathSegmentsParameter != null);
        WebEndpointHttpMethod httpMethod = this.determineHttpMethod(operationMethod.getOperationType());
        Collection<String> consumes = this.getConsumes(httpMethod, method);
        Collection<String> produces = this.getProduces(operationMethod, method);
        return new WebOperationRequestPredicate(path, httpMethod, consumes, produces);
    }

    private Parameter getAllRemainingPathSegmentsParameter(Parameter[] selectorParameters) {
        Parameter trailingPathsParameter = null;
        for (Parameter selectorParameter : selectorParameters) {
            Selector selector = selectorParameter.getAnnotation(Selector.class);
            if (selector.match() != Selector.Match.ALL_REMAINING) continue;
            Assert.state((trailingPathsParameter == null ? 1 : 0) != 0, (String)"@Selector annotation with Match.ALL_REMAINING must be unique");
            trailingPathsParameter = selectorParameter;
        }
        if (trailingPathsParameter != null) {
            Assert.state((trailingPathsParameter == selectorParameters[selectorParameters.length - 1] ? 1 : 0) != 0, (String)"@Selector annotation with Match.ALL_REMAINING must be the last parameter");
        }
        return trailingPathsParameter;
    }

    private String getPath(String rootPath, Parameter[] selectorParameters, boolean matchRemainingPathSegments) {
        StringBuilder path = new StringBuilder(rootPath);
        for (int i = 0; i < selectorParameters.length; ++i) {
            path.append("/{");
            if (i == selectorParameters.length - 1 && matchRemainingPathSegments) {
                path.append("*");
            }
            path.append(selectorParameters[i].getName());
            path.append("}");
        }
        return path.toString();
    }

    private boolean hasSelector(Parameter parameter) {
        return parameter.getAnnotation(Selector.class) != null;
    }

    private Collection<String> getConsumes(WebEndpointHttpMethod httpMethod, Method method) {
        if (WebEndpointHttpMethod.POST == httpMethod && this.consumesRequestBody(method)) {
            return this.endpointMediaTypes.getConsumed();
        }
        return Collections.emptyList();
    }

    private Collection<String> getProduces(DiscoveredOperationMethod operationMethod, Method method) {
        if (!operationMethod.getProducesMediaTypes().isEmpty()) {
            return operationMethod.getProducesMediaTypes();
        }
        if (Void.class.equals(method.getReturnType()) || Void.TYPE.equals(method.getReturnType())) {
            return Collections.emptyList();
        }
        if (this.producesResource(method)) {
            return Collections.singletonList("application/octet-stream");
        }
        return this.endpointMediaTypes.getProduced();
    }

    private boolean producesResource(Method method) {
        if (Resource.class.equals(method.getReturnType())) {
            return true;
        }
        if (WebEndpointResponse.class.isAssignableFrom(method.getReturnType())) {
            ResolvableType returnType = ResolvableType.forMethodReturnType((Method)method);
            return ResolvableType.forClass(Resource.class).isAssignableFrom(returnType.getGeneric(new int[]{0}));
        }
        return false;
    }

    private boolean consumesRequestBody(Method method) {
        return Stream.of(method.getParameters()).anyMatch(parameter -> parameter.getAnnotation(Selector.class) == null);
    }

    private WebEndpointHttpMethod determineHttpMethod(OperationType operationType) {
        if (operationType == OperationType.WRITE) {
            return WebEndpointHttpMethod.POST;
        }
        if (operationType == OperationType.DELETE) {
            return WebEndpointHttpMethod.DELETE;
        }
        return WebEndpointHttpMethod.GET;
    }
}


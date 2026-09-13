/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.annotation.MergedAnnotations$SearchStrategy
 */
package org.springframework.boot.actuate.endpoint.web.annotation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.actuate.endpoint.EndpointFilter;
import org.springframework.boot.actuate.endpoint.EndpointId;
import org.springframework.boot.actuate.endpoint.Operation;
import org.springframework.boot.actuate.endpoint.annotation.DiscoveredOperationMethod;
import org.springframework.boot.actuate.endpoint.annotation.EndpointDiscoverer;
import org.springframework.boot.actuate.endpoint.invoke.OperationInvoker;
import org.springframework.boot.actuate.endpoint.invoke.ParameterValueMapper;
import org.springframework.boot.actuate.endpoint.web.PathMapper;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpoint;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.DiscoveredControllerEndpoint;
import org.springframework.boot.actuate.endpoint.web.annotation.ExposableControllerEndpoint;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.MergedAnnotations;

public class ControllerEndpointDiscoverer
extends EndpointDiscoverer<ExposableControllerEndpoint, Operation>
implements ControllerEndpointsSupplier {
    private final List<PathMapper> endpointPathMappers;

    public ControllerEndpointDiscoverer(ApplicationContext applicationContext, List<PathMapper> endpointPathMappers, Collection<EndpointFilter<ExposableControllerEndpoint>> filters) {
        super(applicationContext, ParameterValueMapper.NONE, Collections.emptyList(), filters);
        this.endpointPathMappers = endpointPathMappers;
    }

    @Override
    protected boolean isEndpointTypeExposed(Class<?> beanType) {
        MergedAnnotations annotations = MergedAnnotations.from(beanType, (MergedAnnotations.SearchStrategy)MergedAnnotations.SearchStrategy.SUPERCLASS);
        return annotations.isPresent(ControllerEndpoint.class) || annotations.isPresent(RestControllerEndpoint.class);
    }

    @Override
    protected ExposableControllerEndpoint createEndpoint(Object endpointBean, EndpointId id, boolean enabledByDefault, Collection<Operation> operations) {
        String rootPath = PathMapper.getRootPath(this.endpointPathMappers, id);
        return new DiscoveredControllerEndpoint(this, endpointBean, id, rootPath, enabledByDefault);
    }

    @Override
    protected Operation createOperation(EndpointId endpointId, DiscoveredOperationMethod operationMethod, OperationInvoker invoker) {
        throw new IllegalStateException("ControllerEndpoints must not declare operations");
    }

    @Override
    protected EndpointDiscoverer.OperationKey createOperationKey(Operation operation) {
        throw new IllegalStateException("ControllerEndpoints must not declare operations");
    }
}

